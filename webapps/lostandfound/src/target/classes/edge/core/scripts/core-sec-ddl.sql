

create table users(
      username varchar(50) not null primary key,
      password varchar(50) not null,
      enabled boolean not null
);

create table authorities (
      username varchar(50) not null,
      authority varchar(50) not null,
      constraint fk_authorities_users foreign key(username) references users(username)	 
);
      
create unique index ix_auth_username on authorities (username,authority);

INSERT INTO users(username,password,enabled)
VALUES('super','1b3231655cebb7a1f783eddf27d254ca', true);
INSERT INTO users(username,password,enabled)
VALUES('user', 'ee11cbb19052e40b07aac0ca060c23ee',true);


INSERT INTO authorities(username,authority)
VALUES('user','ROLE_USER');
INSERT INTO authorities(username,authority)
VALUES('super','ROLE_SUPER');
