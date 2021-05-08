
import java.util.*;

public class Seller implements User {

    private String name;
    private String password;
    private String type;

    public ArrayList<Product> shopping_cart = new ArrayList<Product>();
    public ArrayList<String> hist_purchases = new ArrayList<String>();
    public HashMap<String, Integer> card_history = new HashMap<String,Integer>();


    public Seller(String user_name, String user_passwd, String user_type){
        this.name = user_name;
        this.password = user_passwd;
        this.type = user_type;
    }// for loading user instances from file system

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
}
