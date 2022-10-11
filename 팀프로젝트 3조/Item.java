import java.sql.Blob;

// name, size, color, category, sub_category, image, season 값
public class Item {
	private int id;
	private String name;
	private String size;
	private String color;
	private String category;
	private String subCategory;
	private Blob imageUrl;
	private String season;
	
	
	public Item(int id, Blob imageUrl) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
	}

	public Item(int id, String name, String category, Blob imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.imageUrl = imageUrl;
	}

	
	
	public Item(int id, String name, String category, String subCategory, Blob imageUrl) {
		super();
		this.id = id;
		this.name = name;
		this.category = category;
		this.subCategory = subCategory;
		this.imageUrl = imageUrl;
	}

	public Item(int id, String name, String size, String color, String category, String subCategory, Blob imageUrl,
			String season) {
		super();
		this.id = id;
		this.name = name;
		this.size = size;
		this.color = color;
		this.category = category;
		this.subCategory = subCategory;
		this.imageUrl = imageUrl;
		this.season = season;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getSubCategory() {
		return subCategory;
	}
	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}
	public Blob getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(Blob imageUrl) {
		this.imageUrl = imageUrl;
	}
	public String getSeason() {
		return season;
	}
	public void setSeason(String season) {
		this.season = season;
	}
	@Override
	public String toString() {
		return "상품번호: " + id + " 이름: " + name + ", 사이즈: " + size + ", 색상: " + color + ", 분류: " + subCategory + ", season: " + season;
	}
	
	
}
