CREATE TABLE quizzes (
    quiz_id INTEGER PRIMARY KEY AUTOINCREMENT,
    quiz_title TEXT NOT NULL UNIQUE,
    quiz_category TEXT NOT NULL
);

INSERT INTO quizzes (quiz_title, quiz_category) VALUES ('Quiz 1', 'Category 1');
INSERT INTO quizzes (quiz_title, quiz_category) VALUES ('Quiz 2', 'Category 2');