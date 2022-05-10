package Cards;

import java.util.NoSuchElementException;

public class Place extends Card {
    public enum Value{
        BALLROOM,
        BILLIARDROOM,
        CONSERVATORY,
        DININGROOM,
        HALL,
        KITCHEN,
        LIBRARY,
        LOUNGE,
        STUDY
    }
    
    public Value value;

    public Place(Value value){
        super();
        this.kind = Kind.PLACE;
        this.value = value;
    }

    public Value getValue(){
        return this.value;
    }

    public String toString(){
        switch(value){
            case BALLROOM:
                return "Ballroom";
            case BILLIARDROOM:
                return "Billard Room";
            case CONSERVATORY:
                return "Conservatory";
            case DININGROOM:
                return "Dining Room";
            case HALL:
                return "Hall";
            case KITCHEN:
                return "Kitchen";
            case LIBRARY:
                return "Library";
            case LOUNGE:
                return "Lounge";
            case STUDY:
                return "Study";
            default:
                throw new NoSuchElementException("Error: Unknown Type of Place: From Place.toString()");

        }
    }
}
