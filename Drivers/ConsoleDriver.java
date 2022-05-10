package Drivers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

import Cards.*;
import Rummor.Rummor;
import Players.*;
import Players.Player.Knowledge;


public class ConsoleDriver {

    public static int getInteger(Scanner scanner, String message, int lower, int upper){
        int num = 0;
        boolean goodinput = false;
        while(!goodinput){
            System.out.println(message);
            String input = scanner.nextLine();
            try{
                num = Integer.parseInt(input);
                if(num > upper || num < lower) throw new Exception("Number Not in Bounds"); 
                goodinput = true;
            }catch(Exception e){
                System.out.println("Error:" + e.toString() + " Please Enter a Valid Number.");
                goodinput = false;
            }
        }
        return num;
    }

    public static <E> void displayList(ArrayList<E> list, HashSet<Integer> selected){
        for (int i = 0; i < list.size(); i++) {
            System.out.print("" + (i+1) + ") " + list.get(i));
            if(selected.contains(i)){
                System.out.print(" (Previously Selected)");
            }
            System.out.print("\n");
        }
        System.out.println();
    }

    public static <E> void displayList(ArrayList<E> list){
        for (int i = 0; i < list.size(); i++) {
            System.out.print("" + (i+1) + ") " + list.get(i));
            System.out.print("\n");
        }
        System.out.println();
    }

    public static Rummor makeARummor(Scanner scanner, MainProgram mp){
        Rummor rummor = new Rummor(null, null, null);
        ArrayList<Person> personCards = mp.getPersons();
        ArrayList<Place> placeCards = mp.getPlaces();
        ArrayList<Thing> thingCards = mp.getThings();

        boolean done = false;
        while(!done){
            System.out.println("What was the rumor? ");

            displayList(personCards);
            int perChoicenum = getInteger(scanner, "Select a Person", 1, personCards.size())-1;
            Person personchoice = personCards.get(perChoicenum);
            
            displayList(placeCards);
            int plaChoicenum = getInteger(scanner, "Select a Place:", 1, placeCards.size())-1;
            Place placechoice = placeCards.get(plaChoicenum); 
    
            displayList(thingCards);
            int thiChoicenum = getInteger(scanner, "Select a Thing", 1, thingCards.size())-1;
            Thing thingchoice = thingCards.get(thiChoicenum);

            rummor = new Rummor(personchoice, placechoice, thingchoice);
            System.out.println();
            System.out.println("Here is the rummor: " + rummor.toString());
            System.out.println();
            System.out.println("Is this Correct? (Y/n)");
            String in = scanner.nextLine();
            if(in.equals("Y") || in.equals("y")){
                done = true;
            }
            else{
                done = false;
            }
        }
        return rummor;
    }


    public static void investigate(Rummor rummor, MainProgram mp, Boolean personalQuery, Scanner scanner){
        int rummorMaker;
        if(personalQuery){
            rummorMaker = 0;
        }
        else{
            System.out.println("Who made the rummor? ");
            for(Integer i : mp.getPlayers().keySet()){
                if(i!= -1){
                    System.out.println("" + (i+1) + ") " + mp.getPlayer(i).getName());
                }
            }
            System.out.println();
            rummorMaker = getInteger(scanner, "Select a player", 1, mp.getPlayers().size() - 1) - 1;
        }


        int inquiry = rummorMaker;
        inquiry++;
        if(inquiry >= mp.getPlayers().size()-1) inquiry = 0;
        ArrayList<Boolean> cardShown = new ArrayList<>();
        int shownCard = -1;
        while (rummorMaker != inquiry){
            if(inquiry == 0){
                if(mp.getPlayer(0).canRefute(rummor)){
                    System.out.println("You showed a card.");
                    cardShown.add(true);
                    break;
                }
                else{
                    System.out.println("You didn't have a card to show...");
                    cardShown.add(false);
                }
            }
            else{
                if(mp.getPlayer(inquiry).canRefute(rummor)){
                    System.out.println(mp.getPlayer(inquiry).getName() +" should have shown a card.");
                    cardShown.add(true);
                    break;
                }else{
                    System.out.println("Did " + mp.getPlayer(inquiry).getName() +" show a card? (Y/n)");
                    String in = scanner.nextLine();
                    if(in.equals("Y") || in.equals("y")){
                        cardShown.add(true);
                        if(personalQuery){
                            System.out.println("What card did you get shown? ");
    
                            //Display all cards
                            displayList(mp.getCards());
                            //make a selection
                            shownCard = getInteger(scanner, "Select a Card", 1, mp.getCards().size()) -1;
                            
                            //verify selection
                            System.out.println("You were shown: " +  mp.getCards().get(shownCard).toString() );

                        }
                        break;
                    }
                    else{
                        
                        cardShown.add(false);
                    }
                }
            }

            inquiry++;
            if(inquiry >= mp.getPlayers().size()-1) inquiry = 0;
        }
        
        Card c =null;
        if(shownCard != -1){
            c = mp.getCards().get(shownCard);
        }
        mp.investigate(rummorMaker, cardShown.toArray(new Boolean[cardShown.size()]), rummor, c);
    }

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        //Get Number of Players
        int numPlayers = getInteger(scanner, "How Many Players? (2-6)", 2, 6);



