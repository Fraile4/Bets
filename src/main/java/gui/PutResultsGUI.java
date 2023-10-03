package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Bet;
import domain.Question;
import domain.Quote;
import domain.Transaction;
import domain.User;

import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class PutResultsGUI extends JFrame {

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

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableQuotes = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelQuotes;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private String[] columnNamesQuotes = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QuoteN"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteInfo"),
			ResourceBundle.getBundle("Etiquetas").getString("Quotes") };
	private JButton btnSelectWinner = new JButton(ResourceBundle.getBundle("Etiquetas").getString("SelectWinner"));
	private final JLabel jLabelQuotes = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("MakeBetGUI.lblQuotes.text")); //$NON-NLS-1$ //$NON-NLS-2$

	private Question selectedQuestion;
	private Quote selectedQuote;
	private final JLabel lblQuestionFinishedError = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("EmaitzaIpiniGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private Vector<Quote> quotes;

	private final JLabel lblWinnerSelected = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("EmaitzaIpiniGUI.lblWinnerSelected.text")); //$NON-NLS-1$ //$NON-NLS-2$

	
	public PutResultsGUI(String userName, String language) {
		myScreen = this;

		try {
			jbInit(userName, language);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(String userName, String language) throws Exception {

		BLFacade f = MainGUI.getBusinessLogic();
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(1117, 660));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("PutResults"));
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(40, 245, 163, 14);
		jLabelEvents.setBounds(370, 19, 259, 16);
		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		lblQuestionFinishedError.setHorizontalAlignment(SwingConstants.CENTER);
		lblQuestionFinishedError.setFont(new Font("Tahoma", Font.ITALIC, 12));
		lblQuestionFinishedError.setBackground(new Color(0, 0, 0));
		lblQuestionFinishedError.setForeground(new Color(255, 0, 0));
		lblQuestionFinishedError.setBounds(370, 548, 325, 13);
		getContentPane().add(lblQuestionFinishedError);
		lblQuestionFinishedError.setVisible(false);
		jButtonClose.setBounds(new Rectangle(919, 578, 130, 27));
		lblWinnerSelected.setVisible(false);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 290, 162));

		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth = facade.getEventsWithQueriesMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

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
						btnSelectWinner.setEnabled(false);
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

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(370, 58, 500, 150));
		scrollPaneQueries.setBounds(new Rectangle(40, 286, 406, 184));
		scrollPaneQuotes.setBounds(new Rectangle(533, 286, 500, 184));

		// Kuoten JScrollPane-a sortu

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSelectWinner.setEnabled(false);
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				Vector<Question> queries = ev.getQuestions();

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
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
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});
//Events
		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents);

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

//Queries
		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries);
		tableModelQueries.setDataVector(null, columnNamesQueries);
		tableModelQueries.setColumnCount(3);
		tableQueries.setModel(tableModelQueries);

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				btnSelectWinner.setEnabled(false);
				int i = tableQueries.getSelectedRow();

				selectedQuestion = facade.getQuestion((int) tableModelQueries.getValueAt(i, 0)); // obtain question.

				quotes = facade.getAllQuotes(selectedQuestion);

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

		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableModelQuotes.setDataVector(null, columnNamesQuotes);

		tableQuotes.setModel(tableModelQuotes);
		tableModelQuotes.setColumnCount(3);
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(25);
		tableQuotes.getColumnModel().getColumn(2).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneQuotes, null);

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack")); //$NON-NLS-1$ //$NON-NLS-2$
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
		btnBack.setBounds(61, 578, 107, 27);
		getContentPane().add(btnBack);

		tableQuotes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableQuotes.getSelectedRow();
				selectedQuestion = f.getQuestion(selectedQuestion.getQuestionNumber());
				selectedQuote = facade.getQuote((int) tableModelQuotes.getValueAt(i, 0));
				if(!selectedQuestion.isFinished()){
				btnSelectWinner.setEnabled(true);
				}
			}
		});

		btnSelectWinner.setEnabled(false);
		btnSelectWinner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				lblQuestionFinishedError.setVisible(false);
				lblWinnerSelected.setVisible(false);
				
				f.setQuestionResult(selectedQuestion.getQuestionNumber(), selectedQuote.getqNumber());
				
				btnSelectWinner.setEnabled(false);
			}	
		});
		btnSelectWinner.setBounds(456, 578, 158, 27);

		getContentPane().add(btnSelectWinner);
		jLabelQuotes.setBounds(533, 245, 116, 14);

		getContentPane().add(jLabelQuotes);
		lblWinnerSelected.setHorizontalAlignment(SwingConstants.CENTER);
		lblWinnerSelected.setFont(new Font("Tahoma", Font.PLAIN, 12));
		lblWinnerSelected.setForeground(new Color(0, 128, 64));
		lblWinnerSelected.setBounds(407, 525, 255, 13);

		getContentPane().add(lblWinnerSelected);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		System.exit(0);
	}
}
