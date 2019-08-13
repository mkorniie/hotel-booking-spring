package ua.mkorniie.model.util.directions;

//TODO: finish transferring addresses here
//TODO: add servlet paths here

public enum Pages {
    ACCESS_ERROR_PAGE("templates/no-rights.html"),
    LOGIN("templates/login.html"),
    REGISTER("templates/register-form.html"),
    INDEX("templates/index.html"),
    GENERAL_ERROR("templates/error-1.html"),

    ADMIN_MAIN_PAGE("templates/admin/admin_main.html"),
    ADMIN_REQUESTAPPROVE_PAGE("templates/admin/approve-request.html"),
    ADMIN_ROOMS_PAGE("templates/admin/rooms-table.html"),
    ADMIN_BILLS_PAGE("templates/admin/bills-table.html"),
    ADMIN_USERS_PAGE("templates/admin/users_management.jsp"),

    USER_MAIN_PAGE("templates/user/user-main.html");
//    ADMIN_REQUESTAPPROVE_PAGE("templates/user/approve-request.jsp"),
//    ADMIN_TABLES_PAGE("templates/user/rooms-table.html"),
//    ADMIN_USERS_PAGE("templates/user/users_management.jsp");

    private String url;

    Pages(String url) {
        this.url = url;
    }

    public String getFullUrl() {
        return url;
    }

    public String getCropURL() {
        int startIndex = "templates/".length();
        int endIndex = url.indexOf(".");
        return url.substring(startIndex, endIndex);
    }
}
