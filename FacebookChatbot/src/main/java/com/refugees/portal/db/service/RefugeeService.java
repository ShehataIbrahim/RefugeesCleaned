package com.refugees.portal.db.service;

import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Service;

import com.refugees.portal.db.model.FacebookUser;
import com.refugees.portal.db.model.RefugeeUser;
import com.refugees.portal.db.model.Screening;
import com.refugees.portal.db.model.ScreeningQuestions;
import com.refugees.portal.db.repositories.FacebookUserRepository;
import com.refugees.portal.db.repositories.RefugeeUserRepository;
import com.refugees.portal.db.repositories.ScreeningQuestionRepository;
import com.refugees.portal.db.repositories.ScreeningRepository;

@Service
@EnableJpaRepositories(basePackages = { "com.refugees.portal.db.repositories" })
@EntityScan(basePackages = { "com.refugees.portal.db.model" })
public class RefugeeService {
	final static Logger logger = LoggerFactory.getLogger(RefugeeService.class);
	@Autowired
	RefugeeUserRepository refugeeRepository;

	@Autowired
	ScreeningQuestionRepository questionsRepo;

	@Autowired
	FacebookUserRepository facebookUserRepo;

	@Autowired
	ScreeningRepository screeningRepository;

	//@Autowired
	//ScreeningAnswerRepository screeningAnswerRepository;

	@Autowired
	DataSource datasource;

	public RefugeeUser findRefugeeByUserName(String userName) {
		try {
			return refugeeRepository.findByName(userName);
		} catch (Exception e) {
			logger.warn("findRefugeeByUserName", e);
			return null;
		}
	}

	public RefugeeUser findRefugeeByEmail(String email) {
		try {
			return refugeeRepository.findByMailAddress(email);
		} catch (Exception e) {
			logger.warn("findRefugeeByEmail", e);
			e.printStackTrace();
			return null;
		}
	}

	public RefugeeUser findRefugeeByFacebookId(String fbId) {
		try {
			return refugeeRepository.findByFacebookUserId(fbId);
		} catch (Exception e) {
			e.printStackTrace();
			logger.warn("findRefugeeByFacebookId", e);
			return null;
		}
	}

	public void reflectRefugeeUser(RefugeeUser user) {
		try {
			refugeeRepository.save(user);
		} catch (Exception e) {
			logger.warn("reflectRefugeeUser", e);
		}
	}

	public List<ScreeningQuestions> getAllQuestions() {
		return questionsRepo.findAll();
	}

	public FacebookUser findFacebookUser(String senderId) {
		try {
			Optional<FacebookUser> user = facebookUserRepo.findById(senderId);
			if (user.isPresent())
				return user.get();
		} catch (Exception e) {
			e.printStackTrace();

		}
		return null;
	}

	public void updateFacebook(FacebookUser user) {
		facebookUserRepo.save(user);
	}

	public void updateRefugeeUser(RefugeeUser user) {
		refugeeRepository.save(user);
	}

	public void initiateFacebookUser(String senderId) throws Exception {
		FacebookUser user = new FacebookUser();
		user.setSenderId(senderId);

		facebookUserRepo.save(user);
	}

	public ScreeningQuestions findQuestionById(String id) {
		Optional<ScreeningQuestions> q = questionsRepo.findById(Integer.valueOf(id));
		if (q.isPresent())
			return q.get();
		return null;
	}

	public Integer findNextQuestion(String id) {
		Integer nextId = null;
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			// select next question id from database based on the current id
			ResultSet result = connection.createStatement()
					.executeQuery("select min(screening_q_id) nextId from screening_q where screening_q_id>" + id);
			if (result.next())
				nextId = result.getInt("nextId");
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}
		}
		return nextId;
	}
	public Integer findNextScreeningId() {
		Integer nextId = null;
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			// select next question id from database based on the current id
			ResultSet result = connection.createStatement()
					.executeQuery("select (screening_id) max_id from screening");
			if (result.next())
				nextId = result.getInt("max_id");
			else
				nextId=0;
		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			if (connection != null)
				try {
					connection.close();
				} catch (Exception e) {
				}
		}
		nextId+=1;
		return nextId;
	}
	public Screening createScreeningData(int userId) {
		Screening screening = new Screening();
		screening.setId(findNextScreeningId());
		screening.setUserId(userId);
		screening.setStatus("FB_ONGOING");
		screening = screeningRepository.save(screening);
		return screening;
	}

	public Screening getUserScreeningData(int userId) throws ScreenedBeforeException{
		List<Screening> screeningList = screeningRepository.findByUserId(userId);
		if (screeningList == null || screeningList.isEmpty())
			return null;
		if("FB_ONGOING".equalsIgnoreCase(screeningList.get(screeningList.size() - 1).getStatus()))
			return screeningList.get(screeningList.size() - 1);
		else
			
		throw new ScreenedBeforeException();
	}

	public void updateScreening(Screening data) {
		screeningRepository.save(data);

	}

	public void addScreeningAnswer(int questionId, int screenId, String answer) {
		/*ScreeningAnswer answer = new ScreeningAnswer();
		answer.setAnswer(messageText);
		answer.setScreeningId(screenId);
		answer.setQuestionId(questionId);
		screeningAnswerRepository.save(answer);*/
		Connection connection = null;
		try {
			connection = datasource.getConnection();
			// select next question id from database based on the current id
			connection.createStatement()
					.execute("insert into screening_answer(screening_id,screening_q_id,answer) values("+screenId+","+questionId+",'"+answer+"')");
		}catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
