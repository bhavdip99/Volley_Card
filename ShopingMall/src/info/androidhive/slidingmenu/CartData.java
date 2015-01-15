package info.androidhive.slidingmenu;

public class CartData {

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
	public int getImage() {
		return image;
	}
	public void setImage(int image) {
		this.image = image;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public CartData(String name, String detail, int image, int price) {
		super();
		this.name = name;
		this.detail = detail;
		this.image = image;
		this.price = price;
	}
	String name, detail;
	int image, price;

}
