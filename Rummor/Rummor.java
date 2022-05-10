package Rummor;
import Cards.Person;
import Cards.Place;
import Cards.Thing;

public class Rummor {
    private Person person;
    private Place place;
    private Thing thing;

    public Rummor(Person person, Place place, Thing thing){
        this.person = person;
        this.place = place;
        this.thing = thing;
    }

    public Person getPerson(){
        return person;
    }

    public Place getPlace(){
        return place;
    }

    public Thing getThing(){
        return thing;
    }

    public String toString(){
        String per;
        String pla;
        String thi;
        if(person == null){
            per = "Unknown";
        }else {
            per = person.toString();
        }

        if(place == null){
            pla = "Unknown";
        }else {
            pla = place.toString();
        }

        if(thing == null){
            thi = "Unknown";
        }else {
            thi = thing.toString();
        }
        return per + " in the " + pla + " with the " + thi + ".";
    }

}
