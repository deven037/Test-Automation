package Pages;

import base.BasePage;
import exceptions.Errors;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
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

    public void login(Traveller traveller){
        click(loginMenu);
        wait.waitForVisiblity(loginName);
        type(loginName, traveller.username);
        type(passwordField, traveller.password);
        click(loginBtn);
    }

    public void login(){
        login(new Traveller());
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

}
