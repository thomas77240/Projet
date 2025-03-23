package SAE31_2024.controller;

import SAE31_2024.model.HexTileModel;
import SAE31_2024.toolbox.ToolBox;
import SAE31_2024.view.HexTileView;

/**
 * The HexTileController class manages the interaction between the HexTileModel and HexTileView.
 */
public class HexTileController {

    private HexTileModel model;
    private HexTileView view;
    private int rotation;

    /**
     * Default constructor initializing the model and view.
     */
    public HexTileController() throws IllegalArgumentException {
        int biome1 = (int)(Math.random()*200 % ToolBox.NB_BIOMES);
        int biome2 = (int)(Math.random()*200 % ToolBox.NB_BIOMES);
        int length = (int)(Math.random()*200 % ToolBox.NB_SIDES);

        this.construct(createBiomesArray(biome1,biome2,length));
    }

    /**
     * Constructor initializing the model and view with specified biomes.
     *
     * @param biome1 the first biome type
     * @param biome2 the second biome type
     * @param lengthBiome1 the number of sides with the first biome type
     */
    public HexTileController(int biome1, int biome2, int lengthBiome1) throws IllegalArgumentException {
        this.construct(createBiomesArray(biome1, biome2, lengthBiome1));
    }

    /**
     * Constructor initializing the model and view with specified biomes array.
     *
     * @param sidesBiomes an array representing the biomes on each side
     * @throws IllegalArgumentException if the array length is not 6
     */
    public HexTileController(int[] sidesBiomes) throws IllegalArgumentException {
        this.construct(sidesBiomes);
    }

    private void construct(int[] sidesBiomes) throws IllegalArgumentException {
        if (sidesBiomes == null || sidesBiomes.length != 6) {
            throw new IllegalArgumentException("Invalid length for the argument sidesBiomes");
        }
        this.model = new HexTileModel(sidesBiomes);
        this.view = new HexTileView(model.getSidesBiomes());
    }

    /**
     * Returns the HexTileModel.
     *
     * @return the model
     */
    public HexTileModel getModel() {
        return model;
    }

    /**
     * Returns the HexTileView.
     *
     * @return the view
     */
    public HexTileView getView() {
        return view;
    }

    /**
     * Creates a clone of this HexTileController.
     *
     * @return a new HexTileController with the same biomes
     */
    public HexTileController clone() {
        return new HexTileController(model.getSidesBiomes());
    }

    /**
     * Returns a string representation of the HexTileController.
     *
     * @return a string representing the biomes and their counts
     */
    @Override
    public String toString() {
        int[] biomes = model.getSidesBiomes();
        int biome1 = biomes[0];
        int biome2 = 0;
        int countBiome1 = 0;
        for (int side : biomes) {
            if (side == biome1) {
                countBiome1++;
            } else {
                biome2 = side;
            }
        }
        return "" + biome1 + biome2 + countBiome1;
    }

    /**
     * Helper method to create a biomes array.
     *
     * @param biome1 the first biome type
     * @param biome2 the second biome type
     * @param lengthBiome1 the number of sides with the first biome type
     * @return an array representing the biomes on each side
     */
    private static int[] createBiomesArray(int biome1, int biome2, int lengthBiome1) {
        int[] sidesBiomes = new int[6];
        for (int i = 0; i < lengthBiome1; i++) {
            sidesBiomes[i] = biome1;
        }
        for (int i = lengthBiome1; i < sidesBiomes.length; i++) {
            sidesBiomes[i] = biome2;
        }
        return sidesBiomes;
    }
}