-- Create sequence
CREATE SEQUENCE s_ds_store_id
    START WITH 1
    INCREMENT BY 1;

-- Transfer sequence to the specified schema
--ALTER SCHEMA ismartregister TRANSFER dbo.s_ds_store_id;

-- Create table ds_store_head
CREATE TABLE ds_store_head
(
    id BIGINT NOT NULL PRIMARY KEY DEFAULT NEXT VALUE FOR s_ds_store_id,
    changeHistory NVARCHAR(MAX),
    checksum NVARCHAR(MAX),
    dataRecordTypeName NVARCHAR(MAX),
    deviceDriver NVARCHAR(MAX) NOT NULL,
    generationDate DATETIMEOFFSET(6) NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    name NVARCHAR(MAX),
    PartNum1Analog NVARCHAR(MAX),
    PartNum1Applikation NVARCHAR(MAX),
    PartNum1Digital NVARCHAR(MAX),
    PartNum1System NVARCHAR(MAX),
    serialNo NVARCHAR(MAX),
    serialNoAnalog NVARCHAR(MAX),
    serialNoApplikation NVARCHAR(MAX),
    serialNoDigital NVARCHAR(MAX),
    serialNoSystem NVARCHAR(MAX),
    userComment NVARCHAR(MAX)
);

-- Transfer table to the specified schema
--ALTER SCHEMA ismartregister TRANSFER dbo.ds_store_head;

-- Create table ds_store_body
CREATE TABLE ds_store_body
(
    uniqueStructName NVARCHAR(255) NOT NULL,
    rwDevice NVARCHAR(MAX) NOT NULL,
    valueStr NVARCHAR(MAX),
    id BIGINT NOT NULL,
    CONSTRAINT fk3lfd93wheaommkj1udd3xcity FOREIGN KEY (id) REFERENCES ds_store_head(id) ON DELETE CASCADE,
    PRIMARY KEY (id, uniqueStructName)
);



