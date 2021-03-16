create table role
(
    id   int auto_increment
        primary key,
    name varchar(45) default 'USER' null
);

GO

create table user
(
    id            int auto_increment
        primary key,
    name          varchar(45)          null,
    surname       varchar(45)          null,
    birthday      date                 null,
    email         varchar(45)          null,
    active_status tinyint(1) default 1 null,
    constraint user_email_uindex
        unique (email)
);

GO

create table community
(
    id           int auto_increment
        primary key,
    name         varchar(45) null,
    topic        varchar(45) not null,
    moderator_id int         not null,
    constraint community_name_uindex
        unique (name),
    constraint fk_community_moderator
        foreign key (moderator_id) references user (id)
);

GO

create index fk_community_moderator_index
    on community (moderator_id);

GO

create table community_has_user
(
    community_id int not null,
    user_id      int not null,
    primary key (community_id, user_id),
    constraint fk_community_has_user_community1
        foreign key (community_id) references community (id),
    constraint fk_community_has_user_user1
        foreign key (user_id) references user (id)
);

GO

create index fk_community_has_user_community1_idx
    on community_has_user (community_id);

GO

create index fk_community_has_user_user1_idx
    on community_has_user (user_id);

GO

create table community_message
(
    id           int auto_increment,
    text         varchar(200) not null,
    date         timestamp   not null,
    user_id      int         not null,
    community_id int         not null,
    primary key (id, user_id, community_id),
    constraint fk_community_message_community1
        foreign key (community_id) references community (id),
    constraint fk_community_message_user1
        foreign key (user_id) references user (id)
);

GO

create index community_message_date_index
    on community_message (date desc);

GO

create index fk_community_message_community1_idx
    on community_message (community_id);

GO

create index fk_community_message_user1_idx
    on community_message (user_id);

GO

create table credentials
(
    id       int auto_increment,
    login    varchar(45) null,
    password varchar(90) null,
    user_id  int         not null,
    primary key (id, user_id),
    constraint credentials_login_uindex
        unique (login),
    constraint fk_credentials_user1
        foreign key (user_id) references user (id)
);

GO

create index fk_credentials_user1_idx
    on credentials (user_id);

GO

create table message
(
    date        timestamp   not null,
    text        varchar(200) not null,
    sender_id   int         not null,
    receiver_id int         not null,
    id          int auto_increment
        primary key,
    constraint fk_message_user1
        foreign key (receiver_id) references user (id)
            on update cascade on delete cascade,
    constraint fk_message_user2
        foreign key (sender_id) references user (id)
            on update cascade on delete cascade
);

GO

create index fk_message_user1_idx
    on message (receiver_id);

GO

create index fk_message_user2_idx
    on message (sender_id);

GO

create index message_date_index
    on message (date desc);

GO

create table user_has_friends
(
    user_id   int         not null,
    friend_id int         not null,
    status    varchar(45) not null,
    date      date        not null,
    primary key (user_id, friend_id),
    constraint fk_user_has_friends_user1
        foreign key (user_id) references user (id)
            on update cascade on delete cascade,
    constraint fk_user_has_friends_user2
        foreign key (friend_id) references user (id)
            on update cascade on delete cascade
);

GO

create index fk_user_has_friends_user2_idx
    on user_has_friends (friend_id);

GO

create table user_has_role
(
    user_id int not null,
    role_id int not null,
    primary key (user_id, role_id),
    constraint fk_user_has_role_role1
        foreign key (role_id) references role (id),
    constraint fk_user_has_role_user1
        foreign key (user_id) references user (id)
);

GO

create index fk_user_has_role_role1_idx
    on user_has_role (role_id);

GO

create index fk_user_has_role_user1_idx
    on user_has_role (user_id);

GO

create table wall
(
    id      int auto_increment,
    user_id int not null,
    primary key (id, user_id),
    constraint fk_wall_user1
        foreign key (user_id) references user (id)
);

GO

create table post
(
    id      int auto_increment,
    header  varchar(100) null,
    text    varchar(200) null,
    wall_id int         not null,
    date    date        null,
    user_id int         not null,
    primary key (id, wall_id, user_id),
    constraint fk_post_user1
        foreign key (user_id) references user (id),
    constraint fk_post_wall1
        foreign key (wall_id) references wall (id)
);

GO

create index fk_post_user1_idx
    on post (user_id);

GO

create index fk_post_wall1_idx
    on post (wall_id);

GO

create index fk_wall_user1_idx
    on wall (user_id);

