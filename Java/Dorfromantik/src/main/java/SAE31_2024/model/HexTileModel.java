package SAE31_2024.model;

import SAE31_2024.toolbox.ToolBox;

/**
 * The HexTileModel class represents a hexagonal tile with biomes on its sides.
 */
public class HexTileModel {

    private final int[] sides;
    private final int biome1;
    private final int biome2;
    private BiomesBags biomesBags1;
    private BiomesBags biomesBags2;
    private int x;
    private int y;

    /**
     * Constructor for HexTileModel.
     * Initializes the sides of the hexagonal tile with an int[] corresponding to the sides biomes.
     *
     * @param sides the side's biomes.
     */
    public HexTileModel(int[] sides) {
        this.sides = sides;
        this.biome1 = sides[0];
        this.biome2 = findSecondBiome(sides, biome1);
    }

    /**
     * Constructor for HexTileModel.
     * Initializes the sides of the hexagonal tile with random biomes.
     */
    public HexTileModel() {
        this.sides = new int[ToolBox.NB_SIDES];
        if (Math.random() > 0.15) {
            this.biome1 = getRandomBiome();
            this.biome2 = getRandomBiome();
            fillSidesWithBiomes();
        } else {
            this.biome1 = getRandomBiome();
            fill(this.biome1);
            this.biome2 = this.biome1;
        }
    }

    /**
     * Finds the second biome in the sides array.
     *
     * @param sides the sides array
     * @param biome1 the first biome
     * @return the second biome
     */
    private int findSecondBiome(int[] sides, int biome1) {
        for (int side : sides) {
            if (side != biome1) {
                return side;
            }
        }
        return biome1;
    }

    /**
     * Generates a random biome.
     *
     * @return a random biome
     */
    private int getRandomBiome() {
        int biome = (int) (Math.random() * ToolBox.NB_BIOMES);
        return biome >= 5 ? biome - 1 : biome;
    }

    /**
     * Fills the sides array with biomes.
     */
    private void fillSidesWithBiomes() {
        int mid = (int) (Math.random() * ToolBox.NB_SIDES);
        int nbSide2 = (int) (Math.random() * (ToolBox.NB_SIDES - 3) + 1);
        fill(this.biome1);
        for (int i = mid; i < mid + nbSide2; i++) {
            sides[i % ToolBox.NB_SIDES] = this.biome2;
        }
    }

    /**
     * Fills the sides array with the specified biome.
     *
     * @param biome the biome to fill
     */
    private void fill(int biome) {
        for (int i = 0; i < ToolBox.NB_SIDES; i++) {
            sides[i] = biome;
        }
    }

    /**
     * Gets the biomes on the sides of the hexagonal tile.
     *
     * @return an array of biomes on the sides
     */
    public int[] getSidesBiomes() {
        return sides;
    }

    /**
     * Returns a string representation of the hexagonal tile.
     *
     * @return a string representation of the hexagonal tile
     */
    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder("Sides : |--- 1 ---|--- 2 ---|--- 3 ---|--- 4 ---|--- 5 ---|--- 6 ---|\nBiome : |");
        for (int side : sides) {
            ret.append("--- ").append(side).append(" ---|");
        }
        return ret.toString();
    }

    /**
     * Gets the x coordinate of the hexagonal tile.
     *
     * @return the x coordinate
     */
    public int getX() {
        return x;
    }

    /**
     * Sets the x coordinate of the hexagonal tile.
     *
     * @param x the x coordinate
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * Gets the y coordinate of the hexagonal tile.
     *
     * @return the y coordinate
     */
    public int getY() {
        return y;
    }

    /**
     * Sets the y coordinate of the hexagonal tile.
     *
     * @param y the y coordinate
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * Sets the biomes bags for the hexagonal tile.
     *
     * @param biomesBags1 the first biomes bag
     * @param biomesBags2 the second biomes bag
     */
    public void setBiomesBags(BiomesBags biomesBags1, BiomesBags biomesBags2) {
        this.biomesBags1 = biomesBags1;
        this.biomesBags2 = biomesBags2;
    }

    /**
     * Gets the biomes of the hexagonal tile.
     *
     * @return an array of biomes
     */
    public int[] getBiomes() {
        return new int[]{biome1, biome2};
    }

    /**
     * Gets the biomes bag for the specified side.
     *
     * @param side the side index
     * @return the biomes bag for the specified side
     * @throws IllegalArgumentException if the side index is invalid
     */
    public BiomesBags getBiomeBag(int side) {
        if (sides[side] == biome1) {
            return biomesBags1;
        } else if (sides[side] == biome2) {
            return biomesBags2;
        }
        throw new IllegalArgumentException("Invalid side " + side);
    }

    /**
     * Sets the biomes bag for the specified side.
     *
     * @param side the side index
     * @param bag the biomes bag to set
     * @throws IllegalArgumentException if the side index is invalid
     */
    public void setBiomeBag(int side, BiomesBags bag) {
        if (sides[side] == biome1) {
            biomesBags1 = bag;
        } else if (sides[side] == biome2) {
            biomesBags2 = bag;
        } else {
            throw new IllegalArgumentException("Invalid side " + side);
        }
    }

    /**
     * Gets the first biomes bag.
     *
     * @return the first biomes bag
     */
    public BiomesBags getBiomeBag1() {
        return biomesBags1;
    }

    /**
     * Sets the first biomes bag.
     *
     * @param bag the biomes bag to set
     */
    public void setBiomeBag1(BiomesBags bag) {
        biomesBags1 = bag;
        biomesBags1.setBiome(biome1);
    }

    /**
     * Gets the second biomes bag.
     *
     * @return the second biomes bag
     */
    public BiomesBags getBiomeBag2() {
        return biomesBags2;
    }

    /**
     * Sets the second biomes bag.
     *
     * @param bag the biomes bag to set
     */
    public void setBiomeBag2(BiomesBags bag) {
        biomesBags2 = bag;
        biomesBags2.setBiome(biome2);
    }
}