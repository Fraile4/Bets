package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlIDREF;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@SuppressWarnings("serial")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Quote {

	@Id
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@GeneratedValue
	@XmlIDREF
	private int qNumber;
	private String[] qAnswer = new String[3];
	private Float qFloat;
	private Question question;
	private String state;

	public Quote() {
		super();
	}

	public Quote(String[] qAnswer, Float qFloat, int qNumber, Question query) {
		super();
		this.qAnswer = qAnswer;
		this.qFloat = qFloat;
		this.qNumber = qNumber;
		this.question = query;
		this.state = "looserQuote";
	
	}

	public Quote(String[] qAnswer, Float qFloat, Question query) {
		super();
		this.qAnswer = qAnswer;
		this.qFloat = qFloat;
		this.question = query;
		this.state = "looserQuote";
	}

	public String[] getqAnswer() {
		return qAnswer;
	}

	public void setqAnswer(String[] qAnswer) {
		this.qAnswer = qAnswer;
	}

	public Float getqFloat() {
		return qFloat;
	}

	public void setqFloat(Float qFloat) {
		this.qFloat = qFloat;
	}

	public int getqNumber() {
		return qNumber;
	}

	public void setqNumber(int qNumber) {
		this.qNumber = qNumber;
	}

	public Question getQuestion() {
		return question;
	}
	

	@Override
	public String toString() {
		return "Quote{" + "qNumber=" + qNumber + ", qAnswer='" + qAnswer + '\'' + ", qFloat=" + qFloat + '}';
	}

	public String getState() {
		return state;
	}

	public void setState(String param) {
		state = param;
	}

}
