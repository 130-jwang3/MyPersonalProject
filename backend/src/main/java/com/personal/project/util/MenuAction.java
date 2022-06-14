package com.personal.project.util;

public enum MenuAction {

    ADD_COURSE(1),
    ASSIGN_INSTRUCTOR(2),
    ASSIGN_TA(3),
    ENROLL_STUDENT(4),
    VIEW_ALL_STUDENT(5),
    DROP_COURSE(6),
    VIEW_ALL_COURSES(7),
    MARK_FINAL_GRADE(8),
    CHANGE_FINAL_GRADE(9),
    VIEW_GRADE_DISTRIBUTION(10),
    RETURN(11);


    private int menuAction;

    public int getMenuAction() {
        return menuAction;
    }

    public void setMenuAction(int menuAction) {
        this.menuAction = menuAction;
    }

    public static MenuAction findByID(int ID) {
        for (MenuAction e : values()) {
            if (e.menuAction==ID) {
                return e;
            }
        }
        return null;
    }

    MenuAction(int menuAction) {
        this.menuAction = menuAction;
    }
}
