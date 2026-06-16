CREATE TABLE IF NOT EXISTS menu_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price VARCHAR(20) NOT NULL,
    icon VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS testimonials (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL UNIQUE,
    author VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS contact_messages (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO menu_items (name, price, icon) VALUES
('Espresso', '$5.50', E'\u2615'),
('Latte', '$8.50', E'\U0001F9C3'),
('Cappuccino', '$9.00', E'\U0001F36E'),
('Mocha', '$12.00', E'\U0001F375'),
('Cold Brew', '$6.50', E'\U0001F95C'),
('Flat White', '$8.25', E'\U0001F34E')
ON CONFLICT DO NOTHING;

INSERT INTO testimonials (text, author) VALUES
('The best latte I''ve ever had. The atmosphere is cozy and the staff is incredibly friendly.', 'Sarah L.'),
('Their cold brew is a game changer. I stop here every morning on my way to work.', 'James M.'),
('A hidden gem. Perfect place to work remotely with great coffee and free Wi-Fi.', 'Emily R.')
ON CONFLICT DO NOTHING;
