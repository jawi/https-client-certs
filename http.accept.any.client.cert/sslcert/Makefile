# Creates various types of certificates, as an example

CONF=openssl.cnf
CA_NAME=root_ca
ISSUED_CERTS_DIR=issued
CERTS_DIR=certs
PRIVKEY_DIR=private
PASSWORD=secret
SUBJECT="/O=L\'Xtreme IT consultancy/CN=$@"

all: clean $(CA_NAME) server client exportcerts keystores

clean:
	@rm -rf $(PRIVKEY_DIR) $(CERTS_DIR) $(ISSUED_CERTS_DIR)
	@mkdir -m 0700 $(PRIVKEY_DIR)
	@mkdir -m 0750 $(ISSUED_CERTS_DIR)
	@mkdir -m 0755 $(CERTS_DIR)
	@touch $(ISSUED_CERTS_DIR)/certindex.txt
	@echo "100001" >$(ISSUED_CERTS_DIR)/serial

$(CA_NAME):
	$(call make_ca_cert,$@)

server:
	$(call make_signed_cert,$@)

client:
	$(call make_self_signed_cert,$@)

exportcerts:
	$(call export_cert_pkcs12,$(CA_NAME))
	$(call export_cert_pkcs12,server)
	$(call export_cert_pkcs12,client)
	
keystores:
	$(call import_cert_java_keystore,$(CA_NAME))
	$(call create_java_keystore,server)

make_ca_cert = \
	@echo "*** Creating $(1) certificate ***" && \
		export NAME=$(1) && \
		openssl req -config $(CONF) -new -x509 -extensions v3_ca -out $(CERTS_DIR)/$(1).pem -passout pass:$(PASSWORD) -subj $(SUBJECT)

make_signed_cert = \
	@echo "*** Creating $(1) certificate ***" && \
		export NAME=$(1) && \
		openssl req -config $(CONF) -extensions $(1) -new -nodes -out $(1)_req.pem -subj $(SUBJECT) && \
		echo "*** Signing $(1) certificate with CA certificate ***" && \
		openssl ca -config $(CONF) -passin pass:$(PASSWORD) -extensions $(1) -batch -notext -out $(CERTS_DIR)/$(1).pem -infiles $(1)_req.pem && \
		rm -f $(1)_req.pem

make_self_signed_cert = \
	@echo "*** Creating self-signed certificate for $(1) ***" && \
		export NAME=$(1) && \
		export CA_NAME=ca_$(1) && \
		openssl req -config $(CONF) -new -x509 -extensions v3_ca -out $(CERTS_DIR)/ca_$(1).pem -keyout $(PRIVKEY_DIR)/ca_$(1).pem -passout pass:$(PASSWORD) -subj $(SUBJECT) && \
		openssl req -config $(CONF) -extensions $(1) -new -nodes -out $(1)_req.pem -subj $(SUBJECT) && \
		openssl ca -config $(CONF) -passin pass:$(PASSWORD) -extensions $(1) -batch -notext -out $(CERTS_DIR)/$(1).pem -infiles $(1)_req.pem && \
		rm -f $(1)_req.pem

export_cert_pkcs12 = \
	@echo "*** Exporting $(1) certificate to PKCS12 format ***" && \
		openssl pkcs12 -export -in $(CERTS_DIR)/$(1).pem -inkey $(PRIVKEY_DIR)/$(1).pem -passin pass:$(PASSWORD) -passout pass:$(PASSWORD) -out $(CERTS_DIR)/$(1).p12 -name "$(1) certificate"

create_java_keystore = \
	@echo "*** Creating Java keystore for $(1) ***" && \
		keytool -importkeystore -srckeystore $(CERTS_DIR)/$(1).p12 -srcstoretype pkcs12 -srcstorepass $(PASSWORD) -destkeystore $(CERTS_DIR)/$(1).jks -deststorepass $(PASSWORD)

import_cert_java_keystore = \
	@echo "*** Importing $(1) certificate into keystore ***" && \
		keytool -importcert -file $(CERTS_DIR)/$(1).pem -keystore $(CERTS_DIR)/$(1).jks -storepass $(PASSWORD) -noprompt -trustcacerts -alias $(1)

# Special targets
.PHONY: clean $(CA_NAME) server client exportcerts keystores

.EXPORT_ALL_VARIABLES:

.ONESHELL:





















