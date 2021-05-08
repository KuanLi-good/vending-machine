import java.util.HashMap;
import java.util.HashSet;

public class Transaction {

    private HashMap<String, Integer> cart;
    private HashSet<String> cartItem;
    private boolean success = false;
    private String paymentMethod;
    private String amount;
    private String change;
    private String user;
    private String items;
    private String reason;

    public void setCart(HashMap<String, Integer> cart, HashSet<String> cartItem){
         this.cart = cart;
         this.cartItem = cartItem;
         items = "Items sold: ";
         for (String s : cartItem){
             items += cart.get(s) + " of " + s + ", " ;
         }
    }

    public Transaction(String name){
        this.user = name;
    }

    public void successTransaction(App app){
        for (String s : cartItem){
            int amount = app.productStock.get(s);
            amount -= cart.get(s);
            app.productStock.put(s, amount);
            Product p = app.productMap.get(s);
            p.transaction(cart.get(s));
        }
    }

    public void setPaymentMethod(String paymentMethod){
        this.paymentMethod = paymentMethod;
    }
    public String paymentDetails(){
        return "Paid with " + this.paymentMethod + " by " + this.user;
    }
    public void setAmount(String amount){
        this.amount = amount;
    }
    public void setChange(String change){
        this.change = change;
    }
    public String getMonetDetails(){
        return "Amount paid : " + this.amount + ", " + this.change + ", ";
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String failReason(){
        return this.user + ", " + this.reason;
    }

    public String getFullDetails(){
        return this.items + getMonetDetails() + paymentDetails();
    }
}
