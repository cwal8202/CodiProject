
public class Tag_Theme {
	String codi;
	int number;
	
	public Tag_Theme(String codi) {
		super();
		this.codi = codi;
	}

	public Tag_Theme(String codi, int number) {
		super();
		this.codi = codi;
		this.number = number;
	}

	public String getCodi() {
		return codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {
		return "Tag_Theme [codi=" + codi + ", number=" + number + "]";
	}
	
}
