-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: onthemars
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `code`
--

DROP TABLE IF EXISTS `code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `code` (
  `id` varchar(255) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `code`
--

LOCK TABLES `code` WRITE;
/*!40000 ALTER TABLE `code` DISABLE KEYS */;
INSERT INTO `code` VALUES ('CLR01','WHITE'),('CLR02','RED'),('CLR03','ORANGE'),('CLR04','YELLOW'),('CLR05','GREEN'),('CLR06','BLUE'),('CLR07','NAVY'),('CLR08','PURPLE'),('CLR09','PINK'),('CLR10','BROWN'),('CRS01','WHEAT'),('CRS02','CARROT'),('CRS03','CORN'),('CRS04','CUCUMBER'),('CRS05','EGGPLANT'),('CRS06','POTATO'),('CRS07','REDISH'),('CRS08','STRAWBERRY'),('CRS09','TOMATO'),('CRS10','PINEAPPLE'),('CRT01','SEED'),('CRT02','PLANTED'),('CRT03','SPROUT'),('CRT04','MATURITY'),('CRT05','FRUIT'),('CRT06','HARVEST'),('EYE01','DEFAULT'),('EYE02','CHIC'),('EYE03','ADONIS'),('EYE04','SLEEP'),('EYE05','SMILE'),('EYE06','SAD'),('EYE07','MAD'),('HDG01','DEFAULT'),('HDG02','HAIRBAND'),('HDG03','RIBBON'),('HDG04','HEADSET'),('HDG05','NUTRIENTS'),('HDG06','FORT'),('HDG07','WORM'),('MOU01','DEFAULT'),('MOU02','SMILE'),('MOU03','MUSTACHE'),('MOU04','TONGUE'),('MOU05','SAD'),('MOU06','WOW'),('MOU07','CHU'),('TRC01','MINTED'),('TRC02','LIST'),('TRC03','SALES'),('TRC04','TRANSFER'),('TRC05','CANCEL');
/*!40000 ALTER TABLE `code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `color_code`
--

