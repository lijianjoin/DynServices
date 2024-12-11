CREATE SCHEMA routine_components;
GRANT ALL PRIVILEGES ON SCHEMA routine_components to ismartapplication;
ALTER DEFAULT PRIVILEGES FOR USER postgres IN SCHEMA routine_components GRANT ALL ON TABLES TO ismartapplication;