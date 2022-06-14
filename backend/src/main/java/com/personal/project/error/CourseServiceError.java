package com.personal.project.error;

import java.util.*;

public class CourseServiceError {

    public static List<ServiceError> ERRORS = new ArrayList<ServiceError>();

    static {
        ERRORS.add(new ServiceError(-1, "System Error"));
        ERRORS.add(new ServiceError(-2, "Duplicate record"));
    }

    public static ServiceError getError(int errorCode){
       return  ERRORS.stream().filter(err->err.getErrorCode()==errorCode)
                     .findFirst().orElse(new ServiceError(-999,"Other Error"));
    }


}
