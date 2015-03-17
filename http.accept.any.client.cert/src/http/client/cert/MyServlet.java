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
		X509Certificate[] cert = (X509Certificate[]) req.getAttribute(CERT_KEY);

		PrintWriter writer = resp.getWriter();

		boolean notAuthorized = (cert == null || cert.length < 1);
		if (notAuthorized) {
			writer.append("<html><body><h1>No client certificate provided!</h1>"
					+ "<p>There seems to be no client certificate provided by your browser!</p></body></html>");
		} else {
			writer.println("<html><body><p>");
			if (cert != null) {
				writer.printf("Client cert name: %s", cert[0].getSubjectDN()
						.getName());
			}
			writer.println("</p></body></html>");
		}

		resp.flushBuffer();
	}
}
