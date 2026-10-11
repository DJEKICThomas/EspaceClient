-- Web dotation view
create
or replace view WEBDOTATION_VIEW
as
select b.ID
     , C2.CUS_NCS
     , C2.CUS_NDA
     , C2.CUS_NM1
     , b.BARCODE
     , ARM
     , CASS
     , case when b.LOVEPE = 'L' THEN 'LOC' else 'VEN' END                       as NAT
     , GEN_SRC_ARG
     , ART_DES_DES
     , case when CX.CUS_MISE_EN_SERVICE = 'Y' THEN B.DTC else 0 end             as dtc
     , BDET.DATETIME
     , coalesce(CP.CUS_PORT_NOM, '')                                            as porteur
     , S.REM                                                                    as stop
     , case when AX.ART_XDI_EXJEN = '1' THEN null else BDET.LAST_ENTRY_DATE END as date_in
     , case
           when ((BDET.LAST_RELEASE_DATE < BDET.LAST_ENTRY_DATE) or AX.ART_XDI_EXJEN = '1') THEN null
           else BDET.LAST_RELEASE_DATE END                                      as date_out

from YGDEPAIRON.BARCODE B
         join YGDEPAIRON.BARCODEDET BDET on BDET.BARCODE = B.BARCODE
         join YGDEPAIRON.CUS C2
              on C2.CUS_NCS = B.NCS and C2.CUS_NDA = B.NDA and c2.CUS_TYP = 'C' and C2.CUS_COM = 'DEP' and
                 C2.CUS_DEP = 'DEP'
         inner join YGDEPAIRON.BARCODESTOP as BS
                    on BS.barcode = B.barcode and (BS.DTB > CURRENT_DATE and
                                                   ((BS.PERM = 'Y' and BS.DTF <= DATETIME) OR
                                                    (BS.PERM <> 'Y' and BS.DTR >= CURRENT_DATE)))
         inner join YGDEPAIRON.STOPS as S on BS.STOP = S.CODE /* and S.webactif = 'Y'*/
         join YGDEPAIRON.GEN_SRC GS on GS.GEN_SRC_KEY = B.IIN and GS.GEN_SRC_TYP = 'I' and GS.GEN_SRC_SRC = 'S00' and
                                       GS.GEN_SRC_COM = 'DEP' and GS.GEN_SRC_DEP = 'DEP'
         left join YGDEPAIRON.ART_DES AD on AD.ART_DES_IIN = B.IIN and AD.ART_DES_LNG = 'fr'
         left join YGDEPAIRON.CUS_PORT CP on CP.ID = B.PORTEURID
         join YGDEPAIRON.ART_XDI AX on AX.ART_XDI_IIN = b.IIN and AX.ART_XDI_COM = 'DEP' and AX.ART_XDI_DEP = ''
         left join YGDEPAIRON.CUS_XDC CX
                   on CX.CUS_XDC_NCS = b.NCS and CX.CUS_XDC_NDA = b.NDA and cx.CUS_XDC_COM = 'DEP' and
                      CX.CUS_XDC_DEP = ''
where B.COM = 'DEP'
  and B.DEP = 'DEP'
  and B.FLAG = ''
group by b.ID, CUS_NCS, CUS_NDA, CUS_NM1, b.BARCODE, ARM, CASS, b.LOVEPE, GEN_SRC_ARG, ART_DES_DES, B.DTC, DATETIME,
         BDET.LAST_ENTRY_DATE, BDET.LAST_RELEASE_DATE, AX.ART_XDI_EXJEN, CP.CUS_PORT_NOM, S.REM, CX.CUS_MISE_EN_SERVICE;

create index WEBENTRYCTRL_DATE_NCS_CARRIER_INDEX
    on WEBENTRYCTRL (DATE desc, CARRIER asc);

create index WEBENTRYCTRL_DATE_INDEX
    on WEBENTRYCTRL (DATE desc);

create index WEBENTRYCTRL_BARCODE_INDEX
    on WEBENTRYCTRL (BARCODE);

create index WEBENTRYCTRL_ARTID_INDEX
    on WEBENTRYCTRL (ARTID);

create index WEBENTRYCTRL_NCSID_INDEX
    on WEBENTRYCTRL (NCSID);


