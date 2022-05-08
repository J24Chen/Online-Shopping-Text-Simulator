import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Map.Entry; //Entry used for sorting maps
/*
 * Models a simple ECommerce system. Keeps track of products for sale, registered customers, product orders and
 * orders that have been shipped to a customer
 */
public class ECommerceSystem
{
    //private ArrayList<Product>  products = new ArrayList<Product>();
    private ArrayList<Customer> customers = new ArrayList<Customer>();

    private Map<String,Product> prods = new TreeMap<String, Product>(); //Using a treemap organizes it by ID

    private ArrayList<ProductOrder> orders   = new ArrayList<ProductOrder>();
    private ArrayList<ProductOrder> shippedOrders   = new ArrayList<ProductOrder>();

    private Map<Product,Integer> stats = new HashMap<Product,Integer>(); //Using HashMap since we only care by organizing the hashmap values


    // These variables are used to generate order numbers, customer id's, product id's 
    private int orderNumber = 500;
    private int customerId = 900;
    private int productId = 700;
    
    // General variable used to store an error message when something is invalid (e.g. customer id does not exist)  
    String errMsg = null;
    
    // Random number generator
    Random random = new Random();
    
    public ECommerceSystem()
    {
    	// NOTE: do not modify or add to these objects!! - the TAs will use for testing
    	// If you do the class Shoes bonus, you may add shoe products

    	// Create some products. Notice how generateProductId() method is used

        try { // Using Try and catch becasue I want to catch specific exceptions here only.
            File f = new File("products.txt");
            Scanner in = new Scanner(f);
            /*
            The format of the products.txt File has Each product separated by lines, with the category at the top,
            This while loop checks for each category, and filters them accordingly, making a for loop inside these cases that
            take all the parameters of each product and creating a new product object before returning to the while loop
             */
            String name,id;
            double price;
            int stock;

            while(in.hasNextLine()){
                String categoryCheck = in.nextLine();

                if (categoryCheck.equalsIgnoreCase("BOOKS")){
                    //Gathers all information from a book by using Scanner.next or .nextLine()

                    name = in.nextLine();
                    price = Double.parseDouble(in.nextLine());
                    int  paperbackStock =Integer.parseInt(in.next());
                    int hardcoverStock =Integer.parseInt(in.next());
                    //Since the ints are in the same line, we just use in.next and parse to int

                    in.useDelimiter(":"); //Switch to this delimiter for separating title, Author and year
                    String title = in.next().replaceAll("\r\n",""); //gets rid of the \r\n special characters at the beginning, keeps formatting correct
                    String author = in.next();
                    in.reset(); //resets the delimiter back to original (WhiteSpaces)
                    String year = in.nextLine().replaceFirst(":",""); //Gets rid of the Colon that would otherwise not be delimited

//                    //This block checks if book is a duplicate object or not.
//                    boolean checkDuplicate = false;
//                    Book dummyproduct = new Book(name,"0",price,paperbackStock,hardcoverStock,title,author,year); //Create a dummy book object
//                    for(Product p: prods.values()){
//                        if (p instanceof Book){ //If p is an instance of Book Object, cast product p into a book b
//                            Book b = ((Book) p);
//                            if(b.equals(dummyproduct)) //Uses the book equals method which checks the author
//                                checkDuplicate = true;
//                        }
//                    }
                    /*
                        This block of code isn't needed for this assignment but it is kept here in case it is needed
                        to check duplicate products in the future
                     */

                    id = generateProductId();
                    //Puts into prods map with the productID and the book object
                    prods.put(id, new Book(name, id, price, paperbackStock, hardcoverStock, title, author, year));

                } else if(categoryCheck.equalsIgnoreCase("SHOES")) {

                    name = in.nextLine();
                    price = Double.parseDouble(in.nextLine());
                    //For Shoes product to initialize, the 10 stocks (black6-black10,brown7-brown10) Must be initialized
                    int[] stockCount = new int[10];
                    for (int i = 0; i < stockCount.length; i++) {
                        stockCount[i] = Integer.parseInt(in.next());
                        //Iterates through the 10 options in the file, putting it into an array for shoes
                    }
                    id = generateProductId();
                    prods.put(id, new Shoes(name, id, price, stockCount));


                } else if (categoryCheck.equals("")){ // If there is an empty space in between categories, skip to next line
                    ; // do nothing, since the while loop takes a new line
                } else{ // Else, everything else is just a normal product and gathers accordingly

                    id = generateProductId();
                    name = in.nextLine();
                    price = Double.parseDouble(in.nextLine());
                    stock = in.nextInt();

                    prods.put(id,new Product(name,id,price,stock, Product.Category.valueOf(categoryCheck)));
                    //The Category is basically getting the Enum Value (Which is a string) that matches the String of Category Check.
                    //This can fail easily if the category doesn't contain CategoryCheck
                }

            }
//These catches shouldn't happen if the file is formatted correctly, but they're here just in case.
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        catch (NoSuchElementException e ){
            System.out.println(e);
        }


//    	products.add(new Product("Acer Laptop", generateProductId(), 989.0, 99, Product.Category.COMPUTERS));
//    	products.add(new Product("Apex Desk", generateProductId(), 1378.0, 12, Product.Category.FURNITURE));
//    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "Ahm Gonna Make You Learn", "T. McInerney", "2021"));
//    	products.add(new Product("DadBod Jeans", generateProductId(), 24.0, 50, Product.Category.CLOTHING));
//    	products.add(new Product("Polo High Socks", generateProductId(), 5.0, 199, Product.Category.CLOTHING));
//    	products.add(new Product("Tightie Whities", generateProductId(), 15.0, 99, Product.Category.CLOTHING));
//    	products.add(new Book("Book", generateProductId(), 35.0, 4, 2, "How to Fool Your Prof", "D. Umbast","1997"));
//    	products.add(new Book("Book", generateProductId(), 45.0, 4, 2, "How to Escape from Prison", "A. Fugitive","1963"));
//    	products.add(new Book("Book", generateProductId(), 44.0, 14, 12, "Ahm Gonna Make You Learn More", "T. McInerney","2001"));
//    	products.add(new Product("Rock Hammer", generateProductId(), 10.0, 22, Product.Category.GENERAL));
//        products.add(new Shoes("Jordans", generateProductId(), 149.99, new int[]{2, 3, 4, 56, 7, 8, 9, 10, 3, 4}));

    	// Create some customers. Notice how generateCustomerId() method is used
    	customers.add(new Customer(generateCustomerId(),"Inigo Montoya", "1 SwordMaker Lane, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Prince Humperdinck", "The Castle, Florin"));
    	customers.add(new Customer(generateCustomerId(),"Andy Dufresne", "Shawshank Prison, Maine"));
    	customers.add(new Customer(generateCustomerId(),"Ferris Bueller", "4160 Country Club Drive, Long Beach"));

    }
    
    private String generateOrderNumber()
    {
    	return "" + orderNumber++;
    }

    private String generateCustomerId()
    {
    	return "" + customerId++;
    }
    
    private String generateProductId()
    {
    	return "" + productId++;
    }
    
    public String getErrorMessage()
    {
    	return errMsg;
    }
    
    public void printAllProducts()
    {
    	for (Product p : prods.values())
    		p.print();

    }
    
    // Print all products that are books. See getCategory() method in class Product
    public void printAllBooks()
    {
        //If the category of the product = book, print it.
        //works since p.print prints each product individually.
        for (Product p: prods.values()){
            if (p.getCategory() == Product.Category.BOOKS){
                p.print();
            }
        }
    }

    
    // Print all current orders
    public void printAllOrders()
    {
        for (ProductOrder o : orders)
            o.print();
        // same Concept as printAllProducts

    }
    // Print all shipped orders
    public void printAllShippedOrders()
    {
    	for (ProductOrder p : shippedOrders){
            p.print();
        }
    }
    
    // Print all customers
    public void printCustomers()
    {
    	for (Customer c : customers)
            c.print();
    }

    /*
     * Given a customer id, print all the current orders and shipped orders for them (if any)
     */

    // Adding all exceptions are going to be easy, replace errmsg and return; with throw new..., if they use return methods that are useless replace the method with void keyword
    public void printOrderHistory(String customerId)
    {
      // Make sure customer exists - check using customerId
    	// If customer does not exist, set errMsg String and return false
    	// see video for an appropriate error message string
    	// ... code here

        //Encase everything in this for loop
        //Checks if any customers in the arrayList contains the customerId, if true, does this method

    	for(Customer c : customers ){
            if(c.getId().equalsIgnoreCase(customerId)) {
                // Print current orders of this customer
                System.out.println("Current Orders of Customer " + customerId);
                //Iterates through the product orders, printing only the ones that match the customerId
                for(ProductOrder o : orders) {
                    if(o.getCustomer().getId().equals(customerId))
                        o.print();
                }
                // enter code here

                // Print shipped orders of this customer
                System.out.println("\nShipped Orders of Customer " + customerId);
                //Iterates through the shipped orders, printing only the ones that match the customerId
                for(ProductOrder o: shippedOrders){
                    if(o.getCustomer().getId().equals(customerId))
                        o.print();
                }

//                return true;
                return; //After finding a customer and printing out every order, stop this method.
            }
    	}
        throw new UnknownCustomerException(customerId);
//    	return false;
    }


    public String orderProduct(String productId, String customerId, String productOptions)
    {
    	// First check to see if customer object with customerId exists in array list customers
    	// if it does not, set errMsg and return null (see video for appropriate error message string)
    	// else get the Customer object

//        System.out.printf("productID = %s customerID = %s productOptions = %s  \n", productId, customerId,productOptions);
        Customer c = null;

        for (Customer cu:customers) { //Iterates through the arraylist customers
//            System.out.println("cuID = " + cu.getId() + " compare to " + customerId);
            if (cu.getId().equals(customerId)) {
                c = cu; //Takes the Customer object if they are equal (c becomes a pointer object our customer)

                break;
            }
        }


        //Check if product exists within the map prods, (MORE EFFICIENT CAUSE MAPS BABY WOOOO). If prods doesn't contain ID then throws exception
        if(!prods.containsKey(productId)){
            throw new UnknownProductException(productId);
        }
        Product p = prods.get(productId); //If product exists, get the product

    	
    	// Check if the options are valid for this product (e.g. Paperback or Hardcover or EBook for Book product)
    	// See class Product and class Book for the method vaidOptions()
    	// If options are not valid, throw an exception
        // If the valid options check returns false, return null and an error message
        if (!p.validOptions(productOptions)){
            throw new InvalidOptionsException(p.getCategory(),productId,productOptions);
        }
    	// Check if the product has stock available (i.e. not 0)
    	// See class Product and class Book for the method getStockCount()
    	// If no stock available, set errMsg string and return null
        int stockCount = p.getStockCount(productOptions);
    	if(stockCount ==0){
//            errMsg = "Product " + p.getId() + " " + productOptions + " Out of Stock";
//            return null;
            throw new ProductOutofStockException(p.getId(),productOptions);
        }
      // Create a ProductOrder, (make use of generateOrderNumber() method above)
    	// reduce stock count of product by 1 (see class Product and class Book)
    	// Add to orders list and return order number string
        //adds to the orders arrayList a new product Order with the parameters from this method.
        p.reduceStockCount(productOptions);
        addStats(p); //Adds the product into the stats map
        ProductOrder po = new ProductOrder(generateOrderNumber(),p,c,productOptions);
        orders.add(po);


    	return po.getOrderNumber(); //Returns the Order number for the Method in ECommerceUserInterface
    }
    
    /*
     * Create a new Customer object and add it to the list of customers
     */
    
    public void createCustomer(String name, String address)
    {
    	// Check name parameter to make sure it is not null or ""
    	// If it is not a valid name, set errMsg (see video) and return false
    	// Repeat this check for address parameter
    	
    	// Create a Customer object and add to array list

        //Done here, Making sure Name and Address is not null or empty.
        if ((name == null) || (name.equals("")) ){
            throw new InvalidCustomerNameException();
        } else if ((address == null) || (address.equals("")) ){
            throw new InvalidCustomerAddressException();
        }
    	customers.add(new Customer(generateCustomerId(),name, address));

    }
    
    public ProductOrder shipOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
    	// Retrieve the order from the orders array list, remove it, then add it to the shippedOrders array list
    	// return a reference to the ProductOrder
        for(ProductOrder o: orders){
//            System.out.println(o + " Current ProductOrder object being worked with.");
            if (orderNumber.equalsIgnoreCase(o.getOrderNumber())){
                //adds to shippedOrders, removes from orders
                shippedOrders.add(o);
                //orders.remove(o); //CHANGE THIS . Don't know why this remove doesn't work (EXPLANATION BELOW)
                //^ I figured out why it no work, ArrayList.remove() function requires comparing the two objects
                // In ProductOrder the equals method was overidden with a return false; which made everything
                orders.remove(o);
                return o;

            }
        }


        throw new InvalidOrderException(orderNumber);
    }
    
