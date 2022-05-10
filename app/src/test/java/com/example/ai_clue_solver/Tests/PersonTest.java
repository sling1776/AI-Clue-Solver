package com.example.ai_clue_solver.Tests;

import org.junit.Test;

import com.example.ai_clue_solver.Cards.*;

import org.junit.Assert;

public class PersonTest {
    @Test
    public void verifyValue(){
        for (Person.Value v : Person.Value.values()) {
            Assert.assertEquals(new Person(v).getValue(), v);
            Assert.assertEquals(new Person(v).getKind(), Card.Kind.PERSON);
        }
    }
}
