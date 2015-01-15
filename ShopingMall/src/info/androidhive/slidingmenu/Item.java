package info.androidhive.slidingmenu;


public class Item {
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public Item(String name, String price, String details, int image) {
		super();
		this.name = name;
		this.price = price;
		this.details = details;
		this.image = image;
	}
	String name, price, details;
	int image;
	
	
}
