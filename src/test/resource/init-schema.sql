use wenda;
DROP TABLE IF EXISTS `feed`;
CREATE TABLE `feed` (
                      `id` INT NOT NULL AUTO_INCREMENT,
                      `created_date` DATETIME NULL,
                      `user_id` INT NULL,
                      `data` TINYTEXT NULL,
                      `type` INT NULL,
                      PRIMARY KEY (`id`),
                      INDEX `user_index` (`user_id` ASC))
  ENGINE = InnoDB
  DEFAULT CHARACTER SET = utf8;