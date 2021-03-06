package src.battleshipgame.model;

/**
 * Created by yanair on 05.05.17.
 */
public class Battleship {

    private int shipSize;    
    private int health;
    private int hitPoints;
    private int shunkBonus;
    private String name;
    private boolean horizontalDirection = true;

    Battleship() {
        this.shipSize = -1;
        this.health = -1;
        this.hitPoints = -1;
        this.shunkBonus = -1;
        this.name = "";
    }
    
    Battleship(int size, int health, int point, int bonus, String name) {
        this.shipSize = size;
        this.health = health;
        this.hitPoints = point;
        this.shunkBonus = bonus;
        this.name = name;
    }

    public int getShipSize() {
        return shipSize;
    }
    
    public int getHealth() {
        return health;
    }
    
    public int getPoints() {
        return hitPoints;
    }
    
    public int getBonus() {
        return shunkBonus;
    }
    
    public String getName() {
        return name;
    }
    
    /*public void s  (int name) {
    	this.name=name;
    }*/
    
    public void setShipSize (int shipSize) {
    	this.shipSize=shipSize;
    }
    
    public void setHealth (int health) {
    	this.health=health;
    }
    
    public void setPoints (int hitPoints) {
    	this.hitPoints=hitPoints;
    }
    
    public void setBonus (int shunkBonus) {
    	this.shunkBonus=shunkBonus;
    }
    
    public void setName(String name) {
    	this.name=name;
    }
    
    boolean hasHorizontalDirection() {
        return horizontalDirection;
    }

    public void rotate() {
        horizontalDirection = !horizontalDirection;
    }
        
    public void receiveShot() {
        if (isAlive()) {
            health--;
            System.out.println("Name: " + this.getName() + " Health: " + health);
        }
    }
    
    public boolean isWounded() {
    	return ((health!=shipSize) && (health > 0));
    }

    public boolean isAlive() {
        return health > 0;
    }
    
    @Override    
    public String toString () {
        return "(Size " + shipSize + " / Horizontal direction : " + name + ")";
    }

}
