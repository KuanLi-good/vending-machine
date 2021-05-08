/*
 * This Java source file was generated by the Gradle 'init' task.
 */

import org.fest.swing.fixture.FrameFixture;
import org.fest.swing.fixture.JComboBoxFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.junit.*;
import java.awt.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.Assert.*;


public class AppTest {
    static{
        System.setProperty("java.awt.headless", "false");
    }
    public App app;
    public ReadJSON jsonReader;

    @Before
    public void setUp(){
      this.app = new App();
      app.initializeUser("src/main/users.txt");
      app.JComboBoxConstruction();
    }

    //App tests
    @Test
    public void TestGetUsers(){
      assertNotNull(app.getUsers());
    }

    @Test
    public void TestCustomerValues(){
      app.initializeUser("src/main/users.txt");
      app.initializeUser("Empty.txt");
      ArrayList<User> users = app.getUsers();
      User user1 = users.get(0);
      assertEquals(user1.getName(), "1");
      assertEquals(user1.getPassword(), "1");
      assertEquals(user1.getType(), "customer");
    }


    //JSON Tests
    @Test
    public void TestInvaildFilePath() throws Exception{
       jsonReader = new ReadJSON("src/test/invalid_cards.json");
    }

    @Test
    public void TestSuccessOpen() {
       jsonReader = new ReadJSON("src/test/credit_cards.json");
       assertNotNull(jsonReader.getAccounts());
    }

    //Product tests
    @Test
    public void TestProductInit(){
      Product sprite = new Product("Sprite", 3, 7, "1002", "drink", 0);
      assertEquals(sprite.getName(), "Sprite");
      assertNotNull(sprite.getPrice());
      assertEquals(sprite.getQuantity(), 7);
      sprite.modification(2);
      assertEquals(sprite.getQuantity(), 2);
      assertEquals(sprite.getCode(), "1002");
      sprite.setCode("1003");
      assertEquals(sprite.getCode(), "1003");
    }

    @Test
    public void TestProductAddStock(){
      Product sprite = new Product("Sprite", 3, 7, "1002", "drink", 0);
      assertEquals(sprite.addStock(20),false);
      assertEquals(sprite.addStock(1),true);
    }

    @Test
    public void testLoginSimple(){
        app.loginAccount("1", "1");
        assertEquals(app.loginStatus.getText(),"");
        assertEquals(app.loginPassword.getText(),"");
        assertEquals(app.loginName.getText(),"");
        assertEquals(app.currentCustomerLabel.getText(), "Welcome 1 !");

        app.loginAccount("4", "4");
        assertEquals(app.loginStatus.getText(),"");
        assertEquals(app.loginPassword.getText(),"");
        assertEquals(app.loginName.getText(),"");
        assertEquals(app.cashierLogin.getText(), "Welcome cashier 4 !");

        app.loginAccount("5", "5");
        assertEquals(app.loginStatus.getText(),"");
        assertEquals(app.loginPassword.getText(),"");
        assertEquals(app.loginName.getText(),"");
        assertEquals(app.sellerLogin.getText(), "Welcome seller 5 !");

        app.loginAccount("6", "6");
        assertEquals(app.loginStatus.getText(),"");
        assertEquals(app.loginPassword.getText(),"");
        assertEquals(app.loginName.getText(),"");
        assertEquals(app.ownerLogin.getText(), "Welcome owner 6 !");

        app.loginAccount("7", "7");
        assertEquals(app.loginStatus.getText(),"User not found. Please try again.");

        assertTrue(app.loginCheck("1", "1"));
        assertFalse(app.loginCheck("1", "2"));

        assertEquals(app.loginStatus.getText(),"Invalid password. Please try again.");
        assertFalse(app.loginCheck("1q", "1"));
    }

    @Test
    public void testCustomerBack(){
        app.customerBack();
        assertEquals(app.loginStatus.getText(), "");
        assertEquals(app.createStatus.getText(), "");
        assertEquals(app.loginName.getText(), "");
        assertEquals(app.loginPassword.getText(), "");
        assertEquals(app.createName.getText(), "");
        assertEquals(app.createPassword.getText(), "");
    }

    @Test
    public void testAnonymous(){
        app.logout();
        assertEquals("Anonymous", app.currentUser.getName());
        assertEquals(app.currentCustomerLabel.getText(),"Welcome Anonymous!");
    }

    @Test
    public void testCashierModify(){
        app.updateCredit();
        app.updateTransaction();
        int which = 0;
        String much = "a";
        app.cashierModify(which, much);
        assertEquals(app.cashierHelp.getText(), "Please select or enter a correct amount!");
        which = 1;
        app.cashierModify(which, much);
        assertEquals(app.cashierHelp.getText(), "Please select or enter a correct amount!");
        much = "-1";
        app.cashierModify(which, much);
        assertEquals(app.cashierHelp.getText(), "Please select or enter a correct amount!");
        much = "1";
        app.cashierModify(which, much);
        assertEquals(app.cashierCurrent.getText(), "Current amount: 1");
        assertEquals(app.cashierHelp.getText(), "");
        assertTrue(app.currentChange.get(app.changeType[which - 1]) ==  1);
    }

