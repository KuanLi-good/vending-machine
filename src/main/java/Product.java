public class Product {
    private String name;
    private final int maxCap = 15;
    private int quantity;
    private double price;
    private String type;
    private String code;
    private int sold;

    public Product(String name, double price, int quantity, String code, String type, int sold){
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.type = type;
        this.code = code;
        this.sold = sold;
    }

    public double calculatePrice(int quantity){
        return (double) (this.getPrice() * quantity);
    }

    public boolean addStock(int quantity){
        if (this.quantity + quantity > this.maxCap){
            return false;
        }
        else{
            this.quantity += quantity;
            return true;
        }
    }

    public static boolean checkAvailability(Product product, int quantity){
        return (product.getQuantity() >= quantity);
    }

    public double getPrice(){
        return this.price;
    }

    public String getName(){
        return this.name;
    }

    public int getQuantity(){
        return this.quantity;
    }

    public void transaction(int amount){
        quantity -= amount;
        this.sold += amount;
    }

    public void modification(int amount){
        quantity = amount;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCode() {
        return code;
    }

    public String getType() {
        return type;
    }

    public String getSold() {
        return this.code +"; " + this.name + "; " + this.sold;
    }

    public String getDetails(){
        return "Name: "+this.name +", Code:" + this.code +", Type:" + this.type + ", Quantity: " + this.quantity +", Price: $" + this.price;
    }

    public int soldAmount(){
        return this.sold;
    }

}
