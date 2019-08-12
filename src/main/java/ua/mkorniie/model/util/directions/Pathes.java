package ua.mkorniie.model.util.directions;

import static ua.mkorniie.model.util.directions.Pages.*;

//TODO: finish pathes!
public enum  Pathes {
    MAIN("/", INDEX),

    ADMIN_MAIN("/admin/", ADMIN_MAIN_PAGE),
    ADMIN_APPROVE("/admin/approve", ADMIN_REQUESTAPPROVE_PAGE),
    ADMIN_USERS("/admin/users", ADMIN_USERS_PAGE),
    ADMIN_BILLS("/admin/bills", ADMIN_BILLS_PAGE),
    ADMIN_ROOMS("/admin/rooms", ADMIN_ROOMS_PAGE),
    USER_MAIN("/user/", USER_MAIN_PAGE),

    LOGOUT("/logout", ADMIN_MAIN.page),
    APPROVE_REQ("/admin/approve", null);;


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
        return page.getFullUrl();
    }
}
