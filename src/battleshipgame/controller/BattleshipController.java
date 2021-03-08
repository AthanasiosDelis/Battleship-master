package src.battleshipgame.controller;

import src.battleshipgame.InvalidCountExeception;
import src.battleshipgame.PopUpWindow;
import src.battleshipgame.model.Battleship;
import src.battleshipgame.model.Board.Field;
import src.battleshipgame.model.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;

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
    
    private static int turn = 1;
    private static int lastTurn = 101;

    private Battleship currentPlayerShip;
    
    private ArrayList<String> enemyStats = new ArrayList<String>();
    private ArrayList<String> playerStats = new ArrayList<String>();
    
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
        turn = 1;
        
        matrixPlayer = matrixPlayer1.clone() ;        
        matrixEnemy =  matrixEnemy1.clone();
        
        initializeNewGame();
    }

    @FXML
    private void exitGame(ActionEvent event) {
        Platform.exit();
    	System.exit(0);
    }
    
    @FXML
    
    private void shipRemaining(ActionEvent event) {
    	String remaining = enemyShipState(enemyBoard.carrier) + '\n'
    			+ enemyShipState(enemyBoard.battleship) + '\n'
    			+ enemyShipState(enemyBoard.cruiser) + '\n'
    			+ enemyShipState(enemyBoard.submarine) + '\n'
    			+ enemyShipState(enemyBoard.destroyer) + '\n';        			
    	PopUpWindow.display("6bi:Enemy Ships",remaining) ;    	
    }
    
    @FXML
    
    private void playerFiveShots(ActionEvent event) {
    	String stats ="";
    	for(String s:playerStats) {  		
    		stats +=  s + '\n';
    	}       			
    	PopUpWindow.display("6bii Player Shots",stats) ;    	
    }
    
    @FXML
    
    private void enemyFiveShots(ActionEvent event) {
    	String stats ="";
    	for(String s:enemyStats) {  		
    		stats +=  s + '\n';
    	}       			
    	PopUpWindow.display("6bii Player Shots",stats) ;    			    	 	
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
        
    private int[][] matrixPlayer =  { 
    	    { 1, 1, 1, 1 }, 
    	    { 2, 3, 1, 1 }, 
    	    { 3, 5, 1, 1 }, 
    	    { 4, 7, 1, 1 }, 
    	    { 5, 9, 1, 1 }
    	};
    
    private int[][] matrixEnemy =  { 
    	    { 1, 8, 7, 1 }, 
    	    { 2, 2, 1, 1 }, 
    	    { 3, 4, 1, 1 }, 
    	    { 4, 6, 1, 1 }, 
    	    { 5, 8, 1, 1 }
    	};
    
    private int[][] matrixPlayer1 =  { 
    	    { 1, 1, 1, 1 }, 
    	    { 2, 3, 1, 1 }, 
    	    { 3, 5, 1, 1 }, 
    	    { 4, 7, 1, 1 }, 
    	    { 5, 9, 1, 1 }
    	};
    
    private int[][] matrixEnemy1 =  { 
    	    { 1, 8, 7, 1 }, 
    	    { 2, 2, 1, 1 }, 
    	    { 3, 4, 1, 1 }, 
    	    { 4, 6, 1, 1 }, 
    	    { 5, 8, 1, 1 }
    	};
    
		
	
    
    @FXML
    private void load(ActionEvent event) throws FileNotFoundException {
        
            enemyBoardArea.getChildren().remove(enemyBoard);
            playerBoardArea.getChildren().remove(playerBoard);
            turn = 1;  
        
        //String[] str = new String[2];
         
        PopUpWindow.SignUpForm("Load");
        //System.out.println(PopUpWindow.str[0]);
        //System.out.println(PopUpWindow.str[1]);
        String pathEnemy = PopUpWindow.str[0];
        String pathPlayer = PopUpWindow.str[1];
       // medialab/enemy_default.txt
        // medialab/player_default_ok.txt
        
        Scanner scEnemy = new Scanner(new File(pathEnemy));
        Scanner scPlayer = new Scanner(new File(pathPlayer));
        
        int row = -1;
        while (scEnemy.hasNextLine())
        {
             row++; 
             String line = scEnemy.nextLine();
             String[] arrOfStr = line.split(",");
             for (int j=0; j<4; j++)
                    {
            	 		matrixEnemy[row][j] = Integer.parseInt(arrOfStr[j]);
                    }
        }
        row = -1;
        while (scPlayer.hasNextLine())
        {
             row++; 
             String line = scPlayer.nextLine();
             String[] arrOfStr = line.split(",");
             for (int j=0; j<4; j++)
                    {
            	 		matrixPlayer[row][j] = Integer.parseInt(arrOfStr[j]);
                    }
        }
        initializeNewGame();
    }
    

    private void initializeNewGame() {       
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
        playerBoard = new Board( true);        
        enemyBoard = new Board( false);        
        placeShipsLoad(playerBoard);
        placeShipsLoad(enemyBoard);
        gameRunning = true;        
        try {
        	if(playerBoard.getTotalShipsCount() > 5 ) throw new InvalidCountExeception();
        } catch(InvalidCountExeception e ) {
        	System.out.println("InvalidCount");
        	System.exit(0);
        } 
        enemyBoardArea.getChildren().add(enemyBoard);
        playerBoardArea.getChildren().add(playerBoard);
    }

    //##01
    private void placeRandomly(Board board) {    	
    	board.placeShipOnBoardRandomly(board.carrier);
    	board.placeShipOnBoardRandomly(board.battleship);
    	board.placeShipOnBoardRandomly(board.cruiser);
    	board.placeShipOnBoardRandomly(board.submarine);
    	board.placeShipOnBoardRandomly(board.destroyer);
    }
    
    private void placeShipsLoad(Board board) {
    	
    	board.placeShipOnBoardLoad(board.carrier,matrixEnemy[4][1],matrixEnemy[4][2],matrixEnemy[4][3]);
    	board.placeShipOnBoardLoad(board.battleship,matrixEnemy[3][1],matrixEnemy[3][2],matrixEnemy[3][3]);
    	board.placeShipOnBoardLoad(board.cruiser,matrixEnemy[2][1],matrixEnemy[2][2],matrixEnemy[2][3]);
    	board.placeShipOnBoardLoad(board.submarine,matrixEnemy[1][1],matrixEnemy[1][2],matrixEnemy[1][3]);
    	board.placeShipOnBoardLoad(board.destroyer,matrixEnemy[0][1],matrixEnemy[0][2],matrixEnemy[0][3]);
    }
    
    
    //6bi
   
   private static String enemyShipState(Battleship ship){
	   if(!ship.isAlive()) {
		   return ship.getName() + ": DEAD";
    	}else if(ship.isWounded()) {
    		return ship.getName() + ": WOUNDED";
    	}else {
    		return ship.getName() + ": UNHIT";
    	}
   }
    	 

    private void playerMove() {
    		System.out.println("Player" + turn);
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

            
            if(playerStats.size() > 4) {
            	playerStats.remove(0);
            }
            
            String statsTurn = "Turn: " + Integer.toString(turn) + '\n';
            statsTurn += "Row: " + Integer.toString(row) + '\n';
            statsTurn += "Col: " + Integer.toString(col) + '\n';
            statsTurn += "Hit: " + Boolean.toString(wasHit) + '\n';		
            		
            if(wasHit) {
            	
            	statsTurn += "Name: " + currentField.getName() + '\n' ;
            }
            playerStats.add(statsTurn);
            //System.out.println(playerStats);
            playerShipsRemaining.setText(Integer.toString(playerBoard.getShipsCount()));
            enemyShipsRemaining.setText(Integer.toString(enemyBoard.getShipsCount()));
            playerPercentage.setText(Integer.toString(playerBoard.getPercentage()));
            enemyPercentage.setText(Integer.toString(enemyBoard.getPercentage()));
            enemyScore.setText(Integer.toString(enemyBoard.getScore()));
            playerScore.setText(Integer.toString(playerBoard.getScore()));
            
            /*if (enemyBoard.getShipsCount() == 0) {
                gameRunning = false;
                gameResult.setText("You won the game!");
            }*/

            if (gameRunning) {
                enemyMove();
            }
        
    }
    
    private static void print2D(int mat[]) {
        for (int row: mat) System.out.println(Integer.toString(row));
    }
    
    boolean success = false ;
    int r = -1;
    int c = -1;
    int l = -1;
    int [] where = new int[2];
    //int [] where = new int[2];
    int [][] array = new int[5][2];
    int [][] array1 = {
    		{0,0},
			{0,0},
			{0,0},
			{0,0},
			{0,0},    		
    };
    boolean strategy = false;
    boolean hori = true;
    int horiz = 1;
    int initialize = 1;
    ArrayList<Integer> list = new ArrayList<Integer>();
    //Automates enemy moves
    private int next = 0;
    private int [] tmp = new int[2];
    boolean alive = true;
    boolean forbit = false;
    private void enemyMove() {
        Random random = new Random();
        Board.Field field;
        
        do {
        	int row = -1;
        	int col = -1;
        	
        	boolean initial = false;
        	//Boolean.parseBoolean(enemyStats.get(0));
        	
        	//initialize = 1; MISS_1
        	if(turn > 1 && !strategy) {
        		//System.out.println(Boolean.parseBoolean(enemyStats.get(enemyStats.size()-1)));
        		row = random.nextInt(10);
        		col = random.nextInt(10);
        		initialize = 1;
        	}
        	//START
        	else if (turn == 1 && !strategy) {
        		initialize = 1;
        		row = random.nextInt(10);
        		col = random.nextInt(10);
        	}
        	
        	
        	//1st HIT
    		if(success && initialize == 1) {
    			strategy = true;
    			initialize = 2;
    			success = false;
    			
    			array [4][0]  = r;
				array [4][1]  = c;   
				
				System.out.println(array [4][0]);
				System.out.println(array [4][1]);
				
    			
    			if (r<9) {
    				array [0][0] = where[0] = r + 1;
    				array [0][1] = where[1] = c;        			
    				//Locations.add(where);
    				//print2D(where);print2D(where);
    				//System.out.println("LOCATIONS : " + Locations);
    				//System.out.println("1Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));
                }
                if (r>0) {
                	array [1][0] =  where[0] = r -1;
                	array [1][1] = where[1] = c;
                	//Locations.add(where);
                	//print2D(where);
                	//System.out.println("LOCATIONS : " + Locations);
                }
                if (c>0) {
                	array [2][0] = where[0] = r;
                	array [2][1] = where[1] = c -1;
                	//Locations.add(where);
                	//print2D(where);
                	//System.out.println("LOCATIONS : " + Locations);
                }
                if (c <9) {
                	array [3][0] = where[0] = r;
                	array [3][1] = where[1] = c + 1;
                	//Locations.add(where);
                	//print2D(where);
                	//System.out.println("LOCATIONS : " + Locations);
                }
                //int [] tmp = new int[2];
        		//tmp = Locations.get(next);
        		//next++;
        		//row = tmp[0];
        		//col = tmp[1];
                //System.out.println(" TURN: " + turn);
                //System.out.println(" HERE 0 ");
                //System.out.println(" next: " + next);
                //System.out.println("1Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));        	
                //System.out.println("LOCATIONS : " + Locations);
    		}
        	
        	//MISS_2
          	boolean temp2 = (!success && strategy);
        	if(temp2 && initialize == 2) {
        	//	System.out.println(" HERE 1 ");
        		
        		//tmp = Locations.get(next).clone();
        		//Locations.remove(next);
        		//print2D(tmp);
        		//
        		row = array [next][0];
        		col = array [next][1];
        		next++;
        		
        	//	System.out.println(" TURN: " + turn);
        	//	System.out.println(" next: " + next);
        	//	System.out.println(" 2Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));        		
        		//System.out.println("LOCATIONS : " + Locations);
      
        	}
        	
        	//2nd HIT
        	boolean temp1 = (success && strategy);
        	if( temp1 && initialize == 2) {
        		//System.out.println(" HERE 2 ");
        		initialize = 3;
        		strategy = alive;
        		next = 0;
        		success = true;
        		forbit = true;
        		
        //		System.out.println(" next: " + next);
        		//Locations.clear();
        		//next = 0;
        	//	System.out.println(Boolean.toString(hori));
		
        		if (hori == true) {
        			for(int i=1; i<l; i++) {
        				if( array[4][1] + i < 10) {
        					list.add(array[4][1] + i);// length
        				}
            			
           // 			System.out.println(list);
            		}
        			for(int i=1; i<l; i++) {
        				if( array[4][1] - i > -1) {
        					list.add(array[4][1] - i);// length
        				}
            			
          //  			System.out.println(list);
            		}
        			col = list.get(next);
            		row = array[4][0];
            		next++;
            	//	System.out.println(turn);
            	//	System.out.println(" next: " + next);
            	//	System.out.println("3Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));
        		}else if (hori == false) {
        			for(int i=1; i<l; i++) {
        				if(array[4][0] + i < 10) {
        					list.add(array[4][0] + i);// length
        				}
            			
            		//	System.out.println(list);
            		}
        			for(int i=1; i<l; i++) {
            			if(array[4][0] - i> -1) {
            				list.add(array[4][0] - i);// length
            			}
        				
            		//	System.out.println(list);
            		}
        			col = array[4][1];
        			row = list.get(next);            		
            		next++;
            	//	System.out.println(turn);
            	//	System.out.println(" next: " + next);
            	//	System.out.println("4Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));
        		}
        		if(!strategy) {
    				initialize = 1;
    				list.clear();
    				next = 0;
    				array = array1.clone();
            		row = random.nextInt(10);
            		col = random.nextInt(10);
        		}
        		
        	}
        	
        	
        	//strategy = alive;
        	boolean temp5 = (!forbit && strategy);
        	if( temp5 && initialize == 3) {
        		if (hori == true) {
        		strategy = alive;
        			if(alive) {
        				col = list.get(next);
                		row = array[4][0];
                		next++;
        			}else if(!alive ) {
        				initialize = 1;
        				list.clear();
        				next = 0;
        				array = array1.clone();
        				row = random.nextInt(10);
                		col = random.nextInt(10);
        			}
        		
        		//System.out.println(turn);
        	//	System.out.println(" next: " + next);
        	//	System.out.println("5Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));
        		
        		}else if (hori == false) {
    			strategy = alive;
    				if(alive) {
    					row = list.get(next);
    					col = array[4][1];
    					next++;
    				}else if(!alive ) {
    					initialize = 1;
    					list.clear();
    					next = 0;
    					array = array1.clone();
    					row = random.nextInt(10);
    					col = random.nextInt(10);
    				}
    			
        		//col = random.nextInt(10);
        	//	System.out.println(turn);
        	//	System.out.println(" next: " + next);
        	//	System.out.println("6Row: " + Integer.toString(row) + "Col: "+  Integer.toString(col));
        		}  
        }
        	
        	
        	

        	
        	
        	forbit = false;
            field = playerBoard.getField(row, col);
            
            playerRowChoice.setText(Integer.toString(row+1));
            playerColChoice.setText(Integer.toString(col+1));
            //System.out.println(" HERE 3 BEFORE SHOOT");
            if (field.wasShot()) {
            	System.out.println("Enemy TURN: " + turn);
            	System.out.println(" HERE 4 was SHOOTed ");
            	success = false;
            	enemyTurn = true;
                //break;
            }else {
            	//System.out.println(" HERE 5 ");
            boolean goodShot = playerBoard.receiveShot(field);
            enemyTurn = false;
            playerShipsRemaining.setText(Integer.toString(playerBoard.getShipsCount()));
            enemyShipsRemaining.setText(Integer.toString(enemyBoard.getShipsCount()));
            playerPercentage.setText(Integer.toString(playerBoard.getPercentage()));
            enemyPercentage.setText(Integer.toString(enemyBoard.getPercentage()));
            enemyScore.setText(Integer.toString(enemyBoard.getScore()));
            playerScore.setText(Integer.toString(playerBoard.getScore()));
            
            if(enemyStats.size() > 4) {
            	enemyStats.remove(0);
            }
            
            String statsTurn = "Turn: " + Integer.toString(turn) + '\n';;
            
            statsTurn += "Row: " + Integer.toString(row + 1) + '\n';
            statsTurn += "Col: " + Integer.toString(col + 1) + '\n';
            statsTurn += "Hit: " + Boolean.toString(goodShot) + '\n';		
            success = goodShot;
            if(goodShot) {
            	System.out.println(" HERE 5 SUCCESS = TRUE ");
            	r = row;
            	c = col;
            	l = field.getLength();
            	hori = field.getDirection();
            	alive = field.getAlive();
            	statsTurn += "Name: " + field.getName() + '\n' ;
            }

           
            enemyStats.add(statsTurn);
            //System.out.println(enemyStats) ;
            //for(int i=0; i<enemyStats.size(); i++) {
            	//for(int j=0; j<enemyStats.get(i).size(); i++ )
            	//System.out.println(enemyStats.get(i).get(j)) ;
            //}
            
            //***********
           /* if (playerBoard.getShipsCount() == 0) {
                gameRunning = false;
                gameResult.setText("You lost :(");
            }*/
            }
            
            
            
            
        } while (enemyTurn);
        
        turn++;
        if( turn == lastTurn) {
        	int enemyScor = playerBoard.getScore();
        	int playerScor = enemyBoard.getScore() ;
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

   
    
    

    
  //MUst not change
    //MAY NOT NEEDED IN HE FINAL DESIGN
   /* private void placeShipsLoad(Board board) {
    	
    	board.placeShipOnBoardLoad(board.carrier,matrixEnemy[4][1],matrixEnemy[4][2],matrixEnemy[4][3]);
    	board.placeShipOnBoardLoad(board.battleship,matrixEnemy[3][1],matrixEnemy[3][2],matrixEnemy[3][3]);
    	board.placeShipOnBoardLoad(board.cruiser,matrixEnemy[2][1],matrixEnemy[2][2],matrixEnemy[2][3]);
    	board.placeShipOnBoardLoad(board.submarine,matrixEnemy[1][1],matrixEnemy[1][2],matrixEnemy[1][3]);
    	board.placeShipOnBoardLoad(board.destroyer,matrixEnemy[0][1],matrixEnemy[0][2],matrixEnemy[0][3]);
    }*/
    
    //,matrixEnemy[row][1],matrixEnemy[row][2],matrixEnemy[row][3]
}
