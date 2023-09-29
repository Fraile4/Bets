package gui;

/**
 * @author Software Engineering teachers
 */


import javax.swing.*;

import domain.Event;
import businessLogic.BLFacade;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout.Alignment;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;


public class MainGUI extends JFrame {
	
	private JFrame myScreen;
	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonRegister = null;
	private JButton jButtonCreateQuery = null;
	private JButton jButtonQueryQueries = null;
	private String language = "";

    private static BLFacade appFacadeInterface;
	
	public static BLFacade getBusinessLogic(){
		return appFacadeInterface;
	}
	 
	public static void setBussinessLogic (BLFacade afi){
		appFacadeInterface=afi;
	}
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnQueryPlayerRanking;
	
	/**
	 * This is the default constructor
	 */
	public MainGUI(String language) {
		super();
		
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					//if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println("Error: "+e1.toString()+" , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});
		myScreen = this;
		
		initialize();
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		if(language.equals("es")) {
			rdbtnNewRadioButton_2.setSelected(true);
		}else if(language.equals("eus")) {
			rdbtnNewRadioButton_1.setSelected(true);
		}else{
			rdbtnNewRadioButton.setSelected(true);
			}
	}
	
	

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		this.setSize(495, 394);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setLayout(new GridLayout(0, 1, 0, 0));
			jContentPane.add(getLblNewLabel());
			jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getRegisterButton());
			jContentPane.add(getBtnQueryPlayerRanking());
			jContentPane.add(getPanel());
			
			
		}
		return jContentPane;
	}

	/**
	 *This method initializes RegisterButton 
	 *  
	 */
	private JButton getRegisterButton() {
        if (jButtonRegister == null) {
            jButtonRegister = new JButton();
            jButtonRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterOption"));
            jButtonRegister.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	if(rdbtnNewRadioButton_2.isSelected()) {
                		language = "es";
                	}else	if(rdbtnNewRadioButton.isSelected()) {
                		language = "en";
                	}else{
                		language = "eus";
                	}
                    JFrame r = new RegisterGUI(language);
                    r.setVisible(true);
                    myScreen.setVisible(false);
                }
            });
        }
    return jButtonRegister;
    }
	
	
	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {
		if (jButtonCreateQuery == null) {
			jButtonCreateQuery = new JButton();
			jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUILogin"));
			jButtonCreateQuery.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(rdbtnNewRadioButton_2.isSelected()) {
                		language = "es";
                	}else	if(rdbtnNewRadioButton.isSelected()) {
                		language = "en";
                	}else{
                		language = "eus";
                	}
					JFrame a = new LoginGUI(language);
					a.setVisible(true);
					myScreen.setVisible(false);
				}
			});
		}
		return jButtonCreateQuery;
	}
	
	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton3() {
		if (jButtonQueryQueries == null) {
			jButtonQueryQueries = new JButton();
			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
				public void actionPerformed(java.awt.event.ActionEvent e) {
					if(rdbtnNewRadioButton_2.isSelected()) {
                		language = "es";
                	}else	if(rdbtnNewRadioButton.isSelected()) {
                		language = "en";
                	}else{
                		language = "eus";
                	}
					JFrame a = new FindQuestionsGUI("",language);
					
					a.setVisible(true);
					myScreen.setVisible(false);
				}
			});
		}
		return jButtonQueryQueries;
	}
	

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}
	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnNewRadioButton.setBounds(300, 18, 92, 23);
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					language = "en";
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}
	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnNewRadioButton_1.setBounds(115, 18, 84, 23);
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					language = "eus";
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}
	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.setHorizontalAlignment(SwingConstants.CENTER);
			rdbtnNewRadioButton_2.setBounds(201, 18, 97, 23);
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					language = "es";
					System.out.println("Locale: "+Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}
	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setLayout(null);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}
	
	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
		jButtonCreateQuery.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUILogin"));
		jButtonRegister.setText(ResourceBundle.getBundle("Etiquetas").getString("RegisterOption"));
		btnQueryPlayerRanking.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryPlayersRanking"));
		
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
	}
	private JButton getBtnQueryPlayerRanking() {
		if (btnQueryPlayerRanking == null) {
			btnQueryPlayerRanking = new JButton(ResourceBundle.getBundle("Etiquetas").getString("QueryPlayersRanking"));
			btnQueryPlayerRanking.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					RankingPlayersGUI r = new RankingPlayersGUI("", language);
					r.setVisible(true);
					myScreen.setVisible(false);
					
				}
			});
		}
		return btnQueryPlayerRanking;
	}
} // @jve:decl-index=0:visual-constraint="0,0"

