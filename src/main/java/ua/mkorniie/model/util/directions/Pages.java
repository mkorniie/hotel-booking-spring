package ua.mkorniie.model.util.directions;

public enum Pages {
    LOGIN_PAGE("templates/login.html"),
    REGISTER_PAGE("templates/register-form.html"),
    INDEX_PAGE("templates/index.html"),
    GENERAL_ERROR("templates/error.html"),

    ADMIN_MAIN_PAGE("templates/admin/admin_main.html"),
    ADMIN_REQUEST_APPROVE_PAGE("templates/admin/approve-request.html"),
    ADMIN_BILLS_PAGE("templates/admin/bills-table.html"),
    ADMIN_ROOMS_PAGE("templates/admin/rooms-table.html"),
    ADMIN_USERS_PAGE("templates/admin/users_management.jsp"),

    USER_MAIN_PAGE("templates/user/user-main.html"),
    USER_BILLS_PAGE("templates/user/user-bills.html"),
    USER_REQUESTS_PAGE("templates/user/user-requests.html");


    private String path;

    Pages(String path) {
        this.path = path;
    }

    public String getFullPath() {
        return path;
    }

    public String getCropPath() {
        int startIndex = "templates/".length();
        int endIndex = path.indexOf(".");
        return path.substring(startIndex, endIndex);
    }
}
