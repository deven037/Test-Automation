package flight;

import java.time.LocalDate;

import org.testng.Assert;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.FlightBook;

public class OneWayTest extends BaseTest{
  @Test
  public void oneWayFlight() throws Exception {
	  
	  LocalDate startDate = LocalDate.of(2026, 1, 31);
	  navigateTo("booking_url");
	  
	  FlightBook flightBook = new FlightBook(driver);
	  
	  flightBook.selectFlight();
	  flightBook.chooseFlightType("oneway");
	  flightBook.clickArrival();
	  flightBook.enterArrival("JFK");
	  System.out.println("Arrival is entered....");
	  flightBook.openCalendar();
	  System.out.println("calendar is openend.....");
	  flightBook.selectStartDate(startDate);
	  flightBook.clickSearch();
	  flightBook.waitForResultLoaderDisapper();
	  System.out.println("Result loader disappered....");
	  verifyResults();
  }
  
  protected void verifyResults() {
	  FlightBook flightBook = new FlightBook(driver);
	  Assert.assertTrue(flightBook.results());
  }
}
