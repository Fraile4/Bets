package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;

import javax.swing.JComboBox;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class CreateEventGUI extends JFrame {

	private JPanel contentPane;
	private JCalendar jCalendar = new JCalendar();
	private JTextField txtFldDescription;
	private JFrame myScreen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreateEventGUI frame = new CreateEventGUI("", "");
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
	public CreateEventGUI(String userName, String language) {
		Locale.setDefault(new Locale(language));
		myScreen = this;
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 540, 350);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		jCalendar.setBounds(new Rectangle(159, 36, 221, 129));
		contentPane.add(jCalendar, null);

		JLabel JblEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblEventDate"));
		JblEventDate.setHorizontalAlignment(SwingConstants.CENTER);
		JblEventDate.setFont(new Font("Dialog", Font.BOLD, 12));
		JblEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		JblEventDate.setBounds(220, 3, 99, 25);
		contentPane.add(JblEventDate);

		JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		jButtonBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myScreen.setVisible(false);
				LandscapeAdminGUI a = new LandscapeAdminGUI(userName, language);
				a.setVisible(true);
			}
		});

		jButtonBack.setBounds(new Rectangle(53, 279, 107, 26));
		jButtonBack.setBounds(26, 279, 107, 24);
		contentPane.add(jButtonBack);

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myScreen.setVisible(false);
				System.exit(0);
			}
		});
		jButtonClose.setBounds(new Rectangle(399, 275, 130, 30));
		jButtonClose.setBounds(407, 279, 107, 24);
		contentPane.add(jButtonClose);

		JLabel JblCrEvError1 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblEvCrError1"));
		JblCrEvError1.setFont(new Font("Tahoma", Font.ITALIC, 12));
		JblCrEvError1.setForeground(new Color(255, 0, 0));
		JblCrEvError1.setHorizontalAlignment(SwingConstants.CENTER);
		JblCrEvError1.setBounds(66, 222, 408, 18);
		contentPane.add(JblCrEvError1);
		JblCrEvError1.setVisible(false);

		JLabel JblEvCrError2 = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblEvCrError2"));
		JblEvCrError2.setHorizontalAlignment(SwingConstants.CENTER);
		JblEvCrError2.setFont(new Font("Tahoma", Font.ITALIC, 12));
		JblEvCrError2.setForeground(new Color(0, 128, 0));
		JblEvCrError2.setBounds(18, 225, 504, 13);
		contentPane.add(JblEvCrError2);
		JblEvCrError2.setVisible(false);

		JButton btnCreateEvent = new JButton(ResourceBundle.getBundle("Etiquetas").getString("BtnCreateEvent"));
		btnCreateEvent.setFont(new Font("Tahoma", Font.PLAIN, 11));
		btnCreateEvent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				JblCrEvError1.setVisible(false);
				boolean posible = false;
				String[] parts = txtFldDescription.getText().trim().split("-");
				if (parts.length == 2 && !parts[0].isEmpty() && !parts[1].isEmpty()) {
					posible = true;
				}
				
				if (!txtFldDescription.getText().trim().isEmpty()&&posible) {
					Date date = jCalendar.getDate();
					date = UtilDate.trim(date);
					facade.createEvent(txtFldDescription.getText(), date);
					JblEvCrError2.setVisible(true);
				} else {
					JblCrEvError1.setVisible(true);
				}
			}
		});
		btnCreateEvent.setBounds(new Rectangle(208, 275, 130, 30));
		btnCreateEvent.setBounds(198, 279, 143, 25);
		contentPane.add(btnCreateEvent);

		JLabel lblNewLabel = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("JblDescription"));
		lblNewLabel.setFont(new Font("Dialog", Font.PLAIN, 11));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNewLabel.setBounds(-11, 176, 107, 18);
		contentPane.add(lblNewLabel);

		txtFldDescription = new JTextField();
		txtFldDescription.setBounds(111, 176, 403, 19);
		contentPane.add(txtFldDescription);
		txtFldDescription.setColumns(10);

	}
}