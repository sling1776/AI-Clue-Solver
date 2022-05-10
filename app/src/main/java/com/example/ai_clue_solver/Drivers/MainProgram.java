package com.example.ai_clue_solver.Drivers;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.ai_clue_solver.Cards.*;
import com.example.ai_clue_solver.Players.*;
import com.example.ai_clue_solver.Rummor.Rummor;
import com.example.ai_clue_solver.Players.Player.Knowledge;

public class MainProgram {
    ArrayList<Card> cards = new ArrayList<>();
    ArrayList<Person> personCards = new ArrayList<>();
    ArrayList<Place> placeCards = new ArrayList<>();
    ArrayList<Thing> thingCards = new ArrayList<>();


    public PlayerStateStack stack;
    HashMap<Integer,Player> players;

    
    public MainProgram(Integer[] playerCardCounts, String[] playerNames){
        initializeCardLists();
        try {
            players = makePlayersSet(playerCardCounts, playerNames);
        } catch (Exception e) {
            System.out.println("Defaulting to card count array size for number of players. Removing all playerNames- Default: 'Player <id>'");
            players = makePlayersSet(playerCardCounts);
        }
        stack = new PlayerStateStack(players);
    }

    public MainProgram(Integer[] playerCardCounts){
        initializeCardLists();
        players = makePlayersSet(playerCardCounts);
        stack = new PlayerStateStack(players);
    }
    
    public Player getPlayer(int id)
    {
        return players.get(id);
    }

    public HashMap<Integer, Player> getPlayers(){
        return players;
    }

    public void addToStack(){
        stack.addToStack(players);
        players = stack.peekTop();
    }

    public void undo(){
        stack.popTop();
        players = stack.peekTop();
    }

    public ArrayList<Card> getCards(){
        return cards;
    }
    public ArrayList<Person> getPersons(){
        return personCards;
    }
    public ArrayList<Place> getPlaces(){
        return placeCards;
    }
    public ArrayList<Thing> getThings(){
        return thingCards;
    }
    
    public void addCardToPlayer(Card c, int playerId){
        Player p = players.get(playerId);
        p.setCardHas(c);
        for(Player player: players.values()){
            if(player.getID() != playerId){
                player.setCardHasNot(c);
            }
        }
    }
    public void addCardToPlayer(Person.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHas(v);
        for(Player player: players.values()){
            if(player.getID() != playerId){
                player.setCardHasNot(v);
            }
        }
    }
    public void addCardToPlayer(Place.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHas(v);
        for(Player player: players.values()){
            if(player.getID() != playerId){
                player.setCardHasNot(v);
            }
        }
    }
    public void addCardToPlayer(Thing.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHas(v);
        for(Player player: players.values()){
            if(player.getID() != playerId){
                player.setCardHasNot(v);
            }
        }
    }
    public void addCardsToPlayer(Card[] cardList, int playerId){
        Player p = players.get(playerId);
        for(Card c: cardList){
            p.setCardHas(c);
            for(Player player: players.values()){
                if(player.getID() != playerId){
                    player.setCardHasNot(c);
                }
            }
        }
        
    }


    public void removeCardFromPlayer(Card c, int playerId){
        Player p = players.get(playerId);
        p.setCardHasNot(c);
    }
    public void removeCardFromPlayer(Person.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHasNot(v);
    }
    public void removeCardFromPlayer(Place.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHasNot(v);
    }
    public void removeCardFromPlayer(Thing.Value v, int playerId){
        Player p = players.get(playerId);
        p.setCardHasNot(v);
    }
    public void removeCardsFromPlayer(Card[] cardList, int playerId){
        Player p = players.get(playerId);
        for(Card c: cardList){
            p.setCardHasNot(c);
        }
        
    }

    private void initializeCardLists(){
        
        for(Person.Value v: Person.Value.values()){
            cards.add(new Person(v));
            personCards.add(new Person(v));
        }
        for(Place.Value v: Place.Value.values()){
            cards.add(new Place(v));
            placeCards.add(new Place(v));
        }
        for(Thing.Value v: Thing.Value.values()){
            cards.add(new Thing(v));
            thingCards.add(new Thing(v));
        }
    }

    public static void main(String[] args) {

    }

    public static HashMap<Integer, Player> makePlayersSet(Integer[] cardCounts, String[] names) throws Exception{
        if(cardCounts.length != names.length){
            throw new Exception("CardCount and Names arrays must be same length: from MakePlayersSet in MainProgram");
        }


        HashMap<Integer, Player> h = new HashMap<>();
        Envelope e = new Envelope();
        h.put(e.getID(), e);
        for(int i = 0 ; i < cardCounts.length ; i++){
            h.put(i, new Player(names[i], i, cardCounts[i]));
        }

        return h;
    }

    public static HashMap<Integer, Player> makePlayersSet(Integer[] cardCounts){
        
        HashMap<Integer, Player> h = new HashMap<>();
        Envelope e = new Envelope();
        h.put(e.getID(), e);
        for(int i = 0 ; i < cardCounts.length ; i++){
            h.put(i, new Player(i, cardCounts[i]));
        }
        return h;
    }

