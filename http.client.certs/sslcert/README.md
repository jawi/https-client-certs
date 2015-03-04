# Example certificates

This directory can be used to create an example (root) certificate authority (CA), and use this CA to create a signed
server and client certificate. Everything is preconfigured in such way that only a single `make all` is needed to get
all certificates generated.

The generated output is:

- `certs/*.pem`: the public certificates;
- `certs/*.jks`: the Java keystores (for use with Jetty, password is "secret");
- `certs/*.p12`: the certificates in PKCS12 format (password is "secret");
- `private/*.pem`: the private keys (not password protected);
- `issued/*`: the issued certificates (not needed in normal use).

To make use of the certifactes, you need a X509 key pair from *both* the `certs` and `private` directory. For example,
`certs/server.pem` and `private/server.pem` provide you a valid key pair for usage in your application.

