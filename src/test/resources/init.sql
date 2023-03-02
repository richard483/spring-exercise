DROP TABLE IF EXISTS "public"."users";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
-- DROP  SEQUENCE IF EXISTS users_id_seq;
-- untuk ngulang ID tiap kali di run
CREATE SEQUENCE IF NOT EXISTS users_id_seq;

-- Table Definition
CREATE TABLE "public"."users"
(
    "id"           int4 NOT NULL DEFAULT nextval('users_id_seq'::regclass),
    "address"      varchar(255),
    "created_date" timestamptz,
    "email"        varchar(255),
    "name"         varchar(255),
    "password"     varchar(255),
    "role"         int2,
    PRIMARY KEY ("id")
);
