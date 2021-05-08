
import java.util.*;

public class Customer implements User {

    private String name;
    private String password;
    private String type;
    private boolean anonymous;
    private boolean login;

    public ArrayList<Product> shopping_cart = new ArrayList<Product>();

    public Customer(String user_name, String user_passwd, String user_type){
        this.name = user_name;
        this.password = user_passwd;
        this.type = user_type;
        this.anonymous = false;
        this.login = false;
    }// for loading user instances from file system

    public void logChange(){
        this.login = !login;
    }
    public void logout(){
        this.login = false;
    }
    public void login(){
        this.login = true;
    }
    public boolean checkLog(){
        return this.login;
    }

    public boolean checkStatus(){
        return this.anonymous;
    }

    public void reverseStatus(){
        this.anonymous = !anonymous;
    }
    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getType() {
        return this.type;
    }

    public void clearCart(){
        this.shopping_cart = new ArrayList<>();
    }

    public ArrayList<Product> getShopping_cart(){
        return this.shopping_cart;
    }
}
