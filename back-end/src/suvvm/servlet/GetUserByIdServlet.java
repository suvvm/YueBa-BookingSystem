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
 * Servlet implementation class GetUserById
 */
@WebServlet("/GetUserByIdServlet")
public class GetUserByIdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetUserByIdServlet() {
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
		String id = request.getParameter("id");
		String sql = "SELECT * FROM `user` WHERE id = "+id+"";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			if(rSet.next()) {
				JSONObject obj = JSON.parseObject("{id:'" + rSet.getInt("id") + "', name:'"+rSet.getString("name")+"',pnum:'"+rSet.getString("pnum")+"',shop:'"+rSet.getInt("shop")+"'}");
				out.println(obj);
			}else {
				out.println("error");
			}
		} catch (Exception e) {
			e.printStackTrace();
			out.println("error");
		}
	}

}
