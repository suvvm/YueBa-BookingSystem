package suvvm.inf;
/**
 * 
 * @author suvvm
 *
 */
public class Shop {
	private int id;
	private String space;
	private String workers;
	private String orders;
	private int ownerid;
	/**
	 * 有参构造
	 * @param id		店铺id
	 * @param space		店铺房间索引键
	 * @param workers	店铺员工索引键
	 * @param orders	店铺订单索引键
	 * @param ownerid	店主id
	 */
	public Shop(int id, String space, String workers, String orders, int ownerid) {
		this.id = id;
		this.space = space;
		this.workers = workers;
		this.orders = orders;
		this.ownerid = ownerid;
	}
	/**
	 * 无参构造
	 */
	public Shop() {
		// TODO 自动生成的构造函数存根
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSpace() {
		return space;
	}
	public void setSpace(String space) {
		this.space = space;
	}
	public String getWorkers() {
		return workers;
	}
	public void setWorkers(String workers) {
		this.workers = workers;
	}
	public String getOrders() {
		return orders;
	}
	public void setOrders(String orders) {
		this.orders = orders;
	}
	public int getOwnerid() {
		return ownerid;
	}
	public void setOwnerid(int ownerid) {
		this.ownerid = ownerid;
	}
	
}
