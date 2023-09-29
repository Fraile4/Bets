package gui;

import businessLogic.BLFacade;

import domain.Question;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

import domain.Quote;
import exceptions.QuoteAlreadyExist;
import domain.Event;

public class CreateQuoteGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private JFrame myScreen;

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
	private JScrollPane scrollPaneQuotes = new JScrollPane();

	private JTable tableQuotes = new JTable();

	private DefaultTableModel tableModelQuotes;

	private String[] columnNamesQuotes = new String[] { ResourceBundle.getBundle("Etiquetas").getString("Quotes"),
			ResourceBundle.getBundle("Etiquetas").getString("QuoteInfo"),

	};

	private JButton btnCreateQuote = new JButton(ResourceBundle.getBundle("Etiquetas").getString("AddQuote"));
	private JTextField tfQuoteEn;
	private JTextField tfQuote;
	private JTextField tfQuoteEs;
	private JTextField tfQuoteEus;

	JLabel jblQuoteAlreadyExists = new JLabel();

	public CreateQuoteGUI(String username, String language, int question) {
		myScreen = this;

		try {
			jbInit(username, language, question);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit(String userName, String language, int qnumber) throws Exception {

		jblQuoteAlreadyExists = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("jblQuoteAlreadyExists")); //$NON-NLS-1$ //$NON-NLS-2$
		jblQuoteAlreadyExists.setHorizontalAlignment(SwingConstants.CENTER);
		jblQuoteAlreadyExists.setForeground(Color.RED);
		jblQuoteAlreadyExists.setFont(new Font("Tahoma", Font.ITALIC, 12));
		jblQuoteAlreadyExists.setBounds(297, 550, 305, 21);
		getContentPane().add(jblQuoteAlreadyExists);
		jblQuoteAlreadyExists.setVisible(false);
		BLFacade fe = MainGUI.getBusinessLogic();
		Question question = fe.getQuestion(qnumber);
		Event event = question.getEvent();

		this.setSize(new Dimension(899, 680));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("CreateQuote"));
		jButtonClose.setBounds(688, 587, 130, 27);

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);

			}
		});
		getContentPane().setLayout(null);

		this.getContentPane().add(jButtonClose);
		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableQuotes.setModel(tableModelQuotes);

		if (question.getQuotes() != null) {
			for (domain.Quote q : question.getQuotes()) {
				Vector<Object> row = new Vector<Object>();

				row.add(q.getqNumber());
				row.add(q.getqAnswer());
				row.add(q); // quote object added in order to obtain it with
							// tableModelQuotes.getValueAt(i,2)

				tableModelQuotes.addRow(row);
			}

		}

		scrollPaneQuotes.setBounds(191, 250, 500, 290);

		tableModelQuotes.setColumnCount(3);
		tableQuotes.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQuotes.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneQuotes);

		JButton btnBack = new JButton(ResourceBundle.getBundle("Etiquetas").getString("GetBack"));
		btnBack.setBounds(90, 587, 107, 27);
		btnBack.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				BLFacade f = MainGUI.getBusinessLogic();
				Vector<Integer> quoteNumbers = new Vector<Integer>();
				
				for(Quote quotes: question.getQuotes()) {
					quoteNumbers.add(quotes.getqNumber());
				}
				f.removeQuestion(question.getQuestionNumber(), quoteNumbers);
				CreateQuestionGUI cq = new CreateQuestionGUI(f.getAllEvents(), userName, language);
				cq.setVisible(true);
				myScreen.setVisible(false);

			}
		});
		getContentPane().add(btnBack);

		tfQuote = new JTextField();
		tfQuote.setText("");
		tfQuote.setBounds(325, 201, 345, 21);
		getContentPane().add(tfQuote);
		tfQuote.setColumns(10);

		JLabel jblMinTwoBet = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("TwoQuotes")); //$NON-NLS-1$ //$NON-NLS-2$
		jblMinTwoBet.setFont(new Font("Dialog", Font.ITALIC, 11));
		jblMinTwoBet.setForeground(Color.RED);
		jblMinTwoBet.setHorizontalAlignment(SwingConstants.CENTER);
		jblMinTwoBet.setBounds(272, 551, 338, 14);
		getContentPane().add(jblMinTwoBet);
		jblMinTwoBet.setVisible(false);

		JLabel lblPleaseJustNumbers = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("lblPleaseJustNumbers"));
		lblPleaseJustNumbers.setFont(new Font("Dialog", Font.ITALIC, 11));
		lblPleaseJustNumbers.setForeground(Color.RED);
		lblPleaseJustNumbers.setHorizontalAlignment(SwingConstants.CENTER);
		lblPleaseJustNumbers.setBounds(289, 551, 305, 14);
		getContentPane().add(lblPleaseJustNumbers);
		lblPleaseJustNumbers.setVisible(false);

		btnCreateQuote.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				jblQuoteAlreadyExists.setVisible(false);
				jblMinTwoBet.setVisible(false);
				try {

					BLFacade facade = MainGUI.getBusinessLogic();

					String[] qAnswer = new String[3];
					qAnswer[0] = tfQuoteEn.getText();
					qAnswer[1] = tfQuoteEs.getText();
					qAnswer[2] = tfQuoteEus.getText();

					Float qFloat = Float.parseFloat(tfQuote.getText());
					boolean bool = true;
					if (tableModelQuotes.getRowCount() != 0) {

						for (int i = 0; i < tableModelQuotes.getRowCount(); i++) {
							if (Float.valueOf((float)tableModelQuotes.getValueAt(i, 0)).equals(qFloat)
									| qFloat <= 1) {
								bool = false;

							}
							for (String s : qAnswer) {
								if (((String) tableModelQuotes.getValueAt(i, 1)).equals(s)) {
									bool = false;
									break;
								}
							}
							if (bool)
								break;
						}
					} else {
						if (qFloat <= 1) {
							bool = false;
						}
					}
					if (bool) {

						Quote newQuote = new Quote(qAnswer, qFloat, question);
						facade.addQuote(newQuote, question);
						Vector<Object> row = new Vector<Object>();
						row.add(newQuote.getqFloat());

						if (language == "en")
							row.add(qAnswer[0]);
						else if (language == "es")
							row.add(qAnswer[1]);
						else
							row.add(qAnswer[2]);

						row.add(newQuote);
						tableModelQuotes.addRow(row);

						if (!event.getQuestions().contains(question)) {
							event.getQuestions().add(question);
						}

					} else {
						jblQuoteAlreadyExists.setVisible(true);
					}
				} catch (NumberFormatException er) {

					lblPleaseJustNumbers.setVisible(true);

				}

			}
		});

		scrollPaneQuotes.setViewportView(tableQuotes);
		tableModelQuotes = new DefaultTableModel(null, columnNamesQuotes);
		tableQuotes.setModel(tableModelQuotes);

		btnCreateQuote.setBounds(249, 587, 158, 27);
		getContentPane().add(btnCreateQuote);

		JLabel jblQuoteInfo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("QuoteInfo")); //$NON-NLS-1$ //$NON-NLS-2$
		jblQuoteInfo.setHorizontalAlignment(SwingConstants.RIGHT);
		jblQuoteInfo.setBounds(149, 36, 158, 17);
		getContentPane().add(jblQuoteInfo);

		tfQuoteEn = new JTextField();
		tfQuoteEn.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuoteGUI.tfQuoteEn.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuoteEn.setBounds(325, 34, 345, 21);
		getContentPane().add(tfQuoteEn);
		tfQuoteEn.setColumns(10);

		JLabel jblQuote = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Quotes"));
		jblQuote.setHorizontalAlignment(SwingConstants.RIGHT);
		jblQuote.setBounds(159, 203, 148, 17);
		getContentPane().add(jblQuote);

		JButton btnFinishBet = new JButton(ResourceBundle.getBundle("Etiquetas").getString("FinishBet"));
		btnFinishBet.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (tableModelQuotes.getRowCount() > 1) {
					jblMinTwoBet.setVisible(false);
					LandscapeAdminGUI l = new LandscapeAdminGUI(userName, language);
					l.setVisible(true);
					myScreen.setVisible(false);
				} else {
					jblMinTwoBet.setVisible(true);
				}

			}
		});
		btnFinishBet.setBounds(469, 589, 158, 23);
		getContentPane().add(btnFinishBet);

		tfQuoteEs = new JTextField();
		tfQuoteEs.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuoteGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuoteEs.setBounds(325, 67, 345, 21);
		getContentPane().add(tfQuoteEs);
		tfQuoteEs.setColumns(10);

		tfQuoteEus = new JTextField();
		tfQuoteEus.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateQuoteGUI.textField.text")); //$NON-NLS-1$ //$NON-NLS-2$
		tfQuoteEus.setBounds(325, 100, 345, 21);
		getContentPane().add(tfQuoteEus);
		tfQuoteEus.setColumns(10);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		this.setVisible(false);
		System.exit(0);
	}
}