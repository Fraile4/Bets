package gui;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import businessLogic.BLFacade;
import domain.User;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class RankingPlayersGUI extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private JPanel contentPane;
	private JFrame myScreen;
	private String selectedUser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RankingPlayersGUI frame = new RankingPlayersGUI("", "");
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
	public RankingPlayersGUI(String userName, String language) {
		myScreen = this;
		myScreen.setTitle("Ranking of Players");
		BLFacade facade = MainGUI.getBusinessLogic();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 618, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);

		JScrollPane scrollPaneRanking = new JScrollPane();

		JTable tableRanking = new JTable();
		DefaultTableModel tableModelRanking;
		String[] columnNamesRanking = new String[] { "Top",
				ResourceBundle.getBundle("Etiquetas").getString("JblUserName"),
				ResourceBundle.getBundle("Etiquetas").getString("DineroGanado"),
				ResourceBundle.getBundle("Etiquetas").getString("Apuestasganadoras"),
				ResourceBundle.getBundle("Etiquetas").getString("Tasadeéxito")

		};

		scrollPaneRanking.setBounds(33, 21, 536, 389);
		scrollPaneRanking.setViewportView(tableRanking);
		tableModelRanking = new DefaultTableModel(null, columnNamesRanking);

		
		tableRanking.setModel(tableModelRanking);
		tableRanking.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableRanking.getColumnModel().getColumn(1).setPreferredWidth(25);
		tableRanking.getColumnModel().getColumn(2).setPreferredWidth(25);
		tableRanking.getColumnModel().getColumn(3).setPreferredWidth(25);
		tableRanking.getColumnModel().getColumn(4).setPreferredWidth(25);
		contentPane.setLayout(null);
		contentPane.add(scrollPaneRanking);

		
		Vector<User> allusers = facade.getAllUsers();
		Vector<String[]> sortedUsers = new Vector<String[]>();

		for (User u : allusers) {

			float[] r = facade.getEarnedMoney(u);
			float rating = 0;
			
				
	
			rating = r[0] * r[1] * r[2];
			
			System.out.print(r[0] + "****************");
			String[] s = { u.getUserName(), String.valueOf(rating), String.valueOf(r[0]), String.valueOf(r[1]),
					String.valueOf(r[2]) };
			sortedUsers.add(s);

		}

		Collections.sort(sortedUsers, Comparator.comparing((String[] arr) -> Float.parseFloat(arr[1])).reversed());
		int kont = 1;
		for (String[] user : sortedUsers) {
			System.out.println(Arrays.toString(user));
			Vector<Object> row = new Vector<Object>();

			row.add(kont);
			row.add(user[0]);
			row.add(user[2]);
			row.add(user[3]);
			row.add(user[4]);
			tableModelRanking.addRow(row);
			kont++;
		}

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				BLFacade facade = MainGUI.getBusinessLogic();
				User u = facade.getUser(userName);
				myScreen.setVisible(false);
				if (userName == "") {
					MainGUI a = new MainGUI(language);
					a.setVisible(true);
				} else if (u.isAdmin()) {
					LandscapeAdminGUI a = new LandscapeAdminGUI(userName, language);
					a.setVisible(true);
				} else if (!u.isAdmin()) {
					LandscapeRegisterGUI a = new LandscapeRegisterGUI(userName, language);
					a.setVisible(true);
				}

			}
		});
		btnBack.setBounds(33, 447, 107, 27);
		contentPane.add(btnBack);

		JButton btnClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnClose.setBounds(462, 447, 107, 27);
		btnClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				;
			}
		});
		
		contentPane.add(btnClose);

		JButton btnFollowUser = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FollowUser"));
		
		scrollPaneRanking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableRanking.getSelectedRow();
				selectedUser = (String) tableRanking.getValueAt(i, 1);
				if (!selectedUser.equals(userName)) {
					btnFollowUser.setEnabled(true);
				}else {
					btnFollowUser.setEnabled(false);
				}

			}
		});
		btnFollowUser.setEnabled(false);
		btnFollowUser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				facade.addFollower(userName, selectedUser);
				LandscapeRegisterGUI a = new LandscapeRegisterGUI(userName, language);
				a.setVisible(true);
				myScreen.setVisible(false);
			}
		});
		btnFollowUser.setBounds(223, 449, 156, 23);
		contentPane.add(btnFollowUser);

		scrollPaneRanking.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {	
				if (!selectedUser.equals(userName)) {
					btnFollowUser.setEnabled(true);
				}else {
					btnFollowUser.setEnabled(false);
				}

			}
		});
	}
}
