package Funtionality;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
/**
 * Main user menu of the atm
 * @author Le Thu Huong
 *
 */
public class PrintUserMenu  extends JFrame implements ActionListener{
	private JFrame frame;
	private Font font1,font2;
	private JLabel label;
	private JButton cashWithdrawBtn, cashDepositBtn, balanceInquiryBtn, informationBtn, savingFunctionBtn, returnBtn;
	private ButtonGroup group;
	
			int card_No, account_No, password_No;
			String account_Type;
	
			
	/**
	 * Create GUI(PrintUserMenu) 
	 * @param card_No1 : card number as current user
	 * @param account_No1 : account number of current user
	 * @param password_No1 : his/her password
	 * @param account_Type1 : either saving or checking account
	 */
	public PrintUserMenu(Integer card_No1, Integer account_No1, Integer password_No1, String account_Type1) {
		card_No = card_No1;
		account_No = account_No1;
		password_No = password_No1;
		account_Type = account_Type1;
		
		frame = new JFrame();
		font1 = new Font("Times New Roman", Font.BOLD,35);
		font2 = new Font("Times New Roman", Font.BOLD,20);
		frame.setLayout(null);
		
		label = new JLabel(" Please select your desired service ");
		label.setFont(font1);
		label.setBounds(100, 100, 600, 50);
		frame.add(label);
		
		cashWithdrawBtn = new JButton(" CASH WITHDRAW ");
		cashWithdrawBtn.setFont(font2);
		cashWithdrawBtn.setBounds(100,250,220,40);
		frame.add(cashWithdrawBtn);
		
		cashDepositBtn = new JButton(" CASH DEPOSIT ");
		cashDepositBtn.setFont(font2);
		cashDepositBtn.setBounds(450, 250, 220, 40);
		frame.add(cashDepositBtn);
		
		balanceInquiryBtn = new JButton("BALANCE INQUIRY");
		balanceInquiryBtn.setFont(font2);
		balanceInquiryBtn.setBounds(100, 380, 220, 40);
		frame.add(balanceInquiryBtn);
		
		informationBtn = new JButton(" INFORMATION ");
		informationBtn.setFont(font2);
		informationBtn.setBounds(450, 380, 220, 40);
		frame.add(informationBtn);
		
		savingFunctionBtn = new JButton(" SAVING FUNTION");
		savingFunctionBtn.setFont(font2);
		savingFunctionBtn.setBounds(100,520,220,40);
		frame.add(savingFunctionBtn);
		
		returnBtn = new JButton(" RETURN ");
		returnBtn.setFont(font2);
		returnBtn.setBounds(450, 520, 220, 40);
		frame.add(returnBtn);
		
		group = new ButtonGroup();
		group.add(cashWithdrawBtn);
		group.add(cashDepositBtn);
		group.add(balanceInquiryBtn);
		group.add(informationBtn);
		group.add(savingFunctionBtn);
		group.add(returnBtn);
		
		cashWithdrawBtn.addActionListener(this);
		cashDepositBtn.addActionListener(this);
		balanceInquiryBtn.addActionListener(this);
		informationBtn.addActionListener(this);
		savingFunctionBtn.addActionListener(this);
		returnBtn.addActionListener(this);
		
		frame.setTitle(" TRANSACTION MENU ");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(800,700);
		frame.setVisible(true);
		frame.setLocation(350, 50);
		frame.setResizable(false);	
	}
	
	
	/**
	 * actionPerormed, which implements from ActionListener class
	 */
	public void actionPerformed (ActionEvent event) {
		if (event.getSource() == cashWithdrawBtn) {
			new CashWithdrawMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
			
		}else if (event.getSource() == cashDepositBtn) {
			new CashDepositMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
			
		}else if (event.getSource() == balanceInquiryBtn) {
			new BalanceEnquiryMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
			
		}else if (event.getSource() == informationBtn) {
			new InformationMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
			
		}else if (event.getSource() == savingFunctionBtn) {
			new SavingMenu(card_No, account_No, password_No, account_Type);
			frame.setVisible(false);
			
		}else if (event.getSource() == returnBtn) {
			JOptionPane.showMessageDialog(this, " Thank you and See you again ");
			new Welcome();
			frame.setVisible(false);
		}
	} 
}
