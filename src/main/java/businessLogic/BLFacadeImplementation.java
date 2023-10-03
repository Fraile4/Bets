package businessLogic;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.ConfigXML;
import configuration.UtilDate;
import dataAccess.DataAccess;
import domain.*;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;

/**
 * It implements the business logic as a web service.
 */
@WebService(endpointInterface = "businessLogic.BLFacade")
public class BLFacadeImplementation implements BLFacade {
	DataAccess dbManager;

	public BLFacadeImplementation() {
		System.out.println("Creating BLFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			dbManager = new DataAccess(c.getDataBaseOpenMode().equals("initialize"));
			dbManager.initializeDB();
		} else
			dbManager = new DataAccess();
		dbManager.close();

	}

	public BLFacadeImplementation(DataAccess da) {

		System.out.println("Creating BLFacadeImplementation instance with DataAccess parameter");
		ConfigXML c = ConfigXML.getInstance();

		if (c.getDataBaseOpenMode().equals("initialize")) {
			da.open(true);
			da.initializeDB();
			da.close();

		}
		dbManager = da;
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	public Question createQuestion(Event event, String[] question, float betMinimum, Vector<Quote> quotes)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManager.open(false);
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManager.createQuestion(event, question, betMinimum, quotes);

		dbManager.close();

		return qry;
	};

	/**
	 * This method invokes the data access to retrieve the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEvents(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method retrieves the events of a given date which has minimum one query.
	 * 
	 * @param date
	 * @return
	 */
	public Vector<Event> getEventsWithQueries(Date date) {
		dbManager.open(false);
		Vector<Event> events = dbManager.getEventsWithQueries(date);
		dbManager.close();
		return events;
	}

	/**
	 * This method invokes the data access to retrieve the dates a month for which
	 * there are events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsMonth(date);
		dbManager.close();
		return dates;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events with queries.
	 */
	public Vector<Date> getEventsWithQueriesMonth(Date date) {
		dbManager.open(false);
		Vector<Date> dates = dbManager.getEventsWithQueriesMonth(date);
		dbManager.close();
		return dates;
	}

	public void close() {
		DataAccess dB4oManager = new DataAccess(false);

		dB4oManager.close();

	}

	/**
	 * This method invokes the data access to initialize the database with some
	 * events and questions. It is invoked only when the option "initialize" is
	 * declared in the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD() {
		dbManager.open(false);
		dbManager.initializeDB();
		dbManager.close();
	}

	/**
	 * This method helps to know if that userName has been loged before.
	 * 
	 * @param userName
	 * @param password
	 * @return true if it's in the data base.
	 */
	@WebMethod
	public boolean isLogin(String userName, String password) {

		dbManager.open(false);

		boolean emaitza = dbManager.isLogin(userName, password);
		dbManager.close();
		return emaitza;
	}

	/**
	 * This method is used to register people in the data base.
	 * 
	 * @param userName
	 * @param password
	 */
	@WebMethod
	public void register(String userName, String password, boolean Admin) {

		dbManager.open(false);
		dbManager.register(userName, password, Admin);
		dbManager.close();

	}

	/**
	 * This method add funds to the user.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void addFunds(String userName, Float funds, String type) {
		dbManager.open(false);
		dbManager.addFunds(userName, funds, type);
		dbManager.close();
	}

	/**
	 * This method is used to remove selected user.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void removeUser(String userName) {

		dbManager.open(false);
		dbManager.removeUser(userName);
		dbManager.close();
	}

	/**
	 * This method returns selected user.
	 * 
	 * @param userName
	 * @return
	 */
	@WebMethod
	public User getUser(String userName) {
		dbManager.open(false);
		User u = dbManager.getUser(userName);
		dbManager.close();

		return u;

	}

	@WebMethod
	public Vector<domain.Event> getAllEvents() {

		dbManager.open(false);
		Vector<Event> getAllEvents = dbManager.getAllEvents();
		dbManager.close();
		return getAllEvents;
	}

	@WebMethod
	public void createEvent(String description, Date eventDate) {

		dbManager.open(false);
		dbManager.createEvent(description, eventDate);
		dbManager.close();
	}

	/**
	 * This method is used to remove selected event.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void removeEvent(int eventNumber) {
		dbManager.open(false);
		dbManager.removeEvent(eventNumber);
		dbManager.close();
	}

	/**
	 * This method is used to add Bets to the dataBase.
	 */
	@WebMethod
	public void addQuestion(Question q) throws QuestionAlreadyExist {
		dbManager.open(false);
		dbManager.addQuestion(q);
		dbManager.close();
	}

