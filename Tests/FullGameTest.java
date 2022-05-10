package Tests;

import org.junit.Assert;
import org.junit.Test;

import Cards.Person;
import Cards.Place;
import Cards.Thing;
import Drivers.MainProgram;
import Players.Envelope;
import Players.Player.Knowledge;
import Rummor.Rummor;

public class FullGameTest {
    
    /**
     * This is an actual play through of the game of clue. The rummors made were exactly in the
     * order they occured in the game. At each step, this tests what should have been known at 
     * each level. 
     * 
     * Even after going through my notes several times, I found that the AI had deduced something I had not.
     * When I was originally playing the game, I did not think you could have deduced the weapon until 
     * end of the game, however, the AI was able to deduce it at playerResponse8. I also did not think
     * it was possible to deduce what Cards Jacob had shown Matthew, however, the AI was able to solve
     * both of those cards by the end of the game.
     * 
     * An interesting point to review is in playerResponse9 when the rummor solved from Matthew
     * actually causes a rummor to be solved in Jacobs. Most people would not be able to connect 
     * the dots here and figure out that Jacob had the Lead Pipe at that point as it was also
     * difficult to determine that Matthew had the lounge.
     * 
     * In the end of this game in real life, Spencer did not win with the commputer on his side because
     * Julia made a really good rummor and spoiled everything by claiming she did not have any of those 
     * cards either. Oh well. Such is life. 
     * 
     * Matthew ended up winning the game because he was next in line and claimed the easy victory. From 
     * talking with matthew, he had not yet deduced that the candlestick was the weapon. Had Julia not 
     * given her secret away he would not have won.
     * 
     * Jacob however on that turn learned that the Candlestick was the weapon and also knew the person and 
     * place from a really lucky guess earlier on. He wouldn't have won though as Spencer was before him with 
     * the computer and had figured everything else out. 
     * 
     * Julia was just excited to have randomly guessed it as she wasn't really taking any notes to help 
     * herself out.
     */
    @Test
    public void game1Test(){
        Integer[] numCards = {4, 4, 5, 5};
        String[] names = {"Spencer", "Jacob", "Julia", "Matthew"};
        MainProgram mp = new MainProgram(numCards, names);
        mp.addCardToPlayer(Thing.Value.ROPE, 0);
        mp.addCardToPlayer(Person.Value.SCARLETT, 0);
        mp.addCardToPlayer(Place.Value.BALLROOM, 0);
        mp.addCardToPlayer(Place.Value.BILLIARDROOM, 0);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.ROPE));

        Rummor rummor = new Rummor(new Person(Person.Value.MUSTARD), new Place(Place.Value.BALLROOM), new Thing(Thing.Value.CANDLESTICK));
        int rummorMaker = 2; //Julia
        Boolean[] playerResponses0 = {false, true}; //Spencer Showed a card
        mp.investigate(rummorMaker, playerResponses0, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.MUSTARD));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());

        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.CONSERVATORY), new Thing(Thing.Value.DAGGER));
        rummorMaker = 3; //Matthew
        Boolean[] playerResponses1 = {false, true}; //Jacob Showed A Card
        mp.investigate(rummorMaker, playerResponses1, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.MUSTARD));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Place.Value.BALLROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(1, mp.getPlayer(1).getRummors().size());

        rummor = new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BILLIARDROOM), new Thing(Thing.Value.WRENCH));
        rummorMaker = 1; //Jacob
        Boolean[] playerResponses2 = {false, false, true}; //Spencer Showed A Card
        mp.investigate(rummorMaker, playerResponses2, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(1, mp.getPlayer(1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());


        rummor = new Rummor(new Person(Person.Value.PLUM), new Place(Place.Value.LOUNGE), new Thing(Thing.Value.LEADPIPE));
        rummorMaker = 3; //Matthew
        Boolean[] playerResponses3 = {false, true}; //Jacob Showed A Card
        mp.investigate(rummorMaker, playerResponses3, rummor, null);

        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size());

        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.LIBRARY), new Thing(Thing.Value.WRENCH));
        rummorMaker = 0; //Spencer
        Boolean[] playerResponses4 = {true}; //Jacob Showed A Card
        mp.investigate(rummorMaker, playerResponses4, rummor, new Thing(Thing.Value.WRENCH));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.WRENCH));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.LIBRARY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.LIBRARY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(2).getCardStatus(Place.Value.LIBRARY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(3).getCardStatus(Place.Value.LIBRARY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Place.Value.LIBRARY));

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());


        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.CONSERVATORY), new Thing(Thing.Value.WRENCH));
        rummorMaker = 1; //Jacob
        Boolean[] playerResponses5 = {false, false, false}; //None
        mp.investigate(rummorMaker, playerResponses5, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Place.Value.CONSERVATORY));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.WRENCH));

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size());
        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size()); //Dagger and Leadpipe were the cards shown, but we can't disporve them yet...
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());


        rummor = new Rummor(new Person(Person.Value.SCARLETT), new Place(Place.Value.BILLIARDROOM), new Thing(Thing.Value.WRENCH));
        rummorMaker = 2; //Julia
        Boolean[] playerResponses6 = {false, true}; //Spencer
        mp.investigate(rummorMaker, playerResponses6, rummor, null);

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.SCARLETT));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.SCARLETT));

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.BILLIARDROOM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Place.Value.BILLIARDROOM));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.WRENCH));

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size()); //Should always remain 0...
        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size()); 
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());


        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.CONSERVATORY), new Thing(Thing.Value.WRENCH));
        rummorMaker = 3; //Matthew
        Boolean[] playerResponses7 = {false, true}; //Jacob
        mp.investigate(rummorMaker, playerResponses7, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Place.Value.CONSERVATORY));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.WRENCH));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.WRENCH));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK)); 
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Thing.Value.CANDLESTICK)); 

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size()); 
        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size()); //Should be 2 because we knew he had the wrench.
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());


    /**
     * This is where things start to get interesting...
     * 
     * In playerRewponses8, we can deduce the Conservatory because Jacob revealed that he did not have it.
     * 
     * We can also deduce the Candlestick because we knew Matthew does not have it from the very first Query
     * playerResponses0. Since Jacob and Julia responded no to having the candlestick we can see that it must 
     * be the weapon.
     */

        rummor = new Rummor(new Person(Person.Value.PLUM), new Place(Place.Value.CONSERVATORY), new Thing(Thing.Value.CANDLESTICK));
        rummorMaker = 0; //Spencer
        Boolean[] playerResponses8 = {false, false, true}; //Matthew
        mp.investigate(rummorMaker, playerResponses8, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));
        
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(3).getCardStatus(Person.Value.PLUM));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Person.Value.PLUM));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(-1).getCardStatus(Place.Value.CONSERVATORY));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK)); // This should have been deduced from the first Query
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(-1).getCardStatus(Thing.Value.CANDLESTICK)); //We just needed Julia and Jacob to deduce this which we got from this rummor.

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size()); 
        Assert.assertEquals(2, mp.getPlayer(1).getRummors().size()); //Still don't know about the lead pipe and the dagger...
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size());


        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.LOUNGE), new Thing(Thing.Value.ROPE));
        rummorMaker = 1; //Jacob
        Boolean[] playerResponses9 = {false, true}; //Matthew
        mp.investigate(rummorMaker, playerResponses9, rummor, null);

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.DONTKNOW, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));
        
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.LOUNGE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Place.Value.LOUNGE));//This was deduced because Matthew has the lounge
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.LOUNGE));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(3).getCardStatus(Place.Value.LOUNGE)); //This was the card that matthew showed. We know this because we knew that He did not have Peacock or the rope.
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Place.Value.LOUNGE));

        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(0).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.ROPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.ROPE)); 
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.ROPE)); 

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.LEADPIPE)); //Because Jacob does not have plum or the lounge he must have the lead pipe.
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.LEADPIPE));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.LEADPIPE)); 
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.LEADPIPE)); 


        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size()); 
        Assert.assertEquals(1, mp.getPlayer(1).getRummors().size()); //Still don't know about the lead pipe and the dagger...
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size()); //new Rummor added to Matthew but was immediately removed

        Assert.assertFalse(((Envelope)mp.getPlayer(-1)).foundSoultion()); //Still haven't solved the solution yet.

        //Julia's Lucky Guess:
        rummor = new Rummor(new Person(Person.Value.PEACOCK), new Place(Place.Value.CONSERVATORY), new Thing(Thing.Value.CANDLESTICK));
        rummorMaker = 2; //Julia
        Boolean[] playerResponses10 = {false, false, false}; //NONE
        mp.investigate(rummorMaker, playerResponses10, rummor, null);


        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Person.Value.PEACOCK)); //because Jacob said no, we now know who it is.
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Person.Value.PEACOCK));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(-1).getCardStatus(Person.Value.PEACOCK));
        
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Place.Value.CONSERVATORY));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Place.Value.CONSERVATORY)); //This was the card that matthew showed
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(-1).getCardStatus(Place.Value.CONSERVATORY));

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(1).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.CANDLESTICK));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.CANDLESTICK)); 
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(-1).getCardStatus(Thing.Value.CANDLESTICK)); 

        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(0).getCardStatus(Thing.Value.DAGGER));
        Assert.assertEquals(Knowledge.HAS, mp.getPlayer(1).getCardStatus(Thing.Value.DAGGER));//Because Jacob does not have peacock or the conservatory he must have the dagger...
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(2).getCardStatus(Thing.Value.DAGGER));
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(3).getCardStatus(Thing.Value.DAGGER)); 
        Assert.assertEquals(Knowledge.HASNOT, mp.getPlayer(-1).getCardStatus(Thing.Value.DAGGER)); 

        Assert.assertEquals(0, mp.getPlayer(0).getRummors().size()); 
        Assert.assertEquals(0, mp.getPlayer(1).getRummors().size()); 
        Assert.assertEquals(0, mp.getPlayer(2).getRummors().size());
        Assert.assertEquals(0, mp.getPlayer(3).getRummors().size()); 

        Assert.assertTrue(((Envelope)mp.getPlayer(-1)).foundSoultion()); //Game over!

    }
}
