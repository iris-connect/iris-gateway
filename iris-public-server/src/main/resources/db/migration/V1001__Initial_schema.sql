CREATE TABLE data_request (
	request_id uuid NOT NULL,
	department_id uuid NOT NULL,
	request_start timestamp NULL,
	request_end timestamp NULL,
	request_details text NULL,
	status varchar(50) NOT NULL,
	created timestamp NOT NULL,
	last_modified timestamp NOT NULL,
	CONSTRAINT request_pkey PRIMARY KEY (request_id)
);

CREATE TABLE data_request_feature (
    request_id uuid,
	feature varchar(50),
    PRIMARY KEY (request_id, feature),
    FOREIGN KEY (request_id) REFERENCES data_request(request_id)
    	ON DELETE CASCADE
    	ON UPDATE CASCADE
);


CREATE TABLE data_submission (
	submission_id uuid NOT NULL,
	request_id uuid NOT NULL,
	department_id uuid NOT NULL,
	secret varchar(1000) NULL,
	key_reference varchar(50) NOT NULL,
	feature varchar(50) NOT NULL,
	encrypted_data text NULL,
	created timestamp NOT NULL,
	last_modified timestamp NOT NULL,
	requested timestamp NULL,
	CONSTRAINT submission_pkey PRIMARY KEY (submission_id)
);

CREATE TABLE department (
	department_id uuid NOT NULL,
	name varchar(50) NULL,
	public_key text NOT NULL,
	key_reference varchar(50) NOT NULL,
	created timestamp NOT NULL,
	last_modified timestamp NOT NULL,
	CONSTRAINT department_pkey PRIMARY KEY (department_id)
)
