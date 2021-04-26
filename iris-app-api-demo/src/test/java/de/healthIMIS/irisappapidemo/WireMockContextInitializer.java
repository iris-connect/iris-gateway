package de.healthIMIS.irisappapidemo;

import java.util.Map;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class WireMockContextInitializer 
implements ApplicationContextInitializer<ConfigurableApplicationContext> {

@Override
public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
  WireMockServer wireMockServer = new WireMockServer(new WireMockConfiguration().port(8080));
  wireMockServer.start();

  configurableApplicationContext
    .getBeanFactory()
    .registerSingleton("wireMockServer", wireMockServer);

  configurableApplicationContext.addApplicationListener(applicationEvent -> {
    if (applicationEvent instanceof ContextClosedEvent) {
      wireMockServer.stop();
    }
  });
  
  TestPropertyValues
    .of(Map.of("iris.public-api.apihost", "http://localhost:" + wireMockServer.port()))
    .applyTo(configurableApplicationContext);
}
}
