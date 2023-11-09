CREATE TABLE CashFlow (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  category INTEGER NOT NULL,
  FOREIGN KEY (category) REFERENCES Categories(id) ON DELETE CASCADE
);

CREATE TABLE Savings (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  category INTEGER NOT NULL,
  FOREIGN KEY (category) REFERENCES Categories(id) ON DELETE CASCADE
);

CREATE TABLE Expenses (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  category INTEGER NOT NULL,
  FOREIGN KEY (category) REFERENCES Accounts(id) ON DELETE CASCADE
);

CREATE TABLE Categories (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  balance TEXT NOT NULL
);

CREATE TABLE Accounts (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  title TEXT NOT NULL,
  category_id INTEGER NOT NULL,
  balance TEXT NOT NULL,
  FOREIGN KEY (category_id) REFERENCES Categories(id) ON DELETE CASCADE
);

CREATE TABLE Transactions (
  id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
  account_id INTEGER NOT NULL,
  title TEXT NOT NULL,
  amount TEXT NOT NULL,
  date TEXT NOT NULL,
  description TEXT NOT NULL,
  FOREIGN KEY (account_id) REFERENCES Accounts(id) ON DELETE CASCADE
);


-- TEST DATA
-- Insert dummy data into Categories table
INSERT INTO Categories (title, balance) VALUES ('CashFlow Category 1', '250');
INSERT INTO Categories (title, balance) VALUES ('CashFlow Category 2', '450');
INSERT INTO Categories (title, balance) VALUES ('Savings Category A', '650');
INSERT INTO Categories (title, balance) VALUES ('Savings Category B', '850');
INSERT INTO Categories (title, balance) VALUES ('Expenses Category A', '1050');
INSERT INTO Categories (title, balance) VALUES ('Expenses Category B', '1250');

-- Insert dummy data into Accounts table
INSERT INTO Accounts (title, category_id, balance) VALUES ('CashFlow Account 1A', 1, '250');
INSERT INTO Accounts (title, category_id, balance) VALUES ('CashFlow Account 1B', 2, '450');
INSERT INTO Accounts (title, category_id, balance) VALUES ('Savings Account 2A', 3, '650');
INSERT INTO Accounts (title, category_id, balance) VALUES ('Savings Account 2B', 4, '850');
INSERT INTO Accounts (title, category_id, balance) VALUES ('Expenses Account 3A', 5, '1050');
INSERT INTO Accounts (title, category_id, balance) VALUES ('Expenses Account 3B', 6, '1250');

-- Insert dummy data into Transactions table
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (1, 'CashFlow Transaction1', '100', '2023-10-28', 'Description1A');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (1, 'CashFlow Transaction2', '150', '2023-10-29', 'Description2A');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (2, 'CashFlow Transaction1', '200', '2023-10-30', 'Description1B');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (2, 'CashFlow Transaction2', '250', '2023-10-31', 'Description2B');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (3, 'Savings Transaction1', '300', '2023-11-01', 'Description1C');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (3, 'Savings Transaction2', '350', '2023-11-02', 'Description2C');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (4, 'Savings Transaction1', '400', '2023-11-03', 'Description1D');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (4, 'Savings Transaction2', '450', '2023-11-04', 'Description2D');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (5, 'Expenses Transaction1', '500', '2023-11-05', 'Description1E');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (5, 'Expenses Transaction2', '550', '2023-11-06', 'Description2E');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (6, 'Expenses Transaction1', '600', '2023-11-07', 'Description1F');
INSERT INTO Transactions (account_id, title, amount, date, description) VALUES (6, 'Expenses Transaction2', '650', '2023-11-08', 'Description2F');

-- CashFlow
INSERT INTO CashFlow (category) VALUES (1);
INSERT INTO CashFlow (category) VALUES (2);

INSERT INTO Savings (category) VALUES (3);
INSERT INTO Savings (category) VALUES (4);
--
INSERT INTO Expenses (category) VALUES (5);
INSERT INTO Expenses (category) VALUES (6);