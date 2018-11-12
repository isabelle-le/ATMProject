package Funtionality;

import java.awt.Color;
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

/**
 * Balance Enquiry  shows account information at the exact activated date and time
 * @author Le Thu Huong
 *
 */
public class BalanceEnquiryMenu extends JFrame implements ActionListener {
	private JFrame frame;
	private Font font1,font2;
	private JLabel label,dateLabel,timeLabel,cardLabel,avaiableBalance,l1,l2,l3,l4,text1,text2,text3;
	private JButton ok,cancel;
	
	private Connection con;
	private PreparedStatement ps;
	private Statement st;
	private ResultSet rs;
	
	private int card_No,account_No, password_No;
	private String account_Type,strDate,strTime;
	
	private Date date;
	private GregorianCalendar calendar;
	
	/**
	 * Create its GUI
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	public BalanceEnquiryMenu(int card_No1, int account_No1, int password_No1, String account_Type1) {
		card_No= card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
	
		//get current date and time
		date = new Date();
		calendar = new GregorianCalendar();
		calendar.setTime(date);
		strDate = calendar.get(Calendar.YEAR)+"-"+ (calendar.get(Calendar.MONTH)+1) +"-" + (calendar.get(Calendar.DATE));
		strTime = date.getHours() +":" + date.getMinutes() +":" + date.getSeconds();
		System.out.println(strDate);
		System.out.println(strTime);
		
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD,25);
		font2 = new Font("Times New Roman", Font.BOLD,20);
		frame.setLayout(null);
		
		frame.setTitle("BANK ENQUIRY");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocation(350, 50);
		frame.setSize(800,700);
		frame.setResizable(false);
		frame.setVisible(true);
		
		label = new JLabel(" YOUR BALANCE ENQUIRY ");
		label.setFont(font1);
		label.setBounds(200, 100,500, 40);
		frame.add(label);
		
		dateLabel = new JLabel("DATE");
		dateLabel .setFont(font1);
		dateLabel .setBounds(100, 200,250, 40);
		frame.add(dateLabel );
		
		l1 = new JLabel(strDate);
		l1 .setFont(font1);
		l1.setForeground(Color.BLUE);
		l1.setBounds(400,200,200, 40);
		frame.add(l1);
		
		timeLabel = new JLabel("TIME");
		timeLabel .setFont(font1);
		timeLabel .setBounds(100, 250,250, 40);
		frame.add(timeLabel );
		
		l2 = new JLabel(strTime);
		l2 .setFont(font1);
		l2.setForeground(Color.BLUE);
		l2.setBounds(400, 250,200, 40);
		frame.add(l2);
		
		cardLabel = new JLabel("ATM CARD No");
		cardLabel .setFont(font1);
		cardLabel .setBounds(100, 300,250, 40);
		frame.add(cardLabel );
		
		l3 = new JLabel();
		l3 .setFont(font1);
		l3.setForeground(Color.BLUE);
		l3.setBounds(400, 300,200, 40);
		frame.add(l3);
		
		avaiableBalance = new JLabel("BALANCE IN $");
		avaiableBalance .setFont(font1);
		avaiableBalance .setBounds(100, 350,300, 40);
		frame.add(avaiableBalance );
		
		l4 = new JLabel();
		l4 .setFont(font1);
		l4.setForeground(Color.BLUE);
		l4.setBounds(400, 350,200, 40);
		frame.add(l4);
		
		text1 = new JLabel("Thank you for using Nouveaux Riches Bank.For your futher information");
		text1.setFont(font2);
		text1 .setBounds(50, 500,700, 20);
		frame.add(text1);
		
		text1 = new JLabel("Please contact via Toll Free: 06.1234 or visit our official website: www.nrbank.com");
		text1.setFont(font2);
		text1 .setBounds(50, 530 ,700, 20);
		frame.add(text1);
		
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
		
		/**
		 * connect to database and select account's info
		 * assigned info to variable and print in GUI
		 */
		
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
			System.out.println("Great! full control of database");
			ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no="+ "'"+ account_No + "'" +
			"AND password_no=" +"'"+ password_No+"'");
			rs = ps.executeQuery();
			while(rs.next()) {
				String cardLabel = rs.getString(2);
				l3.setText(cardLabel);
				System.out.println("card_No is " +cardLabel);
				
				String aBalance = rs.getString(6);
				l4.setText(aBalance);
				System.out.println("avaiable balance is " +aBalance);
			}
			con.close();
			
		} catch (Exception sqle) {
			System.out.println(sqle);
		}
	}

	/**
	 * implements from actionListener class
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
