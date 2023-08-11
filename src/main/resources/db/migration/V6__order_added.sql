create table orders(
    id serial primary key,
    shop_id bigint unsigned not null,
    customer_id bigint unsigned not null,
    courier_id bigint unsigned,
    locality varchar(24) not null,
    street varchar(24) not null,
    number int unsigned not null,
    total_amount double not null,
    status int not null,
    foreign key (customer_id) references user(id),
    foreign key (courier_id) references user(id)
);

create table ordered_product(
    id serial primary key,
    product_id bigint unsigned not null,
    orders_id bigint unsigned not null,
    quantity int not null,
    current_price double not null,
    foreign key (product_id) references product(id),
    foreign key (orders_id) references orders(id)
)