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
 * Cash Deposit allows user to deposit cash to their account
 * Used fro both accounts
 * @author Le Thu Huong
 *
 */
public class CashDepositMenu extends JFrame implements ActionListener{
	private JFrame frame;
	private Font font1,font2;
	private JTextField textF;
	private JLabel label1,label2,label3,label4,label5;
	private JButton enterBtn,clearBtn, cancelBtn;
	
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	private Statement st1,st2;
	
	private Date date;
	private GregorianCalendar calendar;
	
	private	float input_Amt,a_Balance,amount,amtPlus,cur_Balance;
	private	int card_No, account_No, password_No;
	private	String account_Type,today_Date;
		
	/**
	 * Create its GUI
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	public CashDepositMenu(int card_No1, int account_No1, int password_No1, String account_Type1) {
		card_No = card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
		// get activity's time and date 
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		today_Date = calendar.get(Calendar.YEAR)+ "-" +(calendar.get(Calendar.MONTH)+1) + "-" + calendar.get(Calendar.DATE);
		System.out.println("Transaction date is "+ today_Date);
		System.out.println("Acount type is " + account_Type);
		
		
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD, 25);
		font2 = new Font("Times New Roman", Font.BOLD, 20);
		frame.setLayout(null);
		
		label1 = new JLabel("Give your deposit amount in the text box");
		label1.setFont(font1);
		label1.setBounds(100,100, 550, 40);
		frame.add(label1);
		
		label2 = new JLabel(" Press ENTER to process ");
		label2.setFont(font1);
		label2.setBounds(100, 220, 500, 40);
		frame.add(label2);
		
		label3 = new JLabel(" Press CLEAR to re-type your deposit amount");
		label3.setFont(font1);
		label3.setBounds(100, 260, 700, 40);
		frame.add(label3);
		
		label4 = new JLabel(" Press CANCEL to stop this transaction ");
		label4.setFont(font1);
		label4.setBounds(100, 300, 500, 40);
		frame.add(label4);
		
		label5 = new JLabel("Desired deposit amount(in$):");
		label5.setFont(font1);
		label5.setBounds(100, 420, 500, 40);
		frame.add(label5);
		
		textF = new JTextField();
		textF.setFont(font2);
		textF.setBounds(450, 420,250,40);
		frame.add(textF);
		
		enterBtn = new JButton(" ENTER ");
		enterBtn.setFont(font2);
		enterBtn.setBounds(100,550,150,40);
		frame.add(enterBtn);
		
		clearBtn = new JButton(" CLEAR ");
		clearBtn.setFont(font2);
		clearBtn.setBounds(330,550,150,40);
		frame.add(clearBtn);
		
		cancelBtn = new JButton(" CANCEL ");
		cancelBtn.setFont(font2);
		cancelBtn.setBounds(560,550,150,40);
		frame.add(cancelBtn);
		
		enterBtn.addActionListener(this);
		clearBtn.addActionListener(this);
		cancelBtn.addActionListener(this);
		
		frame.setTitle(" CASH DEPOSIT ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setLocation(350, 50);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	/**
	 * implements from ActionListener class
	 */
	public void actionPerformed(ActionEvent event) {
		// user activate the enter button
		if(event.getSource()==enterBtn) {
			
			// no input from user
			if ( ((textF.getText()).equals(""))) {
				JOptionPane.showMessageDialog(this, " Please input your desired "
				+ "deposit amount in the text box","Warning",JOptionPane.WARNING_MESSAGE);
				textF.setText("");
			}
			
			// user did inputed the value
			else {
				input_Amt = 0 ;
				amount = 0;
				input_Amt = Float.parseFloat(textF.getText());
				System.out.println("Client want to deposit " + input_Amt);
			
			/**
			 * connect to DB and get info
			 * plus today input to current balance 
			 * insertion all info to transaction table
			 * print out a ticket
			 */
			try {
				float cur_Balance = 0;
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
				System.out.println("Great! full control of database");
				ps = con.prepareStatement("SELECT * FROM atmdb.accountdetail WHERE card_no =" +"'"+ card_No +"'"+ "AND account_no=" +
						"'"+ account_No +"'");
				rs=ps.executeQuery();
				while(rs.next()) {
				cur_Balance = Float.parseFloat(rs.getString(6));
				}
				amtPlus=cur_Balance+input_Amt;
				st1=con.createStatement();
				st1.executeUpdate("update accountdetail set balance="+amtPlus+" where card_no='"+card_No+"'");
                System.out.println("You deposit rs:"+textF.getText());
                st2=con.createStatement();
                st2.executeUpdate("INSERT INTO transaction(card_no,account_no,deposit_amount"
								+ ",withdraw_amount,a_balance,trans_date)VALUES('"+card_No+"','"+account_No+"','"+input_Amt+ "',0,'"+amtPlus+"','"+today_Date+"')");
                
                //print a ticket
                int reply = JOptionPane.showConfirmDialog(null, "Your transaction is in process."
						+ " Do you want a receipt?","Cash Deposit Message",JOptionPane.YES_NO_OPTION); 
                if (reply == JOptionPane.NO_OPTION) {
				new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
				frame.setVisible(false);
                }
                else if(reply == JOptionPane.YES_OPTION) {
				ps =con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+"'"+
						card_No+"'"+"and account_no="+"'"+account_No+"'");
				rs = ps.executeQuery();
				while(rs.next()) {
					float newbalance = rs.getFloat(6);
				JOptionPane.showMessageDialog(this, "Your avaiable balance are: " + 
					newbalance,"Receipt Deposit",JOptionPane.INFORMATION_MESSAGE);	
				new Welcome();
				frame.setVisible(false);
				}
			}
						
            con.close();
			}
			
			catch(Exception sqle){
				System.out.println(sqle);
			}
			
			}
		}
		
		//user activate clear button to clear the text box
		else if (event.getSource() == clearBtn) {
			textF.setText("");
		}
	
		// user activate exit button
		else if (event.getSource()==cancelBtn) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
		
	}
}
