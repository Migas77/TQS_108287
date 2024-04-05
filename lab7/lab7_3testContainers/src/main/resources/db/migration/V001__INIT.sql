CREATE TABLE employees (
    id BIGSERIAL PRIMARY KEY ,
    name varchar(255) not null,
    email varchar(255) not null
);

INSERT INTO employees (name, email)
VALUES
    ('João', 'joao@gmail.com'),
    ('Diogo', 'diogo@gmail.com'),
    ('Rafa', 'rafa@gmail.com');