package de.healthIMIS.iris.public_server.config;

import lombok.Data;

@Data
public class ProviderConfiguration {
  String id;
  String dataRequestEndpoint;
}
