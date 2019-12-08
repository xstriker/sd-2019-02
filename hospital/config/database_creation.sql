----------
-- SURGEON

create schema surgeon;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA surgeon to dis_rest;

create table surgeon.appointments_temp (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

create table surgeon.appointments (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

---------------
--  ANESTHETIST

create schema anesthetist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA anesthetist to dis_rest;

create table anesthetist.appointments_temp (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table anesthetist.appointments_temp to dis_rest;

create table anesthetist.appointments (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table anesthetist.appointments_temp to dis_rest;

-----------
-- HOSPITAL 

create schema hospital;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA hospital to dis_rest;

create table hospital.appointments_temp (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	anesthetist_appointment_response boolean,
	surgeon_appointment_response boolean
);
grant all privileges on table hospital.appointments_temp to dis_rest;

create table hospital.appointments (
	id serial not null,
	appointment_time date,
	open_time timestamp,
	anesthetist_appointment_response boolean,
	surgeon_appointment_response boolean
);
grant all privileges on table hospital.appointments_temp to dis_rest;
