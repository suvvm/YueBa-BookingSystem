package suvvm.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

/**
 * Servlet implementation class SmsServlet
 */
@WebServlet("/SmsServlet")
public class SmsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SmsServlet() {
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
	@SuppressWarnings("deprecation")
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		resp.setHeader("Access-Control-Allow-Origin", "*"); 
		String mobile = req.getParameter("pnum"); 
		String code = req.getParameter("code");
		resp.setContentType("text/html;charset=utf-8");
		PrintWriter out = resp.getWriter();
		DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "", "");
	        IAcsClient client = new DefaultAcsClient(profile);
	        CommonRequest request = new CommonRequest();
	        request.setMethod(MethodType.POST);
	        request.setDomain("dysmsapi.aliyuncs.com");
	        request.setVersion("2017-05-25");
	        request.setAction("SendSms");
	        request.putQueryParameter("RegionId", "cn-hangzhou");
	       // request.putQueryParameter("PhoneNumbers", "17860781150");
	        request.putQueryParameter("PhoneNumbers", mobile);
	        request.putQueryParameter("SignName", "suvvm");
	        request.putQueryParameter("TemplateCode", "SMS_172602858");
	        request.putQueryParameter("TemplateParam", code);
	        try {
	           CommonResponse response = client.getCommonResponse(request);
	           Map map = (Map)JSON.parse(response.getData());
	          if("OK".equals(map.get("Code"))){
	        	  //resp.getWriter().write("发送成功");
	        	  out.print("success");
	          }else {
	        	  out.print("error");
	          }
	        } catch (ServerException e) {
	        	out.print("error");
	            e.printStackTrace();
	        } catch (ClientException e) {
	        	out.print("error");
	            e.printStackTrace();
	        }
	}

}
