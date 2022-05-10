package com.example.ai_clue_solver.Tests;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.example.ai_clue_solver.Cards.*;
import com.example.ai_clue_solver.Players.*;
import com.example.ai_clue_solver.Players.Player.Knowledge;
import com.example.ai_clue_solver.Rummor.Rummor;

public class EnvelopeTest {
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
    public void envelopeNameTest(){
        Envelope e = new Envelope();
        Assert.assertEquals("Envelope", e.getName());
    }

    @Test
    public void envelopeIDTest(){
        Envelope e = new Envelope();
        Assert.assertEquals(-1, e.getID());
    }

    @Test
    public void envelopeNumberOfCardsTest(){
        Envelope e = new Envelope();
        Assert.assertEquals(3, e.getNumberOfCards());
    }

    @Test
    public void envelopeSetCardHas(){
        Envelope e = new Envelope();

        int chosennum = rdm.nextInt(personCards.size());
        e.setCardHas(personCards.get(chosennum).getValue());
        for(int i = 0 ; i < personCards.size(); i++ ){
            if(i == chosennum){
                Assert.assertEquals(Knowledge.HAS, e.getCardStatus(personCards.get(i)));
            }
            else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(personCards.get(i)));
            }
        }
        //Overwrite answer...
        chosennum = rdm.nextInt(personCards.size());
        e.setCardHas(personCards.get(chosennum).getValue());
        for(int i = 0 ; i < personCards.size(); i++ ){
            if(i == chosennum){
                Assert.assertEquals(Knowledge.HAS, e.getCardStatus(personCards.get(i)));
            }
            else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(personCards.get(i)));
            }
        }

        chosennum = rdm.nextInt(placeCards.size());
        e.setCardHas(placeCards.get(chosennum).getValue());
        for(int i = 0 ; i < placeCards.size(); i++ ){
            if(i == chosennum){
                Assert.assertEquals(Knowledge.HAS, e.getCardStatus(placeCards.get(i)));
            }
            else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(placeCards.get(i)));
            }
        }

        chosennum = rdm.nextInt(thingCards.size());
        e.setCardHas(thingCards.get(chosennum).getValue());
        for(int i = 0 ; i > thingCards.size(); i++ ){
            if(i == chosennum){
                Assert.assertEquals(Knowledge.HAS, e.getCardStatus(thingCards.get(i)));
            }
            else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(thingCards.get(i)));
            }
        }
    }


    @Test
    public void envelopeFindSolution(){
        Envelope e = new Envelope();
        for(int i = 1 ; i < personCards.size(); i++ ){
            e.setCardHasNot(personCards.get(i));
        }
        for(int i = 0 ; i < personCards.size(); i++ ){
            if(i == 0){
                Assert.assertEquals( Knowledge.DONTKNOW, e.getCardStatus(personCards.get(i)));
            }else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(personCards.get(i)));
            }
            
        }

        for(int i = 1 ; i < placeCards.size(); i++ ){
            e.setCardHasNot(placeCards.get(i));
        }

        for(int i = 1 ; i < placeCards.size(); i++ ){
            if(i == 0){
                Assert.assertEquals( Knowledge.DONTKNOW, e.getCardStatus(placeCards.get(i)));
            }else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(placeCards.get(i)));
            }
        }

        for(int i = 2 ; i < thingCards.size(); i++ ){
            e.setCardHasNot(thingCards.get(i));
        }
        Assert.assertNull(e.getPerson());
        Assert.assertNull(e.getPlace());
        Assert.assertNull(e.getThing());

        Assert.assertFalse(e.foundSoultion());
        Assert.assertNotNull(e.getPerson());
        Assert.assertNotNull(e.getPlace());
        Assert.assertNull(e.getThing());
        
        e.setCardHasNot(thingCards.get(1));

        for(int i = 1 ; i < thingCards.size(); i++ ){
            if(i == 0){
                Assert.assertEquals( Knowledge.HAS, e.getCardStatus(thingCards.get(i)));
            }else{
                Assert.assertEquals(Knowledge.HASNOT, e.getCardStatus(thingCards.get(i)));
            }
        }

        Assert.assertTrue(e.foundSoultion());
        
        Assert.assertNotNull(e.getPerson());
        Assert.assertNotNull(e.getPlace());
        Assert.assertNotNull(e.getThing());

        
        Rummor r = e.getSolution();
        Assert.assertEquals(r.getPerson().getValue(), personCards.get(0).getValue());
        Assert.assertEquals(r.getPlace().getValue(), placeCards.get(0).getValue());
        Assert.assertEquals(r.getThing().getValue(), thingCards.get(0).getValue());
    }

    @Test
    public void envelopeGetAnswers(){
        Envelope e = new Envelope();

        int chosennum = rdm.nextInt(personCards.size());
        e.setCardHas(personCards.get(chosennum).getValue());

        Assert.assertFalse(e.foundSoultion());
        Assert.assertEquals(personCards.get(chosennum).getValue(), e.getPerson().getValue());
        Assert.assertNull(e.getPlace());
        Assert.assertNull(e.getThing());

        int chosennum1 = rdm.nextInt(placeCards.size());
        e.setCardHas(placeCards.get(chosennum1).getValue());
        Assert.assertFalse(e.foundSoultion());
        Assert.assertEquals(placeCards.get(chosennum1).getValue(), e.getPlace().getValue());
        Assert.assertNull(e.getThing());


        int chosennum2 = rdm.nextInt(thingCards.size());
        e.setCardHas(thingCards.get(chosennum2).getValue());
        Assert.assertTrue(e.foundSoultion());
        Assert.assertEquals(thingCards.get(chosennum2).getValue(), e.getThing().getValue());
        Assert.assertEquals(placeCards.get(chosennum1).getValue(), e.getPlace().getValue());
        Assert.assertEquals(personCards.get(chosennum).getValue(), e.getPerson().getValue());
    }
}
