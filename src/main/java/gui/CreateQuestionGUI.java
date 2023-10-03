package gui;

import java.text.DateFormat;

import java.util.*;

import javax.swing.*;

import com.toedter.calendar.JCalendar;

import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.Event;
import domain.Question;
import domain.Quote;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;

public class CreateQuestionGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JComboBox<Event> jComboBoxEvents = new JComboBox<Event>();
	DefaultComboBoxModel<Event> modelEvents = new DefaultComboBoxModel<Event>();

	private JFrame myScreen;
	
	private JLabel jLabelListOfEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("ListEvents"));
	private JLabel jLabelQuery = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private JLabel jLabelMinBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MinimumBetPrice"));
	private JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));

	private JTextField tfQuestionEn = new JTextField();
	private JTextField tfMinBet = new JTextField();
	private JCalendar jCalendar = new JCalendar();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private JScrollPane scrollPaneEvents = new JScrollPane();

	private JButton jButtonBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
	private JButton jButtonCreate = new JButton(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestion"));
	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JLabel jLabelMsg = new JLabel();
	private JLabel jLabelError = new JLabel();
	
	
	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField tfQuestionEs;
	private JTextField tfQuestionEus;
	private JLabel jblErrorFinishedEvent = new JLabel();
	private final JLabel jblErrorQuestionExists = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuestionExists")); //$NON-NLS-1$ //$NON-NLS-2$

	public CreateQuestionGUI(Vector<domain.Event> v,String userName, String language) {
		myScreen = this;
		try {
			jbInit(v,userName, language);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(Vector<domain.Event> v,String userName, String language) throws Exception {
		
		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(746, 509));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestion"));

		jComboBoxEvents.setModel(modelEvents);
		jComboBoxEvents.setBounds(new Rectangle(277, 50, 376, 20));
		jLabelListOfEvents.setBounds(new Rectangle(290, 18, 277, 20));
		jLabelQuery.setBounds(new Rectangle(35, 208, 91, 20));
		tfQuestionEn.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.tfQuestionEn.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuestionEn.setBounds(new Rectangle(33, 245, 534, 20));
		jLabelMinBet.setHorizontalAlignment(SwingConstants.RIGHT);
		jLabelMinBet.setBounds(new Rectangle(25, 373, 130, 20));
		tfMinBet.setBounds(new Rectangle(162, 373, 80, 20));

		jCalendar.setBounds(new Rectangle(40, 50, 225, 150));
		scrollPaneEvents.setBounds(new Rectangle(25, 44, 346, 116));

		jButtonCreate.setBounds(new Rectangle(307, 425, 130, 30));
		jButtonCreate.setEnabled(false);

		jButtonCreate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonCreate_actionPerformed(e, userName, language);
			}
		});
		jButtonClose.setBounds(new Rectangle(575, 425, 130, 30));
		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButtonClose_actionPerformed(e);
			}
		});

		jButtonBack.setBounds(new Rectangle(25, 425, 130, 30));
		jButtonBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				LandscapeAdminGUI m = new LandscapeAdminGUI(userName, language);
				m.setVisible(true);
				myScreen.setVisible(false);

			}
		});
		jLabelMsg.setBounds(new Rectangle(262, 373, 305, 20));
		jLabelMsg.setForeground(Color.red);
		// jLabelMsg.setSize(new Dimension(305, 20));

		jLabelError.setBounds(new Rectangle(262, 197, 305, 20));
		jLabelError.setForeground(Color.red);

		this.getContentPane().add(jLabelMsg, null);
		this.getContentPane().add(jLabelError, null);

		this.getContentPane().add(jButtonClose, null);
		this.getContentPane().add(jButtonBack, null);
		this.getContentPane().add(jButtonCreate, null);
		this.getContentPane().add(tfQuestionEn, null);
		this.getContentPane().add(jLabelQuery, null);
		this.getContentPane().add(tfMinBet, null);

		this.getContentPane().add(jLabelMinBet, null);
		this.getContentPane().add(jLabelListOfEvents, null);
		this.getContentPane().add(jComboBoxEvents, null);

		this.getContentPane().add(jCalendar, null);
		
		
		BLFacade facade = MainGUI.getBusinessLogic();
		datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
		paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);
		
		

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelEventDate.setBounds(40, 16, 140, 25);
		getContentPane().add(jLabelEventDate);
		
		tfQuestionEs = new JTextField();
		tfQuestionEs.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuestionEs.setBounds(33, 286, 534, 21);
		getContentPane().add(tfQuestionEs);
		tfQuestionEs.setColumns(10);
		
		tfQuestionEus = new JTextField();
		tfQuestionEus.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuestionGUI.textField_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuestionEus.setBounds(33, 326, 534, 21);
		getContentPane().add(tfQuestionEus);
		tfQuestionEus.setColumns(10);
		
		jblErrorFinishedEvent = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("FinishedEvent")); //$NON-NLS-1$ //$NON-NLS-2$
		jblErrorFinishedEvent.setFont(new Font("Tahoma", Font.ITALIC, 12));
		jblErrorFinishedEvent.setForeground(Color.RED);
		jblErrorFinishedEvent.setHorizontalAlignment(SwingConstants.CENTER);
		jblErrorFinishedEvent.setBounds(277, 380, 219, 13);
		getContentPane().add(jblErrorFinishedEvent);
		jblErrorQuestionExists.setFont(new Font("Tahoma", Font.ITALIC, 12));
		jblErrorQuestionExists.setForeground(Color.RED);
		jblErrorQuestionExists.setHorizontalAlignment(SwingConstants.CENTER);
		jblErrorQuestionExists.setBounds(262, 380, 236, 13);
		
		getContentPane().add(jblErrorQuestionExists);
		jblErrorFinishedEvent.setVisible(false);
		jblErrorQuestionExists.setVisible(false);
		
		// Code for JCalendar
		this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
