import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.BeforeClass;
import org.junit.Test;

import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
//import dataAccess.DataAccessInterface;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import domain.Quote;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class CreateQuestionInt {
	 static BLFacadeImplementation sut;
	 static TestFacadeImplementation testBL;

	private Event ev;
	
	@BeforeClass
	public static void setUpClass() {
		//sut= new BLFacadeImplementation();
		
		// you can parametrize the DataAccess used by BLFacadeImplementation
		//DataAccess da= new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		DataAccess da= new DataAccess();

		sut=new BLFacadeImplementation(da);
		
		testBL= new TestFacadeImplementation();
	}
	
	@Test
	//sut.createQuestion:  The event has one question with a queryText. 
	public void test1() {
		try {
			
			//define paramaters
			String eventText="event1";
			String[] queryText= {"query1", "galdera1", "pregunta1"};
			Float betMinimum=new Float(2);
			Vector<Quote> quoteText = new Vector<Quote>();
			String[] qAnswer = {"Yes", "Sí", "Bai"};
			Quote quote1 = new Quote(qAnswer,(float) 1.5, (Question) null);
			String[] qAnswer2 = {"No", "No", "Ez"};
			Quote quote2 =  new Quote(qAnswer,(float) 2.5, (Question) null);;
			quoteText.add(quote1);
			quoteText.add(quote2);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure the state of the system (create object in the dabatase)
			ev = testBL.addEventWithQuestion(eventText,oneDate,queryText,betMinimum, quoteText );
			
			
			//invoke System Under Test (sut)  
			sut.createQuestion(ev, queryText, betMinimum, quoteText);
			
			
			//if the program continues fail
		    fail();
		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			// if the program goes to this point OK  
			assertTrue(true);
			} catch (EventFinished e) {
				// if the program goes to this point fail
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				  //Remove the created objects in the database (cascade removing)   
		          boolean b=testBL.removeEvent(ev);
		           System.out.println("Finally "+b);          
		        }
		   }
	
	@Test
	//sut.createQuestion:  The event has NOT one question with a queryText. 
	public void test2() {
		try {
			
			//define paramaters
			String eventText="event1";
			String[] queryText= {"query1", "galdera1", "pregunta1"};
			Float betMinimum=new Float(2);
			Vector quoteText = new Vector<Quote>();
			String[] qAnswer = {"Yes", "Sí", "Bai"};
			Quote quote1 = new Quote(qAnswer,(float) 1.5, (Question) null);
			String[] qAnswer2 = {"No", "No", "Ez"};
			Quote quote2 =  new Quote(qAnswer,(float) 2.5, (Question) null);;
			quoteText.add(quote1);
			quoteText.add(quote2);
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate=null;;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}	
			
			//configure the state of the system (create object in the dabatase)
			ev = testBL.addEventWithQuestion(eventText,oneDate,queryText, betMinimum, quoteText );
			
			//invoke System Under Test (sut)  
			Question q=sut.createQuestion(ev, queryText, betMinimum, quoteText);
			
			
			//verify the results
			assertTrue(q!=null);
			assertEquals(q.getQuestion(),queryText);
			assertEquals(q.getBetMinimum(),betMinimum,0);
			
			
		   } catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail  
			fail();
			} catch (EventFinished e) {
				// if the program goes to this point fail
			    fail();
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				  //Remove the created objects in the database (cascade removing)   
		          boolean b=testBL.removeEvent(ev);
		           System.out.println("Finally "+b);          
		        }
		   }
	
}
