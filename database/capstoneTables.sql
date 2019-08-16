
BEGIN TRANSACTION;

DROP TABLE IF EXISTS app_user            CASCADE;
DROP TABLE IF EXISTS app_users           CASCADE;
DROP TABLE IF EXISTS food                CASCADE;
DROP TABLE IF EXISTS food_user          CASCADE;
DROP TABLE IF EXISTS restaurant          CASCADE;
DROP TABLE IF EXISTS food_restaurant     CASCADE;
DROP TABLE IF EXISTS user_restaurant    CASCADE;
DROP TABLE IF EXISTS visit             CASCADE;


CREATE TABLE app_users (
    id serial NOT NULL,
    user_name varchar(32) NOT NULL UNIQUE,
    password varchar(32) NOT NULL,
    role varchar(32) NOT NULL,
    salt varchar(255),
    zipcode varchar(9),
    email varchar(100),
    budget int,
 
    CONSTRAINT pk_app_users_id PRIMARY KEY (id)
);

CREATE TABLE food_user (
    food_type varchar(80) NOT NULL,
    user_id int NOT NULL,
    
    CONSTRAINT fk_app_users_id FOREIGN KEY (user_id) REFERENCES app_users(id)
);


CREATE TABLE user_restaurant (
    user_id int NOT NULL,
    restaurant_id char(22) NOT NULL,
    is_liked boolean,
    is_disliked boolean,
    
    CONSTRAINT fk_app_users_id FOREIGN KEY (user_id) REFERENCES app_users(id)
);

CREATE TABLE visit (
    visit_id serial NOT NULL,
    restaurant_id char(22) NOT NULL,
    user_id int NOT NULL,
    review text,
    rating int,
    has_been_asked_about boolean,
    date date not null default CURRENT_DATE,
    
    CONSTRAINT pk_visit_visit_id PRIMARY KEY (visit_id),
    CONSTRAINT fk_app_users_id FOREIGN KEY (user_id) REFERENCES app_users(id)
);

commit;


