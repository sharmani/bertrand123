package bg.util.jee.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// bg.util.jee.web.ServletInit2
public class ServletInit2 extends HttpServlet {

	private static final String HTML_EN_TETE = "<html> <head> <title>bg</title></head> \n<body>";
	private static final String HTML_FIN = "\n</body></html>";

	public ServletInit2() {
		
	}

	@Override
	public void init() throws ServletException {
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String sHtml=HTML_EN_TETE; 
		sHtml=HTML_FIN;
		response.getWriter().println(sHtml);
	}
	
	

}
