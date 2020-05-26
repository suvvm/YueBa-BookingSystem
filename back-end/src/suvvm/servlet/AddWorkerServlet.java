package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suvvm.utils.DB;

/**
 * Servlet implementation class AddWorkerServlet
 */
@WebServlet("/AddWorkerServlet")
public class AddWorkerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddWorkerServlet() {
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
		int pnum = Integer.parseInt(request.getParameter("pnum"));
		try {
			String sql = "SELECT * FROM `user` WHERE pnum="+pnum+"";
			ResultSet rSet = DB.executeQuery(sql);
			if(rSet.equals(null)) {
				out.print("nouser");
			}else {
				int uid = rSet.getInt("id");
				String sql2 = "INSERT INTO `workers_"+shopid+"` VALUES ("+uid+",0)";
				DB.executeUpdate(sql2);
				out.print("success");
			}
		} catch (Exception e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
			out.print("error");
		}
	}

}
