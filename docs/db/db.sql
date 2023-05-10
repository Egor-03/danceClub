CREATE TABLE place (
	id integer PRIMARY KEY AUTOINCREMENT,
	name string NOT NULL
);

CREATE TABLE role (
	id integer PRIMARY KEY AUTOINCREMENT,
	name varchar NOT NULL
);

CREATE TABLE persone (
	id integer PRIMARY KEY AUTOINCREMENT,
	role_id integer NOT NULL REFERENCES role (id),
	first_name varchar NOT NULL,
	second_name varchar NOT NULL,
	patronymic varchar NOT NULL,
	mail varchar NOT NULL
);

CREATE TABLE state (
	id integer PRIMARY KEY AUTOINCREMENT,
	state_name integer NOT NULL
);

CREATE TABLE section (
	id integer PRIMARY KEY AUTOINCREMENT,
	section_name varchar NOT NULL
);

CREATE TABLE groupe (
	id integer PRIMARY KEY AUTOINCREMENT,
	name varchar NOT NULL,
	person_id integer NOT NULL REFERENCES persone (id),
	section_id integer NOT NULL REFERENCES section (id) ,
	data date ,
	state_id integer NOT NULL REFERENCES state (id)
);

CREATE TABLE training (
	id integer PRIMARY KEY AUTOINCREMENT,
	place_id integer NOT NULL REFERENCES place (id),
	coach_id integer NOT NULL REFERENCES persone (id),
	groupe_id integer NOT NULL REFERENCES groupe (id),
	data date 
);








