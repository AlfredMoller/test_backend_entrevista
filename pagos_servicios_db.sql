--
-- PostgreSQL database dump
--

-- Dumped from database version 10.17
-- Dumped by pg_dump version 10.17

-- Started on 2024-08-17 04:56:38

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
-- TOC entry 1 (class 3079 OID 12924)
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- TOC entry 2895 (class 0 OID 0)
-- Dependencies: 1
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 5968153)
-- Name: ciudad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.ciudad (
    id_ciudad integer NOT NULL,
    nombre_ciudad character varying(100) NOT NULL,
    codigo_distrito integer,
    id_departamento integer
);


ALTER TABLE public.ciudad OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 5968151)
-- Name: ciudad_id_ciudad_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.ciudad_id_ciudad_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.ciudad_id_ciudad_seq OWNER TO postgres;

--
-- TOC entry 2896 (class 0 OID 0)
-- Dependencies: 198
-- Name: ciudad_id_ciudad_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.ciudad_id_ciudad_seq OWNED BY public.ciudad.id_ciudad;


--
-- TOC entry 209 (class 1259 OID 5968223)
-- Name: cuentas; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.cuentas (
    id_cuenta integer NOT NULL,
    id_usuario integer NOT NULL,
    saldo numeric(10,2) DEFAULT 0.00 NOT NULL,
    fecha_actualizacion timestamp without time zone DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.cuentas OWNER TO postgres;

--
-- TOC entry 208 (class 1259 OID 5968221)
-- Name: cuentas_id_cuenta_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.cuentas_id_cuenta_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cuentas_id_cuenta_seq OWNER TO postgres;

--
-- TOC entry 2897 (class 0 OID 0)
-- Dependencies: 208
-- Name: cuentas_id_cuenta_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.cuentas_id_cuenta_seq OWNED BY public.cuentas.id_cuenta;


--
-- TOC entry 201 (class 1259 OID 5968161)
-- Name: departamento; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.departamento (
    id_departamento integer NOT NULL,
    nombre_departamento character varying,
    id_pais integer
);


ALTER TABLE public.departamento OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 5968159)
-- Name: departamento_id_departamento_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.departamento_id_departamento_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.departamento_id_departamento_seq OWNER TO postgres;

--
-- TOC entry 2898 (class 0 OID 0)
-- Dependencies: 200
-- Name: departamento_id_departamento_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.departamento_id_departamento_seq OWNED BY public.departamento.id_departamento;


--
-- TOC entry 207 (class 1259 OID 5968204)
-- Name: deudas_servicios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.deudas_servicios (
    id_deuda integer NOT NULL,
    id_usuario integer NOT NULL,
    id_servicio integer NOT NULL,
    numero_referencia_comprobante character varying(50) NOT NULL,
    monto_deuda_total numeric(10,2) NOT NULL,
    monto_abonado numeric(10,2) DEFAULT 0.00,
    fecha_vencimiento date NOT NULL,
    estado_deuda character varying(20) DEFAULT 'Pendiente'::character varying NOT NULL
);


ALTER TABLE public.deudas_servicios OWNER TO postgres;

--
-- TOC entry 206 (class 1259 OID 5968202)
-- Name: deudas_servicios_id_deuda_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.deudas_servicios_id_deuda_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.deudas_servicios_id_deuda_seq OWNER TO postgres;

--
-- TOC entry 2899 (class 0 OID 0)
-- Dependencies: 206
-- Name: deudas_servicios_id_deuda_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.deudas_servicios_id_deuda_seq OWNED BY public.deudas_servicios.id_deuda;


--
-- TOC entry 211 (class 1259 OID 5984622)
-- Name: pagos; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pagos (
    id_pago integer NOT NULL,
    id_usuario integer NOT NULL,
    id_deuda integer NOT NULL,
    id_servicio integer NOT NULL,
    monto_pago numeric(10,2) NOT NULL,
    fecha_pago timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    estado_pago character varying(50) DEFAULT 'Completado'::character varying
);


ALTER TABLE public.pagos OWNER TO postgres;

--
-- TOC entry 210 (class 1259 OID 5984620)
-- Name: pagos_id_pago_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pagos_id_pago_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pagos_id_pago_seq OWNER TO postgres;

--
-- TOC entry 2900 (class 0 OID 0)
-- Dependencies: 210
-- Name: pagos_id_pago_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pagos_id_pago_seq OWNED BY public.pagos.id_pago;


