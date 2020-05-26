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
import suvvm.utils.FVerifyUtil;

/**
 * Servlet implementation class FVerifyServlet
 */
@WebServlet("/FVerifyServlet")
public class FVerifyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FVerifyServlet() {
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
		int type = 1;
		String content_1 = request.getParameter("content_1");
		
		String url = "https://dtplus-cn-shanghai.data.aliyuncs.com/face/verify";
		String ak_id = "";
		String ak_secret = "";
		
		String sql = "SELECT * FROM `user` WHERE pnum = '"+pnum+"'";
		try {
			ResultSet rSet = DB.executeQuery(sql);
			if(rSet.next()) {
				String content_2 = rSet.getString("img");
				System.out.println(content_2);
				String body = "{\"type\":\""+type+"\",\"content_1\":\""+content_1+"\",\"content_2\":\""+content_2+"\"}";
				System.out.println(body);
				try {
					String res = FVerifyUtil.sendPost(url, body, ak_id, ak_secret);
					out.print(res);
				} catch (Exception e) {
					// TODO 自动生成的 catch 块					
					out.print(body);
					e.printStackTrace();
				}
			}
		} catch (Exception e1) {
			out.print("error");
			// TODO 自动生成的 catch 块
			e1.printStackTrace();
		}
	}

}
