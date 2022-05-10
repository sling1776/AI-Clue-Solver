package com.example.ai_clue_solver.Tests;

import org.junit.Test;
import org.junit.Assert;
import com.example.ai_clue_solver.Cards.*;


public class ThingTest {
    @Test
    public void verifyThing(){
        for (Thing.Value v : Thing.Value.values()) {
            Assert.assertEquals(new Thing(v).getValue(), v);
            Assert.assertEquals(new Thing(v).getKind(), Card.Kind.THING);
        }
    }
}