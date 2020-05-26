
package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suvvm.utils.DB;

/**
 * Servlet 实现类 AddOrderServlet
 * @author LENOVO 
 */
@WebServlet("/AddOrderServlet")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddOrderServlet() {
        super();
        // TODO 自动生成的构造函数存根
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO 接收订单信息 添加订单
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 	//跨域
        response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		String cname = request.getParameter("cname");
		String pnum = request.getParameter("pnum");
		int space = Integer.parseInt(request.getParameter("space"));
		String bgtime = request.getParameter("bgtime");
		String edtime = request.getParameter("edtime");
		String sql = "INSERT INTO `order_"+shopid+"` VALUES(null, '"+cname+"', '"+pnum+"', '"+space+"', '"+bgtime+"', '"+edtime+"')";
		if(DB.executeUpdate(sql) > 0) {	//受影响行数大于0添加成功
			out.print("success");
		}else {
			out.print("error");
		}
		
	}

}
