package store;

import Base.BaseTest;
import Pages.StorePage;
import org.testng.annotations.Test;

public class validateLoginWithFeatures extends BaseTest {

    private StorePage store;

    @Test
    public void ValidateLogin(){
        navigateTo("test_store");
        store = new StorePage(driver);
        store.login();
        store.exploreShoes();
        store.addAllVisibleShoes();
        store.clickCart();
    }

}
