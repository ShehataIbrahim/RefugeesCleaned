package com.refugees.db.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.refugees.db.model.Category;

import net.hitachifbbot.utils.DBUtils;

public class CategoryService {
	public static List<Category> getCategories() throws SQLException {

		return DBUtils.preparedStatement(
				"select scat.category_id, scat.category_name from screening_category as scat order by scat.category_id;", ps -> {
				}, r -> {
					List<Category> list = new ArrayList<>();
					while (r.next()) {
						int i = 0;
						list.add(new Category(r.getInt(++i), r.getString(++i)));
					}
					return list;
				});
	}

	public static List<ScreeningQuestion> getCategoryQuestions(int cayegoryId) throws SQLException {
		return DBUtils.preparedStatement(
				"select screening_q_id,category_id,screening_q_text from screening_q where category_id = ? order by screening_q_id;", ps -> {
					ps.setInt(1, cayegoryId);
				}, r -> {
					List<ScreeningQuestion> list = new ArrayList<>();
					while (r.next()) {
						list.add(new ScreeningQuestion(r));
					}
					return list;
				});
	}
	public static Map<Category, List<ScreeningQuestion>> getCategoryQuestions() throws SQLException
	{
		Map<Category, List<ScreeningQuestion>> result=new HashMap<>();
		for(Category c:getCategories())
		{
			result.put(c, getCategoryQuestions(c.getCategoryId()));
		}
		return result;
	}

}