--
-- TOC entry 197 (class 1259 OID 5968145)
-- Name: pais; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.pais (
    id_pais integer NOT NULL,
    nombre_pais character varying(100) NOT NULL,
    codigo_iso_pais2 character varying(6) NOT NULL,
    codigo_iso_pais3 character varying(6) NOT NULL
);


ALTER TABLE public.pais OWNER TO postgres;

--
-- TOC entry 196 (class 1259 OID 5968143)
-- Name: pais_id_pais_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.pais_id_pais_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.pais_id_pais_seq OWNER TO postgres;

--
-- TOC entry 2901 (class 0 OID 0)
-- Dependencies: 196
-- Name: pais_id_pais_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.pais_id_pais_seq OWNED BY public.pais.id_pais;


--
-- TOC entry 205 (class 1259 OID 5968193)
-- Name: servicios; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.servicios (
    id_servicio integer NOT NULL,
    nombre_servicio character varying(100) NOT NULL,
    descripcion text
);


ALTER TABLE public.servicios OWNER TO postgres;

--
-- TOC entry 204 (class 1259 OID 5968191)
-- Name: servicios_id_servicio_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.servicios_id_servicio_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.servicios_id_servicio_seq OWNER TO postgres;

--
-- TOC entry 2902 (class 0 OID 0)
-- Dependencies: 204
-- Name: servicios_id_servicio_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.servicios_id_servicio_seq OWNED BY public.servicios.id_servicio;


--
-- TOC entry 203 (class 1259 OID 5968181)
-- Name: usuario; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.usuario (
    id_usuario integer NOT NULL,
    nombre_usuario character varying(100),
    apellido_usuario character varying(100),
    email_usuario character varying(200),
    telefono_usuario character varying(100),
    clave_usuario character varying(100),
    cedula_usuario character varying(20)
);


ALTER TABLE public.usuario OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 5968179)
-- Name: usuario_id_usuario_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.usuario_id_usuario_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.usuario_id_usuario_seq OWNER TO postgres;

--
-- TOC entry 2903 (class 0 OID 0)
-- Dependencies: 202
-- Name: usuario_id_usuario_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.usuario_id_usuario_seq OWNED BY public.usuario.id_usuario;


--
-- TOC entry 2716 (class 2604 OID 5968156)
-- Name: ciudad id_ciudad; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad ALTER COLUMN id_ciudad SET DEFAULT nextval('public.ciudad_id_ciudad_seq'::regclass);


--
-- TOC entry 2723 (class 2604 OID 5968226)
-- Name: cuentas id_cuenta; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas ALTER COLUMN id_cuenta SET DEFAULT nextval('public.cuentas_id_cuenta_seq'::regclass);


--
-- TOC entry 2717 (class 2604 OID 5968164)
-- Name: departamento id_departamento; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departamento ALTER COLUMN id_departamento SET DEFAULT nextval('public.departamento_id_departamento_seq'::regclass);


--
-- TOC entry 2720 (class 2604 OID 5968207)
-- Name: deudas_servicios id_deuda; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deudas_servicios ALTER COLUMN id_deuda SET DEFAULT nextval('public.deudas_servicios_id_deuda_seq'::regclass);


--
-- TOC entry 2726 (class 2604 OID 5984625)
-- Name: pagos id_pago; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagos ALTER COLUMN id_pago SET DEFAULT nextval('public.pagos_id_pago_seq'::regclass);


--
-- TOC entry 2715 (class 2604 OID 5968148)
-- Name: pais id_pais; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais ALTER COLUMN id_pais SET DEFAULT nextval('public.pais_id_pais_seq'::regclass);


--
-- TOC entry 2719 (class 2604 OID 5968196)
-- Name: servicios id_servicio; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicios ALTER COLUMN id_servicio SET DEFAULT nextval('public.servicios_id_servicio_seq'::regclass);


--
-- TOC entry 2718 (class 2604 OID 5968184)
-- Name: usuario id_usuario; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario ALTER COLUMN id_usuario SET DEFAULT nextval('public.usuario_id_usuario_seq'::regclass);


