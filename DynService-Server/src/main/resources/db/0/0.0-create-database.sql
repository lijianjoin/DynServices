CREATE USER serviceregister with PASSWORD 'register';

CREATE SCHEMA service_register;

GRANT ALL PRIVILEGES ON SCHEMA service_register to serviceregister;

ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA service_register GRANT ALL ON TABLES TO serviceregister

