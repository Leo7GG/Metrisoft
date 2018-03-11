/*
Navicat PGSQL Data Transfer

Source Server         : PosgresUTCV
Source Server Version : 90601
Source Host           : localhost:5432
Source Database       : Libroteca
Source Schema         : public

Target Server Type    : PGSQL
Target Server Version : 90601
File Encoding         : 65001

Date: 2017-05-06 09:44:45
*/


-- ----------------------------
-- Sequence structure for autores_idautor_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "autores_idautor_seq";
CREATE SEQUENCE "autores_idautor_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 3
 CACHE 1;
SELECT setval('"public"."autores_idautor_seq"', 3, true);

-- ----------------------------
-- Sequence structure for bibliotecarios_idbiblio_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "bibliotecarios_idbiblio_seq";
CREATE SEQUENCE "bibliotecarios_idbiblio_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 13
 CACHE 1;
SELECT setval('"public"."bibliotecarios_idbiblio_seq"', 13, true);

-- ----------------------------
-- Sequence structure for libro_autor_id_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "libro_autor_id_seq";
CREATE SEQUENCE "libro_autor_id_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 13
 CACHE 1;
SELECT setval('"public"."libro_autor_id_seq"', 13, true);

-- ----------------------------
-- Sequence structure for libros_idlibro_seq
-- ----------------------------
DROP SEQUENCE IF EXISTS "libros_idlibro_seq";
CREATE SEQUENCE "libros_idlibro_seq"
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 8
 CACHE 1;
SELECT setval('"public"."libros_idlibro_seq"', 8, true);

