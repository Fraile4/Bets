package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;

public class LandscapeRegisterGUI extends JFrame {
	
	private JFrame myScreen;
	private JPanel contentPane;
	private final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
	
					LandscapeRegisterGUI frame = new LandscapeRegisterGUI("","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public LandscapeRegisterGUI(String userName, String language) {
		Locale.setDefault(new Locale(language));
		myScreen = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 625, 526);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		BLFacade f = MainGUI.getBusinessLogic();
		float userMoney = f.getUser(userName).getMoney();
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnAddFunds = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnAddFunds"));
		btnAddFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame addFunds = new AddFundsGUI(userName, language);
				addFunds.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		JButton lblNewLabel = new JButton(ResourceBundle.getBundle("Etiquetas").getString("YourFunds") + " " +String.valueOf(userMoney) + " €");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBackground(Color.BLACK);
		lblNewLabel.setEnabled(false);
		contentPane.add(lblNewLabel);
		btnAddFunds.setFont(new Font("Dialog", Font.BOLD, 12));
		contentPane.add(btnAddFunds);
		
		JButton btnQueryQuestions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnQueryQuestions.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame askQuestions = new FindQuestionsGUI(userName, language);
				askQuestions.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnQueryQuestions);
		
		JButton btnMakeYourBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnUbet"));
		btnMakeYourBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				MakeBetGUI makeBet = new MakeBetGUI(userName,language);
				makeBet.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnMakeYourBet);
		
		JButton btnDeleteAccount = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnDeleteAccount"));
		btnDeleteAccount.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
		
				String uPass = facade.getUser(userName).getPassword();
				DeleteAccountGUI dAccount = new DeleteAccountGUI(userName, language);
				
				dAccount.setVisible(true);
				myScreen.setVisible(false);
				
			}
		});
		
		JButton btnQueryFunds = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FundsMovements")); 
		btnQueryFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FundsMovementsGUI fmove = new FundsMovementsGUI(userName, language);
				fmove.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		
		JButton btnFollowUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FollowUser"));
		if(f.getAllUsers().size()<2) {
			btnFollowUser.setText(ResourceBundle.getBundle("Etiquetas").getString("FollowUser2"));
			btnFollowUser.setEnabled(false);
		}
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FollowUserGUI fUser = new FollowUserGUI(userName, language);
				fUser.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnFollowUser);
		contentPane.add(btnQueryFunds);
		
		JButton btnQueryRanking = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryPlayersRanking")); 
		btnQueryRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RankingPlayersGUI r = new RankingPlayersGUI(userName, language);
				r.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnQueryRanking);
		contentPane.add(btnDeleteAccount);
		
		JButton btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnLogOut"));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame main =new  MainGUI(language);
				main.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnLogOut);
		
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("Landscape"));
	}
}
