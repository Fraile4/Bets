package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.Bet;
import domain.Question;
import domain.Quote;
import domain.Transaction;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableModel;

public class MakeBetGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JFrame myScreen;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();
	private JScrollPane scrollPaneQuotes = new JScrollPane();
	private JScrollPane scrollPaneBets = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableQuotes = new JTable();
	private JTable tableBets = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelQuotes;
	private DefaultTableModel tableModelBets;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesQuotes = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QuoteN"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteInfo"),
			ResourceBundle.getBundle("Etiquetas").getString("Quotes") };

	private String[] columnNamesBets = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Event"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteInfo"),
			ResourceBundle.getBundle("Etiquetas").getString("Quotes"),
			ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteN") };

	private final JLabel jblFunds = new JLabel("Funds"); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jblMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBet")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jblQueryMinBet = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("MakeBetGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jbl€ = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MakeBetGUI.label.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel jbl€2 = new JLabel("€");
	private final JLabel jblTypeAmount = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TypeAmount")); //$NON-NLS-1$ //$NON-NLS-2$
	private JTextField tfTypeAmount;
	private final JLabel lblPleaseJustNumbers = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("lblPleaseJustNumbers"));

	private final JLabel jbl€2_1 = new JLabel("€");
	private final JLabel jblError1MB = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Error1MB"));
	private final JLabel jblError2MB = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Error2MB"));
	private JButton btnBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Bet2"));
	private final JLabel jLabelQuotes = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quotes")); //$NON-NLS-1$ //$NON-NLS-2$

	private Question selectedQuestion;
	private domain.Event selectedEvent;
	private Quote selectedQuote;
	private final JLabel jblBetMade = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("BetPlaced"));

	public MakeBetGUI(String userName, String language) {
		myScreen = this;

		try {
			jbInit(userName, language);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(String userName, String language) throws Exception {
		BLFacade facade1 = MainGUI.getBusinessLogic();
		BLFacade f = MainGUI.getBusinessLogic();
		this.setSize(new Dimension(1343, 801));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
		jblError1MB.setBounds(478, 671, 386, 17);
		jblError1MB.setVisible(false);
		jblError2MB.setBounds(478, 671, 386, 17);
		jblError2MB.setVisible(false);
		jblBetMade.setBounds(565, 694, 212, 14);
		jblBetMade.setVisible(false);
		jblFunds.setBounds(521, 57, 130, 17);

		jblFunds.setText(String.valueOf(f.getUser(userName).getMoney()));
		getContentPane().setLayout(null);
		jLabelEventDate.setBounds(40, 15, 140, 25);
		this.getContentPane().add(jLabelEventDate);
		jLabelQueries.setBounds(810, 218, 163, 14);
		this.getContentPane().add(jLabelQueries);
		jLabelEvents.setBounds(810, 29, 259, 16);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setBounds(1139, 720, 130, 27);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose);
		lblPleaseJustNumbers.setBounds(523, 671, 297, 17);

		lblPleaseJustNumbers.setFont(new Font("Dialog", Font.ITALIC, 12));
		lblPleaseJustNumbers.setForeground(Color.RED);
		lblPleaseJustNumbers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseJustNumbers.setVisible(false);
		getContentPane().add(lblPleaseJustNumbers);

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsWithQueriesMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);
		jCalendar1.setBounds(40, 50, 290, 162);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsWithQueriesMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelQueries.setDataVector(null, columnNamesQueries);
						tableModelQuotes.setDataVector(null, columnNamesQuotes);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEventsWithQueries(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							Vector<Object> row = new Vector<Object>();

							System.out.println("Events " + ev);

							row.add(ev.getEventNumber());
							row.add(ev.getDescription());
							row.add(ev); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)
							tableModelEvents.addRow(row);
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1);

		// Kuoten JScrollPane-a sortu
		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				selectedEvent = ev;
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {

					if (!q.isFinished()) {
						Vector<Object> row = new Vector<Object>();
						String[] questions = q.getQuestion();
						row.add(q.getQuestionNumber());
						if (language == "en")
							row.add(questions[0]);
						else if (language == "es")
							row.add(questions[1]);
						else
							row.add(questions[2]);
						row.add(q); // ev object added in order to obtain it with tableModelEvents.getValueAt(i,2)

						tableModelQueries.addRow(row);
					}

				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});
		
//Events
		scrollPaneEvents.setBounds(809, 55, 500, 150);
		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);
		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
		

