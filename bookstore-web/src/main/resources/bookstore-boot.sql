drop database if exists bookstore_boot;

create database bookstore_boot character set utf8;
use bookstore_boot;


create table auth_group
(
    id int auto_increment,
    name varchar(50),
    primary key (id)
);

create table auth_permission
(
    id int auto_increment,
    name varchar(50) not null ,
    description varchar(128),
    primary key (id)
);

create table auth_group_permission
(
    id int auto_increment,
    group_id int not null ,
    permission_id int not null ,
    primary key (id)
);

create table auth_user_group
(
    id int auto_increment,
    user_id bigint(20) not null ,
    group_id int not null ,
    primary key (id)
);

create table auth_user_permission
(
    id int auto_increment,
    user_id bigint(20) not null ,
    permission_id int not null ,
    primary key (id)
);

create table t_book(
    id bigint(20) auto_increment,
    title varchar(50) not null ,
    isbn varchar(13) not null ,
    price decimal(10,2) default 0.00,
    picture varchar(255) null ,
    author varchar(50) not null ,
    publisher varchar(50) null ,
    publish_date date null ,
    description text null ,
    category_id int null ,
    primary key (id)
);


create table user
(
    id           bigint(20) auto_increment,
    username     varchar(8)   not null,
    password     varchar(128) not null,
    email        varchar(64),
    mobile       varchar(11),
    sex          varchar(1),
    picture      varchar(255),
    create_time  datetime     not null,
    is_deleted   tinyint(1) default 0,
    is_staff     tinyint(1) default 0,
    is_superuser tinyint(1) default 0,
    primary key (id)
);


create table category
(
    id         int auto_increment,
    name        varchar(30),
    parent_id         int,
    primary key (id)
);

create table goods
(
    id           bigint(20) auto_increment,
    name         varchar(30),
    click_num    int        default 0,
    sold_num     int        default 0,
    fav_num      int        default 0,
    goods_num    int        default 0,
    market_price double     default 0.0,
    shop_price   double     default 0.0,
    goods_brief  varchar(128),
    goods_desc   varchar(255),
    ship_free    tinyint    default 0,
    picture      varchar(255),
    is_new       tinyint    default 1,
    is_hot       tinyint    default 0,
    create_time  datetime not null,
    cid          int      not null,
    is_deleted   tinyint(1) default 0,
    primary key (id)
);


create table t_address
(
    id           bigint(20) auto_increment,
    user_id      bigint(20)   not null,
    province      varchar(20)  not null,
    city          varchar(20)  not null,
    district      varchar(20)  not null,
    detail        varchar(255) not null,
    signer_name   varchar(20)  not null,
    signer_mobile varchar(11)  not null,
    is_deleted    tinyint(1) default 0,
    primary key (id)
);

create table t_order(
    id bigint(20) auto_increment,
    user_id bigint not null ,
    book_id bigint not null ,
    number int not null ,
    price decimal(10, 2) not null ,
    address_id bigint not null ,
    pay_type tinyint(1) not null,
    state tinyint(1) not null default 1,
    description varchar(255) null ,
    create_time datetime not null ,
    update_time datetime null ,
    primary key (id)
);

insert into t_order(user_id, book_id, number, price, address_id, pay_type, description, create_time, update_time)
values
(1508650212715945986,437,1,49.00,1508790497676718082,1,'',now(),null);


select b.id,b.title,
       o.create_time,o.state,o.pay_type,
       u.id,u.username,
       a.detail,a.province,a.city,a.district,a.signer_name,a.signer_mobile
from
    t_order o left join user u on o.user_id = u.id
    left join address a on o.address_id = a.id
    left join t_book b on b.id = o.book_id;

create table orders
(
    oid         bigint(20) auto_increment,
    uid         bigint(20)    not null,
    gid         bigint(20)    not null,
    aid         bigint(20)    not null,
    price       double        not null,
    number      int           not null,
    pay_type    varchar(10)   not null,
    state       varbinary(10) not null,
    description varchar(255),
    create_time datetime      not null,
    is_deleted  tinyint(1) default 0,
    primary key (oid)
);


DROP TABLE IF EXISTS oplog;
CREATE TABLE oplog
(
    oid        int(11) NOT NULL AUTO_INCREMENT,
    uid        int(11)      DEFAULT NULL,
    ip         varchar(100) DEFAULT NULL,
    reason     varchar(600) DEFAULT NULL,
    createTime datetime     DEFAULT NULL,
    PRIMARY KEY (oid)
);

drop table if exists t_cart;
create table t_cart(
    id bigint(20) auto_increment,
    book_id bigint(20) not null ,
    user_id bigint(20) not null ,
    count tinyint(1) unsigned default 1,
    create_time datetime not null ,
    primary key (id)
);

create index t_cart_idx on t_cart(user_id);

insert into t_cart values(null, 9787020035250, 1508650212715945986, 1, now());
insert into t_cart values(null, 9787020124756, 1508650212715945986, 2, now());
insert into t_cart values(null, 9787020164837, 1508650212715945986, 1, now());
insert into t_cart values(null, 9787115178220, 1508666451068784641, 1, now());
insert into t_cart values(null, 9787115226266, 1508666451068784641, 1, now());
insert into t_cart values(null, 9787115239075, 1508666451068784641, 1, now());

select
s.id, s.count,s.create_time,
b.isbn book_id,b.title, b.price, b.picture,
u.id user_id, u.username
from
shopping_cart s left join book b on s.book_id = b.isbn
left join user u on u.id = s.user_id where user_id = 1508650212715945986;


drop table if exists t_comment;
create table t_comment(
    id bigint(20) auto_increment,
    content varchar(255) not null ,
    user_id bigint(20) not null ,
    book_id bigint(20) not null ,
    order_id bigint(20) not null ,
    create_time datetime not null ,
    score decimal(2,1) not null ,
    primary key (id),
    unique key (user_id,order_id)
);



select c.id, c.content,c.create_time, c.book_id, b.title,c.user_id ,u.username, u.picture from t_comment c left join user u on c.user_id = u.id
left join t_book b on c.book_id = b.id;




