package Tests;

import org.junit.Test;

import Cards.Card;
import Cards.Person;
import Cards.Place;
import Cards.Thing;
import Cards.Place.Value;
import Players.Player;
import Players.Player.Knowledge;
import Rummor.Rummor;

import java.util.ArrayList;
import java.util.Random;

import org.junit.Assert;
import org.junit.Before;

public class PlayerTest {
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
    public void playerNameTest(){
        Player p = new Player("name", 0, 4);
        Player p1 = new Player(0, 4);
        Player p2 = new Player(1, 4);
        Player p3 = new Player(57, 4);
        
        Assert.assertEquals(p.getName(), "name");

        Assert.assertEquals(p1.getName(), "Player 1");
        Assert.assertEquals(p2.getName(), "Player 2");
        Assert.assertEquals(p3.getName(), "Player 58");
    }

    @Test
    public void playerIDTest(){
        Player p = new Player(-1, 3);
        Player p1 = new Player(0, 4);
        Player p2 = new Player(1, 4);
        Player p3 = new Player(57, 4);
        
        Assert.assertEquals(-1, p.getID());

        Assert.assertEquals(0, p1.getID());
        Assert.assertEquals(1, p2.getID());
        Assert.assertEquals(57, p3.getID());
    }

    @Test
    public void playerSetHasTest(){
        Player p = new Player(0, 4);
        int selected = rdm.nextInt(cards.size());
        p.setCardHas(cards.get(selected));
        Assert.assertEquals(1, p.getNumberOfHas());
        Assert.assertEquals(p.getNumberOfCards() - 1, p.getNumberOfUnknownCards());
        Assert.assertEquals(p.getCardStatus(cards.get(selected)), Knowledge.HAS);
    }

    @Test
    public void playerSetHasNotTest(){
        Player p = new Player(0, 4);
        int selected = rdm.nextInt(cards.size());
        p.setCardHasNot(cards.get(selected));
        Assert.assertEquals(0, p.getNumberOfHas());
        Assert.assertEquals(p.getNumberOfCards(), p.getNumberOfUnknownCards());
        Assert.assertEquals(p.getCardStatus(cards.get(selected)), Knowledge.HASNOT);
    }

