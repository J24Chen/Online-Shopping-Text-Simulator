import java.util.Map;
import java.util.TreeMap;

/*
 * class Product defines a product for sale by the system. 
 * 
 * A product belongs to one of the 5 categories below. 
 * 
 * Some products also have various options (e.g. size, color, format, style, ...). The options can affect
 * the stock count(s). In this generic class Product, product options are not used in get/set/reduce stockCount() methods  
 * 
 * Some products
 */
public class Product
{

	public static enum Category {GENERAL, CLOTHING, BOOKS, FURNITURE, COMPUTERS,SHOES};
	
	private String name;
	private String id;
	private Category category;
	private double price;
	private int stockCount;
	public Map<Integer,Integer> ratings = new TreeMap<Integer, Integer>(); //Creates ratings specific to a product,
	//public since it needs to be accessed by EcommcerSystem
	public double averageRatings = 0; //averageRatings
	
	public Product()
	{
		this.name = "Product";
		this.id = "001";
		this.category = Category.GENERAL;
		this.stockCount = 0;
	}
	
	public Product(String id)
	{
		this("Product", id, 0, 0, Category.GENERAL);
	}

	public Product(String name, String id, double price, int stock, Category category)
	{
		this.name = name;
		this.id = id;
		this.price = price;
		this.stockCount = stock;
		this.category = category;
	}
	/*
	 * This method always returns true in class Product. In subclasses, this method will be overridden
	 * and will check to see if the options specified are valid for this product.
	 */
	public boolean validOptions(String productOptions)
	{
		if(productOptions == null || productOptions.equals("")){
			return true;
			// Makes sure that orderbook doesn't work with other products.
			// To be honest interfaces would be much nicer for this why is this used lol
			//WHAT IS EVEN THE POINT OF THIS IF OPTIONS IS ONLY USED FOR BOOKS. ahem
		}
		return false;
	}
	
	public Category getCategory()
	{
		return category;
	}
	
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public double getPrice()
	{
		return price;
	}

	public void setPrice(double price)
	{
		this.price = price;
	}

	/*
	 * Return the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public int getStockCount(String productOptions)
	{
		return stockCount;
	}
	/*
	 * Set (or replenish) the number of items currently in stock for this product
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void setStockCount(int stockCount, String productOptions)
	{
		this.stockCount = stockCount;
	}
	/*
	 * Reduce the number of items currently in stock for this product by 1 (called when a product has
	 * been ordered by a customer)
	 * Note: in this general class, the productOptions parameter is not used. It may be used
	 * in subclasses.
	 */
	public void reduceStockCount(String productOptions)
	{
		stockCount--;
	}
	
	public void print()
	{
		System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f", id, category, name, price);
	}
	
	/*
	 * Two products are equal if they have the same product Id.
	 * This method is inherited from superclass Object and overridden here
	 */
	public boolean equals(Object other)
	{
		Product otherP = (Product) other;
		return this.id.equals(otherP.id);
	}

	//Computes the average of every Product
	public void setAverageRatings (){
		double x = 0; //The top part of an average
		double total = 0; // Total number of ratings
		for (int i: ratings.keySet()){
			x += i * ratings.get(i); //Multiplies the key by values (In this context, multiples rating # by # of times rated)
			total += ratings.get(i); //adds # of times rated into the total,
		}

		averageRatings = x/total;
	}

	public double getAverageRatings(){
		return averageRatings;
	}

}
