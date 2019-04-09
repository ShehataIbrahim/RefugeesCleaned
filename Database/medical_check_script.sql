CREATE TABLE public.mst_interview_category
(
    interview_category_id integer NOT NULL,
    interview_category_version integer NOT NULL,
    interview_category_name character varying(256) COLLATE pg_catalog."default",
    create_date timestamp without time zone,
    update_date timestamp without time zone,
    CONSTRAINT mst_interview_category_pkey PRIMARY KEY (interview_category_id, interview_category_version)
);
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
);
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
);
CREATE TABLE public.medical_checkup_interviews
(
    resource_key uuid NOT NULL,
    reference_datetime timestamp without time zone NOT NULL,
    item_class_code character varying(100) COLLATE pg_catalog."default" NOT NULL,
    derived_from character varying(26) COLLATE pg_catalog."default" NOT NULL,
    json_data jsonb NOT NULL,
    created_by character varying(32) COLLATE pg_catalog."default" NOT NULL,
    created_at timestamp without time zone NOT NULL,
    updated_by character varying(32) COLLATE pg_catalog."default" NOT NULL,
    updated_at timestamp without time zone NOT NULL,
    CONSTRAINT pk_medical_checkup_interviews PRIMARY KEY (resource_key, reference_datetime, item_class_code, derived_from)
);


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

