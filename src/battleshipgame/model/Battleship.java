package src.battleshipgame.model;


/**
 * This is a class that contains all the basic information about the ships that the game uses.
 * It gives all the basic tool through public getters and setters to the other classes to construxt the game.
 * @author Athanasios Delis on 07.03.2021.
 *
 */
public class Battleship {

    /**
     * The length of the ship.
     */
    private int shipSize;    
    /**
     * The health of the ship. Practically same size with length.
     */
    private int health;
    /**
     * The points given the player that inflicted the damage, augmenting its score.
     */
    private int hitPoints;
    /**
     * The bonus points given the player that sunk the ship, augmenting its score.
     */
    private int shunkBonus;
    /**
     * The name of the ship.
     */
    private String name;
    /**
     * The direction of the ship. Horizontal is the default true.
     */
    private boolean horizontalDirection = true;

    /**
     * This is a default constructor made for the class
     */
    Battleship() {
        this.shipSize = -1;
        this.health = -1;
        this.hitPoints = -1;
        this.shunkBonus = -1;
        this.name = "";
    }
    
    /**
     * This is a constructor made for the class, that sets all the basic parameters analyzed above.
     * Used to initialize the ships in the Board of each player.
     * @param size ,length of the ship.
     * @param health ,health of the ship.
     * @param point ,points given the player that inflicted the damage.
     * @param bonus ,bonus points given the player that sunk the ship.
     * @param name ,name of the ship.
     */
    Battleship(int size, int health, int point, int bonus, String name) {
        this.shipSize = size;
        this.health = health;
        this.hitPoints = point;
        this.shunkBonus = bonus;
        this.name = name;
    }

    /**
     * Getter for length.
     * @return 
     * length.
     */
    public int getShipSize() {
        return shipSize;
    }
    
    /**
     * Getter for health.
     * @return 
     * health.
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * Getter for points.
     * @return 
     * points.
     */
    public int getPoints() {
        return hitPoints;
    }
    
    /**
     * Getter for bonus.
     * @return 
     * bonus.
     */
    public int getBonus() {
        return shunkBonus;
    }
    
    /**
     * Getter for name.
     * @return 
     * name.
     */
    public String getName() {
        return name;
    }
    
    /**
     * Setter for sets ship's size.
     * @param shipSize 
     * sets ship's size.
     */
    public void setShipSize (int shipSize) {
    	this.shipSize=shipSize;
    }
    
    /**
     * Setter for sets ship's health.
     * @param health 
     * sets ship's health.
     */
    public void setHealth (int health) {
    	this.health=health;
    }
    
    /**
     * Setter for sets ship's points.
     * @param hitPoints 
     * sets ship's points.
     */
    public void setPoints (int hitPoints) {
    	this.hitPoints=hitPoints;
    }
    
    /**
     * Setter for the bonus of the ship.
     * @param shunkBonus 
     * sets the bonus of the ship.
     */
    public void setBonus (int shunkBonus) {
    	this.shunkBonus=shunkBonus;
    }
    
    /**
     * Setter for sets the name of the ship.
     * @param name  
     * sets the name of the ship.
     */
    public void setName(String name) {
    	this.name=name;
    }
    
    /**
     * Setter for direction of the ship.
     * @return 
     * direction of the ship.
     */
    boolean hasHorizontalDirection() {
        return horizontalDirection;
    }

    /**
     * Changes the direction of the ship.
     */
    public void rotate() {
        horizontalDirection = !horizontalDirection;
    }
        
    /**
     * Renews the ship's health after a shot.
     */
    public void receiveShot() {
        if (isAlive()) {
            health--;
        }
    }
    
    /**
     * Inspects if ship is wounded.
     * @return 
     * true if the ship is not wounded.
     */
    public boolean isWounded() {
    	return ((health!=shipSize) && (health > 0));
    }

    /**
     * Inspects if ship is dead.
     * @return 
     * true if the ship is not dead.
     */
    public boolean isAlive() {
        return health > 0;
    }
    
}
