package Players;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.NoSuchElementException;

import Cards.Card;
import Cards.Person;
import Cards.Place;
import Cards.Thing;
import Rummor.Rummor;

public class Player {
    private int ID;
    private int numberOfCards;
    public int numberOfHas;
    public int numberOfDontKnow;
    private String playerName;

    public enum Knowledge{
        HAS,
        HASNOT,
        DONTKNOW
    }

    HashMap<Person.Value, Knowledge> personCardTable = new HashMap<>();
    HashMap<Place.Value, Knowledge> placeCardTable = new HashMap<>();
    HashMap<Thing.Value, Knowledge> thingCardTable = new HashMap<>();

    ArrayList<Rummor> validRummors; //keeps Track of Rumors that the player showed a card on.

    public Player(String name, int id, int numberOfCards){
        this.numberOfDontKnow = 0; //changed when we construct the card lists...
        this.ID = id;
        this.playerName = name;
        this.numberOfCards = numberOfCards;
        this.numberOfHas = 0;
        validRummors = new ArrayList<>();
        constructCardLists();
        
    }

    public Player(int id, int numberOfCards){
        this.numberOfDontKnow = 0; //changed when we construct the card lists...
        this.ID = id;
        this.playerName = "Player " + (id + 1);
        this.numberOfCards = numberOfCards;
        this.numberOfHas = 0;
        validRummors = new ArrayList<>();
        constructCardLists();
    }

    public int getID(){
        return this.ID;
    }

    public int getNumberOfCards(){
        return this.numberOfCards;
    }

    public int getNumberOfHas(){
        return this.numberOfHas;
    }

    public int getNumberOfDontKnow(){
        return this.numberOfDontKnow;
    }

    public int getNumberOfUnknownCards(){
        return this.numberOfCards - this.numberOfHas;
    }

    public String getName(){
        return this.playerName;
    }

    public void renamePlayer(String newName){
        this.playerName = newName;
    }


    private void constructCardLists(){
        for (Person.Value v : Person.Value.values()){
            personCardTable.put(v, Knowledge.DONTKNOW);
            numberOfDontKnow++;
        }
        for (Place.Value v : Place.Value.values()){
            placeCardTable.put(v, Knowledge.DONTKNOW);
            numberOfDontKnow++;
        }
        for (Thing.Value v : Thing.Value.values()){
            thingCardTable.put(v, Knowledge.DONTKNOW);
            numberOfDontKnow++;
        }
    }

    public Knowledge getCardStatus(Person.Value v){
        return personCardTable.get(v);
    }

    public Knowledge getCardStatus(Place.Value v){
        return placeCardTable.get(v);
    }

    public Knowledge getCardStatus(Thing.Value v){
        return thingCardTable.get(v);
    }

    public Knowledge getCardStatus(Card c){
        switch(c.getKind()){
            case PERSON:
                return getCardStatus(((Person)c).getValue());
            case PLACE:
                return getCardStatus(((Place)c).getValue());
            case THING:
                return getCardStatus(((Thing)c).getValue());   
            default:
                throw new NoSuchElementException("Error: Unknown Type of Card: From PlayerObject");
        }
    }

    public boolean canRefute(Rummor rummor){
        if(getCardStatus(rummor.getPerson()) == Knowledge.HAS){
            return true;
        }
        if(getCardStatus(rummor.getPlace()) == Knowledge.HAS){
            return true;
        }
        if(getCardStatus(rummor.getThing()) == Knowledge.HAS){
            return true;
        }

        return false;
    }

    public void setCardHas(Person.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        personCardTable.put(v, Knowledge.HAS);
        numberOfHas++;
        if (numberOfCards == numberOfHas){
            setRemainingCardsToHasNot();
        }
    }

    public void setCardHas(Place.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        placeCardTable.put(v, Knowledge.HAS);
        numberOfHas++;
        if (numberOfCards == numberOfHas){
            setRemainingCardsToHasNot();
        }
    }

    public void setCardHas(Thing.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        thingCardTable.put(v, Knowledge.HAS);
        numberOfHas++;
        if (numberOfCards == numberOfHas){
            setRemainingCardsToHasNot();
        }
    }

