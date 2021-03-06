package src.battleshipgame.model;

/**
 * Created by yanair on 05.05.17.
 */
public class Battleship {

    enum Ship {
    	 TWO_DECKER(TWO_DECKER_SIZE),
        THREE_DECKER(THREE_DECKER_SIZE), FOUR_DECKER(FOUR_DECKER_SIZE),FIVE_DECKER(FIVE_DECKER_SIZE);

        private int size;
        private String name;

        Ship(int size) {
            this.size = size;
            this.name= "";
        }

        public int getSize() {
            return size;
        }
    }

    enum Deck {
         TWO, THREE, FOUR, FIVE
    }

    private static final int FOUR_DECKER_SIZE  = 4;
    private static final int FIVE_DECKER_SIZE  = 5;
    private static final int TWO_DECKER_SIZE   = 2;
    private static final int THREE_DECKER_SIZE = 3;

    private int shipSize;
    private boolean horizontalDirection = false;
    private int health;
    //private String name;

    Battleship(Ship ship) {
        this.shipSize = ship.getSize();
        this.health = ship.getSize();
     //   this.name = "";
    }

    int getShipSize() {
        return shipSize;
    }
    
   // public void setName(String name) {
    //	this.name=name;
    //}

    void receiveShot() {
        if (isAlive()) {
            health--;
        }
    }
    
    boolean isWounded() {
    	return ((health!=shipSize) && (health > 0));
    }

    boolean isAlive() {
        return health > 0;
    }

    boolean hasHorizontalDirection() {
        return horizontalDirection;
    }

    public void rotate() {
        horizontalDirection = !horizontalDirection;
    }

    @Override
    public String toString() {
        return "(Size " + shipSize + " / Horizontal direction : " + horizontalDirection + ")";
    }

}
