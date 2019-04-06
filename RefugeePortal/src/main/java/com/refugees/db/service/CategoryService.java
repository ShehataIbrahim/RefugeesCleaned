package com.refugees.db.service;

import com.refugees.db.model.Category;
import com.refugees.db.model.InterviewDisplayAnswer;
import com.refugees.consolidate.model.InterviewAnswerBaseData;
import net.hitachifbbot.utils.DBUtils;

import java.sql.SQLException;
import java.util.*;

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

	public static List<Category> getHealtnCategories() throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id category_id,\n" +
						"interview_category_name category_name from mst_interview_category;", ps -> {
				}, r -> {
					List<Category> list = new ArrayList<>();
					while (r.next()) {
						int i = 0;
						list.add(new Category(r.getInt(++i), r.getString(++i)));
					}
					return list;
				});
	}
	public static List<Category> getHealtnCategories(String ids) throws SQLException {

		return DBUtils.preparedStatementHealth(
				"SELECT interview_category_id category_id,\n" +
						"interview_category_name category_name from mst_interview_category where interview_category_id in("+ids+");", ps -> {
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

	public static Map<Category, List<ScreeningQuestion>> getCategoryQuestions(List<Category> healtnCategories, Collection<InterviewAnswerBaseData> patientAnswers) throws SQLException
	{
		Map<Category, List<ScreeningQuestion>> result=new HashMap<>();
		Map<Category,List<InterviewAnswerBaseData>> answers=new HashMap<>();
		Map<String,Category> cats=new HashMap<>();
		for(Category ca:healtnCategories)
			cats.put(String.valueOf(ca.getCategoryId()),ca);
		for(InterviewAnswerBaseData baseData:patientAnswers)
		{
			Category ca=cats.get(baseData.getInterview_category_id());
			if(!answers.containsKey(ca))
				answers.put(ca,new ArrayList<>());
			answers.get(ca).add(baseData);
		}
		for(Category c:healtnCategories)
		{
			result.put(c, getCategoryQuestions(answers.get(c)));
		}
		return result;
	}

	public static final HashMap<String, InterviewDisplayAnswer> allAnswers;
	static {
		allAnswers=new HashMap<>();
		try {
			InterviewAnswerService.getAllDisplayAnswers().forEach(answer -> {
				allAnswers.put(answer.objectId(),answer);
			});
		}catch(Exception e)
		{

		}
	}
	public static List<ScreeningQuestion> getCategoryQuestions(Collection<InterviewAnswerBaseData> answers) throws SQLException
	{
		List<ScreeningQuestion> result=new ArrayList<>();
		for(InterviewAnswerBaseData c:answers)
		{
			InterviewDisplayAnswer display = allAnswers.get(c.objectId());
			ScreeningQuestion question=new ScreeningQuestion();
			question.setQuestionTextKey(display.getInterviewItem());
			question.setCategoryId(display.getCategoryId());
			question.setQuestionId(display.getInterviewId());
			question.setqId(display.objectId());
			question.setType(display.getType());
			question.setAllowedAnswers(display.getAllowedAnswers());
			result.add(question);
		}
		return result;
	}
}