    /*
     * Cancel a specific order based on order number
     */
    public void cancelOrder(String orderNumber)
    {
      // Check if order number exists first. If it doesn't, set errMsg to a message (see video) 
    	// and return false
        //get every object in the orders arraylist, get their ordernumber and compare them
        for (ProductOrder o: orders){
            if (o.getOrderNumber().equals(orderNumber)){
                orders.remove(o);
                return; //Only removes one instance, so to prevent ConcurrentModificationException, return from the method
            }
        }
        // for loop finishes means no ordernumber was found
        throw new InvalidOrderException(orderNumber);

    }
    
    // Sort products by increasing price
    public void sortByPrice()
    {
        //Compare the products in the arraylist by their price

        //Adds every value into a new arrayList
        ArrayList<Product> values = new ArrayList<Product>();
        for(Product o: prods.values()){
            values.add(o);
        }
//Sorts it by price by making a new Comparator inside Collections
        Collections.sort(values, new Comparator<Product>() {
          @Override
          public int compare(Product o1, Product o2) {
              return (int)(o1.getPrice() - o2.getPrice()); //cast to int since compare requires 1,0,-1
          }

      });
        // Print the sorted products
        for(Product p: values)
        { p.print();}


    }
    
    
    // Sort products alphabetically by product name
    public void sortByName()
    {

        // Look at SortByPrice, same process
        ArrayList<Product> values = new ArrayList<Product>();
        for(Product o: prods.values()){
            values.add(o);
        }
        Collections.sort(values, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return (o1.getName().compareTo(o2.getName()));
            }
        });

        // Print the sorted products
        for(Product p: values)
        { p.print();}
    }
    
        
    // Sort products alphabetically by product name
    public void sortCustomersByName()
    {
        //This one's going to be different since customers has a comparable interface
        Collections.sort(customers); //Explaining how this works is confusing
        /*
        To be honest I don't really know why exactly customers KNOWS how to be compared,
        I believe customers is an arrayLists containing customers, and to compare them with Collections.sort,
        Collections.sort recognizes that Customers implements compareable, so it uses that to sort the method by
        the compareTo method.
         */
    }

    //Adds an Product Item to a customers cart
    public void addItem(String productId, String customerId, String productOptions ){
        Customer c = customerExist(customerId);
        if(!prods.containsKey(productId)){
            throw new UnknownProductException(productId);
        } // Don't need an else statement since throw ends the method anyways.
        Product p = prods.get(productId); //If product exists, get the product

        //Checks if the productOptions is valid before adding to cart
        if (!p.validOptions(productOptions)){

            throw new InvalidOptionsException(p.getCategory(),productId,productOptions);
        }
        c.cart.createItem(new CartItem(p,productOptions)); //Gets the cart specific to the customer, adds a new CartItem with p,productOptions
    }
