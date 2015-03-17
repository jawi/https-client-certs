package http.client.cert;

import java.io.FileInputStream;
import java.security.KeyStore;

import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;

import org.apache.felix.http.jetty.ConnectorFactory;
import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class MySslConnectorFactory implements ConnectorFactory {

	@Override
	public Connector createConnector(Server server) {
		// TODO expose this factory as ManagedService as well in order to make
		// it configurable...
		SslContextFactory sslContextFactory = new SslContextFactory();
		sslContextFactory.setWantClientAuth(true);

		try (FileInputStream fis = new FileInputStream("sslcert/certs/server.jks")) {
			char[] pass = "secret".toCharArray();

			KeyStore store = KeyStore.getInstance(KeyStore.getDefaultType());
			store.load(fis, pass);

			KeyManagerFactory factory = KeyManagerFactory.getInstance(SslContextFactory.DEFAULT_KEYMANAGERFACTORY_ALGORITHM);
			factory.init(store, pass);

			SSLContext context = SSLContext.getInstance("TLS");
			context.init(factory.getKeyManagers(), SslContextFactory.TRUST_ALL_CERTS, null);
			sslContextFactory.setSslContext(context);
		} catch (Exception e) {
			e.printStackTrace(); // TODO
		}

		ServerConnector connector = new ServerConnector(server, sslContextFactory);
		connector.setPort(Activator.ALT_HTTPS_PORT);

		return connector;
	}

}
