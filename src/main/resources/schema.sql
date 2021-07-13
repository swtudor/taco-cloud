create table if not exists User(
    id identity,
    username varchar(20) not null,
    password varchar(500) not null,
    fullname varchar(50) not null,
    street varchar(30) not null,
    city varchar(40) not null,
    state varchar(20) not null,
    zip varchar(20) not null,
    phone_number varchar(30) not null,
    roles varchar(20) not null
);

create table if not exists Ingredient (
  id varchar(4) not null,
  name varchar(50) not null,
  type varchar(10) not null
);

create table if not exists Taco (
  id identity,
  name varchar(50) not null,
  created_at timestamp not null
);

create table if not exists Taco_Ingredients (
  taco_id bigint not null,
  ingredients_id varchar(4) not null
);

alter table Taco_Ingredients
    add foreign key (taco_id) references Taco(id);
alter table Taco_Ingredients
    add foreign key (ingredients_id) references Ingredient(id);

create table if not exists Taco_Order (
	id identity,
	name varchar(50) not null,
	street varchar(50) not null,
	city varchar(50) not null,
	state varchar(2) not null,
	zip varchar(10) not null,
	cc_number varchar(16) not null,
	cc_expiration varchar(5) not null,
	cccvv varchar(3) not null,
    created_at timestamp not null,
    user_id bigint not null
);

create table if not exists Taco_Order_Tacos (
	order_id bigint not null,
	tacos_id bigint not null
);

alter table Taco_Order_Tacos
    add foreign key (order_id) references Taco_Order(id);
alter table Taco_Order_Tacos
    add foreign key (tacos_id) references Taco(id);

alter table Taco_Order
    add foreign key (user_id) references User(id);

