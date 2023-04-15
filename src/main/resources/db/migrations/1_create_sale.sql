--liquibase formatted sql

--changeset create_sale:1
create table umag_hacknu.sale
(
    id        INT(11)  not null auto_increment primary key,
    barcode   BIGINT   not null,
    quantity  int(11)  not null default 1,
    price     int(11)  not null default 0,
    sale_time datetime not null default current_timestamp,
    INDEX sale_barcode_time_idx (barcode, sale_time)
);
