--liquibase formatted sql

--changeset create_supply:1
create table supply
(
    id          INT(11)  not null auto_increment primary key,
    barcode     BIGINT   not null,
    quantity    int(11)  not null default 1,
    price       int(11)  not null default 0,
    supply_time datetime not null default current_timestamp,
    INDEX supply_barcode_time_idx (barcode, supply_time)
);