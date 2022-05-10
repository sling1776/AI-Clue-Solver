package Tests;

import java.util.HashMap;

import org.junit.Assert;
import org.junit.Test;

import Cards.Person;
import Players.Player;
import Players.PlayerStateStack;
import Players.Player.Knowledge;

public class PlayerStateStackTest {
    
    @Test
    public void playerStateStackOperationsTest(){
        Player p = new Player(0, 4);
        Player p1 = new Player(1, 4);
        Player p2 = new Player(2, 4);
        Player p3 = new Player(3, 4);

        Player cp = new Player(0, 4);
        Player cp1 = new Player(1, 4);
        Player cp2 = new Player(2, 4);
        Player cp3 = new Player(3, 4);

        HashMap<Integer, Player> state1 = new HashMap<>();
        HashMap<Integer, Player> state2 = new HashMap<>();

        state1.put(p.getID(), p);
        state1.put(p1.getID(), p1);
        state1.put(p2.getID(), p2);
        state1.put(p3.getID(), p3);
        p.setCardHas(Person.Value.GREEN);



        state2.put(cp.getID(), cp);
        state2.put(cp1.getID(), cp1);
        state2.put(cp2.getID(), cp2);
        state2.put(cp3.getID(), cp3);

        PlayerStateStack stack = new PlayerStateStack(state1);
        Assert.assertEquals(1, stack.size());
        Assert.assertEquals(Knowledge.HAS, stack.popTop().get(p.getID()).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(1, stack.size());

        stack.addToStack(state2);
        Assert.assertEquals(2, stack.size());
        Assert.assertEquals(Knowledge.DONTKNOW, stack.popTop().get(p.getID()).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(1, stack.size());

        stack.addToStack(state2);
        Assert.assertEquals(2, stack.size());
        Assert.assertEquals(Knowledge.DONTKNOW, stack.peekTop().get(p.getID()).getCardStatus(Person.Value.GREEN));
        Assert.assertEquals(2, stack.size());
    }


}
