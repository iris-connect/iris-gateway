package de.healthIMIS.iris.client.auth.db.jwt;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.security.KeyStore;
import java.security.KeyStore.PasswordProtection;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(
        value="security.auth",
        havingValue = "db"
)
public class KeyProvider {

  @NonNull
  private JwtConfiguration jwtConfiguration;

  private KeyStore.PrivateKeyEntry privateKeyEntry;

  @PostConstruct
  private void init() throws Exception {
    var ksFile = new ClassPathResource(jwtConfiguration.getKeystorePath());
    KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
    keystore.load(ksFile.getInputStream(), jwtConfiguration.getKeystorePassword().toCharArray());
    privateKeyEntry = (KeyStore.PrivateKeyEntry) keystore
        .getEntry(
            jwtConfiguration.getKeyAlias(),
            new PasswordProtection(jwtConfiguration.getPrivateKeyPassword().toCharArray()));
  }

  @Bean
  public RSAPrivateKey rsaPrivateKey() {
    return (RSAPrivateKey) privateKeyEntry.getPrivateKey();
  }

  @Bean
  public RSAPublicKey rsaPublicKey() {
    return (RSAPublicKey) privateKeyEntry.getCertificate().getPublicKey();
  }


}
