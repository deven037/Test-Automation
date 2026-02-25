package parabank;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import Base.BaseTest;
import Pages.ParaBankPage;

public class ValidateAccountOpeningWithUserCreation extends BaseTest {

    private ParaBankPage para;

    @BeforeMethod
    public void paraSetup() {
        navigateTo("para_bank");
        para = new ParaBankPage(driver);
    }

    @Test
    public void validateAccOpeningWithUserCreation() {
        para.registerUser();
        para.enterDetails();
        para.clickRegister();

        para.openCreateAccountPage();
        para.openSavingsAccount("SAVINGS");
        para.selectAccountNumberFromDropDown(1);
        para.clickOpenAccountBtn();

        String expectedAccNumber = para.getAccountNumber();
        String accountNumber = para.getAccNumberFromAccountDetails();

        Assert.assertEquals(accountNumber, expectedAccNumber);
    }
}
