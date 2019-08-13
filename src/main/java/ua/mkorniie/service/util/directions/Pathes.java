package ua.mkorniie.service.util.directions;

import static ua.mkorniie.service.util.directions.Pages.*;

public enum  Pathes {
    MAIN("/", INDEX_PAGE),
    REGISTER("/register", REGISTER_PAGE),
    LOGIN("/login", LOGIN_PAGE),

    ADMIN_MAIN("/admin/", ADMIN_MAIN_PAGE),
    ADMIN_APPROVE("/admin/approve", ADMIN_REQUEST_APPROVE_PAGE),
    ADMIN_USERS("/admin/users", ADMIN_USERS_PAGE),
    ADMIN_BILLS("/admin/bills", ADMIN_BILLS_PAGE),
    ADMIN_ROOMS("/admin/rooms", ADMIN_ROOMS_PAGE),

    USER_MAIN("/user/", USER_MAIN_PAGE),
    USER_BILLS("/user/my-bills", USER_BILLS_PAGE),
    USER_REQUESTS("/user/my-requests", USER_REQUESTS_PAGE),

    LOGOUT("/logout", ADMIN_MAIN.page);


    private final String url;
    private final Pages page;

    Pathes(String url, Pages page) {
        this.url = url;
        this.page = page;
    }

    public String getUrl() {
        return url;
    }

    public String getPage() {
        return page.getFullPath();
    }

    public String getCropPagePath() {
        return page.getCropPath();
    }
}