create index WEBSTATISTIC_NCSID_INDEX
    on WEBSTATISTIC (NCSID);

create index WEBSTATISTIC_ARTID_INDEX
    on WEBSTATISTIC (ARTID);


create index WEBSTATISTIC_YEAR_COUNTER_INDEX
    on WEBSTATISTIC (YEAR, COUNTER);

alter table STOPS
    add REM_WEB varchar(256) default '' not null;

alter table STOPS
    add ACTIVE_WEB char(1) default '' not null;

alter table BARCODESTOP
    add REM_WEB varchar(256) default '' not null;

update STOPS as s
set s.ACTIVE_WEB = 'Y'
where s.CODE not in ('002', '014', '020', '023', '026', '029', '031', '033', '037', '041', '043', '046', '047');

update STOPS as s
set s.REM_WEB = s.REM;

alter table YGDEPAIRON.REQUEST_HEADER
    alter column USER set DATA TYPE varchar(50)

alter table YGDEPAIRON.REQUEST_HEADER
    add TICKET VARCHAR(20);

create table YGDEPAIRON.NOTIFICATION
(
    id           bigint generated always as identity
        constraint NOTIFICATION_PK
            primary key,
    gestionnaire varchar(10),
    is_new       smallint with default 0,
    date         timestamp
);

create index NOTIFICATION_ID_INDEX
    on YGDEPAIRON.NOTIFICATION (id);

create table YGDEPAIRON.WEBSTATISTIC2
(
    ID       bigint generated always as identity
        constraint WEBSTATISTIC2_PK
            primary key,
    NCS      char(10)   default '' not null,
    NDA      DECIMAL(5) default 0  not null,
    IIN      DECIMAL(8) default 0  not null,
    YEAR1    DECIMAL(4) default 0  not null,
    MONTH1   DECIMAL(2) default 0  not null,
    COUNTER1 DECIMAL(8) default 0  not null,
    YEAR2    DECIMAL(4) default 0  not null,
    MONTH2   DECIMAL(2) default 0  not null,
    COUNTER2 DECIMAL(8) default 0  not null,
    YEAR3    DECIMAL(4) default 0  not null,
    MONTH3   DECIMAL(2) default 0  not null,
    COUNTER3 DECIMAL(8) default 0  not null,
    NCSID    bigint     default 0  not null,
    ARTID    bigint     default 0  not null
);

-- WEBROLE
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_DOTATION', '016 Accès aux dotations');
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_ADMIN', '998 Accès à la partie administration');
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_FILTRAGE', '017 Accès aux filtrages des demandes');
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_GESTION', '018 Accès à la geston des demandes');
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_CUSSELECTION', '019 Accès à la selection client');
INSERT INTO YGDEPAIRON.WEBROLE (ROLE, COMMENT)
VALUES ('ACCESS_USERLIST', '020 Accès à la liste utilisateur');

-- WEBPROFILEROLE ADMINISTRATEUR
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (2, 42); -- 2 = ID_PROFILE | 42 = ID_ROLE ACCESS_DOTATION
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (2, 43); -- 2 = ID_PROFILE | 42 = ID_ROLE ACCESS_ADMIN
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (2, 44); -- 2 = ID_PROFILE | 42 = ID_ROLE ACCESS_FILTRAGE
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (2, 46);
-- 2 = ID_PROFILE | 42 = ID_ROLE ACCESS_GESTION

-- WEBPROFILEROLE DEPAIRON
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (18, 47);

-- WEBPROFILEROLE GESTIONNAIRE
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (14, 43); -- ACCES A LA PARTIE ADMIN
INSERT INTO YGDEPAIRON.WEBPROFILEROLE (WEBPROFILEID, WEBROLEID)
VALUES (14, 47); -- ACCES A LA PARTIE LISTE UTILISATEUR

-- WEBUSERROLE
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_DOTATION', DEFAULT);
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_ADMIN', DEFAULT);
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_FILTRAGE', DEFAULT);
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_GESTION', DEFAULT);
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_CUSSELECTION', DEFAULT);
INSERT INTO YGDEPAIRON.WEBUSERROLE (USERID, ROLE, COMMENT)
VALUES ('nho@sigroup.be', 'ACCESS_USERLIST', DEFAULT);