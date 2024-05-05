import java.util.ArrayList;
/**
 *  This class is the main class of the "Lian Yu" application. 
 *  "Lian" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery fight bad guys and escape. That's all. 
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    Item arrow, friend, radio, battery, box, key, rock, wood;
    Room storageRoom;
      
    
    private ArrayList<Item> inventory = new ArrayList<Item>();
    private Room previousRoom;
    private Integer lives;
    private ArrayList<ArrayList<Item>> roomState;
    private ArrayList<ArrayList<Item>> playerState;
    private ArrayList<Room> gameState;
    private ArrayList<Integer> life;
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
        lives = 3;

        roomState = new ArrayList<>();
        playerState = new ArrayList<>();
        gameState = new ArrayList<Room>();
        life = new ArrayList<>();

        
        


        
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room start, bay, cave, ds, woods, base, hiddenRoom, graveyard, landmine, ship;
        // create the rooms
        start = new Room("You are on the beach of Lian Yu where your ship has wrecked");
        base = new Room("in a military base functioning as a prison");
        cave = new Room("in a cave that Yao Fei is hiding in");
        ds = new Room("you met death stroke and he will kill you");
        woods = new Room("in the woods");
        bay = new Room("at the Island's bay");
        hiddenRoom = new Room("in a hidden room just outside the military base");
        graveyard = new Room("outside the base by the graveyard");
        landmine = new Room("You stepped in a landmine you are dead now!!!");
        storageRoom = new Room(" in a room where they keep the weapons");
        ship = new Room("You are on the ship heading back home");

        arrow = new Item("arrow");
        friend = new Item("friend");
        radio = new Item("radio");
        battery = new Item("battery");
        box = new Item("box");
        key = new Item("key");
        rock = new Item("rock");
        wood = new Item("wood");

        
        // initialise room exits
        start.setExit("east", cave);
        start.setExit("north", base);
        start.setExit("west", ds);
        start.setName("start");

        base.setExit("north", hiddenRoom);
        base.setExit("east", storageRoom);
        base.setExit("west", landmine);
        base.setExit("south", start);
        base.addItem(radio);
        base.setName("base");

        cave.setExit("west", start);
        cave.setExit("north", landmine);
        cave.addItem(friend);
        cave.setName("cave");

        ds.setExit("east", start);
        ds.setExit("north", woods);
        ds.setName("ds");

        woods.setExit("north", bay);
        woods.setExit("south", ds);
        woods.setExit("east", landmine);
        woods.addItem(wood);
        woods.setName("woods");


        bay.setExit("east", hiddenRoom);
        bay.setExit("south", woods);
        bay.setName("bay");
        bay.setExit("north", ship);

        ship.lockRoom();

        hiddenRoom.setExit("south", base);
        hiddenRoom.setExit("east", graveyard);
        hiddenRoom.setExit("west", bay);
        hiddenRoom.addItem(box);
        hiddenRoom.setName("hiddenRoom");

        graveyard.setExit("west", hiddenRoom);
        graveyard.setExit("south", landmine);
        graveyard.addItem(rock);
        graveyard.setName("graveyard");

        storageRoom.setExit("north", landmine);
        storageRoom.setExit("south", landmine);
        storageRoom.setExit("west", base);
        storageRoom.addItem(arrow);
        storageRoom.lockRoom();
        storageRoom.setName("storageRoom");

        currentRoom = start;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to Lian Yu!");
        System.out.println("Lian Yu is a new, incredibly boring adventure game.");
        System.out.println("Type 'help' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    public boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        if(command.isUnknown()) {
            System.out.println("I don't know what you mean...");
            return false;
        }

        String commandWord = command.getCommandWord();
        if (commandWord.equals("help")) {
            printHelp();
        }
        else if (commandWord.equals("go")) {
            wantToQuit = goRoom(command);
        }
        else if (commandWord.equals("quit")) {
            wantToQuit = quit(command);
        }
        else if (commandWord.equals("inventory")) {
            printInventory();
        

        }
        
        else if (commandWord.equals("take")) {
            takeItem(command);

        }
        else if (commandWord.equals("place")) {
            placeItem(command);

        }
        else if (commandWord.equals("back")) 
        {
            goBack(command);
        }
        // else command not recognised.
        return wantToQuit;
    }

    private void printInventory()
    {
        String output = "";
        for (int i = 0; i<inventory.size() ;i++ ) {
            output += inventory.get(i).getDescription() + " ";
            
        }
        System.out.println("My inventory: ");
        System.out.println(output);
    }


    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("the shores of a chinese island.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }







    /** 
     * Try to in to one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private boolean goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return false;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);
        
        // if(nextRoom == storageRoom && direction.equals("east") && !(inventory.contains(key)))
            // {
                // System.out.println("You need a key to enter that room. Find it!!");
                // return false;
            // }

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
       
        else 
        {

        if(nextRoom.lockStatus() == true)
        {
            System.out.println("You need a key to enter that room. Find it!!");
            return false;
        } 

        if(nextRoom.getName().equals("storageRoom") && direction.equals("east") && (inventory.contains(key)))
        {
        storageRoom.unlockRoom();
         return false;
        }

        // if(nextRoom.getName().equals("base") && direction.equals("north") && !(inventory.contains(friend)))
        // {
            // System.out.println("This place looks really hard to get into. Perhaps maybe someone can help you break in?");
            // return false;
        // }
            playerState.add(new ArrayList<>(inventory));
            roomState.add(new ArrayList<> (currentRoom.getItemsList()));
            gameState.add(currentRoom);
            life.add(lives);

            previousRoom = currentRoom;
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
            
            



            if(currentRoom.getName().equals("ds") && !(inventory.contains(arrow)))
            {
                lives -= 1;

                System.out.println("DeathStroke killed you, you came unarmed. You're dead now Try again!!");
                
                System.out.println("You got " + lives + " lives left.");
                if(lives<1)
                {
                return true;
                }
                else
                {                
                return false;       
                }
            }

            if (currentRoom.getName().equals("landmine")) 
            {   
                lives -= 1;
                
                System.out.println("You stepped in a landmine, you're dead now Try again!!");
                
                System.out.println("You got" + lives + " lives left.");
                if(lives<1)
                {
                return true;
                }
                else
                {                
                return false;       
                }
            }

            if(currentRoom.getName().equals("ds") && (inventory.contains(arrow)))
            {
                System.out.println("You killed DeathStroke. He dropped the battery, Nice job!!");
                currentRoom.addItem(battery);
                return false;
            }
            
            if(currentRoom.getName().equals("hiddenRoom") && !(inventory.contains(rock)))
            {
                System.out.println("There is box, it looks like it has something interesting in it. Maybe if you had something to break it?");
                return false;
            }
            if(currentRoom.getName().equals("hiddenRoom") && (inventory.contains(rock)))
            {
                currentRoom.addItem(key);
                System.out.println("Nice Job the rock broke the box there was a key inside");
                return false;
            }
            if(currentRoom.getName().equals("storageRoom") && currentRoom.items.contains(arrow))
            {
                System.out.println("What a cool bow and arrow maybe that can help you beat DeathStroke");
                return false;
            }
            if(currentRoom.getName().equals("ship"))
            {
                System.out.println("Congratualions you made it and escaped the Island. You are a real survivor");
                return true;
            }


        }
        return false;
    }

 

    private void takeItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to take...
            System.out.println("Get what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = currentRoom.getItem(item);

        if (newItem == null) {
            System.out.println("There is no such item here!");
        }
        else {
            playerState.add(new ArrayList<>(inventory));
            roomState.add(new ArrayList<> (currentRoom.getItemsList()));
            gameState.add(currentRoom);
            life.add(lives);



            inventory.add(newItem);
            currentRoom.removeItem(item);
            System.out.println("You now have " + item + " with you");
            if(inventory.contains(radio) && inventory.contains(battery))
            {
                System.out.println("Finally you called for resque, they will send a ship to pick you up at north of the bay");
                // bay.setExit("north", ship);
            }

        }
    }

    private void placeItem(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know what to place...
            System.out.println("Place what?");
            return;
        }

        String item = command.getSecondWord();

        // Try to leave current room.
        Item newItem = null;
        int index = 0;
        for(int i = 0; i<inventory.size();i++)
        {
            if (inventory.get(i).getDescription().equals(item)) {
                newItem = inventory.get(i);
                index = i;
            }
        }

        if (newItem == null) {
            System.out.println("There is no such item in your inventory!");
        }
        else {
            playerState.add(new ArrayList<>(inventory));
            roomState.add(new ArrayList<> (currentRoom.getItemsList()));
            gameState.add(currentRoom);
            life.add(lives);


            inventory.remove(index);
            currentRoom.addItem(new Item(item));
            System.out.println(item + "has been placed");
        }
    }


    private void goBack(Command command)
    {
        


        if(!command.hasSecondWord()) 
        {
            if((gameState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            currentRoom = gameState.get((gameState.size()-1));
            System.out.println(currentRoom.getLongDescription());
            gameState.remove((gameState.size()-1));
            }

            if((playerState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            inventory = playerState.get(playerState.size()-1);
            printInventory();
    

            playerState.remove(playerState.size()-1);
            }


            if((roomState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
               currentRoom.setItemsList(roomState.get(roomState.size()-1));
                roomState.remove(roomState.size()-1); 
            }

            if((life.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
              lives = life.get((life.size()-1));
            life.remove((life.size()-1));
            }
            
        }
             
        

        else
        {
        String backAgain = command.getSecondWord();
        if (!backAgain.equals("back"))
        {
            System.out.println("Back what?");
        }
        if(backAgain.equals("back"))
        {
            if((gameState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            currentRoom = gameState.get((gameState.size()-1));
            gameState.remove((gameState.size()-1));
            }

            if((playerState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            inventory = playerState.get(playerState.size()-1);
    

            playerState.remove(playerState.size()-1);
            }


            if((roomState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
               currentRoom.setItemsList(roomState.get(roomState.size()-1));
                roomState.remove(roomState.size()-1); 
            }
            if((gameState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            currentRoom = gameState.get((gameState.size()-1));
            System.out.println(currentRoom.getLongDescription());
            gameState.remove((gameState.size()-1));
            }

            if((playerState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
            inventory = playerState.get(playerState.size()-1);
            printInventory();
    

            playerState.remove(playerState.size()-1);
            }


            if((roomState.size()-1)<0)
            {
                System.out.println("There is no going back from here!");
            }
            else
            {
               currentRoom.setItemsList(roomState.get(roomState.size()-1));
                roomState.remove(roomState.size()-1); 
            }
        }
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
