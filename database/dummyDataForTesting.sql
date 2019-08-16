begin transaction;

insert into app_users(user_name,password,role,zipcode,budget) values('estherVanhove','nani','user',12345,4);
insert into app_users(user_name,password,role,budget) values('Martin','I<3Esther','user',1);
insert into app_users(user_name,password,role,zipcode,budget) values('swannInSecurity','I<3Paul','user',12346,3);
insert into app_users(user_name,password,role,zipcode,budget) values('paulVanhove','I<3Josephine','user',12344,4);
insert into app_users(user_name,password,role,zipcode,budget) values('josephineTheDivorced','I<3Freedom','user',12344,4);

commit;

begin transaction;

insert into app_users(user_name,password,role,zipcode,budget) values('leslieKnope','I<3ParksAndRec','user','74085',1);

INSERT INTO food_user(food_type,user_id) VALUES('whipped cream', (select id from app_users where user_name = 'leslieKnope'));

select * from app_users;

select * from food_user;

DELETE FROM food_user WHERE user_id = (select id from app_users where user_name = 'leslieKnope') AND food_type = 'whipped cream';

select * from food_user;

DELETE FROM visit WHERE visit_id = 1;

select * from visit;

rollback;

select * from visit where user_id = (select id from app_users where user_name = 'Martin') and restaurant_id = 'vs8orAK8nDXFxFiIfv0yYQ';

select * from app_users;

select * from visit where restaurant_id = '7hIl2AOZl6Zfr2fAojpeRw' ORDER BY date DESC;

SELECT * FROM visit WHERE user_id = 2 AND restaurant_id = '7hIl2AOZl6Zfr2fAojpeRw' ORDER BY date DESC, visit_id DESC;
