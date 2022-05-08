import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

// Simulation of a Simple E-Commerce System (like Amazon)

public class ECommerceUserInterface
{
	public static void main(String[] args)  {
		// Create the system
		ECommerceSystem amazon = new ECommerceSystem();

		Scanner scanner = new Scanner(System.in);
		System.out.print(">");
		
		// Process keyboard actions
		while (scanner.hasNextLine()) {
			try {
				String action = scanner.nextLine();

				if (action == null || action.equals("")) {
					System.out.print("\n>");
					continue;
				} else if (action.equalsIgnoreCase("Q") || action.equalsIgnoreCase("QUIT"))
					return;

				else if (action.equalsIgnoreCase("PRODS"))    // List all products for sale
				{
					amazon.printAllProducts();
				} else if (action.equalsIgnoreCase("BOOKS"))    // List all books for sale
				{
					amazon.printAllBooks();
				} else if (action.equalsIgnoreCase("CUSTS"))    // List all registered customers
				{
					amazon.printCustomers();
				} else if (action.equalsIgnoreCase("ORDERS")) // List all current product orders
				{
					amazon.printAllOrders();
				} else if (action.equalsIgnoreCase("SHIPPED"))    // List all orders that have been shipped
				{
					amazon.printAllShippedOrders();
				} else if (action.equalsIgnoreCase("NEWCUST"))    // Create a new registered customer
				{
					String name = "";
					String address = "";

					System.out.print("Name: ");
					if (scanner.hasNextLine())
						name = scanner.nextLine();

					System.out.print("\nAddress: ");
					if (scanner.hasNextLine())
						address = scanner.nextLine();

					amazon.createCustomer(name, address);

				} else if (action.equalsIgnoreCase("SHIP"))    // ship an order to a customer
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine(); //if Scanner is available checks for next line
					// Get order number from scanner
					ProductOrder shipResult = amazon.shipOrder(orderNumber);

					if (shipResult == null) { // Checks if the returned amazon.shipOrder method is null, if true an error occured
						System.out.println(amazon.getErrorMessage());
					} else {
						shipResult.print();
					}
					// Ship order to customer (see ECommerceSystem for the correct method to use

				} else if (action.equalsIgnoreCase("CUSTORDERS")) // List all the current orders and shipped orders for this customer id
				{
					String customerId = "";

					System.out.print("Customer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					// Print all current orders and all shipped orders for this customer
					amazon.printOrderHistory(customerId);
				} else if (action.equalsIgnoreCase("ORDER")) // order a product for a certain customer
				{
					String productId = "";
					String customerId = "";

					System.out.print("Product Id: ");
					// Get product Id from scanner
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// Get customer Id from scanner
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();
					//These two scanner.hasNextLine() makes sure that Scanner has something available before taking an input

					// Order the product. Check for valid orderNumber string return and for error message set in ECommerceSystem

					// This order method below is made in reference to ORDERBOOK, look at that method for explanation
					String orderResult = amazon.orderProduct(productId, customerId, "");
					System.out.println("Order #" + orderResult);


					// Print Order Number string returned from method in ECommerceSystem
				} else if (action.equalsIgnoreCase("ORDERBOOK")) // order a book for a customer, provide a format (Paperback, Hardcover or EBook)
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();

					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nFormat [Paperback Hardcover EBook]: ");
					// get book forma and store in options string
					if (scanner.hasNextLine())
						options = scanner.nextLine();
//Does Scanner.nextLine() if scanner.hasNextLine is true for all 3 strings

					// Order product. Check for error mesage set in ECommerceSystem
					// Print order number string if order number is not null
						//Since all errors are checked in .orderProduct, no need for null checks
					//Create a variable that tries amazon.OrderProduct to store the return value.
					//Done like this to prevent calling the method multiple times
					String orderResult = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderResult);


				} else if (action.equalsIgnoreCase("ORDERSHOES")) // order shoes for a customer, provide size and color
				{
					String productId = "";
					String customerId = "";
					String options = "";

					System.out.print("Product Id: ");
					// get product id
					if (scanner.hasNextLine())
						productId = scanner.nextLine();
					System.out.print("\nCustomer Id: ");
					// get customer id
					if (scanner.hasNextLine())
						customerId = scanner.nextLine();

					System.out.print("\nColor: \"Black\" \"Brown\": ");
					// get shoe color and append to options
					if (scanner.hasNextLine())
						options = scanner.nextLine().toLowerCase();

					System.out.print("\nSize: \"6\" \"7\" \"8\" \"9\" \"10\": ");
					// get shoe size and store in options
					if (scanner.hasNextLine())
						options += scanner.nextLine(); // appends the color and size next to each other
					//order shoes

					//same thing as orderbooks method
					String orderResult = amazon.orderProduct(productId, customerId, options);
					System.out.println("Order #" + orderResult);
				} else if (action.equalsIgnoreCase("CANCEL")) // Cancel an existing order
				{
					String orderNumber = "";

					System.out.print("Order Number: ");
					// get order number from scanner
					if (scanner.hasNextLine())
						orderNumber = scanner.nextLine();
					// cancel order. Check for error
					amazon.cancelOrder(orderNumber);

				} else if (action.equalsIgnoreCase("PRINTBYPRICE")) // sort products by price
				{
					amazon.sortByPrice();
				} else if (action.equalsIgnoreCase("PRINTBYNAME")) // sort products by name (alphabetic)
				{
					amazon.sortByName();
				} else if (action.equalsIgnoreCase("SORTCUSTS")) // sort products by name (alphabetic)
				{
					amazon.sortCustomersByName();

				} else if (action.equalsIgnoreCase("ADDTOCART")) //add a product item to a customer's cart
				{
					String customerId = "";
					String productId = "";
					String productOptions = "";
					System.out.print("Customer ID: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine(); //if Scanner is available checks for next line
					System.out.print("\nProduct ID: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine(); //if Scanner is available checks for next line
					System.out.print("\nProduct Options (eg: black6,brown8, hardcover, or leave blank): ");
					if (scanner.hasNextLine())
						productOptions = scanner.nextLine(); //if Scanner is available checks for next line

					amazon.addItem(productId,customerId,productOptions);

				} else if (action.equalsIgnoreCase("REMCARTITEM")) //remove an item from a customer's cart
				{
					String customerId = "";
					String productId = "";

					System.out.print("Customer ID: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine(); //if Scanner is available checks for next line
					System.out.print("\nProduct ID: ");
					if (scanner.hasNextLine())
						productId = scanner.nextLine(); //if Scanner is available checks for next line
					amazon.removeItem(productId,customerId);


				} else if (action.equalsIgnoreCase("PRINTCART")) //Print all items of a customer's cart
				{
					String customerId = "";
					System.out.print("Customer ID: ");

					if (scanner.hasNextLine())
						customerId = scanner.nextLine(); //if Scanner is available checks for next line

					amazon.printCart(customerId);

				} else if (action.equalsIgnoreCase("ORDERITEMS")) //Order all items from a cart
				{
					String customerId = "";
					System.out.print("Customer ID: ");
					if (scanner.hasNextLine())
						customerId = scanner.nextLine(); //if Scanner is available checks for next line

					amazon.orderItems(customerId);
					//Prints the stats
				} else if(action.equalsIgnoreCase("PRINTSTATS"))//Print the order stats of a product
				{
					amazon.printStats();

					//Rates a specific product from 1-5
				} else if(action.equalsIgnoreCase("RATEPRODUCT")) // Rates a product from 1-5
				{
					String productId = "";
					System.out.print("Product ID: ");
					if(scanner.hasNextLine())
						productId = scanner.nextLine();
					System.out.print("\nRating(1-5): ");
					int rating = 0;
					if(scanner.hasNextLine())
						rating = scanner.nextInt();

					amazon.rateProduct(productId,rating);

				//Prints all ratings of a specific product
				} else if(action.equalsIgnoreCase("PRINTRATINGS")) //Prints the ratings of a product
				{
					String productId = "";
					System.out.print("Product ID: ");
					if(scanner.hasNextLine())
						productId = scanner.nextLine();
					amazon.printProductRatings(productId);

					//Prints by a specific rating
				} else if(action.equalsIgnoreCase("PRINTBYRATINGS")) //Prints all products separated by category and rating threshold
				{
					String category = "";
					System.out.print("Category (eg. BOOKS, SHOES, CLOTHING, GENERAL): ");
					if(scanner.hasNextLine())
						category = scanner.nextLine().toUpperCase();

					System.out.print("\nRate (Above which ratings): ");
					int rating = 0;
					if(scanner.hasNextLine())
						rating = scanner.nextInt();
					amazon.printByAverageRatings(category,rating);
				}



				System.out.print("\n>");


			} catch (RuntimeException e){ System.out.println(e);}
			//Catches any RuntimeExceptions (which is all custom exceptions)
			//Prints the exceptions through polymorphism.



		}
	}

}
