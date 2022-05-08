public class CartItem extends Cart {
    //These variables need to be public because we need them in ECommerceSystem
    public Product cip;
    public String ciOptions;

    public CartItem(Product p ,String options ){
        this.cip = p;
        this.ciOptions = options;
    }
    public void print(){
        cip.print(); //Copies the print method from Products, probably more efficient to do something else but this seems lazy easy
    }


}
