CREATE TABLE ALERTS (
    ID uuid NOT NULL,
    TITLE varchar(100) NOT NULL,
    TEXT varchar(1000) NOT NULL,
    CLIENT varchar(100) NOT NULL,
    SOURCE_APP varchar(50) NOT NULL,
    APP_VERSION varchar(50) NOT NULL,
    ALERT_TYPE varchar(50) NOT NULL,
    CONSTRAINT alert_pkey PRIMARY KEY (ID)
)
