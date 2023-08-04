


-- Jeu de données avec la MAJ des entités et creation d'une nouvelle DB 

-- mdp en clair :
-- user 1:hideya***T3
-- user2 : hamza***T3
-- user3 : omar***T3
-- user4 : kais***T3
-- user5 : moahmed***T3

INSERT INTO roles VALUES (1,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP ,'ROLE_REGISTRED');
INSERT INTO roles VALUES (2,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP,'ROLE_STUDENT');
INSERT INTO roles VALUES (3,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP,'ROLE_ADMIN');

INSERT INTO users VALUES (100,'hideya',CURRENT_TIMESTAMP,'hideya',CURRENT_TIMESTAMP ,'hideya@talan.com',true,'rebai','hideya','linkedin','$2a$10$kZpmLuK9P.mdgvJczWS5G.oKyZbg1yBPc4/hdYBFcH/kGhT/UIzam','28888888',null,'hid',null,'eGlzskBnhTt77pIVovnoBYSrrWOOG9JBwJa3Pf5fT108yKs9oNidmmby4LZgKqT0',1);
INSERT INTO users VALUES (101,'hamza',CURRENT_TIMESTAMP,'hamza',CURRENT_TIMESTAMP ,'hamza@talan.com',true,'ben salem','hamza','linkedin','$2a$10$KQQMek5ZWf7EsIMOUqpglOTMkbwav.TRkQ5FjxicjaYyU9WhaAU0q','28888889',null,'ham',null,'ftjP7tm0GFFiCmsLIG5VWHWcMZoaPPywyBV3HA0EbbHGwj8XwGzK0G1mpwlIBIo1',1);
INSERT INTO users VALUES (102,'omar',CURRENT_TIMESTAMP,'omar',CURRENT_TIMESTAMP ,'omar@talan.com',true,'shal','omar','linkedin','$2a$10$URpKsFJCQtp.dnu6RNswLOTtViwHHQh9U28UXMAMO8Nw.SXe/fUHq','28888886',null,'oma',null,'5MfJA700xoQuVDUXeKH562rG67a2Lrk4t3ZdSWVYD4I2VXvUhT7Z5PzL5ve3wGoR',1);
INSERT INTO users VALUES (103,'kais',CURRENT_TIMESTAMP,'kais',CURRENT_TIMESTAMP ,'kais@talan.com',true,'amiri','kais','linkedin','$2a$10$VZ8cYYy123PVpbgmmIcpQ.QxZJdI6j3i4HsiHKUV/JT60KO.LYEbe','28888881',null,'kai',null,'2IenK2XNkxQIerzJqoyJ8hGbAtxWN7NCEMHsckxxHw5AaV2QaSCRE4e7eCxb7kcE',1);
INSERT INTO users VALUES (104,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP ,'mohamed@talan.com',true,'aouadi','mohamed','linkedin','$2a$10$KDeQRQqv4URfjYdgyuMnGesOGbWny2xkGnvfTC5GQrkB0vWEpu77W','26659621',null,'moha',null,'OuSy282BtDpEBhESFEnhlAGMXvVeDyhcNYqRKEkptnWDpPyeYTatTCRXlT7qtJjU',3);

INSERT INTO cursus VALUES (1,'mohamed' ,CURRENT_TIMESTAMP , 'mohamed' ,CURRENT_TIMESTAMP,'Un cursus fullstack Java angular avec un contenu riche', null,'Java Angular ', null, 'PUBLIC', true );
INSERT INTO cursus VALUES (2,'mohamed' ,CURRENT_TIMESTAMP , 'mohamed' ,CURRENT_TIMESTAMP,'Un cursus fullstack PHP angular avec un contenu riche', null,'PHP Angular ', null, 'PUBLIC', true );

INSERT INTO session VALUES (1,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP ,'2022-12-25 ','2022-10-25 ','PLANNED',1);
INSERT INTO session VALUES (2,'mohamed',CURRENT_TIMESTAMP,'mohamed',CURRENT_TIMESTAMP ,'2022-11-25 ','2022-09-25 ','PLANNED',2);

INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-12-25','mohamed',CURRENT_TIMESTAMP ,'passable','cv1.pdf','ENGINEER','Ingénieur',1,true,'oui tres motivee','SITUATION_WORKER','Employé','CIVIL','Génie civil','ACCEPTED','Accepté',1,100);
INSERT INTO application VALUES (nextval('application_id_seq'),'moahmed','2022-10-22','mohamed',CURRENT_TIMESTAMP ,'bien','cv2.pdf','DOCTORATE','Doctorat',5,false,'oui  motivee','SITUATION_SEARCHING','A la recherche d'emploi','ELECTRIQUE','Génie électrique','WITHDRAWAL','Désistement',2,101);
INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-09-13','mohamed',CURRENT_TIMESTAMP ,'à revoir','cv3.pdf','OTHER',3,true,'pas motivee','SITUATION_WORKER','MECHANICAL','CANCELLED',1,102);

INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-05-21','mohamed',CURRENT_TIMESTAMP ,'passable','cv4.pdf','ENGINEER',4,true,'oui tres motivee','SITUATION_SEARCHING','ARCHITECTURE','NEW',2,103);
INSERT INTO application VALUES (nextval('application_id_seq'),'moahmed','2022-12-01','mohamed',CURRENT_TIMESTAMP ,'bien','cv5.pdf','OTHER',0,false,'tres motivee','SITUATION_SEARCHING','ENERGY','REFUSED',2,104);
INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-01-03','mohamed',CURRENT_TIMESTAMP ,'à revoir','cv6.pdf','OTHER',7,false,'pas motivee','SITUATION_WORKER','AUTOMATIC','INPROGRESS',1,104);

INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2021-12-19','mohamed',CURRENT_TIMESTAMP ,'passable','cv7.pdf','ENGINEER',7,false,'ps de motivation','SITUATION_SEARCHING','HYDRAULICS','ABANDONED',2,100);
INSERT INTO application VALUES (nextval('application_id_seq'),'moahmed','2023-11-25','mohamed',CURRENT_TIMESTAMP ,'tres bien','cv8.pdf','DOCTORATE',6,false,'pas de motivation','SITUATION_WORKER','OTHER','ABANDONED',1,102);
INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-12-25','mohamed',CURRENT_TIMESTAMP ,'un bon profil','cv9.pdf','OTHER',2,false,'pas motivee','SITUATION_WORKER','AUTOMATIC','INPROGRESS',2,104);

INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-03-01','mohamed',CURRENT_TIMESTAMP ,'passable','cv10.pdf','ENGINEER',1,true,'ps de motivation','SITUATION_SEARCHING','MECHANICAL','CANCELLED',1,100);
INSERT INTO application VALUES (nextval('application_id_seq'),'moahmed','2022-10-20','mohamed',CURRENT_TIMESTAMP ,'à revoir','cv11.pdf','OTHER',1,true,'oui pour la motivation','SITUATION_SEARCHING','OTHER','INPROGRESS',1,102);
INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-06-15','mohamed',CURRENT_TIMESTAMP ,'un bon profil','cv12.pdf','OTHER',9,true,'oui motivee','SITUATION_WORKER','MECHANICAL','INPROGRESS',2,104);

INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-12-27','mohamed',CURRENT_TIMESTAMP ,'à revoir','cv13.pdf','DOCTORATE',12,true,'pas de motivation','SITUATION_SEARCHING','CIVIL','NEW',2,101);
INSERT INTO application VALUES (nextval('application_id_seq'),'moahmed','2022-12-21','mohamed',CURRENT_TIMESTAMP ,'à revoir','cv14.pdf','OTHER',0,false,'oui pour la motivation','SITUATION_SEARCHING','OTHER','INPROGRESS',1,103);
INSERT INTO application VALUES (nextval('application_id_seq'),'mohamed','2022-08-04','mohamed',CURRENT_TIMESTAMP ,'un bon profil','cv15.pdf','DOCTORATE',4,true,'oui motivee','SITUATION_SEARCHING','ENERGY','INPROGRESS',2,100);