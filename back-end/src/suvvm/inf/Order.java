package suvvm.inf;
/**
 * 
 * @author suvvm
 *
 */
public class Order {
	private int id;
	private String cname;
	private String pnum;
	private int space;
	private String bgtime;
	private String edtime;
	/**
	 * 有参构造
	 * @param id	订单号
	 * @param cname	客户姓名
	 * @param pnum	客户手机
	 * @param space	预定位置
	 * @param bgtime开始时间
	 * @param edtime结束时间
	 */
	public Order(int id, String cname, String pnum, int space, String bgtime, String edtime) {
		this.id = id;
		this.cname = cname;
		this.pnum = pnum;
		this.space = space;
		this.bgtime = bgtime;
		this.edtime = edtime;
	}
	/**
	 * 无参构造
	 */
	public Order() {
		// TODO 自动生成的构造函数存根
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCname() {
		return cname;
	}
	public void setCname(String cname) {
		this.cname = cname;
	}
	public String getPnum() {
		return pnum;
	}
	public void setPnum(String pnum) {
		this.pnum = pnum;
	}
	public int getSpace() {
		return space;
	}
	public void setSpace(int space) {
		this.space = space;
	}
	public String getBgtime() {
		return bgtime;
	}
	public void setBgtime(String bgtime) {
		this.bgtime = bgtime;
	}
	public String getEdtime() {
		return edtime;
	}
	public void setEdtime(String edtime) {
		this.edtime = edtime;
	}
	
}
