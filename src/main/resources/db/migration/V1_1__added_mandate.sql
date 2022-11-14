
CREATE TABLE IF NOT EXISTS `mandate` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement ID for transaction',
  `reference` VARCHAR(100) NOT NULL,
  `app_user` VARCHAR(50) NOT NULL,
  `biller_id` VARCHAR(200) NOT NULL,
  `amount` VARCHAR(50) NOT NULL,
  `account_number` VARCHAR(20) NOT NULL,
  `product_id` VARCHAR(200) NOT NULL,
  `bank_code` VARCHAR(30) NOT NULL,
  `payer_name` VARCHAR(250) NOT NULL,
  `payer_address` TEXT NOT NULL,
  `account_name` VARCHAR(250) NOT NULL,
  `payee_name` VARCHAR(250) NOT NULL,
  `narration` VARCHAR(250) NOT NULL,
  `payee_address` TEXT NOT NULL,
  `phone_number` VARCHAR(20) NOT NULL,
  `email_address` VARCHAR(200) NOT NULL,
  `subscriber_code` VARCHAR(100) NOT NULL,
  `start_date` VARCHAR(50) NOT NULL,
  `end_date` VARCHAR(50) NOT NULL,
  `file_extension` VARCHAR(20) NOT NULL,
  `file_base64_encoded_string` TEXT NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the payment was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time payment was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE mandate ADD INDEX `mandate_reference` (`reference`);

CREATE TABLE IF NOT EXISTS `mandate_configuration` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement ID for transaction',
  `username` VARCHAR(100) NOT NULL,
  `app_user` VARCHAR(100) NOT NULL,
  `biller_id` VARCHAR(200) NOT NULL,
  `password` VARCHAR(200) NOT NULL,
  `api_key` VARCHAR(200) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the payment was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time payment was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


