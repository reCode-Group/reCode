create table if not exists users
(
    id         serial primary key,
    name       varchar(200) not null,
    email      varchar(254) not null unique,
    password   varchar(254) not null,
    surname    varchar(50)  not null,
    lastname varchar(50)
);


create table if not exists roles
(
    id        serial primary key,
    role_name varchar(30) not null
);


create table if not exists users_roles
(
    user_id integer not null,
    role_id integer not null,
    primary key (user_id, role_id)
);

insert into roles values
                      (1, 'ROLE_USER'),
                      (2, 'ROLE_ADMIN');
