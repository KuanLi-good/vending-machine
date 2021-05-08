import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.text.DecimalFormat;
import org.json.simple.*;
import javax.swing.Timer;

public class App extends JFrame {
    public Timer time;
    public HashMap<String, Product> productMap = new HashMap<>();
    public HashMap<String, Integer> productStock = new HashMap<>();
    public SimpleDateFormat sdf = new SimpleDateFormat();

    public Container initialContainer;
    public JPanel checkoutContainer;
    public JPanel successContainer;
    public JPanel cashierContainer;
    public JPanel sellerContainer;
    public JPanel ownerContainer;

    public HashMap<String, String> creditCards = new HashMap<>();
    public JTextField creditName;
    public JPasswordField creditPassword;
    public JButton creditPay;
    public JLabel creditNameLabel;
    public JLabel creditCardHelp;
    public HashMap<String, String> creditHistory = new HashMap<>();

    public JButton successReturn;
    public JLabel successLabel;

    public ArrayList<Product> allList = new ArrayList<Product>();
    private ArrayList<Product> drinkList = new ArrayList<Product>();
    private ArrayList<Product> candiesList = new ArrayList<Product>();
    private ArrayList<Product> chipsList = new ArrayList<Product>();
    private ArrayList<Product> chocolateList = new ArrayList<Product>();

    public JTextField chipQuantity;
    public JTextField drinkQuantity;
    public JTextField candiesQuantity;
    public JTextField chocolateQuantity;

    public JButton chipSelect;
    public JButton drinkSelect;
    public JButton candiesSelect;
    public JButton chocolateSelect;

    public ArrayList<JLabel> cartLabel = new ArrayList<>();

    public JComboBox<String> chipBox = new JComboBox<>();
    public JComboBox<String> drinkBox = new JComboBox<>();
    public JComboBox<String> chocolateBox = new JComboBox<>();
    public JComboBox<String> candiesBox = new JComboBox<>();
    public JComboBox<String> cashBox = new JComboBox<>();
    public JComboBox<String> cashierBox = new JComboBox<>();
    public JComboBox<String> sellerNameBox = new JComboBox<>();
    public JComboBox<String> sellerExecuteBox = new JComboBox<>();

    public double[] changeType;
    private ArrayList<User> userList = new ArrayList<User>();
    public ArrayList<Transaction> transactionsList = new ArrayList<Transaction>();
    public ArrayList<Transaction> cancelList = new ArrayList<Transaction>();
    public boolean enoughChange = true;
    public ArrayList<String> transactionItemHistory = new ArrayList<>();
    public ArrayList<String> transactionNameHistory = new ArrayList<>();
    public JLabel history;

    public JLabel loginStatus;
    public JPanel loginContainer;
    public JTextField loginName;
    public JPasswordField loginPassword;

    public JLabel createStatus;
    public JPanel createContainer;
    public JTextField createName;
    public JPasswordField createPassword;

    public JLabel drinkLabel;
    public JLabel chocoLabel;
    public JLabel candiLabel;
    public JLabel chipLabel;

    public JPanel customerContainer;
    public JTextField quantityText;
    public JTextField productText;
    public JLabel currentCustomerLabel;

    public HashMap<String, Integer> cartQuantity = new HashMap<>();
    public HashSet<String> cartName = new HashSet<>();
    public JLabel customerHelpLabel;

    public JLabel checkoutAmount;
    public JLabel checkoutCart;
    public JLabel checkoutPay;

    public User currentUser;
    public Transaction currentTransaction;
    public int[] currentMoney;
    public double currentAmount;
    public double transactionAmount;
    public HashMap<Double, Integer> currentChange = new HashMap<>();
    public JButton anonymousButton;

    public JTextField addMoneyQuantity;
    public JButton addMoney;
    public JButton payMoney;
    public JButton clearMoney;
    public JLabel addMoneyHelp;
    public JButton cancelMoney;
    public JLabel payMoneyHelp;
    public JLabel successChangeMoney;

    public JLabel cashierCurrent;
    public JTextField cashierTarget;
    public JLabel cashierHelp;
    public JLabel cashierLogin;
    public JLabel cashierReportHelp;

    public JLabel sellerLogin;
    public JLabel sellerHelp;
    public JLabel sellerReportHelp;
    public JTextField sellerTarget;
    public JLabel sellerCurrent;

    public JLabel ownerLogin;
    public JLabel ownerHelp;
    public JLabel ownerReportHelp;
    public JTextField ownerTarget;
    public JPasswordField ownerPassword;
    public JTextField ownerType;


