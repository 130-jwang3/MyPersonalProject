package com.personal.project.util;

import java.sql.*;

@FunctionalInterface
public interface DBQueryProcessor<T> {

    T process(ResultSet rs) throws Exception;
}
