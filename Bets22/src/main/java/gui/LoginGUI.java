package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.Bet;
import domain.User;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.ActionEvent;
import javax.swing.JEditorPane;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField tfUserName;
	private JPasswordField pfPassword;
	private JFrame myScreen;
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					LoginGUI frame = new LoginGUI("");
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
	public LoginGUI(String language) {
	
		Locale.setDefault(new Locale(language));
		myScreen = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 306);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel jblLogin = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUILogin"));
		jblLogin.setHorizontalAlignment(SwingConstants.CENTER);
		jblLogin.setBounds(137, 12, 175, 23);
		contentPane.add(jblLogin);

		JLabel jblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblUserName"));
		jblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		jblUserName.setBounds(20, 73, 113, 23);
		contentPane.add(jblUserName);

		tfUserName = new JTextField();
		tfUserName.setBounds(187, 74, 114, 21);
		contentPane.add(tfUserName);
		tfUserName.setColumns(10);

		JLabel jblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblPassword"));
		jblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		jblPassword.setBounds(20, 133, 113, 17);
		contentPane.add(jblPassword);

		pfPassword = new JPasswordField();
		pfPassword.setBounds(187, 131, 114, 21);
		contentPane.add(pfPassword);

		JLabel JblLoginError1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblLoginError1"));
		JblLoginError1.setFont(new Font("FreeSans", Font.ITALIC, 13));
		JblLoginError1.setForeground(new Color(255, 0, 0));
		JblLoginError1.setHorizontalAlignment(SwingConstants.CENTER);
		JblLoginError1.setBounds(88, 194, 288, 17);
		contentPane.add(JblLoginError1);
		JblLoginError1.setVisible(false);
		
		JLabel JblLoginError2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblLoginError2"));
		JblLoginError2.setForeground(new Color(255, 0, 0));
		JblLoginError2.setFont(new Font("FreeSans", Font.ITALIC, 13));
		JblLoginError2.setHorizontalAlignment(SwingConstants.CENTER);
		JblLoginError2.setBounds(75, 195, 313, 17);
		contentPane.add(JblLoginError2);
		JblLoginError2.setVisible(false);
		
		JButton btnLogin = new JButton(ResourceBundle.getBundle("Etiquetas").getString("MainGUILogin"));
		btnLogin.setBounds(144, 226, 123, 23);
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = tfUserName.getText();
				String password = pfPassword.getText();
				BLFacade facade = MainGUI.getBusinessLogic();
				JblLoginError1.setVisible(false);
				JblLoginError2.setVisible(false);
				
				boolean isLogin = facade.isLogin(userName, password);
				if (facade.getUser(userName) != null) {
					if (isLogin) {
						if (!facade.getUser(userName).isAdmin()) {
							LandscapeRegisterGUI lndscape = new LandscapeRegisterGUI(userName, language);
							lndscape.setVisible(true);
							myScreen.setVisible(false);
						} else {
							LandscapeAdminGUI lndAdmin = new LandscapeAdminGUI(userName, language);
							lndAdmin.setVisible(true);
							myScreen.setVisible(false);
						}

					} else {

						JblLoginError2.setVisible(true);
						pfPassword.setText("");

					}

				} else {
					JblLoginError1.setVisible(true);
				}
			}
		});
		contentPane.add(btnLogin);
		

		

		

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.setBounds(37, 226, 87, 23);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MainGUI m = new MainGUI(language);
				m.setVisible(true);
				myScreen.setVisible(false);

			}
		});
		
		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				 JFrame r = new RegisterGUI(language);
                 r.setVisible(true);
                 myScreen.setVisible(false);
			}
		});
		btnRegister.setBounds(283, 226, 130, 23);
		contentPane.add(btnRegister);
	
		
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI"));

	}
	
	public boolean NANbalia(String NAN) {
	    final String baliozkoLetra = "TRWAGMYFPDXBNJZSQVHLCKE";
	    boolean baliagarri = false;
	    try {
	        int zbkia = Integer.parseInt(NAN.substring(0, NAN.length() - 1));
	        char letra = NAN.charAt(NAN.length() - 1);
	        int hondarra = zbkia % 23;
	        if (letra == baliozkoLetra.charAt(hondarra)) {
	            baliagarri = true;
	        }
	    } catch (NumberFormatException e) {
	        baliagarri = false;
	    }
	    return baliagarri;
	}

}