    @Test
    public void testSellerModify(){
        int what = 0;
        int which = 0;
        app.sellerTarget.setText("Juice");
        app.sellerNameBox.setSelectedIndex(1);
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Please select a correct product or option!");
        what = 1;
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Please select a correct product or option!");
        which = 1;
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Conflict name!");


        what = 3;
        app.sellerTarget.setText("chips");
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Wrong type!");

        app.sellerTarget.setText("chocolate");
        app.sellerModify(which, what);

        app.sellerTarget.setText("candies");
        app.sellerModify(which, what);

        app.sellerTarget.setText("chip");
        app.sellerModify(which, what);

        app.sellerTarget.setText("drink");
        app.sellerModify(which, what);

        what = 4;
        app.sellerTarget.setText("16");
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Amount too large!");

        what = 2;
        app.sellerTarget.setText("1013");
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Conflict code!");

        what = 5;
        app.sellerTarget.setText("-1");
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Invalid price!");
        app.sellerTarget.setText("0");
        app.sellerModify(which, what);
        assertEquals(app.sellerHelp.getText(), "Invalid price!");

    }

    @Test
    public void testChangeGiven(){
        app.cashierCurrent(1);
        int[] customer = new int[11];
        HashMap<Double, Integer> testCheck = new HashMap<>();
        int[] ret = new int[11];
        customer[0] = 1;
        double target = 30.0;
        double[] money = app.changeType;
        for (Double d : money){
            testCheck.put(d, 5);
        }
        ret = app.change(customer,testCheck, target, money);
        assertTrue(ret[10] == 0);
        assertTrue(ret[1] == 1);
        assertTrue(ret[0] == 0);
        assertTrue(ret[2] == 1);
        assertTrue(ret[3] == 0);
        assertTrue(ret[4] == 0);
        assertTrue(ret[5] == 0);
        assertTrue(ret[6] == 0);
        assertTrue(ret[7] == 0);
        assertTrue(ret[8] == 0);
        assertTrue(ret[9] == 0);

        target = 2000.0;
        ret = app.change(customer, app.currentChange, target, money);
        assertTrue(ret[10] == 1);

    }

    @Test
    public void TestProductCompleteTransaction(){
        app.completeTransaction();
        assertEquals(app.creditName.getText(),"");
        assertEquals(app.creditPassword.getText(), "");
        assertEquals(app.creditCardHelp.getText(), "");
        assertEquals(app.successLabel.getText(), "");
        assertEquals(app.quantityText.getText(), "");
        assertEquals(app.productText.getText(),"");
        app.clearCart();
        assertEquals(app.customerHelpLabel.getText(), "");

        assertTrue(app.currentAmount == 0.0);
        assertTrue(app.transactionAmount == 0.0);
        assertEquals(app.addMoneyQuantity.getText(),"");
        assertEquals(app.addMoneyHelp.getText(),"");
        assertEquals(app.payMoneyHelp.getText(),"");
        assertEquals(app.successChangeMoney.getText(),"");
        assertTrue(app.cashBox.getSelectedIndex() == 0);
    }

    @Test
    public void createCheck(){
        assertFalse(app.createCheck("1", "1", true));
        assertFalse(app.createCheck("", "1", true));
        assertFalse(app.createCheck("1", "", false));
        assertFalse(app.createCheck("1", "1", false));
    }

    @Test
    public void Clearcheck(){
        app.cartName.add("Sprite");
        app.cartQuantity.put("Sprite", 1);
        app.setCartLabel();
        assertTrue(app.cartLabel.get(0).getText().equals("1 for Sprite"));
        app.cartQuantity.put("Sprite", 0);
        app.setCartLabel();
        assertTrue(app.cartLabel.get(0).getText().equals(""));
        app.clearCashierSeller();
    }

    @Test
    public void testCreateAndDelete(){
        app.createAccount("11111", "1", "seller", true);
        app.deleteAccount("11111", "seller");
        app.deleteAccount("11111", "seller");
        app.createAccount("11111", "1", "cashier", true);
        app.deleteAccount("11111", "cashier");
        app.createAccount("11111", "1", "owner", true);
        app.deleteAccount("11111", "owner");
        app.createAccount("11111", "1", "customerrrr", true);
        app.deleteAccount("11111", "customer");
    }

    @Test
    public void preProcess(){
        assertFalse(app.updateCart());
        app.cartName.add("Sprite");
        app.cartQuantity.put("Sprite", 0);
        assertFalse(app.updateCart());
        app.cartQuantity.put("Sprite", 1);
        assertTrue(app.updateCart());
        app.preProcessTransaction();
        assertTrue(app.checkoutAmount.getText().equals("Total amount for your purchase: $3.0"));
    }

    @Test
    public void TestSellerCurrent(){
        app.sellerCurrent(0, 0);
        assertTrue(app.sellerCurrent.getText().equals("Current value: "));
        app.sellerCurrent(0, 1);
        assertTrue(app.sellerCurrent.getText().equals("Current value: "));
        app.sellerCurrent(1, 0);
        assertTrue(app.sellerCurrent.getText().equals("Current value: "));
        app.sellerNameBox.setSelectedIndex(1);
        app.sellerCurrent(1, 1);
        assertNotNull(app.sellerCurrent.getText());
        app.sellerCurrent(1, 2);
        assertNotNull(app.sellerCurrent.getText());
        app.sellerCurrent(1, 3);
        assertNotNull(app.sellerCurrent.getText());
        app.sellerCurrent(1, 4);
        assertNotNull(app.sellerCurrent.getText());
        app.sellerCurrent(1, 5);
        assertNotNull(app.sellerCurrent.getText());


    }

}
