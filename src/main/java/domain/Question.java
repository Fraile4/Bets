package domain;

import java.io.*;
import java.util.Vector;

import javax.persistence.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Question implements Serializable {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlIDREF
	private Integer questionNumber;
	private String[] question = new String[3];
	private float betMinimum;
	private Event event;
	private boolean finished = false;
	@OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
	private Vector<Quote> quotes = new Vector<Quote>();

	public Question() {
		super();
	}

	public Question(Integer queryNumber, String[] query, float betMinimum, Event event) {
		super();
		this.questionNumber = queryNumber;
		this.question = query;
		this.betMinimum = betMinimum;
		this.event = event;
	}

	public Question(String[] query, float betMinimum, Event event) {
		super();
		this.question = query;
		this.betMinimum = betMinimum;
		this.event = event;

	}

	/**
	 * Get the number of the question
	 * 
	 * @return the question number
	 */
	public Integer getQuestionNumber() {
		return questionNumber;
	}

	public Vector<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(Vector<Quote> quotes) {
		this.quotes = quotes;
	}

	/**
	 * Set the bet number to a question
	 * 
	 * @param questionNumber to be setted
	 */
	public void setQuestionNumber(Integer questionNumber) {
		this.questionNumber = questionNumber;
	}

	/**
	 * Get the question description of the bet
	 * 
	 * @return the bet question
	 */

	public String[] getQuestion() {
		return question;
	}

	/**
	 * Set the question description of the bet
	 * 
	 * @param question to be setted
	 */
	public void setQuestion(String[] question) {
		this.question = question;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @return the minimum bet ammount
	 */

	public float getBetMinimum() {
		return betMinimum;
	}

	/**
	 * Get the minimun ammount of the bet
	 * 
	 * @param betMinimum minimum bet ammount to be setted
	 */

	public void setBetMinimum(float betMinimum) {
		this.betMinimum = betMinimum;
	}

	/**
	 * Get the event associated to the bet
	 * 
	 * @return the associated event
	 */
	public Event getEvent() {
		return event;
	}

	/**
	 * Set the event associated to the bet
	 * 
	 * @param event to associate to the bet
	 */
	public void setEvent(Event event) {
		this.event = event;
	}
	
	public boolean isFinished() {
		return finished;
	}

	public void setFinished(boolean finished) {
		this.finished = finished;
	}

	public String toString() {
		return questionNumber + ";" + question + ";" + Float.toString(betMinimum);
	}
	
	/**
	 * This method checks if the question already exists for that event
	 * 
	 * @param question that needs to be checked if there exists
	 * @return true if the question exists and false in other case
	 */	
	public boolean DoesQuoteExists(String[] qAnswer)  {	
		for (Quote q:this.getQuotes()){
			String[] quote = q.getqAnswer();
			if (quote[0].compareTo(question[0])==0)
				return true;
		}
		return false;
	}
	
	public void addQuote(Quote q) {
		if(!this.quotes.contains(q))
		this.quotes.add(q);
		
	}

	
}
