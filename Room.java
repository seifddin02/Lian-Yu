import java.util.Set;
import java.util.HashMap;
import java.util.ArrayList;
import javax.swing.ImageIcon;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    ArrayList<Item> items;
    private boolean isLocked;
    private String name;
    ImageIcon image;
    

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        items = new ArrayList<Item>();
        isLocked = false;
        
    }
    
    public void setName(String nom)
    {
        this.name = nom;
    }

    
    public boolean lockStatus()
    {
        return isLocked;
    }
    
    public void lockRoom()
    {
        isLocked = true;
    }
    
    public void unlockRoom()
    {
        isLocked = false;
    }

    public ArrayList getItemsList()
    {
        return items;
    }

    public void setItemsList(ArrayList<Item> itemslist)
    {
        this.items = new ArrayList<>(itemslist);
    }
    
    
    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }
    
    public void setImage(ImageIcon pic) 
    {
        image = pic;
    }
    
    public ImageIcon getPic()
    {
        return image;
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    public String getName()
    {
        return name;
    }

    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " " + exit;
        }
        returnString += "\nItems in the place: \n";
        returnString += getRoomItems();


        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    

    // Get items from the room
    public Item getItem(int index)
    {
        return items.get(index);
    }

    // Get items from the room
    public Item getItem(String itemName)
    {
    
        for (int i = 0; i<items.size() ;i++ )
        {
            if(items.get(i).getDescription().equals(itemName))
            {
                return items.get(i);
            }

        }
        return null;
    }

    // remove items from the room
    public void removeItem(String itemName)
    {
    
        for (int i = 0; i<items.size() ;i++ )
        {
            if(items.get(i).getDescription().equals(itemName))
            {
                items.remove(i);
            }

        }
        
    }


    //add items into a room
    public void addItem(Item newItem)
    {
        items.add(newItem);
    }

    /*
    * Get the names of the items in the room
    */

    public String getRoomItems()
    {
        String output = "";
        for (int i = 0; i<items.size() ;i++ ) {
            output += items.get(i).getDescription() + " ";
            
        }
        return output;
    }
}

