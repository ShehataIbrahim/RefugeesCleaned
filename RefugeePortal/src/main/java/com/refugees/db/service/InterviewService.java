package com.refugees.db.service;

import com.refugees.db.model.Interview;
import com.refugees.db.model.InterviewCategory;
import net.hitachifbbot.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class InterviewService {
	public static List<Interview> getAllInterviews() throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, interview_item, answer_type, create_date, update_date FROM mst_interview;",
				ps -> {
				}, r -> {
					List<Interview> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewFromResultSet(r));
					}
					return list;
				});
	}

	public static List<Interview> getCategoryInterviews(int categoryId) throws SQLException {

		InterviewCategory category=InterviewCategoryService.findLatestCategory(categoryId);
		if(category==null)
			return new ArrayList<>();
		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, interview_item, answer_type, create_date, update_date FROM mst_interview WHERE interview_category_id=? and interview_category_version=?;",
				ps -> {
					ps.setInt(1, category.getCategoryId());
					ps.setInt(2, category.getCategoryVersion());
				}, r -> {
					List<Interview> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewFromResultSet(r));
					}
					return list;
				});
	}
	public static List<Interview> getCategoryInterviews(int categoryId,int version) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, interview_item, answer_type, create_date, update_date FROM mst_interview WHERE interview_category_id=? and interview_category_version=?;",
				ps -> {
					ps.setInt(1, categoryId);
					ps.setInt(2, version);
				}, r -> {
					List<Interview> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewFromResultSet(r));
					}
					return list;
				});
	}
	public static List<Interview> getCategoryInterviews(InterviewCategory category) throws SQLException {
		return getCategoryInterviews(category.getCategoryId(), category.getCategoryVersion());
	}
	public static Interview buildInterviewFromResultSet(ResultSet r) throws SQLException {
		Interview interview=new Interview();
		interview.setInterviewId(r.getInt("interview_id"));
		interview.setCategoryId(r.getInt("interview_category_id"));
		interview.setCategoryVersion(r.getInt("interview_category_version"));
		interview.setInterviewItem(r.getString("interview_item"));
		interview.setAnswerType(r.getString("answer_type"));
		interview.setCreationDate(r.getDate("create_date"));
		interview.setUpdateDate(r.getDate("update_date"));
		return interview;
	}
}
