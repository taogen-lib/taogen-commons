package com.taogen.commons.dataaccess;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Taogen
 */
public class MysqlUtils {
    public static final String ON_DUPLICATE_KEY_UPDATE = " ON DUPLICATE KEY UPDATE ";

    public static final String DEFAULT_JAVA_TYPE = "String";

    public static final String DEFAULT_JDBC_TYPE = "VARCHAR";

    public static final Map<String, List<String>> JAVA_TYPE_DB_TYPES_MAP = new HashMap<String, List<String>>();

    public static final Map<String, String> DB_TYPE_JDBC_TYPE_MAP = new HashMap<String, String>();

    static {
        JAVA_TYPE_DB_TYPES_MAP.put("Integer", Arrays.asList("tinyint", "smallint", "mediumint", "int", "bit", "year"));
        JAVA_TYPE_DB_TYPES_MAP.put("Long", Arrays.asList("bigint"));
        JAVA_TYPE_DB_TYPES_MAP.put("String", Arrays.asList("char", "varchar", "tinytext", "text", "mediumtext", "longtext", "json"));
        JAVA_TYPE_DB_TYPES_MAP.put("Date", Arrays.asList("date", "datetime", "timestamp", "time"));
        JAVA_TYPE_DB_TYPES_MAP.put("Double", Arrays.asList("float", "double"));
        JAVA_TYPE_DB_TYPES_MAP.put("BigDecimal", Arrays.asList("decimal"));
        JAVA_TYPE_DB_TYPES_MAP.put("Byte[]", Arrays.asList("binary", "varbinary", "tinyblob", "blob", "mediumblob", "longblob"));
        JAVA_TYPE_DB_TYPES_MAP.put("enum", Arrays.asList("enum"));
        JAVA_TYPE_DB_TYPES_MAP.put("set", Arrays.asList("set"));

        DB_TYPE_JDBC_TYPE_MAP.put("tinyint", "TINYINT");
        DB_TYPE_JDBC_TYPE_MAP.put("smallint", "SMALLINT");
        DB_TYPE_JDBC_TYPE_MAP.put("mediumint", "INTEGER");
        DB_TYPE_JDBC_TYPE_MAP.put("int", "INTEGER");
        DB_TYPE_JDBC_TYPE_MAP.put("bigint", "BIGINT");
        DB_TYPE_JDBC_TYPE_MAP.put("bit", "BIT");
        DB_TYPE_JDBC_TYPE_MAP.put("year", "INTEGER");
        DB_TYPE_JDBC_TYPE_MAP.put("char", "CHAR");
        DB_TYPE_JDBC_TYPE_MAP.put("varchar", "VARCHAR");
        DB_TYPE_JDBC_TYPE_MAP.put("tinytext", "CLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("text", "CLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("mediumtext", "CLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("longtext", "CLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("json", "VARCHAR");
        DB_TYPE_JDBC_TYPE_MAP.put("date", "DATE");
        DB_TYPE_JDBC_TYPE_MAP.put("datetime", "TIMESTAMP");
        DB_TYPE_JDBC_TYPE_MAP.put("timestamp", "TIMESTAMP");
        DB_TYPE_JDBC_TYPE_MAP.put("time", "TIME");
        DB_TYPE_JDBC_TYPE_MAP.put("float", "FLOAT");
        DB_TYPE_JDBC_TYPE_MAP.put("double", "DOUBLE");
        DB_TYPE_JDBC_TYPE_MAP.put("decimal", "DECIMAL");
        DB_TYPE_JDBC_TYPE_MAP.put("binary", "BINARY");
        DB_TYPE_JDBC_TYPE_MAP.put("varbinary", "VARBINARY");
        DB_TYPE_JDBC_TYPE_MAP.put("tinyblob", "BLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("blob", "BLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("mediumblob", "BLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("longblob", "BLOB");
        DB_TYPE_JDBC_TYPE_MAP.put("enum", "VARCHAR");
    }

    public static String escapeCharacters(String str) {
        if (str == null) {
            return null;
        }
        str = str.replace("\'", "\\\'").replace("\"", "\\\"");
        return str;
    }

    public static String dbTypeToJavaType(String columnDataType) {
        if (columnDataType != null) {
            for (Map.Entry<String, List<String>> entry : JAVA_TYPE_DB_TYPES_MAP.entrySet()) {
                if (entry.getValue().stream()
                        .anyMatch(item -> columnDataType.equalsIgnoreCase(item))) {
                    return entry.getKey();
                }
            }
        }
        return DEFAULT_JAVA_TYPE;
    }

    public static String dbTypeToJdbcType(String columnDataType) {
        if (columnDataType != null) {
            for (Map.Entry<String, String> entry : DB_TYPE_JDBC_TYPE_MAP.entrySet()) {
                if (columnDataType.equalsIgnoreCase(entry.getKey())) {
                    return entry.getValue();
                }
            }
        }
        return DEFAULT_JDBC_TYPE;
    }

    public static String removePrefix(String tableName, String tablePrefix) {
        String removePrefixTableName = tableName;
        if (tablePrefix != null && !tablePrefix.isEmpty()) {
            removePrefixTableName = removePrefixTableName.substring(
                    removePrefixTableName.indexOf(tablePrefix) + tablePrefix.length());
        }
        return removePrefixTableName;
    }
}
