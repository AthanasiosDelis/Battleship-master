package src.battleshipgame.controller;

import src.battleshipgame.model.Battleship;
import src.battleshipgame.model.Board.Field;
import src.battleshipgame.model.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

/**
 * Created by yanair on 06.05.17.
 */
public class BattleshipController implements Initializable {

    public VBox enemyBoardArea;
    public VBox playerBoardArea;

    private Board enemyBoard;
    private Board playerBoard;

    private boolean gameRunning;
    private boolean enemyTurn;
    
    private static int turn = 0;
    private static int lastTurn = 7;

    private Battleship currentPlayerShip;

    @FXML
    private Label gameResult;
    
    @FXML
    private Label playerShipsRemaining;
    
    @FXML
    private Label enemyShipsRemaining;
    
    @FXML
    private Label enemyPercentage;
    
    @FXML
    private Label playerPercentage;
    
    @FXML
    private Label enemyScore;
    
    @FXML
    private Label playerScore;
    
    @FXML
    private Label enemyRowChoice;
    
    @FXML
    private Label enemyColChoice;
    
    @FXML
    private Label playerRowChoice ;
    
    @FXML
    private Label playerColChoice ;
    
    @FXML
    private TextField playerRow ;
    
    @FXML
    private TextField playerCol ;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNewGame();
    }

    @FXML
    private void startNewGame(ActionEvent event) {
        enemyBoardArea.getChildren().remove(enemyBoard);
        playerBoardArea.getChildren().remove(playerBoard);
        initializeNewGame();
    }

    @FXML
    private void load(ActionEvent event) {
        if (!gameRunning) {
        	System.exit(0);
            //placeShipsRandomly(playerBoard);
            //startGame();
        }
    }
    
    
    //@FXML
    //private int enemyShips(ActionEvent event) {
    //    return eneyShipsCount();
    //}

    @FXML
    private void exitGame(ActionEvent event) {
        Platform.exit();
    	System.exit(0);
    }
    
    @FXML
    private void Fire(ActionEvent event) { 
    	boolean t = turn < lastTurn ;
    	boolean v =t && gameRunning ;
    	if ( !gameRunning || !v ) {  		
    		return ;
    	}
        playerMove();
    }
    

    private void initializeNewGame() {
        //BattleshipFactory battleshipFactory = new BattleshipFactory();
        gameResult.setText("");
        playerShipsRemaining.setText("");
        enemyShipsRemaining.setText("");
        enemyPercentage.setText("");
        playerPercentage.setText("");
        enemyScore.setText("");
        playerScore.setText("");
        enemyRowChoice.setText("");
        enemyColChoice.setText("");
        playerRowChoice.setText("");
        playerColChoice.setText("");
        
        
        
        gameRunning = false;
        enemyTurn = false;
        
        //playerBoardClickHandler(),
        playerBoard = new Board( true);        
        enemyBoard = new Board( false);
        placeShipsRandomly(playerBoard);
        startGame();
        //
        
        
        //The area of BOARDS is enter visually here as a VBox
        enemyBoardArea.getChildren().add(enemyBoard);
        playerBoardArea.getChildren().add(playerBoard);
    }
    
    //Manual ship placement function 
    
    /*private EventHandler<MouseEvent> playerBoardClickHandler() {
        return event -> {
            if (gameRunning) {
                return;
            }

            Board.Field currentField = (Board.Field) event.getSource();

            placeShipsRandomly(playerBoard);
            
            startGame();
        };
    }*/
    
    
    //Not needed except ship placement
    
   /* private EventHandler<MouseEvent> playerBoardMouseEnteredHandler() {
        return event -> {
            if (gameRunning) {
                return;
            }
            Board.Field currentField = (Board.Field) event.getSource();
            playerBoard.highlightShipPlacement(currentPlayerShip, currentField);
        };
    }*/
    
    
    //getScore()
    
    //6bi
   private static int enemyShipState (Board board){
    	int count=0;
    	if(board.carrier.isAlive()) {
    		count++;
    	}/*
    	if(board.battleship.isAlive()) {
    		count++;
    	}
    	if(board.cruiser.isAlive()) {
    		count++;
    	}
    	if(board.submarine.isAlive()) {
    		count++;
    	}
    	if(board.destroyer.isAlive()) {
    		count++;
    	}*/
    	
    	return count;
   }
    	 
    	 
    	 
     /*	board.placeShipOnBoardRandomly(board.carrier);
    	board.placeShipOnBoardRandomly(board.battleship);
    	board.placeShipOnBoardRandomly(board.cruiser);
    	board.placeShipOnBoardRandomly(board.submarine);
    	board.placeShipOnBoardRandomly(board.destroyer);
    }*/


    private void playerMove() {
    	
    		Random random = new Random();
    		int row = random.nextInt(10) + 1;
    		int col = random.nextInt(10) + 1;
            
    		//int row =  Integer.parseInt(playerRow.getText());
            //int col =  Integer.parseInt(playerCol.getText());
            Board.Field currentField = enemyBoard.getField(row - 1, col - 1);
            
            //currentField.setRow() ;
            //currentField.setCol()  ;
            //= (Board.Field) event.getSource();

            if (currentField.wasShot()) {
                return;
            }
            
            enemyRowChoice.setText(Integer.toString(currentField.getRow()+1)); 
            enemyColChoice.setText(Integer.toString(currentField.getColumn()+1)); 
            //.setText(Integer.toString());
            boolean wasHit = enemyBoard.receiveShot(currentField);
            
            

            if (enemyBoard.getShipsCount() == 0) {
                gameRunning = false;
                gameResult.setText("You won the game!");
            }

            if (gameRunning) {
                enemyMove();
            }
        //};
    }
    
    //Automates enemy moves
    private void enemyMove() {
        Random random = new Random();
        Board.Field field;

        do {
            int row = random.nextInt(10);
            int col = random.nextInt(10);
            
            field = playerBoard.getField(row, col);
            
            playerRowChoice.setText(Integer.toString(row+1));
            playerColChoice.setText(Integer.toString(col+1));
            
            if (field.wasShot()) {
                break;
            }
            
            enemyTurn = playerBoard.receiveShot(field);
            enemyTurn = false;
            playerShipsRemaining.setText(Integer.toString(playerBoard.getShipsCount()));
            enemyShipsRemaining.setText(Integer.toString(enemyBoard.getShipsCount()));
            playerPercentage.setText(Integer.toString(playerBoard.getPercentage()));
            enemyPercentage.setText(Integer.toString(enemyBoard.getPercentage()));
            enemyScore.setText(Integer.toString(enemyBoard.getScore()));
            playerScore.setText(Integer.toString(playerBoard.getScore()));
            
            
            
            if (playerBoard.getShipsCount() == 0) {
                gameRunning = false;
                gameResult.setText("You lost :(");
            }

        } while (enemyTurn);
        
        turn++;
        if( turn == lastTurn) {
        	int playerScor = playerBoard.getScore();
        	int enemyScor = enemyBoard.getScore() ;
        	if(playerScor > enemyScor) {
        		gameRunning = false;
        		gameResult.setText("You won the game!");
        	}else if(playerScor <= enemyScor) {
        		gameRunning = false;
        		gameResult.setText("You lost :(");
        		
        	}
        	gameRunning = false;        	        	        	
        }
        	
    }

    private void startGame() {    	
        placeShipsRandomly(enemyBoard);
        gameRunning = true;
    }
    
    //MUst not change
    //MAY NOT NEEDED IN HE FINAL DESIGN
    private void placeShipsRandomly(Board board) {
    	
    	board.placeShipOnBoardRandomly(board.carrier);
    	//board.placeShipOnBoardRandomly(board.battleship);
    	//board.placeShipOnBoardRandomly(board.cruiser);
    	//board.placeShipOnBoardRandomly(board.submarine);
    	//board.placeShipOnBoardRandomly(board.destroyer);
    }
}