    public void investigate(int playerMakingRummorID, Boolean[] playerResponses, Rummor rummor, Card cardShown){
        addToStack();
        int questionedPlayer = playerMakingRummorID;
        
        for(Boolean response: playerResponses){
            questionedPlayer++;
            if(questionedPlayer >= players.size()-1){ //not including envelope...
                questionedPlayer = 0;
            }
            if(response){
                if(cardShown != null){
                    addCardToPlayer(cardShown, questionedPlayer);
                }else{
                    players.get(questionedPlayer).addRummor(rummor);
                }
            }else{
                removeCardFromPlayer(rummor.getPerson(), questionedPlayer);
                removeCardFromPlayer(rummor.getPlace(), questionedPlayer);
                removeCardFromPlayer(rummor.getThing(), questionedPlayer);
            }
        }
        


        try {
            simplifyPlayers();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //check for hidden solves...
        if(getPlayer(-1).getCardStatus(rummor.getPerson()) == Knowledge.DONTKNOW){
            boolean personSolve = true;
            for(Player p : getPlayers().values()){
                if(p.getID() != -1) {
                    if(p.getCardStatus(rummor.getPerson()) == Knowledge.DONTKNOW)
                    {
                        personSolve = false;
                    }
                    else if(p.getCardStatus(rummor.getPerson()) == Knowledge.HAS){
                        personSolve = false;
                        getPlayer(-1).setCardHasNot(rummor.getPerson());
                    }
                }
            }
            if(personSolve){
                getPlayer(-1).setCardHas(rummor.getPerson());
            }
        }

        if(getPlayer(-1).getCardStatus(rummor.getPlace()) == Knowledge.DONTKNOW){
            boolean placeSolve = true;
            for(Player p : getPlayers().values()){
                if(p.getID() != -1){
                    if(p.getCardStatus(rummor.getPlace()) == Knowledge.DONTKNOW)
                    {
                        placeSolve = false;
                    }
                    else if(p.getCardStatus(rummor.getPlace()) == Knowledge.HAS){
                        placeSolve = false;
                        getPlayer(-1).setCardHasNot(rummor.getPlace());
                    }
                }
            }
            if(placeSolve){
                getPlayer(-1).setCardHas(rummor.getPlace());
            }
        }

        if(getPlayer(-1).getCardStatus(rummor.getThing()) == Knowledge.DONTKNOW){
            boolean thingSolve = true;
            for(Player p : getPlayers().values()){
                if(p.getID() != -1) 
                {
                    if(p.getCardStatus(rummor.getThing()) == Knowledge.DONTKNOW )
                    {
                        thingSolve = false;
                    }
                    else if(p.getCardStatus(rummor.getThing()) == Knowledge.HAS){
                        thingSolve = false;
                        getPlayer(-1).setCardHasNot(rummor.getThing());
                    }
                }
            }
            if(thingSolve){
                getPlayer(-1).setCardHas(rummor.getThing());
            }
        }
    }

    public void spolier(int playerSpoiling, Knowledge status, Card cardShown){
        addToStack();
        switch(status){
            case HAS:
                addCardToPlayer(cardShown, playerSpoiling);
                break;
            case HASNOT:
                removeCardFromPlayer(cardShown, playerSpoiling);
                break;
            default:
                break;
        }
        try {
            simplifyPlayers();
        } catch (Exception e) {
            System.out.println(e.toString());
        }

        if(getPlayer(-1).getCardStatus(cardShown) == Knowledge.DONTKNOW){
            boolean personSolve = true;
            for(Player p : getPlayers().values()){
                if(p.getID() != -1) {
                    if(p.getCardStatus(cardShown) == Knowledge.DONTKNOW)
                    {
                        personSolve = false;
                    }
                    else if(p.getCardStatus(cardShown) == Knowledge.HAS){
                        personSolve = false;
                        getPlayer(-1).setCardHasNot(cardShown);
                    }
                } 
            }
            if(personSolve){
                getPlayer(-1).setCardHas(cardShown);
            }
        }

    }

    private void simplifyPlayers() throws Exception {
        Boolean done = false;
        while(!done){ //Loop As long as there was something changed in the players...
            done = true;
            ArrayList<ArrayList<Card>> playerSolves = new ArrayList<>();
            for(Integer i: players.keySet()){
                if(i != -1){
                    ArrayList<Card> c = players.get(i).checkRummors();
                    playerSolves.add(c);
    
                }
            }
            for (int i = 0; i < playerSolves.size(); i++) {//for every solve found
                for(Card c : playerSolves.get(i)){              //for card in the solve found
                    for(Integer j: players.keySet()){               //for every player  
                        if(i != j){
                            if(players.get(j).getCardStatus(c)== Knowledge.HAS){
                                throw new Exception("AI error: Card requested to be set to has on multiple people.");
                            }
                            players.get(j).setCardHasNot(c);            //set that card to has not if it isn't the player in question. 
                            done = false;                               //Signal to loop through the process again in case this would trigger another rummor in another player to solve.
                        }
                    }
                }
            }
            ((Envelope)getPlayer(-1)).foundSoultion();
        }
        
    }
}
