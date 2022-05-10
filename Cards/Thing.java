package Cards;

import java.util.NoSuchElementException;

public class Thing extends Card{
    public enum Value{
        CANDLESTICK,
        DAGGER,
        LEADPIPE,
        REVOLVER,
        ROPE,
        WRENCH
    }
    
    public Value value;

    public Thing(Value value){
        super();
        this.kind = Kind.THING;
        this.value = value;
    }

    public Value getValue(){
        return this.value;
    }

    public String toString(){
        switch(value){
            case CANDLESTICK:
                return "Candlestick";
            case DAGGER:
                return "Dagger";
            case LEADPIPE:
                return "Lead Pipe";
            case REVOLVER:
                return "Revolver";
            case ROPE:
                return "Rope";
            case WRENCH:
                return  "Wrench";
            default:
                throw new NoSuchElementException("Error: Unknown Type of Thing: From Thing.toString()");

        }
    }
}
