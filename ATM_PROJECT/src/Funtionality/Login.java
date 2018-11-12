package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 * Login Gui, examine user's input with existing database
 * Only they are matched, allows to enter the next step
 * @author Le Thu Huong
 *
 */
public class Login extends JFrame implements ActionListener {
	private JFrame frame;
	private Font font1,font2;
	
	private JButton loginButton,exitButton,clearButton;
	private ButtonGroup group;
	
	private JLabel label1,label2,label3;
	private JTextField cardField;
	private JPasswordField passwordField;
		
	private Connection con;
	private PreparedStatement ps;
	private ResultSet rs;
	
    private int card_No,account_No,password_No;

    /**
     * create its GUI
     */
	public Login() {
		frame = new JFrame();
		font1 = new Font("Times New Roman",Font.BOLD,25); //label
		font2 = new Font("Times New Roman",Font.BOLD,20); //button
		frame.setLayout(null);
		
		label1 = new JLabel("INSERT YOUR ATM CARD TO LOGIN");
		label1.setFont(font1);
		label1.setBounds(180, 200, 500, 40);
		frame.add(label1);
		
		label2 = new JLabel("Enter card number");
		label2.setFont(font1);
		label2.setBounds(120,300,250,40);
		frame.add(label2);
		
		cardField = new JTextField(20);
		cardField.setBounds(450, 300, 250, 40);
		frame.add(cardField);
		
		label3 = new JLabel("Enter password number");
		label3.setFont(font1);
		label3.setBounds(120,350,300,40);
		frame.add(label3);
		
		passwordField = new JPasswordField(20);
		passwordField.setBounds(450, 350, 250, 40);
		frame.add(passwordField);
		
		loginButton = new JButton("LOGIN");
		loginButton.setFont(font2);
		loginButton.setBounds(120, 450, 130, 40);
		frame.add(loginButton);
		
		clearButton = new JButton("CLEAR");
		clearButton.setFont(font2);
		clearButton.setBounds(330, 450, 130, 40);
		frame.add(clearButton);
		
		exitButton = new JButton("EXIT");
		exitButton.setFont(font2);
		exitButton.setBounds(550, 450, 130, 40);
		frame.add(exitButton);
		
		loginButton.addActionListener(this);
		clearButton.addActionListener(this);
		exitButton.addActionListener(this);
		
		group = new ButtonGroup();
		group.add(loginButton);
		group.add(clearButton);
		group.add(exitButton);
		
		frame.setTitle("PLEASE LOGIN...");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setLocation(350, 50);
		frame.setResizable(false);
		frame.setVisible(true);
	}
	
	/**
	 * actionPerormed, which implements from ActionListener class
	 * Store card number, account number and password number in the variables
	 */
	public void actionPerformed(ActionEvent event) {
		// Login button activated
		if (event.getSource()==loginButton) {
		
		// connect to the DB and examine all info
		try {
			if (((cardField.getText()).equals(""))&&((passwordField.getText()).equals(""))) {
				JOptionPane.showMessageDialog(this, "Please enter your ATM card and password number",
						"Warning",JOptionPane.WARNING_MESSAGE);
				
			}else {
			int x=0;
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/atmdb?user=root&password=&useSSL=false");
			System.out.println("Great! full control of database");
			ps = con.prepareStatement("SELECT * FROM accountdetail WHERE card_no = " +"'" + cardField.getText()+ 
					"'" + "and password_no =" +"'" + passwordField.getText()+"'");
			rs = ps.executeQuery();
			
			// all user inputs are matched, store it in variable and print out a message
			while (rs.next()) {
			card_No = rs.getInt(2); 
			System.out.println("card_No is "+ card_No);
			
			account_No=rs.getInt(1);
			System.out.println("account_No is "+account_No);
			
			password_No = rs.getInt(3);
			System.out.println("password_No is "+password_No);
			
			String name = rs.getString(5);
			System.out.println("client's name is "+name);
			
			System.out.println("Valided card number");
   		 	JOptionPane.showMessageDialog(null,"WELCOME, "+ name + "\n" + " TO NOUVEAX RICHES BANK ");
            new AccountSelection(card_No,account_No,password_No);
            frame.setVisible(false);
			x=1;
			}
			
			// user inputs are not matched, warning message
			if (x == 0)
             {
              JOptionPane.showMessageDialog(null," Invalid ATM card and password combination ",
            		  "Warning",JOptionPane.WARNING_MESSAGE);
              cardField.setText("");
              passwordField.setText("");
             }
			}con.close();
			
		}catch(Exception sqle){
			System.out.println(" Sorry for inconvenience, please recheck your jdbc mysql connection ");
		}
		
		// Clear button activated
		}else if (event.getSource()==clearButton) {
			cardField.setText("");
			passwordField.setText("");
		
		//exit button activated
		}else if (event.getSource() == exitButton) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
	}
}
