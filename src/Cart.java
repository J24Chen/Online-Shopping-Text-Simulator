import java.util.ArrayList;

public class Cart {

    //All methods need to be public as they are used in different classes.
    public ArrayList<CartItem> cartItems = new ArrayList<CartItem>();

    public Cart(ArrayList cartItems ){ //If there is an existing cart, add it to this cart Object.
        this.cartItems = cartItems;
    }


    public Cart(){

    }

    public void createItem(CartItem a){

        cartItems.add(a);
    } //createItem adds a new cartItem into the cart
}
