--
-- PostgreSQL database dump
--

-- Dumped from database version 12.3
-- Dumped by pg_dump version 12.3

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: public; Type: SCHEMA; Schema: -; Owner: postgres
--

CREATE SCHEMA public;


ALTER SCHEMA public OWNER TO postgres;

--
-- Name: SCHEMA public; Type: COMMENT; Schema: -; Owner: postgres
--

COMMENT ON SCHEMA public IS 'standard public schema';


SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: history; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.history (
    date character varying(40) NOT NULL,
    firstname character varying(40) NOT NULL,
    secondname character varying(40) NOT NULL,
    amount character varying(40),
    result character varying(40)
);


ALTER TABLE public.history OWNER TO postgres;

--
-- Name: valcurs; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.valcurs (
    date character varying(100) NOT NULL,
    id character varying(100) NOT NULL,
    numcode character varying(100) NOT NULL,
    charcode character varying(100) NOT NULL,
    nominal character varying(100) NOT NULL,
    name character varying(100) NOT NULL,
    value character varying(100) NOT NULL
);


ALTER TABLE public.valcurs OWNER TO postgres;

--
-- PostgreSQL database dump complete
--

