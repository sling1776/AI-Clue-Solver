package com.example.ai_clue_solver.Cards;

import java.util.NoSuchElementException;

public class Person extends Card {
    public enum Value{
        GREEN,
        MUSTARD,
        PEACOCK,
        PLUM,
        SCARLETT,
        WHITE
    }
    
    public Value value;

    public Person(Value value){
        super();
        this.kind = Kind.PERSON;
        this.value = value;
    }

    public Value getValue(){
        return this.value;
    }

    @Override
    public String toString(){
        switch(value){
            case GREEN:
                return "Mr. Green";
            case MUSTARD:
                return "Col. Mustard";
            case PEACOCK:
                return "Mrs. Peacock";
            case PLUM:
                return "Prof. Plum";
            case SCARLETT:
                return "Miss Scarlett";
            case WHITE:
                return "Ms. White";
            default:
                throw new NoSuchElementException("Error: Unknown Type of Person: From Person.toString()");
            
        }
    }
}