--
-- TOC entry 2875 (class 0 OID 5968153)
-- Dependencies: 199
-- Data for Name: ciudad; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.ciudad (id_ciudad, nombre_ciudad, codigo_distrito, id_departamento) FROM stdin;
1	Concepción	1	1
2	Belen	2	1
3	Horqueta	3	1
4	Loreto	4	1
5	San Carlos del Apa	5	1
6	San Lazaro	6	1
7	Yby Yau	7	1
8	Azotey	8	1
9	Sargento Jose Felix Lopez	9	1
10	San Alfredo	10	1
11	Paso Barreto	11	1
12	San Pedro Del Ycuamandyyu	1	2
13	Antequera	2	2
14	Chore	3	2
15	General Elizardo Aquino	4	2
16	Itacurubí Del Rosario	5	2
17	Lima	6	2
18	Nueva Germania	7	2
19	San Estanislao	8	2
20	San Pablo	9	2
21	Tacuati	10	2
22	Union	11	2
23	25 De Diciembre	12	2
24	Villa Del Rosario	13	2
25	General Francisco Isidoro Resquin	14	2
26	Yataity Del Norte	15	2
27	Guajayvi	16	2
28	Capiibary	17	2
29	Santa Rosa Del Aguaray	18	2
30	Yrybücua	19	2
31	Liberacion	20	2
32	Caacupe	1	3
33	Altos	2	3
34	Arroyos Y Esteros	3	3
35	Atyra	4	3
36	Caraguatay	5	3
37	Emboscada	6	3
38	Eusebio Ayala	7	3
39	Isla Pucu	8	3
40	Itacurubi De La Cordillera	9	3
41	Juan De Mena	10	3
42	Loma Grande	11	3
43	Mbocayaty Del Yhaguy	12	3
44	Nueva Colombia	13	3
45	Piribebuy	14	3
46	Primero De Marzo	15	3
47	San Bernardino	16	3
48	Santa Elena	17	3
49	Tobati	18	3
50	Valenzuela	19	3
51	San Jose Obrero	20	3
52	Villarrica	1	4
53	Borja	2	4
54	Capitan Mauricio Jose Troche	3	4
55	Coronel Martinez	4	4
56	Felix Perez Cardozo	5	4
57	Gral. Eugenio A. Garay	6	4
58	Independencia	7	4
59	Itape	8	4
60	Iturbe	9	4
61	Jose Fassardi	10	4
62	Mbocayaty	11	4
63	Natalicio Talavera	12	4
64	Numi	13	4
65	San Salvador	14	4
66	Yataity	15	4
67	Doctor Bottrell	16	4
68	Paso Yobai	17	4
69	Tebicuary	18	4
70	Coronel Oviedo	1	5
71	Caaguazu	2	5
72	Carayao	3	5
73	Dr. Cecilio Baez	4	5
74	Santa Rosa Del Mbutuy	5	5
75	Dr. Juan Manuel Frutos	6	5
76	Repatriacion	7	5
77	Nueva Londres	8	5
78	San Joaquin	9	5
79	San Jose De Los Arroyos	10	5
80	Yhu	11	5
81	Dr. J. Eulogio Estigarribia	12	5
82	R.I. 3 Corrales	13	5
83	Raul Arsenio Oviedo	14	5
84	Jose Domingo Ocampos	15	5
85	Mariscal Francisco Solano Lopez	16	5
86	La Pastora	17	5
87	3 De Febrero	18	5
88	Simon Bolivar	19	5
89	Vaqueria	20	5
90	Tembiapora	21	5
91	Nueva Toledo	22	5
92	Caazapa	1	6
93	Abai	2	6
94	Buena Vista	3	6
95	Dr. Moises S. Bertoni	4	6
96	Gral. Higinio Morinigo	5	6
97	Maciel	6	6
98	San Juan Nepomuceno	7	6
99	Tavai	8	6
100	Yegros	9	6
101	Yuty	10	6
102	3 De Mayo	11	6
103	Encarnacion	1	7
104	Bella Vista	2	7
105	Cambyreta	3	7
106	Capitan Meza	4	7
107	Capitan Miranda	5	7
108	Nueva Alborada	6	7
109	Carmen Del Parana	7	7
110	Coronel Bogado	8	7
111	Carlos Antonio Lopez	9	7
112	Natalio	10	7
113	Fram	11	7
114	General Artigas	12	7
115	General Delgado	13	7
116	Hohenau	14	7
117	Jesus	15	7
118	Jose Leandro Oviedo	16	7
119	Obligado	17	7
120	Mayor Julio Dionisio Otano	18	7
121	San Cosme Y Damian	19	7
122	San Pedro Del Parana	20	7
123	San Rafael Del Parana	21	7
124	Trinidad	22	7
125	Edelira	23	7
126	Tomas Romero Pereira	24	7
127	Alto Vera	25	7
128	La Paz	26	7
129	Yatytay	27	7
130	San Juan Del Parana	28	7
131	Pirapo	29	7
132	Itapua Poty	30	7
133	San Juan Bautista De Las Misiones	1	8
134	Ayolas	2	8
135	San Ignacio	3	8
136	San Miguel	4	8
137	San Patricio	5	8
138	Santa Maria	6	8
139	Santa Rosa	7	8
140	Santiago	8	8
141	Villa Florida	9	8
142	Yabebyry	10	8
143	Paraguari	1	9
144	Acahay	2	9
145	Caapucu	3	9
146	Caballero	4	9
147	Carapegua	5	9
148	Escobar	6	9
149	La Colmena	7	9
150	Mbuyapey	8	9
151	Pirayu	9	9
152	Quindi	10	9
153	Quyquyho	11	9
154	Roque Gonzalez De Santa Cruz	12	9
155	Sapucai	13	9
156	Tebicuary-Mi	14	9
157	Yaguarón	15	9
158	Ybycui	16	9
159	Ybytymi	17	9
160	Ciudad Del Este	1	10
161	Presidente Franco	2	10
162	Domingo Martinez De Irala	3	10
163	Dr. Juan Leon Mallorquin	4	10
164	Hernandarias	5	10
165	Itakyry	6	10
166	Juan E. O Leary	7	10
167	Nacunday	8	10
168	Ygauazu	9	10
169	Los Cedrales	10	10
170	Minga Guazu	11	10
171	San Cristobal	12	10
172	Santa Rita	13	10
173	Naranjal	14	10
174	Santa Rosa Del Monday	15	10
175	Minga Pora	16	10
176	Mbaracayu	17	10
177	San Alberto	18	10
178	Iruna	19	10
179	Santa Fe Del Parana	20	10
180	Tavapy	21	10
181	Dr. Raul Pena	22	10
182	Aregua	1	11
183	Capiata	2	11
184	Fernando De La Mora	3	11
185	Guarambare	4	11
186	Ita	5	11
187	Itaugua	6	11
188	Lambare	7	11
189	Limpio	8	11
190	Luque	9	11
191	Mariano Roque Alonso	10	11
192	Nueva Italia	11	11
193	Nemby	12	11
194	San Antonio	13	11
195	San Lorenzo	14	11
196	Villa Elisa	15	11
197	Villeta	16	11
198	Ypacarai	17	11
199	Ypane	18	11
200	J. Augusto Saldivar	19	11
201	Pilar	1	12
202	Alberdi	2	12
203	Cerrito	3	12
204	Desmochados	4	12
205	Gral. Jose Eduvigis Diaz	5	12
206	Guazu-Cua	6	12
207	Humaita	7	12
208	Isla Umbu	8	12
209	Laureles	9	12
210	Mayor Jose Dejesus Martinez	10	12
211	Paso De Patria	11	12
212	San Juan Bautista De Neembucu	12	12
213	Tacuaras	13	12
214	Villa Franca	14	12
215	Villa Oliva	15	12
216	Villalbin	16	12
217	Pedro Juan Caballero	1	13
218	Bella Vista	2	13
219	Capitan Bado	3	13
220	Zanja Pytã	4	13
221	Karapai	5	13
222	Salto Del Guaira	1	14
223	Corpus Christi	2	14
224	Villa Curuguaty	3	14
225	Villa Ygatimi	4	14
226	Itanara	5	14
227	Ypejhu	6	14
228	Francisco Caballero Alvarez	7	14
229	Katueté	8	14
230	La Paloma Del Espiritu Santo	9	14
231	Nueva Esperanza	10	14
232	Yasy Cañy	11	14
233	Ybyrarobaná	12	14
234	Yby Pytã	13	14
235	Benjamin Aceval	2	15
236	Puerto Pinasco	3	15
237	Villa Hayes	4	15
238	Nanawa	5	15
239	Jose Falcon	6	15
240	Tte. 1° Manuel Irala Fernandez	7	15
241	Teniente Esteban Martinez	8	15
242	General Jose Maria Bruguez	9	15
243	Mariscal Jose Felix Estigarribia	2	16
244	Filadelfia	3	16
245	Loma Plata	4	16
246	Fuerte Olimpo	1	17
247	Puerto Casado	2	17
248	Bahia Negra	3	17
249	Carmelo Peralta	4	17
252	La Plata	\N	18
253	Mar del Plata	\N	18
254	San Fernando del Valle de Catamarca	\N	19
255	Resistencia	\N	20
256	Rawson	\N	21
257	Comodoro Rivadavia	\N	21
258	Córdoba	\N	22
259	Villa Carlos Paz	\N	22
260	Corrientes	\N	23
261	Paraná	\N	24
262	Concordia	\N	24
263	Formosa	\N	25
264	San Salvador de Jujuy	\N	26
265	Santa Rosa	\N	27
266	La Rioja	\N	28
267	Mendoza	\N	29
268	San Rafael	\N	29
269	Posadas	\N	30
270	Neuquén	\N	31
271	Viedma	\N	32
272	Salta	\N	33
273	San Juan	\N	34
274	San Luis	\N	35
275	Río Gallegos	\N	36
276	Santa Fe	\N	37
277	Rosario	\N	37
278	Santiago del Estero	\N	38
279	Ushuaia	\N	39
280	San Miguel de Tucumán	\N	40
\.


