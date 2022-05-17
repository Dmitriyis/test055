--liquibase formatted sql

--changeset test:1
--precondition-sql-check expectedResult:0 select count(*) from information_schema.tables where TABLE_NAME = 'ARRAY_TEST'
CREATE TABLE IF NOT EXISTS array_test
(
    id             int auto_increment primary key,
    name           varchar(64) NOT NULL UNIQUE,
    element_Result varchar,
    type_task      varchar(10)
);