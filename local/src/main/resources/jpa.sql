create table STUDENT
(
	ID INTEGER default (NEXT VALUE FOR PUBLIC.SYSTEM_SEQUENCE_3E15FEF4_58C2_4E92_82DD_45F1263EF37F) auto_increment,
	NAME VARCHAR(50),
	AGE INTEGER,
	QQNAME VARCHAR(50),
	constraint STUDENT_PK primary key (ID)
);

