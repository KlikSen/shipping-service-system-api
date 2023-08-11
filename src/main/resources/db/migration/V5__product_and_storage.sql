create table product(
    id serial primary key,
    name varchar(24) not null,
    description varchar(150) not null,
    shop_id bigint unsigned not null,
    price double not null,
    constraint shop_FK foreign key (shop_id) references shop(id)
);

create table storage(
    product_id bigint unsigned primary key,
    quantity int not null,
    constraint product_FK foreign key (product_id) references product(id)
)