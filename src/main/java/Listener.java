import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.io.*;

public class Listener implements ActionListener {
    protected App app;
    protected Container container;
    public Listener(App app, Container container){
        this.app = app;
        this.container = container;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.setContentPane(this.container);
        this.app.show();
    }
}

class createListener extends Listener{
    public createListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        ArrayList<User> users = this.app.getUsers();
        String name = this.app.getCreateName();
        String password = this.app.getCreatePassword();
        app.createAccount(name, password, "customer", false);
    }
}

class loginListener extends Listener{
    public loginListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = this.app.getLoginName();
        String password = this.app.getLoginPassword();
        app.loginAccount(name, password);
    }

}

class backListener extends Listener{
    public backListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.customerBack();
    }
}

class checkoutListener extends Listener{
    public checkoutListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        this.app.time.start();
        if(app.updateCart() == false){
            return;
        }

        if (app.preProcessTransaction() == false){
            return;
        }

        Transaction t = new Transaction(this.app.currentUser.getName());
        t.setCart(app.cartQuantity, app.cartName);
        app.currentTransaction = t;
        app.customerHelpLabel.setText("");

        if (app.currentUser.getName().equals("Anonymous") == false){
            if (app.creditHistory.get(app.currentUser.getName()) != null){
                app.creditName.setText(app.creditHistory.get(app.currentUser.getName()));
                app.creditPassword.setText(app.creditCards.get(app.creditHistory.get(app.currentUser.getName())));
            }
        }
        super.actionPerformed(e);
    }
}

class timeListener extends Listener{
    public timeListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Transaction t = new Transaction(app.currentUser.getName());
        t.setReason("timeout");
        app.cancelList.add(t);
        app.updateFail(t);
        app.anonymous();
    }
}

class cancelCustomerListener extends Listener{
    public cancelCustomerListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        Transaction t = new Transaction(app.currentUser.getName());
        if (this.app.enoughChange == false){
            t.setReason("Not enough change");
        }
        else{
            t.setReason("User cancelled");
        }
        this.app.clearCart();
        this.app.quantityText.setText("");
        this.app.productText.setText("");
        app.cancelList.add(t);
        app.updateFail(t);
        app.anonymous();
        app.clearLabels();
    }
}

class clearListener extends Listener {
    public clearListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        this.app.time.start();
        this.app.clearLabels();
    }
}

class payMoneyListener extends Listener{
    public payMoneyListener(App app, Container container){
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.time.stop();
        app.time.start();

        if (app.currentAmount < app.transactionAmount){
            app.addMoneyHelp.setText("Not enough money.");
            return;
        }
        double target = app.currentAmount - app.transactionAmount;
        int[] ret = app.change(app.currentMoney, app.currentChange, target, app.changeType);

        if (ret[10] != 1){
            app.time.stop();
            String message = "Change amount: " + target + ", Change details: ";
            if (target == 0){
                message += "None  ";
            }
            for (int i = 0; i< 10;i++){
                if (ret[i] != 0){
                    message += ret[i] +"*$" + app.changeType[i]+", ";
                }
            }

            for (String s : app.cartName){
                app.transactionNameHistory.add(app.currentUser.getName());
                app.transactionItemHistory.add(s);
            }
            app.updateTransaction();

            message = message.substring(0 ,message.length()-2);
            app.successChangeMoney.setText(message);
            app.currentTransaction.setChange(message);
            app.currentTransaction.setAmount(app.currentAmount + "");
            app.currentTransaction.setPaymentMethod("cash");
            app.transactionsList.add(app.currentTransaction);
            app.currentTransaction.successTransaction(app);
            app.updateSuccess(app.currentTransaction);
            app.JComboBoxConstruction();
            app.successLabel.setText("Successfully paid by cash " + app.currentAmount + " with change " + target +" by "+app.currentUser.getName());
            app.payMoneyHelp.setText("");
            app.addMoneyHelp.setText("");
            app.addMoneyQuantity.setText("");
            app.currentUser = new Customer("Anonymous" , "", "customer");
            app.currentCustomerLabel.setText("Welcome Anonymous!");
            app.setContentPane(container);
            app.updateProduct();
            app.show();
        }
        else{
            app.successChangeMoney.setText("");
            app.payMoneyHelp.setText("Not enough change.");
        }


    }
}

