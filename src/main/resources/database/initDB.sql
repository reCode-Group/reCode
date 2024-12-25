CREATE TABLE IF NOT EXISTS users
(
    id       SERIAL PRIMARY KEY,
    name     VARCHAR(200) NOT NULL,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    surname  VARCHAR(255) NOT NULL,
    lastname VARCHAR(50),
    enabled  VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS roles
(
    id        SERIAL PRIMARY KEY,
    role_name VARCHAR(30) NOT NULL
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id INTEGER NOT NULL REFERENCES users (id),
    role_id INTEGER NOT NULL REFERENCES roles (id),
    PRIMARY KEY (user_id, role_id)
);

CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE TABLE IF NOT EXISTS conversions
(
    id          TEXT PRIMARY KEY   DEFAULT encode(gen_random_bytes(6), 'base64'),
    date        TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    user_id     INTEGER   NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    origin_code TEXT      NOT NULL,
    target_code TEXT      NOT NULL
);

CREATE TABLE IF NOT EXISTS reports
(
    id            SERIAL PRIMARY KEY,
    conversion_id TEXT NOT NULL REFERENCES conversions (id) ON DELETE CASCADE,
    message       TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS subscription_types
(
    id                     SERIAL PRIMARY KEY,
    name                   VARCHAR(100)   NOT NULL,
    description            TEXT,
    max_tokens_per_convert INT            NOT NULL,
    price                  DECIMAL(10, 2) NOT NULL,
    duration_in_days       INT            NOT NULL
);

CREATE TABLE IF NOT EXISTS users_subscriptions
(
    id              SERIAL PRIMARY KEY,
    user_id         INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    subscription_id INTEGER NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    expiration_date DATE    NOT NULL
);

INSERT INTO subscription_types (id, name, description, max_tokens_per_convert, price, duration_in_days)
VALUES ('1', 'Базовая', 'Базовая подписка с ограниченными функциями и токенами', 300, 499, 7),
       ('2', 'Стандартная', 'Стандартная подписка с дополнительными токенами и функциями', 600, 799, 14),
       ('3', 'Премиум', 'Премиум подписка с максимальным количеством токенов и всеми функциями', 1000, 1499, 30)
ON CONFLICT (id) DO NOTHING;

INSERT INTO roles (id, role_name)
VALUES (1, 'ROLE_USER'),
       (2, 'ROLE_ADMIN')
ON CONFLICT (id) DO NOTHING;