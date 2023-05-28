create table if not exists user (
    id bigint unsigned not null auto_increment,
    first_name varchar(30) not null,
    last_name varchar(30) not null,
    email varchar(70) unique not null,
    email_verification bool not null,
    password varchar(70) not null,
    user_role varchar(32) not null,
    register_time datetime,
    primary key (id)
) engine = InnoDB;

create table if not exists shipping_service (
    id bigint unsigned not null auto_increment,
    delivery_type varchar(32) not null,
    shipping_address varchar(255) not null,
    courier_id bigint unsigned,
    primary key (id),
    foreign key (courier_id) references user (id)
) engine = InnoDB;

create table if not exists shop (
    id bigint unsigned not null auto_increment,
    shop_name varchar(50) not null,
    address varchar(255) not null,
    owner_id bigint unsigned not null,
    schedule varchar(1000),
    online_shop bool not null,
    primary key (id),
    foreign key (owner_id) references user (id)
) engine = InnoDB;

create table if not exists product (
    id bigint unsigned not null auto_increment,
    product_name varchar(50) not null,
    description varchar(500) not null,
    price double unsigned not null,
    available_for_order bool not null,
    shop_id bigint unsigned not null,
    primary key (id),
    foreign key (shop_id) references shop (id)
) engine = InnoDB;

create table if not exists shippingdb.order (
    id bigint unsigned not null auto_increment,
    customer_id bigint unsigned not null,
    shipping_id bigint unsigned not null,
    shop_id bigint unsigned not null,
    total_amount double unsigned not null,
    status varchar(32) not null,
    created_time datetime,
    shipped_time datetime,
    primary key (id),
    foreign key (customer_id) references user (id),
    foreign key (shipping_id) references shipping_service (id),
    foreign key (shop_id) references shop (id)
) engine = InnoDB;

create table if not exists order_product_reference (
    product_id bigint unsigned not null,
    order_id bigint unsigned not null,
    count int unsigned not null,
    foreign key (product_id) references product (id),
    foreign key (order_id) references shippingdb.order (id)
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