class addMoneyListener extends Listener {
    public addMoneyListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        this.app.time.start();
        try{
            int which = app.cashBox.getSelectedIndex();
            double value = app.changeType[which-1];
            int much = Integer.parseInt(app.addMoneyQuantity.getText());
            if ((which == 0) || (much == 0)){
                throw new Exception();
            }
            app.currentAmount += much*value;
            app.currentMoney[which-1] += much;
            app.checkoutPay.setText("Money you have inserted: $" + app.currentAmount);
            app.addMoneyHelp.setText("");
            return;
        }
        catch (Exception g){
            app.addMoneyHelp.setText("Please select a correct money type and enter a valid amount!");
        }
    }
}

class clearMoneyListener extends Listener {
    public clearMoneyListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        this.app.time.start();
        app.cashBox.setSelectedIndex(0);
        app.addMoneyQuantity.setText("");
        app.addMoneyHelp.setText("");
        app.payMoneyHelp.setText("");
        app.currentMoney = new int[10];
        app.currentAmount = 0.0;
        app.checkoutPay.setText("Money you have inserted: $0");
    }
}

class productSelectListener extends Listener{
    private JComboBox combo;
    private JTextField text;
    public productSelectListener(App app, Container container, JComboBox combo, JTextField text){
        super(app, container);
        this.combo = combo;
        this.text = text;
    }

    public void actionPerformed(ActionEvent e){
        this.app.time.stop();
        this.app.time.start();

        String which[] = (this.combo.getSelectedItem()+"").split(",");

        try{
            int much = Integer.parseInt(this.text.getText());
            if ((much < 0) || (which.length == 1)){
                throw new Exception();
            }
            if (much > app.productStock.get(which[0])){
                app.customerHelpLabel.setText("Out of Stock!");
                return;
            }
            app.cartQuantity.put(which[0], much);
            app.cartName.add(which[0]);
            app.setCartLabel();
            app.customerHelpLabel.setText("");

        }
        catch (Exception g){
            this.app.customerHelpLabel.setText("Wrong product or amount");
        }
    }
}


class successListener extends Listener {
    public successListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.time.stop();
        String name = this.app.creditName.getText();
        String password = this.app.creditPassword.getText();
        if (this.app.creditCards.get(name) != null){
            if (this.app.creditCards.get(name).equals(password)){
                if (app.currentUser.getName().equals("Anonymous") == false)
                app.creditHistory.put(app.currentUser.getName(), name);
                app.updateCredit();
                for (String s : app.cartName){
                    app.transactionNameHistory.add(app.currentUser.getName());
                    app.transactionItemHistory.add(s);
                }
                app.updateTransaction();

                this.app.creditCardHelp.setText("");
                this.app.setContentPane(container);
                this.app.show();
                this.app.currentTransaction.successTransaction(app);

                app.JComboBoxConstruction();
                app.updateProduct();
                this.app.currentTransaction.setChange("Change Amount: 0.0, Change details: None");
                this.app.currentTransaction.setAmount(app.transactionAmount + "");
                this.app.currentTransaction.setPaymentMethod(name + "'s credit card");
                this.app.transactionsList.add(this.app.currentTransaction);
                this.app.successLabel.setText(this.app.currentTransaction.paymentDetails());
                app.updateSuccess(app.currentTransaction);
                this.app.clearCart();
                this.app.clearLabels();
                return;
            }
        }
        this.app.creditCardHelp.setText("Wrong name or password. Please try again.");
        this.app.time.start();
    }
}


class anonymousListener extends Listener {
    public anonymousListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.anonymous();
    }
}

class returnListener extends Listener {
    public returnListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.completeTransaction();
    }
}

class cashierModifyListener extends Listener {
    public cashierModifyListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            int which = app.cashierBox.getSelectedIndex();
            String much = app.cashierTarget.getText();
            app.cashierModify(which, much);
        }
        catch (Exception g){
            app.cashierHelp.setText("Please select or enter a correct amount!");
        }
    }
}

