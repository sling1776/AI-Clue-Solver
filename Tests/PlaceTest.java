package Tests;

import org.junit.Test;
import org.junit.Assert;

import Cards.Place;
import Cards.Card;

public class PlaceTest {
    @Test
    public void verifyPlace(){
        for (Place.Value v : Place.Value.values()) {
            Assert.assertEquals(new Place(v).getValue(), v);
            Assert.assertEquals(new Place(v).getKind(), Card.Kind.PLACE);
        }
    }
}
