import java.util.Locale;

/* A book IS A product that has additional information - e.g. title, author

 	 A book also comes in different formats ("Paperback", "Hardcover", "EBook")
 	 
 	 The format is specified as a specific "stock type" in get/set/reduce stockCount methods.

*/
public class Book extends Product 
{
  private String author;
  private String title;
  private String year;
  // Stock related information NOTE: inherited stockCount variable is used for EBooks
  int paperbackStock;
  int hardcoverStock;
  
  public Book(String name, String id, double price, int paperbackStock, int hardcoverStock, String title, String author, String year)
  {
  	 // Make use of the constructor in the super class Product. Initialize additional Book instance variables. 
  	 // Set category to BOOKS
     //Creates a product, using product superclass. Stock adds both stocks together and category 2 represents books on the enum
      super(name,id,price,paperbackStock+hardcoverStock,Category.BOOKS);

      // Sets the rest of the variables as a book class.
     this.paperbackStock = paperbackStock;
     this.hardcoverStock = hardcoverStock;
     this.title = title;
     this.author = author;
     this.year = year;

  }
    
  // Check if a valid format  
  public boolean validOptions(String productOptions)
  {
  	// check productOptions for "Paperback" or "Hardcover" or "EBook"
  	// if it is one of these, return true, else return false
    if(productOptions.equalsIgnoreCase("Paperback") || productOptions.equalsIgnoreCase("Hardcover")|| productOptions.equalsIgnoreCase("EBook") ) {
        return true;
    } else {
        return false;
    }
  }
  
  // Override getStockCount() in super class.
  public int getStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and return) the number of stock for "Paperback" etc
  	// Use the variables paperbackStock and hardcoverStock at the top. 
  	// For "EBook", use the inherited stockCount variable.
  	   if(productOptions.equalsIgnoreCase("Paperback")){
            return this.paperbackStock;
       } else if (productOptions.equalsIgnoreCase("hardcover")){
            return this.hardcoverStock;
       }else if (productOptions.equalsIgnoreCase("Ebook")){
             return super.getStockCount(productOptions);
       }else {
             return -1;
       }

	}
  
  public void setStockCount(int stockCount, String productOptions)
	{
    // Use the productOptions to check for (and set) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.

        // Same thing as get, but instead setting it
        if(productOptions.equalsIgnoreCase("Paperback")){
            paperbackStock = stockCount;
        } else if (productOptions.equalsIgnoreCase("hardcover")){
            hardcoverStock = stockCount;
        }else if (productOptions.equalsIgnoreCase("Ebook")){
            super.setStockCount(stockCount, productOptions);
        }else {
            System.out.println("Error, Invalid Product options");
        }
	}
  
  /*
   * When a book is ordered, reduce the stock count for the specific stock type
   */
  public void reduceStockCount(String productOptions)
	{
  	// Use the productOptions to check for (and reduce) the number of stock for "Paperback" etc
   	// Use the variables paperbackStock and hardcoverStock at the top. 
   	// For "EBook", set the inherited stockCount variable.

        if(productOptions.equalsIgnoreCase("Paperback")){
             this.paperbackStock--;
        } else if (productOptions.equalsIgnoreCase("hardcover")){
             hardcoverStock--;
        }else if (productOptions.equalsIgnoreCase("Ebook")){
            super.setStockCount(super.getStockCount(productOptions),productOptions);
        }else {
            System.out.println("Error, invalid productOptions");
        }
	}
  /*
   * Print product information in super class and append Book specific information title and author
   */
  public void print()
  {
  	// Replace the line below.
  	// Make use of the super class print() method and append the title and author info. See the video
      System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f Book Title: %-50s", super.getId(), super.getCategory(), super.getName(), super.getPrice(), title);
  }

  //Checks if the Book Title's name is the same, if so then they are equal
    public boolean equals(Object other)
    {
        Book otherP = (Book) other;
        return this.title.equals(otherP.title);
    }

}


