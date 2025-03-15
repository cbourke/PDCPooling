
drop table if exists Address;
drop table if exists Email;
drop table if exists Person;

create table if not exists Person (
  personId int not null primary key auto_increment,
  uuid varchar(36) not null unique key,
  firstName varchar(255) not null,
  lastName varchar(255) not null
);

create table if not exists Email (
  emailId int not null primary key auto_increment,
  address varchar(255) not null,
  personId int not null,
  foreign key (personId) references Person(personId)
);

create table if not exists Address (
  addressId int not null primary key auto_increment,
  street varchar(255) not null,
  city varchar(255) not null,
  state varchar(255) not null,
  zip varchar(5) not null,
  personId int not null,
  foreign key (personId) references Person(personId)
);

insert into Person (personId, uuid, firstName, lastName) values (1, 'ff7d8123-19a1-41b1-bfa9-0fad765bd6b6', 'Tobin', 'Wollard');
insert into Person (personId, uuid, firstName, lastName) values (2, '3f2b50e4-c0c4-41fa-8aa6-f6c47c187554', 'Isadore', 'Conniam');
insert into Person (personId, uuid, firstName, lastName) values (3, 'e88797fa-9e16-4660-906f-d6a6b48671da', 'Luce', 'Burnapp');
insert into Person (personId, uuid, firstName, lastName) values (4, 'f8f9d931-5dad-4277-a235-b3d2fd37e578', 'Mauricio', 'Thornewill');
insert into Person (personId, uuid, firstName, lastName) values (5, '1a7c8864-0a20-4e33-a124-88d38ce6829a', 'Saudra', 'Grishenkov');
 
insert into Address (personId, street, city, state, zip) values (1, '5 Northwestern Lane', 'Saint Petersburg', 'Florida', '33710');
insert into Address (personId, street, city, state, zip) values (2, '693 Forest Run Way', 'Tallahassee', 'Florida', '32399');
insert into Address (personId, street, city, state, zip) values (3, '65610 Northland Avenue', 'Arlington', 'Virginia', '22225');
insert into Address (personId, street, city, state, zip) values (4, '95 Susan Plaza', 'Topeka', 'Kansas', '66606');
insert into Address (personId, street, city, state, zip) values (5, '492 Lake View Park', 'El Paso', 'Texas', '88535');

insert into Email (personId, address) values (2, 'smollindinia0@wikispaces.com');
insert into Email (personId, address) values (3, 'cmcilwraith1@dot.gov');
insert into Email (personId, address) values (3, 'tcaslane2@51.la');
insert into Email (personId, address) values (4, 'lbythell3@sciencedirect.com');
insert into Email (personId, address) values (4, 'svanderkruijs4@europa.eu');
insert into Email (personId, address) values (4, 'mspilling5@tinyurl.com');
insert into Email (personId, address) values (5, 'kjiricka6@storify.com');
insert into Email (personId, address) values (5, 'aabys7@nasa.gov');
insert into Email (personId, address) values (5, 'hcolchett8@youku.com');
insert into Email (personId, address) values (5, 'gwestwick9@trellian.com');

 