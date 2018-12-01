/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eight_Puzzle_Game;

import java.awt.Point;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Abd El Rahman
 */
public class Board implements Comparable {
    
    
    public static final Boolean MANHATTAN = true;
    public static final Boolean EUCLIDIAN = false;
    public static Boolean heuristic = MANHATTAN;

    private static final int[][] GOAL = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}}; // ALIAA's IDEA

    private int depth = 0;
    private int cost;

    private int[][] board = new int[3][3];
    private Point zeroIndex = new Point();
    private String previousMove = "";
    private Board parent = null;
    private Board up = null;
    private Board down = null;
    private Board left = null;
    private Board right = null;

    public Board(int[][] board) {
        //this.board = board;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                this.board[i][j] = board[i][j];
            }
        }

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    zeroIndex.x = i;
                    zeroIndex.y = j;
                    break;
                }
            }
        }
        this.depth = 0;
    }

    public LinkedList<Board> getNeighbours() {
        LinkedList<Board> neighbours = new LinkedList<>();

        if (setUp()) {
            neighbours.add(up);
        }
        if (setDown()) {
            neighbours.add(down);
        }
        if (setLeft()) {
            neighbours.add(left);
        }
        if (setRight()) {
            neighbours.add(right);
        }

        return neighbours;

    }

    private boolean setUp() {
        if (this.zeroIndex.x != 0 && !previousMove.equals("DOWN")) {
            up = new Board(this.board);
            up.board[zeroIndex.x][zeroIndex.y] = up.board[zeroIndex.x - 1][zeroIndex.y];
            up.board[zeroIndex.x - 1][zeroIndex.y] = 0;
            up.zeroIndex.x--;
            up.previousMove = "UP";
            up.parent = this;

            up.depth = this.depth + 1;
            up.setCost();

            return true;
        }
        return false;
    }

    private boolean setDown() {
        if (this.zeroIndex.x != 2 && !previousMove.equals("UP")) {
            down = new Board(this.board);
            down.board[zeroIndex.x][zeroIndex.y] = down.board[zeroIndex.x + 1][zeroIndex.y];
            down.board[zeroIndex.x + 1][zeroIndex.y] = 0;
            down.zeroIndex.x++;
            down.previousMove = "DOWN";
            down.parent = this;

            down.depth = this.depth + 1;
            down.setCost();

            return true;
        }
        return false;
    }

    private boolean setRight() {
        if (this.zeroIndex.y != 2 && !previousMove.equals("LEFT")) {
            right = new Board(this.board);
            right.board[zeroIndex.x][zeroIndex.y] = right.board[zeroIndex.x][zeroIndex.y + 1];
            right.board[zeroIndex.x][zeroIndex.y + 1] = 0;
            right.zeroIndex.y++;
            right.previousMove = "RIGHT";
            right.parent = this;

            right.depth = this.depth + 1;
            right.setCost();

            return true;
        }
        return false;
    }

    private boolean setLeft() {
        if (this.zeroIndex.y != 0 && !previousMove.equals("RIGHT")) {
            left = new Board(this.board);
            left.board[zeroIndex.x][zeroIndex.y] = left.board[zeroIndex.x][zeroIndex.y - 1];
            left.board[zeroIndex.x][zeroIndex.y - 1] = 0;
            left.zeroIndex.y--;
            left.previousMove = "LEFT";
            left.parent = this;

            left.depth = this.depth + 1;
            left.setCost();

            return true;
        }
        return false;
    }

    public Board getUp() {
        return up;
    }

    public Board getDown() {
        return down;
    }

    public Board getLeft() {
        return left;
    }

    public Board getRight() {
        return right;
    }

    public String getPreviousMove() {
        return previousMove;
    }

    public Board getParent() {
        return parent;
    }

    public int[][] getBoard() {
        return board;
    }

    public int getDepth() {
        return depth;
    }

    public int getCost() {
        return cost;
    }

    public void setCost() {
        if (heuristic) {
            this.cost = this.depth + this.getManhatanDistance();
        } else {
            this.cost = this.depth + this.getEuclidianDistance();
        }
    }

    public int getManhatanDistance() {

        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != i * 3 + j) {
                    distance += Math.abs(i - this.board[i][j] / 3) + Math.abs(j - this.board[i][j] % 3);
                }
            }
        }
        return distance;
    }

    public int getEuclidianDistance() {
        int distance = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] != i * 3 + j) {
                    distance += Math.sqrt(Math.pow(i - this.board[i][j] / 3, 2) + Math.pow(j - this.board[i][j] % 3, 2));
                }
            }
        }
        return distance;
    }

    @Override
    public boolean equals(Object me) {
        if (me.getClass() != Board.class) {
            return false;
        }

        return Arrays.deepEquals(((Board) me).board, this.board);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.deepHashCode(this.board);
        return hash;
    }

    @Override
    public int compareTo(Object o) {
        if (this.cost == ((Board) o).cost) {
            return 0;
        } else if (this.cost > ((Board) o).cost) {
            return 1;
        } else {
            return -1;
        }
    }

}
