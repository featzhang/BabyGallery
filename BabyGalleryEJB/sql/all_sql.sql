CREATE TABLE t_comment
(
  id       INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  content  VARCHAR(45),
  image_id VARCHAR(45),
  view_id  INT
);
CREATE TABLE t_image
(
  id         INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  image_name VARCHAR(30)     NOT NULL
);
CREATE TABLE t_message
(
  id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  image_ids   VARCHAR(45),
  user_id     INT,
  update_time DATETIME,
  tag         VARCHAR(45),
  title       VARCHAR(45),
  content     VARCHAR(450),
  mark_point  DATETIME
);
CREATE TABLE t_user
(
  id            INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name          VARCHAR(45),
  photo_count   INT DEFAULT 0   NOT NULL,
  email         VARCHAR(45),
  baby_name     VARCHAR(45),
  baby_birthday DATETIME
);
CREATE TABLE t_viewer
(
  id          INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
  name        VARCHAR(45),
  inviteCode  VARCHAR(45),
  isBlacklist INT                      DEFAULT 1
);