//				this.jCalendar.addPropertyChangeListener(new PropertyChangeListener() {
//					public void propertyChange(PropertyChangeEvent propertychangeevent) {
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					System.out.println("calendarAnt: "+calendarAnt.getTime());
					System.out.println("calendarAct: "+calendarAct.getTime());
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar.getLocale());
					
					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct!=monthAnt) {
						if (monthAct==monthAnt+2) { 
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolverá 2 de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt+1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}
						
						jCalendar.setCalendar(calendarAct);
						
						BLFacade facade = MainGUI.getBusinessLogic();

						datesWithEventsCurrentMonth=facade.getEventsMonth(jCalendar.getDate());
					}



					paintDaysWithEvents(jCalendar,datesWithEventsCurrentMonth);

					//	Date firstDay = UtilDate.trim(new Date(jCalendar.getCalendar().getTime().getTime()));
					Date firstDay = UtilDate.trim(calendarAct.getTime());

					try {
						BLFacade facade = MainGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents")
									+ ": " + dateformat1.format(calendarAct.getTime()));
						else
							jLabelListOfEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						jComboBoxEvents.removeAllItems();
						System.out.println("Events " + events);

						for (domain.Event ev : events)
							modelEvents.addElement(ev);
						jComboBoxEvents.repaint();

						if (events.size() == 0)
							jButtonCreate.setEnabled(false);
						else
							jButtonCreate.setEnabled(true);

					} catch (Exception e1) {

						jLabelError.setText(e1.getMessage());
					}

				}
			}
		});
	}

	
public static void paintDaysWithEvents(JCalendar jCalendar,Vector<Date> datesWithEventsCurrentMonth) {
		// For each day with events in current month, the background color for that day is changed to cyan.

		
		Calendar calendar = jCalendar.getCalendar();
		
		int month = calendar.get(Calendar.MONTH);
		int today=calendar.get(Calendar.DAY_OF_MONTH);
		int year=calendar.get(Calendar.YEAR);
		
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		int offset = calendar.get(Calendar.DAY_OF_WEEK);

	/*	if (Locale.getDefault().equals(new Locale("es")))
			offset += 4;
		else*/
			offset += 5;
		
		
	 	for (Date d:datesWithEventsCurrentMonth){

	 		calendar.setTime(d);
	 		System.out.println(d);
	 		

			
			// Obtain the component of the day in the panel of the DayChooser of the
			// JCalendar.
			// The component is located after the decorator buttons of "Sun", "Mon",... or
			// "Lun", "Mar"...,
			// the empty days before day 1 of month, and all the days previous to each day.
			// That number of components is calculated with "offset" and is different in
			// English and Spanish
//			    		  Component o=(Component) jCalendar.getDayChooser().getDayPanel().getComponent(i+offset);; 
			Component o = (Component) jCalendar.getDayChooser().getDayPanel()
					.getComponent(calendar.get(Calendar.DAY_OF_MONTH) + offset);
			o.setBackground(Color.CYAN);
	 	}
	 	
 			calendar.set(Calendar.DAY_OF_MONTH, today);
	 		calendar.set(Calendar.MONTH, month);
	 		calendar.set(Calendar.YEAR, year);

	 	
	}
	
	 
	private void jButtonCreate_actionPerformed(ActionEvent e,String userName, String language) {
		jblErrorFinishedEvent.setVisible(false);
		jblErrorQuestionExists.setVisible(false);
		try {
			jLabelError.setText("");
			jLabelMsg.setText("");

			// Displays an exception if the query field is empty
			String inputQuery = tfQuestionEn.getText();
			

			if (inputQuery.length() > 0 ) {

				// It could be to trigger an exception if the introduced string is not a number
				float inputPrice = Float.parseFloat(tfMinBet.getText().trim());

				if (inputPrice <= 0)
					jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
				else {
					// Obtain the business logic from a StartWindow class (local or remote)
					//BLFacade facade = MainGUI.getBusinessLogic();
					
					String[] allQueries = new String[3];
					allQueries[0] = tfQuestionEn.getText();
					allQueries[1] = tfQuestionEs.getText();
					allQueries[2] = tfQuestionEus.getText();
					//Question question = new domain.Question(allQueries, inputPrice ,(Event) jComboBoxEvents.getSelectedItem());

					Event event = (Event) jComboBoxEvents.getSelectedItem();
					
					BLFacade facade = MainGUI.getBusinessLogic();
					Question fQuestion = null;
					try {
						fQuestion = facade.createQuestion(event,allQueries,inputPrice,new Vector<Quote>());
						CreateQuoteGUI cq = new CreateQuoteGUI(userName, language, fQuestion.getQuestionNumber());
						cq.setVisible(true);
						myScreen.setVisible(false);
						jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryCreated"));
					} catch(exceptions.EventFinished e1) {
						jblErrorFinishedEvent.setVisible(true);
					} catch (exceptions.QuestionAlreadyExist e2) {
						jblErrorQuestionExists.setVisible(true);
					}
					
				}
			} else
				jLabelMsg.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorQuery"));
		} catch (java.lang.NumberFormatException e1) {
			jLabelError.setText(ResourceBundle.getBundle("Etiquetas").getString("ErrorNumber"));
		} catch (Exception e1) {

			e1.printStackTrace();

		}
	}

	
	private void jButtonClose_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		System.exit(0);
	}
}