package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.User;
import dataAccess.DataAccess;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class RegisterGUI extends JFrame {

	private JFrame nirePantaila;
	private JPanel contentPane;
	private JTextField tfUserName;
	private JPasswordField tfPassword;
	private JPasswordField tfPassword2;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tfNan;

	protected JLabel JblAccount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RegisterGUI frame = new RegisterGUI("");
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
	public RegisterGUI(String language) {

		nirePantaila = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 373);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		Locale.setDefault(new Locale(language));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel JblCreateAccount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblCreateAccount"));
		JblCreateAccount.setFont(new Font("FreeSans", Font.BOLD, 12));
		JblCreateAccount.setHorizontalAlignment(SwingConstants.CENTER);
		JblCreateAccount.setBounds(173, 12, 101, 17);
		JblCreateAccount.setText(ResourceBundle.getBundle("Etiquetas").getString("JblCreateAccount"));
		contentPane.add(JblCreateAccount);

		tfUserName = new JTextField();
		tfUserName.setBounds(216, 67, 114, 21);
		contentPane.add(tfUserName);
		tfUserName.setColumns(10);

		tfPassword = new JPasswordField();
		tfPassword.setBounds(216, 112, 114, 21);
		contentPane.add(tfPassword);

		JLabel JblUserName = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblUserName"));
		JblUserName.setFont(new Font("Dialog", Font.PLAIN, 11));
		JblUserName.setHorizontalAlignment(SwingConstants.RIGHT);
		JblUserName.setBounds(-23, 71, 204, 17);
		contentPane.add(JblUserName);

		JLabel JblPassword = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblPassword"));
		JblPassword.setHorizontalAlignment(SwingConstants.RIGHT);
		JblPassword.setFont(new Font("Dialog", Font.PLAIN, 11));
		JblPassword.setBounds(-23, 116, 204, 17);
		contentPane.add(JblPassword);

		JLabel JblPassword2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblPassword2"));
		JblPassword2.setHorizontalAlignment(SwingConstants.RIGHT);
		JblPassword2.setFont(new Font("Dialog", Font.PLAIN, 11));
		JblPassword2.setBounds(-23, 159, 204, 17);
		contentPane.add(JblPassword2);

		JLabel JblRegisterError1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblRegisterError1"));
		JblRegisterError1.setForeground(new Color(255, 0, 0));
		JblRegisterError1.setFont(new Font("Dialog", Font.ITALIC, 11));
		JblRegisterError1.setHorizontalAlignment(SwingConstants.CENTER);
		JblRegisterError1.setBounds(18, 305, 414, 27);
		contentPane.add(JblRegisterError1);
		JblRegisterError1.setVisible(false);

		JLabel JblRegisterError2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblRegisterError2"));
		JblRegisterError2.setHorizontalAlignment(SwingConstants.CENTER);
		JblRegisterError2.setForeground(new Color(255, 0, 0));
		JblRegisterError2.setFont(new Font("Dialog", Font.ITALIC, 11));
		JblRegisterError2.setBounds(20, 310, 414, 17);
		contentPane.add(JblRegisterError2);
		JblRegisterError2.setVisible(false);

		JLabel JblRegisterError3 = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("RegisterGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		JblRegisterError3.setForeground(new Color(255, 0, 0));
		JblRegisterError3.setFont(new Font("DejaVu Sans", Font.ITALIC, 11));
		JblRegisterError3.setHorizontalAlignment(SwingConstants.CENTER);
		JblRegisterError3.setBounds(18, 311, 414, 14);
		contentPane.add(JblRegisterError3);
		JblRegisterError3.setVisible(false);

		JButton btnRegister = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Register"));
		btnRegister.setBounds(188, 278, 124, 20);
		contentPane.add(btnRegister);

		tfPassword2 = new JPasswordField();
		tfPassword2.setBounds(216, 155, 114, 21);
		contentPane.add(tfPassword2);

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.setBounds(49, 277, 89, 23);
		contentPane.add(btnBack);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				MainGUI m = new MainGUI(language);
				m.setVisible(true);
				nirePantaila.setVisible(false);
			}
		});
		tfNan = new JTextField();
		tfNan.setText("");
		tfNan.setBounds(189, 239, 114, 21);
		contentPane.add(tfNan);
		tfNan.setColumns(10);
		tfNan.setVisible(false);

		JLabel jblNan = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("DNI"));
		jblNan.setBounds(83, 243, 60, 17);
		contentPane.add(jblNan);
		jblNan.setVisible(false);

		JRadioButton rbtnNAdmin = new JRadioButton(
				ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.rdbtnNewRadioButton.text")); //$NON-NLS-1$ //$NON-NLS-2$
		buttonGroup.add(rbtnNAdmin);
		rbtnNAdmin.setBounds(81, 192, 130, 25);
		contentPane.add(rbtnNAdmin);
		rbtnNAdmin.setSelected(true);
		rbtnNAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jblNan.setVisible(false);
				tfNan.setVisible(false);
			}
		});

		JRadioButton rbtnAdmin = new JRadioButton(
				ResourceBundle.getBundle("Etiquetas").getString("LoginGUI.rdbtnAdmin.text")); //$NON-NLS-1$ //$NON-NLS-2$
		rbtnAdmin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jblNan.setVisible(true);
				tfNan.setVisible(true);
			}
		});
		buttonGroup.add(rbtnAdmin);
		rbtnAdmin.setBounds(241, 192, 130, 25);
		contentPane.add(rbtnAdmin);
		
		JLabel jblRegisterError4 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("jblRegisterError4")); //$NON-NLS-1$ //$NON-NLS-2$
		jblRegisterError4.setFont(new Font("Dialog", Font.ITALIC, 12));
		jblRegisterError4.setForeground(new Color(255, 0, 0));
		jblRegisterError4.setBounds(195, 309, 60, 17);
		contentPane.add(jblRegisterError4);
		jblRegisterError4.setVisible(false);

		btnRegister.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String userName = tfUserName.getText();
				String password = tfPassword.getText();
				String password2 = tfPassword2.getText();
				boolean registered = false;
				BLFacade facade = MainGUI.getBusinessLogic();
				JblRegisterError1.setVisible(false);
				JblRegisterError2.setVisible(false);
				JblRegisterError3.setVisible(false);
				User u = facade.getUser(userName);

				if (u != null) {

					JblRegisterError1.setVisible(true);

				} else {
					if (!userName.trim().equals("")) {
						if (!password.equals(password2) | password.trim().equals(""))
							if (password.trim().equals(""))
								JblRegisterError3.setVisible(true);
							else
								JblRegisterError2.setVisible(true);

						else if (password.equals(password2)) {

							DataAccess d = new DataAccess();
							if (rbtnNAdmin.isSelected()) {
								d.open(true);

								d.register(userName, password, false);

								d.close();

								JFrame maingui = new LandscapeRegisterGUI(userName, language);
								maingui.setVisible(true);
								nirePantaila.setVisible(false);
							}else {
								if(nanEgiaztatu(tfNan.getText())) {
									d.open(true);

									d.register(userName, password, true);

									d.close();

									JFrame maingui = new LandscapeAdminGUI(userName, language);
									maingui.setVisible(true);
									nirePantaila.setVisible(false);
									
								}else {
									jblRegisterError4.setVisible(true);
								}
							}
							
						}

					}

				}

			}
		});

	}
	
	public boolean nanEgiaztatu(String nan) {
	    final String hizkiBaliagarri = "TRWAGMYFPDXBNJZSQVHLCKE";
	    boolean baliagarri = false;
	    try {
	        int zenbakia = Integer.parseInt(nan.substring(0, nan.length() - 1));
	        char hizki = nan.charAt(nan.length() - 1);
	        int hondarra = zenbakia % 23;
	        if (hizki == hizkiBaliagarri.charAt(hondarra)) {
	            baliagarri = true;
	        }
	    } catch (NumberFormatException e) {
	        baliagarri = false;
	    }
	    return baliagarri;
	}
}