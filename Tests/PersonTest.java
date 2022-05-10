package Tests;

import org.junit.Test;

import Cards.Card;
import Cards.Person;

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
