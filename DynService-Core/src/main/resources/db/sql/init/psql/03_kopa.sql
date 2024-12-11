create sequence s_ds_store_id;

alter sequence s_ds_store_id owner to ismartregister;


CREATE TABLE IF NOT EXISTS ds_store_head
(
    id bigint default nextval('s_ds_store_id'::regclass) not null
    primary key,
    "changeHistory" text,
    "checksum" text,
    "dataRecordTypeName" text,
    "deviceDriver" text not null,
    "generationDate" timestamp(6) with time zone default now() not null,
    "name" text,
    "PartNum1Analog" text,
    "PartNum1Applikation" text,
    "PartNum1Digital" text,
    "PartNum1System" text,
    "serialNo" text,
    "serialNoAnalog" text,
    "serialNoApplikation" text,
    "serialNoDigital" text,
    "serialNoSystem" text,
    "userComment" text
);

alter table ds_store_head
    owner to ismartregister;


CREATE TABLE IF NOT EXISTS ds_store_body
(
    "uniqueStructName" text not null,
    "rwDevice" text not null,
    "valueStr" text,
    id bigint not null constraint fk3lfd93wheaommkj1udd3xcity  references ds_store_head on delete cascade,
    primary key (id, "uniqueStructName")
);

alter table ds_store_body
    owner to ismartregister;




