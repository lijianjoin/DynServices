-- -- Create a login for the user
-- CREATE LOGIN ismartregister WITH PASSWORD = 'register@123';
-- GO
-- CREATE LOGIN ismartapplication WITH PASSWORD = 'application@123';  
-- GO

-- -- Use the ismart2 database
-- USE ismart2;
-- GO

-- -- Create a user for the login in the database
-- CREATE USER ismartregister FOR LOGIN ismartregister;
-- GO
-- CREATE USER ismartapplication FOR LOGIN ismartapplication;
-- GO

-- -- Grant all privileges on the database to the user
-- ALTER ROLE db_owner ADD MEMBER ismartregister;
-- GO
-- ALTER ROLE db_owner ADD MEMBER ismartapplication;
-- GO

-- Create schemas
-- CREATE SCHEMA [public];
-- GO
CREATE SCHEMA workplace_schema;
GO
CREATE SCHEMA product_schema;
GO
CREATE SCHEMA product_type_schema;
GO
CREATE SCHEMA [service-register];
GO
CREATE SCHEMA available_routines;
GO


-- -- Grant all privileges on the schemas to the user
-- GRANT CONTROL ON SCHEMA::workplace_schema TO ismartapplication;
-- GO
-- GRANT CONTROL ON SCHEMA::product_schema TO ismartapplication;
-- GO
-- GRANT CONTROL ON SCHEMA::product_type_schema TO ismartapplication;
-- GO
-- GRANT CONTROL ON SCHEMA::[service-register] TO ismartregister;
-- GO
-- -- GRANT CONTROL ON SCHEMA::[public] TO ismartapplication;
-- -- GO