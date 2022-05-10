# AI Clue Solver
*Code by Spencer Lingwall*

---

*To run this code with a Console UI run it from the* [Console Driver](Drivers/ConsoleDriver.java). 

## Purpose
My main goal with this project was to learn very basic boolean solving methods in a fun game of clue. I tried several strategies and the one I came up with here is probably the most effiecient one that I could come up with. 

The strategy is to keep track of every card and determine where every card is rather than focusing on what is in the Envelope. My strategy relies heavily on knowing what every player does not have. It can then deduce what the players and the envelope does have from all the information that was entered.

Unfortunately it keeps track of everything that goes on in the game and so requires a lot of entering in correct values for everything. This however is the advantage of the computer over the average person. Where so much information would normally swamp a person's brain with the complexity, the computer is able to quickly deduce many things that people would normally not be able to see. 

**I must add a disclaimer here**: you are not guaranteed a win because there are always lucky guesses that give one person a lot of information early on in the game. 

This algorithim does however give you a significant advantage over the normal player as you will be able to deduce more information from what is going on than you would normally. That being said anyone could potentially follow the algorithim and come to the same results, however, in game most people cannot calculate that fast.

--- 

## Setup

### Main Program
The [Main program](Drivers/MainProgram.java) is what keeps track of a hashtable of players. In this hashtable is the Envelope which is also a type of player but it has more capabilities as we know a bit more information about the kind of cards that are in the envelope. The envelope always has an ID of -1. Players will have ID's of 0-5 with the player using the program having an ID of 0. 

Because there are not that many players (typically a max of 6) I did not feel it would take too much space, so I implemented a stack of these hashtables to keep track of various game states. I used this so a player could undo an entry if someone messed up and they needed to go back. I found as I was playtesting this that the undo button was invaluable when playing with young children.

The main program also keeps track of the two important methods that control the game flow: `investigate` and `spolier`.

`investigate` takes 4 parameters:

>` public void investigate(int playerMakingRummorID, Boolean[] playerResponses, Rummor rummor, Card cardShown){ ... }`

The `playerMakingRummorID` is basically what it sounds like. It is the ID of the player making the rummor. When this is 0, meaning the player using the program is making the rummor, the `cardShown` parameter should not be `null`. Every other time it is `null`. 

The `playerResponses` is a series of boolean values that players give to the person making the rummor. It always starts with the player to the left of the person making the rummor and ends with the first person who shows a card or the person to the right of the rummor maker if no one showed a card. A `false` is entered for the player if they don't show a card and a `true` if they do. 

The `rummor` parameter is the rummor that was given. If a player showed a card but it was unknown to the player with this program, the rummor will get added to that particular player's profile so we can later deduce what card was shown. 

`spoiler` takes 3 parameters.

>`public void spolier(int playerSpoiling, Knowledge status, Card cardShown){ ... }`

This will take information such as if a player tells you that they have or do not have a card without going through the normal process or making a rummor. 

The `status` parameter is where we determine if the player `HAS` or `HASNOT` the `cardShown`. There is not code to do something with the `DONTKNOW` option because it is not a spoiler that we don't know what someone has. 


### Console Driver
The [Console Driver](Drivers/ConsoleDriver.java) is a console UI for the solver. It consists of a `main` and is where we run the program from. It makes the `MainProgram` object and then collects the needed information from the user to pass it to the various `MainProgram` methods. 

I have separated this from the `MainProgram` in the hopes that I can implement a GUI for the solver without much refactoring.

### Players
There are two types of players: normal players and the Envelope. I did this because the Envelope has very similar properties to the player with a bit more information. I did override the `public void setCardHas(...){}` methods because there are different rules regarding the envelope than that of the players. For example, we know that there will only be one person card in the envelope, so when we know the envelope has a specific person then we also know that it does not have any of the other person cards. 

It was also important to make `Envelope` Extend `Player` because the state stack that allows a user to undo in the `MainProgram` needs to keep track of both the players and the envelope. It was easiest to put them in the same category.

The players have three hashtables in them each keeping track of the Knowledge we have regarding those types of cards: `HAS`, `HASNOT`, and `DONTKNOW`. Originally all cards in each hashtable start as `DONTKNOW` and as information gets added in we change its values to `HAS` or `HASNOT`.

Players also keep track of a list of valid rummors. These are rummors that they responded to and showed a card, however we don't know what card was shown. Using these we can figure out which card they showed if we know that they do not have the other two cards in the rummor. To keep things clean, when we know they have one of the cards in the rummor, that rummor is removed from the list of valid rummors. 

I thought about implementing a much more complicated AI that would be able to take a set of rummors with their known number of cards and deduce from that what cards they have. The issue with this is it would take a large number of rummors to be able to deduce anything. In realistic gameplay, we can figure out what card was shown from a rummor much faster by comparing the rummors to what the player does not have instead of comparing the rummors to each other. There are only a few cases where we would be able to gather more information implementing the more complex AI than what I did and in those cases, the games of clue would need to be extremely long as the algorithim would require minimally 2n rummors where n is the number of cards that a player has which is usually greater than 3. For a typical game we would need to add about 8 rummors to a person before we were able to prove any information from them. A typical game of clue has a total of 16 rummors divided between 4 players giving each player about 4 rummors total. This means we would not be able to get anything from this unless the game was extremely long or the game was played just perfectly for us to be able to determine what it was. In short, its a lot of work for something that will rarely give us an advantage over other players and we are likely to have figured it out the way I already implemented by the time we could have figured it out from the more complicated way rendering the more Complex AI useless.

Sticking with what we know about above, the `checkRummors()` method checks to see if we can deduce what card was shown in a rummor by determining what the player does not have and comparing it to the rummor.

### Cards
There are three Kinds of cards: `PERSON`, `PLACE`, and `THING`. Each kind of card has a set of values that it can be. In the standard game of clue there are 6 Persons, 9 Places, and 6 Things totaling 21 cards. The cards really do nothing but act as keys for the player card hashtables.

### Rummor
The rummor holds 1 of each kind of card. Its main purpose is to easily keep information together and more organized, especially in the Player `validRummors` list which is used to deduce what cards the players actually have. 

---

## Tests

I wrote several Unit tests to verify that the program works and I have ran it several times with the console GUI. Probably the most interesting test is the [Full Game Test](Tests/FullGameTest.java) which is an actual game I played and you can see where the computer clearly has an advantage over what a normal person would have been able to deduce. I did not think it was possible to know what the weapon was until the very end of the game where Julia gave away the answer. After going back through my notes that I took though and watching the program slowly, I was amazed at how much information it was able to figure out just from what was there. 

Sadly I did not win that game because of Julia's lucky guess and excited exclaimation that she didn't have any of those cards either which gave away a lot of information...


