package http.client.cert;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.cert.X509Certificate;

public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	static final String CERT_KEY = "javax.servlet.request.X509Certificate";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String pathInfo = req.getPathInfo();
		if (pathInfo == null || "".equals(pathInfo)) {
			pathInfo = "/";
		}

		X509Certificate[] cert = (X509Certificate[]) req.getAttribute(CERT_KEY);
		String creds = req.getHeader("Authorization");

		PrintWriter writer = resp.getWriter();

		boolean notAuthorized = (cert == null || cert.length < 1)
				&& (creds == null);
		if (notAuthorized) {
			if ("/login".equals(pathInfo)) {
				resp.addHeader("WWW-Authenticate", "Basic realm=\"My Realm\"");
				resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} else {
				String host1 = String.format("%s:%d", req.getServerName(), req.getServerPort());
				String host2 = String.format("%s:%d", req.getServerName(), Activator.ALT_HTTPS_PORT);

				writer.printf("<html><body><h1>Please login</h1><p>"
					+ "Click <a href='//%s/login'>here</a> to login using username/password.<br/>"
					+ "Click <a href='//%s/'>here</a> to login using your client certificate."
					+ "</p></body></html>", host1, host2);
			}
		} else {
			writer.println("<html><body><p>");
			if (cert != null) {
				writer.printf("Client cert name: %s", cert[0].getSubjectDN().getName());
			} else {
				writer.printf("Client credentials: %s", creds);
			}
			writer.println("</p></body></html>");
		}

		resp.flushBuffer();
	}
}