//Removes an Product Item from a customers cart
    public void removeItem(String productId, String customerId){
        Customer c = customerExist(customerId);


        /*Essentially iterates through the cartItems arraylist that belongs to customer, checks if that product id = the one listed
        and removes it if there's a match
        */
        for(CartItem ci : c.cart.cartItems){
            if(ci.cip.getId().equals(productId)){
                c.cart.cartItems.remove(ci);
                return; //returns from the method before the throw execption gets called
            }
        }
        throw new UnknownProductException(productId);

    }

    public void printCart(String customerId){
//        int index = customers.indexOf(new Customer(customerId)); //Different, more efficient way of finding if customers exist in the ArrayList
//        if (index == -1){ //Why didn't I think of this :(
//            throw new UnknownCustomerException(customerId);
//        }
//        Customer c = customers.get(index);
    // For some reason customer check above doesn't work? Not sure why though

        Customer c = customerExist(customerId);

        for(CartItem ci: c.cart.cartItems){
            ci.print();
        }

    }

    public void orderItems(String customerId){
        Customer c = customerExist(customerId);

        for(CartItem ci: c.cart.cartItems) {
            orderProduct(ci.cip.getId(), c.getId(), ci.ciOptions); //wow this looks so weird, basically gets the product and options from CartItems, and the customerId from c
        }
        //Now clear the entire arrayList of CartItems, much easier to do since I don't need to find anything.
        c.cart.cartItems.clear();


    }



    private Customer customerExist(String customerId){
        //This method checks if the customer exists Because for some reason the index method doesn't work and this is too long to be copy and pasted
        Customer c = null;
        for(Customer cu:customers){
            if (cu.getId().equals(customerId)){
                c = cu;
                break;
            } // Checking if customerId exists in customers arrayList, if not throw an exception below
        }

        if (c == null){

            throw new UnknownCustomerException(customerId);
        }
        return c;
    }

    //Adds any ordered product into the stats map
    private void addStats(Product p){
        // Two cases, if stats are new vs if stats are old
        //Very simple counter program using maps

        if(stats.containsKey(p)){
            stats.replace(p,stats.get(p)+1); //Adds 1 to the value to the map IF it exists
        } else {
            stats.put(p,1);
        }
    }

    // creates an organized LinkedList of the stats map, converts into an organized map and prints out accordingly.
    /*
    This process requires the use of Entry, an interface that essentially takes the key and value and puts it into one
    variable
     */
    public void printStats(){
        //Converts the Map into a linkedList by taking the entrySet of stats
        List<Entry<Product,Integer>> list = new LinkedList<Entry<Product,Integer>>(stats.entrySet());

        //Sort this list with Collections.sort, Creating a new comparator interface that comapres the two integers
        Collections.sort(list, new Comparator<Entry<Product, Integer>>() {
            @Override
            public int compare(Entry<Product, Integer> o1, Entry<Product, Integer> o2) {
                return o2.getValue() - o1.getValue(); //Returns the Integer of o2-o1, getting the decreasing order
            }
        });
        //Create a sortedStats map with this sorted list
        Map <Product,Integer> sortedStats = new LinkedHashMap<Product,Integer>();

        for(Entry<Product,Integer> o: list){
            sortedStats.put(o.getKey(),o.getValue());
        }
        //Now use a for loop to print out the sortedStats map for proper formatting
        for(Entry<Product,Integer> i : sortedStats.entrySet()){
            System.out.println("Name: " + i.getKey().getName() + " ProductId: " + i.getKey().getId()+ ". Times Ordered:" + i.getValue());
        }

    }
    public void rateProduct(String productId, int rate){
        //Check if the product exists, assign Product p to that product id
        if(!prods.containsKey(productId))
            throw new UnknownProductException(productId);
        Product p = prods.get(productId);

        //If rating is not 1-5 throw an exception
        if(rate < 1 || rate > 5)
            throw new InvalidRating(rate);

        //adds the user input into the ratings map
        if(p.ratings.containsKey(rate)){
            p.ratings.put(rate,p.ratings.get(rate)+1);
        } else{
            p.ratings.put(rate,1);
        }
    }

    public void printProductRatings(String productId){
        //Check if the product exists, assign Product p to that product id
        if(!prods.containsKey(productId))
            throw new UnknownProductException(productId);
        Product p = prods.get(productId);

        System.out.println("Ratings for product " + productId +":");
        for(int i: p.ratings.keySet()){
            System.out.println(" Rating: " + i + "\t Number of ratings: " + p.ratings.get(i));
        }
    }

    public void printByAverageRatings(String category,int rate) {
        //Set an arrayList containing all products of a certain category
        ArrayList<Product> sortedRatings = new ArrayList<Product>();
        //Iterates through the values of the prods (which is products). Checks if product category = the users input
        for (Product p: prods.values()){
            if(p.getCategory() == Product.Category.valueOf(category)){
                sortedRatings.add(p);
            }
        }
        //Iterates through the arrayList of products
        for(Product p: sortedRatings){
            p.setAverageRatings(); //Sets the average ratings of all products since it's not done anywhere else.
            if(p.getAverageRatings() > rate){  //Checks if the average rating is above rate
                p.print();
            }
        }



    }


}


