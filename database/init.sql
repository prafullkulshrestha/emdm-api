CREATE DATABASE emdm_api;
\connect emdm_api

CREATE schema emdm;

CREATE TABLE IF NOT EXISTS emdm.organizations (
  organization_id                        BIGSERIAL, 
  name                       VARCHAR(100) NOT NULL,
  about                      VARCHAR(500) NOT NULL,
  created_by VARCHAR(32) NOT NULL, 
  create_date TIMESTAMP(6) NOT NULL, 
  updated_by VARCHAR(32) NOT NULL, 
  last_update_date TIMESTAMP(6) NOT NULL, 
  CONSTRAINT u_organizations_on
	     UNIQUE (name),
  CONSTRAINT pk_organizations PRIMARY KEY (organization_id));


CREATE TABLE IF NOT EXISTS emdm.developers (
  developer_id                        BIGSERIAL,
  organization_id                     BIGINT NOT NULL, 
  name                                VARCHAR(100) NOT NULL,
  age                                 INT NOT NULL,
  created_by VARCHAR(32) NOT NULL, 
  create_date TIMESTAMP(6) NOT NULL, 
  updated_by VARCHAR(32) NOT NULL, 
  last_update_date TIMESTAMP(6) NOT NULL, 
  CONSTRAINT pk_developers PRIMARY KEY (developer_id),
  CONSTRAINT fk_developers_oi 
	     FOREIGN KEY (organization_id) 
	     REFERENCES emdm.organizations(organization_id));
  
CREATE TABLE IF NOT EXISTS emdm.user_api_rate_limits (
  user_api_rate_limit_id                BIGSERIAL,
  username                              VARCHAR(50) NOT NULL,
  api                                   VARCHAR(100) NOT NULL,
  rate_limit                            INT NOT NULL,
  created_by VARCHAR(32) NOT NULL, 
  create_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  updated_by VARCHAR(32) NOT NULL, 
  last_update_date TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP, 
  CONSTRAINT pk_user_api_rate_limits PRIMARY KEY (user_api_rate_limit_id));

INSERT INTO emdm.organizations (organization_id, name, about, created_by, create_date, updated_by, last_update_date) 
VALUES (1, 'Delta Dental', 'Delta Dental is America''s largest and most trusted dental benefits carrier. They cover more Americans than any other dental benefits provider.', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.organizations (organization_id, name, about, created_by, create_date, updated_by, last_update_date) 
VALUES (2, 'Advance Auto Parts', 'Advance Auto Parts, Inc. is a leading automotive aftermarket parts provider headquartered in Raleigh, N.C., that serves both professional installer and do-it-yourself customers.', 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);


INSERT INTO emdm.developers (developer_id, organization_id, name, age, created_by, create_date, updated_by, last_update_date) VALUES (1, 1, 'Alyson Kelly', 28, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.developers (developer_id, organization_id, name, age, created_by, create_date, updated_by, last_update_date) VALUES (2, 1, 'Mike Phillip', 32, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.developers (developer_id, organization_id, name, age, created_by, create_date, updated_by, last_update_date) VALUES (3, 1, 'Rob Curtin', 40, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.developers (developer_id, organization_id, name, age, created_by, create_date, updated_by, last_update_date) VALUES (4, 2, 'Mike Parrish', 36, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.developers (developer_id, organization_id, name, age, created_by, create_date, updated_by, last_update_date) VALUES (5, 2, 'Shaelyn Ellis', 30, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);

INSERT INTO emdm.user_api_rate_limits (user_api_rate_limit_id, username, api, rate_limit, created_by, create_date, updated_by, last_update_date) VALUES (1, 'User 1', '/api/v1/developers', 100, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.user_api_rate_limits (user_api_rate_limit_id, username, api, rate_limit, created_by, create_date, updated_by, last_update_date) VALUES (2, 'User 2', '/api/v1/developers', 50, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.user_api_rate_limits (user_api_rate_limit_id, username, api, rate_limit, created_by, create_date, updated_by, last_update_date) VALUES (3, 'User 1', '/api/v1/organizations', 250, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
INSERT INTO emdm.user_api_rate_limits (user_api_rate_limit_id, username, api, rate_limit, created_by, create_date, updated_by, last_update_date) VALUES (4, 'User 2', '/api/v1/organizations', 500, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP, 'SEEDED_DURING_BUILD', CURRENT_TIMESTAMP);