--
-- TOC entry 2885 (class 0 OID 5968223)
-- Dependencies: 209
-- Data for Name: cuentas; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.cuentas (id_cuenta, id_usuario, saldo, fecha_actualizacion) FROM stdin;
1	5	250000.00	2024-08-15 10:30:00
2	5	150000.00	2024-08-15 11:45:00
3	5	300000.00	2024-08-14 08:20:00
\.


--
-- TOC entry 2877 (class 0 OID 5968161)
-- Dependencies: 201
-- Data for Name: departamento; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.departamento (id_departamento, nombre_departamento, id_pais) FROM stdin;
1	Concepción	139
2	San Pedro	139
3	Cordillera	139
4	Guairá	139
5	Caaguazú	139
6	Caazapá	139
7	Itapúa	139
8	Misiones	139
9	Paraguarí	139
10	Alto Paraná	139
11	Central	139
12	Ñeembucú	139
13	Amambay	139
14	Canindeyú	139
15	Presidente Hayes	139
16	Alto Paraguay	139
17	Boquerón	139
18	Buenos Aires	9
19	Catamarca	9
20	Chaco	9
21	Chubut	9
22	Córdoba	9
23	Corrientes	9
24	Entre Ríos	9
25	Formosa	9
26	Jujuy	9
27	La Pampa	9
28	La Rioja	9
29	Mendoza	9
30	Misiones	9
31	Neuquén	9
32	Río Negro	9
33	Salta	9
34	San Juan	9
35	San Luis	9
36	Santa Cruz	9
37	Santa Fe	9
38	Santiago del Estero	9
39	Tierra del Fuego	9
40	Tucumán	9
\.