    public void setCardHasNot(Person.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        personCardTable.put(v, Knowledge.HASNOT);
        if (numberOfCards == (numberOfHas + numberOfDontKnow)){
            setRemainingCardsToHas();
        }
    }

    public void setCardHasNot(Place.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        placeCardTable.put(v, Knowledge.HASNOT);
        if (numberOfCards == (numberOfHas + numberOfDontKnow)){
            setRemainingCardsToHas();
        }
    }

    public void setCardHasNot(Thing.Value v){
        if(getCardStatus(v) == Knowledge.DONTKNOW){
            numberOfDontKnow--;
        }
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        thingCardTable.put(v, Knowledge.HASNOT);
        if (numberOfCards == (numberOfHas + numberOfDontKnow)){
            setRemainingCardsToHas();
        }
    }

    public void setCardDontKnow(Person.Value v){
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        numberOfDontKnow++;
        personCardTable.put(v, Knowledge.DONTKNOW);
    }

    public void setCardDontKnow(Place.Value v){
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        numberOfDontKnow++;
        placeCardTable.put(v, Knowledge.DONTKNOW);
    }

    public void setCardDontKnow(Thing.Value v){
        if(getCardStatus(v) == Knowledge.HAS){
            numberOfHas--;
        }
        numberOfDontKnow++;
        thingCardTable.put(v, Knowledge.DONTKNOW);
    }

    public void setCardDontKnow(Card c){
        switch(c.getKind()){
            case PERSON:
                setCardDontKnow(((Person)c).getValue());
                break;
            case PLACE:
                setCardDontKnow(((Place)c).getValue());
                break;
            case THING:
                setCardDontKnow(((Thing)c).getValue());
                break;
            default:
                throw new NoSuchElementException("Error: Unknown Type of Card: From PlayerObject");
        }
    }

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

    
    public void setCardHasNot(Card c){
        switch(c.getKind()){
            case PERSON:
                setCardHasNot(((Person)c).getValue());
                break;
            case PLACE:
                setCardHasNot(((Place)c).getValue());
                break;
            case THING:
                setCardHasNot(((Thing)c).getValue());
                break;
            default:
                throw new NoSuchElementException("Error: Unknown Type of Card: From PlayerObject");
        }
    }

    private void setRemainingCardsToHasNot(){
        for (Person.Value v : Person.Value.values()){
            if(personCardTable.get(v) == Knowledge.DONTKNOW){
                personCardTable.put(v, Knowledge.HASNOT);
                numberOfDontKnow--;
            }
        }
        for (Place.Value v : Place.Value.values()){
            if(placeCardTable.get(v) == Knowledge.DONTKNOW){
                placeCardTable.put(v, Knowledge.HASNOT);
                numberOfDontKnow--;
            }
        }
        for (Thing.Value v : Thing.Value.values()){
            if(thingCardTable.get(v) == Knowledge.DONTKNOW){
                thingCardTable.put(v, Knowledge.HASNOT);
                numberOfDontKnow--;
            }
        }
    }

    private void setRemainingCardsToHas(){
        for (Person.Value v : Person.Value.values()){
            if(personCardTable.get(v) == Knowledge.DONTKNOW){
                personCardTable.put(v, Knowledge.HAS);
                numberOfDontKnow--;
                numberOfHas++;
            }
        }
        for (Place.Value v : Place.Value.values()){
            if(placeCardTable.get(v) == Knowledge.DONTKNOW){
                placeCardTable.put(v, Knowledge.HAS);
                numberOfDontKnow--;
                numberOfHas++;
            }
        }
        for (Thing.Value v : Thing.Value.values()){
            if(thingCardTable.get(v) == Knowledge.DONTKNOW){
                thingCardTable.put(v, Knowledge.HAS);
                numberOfDontKnow--;
                numberOfHas++;
            }
        }
    }

