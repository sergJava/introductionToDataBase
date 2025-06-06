-- liquibase formatted sql

-- changeset sfibikh:1
CREATE TABLE IF NOT EXISTS student(
  id SERIAL,
  age INTEGER,
  name TEXT
);

CREATE TABLE IF NOT EXISTS faculty(
  id SERIAL,
  name TEXT,
  color TEXT
)

-- changeset sfibikh:2
CREATE INDEX student_name_index ON student(name)

-- changeset sfibikh:3
CREATE INDEX faculty_name_color_index ON faculty(name, color)

