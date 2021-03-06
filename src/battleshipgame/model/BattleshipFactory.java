package src.battleshipgame.model;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Created by yanair on 05.05.17.
 */
public class BattleshipFactory {

    //MUst change BattleshipFactory
	//No more needed
    private static final int TWO_DECKER_QUANTITY        = 1;
    private static final int THREE_DECKER_QUANTITY      = 1;
    private static final int FOUR_DECKER_QUANTITY       = 1;
    private static final int FIVE_DECKER_QUANTITY     = 1;
    //private static final int[] SHIP_POINTS = {50, 100, 100, 250, 350};
    //private static final int[] SHIP_BONUSES = {0, 0, 250, 500, 1000};
    
   // enum Names {
   //     Destroyer, Submarine, Cruiser, Battleship, Carrier
   //}
    
    //MUst change
    //5 add ships 1 per type
    private LinkedList<Battleship> ships = new LinkedList<>();

    {
        addShips(Battleship.Deck.TWO);
        addShips(Battleship.Deck.THREE);
        addShips(Battleship.Deck.FOUR);
        addShips(Battleship.Deck.FIVE);
    }
    //Must change BattleshipFactory.ships
    //Above helps creation
    //adds with List.add() method a ship maybe not needed at all as a method
    //but you need the 5 ships created
    private void addShips(Battleship.Deck deck) {
        
    	int shipQuantity = 1;
    	
        for (int i = 0; i < shipQuantity; i++) {
            ships.add(getBattleship(deck));
        }
    }
    
        
    //Must change BattleshipFactory
    //becomes Invalid Count Exception 
    static void getTotalShipsCount() {
       // return Arrays.stream(new int []{getShipQuantity(Battleship.Deck.FIVE), getShipQuantity(Battleship.Deck.TWO),
         //       getShipQuantity(Battleship.Deck.THREE), getShipQuantity(Battleship.Deck.FOUR)}, 0, 4).sum();
    }
    
    //MUst change BattleshipFactory
    //returns the ships in order
    private Battleship getBattleship(Battleship.Deck deck) {
        switch (deck) {
            case FIVE:
                return new Battleship(Battleship.Ship.FIVE_DECKER);
            case TWO:
                return new Battleship(Battleship.Ship.TWO_DECKER);
            case THREE:
                return new Battleship(Battleship.Ship.THREE_DECKER);
            case FOUR:
                return new Battleship(Battleship.Ship.FOUR_DECKER);
            default:
                return null;
        }
    }
    //MUst not change BattleshipController.initializeNewGame
    //placeShipsRandomly uses it for manual positioning
    //MAY NOT NEEDED IN HE FINAL DESIGN
    public Battleship getNextShip() {
        return ships.pollFirst(); // returns null for empty list
    }

}
