package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Provide information of every function 
 * @author Le Thu Huong
 *
 */
public class InformationMenu extends JFrame implements ActionListener {
	
	private JFrame frame;
	private Font font1,font2,font3;
	private JLabel l1,l2,l3,l4,l5,l6,l7,l8,l9;
	private JButton ok,cancel;
	
	private int card_No,account_No, password_No;
	private String account_Type;
	
	/**
	 * Create GUI(information Menu) 
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	public InformationMenu(int card_No1, int account_No1, int password_No1, String account_Type1) {
		card_No= card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD,25);
		font2 = new Font("Times New Roman", Font.BOLD,20);
		font3 = new Font("Times New Roman",Font.BOLD+Font.ITALIC,20);
		frame.setLayout(null);
		
		frame.setTitle(" INFORMATION MENU ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(350, 50);
		frame.setSize(800,700);
		frame.setResizable(false);
		frame.setVisible(true);
		
		l1 = new JLabel(" INFORMATION MENU ");
		l1.setFont(font1);
		l1.setBounds(250, 100, 500, 40);
		frame.add(l1);
		
		l2 = new JLabel("CASH WITHDRAW: withdraw upto $1000 per day from your account");
		l2.setFont(font2);
		l2.setBounds(50, 160, 700, 40);
		frame.add(l2);
		
		l3 = new JLabel("CASH DEPOSIT: deposit money to your account");
		l3.setFont(font2);
		l3.setBounds(50, 210, 650, 40);
		frame.add(l3);
		
		l4 = new JLabel("SAVING FUNCTION: start saving today and get your pre-pay interest amount");
		l4.setFont(font2);
		l4.setBounds(50, 260, 700, 40);
		frame.add(l4);
		
		l5 = new JLabel("BALANCE ENQUIRY: check your avaiable balance");
		l5.setFont(font2);
		l5.setBounds(50, 310,700, 40);
		frame.add(l5);
		
		l6 = new JLabel("Nouveax Riches ATM's app is a product of Le Thu Huong_ADEO1(EISTI)");
		l6.setFont(font3);
		l6.setBounds(50, 420, 700, 20);
		frame.add(l6);
		
		l6 = new JLabel("Under instruction of Mr. Tahar Gherbi");
		l6.setFont(font3);
		l6.setBounds(50, 450, 700, 20);
		frame.add(l6);
		
		l8 = new JLabel("Thank you for using Nouveaux Riches Bank.For your futher information");
		l8.setFont(font2);
		l8.setBounds(50, 500, 700, 20);
		frame.add(l8);
		
		l9 = new JLabel("Please contact via Toll Free: 06.1234 or visit our official website: www.nrbank.com");
		l9.setFont(font2);
		l9.setBounds(50, 520 ,700, 40);
		frame.add(l9);
		
		ok = new JButton(" OK ");
		ok.setFont(font2);
		ok.setBounds(160, 600 ,150, 40);
		frame.add(ok);
		
		cancel = new JButton(" CANCEL ");
		cancel.setFont(font2);
		cancel.setBounds(500, 600 ,150, 40);
		frame.add(cancel);
		
		ok.addActionListener(this);
		cancel.addActionListener(this);
	}
	
	/**
	 * implements from ActionListener class
	 */
	public void actionPerformed(ActionEvent event) {
		if (event.getSource()== ok) {
			new PrintUserMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
		} else if (event.getSource() == cancel) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
	} 
}
