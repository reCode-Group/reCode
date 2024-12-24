create table if not exists users
(
    id         serial primary key,
    name       varchar(200) not null,
    email      varchar(255) not null unique,
    password   varchar(255) not null,
    surname    varchar(255)  not null,
    lastname   varchar(50),
	enabled	   varchar(50)
);


create table if not exists roles
(
    id        serial primary key,
    role_name varchar(30) not null
);


create table if not exists users_roles
(
    user_id integer  not null references users(id),
    role_id integer not null references roles(id),
    primary key (user_id, role_id)
);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

create table if not exists conversions
(
    id TEXT PRIMARY KEY DEFAULT encode(gen_random_bytes(6), 'base64'),
    date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id integer not null references users(id) on delete cascade,
    origin_code TEXT NOT NULL,
    target_code TEXT NOT NULL
);
create table if not exists reports
(
    id        serial primary key,
    conversion_id TEXT  not null references conversions(id) ON DELETE CASCADE,
    message TEXT NOT NULL
);

INSERT INTO roles (id, role_name)
VALUES
    (1, 'ROLE_USER'),
    (2, 'ROLE_ADMIN')
ON CONFLICT (id) DO NOTHING;