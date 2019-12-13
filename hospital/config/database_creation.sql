----------
-- SURGEON

create schema surgeon;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA surgeon to dis_rest;

create table surgeon.appointments_temp (
	id integer not null,
	appointment_date date,
	appointment_success boolean null,
	myself_success boolean null
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

create table surgeon.appointments (
	id integer not null,
	appointment_date date
);
grant all privileges on table surgeon.appointments_temp to dis_rest;

---------------
--  ANESTHETIST

create schema anesthetist;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA anesthetist to dis_rest;

create table anesthetist.appointments_temp (
	id integer not null,
	appointment_date date,
	appointment_success boolean null,
	myself_success boolean null
);
grant all privileges on table anesthetist.appointments_temp to dis_rest;

create table anesthetist.appointments (
	id integer not null,
	appointment_date date
);
grant all privileges on table anesthetist.appointments_temp to dis_rest;

-----------
-- HOSPITAL 

create schema hospital;
GRANT USAGE, SELECT ON ALL SEQUENCES IN SCHEMA hospital to dis_rest;

create table hospital.appointments_temp (
	id serial not null,
	appointment_date date,
	anesthetist_appointment_response boolean null,
	surgeon_appointment_response boolean null,
	appointment_success boolean null,
	myself_success boolean null
);
grant all privileges on table hospital.appointments_temp to dis_rest;

create table hospital.appointments (
	id integer not null,
	appointment_date date
);
grant all privileges on table hospital.appointments_temp to dis_rest;

-- insert into hospital.appointments_temp (appointment_date) values ('2019-10-01');