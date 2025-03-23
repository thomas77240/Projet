package SAE31_2024.model;

/**
 * The BiomesBags class represents a collection of hexagonal tiles associated with a specific biome.
 */
public class BiomesBags {

    private HexTileModel[] bagContent;
    private int biome;

    /**
     * Constructs a BiomesBags object with the specified biome.
     *
     * @param biome the biome associated with this bag
     */
    public BiomesBags(int biome) {
        this.bagContent = new HexTileModel[1];
        this.biome = biome;
    }

    /**
     * Constructs a BiomesBags object with no specified biome.
     */
    public BiomesBags() {
        this.bagContent = new HexTileModel[1];
    }

    /**
     * Adds a hexagonal tile to the bag.
     *
     * @param tile the hexagonal tile to add
     */
    public void add(HexTileModel tile) {
        for (HexTileModel tileOfBag : bagContent) {
            if (tileOfBag == tile) {
                return;
            }
        }

        if (bagContent[bagContent.length - 1] == null) {
            bagContent[bagContent.length - 1] = tile;
        } else {
            HexTileModel[] temp = new HexTileModel[bagContent.length + 1];
            System.arraycopy(bagContent, 0, temp, 0, bagContent.length);
            temp[bagContent.length] = tile;
            bagContent = temp;
        }
    }

    /**
     * Gets the content of the bag.
     *
     * @return an array of hexagonal tiles in the bag
     */
    public HexTileModel[] getBagContent() {
        return bagContent;
    }

    /**
     * Returns the score of this biome bag.
     *
     * @return the score, calculated as the number of tiles squared
     */
    public int getBagScore() {
        return bagContent.length * bagContent.length;
    }

    /**
     * Gets the biome associated with this bag.
     *
     * @return the biome
     */
    public int getBiome() {
        return biome;
    }

    /**
     * Sets the biome associated with this bag.
     *
     * @param biome the biome to set
     */
    public void setBiome(int biome) {
        this.biome = biome;
    }
}