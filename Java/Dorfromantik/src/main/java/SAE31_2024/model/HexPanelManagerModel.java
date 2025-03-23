package SAE31_2024.model;

import java.rmi.AlreadyBoundException;
import java.util.Objects;

/**
 * The HexPanelManagerModel class manages a collection of hexagonal tiles and their placement.
 */
public class HexPanelManagerModel {
    private final HexTileModel[] tiles;
    private final BiomesBags[] bags;
    private int itter;

    /**
     * Constructor for HexPanelManagerModel.
     * Initializes the tiles and bags arrays and sets the iterator to 0.
     */
    public HexPanelManagerModel() {
        this.tiles = new HexTileModel[50];
        this.bags = new BiomesBags[100];
        this.itter = 0;
    }

    /**
     * Adds a tile to the panel at the specified coordinates.
     *
     * @param tile the tile to add
     * @param x the x coordinate
     * @param y the y coordinate
     * @param force whether to force the placement
     * @throws AlreadyBoundException if the tile cannot be placed
     * @throws IndexOutOfBoundsException if the maximum number of tiles is reached
     */
    public void addTile(HexTileModel tile, int x, int y, boolean force) throws AlreadyBoundException, IndexOutOfBoundsException {
        if (this.itter == 50) throw new IndexOutOfBoundsException();
        if (!force && !this.CheckCanBePlaced(x, y)) throw new AlreadyBoundException();

        int[][] tilesNext2 = getTilesNext2(x, y);

        for (HexTileModel checkedTile : this.tiles) {
            if (checkedTile == null) break;

            boolean notIn = true;
            for (int[] coords : tilesNext2) {
                if (coords[0] == checkedTile.getX() && coords[1] == checkedTile.getY()) {
                    notIn = false;
                    break;
                }
            }
            if (notIn) continue;

            int difX = checkedTile.getX() - x;
            int difY = checkedTile.getY() - y;

            if (Math.abs(difX) > 2 || Math.abs(difY) > 3) break;

            boolean right = difX >= 0;
            boolean left = !right;
            boolean top2 = difY == 2, top = difY == 1, bottom = difY == -1, bottom2 = difY == -2;

            if (bottom2 && checkedTile.getSidesBiomes()[4] == tile.getSidesBiomes()[1]) {
                tile.setBiomeBag(1, checkedTile.getBiomeBag(4));
                tile.getBiomeBag(1).add(tile);
            } else if (top2 && checkedTile.getSidesBiomes()[1] == tile.getSidesBiomes()[4]) {
                tile.setBiomeBag(4, checkedTile.getBiomeBag(1));
                tile.getBiomeBag(4).add(tile);
            } else if (left && bottom && checkedTile.getSidesBiomes()[5] == tile.getSidesBiomes()[2]) {
                tile.setBiomeBag(2, checkedTile.getBiomeBag(5));
                tile.getBiomeBag(2).add(tile);
            } else if (left && top && checkedTile.getSidesBiomes()[0] == tile.getSidesBiomes()[3]) {
                tile.setBiomeBag(3, checkedTile.getBiomeBag(0));
                tile.getBiomeBag(3).add(tile);
            } else if (right && bottom && checkedTile.getSidesBiomes()[3] == tile.getSidesBiomes()[0]) {
                tile.setBiomeBag(0, checkedTile.getBiomeBag(3));
                tile.getBiomeBag(0).add(tile);
            } else if (right && top && checkedTile.getSidesBiomes()[2] == tile.getSidesBiomes()[5]) {
                tile.setBiomeBag(5, checkedTile.getBiomeBag(2));
                tile.getBiomeBag(5).add(tile);
            }
        }

        if (tile.getBiomeBag1() == null) {
            tile.setBiomeBag1(new BiomesBags());
            tile.getBiomeBag1().add(tile);
            for (int i = 0; i < this.bags.length; i++) {
                if (this.bags[i] == null) {
                    this.bags[i] = tile.getBiomeBag1();
                    break;
                }
            }
        }

        if (tile.getBiomes()[0] == tile.getBiomes()[1]) {
            tile.setBiomeBag2(tile.getBiomeBag1());
        } else if (tile.getBiomeBag2() == null) {
            tile.setBiomeBag2(new BiomesBags());
            tile.getBiomeBag2().add(tile);
            for (int i = 0; i < this.bags.length; i++) {
                if (this.bags[i] == null) {
                    this.bags[i] = tile.getBiomeBag2();
                    break;
                }
            }
        }

        tile.setX(x);
        tile.setY(y);
        this.tiles[this.itter++] = tile;
    }

    /**
     * Checks if a tile can be placed at the specified coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the tile can be placed, false otherwise
     */
    public boolean CheckCanBePlaced(int x, int y) {
        for (int i = 0; i < itter; i++) {
            if (Objects.requireNonNull(this.tiles[i]).getX() == x && this.tiles[i].getY() == y) return false;
        }

        for (int i = 0; i < this.itter; i++) {
            int[][] TileNext2 = getTilesNext2(this.tiles[i].getX(), this.tiles[i].getY());
            for (int[] coord : TileNext2) {
                if (coord[0] == x && coord[1] == y) return true;
            }
        }
        return false;
    }

    /**
     * Gets the coordinates of the tiles adjacent to the specified coordinates.
     *
     * @param x the x coordinate
     * @param y the y coordinate
     * @return a 2D array of the coordinates of the adjacent tiles
     */
    private static int[][] getTilesNext2(int x, int y) {
        if (y == 0) {
            return new int[][]{{x + 1, y + 2}, {x + 1, y + 1}, {x + 1, y - 1}, {x + 1, y - 2}, {x, y + 1}, {x, y - 1}};
        } else if (y == 1) {
            return new int[][]{{x + 1, y + 2}, {x + 1, y + 1}, {x, 0}, {x, y - 2}, {x - 1, 0}, {x, y + 1}};
        } else if (y == -1) {
            return new int[][]{{x + 1, y - 2}, {x + 1, y - 1}, {x, 0}, {x, y + 2}, {x - 1, 0}, {x, y - 1}};
        } else if (y > 1) {
            return new int[][]{{x + 1, y + 2}, {x + 1, y + 1}, {x, y - 1}, {x - 1, y - 2}, {x - 1, y - 1}, {x, y + 1}};
        } else {
            return new int[][]{{x + 1, y - 2}, {x + 1, y - 1}, {x, y + 1}, {x - 1, y + 2}, {x - 1, y + 1}, {x, y - 1}};
        }
    }

    /**
     * Gets the array of tiles.
     *
     * @return the array of tiles
     */
    public HexTileModel[] getTiles() {
        return tiles;
    }

    /**
     * Gets the total score of all the biomes bags.
     *
     * @return the total score
     */
    public int getScore() {
        int scoreTT = 0;
        for (BiomesBags bag : this.bags) {
            if (bag != null) {
                scoreTT += bag.getBagScore();
            } else {
                break;
            }
        }
        return scoreTT;
    }

    /**
     * Checks if all tiles have been placed.
     *
     * @return true if all tiles have been placed, false otherwise
     */
    public boolean allTilesPlaced() {
        return this.itter == 50;
    }
}