-- ----------------------------
-- Table structure for autores
-- ----------------------------
DROP TABLE IF EXISTS "autores";
CREATE TABLE "autores" (
"idautor" int4 DEFAULT nextval('autores_idautor_seq'::regclass) NOT NULL,
"nombre" varchar(200) COLLATE "default" NOT NULL,
"apaterno" varchar(100) COLLATE "default" NOT NULL,
"amaterno" varchar(100) COLLATE "default",
"estatus" bit(1) DEFAULT B'0'::"bit" NOT NULL,
"nacionalidad" varchar(100) COLLATE "default" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of autores
-- ----------------------------
BEGIN;
INSERT INTO "autores" VALUES ('1', 'Yekoot', 'sor', 'brera', E'1', 'jugador');
INSERT INTO "autores" VALUES ('2', 'gato', 'sor', 'cab', E'1', 'mexico');
INSERT INTO "autores" VALUES ('3', 'miau', 'ooooo', 'jdjdj', E'1', 'owowow');
COMMIT;

-- ----------------------------
-- Table structure for bibliotecarios
-- ----------------------------
DROP TABLE IF EXISTS "bibliotecarios";
CREATE TABLE "bibliotecarios" (
"idbiblio" int4 DEFAULT nextval('bibliotecarios_idbiblio_seq'::regclass) NOT NULL,
"nombre" varchar(100) COLLATE "default" NOT NULL,
"paterno" varchar(100) COLLATE "default" NOT NULL,
"materno" varchar(100) COLLATE "default" NOT NULL,
"direccion" varchar(200) COLLATE "default" NOT NULL,
"ciudad" varchar(100) COLLATE "default" NOT NULL,
"pais" varchar(100) COLLATE "default" NOT NULL,
"cp" varchar(5) COLLATE "default" NOT NULL,
"telefono" varchar(20) COLLATE "default" NOT NULL,
"correo" varchar(200) COLLATE "default" NOT NULL,
"puesto" varchar(100) COLLATE "default" NOT NULL,
"fechacon" date NOT NULL,
"estatus" bit(1) DEFAULT B'1'::"bit" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of bibliotecarios
-- ----------------------------
BEGIN;
INSERT INTO "bibliotecarios" VALUES ('1', 'Guadalupe', 'Sorcia', 'Cabrera', 'Conocido ', 'Córdoba', 'México', '94480', '2711056841', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('2', 'Guadalupe', 'Sorcia', 'Cabrera', 'Conocido ', 'Córdoba', 'México', '94480', '2711056841', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('3', 'Guadalupe', 'Sorcia', 'Cabrera', 'Conocido ', 'Córdoba', 'México', '94480', '2711056841', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('4', 'Guadalupe', 'Sorcia', 'Cabrera', 'Conocido ', 'Córdoba', 'México', '94480', '2711056841', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('5', 'Guadalupe', 'Sorcia', 'Cabrera', 'Conocido ', 'Córdoba', 'México', '94480', '2711056841', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'1');
INSERT INTO "bibliotecarios" VALUES ('6', 'LUPE', 'sor', 'ca', 'conocido', 'cordoba', 'mexico', '94480', '2711056841', '1072@hamkd.com', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('7', 'guadalupe', 'Trujilo', 'cabrera', 'conocido', 'cordoba', 'mexico', '94480', '2710152648', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'1');
INSERT INTO "bibliotecarios" VALUES ('8', 'Yekoot', 'Sorcia', 'Tiki', 'Conocido S/N', 'Fortin', 'Mexico', '94470', '2711573455', '10792@utcv.edu.mx', 'Gerente', '2017-02-04', E'0');
INSERT INTO "bibliotecarios" VALUES ('10', 'Gato', 'Gatos', 'Gatitos', 'NO SE', 'cordoba', 'mexico', '94505', '512645', 'coco@jk.com', 'Bibliotecario', '2017-02-11', E'0');
INSERT INTO "bibliotecarios" VALUES ('11', 'g', 'a', '4', '7', 'h', 'p', '7', 'kkkkk', 'ljkk', 'Bibliotecario', '2017-02-18', E'0');
INSERT INTO "bibliotecarios" VALUES ('12', 'dano', 'Daniel', 'Carrera', 'Conocido', 'Cordoba', 'Mexico', '94480', '271548621', 'dan_2008@tu.com', 'Invitado', '2017-02-25', E'1');
INSERT INTO "bibliotecarios" VALUES ('13', 'LUPE', 'sor', 'ca', 'conocido', 'cordoba', 'mexico', '94480', '2711056841', '1072@hamkd.com', 'Gerente', '2017-02-04', E'0');
COMMIT;

-- ----------------------------
-- Table structure for libro_autor
-- ----------------------------
DROP TABLE IF EXISTS "libro_autor";
CREATE TABLE "libro_autor" (
"id" int4 DEFAULT nextval('libro_autor_id_seq'::regclass) NOT NULL,
"idlibro" int4 NOT NULL,
"idautor" int4 NOT NULL,
"estatus" bit(1) DEFAULT B'1'::"bit" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of libro_autor
-- ----------------------------
BEGIN;
INSERT INTO "libro_autor" VALUES ('1', '1', '2', E'1');
INSERT INTO "libro_autor" VALUES ('2', '2', '2', E'1');
INSERT INTO "libro_autor" VALUES ('3', '3', '3', E'1');
INSERT INTO "libro_autor" VALUES ('4', '4', '3', E'1');
INSERT INTO "libro_autor" VALUES ('5', '5', '3', E'1');
INSERT INTO "libro_autor" VALUES ('6', '5', '2', E'0');
INSERT INTO "libro_autor" VALUES ('7', '5', '1', E'1');
INSERT INTO "libro_autor" VALUES ('8', '6', '1', E'1');
INSERT INTO "libro_autor" VALUES ('9', '6', '2', E'1');
INSERT INTO "libro_autor" VALUES ('10', '7', '2', E'1');
INSERT INTO "libro_autor" VALUES ('11', '8', '2', E'1');
INSERT INTO "libro_autor" VALUES ('12', '8', '1', E'1');
INSERT INTO "libro_autor" VALUES ('13', '8', '3', E'1');
COMMIT;

-- ----------------------------
-- Table structure for libros
-- ----------------------------
DROP TABLE IF EXISTS "libros";
CREATE TABLE "libros" (
"idlibro" int4 DEFAULT nextval('libros_idlibro_seq'::regclass) NOT NULL,
"isbn" varchar(100) COLLATE "default" NOT NULL,
"titulo" varchar(200) COLLATE "default" NOT NULL,
"editorial" varchar(200) COLLATE "default" NOT NULL,
"numeroe" varchar(100) COLLATE "default" NOT NULL,
"anio" varchar(5) COLLATE "default" NOT NULL,
"estatus" bit(1) DEFAULT B'1'::"bit" NOT NULL
)
WITH (OIDS=FALSE)

;

-- ----------------------------
-- Records of libros
-- ----------------------------
BEGIN;
INSERT INTO "libros" VALUES ('1', '12345', 'La Abeja', 'Coma', '85', '2017', E'1');
INSERT INTO "libros" VALUES ('2', '00544', 'Jajajaja te engañe', 'pc', '987', '2015', E'1');
INSERT INTO "libros" VALUES ('3', '0000', 'poder', 'razon', '951', '1235', E'1');
INSERT INTO "libros" VALUES ('4', '0258', 'GDW', 'Jet', '136497', '2052', E'1');
INSERT INTO "libros" VALUES ('5', '369369', 'Este es nuevo', 'ÑLO', '784', '0001', E'1');
INSERT INTO "libros" VALUES ('6', '171717', 'IBMLenovo', 'CANVAS', '00001', '2018', E'1');
INSERT INTO "libros" VALUES ('7', '78', 'irru', 'jnbc', 'jbhv', 'hhyhy', E'1');
INSERT INTO "libros" VALUES ('8', 'aaaaa', 'Cafe', 'arbol', '025', '1996', E'1');
COMMIT;

-- ----------------------------
-- View structure for vmautores
-- ----------------------------
CREATE OR REPLACE VIEW "vmautores" AS 
 SELECT a.idautor,
    a.nombre,
    a.apaterno,
    a.amaterno,
    a.nacionalidad,
    li.idlibro,
    li.titulo,
    la.estatus
   FROM ((autores a
     JOIN libro_autor la ON ((a.idautor = la.idautor)))
     JOIN libros li ON ((li.idlibro = la.idlibro)));

-- ----------------------------
-- Alter Sequences Owned By 
-- ----------------------------
ALTER SEQUENCE "autores_idautor_seq" OWNED BY "autores"."idautor";
ALTER SEQUENCE "bibliotecarios_idbiblio_seq" OWNED BY "bibliotecarios"."idbiblio";
ALTER SEQUENCE "libro_autor_id_seq" OWNED BY "libro_autor"."id";
ALTER SEQUENCE "libros_idlibro_seq" OWNED BY "libros"."idlibro";

-- ----------------------------
-- Primary Key structure for table autores
-- ----------------------------
ALTER TABLE "autores" ADD PRIMARY KEY ("idautor");

-- ----------------------------
-- Primary Key structure for table bibliotecarios
-- ----------------------------
ALTER TABLE "bibliotecarios" ADD PRIMARY KEY ("idbiblio");

-- ----------------------------
-- Primary Key structure for table libro_autor
-- ----------------------------
ALTER TABLE "libro_autor" ADD PRIMARY KEY ("id");

-- ----------------------------
-- Uniques structure for table libros
-- ----------------------------
ALTER TABLE "libros" ADD UNIQUE ("isbn");

-- ----------------------------
-- Primary Key structure for table libros
-- ----------------------------
ALTER TABLE "libros" ADD PRIMARY KEY ("idlibro");

-- ----------------------------
-- Foreign Key structure for table "libro_autor"
-- ----------------------------
ALTER TABLE "libro_autor" ADD FOREIGN KEY ("idautor") REFERENCES "autores" ("idautor") ON DELETE NO ACTION ON UPDATE NO ACTION;
ALTER TABLE "libro_autor" ADD FOREIGN KEY ("idlibro") REFERENCES "libros" ("idlibro") ON DELETE NO ACTION ON UPDATE NO ACTION;
