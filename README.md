


A Vending Machine with GUI handles various tasks depended on user types. 


USER LOGIN

(Note: all user info(type, name, password) will be found in users.txt)

- As an Anonymous Customer:

You are able to purchase any items in stock at the homepage. Move to PURCHASE ITEM section to read the guide of purchasing items.

- As a Login Customer:

If you have an account already, at the homepage, you should press [2: Login your account] to purchase items as a login customer. After providing name and password, you should be proceeded to the purchase page the same as homepage to make a selection.

If you do not have an account yet, at the homepage, you should choose [1: Create new account] to create a new account. You will be directed to the purchase page without logging in.

You can always press [Cancel] to go back to homepage/anonymous customer page and restart the system.

- As a Seller:

You are required to have an account to login as a seller. You should press [2: Login your account] and provide valid name and password. You will be recognized as a seller and proceeded to the page of seller functions. 

You are able to inspect and modify all items in the vending machine, including name, code, category, quantity and price. 

You can generate sales report by pressing [Generate Sales Report] (salesReport.txt) as well as product report by pressing [Generate Available Product Report] (productReport.txt).

You can always press [Back] to go back to homepage/anonymous customer page and restart the system.

- As a Cashier:

You are required to have an account to login as a cashier. You should press [2: Login your account] and provide valid name and password. You will be recognized as a cashier and proceeded to the page of cashier functions. 

You are able to inspect and modify the quantity of notes and coins inside this vending machine.

You can generate current cash amount report by pressing [Botton: Generate Change Report] (changeReport.txt) as well as transaction report by pressing [Botton: Generate Transaction Report] (transactionReport.txt).

You can always press [Back] to go back to homepage/anonymous customer page and restart the system.

- As an Owner:

You are required to have an account to login as an owner. You should press [2: Login your account] and provide valid name and password. You will be recognized as an owner and proceeded to the page of all(cashier+seller+owner) functions. 

Instructions for [Seller] and [Cashier] can be found above. 

Especially for the owner, you are able to add/delete users in this system. 

For addition, the name should be unique among any other users inside this system. Otherwise, error message will be printed.

For deletion, you should provide the specific name, its password and its type to proceed. Otherwise, error message will be printed.

(Note: when you create a new customer, you will be automatically logged in as this new customer and directed to the purchase page.)

You can generate current user report by pressing [Botton: Generate User Report] (users.txt) as well as user deleted report by pressing [Botton: Generate Cancel Report] (cancel.txt).

You can always press [Back] to go back to homepage/anonymous customer page and restart the system.


PURCHASE ITEM

- At the purchase page/homepage, you can press [Select] to select items using the combo boxes. The selected items will be listed below. The quantity can always be modified before proceeding to checkout.

If you insert a negative number or the number is larger than the current stock, error messages will be printed.

You can press [Clear] if you want to restart the purchase. You can always press [Cancel] to go back to homepage/anonymous customer page and restart the system. 

- After you finish the selection of items, press [Checkout] to proceed to the checkout page.

You have a choice of paying by card or cash.
 
For card payment, you have to be an existing cardholder matching with the given credit card list. If you login in with an existing account who paid with credit card before, credit card info will be automated filled but you can still choose to pay by cash.

For cash payment, you are required to insert sufficient amount of cash. $100 note will not be accepted here. If the cash inside this vending machine is not enough to give you a correct change, error message will be printed.

After a payment, a successful message will be printed.

During the transaction, if you don't do any operation in one minute, you will be automatically logged out.




