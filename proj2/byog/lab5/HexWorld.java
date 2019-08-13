package byog.lab5;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Arrays;
import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 * @author zhilic created on 8/12/2019.
 */
public class HexWorld {
    private static int WIDTH;
    private static int HEIGHT;
    private static int size;

    private static final int SEED = 723;
    private static final Random RANDOM = new Random(SEED);

    /**
     * Initialize a HexWorld with size-n hexagons, consisting of 19 total hexagons.
     * @param n: the size of each hexagon's lines except for the "middle" which is always 2.
     *
     * The width of each hexagon ("middle line") is n+2*(n-1) ==> 3 * n - 2
     * The height of each hexagon is 2 * n;
     * The width of the HexWorld should be able to include 3 "middle" + 2 "top",
     *     ==> 3*(3*n-2)+2*n  ==> 11 * n - 6  ==> plus some margins
     * The height of the HexWorld should be able to include 5 hexagons,
     *     ==> 5*2*n ==> 10 * n ==> plus some margins
     */
    public HexWorld(int n) {
        size = n;
        WIDTH = 11 * n - 2;
        HEIGHT = 10 * n + 2;
    }

    /**
     * @param tiles: the area to draw tiles;
     *        x:     x coordinate of the lower left corner of the hexagon to be drawn
     *        y:     y coordinate of the lower left corner of the hexagon to be drawn
     */
    public void addHexagon(TETile[][] tiles, int x, int y) {
        TETile tile = randomTile();
        /**
         * Draw the bottom half of a hexagon (from bottom to top, from left to right)
         */
        for (int l = 0; l < size; l++) {
            for (int xx = x; xx < x + size + 2 * l; xx++) {
                tiles[xx - l][y + l] = colorfulTile(tile);
            }
        }
        /**
         * Draw the top half of a hexagon (from top to bottom, from left to right)
         */
        for (int l = 0; l < size; l++) {
            for (int xx = x; xx < x + size + 2 * l; xx++) {
                tiles[xx - l][y + 2 * size -1 - l] = colorfulTile(tile);
            }
        }
    }

    /**
     * Get an array of x coordinates of all 19 hexagons' left bottom corners
     * from left to right, bottom to top.
     * The left bottom corner of the bottom hexagon should start at WIDTH / 2 - size / 2
     * The layout of all hexagons are:
     *          18
     *        16  17
     *      13  14  15
     *        11  12
     *      8   9   10
     *        6   7
     *      3   4   5
     *        1   2
     *          0
     * The difference of x coordinates between two adjacent hexagons on the diagonal
     * (i.e. 0 & 1, 1 & 3) is 2 * size - 1;
     */
    private static int[] getXCoordinates() {
        int[] xs = new int[19];
        xs[0] = WIDTH / 2 - size / 2;
        xs[18] = WIDTH / 2 - size / 2;
        System.out.println("xs[18]: " + xs[18]);
        for (int i = 1; i < 18; i += 5) {
            xs[i] = WIDTH / 2 - size / 2 - (2 * size - 1);
        }
        for (int i = 2; i < 18; i += 5) {
            xs[i] = WIDTH / 2 - size / 2 + (2 * size - 1);
        }
        for (int i = 3; i < 18; i += 5) {
            xs[i] = WIDTH / 2 - size / 2 - 2 * (2 * size - 1);
        }
        for (int i = 4; i < 18; i += 5) {
            xs[i] = WIDTH / 2 - size / 2;
        }
        for (int i = 5; i < 18; i += 5) {
            xs[i] = WIDTH / 2 - size / 2 + 2 * (2 * size - 1);
        }
        return xs;
    }

    /**
     * Get an array of x coordinates of all 19 hexagons' left bottom corners
     * from left to right, bottom to top.
     * The left bottom corner of the bottom hexagon should start at 1
     * The difference of y coordinates between to adjacent levels of hexagons is size
     */
    private static int[] getYCoordinates() {
        int[] ys = new int[19];
        ys[0] = 1;
        for (int i = 1; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                ys[i + j * 5] = 1 + size * (2 * j + 1);
            }
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                ys[i + j * 5 - 3] = 1 + size * 2 * j;
            }
        }
        ys[18] = 1 + size * 8;
        return ys;
    }

    private static TETile randomTile() {
        int tileNum = RANDOM.nextInt(6);
        switch (tileNum) {
            case 0: return Tileset.GRASS;
            case 1: return Tileset.WATER;
            case 2: return Tileset.FLOWER;
            case 3: return Tileset.SAND;
            case 4: return Tileset.MOUNTAIN;
            case 5: return Tileset.TREE;
            default: return Tileset.NOTHING;
        }
    }

    private static TETile colorfulTile(TETile tile) {
        return TETile.colorVariant(tile, 50, 50, 50, RANDOM);
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        HexWorld hexWorld = new HexWorld(3); /* To set WIDTH & HEIGHT*/

        ter.initialize(WIDTH, HEIGHT);
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        /**
         * fill in Tileset.NOTHING in every tile of the world
         */
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }

        /**
         * fill in the hexagons
         */
        int[] xs = hexWorld.getXCoordinates();
        int[] ys = hexWorld.getYCoordinates();
        for (int i = 0; i < xs.length; i++) {
            hexWorld.addHexagon(world, xs[i], ys[i]);
        }

        ter.renderFrame(world);


    }
}
