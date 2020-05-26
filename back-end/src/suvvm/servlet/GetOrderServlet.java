package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import suvvm.inf.Order;
import suvvm.utils.DB;

/**
 * Servlet implementation class GetOrderServlet
 */
@WebServlet("/GetOrderServlet")
public class GetOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetOrderServlet() {
        super();
        // TODO Auto-generated constructor stub
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
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setHeader("Access-Control-Allow-Origin", "*"); 
        response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		String sql = "SELECT * FROM `order_"+shopid+"`";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			List<Order> list = new ArrayList<Order>();
			while (rSet.next()) {
				list.add(new Order(rSet.getInt("id"), rSet.getString("cname"), rSet.getString("pnum"), rSet.getInt("space"), rSet.getString("bgtime"), rSet.getString("edtime")));
			}
			JSONArray obj = JSON.parseArray(JSON.toJSONString(list));
	        out.print(obj);
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			out.print("error");
		}
	}

}
