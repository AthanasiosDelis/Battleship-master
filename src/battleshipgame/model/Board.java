package src.battleshipgame.model;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import src.battleshipgame.OverlapTilesException;
import src.battleshipgame.OversizeException;
import src.battleshipgame.PopUpWindow;
import src.battleshipgame.AdjacentTilesException;
import src.battleshipgame.InvalidCountExeception;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.LinkedList;

//import static src.battleshipgame.model.BattleshipFactory.getTotalShipsCount;

/**
 * Created by Athanasios Delis on 07.03.2021.
 */
public class Board extends Parent {
	
	
    //MUst change
    //5 add ships 1 per type
    

    private static final int ROW = 10;
    private static final int COL = 10;
    
    public Battleship carrier = new Battleship(5, 5, 350, 1000, "Carrier");
    public Battleship battleship = new Battleship(4, 4, 250, 500, "Battleship");
    public Battleship cruiser = new Battleship(3, 3, 100, 250, "Cruiser");
    public Battleship submarine = new Battleship(3, 3, 100, 0, "Submarine");
    public Battleship destroyer = new Battleship(2, 2, 50, 0, "Destroyer");
    
    public LinkedList<Battleship> ships = new LinkedList<>();

    {    	
        ships.add(carrier);        
        ships.add(battleship);        
        ships.add(cruiser);        
        ships.add(submarine);        
        ships.add(destroyer);        
    }

    public int getTotalShipsCount() {
    	return ships.size();
    }

    public Battleship getNextShip() {
        return ships.pollFirst(); // returns null for empty list
    } 
    
    private int oneMoreShot = 0;
    private int oneGoodShot = 0;
    private int percentage = 0;
    private int score = 0 ;
    
    //MAybe here the exception!!!
    
    
    private int shipsCount = getTotalShipsCount();
    
    private int shipsAlive = 1;
    private int shipsWounde = 0;
    private int shipsDead = 0;
    private boolean playerBoard;
    private VBox rows = new VBox();
    private List<Field> highlightedFields = new ArrayList<>();
    
    
    //Makes the tableaux
    
    public Board( boolean playerBoard) {
        this.playerBoard = playerBoard;
        for (int row = 0; row < ROW; row++) {
            HBox currentRow = new HBox();
            for (int col = 0; col < COL; col++) {
                Field field = new Field(row, col);
                
                currentRow.getChildren().add(field);
            }
            rows.getChildren().add(currentRow);
        }
        getChildren().add(rows);
    }

    //PUTS SHIP IN ITS PLACE
    public boolean setShip(Battleship ship, Field startField) {
        int shipSize = ship.getShipSize();
        
        startField.setBattleship(ship);
        
        if (!canSetShip(ship, startField)) {
            return false;
        }

        for (int i = 0; i < shipSize; i++) {
            if (ship.hasHorizontalDirection()) {
                Field occupiedField = getField(startField.getRow(), startField.getColumn() + i);
                occupiedField.setShipField(ship);
                if (startField.getRow()<9) {
                	Field occupiedField1 = getField(startField.getRow()+1, startField.getColumn() + i);
                	occupiedField1.setBusy(true);
                }
                if (startField.getRow()>0) {
                	Field occupiedField2 = getField(startField.getRow()-1, startField.getColumn() + i);
                	occupiedField2.setBusy(true);
                }
                if (startField.getColumn()>0) {
                	Field occupiedField3 = getField(startField.getRow(), startField.getColumn() - 1);
                	occupiedField3.setBusy(true);
                }
                if (startField.getColumn() + shipSize <9) {
                	Field occupiedField4 = getField(startField.getRow(), startField.getColumn() + shipSize);
                	occupiedField4.setBusy(true);
                }
                
                
            } else {
                Field occupiedField = getField(startField.getRow() + i, startField.getColumn());
                occupiedField.setShipField(ship);
                if (startField.getColumn()<9) {
                	Field occupiedField1 = getField(startField.getRow() + i, startField.getColumn() +1);
                	occupiedField1.setBusy(true);
                }
                if (startField.getColumn()>0) {
                	Field occupiedField2 = getField(startField.getRow() + i, startField.getColumn() -1);
                	occupiedField2.setBusy(true);
                }
                if (startField.getRow()>0) {
                	Field occupiedField3 = getField(startField.getRow() - 1, startField.getColumn() );
                	occupiedField3.setBusy(true);
                }
                if (startField.getRow() + shipSize <9) {
                	Field occupiedField4 = getField(startField.getRow() + shipSize, startField.getColumn() );
                	occupiedField4.setBusy(true);
                }
            }
        }
        return true;
    }
    
