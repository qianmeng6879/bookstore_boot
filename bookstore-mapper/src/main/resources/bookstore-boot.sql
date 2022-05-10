drop database if exists bookstore_boot;

create database bookstore_boot character set utf8;
use bookstore_boot;


create table t_user
(
    id          bigint(20) auto_increment,
    username    varchar(12)  not null,
    password    varchar(128) not null,
    nickname    varchar(12)  not null,
    email       varchar(64)  null,
    mobile      varchar(11)  null,
    sex         varchar(1) default '男',
    picture     varchar(255) null,
    create_time datetime     not null,
    update_time datetime     null,
    is_deleted  tinyint(1) default 0,
    primary key (id),
    unique key (username)
);

create table auth_role
(
    id   int auto_increment,
    name varchar(50) not null,
    primary key (id),
    unique key (name)
);

create table auth_permission
(
    id          int auto_increment,
    name        varchar(50)  not null,
    description varchar(128) null,
    primary key (id),
    unique key (name)
);

create table auth_role_permission
(
    id            int auto_increment,
    role_id       int not null,
    permission_id int not null,
    primary key (id),
    unique key (role_id, permission_id)
);

create table auth_user_role
(
    id      int auto_increment,
    user_id bigint(20) not null,
    role_id int        not null,
    primary key (id),
    unique key (user_id, role_id)
);

create table t_book
(
    id           bigint(20) auto_increment,
    title        varchar(50)  not null,
    isbn         char(13)     not null,
    price        decimal(10, 2) default 0.00,
    picture      varchar(255) null,
    author       varchar(50)  not null,
    publisher    varchar(50)  null,
    publish_date date         null,
    description  text         null,
    category_id  int          null,
    create_time  datetime     not null,
    update_time  datetime     null,
    primary key (id)
);

create table t_category
(
    id        int auto_increment,
    name      varchar(30) not null,
    parent_id int         null,
    primary key (id),
    unique key (name)
);

create table t_address
(
    id            bigint(20) auto_increment,
    user_id       bigint(20)   not null,
    province      varchar(20)  not null,
    city          varchar(20)  not null,
    district      varchar(20)  not null,
    detail        varchar(255) not null,
    signer_name   varchar(20)  not null,
    signer_mobile varchar(11)  not null,
    create_time   datetime     not null,
    update_time   datetime     null,
    primary key (id)
);

create table t_order
(
    id            bigint(20) auto_increment,
    user_id       bigint         not null,
    book_id       bigint         not null,
    book_title    varchar(50)    not null,
    book_isbn     char(13)       not null,
    book_picture  varchar(255)   null,
    book_price    decimal(10, 2) not null,
    number        int            not null,
    price         decimal(10, 2) not null,
    pay_type      varchar(10)    not null,
    state         tinyint(1)     not null default 1,
    province      varchar(20)    not null,
    city          varchar(20)    not null,
    district      varchar(20)    not null,
    detail        varchar(255)   not null,
    signer_name   varchar(20)    not null,
    signer_mobile varchar(11)    not null,
    description   varchar(255)   null,
    create_time   datetime       not null,
    update_time   datetime       null,
    primary key (id)
);

CREATE TABLE t_oplog
(
    id         int(11) NOT NULL AUTO_INCREMENT,
    user_id    int(11)      DEFAULT NULL,
    ip         varchar(100) DEFAULT NULL,
    reason     varchar(600) DEFAULT NULL,
    createTime datetime     DEFAULT NULL,
    PRIMARY KEY (id)
);

create table t_cart
(
    id          bigint(20) auto_increment,
    book_id     bigint(20) not null,
    user_id     bigint(20) not null,
    count       tinyint(1) unsigned default 1,
    create_time datetime   not null,
    primary key (id),
    unique key (user_id, book_id)
);

create table t_comment
(
    id          bigint(20) auto_increment,
    content     varchar(255)  not null,
    user_id     bigint(20)    not null,
    book_id     bigint(20)    not null,
    order_id    bigint(20)    not null,
    create_time datetime      not null,
    score       decimal(2, 1) not null,
    primary key (id),
    unique key (user_id, order_id)
);


insert into t_user(id, username, password, email, create_time, nickname)
values (1, 'zero', '594940807a9d53f817d0d8647add05c7', 'qianmeng6879@163.com', now(), 'zero');

insert into t_user(id, username, password, email, create_time, nickname)
values (2, 'lisi', '594940807a9d53f817d0d8647add05c7', 'lisi@163.com', now(), 'lisi');

insert into t_user(id, username, password, email, create_time, nickname)
values (3, 'wangwu', '594940807a9d53f817d0d8647add05c7', 'wangwu@163.com', now(), 'wangwu');



insert into auth_role(id, name)
values (1, 'ADMIN');
insert into auth_role(id, name)
values (2, 'STAFF');
insert into auth_role(id, name)
values (3, 'USER');

insert into auth_permission(id, name, description)
values (1, 'user:view', '用户查询');
insert into auth_permission(id, name, description)
values (2, 'user:create', '用户创建');
insert into auth_permission(id, name, description)
values (3, 'user:remove', '用户删除');
insert into auth_permission(id, name, description)
values (4, 'user:update', '用户修改');

insert into auth_role_permission(id, role_id, permission_id)
values (1, 1, 1);
insert into auth_role_permission(id, role_id, permission_id)
values (2, 1, 2);
insert into auth_role_permission(id, role_id, permission_id)
values (3, 1, 3);
insert into auth_role_permission(id, role_id, permission_id)
values (4, 1, 4);
insert into auth_role_permission(id, role_id, permission_id)
values (5, 2, 1);
insert into auth_role_permission(id, role_id, permission_id)
values (6, 2, 2);
insert into auth_role_permission(id, role_id, permission_id)
values (7, 2, 4);
insert into auth_role_permission(id, role_id, permission_id)
values (8, 3, 1);

insert into auth_user_role(id, user_id, role_id)
values (1, 1, 1);
insert into auth_user_role(id, user_id, role_id)
values (2, 1, 2);
insert into auth_user_role(id, user_id, role_id)
values (3, 1, 3);
insert into auth_user_role(id, user_id, role_id)
values (4, 2, 2);
insert into auth_user_role(id, user_id, role_id)
values (5, 2, 3);
insert into auth_user_role(id, user_id, role_id)
values (6, 3, 3);

select distinct ap.name, ap.description
from auth_role_permission arp
         left join auth_role ar on arp.role_id = ar.id
         left join auth_permission ap on arp.permission_id = ap.id
where ar.id = (select id from t_user where username = 'lisi');

