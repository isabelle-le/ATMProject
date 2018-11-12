package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 * Welcome gui, contains main function
 * @author Le Thu Huong
 *
 */

public class Welcome extends JFrame implements ActionListener{
	private JFrame frame;
	private Font font1,font2;
	private JLabel label1,label2,label3;
	private JButton enterButton,exitButton;

	/**
	 * Create its GUI 
	 */
	public Welcome() {
		frame = new JFrame();
		font1 = new Font("Times New Roman",Font.BOLD,25); //label's font
		font2 = new Font("Times New Roman",Font.BOLD,20); //button's font
		frame.setLayout(null);
		
		label1 = new JLabel("WELCOME TO NOUVEAUX RICHES BANK");
		label1.setFont(font1);
		label1.setBounds(120,200,580,40);
		frame.add(label1);
		
		label2 = new JLabel("OFFLINE ATM");
		label2.setFont(font1);
		label2.setBounds(300,250,300,40);
		frame.add(label2);
		
		label3 = new JLabel("Press ENTER to use ATM or press EXIT to quit");
		label3.setFont(font1);
		label3.setBounds(120,400,580,40);
		frame.add(label3);
		
		enterButton = new JButton("ENTER");
		enterButton.setFont(font2);
		enterButton.setBounds(200,500,130,40);
		frame.add(enterButton);
		enterButton.addActionListener(this);
		
		exitButton = new JButton("EXIT");
		exitButton.setFont(font2);
		exitButton.setBounds(450,500,130,40);
		frame.add(exitButton);
		exitButton.addActionListener(this);
		
		frame.setTitle("WELCOME TO NOUVEAUX RICHES BANK");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setLocation(350,50);
		frame.setResizable(false);
		frame.setVisible(true);	
	}
	
	/**
	 * actionPerormed, which implements from ActionListener class
	 */
	public void actionPerformed(ActionEvent event){
		if (event.getSource()==enterButton) {
			new Login();
			frame.setVisible(false);
		}else if (event.getSource()==exitButton) {
			System.exit(0);
		}
	}
	
	/**
	 * create an object
	 * @param args
	 */
	public static void main(String args[]){
		Welcome welcome = new Welcome();
	}
}
