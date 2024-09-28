-- Index game_name
CREATE INDEX index_game_name ON game(name);

-- Index processor_brand
CREATE INDEX index_processor_brand ON processor(brand);

-- Index processor_version
CREATE INDEX index_processor_version ON processor(version);

-- Index graphics_brand
CREATE INDEX index_graphics_brand ON graphics(brand);

-- Index graphics_version
CREATE INDEX index_graphics_version ON graphics(version);

-- Index developer_name
CREATE INDEX index_developer_name ON developer(name);

-- Index publisher_name
CREATE INDEX index_publisher_name ON publisher(name);

-- Index language_name
CREATE INDEX index_language_name ON language(name);

-- Index feature_name
CREATE INDEX index_feature_name ON feature(name);

-- Index genre_name
CREATE INDEX index_genre_name ON genre(name);

ALTER TABLE game MODIFY description TEXT;