drop table bankaccholder;
set pagesize 200
set linesize 200
set num 10
create table bankaccholder(id number(6) constraint pk_id primary key,accholdername varchar2(30),password varchar2(30),accbalance number(15),typeofaccount varchar2(30));