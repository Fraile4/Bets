package domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlID;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlAccessorType(XmlAccessType.FIELD)
@Entity
public class Transaction {

	@XmlID
	@XmlJavaTypeAdapter(IntegerAdapter.class)
	@Id
	@GeneratedValue
	private int transactionNumber;
	private Float amount;
	private String type;
	private User userT;

	public Transaction() {
		super();
	}

	public Transaction(User userT, Float amount, String type) {
		this.amount = amount;
		this.type = type;
		this.userT = userT;
	}

	public Float getAmount() {
		return amount;
	}

	public void setAmount(Float amount) {
		this.amount = amount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getTransactionNumber() {
		return transactionNumber;
	}

	public void setTransactionNumber(int transactionNumber) {
		this.transactionNumber = transactionNumber;
	}

	public User getUser() {
		return this.userT;
	}

	public void setUser(User userT) {
		this.userT = userT;
	}

	@Override
	public String toString() {
		String str = "Transaction " + this.transactionNumber + ": ";
		if (this.type.equals("Bet")) {
			str += "-";
		}
		str += "$" + this.amount;
		return str;
	}

}
