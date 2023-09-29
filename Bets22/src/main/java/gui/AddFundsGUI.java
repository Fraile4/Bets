package gui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.objectdb.o.HMP.f;

import businessLogic.BLFacade;
import domain.Transaction;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Font;

public class AddFundsGUI extends JFrame {
	private static final long serialVersionUID = 1L;
	private JFrame myScreen;
	private JPanel contentPane;
	private JTextField tfAmount;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AddFundsGUI frame = new AddFundsGUI("", "");
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
	public AddFundsGUI(String userName, String language) {
		Locale.setDefault(new Locale(language));
		myScreen = this;
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("lblAmount"));
		lblAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		lblAmount.setBounds(62, 62, 98, 17);
		contentPane.add(lblAmount);

		tfAmount = new JTextField();
		tfAmount.setBounds(165, 60, 114, 21);
		contentPane.add(tfAmount);
		tfAmount.setColumns(10);

		JLabel jblEuro = new JLabel("€");
		jblEuro.setBounds(281, 62, 15, 17);
		contentPane.add(jblEuro);

		JLabel jblError1AddFunds = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("AddFundsGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jblError1AddFunds.setFont(new Font("Tahoma", Font.ITALIC, 12));
		jblError1AddFunds.setHorizontalAlignment(SwingConstants.CENTER);
		jblError1AddFunds.setForeground(new Color(255, 0, 0));
		jblError1AddFunds.setBounds(35, 160, 359, 14);
		contentPane.add(jblError1AddFunds);
		jblError1AddFunds.setVisible(false);

		JLabel jblError2AddFunds = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("AddFundsGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		jblError2AddFunds.setFont(new Font("Tahoma", Font.ITALIC, 12));
		jblError2AddFunds.setHorizontalAlignment(SwingConstants.CENTER);
		jblError2AddFunds.setForeground(new Color(255, 0, 0));
		jblError2AddFunds.setBounds(80, 161, 275, 13);
		contentPane.add(jblError2AddFunds);
		jblError2AddFunds.setVisible(false);

		JButton btnAddFunds = new JButton(ResourceBundle.getBundle("Etiquetas").getString("btnAddFunds"));
		btnAddFunds.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				jblError1AddFunds.setVisible(false);
				jblError2AddFunds.setVisible(false);
				float amount = -1;
				boolean add = true;

				try {
					amount = Float.parseFloat(tfAmount.getText());
				} catch (NumberFormatException e) {
					jblError1AddFunds.setVisible(true);
					add = false;
				}

				if (add) {
					if (amount < 0) {
						add = false;
						jblError2AddFunds.setVisible(true);
					}

					if (add) {
					
						
					
						facade.addFunds(userName, Float.parseFloat(tfAmount.getText()), "Deposit");

						boolean isAdmin = facade.getUser(userName).isAdmin();

						LandscapeRegisterGUI lndRegister = new LandscapeRegisterGUI(userName, language);
						lndRegister.setVisible(true);
						myScreen.setVisible(false);

					}
				}

			}
		});
		btnAddFunds.setBounds(162, 216, 150, 27);
		contentPane.add(btnAddFunds);

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (facade.getUser(userName).isAdmin()) {
					LandscapeAdminGUI lndAdmn = new LandscapeAdminGUI(userName, language);
					lndAdmn.setVisible(true);
				} else {
					LandscapeRegisterGUI lndReg = new LandscapeRegisterGUI(userName, language);
					lndReg.setVisible(true);
				}
				myScreen.setVisible(false);

			}
		});
		btnBack.setBounds(20, 216, 98, 27);
		contentPane.add(btnBack);

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("btnAddFunds"));

	}
}
