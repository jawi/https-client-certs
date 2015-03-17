package http.client.cert;

import java.util.Properties;

import javax.servlet.Servlet;

import org.apache.felix.dm.DependencyActivatorBase;
import org.apache.felix.dm.DependencyManager;
import org.apache.felix.http.jetty.ConnectorFactory;
import org.osgi.framework.BundleContext;

public class Activator extends DependencyActivatorBase {
	public static final int ALT_HTTPS_PORT = 8443;
	
	@Override
	public void destroy(BundleContext context, DependencyManager dm) throws Exception {
		// Nop
	}

	@Override
	public void init(BundleContext context, DependencyManager dm) throws Exception {
		dm.add(createComponent()
				.setInterface(ConnectorFactory.class.getName(), null)
				.setImplementation(MySslConnectorFactory.class));

		Properties props = new Properties();
		props.put("alias", "/");
		
		dm.add(createComponent() //
				.setInterface(Servlet.class.getName(), props)
				.setImplementation(MyServlet.class));
	}
}
