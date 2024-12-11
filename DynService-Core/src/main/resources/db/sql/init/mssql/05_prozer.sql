CREATE TABLE wcub_f
(
    fd NVARCHAR(255) NOT NULL PRIMARY KEY,
    typ    NVARCHAR(255),
    prg    NVARCHAR(255),
    uts    NVARCHAR(255),
    med    NVARCHAR(255),
    nmun   NVARCHAR(255),
    eh1    NVARCHAR(255),
    eh2    NVARCHAR(255),
    zul    NVARCHAR(255),
    bmo    NVARCHAR(255),
    pad    NVARCHAR(255),
    pae    NVARCHAR(255),
    ped    NVARCHAR(255),
    pee    NVARCHAR(255),
    gkl    NVARCHAR(255),
    t_rep1 NVARCHAR(255),
    t_rep2 NVARCHAR(255),
    t_eck  NVARCHAR(255),
    t_ken  NVARCHAR(255),
    t_lin1 NVARCHAR(255),
    t_lin2 NVARCHAR(255),
    t_lin3 NVARCHAR(255),
    t_lin4 NVARCHAR(255),
    t_lin5 NVARCHAR(255),
    fm01   NVARCHAR(255),
    fm02   NVARCHAR(255),
    fm03   NVARCHAR(255),
    fm04   NVARCHAR(255),
    fm05   NVARCHAR(255),
    fm06   NVARCHAR(255),
    fm07   NVARCHAR(255),
    fm08   NVARCHAR(255),
    fm09   NVARCHAR(255),
    fm10   NVARCHAR(255),
    fm11   NVARCHAR(255),
    fm12   NVARCHAR(255),
    fm13   NVARCHAR(255),
    fm14   NVARCHAR(255),
    min    NVARCHAR(255),
    max    NVARCHAR(255),
    e      NVARCHAR(255),
    d      NVARCHAR(255),
    gnwt   NVARCHAR(255)
);

CREATE TABLE wcub_mes
(
    fab NVARCHAR(255) NOT NULL,
    mes INT           NOT NULL,
    num INT           NOT NULL,
    dis NVARCHAR(255) NOT NULL,
    dat DATETIME DEFAULT '1970-01-01 01:00:00'
);
CREATE INDEX wcub_mes_fab_idx ON wcub_mes (fab);
CREATE INDEX wcub_mes_dat_idx ON wcub_mes (dat);

CREATE TABLE wcub_v
(
    fd   NVARCHAR(255) NOT NULL,
    fab  NVARCHAR(255) NOT NULL,
    gew  NVARCHAR(255),
    dat  NVARCHAR(255) DEFAULT CONVERT(VARCHAR, GETDATE(), 104),
    pser NVARCHAR(255),
    pfa  NVARCHAR(255),
    mfab NVARCHAR(255)
);
CREATE INDEX wcub_v_fab ON wcub_v (fab);
CREATE INDEX wcub_v_fd ON wcub_v (fd);

-- INSERTS
INSERT INTO wcub_f
VALUES ('^(BCA|BCE|PBA)224.*', '220 g', '', '', 'nc', '', 'g', 'mg', '', '', '', '', '', '', '', '1e-04', '', '4e-04',
        '4e-04', '2e-04', '', '', '', '', '%.0f', '%.2f', '%.2f', '%.0f', '%.2f', '%.2f', '%.2f', '%.0f', '', '',
        '%.2f', '%.0f', '%.2f', '%.2f', '', '220 g', '', '0.1 mg', '');

INSERT INTO wcub_v
VALUES ('BCE224I-1S', '45201720', NULL, '2024-04-02', 'wdnull', '0', '0');

INSERT INTO wcub_mes
VALUES ('45201720', 0, 1, '', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 0, '200.00000', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 1, '0.00003', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 1, 8, '10', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 2, 0, '200', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 2, 5, '0.00020', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 3, 0, '200.00000', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 3, 5, '0.00039', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 4, 0, '5', '2024-04-02 14:02:39.533');
INSERT INTO wcub_mes
VALUES ('45201720', 4, 1, '0.00006', '2024-04-02 14:02:39.533');
