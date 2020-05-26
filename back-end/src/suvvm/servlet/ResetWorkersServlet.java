package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import suvvm.utils.DB;

/**
 * Servlet implementation class ResetWorkersServlet
 */
@WebServlet("/ResetWorkersServlet")
public class ResetWorkersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetWorkersServlet() {
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
		String spacelist = request.getParameter("list");
		//System.out.println(spacelist);
		PrintWriter out = response.getWriter();
		JSONArray jsonArray = JSONArray.parseArray(spacelist);
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		String clrSql = "SELECT * FROM `workers_"+shopid+"`";
		try {
			ResultSet cltrSet = DB.executeQuery(clrSql);
			while(cltrSet.next()) {
				String clrSql2 = "UPDATE `user` SET shop = null WHERE id = "+cltrSet.getInt("id")+"";
				DB.executeUpdate(clrSql2);
			}
			String sql = "truncate table `workers_"+shopid+"`";
			DB.executeUpdate(sql);
			boolean err = false;
			for(int i = 0; i < jsonArray.size(); i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String sql2 = "SELECT * FROM `user` WHERE pnum = "+obj.getString("tel")+"";
				try {
					ResultSet rSet = DB.executeQuery(sql2);
					if(rSet.next()) {
						String sql3 = "INSERT INTO `workers_"+shopid+"` VALUES ("+ rSet.getInt("id") +",0)";
						DB.executeUpdate(sql3);
						String sql4 = "UPDATE `user` SET shop="+shopid+" WHERE id = "+rSet.getInt("id")+"";
						DB.executeUpdate(sql4);
					}else {
						err = true;
						break;
					}
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					err = true;
					e.printStackTrace();
					break;
				}
			}
			if (!err) {
				out.print("success");
			} else {
				out.print("error");
			}
		} catch (Exception e1) {
			// TODO 自动生成的 catch 块
			out.print("error");
			e1.printStackTrace();
		}	
	}
}