    @Test
    public void playerAutoSetHasNot(){
        Player p = new Player(0, 3);
        int selected = 0;//the first card
        int selected1 = 14;
        int selected2 = 20; //the last card in the list
        p.setCardHas(cards.get(selected));
        p.setCardHas(cards.get(selected1));
        for(int i = 0; i < cards.size(); i ++){
            if(i != selected && i != selected1){
                Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(cards.get(i)));
            }
        }
        p.setCardHas(cards.get(selected2));
        Assert.assertEquals(3, p.getNumberOfHas());
        Assert.assertEquals(0, p.getNumberOfUnknownCards());
        for(int i = 0; i < cards.size(); i ++){
            if(i != selected && i != selected1 && i != selected2){
                Assert.assertEquals(Knowledge.HASNOT, p.getCardStatus(cards.get(i)));
            }
        }
        Assert.assertEquals(0, p.getNumberOfDontKnow());
    }

    @Test
    public void playerAutoSetHas(){
        Player p = new Player(0, 18);
        int selected = 0;//the first card
        int selected1 = 14;
        int selected2 = 20; //the last card in the list
        p.setCardHasNot(cards.get(selected));
        p.setCardHasNot(cards.get(selected1));
        for(int i = 0; i < cards.size(); i ++){
            if(i != selected && i != selected1){
                Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(cards.get(i)));
            }
        }
        Assert.assertEquals(18, p.getNumberOfUnknownCards());
        Assert.assertEquals(0, p.getNumberOfHas());

        p.setCardHasNot(cards.get(selected2));
        Assert.assertEquals(18, p.getNumberOfHas());
        Assert.assertEquals(0, p.getNumberOfUnknownCards());
        for(int i = 0; i < cards.size(); i ++){
            if(i != selected && i != selected1 && i != selected2){
                Assert.assertEquals(Knowledge.HAS, p.getCardStatus(cards.get(i)));
            }
        }
    }

    @Test
    public void playerRenameTest(){
        Player p = new Player(0, 4);
        Assert.assertEquals("Player 1", p.getName());
        p.renamePlayer("name");
        Assert.assertEquals("name", p.getName());
    }


    @Test
    public void playerCanRefuteTest(){
        Rummor rummor = new Rummor(personCards.get(0), placeCards.get(0), thingCards.get(0));
        Player p = new Player(0, 4);
        Player p1 = new Player(0, 4);
        Player p2 = new Player(0, 4);
        Player p3 = new Player(0, 4);
        p.setCardHas(personCards.get(0));
        p1.setCardHas(placeCards.get(0));
        p2.setCardHas(thingCards.get(0));
        p3.setCardHas(personCards.get(1));

        Assert.assertTrue(p.canRefute(rummor));
        Assert.assertTrue(p1.canRefute(rummor));
        Assert.assertTrue(p2.canRefute(rummor));
        Assert.assertFalse(p3.canRefute(rummor));
    }

    @Test
    public void playerCopyTest(){
        Player p = new Player("name", 7, 6);
        p.setCardHas(Person.Value.GREEN);
        p.setCardHasNot(Person.Value.PLUM);
        p.setCardHas(Place.Value.STUDY);
        p.setCardHas(Thing.Value.WRENCH);

        Player copyP = p.copyPlayer();

        Assert.assertEquals(Knowledge.HAS, copyP.getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, copyP.getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HAS, copyP.getCardStatus(Place.Value.STUDY));
        Assert.assertEquals(Knowledge.HAS, copyP.getCardStatus(Thing.Value.WRENCH));
    }


    @Test
    public void playerAddRummor(){
        Player p = new Player(0, 3);
        Assert.assertEquals(0, p.getRummors().size());
        p.addRummor(new Rummor(new Person(Person.Value.GREEN), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        Assert.assertEquals(1, p.getRummors().size());
    }


    @Test
    public void playerCheckRummors(){
        Player p = new Player(0, 3);
        p.setCardHasNot(Person.Value.GREEN);
        p.setCardHasNot(Place.Value.BALLROOM);
        Assert.assertEquals(0, p.checkRummors().size());
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Thing.Value.CANDLESTICK));
        p.addRummor(new Rummor(new Person(Person.Value.GREEN), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Thing.Value.CANDLESTICK));
        ArrayList<Card> cs = p.checkRummors();
        Assert.assertEquals(1, cs.size());
        Assert.assertEquals(Card.Kind.THING, cs.get(0).getKind());
        Assert.assertEquals(Thing.Value.CANDLESTICK, ((Thing)cs.get(0)).getValue());
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(0, p.getRummors().size());

        p = new Player(0, 3);
        p.setCardHasNot(Thing.Value.CANDLESTICK);
        p.setCardHasNot(Place.Value.BALLROOM);
        Assert.assertEquals(0, p.checkRummors().size());
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Person.Value.MUSTARD));
        p.addRummor(new Rummor(new Person(Person.Value.MUSTARD), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Person.Value.MUSTARD));
        cs = p.checkRummors();
        Assert.assertEquals(1, cs.size());
        Assert.assertEquals(Card.Kind.PERSON, cs.get(0).getKind());
        Assert.assertEquals(Person.Value.MUSTARD, ((Person)cs.get(0)).getValue());
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Person.Value.MUSTARD));
        Assert.assertEquals(0, p.getRummors().size());

        p = new Player(0, 3);
        p.setCardHasNot(Thing.Value.CANDLESTICK);
        p.setCardHasNot(Person.Value.PEACOCK);
        Assert.assertEquals(0, p.checkRummors().size());
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Place.Value.HALL));
        p.addRummor(new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.HALL), new Thing(Thing.Value.CANDLESTICK)));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Place.Value.HALL));
        cs = p.checkRummors();
        Assert.assertEquals(1, cs.size());
        Assert.assertEquals(Card.Kind.PLACE, cs.get(0).getKind());
        Assert.assertEquals(Place.Value.HALL, ((Place)cs.get(0)).getValue());
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Place.Value.HALL));
        Assert.assertEquals(0, p.getRummors().size());



        p = new Player("name", 5, 3);
        p.setCardHas(Person.Value.SCARLETT);
        p.addRummor(new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.HALL), new Thing(Thing.Value.CANDLESTICK)));
        cs = p.checkRummors();
        Assert.assertEquals(Person.Value.SCARLETT, ((Person)cs.get(0)).getValue());
        Assert.assertEquals(0, p.getRummors().size());



        p = new Player("name", 5, 3);
        p.setCardHasNot(Person.Value.SCARLETT);
        p.setCardHasNot(Place.Value.BALLROOM);
        p.addRummor(new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        p.addRummor(new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.WRENCH)));
        cs = p.checkRummors();
        Assert.assertEquals(2, cs.size());
        Assert.assertEquals(Thing.Value.CANDLESTICK, ((Thing)cs.get(0)).getValue());
        Assert.assertEquals(Thing.Value.WRENCH, ((Thing)cs.get(1)).getValue());
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(0, p.getRummors().size());

        p = new Player("name", 5, 3);
        p.setCardHasNot(Person.Value.SCARLETT);
        p.addRummor(new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        p.addRummor(new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.WRENCH)));
        cs = p.checkRummors();
        Assert.assertEquals(0, cs.size());
        Assert.assertEquals(2, p.getRummors().size());
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Thing.Value.WRENCH));


        p = new Player("name", 5, 3);
        p.addRummor(new Rummor(new Person(Person.Value.GREEN), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));
        cs = p.checkRummors();
        Assert.assertEquals(0, cs.size());
        Assert.assertEquals(1, p.getRummors().size());

        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Thing.Value.CANDLESTICK));

        p.setCardHasNot(Person.Value.PLUM);
        p.setCardHasNot(Place.Value.BALLROOM);
        p.setCardHasNot(Thing.Value.CANDLESTICK);

        Assert.assertEquals(Knowledge.HASNOT, p.getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, p.getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, p.getCardStatus(Thing.Value.CANDLESTICK));

        cs = p.checkRummors();

        Assert.assertEquals(Knowledge.HASNOT, p.getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, p.getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, p.getCardStatus(Thing.Value.CANDLESTICK));

        Assert.assertEquals(1, cs.size());
        Assert.assertEquals(Person.Value.GREEN, ((Person)cs.get(0)).getValue());
        Assert.assertEquals(0, p.getRummors().size());
        
    }
}
