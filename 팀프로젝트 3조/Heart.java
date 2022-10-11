
public class Heart {
	public int id;
	public String user_id;
	public int CodiSet_id;
	public String record_time;
	
	public Heart( int codiSet_id) {
		CodiSet_id = codiSet_id;
	}
	
	
	
	public Heart(int id, String user_id, int codiSet_id) {
		super();
		this.id = id;
		this.user_id = user_id;
		CodiSet_id = codiSet_id;
	}
	
	public Heart(int id, String user_id, int codiSet_id, String record_time) {
		super();
		this.id = id;
		this.user_id = user_id;
		CodiSet_id = codiSet_id;
		this.record_time = record_time;
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
	public int getCodiSet_id() {
		return CodiSet_id;
	}
	public void setCodiSet_id(int codiSet_id) {
		CodiSet_id = codiSet_id;
	}
	public String getRecord_time() {
		return record_time;
	}
	public void setRecord_time(String record_time) {
		this.record_time = record_time;
	}
	
	@Override
	public String toString() {
		return "Heart [id=" + id + ", user_id=" + user_id + ", CodiSet_id=" + CodiSet_id + "]";
	}
	
	
	
	
}
