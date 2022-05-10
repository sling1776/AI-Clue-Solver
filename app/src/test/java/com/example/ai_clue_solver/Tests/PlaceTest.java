package com.example.ai_clue_solver.Tests;

import org.junit.Test;
import org.junit.Assert;

import com.example.ai_clue_solver.Cards.*;

public class PlaceTest {
    @Test
    public void verifyPlace(){
        for (Place.Value v : Place.Value.values()) {
            Assert.assertEquals(new Place(v).getValue(), v);
            Assert.assertEquals(new Place(v).getKind(), Card.Kind.PLACE);
        }
    }
}
