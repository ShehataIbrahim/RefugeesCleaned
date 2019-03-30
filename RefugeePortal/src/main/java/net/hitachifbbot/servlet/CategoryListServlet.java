package net.hitachifbbot.servlet;

import net.hitachifbbot.utils.DBUtils;
import net.hitachifbbot.utils.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CategoryListServlet extends AppServlet {

    /**
     * GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        try{
            List<Category> result = DBUtils.preparedStatement("select " +
                            "scat.category_id, scat.category_name " +
                            "from screening_category as scat ;",
                    ps -> {
                    },
                    r -> {
                        List<Category> list = new ArrayList<>();
                        while(r.next()){
                            int i = 0;
                            list.add(new Category(
                                    r.getInt(++i),
                                    r.getString(++i)
                            ));
                        }
                        return list;
                    });
            HashMap<String,Object> map = new HashMap<String,Object>(){{
                put("resultList", result);
            }};
            Template.responseTranslated("doctor/screening_list",map,req,resp);
        }catch (SQLException e) {
            throw new IOException(e);
        }
    }

    public static String getFormattedDate(Timestamp gmtDate){
        Timestamp ts = gmtDate;
        LocalDateTime gmtLdt = ts.toLocalDateTime();
        ZonedDateTime gmtZdt = gmtLdt.atZone(ZoneId.of("GMT", ZoneId.SHORT_IDS));
        ZonedDateTime jstZdt = gmtZdt.withZoneSameInstant(ZoneId.of("JST", ZoneId.SHORT_IDS));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        return formatter.format(jstZdt);
    }

    /**
     * POST
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }

    public class Category{
        private int categoryId;
        private String categoryName;
        public Category() {
		}
        
		public Category(int categoryId, String categoryName) {
			super();
			this.categoryId = categoryId;
			this.categoryName = categoryName;
		}

		public int getCategoryId() {
			return categoryId;
		}
		public void setCategoryId(int categoryId) {
			this.categoryId = categoryId;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}
        

    }
}
//select distinct on(nu.nammin_id) s.screening_id, s.answered_at, nu.nammin_id, nu.nammin_name, sr.result from screening as s join nammin_user as nu on nu.nammin_id = s.nammin_id join screening_result as sr on sr.screening_id = s.screening_id where sr.category_id = 5 order by nu.nammin_id, s.answered_at desc;