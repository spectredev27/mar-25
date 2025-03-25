create sequence Artifact_SEQ start with 53 increment by 50;
create table Artifact (id integer not null, groupName varchar(255), artifactName varchar(255), primary key (id));
