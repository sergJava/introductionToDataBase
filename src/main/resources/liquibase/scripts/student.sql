-- liquibase formatted sql

-- changeset sfibikh:1
CREATE TABLE student(
  id SERIAL,
  age INTEGER,
  name TEXT
)