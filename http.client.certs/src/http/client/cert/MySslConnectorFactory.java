package http.client.cert;

import org.apache.felix.http.jetty.ConnectorFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class MySslConnectorFactory implements ConnectorFactory {

	@Override
	public Connector createConnector(Server server) {
		SslContextFactory sslContextFactory = new SslContextFactory();
		// TODO expose this factory as ManagedService as well in order to make it configurable...
		sslContextFactory.setKeyStorePath("sslcert/certs/server.jks");
		sslContextFactory.setKeyStorePassword("secret");
		sslContextFactory.setTrustStorePath("sslcert/certs/root_ca.jks");
		sslContextFactory.setTrustStorePassword("secret");
		sslContextFactory.setNeedClientAuth(true);

		ServerConnector connector = new ServerConnector(server, sslContextFactory);
		connector.setPort(Activator.ALT_HTTPS_PORT);

		return connector;
	}

}
