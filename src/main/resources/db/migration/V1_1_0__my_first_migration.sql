create table if not exists fileentity
(
    id          int auto_increment,
    filenumber  varchar(50),
    name        varchar(50),
    date        datetime,
    issue       varchar(50),
    description varchar(4096),
    archive     boolean,
    encrypted   boolean,
    constraint pk_client primary key (id)
);



create table if not exists userentity
(
    id         int primary key auto_increment,
    first_name varchar(25),
    last_name  varchar(25),
    email      varchar(25),
    username   varchar(25),
    password   varchar(255),
    role       varchar(25)
);
#create sequence if not exists user_sequence start with 2 increment by 1;
insert into userentity(id, first_name, last_name, email, username, password, role)
values (1, 'Steve', 'Jobs', 'steve@apple.org', 'admin', '$2a$10$Fq4YQ.XB7LWeblmNglQZ6eWxtUy9nJ74kJS8NUAvD2ovyRvQoCEL.',
        'admin');
insert into userentity(id, first_name, last_name, email, username, password, role)
values (2, 'Bill', 'Gates', 'bill@windows.com', 'edit', '$2a$10$xxcAujAdKNiyMBsU3pREtuRKshGi0lop/WbiybVijQt.Kb/NDI3Ky',
        'edit');
insert into userentity(id, first_name, last_name, email, username, password, role)
values (3, 'Linus', 'Torwalds', 'linux@linux.com', 'read',
        '$2a$10$8LpIdb3lz9LaBHAgd9XyEestihIBA9K9T5OSpU4IF8J1y2lXM9uhW', 'read');

create table if not exists jwtoken
(
    id            int primary key auto_increment,
    token         varchar(1024),
    token_type    varchar(100),
    revoked       boolean,
    expired       boolean,
    userentity_id int
);

create table if not exists uploaddocfiles
(
    id       int primary key auto_increment,
    fileId   int,
    name     varchar(25),
    type     varchar(25),
    filedata mediumblob
);

create table if not exists language
(
    id    int primary key,
    name  varchar(128),
    aktiv bool
);

insert into language(id, name, aktiv)
values (1, 'German', false);
insert into language(id, name, aktiv)
values (2, 'English', true);
insert into language(id, name, aktiv)
values (3, 'Russian', false);
insert into language(id, name, aktiv)
values (4, 'Spanish', false);
insert into language(id, name, aktiv)
values (5, 'French', false);

create table if not exists role
(
    id   int primary key,
    name varchar(128)
);

insert into role(id, name)
values (1, 'admin');
insert into role(id, name)
values (2, 'edit');
insert into role(id, name)
values (3, 'read');