--
-- TOC entry 2883 (class 0 OID 5968204)
-- Dependencies: 207
-- Data for Name: deudas_servicios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.deudas_servicios (id_deuda, id_usuario, id_servicio, numero_referencia_comprobante, monto_deuda_total, monto_abonado, fecha_vencimiento, estado_deuda) FROM stdin;
1	5	1	123456789	5000000.00	0.00	2024-09-15	Pendiente
2	5	4	123456789	140000.00	420000.00	2024-09-15	Cancelado
\.


--
-- TOC entry 2887 (class 0 OID 5984622)
-- Dependencies: 211
-- Data for Name: pagos; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pagos (id_pago, id_usuario, id_deuda, id_servicio, monto_pago, fecha_pago, estado_pago) FROM stdin;
4	5	2	4	140000.00	2024-08-17 01:45:28.722388	Pagado
\.


--
-- TOC entry 2873 (class 0 OID 5968145)
-- Dependencies: 197
-- Data for Name: pais; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.pais (id_pais, nombre_pais, codigo_iso_pais2, codigo_iso_pais3) FROM stdin;
1	Afganistán	AF	AFG
2	Albania	AL	ALB
3	Alemania	DE	DEU
4	Andorra	AD	AND
5	Angola	AO	AGO
6	Antigua y Barbuda	AG	ATG
7	Arabia Saudita	SA	SAU
8	Argelia	DZ	DZA
9	Argentina	AR	ARG
10	Armenia	AM	ARM
11	Australia	AU	AUS
12	Austria	AT	AUT
13	Azerbaiyán	AZ	AZE
14	Bahamas	BS	BHS
15	Bangladés	BD	BGD
16	Barbados	BB	BRB
17	Baréin	BH	BHR
18	Bélgica	BE	BEL
19	Belice	BZ	BLZ
20	Benín	BJ	BEN
21	Bielorrusia	BY	BLR
22	Birmania	MM	MMR
23	Bolivia	BO	BOL
24	Bosnia y Herzegovina	BA	BIH
25	Botsuana	BW	BWA
26	Brasil	BR	BRA
27	Brunéi	BN	BRN
28	Bulgaria	BG	BGR
29	Burkina Faso	BF	BFA
30	Burundi	BI	BDI
31	Bután	BT	BTN
32	Cabo Verde	CV	CPV
33	Camboya	KH	KHM
34	Camerún	CM	CMR
35	Canadá	CA	CAN
36	Catar	QA	QAT
37	Chad	TD	TCD
38	Chile	CL	CHL
39	China	CN	CHN
40	Chipre	CY	CYP
41	Ciudad del Vaticano	VA	VAT
42	Colombia	CO	COL
43	Comoras	KM	COM
44	Corea del Norte	KP	PRK
45	Corea del Sur	KR	KOR
46	Costa de Marfil	CI	CIV
47	Costa Rica	CR	CRI
48	Croacia	HR	HRV
49	Cuba	CU	CUB
50	Dinamarca	DK	DNK
51	Dominica	DM	DMA
52	Ecuador	EC	ECU
53	Egipto	EG	EGY
54	El Salvador	SV	SLV
55	Emiratos Árabes Unidos	AE	ARE
56	Eritrea	ER	ERI
57	Eslovaquia	SK	SVK
58	Eslovenia	SI	SVN
59	España	ES	ESP
60	Estados Unidos	US	USA
61	Estonia	EE	EST
62	Esuatini	SZ	SWZ
63	Etiopía	ET	ETH
64	Filipinas	PH	PHL
65	Finlandia	FI	FIN
66	Fiyi	FJ	FJI
67	Francia	FR	FRA
68	Gabón	GA	GAB
69	Gambia	GM	GMB
70	Georgia	GE	GEO
71	Ghana	GH	GHA
72	Granada	GD	GRD
73	Grecia	GR	GRC
74	Guatemala	GT	GTM
75	Guyana	GY	GUY
76	Guinea	GN	GIN
77	Guinea-Bisáu	GW	GNB
78	Guinea Ecuatorial	GQ	GNQ
79	Haití	HT	HTI
80	Honduras	HN	HND
81	Hungría	HU	HUN
82	India	IN	IND
83	Indonesia	ID	IDN
84	Irak	IQ	IRQ
85	Irán	IR	IRN
86	Irlanda	IE	IRL
87	Islandia	IS	ISL
88	Islas Marshall	MH	MHL
89	Islas Salomón	SB	SLB
90	Israel	IL	ISR
91	Italia	IT	ITA
92	Jamaica	JM	JAM
93	Japón	JP	JPN
94	Jordania	JO	JOR
95	Kazajistán	KZ	KAZ
96	Kenia	KE	KEN
97	Kirguistán	KG	KGZ
98	Kiribati	KI	KIR
99	Kuwait	KW	KWT
100	Laos	LA	LAO
101	Lesoto	LS	LSO
102	Letonia	LV	LVA
103	Líbano	LB	LBN
104	Liberia	LR	LBR
105	Libia	LY	LBY
106	Liechtenstein	LI	LIE
107	Lituania	LT	LTU
108	Luxemburgo	LU	LUX
109	Madagascar	MG	MDG
110	Malasia	MY	MYS
111	Malaui	MW	MWI
112	Maldivas	MV	MDV
113	Malí	ML	MLI
114	Malta	MT	MLT
115	Marruecos	MA	MAR
116	Mauricio	MU	MUS
117	Mauritania	MR	MRT
118	México	MX	MEX
119	Micronesia	FM	FSM
120	Moldavia	MD	MDA
121	Mónaco	MC	MCO
122	Mongolia	MN	MNG
123	Montenegro	ME	MNE
124	Mozambique	MZ	MOZ
125	Namibia	NA	NAM
126	Nauru	NR	NRU
127	Nepal	NP	NPL
128	Nicaragua	NI	NIC
129	Níger	NE	NER
130	Nigeria	NG	NGA
131	Noruega	NO	NOR
132	Nueva Zelanda	NZ	NZL
133	Omán	OM	OMN
134	Países Bajos	NL	NLD
135	Pakistán	PK	PAK
136	Palaos	PW	PLW
137	Panamá	PA	PAN
138	Papúa Nueva Guinea	PG	PNG
139	Paraguay	PY	PRY
140	Perú	PE	PER
141	Polonia	PL	POL
142	Portugal	PT	PRT
143	Reino Unido	GB	GBR
144	República Centroafricana	CF	CAF
145	República Checa	CZ	CZE
146	República del Congo	CG	COG
147	República Democrática del Congo	CD	COD
148	República Dominicana	DO	DOM
149	República Sudafricana	ZA	ZAF
150	Ruanda	RW	RWA
151	Rumania	RO	ROU
152	Rusia	RU	RUS
153	Samoa	WS	WSM
154	San Cristóbal y Nieves	KN	KNA
155	San Marino	SM	SMR
156	San Vicente y las Granadinas	VC	VCT
157	Santa Lucía	LC	LCA
158	Santo Tomé y Príncipe	ST	STP
159	Senegal	SN	SEN
160	Serbia	RS	SRB
161	Seychelles	SC	SYC
162	Sierra Leona	SL	SLE
163	Singapur	SG	SGP
164	Siria	SY	SYR
165	Somalia	SO	SOM
166	Sri Lanka	LK	LKA
167	Suazilandia	SZ	SWZ
168	Sudán	SD	SDN
169	Sudán del Sur	SS	SSD
170	Suecia	SE	SWE
171	Suiza	CH	CHE
172	Surinam	SR	SUR
173	Tailandia	TH	THA
174	Tanzania	TZ	TZA
175	Tayikistán	TJ	TJK
176	Timor Oriental	TL	TLS
177	Togo	TG	TGO
178	Tonga	TO	TON
179	Trinidad y Tobago	TT	TTO
180	Túnez	TN	TUN
181	Turkmenistán	TM	TKM
182	Turquía	TR	TUR
183	Tuvalu	TV	TUV
184	Ucrania	UA	UKR
185	Uganda	UG	UGA
186	Uruguay	UY	URY
187	Uzbekistán	UZ	UZB
188	Vanuatu	VU	VUT
189	Venezuela	VE	VEN
190	Vietnam	VN	VNM
191	Yemen	YE	YEM
192	Yibuti	DJ	DJI
193	Zambia	ZM	ZMB
194	Zimbabue	ZW	ZWE
\.


