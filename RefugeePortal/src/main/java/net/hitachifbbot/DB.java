package net.hitachifbbot;

import net.hitachifbbot.model.DoctorUser;
import net.hitachifbbot.utils.DBUtils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

public class DB {

    public static NamminUser getNamminUserByMailAddress(String mailAddress) throws SQLException {
        return DBUtils.preparedStatement("SELECT * FROM nammin_user WHERE MAIL_ADDRESS = ?",
                ps -> {
                    ps.setString(1, mailAddress);
                },
                r -> {
                    if (!r.next()) {
                        return null;
                    }
                    return NamminUser.getFromResultSet(r);
                });
    }

    public static ArrayList<NamminAnswer> getNamminAnswer(int namminID) throws SQLException {
        return DBUtils.preparedStatement("select answer.nammin_id,answer.nammin_q_id,answer.answer,question.screening_q_text from nammin_answer answer inner join screening_q question on question.screening_q_id=answer.nammin_q_id WHERE nammin_id = ?",
                ps -> {
                    ps.setInt(1, namminID);
                },
                r -> {
                    ArrayList<NamminAnswer> result = new ArrayList<>();
                    while (r.next()) {
                        result.add(NamminAnswer.getFromResultSet(r));
                    }
                    return result;
                });
    }

    public static ArrayList<NamminAnswer> getUserAnswers(int namminID) throws SQLException {
        return DBUtils.preparedStatement("select sc.nammin_id, ans.screening_q_id nammin_q_id, ans.answer ,question.screening_q_text from (screening sc inner join screening_answer ans on sc.screening_id = ans.screening_id) inner join screening_q question on ans.screening_q_id= question.screening_q_id WHERE nammin_id = ?",
                ps -> {
                    ps.setInt(1, namminID);
                },
                r -> {
                    ArrayList<NamminAnswer> result = new ArrayList<>();
                    while (r.next()) {
                        result.add(NamminAnswer.getFromResultSet(r));
                    }
                    return result;
                });
    }
    public static ArrayList<ScreeningResultSet> getLastScreeningAnswer(int namminID) throws SQLException {
        return DBUtils.preparedStatement("SELECT screening_q_id,answer,category_id,screening_q_text FROM view_last_screening_answer " +
                        "WHERE nammin_id = ?",
                ps -> {
                    ps.setInt(1, namminID);
                },
                r -> {
                    ArrayList<ScreeningResultSet> result = new ArrayList<>();
                    while (r.next()) {
                        result.add(
                                new ScreeningResultSet(
                                        r.getInt("screening_q_id"),
                                        r.getString("answer"),
                                        r.getInt("category_id") == 1,
                                        r.getString("screening_q_text")
                                )
                        );
                    }
                    return result;
                });
    }

    public static DoctorUser getDoctorUserByID(String userID) throws SQLException {
        return DBUtils.preparedStatement("SELECT * FROM doctor_user WHERE DOCTOR_ID = ?",
                ps -> {
                    ps.setString(1, userID);
                },
                r -> {
                    if (!r.next()) {
                        return null;
                    }
                    return DoctorUser.getFromResultSet(r);
                });
    }

    public static class NamminAnswer{
        /*
            nammin_id   INT REFERENCES nammin_user(nammin_id),
            nammin_q_id INT,
            answer      VARCHAR(1024)
         */
        public int namminID;
        public int namminQID;
        public String questionKey;
        public String answer;

        public String getQuestionKey() {
			return questionKey;
		}

		public void setQuestionKey(String questionKey) {
			this.questionKey = questionKey;
		}

		public NamminAnswer(int namminID, int namminQID, String answer) {
            this.namminID = namminID;
            this.namminQID = namminQID;
            this.answer = answer;
        }

        public NamminAnswer(int namminID, int namminQID, String questionKey, String answer) {
			super();
			this.namminID = namminID;
			this.namminQID = namminQID;
			this.questionKey = questionKey;
			this.answer = answer;
		}

		public static NamminAnswer getFromResultSet(ResultSet r) throws SQLException {
            return new NamminAnswer(
                    r.getInt("nammin_id"),
                    r.getInt("nammin_q_id"),
                    r.getString("screening_q_text"),
                    r.getString("answer")
            );
        }
    }

 

    


    public static class ScreeningAnswer{
        /*
    screening_id   INT REFERENCES screening(screening_id),
    screening_q_id INT REFERENCES screening_q(screening_q_id),
    answer         VARCHAR(1024)
        */
        public int screeningID;
        public int screeningQID;
        public String answer;

        public ScreeningAnswer(int screeningID, int screeningQID, String answer) {
            this.screeningID = screeningID;
            this.screeningQID = screeningQID;
            this.answer = answer;
        }

        public static ScreeningAnswer getFromResultSet(ResultSet r) throws SQLException {
            return new ScreeningAnswer(
                    r.getInt("screening_id"),
                    r.getInt("screening_q_id"),
                    r.getString("answer")
            );
        }
    }

    public static class ScreeningResultSet{
        /*
    screening_id   INT REFERENCES screening(screening_id),
    screening_q_id INT REFERENCES screening_q(screening_q_id),
    answer         VARCHAR(1024)
        */
        public int screeningQID;
        public String answer;
        /**
         * 初回検診か？
         */
        public boolean isFirstQuestion;
        public String questionKey;

        public ScreeningResultSet(int screeningQID, String answer, boolean isFirstQuestion,String questionKey) {
            this.screeningQID = screeningQID;
            this.answer = answer;
            this.isFirstQuestion = isFirstQuestion;
            this.questionKey=questionKey;
        }
    }
}
