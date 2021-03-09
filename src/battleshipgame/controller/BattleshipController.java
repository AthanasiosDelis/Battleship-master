package src.battleshipgame.controller;

import src.battleshipgame.CoordinatesException;
import src.battleshipgame.InvalidCountExeception;
import src.battleshipgame.PopUpWindow;
import src.battleshipgame.model.Battleship;
import src.battleshipgame.model.Board;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * @author Athanasios Delis on 07.03.2021.
 *
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
    private static Random rand = new Random();
    private static boolean firstTurnPlayer = rand.nextBoolean();    
    private Battleship currentPlayerShip;    
    private ArrayList<String> enemyStats = new ArrayList<String>();
    private ArrayList<String> playerStats = new ArrayList<String>();
    private Scanner scEnemy;
    private Scanner scPlayer;
    private boolean fireAuto;
    
    
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
    
    @FXML
    private Label playsFirst;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeNewGame();
    }

    @FXML
    private void startNewGame(ActionEvent event) {
        enemyBoardArea.getChildren().remove(enemyBoard);
        playerBoardArea.getChildren().remove(playerBoard);
        turn = 1;
        firstTurnPlayer = rand.nextBoolean();
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
    	fireAuto = false;
    	boolean t = turn < lastTurn ;
    	boolean v =t && gameRunning ;
    	if ( !gameRunning || !v ) {  		
    		return ;
    	}
    	
    	   				
			try{
				int row;
				int col;
				String tmp1;
	    		String tmp2;
				tmp1 = playerRow.getText();
				tmp2 = playerCol.getText();
				//PopUpWindow.display(Boolean.toString(isNumeric(tmp1) ) ,Boolean.toString(isNumeric(tmp2)));
				if( !isNumeric(tmp1) || !isNumeric(tmp2)) throw new CoordinatesException ();
				row = Integer.parseInt(tmp1);
				col = Integer.parseInt(tmp2);
				if((row >10 && row<1) || (col >10 && col<1)) throw new CoordinatesException ();
				//if() throw	new CoordinatesException ();		
			}catch(CoordinatesException e) {
				PopUpWindow.display("Exception","Numbers from 1-10 as coordinates, dummy!");
				//allGood = false;
				return;
			}
		
    	
    	
    	//RANDOM TURN
    	if( firstTurnPlayer) {
    		playerMove();
    	}
    	else {
    		enemyMove();
    	}        
    }
    
    @FXML
    private void FireAuto(ActionEvent event) {
    	fireAuto = true;
    	boolean t = turn < lastTurn ;
    	boolean v =t && gameRunning ;
    	if ( !gameRunning || !v ) {  		
    		return ;
    	}
    	//RANDOM TURN
    	if( firstTurnPlayer) {
    		playerMove();
    	}
    	else {
    		enemyMove();
    	}        
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
    private void load(ActionEvent event) throws FileNotFoundException, NullPointerException {
        
            enemyBoardArea.getChildren().remove(enemyBoard);
            playerBoardArea.getChildren().remove(playerBoard);
            turn = 1;  
            firstTurnPlayer = rand.nextBoolean();      
         
        PopUpWindow.SignUpForm("Load");
        String pathEnemy = PopUpWindow.str[0];
        String pathPlayer = PopUpWindow.str[1];
       // "medialab/enemy_default.txt";
        // "medialab/player_default_adj.txt";
        try {
        	scEnemy = new Scanner(new File(pathEnemy)); 
        	scPlayer = new Scanner(new File(pathPlayer));
        } catch (FileNotFoundException e) {
        	PopUpWindow.display("Exception","FileNotFoundException") ;
        	return;
        }catch (NullPointerException e) {
        	PopUpWindow.display("Exception","NullPointerException") ;
        	return;
        }
                
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
        playsFirst.setText("Human Player Plays First: " + Boolean.toString( firstTurnPlayer));
        gameRunning = false;
        enemyTurn = false;
        playerBoard = new Board( true);        
        enemyBoard = new Board( false);        
        placeShipsLoadPlayer(playerBoard);
        placeShipsLoadEnemy(enemyBoard);
        gameRunning = true;        
        try {
        	if(playerBoard.getTotalShipsCount()  > 5 ) throw new InvalidCountExeception();
        } catch(InvalidCountExeception e ) {
        	
        	PopUpWindow.display("Exception","InvalidCount") ;
        	Platform.exit();
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
    
    private void placeShipsLoadEnemy(Board board) {
    	
    	board.placeShipOnBoardLoad(board.carrier,matrixEnemy[4][1],matrixEnemy[4][2],matrixEnemy[4][3]);
    	board.placeShipOnBoardLoad(board.battleship,matrixEnemy[3][1],matrixEnemy[3][2],matrixEnemy[3][3]);
    	board.placeShipOnBoardLoad(board.cruiser,matrixEnemy[2][1],matrixEnemy[2][2],matrixEnemy[2][3]);
    	board.placeShipOnBoardLoad(board.submarine,matrixEnemy[1][1],matrixEnemy[1][2],matrixEnemy[1][3]);
    	board.placeShipOnBoardLoad(board.destroyer,matrixEnemy[0][1],matrixEnemy[0][2],matrixEnemy[0][3]);
    }
    
    private void placeShipsLoadPlayer(Board board) {
    	
    	board.placeShipOnBoardLoad(board.carrier,matrixPlayer[4][1],matrixPlayer[4][2],matrixPlayer[4][3]);
    	board.placeShipOnBoardLoad(board.battleship,matrixPlayer[3][1],matrixPlayer[3][2],matrixPlayer[3][3]);
    	board.placeShipOnBoardLoad(board.cruiser,matrixPlayer[2][1],matrixPlayer[2][2],matrixPlayer[2][3]);
    	board.placeShipOnBoardLoad(board.submarine,matrixPlayer[1][1],matrixPlayer[1][2],matrixPlayer[1][3]);
    	board.placeShipOnBoardLoad(board.destroyer,matrixPlayer[0][1],matrixPlayer[0][2],matrixPlayer[0][3]);
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
    	 
   //IF YOU REALLY READ THE CODE YEAH I KNOW DA FUCK WITH THIS APPROACH?!?!?
   private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

   public boolean isNumeric(String strNum) {
       if (strNum == null) {
           return false; 
       }
       return pattern.matcher(strNum).matches();
   }
    private void playerMove(){
    		
    		Random random = new Random();
    		int row = -1;
    		int col = -1;
    		//boolean allGood = true;
    		
    		
    		
    			if(fireAuto) {
    				row = random.nextInt(10) + 1;
    				col = random.nextInt(10) + 1;
    				//allGood=true;
    			}else if (!fireAuto){
    				row = Integer.parseInt(playerRow.getText());
    				col = Integer.parseInt(playerCol.getText());
    			}  			

            Board.Field currentField = enemyBoard.getField(row - 1, col - 1);

            if (currentField.wasShot()) {
                return;
            }
            
            enemyRowChoice.setText(Integer.toString(currentField.getRow()+1)); 
            enemyColChoice.setText(Integer.toString(currentField.getColumn()+1)); 
       
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
            
            playerShipsRemaining.setText("Player ShipsRemaining: " + Integer.toString(playerBoard.getShipsCount()));
            enemyShipsRemaining.setText("Enemy ShipsRemaining: " +Integer.toString(enemyBoard.getShipsCount()));
            playerPercentage.setText("Player Percentage: " +Float.toString(playerBoard.getPercentage()) + "%" );
            enemyPercentage.setText("Enemy Percentage: " +Float.toString(enemyBoard.getPercentage()) + "%" );
            enemyScore.setText("Enemy Score: " +Integer.toString(enemyBoard.getScore()));
            playerScore.setText("Player Score: " +Integer.toString(playerBoard.getScore()));
            
            if (enemyBoard.getShipsCount() == 0) {
                gameRunning = false;
                gameResult.setText("You won the game!");
            }
          
            if (gameRunning && firstTurnPlayer ) {
                enemyMove();
            }
        
    }
    
   
    //Automates enemy moves
    boolean success = false ;
    int r = -1;
    int c = -1;
    int l = -1;  
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
    private int next = 0;    
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
        		row = random.nextInt(10);
        		col = random.nextInt(10);
        		next = 0;
        		initialize = 1;
        	}
        	//START
        	else if (turn == 1 && !strategy) {
        		initialize = 1;
        		next = 0;
        		row = random.nextInt(10);
        		col = random.nextInt(10);
        	}
        	
        	
        	//1st HIT
    		if(success && initialize == 1) {
    			strategy = true;
    			initialize = 2;
    			success = false;
    			next = 0;
    			
    			array [4][0]  = r;
				array [4][1]  = c;   

    			if (r<9) {
    				array [0][0] =  r + 1;
    				array [0][1] =  c;        			
    			}
                if (r>0) {
                	array [1][0] =   r -1;
                	array [1][1] =  c;
                }
                if (c>0) {
                	array [2][0] =  r;
                	array [2][1] = c -1;
                }
                if (c <9) {
                	array [3][0] =  r;
                	array [3][1] =  c + 1;
                }
    		}
        	
        	//MISS_2
          	boolean temp2 = (!success && strategy);
        	if(temp2 && initialize == 2) {
        		row = array [next][0];
        		col = array [next][1];
        		next++;
        	}
        	
        	//2nd HIT
        	boolean temp1 = (success && strategy);
        	if( temp1 && initialize == 2) {
        		
        		initialize = 3;
        		strategy = alive;
        		next = 0;
        		success = true;
        		forbit = true;

        		if (hori == true) {
        			for(int i=1; i<l; i++) {
        				if( array[4][1] + i < 10) {
        					list.add(array[4][1] + i);// length
        				}
            			

            		}
        			for(int i=1; i<l; i++) {
        				if( array[4][1] - i > -1) {
        					list.add(array[4][1] - i);// length
        				}
            			

            		}
        			col = list.get(next);
            		row = array[4][0];
            		next++;

        		}else if (hori == false) {
        			for(int i=1; i<l; i++) {
        				if(array[4][0] + i < 10) {
        					list.add(array[4][0] + i);// length
        				}            			            		
            		}
        			for(int i=1; i<l; i++) {
            			if(array[4][0] - i> -1) {
            				list.add(array[4][0] - i);// length
            			}
            		}
        			col = array[4][1];
        			row = list.get(next);            		
            		next++;
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

        		}  
        }

        	forbit = false;
            field = playerBoard.getField(row, col);
            
            playerRowChoice.setText(Integer.toString(row+1));
            playerColChoice.setText(Integer.toString(col+1));

            if (field.wasShot()) {

            	success = false;
            	enemyTurn = true;
              
            }else {
            	
            boolean goodShot = playerBoard.receiveShot(field);
            enemyTurn = false;
            playerShipsRemaining.setText("Player ShipsRemaining: " + Integer.toString(playerBoard.getShipsCount()));
            enemyShipsRemaining.setText("Enemy ShipsRemaining: " +Integer.toString(enemyBoard.getShipsCount()));
            playerPercentage.setText("Player Percentage: " +Float.toString(playerBoard.getPercentage()) + "%" );
            enemyPercentage.setText("Enemy Percentage: " +Float.toString(enemyBoard.getPercentage()) + "%" );
            enemyScore.setText("Enemy Score: " +Integer.toString(enemyBoard.getScore()));
            playerScore.setText("Player Score: " +Integer.toString(playerBoard.getScore()));
            
            if(enemyStats.size() > 4) {
            	enemyStats.remove(0);
            }
            
            String statsTurn = "Turn: " + Integer.toString(turn) + '\n';;
            
            statsTurn += "Row: " + Integer.toString(row + 1) + '\n';
            statsTurn += "Col: " + Integer.toString(col + 1) + '\n';
            statsTurn += "Hit: " + Boolean.toString(goodShot) + '\n';		
            success = goodShot;
            if(goodShot) {
            	r = row;
            	c = col;
            	l = field.getLength();
            	hori = field.getDirection();
            	alive = field.getAlive();
            	statsTurn += "Name: " + field.getName() + '\n' ;
            }

           
            enemyStats.add(statsTurn);

            }
            
            
            
            
        } while (enemyTurn);
        
        
        if (playerBoard.getShipsCount() == 0) {
            gameRunning = false;
            gameResult.setText("Computer won the game!");
        }
      
        if (gameRunning && !firstTurnPlayer ) {
        	playerMove();
        }
        
      //RANDOM TURN
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

}
