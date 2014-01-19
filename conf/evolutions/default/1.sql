# --- Created by Ebean DDL
# To stop Ebean DDL generation, remove this comment and start using Evolutions

# --- !Ups

create table aws_s3 (
  id                        bigint not null,
  subject                   varchar(255),
  access_key                varchar(255),
  secret_key                varchar(255),
  region                    varchar(255),
  bucket_name               varchar(255),
  object_key                varchar(255),
  constraint pk_aws_s3 primary key (id))
;

create sequence aws_s3_seq;




# --- !Downs

SET REFERENTIAL_INTEGRITY FALSE;

drop table if exists aws_s3;

SET REFERENTIAL_INTEGRITY TRUE;

drop sequence if exists aws_s3_seq;

