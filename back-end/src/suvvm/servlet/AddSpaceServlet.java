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
 * Servlet implementation class AddSpaceServlet
 */
@WebServlet("/AddSpaceServlet")
public class AddSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddSpaceServlet() {
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
		String name = request.getParameter("name");
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		int worker = Integer.parseInt(request.getParameter("worker"));
		String sql2 = "";
		if (worker != 0) {
			sql2 = "INSERT INTO `space_"+shopid+"` VALUES (null,'"+name+"',"+worker+")";
		} else {
			sql2 = "INSERT INTO `space_"+shopid+"` VALUES (null,'"+name+"',null)";
		}
		int result = DB.executeUpdate(sql2);
		if(result > 0) {
			out.print("success");
		}else {
			out.print("error");
		}
		
	}

}
