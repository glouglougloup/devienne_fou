CREATE TABLE devienne_fou_character (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    region VARCHAR(50),
    realm VARCHAR(100),
    race VARCHAR(50),
    wow_class VARCHAR(50),
    profile_url VARCHAR(255),
    UNIQUE(name, region, realm)
);

CREATE TABLE mythic_plus_run_history(
    id SERIAL PRIMARY KEY,
    character_id BIGINT REFERENCES devienne_fou_character(id),
    week_start DATE NOT NULL,
--    total_runs INT DEFAULT 0,
--    highest_level_run INT,
--    level_10_or_above_runs INT,
    week_status VARCHAR(10) CHECK (week_status IN ('VALIDATED', 'WARNING', 'BAD')),
    recorded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(character_id, week_start)
);