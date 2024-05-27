create table if not exists person
(
    id          int auto_increment,
    vorname     varchar(50),
    name        varchar(50),
    email       varchar(50),
    birthsday   date,
    cellphone   varchar(50),
    address_id  numeric,
    description varchar(4096),

    constraint pk_client primary key (id)
);

#create sequence if not exists person_sequence start with 1 increment by 1;

create table if not exists photoimage
(
    id        int primary key auto_increment,
    fileId    numeric,
    name      varchar(25),
    type      varchar(25),
    imagedata mediumblob
);

#create sequence if not exists photoimage_sequence start with 1 increment by 1;

create table if not exists address
(
    id         int primary key auto_increment,
    plz        int,
    country    varchar(50),
    region     varchar(50),
    ort        varchar(50),
    strasse    varchar(50),
    hausnummer numeric
);
#create sequence if not exists address_sequence start with 4 increment by 1;

create table if not exists userentity
(
    id       int primary key auto_increment,
    username varchar(25),
    password varchar(255),
    role     varchar(25)
);
#create sequence if not exists user_sequence start with 2 increment by 1;
insert into userentity(id, username, password, role)
values (1, 'admin', '$2a$10$Fq4YQ.XB7LWeblmNglQZ6eWxtUy9nJ74kJS8NUAvD2ovyRvQoCEL.', 'admin');
insert into userentity(id, username, password, role)
values (2, 'edit', '$2a$10$xxcAujAdKNiyMBsU3pREtuRKshGi0lop/WbiybVijQt.Kb/NDI3Ky', 'edit');
insert into userentity(id, username, password, role)
values (3, 'read', '$2a$10$8LpIdb3lz9LaBHAgd9XyEestihIBA9K9T5OSpU4IF8J1y2lXM9uhW', 'read');

create table if not exists jwtoken
(
    id            int primary key auto_increment,
    token         varchar(1024),
    token_type    varchar(100),
    revoked       boolean,
    expired       boolean,
    userentity_id int
);
#create sequence if not exists jwtoken_sequence start with 1 increment by 1;

create table if not exists certificate
(
    id   int primary key auto_increment,
    name varchar(128)
);
#create sequence if not exists certificate_sequence start with 5 increment by 1;
insert into certificate(id, name)
values (1, 'Oracle Certified Professional (OCP)');
insert into certificate(id, name)
values (2, 'Oracle Certified Associate (OCA)');
insert into certificate(id, name)
values (3, 'Oracle Certified Master (OCM)');
insert into certificate(id, name)
values (4, 'Oracle Certified Expert (OCE)');

create table if not exists education
(
    id   int primary key auto_increment,
    name varchar(128)
);
#create sequence if not exists education_sequence start with 4 increment by 1;
insert into education(id, name)
values (1, 'Technischer Assistent Elektrotechnik');
insert into education(id, name)
values (2, 'Dipl.Ing. (FH)');
insert into education(id, name)
values (3, 'Dipl.Ing. (Univ)');

create table if not exists expertise
(
    id   int primary key auto_increment,
    name varchar(128)
);
#create sequence if not exists expertise_sequence start with 4 increment by 1;
insert into expertise(id, name)
VALUES (1, 'Java Experte');
insert into expertise(id, name)
VALUES (2, 'C++ Erweiterte Kenntnisse');
insert into expertise(id, name)
VALUES (3, 'C Grundkenntnisse');

create table if not exists position
(
    id   int primary key auto_increment,
    name varchar(128)
);

#create sequence if not exists position_sequence start with 6 increment by 1;
insert into `position`(id, name)
VALUES (1, 'Leitender Angestellte');
insert into `position`(id, name)
VALUES (2, 'Angestellte');
insert into `position`(id, name)
values (3, 'Studentische Hilfskraft');
insert into `position`(id, name)
values (4, 'Praktikant');
insert into `position`(id, name)
values (5, 'Auszubilderder');


create table if not exists uploaddocfiles
(
    id       int primary key auto_increment,
    personId int,
    name     varchar(25),
    type     varchar(25),
    filedata mediumblob
);
#create sequence if not exists uploaddocfiles_sequence start with 1 increment by 1;

create table if not exists profession
(
    id   int primary key auto_increment,
    name varchar(128)
);
#create sequence if not exists profession_sequence start with 5 increment by 1;
insert into profession(id, name)
values (1, 'Frontend Entwickler Java/Web');
insert into profession(id, name)
values (2, 'Backend Entwickler Java/Spring Framework');
insert into profession(id, name)
values (3, 'Fullstack Entwickler Web');
insert into profession(id, name)
values (4, 'Web Entwickler Angular/TypeScript');

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