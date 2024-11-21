CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       email VARCHAR(255) UNIQUE NOT NULL,
                       password_hash VARCHAR(255) NOT NULL,
                       phone_number VARCHAR(15) UNIQUE,
                       first_name VARCHAR(100) NOT NULL,
                       middle_name VARCHAR(100),
                       last_name VARCHAR(100) NOT NULL,
                       address VARCHAR(255),
                       street_address VARCHAR(255),
                       address_line_2 VARCHAR(255),
                       city VARCHAR(100),
                       state VARCHAR(100),
                       zip_code VARCHAR(20),
                       country VARCHAR(50),
                       preferred_location GEOMETRY(Point, 4326),
                       wishlist_id INTEGER,
                       created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                       role VARCHAR(6) CHECK (role IN ('USER', 'OWNER', 'ADMIN')) DEFAULT 'USER' NOT NULL,
                       is_active BOOLEAN DEFAULT TRUE,
                       last_login TIMESTAMP
);

CREATE TABLE wishlist (
                          id SERIAL PRIMARY KEY,
                          user_id INTEGER NOT NULL UNIQUE,  -- Each user has a single wishlist
                          FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE wishlist_items (
                                wishlist_id INTEGER NOT NULL,
                                product_id INTEGER NOT NULL,
                                PRIMARY KEY (wishlist_id, product_id),
                                FOREIGN KEY (wishlist_id) REFERENCES wishlist(id) ON DELETE CASCADE,
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

CREATE TABLE store_owners (
                              store_id INTEGER NOT NULL,
                              owner_id INTEGER NOT NULL,
                              PRIMARY KEY (store_id, owner_id),
                              FOREIGN KEY (store_id) REFERENCES stores(id) ON DELETE CASCADE,
                              FOREIGN KEY (owner_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE stores_product (
                                store_id INTEGER NOT NULL,
                                product_id INTEGER NOT NULL,
                                stock INTEGER NOT NULL CHECK (stock >= 0) DEFAULT 0,
                                PRIMARY KEY (store_id, product_id),
                                FOREIGN KEY (store_id) REFERENCES stores(id) ON DELETE CASCADE,
                                FOREIGN KEY (product_id) REFERENCES products(id) ON DELETE CASCADE
);

alter table stores_product add column price numeric(10, 2) not null default 0;

CREATE TABLE stores (
                        id SERIAL PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        address VARCHAR(255),
                        street_address VARCHAR(255),
                        address_line_2 VARCHAR(255),
                        city VARCHAR(100),
                        state VARCHAR(100),
                        zip_code VARCHAR(20),
                        country VARCHAR(50),
                        location GEOMETRY(Point, 4326) NOT NULL,
                        main_owner INTEGER NOT NULL,
                        contact_number VARCHAR(15),
                        email VARCHAR(255) NOT NULL,
                        rating NUMERIC(2, 1) CHECK (rating >= 0 AND rating <= 5) DEFAULT 0,
                        total_reviews INTEGER DEFAULT 0,
                        created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_main_owner FOREIGN KEY (main_owner) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE products (
                          id SERIAL PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          description TEXT NOT NULL,
                          price NUMERIC(10, 2) NOT NULL CHECK (price >= 0),
                          category VARCHAR(100),
                          sku VARCHAR(50),
                          barcode VARCHAR(50),
                          created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                          updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
