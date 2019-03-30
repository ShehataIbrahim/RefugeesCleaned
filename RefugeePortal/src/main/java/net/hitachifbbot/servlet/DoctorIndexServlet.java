package net.hitachifbbot.servlet;

import net.hitachifbbot.filter.CSRFFilter;
import net.hitachifbbot.utils.DBUtils;
import net.hitachifbbot.utils.Template;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DoctorIndexServlet extends AppServlet {

    /**
     * GETメソッド処理
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
//        try{
//            List<Category> category = DBUtils.preparedStatement("SELECT * FROM screening_category", // 全カテゴリを取得
//                    ps -> {},
//                    r -> {
//                        List<Category> list = new ArrayList<>();
//                        while(r.next()){
//                            int i = 0;
//                            list.add(new Category(
//                                    r.getString(++i),
//                                    r.getString(++i)
//                            ));
//                        }
//                        return list;
//                    });
//
//            HashMap<String,Object> map = new HashMap<>();
//            map.put("category", category);
//        }catch (SQLException e) {
//            throw new IOException(e);
//        }
        // 本当は全体的にこれ入れたいがデグレたくないのでここだけ...
        resp.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        resp.setHeader("Pragma", "no-cache");
        Template.responseTranslated("doctor/index", new HashMap<String, Object>(){{
            put("csrfTokenName", CSRFFilter.CSRF_TOKEN_PARAM_NAME);
            put("csrfToken", CSRFFilter.getCSRFToken(req));
        }},req,resp);
    }

    /**
     * POST処理
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
    }


    /**
     * カテゴリクラス
     */
    public static class Category{
        // カテゴリID
        public String categoryID;

        // カテゴリ名
        public String categoryName;

        /**
         * コンストラクタ
         * @param categoryID　カテゴリID
         * @param categoryName　カテゴリ名
         */
        public Category(String categoryID, String categoryName){
            this.categoryID = categoryID;
            this.categoryName = categoryName;
        }

        public String getCategoryID() {
            return categoryID;
        }

        public String getCategoryName() {
            return categoryName;
        }
    }
}
