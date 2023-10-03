
package gui;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Color;

public class FollowUserGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JList<String> list;
	private DefaultListModel<String> listModel;

	private JFrame myScreen;

	private PreparedStatement stmt;
	private ResultSet rs; 
	

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FollowUserGUI frame = new FollowUserGUI("", "");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FollowUserGUI(String userName, String language) {
		this.setTitle("FollowUser");
		myScreen = this;
		BLFacade f = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 360);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		textField = new JTextField();
		textField.setForeground(new Color(0, 0, 0));
		textField.setBounds(10, 36, 414, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		listModel = new DefaultListModel<>();
		list = new JList<>(listModel);
		JButton btnFollowUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FollowUser"));
		btnFollowUser.setEnabled(false);
		list.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				btnFollowUser.setEnabled(true);
			}
		});
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setBounds(10, 67, 414, 208);
		contentPane.add(list);

		textField.addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent arg0) {
				// no action needed
			}

			@Override
			public void keyReleased(KeyEvent arg0) {

				String query = textField.getText() + "%";
				Vector<String> gotUsers = f.getTypedUser(query, userName);
				if (query.isEmpty()) {
					DefaultListModel<String> emptyListModel = new DefaultListModel<>();
					list.setModel(emptyListModel);
					return;

				}

				// Verificar si la tecla pulsada es la tecla de retroceso (backspace)
				if (arg0.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
					if (gotUsers.size() != 0) {
						if (!query.trim().isEmpty() && query.charAt(0) == gotUsers.get(0).charAt(0)) {
							listModel.removeAllElements();
							for (int i = 0; i < gotUsers.size(); i++) {
								listModel.addElement(gotUsers.get(i));
							}
						}
						return;
					}
				}
				if (gotUsers.size() != 0) {
					if (!query.isEmpty() && query.charAt(0) == gotUsers.get(0).charAt(0)) {
						listModel.removeAllElements();
						for (int i = 0; i < gotUsers.size(); i++) {

							listModel.addElement(gotUsers.get(i));
						}
					}
				}
			}

			@Override
			public void keyTyped(KeyEvent arg0) {
				// no action needed
			}

		});

		JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		jButtonClose.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				myScreen.setVisible(false);
				System.exit(0);
			}
		});
		jButtonClose.setBounds(new Rectangle(399, 275, 130, 30));
		jButtonClose.setBounds(341, 286, 83, 24);
		contentPane.add(jButtonClose);

		JLabel jblFindUser = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FindUser")); //$NON-NLS-1$ //$NON-NLS-2$
		jblFindUser.setBounds(11, 11, 212, 14);
		contentPane.add(jblFindUser);

		JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		jButtonBack.setFont(new Font("Tahoma", Font.PLAIN, 11));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				User u = facade.getUser(userName);

				if (u.isAdmin()) {
					LandscapeAdminGUI a = new LandscapeAdminGUI(userName, language);
					a.setVisible(true);

				} else if (!u.isAdmin()) {
					LandscapeRegisterGUI a = new LandscapeRegisterGUI(userName, language);
					a.setVisible(true);
				}
				myScreen.setVisible(false);
			}
		});

		jButtonBack.setBounds(new Rectangle(53, 279, 107, 26));
		jButtonBack.setBounds(10, 286, 83, 24);
		contentPane.add(jButtonBack);

		
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				f.addFollower(userName, list.getSelectedValue());
				LandscapeRegisterGUI a = new LandscapeRegisterGUI(userName, language);
				a.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		btnFollowUser.setBounds(139, 287, 156, 23);
		contentPane.add(btnFollowUser);
	}
}
