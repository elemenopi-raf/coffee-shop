DROP TABLE IF EXISTS menu_items CASCADE;
DROP TABLE IF EXISTS testimonials CASCADE;
DROP TABLE IF EXISTS contact_messages CASCADE;
DROP TABLE IF EXISTS newsletter_emails CASCADE;

CREATE TABLE menu_items (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    price NUMERIC(10,2) NOT NULL,
    category VARCHAR(20) NOT NULL DEFAULT 'HOT',
    image_url VARCHAR(300) NOT NULL
);

CREATE TABLE testimonials (
    id SERIAL PRIMARY KEY,
    text TEXT NOT NULL UNIQUE,
    author VARCHAR(100) NOT NULL
);

CREATE TABLE contact_messages (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(200) NOT NULL,
    message TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE newsletter_emails (
    id SERIAL PRIMARY KEY,
    email VARCHAR(200) NOT NULL UNIQUE,
    subscribed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

INSERT INTO menu_items (name, price, category, image_url) VALUES
('Espresso', 5.50, 'HOT', 'https://images.unsplash.com/photo-1510707577719-ae7c14805e3a?w=300&h=300&fit=crop'),
('Latte', 8.50, 'HOT', 'https://images.unsplash.com/photo-1570968915860-54d5c301fa9f?w=300&h=300&fit=crop'),
('Cappuccino', 9.00, 'HOT', 'https://images.unsplash.com/photo-1572442388796-11668a67e53d?w=300&h=300&fit=crop'),
('Mocha', 12.00, 'HOT', 'https://images.unsplash.com/photo-1578314675249-a6910f80cc4e?w=300&h=300&fit=crop'),
('Flat White', 8.25, 'HOT', 'https://images.unsplash.com/photo-1461023058943-07fcbe16d735?w=300&h=300&fit=crop'),
('Cold Brew', 6.50, 'COLD', 'https://images.unsplash.com/photo-1517701550927-30cf4ba1dba5?w=300&h=300&fit=crop'),
('Iced Latte', 7.50, 'COLD', 'https://images.unsplash.com/photo-1559305616-3f99cd43e353?w=300&h=300&fit=crop'),
('Smoothie', 9.50, 'COLD', 'https://images.unsplash.com/photo-1505252585461-04db1eb84625?w=300&h=300&fit=crop'),
('Croissant', 4.50, 'PASTRY', 'https://images.unsplash.com/photo-1509365465985-25d11c17e812?w=300&h=300&fit=crop'),
('Blueberry Muffin', 3.75, 'PASTRY', 'https://images.unsplash.com/photo-1607958996333-41aef7caefaa?w=300&h=300&fit=crop'),
('Chocolate Chip Cookie', 2.50, 'PASTRY', 'https://images.unsplash.com/photo-1499636136210-6f4ee915583e?w=300&h=300&fit=crop')
ON CONFLICT DO NOTHING;

INSERT INTO testimonials (text, author) VALUES
('The best latte I''ve ever had. The atmosphere is cozy and the staff is incredibly friendly.', 'Sarah L.'),
('Their cold brew is a game changer. I stop here every morning on my way to work.', 'James M.'),
('A hidden gem. Perfect place to work remotely with great coffee and free Wi-Fi.', 'Emily R.')
ON CONFLICT DO NOTHING;