DROP TABLE IF EXISTS `color_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `color_code` (
  `hex_code` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK8r7nyi0bet4edpntorca12eyq` FOREIGN KEY (`id`) REFERENCES `code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `color_code`
--

LOCK TABLES `color_code` WRITE;
/*!40000 ALTER TABLE `color_code` DISABLE KEYS */;
INSERT INTO `color_code` VALUES ('F2F2F2','CLR01'),('FD8A8A','CLR02'),('FEBE8C','CLR03'),('FFF6BD','CLR04'),('C4DFAA','CLR05'),('AEE2FF','CLR06'),('8EA7E9','CLR07'),('DBC6EB','CLR08'),('FEDEFF','CLR09'),('C8B6A6','CLR10');
/*!40000 ALTER TABLE `color_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop`
--

DROP TABLE IF EXISTS `crop`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `col_num` int DEFAULT NULL,
  `cooltime` int NOT NULL,
  `reg_dt` datetime(6) NOT NULL,
  `row_num` int DEFAULT NULL,
  `state` varchar(255) NOT NULL,
  `type` varchar(255) NOT NULL,
  `upd_dt` datetime(6) NOT NULL,
  `address` varchar(42) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKs0u8x3911vv7nqvb0y5yse177` (`address`),
  CONSTRAINT `FKs0u8x3911vv7nqvb0y5yse177` FOREIGN KEY (`address`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop`
--

LOCK TABLES `crop` WRITE;
/*!40000 ALTER TABLE `crop` DISABLE KEYS */;
/*!40000 ALTER TABLE `crop` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `crop_code`
--

DROP TABLE IF EXISTS `crop_code`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `crop_code` (
  `bio` varchar(255) DEFAULT NULL,
  `plural` varchar(255) DEFAULT NULL,
  `id` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FKs3wmtr8nu8utc8uauvkm9bmxv` FOREIGN KEY (`id`) REFERENCES `code` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `crop_code`
--

LOCK TABLES `crop_code` WRITE;
/*!40000 ALTER TABLE `crop_code` DISABLE KEYS */;
INSERT INTO `crop_code` VALUES ('밀은 길쭉하고 키가 크며 영양가가 높은 곡물의 대표적인 캐릭터입니다! 건강하고 강한 몸을 가지고 있고, 먹을 것을 좋아하죠. 또한, 밀은 여러 가지 요리에 활용될 수 있어 다양한 모습으로 변신할 수 있습니다.','WHEAT','CRS01'),('당근은 주황색 몸통과 작은 녹색 잎사귀를 가지고 있습니다. 섬유가 적당히 있어 탄탄하고 질감이 좋은 몸을 가지고 있죠. 그리고 베타카로틴이라는 성분이 풍부하게 함유되어 있어 눈 건강에 매우 좋아요.','CARROTS','CRS02'),('옥수수는 노란색이 알알이 빛나는 순금의 열매입니다. 초록 껍질을 입고 있는 재미난 친구죠. 옥수수의 씨눈에는 필수 지방산인 리놀레산이 풍부해 콜레스테롤을 낮춰주고 동맥경화 예방에 도움을 준답니다!','CORN','CRS03'),('오이는 길쭉한 몸을 가지고 있으며 조직의 96%가 수분으로 이루어져 있어 청량한 캐릭터죠! 꼭지가 싱싱하고 모양이 일정하며, 색상이 진하고 부드럽습니다. 차가운 성질을 가지고 있어 피부의 열을 진정시키는데 도움이 됩니다.','CUCUMBERS','CRS04'),('가지는 길쭉하거나 둥근 모양을 가지고 있습니다. 자주색의 은은한 색상과 어긋나게 난 잎이 독특하죠. 자주빛을 내는 가지의 안토시아닌 색소는 항암 효과가 있는 것으로도 알려져 있답니다!','EGGPLANTS','CRS05'),('감자는 동그랗고 흙과 비슷한 색을 가지고 있는 투박한 캐릭터입니다. 하지만 감자는 피부 미백과 진정, 위산 과다 개선, 소화 도움, 사과보다 많은 비타민 C, 유해 물질 배출 등 다양한 능력을 가지고 있는 팔방미인이에요.','POTATOES','CRS06'),('무는 둥글고 단단한 몸을 가지고 있고 위부터 아래로 초록색에서 흰색으로 그라데이션이 있습니다. 무는 알싸하면서도 달콤한 맛을 내며, 계절에 따라 다른 맛을 내죠. 모든 계절의 무를 만나보고 싶지 않나요?','RADISHES','CRS07'),('딸기는 조그맣고 빨간 친구죠. 특유의 향기와 과즙이 풍부하여 많은 사람에게 인기입니다. 한 가지 알아둘 점이 있어요. 딸기는 작지만 강합니다. 온도에 잘 적응해 적도 부근에서 북극 근처까지 전 세계에서 만날 수 있답니다.','STRAWBERRIES','CRS08'),('토마토는 꼭지가 단단하고 붉은 빛깔이 선명한 캐릭터입니다. 토마토는 항산화 능력으로 유명하죠! 토마토의 붉은색을 만드는 라이코펜이 노화의 원인이 되는 활성산소를 배출시켜 젊음을 유지시킨답니다.','TOMATOES','CRS09'),('파인애플은 노랗고 단단한 몸과 뾰족하고 높은 잎을 가지고 있는 친구입니다. 과육은 누구보다도 달콤하지만 고기를 분해시키는 단백질 분해 효소인 브로멜린을 가지고 있기도 한 달콤 살벌한 캐릭터랍니다.','PINEAPPLES','CRS10');
/*!40000 ALTER TABLE `crop_code` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `favorite`
--

DROP TABLE IF EXISTS `favorite`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `favorite` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `address` varchar(42) NOT NULL,
  `transaction_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKhlxbfm11c6firud9ygei6a950` (`address`),
  KEY `FKtkqyla0xv6yumfnu4oc0k0dh` (`transaction_id`),
  CONSTRAINT `FKhlxbfm11c6firud9ygei6a950` FOREIGN KEY (`address`) REFERENCES `member` (`address`),
  CONSTRAINT `FKtkqyla0xv6yumfnu4oc0k0dh` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `favorite`
--

LOCK TABLES `favorite` WRITE;
/*!40000 ALTER TABLE `favorite` DISABLE KEYS */;
/*!40000 ALTER TABLE `favorite` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `member`
--

DROP TABLE IF EXISTS `member`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `member` (
  `address` varchar(42) NOT NULL,
  `reg_dt` datetime(6) NOT NULL,
  PRIMARY KEY (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `member`
--

LOCK TABLES `member` WRITE;
/*!40000 ALTER TABLE `member` DISABLE KEYS */;
/*!40000 ALTER TABLE `member` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nft`
--

DROP TABLE IF EXISTS `nft`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nft` (
  `address` varchar(255) NOT NULL,
  `activated` bit(1) NOT NULL,
  `bg` int NOT NULL,
  `dna` varchar(255) NOT NULL,
  `tier` int NOT NULL,
  `token_id` varchar(255) NOT NULL,
  `type` int NOT NULL,
  `user_address` varchar(42) NOT NULL,
  PRIMARY KEY (`address`),
  KEY `FKk20qf0njq6hg2sxdbvhdjh6i3` (`user_address`),
  CONSTRAINT `FKk20qf0njq6hg2sxdbvhdjh6i3` FOREIGN KEY (`user_address`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nft`
--

LOCK TABLES `nft` WRITE;
/*!40000 ALTER TABLE `nft` DISABLE KEYS */;
/*!40000 ALTER TABLE `nft` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nft_history`
--

DROP TABLE IF EXISTS `nft_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nft_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `event_type` varchar(255) NOT NULL,
  `price` double NOT NULL,
  `reg_dt` datetime(6) NOT NULL,
  `buyer_id` varchar(42) NOT NULL,
  `address` varchar(255) NOT NULL,
  `seller_id` varchar(42) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKbtylkf16kdd2w0nbiau9e23v8` (`buyer_id`),
  KEY `FK2dthwwagnk16gy0m7kewimcpg` (`address`),
  KEY `FKmixs7ybl0qq4s0k5bjd0bbh44` (`seller_id`),
  CONSTRAINT `FK2dthwwagnk16gy0m7kewimcpg` FOREIGN KEY (`address`) REFERENCES `nft` (`address`),
  CONSTRAINT `FKbtylkf16kdd2w0nbiau9e23v8` FOREIGN KEY (`buyer_id`) REFERENCES `member` (`address`),
  CONSTRAINT `FKmixs7ybl0qq4s0k5bjd0bbh44` FOREIGN KEY (`seller_id`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nft_history`
--

LOCK TABLES `nft_history` WRITE;
/*!40000 ALTER TABLE `nft_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `nft_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `nft_t2`
--

DROP TABLE IF EXISTS `nft_t2`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `nft_t2` (
  `eyes` int NOT NULL,
  `headgear` int NOT NULL,
  `mouth` int NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`address`),
  CONSTRAINT `FKtr0s7ijrb50cmlhyq8jjpylb1` FOREIGN KEY (`address`) REFERENCES `nft` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `nft_t2`
--

LOCK TABLES `nft_t2` WRITE;
/*!40000 ALTER TABLE `nft_t2` DISABLE KEYS */;
/*!40000 ALTER TABLE `nft_t2` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) NOT NULL,
  `deleted` bit(1) NOT NULL,
  `reg_dt` datetime(6) NOT NULL,
  `verified` bit(1) NOT NULL,
  `address` varchar(42) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjg07oms0hmki97vrgy5unmls0` (`address`),
  CONSTRAINT `FKjg07oms0hmki97vrgy5unmls0` FOREIGN KEY (`address`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `profile`
--

DROP TABLE IF EXISTS `profile`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `profile` (
  `color` varchar(255) NOT NULL,
  `nickname` varchar(255) NOT NULL,
  `profile_img` varchar(255) NOT NULL,
  `seed_cnt` int NOT NULL,
  `address` varchar(42) NOT NULL,
  PRIMARY KEY (`address`),
  CONSTRAINT `FKfgpaxm30dwnbq1e0ogruu0fid` FOREIGN KEY (`address`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `profile`
--

LOCK TABLES `profile` WRITE;
/*!40000 ALTER TABLE `profile` DISABLE KEYS */;
/*!40000 ALTER TABLE `profile` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `seed_history`
--

DROP TABLE IF EXISTS `seed_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `seed_history` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `cnt` int NOT NULL,
  `price` double NOT NULL,
  `reg_dt` datetime(6) NOT NULL,
  `address` varchar(42) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKpmx74an2piuihab0hw3qbfxol` (`address`),
  CONSTRAINT `FKpmx74an2piuihab0hw3qbfxol` FOREIGN KEY (`address`) REFERENCES `member` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `seed_history`
--

LOCK TABLES `seed_history` WRITE;
/*!40000 ALTER TABLE `seed_history` DISABLE KEYS */;
/*!40000 ALTER TABLE `seed_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `activated` bit(1) NOT NULL,
  `price` double DEFAULT NULL,
  `reg_dt` datetime(6) NOT NULL,
  `view_cnt` int NOT NULL,
  `address` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_m9vu0urputdk1o6hxn4bmsyvx` (`address`),
  CONSTRAINT `FKruukdqp3xb5bsm1m64qrt3jqg` FOREIGN KEY (`address`) REFERENCES `nft` (`address`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `views`
--

DROP TABLE IF EXISTS `views`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `views` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `view_dt` datetime(6) NOT NULL,
  `address` varchar(42) NOT NULL,
  `transaction_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKgm5t2yu1q7oqs6kxh357m1omq` (`address`),
  KEY `FKoe1qajwjuneexmwwsys0dbvmr` (`transaction_id`),
  CONSTRAINT `FKgm5t2yu1q7oqs6kxh357m1omq` FOREIGN KEY (`address`) REFERENCES `member` (`address`),
  CONSTRAINT `FKoe1qajwjuneexmwwsys0dbvmr` FOREIGN KEY (`transaction_id`) REFERENCES `transaction` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `views`
--

LOCK TABLES `views` WRITE;
/*!40000 ALTER TABLE `views` DISABLE KEYS */;
/*!40000 ALTER TABLE `views` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-03-20 17:55:09
