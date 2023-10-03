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
public class Bet implements Serializable {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlIDREF
	private Integer betNumber;
	private User user;
	private float importedMoney;
	

	private Vector<Quote> quote = new Vector<Quote>();
	
	
	public Bet() {
		super();
	}

	public Bet(User user, Integer betNumber, Vector<Quote> quote , float importedMoney) {
		super();
		this.user = user;
		this.betNumber = betNumber;
		this.quote = quote;
		this.importedMoney = importedMoney;
		
	}

	public Bet(User user, Vector<Quote> quote , float importedMoney, String notPlayed) {
		super();
		this.user = user;
		this.quote = quote;
		this.importedMoney = importedMoney;
	}


	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return this.betNumber + ";"+ this.quote.toString() + ";" + Float.toString(importedMoney);
	}

	public Integer getBetNumber() {
		return betNumber;
	}

	public void setBetNumber(Integer betNumber) {
		this.betNumber = betNumber;
	}

	public Vector<Quote> getQuotes() {
		return quote;
	}

	public void setQuotes(Vector<Quote> quote) {
		this.quote = quote;
	}

	public float getImportedMoney() {
		return importedMoney;
	}

	public void setImportedMoney(float importedMoney) {
		this.importedMoney = importedMoney;
	}
	
	public float getTotalQuote() {
		float totalQuote = 0;
		for(Quote q : this.getQuotes()) totalQuote = totalQuote + q.getqFloat();
		totalQuote = totalQuote / this.getQuotes().size();
		
		return totalQuote;
	}

}

