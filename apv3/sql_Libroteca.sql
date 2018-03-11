
CREATE TABLE autores
(
  idautor serial NOT NULL,
  nombre character varying(200) NOT NULL,
  apaterno character varying(100) NOT NULL,
  amaterno character varying(100),
  nacionalidad character varying(100),
  estatus bit(1) DEFAULT B'1'::"bit",
  CONSTRAINT autores_pkey PRIMARY KEY (idautor)
)

CREATE TABLE bibliotecarios
(
  idbiblio serial NOT NULL,
  nombre character varying(100) NOT NULL,
  paterno character varying(100) NOT NULL,
  materno character varying(100) NOT NULL,
  direccion character varying(200) NOT NULL,
  ciudad character varying(100) NOT NULL,
  pais character varying(100) NOT NULL,
  cp character varying(5) NOT NULL,
  telefono character varying(200) NOT NULL,
  correo character varying(200) NOT NULL,
  puesto character varying(100) NOT NULL,
  fechacon date NOT NULL,
  estatus bit(1) NOT NULL DEFAULT B'1'::"bit",
  CONSTRAINT biblioteca_pkey PRIMARY KEY (idbiblio)
)

CREATE TABLE libro_autor
(
  id serial NOT NULL,
  idlibro integer NOT NULL,
  idautor integer NOT NULL,
  estatus bit(1) NOT NULL DEFAULT B'1'::"bit",
  CONSTRAINT libro_autor_pkey PRIMARY KEY (id),
  CONSTRAINT libro_autor_idautor_fkey FOREIGN KEY (idautor)
      REFERENCES autores (idautor) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT libro_autor_idlibro_fkey FOREIGN KEY (idlibro)
      REFERENCES libros (idlibro) MATCH SIMPLE
      ON UPDATE NO ACTION ON DELETE NO ACTION
)

CREATE TABLE libros
(
  idlibro serial NOT NULL,
  isbn character varying(100) NOT NULL,
  titulo character varying(200) NOT NULL,
  editorial character varying(200) NOT NULL,
  numeroe character varying(100) NOT NULL,
  anio character varying(5) NOT NULL,
  estatus bit(1) NOT NULL DEFAULT B'1'::"bit",
  CONSTRAINT libros_pkey PRIMARY KEY (idlibro)
)

CREATE TABLE venta
(
  id serial NOT NULL,
  idlibro integer NOT NULL,
  idautor integer NOT NULL,
  monto integer NOT NULL,
  fecha date NOT NULL
)



