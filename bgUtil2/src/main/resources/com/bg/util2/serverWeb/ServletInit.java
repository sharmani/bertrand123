package com.bg.util2.serverWeb;

// com.bg.util2.serverWeb.ServletInit
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bg.util2.HostName;
import com.bg.util2.UtilCopy;
import com.bg.util2.logger.LoggerFactoryBg;
import com.bg.util2.spring.UtilSpring;

public class ServletInit extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final Object ACTION_GET_LOG = "getLog";
	private static final Object ACTION_GET_LOG_INFO = "getLogInfo";

	private static ServletInit instance;

	public ServletInit() {
		super();
		instance = this;

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
		String r = "<h2>" + HostName.hostname() + "</h2>";
		String action = "" + request.getParameter("action");
		String name = request.getParameter("name");
		String iStr = request.getParameter("i");
		if (action.equals(ACTION_GET_LOG)) {
			r += " <br/>Get Log No Implemented Yet  name:" + name + "  i: " + iStr;
			copyLogFile(name, iStr, response);
			return;
		} else if (action.equals(ACTION_GET_LOG_INFO)){
				r += getLogInfo(name); 
		} else {
			r += " <br/>" + this.getMenuLogHtml();
		}
		r = "<html><head> <title>init " + HostName.hostname() + " </title></head> <body>" + r + "</body></html>";
		response.getWriter().write(r);
	}

	private String getLogInfo(String name) {
		String r ="";
		r +="<h2>LogInfos "+name+" </h2>";
		File f = LoggerFactoryBg.getFile(name);
		r += "file: "+f.getAbsolutePath()+" exists: "+f.exists()+" length:"+f.length()+" ";
		return r;
	}

	private void copyLogFile(String name, String iStr, HttpServletResponse response) {
		int i = 0;
		if (iStr == null) {
		} else if (iStr.trim().length() == 0) {
		} else {
			try {
				i = Integer.parseInt(iStr.trim());
			} catch (NumberFormatException e) {
			}
		}
		copyLogFile(name, i, response);
	}

	private void copyLogFile(String name, int i, HttpServletResponse response) {
		try {
			response.setContentType("application/rtf");			
			File f = LoggerFactoryBg.getFile(name, i);;
			OutputStream out = response.getOutputStream();
			UtilCopy.copy(f, out);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private String getMenuLogHtml() {
		String r = "<h2> Logs </h2>";
		r += "\n<table border='1'>";
		HashMap<Logger, String> hLoggers = LoggerFactoryBg.getHLoggers();
		Collection<String> collectNames = hLoggers.values();
		TreeSet<String> ts = new TreeSet<String>(collectNames);
		for (String name : ts) {
			r += "\n<tr>";			
			r += "<td>" + getLinkFileLog(name, 0, name) + "</td><td>" + getLinkFileLog(name, 1, "old") + " " + getLinkFileLog(name, 2, ".")+"</td><td>" +getLinkInfoLog( name ,"i")+ "</td>";
			r += "</tr>";
		}
		r += "\n</table>";
		return r;
	}

	private String getLinkFileLog(String name, int i, String label) {
		String link = "<a href='" + this.getServletName() + "?action=" + ACTION_GET_LOG + "&name=" + name + "&i=" + i + "'>" + label + "</a>";
		return link;
	}
	private String getLinkInfoLog(String name ,String label) {
		String link = "<a href='" + this.getServletName() + "?action=" + ACTION_GET_LOG_INFO + "&name=" + name + "'>" + label + "</a>";
		return link;
	}

	public File getWEB_INF() {
		String pathDefault = "WEB-INF";
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
			System.out.println("realPathWebInf ----------------------------" + realPathWebInf);
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
		UtilSpring.getInstance().initSpringConfigFromNameRootThread(webInf, "spring", true);
	}

	public static ServletInit getInstance() {
		return instance;
	}

}
