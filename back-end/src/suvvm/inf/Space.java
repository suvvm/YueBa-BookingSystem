package suvvm.inf;
/**
 * 
 * @author suvvm
 *
 */
public class Space {
	private int id;
	private String name;
	private int worker;
	/**
	 * 有参构造
	 * @param id	房间id
	 * @param name	房间名
	 * @param worker服务人员id
	 */
	public Space(int id, String name, int worker) {
		this.id = id;
		this.name = name;
		this.worker = worker;
	}
	/**
	 * 无参构造
	 */
	public Space() {
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
	public int getWorker() {
		return worker;
	}
	public void setWorker(int worker) {
		this.worker = worker;
	}
}
