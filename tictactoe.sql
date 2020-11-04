DROP DATABASE "TicTacToe";
--
-- TOC entry 2826 (class 1262 OID 38324)
-- Name: TicTacToe; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "TicTacToe" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'English_United States.1252' LC_CTYPE = 'English_United States.1252';


ALTER DATABASE "TicTacToe" OWNER TO "postgres";

\connect "TicTacToe"

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

SET default_tablespace = '';

SET default_table_access_method = "heap";

--
-- TOC entry 203 (class 1259 OID 38328)
-- Name: Games; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Games" (
    "gameId" integer NOT NULL,
    "winner" character varying(1) NOT NULL
);


ALTER TABLE "public"."Games" OWNER TO "postgres";

--
-- TOC entry 202 (class 1259 OID 38326)
-- Name: Games_gameId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Games_gameId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Games_gameId_seq" OWNER TO "postgres";

--
-- TOC entry 2827 (class 0 OID 0)
-- Dependencies: 202
-- Name: Games_gameId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Games_gameId_seq" OWNED BY "public"."Games"."gameId";


--
-- TOC entry 204 (class 1259 OID 38360)
-- Name: PastMoves; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."PastMoves" (
    "gameId" integer NOT NULL,
    "move" character varying(2) NOT NULL,
    "player" character varying(1) NOT NULL
);


ALTER TABLE "public"."PastMoves" OWNER TO "postgres";

--
-- TOC entry 2691 (class 2604 OID 38331)
-- Name: Games gameId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Games" ALTER COLUMN "gameId" SET DEFAULT "nextval"('"public"."Games_gameId_seq"'::"regclass");


--
-- TOC entry 2693 (class 2606 OID 38333)
-- Name: Games Games_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Games"
    ADD CONSTRAINT "Games_pkey" PRIMARY KEY ("gameId");


--
-- TOC entry 2694 (class 2606 OID 38363)
-- Name: PastMoves PastM_gameId_fkey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."PastMoves"
    ADD CONSTRAINT "PastM_gameId_fkey" FOREIGN KEY ("gameId") REFERENCES "public"."Games"("gameId");


