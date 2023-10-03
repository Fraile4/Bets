package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;
import javax.swing.JPasswordField;

public class DeleteAccountGUI extends JFrame {

	private JFrame myScreen;
	private JPanel contentPane;
	private JTextField tfUser;
	private JPasswordField pfPassword;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DeleteAccountGUI frame = new DeleteAccountGUI("","");
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
	 * @param uPass
	 */
	public DeleteAccountGUI(String userName, String language) {
		myScreen = this;
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAdmnIntroduction = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblAdmnIntroduction"));
		lblAdmnIntroduction.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblAdmnIntroduction.setHorizontalAlignment(SwingConstants.CENTER);
		lblAdmnIntroduction.setBounds(49, 12, 358, 17);
		contentPane.add(lblAdmnIntroduction);
		if(!facade.getUser(userName).isAdmin()) {
			lblAdmnIntroduction.setVisible(false);
		}
		
		JLabel lblIntroduce = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblIntroduce"));
		lblIntroduce.setFont(new Font("FreeSans", Font.BOLD, 13));
		lblIntroduce.setHorizontalAlignment(SwingConstants.CENTER);
		lblIntroduce.setBounds(77, 12, 301, 17);
		contentPane.add(lblIntroduce);
		if(facade.getUser(userName).isAdmin()) {
			lblIntroduce.setVisible(false);
		}
		
		JLabel lblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblPassword"));
		lblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPassword.setBounds(49, 130, 132, 17);
		contentPane.add(lblPassword);

		

		JLabel lblUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblUserName"));
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(49, 74, 132, 17);
		contentPane.add(lblUser);
		
		tfUser = new JTextField();
		tfUser.setBounds(198, 72, 114, 21);
		contentPane.add(tfUser);
		tfUser.setColumns(10);
		if(!facade.getUser(userName).isAdmin()) {
			tfUser.setText(userName);
		}
		
		pfPassword = new JPasswordField();
		pfPassword.setBounds(198, 128, 114, 21);
		contentPane.add(pfPassword);
		
		JLabel lblError1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblLoginError2"));
		lblError1.setForeground(new Color(255, 0, 0));
		lblError1.setFont(new Font("FreeSans", Font.ITALIC, 13));
		lblError1.setHorizontalAlignment(SwingConstants.CENTER);
		lblError1.setBounds(23, 198, 384, 17);
		contentPane.add(lblError1);
		lblError1.setVisible(false);
		
		JLabel lblError2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblLoginError1"));
		lblError2.setForeground(new Color(255, 0, 0));
		lblError2.setFont(new Font("FreeSans", Font.ITALIC, 13));
		lblError2.setHorizontalAlignment(SwingConstants.CENTER);
		lblError2.setBounds(49, 197, 367, 17);
		contentPane.add(lblError2);
		lblError2.setVisible(false);
		
		JLabel lblError3 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblDelAcc"));
		lblError3.setForeground(new Color(255, 0, 0));
		lblError3.setFont(new Font("FreeSans", Font.ITALIC, 13));
		lblError3.setHorizontalAlignment(SwingConstants.CENTER);
		lblError3.setBounds(23, 197, 393, 17);
		contentPane.add(lblError3);
		lblError3.setVisible(false);
		
		
		JLabel jblSuccesfullyD = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblSuccesfullyD")); 
		jblSuccesfullyD.setHorizontalAlignment(SwingConstants.CENTER);
		jblSuccesfullyD.setForeground(new Color(0, 128, 0));
		jblSuccesfullyD.setBounds(49, 200, 358, 14);
		contentPane.add(jblSuccesfullyD);
		jblSuccesfullyD.setVisible(false);
		
		JButton btnDelete = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnDelete"));
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				BLFacade facade = MainGUI.getBusinessLogic();
				boolean isAdmin = facade.getUser(userName).isAdmin();
				lblError1.setVisible(false);
				lblError2.setVisible(false);
				lblError3.setVisible(false);
				jblSuccesfullyD.setVisible(false);

				if(isAdmin) { //Banning a user account.
					if(facade.getUser(tfUser.getText()) != null && !facade.getUser(tfUser.getText()).isAdmin()) {
						if (pfPassword.getText().equals(facade.getUser(tfUser.getText()).getPassword())) {
							
							facade.removeUser(tfUser.getText());
							jblSuccesfullyD.setVisible(true);
							pfPassword.setText("");
							tfUser.setText("");
						

						}else {
							lblError1.setVisible(true);
						}
					}else if(facade.getUser(tfUser.getText()) == null) {
						
						lblError2.setVisible(true);
					}else if(facade.getUser(tfUser.getText()) != null && facade.getUser(tfUser.getText()).isAdmin()) {
						
						lblError3.setVisible(true);
					}
					
				}else {
					if(userName.equals(tfUser.getText())) {
						
						if (pfPassword.getText().equals(facade.getUser(userName).getPassword())) {

							facade.removeUser(userName);
							MainGUI main = new MainGUI(language);
							main.setVisible(true);
							myScreen.setVisible(false);

						} else {
							lblError1.setVisible(true);
						}
						

					}else {
						lblError2.setVisible(true);
					}
				}
				
				
			
			}
		});
		btnDelete.setBounds(178, 227, 107, 21);
		contentPane.add(btnDelete);
		
		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(facade.getUser(userName).isAdmin()) {
					LandscapeAdminGUI lndAdmn = new LandscapeAdminGUI(userName, language);
					lndAdmn.setVisible(true);
				}else {
					LandscapeRegisterGUI lndReg = new LandscapeRegisterGUI(userName, language);
					lndReg.setVisible(true);
				}
				myScreen.setVisible(false);
				
			}
		});
		btnBack.setBounds(33, 227, 96, 21);
		contentPane.add(btnBack);
		
		
		
		
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("BtnDeleteAccount"));
		
		
		
	}
}
