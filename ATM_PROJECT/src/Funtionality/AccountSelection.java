package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/**
 * Account selection class used for get account type of user 
 * @author Le Thu Huong
 *
 */
public class AccountSelection extends JFrame implements ActionListener{
	
	private JFrame frame;
	private Font font1,font2;
	private JLabel label;
	private JButton sAcct, cAcct, returnButton;
	private ButtonGroup group;
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
	private	int card_No,account_No,password_No;
	private String account_Type,account_Type_Return;

	/**
	 * build its GUI
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 */
	public AccountSelection(int card_No1, int account_No1, int password_No1) {
		card_No = card_No1;
		password_No = password_No1;
		account_No = account_No1;
		
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD,25);
		font2 = new Font("Times New Roman", Font.BOLD,20);
		frame.setLayout(null);
		
		label = new JLabel("Please select your desired account to process");
		label.setFont(font1);
		label.setBounds(100, 160, 650, 40);
		frame.add(label);
		
		sAcct = new JButton("SAVING ACCOUNT");
		sAcct.setFont(font2);
		sAcct.setBounds(100, 350, 260, 40);
		frame.add(sAcct);
		
		cAcct = new JButton("CHECKING ACCOUNT");
		cAcct.setFont(font2);
		cAcct.setBounds(430, 350, 260, 40);
		frame.add(cAcct);
		
		returnButton = new JButton("RETURN");
		returnButton.setFont(font2);
		returnButton.setBounds(260, 500, 260, 40);
		frame.add(returnButton);
		
		group = new ButtonGroup();
		group.add(sAcct);
		group.add(cAcct);
		group.add(returnButton);

		sAcct.addActionListener(this);
		cAcct.addActionListener(this);
		returnButton.addActionListener(this);
		
		frame.setTitle("ACCOUNT SELECTION...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setLocation(350, 50);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * actionPerormed, which implements from ActionListener class
	 * examine user account in the databse and store in account_Type variable
	 */
	public void actionPerformed(ActionEvent event) {
		//user activated saving account
		if (event.getSource() == sAcct) {
			
			// examine user's input and info from the database
			try {
				int x = 0;
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
				System.out.println("Great! full control of database");
				ps = con.prepareStatement("SELECT account_type FROM accountdetail WHERE card_no = " +"'" + card_No+ 
						"'" + "and password_no =" +"'" +password_No+"'"+ "and account_no =" +"'"+account_No+"'");
				rs = ps.executeQuery();
				
				while (rs.next()) {
					account_Type_Return =rs.getString(1);
					System.out.println("Account_Type is " +account_Type_Return);
					x = 1;
				}
				
				// saving account matched
				if(x==1){
					if(account_Type_Return.equals("saving"))
	             	{
	                  account_Type="saving";
	             	  System.out.println("Client chosed to use account" +account_Type_Return);
	             	 new PrintUserMenu(card_No, account_No, password_No,account_Type);
			       	 frame.setVisible(false);
	             	}
					
					// it is not a saving account
					else { 
						JOptionPane.showMessageDialog(this, "Sorry for inconvenience, you do not have a saving account"
								+ "\n "+ " Please contact your bank to free saving account opening ");
						frame.setVisible(true);
					}
				}
				con.close();
				
			}catch(Exception sqle){
				System.out.println("Sorry for inconvenience, please recheck your jdbc mysql connection");
			}
			
			
		// user activate checking account	
		}else if (event.getSource() == cAcct) {
			// examine user's input and info from the database
			try {
				int x = 0;
				
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
				System.out.println("Great! full control of database");
				ps = con.prepareStatement("SELECT account_type FROM accountdetail WHERE card_no = " +"'" + card_No+ 
						"'" + "and password_no =" +"'" +password_No+"'"+ "and account_no =" +"'"+account_No+"'");
				rs = ps.executeQuery();
				
				while (rs.next()) {
					account_Type_Return =rs.getString(1);
					System.out.println("Account_Type is " +account_Type_Return);
					x = 1;
				}
				
				// checking acc is matched
				if(x==1){
					if(account_Type_Return.equals("checking"))
	             	{
	                  account_Type="checking";
	             	  System.out.println("Account_Type is " +account_Type_Return);
	             	  new PrintUserMenu(card_No, account_No, password_No,account_Type);
			       	  frame.setVisible(false);
	             	}
					
					// it is not a checking acc
					else {
						JOptionPane.showMessageDialog(this, "Sorry for inconvenience, you do not have a saving account"
								+ "\n "+ " Please contact your bank to free checking account opening ");
						frame.setVisible(true);
					}
				}
				con.close();
				
			}catch(Exception sqle){
				System.out.println("Sorry for inconvenience, please recheck your jdbc mysql connection");
			}
		
			
		// user activated exit button	
		}else if (event.getSource() == returnButton) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
	}
}
