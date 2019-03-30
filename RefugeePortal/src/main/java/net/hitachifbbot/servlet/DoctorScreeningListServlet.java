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

public class DoctorScreeningListServlet extends AppServlet {

    /**
     * GET
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        Integer categoryID = Integer.parseInt(req.getParameter("c_id"));
        String categoryName = req.getParameter("c_name");
        try{
            List<ScreeningResult> result = DBUtils.preparedStatement("select " +
                            "temp1.screening_id, temp1.answered_at, nu.nammin_id, nu.nammin_name, sr.result " +
                            "from nammin_user as nu " +
                            "join screening_result as sr on sr.nammin_id = nu.nammin_id " +
                            "join " +
                            "  (SELECT nammin_id,max(screening_id) as screening_id,max(answered_at) as answered_at from screening where status='INITIAL' group by nammin_id ) as temp1 " +
                            "    on temp1.nammin_id = nu.nammin_id " +
                            "where sr.category_id = ? " +
                            " order by temp1.answered_at desc;", // 選択されたカテゴリの問診結果を取得する
                    ps -> {
                        ps.setInt(1, categoryID);
                    },
                    r -> {
                        List<ScreeningResult> list = new ArrayList<>();
                        while(r.next()){
                            int i = 0;
                            list.add(new ScreeningResult(
                                    r.getInt(++i),
                                    getFormattedDate(r.getTimestamp(++i)),
                                    r.getInt(++i),
                                    r.getString(++i),
                                    r.getString(++i),
                                    categoryID
                            ));
                        }
                        return list;
                    });
            HashMap<String,Object> map = new HashMap<String,Object>(){{
                put("categoryID", categoryID);
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

    public class ScreeningResult{
        public int screeningID;
        public String answeredAt;
        public int namminID;
        public String namminName;
        public String result;
        public int categoryID;

        public ScreeningResult(int screeningID, String answeredAt, int namminID, String namminName, String result, int categoryID) {
            this.screeningID = screeningID;
            this.answeredAt = answeredAt;
            this.namminID = namminID;
            this.namminName = namminName;
            this.result = result;
            this.categoryID = categoryID;
        }

        public int getScreeningID() {
            return screeningID;
        }

        public String getAnsweredAt() {
            return answeredAt;
        }

        public int getNamminID() {
            return namminID;
        }

        public String getNamminName() {
            return namminName;
        }

        public String getResult() {
            return result;
        }

        public int getCategoryID() {
            return categoryID;
        }
    }
}
//select distinct on(nu.nammin_id) s.screening_id, s.answered_at, nu.nammin_id, nu.nammin_name, sr.result from screening as s join nammin_user as nu on nu.nammin_id = s.nammin_id join screening_result as sr on sr.screening_id = s.screening_id where sr.category_id = 5 order by nu.nammin_id, s.answered_at desc;