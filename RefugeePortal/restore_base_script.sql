CREATE USER core WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
ALTER USER core PASSWORD 'core';
CREATE USER dbadmin WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
ALTER USER dbadmin PASSWORD 'dbadmin';
CREATE USER health WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
ALTER USER health PASSWORD 'health';
CREATE USER node WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
ALTER USER node PASSWORD 'node';
CREATE USER personal WITH LOGIN SUPERUSER INHERIT CREATEDB CREATEROLE NOREPLICATION;
ALTER USER personal PASSWORD 'personal';
CREATE DATABASE health WITH ENCODING = 'UTF8';
CREATE DATABASE core WITH ENCODING = 'UTF8';
CREATE DATABASE personal WITH ENCODING = 'UTF8';
CREATE DATABASE portal WITH ENCODING = 'UTF8';