-- Creazione della tabella quizzes con la struttura corretta
CREATE TABLE IF NOT EXISTS quizzes (
    quiz_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    quiz_title TEXT NOT NULL UNIQUE,
    quiz_category TEXT NOT NULL,
    is_favorite INTEGER NOT NULL DEFAULT 0
);

-- Inserimento di dati di esempio
INSERT INTO quizzes (quiz_title, quiz_category, is_favorite) VALUES
    ('Quiz 1', 'Geography', 0),
    ('Quiz 2', 'Geography', 1),
    ('Quiz 3', 'Science', 0),
    ('Quiz 4', 'Math', 1);