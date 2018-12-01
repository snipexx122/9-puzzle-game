/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eight_Puzzle_Game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import javax.swing.JLabel;

/**
 *
 * @author Abd El Rahman
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /* Animation x = new Animation();
        x.draw();*/

        Scanner s = new Scanner(System.in);
        String[] input = s.nextLine().split(",");
        
        int[][] table = {{Integer.parseInt(input[0]), Integer.parseInt(input[1]), Integer.parseInt(input[2])},
                         {Integer.parseInt(input[3]), Integer.parseInt(input[4]), Integer.parseInt(input[5])}, 
                         {Integer.parseInt(input[6]), Integer.parseInt(input[7]), Integer.parseInt(input[8])}};
        
        int[][] goal = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}};
        Board initialState = new Board(table);
        Board goalState = new Board(goal);
        AI solver = new AI();
        
        boolean success = solver.aStar(initialState, goalState, Board.MANHATTAN);
        if (!success)
            System.out.println("Failed To Reach Goal");
        

    }

    public static void print(int[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print((board[i][j] == 0 ? " " : board[i][j]) + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }

    }

}
