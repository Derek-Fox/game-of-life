package gameoflife;

import java.util.Arrays;
import java.util.Scanner;

/**
 * My personal version of Conway's game of life.
 *
 * @author Derek Fox
 */
public class GameOfLife {

    /**
     * Tests for methods/driver for game
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        int[][] initialBoard = randomState(50, 50);
        int[][] nextState = nextBoardState(initialBoard);
        
        Scanner in = new Scanner(System.in);

        //String s = in.nextLine();
        
        while(true) {
            render(nextState);
//            for (int i = 0; i < initialBoard.length; i++) {
//                System.out.print("-");
//            }
            nextState = nextBoardState(nextState);
            //s = in.nextLine();
        }
    }

    /**
     * Takes an input width and height and returns an empty game board. Throws
     * IAE for negative inputs.
     *
     * @param width desired width of game board
     * @param height desired height of game board
     * @return 2-D int array initialized to all 0s.
     */
    public static int[][] deadState(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("No negative values!");
        }

        int[][] board = new int[width][height];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                board[i][j] = 0;
            }
        }

        return board;
    }

    /**
     * Takes an input width and height and returns a game board with each square
     * initialized to a random value. Throws IAE for negative inputs.
     *
     * @param width desired board width
     * @param height desired board height
     * @return
     */
    public static int[][] randomState(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("No negative values!");
        }

        int[][] board = deadState(width, height);

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (Math.random() < 0.5) {
                    board[i][j] = 1;
                }
            }
        }

        return board;
    }

    /**
     * Takes a board state and formats/prints to standard output.
     *
     * @param board Current board state.
     */
    public static void render(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == 1) {
                    System.out.print("#");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.print("\n");
        }
    }

    /**
     * Takes an input board state and calculates the next state.
     *
     * @param initialState 2-D int array of 0s and 1s that represents the
     * current state of the game board
     * @return 2-D int array of 0s and 1s that represents the next state of the
     * game board
     */
    public static int[][] nextBoardState(int[][] initialState) {
        int width = initialState.length, height = initialState[0].length;

        int[][] nextBoardState = deadState(width, height);

        int amountSurrounding; //need inside the for loops

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (x == 0 || x == width - 1 || y == 0 || y == height - 1) { //check if current square is on the edge - need different logic
                    if (x == 0 && y == 0) { //top left corner
                        amountSurrounding = initialState[x + 1][y] + initialState[x][y + 1] + initialState[x + 1][y + 1];
                        if (initialState[x][y] == 1) { //cell is alive to start
                            if (amountSurrounding <= 1) { //die from underpopulation
                                nextBoardState[x][y] = 0;
                            } else {
                                nextBoardState[x][y] = 1;
                            }
                        } else { //cell is dead to start
                            if (amountSurrounding == 3) { //revive from reproduction
                                nextBoardState[x][y] = 1;
                            }
                        }
                    } else if (x == 0 && y == height - 1) { //bottom left corner
                        amountSurrounding = initialState[x + 1][y] + initialState[x][y - 1] + initialState[x + 1][y - 1];
                        if (initialState[x][y] == 1) { //cell is alive to start
                            if (amountSurrounding <= 1) { //die from underpopulation
                                nextBoardState[x][y] = 0;
                            } else {
                                nextBoardState[x][y] = 1;
                            }
                        } else { //cell is dead to start
                            if (amountSurrounding == 3) { //revive from reproduction
                                nextBoardState[x][y] = 1;
                            }
                        }
                    } else if (x == width - 1 && y == 0) { //top right corner
                        amountSurrounding = initialState[x - 1][y] + initialState[x][y + 1] + initialState[x - 1][y + 1];
                        if (initialState[x][y] == 1) { //cell is alive to start
                            if (amountSurrounding <= 1) { //die from underpopulation
                                nextBoardState[x][y] = 0;
                            } else {
                                nextBoardState[x][y] = 1;
                            }
                        } else { //cell is dead to start
                            if (amountSurrounding == 3) { //revive from reproduction
                                nextBoardState[x][y] = 1;
                            }
                        }
                    } else if (x == width - 1 && y == height - 1) { //bottom right corner
                        amountSurrounding = initialState[x - 1][y] + initialState[x][y - 1] + initialState[x - 1][y - 1];
                        if (initialState[x][y] == 1) { //cell is alive to start
                            if (amountSurrounding <= 1) { //die from underpopulation
                                nextBoardState[x][y] = 0;
                            } else {
                                nextBoardState[x][y] = 1;
                            }
                        } else { //cell is dead to start
                            if (amountSurrounding == 3) { //revive from reproduction
                                nextBoardState[x][y] = 1;
                            }
                        }
                    } else { //on the edge but not the corner
                        if (x == 0) { //left edge
                            amountSurrounding = initialState[x][y-1] + initialState[x][y + 1] + initialState[x + 1][y + 1] + initialState[x+1][y-1] + initialState[x+1][y];
                            if (initialState[x][y] == 1) { //cell is alive to start
                                if (amountSurrounding <= 1) { //die from underpopulation
                                    nextBoardState[x][y] = 0;
                                } else if (amountSurrounding > 3) { //die from overpopulation
                                    nextBoardState[x][y] = 0;
                                } else {
                                nextBoardState[x][y] = 1;
                            }
                            } else { //cell is dead to start
                                if (amountSurrounding == 3) { //revive from reproduction
                                    nextBoardState[x][y] = 1;
                                }
                            }
                        } else if (x == width - 1) { //right edge
                            amountSurrounding = initialState[x][y-1] + initialState[x][y + 1] + initialState[x - 1][y - 1] + initialState[x-1][y+1] + initialState[x-1][y];
                            if (initialState[x][y] == 1) { //cell is alive to start
                                if (amountSurrounding <= 1) { //die from underpopulation
                                    nextBoardState[x][y] = 0;
                                } else if (amountSurrounding > 3) { //die from overpopulation
                                    nextBoardState[x][y] = 0;
                                } else {
                                nextBoardState[x][y] = 1;
                            }
                            } else { //cell is dead to start
                                if (amountSurrounding == 3) { //revive from reproduction
                                    nextBoardState[x][y] = 1;
                                }
                            }
                        } else if (y == 0) { //top edge
                            amountSurrounding = initialState[x-1][y] + initialState[x+1][y] + initialState[x + 1][y + 1] + initialState[x-1][y+1] + initialState[x][y+1];
                            if (initialState[x][y] == 1) { //cell is alive to start
                                if (amountSurrounding <= 1) { //die from underpopulation
                                    nextBoardState[x][y] = 0;
                                } else if (amountSurrounding > 3) { //die from overpopulation
                                    nextBoardState[x][y] = 0;
                                } else {
                                nextBoardState[x][y] = 1;
                            }
                            } else { //cell is dead to start
                                if (amountSurrounding == 3) { //revive from reproduction
                                    nextBoardState[x][y] = 1;
                                }
                            }
                        } else if (y == height - 1) { //bottom edge
                            amountSurrounding = initialState[x][y-1] + initialState[x-1][y] + initialState[x+1][y] + initialState[x-1][y-1] + initialState[x+1][y-1];
                            if (initialState[x][y] == 1) { //cell is alive to start
                                if (amountSurrounding <= 1) { //die from underpopulation
                                    nextBoardState[x][y] = 0;
                                } else if (amountSurrounding > 3) { //die from overpopulation
                                    nextBoardState[x][y] = 0;
                                } else {
                                nextBoardState[x][y] = 1;
                            }
                            } else { //cell is dead to start
                                if (amountSurrounding == 3) { //revive from reproduction
                                    nextBoardState[x][y] = 1;
                                }
                            }
                        }
                    }
                } else { //not an edge square
                    amountSurrounding = initialState[x-1][y-1] + initialState[x][y-1] + initialState[x+1][y-1] + initialState[x+1][y] + initialState[x+1][y+1] + initialState[x][y+1] + initialState[x-1][y+1] + initialState[x-1][y];
                            if (initialState[x][y] == 1) { //cell is alive to start
                                if (amountSurrounding <= 1) { //die from underpopulation
                                    nextBoardState[x][y] = 0;
                                } else if (amountSurrounding > 3) { //die from overpopulation
                                    nextBoardState[x][y] = 0;
                                } else {
                                nextBoardState[x][y] = 1;
                            }
                            } else { //cell is dead to start
                                if (amountSurrounding == 3) { //revive from reproduction
                                    nextBoardState[x][y] = 1;
                                }
                            }
                }
            }
        }
        
        return nextBoardState;
    }

}
