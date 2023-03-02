DROP TABLE IF EXISTS "public"."users";
-- This script only contains the table creation statements and does not fully represent the table in the database. It's still missing: indices, triggers. Do not use it as a backup.

-- Sequence and defined type
DROP SEQUENCE IF EXISTS users_id_seq;
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

INSERT INTO users (name, email, password, role, address, created_date)
VALUES ('admin', 'admin@localhost', 'admin', 2, 'admin', '2020-01-01 00:00:00'),
       ('richard', 'richard@gmail.com', 'richard', 1, 'richard`s address', '2020-01-01 00:00:00'),
       ('james', 'james@gmail.com', 'james', 1, 'james`s address', '2020-01-01 00:00:00'),
       ('jane', 'jane@gmail.com', 'jane', 1, 'jane`s address', '2020-01-01 00:00:00'),
       ('john', 'john@gmail.com', 'john', 1, 'john`s address', '2020-01-01 00:00:00'),
       ('Michael', 'michael@yahoo.com', 'michael', 1, 'michael`s address', '2020-01-01 00:00:00'),
       ('Maria Marionette', 'mariring@ninisani.co.au', 'mariring', 1, 'mariring`s address', '2020-01-01 00:00:00'),
       ('Rosemi Lovelock', 'rosebuds@ninisani.co.jp', 'rosebuds', 1, 'rosebuds`s address', '2020-01-01 00:00:00'),
       ('Nina Kosaka', 'kosaka.nina@ninisani.co.ru', 'mommynina', 1, 'mommynina`s address', '2020-01-01 00:00:00'),
       ('Enna Alouette', 'binches.babu@ninisani.co.ph', 'aloupeeps', 1, 'aloupeeps`s address', '2020-01-01 00:00:00'),
       ('Millie Parfait', 'gagu.babu@ninisani.co.jp', 'gagubabu', 1, 'gagubabu`s address', '2020-01-01 00:00:00'),
       ('Elira Pendora', 'hello.pogi@ninisani.co.ca', 'hellopogi', 1, 'hellopogi`s address', '2020-01-01 00:00:00');
-- count = 12
