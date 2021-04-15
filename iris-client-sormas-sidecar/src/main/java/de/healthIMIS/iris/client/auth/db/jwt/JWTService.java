package de.healthIMIS.iris.client.auth.db.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

@AllArgsConstructor
@Component
public class JWTService implements JWTVerifier, JWTSigner {

  private RSAPrivateKey privateKey;

  private RSAPublicKey publicKey;

  @Override
  public String sign(Builder builder) {
    return builder.sign(Algorithm.RSA256(publicKey, privateKey));
  }

  @Override
  public DecodedJWT verify(String jwt) {
    return JWT.require(Algorithm.RSA256(publicKey, privateKey)).build().verify(jwt);
  }
}
