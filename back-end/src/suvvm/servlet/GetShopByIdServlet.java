package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import suvvm.inf.Shop;
import suvvm.utils.DB;

/**
 * Servlet implementation class GetShopByIdServlet
 */
@WebServlet("/GetShopByIdServlet")
public class GetShopByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetShopByIdServlet() {
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
		response.setHeader("Access-Control-Allow-Origin", "*"); 
		request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=UTF-8");
		PrintWriter out = response.getWriter();
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		String sql = "SELECT * FROM `shop` WHERE id = "+shopid+"";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			if (rSet.next()) {
				JSONObject object = JSON.parseObject(JSON.toJSONString(new Shop(rSet.getInt("id"), rSet.getString("space"), rSet.getString("workers"), rSet.getString("orders"), rSet.getInt("ownerid"))));
				out.print(object);
			}else {
				out.print("error");
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			out.print("error");
		}
	}

}
