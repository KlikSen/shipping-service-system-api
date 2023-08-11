create table if not exists user (
    id bigint unsigned not null auto_increment,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(40) not null,
    email_verification bool not null,
    password varchar(70) not null,
    user_role varchar(32) not null,
    register_time datetime,
    primary key (id),
    CONSTRAINT email_UC UNIQUE (email)
) engine = InnoDB;

create table if not exists confirmation (
	id bigint unsigned not null auto_increment,
    token varchar(100) not null,
    created datetime not null,
    confirmed datetime,
    expired datetime not null,
    user_id bigint unsigned not null,
    primary key(id),
    foreign key (user_id) references user(id)
) engine = InnoDB;
