/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

# Dump of table access bank configuration
# ------------------------------------------------------------
CREATE TABLE IF NOT EXISTS `configuration` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement ID for transaction',
  `base_url` VARCHAR(255) NOT NULL,
  `client_id` VARCHAR(255) NOT NULL,
  `client_secret` VARCHAR(255) NOT NULL,
  `grant_type` VARCHAR(50) NOT NULL,
  `scope` VARCHAR(100) NOT NULL,
  `institution_code` VARCHAR(40) NOT NULL,
  `biller_id` VARCHAR(255) NOT NULL,
  `mandate_reference_number` VARCHAR(100) NOT NULL,
  `source_account_name` VARCHAR(266) NOT NULL,
  `source_account_number` VARCHAR(20) NOT NULL,
  `source_bvn` VARCHAR(20) NOT NULL,
  `source_kyc_level` VARCHAR(10) NOT NULL,
  `source_institution_code` VARCHAR(40) NOT NULL,
  `authorization_code` VARCHAR(100) NOT NULL,
  `proxy_url` TEXT DEFAULT NULL COMMENT 'Server proxy url',
  `proxy_port` VARCHAR(10) DEFAULT NULL COMMENT 'Server proxy port',
  `proxy_username` VARCHAR(512) DEFAULT NULL COMMENT 'Server proxy username',
  `proxy_password` VARCHAR(512) DEFAULT NULL COMMENT 'Server proxy password',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the payment was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time payment was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE IF NOT EXISTS `payment` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement ID for transaction',
  `source_institution_code` VARCHAR(20) DEFAULT NULL,
  `amount` VARCHAR(50) NOT NULL,
  `beneficiary_account_name` VARCHAR(200)  NOT NULL,
  `beneficiary_account_number` VARCHAR(20) NOT NULL,
  `beneficiary_bank_verification_number` VARCHAR(20) NULL,
  `beneficiary_kyc_level` VARCHAR(10) DEFAULT NULL,
  `channel_code`  VARCHAR(20) NOT NULL,
  `response_code` VARCHAR(20) DEFAULT NULL,
  `response_message` TEXT DEFAULT NULL,
  `originator_account_name` VARCHAR(200) NOT NULL,
  `originator_account_number` VARCHAR(20) NOT NULL,
  `originator_bank_verification_number` VARCHAR(20) NOT NULL,
  `originator_kyc_level` VARCHAR(10) DEFAULT NULL,
  `destination_institution_code` VARCHAR(20) DEFAULT NULL,
  `mandate_reference_number` VARCHAR(200) DEFAULT NULL,
  `name_enquiry_ref` VARCHAR(50) DEFAULT NULL,
  `originator_narration` TEXT DEFAULT NULL,
  `payment_reference` VARCHAR(255) DEFAULT NULL,
  `transaction_id` VARCHAR(100) NOT NULL,
  `linking_reference` VARCHAR(50) DEFAULT NULL,
  `provider_reference` VARCHAR(50) DEFAULT NULL,
  `transaction_location` VARCHAR(100) DEFAULT NULL,
  `beneficiary_narration` TEXT DEFAULT NULL,
  `biller_id` VARCHAR(100) DEFAULT NULL,
  `nibss_response_code` VARCHAR(10) DEFAULT NULL,
  `session_Id` VARCHAR(100) DEFAULT NULL,
  `nibss_error_timestamp` VARCHAR(30) DEFAULT NULL,
  `nibss_error_code` VARCHAR(20) DEFAULT NULL,
  `nibss_error_message` TEXT DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the transaction was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time transaction was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE payment ADD INDEX `payment_transaction_id` (`transaction_id`);

CREATE TABLE IF NOT EXISTS `log` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement Primary Key',
  `reference` varchar(100) DEFAULT NULL COMMENT 'reference for the transaction',
  `operation_type` varchar(1024)  NULL COMMENT 'operation that was performed',
  `request_body` text COMMENT 'Raw request',
  `response_body` text COMMENT 'Raw response',
  `response_code` varchar(100) DEFAULT NULL COMMENT 'HTTP status code of response',
  `status` varchar(100) DEFAULT NULL COMMENT 'Status',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When log was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time log was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

ALTER TABLE log ADD INDEX `log_reference` (`reference`);

CREATE TABLE IF NOT EXISTS `auth` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT COMMENT 'Autoincrement ID for transaction',
  `username` VARCHAR(100) NOT NULL COMMENT 'Username',
  `password` VARCHAR(255) NOT NULL COMMENT 'Password',
  `unique_id`  VARCHAR(200) NOT NULL COMMENT 'Unique, token clain',
  `issuer`  VARCHAR(200) NOT NULL COMMENT 'issuer',
  `type`  VARCHAR(20) NOT NULL COMMENT 'type',
  `status`  VARCHAR(20) NOT NULL COMMENT 'status',
  `expiry`  int NOT NULL COMMENT 'Token expiry',
  `created_at` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'When the transaction was created',
  `updated_at` timestamp NULL DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last time transaction was updated',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;

INSERT INTO `auth` (`id`, `username`, `password`, `unique_id`, `issuer`, `expiry`, `type`, `status`, `created_at`, `updated_at`) VALUES (NULL, 'configApi', 'b69da5d1cb4fb3c8745c41eb9e5362913ef31cbde', '01ec0fcf-9fd6-1206-bae5-a49c20867b0a', 'https://fluterwavedev.com', '3600000', 'CONFIGURATION', 'ACTIVE', CURRENT_TIMESTAMP, NULL);