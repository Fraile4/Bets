package gui;

import java.awt.EventQueue;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JLabel;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class FundsMovementsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JFrame myScreen;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FundsMovementsGUI frame = new FundsMovementsGUI("","");
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
	public FundsMovementsGUI(String userName, String language) {
		myScreen = this;
		myScreen.setTitle(ResourceBundle.getBundle("Etiquetas").getString("FundsMovements"));
		Locale.setDefault(new Locale(language));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 811, 566);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JTextPane tpFundsMovements = new JTextPane();
		tpFundsMovements.setEditable(false);
		tpFundsMovements.setBounds(8, 31, 777, 434);
		contentPane.add(tpFundsMovements);
		//Show movements here;
		BLFacade facade = MainGUI.getBusinessLogic();

		User u = facade.getUser(userName);
		
		tpFundsMovements.setText(u.showMovements());
		
		
		JLabel jblFundsMovements = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QueryFunds"));
		jblFundsMovements.setFont(new Font("Dialog", Font.BOLD, 12));
		jblFundsMovements.setHorizontalAlignment(SwingConstants.CENTER);
		jblFundsMovements.setBounds(262, 4, 270, 22);
		contentPane.add(jblFundsMovements);
		
		JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		jButtonBack.setFont(new Font("Dialog", Font.BOLD, 12));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				LandscapeRegisterGUI a = new LandscapeRegisterGUI(userName, language);
				a.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		jButtonBack.setBounds(40, 476, 129, 23);
		contentPane.add(jButtonBack);
		
		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setFont(new Font("Dialog", Font.BOLD, 12));
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myScreen.setVisible(false);
				System.exit(0);
			}
		});
		btnClose.setBounds(632, 476, 129, 23);
		contentPane.add(btnClose);
	}
}
