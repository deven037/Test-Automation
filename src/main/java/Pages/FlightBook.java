package Pages;

import java.time.LocalDate;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import base.BasePage;

public class FlightBook extends BasePage {

	public FlightBook(WebDriver driver) {
		super(driver);
	}
	
	private By flight = By.xpath("//*[@id=\'flights\']");
	
	private By oneWay = By.xpath("//*[@id=\'basiclayout\']/div/div/div[2]/div/div/div/div[1]/div/fieldset/div/div[2]/label/span[2]");
	private By roundTrip = By.xpath("");
	private By multiCity = By.xpath("");
	
	private By departure = By.xpath("//*[@id=\"basiclayout\"]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div[1]/div/button[1]");
	private By arrival = By.xpath("//*[@id=\"basiclayout\"]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div[1]/div/button[3]");
	
	private By enterDepart = By.xpath("//*[@id=\":Rh6l19dl:\"]/div/div/div/div[1]/div/div/div/div/input");
	private By enterArrival = By.xpath("//*[@id=\":Rj6l19dl:\"]/div/div/div/div[2]/div/div/div/div/input");
	private By calendar = By.xpath("//*[@id=\"basiclayout\"]/div/div/div[2]/div/div/div/div[2]/div/div/div/div/div/div[2]/button");
	
	private By clickRecommendedDeparture = By.xpath("//*[@id=\"flights-searchbox_suggestions\"]");
	private By clickRecommendedArrival = By.xpath("//*[@id=\"flights-searchbox_suggestions\"]/li[1]");
	
	private By search = By.xpath("//*[@id=\"basiclayout\"]/div/div/div[2]/div/div/div/div[2]/div/div/button/span");
	private By searchResultLoader = By.xpath("//*[@id=\"basiclayout\"]/div/div/div[3]/div/div[1]/div");
	
	private By result = By.xpath("//*[@id=\"BEST\"]/div[1]/ul");
	
	public void selectFlight() {
		wait.waitForVisiblity(flight);
		wait.waitForInteractableAndClick(flight);
	}
	
	public void chooseFlightType(String flightType) {

        switch (flightType.toLowerCase()) {

            case "oneway":
            	wait.waitForVisiblity(oneWay);
                chooseFlightType(oneWay);
                break;

            case "roundtrip":
                chooseFlightType(roundTrip);
                break;

            case "multicity":
                chooseFlightType(multiCity);
                break;

            default:
                throw new IllegalArgumentException(
                    "Invalid flight type: " + flightType
                );
        }
    }
	
	public void clickDeparture() {
		click(departure);
	}
	
	public void clickArrival() {
		wait.waitForVisiblity(arrival);
		click(arrival);
	}
	
	public void enterDestination(String destination) {
		click(enterDepart);
		wait.clickBackSpace();
		type(enterDepart, destination);
		wait.waitForVisiblity(clickRecommendedDeparture);
		click(clickRecommendedDeparture);
	}
	
	public void enterArrival(String arrival) {
		click(enterArrival);
		type(enterArrival, arrival);
		wait.waitForVisiblity(clickRecommendedArrival);
		click(clickRecommendedArrival);
	}
	
	public void openCalendar() {
		click(calendar);
	}
	
	public void selectStartDate(LocalDate date) {
        click(getDateLocator(date));
    }

    public void selectRoundTripDates(LocalDate start, LocalDate end) {

        if (end.isBefore(start)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        click(getDateLocator(start));
        click(getDateLocator(end));
    }

    private By getDateLocator(LocalDate date) {
        return By.xpath("//span[@data-date='" + date.toString() + "']");
    }
    
    public void timeOut(int seconds) throws Exception {
    	customTimeout(seconds);
    }
    
    
    public void clickSearch() {
    	click(search);
    }
    
    public void waitForResultLoaderDisapper() {
    	if(isDisplayed(searchResultLoader)) {
    		wait.waitForElementInvisibility(searchResultLoader);
    	}
    }
    
    public boolean results() {
    	return wait.waitForPresence(result).isDisplayed();
    }
    
}