        //Get Names of players for Ease of Play
        ArrayList<String> names = new ArrayList<>();
        for(int i = 0; i < numPlayers; i++){
            if(i == 0){
                System.out.println("Enter Your name: ");
            }else{
                System.out.println("Enter Player " + (i+1) + "'s name: ");
            }
            String input = scanner.nextLine();
            if(input.equals("")){
                names.add("Player " + (i +1));
            }else{
                names.add(input);
            }
        }



        //Get number of cards for each player
        ArrayList<Integer> numCards = new ArrayList<>();
        switch(numPlayers){
            case 2: //all players have 9 cards
                for(int i = 0; i < numPlayers; i++){
                    numCards.add(9);
                }
            break; 

            case 3: // all players have 6 cards
                for(int i = 0; i < numPlayers; i++){
                    numCards.add(6);
                }
            break; 

            case 4://2 players have 4. 2 have 5
                HashSet<Integer> selected = new HashSet<>();
                System.out.println("Two Players have 5 cards. Which are they?");
                System.out.println("List of Players: ");

                displayList(names, selected);
                selected.add(getInteger(scanner, "Select One Player: ", 1, numPlayers) - 1);

                System.out.println("List of Players: ");
                displayList(names, selected);
                int selected2 = getInteger(scanner, "Select Second Player: ", 1, numPlayers) - 1;
                while(selected.contains(selected2)){
                    System.out.println("Cannot Select Same Player Twice.");
                    selected2 = getInteger(scanner, "Select Second Player: ", 1, numPlayers) - 1;
                }
                selected.add(selected2);

                for(int i = 0; i < numPlayers; i++){
                    if(selected.contains((Integer)i)){
                        numCards.add(5);
                    }else{
                        numCards.add(4);
                    }
                    
                }
            break;

            case 5:
                //three players have 4. 2 have 3
                HashSet<Integer> selections = new HashSet<>();
                System.out.println("Two Players have 3 cards. Which are they?");
                System.out.println("List of Players: ");
                displayList(names, selections);
                int s1 = getInteger(scanner, "Select One Player: ", 1, numPlayers) - 1;
                selections.add(s1);

                System.out.println("List of Players: ");
                displayList(names,selections);
                int s2 = getInteger(scanner, "Select Second Player: ", 1, numPlayers) - 1;
                while(selections.contains(s2)){
                    System.out.println("Cannot Select Same Player Twice.");
                    s2 = getInteger(scanner, "Select Second Player: ", 1, numPlayers) - 1;
                }
                selections.add(s2);


                for(int i = 0; i < numPlayers; i++){
                    if(selections.contains(i)){
                        numCards.add(3);
                    }else{
                        numCards.add(4);
                    }
                    
                }
            break;

            case 6: //even division of 3 cards each
                for(int i = 0; i < numPlayers; i++){
                    numCards.add(3);
                }
            break; 

            default:
                scanner.close();
                System.out.println("Error: Invalid number of Players.");
                throw new Exception("Error Invalid number of Players");
        }



