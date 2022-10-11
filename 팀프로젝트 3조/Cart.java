
public class Cart {
	int id;
	String user_id;
	String product;
	
	public Cart(String user_id, String product) {
		super();
		this.user_id = user_id;
		this.product = product;
	}
	
	public Cart(int id, String user_id, String product) {
		super();
		this.id = id;
		this.user_id = user_id;
		this.product = product;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	
	
	@Override
	public String toString() {
		return "Cart [id=" + id + ", user_id=" + user_id + ", product=" + product + "]";
	}
	
	
}
