create table PERSON
(
    id BIGINT primary key identity,
    firstName	varchar(80) not null,
    lastName varchar(80) not null,
    email varchar(256) not null
 );
ALTER TABLE PERSON ALTER COLUMN id RESTART WITH 1;
ALTER TABLE PERSON ADD CONSTRAINT unique_email UNIQUE (email);