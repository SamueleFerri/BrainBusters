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

-- Creazione della tabella badges con la struttura corretta
CREATE TABLE IF NOT EXISTS badges (
    badge_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    badge_title TEXT NOT NULL UNIQUE,
    badge_color TEXT NOT NULL,
    required_quizzes INTEGER NOT NULL
);

-- Inserimento di dati di esempio nella tabella badges
INSERT INTO badges (badge_title, badge_color, required_quizzes) VALUES
    ('Novice Explorer', 'Blue', 5),
    ('Intermediate Navigator', 'Green', 10),
    ('Master Adventurer', 'Gold', '15');
