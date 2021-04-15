package de.healthIMIS.iris.client.auth.db;

import java.util.concurrent.TimeUnit;

public class SecurityConstants {

  public static final String SECRET = "SecretKeyToGenJWTs";
  public static final long EXPIRATION_TIME = TimeUnit.MINUTES.toMillis(60);
  public static final String TOKEN_PREFIX = "Bearer ";
  public static final String HEADER_STRING = "Authorization";
}
