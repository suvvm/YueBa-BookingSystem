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
import suvvm.utils.MD5;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
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
		String pnum = request.getParameter("pnum");
		String password = request.getParameter("password");
		String sql = "SELECT * FROM `user` WHERE pnum ='"+pnum+"' && password = '"+MD5.getMd5(password)+"' ";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			if (rSet.next()) {
				JSONObject obj = JSON.parseObject("{id:'" + rSet.getInt("id") + "', shop:'"+rSet.getInt("shop")+"'}");
				out.println(obj);
			}else {
				out.println("error");
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			out.println("error");
		}
	}

}
