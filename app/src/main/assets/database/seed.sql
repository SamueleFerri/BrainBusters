    -- Creazione della tabella users
    CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    user_name TEXT NOT NULL,
    user_surname TEXT NOT NULL,
    user_username TEXT NOT NULL,
    user_email TEXT NOT NULL,
    user_password TEXT NOT NULL,
    user_image TEXT NOT NULL,
    user_position TEXT NOT NULL,
    UNIQUE(user_name, user_email)
    );

    -- Inserimento di 9 utenti
    INSERT INTO users (user_name, user_surname, user_username, user_email, user_password, user_image, user_position) VALUES
    ('Mario', 'Rossi', 'mrossi', 'mario.rossi@example.com', 'password1', '/storage/emulated/0/Pictures/IMG_20240522_173553.jpg', 'Manager'),
    ('Luigi', 'Verdi', 'lverdi', 'luigi.verdi@example.com', 'password2', 'content://media/picker/0/com.android.providers.media.photopicker/media/1000000035', 'Developer'),
    ('Giulia', 'Bianchi', 'gbianchi', 'giulia.bianchi@example.com', 'password3', 'image3.png', 'Designer'),
    ('Francesca', 'Neri', 'fneri', 'francesca.neri@example.com', 'password4', 'image4.png', 'Analyst'),
    ('Alessandro', 'Gialli', 'agialli', 'alessandro.gialli@example.com', 'password5', 'image5.png', 'Manager'),
    ('Lorenzo', 'Blu', 'lblu', 'lorenzo.blu@example.com', 'password6', 'image6.png', 'Developer'),
    ('Elena', 'Marrone', 'emarrone', 'elena.marrone@example.com', 'password7', 'image7.png', 'Designer'),
    ('Matteo', 'Viola', 'mviola', 'matteo.viola@example.com', 'password8', 'image8.png', 'Analyst'),
    ('Sara', 'Rosa', 'srosa', 'sara.rosa@example.com', 'password9', 'image9.png', 'Manager');

    -- Creazione della tabella badges
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

    -- Creazione della tabella careers
    CREATE TABLE IF NOT EXISTS careers (
    career_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    career_score INTEGER NOT NULL,
    career_user_id INTEGER NOT NULL,
    career_badge_id INTEGER NOT NULL,
    FOREIGN KEY (career_user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (career_badge_id) REFERENCES badges(badge_id) ON DELETE CASCADE
    );

    -- Inserimento delle carriere
    INSERT INTO careers (career_score, career_user_id, career_badge_id) VALUES
    (10, 1, 1), -- Carriera per Mario Rossi
    (20, 2, 2), -- Carriera per Luigi Verdi
    (15, 3, 3), -- Carriera per Giulia Bianchi
    (18, 4, 1), -- Carriera per Francesca Neri
    (22, 5, 2), -- Carriera per Alessandro Gialli
    (16, 6, 3), -- Carriera per Lorenzo Blu
    (14, 7, 1), -- Carriera per Elena Marrone
    (17, 8, 2), -- Carriera per Matteo Viola
    (21, 9, 3); -- Carriera per Sara Rosa

    -- Creazione della tabella quizzes
    CREATE TABLE IF NOT EXISTS quizzes (
    quiz_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    quiz_title TEXT NOT NULL UNIQUE,
    quiz_category TEXT NOT NULL,
    is_favorite INTEGER NOT NULL DEFAULT 0
    );

    -- Inserimento di dati di esempio nella tabella quizzes
    INSERT INTO quizzes (quiz_title, quiz_category, is_favorite) VALUES
    ('Capitali', 'Geography', 0),
    ('Dove sono gli animali', 'Geography', 0),
    ('Informatica', 'It', 0);

    -- Creazione della tabella questions
    CREATE TABLE IF NOT EXISTS questions (
    question_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    question_number TEXT NOT NULL,
    question_question TEXT NOT NULL,
    question_quizId INTEGER NOT NULL,
    FOREIGN KEY (question_quizId) REFERENCES quizzes(quiz_id) ON DELETE CASCADE
    );

    -- Creazione della tabella responses
    CREATE TABLE IF NOT EXISTS responses (
    response_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
    response_text TEXT NOT NULL,
    response_score INTEGER NOT NULL,
    response_question_id INTEGER NOT NULL,
    FOREIGN KEY (response_question_id) REFERENCES questions(question_id) ON DELETE CASCADE
    );

    -- Inserimento di domande per il Quiz "Capitali"
    INSERT INTO questions (question_number, question_question, question_quizId) VALUES
    ('1', 'Qual è la capitale della Francia?', 1),
    ('2', 'Qual è la capitale dell’Italia?', 1),
    ('3', 'Qual è la capitale del Giappone?', 1),
    ('4', 'Qual è la capitale del Brasile?', 1),
    ('5', 'Qual è la capitale della Germania?', 1);

    -- Inserimento di risposte per il Quiz "Capitali", Domanda 1
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Parigi', 5, 1),
    ('Londra', 0, 1),
    ('Roma', 0, 1),
    ('Berlino', 0, 1);

    -- Inserimento di risposte per il Quiz "Capitali", Domanda 2
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Roma', 5, 2),
    ('Parigi', 0, 2),
    ('Londra', 0, 2),
    ('Berlino', 0, 2);

    -- Inserimento di risposte per il Quiz "Capitali", Domanda 3
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Tokyo', 5, 3),
    ('Pechino', 0, 3),
    ('Seul', 0, 3),
    ('Bangkok', 0, 3);

    -- Inserimento di risposte per il Quiz "Capitali", Domanda 4
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Brasilia', 5, 4),
    ('Rio de Janeiro', 0, 4),
    ('Sao Paulo', 0, 4),
    ('Buenos Aires', 0, 4);

    -- Inserimento di risposte per il Quiz "Capitali", Domanda 5
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Berlino', 5, 5),
    ('Amburgo', 0, 5),
    ('Monaco', 0, 5),
    ('Colonia', 0, 5);

    -- Inserimento di domande per il Quiz "Dove sono gli animali"
    INSERT INTO questions (question_number, question_question, question_quizId) VALUES
    ('1', 'Dove vive il panda gigante?', 2),
    ('2', 'Dove vive il canguro?', 2),
    ('3', 'Dove vive il pinguino?', 2),
    ('4', 'Dove vive il leone?', 2),
    ('5', 'Dove vive l’orso polare?', 2);

    -- Inserimento di risposte per il Quiz "Dove sono gli animali", Domanda 1
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Cina', 5, 6),
    ('Australia', 0, 6),
    ('Antartide', 0, 6),
    ('Africa', 0, 6);

    -- Inserimento di risposte per il Quiz "Dove sono gli animali", Domanda 2
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Australia', 5, 7),
    ('Cina', 0, 7),
    ('Africa', 0, 7),
    ('Antartide', 0, 7);

    -- Inserimento di risposte per il Quiz "Dove sono gli animali", Domanda 3
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Antartide', 5, 8),
    ('Australia', 0, 8),
    ('Cina', 0, 8),
    ('Africa', 0, 8);

    -- Inserimento di risposte per il Quiz "Dove sono gli animali", Domanda 4
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Africa', 5, 9),
    ('Australia', 0, 9),
    ('Cina', 0, 9),
    ('Antartide', 0, 9);

    -- Inserimento di risposte per il Quiz "Dove sono gli animali", Domanda 5
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Artico', 5, 10),
    ('Antartide', 0, 10),
    ('Africa', 0, 10),
    ('Australia', 0, 10);

    -- Inserimento di domande per il Quiz "Informatica"
    INSERT INTO questions (question_number, question_question, question_quizId) VALUES
    ('1', 'Qual è l’acronimo di CPU?', 3),
    ('2', 'Cos’è un URL?', 3),
    ('3', 'Chi ha inventato il World Wide Web?', 3),
    ('4', 'Quale linguaggio di programmazione è noto per la sua semplicità?', 3),
    ('5', 'Qual è il sistema operativo open source più popolare?', 3);

    -- Inserimento di risposte per il Quiz "Informatica", Domanda 1
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Central Processing Unit', 5, 11),
    ('Computer Program Unit', 0, 11),
    ('Central Programming Unit', 0, 11),
    ('Computer Processing Unit', 0, 11);

    -- Inserimento di risposte per il Quiz "Informatica", Domanda 2
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Uniform Resource Locator', 5, 12),
    ('Universal Resource Locator', 0, 12),
    ('Uniform Resource Link', 0, 12),
    ('Universal Resource Link', 0, 12);

    -- Inserimento di risposte per il Quiz "Informatica", Domanda 3
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Tim Berners-Lee', 5, 13),
    ('Bill Gates', 0, 13),
    ('Steve Jobs', 0, 13),
    ('Linus Torvalds', 0, 13);

    -- Inserimento di risposte per il Quiz "Informatica", Domanda 4
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Python', 5, 14),
    ('Java', 0, 14),
    ('C++', 0, 14),
    ('Perl', 0, 14);

    -- Inserimento di risposte per il Quiz "Informatica", Domanda 5
    INSERT INTO responses (response_text, response_score, response_question_id) VALUES
    ('Linux', 5, 15),
    ('Windows', 0, 15),
    ('MacOS', 0, 15),
    ('Android', 0, 15);