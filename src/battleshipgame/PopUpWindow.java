package src.battleshipgame;

import java.awt.Container;
import src.battleshipgame.controller.BattleshipController;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;

import java.awt.Container;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;

public class PopUpWindow {
	

	
	public static void display(String title, String message) {
		Stage window= new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle(title);
		window.setMinWidth(350);
		
		Label label = new Label();
		label.setText(message);
		Button closeButton = new Button("Close the window");
		closeButton.setOnAction(e -> window.close());
		
		VBox layout = new VBox(10);
		layout.getChildren().addAll(label, closeButton);
		layout.setAlignment(Pos.CENTER);
		
		Scene scene = new Scene(layout);
		window.setScene(scene);
		window.showAndWait();
	}
	
	//private JFrame ourFrame = new JFrame("Enemy Ships");
	
	public static String[] str = new String[2];
	
	public static String[] SignUpForm(String title){
		
	str = new String[2];
	Stage window= new Stage();
	window.initModality(Modality.APPLICATION_MODAL);
	window.setTitle(title);
	window.setMinWidth(350);
	
	//String[] string = new String[2];
	Label label0 = new Label();
	Label label1 = new Label();
	Label label2 = new Label();
	Label label3 = new Label();
	Label label4 = new Label();
	
	label0.setText("Proper Form General Example: " + '\n' + "medialab/NAME_OF_FILLE.txt");
	label1.setText("Enemy Path");
	label2.setText("Proper Form Example: " + '\n' + "medialab/enemy_default.txt");
	label3.setText("Player Path");
	label4.setText("Proper Form Example: " + '\n' + "medialab/player_default.txt");
	
	TextField path1 = new TextField();
	TextField path2 = new TextField();
	
	Button closeButton = new Button("Load");
	closeButton.setOnAction(e -> {
		str[0] = path1.getText();		
		str[1] = path2.getText();
		//System.out.println(str[0]);
        //System.out.println(str[1]);
		window.close();
	});
	
	VBox layout = new VBox(10);
	layout.getChildren().addAll(label0, label1 , label2, path1, label3, label4, path2, closeButton); 
	
	Scene scene = new Scene(layout);
	window.setScene(scene);
	window.showAndWait();
	
	return str;
	}
       
		/*JTextArea textArea = new JTextArea();
		textArea.setEditable(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
		scrollPane.requestFocus();
		textArea.requestFocusInWindow();
		scrollPane.setPreferredSize(new Dimension(800, 600));
		JOptionPane.showMessageDialog((Control) BattleshipController.controller.control, scrollPane,"Paste Info", JOptionPane.PLAIN_MESSAGE);
		String info = textArea.getText(); 
		
		ourFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		ourFrame.setBounds(200, 100, 400, 200);
		
		Container container = ourFrame.getContentPane();
		container.setLayout(null);
		
		JLabel logo = new JLabel("The remaining ships of the enemy are:");
		logo.setBounds(60,5,250,30);
		
		JLabel name_label = new JLabel("Player :");
		name_label.setBounds(20,30,250,30);
		
		JLabel email_label = new JLabel("Enemy :");
		email_label.setBounds(20,60,250,30);
		
		JTextField nameText = new JTextField();
		nameText.setBounds(65,30,250,30);
		
		JTextField emailText = new JTextField();
		emailText.setBounds(65, 60, 250, 30);
		
		JButton subBtn  = new JButton("Play");
		subBtn.setBounds(150,90,100,30);
		
		container.add(logo);
		container.add(name_label);
		container.add(email_label);
		container.add(nameText);
		container.add(emailText);
		container.add(subBtn);
		ourFrame.setVisible(true);
		
	}*/

}

