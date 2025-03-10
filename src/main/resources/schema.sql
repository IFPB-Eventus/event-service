CREATE TABLE event_plans (
                             id SERIAL PRIMARY KEY,
                             name VARCHAR(100) NOT NULL,
                             event_date DATE NOT NULL,
                             microphones INTEGER NOT NULL,
                             projectors INTEGER NOT NULL,
                             rooms TEXT,
                             members TEXT
);

CREATE TABLE events (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(100) NOT NULL,
                        expected_participants INTEGER NOT NULL,
                        event_date DATE NOT NULL,
                        registration_deadline DATE NOT NULL,
                        photo BYTEA
);

CREATE TABLE activities (
                            id SERIAL PRIMARY KEY,
                            name VARCHAR(100) NOT NULL,
                            location VARCHAR(100) NOT NULL,
                            activity_date DATE NOT NULL,
                            time TIME NOT NULL,
                            type VARCHAR(100) NOT NULL,
                            photo BYTEA,
                            event_id INTEGER NOT NULL,
                            FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE event_registrations (
                                     id SERIAL PRIMARY KEY,
                                     user_id INTEGER NOT NULL,
                                     event_id INTEGER NOT NULL,
                                     FOREIGN KEY (event_id) REFERENCES events(id)
);

CREATE TABLE activity_registrations (
                                        id SERIAL PRIMARY KEY,
                                        user_id INTEGER NOT NULL,
                                        activity_id INTEGER NOT NULL,
                                        FOREIGN KEY (activity_id) REFERENCES activities(id)
);