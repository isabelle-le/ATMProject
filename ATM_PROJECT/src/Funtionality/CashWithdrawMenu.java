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
 * Cash withdraw to withdraw money from your account
 * used for both accounts
 * @author Le Thu Huong
 *
 */
public class CashWithdrawMenu extends JFrame implements ActionListener {
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
	
	private	float input_Amt,a_Balance,amount,amtMinus,sum_Balance;
	private	int card_No, account_No, password_No;
	private	String account_Type,today_Date;
		
	/**
	 * Create its GUI
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	public CashWithdrawMenu(int card_No1, int account_No1, int password_No1, String account_Type1) {
		card_No = card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
		// get transaction date and time
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
		
		label1 = new JLabel("Give your withdraw amount in the text box");
		label1.setFont(font1);
		label1.setBounds(100,100, 550, 40);
		frame.add(label1);
		
		label2 = new JLabel(" Press ENTER to process ");
		label2.setFont(font1);
		label2.setBounds(100, 220, 500, 40);
		frame.add(label2);
		
		label3 = new JLabel(" Press CLEAR to re-type your withdraw amount");
		label3.setFont(font1);
		label3.setBounds(100, 260, 700, 40);
		frame.add(label3);
		
		label4 = new JLabel(" Press CANCEL to stop this transaction ");
		label4.setFont(font1);
		label4.setBounds(100, 300, 500, 40);
		frame.add(label4);
		
		label5 = new JLabel("Desired withdraw amount(in$):");
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
		
		frame.setTitle(" CASH WITHDRAW");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setLocation(350, 50);
		frame.setVisible(true);
		frame.setResizable(false);
	}

	/**
	 * implements ActionListener class
	 */
	public void actionPerformed (ActionEvent event) {
		
		// user activate enter button to valid withdraw trans
		if(event.getSource()==enterBtn) {
			if ( ((textF.getText()).equals("")))  {
				JOptionPane.showMessageDialog(this, " Please input your desired "
				+ "withdraw amount in the text box","Warning",JOptionPane.WARNING_MESSAGE);
				textF.setText("");
				
			//	user did input int into the withdraw box
			}else {
				input_Amt = 0 ;
				amount = 0;
				input_Amt = Float.parseFloat(textF.getText());
				System.out.println("Client want to withdraw " + input_Amt);
			
				
			/**
			 * TEST: How much money has user withdraw today? Store it into sum_Balance variable
			 * connect to DB and get info
			 * get the account balance and compare with rule : 
			 * minus today withdraw to the existing balance
			 * update this to transaction table
			 * print a ticket
			 */
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
				System.out.println("Great! full control of database");
				ps = con.prepareStatement("SELECT SUM(withdraw_amount) FROM atmdb.transaction WHERE trans_date ="
						+ "" +"'"+ today_Date +"'"+ "AND account_no=" +"'"+account_No+"'"+ "AND card_no="
						+ "" +"'"+ card_No+"'");
				rs = ps.executeQuery();
				while (rs.next()) {
					sum_Balance = 0;
					sum_Balance = sum_Balance + rs.getFloat(1);
					System.out.println("Today, Total withdraw amount of this account is " + sum_Balance);
				}
				
				
				
				/**
				 * RULE: sum_balance : positive value. User did withdraw today
				 * account : saving account  
				 * sum_balance test : Is it smaller than 1000 or not?
				 * user's input test : Is it smaller than 500 or not? Is it greater than 5 and can be divided to 5?
				 * Minus this withdraw to the balance
				 * Update the account detail table and insertion all info to transaction table 
				 * print a receipt
				 */
				if(sum_Balance>1.0) {
					if (account_Type == "saving") {
						if (sum_Balance<=1000) {
							if (input_Amt<5) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! You can not withdraw less than $5 ","Warning",
									JOptionPane.WARNING_MESSAGE);
						     textF.setText("");
								}
								else if(input_Amt>500) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw greater than $500 "
										+ "per time","Warning",JOptionPane.WARNING_MESSAGE);
								textF.setText("");
								}
								else if(input_Amt>5 && (!(input_Amt % 5 ==0))){JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Withdraw amount "
										+ "should be multiple of 5","Warning",JOptionPane.WARNING_MESSAGE);
					            textF.setText("");
								}
						else {
							ps = con.prepareStatement("SELECT * FROM atmdb.accountdetail WHERE card_no =" +"'"+ card_No +"'"+ "AND account_no="
						+"'"+account_No+"'"+ "AND password_no=" +"'"+ password_No +"'" +"AND account_type=" +"'"+ account_Type+ "'");
							rs=ps.executeQuery();
							while(rs.next()) {
							a_Balance = Float.parseFloat(rs.getString(6));
							if (a_Balance>20) {
									if (input_Amt<=(a_Balance-20)) {
										amount=((a_Balance-20)-input_Amt);
										amtMinus=(amount+20);
										st1=con.createStatement();
										st1.executeUpdate("update accountdetail set balance="+amtMinus+" where card_no='"+card_No+"'");
			                            System.out.println("You withdraw rs:"+textF.getText());
					                    st2=con.createStatement();
					                    st2.executeUpdate("INSERT INTO transaction(card_no,account_no,deposit_amount"
											+ ",withdraw_amount,a_balance,trans_date)VALUES('"+card_No+"','"+account_No+"'"
											+ ",0,'"+input_Amt+"','"+amtMinus+"','"+today_Date+"')");
					                    int reply = JOptionPane.showConfirmDialog(null, "Your transaction is in process."
												+ " Do you want a receipt?","Cash Withdraw Message",JOptionPane.YES_NO_OPTION); 
				                    if (reply == JOptionPane.NO_OPTION) {
										new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
										frame.setVisible(false);
				                    }
				                    else if(reply == JOptionPane.YES_OPTION) {
										ps =con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+"'"+
												card_No+"'"+"and account_no="+"'"+account_No+"'");
										rs = ps.executeQuery();
										while(rs.next()) {
											float currentbalance = rs.getFloat(6);
										JOptionPane.showMessageDialog(this, "Your avaiable balance are:" + 
											currentbalance,"Receipt withdraw",JOptionPane.INFORMATION_MESSAGE);	
										new Welcome();
										frame.setVisible(false);
										}
									}
									}///
									else{JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less to withdraw amount","Warning",
											JOptionPane.WARNING_MESSAGE);
									textF.setText("");
										}
				          		}
							else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less,You should keep minimum balance $20",
									"Warning",JOptionPane.WARNING_MESSAGE);
							textF.setText("");
						         }
							}
						}
				      	}
						else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw per day greater than $1000","Warning",
								JOptionPane.WARNING_MESSAGE);
						textF.setText("");
					      	 }
				     	}
					
					
					/**
					 * RULE: sum_balance : positive value. User did withdraw today
					 * account : checking account  
					 * sum_balance test : Is it smaller than 1000 or not?
					 * user's input test : Is it smaller than 500 or not? Is it greater than 5 and can be divided to 5?
					 * Minus this withdraw to the balance
					 * Update the account detail table and insertion all info to transaction table 
					 * print a receipt
					 */					
					
					else if(account_Type == "checking") {
							if (sum_Balance<=1000) {
								if (input_Amt<5) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! You can not withdraw less than $5 ",
										"Warning",JOptionPane.WARNING_MESSAGE);
							     textF.setText("");
									}
									else if(input_Amt>500) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw greater than "
											+ "$500 per time","Warning",JOptionPane.WARNING_MESSAGE);
									textF.setText("");
									}
									else if(input_Amt>5 && (!(input_Amt % 5 ==0))){JOptionPane.showMessageDialog(this,"Withdraw amount "
											+ "should be multiple of 5","Warning",JOptionPane.WARNING_MESSAGE);
						            textF.setText("");
									}
							else {
								ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no =" +"'"+ card_No +"'"+ "AND account_no="
										+"'"+account_No+"'"+ "AND account_type=" +"'"+ account_Type+ "'"+"AND password_no=" +"'"+ password_No +"'");
								rs=ps.executeQuery();
								while(rs.next()) {
								a_Balance = Float.parseFloat(rs.getString(6));
								if (a_Balance>20) {
										if (input_Amt<=(a_Balance-20)) {
											amount=((a_Balance-20)-input_Amt);
											amtMinus=(amount+20);
											st1=con.createStatement();
											st1.executeUpdate("update accountdetail set balance="+amtMinus+" where card_no='"+card_No+"'");
				                            System.out.println("You withdraw rs:"+textF.getText());
						                    st2=con.createStatement();
						                    st2.executeUpdate("INSERT INTO transaction(card_no,account_no,deposit_amount"
												+ ",withdraw_amount,a_balance,trans_date)VALUES('"+card_No+"','"+account_No+"'"
												+ ",0,'"+input_Amt+"','"+amtMinus+"','"+today_Date+"')");
						                    int reply = JOptionPane.showConfirmDialog(null, "Your transaction is in process."
													+ " Do you want a receipt?","Cash Withdraw Message",JOptionPane.YES_NO_OPTION); 
					                    if (reply == JOptionPane.NO_OPTION) {
											new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
											frame.setVisible(false);
					                    }
					                    else if(reply == JOptionPane.YES_OPTION) {
											ps =con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+"'"+
													card_No+"'"+"and account_no="+"'"+account_No+"'");
											rs = ps.executeQuery();
											while(rs.next()) {
												float currentbalance = rs.getFloat(6);
											JOptionPane.showMessageDialog(this, "Your avaiable balance are:" + 
												currentbalance,"Receipt withdraw",JOptionPane.INFORMATION_MESSAGE);	
											new Welcome();
											frame.setVisible(false);
											}
										}
										}///
										else{JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less to withdraw amount","Warning"
												,JOptionPane.WARNING_MESSAGE);
										textF.setText("");
											}
					          		}
								else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less,You should keep minimum balance $20"
										,"Warning",JOptionPane.WARNING_MESSAGE);
								textF.setText("");
							         }
								}
							}
					      	}else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw per day greater than $1000","Warning"
									,JOptionPane.WARNING_MESSAGE);
							textF.setText("");
						      	 }
					     	}
				}
				
				
				
				
				// if record not found on current date
				else {
					
					
				/**
				 * RULE: sum_balance : positive value. User did withdraw today
				 * account : saving account  
				 * sum_balance test : Is it smaller than 1000 or not?
				 * user's input test : Is it smaller than 500 or not? Is it greater than 5 and can be divided to 5?
				 * Minus this withdraw to the balance
				 * Update the account detail table and insertion all info to transaction table 
				 * print a receipt
				 */		
					if (account_Type == "saving") {
						if (sum_Balance<=1000) {
							if (input_Amt<5) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! You can not withdraw less than $5 ","Warning"
									,JOptionPane.WARNING_MESSAGE);
						     textF.setText("");
								}
								else if(input_Amt>500) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw greater than $500 "
										+ " per time","Warning",JOptionPane.WARNING_MESSAGE);
								textF.setText("");
								}
								else if(input_Amt>5 && (!(input_Amt % 5 ==0))){JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Withdraw amount should be"
										+ " multiple of 5","Warning",JOptionPane.WARNING_MESSAGE);
					            textF.setText("");
								}
						else {
							ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no =" +"'"+ card_No +"'"+ "AND account_no=" +
									"'"+account_No+"'"+ "AND account_type=" +"'"+ account_Type+"'"+ "AND password_no=" +"'"+ password_No +"'");
							rs=ps.executeQuery();
							while(rs.next()) {
							a_Balance = Float.parseFloat(rs.getString(6));
							if (a_Balance>20) {
									if (input_Amt<=(a_Balance-20)) {
										amount=((a_Balance-20)-input_Amt);
										amtMinus=(amount+20);
										st1=con.createStatement();
										st1.executeUpdate("update accountdetail set balance="+amtMinus+" where card_no='"+card_No+"'");
			                            System.out.println("You withdraw rs:"+textF.getText());
					                    st2=con.createStatement();
					                    st2.executeUpdate("INSERT INTO transaction(card_no,account_no,deposit_amount"
											+ ",withdraw_amount,a_balance,trans_date)VALUES('"+card_No+"','"+account_No+"'"
											+ ",0,'"+input_Amt+"','"+amtMinus+"','"+today_Date+"')");
					                    int reply = JOptionPane.showConfirmDialog(null, "Your transaction is in process."
												+ " Do you want a receipt?","Cash Withdraw Message",JOptionPane.YES_NO_OPTION); 
				                    if (reply == JOptionPane.NO_OPTION) {
										new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
										frame.setVisible(false);
				                    }
				                    else if(reply == JOptionPane.YES_OPTION) {
										ps =con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+"'"+
												card_No+"'"+"and account_no="+"'"+account_No+"'");
										rs = ps.executeQuery();
										while(rs.next()) {
											float currentbalance = rs.getFloat(6);
										JOptionPane.showMessageDialog(this, "Your avaiable balance are:" + 
											currentbalance,"Receipt withdraw",JOptionPane.INFORMATION_MESSAGE);	
										new Welcome();
										frame.setVisible(false);
										}
									}
									}
									else{JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less to withdraw amount","Warning"
											,JOptionPane.WARNING_MESSAGE);
									textF.setText("");
										}
				          		}
							else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less,You should keep minimum balance $20 ","Warning"
									,JOptionPane.WARNING_MESSAGE);
							textF.setText("");
						         }
							}
						}
				      	}
						else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw per day greater than $1000 ","Warning"
								,JOptionPane.WARNING_MESSAGE);
						textF.setText("");
					      	 }
				     	}
				
					
					/**
					 * RULE: sum_balance : positive value. User did withdraw today
					 * account : checking account  
					 * sum_balance test : Is it smaller than 1000 or not?
					 * user's input test : Is it smaller than 500 or not? Is it greater than 5 and can be divided to 5?
					 * Minus this withdraw to the balance
					 * Update the account detail table and insertion all info to transaction table 
					 * print a receipt
					 */		
					
					else if (account_Type == "checking") {
							if (sum_Balance<=1000) {
								if (input_Amt<5) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! You can not withdraw less than $5 ","Warning"
										,JOptionPane.WARNING_MESSAGE);
							     textF.setText("");
									}
									else if(input_Amt>500) {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw greater than $500 "
											+ "per time","Warning",JOptionPane.WARNING_MESSAGE);
									textF.setText("");
									}
									else if(input_Amt>5 && (!(input_Amt % 5 ==0))){JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Withdraw amount "
											+ "should be multiple of 5","Warning",JOptionPane.WARNING_MESSAGE);
						            textF.setText("");
									}
							else {
								ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no =" +"'"+ card_No +"'"+ "AND account_no=" +
										"'"+account_No+"'"+ "AND account_type=" +"'"+ account_Type+"'"+ "AND password_no=" +"'"+ password_No +"'");
								rs=ps.executeQuery();
								while(rs.next()) {
								a_Balance = Float.parseFloat(rs.getString(6));
								if (a_Balance>20) {
										if (input_Amt<=(a_Balance-20)) {
											amount=((a_Balance-20)-input_Amt);
											amtMinus=(amount+20);
											st1=con.createStatement();
											st1.executeUpdate("update accountdetail set balance="+amtMinus+" where card_no='"+card_No+"'");
				                            System.out.println("You withdraw rs:"+textF.getText());
						                    st2=con.createStatement();
						                    st2.executeUpdate("INSERT INTO transaction(card_no,account_no,deposit_amount"
												+ ",withdraw_amount,a_balance,trans_date)VALUES('"+card_No+"','"+account_No+"'"
												+ ",0,'"+input_Amt+"','"+amtMinus+"','"+today_Date+"')");
						                    int reply = JOptionPane.showConfirmDialog(null, "Your transaction is in process."
													+ " Do you want a receipt?","Cash Withdraw Message",JOptionPane.YES_NO_OPTION); 
					                    if (reply == JOptionPane.NO_OPTION) {
											new BalanceEnquiryMenu(card_No,account_No,password_No,account_Type);
											frame.setVisible(false);
					                    }
					                    else if(reply == JOptionPane.YES_OPTION) {
											ps =con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+"'"+
													card_No+"'"+"and account_no="+"'"+account_No+"'");
											rs = ps.executeQuery();
											while(rs.next()) {
												float currentbalance = rs.getFloat(6);
											JOptionPane.showMessageDialog(this, "Your avaiable balance are:" + 
												currentbalance,"Receipt withdraw",JOptionPane.INFORMATION_MESSAGE);	
											new Welcome();
											frame.setVisible(false);
											}
										}
										}
										else{JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less to withdraw amount","Warning"
												,JOptionPane.WARNING_MESSAGE);
										textF.setText("");
											}
					          		}
								else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your balance is less,You should keep minimum balance $20 "
										,"Warning",JOptionPane.WARNING_MESSAGE);
								textF.setText("");
							         }
								}
							}
					      	}
							else {JOptionPane.showMessageDialog(this,"CAN NOT PROCESS! Your can not withdraw per day greater than $1000 ","Warning"
									,JOptionPane.WARNING_MESSAGE);
							textF.setText("");
						      	 }
					     	}
					}
					con.close();
				}
			catch(Exception sqle){
				System.out.println(sqle);
			}
			}
		}
			
		//Clear button activated, clear all input
		else if (event.getSource() == clearBtn) {
			textF.setText("");
		}
		
		// Exit button activated
		else if (event.getSource()==cancelBtn) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
	}
}
