package com.canalbrewing.abcdatacollector.dal;

public interface InitializeSql {
    String APP_MODE = "app_mode";
    String RELATIONSHIPS = "relationships";
    String OBSERVED = "observed";
    String OBSERVED_DEFAULTS = "observed_defaults";
    String INCIDENTS = "incidents";
    String INCIDENT_ABCS = "incident_abcs";
    String MESSAGES = "messages";
    String OBSERVERS = "observers";
    String USERS = "users";
    String USER_SESSION = "user_session";

    String DROP_APP_MODE = "DROP TABLE app_mode";
    String DROP_RELATIONSHIPS = "DROP TABLE relationships";
    String DROP_OBSERVED = "DROP TABLE observed";
    String DROP_OBSERVED_DEFAULTS = "DROP TABLE observed_defaults";
    String DROP_INCIDENTS = "DROP TABLE incidents";
    String DROP_INCIDENT_ABCS = "DROP TABLE incident_abcs";
    String DROP_MESSAGES = "DROP TABLE messages";
    String DROP_OBSERVERS = "DROP TABLE observers";
    String DROP_USERS = "DROP TABLE users";
    String DROP_USER_SESSION = "DROP TABLE user_session";

    String CREATE_APP_MODE = "CREATE TABLE app_mode (id int primary key generated always as identity, setup_complete char(1), require_login char(1), start_page varchar(32))";
    String CREATE_RELATIONSHIPS = "CREATE TABLE relationships (id int primary key generated always as identity, relationship varchar(128))";
    String CREATE_OBSERVED = "CREATE TABLE observed (id int primary key generated always as identity, observed_name varchar(128), user_id int default 0)";
    String CREATE_OBSERVED_DEFAULTS = "CREATE TABLE observed_defaults (id int primary key generated always as identity, observed_id int, type_cd char(1), value varchar(180), active_fl char(1))";
    String CREATE_INCIDENTS = "CREATE TABLE incidents (id int primary key generated always as identity, incident_date timestamp, observed_id int, user_id int, location_id int, duration int, intensity varchar(16), description varchar(1024))";
    String CREATE_INCIDENT_ABCS = "CREATE TABLE incident_abcs (id int primary key generated always as identity, incident_id int, observed_abc_id int)";
    String CREATE_MESSAGES = "CREATE TABLE messages (id int primary key generated always as identity, user_id int, from_user_id int, message_date timestamp, read_fl char(1), message varchar(1024))";
    String CREATE_OBSERVERS = "CREATE TABLE observers (id int primary key generated always as identity, user_id int, observed_id int, role varchar(64), relationship_id int, access_status varchar(64), access_key varchar(64))";
    String CREATE_USERS = "CREATE TABLE users (id int primary key generated always as identity, user_id int, user_name varchar(64), email varchar(256), password varchar(128) for bit data, salt varchar(128) for bit data, start_page varchar(32), status varchar(32), signed_in_key varchar(64))";
    String CREATE_USER_SESSION = "CREATE TABLE user_session (id int primary key generated always as identity, session_token varchar(64), user_id int, session_datetime timestamp, active_fl char(1))";

    String[] INIT_APP_MODE = {
            "INSERT INTO app_mode (setup_complete, require_login, start_page) VALUES ('N', 'N', 'DASHBOARD')"
    };

    String[] INIT_RELATIONSHIPS = {
            "INSERT INTO relationships (relationship) VALUES ('Aide')",
            "INSERT INTO relationships (relationship) VALUES ('Analyst')",
            "INSERT INTO relationships (relationship) VALUES ('Aunt')",
            "INSERT INTO relationships (relationship) VALUES ('Brother')",
            "INSERT INTO relationships (relationship) VALUES ('Father')",
            "INSERT INTO relationships (relationship) VALUES ('Grandfather')",
            "INSERT INTO relationships (relationship) VALUES ('Grandmother')",
            "INSERT INTO relationships (relationship) VALUES ('Mother')",
            "INSERT INTO relationships (relationship) VALUES ('Other')",
            "INSERT INTO relationships (relationship) VALUES ('Sister')",
            "INSERT INTO relationships (relationship) VALUES ('Stepfather')",
            "INSERT INTO relationships (relationship) VALUES ('Stepmother')",
            "INSERT INTO relationships (relationship) VALUES ('Teacher')",
            "INSERT INTO relationships (relationship) VALUES ('Uncle')"
    };

    TableDDL[] ALL_TABLES = {
            new TableDDL(APP_MODE, DROP_APP_MODE, CREATE_APP_MODE, INIT_APP_MODE),
            new TableDDL(RELATIONSHIPS, DROP_RELATIONSHIPS, CREATE_RELATIONSHIPS, INIT_RELATIONSHIPS),
            new TableDDL(OBSERVED, DROP_OBSERVED, CREATE_OBSERVED),
            new TableDDL(OBSERVED_DEFAULTS, DROP_OBSERVED_DEFAULTS, CREATE_OBSERVED_DEFAULTS),
            new TableDDL(INCIDENTS, DROP_INCIDENTS, CREATE_INCIDENTS),
            new TableDDL(INCIDENT_ABCS, DROP_INCIDENT_ABCS, CREATE_INCIDENT_ABCS),
            new TableDDL(MESSAGES, DROP_MESSAGES, CREATE_MESSAGES),
            new TableDDL(OBSERVERS, DROP_OBSERVERS, CREATE_OBSERVERS),
            new TableDDL(USERS, DROP_USERS, CREATE_USERS),
            new TableDDL(USER_SESSION, DROP_USER_SESSION, CREATE_USER_SESSION)
    };

    String SELECT_APP_MODE = "SELECT id, setup_complete, require_login, start_page FROM app_mode";
    String UPDATE_APP_MODE = "UPDATE app_mode SET setup_complete = ?, require_login = ?, start_page = ? WHERE id = ?";
    String SELECT_RELATIONSHIPS = "select id, relationship from relationships order by relationship";
}