    //****************THE PLACE FOR THE 2 EXCEPTIONS
    private boolean canSetShip(Battleship ship, Field startField) { // todo add logic for adjacent fields
        int shipSize = ship.getShipSize();

        // check that there's enough space for ship
        if (ship.hasHorizontalDirection()) {
        	try {
        		if (startField.getColumn() + shipSize > COL) throw new OversizeException();
        	} catch (OversizeException e) {
        		
        		PopUpWindow.display("Exception","OverSize") ;
        		Platform.exit();
            	System.exit(0);
                return false;
            }
        } else {
        	try {
        		if (startField.getRow() + shipSize > COL) throw new OversizeException();
        	} catch (OversizeException e){
        		PopUpWindow.display("Exception","OverSize") ;
        		Platform.exit();
            	System.exit(0);
                return false;
            }
        }

        // check that fields are not occupied
        for (int i = 0; i < shipSize; i++) {
            if (ship.hasHorizontalDirection()) {
                Field currentField = getField(startField.getRow(), startField.getColumn() + i);
                try{
                	if (currentField.isOccupied()) throw new OverlapTilesException();                	
                }catch (OverlapTilesException e){
                	PopUpWindow.display("Exception","Overlap") ;
                	Platform.exit();
                	System.exit(0);
                	return false;
                }                
            } else {
                Field currentField = getField(startField.getRow() + i, startField.getColumn());
                try{
                	if (currentField.isOccupied()) throw new OverlapTilesException();
                }catch (OverlapTilesException e){
                	PopUpWindow.display("Exception","Overlap") ;
                	Platform.exit();
                	System.exit(0);
                    return false;
                }
            }
        }
        
     // check that fields are not busy
        for (int i = 0; i < shipSize; i++) {
            if (ship.hasHorizontalDirection()) {
                Field currentField = getField(startField.getRow(), startField.getColumn() + i);
                try{
                	if (currentField.isBusy()) throw new AdjacentTilesException();                	
                }catch (AdjacentTilesException e){
                	PopUpWindow.display("Exception","Adjacent") ;
                	Platform.exit();
                	System.exit(0);
                	return false;
                }                
            } else {
                Field currentField = getField(startField.getRow() + i, startField.getColumn());
                try{
                	if (currentField.isBusy()) throw new AdjacentTilesException();
                }catch (AdjacentTilesException e){
                	PopUpWindow.display("Exception","Adjacent") ;
                	Platform.exit();
                	System.exit(0);
                    return false;
                }
            }
        }
        return true;
    }

    //shmantikes oi 2 alles den tis xreiazomi
    public void highlightShipPlacement(Battleship ship, Field startField) {
        if (ship == null || !canSetShip(ship, startField)) {
            return;
        }
        
           
        
        int shipSize = ship.getShipSize();

        for (int i = 0; i < shipSize; i++) {
            if (ship.hasHorizontalDirection()) {
                Field occupiedField = getField(startField.getRow(), startField.getColumn() + i);
                occupiedField.highlight();
                highlightedFields.add(occupiedField);
            } else {
                Field occupiedField = getField(startField.getRow() + i, startField.getColumn());
                occupiedField.highlight();
                highlightedFields.add(occupiedField);
            }
        }
    }

    public void removeShipPlacementHighlight() {
        for (Field f : highlightedFields) {
            f.removeHighlight();
        }
    }

