alter table if exists user_role
    drop constraint if exists FKfpm8swft53ulq2hl11yplpr5;

alter table if exists user_subscriptions
    drop constraint if exists FK4yw84gj44ckwb4cbg7kmrkai5;

alter table if exists user_subscriptions
    drop constraint if exists FKm69uaasbua17sgdnhsq10yxd5;


alter table if exists message
    drop constraint if exists FK70bv6o4exfe3fbrho7nuotopf;

alter table if exists message
    drop constraint if exists FKc3jkt36cxuic4o8eubtfau5w6;


alter table if exists post
    drop constraint if exists FKrm2u0ujvvi9euawhsm1km29m4;

alter table if exists post_like
    drop constraint if exists FK9j3wh3dvfv4flpe7ouv6qtcau;

alter table if exists post_like
    drop constraint if exists FKj7iy0k7n3d0vkh8o7ibjna884;


alter table if exists comment
    drop constraint if exists FKgcgdcgly6u49hf4g8y2di3g4p;

alter table if exists comment
    drop constraint if exists FKs1slvnkuemjsq2kj4h3vhx7i1;

alter table if exists comment_like
    drop constraint if exists FKeh0kvj98rlb19mbg8ooifb9be;

alter table if exists comment_like
    drop constraint if exists FKqlv8phl1ibeh0efv4dbn3720p;


alter table if exists reply
    drop constraint if exists FKipvbdtr2tvlo08122kmha4p9r;
alter table if exists reply
    drop constraint if exists FK6w0ns67lrq1jdiwi5xvtj1vxx;
alter table if exists reply_like
    drop constraint if exists FKk68gc42n9ljju6tsbtbn5ifrj;
alter table if exists reply_like
    drop constraint if exists FKqf4y6h9cfw6jtjrd04p1rpbbj;


drop table if exists usr cascade;
drop table if exists user_role cascade;
drop table if exists user_subscriptions cascade;
drop table if exists message cascade;
drop table if exists post cascade;
drop table if exists post_like cascade;
drop table if exists comment cascade;
drop table if exists comment_like cascade;
drop table if exists reply cascade;
drop table if exists reply_like cascade;


drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start 100 increment 1;


create table usr
(
    id              int8        not null,
    birthday        date,
    is_active       boolean     not null,
    activation_code varchar,
    name            varchar(30) not null,
    password        varchar(100) not null,
    status          varchar(50),
    surname         varchar(30) not null,
    username        varchar(30) not null,
    email           varchar(30) not null,
    primary key (id)
);

create table user_role
(
    user_id int8         not null,
    role    varchar(255) not null,
    primary key (user_id, role)
);

create table user_subscriptions
(
    subscriber_id int8 not null,
    target_id     int8 not null,
    primary key (target_id, subscriber_id)
);

create table message
(
    id                 int8          not null,
    creation_date_time timestamp     not null,
    text               varchar(5000) not null,
    user_id            int8          not null,
    target_id          int8          not null,
    primary key (id)
);

create table post
(
    id                 int8          not null,
    creation_date_time timestamp     not null,
    text               varchar(5000) not null,
    user_id            int8          not null,
    primary key (id)
);
create table post_like
(
    post_id int8 not null,
    user_id int8 not null,
    primary key (post_id, user_id)
);

create table comment
(
    id                 int8          not null,
    creation_date_time timestamp     not null,
    text               varchar(5000) not null,
    user_id            int8          not null,
    post_id            int8          not null,
    primary key (id)
);
create table comment_like
(
    comment_id int8 not null,
    user_id    int8 not null,
    primary key (comment_id, user_id)
);

create table reply
(
    id                 int8          not null,
    creation_date_time timestamp     not null,
    text               varchar(5000) not null,
    user_id            int8          not null,
    comment_id         int8          not null,
    primary key (id)
);

create table reply_like
(
    reply_id int8 not null,
    user_id  int8 not null,
    primary key (reply_id, user_id)
);

alter table if exists user_role
    add constraint FKfpm8swft53ulq2hl11yplpr5 foreign key (user_id) references usr;

alter table if exists user_subscriptions
    add constraint FK4yw84gj44ckwb4cbg7kmrkai5 foreign key (target_id) references usr;

alter table if exists user_subscriptions
    add constraint FKm69uaasbua17sgdnhsq10yxd5 foreign key (subscriber_id) references usr;


alter table if exists message
    add constraint FK70bv6o4exfe3fbrho7nuotopf foreign key (user_id) references usr;

alter table if exists message
    add constraint FKc3jkt36cxuic4o8eubtfau5w6 foreign key (target_id) references usr;


alter table if exists post
    add constraint FKrm2u0ujvvi9euawhsm1km29m4 foreign key (user_id) references usr;

alter table if exists post_like
    add constraint FK9j3wh3dvfv4flpe7ouv6qtcau foreign key (user_id) references usr;

alter table if exists post_like
    add constraint FKj7iy0k7n3d0vkh8o7ibjna884 foreign key (post_id) references post on delete cascade;


alter table if exists comment
    add constraint FKgcgdcgly6u49hf4g8y2di3g4p foreign key (user_id) references usr;

alter table if exists comment
    add constraint FKs1slvnkuemjsq2kj4h3vhx7i1 foreign key (post_id) references post on delete cascade;

alter table if exists comment_like
    add constraint FKeh0kvj98rlb19mbg8ooifb9be foreign key (user_id) references usr;

alter table if exists comment_like
    add constraint FKqlv8phl1ibeh0efv4dbn3720p foreign key (comment_id) references comment on delete cascade;


alter table if exists reply
    add constraint FKipvbdtr2tvlo08122kmha4p9r foreign key (user_id) references usr;

alter table if exists reply
    add constraint FK6w0ns67lrq1jdiwi5xvtj1vxx foreign key (comment_id) references comment on delete cascade;

alter table if exists reply_like
    add constraint FKk68gc42n9ljju6tsbtbn5ifrj foreign key (user_id) references usr;

alter table if exists reply_like
    add constraint FKqf4y6h9cfw6jtjrd04p1rpbbj foreign key (reply_id) references reply on delete cascade;