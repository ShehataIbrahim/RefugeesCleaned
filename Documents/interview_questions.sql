-- Table: public.mst_interview_category

-- DROP TABLE public.mst_interview_category;

CREATE TABLE public.mst_interview_category
(
    interview_category_id integer NOT NULL,
    interview_category_version integer NOT NULL,
    interview_category_name character varying(256) COLLATE pg_catalog."default",
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    CONSTRAINT mst_interview_category_pkey PRIMARY KEY (interview_category_id, interview_category_version)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.mst_interview_category
    OWNER to postgres;
-- Table: public.mst_interview

-- DROP TABLE public.mst_interview;

CREATE TABLE public.mst_interview
(
    interview_category_id integer NOT NULL,
    interview_category_version integer NOT NULL,
    interview_id integer NOT NULL,
    interview_item character varying(1000) COLLATE pg_catalog."default",
    answer_type character varying(20) COLLATE pg_catalog."default",
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    CONSTRAINT mst_interview_pkey PRIMARY KEY (interview_category_id, interview_category_version, interview_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.mst_interview
    OWNER to postgres;
-- Table: public.mst_answer

-- DROP TABLE public.mst_answer;

CREATE TABLE public.mst_answer
(
    interview_category_id integer NOT NULL,
    interview_category_version integer NOT NULL,
    interview_id integer NOT NULL,
    answer_id integer NOT NULL,
    answer_item character varying(1000) COLLATE pg_catalog."default",
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    CONSTRAINT mst_answer_pkey PRIMARY KEY (interview_category_id, interview_category_version, interview_id, answer_id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.mst_answer
    OWNER to postgres;
-- View: public.interview_questions_view

-- DROP VIEW public.interview_questions_view;

CREATE OR REPLACE VIEW public.interview_questions_view AS
 SELECT row_number() OVER (ORDER BY interview_display.interview_category_id, interview_display.interview_id) AS id,
    interview_display.interview_category_id,
    interview_display.interview_category_version,
    interview_display.interview_id,
    interview_display.answer_id,
    interview_display.interview_item,
    interview_display.answer_type,
    interview_display.answer_item
   FROM ( SELECT DISTINCT mst_interview.interview_category_id,
            mst_interview.interview_category_version,
            mst_answer.interview_id,
                CASE
                    WHEN lower(mst_interview.answer_type::text) = 'list'::text THEN mst_answer.answer_id
                    ELSE 0
                END AS answer_id,
            mst_interview.interview_item,
            mst_interview.answer_type,
                CASE
                    WHEN lower(mst_interview.answer_type::text) = 'list'::text THEN mst_answer.answer_item
                    ELSE NULL::character varying
                END AS answer_item
           FROM mst_answer
             JOIN mst_interview ON mst_answer.interview_id = mst_interview.interview_id) interview_display;

ALTER TABLE public.interview_questions_view
    OWNER TO postgres;
SET DEFINE OFF;
Insert into "mst_interview_category" ("interview_category_id","interview_category_version","interview_category_name","create_date","update_date") values (1,1,'Initial overwhole health check',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview_category" ("interview_category_id","interview_category_version","interview_category_name","create_date","update_date") values (2,1,'Internal Medicine (IM)',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview_category" ("interview_category_id","interview_category_version","interview_category_name","create_date","update_date") values (3,1,'COPD',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview_category" ("interview_category_id","interview_category_version","interview_category_name","create_date","update_date") values (4,1,'Respiratory medicine',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview_category" ("interview_category_id","interview_category_version","interview_category_name","create_date","update_date") values (5,1,'Diabetes',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
REM INSERTING into "mst_interview"
SET DEFINE OFF;
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (1,1,1,'Do you have any allergy or any intolerance? (1: Yes, 2: No)','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (1,1,2,'Are you smoking? (1: Yes, 2: No)','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (1,1,3,'Do you have a depraved appetite? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,1,'Do you have certain symptom? (1: Yes, 2: No) 
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,2,'Since when have you the symptoms?
','FREE',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,3,'What is the symptoms?
','FREE',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,4,'Have you been consulted from some doctors recently? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,5,'Have you hospitalized or taken a surgery operation ever? (1: Yes, 2:No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,6,'What is the disease?
','FREE',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (2,1,7,'Do you take any medicine at present? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (3,1,1,'How many number of cigarrets, How much grams of  minced tobacco or how many time do you somke a day?
','FREE',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (3,1,2,'Have you often gotten phlegm in the back of your throat just after waking up in the morning? (1: Yes, 2:No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (3,1,3,'Do you have a wheeze (gurgle-gurgle, hiss-hiss) often? (1: Yes, 2:No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (4,1,1,'Have you sored in your sleep in the past 3 months? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (4,1,2,'Have your familit told you that your breathing may stop during your sleep in the past 3 months? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (4,1,3,'Did you feel sleepy strongly or getting asleep in the daytime or during the time you must be awake in the last 3 months? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (5,1,1,'Are you thirsty often? (1: Yes, 2: No)','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (5,1,2,'Have you ever been told that blood sugar is high? (1: Yes, 2; No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_interview" ("interview_category_id","interview_category_version","interview_id","interview_item","answer_type","create_date","update_date") values (5,1,3,'Does your family have diabetes? (1: Yes, 2: No)
','LIST',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));

SET DEFINE OFF;
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,1,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,2,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,3,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,1,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,4,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,5,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,7,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (3,1,2,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (3,1,3,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,1,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,2,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,3,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,1,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,2,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,3,1,'YES',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,1,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,2,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (1,1,3,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,1,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,4,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,5,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (2,1,7,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (3,1,2,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (3,1,3,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,1,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,2,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (4,1,3,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,1,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,2,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));
Insert into "mst_answer" ("interview_category_id","interview_category_version","interview_id","answer_id","answer_item","create_date","update_date") values (5,1,3,2,'NO',to_timestamp('2018-12-26 11:00:00.0','null'),to_timestamp('2018-12-26 11:00:00.0','null'));


	