--
-- TOC entry 2881 (class 0 OID 5968193)
-- Dependencies: 205
-- Data for Name: servicios; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.servicios (id_servicio, nombre_servicio, descripcion) FROM stdin;
1	ANDE	Administración Nacional de Electricidad - Proveedor de electricidad en Paraguay.
2	ESSAP	Empresa de Servicios Sanitarios del Paraguay S.A. - Proveedor de agua potable y alcantarillado.
3	Tigo	Proveedor de servicios de telecomunicaciones, incluyendo telefonía móvil, internet y televisión por cable.
4	Personal	Proveedor de servicios de telecomunicaciones, incluyendo telefonía móvil e internet.
5	Claro	Proveedor de servicios de telecomunicaciones, incluyendo telefonía móvil, internet y televisión por cable.
\.


--
-- TOC entry 2879 (class 0 OID 5968181)
-- Dependencies: 203
-- Data for Name: usuario; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.usuario (id_usuario, nombre_usuario, apellido_usuario, email_usuario, telefono_usuario, clave_usuario, cedula_usuario) FROM stdin;
5	Ignacio	Quintana	ignacio@example.com	123456789	LODkAPspkRABWevAMm/Nvg==	1234567
7	Diego	Duarte	diego@example.com	+595 983211667	+BkeIoSE85kqB6AwKDSEnQ==	4789999
\.


