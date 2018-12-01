/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Eight_Puzzle_Game;

import java.awt.Color;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;
import java.util.Stack;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Abd El Rahman
 */
public class AI {
    
    private final Animation GUI = new Animation();
    
    private int nodes_Expanded = 0;
    private int search_Depth = 0;
    private long startTime;
    private long endTime;

    public boolean BFS(Board initialState, Board goalTest) {
        
        startTime = System.currentTimeMillis();
        Board state;
        Queue<Board> frontier = new LinkedList<>();
        Set<Board> explored = new HashSet<>();
        search_Depth = 0;

        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            state = frontier.poll();
            explored.add(state);
            search_Depth = Integer.max(state.getDepth(),search_Depth);

            if (state.equals(goalTest)) {
                endTime = System.currentTimeMillis();
                return success(state);
            }

            for (Board neighbour : state.getNeighbours()) {
                if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                    frontier.add(neighbour);
                }
            }
            nodes_Expanded = Integer.max(frontier.size(),nodes_Expanded);
        }
        return false;
    }
    
    public boolean DFS(Board initialState, Board goalTest){
        return DLS(initialState, goalTest, 32);
    }

    public boolean DLS(Board initialState, Board goalTest, int limit) {
        startTime = System.currentTimeMillis();
        Board state;
        Stack<Board> frontier = new Stack<>();
        Set<Board> explored = new HashSet<>();
        search_Depth = 0;

        frontier.push(initialState);
        while (!frontier.isEmpty()) {
            state = frontier.pop();
            print(state.getBoard());
            explored.add(state);
            search_Depth = Integer.max(state.getDepth(),search_Depth);

            if (state.equals(goalTest)) {
                endTime = System.currentTimeMillis();
                return success(state);
            }

            if(state.getDepth()< limit){
                for (Board neighbour : state.getNeighbours()) {
                    if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                        frontier.push(neighbour);
                    }
                }
            }
            nodes_Expanded = Integer.max(frontier.size(),nodes_Expanded);
        }
        return false;
    }
        
    public boolean aStar(Board initialState, Board goalTest, Boolean heuristic){
        Board.heuristic = heuristic;
        startTime = System.currentTimeMillis();
        Board state;
        PriorityQueue<Board> frontier = new PriorityQueue<>();
        Set<Board> explored = new HashSet<>();
        search_Depth = 0;
        
        frontier.add(initialState);

        while (!frontier.isEmpty()) {
            state = frontier.poll();
            explored.add(state);
            search_Depth = Integer.max(state.getDepth(),search_Depth);
            
            print(state.getBoard());
            
            if (state.equals(goalTest)) {
                endTime = System.currentTimeMillis();
                return success(state);
            }

            for (Board neighbour : state.getNeighbours()) {
                if (!frontier.contains(neighbour) && !explored.contains(neighbour)) {
                    frontier.add(neighbour);
                }else if (frontier.contains(neighbour)){
                    frontier.remove(neighbour);
                    frontier.add(neighbour);
                }
            }
            nodes_Expanded = Integer.max(frontier.size(),nodes_Expanded);
        }
        return failure();
        
    }

    public static void print(int[][] board) {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print((board[i][j] == 0 ? " " : board[i][j]) + " | ");
            }
            System.out.println();
            System.out.println("-------------");
        }
        System.out.println();

    }

    private boolean success(Board state) {
        Board lastState = state;
        Stack<String> moves = new Stack<>();
        Stack<Board> steps = new Stack<>();

        while (state.getParent() != null) {
            moves.add(state.getPreviousMove());
            steps.add(state);
            state = state.getParent();
        }
        steps.add(state);
        
        Collections.reverse((moves));
        System.out.println("Path_To_Goal: " + moves);
        System.out.println("Cost_Of_Path: " + moves.size());
        System.out.println("Nodes_Expanded: " + nodes_Expanded);
        System.out.println("Search_Depth: " + search_Depth);
        System.out.println("Running_Time: " + ((endTime-startTime)/1000.0) + "s");
        
        GUI.setVisible(true);
        GUI.setAlwaysOnTop(true);
        GUI.setLocationRelativeTo(null);
        GUI.getContentPane().setBackground(new Color(128,0,64));
        
        Collections.reverse(steps);
        steps.forEach((step) -> {
            try {
                GUI.update(step.getBoard());
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException ex) {
                Logger.getLogger(AI.class.getName()).log(Level.SEVERE, null, ex);
            }
        });

        
        return true;
    }

    private boolean failure() {
        return false;
    }
}

// 5,6,7,2,5,4,3,0,1