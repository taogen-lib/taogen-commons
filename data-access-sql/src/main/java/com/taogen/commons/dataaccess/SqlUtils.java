package com.taogen.commons.dataaccess;

import com.taogen.commons.dataaccess.vo.LabelAndData;
import lombok.extern.log4j.Log4j2;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author taogen
 */
@Log4j2
public class SqlUtils {
    public LabelAndData executeQuery(DataSource dataSource, String sql) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            log.debug("connection: {}", connection.toString());
            try (Statement statement = connection.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    ResultSetMetaData metaData = resultSet.getMetaData();
                    List<String> labels = getQueryLabelsByMetaData(metaData);
                    List<List<Object>> resultData = getQueryResultData(resultSet, metaData.getColumnCount());
                    return new LabelAndData(labels, resultData);
                }
            }
        }
    }

    private List<String> getQueryLabelsByMetaData(ResultSetMetaData resultSetMetaData) throws SQLException {
        List<String> labels = new ArrayList<>();
        int columnNum = resultSetMetaData.getColumnCount();
        for (int i = 0; i < columnNum; i++) {
            labels.add(resultSetMetaData.getColumnLabel(i + 1));
        }
        return labels;
    }


    private List<List<Object>> getQueryResultData(ResultSet resultSet, int columnNum) throws SQLException {
        List<List<Object>> valuesList = new ArrayList<>();
        int row = 0;
        while (resultSet.next()) {
            row++;
            log.debug("row is {}", row);
            List<Object> values = new ArrayList<>();
            for (int i = 0; i < columnNum; i++) {
                Object value = resultSet.getObject(i + 1);
                values.add(value);
            }
            valuesList.add(values);
        }
        return valuesList;
    }
}
