package Tests;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import Cards.Card;
import Cards.Person;
import Cards.Place;
import Cards.Thing;
import Cards.Person.Value;
import Drivers.MainProgram;
import Players.Player;
import Players.Player.Knowledge;
import Rummor.Rummor;

public class MainProgramTest {
    @Test
    public void mainProgramMakePlayerHashMapWithNames(){
        Integer[] pcc = {1,2,3,4,5};
        String[] pn = {"a","b","c","d","e"};
        HashMap<Integer,Player> h = new HashMap<>();
        try {
            h = MainProgram.makePlayersSet(pcc, pn);
        } catch (Exception e) {
            Assert.assertTrue(false); //There was an exception while making the hash set.
        }

        Assert.assertEquals("Envelope", h.get(-1).getName());
        Assert.assertEquals(3, h.get(-1).getNumberOfCards());
        Assert.assertEquals("a", h.get(0).getName());
        Assert.assertEquals(1, h.get(0).getNumberOfCards());
        Assert.assertEquals("b", h.get(1).getName());
        Assert.assertEquals(2, h.get(1).getNumberOfCards());
        Assert.assertEquals("c", h.get(2).getName());
        Assert.assertEquals(3, h.get(2).getNumberOfCards());
        Assert.assertEquals("d", h.get(3).getName());
        Assert.assertEquals(4, h.get(3).getNumberOfCards());
        Assert.assertEquals("e", h.get(4).getName());
        Assert.assertEquals(5, h.get(4).getNumberOfCards());
    }

    @Test
    public void mainProgramMakePlayerHashMapNoNames(){
        Integer[] pcc = {1,2,3,4,5};
        HashMap<Integer,Player> h = new HashMap<>();
        try {
            h = MainProgram.makePlayersSet(pcc);
        } catch (Exception e) {
            Assert.assertTrue(false); //There was an exception while making the hash set.
        }

        Assert.assertEquals("Envelope", h.get(-1).getName());
        Assert.assertEquals(3, h.get(-1).getNumberOfCards());
        Assert.assertEquals("Player 1", h.get(0).getName());
        Assert.assertEquals(1, h.get(0).getNumberOfCards());
        Assert.assertEquals("Player 2", h.get(1).getName());
        Assert.assertEquals(2, h.get(1).getNumberOfCards());
        Assert.assertEquals("Player 3", h.get(2).getName());
        Assert.assertEquals(3, h.get(2).getNumberOfCards());
        Assert.assertEquals("Player 4", h.get(3).getName());
        Assert.assertEquals(4, h.get(3).getNumberOfCards());
        Assert.assertEquals("Player 5", h.get(4).getName());
        Assert.assertEquals(5, h.get(4).getNumberOfCards());
    }

    @Test
    public void mainProgramGetPlayer(){
        Integer[] pcc = {1,2,3,4,5};
        String[] pn = {"a","b","c","d","e"};
        MainProgram mp = new MainProgram(pcc, pn);
        MainProgram mp2 = new MainProgram(pcc);

        Assert.assertEquals("Envelope", mp.getPlayer(-1).getName());
        Assert.assertEquals(3, mp.getPlayer(-1).getNumberOfCards());
        Assert.assertEquals("a", mp.getPlayer(0).getName());
        Assert.assertEquals(1, mp.getPlayer(0).getNumberOfCards());

        Assert.assertEquals("Envelope", mp2.getPlayer(-1).getName());
        Assert.assertEquals(3, mp2.getPlayer(-1).getNumberOfCards());
        Assert.assertEquals("Player 1", mp2.getPlayer(0).getName());
        Assert.assertEquals(1, mp2.getPlayer(0).getNumberOfCards());


        Assert.assertEquals(-1, mp.getPlayer(-1).getID());
        Assert.assertEquals(0, mp.getPlayer(0).getID());
        Assert.assertEquals(1, mp.getPlayer(1).getID());
        Assert.assertEquals(2, mp.getPlayer(2).getID());
    }

    @Test
    public void mainProgramAddCardToPlayerTest(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);
        mp.addCardToPlayer(Person.Value.GREEN, 0);
        mp.addCardToPlayer(Place.Value.BALLROOM, 1);
        mp.addCardToPlayer(Thing.Value.CANDLESTICK, 2);
        mp.addCardToPlayer(new Person(Value.PLUM), 3);
        mp.addCardToPlayer(new Place(Place.Value.BILLIARDROOM), 3);
        mp.addCardToPlayer(new Thing(Thing.Value.DAGGER), 1);

