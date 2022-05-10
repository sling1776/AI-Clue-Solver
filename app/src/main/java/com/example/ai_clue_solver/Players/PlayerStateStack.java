package com.example.ai_clue_solver.Players;
import java.util.HashMap;
import java.util.Stack;

public class PlayerStateStack {
    Stack<HashMap<Integer, Player>> stack;

    public PlayerStateStack(HashMap<Integer, Player> firstState){
        stack = new Stack<>();
        stack.add(firstState);
    }

    public int size(){
        return stack.size();
    }
    public void addToStack(HashMap<Integer, Player> state){
        HashMap<Integer, Player> copyState = new HashMap<>();
        
        for(Integer i: state.keySet()){
            Player p = state.get(i);
            if(p!= null){
                Player copyP = p.copyPlayer();
                copyState.put(i, copyP);
            }
        }
        stack.add(copyState);
    }

    public HashMap<Integer, Player> popTop(){
        if(stack.size() == 1){
            return stack.peek();
        }
        return stack.pop();
    }

    public HashMap<Integer, Player> peekTop(){
        return stack.peek();
    }
}
