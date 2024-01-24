package control;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import service.CommandProcess;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;
/**
 * Servlet implementation class Controller
 */
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Map<String, Object> commandMap = new HashMap<String, Object>();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
		// web.xml에서 propertyConfig에 해당하는 init-param 의 값을 읽어옴
		String props = config.getInitParameter("config");
		//  /WEB-INF/command.properties
		System.out.println("1, init String props=>"+props);
		
		Properties pr = new Properties();
		FileInputStream f = null;
		try {
			String configFilePath = config.getServletContext().getRealPath(props);
			System.out.println("2. init String configFilePath=>"+ configFilePath);
			f= new FileInputStream(configFilePath);
			pr.load(f);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(f != null)
				try {
					f.close();
				}catch (IOException e2) {}
		}
		
		Iterator keyIter =pr.keySet().iterator();
		
		while (keyIter.hasNext()) {
			String command = (String) keyIter.next();
			String className = pr.getProperty(command);
			System.out.println("3. init command=>" + command);
			System.out.println("4. init className=>" + className);
		
		try {
			Class<?> commandClass = Class.forName(className); 
			CommandProcess commandInstance =
						(CommandProcess)commandClass.getDeclaredConstructor().newInstance();
			commandMap.put(command, commandInstance);
			
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
		requestServletPro(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		requestServletPro(request,response);
	}
	protected void requestServletPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String view = null;
		CommandProcess com = null;
		String command =request.getRequestURI();
		System.out.println("1. requestServletPro command=>"+command);
		command = command.substring(request.getContextPath().length());
		System.out.println("2.requestServletPro command substring=> "+command);
		
		try{
			com = (CommandProcess) commandMap.get(command);
			System.out.println("3.requestServletPro command=>"+ command);
			System.out.println("4.requestServletPro com=>" + com);
			view = com.requestPro(request, response);
			System.out.println("5.requestServletPro view=>"+ view);
		}catch (Exception e) {
			throw new ServletException(e);
		}
		RequestDispatcher dispatcher = request.getRequestDispatcher(view);
		dispatcher.forward(request, response);
	}
}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
