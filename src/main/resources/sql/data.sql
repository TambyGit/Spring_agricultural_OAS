CREATE
DATABASE agricultural_db;
CREATE
USER agricultural_user WITH PASSWORD '123456';
GRANT ALL PRIVILEGES ON DATABASE
agricultural_db TO agricultural_user;
GRANT ALL PRIVILEGES ON SCHEMA
PUBLIC TO agricultural_user;