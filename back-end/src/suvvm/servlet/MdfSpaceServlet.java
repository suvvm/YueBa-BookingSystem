package suvvm.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import suvvm.utils.DB;

/**
 * Servlet implementation class MdfSpaceServlet
 */
@WebServlet("/MdfSpaceServlet")
public class MdfSpaceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MdfSpaceServlet() {
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
		String name = request.getParameter("name");
		int shopid = Integer.parseInt(request.getParameter("shopid"));
		int id = Integer.parseInt(request.getParameter("id"));
		int worker = Integer.parseInt(request.getParameter("worker"));
		
		String sql = "UPDATE `space_"+shopid+"` SET name='"+name+"', worker="+worker+" WHERE id="+id+"";
		DB.executeUpdate(sql);
	}

}
