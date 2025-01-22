/*
 Navicat Premium Dump SQL

 Source Server         : test2
 Source Server Type    : MySQL
 Source Server Version : 80039 (8.0.39)
 Source Host           : localhost:3306
 Source Schema         : gy

 Target Server Type    : MySQL
 Target Server Version : 80039 (8.0.39)
 File Encoding         : 65001

 Date: 19/01/2025 19:14:40
*/

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ----------------------------
-- Table structure for account
-- ----------------------------
DROP TABLE IF EXISTS `account`;
CREATE TABLE `account`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `role` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NULL DEFAULT NULL,
  `register_time` datetime NULL DEFAULT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `unique_email`(`email` ASC) USING BTREE,
  UNIQUE INDEX `unique_username`(`username` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 8 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of account
-- ----------------------------
INSERT INTO `account` VALUES (4, 'user1', 'lxyjcmz@163.com', '$2a$10$7MvFIrBGoq8pUMvR8rjsy.AgZe12B18a6bcVioPpHAtBHIyjDsype', 'user', '2024-10-07 17:09:49');
INSERT INTO `account` VALUES (6, 'user2', 'jiancangmeizhu@163.com', '$2a$10$7cE6/mNqOcTB7p2EAKtu7e43zZ5v/EmMFtYFduiiLUKDedKTBmPGG', 'user', '2024-10-07 17:31:53');
INSERT INTO `account` VALUES (7, '111', '1351698043@qq.com', '$2a$10$zGWV5M.BGVHyTHKLNL4cpeBU9pbVv/WOJtynd44qoog1LTQFvRrBC', 'user', '2024-10-31 20:27:10');

-- ----------------------------
-- Table structure for initials
-- ----------------------------
DROP TABLE IF EXISTS `initials`;
CREATE TABLE `initials`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `letter` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `articulation_point` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `pronunciation_method` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 24 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of initials
-- ----------------------------
INSERT INTO `initials` VALUES (1, 'b', '双唇音 上唇下唇', '塞音 清音 不送气音');
INSERT INTO `initials` VALUES (2, 'p', '双唇音 上唇下唇', '塞音 清音 送气音');
INSERT INTO `initials` VALUES (3, 'd', '舌尖中音 舌尖上齿龈', '塞音 清音 不送气音');
INSERT INTO `initials` VALUES (4, 't', '舌尖中音 舌尖上齿龈', '塞音 清音 送气音');
INSERT INTO `initials` VALUES (5, 'g', '舌根音 舌根软腭', '塞音 清音 不送气音');
INSERT INTO `initials` VALUES (6, 'k', '舌根音 舌根软腭', '塞音 清音 送气音');
INSERT INTO `initials` VALUES (7, 'z', '舌尖前音 舌尖上齿背', '塞擦音 清音 不送气音');
INSERT INTO `initials` VALUES (8, 'c', '舌尖前音 舌尖上齿背', '塞擦音 清音 送气音');
INSERT INTO `initials` VALUES (9, 'zh', '舌尖后音 舌尖硬腭前', '塞擦音 清音 不送气音');
INSERT INTO `initials` VALUES (10, 'ch', '舌尖后音 舌尖硬腭前', '塞擦音 清音 送气音');
INSERT INTO `initials` VALUES (11, 'q', '舌面音 舌面硬腭前', '塞擦音 清音 送气音');
INSERT INTO `initials` VALUES (12, 'f', '唇齿音 上齿下唇', '擦音 清音');
INSERT INTO `initials` VALUES (13, 's', '舌尖前音 舌尖上齿背', '擦音 清音');
INSERT INTO `initials` VALUES (14, 'sh', '舌尖后音 舌尖硬腭前', '擦音 清音');
INSERT INTO `initials` VALUES (15, 'x', '舌面音 舌面硬腭前', '擦音 清音');
INSERT INTO `initials` VALUES (16, 'r', '舌尖后音 舌尖硬腭前', '擦音 浊音');
INSERT INTO `initials` VALUES (17, 'm', '双唇音 上唇下唇', '鼻音浊音');
INSERT INTO `initials` VALUES (18, 'n', '舌尖中音 舌尖上齿龈', '鼻音 浊音');
INSERT INTO `initials` VALUES (19, 'l', '舌尖中音 舌尖上齿龈', '边音 浊音');
INSERT INTO `initials` VALUES (20, 'j', '舌面音 舌面硬腭前', '塞擦音 清音 不送气音');
INSERT INTO `initials` VALUES (21, 'h', '舌根音 舌面后音', '擦音 清音');
INSERT INTO `initials` VALUES (22, 'w', '双唇音', '元音');
INSERT INTO `initials` VALUES (23, 'y', '舌尖前音', '元音');

-- ----------------------------
-- Table structure for train_text
-- ----------------------------
DROP TABLE IF EXISTS `train_text`;
CREATE TABLE `train_text`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `author` varchar(40) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT NULL,
  `text` text CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL,
  `category` enum('ancient poetry','modern poetry','prose') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `suggested_duration` int NULL DEFAULT NULL,
  `applicable_people` enum('children','adult') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `grade` enum('primary','middle rank','senior') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 44 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_unicode_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of train_text
-- ----------------------------
INSERT INTO `train_text` VALUES (1, '北京的春节(节选)', '老舍', '“腊七腊八，\r\n冻死寒鸦”，\r\n这是一年里最冷的时候。\r\n在腊八这天，\r\n家家都熬腊八粥。\r\n粥是用各种米，各种豆，\r\n与各种干果熬成的。\r\n这不是粥，而是小型的农业展览会。', 'prose', 90, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (2, '北京的春节(节选)', '老舍', '除此之外，\r\n这一天还要泡腊八蒜。\r\n把蒜瓣放进醋里，\r\n封起来，\r\n为过年吃饺子用。\r\n到年底，\r\n蒜泡得色如翡翠，\r\n醋也有了些辣味，\r\n色味双美，\r\n使人忍不住要多吃几个饺子。', 'prose', 90, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (3, '北京的春节(节选)', '老舍', '孩子们准备过年，\r\n第一件大事就是买杂拌儿，\r\n这是用花生、胶枣、榛子、栗子等干果与蜜饯掺和成的，\r\n第二件大事是买爆竹，\r\n特别是男孩子们。\r\n恐怕第三件事才是买各种玩意儿。', 'prose', 90, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (4, '北京的春节(节选)', '老舍', '腊月二十三过小年，\r\n差不多就是过春节的“彩排”。\r\n天一擦黑儿，\r\n鞭炮响起来，\r\n便有了过年的味道。\r\n这一天，\r\n是要吃糖的，\r\n街上早有好多卖麦芽糖与江米糖的。', 'prose', 90, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (5, '北京的春节(节选)', '老舍', '过了二十三，\r\n大家更忙。\r\n必须大扫除一次，\r\n还要把肉、鸡、鱼、青菜、年糕什么的都预备充足，\r\n店铺多数正月初一到初五关门，\r\n到正月初六才开张。', 'prose', 90, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (6, '春（节选）', '朱自清', '小草偷偷地从土里钻出来，\r\n嫩嫩的，绿绿的。\r\n园子里，田野里，瞧去，\r\n一大片一大片满是的。\r\n坐着，躺着，打两个滚，\r\n踢几脚球，赛几趟跑，\r\n捉几回迷藏。\r\n风轻悄悄的，\r\n草软绵绵的。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (7, '春（节选）', '朱自清', '“吹面不寒杨柳风”，\r\n不错的，\r\n像母亲的手抚摸着你。\r\n风里带来些新翻的泥土的气息，\r\n混着青草味儿，\r\n还有各种花的香，\r\n都在微微湿润的空气里酝酿。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (8, '春（节选）', '朱自清', '鸟儿将巢安在繁花绿叶当中，\r\n高兴起来了，\r\n呼朋引伴地卖弄清脆的喉咙，\r\n唱出宛转的曲子，\r\n跟轻风流水应和着。\r\n牛背上牧童的短笛，\r\n这时候也成天嘹亮地响着。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (9, '春（节选）', '朱自清', '雨是最寻常的，\r\n一下就是三两天。\r\n可别恼。看，\r\n像牛毛，像花针，像细丝，\r\n密密地斜织着，\r\n人家屋顶上全笼着一层薄烟。\r\n树叶儿却绿得发亮，\r\n小草儿也青得逼你的眼。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (10, '春（节选）', '朱自清', '傍晚时候，\r\n上灯了，\r\n一点点黄晕的光，\r\n烘托出一片安静而和平的夜。\r\n在乡下，小路上，石桥边，\r\n有撑起伞慢慢走着的人，\r\n地里还有工作的农民，\r\n披着蓑戴着笠。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (11, '春（节选）', '朱自清', '天上风筝渐渐多了，\r\n地上孩子也多了。\r\n城里乡下，家家户户，老老小小，\r\n也赶趟儿似的，\r\n一个个都出来了。\r\n舒活舒活筋骨，\r\n抖抖擞精神，\r\n各做各的一份儿事去。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (12, '匆匆（节选）', '朱自清', '燕子去了，\r\n有再来的时候；\r\n杨柳枯了，\r\n有再青的时候；\r\n桃花谢了，\r\n有再开的时候。\r\n但是，聪明的，\r\n你告诉我，\r\n我们的日子为什么一去不复返呢？', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (13, '匆匆（节选）', '朱自清', '去的尽管去了，\r\n来的尽管来着；\r\n去来的中间，\r\n又怎样地匆匆呢？\r\n早上我起来的时候，\r\n小屋里射进两三方斜斜的太阳。\r\n太阳他有脚啊，\r\n轻轻悄悄地挪移了。\r\n我觉察他去的匆匆了，\r\n伸出手遮挽时，\r\n他又从遮挽着的手边过去；\r\n天黑时，我躺在床上，\r\n他便伶伶俐俐地从我身上跨过，\r\n从我脚边飞去了。', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (14, '匆匆（节选）', '朱自清', '等我睁开眼和太阳再见，\r\n这算又溜走了一日。\r\n在逃去如飞的日子里，\r\n在千门万户的世界里的我能做些什么呢？\r\n只有徘徊罢了，\r\n只有匆匆罢了；\r\n在八千多日的匆匆里，\r\n除徘徊外，\r\n又剩些什么呢？', 'prose', 90, 'adult', 'senior');
INSERT INTO `train_text` VALUES (15, '咏柳', '贺知章', '碧玉妆成一树高，\r\n万条垂下绿丝绦。\r\n不知细叶谁裁出，\r\n二月春风似剪刀。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (16, '宿建德江', '孟浩然', '移舟泊烟渚，\r\n日暮客愁新。\r\n野旷天低树，\r\n江清月近人。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (17, '凉州词', '王翰', '葡萄美酒夜光杯，\r\n欲饮琵琶马上催。\r\n醉卧沙场君莫笑，\r\n古来征战几人回？', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (18, '过故人庄', '孟浩然', '故人具鸡黍，\r\n邀我至田家。\r\n绿树村边合，\r\n青山郭外斜。\r\n开轩面场圃，\r\n把酒话桑麻。\r\n待到重阳日，\r\n还来就菊花。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (19, '芙蓉楼送辛渐', '王昌龄', '寒雨连江夜入吴，\r\n平明送客楚山孤。\r\n洛阳亲友如相问，\r\n一片冰心在玉壶。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (20, '渡汉江', '宋之问', '岭外音书断，\r\n经冬复历春。\r\n近乡情更怯，\r\n不敢问来人。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (21, '登幽州台歌', '陈子昂', '前不见古人，\r\n后不见来者。\r\n念天地之悠悠，\r\n独怆然而涕下！', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (22, '次北固山下', '王湾', '客路青山外，\r\n行舟绿水前。\r\n潮平两岸阔，\r\n风正一帆悬。\r\n海日生残夜，\r\n江春入旧年。\r\n乡书何处达？\r\n归雁洛阳边。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (23, '送杜少府之任蜀州', '王勃', '城阙辅三秦，\r\n风烟望五津。\r\n与君离别意，\r\n同是宦游人。\r\n海内存知己，\r\n天涯若比邻。\r\n无为在歧路，\r\n儿女共沾巾。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (24, '易水送别', '骆宾王', '此地别燕丹，\r\n壮士发冲冠。\r\n惜时人已没，\r\n今日水犹寒。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (25, '从军行', '杨炯', '烽火照西京，\r\n心中自不平。\r\n牙璋辞凤阙，\r\n铁骑绕龙城。\r\n雪暗凋旗画，\r\n风多杂鼓声。\r\n宁为百夫长，\r\n胜作一书生。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (26, '黄鹤楼', '崔颢', '昔人已乘黄鹤去，\r\n此地空余黄鹤楼。\r\n黄鹤一去不复返，\r\n白云千载空悠悠。\r\n晴川历历汉阳树，\r\n芳草萋萋鹦鹉洲。\r\n日暮乡关何处是？\r\n烟波江上使人愁。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (27, '回乡偶书', '贺知章', '少小离家老大回，\r\n乡音无改鬓毛衰。\r\n儿童相见不相识，\r\n笑问客从何处来。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (28, '采莲曲', '王昌龄', '荷叶罗裙一色裁，\r\n芙蓉向脸两边开。\r\n乱入池中看不见，\r\n闻歌始觉有人来。', 'ancient poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (29, '面朝大海，春暖花开(选段)', '海子', '从明天起，\r\n做一个幸福的人。\r\n喂马，劈柴，周游世界。\r\n从明天起，\r\n关心粮食和蔬菜。\r\n我有一所房子，\r\n面朝大海，春暖花开。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (30, '面朝大海，春暖花开(选段)', '海子', '从明天起，\r\n和每一个亲人通信。\r\n告诉他们我的幸福，\r\n那幸福的闪电告诉我的。\r\n我将告诉每一个人。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (31, '面朝大海，春暖花开(选段)', '海子', '给每一条河每一座山取一个温暖的名字\r\n陌生人，\r\n我也为你祝福。\r\n愿你有一个灿烂的前程，\r\n愿你有情人终成眷属，\r\n愿你在尘世获得幸福，\r\n我只愿面朝大海，\r\n春暖花开。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (32, '我爱这土地(选段)', '艾青', '假如我是一只鸟，\r\n我也应该用嘶哑的喉咙歌唱：\r\n这被暴风雨所打击着的土地，\r\n这永远汹涌着我们的悲愤的河流；', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (33, '母亲', '冰心', '母亲呵！\r\n天上的风雨来了，\r\n鸟儿躲到它的巢里；\r\n心中的风雨来了，\r\n我只躲到你的怀里。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (34, '再别康桥(节选)', '徐志摩', '轻轻的我走了，\r\n正如我轻轻的来；\r\n我轻轻的招手，\r\n作别西天的云彩。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (35, '再别康桥(节选)', '徐志摩', '那河畔的金柳，\r\n是夕阳中的新娘；\r\n波光里的艳影，\r\n在我的心头荡漾。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (36, '再别康桥(节选)', '徐志摩', '那榆荫下的一潭，\r\n不是清泉，\r\n是天上虹；\r\n揉碎在浮藻间，\r\n沉淀着彩虹似的梦。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (37, '再别康桥(节选)', '徐志摩', '但我不能放歌，\r\n悄悄是别离的笙箫；\r\n夏虫也为我沉默，\r\n沉默是今晚的康桥！', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (38, '仿佛(节选)', '泰戈尔', '我不记得我的母亲\r\n只是在游戏中间\r\n有时仿佛有一段歌调\r\n在我玩具上回旋\r\n是她在晃动我的摇篮\r\n所哼的那些歌调', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (39, '仿佛(节选)', '泰戈尔', '我不记得我的母亲\r\n但是在初秋的早晨\r\n合欢花香在空气中浮动\r\n庙殿里晨祷的馨香\r\n仿佛向我吹来母亲的气息', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (40, '仿佛(节选)', '泰戈尔', '我不记得我的母亲\r\n只是我从卧室的窗里\r\n外望悠远的蓝天\r\n我仿佛觉得\r\n母亲凝住我的目光\r\n布满了整个天空', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (41, '天真的预示(节选)', '布莱克', '一粒沙子看出世界，\r\n一朵野花里见天国。\r\n在你掌里盛住无限，\r\n一时间里便是永远。', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (42, '致大自然(节选)', '荷尔德林', '当我还在你的面纱旁游戏\r\n还像花儿依傍在你身旁\r\n还倾听你每一声心跳\r\n它将我温柔颤抖的心环绕', 'modern poetry', 60, 'adult', 'middle rank');
INSERT INTO `train_text` VALUES (43, '致大自然(节选)', '荷尔德林', '当我的心还向着太阳\r\n以为阳光听得见它的跃动\r\n它把星星称作兄弟\r\n把春天当作神的旋律', 'modern poetry', 60, 'adult', 'middle rank');

-- ----------------------------
-- Table structure for user
-- ----------------------------
DROP TABLE IF EXISTS `user`;
CREATE TABLE `user`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `register_time` datetime NOT NULL,
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `name`(`name` ASC) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 3 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_0900_ai_ci ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of user
-- ----------------------------
INSERT INTO `user` VALUES (1, 'test_user', '207acd61a3c1bd506d7e9a4535359f8a', '2024-09-29 10:47:56');
INSERT INTO `user` VALUES (2, 'test_user2', 'da729c5be4adc8d911b00a910e7dd472', '2024-09-29 12:28:48');

-- ----------------------------
-- Table structure for vowels
-- ----------------------------
DROP TABLE IF EXISTS `vowels`;
CREATE TABLE `vowels`  (
  `id` int NOT NULL AUTO_INCREMENT,
  `letter` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
  `pronunciation_type` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `rough_structure` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  `smooth_structure` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 29 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin ROW_FORMAT = DYNAMIC;

-- ----------------------------
-- Records of vowels
-- ----------------------------
INSERT INTO `vowels` VALUES (1, 'i', '齐齿呼', '单韵母', '单韵母1');
INSERT INTO `vowels` VALUES (2, 'u', '合口呼', '单韵母', '单韵母1');
INSERT INTO `vowels` VALUES (3, 'ü', '撮口呼', '单韵母', '单韵母1');
INSERT INTO `vowels` VALUES (4, 'a', '开口呼', '单韵母', '单韵母2');
INSERT INTO `vowels` VALUES (5, 'o', '开口呼', '单韵母', '单韵母3');
INSERT INTO `vowels` VALUES (6, 'e', '开口呼', '单韵母', '单韵母4');
INSERT INTO `vowels` VALUES (7, 'ue', '合口呼', '复韵母', '复韵母2');
INSERT INTO `vowels` VALUES (8, 'ê', '开口呼', '单韵母', '单韵母5');
INSERT INTO `vowels` VALUES (9, 'er', '开口呼', '单韵母', '单韵母6');
INSERT INTO `vowels` VALUES (10, 'ai', '开口呼', '复韵母', '复韵母1');
INSERT INTO `vowels` VALUES (11, 'ei', '开口呼', '复韵母', '复韵母2');
INSERT INTO `vowels` VALUES (12, 'ie', '齐齿呼', '复韵母', '复韵母2');
INSERT INTO `vowels` VALUES (13, 'üe', '撮口呼', '复韵母', '复韵母2');
INSERT INTO `vowels` VALUES (14, 'ao', '开口呼', '复韵母', '复韵母3');
INSERT INTO `vowels` VALUES (15, 'ou', '开口呼', '复韵母', '复韵母4');
INSERT INTO `vowels` VALUES (16, 'iu', '齐齿呼', '复韵母', '复韵母4');
INSERT INTO `vowels` VALUES (17, 'ui', '合口呼', '复韵母', '复韵母4');
INSERT INTO `vowels` VALUES (18, 'an', '开口呼', '前鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (19, 'en', '开口呼', '前鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (20, 'in', '齐齿呼', '前鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (21, 'un', '合口呼', '前鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (22, 'uan', '合口呼', '前鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (23, 'ang', '开口呼', '后鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (24, 'eng', '开口呼', '后鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (25, 'ong', '开口呼', '后鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (26, 'ing', '齐齿呼', '后鼻韵母', '鼻韵母');
INSERT INTO `vowels` VALUES (27, '-i', '开口呼', '单韵母', '单韵母1');
INSERT INTO `vowels` VALUES (28, 'i-', '开口呼', '单韵母', '单韵母1');

SET FOREIGN_KEY_CHECKS = 1;
