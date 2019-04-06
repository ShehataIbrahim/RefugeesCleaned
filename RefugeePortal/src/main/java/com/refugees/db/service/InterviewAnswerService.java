package com.refugees.db.service;

import com.refugees.consolidate.model.AllowedAnswer;
import com.refugees.db.model.*;
import net.hitachifbbot.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InterviewAnswerService {
	public static List<InterviewAnswer> getAnswers() throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, answer_id, answer_item, create_date, update_date FROM mst_answer;",
				ps -> {
				}, r -> {
					List<InterviewAnswer> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewAnswerFromResultSet(r));
					}
					return list;
				});
	}
	public static List<InterviewAnswer> getAllInterviewAnswers(Interview interview) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, answer_id, answer_item, create_date, update_date FROM mst_answer where interview_category_id=? and interview_category_version=? and interview_id=? ;",
				ps -> {
					ps.setInt(1, interview.getCategoryId());
					ps.setInt(2, interview.getCategoryVersion());
					ps.setInt(3, interview.getInterviewId());
				}, r -> {
					List<InterviewAnswer> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewAnswerFromResultSet(r));
					}
					return list;
				});
	}
	public static List<InterviewAnswer> getAllCategoryAnswers(InterviewCategory category) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, answer_id, answer_item, create_date, update_date FROM mst_answer where interview_category_id=? and interview_category_version=? ;",
				ps -> {
					ps.setInt(1, category.getCategoryId());
					ps.setInt(2, category.getCategoryVersion());
				}, r -> {
					List<InterviewAnswer> list = new ArrayList<>();
					while (r.next()) {
						list.add(buildInterviewAnswerFromResultSet(r));
					}
					return list;
				});
	}
	public static InterviewAnswer buildInterviewAnswerFromResultSet(ResultSet r) throws SQLException {
		InterviewAnswer answer=new InterviewAnswer();
		answer.setInterviewId(r.getInt("interview_id"));
		answer.setCategoryId(r.getInt("interview_category_id"));
		answer.setCategoryVersion(r.getInt("interview_category_version"));

		answer.setCreationDate(r.getDate("create_date"));
		answer.setUpdateDate(r.getDate("update_date"));
		answer.setAnswerId(r.getInt("answer_id"));
		answer.setAnswerItem(r.getString("answer_item"));
		return answer;
	}
	public static List<InterviewDisplayAnswer> getCategoryDisplayAnswers(int categoryId,int version)throws SQLException 
	{
		return DBUtils.preparedStatementHealth(
				"select mst_answer.interview_id,mst_answer.answer_id, mst_interview.interview_item,mst_interview.answer_type,mst_answer.answer_item from mst_answer inner join mst_interview on mst_answer.interview_id = mst_interview.interview_id where mst_interview.interview_category_id=? and mst_interview.interview_category_version=? order by mst_interview.interview_category_id,mst_interview.interview_id;",
				ps -> {
					ps.setInt(1, categoryId);
					ps.setInt(2, version);
				}, r -> {
					List<InterviewDisplayAnswer> list = new ArrayList<>();
					while (r.next()) {
						InterviewDisplayAnswer answer=buildInterviewDisplayAnswerFromResultSet(r);
						answer.setCategoryId(categoryId);
						answer.setCategoryVersion(version);
						list.add(answer);
					}
					return list;
				});
	}
	public static List<InterviewDisplayAnswer> getAllDisplayAnswers()throws SQLException 
	{
		return DBUtils.preparedStatementHealth(
				"select interview_category_id, interview_category_version,interview_id,answer_id, interview_item,answer_type,answer_item from interview_questions_view;",
				ps -> {
				}, r -> {
					List<InterviewDisplayAnswer> list = new ArrayList<>();
					while (r.next()) {
						InterviewDisplayAnswer answer=buildInterviewDisplayAnswerFromResultSet(r);
						answer.setCategoryId(r.getInt("interview_category_id"));
						answer.setCategoryVersion(r.getInt("interview_category_version"));
						list.add(answer);
					}
					final HashMap<String, InterviewDisplayAnswer> displayItems=new HashMap<>();
					list.stream().forEach(question->{

						if(displayItems.containsKey(question.objectId()) && !AnswerTypesEnum.FREE.toString().equalsIgnoreCase(question.getAnswerType()))
						{
							InterviewDisplayAnswer d=displayItems.get(question.objectId());
							AllowedAnswer answer=new AllowedAnswer();
							answer.setAnswer(question.getAnswerItem());
							answer.setAnswerId(question.getAnswerId());
							d.getAllowedAnswers().add(answer);
						}else
						{
							if(!AnswerTypesEnum.FREE.toString().equalsIgnoreCase(question.getAnswerType())) {
								AllowedAnswer answer = new AllowedAnswer();
								answer.setAnswer(question.getAnswerItem());
								answer.setAnswerId(question.getAnswerId());
								question.getAllowedAnswers().add(answer);
							}
							displayItems.put(question.objectId(),question);
						}
					});
					return new ArrayList<>(displayItems.values());

				});
	}	
	public static List<InterviewDisplayAnswer> getAllFreeDisplayAnswers()throws SQLException 
	{
		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, interview_item, answer_type,1 as answer_id , null as answer_item, create_date, update_date FROM mst_interview where upper(answer_type)='FREE';",
				ps -> {
				}, r -> {
					List<InterviewDisplayAnswer> list = new ArrayList<>();
					while (r.next()) {
						InterviewDisplayAnswer answer=buildInterviewDisplayAnswerFromResultSet(r);
						answer.setCategoryId(r.getInt("interview_category_id"));
						answer.setCategoryVersion(r.getInt("interview_category_version"));
						list.add(answer);
					}
					return list;
				});
	}
	public static List<InterviewDisplayAnswer> getAllCategoryFreeDisplayAnswers(int categoryId,int version)throws SQLException 
	{
		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id, interview_category_version, interview_id, interview_item, answer_type,1 as answer_id , null as answer_item, create_date, update_date FROM mst_interview where upper(answer_type)='FREE' AND interview_category_id=? AND interview_category_version=?;",
				ps -> {
					ps.setInt(1, categoryId);
					ps.setInt(2, version);
				}, r -> {
					List<InterviewDisplayAnswer> list = new ArrayList<>();
					while (r.next()) {
						InterviewDisplayAnswer answer=buildInterviewDisplayAnswerFromResultSet(r);
						answer.setCategoryId(r.getInt("interview_category_id"));
						answer.setCategoryVersion(r.getInt("interview_category_version"));
						list.add(answer);
					}
					return list;
				});
	}
	public static List<InterviewDisplayAnswer> getAllCategoryFreeDisplayAnswers(InterviewCategory category)throws SQLException 
	{
		return getAllCategoryFreeDisplayAnswers(category.getCategoryId(),category.getCategoryVersion());
	}
	public static List<InterviewDisplayAnswer> getCategoryDisplayAnswers(InterviewCategory category)throws SQLException 
	{
		return getCategoryDisplayAnswers(category.getCategoryId(),category.getCategoryVersion());
	}
	public static InterviewDisplayAnswer buildInterviewDisplayAnswerFromResultSet(ResultSet r) throws SQLException {
		InterviewDisplayAnswer answer=new InterviewDisplayAnswer();
		answer.setInterviewId(r.getInt("interview_id"));
		answer.setAnswerId(r.getInt("answer_id"));
		answer.setAnswerItem(r.getString("answer_item"));
		answer.setInterviewItem(r.getString("interview_item"));
		answer.setAnswerType(r.getString("answer_type"));
		return answer;
	}
	public static List<InterviewDisplayAnswer> cleanDisplayList(List<InterviewDisplayAnswer> mainList)
	{
		List<InterviewDisplayAnswer> toBeRemoved=new ArrayList<>();
		HashMap<Integer, InterviewDisplayAnswer> processedItems=new HashMap<>();
		mainList.stream().forEach(answer ->{
			if(processedItems.containsKey(answer.hashCode()))
			{
				toBeRemoved.add(answer);
				//processedItems.get(answer.hashCode()).getAllowedAnswers().add(answer.getAnswerItem());
			}else
				processedItems.put(answer.hashCode(), answer);
		});
		return new ArrayList<InterviewDisplayAnswer>(processedItems.values());
	}
}
