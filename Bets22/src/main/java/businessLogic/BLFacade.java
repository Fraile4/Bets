package businessLogic;

import java.util.Vector;
import java.util.Date;

//import domain.Booking;
import domain.*;

import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;

import javax.jws.WebMethod;
import javax.jws.WebService;

import configuration.UtilDate;

/**
 * Interface that specifies the business logic.
 */
@WebService
public interface BLFacade {

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @param quote      of the question
	 * @return the created question, or null, or an exception
	 * @throws EventFinished        if current data is after data of the event
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	@WebMethod
	Question createQuestion(Event event, String[] question, float betMinimum, Vector<Quote> quotes)
			throws EventFinished, QuestionAlreadyExist;

	/**
	 * This method retrieves the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	@WebMethod
	public Vector<Event> getEvents(Date date);

	/**
	 * This method retrieves the events of a given date which has minimum one query.
	 * 
	 * @param date
	 * @return
	 */
	@WebMethod
	public Vector<Event> getEventsWithQueries(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	@WebMethod
	public Vector<Date> getEventsMonth(Date date);

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events with queries.
	 */
	@WebMethod
	public Vector<Date> getEventsWithQueriesMonth(Date date);

	/**
	 * This method calls the data access to initialize the database with some events
	 * and questions. It is invoked only when the option "initialize" is declared in
	 * the tag dataBaseOpenMode of resources/config.xml file
	 */
	@WebMethod
	public void initializeBD();

	/**
	 * This method helps to know if that userName has been loged before.
	 * 
	 * @param userName
	 * @param password
	 * @return true if it's in the data base.
	 */
	@WebMethod
	public boolean isLogin(String userName, String password);

	/**
	 * This method is used to register people in the data base.
	 * 
	 * @param userName
	 * @param password
	 */
	@WebMethod
	public void register(String userName, String password, boolean Admin);

	/**
	 * This method add funds to the user.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void addFunds(String userName, Float funds, String type);

	/**
	 * This method is used to remove selected user.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void removeUser(String userName);

	/**
	 * This method returns selected user.
	 * 
	 * @param userName
	 * @return
	 */
	@WebMethod
	public User getUser(String userName);

	/**
	 * This method returns all events on the data base.
	 */
	@WebMethod
	public Vector<domain.Event> getAllEvents();

	/**
	 * This method creates an event.
	 */
	@WebMethod
	public void createEvent(String description, Date eventDate);

	/**
	 * This method is used to remove selected event.
	 * 
	 * @param userName
	 */
	@WebMethod
	public void removeEvent(int eventNumber);

	/**
	 * This method is used to add Bets to the dataBase.
	 */
	@WebMethod
	public void addQuestion(Question q) throws QuestionAlreadyExist;

	@WebMethod
	public void addQuestionToEvent(int qN, int eN);

	/**
	 * @WebMethod public void addQuoteToQuestion(int quoteN, int questionN);
	 **/

	/**
	 * This method adds the last money movement.
	 * 
	 * @param add
	 * @param amount
	 */
	@WebMethod
	public void addMovements(String userName, Transaction t);

	/**
	 * This method updates the received question
	 * 
	 * @param question
	 */
	@WebMethod
	public void updateQuestion(Question question);

	/**
	 * This method adds the received quote.
	 * 
	 * @param question
	 */
	@WebMethod
	public void addQuote(Quote quote, Question question) ;//throws QuoteAlreadyExist;

	/**
	 * This method updates the received question
	 * 
	 * @param question
	 */
	@WebMethod
	public void updateEvent(Event event);

	/**
	 * This returns all the quotes of the received question.
	 * 
	 * @param question
	 */
	@WebMethod
	public Vector<Quote> getAllQuotes(Question question);

	/**
	 * This returns all Bets.
	 * 
	 * @return
	 */
	@WebMethod
	public Vector<Bet> getAllBets();

	/**
	 * This method returns a Quote.
	 * 
	 * @param i
	 * @return
	 */
	@WebMethod
	public Quote getQuote(int i);

	/**
	 * This method returns a Question.
	 * 
	 * @param i
	 * @return
	 */
	@WebMethod
	public Question getQuestion(int i);

	/**
	 * This method adds the Bet to the dataBase.
	 * 
	 * @param i
	 * @return
	 */
	@WebMethod
	public void addBet(Bet bet, String userName);

	/**
	 * This method updates the received user
	 * 
	 * @param question
	 */
	@WebMethod
	public void updateUser(User user);

	/**
	 * This method returns a Vector with the required quotes
	 * 
	 * @param quote
	 * @return
	 */
	@WebMethod
	public Vector<Bet> getBetsFromQuote(Quote quote);

	/**
	 * This method adds the Transaction to the dataBase.
	 * 
	 * @param i
	 * @return
	 */
	@WebMethod
	public void addTransaction(User u, Transaction transaction);

	/**
	 * This method returns all users on the data base.
	 */
	@WebMethod
	public Vector<User> getAllUsers();

	@WebMethod
	public int getLastQuoteNumber();

	@WebMethod
	public void setQuestionResult(int quotN, int queN);
	
	@WebMethod
	public void addFollower(String follower, String followed);
	
	@WebMethod
	public void makeBet(String userName, float moneyToBet, Vector<Quote> quotes);
		
	@WebMethod 
	public float[] getEarnedMoney(User username);
	
	@WebMethod 
	public Vector<String> getTypedUser(String query, String userName);
	
	@WebMethod
	public void removeQuestion(int i, Vector<Integer> v);
}



