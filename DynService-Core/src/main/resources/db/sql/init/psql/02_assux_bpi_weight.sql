CREATE TABLE IF NOT EXISTS assux_bpi_weight (
    name VARCHAR(255) NOT NULL PRIMARY KEY,
    weight DOUBLE PRECISION,
    gueltig_bis TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
                              anforderung INT NOT NULL DEFAULT 99,
                              counter INT DEFAULT 0,
                              ed TIMESTAMP,
                              ken INT
                              );

alter table assux_bpi_weight
    owner to ismartregister;
