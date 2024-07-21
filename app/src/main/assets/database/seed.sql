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

-- Creazione della tabella responses con la struttura corretta
CREATE TABLE IF NOT EXISTS responses (
    response_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    response_text TEXT NOT NULL,
    response_score INTEGER NOT NULL,
    response_question_id INTEGER NOT NULL,
    FOREIGN KEY (response_question_id) REFERENCES questions(question_id) ON DELETE CASCADE
);

-- Inserimento di risposte per il Quiz 1, Domanda 1
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Paris', 5, 1),
    ('London', 0, 1),
    ('Berlin', 0, 1),
    ('Madrid', 0, 1);

-- Inserimento di risposte per il Quiz 1, Domanda 2
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Sahara', 5, 2),
    ('Gobi', 0, 2),
    ('Kalahari', 0, 2),
    ('Arctic', 0, 2);

-- Inserimento di risposte per il Quiz 1, Domanda 3
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Nile', 5, 3),
    ('Amazon', 0, 3),
    ('Yangtze', 0, 3),
    ('Mississippi', 0, 3);

-- Inserimento di risposte per il Quiz 1, Domanda 4
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Yen', 5, 4),
    ('Won', 0, 4),
    ('Dollar', 0, 4),
    ('Euro', 0, 4);

-- Inserimento di risposte per il Quiz 1, Domanda 5
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Mars', 5, 5),
    ('Venus', 0, 5),
    ('Jupiter', 0, 5),
    ('Saturn', 0, 5);

-- Inserimento di risposte per il Quiz 2, Domanda 1
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Albert Einstein', 5, 6),
    ('Isaac Newton', 0, 6),
    ('Galileo Galilei', 0, 6),
    ('Nikola Tesla', 0, 6);

-- Inserimento di risposte per il Quiz 2, Domanda 2
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('H2O', 5, 7),
    ('O2', 0, 7),
    ('CO2', 0, 7),
    ('NaCl', 0, 7);

-- Inserimento di risposte per il Quiz 2, Domanda 3
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('299,792,458 m/s', 5, 8),
    ('150,000,000 m/s', 0, 8),
    ('300,000,000 m/s', 0, 8),
    ('299,792 km/s', 0, 8);

-- Inserimento di risposte per il Quiz 2, Domanda 4
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Diamond', 5, 9),
    ('Gold', 0, 9),
    ('Iron', 0, 9),
    ('Platinum', 0, 9);

-- Inserimento di risposte per il Quiz 2, Domanda 5
INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Albert Einstein', 5, 10),
    ('Isaac Newton', 0, 10),
    ('Galileo Galilei', 0, 10),
    ('Nikola Tesla', 0, 10);

-- Creazione della tabella badges con la struttura corretta
CREATE TABLE IF NOT EXISTS badges (
    badge_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    badge_title TEXT NOT NULL UNIQUE,
    badge_color TEXT NOT NULL,
    required_quizzes INTEGER NOT NULL
);

-- Inserimento di badge di esempio
INSERT INTO badges (badge_title, badge_color, required_quizzes) VALUES
    ('Principiante dei quiz', '#0000FF', 1),
    ('Novizio dei quiz', '#088F8F', 2),
    ('Esperto dei quiz', '#FF0000', 10);
