package com.mixologyxp;

import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.ObjectComposition;
import net.runelite.api.Skill;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetType;
import net.runelite.api.events.GameTick;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@PluginDescriptor(
		name = "Mixology XP",
		description = "Detects the best potion for the best XP in Mastering Mixology.",
		tags = {"herblore", "mixology"}
)
public class MixologyXPPlugin extends Plugin {

	private static final int WIDGET_GROUP_ID = 882;

	// Map sprite IDs to directions
	private static final Map<Integer, String> spriteDirectionMap = new HashMap<>();

	static {
		spriteDirectionMap.put(5672, "East");
		spriteDirectionMap.put(5673, "West");
		spriteDirectionMap.put(5674, "North");
	}

	private Potion highestPotion;
	private Potion lastSentPotion = null;
	private String currentDirection = null;
	private String tempDirection = null;

	@Inject
	private Client client;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private HerbloreOverlay herbloreOverlay;

	@Override
	protected void startUp() throws Exception {
		overlayManager.add(herbloreOverlay);
	}

	@Override
	protected void shutDown() throws Exception {
		overlayManager.remove(herbloreOverlay);
	}

	@Subscribe
	public void onGameTick(GameTick event) {
		highestPotion = null;
		currentDirection = null;
		tempDirection = null;

		Widget widgetGroup = client.getWidget(WIDGET_GROUP_ID, 0);
		if (widgetGroup != null) {
			findHighestPotion(widgetGroup);
		}
	}

	private void findHighestPotion(Widget widget) {
		int playerHerbloreLevel = client.getRealSkillLevel(Skill.HERBLORE);

		if (widget.getType() == WidgetType.GRAPHIC) {
			int spriteId = widget.getSpriteId();
			if (spriteDirectionMap.containsKey(spriteId)) {
				tempDirection = spriteDirectionMap.get(spriteId);
			}
		}

		if (widget.getText() != null && !widget.getText().isEmpty()) {
			for (Potion potion : PotionData.getPotions()) {
				if (widget.getText().contains(potion.getName()) && potion.getHerbloreLevel() <= playerHerbloreLevel) {
					if (highestPotion == null || potion.getHerbloreLevel() > highestPotion.getHerbloreLevel()) {
						highestPotion = potion;
						currentDirection = tempDirection;
					}
				}
			}
		}

		if (widget.getDynamicChildren() != null) {
			for (Widget child : widget.getDynamicChildren()) {
				findHighestPotion(child);
			}
		}
		if (widget.getStaticChildren() != null) {
			for (Widget child : widget.getStaticChildren()) {
				findHighestPotion(child);
			}
		}
	}

	public ObjectComposition getMorphedGameObject(GameObject gameObject) {
		if (gameObject != null) {
			ObjectComposition objectComposition = client.getObjectDefinition(gameObject.getId());
			if (objectComposition != null && objectComposition.getImpostorIds() != null) {
				return objectComposition.getImpostor();
			}
		}
		return null;
	}

	public Potion getHighestPotion() {
		return highestPotion;
	}

	public String getCurrentDirection() {
		return currentDirection;
	}

	public boolean isBestPotion(Potion potion) {
		return true;
	}
}
