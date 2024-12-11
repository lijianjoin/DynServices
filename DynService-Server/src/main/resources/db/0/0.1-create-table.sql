
CREATE TABLE "service_register"."dynservice_provider"(
      id                   SERIAL PRIMARY KEY,
      uri                  VARCHAR(255) NOT NULL,
      groupid              VARCHAR(255) NOT NULL,
      resourceid           VARCHAR(255) NOT NULL,
      serviceid            VARCHAR(255) NOT NULL,
      resource_version     VARCHAR(255) NOT NULL,
      service_version      VARCHAR(255) NOT NULL,
      uuid                 VARCHAR(255) NOT NULL,
      additional_info      VARCHAR(255) NOT NULL,
      detection_interval   INTEGER NOT NULL,
      service_name         VARCHAR(255) NOT NULL,
      service_location     VARCHAR(255),
      last_check           TIMESTAMP
);