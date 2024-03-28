package com.wanzi.changecode.templateaction;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author by: ly
 * @ClassName: StringFormatTypeEnum
 * @Description: String转换类枚举
 * @Date: 2023/12/21 上午11:47
 */
public enum TemplateType {
    /**
     * 转驼峰命名
     */
    LOMBOK("@Accessors", accessors()),
    TIME_FOR_JSON("时间[序列化]", timeForJsonContent()),
    /**
     * 创建表sql
     */
    SQL_FOR_CREATE("SQL[创建]", sqlForCreateContent()),
    /**
     * 更新表sql
     */
    SQL_FOR_UPDATE("SQL[更新]", sqlForUpdateContent());
    private final String type;
    private final String content;

    TemplateType(String type, String content) {
        this.type = type;
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public static List<String> types() {
        return Stream.of(TemplateType.values()).map(TemplateType::getType).collect(Collectors.toList());
    }

    public static String content(String type) {
        return Stream.of(TemplateType.values()).filter(item -> type.equals(item.type)).findAny().get().content;
    }


    private static String accessors() {
        return "@Data\n" +
                "@Builder\n" +
                "@NoArgsConstructor\n" +
                "@AllArgsConstructor";
    }

    private static String timeForJsonContent() {
        return "@DateTimeFormat(pattern = \"yyyy-MM-dd HH:mm:ss\")\n" +
                "    @JsonFormat(pattern = \"yyyy-MM-dd HH:mm:ss\", timezone = \"GMT+8\")";
    }
    private static String sqlForCreateContent() {
        return "CREATE TABLE IF NOT EXISTS nsy_oms_publish.demo_table\n" +
                "(\n" +
                "    `id`                INT(10) UNSIGNED                       NOT NULL AUTO_INCREMENT,\n" +
                "    `website_id`        INT(11)                                NOT NULL DEFAULT 0 COMMENT '店铺id',\n" +
                "    `website_name`      VARCHAR(50)                            NOT NULL DEFAULT '' COMMENT '店铺名',\n" +
                "\n" +
                "    `status`            ENUM ('Active', 'Inactive', 'Pending') NOT NULL comment '枚举类型',\n" +
                "\n" +
                "    `is_tinyint`        tinyint(1)                                      default 0 not null comment '是否--[0:否,1:是] 默认:-128到127',\n" +
                "    `price`             decimal(18, 2)                         NOT NULL DEFAULT '0.00' COMMENT '支付金额',\n" +
                "\n" +
                "    `example_year`      YEAR                                   NULL     DEFAULT NULL COMMENT '格式为 ''YYYY''',\n" +
                "    `example_date`      DATE                                   NULL     DEFAULT NULL COMMENT '格式为 ''YYYY-MM-DD''',\n" +
                "    `example_time`      TIME                                   NULL     DEFAULT NULL COMMENT '格式为 ''HH:MM:SS''',\n" +
                "    `example_datetime`  DATETIME                               NULL     DEFAULT NULL COMMENT '格式为 ''YYYY-MM-DD HH:MM:SS'', 用于同时表示日期和时间',\n" +
                "    `example_timestamp` TIMESTAMP                              NULL     DEFAULT NULL COMMENT '格式为 ''YYYY-MM-DD HH:MM:SS'', 范围为 ''1970-01-01 00:00:01'' UTC 到 ''2038-01-19 03:14:07'' UTC [带时区]',\n" +
                "\n" +
                "    `create_by`         VARCHAR(50)                            NOT NULL DEFAULT '' COMMENT '创建人',\n" +
                "    `create_date`       TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',\n" +
                "    `update_by`         VARCHAR(50)                            NOT NULL DEFAULT '' COMMENT '更新人',\n" +
                "    `update_date`       TIMESTAMP                              NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '更新时间',\n" +
                "    `version`           INT(11)                                NOT NULL DEFAULT 0 COMMENT '版本号',\n" +
                "    `location`          VARCHAR(50)                            NOT NULL DEFAULT 'QUANZHOU' COMMENT '公司名称',\n" +
                "    PRIMARY KEY (`id`),\n" +
                "    KEY `idx_website_id` (`website_id`),\n" +
                "    KEY `idx_website_id_website_name` (`website_id`, `website_name`)\n" +
                ") ENGINE = INNODB\n" +
                "  DEFAULT CHARSET = utf8mb4\n" +
                "  COLLATE = utf8mb4_unicode_ci COMMENT = 'demo表';";
    }
    private static String sqlForUpdateContent() {
        return "CREATE TABLE IF NOT EXISTS nsy_backup.demo_table_spxt_1110 LIKE nsy_oms_publish.demo_table;\n" +
                "\n" +
                "INSERT INTO nsy_backup.demo_table_spxt_1110\n" +
                "SELECT *\n" +
                "FROM nsy_oms_publish.demo_table\n" +
                "WHERE website_id IN (155, 156, 157, 158);\n" +
                "\n" +
                "UPDATE nsy_oms_publish.demo_table c\n" +
                "    INNER JOIN nsy_backup.demo_table_spxt_1110 b ON c.id = b.id\n" +
                "SET c.website_id = 1,\n" +
                "    c.update_by= '#SPXT-1110'\n" +
                "WHERE 1 = 1;";
    }




}
