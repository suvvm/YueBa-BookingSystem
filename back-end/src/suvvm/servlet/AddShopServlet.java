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

import suvvm.utils.DB;

/**
 * Servlet implementation class AddShopServlet
 */
@WebServlet("/AddShopServlet")
public class AddShopServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddShopServlet() {
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
		int ownerid = Integer.parseInt(request.getParameter("ownerid"));
		String sql = "INSERT INTO `shop` VALUES (null, null, null, null, " + ownerid + ")";
		int res = DB.executeUpdate(sql);
		if(res > 0) {
			String sql2 = "SELECT * FROM `shop` WHERE ownerid = "+ownerid+""; 
			try {
				ResultSet rSet = DB.executeQuery(sql2);
				if(rSet.next()) {
					int shopid = rSet.getInt("id");
					String sql3 = "UPDATE `shop` SET space='space_"+shopid+"', workers='workers_"+shopid+"', orders='order_"+shopid+"'  WHERE ownerid="+ownerid+"";
					DB.executeUpdate(sql3);
					String sql4 = "create table space_"+shopid+"(" + 
									"id int auto_increment primary key," + 
									"name nvarchar(20)," + 
									"worker int" + 
								   ")auto_increment=30000001 default charset=utf8;";
					DB.executeUpdate(sql4);
					String sql5 = "create table workers_"+shopid+"("+
										"id int,"+
										"isManager bool"+
									")default charset=utf8;";
					DB.executeUpdate(sql5);
					String sql6 = "create table order_"+shopid+"(" +
							"id int auto_increment primary key," +
							"cname nvarchar(20)," +
							"pnum nvarchar(11)," +
							"space int," +
							"bgtime datetime," +
							"edtime datetime" +
							")auto_increment=40000001 default charset=utf8 default charset=utf8;";
					DB.executeUpdate(sql6);
					String sql7 = "UPDATE `user` SET shop = "+shopid+" WHERE id = "+ownerid+"";
					DB.executeUpdate(sql7);
					JSONObject object = JSON.parseObject("{shopid:'"+shopid+"'}");
					out.println(object);
				}else {
					out.println("error");
				}
			} catch (Exception e) {
				// TODO 自动生成的 catch 块
				out.println("error");
				e.printStackTrace();
			}
		}else {
			out.println("error");
		}
	}

}
