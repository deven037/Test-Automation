package store;

import Base.BaseTest;
import Pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class e2eMenProduct extends BaseTest {

    private StorePage store;

    @BeforeMethod
    public void paraSetup() {
        navigateTo("test_store");
        store = new StorePage(driver);
    }

    @Test
    public void validateMenProductPurchase() {
        store.login();
        store.chooseSkinCareMen();
        store.selectProductByName("EYE MASTER");
        store.clickCart();
        store.checkOut();
        String orderSummaryAmount = store.getAmountInOrderSummary();
        store.confirmOrder();
        verifyTotalAmount();
        store.printOrderMessage();
        store.gotoInvoicePage();
        String invoiceAmount = store.getAmountOnInvoicePage();

        Assert.assertEquals(invoiceAmount, orderSummaryAmount, "Total amount on invoice page does not match the amount in order summary.");
    }

    protected void verifyTotalAmount(){
        StorePage store = new StorePage(driver);
        String totalAmount = store.getTotalAmount();
        String orderSummaryAmount = store.getAmountInOrderSummary();

        Assert.assertEquals(totalAmount, orderSummaryAmount, "Total amount in cart does not match the amount in order summary.");
    }

}
