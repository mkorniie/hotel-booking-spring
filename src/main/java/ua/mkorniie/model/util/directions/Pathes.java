package ua.mkorniie.model.util.directions;

import static ua.mkorniie.model.util.directions.Pages.*;

//TODO: finish pathes!
public enum  Pathes {
    MAIN("/", INDEX),

    ADMIN_MAIN("/admin/", ADMIN_MAIN_PAGE),
    ADMIN_APPROVE("/admin/approve", ADMIN_REQUESTAPPROVE_PAGE),
    ADMIN_USERS("/admin/users", ADMIN_USERS_PAGE),
    ADMIN_TABLES("/admin/tables", ADMIN_TABLES_PAGE),
    USER_MAIN("/user/", USER_MAIN_PAGE),

    LOGOUT("/logout", ADMIN_MAIN.page),
    APPROVE_REQ("/approve", null);;


    private String url;
    private Pages page;

    Pathes(String url, Pages page) {
        this.url = url;
        this.page = page;
    }

    public String getUrl() {
        return url;
    }
    public String getPage() {
        return page.getFullUrl();
    }
}
