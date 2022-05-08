import java.util.Locale;

public class Shoes extends Product{
    private int size;
    private int colour;


    public Shoes(String name, String id, double price, int stock[] ){
        super(name,id,price, 999 ,Category.SHOES); // the stock of the entire shoe object doesn't matter
        //products.add(new Shoes("Jordans", generateProductId(), 149.99, new int[]{2, 3, 4, 56, 7, 8, 9, 10, 3, 4}));
        stockManage.stockCount = stock; //sets the stockCount to the parameter of Shoes
    }

    /*
    TO manage all the stocks efficiently, a class stockManage is created that contains two arrays
    which corresponds to the stockSize and the stockName.
    Main purpose is to allow class Shoes to work similarly to Book e.g allowing getStock with a string param
    and working properly
     */

    class stockManage {
        //Creates two arrays that contain the stockCount and the name of the specified region
        //These two Arrays are alligned with each other, where StockName Black7 and its stockCount are on the same index

        static int[] stockCount = new int[10];
        static String[] stockName = { "black6","black7","black8","black9","black10","brown6","brown7","brown8","brown9","brown10"};
        //We want these two arrays to be static, don't create multiple instances of these arrays in one object that'd be horrible.

        //These methods find the stockCount or stockNumber based on the index given.
        public static int getStockNum(int siz){
            return stockCount[siz];
        }
        public String getStockName(int siz){
            return stockName[siz];
        }


        public static int getIndex(String a){ //Gets the index of the specified name in the stockName 2d array
           //Static is required in order to prevent errors when calling
            for (int i = 0; i < stockName.length; i++){
                    if (stockName[i].equalsIgnoreCase(a))
                        return i; //returns the index that corresponds to the name inside the array stockName,
                }
//            System.out.println("Index of String " + a + " not found!");
            return -1; // This should really be throwing an error that an index isn't found but I'm lazy.
            //Realistically this return method is never reached as a shoes class is never called with the gatechecking of ValidOptions
        }

        public void setStockCount (int siz, int num){
            stockCount[siz] = num;
        }


    }
    //Will be following the book code structure
    public boolean  validOptions(String productOptions){
        productOptions = productOptions.toLowerCase(); //set the productOptions to lower case\
        //Make a switch case that checks if it fits any of these strings. Returns true if any occurs
        // else returns false
        switch(productOptions){
            case "black6":
            case "black7":
            case "black8":
            case "black9":
            case "black10":
            case "brown6":
            case "brown7":
            case "brown8":
            case "brown9":
            case "brown10":
                return true;
            default:
                return false;

        }
    }
    public int getStockCount(String productOptions){
        if(validOptions(productOptions)) {
            int ind = stockManage.getIndex(productOptions); //Gets the index that matches the stock name
            return stockManage.getStockNum(ind);
        }
        return 0;
    }



    public void reduceStockCount(String productOptions){
        if(validOptions(productOptions)){
            int ind = stockManage.getIndex(productOptions); //Gets the index that matches the stock name
//            System.out.println("stockCount of " + stockManage.stockName[ind] + " is before " + stockManage.stockCount[ind]);
            stockManage.stockCount[ind]--;
//            System.out.println("stockCount is now " + stockManage.stockCount[ind]);
        }
    }

    /*
    Sets the StockCount given a string, uses the stockManage class
     */
    public void setStockCount(String productOptions){
        if(validOptions(productOptions)){
            int ind = stockManage.getIndex(productOptions);
            stockManage.stockCount[ind]--;
        }
    }

public void print(){
    System.out.printf("\nId: %-5s Category: %-9s Name: %-20s Price: %7.1f ", super.getId(), super.getCategory(), super.getName(), super.getPrice());
}
















}
