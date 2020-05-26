package suvvm.inf;
/**
 * 
 * @author suvvm
 *
 */
public class Customer {
	private String cname;
	private String pnum;
	/**
	 * 
	 * @param cname 客户姓名
	 * @param pnum	客户手机
	 */
	public Customer(String cname, String pnum) {
		this.cname = cname;
		this.pnum = pnum;
	}
	/**
	 * 无参构造
	 */
	public Customer() {
		// TODO 自动生成的构造函数存根
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
}
