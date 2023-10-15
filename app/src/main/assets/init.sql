CREATE TABLE IF NOT EXISTS FinancialCategories (
    financial_category_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL,
    parent INTEGER NOT NULL,
    UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS FinancialRecords (
    financial_record_id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,
    title TEXT NOT NULL,
    amount TEXT NOT NULL,
    category INTEGER NOT NULL,
    FOREIGN KEY (category) REFERENCES FinancialCategories (financial_category_id) ON DELETE CASCADE
);


-- PRE POPULATED ENTRIES
INSERT INTO FinancialCategories (name, parent) VALUES ('Budget', -1);
INSERT INTO FinancialCategories (name, parent) VALUES ('Expenses', -1);

WITH categories(category_name) AS (
    VALUES ('Housing'), ('Utilities'), ('Transportation'), ('Groceries'), ('Dining Out'), ('Entertainment')
)
INSERT INTO FinancialCategories (name, parent)
SELECT category_name, (
    SELECT financial_category_id
    FROM FinancialCategories
    WHERE name = 'Expenses'
)
FROM categories;