    //##01
    public void placeShipOnBoardRandomly(Battleship battleship) {
        boolean shipPlaced;
        Random random = new Random();

        do {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            Field start = new Field(row, col);

            if (random.nextBoolean()) {
                battleship.rotate();
            }

            shipPlaced = setShip(battleship, start);
        } while (!shipPlaced);
    }
    
    
    public void placeShipOnBoardLoad(Battleship battleship, int rowShip ,int colShip ,int direction) {
        boolean shipPlaced;
        Random random = new Random();

        
            int row = rowShip ;
            int col = colShip ;
            Field start = new Field(row, col);

            if(direction == 2) {
            	battleship.rotate();
            }

            shipPlaced = setShip(battleship, start);
        
    }
    
    //JUST A GETTER
    public Field getField(int row, int col) {
        HBox hBox = (HBox) rows.getChildren().get(row);
        return (Field) hBox.getChildren().get(col);
    }


    //MAy be used maybe not
    //STOP TIME FOR SOME TIME GOOGLE HOW MUCH
    private void sleep() {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    public int getScore(){
    	return score;
    }
    
    //Debugging func
    public int getShoots() {
    	return oneMoreShot;
    }
    
    public int getPercentage() {
    	return percentage;
    }

    public int getShipsCount() {
        return shipsCount;
    }
   
    
    public boolean receiveShot(Field field) {
        //if (!playerBoard) {
          //  sleep();
        //}
             
        return field.shoot();
    }
    //THE MAIN DESIGN
    public class Field extends Rectangle {

        private static final int FIELD_SIZE = 30;

        private int row;
        private int column;

        private boolean wasShot = false;
        private boolean isOccupied = false;
        private boolean isBusy = false;

        private Battleship battleship;
        
                
        public Field(int row, int column) {
            super(FIELD_SIZE, FIELD_SIZE);
            this.row = row;
            this.column = column;
            
            setFill(Color.WHITE);
            setStroke(Color.DARKGRAY);
        }
        
        public void setBattleship(Battleship battleship) {
        	this.battleship=battleship;
        }
        
        public String getName(){
        	return this.battleship.getName();
        }
        
        public void setRow(int row) {
            this.row = row;
        }

        public void setCol(int col) {
            this.column = col;
        }
        
        public void setBusy(Boolean busy) {
            this.isBusy = busy;
            //setFill(Color.GREEN);
            //setStroke(Color.DARKGRAY);            
        }

        public int getRow() {
            return row;
        }
        
        public boolean getAlive() {
            return this.battleship.isAlive();
        }

        public int getColumn() {
            return column;
        }
        
        public boolean getDirection() {
            return this.battleship.hasHorizontalDirection();
        }
        
        
        public int getLength() {
            return this.battleship.getShipSize();
        }

        public boolean wasShot() {
            return wasShot;
        }

        private boolean isOccupied() {
            return isOccupied;
        }
        
        private boolean isBusy() {
            return isBusy;
        }

        private void highlight() {
            if (!isOccupied()) {
                setFill(Color.LIGHTBLUE);
            }
        }

        private void removeHighlight() {
            if (!isOccupied()) {
                setFill(Color.WHITE);
            }
        }

        private void setShipField(Battleship battleship) {
            this.battleship = battleship;
            isOccupied = true;
            if (playerBoard) {
                setFill(Color.ROYALBLUE);
            }
        }
        

        private boolean shoot() {
            wasShot = true;
            oneMoreShot++;
            percentage = (oneGoodShot * 100) / (oneMoreShot + oneGoodShot);
            
            
            
            
            if (battleship != null) {
                battleship.receiveShot();
                setFill(Color.ORANGERED);
                
                oneGoodShot++;
                score = score + battleship.getPoints();
                percentage = (oneGoodShot * 100) / (oneMoreShot + oneGoodShot);
                
                                         
                if (!battleship.isAlive()) {
                	score = score + battleship.getBonus();
                    shipsCount--;
                    
                }
                return true;
            }

            setFill(Color.GRAY);
            return false;
        }

        @Override
        public String toString() {
            return "(" + row + ", " + column + ")";
        }
    }

}
