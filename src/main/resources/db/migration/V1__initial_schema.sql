CREATE SEQUENCE IF NOT EXISTS formats_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE IF NOT EXISTS consoles_id_seq START WITH 1 INCREMENT BY 1;

-- Create formats table
CREATE TABLE IF NOT EXISTS formats (
    id INT PRIMARY KEY DEFAULT nextval('formats_id_seq'),
    name VARCHAR(50) NOT NULL
);

-- Create consoles table
CREATE TABLE IF NOT EXISTS consoles (
    id INT PRIMARY KEY DEFAULT nextval('consoles_id_seq'),
    name VARCHAR(100) NOT NULL,
    format_id INT NOT NULL,
    FOREIGN KEY (format_id) REFERENCES formats(id)
);

-- Create games table
CREATE TABLE IF NOT EXISTS games (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name VARCHAR(255) NOT NULL UNIQUE,
    console_id INT NOT NULL,
    rating INT NOT NULL,
    FOREIGN KEY (console_id) REFERENCES consoles(id)
);