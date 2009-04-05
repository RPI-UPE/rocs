CREATE TABLE Semesters ( 
	semesterId INT,
	semesterDesc VARCHAR(255),
	PRIMARY KEY (`semesterId`)
);

CREATE TABLE Courses (
	semesterId INT,	
	courseId INT,	
	name VARCHAR(255),
	dept VARCHAR(255),
	num INT,
	credMin INT,
	creditMax INT,
	gradeType VARCHAR(255),
	PRIMARY KEY (`semesterId`, `courseId`),
	FOREIGN KEY (`semesterId`) REFERENCES `Semesters`(`semesterId`) ON DELETE CASCADE
);

CREATE TABLE CrossListings (
	crossListingId INT,
	maxSeats INT,
	PRIMARY KEY (`crossListingId`)
);

CREATE TABLE Sections (
	courseId INT,
	sectionId INT,
	sectionNum INT,	
	majorRevision BIGINT,
	crossListingId INT,
	seats int,
	students int, 
	PRIMARY KEY (`sectionId`),
	FOREIGN KEY (`courseId`) REFERENCES `Courses`(`courseId`),
	FOREIGN KEY (`crossListingId`) REFERENCES `CrossListings`(`crossListingId`) ON DELETE SET NULL
);

CREATE TABLE Notes(
	noteId	 INT,	
	courseId INT,
	note VARCHAR(4096),
	PRIMARY KEY (`noteId`),
	FOREIGN KEY (`courseId`) REFERENCES `Courses`(`courseId`)	
);


CREATE TABLE savedSchedules (
	scheduleId INT,	
	userId VARCHAR(255),
	name   VARCHAR(1024),
	PRIMARY KEY (`scheduleId`)
);

CREATE TABLE savedScheduleCourses (
	scheduleId INT,
	sectionId INT,
	majorRevision BIGINT,
	PRIMARY KEY (`scheduleId`,`sectionId`),
	FOREIGN KEY (`scheduleId`) REFERENCES Sections(`sectionId`)
);
