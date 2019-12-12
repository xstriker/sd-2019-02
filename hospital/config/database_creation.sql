----------
-- SURGEON

create schema surgeon;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA surgeon to dis_rest;

create table surgeon.appointments_temp (
	id integer not null,
	appointment_time date unique,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

create table surgeon.appointments (
	id integer not null,
	appointment_time date unique,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

---------------
--  ANESTHETIST

create schema anesthetist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA anesthetist to dis_rest;

create table anesthetist.appointments_temp (
	id integer not null,
	appointment_time date unique,
	open_time timestamp,
	appointment_response boolean
);
grant all privileges on table anesthetist.appointments_temp to dis_rest;

create table anesthetist.appointments (
	id integer not null,
	appointment_time date unique,
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
	appointment_time date unique,
	open_time timestamp,
	anesthetist_appointment_response boolean null,
	surgeon_appointment_response boolean null
);
grant all privileges on table hospital.appointments_temp to dis_rest;

create table hospital.appointments (
	id integer not null,
	appointment_time date unique,
	open_time timestamp,
	anesthetist_appointment_response boolean null,
	surgeon_appointment_response boolean null
);
grant all privileges on table hospital.appointments_temp to dis_rest;

insert into hospital.appointments_temp (appointment_time, open_time, anesthetist_appointment_response, surgeon_appointment_response) values ('2019-10-01', now(), null, null);
