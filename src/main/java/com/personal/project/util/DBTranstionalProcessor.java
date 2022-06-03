package com.personal.project.util;

import java.sql.*;

@FunctionalInterface
public interface DBTranstionalProcessor {

    void process(Connection conn) throws Exception;

}
