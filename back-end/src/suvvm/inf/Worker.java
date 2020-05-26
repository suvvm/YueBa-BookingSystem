package suvvm.inf;
/**
 * 
 * @author suvvm
 *
 */
public class Worker {
	private int id;
	private String name;
	private String tel;
	/**
	 * 有参构造
	 * @param id	
	 * @param name
	 * @param tel
	 */
	public Worker(int id, String name, String tel) {
		this.id = id;
		this.name = name;
		this.tel = tel;
	}
	/**
	 * 无参构造
	 */
	public Worker() {
		// TODO 自动生成的构造函数存根
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
	public String getTel() {
		return tel;
	}
	public void setTel(String tel) {
		this.tel = tel;
	}
	
}	
