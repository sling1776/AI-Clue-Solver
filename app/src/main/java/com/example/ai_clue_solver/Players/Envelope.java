package com.example.ai_clue_solver.Players;

import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Random;

import com.example.ai_clue_solver.Cards.*;
import com.example.ai_clue_solver.Rummor.Rummor;

public class Envelope extends Player {
    
    private Person person = null;
    private Place place = null;
    private Thing thing = null;
    
    public Envelope(){
        super("Envelope", -1, 3);
    }

    @Override
    public void setCardHas(Person.Value v){
        personCardTable.put(v, Knowledge.HAS);
        person = new Person(v);
        for(Person.Value val: Person.Value.values()){
            if(val != v){
                setCardHasNot(val);
            }
        }
    }

    @Override
    public void setCardHas(Place.Value v){
        placeCardTable.put(v, Knowledge.HAS);
        place = new Place(v);
        for(Place.Value val: Place.Value.values()){
            if(val != v){
                setCardHasNot(val);
            }
        }
    }

    @Override
    public void setCardHas(Thing.Value v){
        thingCardTable.put(v, Knowledge.HAS);
        thing = new Thing(v);
        for(Thing.Value val: Thing.Value.values()){
            if(val != v){
                setCardHasNot(val);
            }
        }
    }

    @Override
    public void setCardHas(Card c){
        switch(c.getKind()){
            case PERSON:
                setCardHas(((Person)c).getValue());
                break;
            case PLACE:
                setCardHas(((Place)c).getValue());
                break;
            case THING:
                setCardHas(((Thing)c).getValue());
                break;
            default:
                throw new NoSuchElementException("Error: Unknown Type of Card: From PlayerObject");
        }
    }

    public boolean foundSoultion(){
        checkListsForSolution();
        return person != null && place != null && thing != null;
    }

    public Rummor getSolution(){
        return new Rummor(person, place, thing);
    }

    public Person getPerson(){
        return person;
    }

    public Place getPlace(){
        return place;
    }

    public Thing getThing(){
        return thing;
    }

    private void checkListsForSolution(){
        if(person == null){
            int fullCount = 0;
            int hasNotCount = 0;
            for (Person.Value v : Person.Value.values()) {
                if(getCardStatus(v) == Knowledge.HASNOT){
                    hasNotCount++;
                }
                fullCount ++;
            }
            if((hasNotCount + 1) == fullCount){
                for (Person.Value v : Person.Value.values()) {
                    if(getCardStatus(v) == Knowledge.DONTKNOW){
                        setCardHas(v);
                        break;
                    }else if(getCardStatus(v) == Knowledge.HAS){
                        setCardHas(v);
                        break;
                    }
                }
            }
        }

        if(place == null){
            int fullCount = 0;
            int hasNotCount = 0;
            for (Place.Value v : Place.Value.values()) {
                if(getCardStatus(v) == Knowledge.HASNOT){
                    hasNotCount++;
                }
                fullCount ++;
            }
            if((hasNotCount + 1) == fullCount){
                for (Place.Value v : Place.Value.values()) {
                    if(getCardStatus(v) == Knowledge.DONTKNOW){
                        setCardHas(v);
                        break;
                    }else if(getCardStatus(v) == Knowledge.HAS){
                        setCardHas(v);
                        break;
                    }
                }
            }
        }

        if(thing == null){
            int fullCount = 0;
            int hasNotCount = 0;
            for (Thing.Value v : Thing.Value.values()) {
                if(getCardStatus(v) == Knowledge.HASNOT){
                    hasNotCount++;
                }
                fullCount ++;
            }
            if((hasNotCount + 1) == fullCount){
                for (Thing.Value v : Thing.Value.values()) {
                    if(getCardStatus(v) == Knowledge.DONTKNOW){
                        setCardHas(v);
                        break;
                    }else if(getCardStatus(v) == Knowledge.HAS){
                        setCardHas(v);
                        break;
                    }
                }
            }
        }
    }

    public Rummor makeSuggestion(){
        ArrayList<Person> availablePersons = new ArrayList<>();
        ArrayList<Place> availablePlaces = new ArrayList<>();
        ArrayList<Thing> availableThings = new ArrayList<>();

        if(person == null){
            for (Person.Value v : Person.Value.values()) {
                if(getCardStatus(v) == Knowledge.DONTKNOW){
                    availablePersons.add(new Person(v));
                }
            }
        }
        else {
            availablePersons.add(person);
        }


        if(place == null){
            for (Place.Value v : Place.Value.values()) {
                if(getCardStatus(v) == Knowledge.DONTKNOW){
                    availablePlaces.add(new Place(v));
                }
            }
        }
        else{
            availablePlaces.add(place);
        }

        if(thing == null){
            for (Thing.Value v : Thing.Value.values()) {
                if(getCardStatus(v) == Knowledge.DONTKNOW){
                    availableThings.add(new Thing(v));
                }
            }
        }
        else{
            availableThings.add(thing);
        }


        Random rdm = new Random();

        Person chosenPerson = availablePersons.get(rdm.nextInt(availablePersons.size()));
        Place chosenPlace = availablePlaces.get(rdm.nextInt(availablePlaces.size()));
        Thing chosenThing = availableThings.get(rdm.nextInt(availableThings.size()));

        return new Rummor(chosenPerson, chosenPlace, chosenThing);
    }

    @Override
    public Envelope copyPlayer(){
        Envelope p = new Envelope();
        p.numberOfHas = this.numberOfHas;
        p.numberOfDontKnow = this.numberOfDontKnow;

        for(Person.Value k: personCardTable.keySet()){
            Knowledge x = this.personCardTable.get(k);
            p.personCardTable.put(k, x);
        }
        for(Place.Value k: placeCardTable.keySet()){
            Knowledge x = this.placeCardTable.get(k);
            p.placeCardTable.put(k, x);
        }
        for(Thing.Value k: thingCardTable.keySet()){
            Knowledge x = this.thingCardTable.get(k);
            p.thingCardTable.put(k, x);
        }

        for(Rummor rummor: this.getRummors()){
            p.addRummor(rummor);
        }

        return p;
    }
}
