/**
 * @author zhilic created on 8/13/19
 */

package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;


public class WorldGenerator {

    private int WIDTH;
    private static int HEIGHT;
    private static long SEED;
    private static Random RANDOM;

    private class Position {
        private int x;
        private int y;

        private Position(int xPos, int yPos) {
            x = xPos;
            y = yPos;
        }

        @Override
        public String toString() {
            return "(" + x + ", " + y + ")";
        }
    }

    private class Rectangle implements Comparable {
        private Position bottomLeft;
        private int height;
        private int width;

        private Rectangle(Position p, int h, int w) {
            bottomLeft = p;
            height = h;
            width = w;
        }

        /** Get the sqaure distance of a rectangle's bottom left corner to the Position(0,0) */
        private int squareDistToZero() {
            return bottomLeft.x * bottomLeft.x + bottomLeft.y * bottomLeft.y;
        }

        @Override
        public int compareTo(Object o) {
            Rectangle that = (Rectangle) o;
            if (this.squareDistToZero() < that.squareDistToZero()) {
                return -1;
            } else if (this.squareDistToZero() == that.squareDistToZero()) {
                if (this.bottomLeft.x < that.bottomLeft.x) {
                    return -1;
                } else {
                    return 1;
                }
            } else {
                return 1;
            }
        }

        private TETile[][] drawRectangle(TETile[][] world) {
            for (int xx = bottomLeft.x; xx < bottomLeft.x + width; xx++) {
                for (int yy = bottomLeft.y; yy < bottomLeft.y + height; yy++) {
                    world[xx][yy] = Tileset.FLOOR;
                }
            }
            return world;
        }

        /** Get the Positions on a rectangle's borders without four corners */
        private ArrayList<Position> getBordersWoConers() {
            ArrayList<Position> borders = new ArrayList<>();
            for (int xx = bottomLeft.x; xx < bottomLeft.x + width; xx++) {
                borders.add(new Position(xx, bottomLeft.y - 1));
                borders.add(new Position(xx, bottomLeft.y + height));
            }
            for (int yy = bottomLeft.y; yy < bottomLeft.y + height; yy++) {
                borders.add(new Position(bottomLeft.x - 1, yy));
                borders.add(new Position(bottomLeft.x + width, yy));
            }
            return borders;
        }

        /** Get the Positions on a rectangle's borders with four corners */
        private ArrayList<Position> getBorders() {
            ArrayList<Position> borders = getBordersWoConers();
            borders.add(new Position(bottomLeft.x - 1, bottomLeft.y - 1));
            borders.add(new Position(bottomLeft.x - 1, bottomLeft.y + height));
            borders.add(new Position(bottomLeft.x + width, bottomLeft.y - 1));
            borders.add(new Position(bottomLeft.x + width, bottomLeft.y + height));
            return borders;
        }

