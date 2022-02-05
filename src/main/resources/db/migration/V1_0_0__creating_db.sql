    drop table if exists drivers;

    drop table if exists roles;

    drop table if exists trips;

    drop table if exists users;

    drop table if exists users_roles;

    create table drivers (
       id bigint not null auto_increment,
        car_make varchar(255) not null,
        car_number varchar(255) not null,
        first_name varchar(255) not null,
        phone varchar(255) not null,
        second_name varchar(255) not null,
        surname varchar(255) not null,
        primary key (id)
    );

    create table roles (
       id bigint not null auto_increment,
        role varchar(255),
        primary key (id)
    );

    create table trips (
       id bigint not null auto_increment,
        latitude double precision not null,
        longitude double precision not null,
        deleted bit,
        deleted_at bit,
        finished date,
        ordered_at date,
        started date,
        driver_id bigint,
        user_id bigint,
        primary key (id)
    );

    create table users (
       id bigint not null auto_increment,
        city varchar(200),
        first_name varchar(150),
        second_name varchar(150),
        password varchar(500),
        phone varchar(17),
        surname varchar(150),
        primary key (id)
    );

    create table users_roles (
       user_id bigint not null,
        role_id bigint not null,
        primary key (user_id, role_id)
    );

    alter table users
       add constraint UK_du5v5sr43g5bfnji4vb8hg5s3 unique (phone);

    alter table trips
       add constraint FKaq8aob3mymsvmh1fkko7k9ql9
       foreign key (driver_id)
       references drivers (id);

    alter table trips
       add constraint FK8wb14dx6ed0bpp3planbay88u
       foreign key (user_id)
       references users (id);

    alter table users_roles
       add constraint FKj6m8fwv7oqv74fcehir1a9ffy
       foreign key (role_id)
       references roles (id);

    alter table users_roles
       add constraint FK2o0jvgh89lemvvo17cbqvdxaa
       foreign key (user_id)
       references users (id);