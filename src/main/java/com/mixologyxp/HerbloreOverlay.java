package com.mixologyxp;

import net.runelite.api.*;
import net.runelite.client.ui.overlay.OverlayPanel;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.components.LineComponent;
import net.runelite.client.ui.overlay.components.TitleComponent;
import net.runelite.client.ui.overlay.outline.ModelOutlineRenderer;

import javax.inject.Inject;
import java.awt.*;

@SuppressWarnings("deprecation")
public class HerbloreOverlay extends OverlayPanel {
    @SuppressWarnings("deprecation")
    private final MixologyXPPlugin plugin;
    private final Client client;
    private final ModelOutlineRenderer outlineRenderer;

    // Object IDs
    private static final int OBJECT_ID_NORTH = 55390;
    private static final int OBJECT_ID_EAST = 55389;
    private static final int OBJECT_ID_WEST = 55391;
    private static final int OBJECT_ID_SPECIAL = 54917;
    private static final int OBJECT_ID_STANDARD = 55395;

    // Graphical object IDs
    private static final int GRAPHICS_OBJECT_ID_WEST = 2955;
    private static final int GRAPHICS_OBJECT_ID_NORTH = 2954;

    @Inject
    public HerbloreOverlay(Client client, MixologyXPPlugin plugin, ModelOutlineRenderer outlineRenderer) {
        super(plugin);
        this.client = client;
        this.plugin = plugin;
        this.outlineRenderer = outlineRenderer;
        setPosition(OverlayPosition.TOP_LEFT);
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        Potion highestPotion = plugin.getHighestPotion();
        String direction = plugin.getCurrentDirection();

        if (highestPotion != null) {
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Mixology")
                    .color(Color.GREEN)
                    .build());

            panelComponent.getChildren().add(LineComponent.builder()
                    .left(highestPotion.getName())
                    .build());

            panelComponent.getChildren().add(LineComponent.builder()
                    .left("Combination:")
                    .right(highestPotion.getCombination())
                    .build());

            // First check for graphical objects and outline in green if present
            if (isGraphicsObjectPresent(GRAPHICS_OBJECT_ID_WEST)) {
                outlineObject(OBJECT_ID_WEST, Color.GREEN);  // Outline west object in green
            }
            else if (isGraphicsObjectPresent(GRAPHICS_OBJECT_ID_NORTH)) {
                outlineObject(OBJECT_ID_NORTH, Color.GREEN);  // Outline north object in green
            }
            else {
                // Check if the inventory contains the finished potion (special object)
                boolean hasFinishedPotion = inventoryContainsPotion(highestPotion.getFinishedPotionId());

                if (hasFinishedPotion) {
                    outlineObject(OBJECT_ID_SPECIAL, Color.CYAN);
                }
                else {
                    // Check for directional potion or morph ID
                    boolean hasDirectionalPotion = inventoryContainsPotion(highestPotion.getDirectionalPotionId());
                    if (hasDirectionalPotion) {
                        outlineDirectionalObject(direction);
                    }
                    else if (isMorphObjectPresent(highestPotion.getMorphId())) {
                        outlineObject(OBJECT_ID_STANDARD, Color.CYAN);
                    }
                }
            }
        }

        return super.render(graphics);
    }

    // Method to check if the graphical object with the specified ID is present
    private boolean isGraphicsObjectPresent(int graphicsObjectId) {
        for (GraphicsObject graphicsObject : client.getGraphicsObjects()) {
            if (graphicsObject.getId() == graphicsObjectId) {
                return true;  // Found the graphical object
            }
        }
        return false;  // Graphical object not found
    }

    // Check if the player's inventory contains a specific potion ID
    private boolean inventoryContainsPotion(int potionId) {
        ItemContainer inventory = client.getItemContainer(InventoryID.INVENTORY);
        if (inventory != null) {
            for (Item item : inventory.getItems()) {
                if (item != null && item.getId() == potionId) {
                    return true;
                }
            }
        }
        return false;
    }

    // Use getMorphedGameObject to check if the correct morph object is present
    private boolean isMorphObjectPresent(int morphObjectId) {
        Tile[][][] tiles = client.getScene().getTiles();
        for (int z = 0; z < tiles.length; z++) {
            for (int x = 0; x < tiles[z].length; x++) {
                for (int y = 0; y < tiles[z][x].length; y++) {
                    Tile tile = tiles[z][x][y];
                    if (tile != null) {
                        for (GameObject gameObject : tile.getGameObjects()) {
                            if (gameObject != null) {
                                ObjectComposition morphedObject = plugin.getMorphedGameObject(gameObject);
                                if (morphedObject != null && morphedObject.getId() == morphObjectId) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    // Outline directional object based on the direction (North, East, or West)
    private void outlineDirectionalObject(String direction) {
        if ("North".equals(direction)) {
            outlineObject(OBJECT_ID_NORTH, Color.CYAN);
        } else if ("East".equals(direction)) {
            outlineObject(OBJECT_ID_EAST, Color.CYAN);
        } else if ("West".equals(direction)) {
            outlineObject(OBJECT_ID_WEST, Color.CYAN);
        }
    }

    // Method to outline the game object with the specified object ID and color
    private void outlineObject(int objectId, Color color) {
        Tile[][][] tiles = client.getScene().getTiles();
        for (int z = 0; z < tiles.length; z++) {
            for (int x = 0; x < tiles[z].length; x++) {
                for (int y = 0; y < tiles[z][x].length; y++) {
                    Tile tile = tiles[z][x][y];
                    if (tile != null) {
                        for (GameObject gameObject : tile.getGameObjects()) {
                            if (gameObject != null && gameObject.getId() == objectId) {
                                outlineRenderer.drawOutline(gameObject, 2, color, 2);  // Outline the object with the specified color
                            }
                        }
                    }
                }
            }
        }
    }
}