	@WebMethod
	public void addQuestionToEvent(int qN, int eN) {
		dbManager.open(false);
		dbManager.addQuestionToEvent(qN, eN);
		dbManager.close();
	}

	/**
	 * @WebMethod public void addQuoteToQuestion(int quoteN, int questionN) {
	 *            dbManager.open(false); dbManager.addQuoteToQuestion(quoteN,
	 *            questionN); dbManager.close(); }
	 **/

	/**
	 * This method adds the last money movement.
	 * 
	 * @param add
	 * @param amount
	 */
	@WebMethod
	public void addMovements(String userName, Transaction t) {
		dbManager.open(false);
		dbManager.addMovements(userName, t);
		dbManager.close();
	}

	/**
	 * This method updates the received question
	 */
	@WebMethod
	public void updateQuestion(Question question) {
		dbManager.open(false);
		dbManager.updateQuestion(question);
		dbManager.close();
	}

	/**
	 * 
	 */
	@WebMethod
	public void addQuote(Quote q, Question question) {// throws QuoteAlreadyExist
		dbManager.open(false);
		dbManager.addQuote(q, question);
		dbManager.close();
	}

	/**
	 * This method updates the received event
	 */
	@WebMethod
	public void updateEvent(Event event) {
		dbManager.open(false);
		dbManager.updateEvent(event);
		dbManager.close();
	}

	@Override
	public Vector<Quote> getAllQuotes(Question question) {
		dbManager.open(false);
		Vector<Quote> quotes = dbManager.getAllQuotes(question);
		dbManager.close();
		return quotes;
	}

	@Override
	public Vector<domain.Bet> getAllBets() {
		dbManager.open(false);
		Vector<Bet> b = dbManager.getAllBets();
		dbManager.close();
		return b;
	}

	@Override
	public Quote getQuote(int i) {
		dbManager.open(false);
		Quote q = dbManager.getQuote(i);
		dbManager.close();
		return q;
	}

	@Override
	public Question getQuestion(int i) {
		dbManager.open(false);
		Question q = dbManager.getQuestion(i);
		dbManager.close();
		return q;

	}

	@Override
	public void addBet(Bet bet, String userName) {
		dbManager.open(false);
		dbManager.addBet(bet, userName);
		dbManager.close();
	}

	@Override
	public void updateUser(User user) {
		dbManager.open(false);
		dbManager.updateUser(user);
		dbManager.close();
	}

	@Override
	public Vector<Bet> getBetsFromQuote(Quote quote) {
		dbManager.open(false);
		Vector<Bet> bets = dbManager.getBetsFromQuote(quote);
		dbManager.close();
		return bets;
	}

	@Override
	public void addTransaction(User u, Transaction transaction) {
		dbManager.open(false);
		dbManager.addTransaction(u, transaction);
		dbManager.close();
	}

	@Override
	public Vector<User> getAllUsers() {
		dbManager.open(false);
		Vector<User> users = dbManager.getAllUsers();
		dbManager.close();
		return users;
	}


	@Override
	public int getLastQuoteNumber() {
		dbManager.open(false);
		int max = dbManager.getLastQuoteNumber();
		dbManager.close();
		return max;
	}

	@Override
	public void setQuestionResult(int quoteNumber, int questionNumber) {
		dbManager.open(false);
		dbManager.setQuestionResult(quoteNumber, questionNumber);
		dbManager.close();
	}

	@Override
	public void addFollower(String follower, String followed) {
		dbManager.open(false);
		dbManager.addFollower(follower, followed);
		dbManager.close();
	}
	
	@Override
	public void removeQuestion(int i, Vector<Integer> v) {
		dbManager.open(false);
		dbManager.removeQuestion(i,v);
		dbManager.close();
	}
	
	@Override
	public void makeBet(String userName, float moneyToBet, Vector<Quote> quotes) {
		dbManager.open(false);
		dbManager.makeBet(userName, moneyToBet, quotes);
		dbManager.close();
	}
	
	@Override
	public float[] getEarnedMoney(User uN) {
		dbManager.open(false);
		float[] r = dbManager.getEarnedMoney(uN);
		dbManager.close();
		return r;
	}
	
	@Override
	public Vector<String> getTypedUser(String query, String userName){
		dbManager.open(false);
		Vector<String> userList = dbManager.getTypedUser(query, userName);
		dbManager.close();
		return userList;
	}

}
