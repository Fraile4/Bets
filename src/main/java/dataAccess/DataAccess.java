package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.jws.WebMethod;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.*;
import exceptions.QuestionAlreadyExist;
import exceptions.QuoteAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		this(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			db.getTransaction().commit();
			System.out.println("Db initialized");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String[] question, float betMinimum, Vector<Quote> quotes)
			throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum, quotes);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves the events of a given date which has minimum one query.
	 * 
	 * @param date
	 * @return
	 */
	public Vector<Event> getEventsWithQueries(Date date) {
		Vector<domain.Event> evWithQueries = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<domain.Event> events = query.getResultList();
		for (Event ev : events) {
			if (ev.getQuestions().size() != 0) {
				System.out.println(ev.toString());
				evWithQueries.add(ev);
			}
		}
		return evWithQueries;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events with queries.
	 */
	public Vector<Date> getEventsWithQueriesMonth(Date date) {
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Event> query = db
				.createQuery("SELECT DISTINCT ev FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Event.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Event> events = query.getResultList();
		for (Event e : events) {
			if (e.getQuestions().size() != 0) {
				System.out.println(e.toString());
				res.add(e.getEventDate());
			}
		}
		return res;
	}

	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public boolean existQuestion(Event event, String[] question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	/**
	 * This method helps to know if that userName has been loged before.
	 * 
	 * @param userName
	 * @param password
	 * @return true if it's in the data base.
	 */
	public boolean isLogin(String userName, String password) {
		boolean found = false;
		db.getTransaction().begin();
		User user = db.find(User.class, userName);
		if (user != null) {
			if (user.getUserName().equals(userName) && user.getPassword().equals(password))
				found = true;
		}
		db.getTransaction();

		return found;
	}

	/**
	 * This method is used to register people in the data base.
	 * 
	 * @param userName
	 * @param password
	 */
	public void register(String userName, String password, boolean admin) {

		db.getTransaction().begin();
		try {
			User u = new User(userName, password, admin);

			db.persist(u);
		} catch (Exception e) {
			e.printStackTrace();
		}

		db.getTransaction().commit();

	}

	/**
	 * This method add funds to the user.
	 * 
	 * @param userName
	 */
	public void addFunds(String userName, Float funds, String type) {

		db.getTransaction().begin();
		User u = db.find(User.class, userName);
		Transaction t = new Transaction(u, funds, type);
		u.setMoney(u.getMoney() + funds); // Here we got the complete amount of money;
		u.addTransaction(t);
		db.persist(u);

		db.getTransaction().commit();

	}

	/**
	 * This method is used to remove selected user.
	 * 
	 * @param userName
	 */
	public void removeUser(String userName) {

		User rUser = db.find(User.class, userName);

		db.getTransaction().begin();

		for (Transaction t : rUser.getMovements())
			db.remove(t);

		for (Bet b : rUser.getBets())
			db.remove(b);

		db.remove(rUser);

		db.getTransaction().commit();
	}

	/**
	 * This method returns selected user.
	 * 
	 * @param userName
	 * @return
	 */
	public User getUser(String userName) {
		db.getTransaction().begin();
		User u = db.find(User.class, userName);
		db.getTransaction().commit();

		return u;

	}

	public Vector<domain.Event> getAllEvents() {

		db.getTransaction().begin();
		Query query = db.createQuery("SELECT event FROM Event event");
		List<Event> getAllEvents1 = query.getResultList();
		Vector<Event> getAllEvents = new Vector<>(getAllEvents1);
		db.getTransaction();

		return getAllEvents;
	}

	public void createEvent(String description, Date eventDate) {
		Event event = new Event(description, eventDate);
		Vector<domain.Event> eventsondate = this.getEvents(eventDate);
		boolean posible = true;
		int i = 0;
		while (posible && i < eventsondate.size()) {
			if (eventsondate.get(i).getDescription().equals(description)) {
				posible = false;
			} else {
				i++;
			}
		}
		if (posible) {
			db.getTransaction().begin();
			TypedQuery<Integer> query = db.createQuery("SELECT MAX(e.eventNumber) FROM Event e", Integer.class);
			int maxEventNumber = -1;

			try {
				maxEventNumber = query.getSingleResult();
			} catch (java.lang.NullPointerException e) {
				maxEventNumber = 0;
			}

			event.setEventNumber(maxEventNumber + 1);
			db.persist(event);
			db.getTransaction().commit();
		}

	}

	public void removeEvent(int eventNumber) {
		Event e = db.find(Event.class, eventNumber);

		for (Question question : e.getQuestions()) {
			for (Quote quote : question.getQuotes()) {
				float nwTotalQuote = 1;
				for (Bet b : this.getBetsFromQuote(quote)) {

					boolean buleano = true;
					for (Quote quo : b.getQuotes()) {

						if (!quo.equals(quote)) {
							if (quo.getState().equals("looserQuote")) {
								buleano = false;
								break;
							} else {
								nwTotalQuote = nwTotalQuote * quo.getqFloat();

							}
						}

					}
					if (buleano && !question.isFinished()) {

						this.addFunds(b.getUser().getUserName(), nwTotalQuote * b.getImportedMoney(), "Bet Win");
					}

					db.getTransaction().begin();
					db.remove(b);
					db.getTransaction().commit();
				}
				db.getTransaction().begin();
				db.remove(quote);
				db.getTransaction().commit();
			}
			db.getTransaction().begin();
			db.remove(question);
			db.getTransaction().commit();
		}
		db.getTransaction().begin();
		db.remove(e);
		db.getTransaction().commit();

	}

	public void addQuestion(Question q) throws QuestionAlreadyExist {
		db.getTransaction().begin();
		TypedQuery<Integer> query = db.createQuery("SELECT MAX(q.questionNumber) FROM Question q", Integer.class);
		int maxQuestionNumber = -1;
		try {
			maxQuestionNumber = query.getSingleResult();
		} catch (java.lang.NullPointerException e1) {
			maxQuestionNumber = 0;
		}
		q.setQuestionNumber(maxQuestionNumber + 1);

		db.persist(q);
		db.getTransaction().commit();
	}

	public void addQuestionToEvent(int qN, int eN) {
		db.getTransaction().begin();
		Event e = db.find(Event.class, eN);
		Question q = db.find(Question.class, qN);
		Vector<Question> eventQuestions = e.getQuestions();
		if (!eventQuestions.contains(q))
			eventQuestions.add(q);
		db.persist(e);
		db.getTransaction().commit();
	}

	public void addMovements(String userName, Transaction t) {

		User u = db.find(User.class, userName);

		if (u.getMovements() != null) {

			db.getTransaction().begin();
			u.getMovements().add(t);
			db.persist(u);
			db.getTransaction().commit();
		}
	}

	public void updateQuestion(Question question) {
		db.getTransaction().begin();
		db.merge(question);
		db.getTransaction().commit();
	}

	public void addQuote(Quote quote, Question question) { // throws QuoteAlreadyExist
		db.getTransaction().begin();
		Question q = db.find(Question.class, question.getQuestionNumber());
		TypedQuery<Integer> query = db.createQuery("SELECT MAX(q.qNumber) FROM Quote q", Integer.class);

		/*
		 * if (quote.getQuestion().DoesQuoteExists(quote.getqAnswer())) throw new
		 * QuoteAlreadyExist();
		 */

		int maxQuoteNumber = -1;
		try {
			maxQuoteNumber = query.getSingleResult();
		} catch (java.lang.NullPointerException e1) {
			maxQuoteNumber = 0;
		}
		quote.setqNumber(maxQuoteNumber + 1);
		q.addQuote(quote);

		db.persist(q);

		db.getTransaction().commit();

	}

	public int getLastQuoteNumber() {
		db.getTransaction().begin();
		TypedQuery<Integer> query = db.createQuery("SELECT MAX(q.qNumber) FROM Quote q", Integer.class);
		int max = -1;
		try {
			max = query.getSingleResult();
		} catch (java.lang.NullPointerException e1) {
			max = 0;
		}
		db.getTransaction().commit();
		return max;
	}

	public void updateEvent(Event event) {
		db.getTransaction().begin();
		db.merge(event);
		db.getTransaction().commit();
	}

	public Vector<Quote> getAllQuotes(Question question) {
		db.getTransaction().begin();
		Question q = db.find(Question.class, question.getQuestionNumber());
		q.getQuotes().size();
		db.getTransaction().commit();
		return q.getQuotes();
	}

	public Vector<domain.Bet> getAllBets() {
		db.getTransaction().begin();
		Query query = db.createQuery("SELECT bet FROM Bet bet");
		List<domain.Bet> getAllBets = query.getResultList();
		Vector<domain.Bet> allBets = new Vector<domain.Bet>(getAllBets);
		db.getTransaction().commit();

		return allBets;
	}

	public Quote getQuote(int i) {
		db.getTransaction().begin();
		Quote q = db.find(Quote.class, i);
		db.getTransaction().commit();
		return q;
	}

	public Question getQuestion(int i) {
		db.getTransaction().begin();
		Question q = db.find(Question.class, i);
		db.getTransaction().commit();

		return q;
	}

	public void addBet(Bet bet, String userName) {
		// db.getTransaction().begin();
		TypedQuery<Integer> query = db.createQuery("SELECT MAX(bet.betNumber) FROM Bet bet", Integer.class);
		int maxBetNumber = -1;
		try {
			maxBetNumber = query.getSingleResult();
		} catch (java.lang.NullPointerException e) {
			maxBetNumber = 0;
		}

		bet.setBetNumber(maxBetNumber + 1);

		Transaction t = new Transaction(bet.getUser(), bet.getImportedMoney(), "Bet");
		User u = db.find(User.class, userName);
		// db.getTransaction().commit();
		u.addTransaction(t);
		u.setMoney(u.getMoney() - bet.getImportedMoney());
		u.addBet(bet);
		db.getTransaction().begin();
		db.persist(u);
		db.getTransaction().commit();
	}

	public void updateUser(User user) {
		db.getTransaction().begin();
		for (Bet bet : user.getBets()) {
			db.merge(bet);
		}
		db.merge(user);
		db.getTransaction().commit();
	}

	public Vector<Bet> getBetsFromQuote(Quote quote) {
		db.getTransaction().begin();
		Query query = db.createQuery("SELECT b FROM Bet b WHERE b.quote.qNumber = :quoteNumber");
		query.setParameter("quoteNumber", quote.getqNumber());
		List<Bet> resultList = query.getResultList();
		Vector<Bet> result = new Vector<Bet>(resultList);
		db.getTransaction().commit();
		return result;
	}

	public void addTransaction(User u, Transaction transaction) {
		db.getTransaction().begin();
		TypedQuery<Integer> query = db.createQuery("SELECT MAX(t.transactionNumber) FROM Transaction t", Integer.class);
		int maxTransactionNumber = -1;
		try {
			maxTransactionNumber = query.getSingleResult();
		} catch (java.lang.NullPointerException e) {
			maxTransactionNumber = 0;
		}

		transaction.setTransactionNumber(maxTransactionNumber + 1);
		u.addTransaction(transaction);
		db.merge(u);

		db.getTransaction().commit();
	}

	
	public void removeQuestion(int qn, Vector<Integer> quoteNs) {
		Question q = db.find(Question.class, qn);
		Vector<Question> evQuestions = q.getEvent().getQuestions();
		Vector<Question> newQuestion = new Vector<Question>();
		for(Question question : evQuestions) {
			if(!question.equals(q)) {
				newQuestion.add(question);
			}
		}
		q.getEvent().setQuestions(newQuestion);
		db.getTransaction().begin();
		db.remove(q);
		for(Integer i : quoteNs) {
			Quote quote = db.find(Quote.class, i);
			db.remove(quote);
		}
		db.persist(q.getEvent());
		db.getTransaction().commit();
	}
	public Vector<User> getAllUsers() {
		db.getTransaction().begin();
		Query query = db.createQuery("SELECT u FROM User u WHERE u.admin = false");
		List<User> resultList = query.getResultList();
		Vector<User> allUsers = new Vector<User>(resultList);
		db.getTransaction().commit();

		return allUsers;
	}

	public void addFollower(String follower, String followed) {
		db.getTransaction().begin();
		User follower1 = db.find(User.class, follower);
		User followed1 = db.find(User.class, followed);

		followed1.addFollowers(follower1);
		follower1.addFollowed(followed1);
		db.persist(followed1);
		db.persist(follower1);
		db.getTransaction().commit();

	}

	public void makeBet(String userName, float moneyToBet, Vector<Quote> quotes) {
		User user = this.getUser(userName);
		Bet b = new Bet(user, quotes, moneyToBet, "notPlayed");
		this.addBet(b, userName);

		for (User u : this.getUser(userName).getFollowers()) {
			Bet bet = new Bet(u, quotes, moneyToBet, "notPlayed");
			this.addBet(bet, u.getUserName());
		}

		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();

	}

	public void setQuestionResult(int questionNumber, int quoteNumber) {

		Question question = db.find(Question.class, questionNumber);
		Quote quote = db.find(Quote.class, quoteNumber);

		for (Quote q : question.getQuotes()) {

			if (q.getqNumber() != quoteNumber) {
				db.getTransaction().begin();
				q.setState("looserQuote");
				db.persist(q);
				db.getTransaction().commit();

			} else {
				db.getTransaction().begin();
				quote.setState("win");
				db.persist(quote);
				db.getTransaction().commit();
			}

		}

		for (Bet b : this.getBetsFromQuote(quote)) {
			boolean buleano = true;
			for (Quote quo : b.getQuotes()) {
				if (quo.getState().equals("looserQuote")) {
					buleano = false;
					break;
				}
			}
			if (buleano) {
				this.addFunds(b.getUser().getUserName(), b.getImportedMoney() * b.getTotalQuote(), "Bet Win");
			}
		}
		question.setFinished(true);
		db.getTransaction().begin();
		db.persist(question);
		db.getTransaction().commit();
	}

	public float[] getEarnedMoney(User userName) {
		float result = 0;
		float kont = 0;
		int kontBet = 0;
		float wR;
		for (Transaction t : userName.getMovements()) {
			if (t.getType().equals("Bet Win")) {
				result += t.getAmount();
				kont++;
			} else if (t.getType().equals("Bet")) {
				kontBet++;
			}
		}
		if (kontBet != 0) {
		wR = kont / kontBet;
		}else {
			wR = 0;
		}
		float[] r = { result, kont, wR };
		return r;
	}

	public Vector<String> getTypedUser(String query, String userName) {
		Vector<String> userList = new Vector<String>();
		PreparedStatement stmt;
		ResultSet rs;
		if(!query.trim().isEmpty()) {
		TypedQuery<domain.User> dbQuery = db.createQuery("SELECT u FROM User u WHERE u.userName LIKE :query AND u.admin = false",
				User.class);
		dbQuery.setParameter("query", "%" + query + "%");

		List<User> users = dbQuery.getResultList();

		for (User u : users) {
			boolean found = false;
			for(User use : this.getUser(userName).getFolloweds()) {
				if(u.equals(use)) 
					found = true;
			}
			if(!u.getUserName().equals(userName) && !found)
			userList.add(u.getUserName());
		}
		}
		return userList;
	}
}

//Prueba
