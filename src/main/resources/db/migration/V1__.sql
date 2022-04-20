CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START WITH 1 INCREMENT BY 1;

CREATE TABLE role
(
    id   BIGINT NOT NULL,
    name VARCHAR(255),
    CONSTRAINT pk_role PRIMARY KEY (id)
);

create sequence user_service.role_id_seq;

alter table user_service.role alter column id set default nextval('user_service.role_id_seq'::regclass);

alter sequence user_service.role_id_seq owned by user_service.role.id;

CREATE TABLE users
(
    id      BIGINT NOT NULL,
    name    VARCHAR(255),
    role_id BIGINT,
    CONSTRAINT pk_users PRIMARY KEY (id)
);

create sequence user_service.users_id_seq;

alter table user_service.users alter column id set default nextval('user_service.users_id_seq'::regclass);

alter sequence user_service.users_id_seq owned by user_service.users.id;


ALTER TABLE users
    ADD CONSTRAINT FK_USERS_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

INSERT INTO user_service.role (id, name)
VALUES (DEFAULT, 'ADMIN'::varchar(255));

INSERT INTO user_service.role (id, name)
VALUES (DEFAULT, 'USER'::varchar(255));

INSERT INTO user_service.users (id, name, role_id)
VALUES (DEFAULT, 'mira_soft_1@mail.ru'::varchar(255), 1::bigint);

INSERT INTO user_service.users (id, name, role_id)
VALUES (DEFAULT, 'mira_soft_2@mail.ru'::varchar(255), 2::bigint);


