CREATE TABLE IF NOT EXISTS wcub_f
(
    fd text NOT NULL primary key,
    typ text,
    prg text,
    uts text,
    med text,
    nmun text,
    eh1 text,
    eh2 text,
    zul text,
    bmo text,
    pad text,
    pae text,
    ped text,
    pee text,
    gkl text,
    t_rep1 text,
    t_rep2 text,
    t_eck text,
    t_ken text,
    t_lin1 text,
    t_lin2 text,
    t_lin3 text,
    t_lin4 text,
    t_lin5 text,
    fm01 text,
    fm02 text,
    fm03 text,
    fm04 text,
    fm05 text,
    fm06 text,
    fm07 text,
    fm08 text,
    fm09 text,
    fm10 text,
    fm11 text,
    fm12 text,
    fm13 text,
    fm14 text,
    min text,
    max text,
    e text,
    d text,
    gnwt text
); -- WITH OIDS;  Postgres 16: creating a table WITH OIDS is not supported anymore.

CREATE TABLE IF NOT EXISTS wcub_mes
(
    fab text    NOT NULL,
    mes integer NOT NULL,
    num integer NOT NULL,
    dis text    NOT NULL,
    dat timestamp with time zone DEFAULT '1970-01-01 01:00:00'::timestamp without time zone
); -- WITH OIDS;  Postgres 16: creating a table WITH OIDS is not supported anymore.
CREATE INDEX wcub_mes_fab_idx ON wcub_mes USING btree (fab);
CREATE INDEX wcub_mess_dat_idx ON wcub_mes USING btree (dat);

CREATE TABLE IF NOT EXISTS wcub_v
(
    fd   text NOT NULL,
    fab  text NOT NULL,
    gew  text,
    dat  text DEFAULT to_char(now(), 'DD.MM.YYYY'::text),
    pser text,
    pfa  text,
    mfab text
); -- WITH OIDS;  Postgres 16: creating a table WITH OIDS is not supported anymore.
CREATE INDEX wcub_v_fab ON wcub_v USING btree (fab);
CREATE INDEX wcub_v_fd ON wcub_v USING btree (fd);

-- INSERTS
INSERT INTO wcub_f
VALUES ('^(BCA|BCE|PBA)224.*', '220 g', '', '', 'nc', '', 'g', 'mg', '', '', '', '', '', '', '', '1e-04', '', '4e-04',
        '4e-04', '2e-04', '', '', '', '', '%.0f', '%.2f', '%.2f', '%.0f', '%.2f', '%.2f', '%.2f', '%.0f', '', '',
        '%.2f', '%.0f', '%.2f', '%.2f', '', '220 g', '', '0.1 mg', '');

INSERT INTO wcub_v
VALUES ('BCE224I-1S', '45201720', NULL, '02.04.2024', 'wdnull', '0', '0');

INSERT INTO wcub_mes
VALUES ('45201720', 0, 1, '', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 0, '200.00000', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 1, '0.00003', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 8, '10', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 2, 0, '200', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 2, 5, '0.00020', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 3, 0, '200.00000', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 3, 5, '0.00039', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 4, 0, '5', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('45201720', 4, 1, '0.00006', '2024-04-02 14:02:39.533267+02');
