/*
 *  class Customer defines a registered customer. It keeps track of the customer's name and address. 
 *  A unique id is generated when when a new customer is created. 
 *  
 *  Implement the Comparable interface and compare two customers based on name
 */
public class Customer implements Comparable<Customer> {
	private String id;
	private String name;
	private String shippingAddress;
	public Cart cart = new Cart();

	public Customer(String id) {
		this.id = id;
		this.name = "";
		this.shippingAddress = "";
	}

	public Customer(String id, String name, String address) {
		this.id = id;
		this.name = name;
		this.shippingAddress = address;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShippingAddress() {
		return shippingAddress;
	}

	public void setShippingAddress(String shippingAddress) {
		this.shippingAddress = shippingAddress;
	}

	public void print() {
		System.out.printf("\nName: %-20s ID: %3s Address: %-35s", name, id, shippingAddress);
	}

	public int compareTo(Customer a) {
		return this.getName().compareTo(a.getName());
		//Interfaces woooo
	}

	public boolean equals(Object other) {
		Customer c = (Customer) other;
		return this.compareTo(c) == 0;
		// If the compareTo function returns 0, then the two strings are equal.
	}
}