    public void initializeUser(String filename){
        try{
            File f = new File(filename);
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()){
                String [] userParameter = scan.nextLine().split(", ");
                if (userParameter[0].equals("customer")){
                    Customer user = new Customer(userParameter[1], userParameter[2], userParameter[0]);
                    if (userParameter.length > 3){

                    }
                    this.getUsers().add(user);
                }
                else if (userParameter[0].equals("seller")){
                    User user  = new Seller(userParameter[1], userParameter[2], userParameter[0]);
                    this.getUsers().add(user);
                }
                else if (userParameter[0].equals("cashier")){
                    User user  = new Cashier(userParameter[1], userParameter[2], userParameter[0]);
                    this.getUsers().add(user);
                }
                else{
                    User user  = new Owner(userParameter[1], userParameter[2], userParameter[0]);
                    this.getUsers().add(user);
                }
            }
        }
        catch (Exception e){
            System.out.println("No such file.");
            e.printStackTrace();
        }
    }

    public String getLoginName(){
        return this.loginName.getText();
    }

    public String getLoginPassword(){
        return this.loginPassword.getText();
    }

    public String getCreateName(){
        return this.createName.getText();
    }

    public String getCreatePassword(){
        return this.createPassword.getText();
    }

    public ArrayList<User> getUsers(){
        return this.userList;
    }

    public App(){
        Font f = new Font("Times New Roman", Font.PLAIN, 18);
        sdf.applyPattern("yyyy-MM-dd HH:mm:ss");
        initializeProduct("src/main/product.txt");
        initializeCredit("src/main/cardHistory.txt");
        initializeTransaction("src/main/transactionHistory.txt");
        this.customerContainer = new JPanel();
        this.loginContainer = new JPanel();
        this.createContainer = new JPanel();
        this.initialContainer = new JPanel();
        this.cashierContainer = new JPanel();
        this.sellerContainer = new JPanel();
        this.ownerContainer = new JPanel();

        this.currentUser = new Customer("Anonymous", "", "customer");
        this.setContentPane(this.customerContainer);
        this.show();

        this.checkoutContainer = new JPanel();
        this.successContainer = new JPanel();

        this.initialContainer.setSize(600, 800);
        this.initialContainer.setLayout(null);

        this.initialContainer.setName("initial");
        this.loginContainer.setName("login");
        this.createContainer.setName("create");
        this.customerContainer.setName("customer");
        this.checkoutContainer.setName("checkout");
        this.successContainer.setName("success");

        this.customerContainer.setLayout(null);
        this.loginContainer.setLayout(null);
        this.createContainer.setLayout(null);
        this.checkoutContainer.setLayout(null);
        this.successContainer.setLayout(null);
        this.cashierContainer.setLayout(null);
        this.sellerContainer.setLayout(null);
        this.ownerContainer.setLayout(null);

        ActionListener timeAction = new timeListener(this, this.customerContainer);
        this.time = new Timer(10000, timeAction);

        this.creditNameLabel = new JLabel("Credit card payment: ");
        this.creditNameLabel.setBounds(50, 250, 250, 40);
        creditNameLabel.setFont(f);

        this.checkoutContainer.add(this.creditNameLabel);
        this.creditNameLabel = new JLabel("Name: ");
        this.creditNameLabel.setBounds(50, 300, 100, 40);
        creditNameLabel.setFont(f);
        this.checkoutContainer.add(this.creditNameLabel);
        this.creditNameLabel = new JLabel("Password: ");
        this.creditNameLabel.setBounds(50, 350, 100, 40);
        creditNameLabel.setFont(f);
        this.checkoutContainer.add(this.creditNameLabel);

        this.creditCardHelp = new JLabel("");
        this.creditCardHelp.setBounds(300, 350, 400, 40);
        this.creditCardHelp.setName("creditcardhelp");
        creditCardHelp.setFont(f);
        creditCardHelp.setForeground(Color.RED);
        this.checkoutContainer.add(this.creditCardHelp);

        this.creditPay = new JButton("Pay with card");
        this.creditPay.setBounds(3000, 300, 200, 40);
        this.creditPay.setName("creditpay");
        this.checkoutContainer.add(this.creditPay);
        ActionListener successListenerAction = new successListener(this, this.successContainer);
        this.creditPay.addActionListener(successListenerAction);

        this.successLabel = new JLabel("");
        this.successLabel.setName("successlabel");
        this.successContainer.add(this.successLabel);
        this.successLabel.setBounds(100, 100, 600, 40);

        this.successChangeMoney = new JLabel("");
        this.successChangeMoney.setName("successChangeMoney");
        this.successContainer.add(this.successChangeMoney);
        this.successChangeMoney.setBounds(100, 200, 600, 40);

        this.creditName = new JTextField();
        this.creditPassword = new JPasswordField();
        this.creditPassword.setEchoChar('*');
        this.creditName.setName("creditname");
        this.creditPassword.setName("creditpassword");
        this.checkoutContainer.add(this.creditName);
        this.checkoutContainer.add(this.creditPassword);
        this.creditName.setBounds(150, 300, 100, 40);
        this.creditPassword.setBounds(150, 350, 100, 40);


        cartLabelConstruction();

        ReadJSON readJSON =  new ReadJSON("src/main/credit_cards.json");
        this.creditCards = readJSON.getAccounts();

        this.currentMoney = new int[10];
        this.currentAmount = 0;
        this.changeType = new double[]{50,20,10,5,2,1,0.5,0.2,0.1,0.05};
        initializeChange("src/main/change.txt");

        this.customerContainer.add(this.chipBox);
        this.customerContainer.add(this.drinkBox);
        this.customerContainer.add(this.candiesBox);
        this.customerContainer.add(this.chocolateBox);
        this.checkoutContainer.add(this.cashBox);

        this.chipQuantity = new JTextField();
        chipQuantity.setName("chip");
        chipQuantity.setBounds(450, 80, 100, 40);
        this.drinkQuantity = new JTextField();
        drinkQuantity.setBounds(450, 130, 100, 40);
        this.candiesQuantity = new JTextField();
        candiesQuantity.setBounds(450, 180, 100, 40);
        this.chocolateQuantity = new JTextField();
        chocolateQuantity.setBounds(450, 230, 100, 40);

        this.drinkSelect = new JButton("Select");
        drinkSelect.setBounds(580, 130, 100, 40);
        drinkSelect.addActionListener(new productSelectListener(this, this.customerContainer, drinkBox, drinkQuantity) );
        customerContainer.add(drinkQuantity);
        customerContainer.add(drinkSelect);

        this.chipSelect = new JButton("Select");
        chipSelect.setBounds(580, 80, 100, 40);
        chipSelect.setName("selectChip");
        chipSelect.addActionListener(new productSelectListener(this, this.customerContainer, chipBox, chipQuantity) );
        customerContainer.add(chipQuantity);
        customerContainer.add(chipSelect);

        this.candiesSelect = new JButton("Select");
        candiesSelect.setBounds(580, 180, 100, 40);
        candiesSelect.addActionListener(new productSelectListener(this, this.customerContainer, candiesBox, candiesQuantity) );
        customerContainer.add(candiesQuantity);
        customerContainer.add(candiesSelect);

        this.chocolateSelect = new JButton("Select");
        chocolateSelect.setBounds(580, 230, 100, 40);
        chocolateSelect.addActionListener(new productSelectListener(this, this.customerContainer, chocolateBox, chocolateQuantity) );
        customerContainer.add(chocolateQuantity);
        customerContainer.add(chocolateSelect);

        JLabel productLabel1 = new JLabel("Chips: ");
        productLabel1.setBounds(20, 80, 300, 40);
        productLabel1.setFont(f);
        this.customerContainer.add(productLabel1);

        this.history = new JLabel("History: ");
        history.setBounds(20, 650, 500, 40);
        history.setFont(f);
        this.customerContainer.add(history);

        this.currentCustomerLabel = new JLabel("Welcome Anonymous!");
        currentCustomerLabel.setBounds(20, 600, 300, 40);
        currentCustomerLabel.setFont(f);
        this.customerContainer.add(currentCustomerLabel);

        JLabel productLabel2 = new JLabel("Drinks: ");
        productLabel2.setBounds(20, 130, 300, 40);
        productLabel2.setFont(f);
        this.customerContainer.add(productLabel2);

        JLabel productLabel3 = new JLabel("Candies: ");
        productLabel3.setBounds(20, 180, 300, 40);
        productLabel3.setFont(f);
        this.customerContainer.add(productLabel3);

        JLabel productLabel4 = new JLabel("Chocolates: ");
        productLabel4.setBounds(20, 230, 300, 40);
        productLabel4.setFont(f);
        this.customerContainer.add(productLabel4);

        JLabel loginHelpLabel = new JLabel("Please login with your account");
        loginHelpLabel.setBounds(20, 240, 300, 25);
        loginHelpLabel.setFont(f);
        this.loginContainer.add(loginHelpLabel);
        loginHelpLabel.setForeground(Color.RED);

        JLabel createHelpLabel = new JLabel("Please create an account: ");
        createHelpLabel.setBounds(20, 240, 300, 25);
        createHelpLabel.setFont(f);
        this.createContainer.add(createHelpLabel);
        createHelpLabel.setForeground(Color.RED);

        JLabel loginNameLabel = new JLabel("Name: ");
        loginNameLabel.setBounds(20, 280, 80, 25);
        loginNameLabel.setFont(f);
        this.loginContainer.add(loginNameLabel);

        JLabel loginPasswordLable = new JLabel("Password: ");
        loginPasswordLable.setBounds(20, 320, 80, 25);
        loginPasswordLable.setFont(f);
        this.loginContainer.add(loginPasswordLable);

        JLabel createNameLabel = new JLabel("Name: ");
        createNameLabel.setBounds(20, 280, 80, 25);
        createNameLabel.setFont(f);
        this.createContainer.add(createNameLabel);

        JLabel createPasswordLable = new JLabel("Password: ");
        createPasswordLable.setBounds(20, 320, 80, 25);
        createPasswordLable.setFont(f);
        this.createContainer.add(createPasswordLable);


        this.loginName = new JTextField(25);
        this.loginName.setBounds(120,280,320,25);
        this.loginName.setName("Name: ");
        this.loginContainer.add(this.loginName);

        this.loginPassword = new JPasswordField();
        this.loginPassword.setEchoChar('*');
        this.loginPassword.setBounds(120,320,320,25);
        this.loginPassword.setName("Password: ");
        this.loginContainer.add(this.loginPassword);

        this.createName = new JTextField(25);
        this.createName.setBounds(120,280,320,25);
        this.createName.setName("Name: ");
        this.createContainer.add(this.createName);

        this.createPassword = new JPasswordField();
        this.createPassword.setEchoChar('*');
        this.createPassword.setBounds(120,320,320,25);
        this.createPassword.setName("Password: ");
        this.createContainer.add(this.createPassword);

        this.successReturn = new JButton("Return");
        this.successReturn.setName("Back");
        this.successReturn.setBounds(100, 400, 100, 40);
        ActionListener backAction3 = new returnListener(this, this.customerContainer);
        this.successReturn.addActionListener(backAction3);
        this.successContainer.add(successReturn);

        this.anonymousButton = new JButton("3: Anonymous");
        this.anonymousButton.setName("anony");
        this.anonymousButton.setFont(f);
        this.anonymousButton.setBounds(480, 700, 200, 40);
        ActionListener anonymousAction = new anonymousListener(this, this.customerContainer);
        this.anonymousButton.addActionListener(anonymousAction);

        JLabel cashierSelect = new JLabel("Select type: ");
        cashierSelect.setBounds(30, 100, 200, 40);
        cashierSelect.setFont(f);
        this.cashierContainer.add(cashierSelect);

        this.cashierLogin = new JLabel("Welcome");
        cashierLogin.setBounds(30, 50, 200, 40);
        cashierLogin.setFont(f);
        this.cashierContainer.add(cashierLogin);

        JLabel cashiertarget = new JLabel("Target amount: ");
        cashiertarget.setBounds(30, 250, 200, 40);
        cashiertarget.setFont(f);
        this.cashierContainer.add(cashiertarget);

        this.cashierCurrent = new JLabel("Current amount: ");
        cashierCurrent.setBounds(30, 175, 200, 40);
        cashierCurrent.setFont(f);
        this.cashierContainer.add(cashierCurrent);

        this.cashierHelp = new JLabel("");
        cashierHelp.setBounds(220, 320, 400, 40);
        cashierHelp.setFont(f);
        this.cashierContainer.add(cashierHelp);
        cashierHelp.setForeground(Color.RED);

        this.cashierReportHelp = new JLabel("");
        cashierReportHelp.setBounds(150, 550, 400, 40);
        cashierReportHelp.setFont(f);
        this.cashierContainer.add(cashierReportHelp);

        this.cashierTarget = new JTextField("");
        cashierTarget.setBounds(150, 250, 50, 40);
        cashierTarget.setFont(f);
        this.cashierContainer.add(cashierTarget);

        JButton cashierModify = new JButton("Modify");
        cashierModify.setName("cashierModify");
        cashierModify.setBounds(220, 250, 100, 40);
        cashierModify.setFont(f);
        ActionListener cashierAction = new cashierModifyListener(this, this.cashierContainer);
        cashierModify.addActionListener(cashierAction);
        this.cashierContainer.add(cashierModify);

        JButton cashierBack = new JButton("Back");
        cashierBack.setName("cashierBack");
        cashierBack.setBounds(330, 250, 100, 40);
        cashierBack.setFont(f);
        ActionListener cashierBackAction = new cashierBackListener(this, this.customerContainer);
        cashierBack.addActionListener(cashierBackAction);
        this.cashierContainer.add(cashierBack);

        JButton cashierChangeReport = new JButton("Generate change report");
        cashierChangeReport.setName("cashierChangeReport");
        cashierChangeReport.setBounds(120, 400, 400, 40);
        cashierChangeReport.setFont(f);
        ActionListener cashierChangeReportAction = new cashierChangeReportListener(this, this.cashierContainer);
        cashierChangeReport.addActionListener(cashierChangeReportAction);
        this.cashierContainer.add(cashierChangeReport);

        JButton cashierTransactionReport = new JButton("Generate transaction report");
        cashierTransactionReport.setName("cashierTransactionReport");
        cashierTransactionReport.setBounds(120, 475, 400, 40);
        cashierTransactionReport.setFont(f);
        ActionListener cashierTransactionReportAction = new cashierTransactionReportListener(this, this.cashierContainer);
        cashierTransactionReport.addActionListener(cashierTransactionReportAction);
        this.cashierContainer.add(cashierTransactionReport);

        JLabel sellerSelect = new JLabel("Select product: ");
        sellerSelect.setBounds(30, 100, 200, 40);
        sellerSelect.setFont(f);
        this.sellerContainer.add(sellerSelect);

        JLabel sellerExecuteSelect = new JLabel("Select option: ");
        sellerExecuteSelect.setBounds(30, 150, 200, 40);
        sellerExecuteSelect.setFont(f);
        this.sellerContainer.add(sellerExecuteSelect);

        this.sellerLogin = new JLabel("Welcome");
        sellerLogin.setBounds(30, 50, 200, 40);
        sellerLogin.setFont(f);
        this.sellerContainer.add(sellerLogin);

        this.ownerLogin = new JLabel("Welcome");
        ownerLogin.setBounds(30, 50, 200, 40);
        ownerLogin.setFont(f);
        this.ownerContainer.add(ownerLogin);

        JLabel sellertarget = new JLabel("Target value: ");
        sellertarget.setBounds(30, 250, 200, 40);
        sellertarget.setFont(f);
        this.sellerContainer.add(sellertarget);

        this.sellerCurrent = new JLabel("Current value: ");
        sellerCurrent.setBounds(30, 200, 250, 40);
        sellerCurrent.setFont(f);
        this.sellerContainer.add(sellerCurrent);

        this.sellerHelp = new JLabel("");
        sellerHelp.setBounds(150, 360, 400, 40);
        sellerHelp.setFont(f);
        this.sellerContainer.add(sellerHelp);
        sellerHelp.setForeground(Color.RED);

        this.sellerReportHelp = new JLabel("");
        sellerReportHelp.setBounds(150, 625, 400, 40);
        sellerReportHelp.setFont(f);
        this.sellerContainer.add(sellerReportHelp);

        this.sellerTarget = new JTextField("");
        sellerTarget.setBounds(150, 250, 150, 40);
        sellerTarget.setFont(f);
        this.sellerContainer.add(sellerTarget);

        JButton sellerModify = new JButton("Modify");
        sellerModify.setName("sellerModify");
        sellerModify.setBounds(150, 320, 100, 40);
        sellerModify.setFont(f);
        ActionListener sellerAction = new sellerModifyListener(this, this.sellerContainer);
        sellerModify.addActionListener(sellerAction);
        this.sellerContainer.add(sellerModify);

        JButton sellerBack = new JButton("Back");
        sellerBack.setName("sellerBack");
        sellerBack.setBounds(260, 320, 100, 40);
        sellerBack.setFont(f);
        ActionListener sellerBackAction = new sellerBackListener(this, this.customerContainer);
        sellerBack.addActionListener(sellerBackAction);
        this.sellerContainer.add(sellerBack);

        JButton sellerProductReport = new JButton("Generate available product report");
        sellerProductReport.setName("sellerChangeReport");
        sellerProductReport.setBounds(120, 475, 400, 40);
        sellerProductReport.setFont(f);
        ActionListener sellerProductReportAction = new sellerProductReportListener(this, this.cashierContainer);
        sellerProductReport.addActionListener(sellerProductReportAction);
        this.sellerContainer.add(sellerProductReport);

        JButton sellerSalesReport = new JButton("Generate sales report");
        sellerSalesReport.setName("sellerTransactionReport");
        sellerSalesReport.setBounds(120, 550, 400, 40);
        sellerSalesReport.setFont(f);
        ActionListener sellerSalesReportAction = new sellerSalesReportListener(this, this.cashierContainer);
        sellerSalesReport.addActionListener(sellerSalesReportAction);
        this.sellerContainer.add(sellerSalesReport);

        JButton ownerCashier = new JButton("Cashier");
        ownerCashier.setName("ownerCashier");
        ownerCashier.setBounds(30, 130, 100, 40);
        ownerCashier.setFont(f);
        ActionListener ownerCashierAction = new ownerChangeReportListener(this, this.cashierContainer);
        ownerCashier.addActionListener(ownerCashierAction);
        this.ownerContainer.add(ownerCashier);

        JButton ownerSeller = new JButton("Seller");
        ownerSeller.setName("BackLogin");
        ownerSeller.setBounds(180, 130, 100, 40);
        ownerSeller.setFont(f);
        ActionListener ownerSellerAction = new ownerProductReportListener(this, this.sellerContainer);
        ownerSeller.addActionListener(ownerSellerAction);
        this.ownerContainer.add(ownerSeller);

        JLabel ownerSelect = new JLabel("Enter user type: ");
        ownerSelect.setBounds(30, 300, 200, 40);
        ownerSelect.setFont(f);
        this.ownerContainer.add(ownerSelect);

        this.ownerType = new JTextField("");
        ownerType.setBounds(170, 300, 150, 40);
        ownerType.setFont(f);
        this.ownerContainer.add(ownerType);

        JLabel ownertarget = new JLabel("Target name: ");
        ownertarget.setBounds(30, 250, 200, 40);
        ownertarget.setFont(f);
        this.ownerContainer.add(ownertarget);

        JLabel ownerpass = new JLabel("Enter password when add: ");
        ownerpass.setBounds(330, 220, 200, 40);
        ownerpass.setFont(f);
        this.ownerContainer.add(ownerpass);

        this.ownerPassword = new JPasswordField("");
        ownerPassword.setEchoChar('*');
        ownerPassword.setBounds(330, 250, 250, 40);
        ownerPassword.setFont(f);
        this.ownerContainer.add(ownerPassword);

        this.ownerHelp = new JLabel("");
        ownerHelp.setBounds(30, 350, 400, 40);
        ownerHelp.setFont(f);
        this.ownerContainer.add(ownerHelp);
        ownerHelp.setForeground(Color.RED);

        this.ownerReportHelp = new JLabel("");
        ownerReportHelp.setBounds(150, 625, 400, 40);
        ownerReportHelp.setFont(f);
        this.ownerContainer.add(ownerReportHelp);

        this.ownerTarget = new JTextField("");
        ownerTarget.setBounds(170, 250, 150, 40);
        ownerTarget.setFont(f);
        this.ownerContainer.add(ownerTarget);

        JButton ownerAdd = new JButton("Add");
        ownerAdd.setName("ownerAdd");
        ownerAdd.setBounds(380, 320, 100, 40);
        ownerAdd.setFont(f);
        ActionListener ownerAddAction = new ownerAddListener(this, this.ownerContainer);
        ownerAdd.addActionListener(ownerAddAction);
        this.ownerContainer.add(ownerAdd);

        JButton ownerDelete = new JButton("Delete");
        ownerDelete.setName("ownerDelete");
        ownerDelete.setBounds(380, 370, 100, 40);
        ownerDelete.setFont(f);
        ActionListener ownerDeleteAction = new ownerDeleteListener(this, this.ownerContainer);
        ownerDelete.addActionListener(ownerDeleteAction);
        this.ownerContainer.add(ownerDelete);

        JButton ownerBack = new JButton("Back");
        ownerBack.setName("ownerBack");
        ownerBack.setBounds(330, 130, 100, 40);
        ownerBack.setFont(f);
        ActionListener sellerBackAction2 = new sellerBackListener(this, this.customerContainer);
        ownerBack.addActionListener(sellerBackAction2);
        this.ownerContainer.add(ownerBack);

        JButton ownerProductReport = new JButton("Generate user report");
        ownerProductReport.setName("sellerChangeReport");
        ownerProductReport.setBounds(120, 475, 400, 40);
        ownerProductReport.setFont(f);
        ActionListener ownerUserReportAction = new ownerUserReportListener(this, this.ownerContainer);
        ownerProductReport.addActionListener(ownerUserReportAction);
        this.ownerContainer.add(ownerProductReport);

        JButton ownerCancelReport = new JButton("Generate cancel report");
        ownerCancelReport.setName("ownerCancelReport");
        ownerCancelReport.setBounds(120, 550, 400, 40);
        ownerCancelReport.setFont(f);
        ActionListener ownerCancelReportListenerAction = new ownerCancelReportListener(this, this.ownerContainer);
        ownerCancelReport.addActionListener(ownerCancelReportListenerAction);
        this.ownerContainer.add(ownerCancelReport);


        JButton backLogin = new JButton("Back");
        backLogin.setName("BackLogin");
        backLogin.setBounds(100, 400, 100, 40);
        backLogin.setFont(f);
        ActionListener backAction1 = new backListener(this, this.customerContainer);
        backLogin.addActionListener(backAction1);
        this.loginContainer.add(backLogin);


        JButton backCreate = new JButton("Back");
        backCreate.setName("BackCreate");
        backCreate.setBounds(100, 400, 100, 40);
        backCreate.setFont(f);
        ActionListener backAction2 = new backListener(this, this.customerContainer);
        backCreate.addActionListener(backAction2);
        this.createContainer.add(backCreate);

        JLabel productsLabel = new JLabel("Products: ");
        productsLabel.setBounds(30, 280, 80, 25);
        productsLabel.setFont(f);
        this.customerContainer.add(productsLabel);

        this.productText = new JTextField(25);
        this.productText.setBounds(120,280,320,25);
        this.productText.setName("product");

        this.quantityText = new JTextField(25);
        this.quantityText.setBounds(120,320,320,25);
        this.quantityText.setName("quantity");

        this.customerHelpLabel = new JLabel("");
        this.customerHelpLabel.setName("customerhelp");
        this.customerHelpLabel.setBounds(560,300,500,25);
        this.customerHelpLabel.setFont(f);
        customerHelpLabel.setForeground(Color.RED);
        this.customerContainer.add(this.customerHelpLabel);

        JButton cancel = new JButton("Cancel");
        cancel.setName("Cancel1");
        cancel.setBounds(580, 475, 100, 40);
        cancel.setFont(f);
        ActionListener cancelAction = new cancelCustomerListener(this, this.customerContainer);
        cancel.addActionListener(cancelAction);
        this.customerContainer.add(cancel);

        this.cancelMoney = new JButton("Cancel");
        this.cancelMoney.setName("Cancel2");
        this.cancelMoney.setBounds(400, 160, 100, 40);
        cancelMoney.setFont(f);
        ActionListener cancelAction1 = new cancelCustomerListener(this, this.customerContainer);
        this.cancelMoney.addActionListener(cancelAction1);
        this.checkoutContainer.add(cancelMoney);

        JButton clear = new JButton("Clear");
        this.customerContainer.add(clear);
        clear.setName("Cancel");
        clear.setBounds(580, 400, 100, 40);
        clear.setFont(f);
        ActionListener clearAction = new clearListener(this, this.customerContainer);
        clear.addActionListener(clearAction);

        JButton checkout = new JButton("Checkout");
        this.customerContainer.add(checkout);
        checkout.setName("Checkout");
        checkout.setBounds(580, 550, 150, 40);
        checkout.setFont(f);
        ActionListener checkoutAction = new checkoutListener(this, this.checkoutContainer);
        checkout.addActionListener(checkoutAction);

        this.checkoutAmount = new JLabel("");
        this.checkoutAmount.setBounds(50,180,400,25);
        this.checkoutAmount.setFont(f);
        this.checkoutAmount.setName("checkoutamount");
        this.checkoutContainer.add(this.checkoutAmount);

        this.checkoutCart = new JLabel("");
        this.checkoutCart.setName("checkoutcart");
        this.checkoutCart.setBounds(50,100,800,25);
        this.checkoutCart.setFont(f);
        this.checkoutContainer.add(this.checkoutCart);

        this.checkoutPay = new JLabel("");
        this.checkoutPay.setBounds(50,400,400,25);
        this.checkoutPay.setFont(f);
        this.checkoutPay.setName("checkoutpay");
        this.checkoutContainer.add(this.checkoutPay);

        this.addMoney = new JButton("Insert");
        this.checkoutContainer.add(this.addMoney);
        this.addMoney.setBounds(50, 550, 100, 40);
        this.addMoney.setFont(f);
        this.addMoney.setName("addmoney");
        ActionListener addAction = new addMoneyListener(this, this.checkoutContainer);
        this.addMoney.addActionListener(addAction);

        this.payMoney = new JButton("Pay by cash");
        this.payMoney.setName("paybycash");
        this.checkoutContainer.add(this.payMoney);
        this.payMoney.setBounds(400, 550, 200, 40);
        this.payMoney.setFont(f);
        ActionListener payMoneyAction = new payMoneyListener(this, this.successContainer);
        this.payMoney.addActionListener(payMoneyAction);

        this.payMoneyHelp = new JLabel("");
        this.payMoneyHelp.setName("paymoneyhelp");
        this.checkoutContainer.add(this.payMoneyHelp);
        this.payMoneyHelp.setFont(f);
        this.payMoneyHelp.setBounds(350, 350, 150, 40);
        payMoneyHelp.setForeground(Color.RED);

        this.clearMoney = new JButton("Clear");
        this.clearMoney.setName("clearmoney");
        this.checkoutContainer.add(this.clearMoney);
        this.clearMoney.setBounds(200, 550, 100, 40);
        this.clearMoney.setFont(f);
        ActionListener clearMoneyAction = new clearMoneyListener(this, this.checkoutContainer);
        this.clearMoney.addActionListener(clearMoneyAction);



        this.addMoneyQuantity = new JTextField();
        this.addMoneyQuantity.setName("addmoneyquantity");
        this.addMoneyQuantity.setBounds(400, 450, 100, 40);
        this.checkoutContainer.add(this.addMoneyQuantity);

        this.addMoneyHelp = new JLabel("");
        this.addMoneyHelp.setName("addmoneyhelp");
        this.addMoneyHelp.setBounds(50, 500, 500, 40);
        this.addMoneyHelp.setFont(f);
        this.checkoutContainer.add(this.addMoneyHelp);
        addMoneyHelp.setForeground(Color.RED);

        this.loginStatus = new JLabel( "");
        this.loginContainer.add(this.loginStatus);
        this.loginStatus.setFont(f);
        this.loginStatus.setBounds(100, 100, 400, 25);

        this.createStatus = new JLabel("");
        this.createContainer.add(this.createStatus);
        this.createStatus.setFont(f);
        this.createStatus.setName("createStatus");
        this.createStatus.setBounds(100, 100, 400, 25);

        JButton login = new JButton("Login account");
        this.loginContainer.add(login);
        login.setName("Login account");
        login.setBounds(250, 400, 300, 40);
        login.setFont(f);
        ActionListener loginAction = new loginListener(this, this.customerContainer);
        login.addActionListener(loginAction);

        JButton create = new JButton("Create new account");
        this.createContainer.add(create);
        create.setName("Create new account");
        create.setBounds(250, 400, 300, 40);
        create.setFont(f);
        ActionListener createAction = new createListener(this, this.customerContainer);
        create.addActionListener(createAction);

        JButton createAccount = new JButton("1: Create new account");
        this.customerContainer.add(createAccount);
        createAccount.setName("Create new account");
        createAccount.setBounds(30, 700, 200, 40);
        createAccount.setFont(f);
        ActionListener createAccountAction = new Listener(this, this.createContainer);
        createAccount.addActionListener(createAccountAction);

        JButton loginAccount = new JButton("2: Login your account");
        this.customerContainer.add(loginAccount);
        loginAccount.setName("Login account");
        loginAccount.setBounds(250, 700, 200, 40);
        loginAccount.setFont(f);
        ActionListener loginAccountAction = new Listener(this, this.loginContainer);
        loginAccount.addActionListener(loginAccountAction);
        this.JComboBoxConstruction();
        anonymous();
        time.stop();
        updateHistory();
        this.setVisible(true);
        this.pack();
    }

    public static void main(String[] args) {
        System.setProperty("java.awt.headless", "false");
        App app = new App();
        app.initializeUser("src/main/users.txt");
        app.setTitle("Group 2 vending machine");
        app.setSize(800, 800);
        app.setResizable(false);
        app.setLayout(null);
    }

    public boolean updateCart(){
        if (cartName.size() == 0){
            customerHelpLabel.setText("No product selected.");
            return false;
        }

        for (String s : cartName){
            if (cartQuantity.get(s) > 0){
                customerHelpLabel.setText("");
                return true;
            }
        }
        customerHelpLabel.setText("No product selected.");
        return false;
    }

    public void clearCart(){
        this.cartQuantity = new HashMap<>();
        this.cartName = new HashSet<>();
        clearLabels();
    }

    public boolean preProcessTransaction(){
        String summary = "Products you buy: ";
        double amount = 0.0;

        for (Product p : this.allList){
            String name = p.getName();
            if (this.cartQuantity.get(name) != null){
                if (this.cartQuantity.get(name) > 0){
                    summary += this.cartQuantity.get(name) +" "+name+", ";
                    amount += p.calculatePrice(this.cartQuantity.get(name));
                }
            }
        }

        int sl = summary.length();
        summary = summary.substring(0, sl-2);
        this.checkoutCart.setText(summary);
        this.checkoutAmount.setText("Total amount for your purchase: $" + amount);
        this.checkoutPay.setText("Money you have inserted: $0");
        this.customerHelpLabel.setText("");
        this.transactionAmount = amount;
        return true;
    }

    public void JComboBoxConstruction(){
        this.chipBox.removeAllItems();
        this.chocolateBox.removeAllItems();
        this.candiesBox.removeAllItems();
        this.drinkBox.removeAllItems();
        this.cashBox.removeAllItems();
        this.cashierBox.removeAllItems();
        this.sellerNameBox.removeAllItems();
        this.sellerExecuteBox.removeAllItems();

        this.chipBox.addItem("Please select");
        this.chipBox.setName("chipbox");
        for (Product p : this.allList){
            if (p.getType().equals("chip") == false){
                continue;
            }
            String detail = p.getName() + ", $" + p.getPrice() + ", Quantity: " + p.getQuantity();
            this.chipBox.addItem(detail);
        }
        this.chipBox.setBounds(120, 80, 300, 40);
        this.chipBox.setSelectedIndex(0);

        this.drinkBox.addItem("Please select");
        this.drinkBox.setName("drinkbox");
        for (Product p : this.allList){
            if (p.getType().equals("drink") == false){
                continue;
            }
            String detail = p.getName() + ", $" + p.getPrice() + ", Quantity: " + p.getQuantity();
            this.drinkBox.addItem(detail);

        }
        this.drinkBox.setBounds(120, 130, 300, 40);
        this.drinkBox.setSelectedIndex(0);

        this.candiesBox.addItem("Please select");
        this.candiesBox.setName("candiesbox");
        for (Product p : this.allList){
            if (p.getType().equals("candies") == false){
                continue;
            }
            String detail = p.getName() + ", $" + p.getPrice() + ", Quantity: " + p.getQuantity();
            this.candiesBox.addItem(detail);

        }
        this.candiesBox.setBounds(120, 180, 300, 40);
        this.candiesBox.setSelectedIndex(0);

        this.chocolateBox.addItem("Please select");
        this.chocolateBox.setName("chocolatebox");
        for (Product p : this.allList){
            if (p.getType().equals("chocolate") == false){
                continue;
            }
            String detail = p.getName() + ", $" + p.getPrice() + ", Quantity: " + p.getQuantity();
            this.chocolateBox.addItem(detail);

        }
        this.chocolateBox.setBounds(120, 230, 300, 40);
        this.chocolateBox.setSelectedIndex(0);

        this.cashBox.addItem("-Select-");
        this.cashBox.setName("cashbox");
        cashBox.addItem("$50");
        cashBox.addItem("$20");
        cashBox.addItem("$10");
        cashBox.addItem("$5");
        cashBox.addItem("$2");
        cashBox.addItem("$1");
        cashBox.addItem("50c");
        cashBox.addItem("20c");
        cashBox.addItem("10c");
        cashBox.addItem("5c");
        this.cashBox.setBounds(50, 450, 300, 40);

        this.cashierBox.addItem("-Select-");
        this.cashierBox.setName("cashierbox");
        cashierBox.addItem("$50");
        cashierBox.addItem("$20");
        cashierBox.addItem("$10");
        cashierBox.addItem("$5");
        cashierBox.addItem("$2");
        cashierBox.addItem("$1");
        cashierBox.addItem("50c");
        cashierBox.addItem("20c");
        cashierBox.addItem("10c");
        cashierBox.addItem("5c");
        this.cashierContainer.add(cashierBox);
        this.cashierBox.addItemListener(new cashierCurrentListener(this, this.cashierBox));
        this.cashierBox.setBounds(150, 100, 200, 40);

        sellerNameBox.addItem("-Select-");
        this.sellerNameBox.setName("sellerNameBox");
        for (Product p : this.allList){
            sellerNameBox.addItem(p.getName());
        }
        sellerNameBox.setBounds(150, 100, 200, 40);
        this.sellerContainer.add(sellerNameBox);
        this.sellerContainer.add(sellerExecuteBox);
        sellerExecuteBox.setBounds(150, 150, 200, 40);
        sellerExecuteBox.addItem("-Select-");
        sellerExecuteBox.addItem("Name");
        sellerExecuteBox.addItem("Code");
        sellerExecuteBox.addItem("Category");
        sellerExecuteBox.addItem("Quantity");
        sellerExecuteBox.addItem("Price");
        this.sellerExecuteBox.addItemListener(new sellerCurrentListener(this, this.sellerNameBox, this.sellerExecuteBox));
        this.sellerNameBox.addItemListener(new sellerCurrentListener(this, this.sellerNameBox, this.sellerExecuteBox));
    }

    public int[] change(int[] customer, HashMap<Double,Integer> currentChange, double target, double[] money){
        int[] rollback = new int[money.length];
        int[] ret = new int[money.length+1];
        for (int i = 0; i<money.length; i++){
            int now = currentChange.get(money[i]);
            rollback[i] = now;
            now += customer[i];
            currentChange.put(money[i], now);
        }

        for (int i = 0; i<money.length;i++){
            while ((target >= money[i]) && (currentChange.get(money[i]) > 0)){
                target -= money[i];
                ret[i] += 1;
                int which = currentChange.get(money[i]) - 1;
                currentChange.put(money[i], which);
            }
        }

        if (target == 0){
            ret[10] = 0;
            enoughChange = true;
            updateChange();
            return ret;
        }
        else{
            ret[10] = 1;
            for (int i = 0; i<10; i++){
                currentChange.put(money[i], rollback[i]);
            }
            enoughChange = false;
            return ret;
        }
    }

    public void createAccount(String name, String password, String type, boolean owner){
        boolean success = createCheck(name, password, owner);
        time.stop();
        if (success == true){
            clearCart();
            User u = new Customer(name, password, "customer");
            if  (type.equals("customer")){
                time.stop();
                getUsers().add(u);
                currentUser = u;
                currentCustomerLabel.setText("Welcome " + u.getName() + " !");
                createStatus.setText("");
                setContentPane(this.customerContainer);
                time.start();
                createPassword.setText("");
                createName.setText("");
                show();
            }
            else if (type.equals("cashier")){
                u = new Cashier(name, password, "cashier");
                getUsers().add(u);
            }
            else if (type.equals("seller")){
                u = new Seller(name, password, "seller");
                getUsers().add(u);
            }
            else if (type.equals("owner")){
                u = new Owner(name, password, "owner");
                getUsers().add(u);
            }
            else{
                this.ownerHelp.setText("Wrong user type!");
                return;
            }

            try{
                File f = new File("src/main/users.txt");
                PrintWriter write = new PrintWriter(f);
                for (User k : getUsers()){
                    write.println(k.getType()+", "+k.getName() + ", " + k.getPassword());
                }
                if (owner){
                    this.ownerHelp.setText("Successfully created " + u.getType() + " " + u.getName());
                }
                write.close();
            }
            catch (Exception g){
                g.printStackTrace();
            }
        }
    }

    public boolean createCheck(String name, String password, boolean owner){
        String message = "";

        if ((name.length() == 0) || (password.length() == 0)){
            message = "User name and password cannot be empty!";
            if (owner){
                this.ownerHelp.setText(message);
            }
            else{
                createStatus.setText(message);
            }
            return false;
        }

        for (User u : this.userList){
            if (u.getName().equals(name)){
                message = "User already existed. Please try again.";
                if (owner){
                    this.ownerHelp.setText(message);
                }
                else{
                    createStatus.setText(message);
                }
                return false;
            }
        }
        return true;
    }

    public void loginAccount(String name, String password){
        time.stop();
        boolean success = loginCheck(name, password);
        if (success){
            clearCart();
            loginStatus.setText("");
            loginPassword.setText("");
            loginName.setText("");
            show();
        }
    }

    public boolean loginCheck(String name, String password){
        String message = "";
        time.stop();
        for (User u : userList) {
            if (u.getName().equals(name)) {
                if (u.getPassword().equals(password)) {
                    if (u.getType().equals("customer")) {
                        time.stop();
                        currentUser = u;
                        currentCustomerLabel.setText("Welcome " + u.getName() + " !");
                        time.start();
                        setContentPane(this.customerContainer);
                        updateHistory();
                    }
                    else if (u.getType().equals("cashier")) {
                        setContentPane(this.cashierContainer);
                        cashierLogin.setText("Welcome cashier " + u.getName() + " !");
                    }
                    else if (u.getType().equals("seller")) {
                        setContentPane(this.sellerContainer);
                        sellerLogin.setText("Welcome seller " + u.getName() + " !");
                    }
                    else {
                        setContentPane(this.ownerContainer);
                        ownerLogin.setText("Welcome owner " + u.getName() + " !");
                    }
                    return true;
                } else {
                    message = "Invalid password. Please try again.";
                    loginStatus.setText(message);
                    return false;
                }
            }
        }
        message = "User not found. Please try again.";
        loginStatus.setText(message);
        return false;
    }

    public void customerBack(){
        time.stop();
        loginStatus.setText("");
        createStatus.setText("");
        loginName.setText("");
        loginPassword.setText("");
        createName.setText("");
        createPassword.setText("");
        time.start();
        setContentPane(customerContainer);
        show();
    }

    public void logout(){
        time.stop();
        Transaction t = new Transaction(currentUser.getName());
        anonymous();
        time.start();
        enoughChange = true;
    }

    public void completeTransaction(){
        clearLabels();
        creditName.setText("");
        creditPassword.setText("");
        creditCardHelp.setText("");
        successLabel.setText("");
        quantityText.setText("");
        productText.setText("");
        clearCart();
        customerHelpLabel.setText("");
        currentMoney = new int[10];
        currentAmount = 0.0;
        transactionAmount = 0.0;
        addMoneyQuantity.setText("");
        addMoneyHelp.setText("");
        payMoneyHelp.setText("");
        successChangeMoney.setText("");
        cashBox.setSelectedIndex(0);
        enoughChange = true;
        anonymous();
    }

    public void cashierModify(int which, String much){
        try{
            if (which == 0){
                throw new Exception();
            }

            double value = changeType[which-1];
            int amount = Integer.parseInt(much);
            if (amount < 0){
                throw new Exception();
            }

            currentChange.put(value, amount);
            cashierCurrent.setText("Current amount: " + currentChange.get(value));
            cashierHelp.setText("");
            updateChange();
        }
        catch (Exception g){
            cashierHelp.setText("Please select or enter a correct amount!");
        }
    }

    public void sellerModify(int which, int what){

        if ((what != 0) && (which != 0)){
            Product p = productMap.get(sellerNameBox.getSelectedItem());
            String info = sellerTarget.getText();

            if (what == 1){
                for (Product pr : allList){
                    if (pr.getName().equals(info)){
                        sellerHelp.setText("Conflict name!");
                        return;
                    }
                }
                String oldName = p.getName();
                productMap.put(oldName, null);
                p.setName(info);
                productMap.put(info, p);
                productStock.put(oldName, null);
                productStock.put(info, p.getQuantity());
            }

            else if (what == 2){
                for (Product pr : allList){
                    if (pr.getCode().equals(info)){
                        sellerHelp.setText("Conflict code!");
                        return;
                    }
                }
                p.setCode(info);
            }

            else if (what == 3){
                if ((info.equals("chip")) || (info.equals("candies")) || (info.equals("chocolate")) || (info.equals("drink"))){
                    p.setType(info);
                }
                else{
                    sellerHelp.setText("Wrong type!");
                    return;
                }
            }

            else if (what == 4){
                int amount = Integer.parseInt(info);
                if (amount > 15){
                    sellerHelp.setText("Amount too large!");
                    return;
                }
                p.modification(amount);
                productStock.put(p.getName(), amount);
            }
            else{
                double amount = Double.parseDouble(info);
                if (amount <= 0){
                    sellerHelp.setText("Invalid price!");
                    return;
                }
                p.setPrice(amount);
            }

            JComboBoxConstruction();
            sellerNameBox.setSelectedIndex(which);
            sellerExecuteBox.setSelectedIndex(what);
            sellerCurrent.setText("Current value: " + info);
            sellerHelp.setText("");
            updateProduct();
        }
        else{
            sellerHelp.setText("Please select a correct product or option!");
        }
    }

    public void cashierCurrent(int which){
        if (which != 0){
            double value = changeType[which-1];
            int amount = currentChange.get(value);
            cashierCurrent.setText("Current amount: " + amount);
            cashierTarget.setText("5");
        }
    }

    public void sellerCurrent(int which, int what){
        if ((what != 0) && (which != 0)){
            Product p = productMap.get(sellerNameBox.getSelectedItem());
            String info = "";
            if (what == 1){
                info = p.getName();
            }
            else if (what == 2){
                info = p.getCode();
            }
            else if (what == 3){
                info = p.getType();
            }
            else if (what == 4){
                info = p.getQuantity() + "";
            }
            else{
                info = p.getPrice() + "";
            }
            sellerCurrent.setText("Current value: " + info);
        }
    }

    public void clearCashierSeller(){
        cashierCurrent.setText("Current amount: ");
        cashierReportHelp.setText("");
        cashierTarget.setText("");
        cashierBox.setSelectedIndex(0);

        sellerCurrent.setText("Current value: ");
        sellerReportHelp.setText("");
        sellerTarget.setText("");
        sellerNameBox.setSelectedIndex(0);
        sellerExecuteBox.setSelectedIndex(0);

        ownerHelp.setText("");
        ownerPassword.setText("");
        ownerType.setText("");
        ownerTarget.setText("");
        ownerReportHelp.setText("");
        setContentPane(customerContainer);
        show();
    }

    public void deleteAccount(String name, String type){
        int target = -1;
        for (int i = 0;i < userList.size(); i++){
            User u = userList.get(i);
            if ((u.getName().equals(name)) && (u.getType().equals(type))){
                target = i;
                break;
            }
        }

        if (target == -1){
            ownerHelp.setText("User not found.");
        }
        else{
            userList.remove(target);
            generateUserReport();
            ownerHelp.setText("Successfully deleted " + type + " " + name);
        }
    }

    public void generateUserReport(){
        try{
            File f = new File("src/main/users.txt");
            PrintWriter write = new PrintWriter(f);
            for (User k : getUsers()){
                write.println(k.getType()+", "+k.getName() + ", " + k.getPassword());
            }
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }

    public void generateTransactionReport(){
        cashierReportHelp.setText("Successfully generate transactionReport.txt");
    }

    public void initializeProduct(String file){
        File f = new File(file);
        try{
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()){
                String next = scan.nextLine();
                String [] detail = next.split(", ");
                Product p = new Product(detail[0], Double.parseDouble(detail[4]), Integer.parseInt(detail[3]), detail[1], detail[2], Integer.parseInt(detail[5]));
                allList.add(p);
                productStock.put(p.getName(), p.getQuantity());
                productMap.put(p.getName(), p);
            }
        }
        catch(Exception e){
            System.out.println("File not found.");
        }
    }

    public void updateProduct(){
        try{
            File f = new File("src/main/product.txt");
            PrintWriter write = new PrintWriter(f);
            for (Product p : allList){
                write.println(p.getName() + ", " + p.getCode() + ", " + p.getType() +", "+ p.getQuantity() + ", " + p.getPrice() + ", " + p.soldAmount());
            }
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }

    public void initializeChange(String file){
        File f = new File(file);
        try{
            Scanner scan = new Scanner(f);
            int current = 0;
            while (scan.hasNextLine()){
                String next = scan.nextLine();
                String [] detail = next.split(", ");
                currentChange.put(changeType[current], Integer.parseInt(detail[1]));
                current++;
            }
        }
        catch(Exception e){
            System.out.println("File not found.");
        }
    }

    public void updateChange(){
        try{
            File f = new File("src/main/change.txt");
            PrintWriter write = new PrintWriter(f);
            for (Double d : changeType){
                write.println(d + ", " + currentChange.get(d));
            }
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }

    public void initializeCredit(String file){
        File f = new File(file);
        try{
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()){
                String next = scan.nextLine();
                String [] detail = next.split(", ");
                creditHistory.put(detail[0], detail[1]);
            }
        }
        catch(Exception e){
            System.out.println("File not found.");
        }
    }

    public void updateCredit(){
        try{
            File f = new File("src/main/cardHistory.txt");
            PrintWriter write = new PrintWriter(f);
            for (User u : userList){
                if (creditHistory.get(u.getName()) != null){
                    write.println(u.getName() + ", " + creditHistory.get(u.getName()));
                }
            }
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }

    public void initializeTransaction(String path){
        File f = new File(path);
        try{
            Scanner scan = new Scanner(f);
            while (scan.hasNextLine()){
                String next = scan.nextLine();
                String [] detail = next.split(", ");
                transactionItemHistory.add(detail[0]);
                transactionNameHistory.add(detail[1]);
            }
        }
        catch(Exception e){
            System.out.println("File not found.");
        }
    }

    public void updateTransaction(){
        try{
            File f = new File("src/main/transactionHistory.txt");
            PrintWriter write = new PrintWriter(f);
            for (int i = 0; i< transactionItemHistory.size();i++){
                write.println(transactionItemHistory.get(i) + ", " + transactionNameHistory.get(i));
            }
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }

    public void updateHistory(){
        ArrayList<String> h = new ArrayList<>();
        for (int i = 0; i<transactionNameHistory.size(); i++){
            if (transactionNameHistory.get(i).equals(currentUser.getName())){
                h.add(transactionItemHistory.get(i));
            }
        }

        HashSet<String> s = new HashSet<>();
        String agg = "";

        for (int i = h.size() - 1; i >= 0; i--){
            if (s.size() < 5){
                s.add(h.get(i));
            }
        }

        for (String ss : s){
            agg += ss + ", ";
        }

        if (agg.length() > 2){
            agg = agg.substring(0, agg.length() - 2);
        }
        history.setText("History: " + agg);


    }

    public void cartLabelConstruction(){
        for (int i = 0; i < 8; i++){
            JLabel j = new JLabel("");
            cartLabel.add(j);
            j.setBounds(120, 275+i*40, 200, 40);
            customerContainer.add(j);
            j.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        }

        for (int i = 0; i < 8; i++){
            JLabel j = new JLabel("");
            cartLabel.add(j);
            j.setBounds(350, 275+i*40, 200, 40);
            customerContainer.add(j);
            j.setFont(new Font("Times New Roman", Font.PLAIN, 18));
        }
    }

    public void setCartLabel(){
        for (JLabel j : cartLabel){
            j.setText("");
        }
        int current = 0;
        for (int i = 0; i < allList.size(); i++){
            Product p = allList.get(i);
            if (cartName.contains(p.getName())){
                if (cartQuantity.get(p.getName()) != 0){
                    cartLabel.get(current).setText(cartQuantity.get(p.getName()) +  " for " + p.getName());
                    current++;
                }
            }
        }
    }

    public void updateSuccess(Transaction t){
        try{
            File f = new File("src/main/transactionReport.txt");
            FileWriter fw = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fw);
            pw.println(t.getFullDetails());
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void updateFail(Transaction t){
        try{
            File f = new File("src/main/cancel.txt");
            FileWriter fw = new FileWriter(f, true);
            PrintWriter pw = new PrintWriter(fw);
            Date date = new Date();
            pw.println(t.failReason() + ", " + sdf.format(date));
            pw.flush();
            fw.flush();
            pw.close();
            fw.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void anonymous(){
        time.stop();
        currentUser = new Customer("Anonymous", "", "customer");
        currentCustomerLabel.setText("Welcome Anonymous!");
        setContentPane(customerContainer);
        enoughChange = true;
        updateHistory();
        clearLabels();
        JComboBoxConstruction();
        show();
    }

    public void clearLabels(){
        for (JLabel j : cartLabel){
            j.setText("");
        }
        cartQuantity = new HashMap<>();
        cartName = new HashSet<>();
        candiesQuantity.setText("");
        chocolateQuantity.setText("");
        chipQuantity.setText("");
        drinkQuantity.setText("");
        customerHelpLabel.setText("");

    }


}
