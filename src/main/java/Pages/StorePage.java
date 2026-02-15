package Pages;

import base.BasePage;
import exceptions.Errors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testdata.Traveller;

import java.util.List;

public class StorePage extends BasePage {

    public StorePage(WebDriver driver) {
        super(driver);
    }

    private By loginMenu = By.xpath("//*[@id=\"main_menu_top\"]/li[2]/a/span");

    private By loginName = By.xpath("//*[@id=\"loginFrm_loginname\"]");
    private By passwordField = By.xpath("//*[@id=\"loginFrm_password\"]");
    private By loginBtn = By.xpath("//*[@id=\"loginFrm\"]/fieldset/button");

    private By apparel = By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[2]/a");
    private By shoes = By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[2]/div/ul[1]/li[1]/a");

    private By backToShoes = By.cssSelector("body > div > div:nth-child(2) > div:nth-child(2) > section > ul > li:nth-child(3) > a");

    private By shoeCard = By.cssSelector("#maincontainer > div > div > div > div > div.thumbnails.grid.row.list-inline > div");
    private By view = By.xpath("//*[@id=\"maincontainer\"]/div/div/div/div/div[2]/div[1]/div[2]/div[1]/a[1]");
    private By viewInfo = By.xpath(".//a[contains(@class,'prdocutname')]");

    private By addToCart = By.cssSelector(".productpagecart");

    private By gotoCart = By.xpath("//*[@id=\"main_menu_top\"]/li[3]/a");

    private By currencySelector = By.xpath("/html/body/div/header/div[2]/div/div[2]/ul/li");
    private By currencyTypes = By.cssSelector("ul.dropdown-menu.currency li a");

    private By amount = By.cssSelector(".pricetag.jumbotron");

    private By men = By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[6]/a");
    private By skincare = By.xpath("//*[@id=\"categorymenu\"]/nav/ul/li[6]/div/ul[1]/li[4]/a");

    private By checkoutBtn = By.xpath("//*[@id=\"cart_checkout1\"]");
    private By confirmOrder = By.xpath("//*[@id=\"checkout_btn\"]");

    private By totalAmount = By.xpath("//*[@id=\"maincontainer\"]/div/div[1]/div/div[2]/div/div[1]/table/tbody/tr[3]/td[2]/span");
    private By amountInOrderSummary = By.xpath("//*[@id=\"maincontainer\"]/div/div[2]/div[1]/table[2]/tbody/tr[3]/td[2]/span");

    private By orderNumberMessage = By.xpath("//*[@id=\"maincontainer\"]/div/div/div/div/section/p[2]");

    private By invoiceLink = By.xpath("//*[@id=\"maincontainer\"]/div/div/div/div/section/p[3]/a");
    private By amountOnInvoicePage = By.xpath("//*[@id=\"maincontainer\"]/div/div[1]/div/div/div[2]/div[2]/table/tbody/tr[3]/td[2]");


    public void login(Traveller traveller) {
        click(loginMenu);
        wait.waitForVisiblity(loginName);
        type(loginName, traveller.username);
        type(passwordField, traveller.password);
        click(loginBtn);
    }

    public void login() {
        login(new Traveller());
    }

    public void chooseSkinCareMen() {
        hover(men);
        click(skincare);
    }

    public void selectProductByName(String productName) {
        wait.waitForVisiblity(shoeCard);
        List<WebElement> products = getElements(shoeCard);
        for (WebElement product : products) {
            WebElement nameElement = product.findElement(viewInfo);
            if (nameElement.getText().equalsIgnoreCase(productName)) {
                nameElement.click();
                click(addToCart);
                return;
            }
        }
        throw new Errors(Errors.ErrorType.AUTOMATION, "Product not found: " + productName);
    }

    public void selectCurrency(String currency) {
        hover(currencySelector);
        List<WebElement> currencies = getElements(currencyTypes);
        for (WebElement curr : currencies) {
            if (curr.getText().contains(currency)) {
                curr.click();
                return;
            }
        }
        throw new Errors(Errors.ErrorType.AUTOMATION, "Currency not found: " + currency);
    }

    public String getDisplayedCurrency() {
        wait.waitForVisiblity(amount);
        String price = getElements(amount).get(0).getText();
        String currency = price.replaceAll("[0-9.,\\s]", "").trim();
        System.out.println("Displayed currency symbol: " + currency);
        return currency;
    }

    public void exploreShoes() {
        hover(apparel);
        click(shoes);
    }

    public void addAllVisibleShoes() {

        wait.waitForVisiblity(shoeCard);

        List<WebElement> shoeList = getElements(shoeCard);
        int totalShoes = shoeList.size();

        System.out.println("Total number of shoes visible : " + totalShoes);

        for (int i = 0; i < totalShoes; i++) {

            System.out.println("Clicking on shoe index : " + i);

            try {
                WebElement card = shoeList.get(i);

                WebElement viewBtn = card.findElement(viewInfo);
                viewBtn.click();

                wait.waitForVisiblity(addToCart);
                click(addToCart);

                exploreShoes();

                wait.waitForVisiblity(shoeCard);
                shoeList = getElements(shoeCard);

            } catch (Exception e) {
                throw new Errors(
                        Errors.ErrorType.AUTOMATION,
                        "Failed while processing shoe at index : " + i,
                        e
                );
            }
        }
    }

    public void clickCart() {
        click(gotoCart);
    }

    public void checkOut(){
        click(checkoutBtn);
    }

    public void confirmOrder() {
        click(confirmOrder);
    }

    public String getTotalAmount() {
        wait.waitForVisiblity(totalAmount);
        String amount = getText(totalAmount);
        return amount;
    }

    public String getAmountInOrderSummary() {
        wait.waitForVisiblity(amountInOrderSummary);
        String amount = getText(amountInOrderSummary);
        return amount;
    }

    public void printOrderMessage() {
    	wait.waitForVisiblity(orderNumberMessage);
    	String message = getText(orderNumberMessage);
    	System.out.println(message);
    }

    public void gotoInvoicePage(){
        click(invoiceLink);
    }

    public String getAmountOnInvoicePage() {
        wait.waitForVisiblity(amountOnInvoicePage);
        String amount = getText(amountOnInvoicePage);
        return amount;
    }

}
