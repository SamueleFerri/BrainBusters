-- Creazione della tabella quizzes con la struttura corretta
CREATE TABLE IF NOT EXISTS quizzes (
    quiz_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    quiz_title TEXT NOT NULL UNIQUE,
    quiz_category TEXT NOT NULL,
    is_favorite INTEGER NOT NULL DEFAULT 0
);

-- Inserimento di dati di esempio nella tabella quizzes
INSERT INTO quizzes (quiz_title, quiz_category, is_favorite) VALUES
    ('Quiz 1', 'Geography', 0),
    ('Quiz 2', 'Geography', 1),
    ('Quiz 3', 'Science', 0),
    ('Quiz 4', 'Math', 1);

-- Creazione della tabella questions con la struttura corretta
CREATE TABLE IF NOT EXISTS questions (
    question_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    question_number TEXT NOT NULL,
    question_question TEXT NOT NULL,
    question_quizId INTEGER NOT NULL,
    FOREIGN KEY (question_quizId) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
);

-- Inserimento di domande per il Quiz 1
INSERT INTO questions (question_number, question_question, question_quizId) VALUES
    ('1', 'What is the capital of France?', 1),
    ('2', 'What is the largest desert in the world?', 1),
    ('3', 'Which river flows through Egypt?', 1),
    ('4', 'What is the currency of Japan?', 1),
    ('5', 'Which planet is known as the Red Planet?', 1);

-- Inserimento di domande per il Quiz 2
INSERT INTO questions (question_number, question_question, question_quizId) VALUES
    ('1', 'Who developed the theory of relativity?', 2),
    ('2', 'What is the chemical symbol for water?', 2),
    ('3', 'What is the speed of light?', 2),
    ('4', 'What is the hardest natural substance on Earth?', 2),
    ('5', 'Who is known as the father of modern physics?', 2);

-- Creazione della tabella badges con la struttura corretta
CREATE TABLE IF NOT EXISTS badges (
    badge_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    badge_title TEXT NOT NULL UNIQUE,
    badge_color TEXT NOT NULL,
    required_quizzes INTEGER NOT NULL
);

-- Inserimento di dati di esempio nella tabella badges
INSERT INTO badges (badge_title, badge_color, required_quizzes) VALUES
    ('Principiante dei quiz', '#0000FF', 1),
    ('Novizio dei quiz', '#088F8F', 2),
    ('Esperto dei quiz', '#FF0000', 10);