//Queries
		scrollPaneQueries.setBounds(851, 244, 406, 184);
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableModelQueries.setDataVector(null, columnNamesQueries);
		tableModelQueries.setColumnCount(3);
		tableQueries.setModel(tableModelQueries);

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQueries.getSelectedRow();

				domain.Question q = facade.getQuestion((int) tableModelQueries.getValueAt(i, 0)); // obtain question.
				selectedQuestion = q;
				String minBet = String.valueOf(q.getBetMinimum());

				BLFacade facade = MainGUI.getBusinessLogic();
				Vector<Quote> quotes = facade.getAllQuotes(q);

				if (quotes.isEmpty()) {
					jLabelQuotes.setText(ResourceBundle.getBundle("Etiquetas").getString("NoQuotes"));
				} else {

					tableModelQuotes.setRowCount(0);

					for (Quote q1 : quotes) {

						Vector<Object> row = new Vector<Object>();
						String[] quoteAns = q1.getqAnswer();
						row.add(q1.getqNumber());

						if (language == "en")
							row.add(quoteAns[0]);
						else if (language == "es")
							row.add(quoteAns[1]);
						else
							row.add(quoteAns[2]);

						row.add(q1.getqFloat());
						row.add(q1);

						tableModelQuotes.addRow(row);
					}

				}

				
			}
		});

		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableQueries.getColumnModel().removeColumn(tableQueries.getColumnModel().getColumn(2));
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableModelQuotes.setDataVector(null, columnNamesQuotes);
		tableModelQuotes.setColumnCount(3);
		scrollPaneQuotes.setBounds(809, 468, 500, 184);

		scrollPaneBets.setViewportView(tableBets);
		tableModelBets = new DefaultTableModel(null, columnNamesBets);
		tableModelBets.setDataVector(null, columnNamesBets);

		tableBets.setModel(tableModelBets);
		tableModelBets.setColumnCount(5);
		scrollPaneBets.setBounds(27, 261, 687, 327);
		tableBets.getColumnModel().getColumn(0).setPreferredWidth(60);
		tableBets.getColumnModel().getColumn(1).setPreferredWidth(40);
		tableBets.getColumnModel().getColumn(2).setPreferredWidth(50);
		tableBets.getColumnModel().getColumn(3).setPreferredWidth(10);
		tableBets.getColumnModel().getColumn(3).setPreferredWidth(10);

		this.getContentPane().add(scrollPaneEvents);
		this.getContentPane().add(scrollPaneQueries);
		this.getContentPane().add(scrollPaneBets);
		this.getContentPane().add(scrollPaneQuotes);

		scrollPaneQuotes.setViewportView(tableQuotes);

		tableQuotes.setModel(tableModelQuotes);
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(25);
		tableQuotes.getColumnModel().getColumn(2).setPreferredWidth(268);

		tableQuotes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQuotes.getSelectedRow();

				selectedQuote = f.getQuote((int) tableModelQuotes.getValueAt(i, 0));

				btnBet.setEnabled(true);

				selectedQuestion = f.getQuestion(selectedQuestion.getQuestionNumber());

				Vector<Object> row = new Vector<Object>();
				String[] quoteAns = selectedQuote.getqAnswer();
				row.add(selectedEvent.getDescription());
				float max = selectedQuestion.getBetMinimum();
				if (tableModelBets.getRowCount() == 0) {
					if (language == "en")
						row.add(quoteAns[0]);
					else if (language == "es")
						row.add(quoteAns[1]);
					else
						row.add(quoteAns[2]);

					row.add(selectedQuote.getqFloat());
					row.add(selectedQuestion.getQuestionNumber());
					row.add(selectedQuote.getqNumber());

					tableModelBets.addRow(row);
					jblQueryMinBet.setText(String.valueOf(max));
				} else {
					
					boolean found = false;
					for (int k = 0; k < tableModelBets.getRowCount(); k++) {
						if ((int) tableModelBets.getValueAt(k, 3) == selectedQuestion.getQuestionNumber()) {
							found = true;

							break;
						}
						if (((Question) f.getQuestion((int) tableModelBets.getValueAt(k, 3))).getBetMinimum() > max) {
							max = ((Question) f.getQuestion((int) tableModelBets.getValueAt(k, 3))).getBetMinimum();
						}
					}
					if (!found) {
						if (language == "en")
							row.add(quoteAns[0]);
						else if (language == "es")
							row.add(quoteAns[1]);
						else
							row.add(quoteAns[2]);

						row.add(selectedQuote.getqFloat());
						row.add(selectedQuestion.getQuestionNumber());
						row.add(selectedQuote.getqNumber());

						tableModelBets.addRow(row);
						jblQueryMinBet.setText(String.valueOf(max));
					}
				}
			}
		});

		tableBets.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableBets.getSelectedRow();

				tableModelBets.removeRow(i);
				float max = 0;
				for (int k = 0; k < tableModelBets.getRowCount(); k++) {

					if (((Question) f.getQuestion((int) tableModelBets.getValueAt(k, 3))).getBetMinimum() > max) {
						max = ((Question) f.getQuestion((int) tableModelBets.getValueAt(k, 3))).getBetMinimum();
					}
				}
				jblQueryMinBet.setText(String.valueOf(max));
				if(tableModelBets.getRowCount()==0)
					btnBet.setEnabled(false);
			}
		});

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack")); //$NON-NLS-1$ //$NON-NLS-2$
		btnBack.setBounds(73, 720, 107, 27);
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
		getContentPane().add(btnBack);

		JLabel jblYourFunds = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("YourFunds1"));
		jblYourFunds.setBounds(330, 56, 186, 17);
		jblYourFunds.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(jblYourFunds);
		jblFunds.setHorizontalAlignment(SwingConstants.RIGHT);

		getContentPane().add(jblFunds);
		jblMinBet.setBounds(330, 79, 186, 17);
		jblMinBet.setHorizontalAlignment(SwingConstants.RIGHT);

		getContentPane().add(jblMinBet);
		jblQueryMinBet.setBounds(521, 79, 130, 17);
		jblQueryMinBet.setHorizontalAlignment(SwingConstants.RIGHT);

		getContentPane().add(jblQueryMinBet);
		jbl€.setBounds(663, 57, 32, 17);

		getContentPane().add(jbl€);
		jbl€2.setBounds(663, 79, 32, 17);

		getContentPane().add(jbl€2);
		jblTypeAmount.setBounds(330, 102, 186, 17);
		jblTypeAmount.setHorizontalAlignment(SwingConstants.RIGHT);

		getContentPane().add(jblTypeAmount);

		jblQueryMinBet.setText("");

		tfTypeAmount = new JTextField();
		tfTypeAmount.setBounds(527, 100, 131, 21);
		tfTypeAmount.setHorizontalAlignment(SwingConstants.RIGHT);
		tfTypeAmount.setText("");
		getContentPane().add(tfTypeAmount);
		tfTypeAmount.setColumns(10);
		jbl€2_1.setBounds(663, 102, 32, 17);

		getContentPane().add(jbl€2_1);
		btnBet.setBounds(593, 720, 158, 27);

		btnBet.setEnabled(false);
		btnBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				BLFacade facade = MainGUI.getBusinessLogic();
				lblPleaseJustNumbers.setVisible(false);
				jblError1MB.setVisible(false);
				jblError2MB.setVisible(false);

				float moneyToBet = 0;

				if (!tfTypeAmount.getText().isEmpty()) {

					try {
						moneyToBet = Float.parseFloat(tfTypeAmount.getText());
					} catch (NumberFormatException e) {
						lblPleaseJustNumbers.setVisible(true);
					}
				}
				if (!lblPleaseJustNumbers.isVisible()) {
					User user = facade.getUser(userName);

					if (moneyToBet > user.getMoney()) {
						jblError1MB.setVisible(true);

					} else if (moneyToBet < Float.parseFloat(jblQueryMinBet.getText())) {
						jblError2MB.setVisible(true);
					}
				}

				if (!jblError1MB.isVisible() && !jblError2MB.isVisible()) {
					Vector<Quote> quotesToBet = new Vector<Quote>();

					for (int k = 0; k < tableModelBets.getRowCount(); k++) {
						quotesToBet.add(facade.getQuote((int) tableModelBets.getValueAt(k, 4)));
					}

					facade.makeBet(userName, moneyToBet, quotesToBet);
					jblBetMade.setVisible(true);
					btnBet.setEnabled(false);
					jblFunds.setText(String.valueOf(facade.getUser(userName).getMoney()));

				}
			}
		});
		getContentPane().add(btnBet);
		jblError1MB.setFont(new Font("Dialog", Font.ITALIC, 12));
		jblError1MB.setForeground(Color.RED);
		jblError1MB.setHorizontalAlignment(SwingConstants.CENTER);

		getContentPane().add(jblError1MB);
		jblError2MB.setFont(new Font("Dialog", Font.ITALIC, 12));
		jblError2MB.setForeground(Color.RED);
		jblError2MB.setHorizontalAlignment(SwingConstants.CENTER);

		getContentPane().add(jblError2MB);
		jLabelQuotes.setBounds(810, 442, 116, 14);

		getContentPane().add(jLabelQuotes);
		jblBetMade.setForeground(new Color(0, 128, 0));
		jblBetMade.setFont(new Font("Tahoma", Font.ITALIC, 11));
		jblBetMade.setHorizontalAlignment(SwingConstants.CENTER);

		getContentPane().add(jblBetMade);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		System.exit(0);
	}
}