        Assert.assertEquals(1, mp.getPlayer(0).getNumberOfHas());
        Assert.assertEquals(0, mp.getPlayer(0).getNumberOfUnknownCards());
        int hasCount = 0;
        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(c));
            if(mp.getPlayer(0).getCardStatus(c) == Knowledge.HAS){
                hasCount++;
            }
        }
        Assert.assertEquals(1, hasCount);
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));


        Assert.assertEquals(2, mp.getPlayer(1).getNumberOfHas());
        Assert.assertEquals(0, mp.getPlayer(1).getNumberOfUnknownCards());
        hasCount = 0;
        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(c));
            if(mp.getPlayer(1).getCardStatus(c) == Knowledge.HAS){
                hasCount++;
            }
        }
        Assert.assertEquals(2, hasCount);
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.DAGGER));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));



        Assert.assertEquals(1, mp.getPlayer(2).getNumberOfHas());
        Assert.assertEquals(2, mp.getPlayer(2).getNumberOfUnknownCards());
        hasCount = 0;
        int hasnotCount = 0;
        for (Card c : mp.getCards()) {
            if(mp.getPlayer(2).getCardStatus(c) == Knowledge.HAS){
                hasCount++;
            }
            else if(mp.getPlayer(2).getCardStatus(c) == Knowledge.HASNOT){
                hasnotCount++;
            }
        }
        Assert.assertEquals(1, hasCount);
        Assert.assertEquals(5, hasnotCount);
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(2).getCardStatus(Place.Value.DININGROOM));


        Assert.assertEquals(2, mp.getPlayer(3).getNumberOfHas());
        Assert.assertEquals(2, mp.getPlayer(3).getNumberOfUnknownCards());
        hasCount = 0;
        hasnotCount = 0;
        for (Card c : mp.getCards()) {
            if(mp.getPlayer(3).getCardStatus(c) == Knowledge.HAS){
                hasCount++;
            }
            else if(mp.getPlayer(3).getCardStatus(c) == Knowledge.HASNOT){
                hasnotCount++;
            }
        }
        Assert.assertEquals(2, hasCount);
        Assert.assertEquals(4, hasnotCount);
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(3).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(3).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Person.Value.SCARLETT));





    }

    @Test
    public void mainProgramAddMultipleCardsToPlayerTest(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);

        Card[] list1 = {new Person(Value.PLUM), new Place(Cards.Place.Value.BALLROOM), new Thing(Thing.Value.LEADPIPE)};
        Card[] list2 = {new Person(Value.GREEN), new Place(Cards.Place.Value.DININGROOM)};

        mp.addCardsToPlayer(list1, 4);

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(4).getCardStatus(Person.Value.GREEN));

        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.HASNOT, mp.getPlayer(4).getCardStatus(c));
        }

        mp.addCardsToPlayer(list2, 4);

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(Place.Value.DININGROOM));

        Assert.assertEquals(0, mp.getPlayer(4).getNumberOfUnknownCards());
        Assert.assertEquals(5, mp.getPlayer(4).getNumberOfHas());
        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.DONTKNOW, mp.getPlayer(4).getCardStatus(c));
        }

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Person.Value.PLUM));

    }





    public void mainProgramRemoveCardToPlayerTest(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);
        mp.removeCardFromPlayer(Person.Value.GREEN, 0);
        mp.removeCardFromPlayer(Place.Value.BALLROOM, 1);
        mp.removeCardFromPlayer(Thing.Value.CANDLESTICK, 2);
        mp.removeCardFromPlayer(new Person(Value.PLUM), 3);
        mp.removeCardFromPlayer(new Place(Place.Value.BILLIARDROOM), 3);
        mp.removeCardFromPlayer(new Thing(Thing.Value.DAGGER), 1);

        Assert.assertEquals(0, mp.getPlayer(0).getNumberOfHas());
        Assert.assertEquals(1, mp.getPlayer(0).getNumberOfUnknownCards());
        int hasnotCount = 0;
        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(c));
            if(mp.getPlayer(0).getCardStatus(c) == Knowledge.HASNOT){
                hasnotCount++;
            }
        }
        Assert.assertEquals(1, hasnotCount);
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));


        Assert.assertEquals(0, mp.getPlayer(1).getNumberOfHas());
        Assert.assertEquals(2, mp.getPlayer(1).getNumberOfUnknownCards());
        hasnotCount = 0;
        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(c));
            if(mp.getPlayer(1).getCardStatus(c) == Knowledge.HASNOT){
                hasnotCount++;
            }
        }
        Assert.assertEquals(2, hasnotCount);
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.DAGGER));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));


        
    }

    @Test
    public void mainProgramRemoveMultipleCardsFromPlayerTest(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);

        Card[] list1 = {new Person(Value.PLUM), new Place(Cards.Place.Value.BALLROOM), new Thing(Thing.Value.LEADPIPE)};

        mp.removeCardsFromPlayer(list1, 4);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(4).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(4).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(4).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(4).getCardStatus(Person.Value.GREEN));

        for (Card c : mp.getCards()) {
            Assert.assertNotEquals(Knowledge.HAS, mp.getPlayer(4).getCardStatus(c));
        } 

    }

    public void mainProgramStackAddToStack(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);

        Assert.assertEquals(1, mp.stack.size());
        HashMap<Integer, Player> state = mp.stack.popTop();
        Assert.assertEquals(1, mp.stack.size());
        
        mp.addToStack();
        Assert.assertEquals(2, mp.stack.size());
        
        mp.addCardToPlayer(Person.Value.PEACOCK, 0);
        mp.addCardToPlayer(Person.Value.PEACOCK, 1);
        mp.addCardToPlayer(Person.Value.PEACOCK, 2);
        mp.addCardToPlayer(Thing.Value.CANDLESTICK, 0);
        mp.addCardToPlayer(Place.Value.BILLIARDROOM, 0);

        Assert.assertEquals(Knowledge.DONTKNOW, state.get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, state.get(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, state.get(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, state.get(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, state.get(0).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertNotEquals(mp.getPlayers().get(0).getCardStatus(Person.Value.PEACOCK), state.get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(0).getCardStatus(Place.Value.BILLIARDROOM));
  
    }
    

    @Test
    public void mainProgramUndo(){
        Integer[] pcc = {1,2,3,4,5};
        MainProgram mp = new MainProgram(pcc);

        Assert.assertEquals(1, mp.stack.size());
        
        mp.addToStack();

        HashMap<Integer, Player> state2 = mp.stack.peekTop();
        Assert.assertEquals(2, mp.stack.size());
        

        mp.addCardToPlayer(Person.Value.PEACOCK, 0);
        mp.addCardToPlayer(Person.Value.GREEN, 1);
        mp.addCardToPlayer(Person.Value.PLUM, 2);
        mp.addCardToPlayer(Thing.Value.CANDLESTICK, 0);
        mp.addCardToPlayer(Place.Value.BILLIARDROOM, 0);

        mp.getPlayer(0).addRummor(new Rummor(new Person(Person.Value.GREEN), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)));

        Assert.assertEquals(Knowledge.HAS, mp.getPlayers().get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, state2.get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(1, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(1).getRummors().size());
        
        mp.undo();

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(1).getRummors().size());
        Assert.assertEquals(Knowledge.HAS, state2.get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayers().get(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayers().get(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayers().get(2).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayers().get(0).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayers().get(0).getCardStatus(Thing.Value.CANDLESTICK));
  
    }

    @Test public void mainProgramInvestigateTest(){
        Integer[] pcc = {3,3,3,3};
        MainProgram mp = new MainProgram(pcc);
        Boolean[] ar = {false, true};
        mp.investigate(1, ar, new Rummor(new Person(Person.Value.GREEN), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)), null);
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Person.Value.GREEN));

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Place.Value.BALLROOM));

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK));

        Assert.assertEquals(0, mp.getPlayer(-1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(1, mp.getPlayer(3).getRummors().size());

        mp.investigate(2, ar, new Rummor(new Person(Person.Value.PLUM), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK)), new Thing(Thing.Value.CANDLESTICK));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(3).getCardStatus(Person.Value.GREEN)); //this is the thing we are really checking for. Can my two investigations deduce this info.

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(2).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PLUM));

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.BALLROOM));

        

        Assert.assertEquals(0, mp.getPlayer(-1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());

    }

    @Test
    public void mainProgramSpoilerHasTest(){
        Integer[] pcc = {3,3,3,3};
        MainProgram mp = new MainProgram(pcc);

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));

        mp.spolier(1, Knowledge.HAS, new Person(Person.Value.GREEN));
        mp.spolier(1, Knowledge.HAS, new Place(Place.Value.BALLROOM));
        mp.spolier(1, Knowledge.HAS, new Thing(Thing.Value.CANDLESTICK));

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
    }

    @Test
    public void mainProgramSpoilerHasNotTest(){
        Integer[] pcc = {3,3,3,3};
        MainProgram mp = new MainProgram(pcc);

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));

        mp.spolier(0, Knowledge.HASNOT, new Person(Person.Value.GREEN));
        mp.spolier(0, Knowledge.HASNOT, new Place(Place.Value.BALLROOM));
        mp.spolier(0, Knowledge.HASNOT, new Thing(Thing.Value.CANDLESTICK));

        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
    }

}