        /** Check if a rectangle has overlap with an array of existing rectangles */
        private boolean overlap(ArrayList<Rectangle> rects) {
            for (Rectangle rect : rects) {
                if (rect != null) {
                    int maxLeft = Math.max(rect.bottomLeft.x, this.bottomLeft.x);
                    int minRight = Math.min(rect.bottomLeft.x + rect.width,
                                            this.bottomLeft.x + this.width);
                    int maxBottom = Math.max(rect.bottomLeft.y, this.bottomLeft.y);
                    int minTop = Math.min(rect.bottomLeft.y + rect.height,
                                          this.bottomLeft.y + this.height);
                    if (maxLeft < minRight && maxBottom < minTop) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public String toString() {
            return "Rectangle left bottom corner @ " + bottomLeft.toString()
                    + "\nRectangle height: " + height + "; width: " + width + "\n";
        }
    }

    public WorldGenerator(int w, int h, long seed) {
        WIDTH = w;
        HEIGHT = h;
        SEED = seed;
        RANDOM = new Random(SEED);
    }

    public Rectangle generateRandomRect() {
        /**
         * Randomize a Position as the left bottom corner of a rectangle.
         * The floor area can't be the border of the world map (leave margin for walls).
         */
        Position p = new Position(RandomUtils.uniform(RANDOM, 1, WIDTH - 4),
                RandomUtils.uniform(RANDOM, 1, HEIGHT - 4));
        /**
         * Randomly generate integers for rectangle height and width.
         * Limit the number to the minimum of border limitation & size limitation.
         */
        int height = RandomUtils.uniform(RANDOM, 3,
                Math.max(4, Math.min(HEIGHT - p.y - 1, HEIGHT / 3)));
        int width = RandomUtils.uniform(RANDOM, 3,
                Math.max(4, Math.min(WIDTH - p.x - 1, WIDTH / 7)));
        Rectangle rect = new Rectangle(p, height, width);
        return rect;
    }

    public Rectangle generateHorizontalHallway(Position a, Position b) {
        /**
         * Add a little extra width to the connecting horizontal hallway,
         * a random number in [0, 5), but it's also subject to border limitation
         * ==> can't be wider than (WIDTH - 1- min(a.x, b.x))
         * ==> also can't be wider than max(a.x, b.x)
         */
        int width = RandomUtils.uniform(RANDOM, Math.abs(a.x - b.x) + 1,
                Math.min(Math.abs(a.x - b.x) + 1 + 5,
                         Math.min(WIDTH - Math.min(a.x, b.x), Math.max(a.x, b.x) + 1)));
        double n = RandomUtils.uniform(RANDOM);
        if (a.x > b.x) {
            if (n > 0.5) {
                /** the horizontal hallway is connected to Position a */
                int leftStart = a.x - (width - 1);
                return new Rectangle(new Position(leftStart, a.y), 1, width);
            } else {
                /** the horizontal hallway is connected to Position b (leftStart = b.x) */
                return new Rectangle(b, 1, width);
            }
        } else {
            if (n > 0.5) {
                /** the horizontal hallway is connected to Position a (leftStart = a.x) */
                return new Rectangle(a, 1, width);
            } else {
                /** the horizontal hallway is connected to Position b */
                int leftStart = b.x - (width - 1);
                return new Rectangle(new Position(leftStart, b.y), 1, width);
            }
        }
    }

    public ArrayList<Rectangle> generateHallways(Position a, Position b) {
        ArrayList<Rectangle> hallways = new ArrayList<>();
        /**
         * Similarly, add a little extra height to the connecting vertical hallway,
         * a random number in [0, 5), but it's also subject to border limitation
         * ==> can't be higher than (HEIGHT - min(a.y, b.y))
         * ==> also can't be higher than max(a.y, b.y)
         */
        int height = RandomUtils.uniform(RANDOM, Math.abs(a.y - b.y) + 1,
                Math.min(Math.abs(a.y - b.y) + 1 + 5,
                         Math.min(HEIGHT - Math.min(a.y, b.y), Math.max(a.y, b.y) + 1)));

        /** Decide horizontal hallway first to make sure two hallways intersect */
        Rectangle horizontal = generateHorizontalHallway(a, b);
        hallways.add(horizontal);
        if (horizontal.bottomLeft.y == a.y) {
            /**
             * The horizontal hallway is connected to Position a,
             * so the vertical hallway must be connect to Position b.
             */
            if (b.y > a.y) {
                int bottomStart = b.y - (height - 1);
                hallways.add(new Rectangle(new Position(b.x, bottomStart), height, 1));
            } else {
                hallways.add(new Rectangle(b, height, 1));
            }
        } else {
            /**
             * The horizontal hallway is connected to Position b,
             * so the vertical hallway must be connect to Position a.
             */
            if (b.y > a.y) {
                hallways.add(new Rectangle(a, height, 1));
            } else {
                int bottomStart = a.y - (height - 1);
                hallways.add(new Rectangle(new Position(a.x, bottomStart), height, 1));
            }
        }
        return hallways;
    }

    public Position[] connectRectangles(Rectangle a, Rectangle b) {
        ArrayList<Position> aBorders = a.getBordersWoConers();
        ArrayList<Position> bBorders = b.getBordersWoConers();
        /**
         * Get two random Positions on each rectangle's border without corners.
         * The two Positions can't lie at the border of the world map.
         * */
        Position[] connects = {new Position(0, 0), new Position(0, 0)};
        while (connects[0].x == 0 || connects[0].x == WIDTH - 1
            || connects[0].y == 0 || connects[0].y == HEIGHT - 1) {
            connects[0] = aBorders.get(RandomUtils.uniform(RANDOM, aBorders.size()));
        }
        while (connects[1].x == 0 || connects[1].x == WIDTH - 1
                || connects[1].y == 0 || connects[1].y == HEIGHT - 1) {
            connects[1] = bBorders.get(RandomUtils.uniform(RANDOM, bBorders.size()));
        }
//        System.out.println(connects[0] + "    " + connects[1]);
        return connects;
    }

    public TETile[][] initializeWorld() {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    /** Randomly select a tile on the borders of rectangles for locked door. */
    public Position generateDoor(TETile[][] world, ArrayList<Rectangle> rects) {
        Position door = new Position(0, 0);
        ArrayList<Position> openSpace = getSurrounding(world, door, Tileset.NOTHING);
        while (world[door.x][door.y] != Tileset.WALL || openSpace.size() == 0) {
            Rectangle rectDoor = rects.get(RandomUtils.uniform(RANDOM, rects.size()));
            ArrayList<Position> bordersDoor = rectDoor.getBordersWoConers();
            door = bordersDoor.get(RandomUtils.uniform(RANDOM, bordersDoor.size()));
            openSpace = getSurrounding(world, door, Tileset.NOTHING);
//            System.out.println("door: " + door);
        }
        return door;
    }

    /** Randomly select a FLOOR tile as the starting point of player. */
    public Position generatePlayer(TETile[][] world, ArrayList<Rectangle> rects) {
        Position player = new Position(0, 0);
        while (world[player.x][player.y] != Tileset.FLOOR) {
            player = new Position(RandomUtils.uniform(RANDOM, WIDTH),
                                  RandomUtils.uniform(RANDOM, HEIGHT));
        }
        return player;
    }

    /** Recolor walls (optional) */
    public TETile[][] recolorWalls(TETile[][] world, ArrayList<Rectangle> hallways) {
        for (Rectangle rect : hallways) {
            ArrayList<Position> borders = rect.getBorders();
            for (Position p : borders) {
                if (world[p.x][p.y] == Tileset.WALL) {
                    world[p.x][p.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, RANDOM);
                }
            }
        }
        return world;
    }


    /**
     * 1. Generate a random integer n ==> Number of rectangles in this world.
     * 2. Generate n random rectangles with random Positions and random width and height,
     *    and draw rectangles with FLOOR (need to watch out for boundaries)
     * 3. Sort the n rectangles based on the distance of their bottom left corners to zero.
     * 4. Connect adjacent rectangles with hallways.
     * 5. Draw all the hallways with FLOOR.
     * 6. Collect all borders of all rectangles and hallways
     *    (special rectangels with width=1 or heigh=1).
     * 7. If the border tile is filled with NOTHING, fill it with WALL.
     */
    public TETile[][] drawWorld() {
        TETile[][] world = initializeWorld();
        /** Decide the random number of rectangles */
        int n = RandomUtils.uniform(RANDOM, 12, 17);

        /**
         * Keep randomly generating rectangles until the array rects is fulfilled.
         * Check if a newly generated rectangle has overlap with existing rectangles.
         * If no overlap, add the generated rectangle to the array.
         */
        ArrayList<Rectangle> rects = new ArrayList<Rectangle>();
        while (rects.size() < n) {
            Rectangle rect = generateRandomRect();
            boolean overlap = rect.overlap(rects);
            if (!overlap) {
                rects.add(rect);
            }
        }
//        System.out.println(rects);

        /**
         * Sort all rectangles based on its distance to the Position(0,0) so that the two connected
         * rectangles are not too far away. Randomly generate hallways to connect rectangles.
         * The for-loop ensures that each rectangle is connected to at least one rectangle.
         */
        Collections.sort(rects);
        ArrayList<Rectangle> hallways = new ArrayList<>();
        for (int ii = 0; ii < rects.size() - 1; ii++) {
            Position[] connects = connectRectangles(rects.get(ii), rects.get(ii + 1));
            ArrayList<Rectangle> h = generateHallways(connects[0], connects[1]);
            hallways.addAll(h);
        }

        hallways.addAll(rects);
        /** fill in FLOORs in all rectangles and hallways and WALLs on all borders. */
        for (Rectangle rect : hallways) {
            world = rect.drawRectangle(world);
            ArrayList<Position> borders = rect.getBorders();
            for (Position p : borders) {
                if (world[p.x][p.y] == Tileset.NOTHING) {
                    /** has to fill in the original WALL to easily generate a random door later */
//                    world[p.x][p.y] = TETile.colorVariant(Tileset.WALL, 30, 30, 30, RANDOM);
                    world[p.x][p.y] = Tileset.WALL;
                }
            }
        }

        Position door = generateDoor(world, rects);
        world[door.x][door.y] = Tileset.LOCKED_DOOR;
        Position player = generatePlayer(world, hallways); // ArrayList hallways include both rooms and hallways
        world[player.x][player.y] = Tileset.PLAYER;
        world = recolorWalls(world, hallways);

        return world;
    }

    /** Get the 4 surrounding tiles of a tile if the surrounding tile is a specific tile. */
    public ArrayList<Position> getSurrounding(TETile[][] world, Position p, TETile tile) {
        ArrayList<Position> surrounding = new ArrayList<>();
        if (p.x > 0 && world[p.x - 1][p.y] == tile) {
            surrounding.add(new Position(p.x - 1, p.y));
        }
        if (p.x < WIDTH - 1 && world[p.x + 1][p.y] == tile) {
            surrounding.add(new Position(p.x + 1, p.y));
        }
        if (p.y > 0 && world[p.x][p.y - 1] == tile) {
            surrounding.add(new Position(p.x, p.y - 1));
        }
        if (p.y < HEIGHT - 1 && world[p.x][p.y + 1] == tile) {
            surrounding.add(new Position(p.x, p.y + 1));
        }
        return surrounding;
    }


    public static void main(String[] args) {
        WorldGenerator generator = new WorldGenerator(80, 30, 2000);
        TERenderer ter = new TERenderer();
        /** Adjusted TERenderer.initialize method to leave some margins around my map. */
        ter.initialize(generator.WIDTH, generator.HEIGHT);
        TETile[][] world = generator.drawWorld();
        ter.renderFrame(world);
    }

}
