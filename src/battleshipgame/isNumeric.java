package src.battleshipgame;

public static boolean isNumeric(String str) { 
	  try {  
	    Integer.parseInteger(str);  
	    return true;
	  } catch(NumberFormatException e){  
	    return false;  
	  }  
	}