    public String toString(){
        String dkn = "?";
        String h = "O";
        String hn = "X";
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------\n");
        sb.append("Id: " + this.ID + " Name: " + this.playerName + "\n");
        sb.append("-------------------\n");
        sb.append("Person: \n");
        int num = 0;
        for (Person.Value v : personCardTable.keySet()){
            if (num == 3){
                sb.append("\n");
                num = 0;
            }
            String kn = "";
            switch(getCardStatus(new Person(v))){
                case DONTKNOW:
                    kn = dkn;
                    break;
                case HAS:
                    kn = h;
                    break;
                case HASNOT:
                    kn = hn;
                    break;
                default:
                    break;
                
            }
            sb.append(new Person(v).toString() + ": " + kn + "\t");

            
            num ++;
        }

        sb.append("\n");
        sb.append("-------------------\n");
        sb.append("Place: \n");
        num = 0;
        for (Place.Value v : Place.Value.values()){
            if (num == 3){
                sb.append("\n");
                num = 0;
            }
            String kn = "";
            switch(getCardStatus(new Place(v))){
                case DONTKNOW:
                    kn = dkn;
                    break;
                case HAS:
                    kn = h;
                    break;
                case HASNOT:
                    kn = hn;
                    break;
                default:
                    break;
                
            }
            sb.append(new Place(v).toString() + ": " + kn + "\t");

            
            num ++;
        }


        sb.append("\n");
        sb.append("-------------------\n");
        sb.append("Thing: \n");
        num = 0;
        for (Thing.Value v : Thing.Value.values()){
            if (num == 3){
                sb.append("\n");
                num = 0;
            }
            String kn = "";
            switch(getCardStatus(new Thing(v))){
                case DONTKNOW:
                    kn = dkn;
                    break;
                case HAS:
                    kn = h;
                    break;
                case HASNOT:
                    kn = hn;
                    break;
                default:
                    break;
                
            }
            sb.append(new Thing(v).toString() + ": " + kn + "\t");

            
            num ++;
        }

        sb.append("\n");
        sb.append("-------------------\n");
        return sb.toString();
    }

    public Player copyPlayer(){
        Player p = new Player(playerName, ID, numberOfCards);
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


    public void addRummor(Rummor rummor){
        validRummors.add(rummor);
    }

    public ArrayList<Rummor> getRummors(){
        return validRummors;
    }

    public ArrayList<Card> checkRummors(){
        ArrayList<Rummor> trash =  new ArrayList<>();
        ArrayList<Card> cardsFound = new ArrayList<>();

        for(int i = 0 ; i < validRummors.size(); i++){
            Rummor r = validRummors.get(i);
            if (personCardTable.get(r.getPerson().getValue()) == Knowledge.HAS){
                trash.add(r);
                cardsFound.add(r.getPerson());
                continue;
            }
            else if (placeCardTable.get(r.getPlace().getValue()) == Knowledge.HAS){
                trash.add(r);
                cardsFound.add(r.getPlace());
                continue;
            }
            else if (thingCardTable.get(r.getThing().getValue()) == Knowledge.HAS){
                trash.add(r);
                cardsFound.add(r.getThing());
                continue;
            }

            if(personCardTable.get(r.getPerson().getValue()) == Knowledge.DONTKNOW &&
                placeCardTable.get(r.getPlace().getValue()) == Knowledge.HASNOT &&
                thingCardTable.get(r.getThing().getValue()) == Knowledge.HASNOT)
                {
                    setCardHas(r.getPerson().getValue());
                    trash.add(r);
                    cardsFound.add(r.getPerson());
            }

            if(personCardTable.get(r.getPerson().getValue()) == Knowledge.HASNOT &&
                placeCardTable.get(r.getPlace().getValue()) == Knowledge.DONTKNOW &&
                thingCardTable.get(r.getThing().getValue()) == Knowledge.HASNOT)
                {
                    setCardHas(r.getPlace().getValue());
                    trash.add(r);
                    cardsFound.add(r.getPlace());
            }

            if(personCardTable.get(r.getPerson().getValue()) == Knowledge.HASNOT &&
                placeCardTable.get(r.getPlace().getValue()) == Knowledge.HASNOT &&
                thingCardTable.get(r.getThing().getValue()) == Knowledge.DONTKNOW)
                {
                    setCardHas(r.getThing().getValue());
                    trash.add(r);
                    cardsFound.add(r.getThing());
            }



        }

        for(int i = 0; i < trash.size(); i ++){
            validRummors.remove(trash.get(i));
        }
        return cardsFound;
    }

}
