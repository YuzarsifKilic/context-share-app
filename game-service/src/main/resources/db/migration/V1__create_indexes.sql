-- V1__Create_Indexes.sql

-- Index game_name
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'game' AND INDEX_NAME = 'index_game_name') THEN
CREATE INDEX index_game_name ON game(name);
END IF;

-- Index processor_brand
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'processor' AND INDEX_NAME = 'index_processor_brand') THEN
CREATE INDEX index_processor_brand ON processor(brand);
END IF;

-- Index processor_version
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'processor' AND INDEX_NAME = 'index_processor_version') THEN
CREATE INDEX index_processor_version ON processor(version);
END IF;

-- Index graphics_brand
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'graphics' AND INDEX_NAME = 'index_graphics_brand') THEN
CREATE INDEX index_graphics_brand ON graphics(brand);
END IF;

-- Index graphics_version
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'graphics' AND INDEX_NAME = 'index_graphics_version') THEN
CREATE INDEX index_graphics_version ON graphics(version);
END IF;

-- Index developer_name
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'developer' AND INDEX_NAME = 'index_developer_name') THEN
CREATE INDEX index_developer_name ON developer(name);
END IF;

-- Index publisher_name
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'publisher' AND INDEX_NAME = 'index_publisher_name') THEN
CREATE INDEX index_publisher_name ON publisher(name);
END IF;

-- Index language_name
IF NOT EXISTS (SELECT * FROM INFORMATION_SCHEMA.STATISTICS WHERE TABLE_NAME = 'language' AND INDEX_NAME = 'index_language_name') THEN
CREATE INDEX index_language_name ON language(name);
END IF;