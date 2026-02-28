CREATE DATABASE SinoNihon3;
GO
USE SinoNihon3;
GO

CREATE TABLE Users (
    User_id INT IDENTITY(1,1) PRIMARY KEY,
    Username NVARCHAR(50) NOT NULL UNIQUE,
    Password NVARCHAR(255) NOT NULL,
    FullName NVARCHAR(100) NOT NULL,
    Phone NVARCHAR(20) NOT NULL,
    Email NVARCHAR(100) NOT NULL UNIQUE,
    Role NVARCHAR(20) DEFAULT 'User',
    IsVerified BIT DEFAULT 0,
    CreatedAt DATETIME DEFAULT GETDATE()
);
GO

CREATE TABLE EmailVerification (
    Verification_id INT IDENTITY(1,1) PRIMARY KEY,
    User_id INT NOT NULL,
    OTP NVARCHAR(10) NOT NULL,
    ExpiredAt DATETIME NOT NULL,
    FOREIGN KEY (User_id) REFERENCES Users(User_id) ON DELETE CASCADE
);
GO



-- ===== COURSES =====
CREATE TABLE Courses (
    CourseID INT IDENTITY PRIMARY KEY,
    Title NVARCHAR(200),
    Language NVARCHAR(50), -- Chinese / Japanese
    Description NVARCHAR(MAX)
);

-- ===== LESSONS =====
CREATE TABLE Lessons (
    LessonID INT IDENTITY PRIMARY KEY,
    CourseID INT,
    Title NVARCHAR(200),
    FOREIGN KEY (CourseID) REFERENCES Courses(CourseID)
);

-- ===== VOCABULARY =====
CREATE TABLE Vocabulary (
    VocabID INT IDENTITY PRIMARY KEY,
    LessonID INT,
    Word NVARCHAR(200),
    Meaning NVARCHAR(200),
    Pronunciation NVARCHAR(200),
    FOREIGN KEY (LessonID) REFERENCES Lessons(LessonID)
);

-- ===== USER VOCAB PROGRESS =====
CREATE TABLE UserVocabProgress (
    UserID INT,
    VocabID INT,
    IsLearned BIT DEFAULT 0,
    PRIMARY KEY (UserID, VocabID)
);

-- ===== TEST RESULT =====
CREATE TABLE TestResult (
    ResultID INT IDENTITY PRIMARY KEY,
    UserID INT,
    LessonID INT,
    Score INT
);

CREATE TABLE UserLessonProgress (
    UserID INT,
    LessonID INT,
    PRIMARY KEY (UserID, LessonID)
);

-- ===== COURSES =====
INSERT INTO Courses (Title, Language, Description)
VALUES 
(N'Tiếng Trung Cơ Bản', 'Chinese', N'Học từ vựng tiếng Trung cho người mới bắt đầu'),
(N'Tiếng Nhật N5', 'Japanese', N'Học từ vựng và Kanji trình độ N5');

-- ===== LESSONS =====
INSERT INTO Lessons (CourseID, Title) VALUES
(1, N'Bài 1: Chào hỏi'),
(1, N'Bài 2: Gia đình'),
(2, N'Lesson 1: Greeting'),
(2, N'Lesson 2: Numbers');

-- ===== VOCABULARY =====
INSERT INTO Vocabulary (LessonID, Word, Meaning, Pronunciation) VALUES
(1, N'你好', N'Xin chào', N'Nǐ hǎo'),
(1, N'谢谢', N'Cảm ơn', N'Xièxiè'),
(2, N'妈妈', N'Mẹ', N'Māma'),
(3, N'こんにちは', N'Xin chào', N'Konnichiwa'),
(4, N'一', N'Số 1', N'Ichi');