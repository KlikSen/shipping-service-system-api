CREATE TABLE jwt (
    id bigint unsigned auto_increment NOT NULL,
    token varchar(512) NOT NULL,
    user_id bigint unsigned NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
)