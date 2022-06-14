package com.personal.project.util;

import java.sql.*;
import java.util.*;

public class SQLExecutor<T> {

    public T runQuery(String sqlStatement, List<Object> parameters,DBQueryProcessor<T> processor) throws Exception {
        try (Connection conn = MysqlDataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
                if (parameters != null && parameters.size() > 0) {
                    for (int i = 0; i < parameters.size(); i++)
                        st.setObject(i + 1, parameters.get(i));
                }
                ResultSet rs = st.executeQuery();
                T t = processor.process(rs);
                return t;
            }
        }
    }



    public static boolean runTransaction(DBTranstionalProcessor processor) throws Exception {
        Connection con=MysqlDataSource.getConnection();
        try{
            con.setAutoCommit(false);
            processor.process(con);
            con.commit();
            return true;
        }catch (SQLException e){
            e.printStackTrace();
            con.rollback();
            return false;
        }finally {
            con.setAutoCommit(true);
            con.close();
        }

    }

    public static Long runInsertWithPK(String sqlStatement, List<Object> parameters) throws Exception {
        try (Connection conn = MysqlDataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sqlStatement,Statement.RETURN_GENERATED_KEYS)) {
                if (parameters != null && parameters.size() > 0) {
                    for (int i = 0; i < parameters.size(); i++)
                        st.setObject(i + 1, parameters.get(i));

                }
                st.executeUpdate();
                ResultSet rs=st.getGeneratedKeys();
                if(rs.next())
                    return rs.getLong(1);
                else
                    return null;
            }
        }
    }

    public static int runUpdate(String sqlStatement, List<Object> parameters) throws Exception {
        try (Connection conn = MysqlDataSource.getConnection()) {
            try (PreparedStatement st = conn.prepareStatement(sqlStatement)) {
                if (parameters != null && parameters.size() > 0) {
                    for (int i = 0; i < parameters.size(); i++)
                        st.setObject(i + 1, parameters.get(i));
                }
                return st.executeUpdate();
            }
        }
    }


    public static boolean hasColumn(ResultSet rs, String columnName) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int columns = rsmd.getColumnCount();
        for (int x = 1; x <= columns; x++) {
            if (columnName.equals(rsmd.getColumnName(x))) {
                return true;
            }
        }
        return false;
    }
}
