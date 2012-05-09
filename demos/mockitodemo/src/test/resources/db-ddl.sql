drop table MY_ACCOUNT if exists;

create table MY_ACCOUNT (ID integer identity primary key, A_NUMBER varchar(9), A_NAME varchar(50) not null, unique(A_NUMBER));


