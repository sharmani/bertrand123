package com.bg.util2.web;
// com.bg.util2.web.ServletInit
import java.io.File;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bg.util2.HostName;
import com.bg.util2.spring.UtilSpring;

public class ServletInit extends HttpServlet{

	private static ServletInit instance;
	public ServletInit() {
		super();
		instance=this;
		
	}
	

	@Override
	public void init() throws ServletException {
		super.init();
		this.initSpring();
	}


	


	
	public void destroy() {
		super.destroy();
		UtilSpring.getInstance().destroySingletons();
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String r="<h2>"+HostName.hostname()+"</h2>";
		r ="<html><head> <title>init"+HostName.hostname()+" </title></head> <body>"+r+"</body></html>";
		response.getWriter().write(r);
	}
	
	
	
	public File getWEB_INF() {
		String pathDefault = "WEB-INF" ;
		try {
			ServletContext servletContext = this.getServletContext();
			String realPathWebInf = servletContext.getRealPath("/WEB-INF");
			if (realPathWebInf == null) {
				System.out.println("! servletContext.getRealPath is null !");
				return new File(pathDefault);
			}
			File file = new File(realPathWebInf);
			file = file.getAbsoluteFile();
			return file;
		} catch (Throwable e) {
			
			return new File(pathDefault);
		}
	}
	
	/**
	 * @param fileName
	 * @return
	 */
	public File getFileInWEB_INF(String fileName) {
		String pathDefault = "WEB-INF" + File.separator + fileName;
		try {
			ServletContext servletContext = this.getServletContext();
			String realPathWebInf = servletContext.getRealPath("/WEB-INF/" + fileName);
			System.out.println("realPathWebInf ----------------------------"+realPathWebInf);
			if (realPathWebInf == null) {
				System.out.println("! servletContext.getRealPath is null !");
				return new File(pathDefault);
			}
			File file = new File(realPathWebInf);
			return file;
		} catch (Throwable e) {
			return new File(pathDefault);
		}
	}
	
	private void initSpring() {
		File webInf = getWEB_INF();
		UtilSpring.getInstance().initSpringConfigFromNameRootThread(webInf,"spring",true);
	}
	
	

}
