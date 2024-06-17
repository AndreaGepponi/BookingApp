-- table generation

CREATE TABLE IF NOT EXISTS "CreditCard" (
	cardNumber VARCHAR(50) PRIMARY KEY,
	cardType VARCHAR(50),
	cardExpirationDate VARCHAR(50),
	cardSecurityCode VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS "User" (
    id SERIAL PRIMARY KEY,
	name VARCHAR(50),
	surname VARCHAR(50),
	age INTEGER CONSTRAINT age_positive CHECK (age >= 0 AND age < 120),
	username VARCHAR(50) NOT NULL UNIQUE,
	role VARCHAR(50) NOT NULL,
	password VARCHAR(100) NOT NULL,
	creditCard VARCHAR(50) UNIQUE,
	FOREIGN KEY (creditCard) REFERENCES "CreditCard"(cardNumber)
);


CREATE TABLE IF NOT EXISTS "Structure"(
	name VARCHAR(50) PRIMARY KEY,
	place VARCHAR(50) NOT NULL,
	manager VARCHAR(50) NOT NULL,
	FOREIGN KEY(manager) REFERENCES "User"(username),
	type VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS "Review"(
	score INTEGER CONSTRAINT score_positive CHECK (score >= 1 AND score < 6),
	text VARCHAR(150),
	Hotel VARCHAR(50) NOT NULL,
	FOREIGN KEY (Hotel) REFERENCES "Structure"(name),
	users VARCHAR(50) NOT NULL,
	FOREIGN KEY(users) REFERENCES "User"(username)
);

CREATE TABLE IF NOT EXISTS "Room"(
	id SERIAL PRIMARY KEY,
	users VARCHAR(50) NOT NULL,
	FOREIGN KEY(users) REFERENCES "User"(username),
	hotel VARCHAR(50) not null,
	FOREIGN KEY(hotel) REFERENCES "Structure"(name),
	price INTEGER CONSTRAINT price CHECK (price > 0)
);

CREATE TABLE IF NOT EXISTS "Booking"(
	users VARCHAR(50) NOT NULL,
	FOREIGN KEY(users) REFERENCES "User"(username),
	hotel VARCHAR(50) not null,
	FOREIGN KEY(hotel) REFERENCES "Structure"(name),
	period VARCHAR(50) NOT NULL,
	BkId INTEGER NOT NULL PRIMARY KEY   ,
	FOREIGN KEY(BkId) REFERENCES "Room"(id)
);
