CREATE TABLE shop (
	id serial primary key,
    name varchar(32) NOT NULL,
	locality varchar(32) NOT NULL,
    street varchar(32) NOT NULL,
    number int NOT NULL,
    user_id bigint unsigned unique NOT NULL,
	CONSTRAINT user_FK FOREIGN KEY(user_id) REFERENCES user(id)
);

CREATE TABLE work_day(
    id serial primary key,
    start_time time not null,
    end_time time not null,
    day_of_week int not null,
    shop_id bigint unsigned not null,
    foreign key (shop_id) references shop(id)
)