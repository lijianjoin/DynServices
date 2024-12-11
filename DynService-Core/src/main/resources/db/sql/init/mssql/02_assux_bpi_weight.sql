CREATE TABLE assux_bpi_weight (
    name NVARCHAR(255) NOT NULL PRIMARY KEY,
    weight FLOAT,
    gueltig_bis DATETIMEOFFSET NOT NULL DEFAULT SYSDATETIMEOFFSET(),
    anforderung INT NOT NULL DEFAULT 99,
    counter INT DEFAULT 0,
    ed DATETIME,
    ken INT
);

-- alter table assux_bpi_weight
--     owner to ismartregister;
