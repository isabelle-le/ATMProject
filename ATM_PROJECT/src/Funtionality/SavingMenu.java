package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
* It's a saving menu, it presented in its own GUI 
 * @author LE THU HUONG
 *
 */

public class SavingMenu extends JFrame implements ActionListener {
	
	private JFrame frame;
	private Font font1,font2,font3;
	private JLabel l1,l2,l3,l4,l5,l6;
	private JTextField inputAmt;
	private JButton m3,m6,m12,start,cancel;
	
	private Connection con;
	private PreparedStatement ps;
	private Statement st1,st2,st3;
	private ResultSet rs;
	
	private Date date;
	private GregorianCalendar calendar;
	
	private float input_Amt, amount,a_Balance;
	private float savAccBalance;
	private	int card_No, account_No, password_No;
	private String account_Type, today_Date, end_Date;
	private double interest_Amt;
	private double interestRate3m;
	private double interestRate6m;
	private double interestRate12m;
	
	/**
	 * Create GUI(SavingFunctionMenu) 
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	
	public SavingMenu(int card_No1, int account_No1, int password_No1, String account_Type1) {
		// create a new saving account
		card_No = card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
		//get current date 
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		today_Date = calendar.get(Calendar.YEAR)+"-"+ (calendar.get(Calendar.MONTH)+1) + "-" + (calendar.get(Calendar.DATE));
		System.out.println(today_Date);
		
		//construct the GUI
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD,25);
		font2 = new Font("Times New Roman", Font.BOLD + Font.ITALIC,25);
		font3 = new Font("Times New Roman", Font.BOLD,20);
		frame.setLayout(null);
		
		frame.setTitle(" SAVING MENU ");
		frame.setLocation(350, 50);
		frame.setSize(800,700);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.setResizable(false);
		
		l1 = new JLabel(" SAVING MENU ");
		l1.setFont(font1);
		l1.setBounds(300, 80,500, 40);
		frame.add(l1);
		
		l2 = new JLabel("3 months saving with interest rate of 1.5%");
		l2.setFont(font2);
		l2.setBounds(120, 150,700, 40);
		frame.add(l2);
		
		l3 = new JLabel("6 months saving with interest rate of 3.33%");
		l3.setFont(font2);
		l3.setBounds(120, 200,700, 40);
		frame.add(l3);
		
		l4 = new JLabel("12 months saving with interest rate of 6.66%");
		l4.setFont(font2);
		l4.setBounds(120, 250,700, 40);
		frame.add(l4);
		
		l5 = new JLabel("DESIRED SAVING AMOUNT IN $");
		l5.setFont(font1);
		l5.setBounds(50,350,450, 40);
		frame.add(l5);
		
		inputAmt = new JTextField();
		inputAmt.setBounds(450,350,250,40);
		frame.add(inputAmt);
		
		l6 = new JLabel("PLEASE SELECT YOUR DESIRED SAVING PERIOD");
		l6.setFont(font1);
		l6.setBounds(50, 400,700, 40);
		frame.add(l6);
		
		m3 = new JButton(" 3 MONTHS ");
		m3.setFont(font3);
		m3.setBounds(50, 450, 200, 40);
		frame.add(m3);
		
		m6 = new JButton(" 6 MONTHS ");
		m6.setFont(font3);
		m6.setBounds(300, 450, 200, 40);
		frame.add(m6);
		
		m12 = new JButton(" 12 MONTHS ");
		m12.setFont(font3);
		m12.setBounds(540, 450, 200, 40);
		frame.add(m12);
		
		start = new JButton(" START SAVING ");
		start.setFont(font3);
		start.setBounds(50, 570, 300, 40);
		frame.add(start);
		
		cancel = new JButton(" CANCEL ");
		cancel.setFont(font3);
		cancel.setBounds(530, 570, 200, 40);
		frame.add(cancel);
		
		m3.addActionListener(this);
		m6.addActionListener(this);
		m12.addActionListener(this);
		start.addActionListener(this);
		cancel.addActionListener(this);
	}
	
	/**
	 * implements from ActionListener class
	 */
	public void actionPerformed (ActionEvent event) {
		
		// Given value to each period button (3 months, 6 months and 12 months)
		if (event.getSource()== m3) {
			 interestRate3m = 0.015;
			System.out.println(interestRate3m);
			
		}else if (event.getSource()== m6) {
			 interestRate6m = 0.033;
			System.out.println(interestRate6m);
			
		}else if (event.getSource() == m12) {
			 interestRate12m = 0.066;
			System.out.println(interestRate12m);
			
			
		//Start-button activated
		}else if (event.getSource()== start) {
			
			//no input from user
			if(((inputAmt.getText()).equals(""))) {
				JOptionPane.showMessageDialog(this, " Please input your desired "
				+ "withdraw amount in the text box","Warning",JOptionPane.WARNING_MESSAGE);
				inputAmt.setText("");
				
			//re-check account type
			}else if (account_Type !="saving"){
				JOptionPane.showMessageDialog(this, " Do you want to have a saving account? Please contact your nearest branch to create "
						+ " a saving account ","Warning",JOptionPane.WARNING_MESSAGE);
			}else if(account_Type =="saving"){
				input_Amt = 0;
				amount = 0;
				input_Amt = Float.parseFloat(inputAmt.getText());
				System.out.println(input_Amt);
				
				
				
			//connect to database to get current balance	
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
				ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no=" + "'" + card_No + "'"+ "AND account_no=" +"'"+account_No +"'");
				rs = ps.executeQuery();
			while(rs.next()) {
				savAccBalance = rs.getFloat(6);
				System.out.println(savAccBalance);
				
			//user input must be a positive number
			if(input_Amt>0) {
				
				/*if input amount is less than it's balance and monthly interest rate is 0.015
				 *(3months saving option)
				 */
				if(input_Amt<savAccBalance && interestRate3m == 0.015) {
					
					//bank collects the money first 
					amount = savAccBalance - input_Amt; 
					st1 = con.createStatement();
					st1.executeUpdate("UPDATE accountdetail SET balance = " + amount+"WHERE card_no='" + card_No +"'");
					System.out.println("You want to save " + inputAmt.getText());
					
					//insert transaction to saving table
					interest_Amt = input_Amt * interestRate3m; 
					float i = (float)interest_Amt;
					a_Balance = input_Amt + i;
					end_Date=  calendar.get(Calendar.YEAR)+"-"+ (calendar.get(Calendar.MONTH)+4) + "-" + (calendar.get(Calendar.DATE));
					st2 = con.createStatement();
					st2.executeUpdate("INSERT INTO saving(card_no,account_no,saving_amount,interest_amt,a_balance,trans_date, end_date) VALUE('"+
					card_No+"','"+ account_No +"','"+ input_Amt+"','"+ i +"','"+a_Balance+"','"+today_Date+"','"+end_Date+"')");
					
					//Pre-pay interest to account
					st3 = con.createStatement();
					st3.executeUpdate("UPDATE accountdetail SET balance = " +amount+"WHERE card_no='" + card_No +"'");
						
					//print a receipt for user (using J panel)
					int reply = JOptionPane.showConfirmDialog(null, " Your transaction is in process."
							+ " Do you want a receipt? ","Saving Message",JOptionPane.YES_NO_OPTION); 
						if (reply == JOptionPane.NO_OPTION) {
							new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
							frame.setVisible(false);
						}
						else if(reply == JOptionPane.YES_OPTION) {
							ps =con.prepareStatement("SELECT * FROM saving WHERE card_no="+"'"+card_No+"'"+"and account_no="+
						"'"+account_No+"'"+"and saving_amount="+"'"+input_Amt+"'"+"and interest_amt="+"'"+i+"'"+"and a_balance="+"'"+a_Balance+"'"
						+"and trans_date="+"'"+today_Date+"'"+"and end_date="+"'"+end_Date+"'");
							rs = ps.executeQuery();
						while(rs.next()) {
							float savingAmt = rs.getFloat(4);
							float interestAmt = rs.getFloat(5); 
							float currentBalance = rs.getFloat(6);
							String endDate = rs.getString(8);
						JOptionPane.showMessageDialog(this, "You had create saving $ "+ savingAmt + " . With interest $ "+interestAmt+
								" . Your saving account balance is $ " + currentBalance + " on the day " + endDate ,"Receipt saving",JOptionPane.INFORMATION_MESSAGE);	
						new Welcome();
						frame.setVisible(false);
						}
						}
				}//END 3 months	
				
				
				/*if input amount is less than it's balance and monthly interest rate is 0.033
				 *( 6 months saving option)
				 */
				else if(input_Amt<savAccBalance &&  interestRate6m == 0.033) {
					
					//bank collects the saving amount first 
					amount = savAccBalance - input_Amt; 
					st1 = con.createStatement();
					st1.executeUpdate("UPDATE accountdetail SET balance = " + amount+"WHERE card_no='" + card_No +"'");
					System.out.println("You want to save " + inputAmt.getText());
					
					//insert transaction to saving table
					interest_Amt = input_Amt * interestRate6m; 
					float i = (float)interest_Amt;
					a_Balance = input_Amt + i;
					end_Date=  calendar.get(Calendar.YEAR)+"-"+ (calendar.get(Calendar.MONTH)+7) + "-" + (calendar.get(Calendar.DATE));
					st2 = con.createStatement();
					st2.executeUpdate("INSERT INTO saving(card_no,account_no,saving_amount,interest_amt,a_balance,trans_date, end_date) VALUE('"+
					card_No+"','"+ account_No +"','"+ input_Amt+"','"+ i +"','"+a_Balance+"','"+today_Date+"','"+end_Date+"')");
					
					//Pre-pay interest to account
					st3 = con.createStatement();
					st3.executeUpdate("UPDATE accountdetail SET balance = " +amount+"WHERE card_no='" + card_No +"'");
						
					//print a receipt for user (using J panel)
					int reply = JOptionPane.showConfirmDialog(null, " Your transaction is in process."
							+ " Do you want a receipt? ","Saving Message",JOptionPane.YES_NO_OPTION); 
						if (reply == JOptionPane.NO_OPTION) {
							new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
							frame.setVisible(false);
						}
						else if(reply == JOptionPane.YES_OPTION) {
							ps =con.prepareStatement("SELECT * FROM saving WHERE card_no="+"'"+card_No+"'"+"and account_no="+
									"'"+account_No+"'"+"and saving_amount="+"'"+input_Amt+"'"+"and interest_amt="+"'"+i+"'"+"and a_balance="+"'"+a_Balance+"'"
									+"and trans_date="+"'"+today_Date+"'"+"and end_date="+"'"+end_Date+"'");
							rs = ps.executeQuery();
						while(rs.next()) {
							float savingAmt= rs.getFloat(4);
							float interestAmt= rs.getFloat(5); 
							float currentBalance= rs.getFloat(6);
							String endDate= rs.getString(8);
							JOptionPane.showMessageDialog(this, "You had create saving $ "+ savingAmt+ " . With interest $ "+interestAmt+
									" . Your saving account balance is $ " + currentBalance+ " on the day " + endDate,"Receipt saving",JOptionPane.INFORMATION_MESSAGE);	
							new Welcome();
							frame.setVisible(false);
						}
						}
				}//END 6 months	
				
				
				/*if input amount is less than it's balance and monthly interest rate is 0.066
				 *( 12 months saving option)
				 */
				else if(input_Amt<savAccBalance &&  interestRate12m == 0.066) {
					
					//bank collects the saving amount first 
					amount = savAccBalance - input_Amt; 
					st1 = con.createStatement();
					st1.executeUpdate("UPDATE accountdetail SET balance = " + amount+"WHERE card_no='" + card_No +"'");
					System.out.println("You want to save " + inputAmt.getText());
					
					//insert transaction to saving table
					interest_Amt = input_Amt * interestRate12m; 
					float i = (float)interest_Amt;
					a_Balance = input_Amt + i;
					end_Date= (calendar.get(Calendar.YEAR)+1)+"-"+ (calendar.get(Calendar.MONTH)+1) + "-" + (calendar.get(Calendar.DATE));
					st2 = con.createStatement();
					st2.executeUpdate("INSERT INTO saving(card_no,account_no,saving_amount,interest_amt,a_balance,trans_date, end_date) VALUE('"+
					card_No+"','"+ account_No +"','"+ input_Amt+"','"+ i +"','"+a_Balance+"','"+today_Date+"','"+end_Date+"')");
					
					//Pre-pay interest to account
					st3 = con.createStatement();
					st3.executeUpdate("UPDATE accountdetail SET balance = " +amount+"WHERE card_no='" + card_No +"'");
						
					//print a receipt for user (using J panel)
					int reply = JOptionPane.showConfirmDialog(null, " Your transaction is in process."
							+ " Do you want a receipt? ","Saving Message",JOptionPane.YES_NO_OPTION); 
						if (reply == JOptionPane.NO_OPTION) {
							new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
							frame.setVisible(false);
						}
						else if(reply == JOptionPane.YES_OPTION) {
							ps =con.prepareStatement("SELECT * FROM saving WHERE card_no="+"'"+card_No+"'"+"and account_no="+
									"'"+account_No+"'"+"and saving_amount="+"'"+input_Amt+"'"+"and interest_amt="+"'"+i+"'"+"and a_balance="+"'"+a_Balance+"'"
									+"and trans_date="+"'"+today_Date+"'"+"and end_date="+"'"+end_Date+"'");
							rs = ps.executeQuery();
						while(rs.next()) {
							float savingAmt= rs.getFloat(4);
							float interestAmt= rs.getFloat(5); 
							float currentBalance= rs.getFloat(6);
							String endDate= rs.getString(8);
						JOptionPane.showMessageDialog(this, "You had create saving $ "+ savingAmt+ " . With interest $ "+interestAmt+
								" . Your saving account balance is $ " + currentBalance+ " on the day " + endDate,"Receipt saving",JOptionPane.INFORMATION_MESSAGE);	
						new Welcome();
						frame.setVisible(false);
						}
						}
						
				}//END 12 months	
				
				
				//WARNING : User's input > balance	
				else {
					JOptionPane.showMessageDialog(this, " CAN NOT PROCESS! Your do not have enough balance to process"
							,"Warning",JOptionPane.WARNING_MESSAGE);
					inputAmt.setText("");
				}
				
				//WARNING: a negative saving from user
				}else {
					JOptionPane.showMessageDialog(this, " CAN NOT PROCESS! Please input a positive number to process"
							,"Warning",JOptionPane.WARNING_MESSAGE);
					inputAmt.setText("");
				}
			}
			con.close();	
			
			//catch error (if any)
			} catch (Exception sqle) {
				System.out.println(sqle);
			}
				}
			
		//cancel button activated
		}else if (event.getSource() == cancel) {
			JOptionPane.showMessageDialog(this, " Thank you and see you again ");
			new Welcome();
			frame.setVisible(false);
		}
	}
//END
}
