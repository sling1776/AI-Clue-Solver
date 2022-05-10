package com.example.ai_clue_solver.Cards;
public class Card {

    public enum Kind{
        PERSON,
        PLACE,
        THING
    }

    public Kind kind;
    public Card(){

    }

    public Kind getKind(){
        return this.kind;
    }

    
}
