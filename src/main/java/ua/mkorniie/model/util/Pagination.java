package ua.mkorniie.model.util;

import com.sun.istack.internal.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;


public class Pagination<T> {
    @Getter @Setter private static int entriesPerPage = 3;

    public int getNumberOfPages(@NotNull List<T> allEntries) {
        Integer size = allEntries.size();
        if (size == 0) {
            return 1;
        }
        return (size / entriesPerPage) + (size % entriesPerPage == 0 ? 0 : 1);
    }

    public List<T> getEntries(@NotNull List<T> allEntries, int pageNum) {
        int startIndex = entriesPerPage * (pageNum - 1);
        int endIndex = startIndex + entriesPerPage - 1;

        List<T> result = new ArrayList<T>();
        if (allEntries.size() < (startIndex + 1)) {
            return result;
        }
        if ((endIndex + 1) > allEntries.size()) {
            endIndex = allEntries.size() - 1;
        }
        for (int i = startIndex; i <= endIndex; i++) {
            result.add(allEntries.get(i));
        }
        return result;
    }

    public void paginate(String tag, @NotNull List<T> allEntries, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        String page = request.getParameter("page");
        String table = request.getParameter("table");

        int pageCount = getNumberOfPages(allEntries);
        request.setAttribute("page-count" + tag, pageCount);

        
        int pageId = 1;
        try {
            pageId = Integer.parseInt(page);
        } catch (Exception e) { }
        request.setAttribute("entries" + tag, getEntries(allEntries, pageId));
        request.setAttribute("active-page" + tag, pageId);
    }

    /**
     * Sets a page count and page content (retrieves page number from GET request)
     * @param allEntries - list of all entries (from Repo)
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    public void paginate(@NotNull List<T> allEntries, @NotNull HttpServletRequest request, @NotNull HttpServletResponse response) {
        String page = request.getParameter("page");
        int pageCount = getNumberOfPages(allEntries);
        request.setAttribute("page-count", pageCount);

        int pageId = 1;
        try {
            pageId = Integer.parseInt(page);
        } catch (Exception e) { }
        request.setAttribute("entries", getEntries(allEntries, pageId));
        request.setAttribute("active-page", pageId);
    }
}