//Exceptions declared outside of public class EcommerceSystem.

/* Created new exceptions that functionally perform the same thing, requiring the new thrown exception to contain certain string info
    All the errors are formatted inside the exception, requiring information from the method that thrown it.
*/
class UnknownCustomerException extends RuntimeException
{
    public UnknownCustomerException(String customerId){
        super("Customer " + customerId + " not found");
    }
}

class UnknownProductException extends RuntimeException
{
    public UnknownProductException(String productId){
        super("Product " + productId + " Not Found");
    }
}

class InvalidOptionsException extends RuntimeException
{
    public InvalidOptionsException(Product.Category category, String productId, String options){
        super("Product " + category + " ProductID " + productId + " Invalid Options: " + options);
    }
}

class ProductOutofStockException extends RuntimeException
{
    public ProductOutofStockException(String productId,String options){
        super("Product " + productId + " " + options + " Out of Stock");
    }
}

class InvalidCustomerNameException extends RuntimeException
{
    public InvalidCustomerNameException(){
        super("Invalid Customer name");
    }
}

class InvalidCustomerAddressException extends RuntimeException
{
    public InvalidCustomerAddressException(){
        super("Invalid Customer Address");
    }
}

class InvalidOrderException extends RuntimeException
{
    public InvalidOrderException(String orderNumber){
        super("Order " + orderNumber + " Not Found");
    }
}

class InvalidRating extends RuntimeException
{
    public InvalidRating (int rating){
        super("Rating Number " + rating + " too high or too low" );
    }
}


