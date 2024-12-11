-- SYSTEM INITIALIZATION MOCKS START
-- Needed for mocked workplace below - remove if the mocked workplace is removed
INSERT INTO product_schema.product (id, serial_number, product_type_id, last_executed_process_step_id,
                                    last_executed_process_step_routine_id)
VALUES ('6c0a0cdb-51b4-4239-90d8-f0574ba02649', '0000522966', '85c0aeb8-9773-4e98-a792-326e901ad170', null, null);

-- Needed as long as the workplace recognition of a balance is not implemented to create a productWorkplace
INSERT INTO workplace_schema.workplace_type (id, name)
VALUES ('550e8400-e29b-41d4-a716-446655440027', 'SQL_Mocked_Workplace');

-- Needed as long as there are no master data for workplaces
INSERT INTO workplace_schema.tag (id, name)
VALUES ('550e8400-e29b-41d4-a716-446655440028', 'MockedWorkplaceTag');

-- Needed as long as there are no master data for workplaces
INSERT INTO workplace_schema.workplace (id, type_id, tag_id, uri)
VALUES ('550e8400-e29b-41d4-a716-446655440026', '550e8400-e29b-41d4-a716-446655440027',
        '550e8400-e29b-41d4-a716-446655440028', 'MockedWorkplaceA');

-- Needed as long as the workplace recognition of a balance is not implemented to create a productWorkplace
INSERT INTO product_schema.product_workplace (id, product_id, workplace_id, connection_timestamp, active)
VALUES ('56da67b2-bc3d-40ed-b277-c7c2fbd16ec5', '6c0a0cdb-51b4-4239-90d8-f0574ba02649',
        '550e8400-e29b-41d4-a716-446655440026', '2023-01-01T00:00:00', 'true');

-- weightMockData needed until a workplace master data is set up
INSERT INTO workplace_schema.weight_set (id, workplace_id, gwi)
VALUES ('550e8400-e29b-41d4-a716-446655440029', '550e8400-e29b-41d4-a716-446655440026', 'GWI_A');

-- weightMockData needed until a workplace master data is set up
INSERT INTO workplace_schema.weight (id, weight_set_id, nominal_value, deviation_value, mark, form,
                                     measurement_accuracy)
VALUES ('550e8400-e29b-41d4-a716-446655440030', '550e8400-e29b-41d4-a716-446655440029', 100.0, 0.1, 1.0, 'FormA', 0.01);
-- SYSTEM INITIALIZATION MOCKS END