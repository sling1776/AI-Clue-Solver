package com.example.ai_clue_solver.Tests;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Before;
import org.junit.Test;

import com.example.ai_clue_solver.Cards.*;
import com.example.ai_clue_solver.Rummor.Rummor;
import org.junit.Assert;

public class RummorTest {

    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Person> personCards = new ArrayList<>();
    ArrayList<Place> placeCards = new ArrayList<>();
    ArrayList<Thing> thingCards = new ArrayList<>();

    Random rdm = new Random();

        
    @Before
    public void setup(){
        for(Person.Value v: Person.Value.values()){
            personCards.add(new Person(v));
            cards.add(new Person(v));
        }
        for(Place.Value v: Place.Value.values()){
            placeCards.add(new Place(v));
            cards.add(new Place(v));
        }
        for(Thing.Value v: Thing.Value.values()){
            thingCards.add(new Thing(v));
            cards.add(new Thing(v));
        }
    }

    @Test
    public void rummorTest(){
        Rummor rummor = new Rummor(personCards.get(0), placeCards.get(0), thingCards.get(0));
        
        Assert.assertEquals(personCards.get(0).getValue(),rummor.getPerson().getValue() );
        Assert.assertEquals(placeCards.get(0).getValue(),rummor.getPlace().getValue() );
        Assert.assertEquals(thingCards.get(0).getValue(),rummor.getThing().getValue() );

        Rummor rummor2 = new Rummor(personCards.get(1), placeCards.get(2), thingCards.get(3));
        
        Assert.assertEquals(personCards.get(1).getValue(), rummor2.getPerson().getValue() );
        Assert.assertEquals(placeCards.get(2).getValue(), rummor2.getPlace().getValue() );
        Assert.assertEquals(thingCards.get(3).getValue(), rummor2.getThing().getValue() );
    }
}
