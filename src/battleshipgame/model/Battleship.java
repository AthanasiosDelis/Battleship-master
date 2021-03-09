package src.battleshipgame.model;


/**
 * @author Athanasios Delis on 07.03.2021.
 *
 */
public class Battleship {

    /**
     * 
     */
    private int shipSize;    
    /**
     * 
     */
    private int health;
    /**
     * 
     */
    private int hitPoints;
    /**
     * 
     */
    private int shunkBonus;
    /**
     * 
     */
    private String name;
    /**
     * 
     */
    private boolean horizontalDirection = true;

    /**
     * 
     */
    Battleship() {
        this.shipSize = -1;
        this.health = -1;
        this.hitPoints = -1;
        this.shunkBonus = -1;
        this.name = "";
    }
    
    /**
     * @param size
     * @param health
     * @param point
     * @param bonus
     * @param name
     */
    Battleship(int size, int health, int point, int bonus, String name) {
        this.shipSize = size;
        this.health = health;
        this.hitPoints = point;
        this.shunkBonus = bonus;
        this.name = name;
    }

    /**
     * @return
     */
    public int getShipSize() {
        return shipSize;
    }
    
    /**
     * @return
     */
    public int getHealth() {
        return health;
    }
    
    /**
     * @return
     */
    public int getPoints() {
        return hitPoints;
    }
    
    /**
     * @return
     */
    public int getBonus() {
        return shunkBonus;
    }
    
    /**
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * @param shipSize
     */
    public void setShipSize (int shipSize) {
    	this.shipSize=shipSize;
    }
    
    /**
     * @param health
     */
    public void setHealth (int health) {
    	this.health=health;
    }
    
    /**
     * @param hitPoints
     */
    public void setPoints (int hitPoints) {
    	this.hitPoints=hitPoints;
    }
    
    /**
     * @param shunkBonus
     */
    public void setBonus (int shunkBonus) {
    	this.shunkBonus=shunkBonus;
    }
    
    /**
     * @param name
     */
    public void setName(String name) {
    	this.name=name;
    }
    
    /**
     * @return
     */
    boolean hasHorizontalDirection() {
        return horizontalDirection;
    }

    /**
     * 
     */
    public void rotate() {
        horizontalDirection = !horizontalDirection;
    }
        
    /**
     * 
     */
    public void receiveShot() {
        if (isAlive()) {
            health--;
        }
    }
    
    /**
     * @return
     */
    public boolean isWounded() {
    	return ((health!=shipSize) && (health > 0));
    }

    /**
     * @return
     */
    public boolean isAlive() {
        return health > 0;
    }
    
}