--
-- TOC entry 2904 (class 0 OID 0)
-- Dependencies: 198
-- Name: ciudad_id_ciudad_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.ciudad_id_ciudad_seq', 1, false);


--
-- TOC entry 2905 (class 0 OID 0)
-- Dependencies: 208
-- Name: cuentas_id_cuenta_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.cuentas_id_cuenta_seq', 3, true);


--
-- TOC entry 2906 (class 0 OID 0)
-- Dependencies: 200
-- Name: departamento_id_departamento_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.departamento_id_departamento_seq', 1, false);


--
-- TOC entry 2907 (class 0 OID 0)
-- Dependencies: 206
-- Name: deudas_servicios_id_deuda_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.deudas_servicios_id_deuda_seq', 2, true);


--
-- TOC entry 2908 (class 0 OID 0)
-- Dependencies: 210
-- Name: pagos_id_pago_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pagos_id_pago_seq', 6, true);


--
-- TOC entry 2909 (class 0 OID 0)
-- Dependencies: 196
-- Name: pais_id_pais_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.pais_id_pais_seq', 1, false);


--
-- TOC entry 2910 (class 0 OID 0)
-- Dependencies: 204
-- Name: servicios_id_servicio_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.servicios_id_servicio_seq', 5, true);


--
-- TOC entry 2911 (class 0 OID 0)
-- Dependencies: 202
-- Name: usuario_id_usuario_seq; Type: SEQUENCE SET; Schema: public; Owner: postgres
--

