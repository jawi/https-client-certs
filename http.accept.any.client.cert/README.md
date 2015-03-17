# http.accept.any.client.certs

Small PoC showing how you can configure Felix Jetty to accept any client certificate.

## Usage:

1. ensure that the certificates are generated:
   a. `cd sslcert && make`;
   b. import the `sslcert/certs/root_ca.{pem|p12}` in your keystore/browser to ensure the client & server certificate can be verified;
   c. import the `sslcert/certs/client.p12` in order to have a client certificate present that can be used from the browser;
2. add the following two entries to your `hosts` file:
    127.0.0.1 server.localhost
    127.0.0.1 client.localhost
3. in Eclipse, run the `local.bndrun` file.
 
You should be able to access the default page on `https://server.localhost:8443` without
the prompt for a client certificate but with a basic HTTP authentication prompt (fill
anything you want) and when accessing the URL `https://server.localhost:8444`, your
browser should prompt you for a client certificate.
