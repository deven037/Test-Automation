package store;

import Base.BaseTest;
import Pages.StorePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class validateCurrencySelection extends BaseTest {

    @Test
    public void validateCurrencySelection() {
        navigateTo("test_store");
        StorePage store = new StorePage(driver);
        store.login();
        store.exploreShoes();
        store.selectCurrency("EURO");
        Assert.assertEquals(store.getDisplayedCurrency(), "€");
        store.selectCurrency("US");
        Assert.assertEquals(store.getDisplayedCurrency(), "$");
        store.selectCurrency("POUND");
        Assert.assertEquals(store.getDisplayedCurrency(), "£");
    }
}
