package com.refugees.db.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.refugees.db.model.Category;
import com.refugees.db.model.InterviewCategory;

import net.hitachifbbot.utils.DBUtils;

public class InterviewCategoryService {
	public static List<InterviewCategory> getAllCategories() throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_category_name, create_date, update_date FROM mst_interview_category order by interview_category_id;",
				ps -> {
				}, r -> {
					List<InterviewCategory> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildCategoryFromResultSet(r));
					}
					return list;
				});
	}

	public static InterviewCategory findCategory(int categoryId, int version) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_category_name, create_date, update_date FROM mst_interview_category where interview_category_id=? and interview_category_version=?;",
				ps -> {

					ps.setInt(1, categoryId);
					ps.setInt(2, version);
				}, r -> {

					if (r.next()) {
						return buildCategoryFromResultSet(r);
					} else
						return null;
				});
	}

	public static InterviewCategory findLatestCategory(int categoryId) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_category_name, create_date, update_date FROM mst_interview_category where  interview_category_id=? and interview_category_version in (select max(interview_category_version) category_version from mst_interview_category where interview_category_id=?);",
				ps -> {
					ps.setInt(1, categoryId);
					ps.setInt(2, categoryId);
				}, r -> {

					if (r.next()) {
						return buildCategoryFromResultSet(r);
					} else
						return null;
				});
	}

	private static InterviewCategory buildCategoryFromResultSet(ResultSet r) throws SQLException {
		InterviewCategory category = new InterviewCategory();
		category.setCategoryId(r.getInt("interview_category_id"));
		category.setCategoryVersion(r.getInt("interview_category_version"));
		category.setCategoryName(r.getString("interview_category_name"));
		category.setCreationDate(r.getDate("create_date"));
		category.setUpdateDate(r.getDate("update_date"));
		return category;
	}

}
