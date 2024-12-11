CREATE USER ismartregister WITH PASSWORD 'register';
CREATE USER ismartapplication WITH PASSWORD 'application';

CREATE SCHEMA workplace_schema;
CREATE SCHEMA product_schema;
CREATE SCHEMA product_type_schema;
CREATE SCHEMA "service-register";
CREATE SCHEMA available_routines;

GRANT ALL PRIVILEGES ON SCHEMA "service-register" to ismartregister;
GRANT ALL PRIVILEGES ON SCHEMA workplace_schema to ismartapplication;
GRANT ALL PRIVILEGES ON SCHEMA product_schema to ismartapplication;
GRANT ALL PRIVILEGES ON SCHEMA product_type_schema to ismartapplication;
GRANT ALL PRIVILEGES ON SCHEMA available_routines to ismartapplication;
GRANT ALL PRIVILEGES ON SCHEMA public to ismartapplication;

ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA public GRANT ALL ON TABLES TO ismartapplication;
ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA workplace_schema GRANT ALL ON TABLES TO ismartapplication;
ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA product_schema GRANT ALL ON TABLES TO ismartapplication;
ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA product_type_schema GRANT ALL ON TABLES TO ismartapplication;
ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA available_routines GRANT ALL ON TABLES TO ismartapplication;

ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA "service-register" GRANT ALL ON TABLES TO ismartregister;
