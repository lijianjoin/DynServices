-- dialect: PostgreSQL

INSERT INTO product_type_schema.product_type (id, name, version)
VALUES ('eab8d770-7901-40ba-afda-09c50c1b4cf7', '1000076330', '1.0.0');
INSERT INTO product_type_schema.process_template (id, product_type_id, name, version, manufacturing_site)
VALUES ('ac51bfd6-813b-4c0d-a079-2c02a58b9008', 'eab8d770-7901-40ba-afda-09c50c1b4cf7', 'SYSTEM_STANDARD_BALANCE',
        '0.0.1', 'BEIJING');
INSERT INTO product_type_schema.process_step_template (id, process_template_id, name, sequence)
VALUES ('2295ae60-30dd-4aff-8a09-8ddf15565221', 'ac51bfd6-813b-4c0d-a079-2c02a58b9008', 'SYSTEM_INITIATION', 1);
INSERT INTO product_type_schema.process_step_routine_template (id, process_step_template_id, sequence,
                                                               workplace_type_id, name, routine_service_name, version)
VALUES ('e5805e0f-8375-4861-8dfb-6935e30af840', '2295ae60-30dd-4aff-8a09-8ddf15565221', 1,
        '214bd598-affe-4137-ae06-8e9794df4539', 'FAT_GENERATION_AND_PRINT', 'FatRoutine', '0.0.1');
INSERT INTO product_type_schema.parameters_template (id, product_type_id, name, description, [values])
VALUES ('4cff1600-8e34-4cc0-9491-da734260f4c3', 'eab8d770-7901-40ba-afda-09c50c1b4cf7',
        'ToBeDefined_producTypeParamName',
        'Parameter for productTypes. To be discussed if one Parameter per productType is needed or if there are more general forms.',
        '{"type": "ToBeDefined", "value": ""}');
INSERT INTO product_type_schema.parameters_template (id, process_template_id, name, description, [values])
VALUES ('81649eff-8b80-4b13-afb7-59ed264be1d9', 'ac51bfd6-813b-4c0d-a079-2c02a58b9008', 'SYSTEM_DEVICE',
        'Parameter for processTemplates. To be discussed if one Parameter per productType is needed or if there are more general forms.',
        '{"type": "ToBeDefined", "value": ""}');
INSERT INTO product_type_schema.parameters_template (id, process_step_template_id, name, description, [values])
VALUES ('ad221f7a-8713-4236-b82b-eb6c6cb03a73', '2295ae60-30dd-4aff-8a09-8ddf15565221', 'StepParameterStandard',
        'Parameter for processStep Templates. To be discussed if one Parameter per productType is needed or if there are more general forms.',
        '{"type": "StepParameterStandard", "value": {"workplaceTag": "MockedWorkplaceTag", "maxRetries": 3}}');
INSERT INTO product_type_schema.parameters_template (id, process_step_routine_template_id, name, description, [values])
VALUES ('5d2a8e11-2f78-4d39-a411-df6c3670cea8', 'e5805e0f-8375-4861-8dfb-6935e30af840',
        'RoutineParameterFatGenerationAndPrint',
        'Parameter for processStepRoutine Templates. Name relates to the class used for deserializing value part of the JSON String',
        '{"type": "RoutineParameterFatGenerationAndPrint", "value": {"@type": "RoutineParameterFatGenerationAndPrint", "maxRunCycles": 3}}');

INSERT INTO wcub_v
VALUES ('BCE224I-1S', '44708230', NULL, '02.04.2024', 'wdnull', '0', '0');

INSERT INTO wcub_mes
VALUES ('44708230', 0, 1, '', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 1, 0, '200.00000', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 1, 1, '0.00003', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 1, 8, '10', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 2, 0, '200', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 2, 5, '0.00020', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 3, 0, '200.00000', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 3, 5, '0.00039', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 4, 0, '5', '2024-04-02 14:02:39.533267+02');
INSERT INTO wcub_mes
VALUES ('44708230', 4, 1, '0.00006', '2024-04-02 14:02:39.533267+02');