SELECT pg_catalog.setval('public.usuario_id_usuario_seq', 7, true);


--
-- TOC entry 2732 (class 2606 OID 5968158)
-- Name: ciudad ciudad_temp_pkey1; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.ciudad
    ADD CONSTRAINT ciudad_temp_pkey1 PRIMARY KEY (id_ciudad);


--
-- TOC entry 2742 (class 2606 OID 5968230)
-- Name: cuentas cuentas_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas
    ADD CONSTRAINT cuentas_pkey PRIMARY KEY (id_cuenta);


--
-- TOC entry 2734 (class 2606 OID 5968169)
-- Name: departamento departamento_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.departamento
    ADD CONSTRAINT departamento_pkey PRIMARY KEY (id_departamento);


--
-- TOC entry 2740 (class 2606 OID 5968210)
-- Name: deudas_servicios deudas_servicios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deudas_servicios
    ADD CONSTRAINT deudas_servicios_pkey PRIMARY KEY (id_deuda);


--
-- TOC entry 2744 (class 2606 OID 5984629)
-- Name: pagos pagos_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagos
    ADD CONSTRAINT pagos_pkey PRIMARY KEY (id_pago);


--
-- TOC entry 2730 (class 2606 OID 5968150)
-- Name: pais pais_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pais
    ADD CONSTRAINT pais_pkey PRIMARY KEY (id_pais);


--
-- TOC entry 2738 (class 2606 OID 5968201)
-- Name: servicios servicios_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.servicios
    ADD CONSTRAINT servicios_pkey PRIMARY KEY (id_servicio);


--
-- TOC entry 2736 (class 2606 OID 5968189)
-- Name: usuario usuario_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id_usuario);


--
-- TOC entry 2749 (class 2606 OID 5984635)
-- Name: pagos fk_deuda; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagos
    ADD CONSTRAINT fk_deuda FOREIGN KEY (id_deuda) REFERENCES public.deudas_servicios(id_deuda) ON DELETE CASCADE;


--
-- TOC entry 2746 (class 2606 OID 5968216)
-- Name: deudas_servicios fk_servicio; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deudas_servicios
    ADD CONSTRAINT fk_servicio FOREIGN KEY (id_servicio) REFERENCES public.servicios(id_servicio) ON DELETE CASCADE;


--
-- TOC entry 2750 (class 2606 OID 5984640)
-- Name: pagos fk_servicio; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagos
    ADD CONSTRAINT fk_servicio FOREIGN KEY (id_servicio) REFERENCES public.servicios(id_servicio) ON DELETE CASCADE;


--
-- TOC entry 2745 (class 2606 OID 5968211)
-- Name: deudas_servicios fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.deudas_servicios
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON DELETE CASCADE;


--
-- TOC entry 2747 (class 2606 OID 5968231)
-- Name: cuentas fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.cuentas
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON DELETE CASCADE;


--
-- TOC entry 2748 (class 2606 OID 5984630)
-- Name: pagos fk_usuario; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.pagos
    ADD CONSTRAINT fk_usuario FOREIGN KEY (id_usuario) REFERENCES public.usuario(id_usuario) ON DELETE CASCADE;


-- Completed on 2024-08-17 04:56:38

--
-- PostgreSQL database dump complete
--