        //Create Main Program
        MainProgram mainProgram = new MainProgram(numCards.toArray(new Integer[numCards.size()]), names.toArray(new String[names.size()])); 
        
        //Get cards of primary user
        HashSet<Integer> selectedCards = new HashSet<>();
        System.out.println();
        System.out.println("What cards do you have?");
        for(int i = 0 ; i < mainProgram.getPlayer(0).getNumberOfCards(); i++){
            displayList(mainProgram.getCards(), selectedCards);
            int x = getInteger(scanner, "Select a Card: ", 1, mainProgram.getCards().size()) - 1;
            while(selectedCards.contains((Integer)x)){
                System.out.println("Cannot choose same card twice.");
                x = getInteger(scanner, "Select a Card: ", 1, mainProgram.getCards().size()) - 1;
            }
            selectedCards.add((Integer)x);
        }
        for(Integer c: selectedCards){
            mainProgram.addCardToPlayer(mainProgram.getCards().get(c), 0);
        }


        //gameloop
            //select Choice: 
                //other personRumor
                //personalRumor
                //spoiler

        boolean done = false;
        while(!done){
            System.out.println();
            System.out.println("Main Menu");
            System.out.println("1) Personal Rumor");
            System.out.println("2) Other Person Rumor");
            System.out.println("3) Spoiler");
            System.out.println("4) Display Player Knowledge");
            System.out.println("5) Undo Last Entry");
            System.out.println("6) Exit");
            System.out.println();
            int choice = getInteger(scanner, "Make a selection", 1, 6);

            switch(choice){
                case 1:
                    //offer a suggestion
                    System.out.println("Here is a suggestion: " + ((Envelope)mainProgram.getPlayer(-1)).makeSuggestion().toString());
                    //make a rumor
                    Rummor rummor = makeARummor(scanner, mainProgram);
                    //ask which player showed a card.
                        //implement an undo button to go back.
                    investigate(rummor, mainProgram, true, scanner);
                break;
                case 2:
                    //make a rumor
                    Rummor rummor2 = makeARummor(scanner, mainProgram);
                    //ask which player made the suggestion
                    investigate(rummor2, mainProgram, false, scanner);
                    //ask which player showed a card.
                break;
                case 3:
                    System.out.println("Which Player unwittingly gave you information?");
                    for(Integer i : mainProgram.getPlayers().keySet()){
                        if(i != -1){
                            System.out.println("" + (i+1) + ") " + mainProgram.getPlayer(i).getName());
                        }
                    }
                    System.out.println();
                    int spolingPlayerid = getInteger(scanner, "Select a Player", 1, mainProgram.getPlayers().size() -1) -1;

                    System.out.println();
                    System.out.println("Which card did they talk about?");
                    displayList(mainProgram.getCards());
                    int spoiledCardid = getInteger(scanner, "Select a Card", 1, mainProgram.getCards().size()) -1;

                    System.out.println("Do they have or not have the card?");
                    System.out.println("1) Have");
                    System.out.println("2) Not Have");
                    int stat = getInteger(scanner, "Select what they gave away:", 1, 2);


                    mainProgram.spolier(spolingPlayerid, stat == 1? Knowledge.HAS: Knowledge.HASNOT, mainProgram.getCards().get(spoiledCardid));
                    System.out.println("Heh heh heh.");
                break;
                case 4:
                    System.out.println("Which Player do you want to see information about?");
                    for(Integer i : mainProgram.getPlayers().keySet()){
                        System.out.println("" + (i+1) + ") " + mainProgram.getPlayer(i).getName());
                    }
                    System.out.println();
                    int playerid = getInteger(scanner, "Select a Player", 0, mainProgram.getPlayers().size()) -1;
                    System.out.println(mainProgram.getPlayer(playerid).toString());
                    

                break;
                case 5:
                    mainProgram.undo();
                break;
                case 6:
                    done = true;
                break;
                default:
                    System.out.println("Error: Unknown Entry.");

            }


            if(((Envelope)mainProgram.getPlayer(-1)).foundSoultion()){
                System.out.println("Found The Solution!");
                System.out.println(((Envelope)mainProgram.getPlayer(-1)).getSolution().toString());
            }
        }

        scanner.close();
            
    }
}
