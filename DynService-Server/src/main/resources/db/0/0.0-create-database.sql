CREATE USER dynserviceregister with PASSWORD 'dynregister';

CREATE SCHEMA dyn_service_register;

GRANT ALL PRIVILEGES ON SCHEMA dyn_service_register to dynserviceregister;

ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA dyn_service_register GRANT ALL ON TABLES TO dynserviceregister