class sellerModifyListener extends Listener {
    public sellerModifyListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        int which = app.sellerNameBox.getSelectedIndex();
        int what = app.sellerExecuteBox.getSelectedIndex();
        app.sellerModify(which, what);
    }
}

class cashierCurrentListener implements ItemListener{
    private App app;
    private JComboBox combo;
    public cashierCurrentListener(App app, JComboBox combo){
        this.app = app;
        this.combo = combo;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            int which = combo.getSelectedIndex();
            app.cashierCurrent(which);
        }
    }
}

class sellerCurrentListener implements ItemListener{
    private App app;
    private JComboBox nameCombo;
    private JComboBox optionCombo;

    public sellerCurrentListener(App app, JComboBox nameCombo, JComboBox optionCombo){
        this.app = app;
        this.nameCombo = nameCombo;
        this.optionCombo = optionCombo;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getStateChange() == ItemEvent.SELECTED){
            int which = nameCombo.getSelectedIndex();
            int what = optionCombo.getSelectedIndex();
            app.sellerCurrent(which, what);
        }
    }
}


class cashierBackListener extends Listener {
    public cashierBackListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.clearCashierSeller();
    }
}

class sellerBackListener extends Listener {
    public sellerBackListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.clearCashierSeller();
    }
}

class cashierChangeReportListener extends Listener {
    public cashierChangeReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            File f = new File("src/main/changeReport.txt");
            PrintWriter write = new PrintWriter(f);
            write.println("$50, " + app.currentChange.get(50.0));
            write.println("$20, " + app.currentChange.get(20.0));
            write.println("$10, " + app.currentChange.get(10.0));
            write.println("$5, " + app.currentChange.get(5.0));
            write.println("$2, " + app.currentChange.get(2.0));
            write.println("$1, " + app.currentChange.get(1.0));
            write.println("50c, " + app.currentChange.get(0.5));
            write.println("20c, " + app.currentChange.get(0.2));
            write.println("10c, " + app.currentChange.get(0.1));
            write.println("5c, " + app.currentChange.get(0.05));
            app.cashierReportHelp.setText("Successfully generate changeReport.txt");
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }
}

class cashierTransactionReportListener extends Listener {
    public cashierTransactionReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.generateTransactionReport();
    }
}

class sellerProductReportListener extends Listener {
    public sellerProductReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            File f = new File("src/main/productReport.txt");
            PrintWriter write = new PrintWriter(f);
            for (Product p : app.allList){
                if (p.getQuantity() > 0){
                    write.println(p.getDetails());
                }
            }
            app.sellerReportHelp.setText("Successfully generate productReport.txt");
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }
}

class sellerSalesReportListener extends Listener {
    public sellerSalesReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try{
            File f = new File("src/main/salesReport.txt");
            PrintWriter write = new PrintWriter(f);
            for (Product p : app.allList){
                write.println(p.getSold());
            }
            app.sellerReportHelp.setText("Successfully generate salesReport.txt");
            write.close();
        }
        catch (Exception g){
            g.printStackTrace();
        }
    }
}

class ownerChangeReportListener extends Listener {
    public ownerChangeReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.cashierLogin.setText(this.app.ownerLogin.getText());
        this.app.setContentPane(container);
        this.app.show();
    }
}

class ownerProductReportListener extends Listener {
    public ownerProductReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        this.app.sellerLogin.setText(this.app.ownerLogin.getText());
        this.app.setContentPane(container);
        this.app.show();
    }
}

class ownerAddListener extends Listener{
    public ownerAddListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = app.ownerTarget.getText();
        String password = app.ownerPassword.getText();
        String type = app.ownerType.getText();
        app.createAccount(name, password, type, true);
    }
}

class ownerDeleteListener extends Listener{
    public ownerDeleteListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = app.ownerTarget.getText();
        String type = app.ownerType.getText();
        app.deleteAccount(name, type);
    }
}

class ownerUserReportListener extends Listener {
    public ownerUserReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.generateUserReport();
        app.ownerReportHelp.setText("Successfully generated users.txt");
    }
}

class ownerCancelReportListener extends Listener {
    public ownerCancelReportListener(App app, Container container) {
        super(app, container);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        app.ownerReportHelp.setText("Successfully generated cancel.txt");
    }
}



