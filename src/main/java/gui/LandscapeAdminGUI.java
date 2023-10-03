package gui;

import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.Insets;
import java.awt.Color;
import java.awt.SystemColor;

public class LandscapeAdminGUI extends JFrame {

	private JFrame myScreen;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LandscapeAdminGUI frame = new LandscapeAdminGUI("","");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param userName
	 */
	public LandscapeAdminGUI(String userName, String language) {
		Locale.setDefault(new Locale(language));
		myScreen = this;
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 585, 449);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		BLFacade f = MainGUI.getBusinessLogic();
		float userMoney = f.getUser(userName).getMoney();

		JButton btnCreateEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnCreateEvent"));
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateEventGUI a = new CreateEventGUI(userName, language);
				a.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.setLayout(new GridLayout(0, 1, 0, 0));
		
		JButton btnQueryQueries = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		btnQueryQueries.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FindQuestionsGUI a = new FindQuestionsGUI(userName, language);
				a.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnQueryQueries);
		
		
		JButton btnQueryRanking = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryPlayersRanking")); 
		btnQueryRanking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				RankingPlayersGUI r = new RankingPlayersGUI(userName, language);
				r.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnQueryRanking);
		contentPane.add(btnQueryRanking);
		contentPane.add(btnCreateEvent);
		
		JButton btnCreateQuestion = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuery"));
		btnCreateQuestion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				CreateQuestionGUI cQuestion = new CreateQuestionGUI(facade.getAllEvents(),userName, language);
				cQuestion.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnCreateQuestion);
		
		JButton btnLogOut = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnLogOut"));
		btnLogOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFrame main =new  MainGUI(language);
				main.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		
		JButton btnPutResult = new JButton(ResourceBundle.getBundle("Etiquetas").getString("PutResults"));
		btnPutResult.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				PutResultsGUI EmIpGUI = new PutResultsGUI(userName,language);
				EmIpGUI.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		contentPane.add(btnPutResult);
		
		JButton btnDeleteEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("DeleteEvent"));
		btnDeleteEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Vector<domain.Event> v = facade.getAllEvents();
				DeleteEventGUI dEvent = new DeleteEventGUI(userName, language);
				dEvent.setVisible(true);
				myScreen.setVisible(false);
				
			}
		});
		contentPane.add(btnDeleteEvent);
		
				JButton btnBanUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnBanUser"));
				btnBanUser.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						DeleteAccountGUI dAccount = new DeleteAccountGUI(userName, language);
						dAccount.setVisible(true);
						myScreen.setVisible(false);
					}
				});
				contentPane.add(btnBanUser);
		contentPane.add(btnLogOut);
		JButton btnUQueryQuestions = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("LandscapeAdmin"));
	}
}
