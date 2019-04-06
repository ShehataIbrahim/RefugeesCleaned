package com.refugees.portal.controllers;

import java.io.IOException;
import java.util.*;

import com.refugees.portal.db.health.model.AllowedAnswer;
import com.refugees.portal.db.health.model.AnswerTypesEnum;
import com.refugees.portal.db.health.model.InterviewDisplay;
import com.refugees.portal.db.health.model.QuestionDependency;
import com.refugees.portal.services.ConsolidateService;
import com.refugees.portal.services.model.InterviewAnswerBaseData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.github.messenger4j.MessengerPlatform;
import com.github.messenger4j.exceptions.MessengerApiException;
import com.github.messenger4j.exceptions.MessengerIOException;
import com.github.messenger4j.exceptions.MessengerVerificationException;
import com.github.messenger4j.receive.MessengerReceiveClient;
import com.github.messenger4j.receive.handlers.QuickReplyMessageEventHandler;
import com.github.messenger4j.receive.handlers.TextMessageEventHandler;
import com.github.messenger4j.send.MessengerSendClient;
import com.github.messenger4j.send.NotificationType;
import com.github.messenger4j.send.QuickReply;
import com.github.messenger4j.send.Recipient;
import com.github.messenger4j.send.SenderAction;
import com.refugees.portal.db.model.FacebookUser;
import com.refugees.portal.db.model.RefugeeUser;
import com.refugees.portal.db.model.Screening;
import com.refugees.portal.db.service.RefugeeService;
import com.refugees.portal.db.service.ScreenedBeforeException;
import com.refugees.portal.demo.Translator;

@RestController
@RequestMapping("/callback")
public class ChatbotCallBack {
    @Autowired
    List<InterviewDisplay> questions;
    @Autowired
    ConsolidateService service;
    @Value("${fb.pageAccessToken}")
    private String pageAccessToken_;
    private final MessengerReceiveClient receiveClient;
    private static MessengerSendClient sendClient;
    @Autowired
    ConsolidateService consolidateService;
    final static HashMap<String, QuestionDependency> questionDependencies;

    static {
        questionDependencies = new HashMap<>();
        QuestionDependency d = null;
        d = new QuestionDependency();
        d.setInterviewId("2_1_1");
        AllowedAnswer answer = new AllowedAnswer();
        answer.setAnswerId(1);
        answer.setAnswer("YES");
        d.setValidAnswer(answer);
        questionDependencies.put("2_1_2", d);
        questionDependencies.put("2_1_3", d);
        d = new QuestionDependency();
        d.setInterviewId("2_1_4");
        d.setValidAnswer(answer);
        questionDependencies.put("2_1_5", d);

        d = new QuestionDependency();
        d.setInterviewId("1_1_2");
        d.setValidAnswer(answer);
        questionDependencies.put("3_1_1", d);
    }

    private static final Logger logger = LoggerFactory.getLogger(ChatbotCallBack.class);

    @Autowired
    RefugeeService refugeeService;
/*
    enum UserEvents {
        ASK_YES_NO, ASK_TEXT
    }*/

    @Autowired
    public ChatbotCallBack(@Value("${fb.random.key}") final String appSecret,
                           @Value("${fb.verifyToken}") final String verifyToken, final MessengerSendClient sendClient) {
        this.receiveClient = MessengerPlatform.newReceiveClientBuilder(appSecret, verifyToken)
                .onTextMessageEvent(newTextMessageEventHandler())
                .onQuickReplyMessageEvent(newQuickReplyMessageEventHandler()).build();
        ChatbotCallBack.sendClient = sendClient;
        // allQuestions = refugeeService.getAllQuestions();
    }

    @GetMapping
    public ResponseEntity<String> verifyWebhook(@RequestParam("hub.mode") final String mode,
                                                @RequestParam("hub.verify_token") final String verifyToken,
                                                @RequestParam("hub.challenge") final String challenge) {
        try {
            return ResponseEntity.ok(this.receiveClient.verifyWebhook(mode, verifyToken, challenge));
        } catch (MessengerVerificationException e) {
            logger.warn("Webhook verification failed:", e);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<Void> handleCallback(@RequestBody final String payload,
                                               @RequestHeader("X-Hub-Signature") final String signature) {
        try {
            this.receiveClient.processCallbackPayload(payload, signature);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (MessengerVerificationException e) {
            logger.warn("Processing of callback payload failed:" + e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
    }

    // auto reply method
    private TextMessageEventHandler newTextMessageEventHandler() {
        return event -> {

            logger.info("Received TextMessageEvent: " + event);
            final String senderId = event.getSender().getId();
            try {
                //final String messageId = event.getMid();
                final String messageText = event.getText();
                final Date timestamp = event.getTimestamp();
                processInputMessage(senderId, messageText, timestamp);

            } catch (Exception e) {
                try {
                    sendReadReceipt(senderId);
                } catch (Exception e1) {
                    logger.warn(e1.getMessage(), e1);
                }
            }
        };
    }

    private void processInputMessage(String senderId, String messageText, Date timestamp) {
        FacebookUser user = refugeeService.findFacebookUser(senderId);
        // first time user scenario
        if (user == null) {
            System.out.println("First time user");
            try {
                // start tracking user's visit
                refugeeService.initiateFacebookUser(senderId);
            } catch (Exception e) {
                sendTextMessage(senderId,
                        "You are very welcomed to our system and we wish if we can proceed now but we have a technical issue :(");
                return;
            }
            // Ask user for his username
            sendTextMessage(senderId, "Welcome to our system, please provide your username to proceed");
        } else {
            // Alread talked before but no username provided
            if (user.getUserName() == null) {
                System.out.println("we talked but we don't know each other scenario this time it should be user name");
                // check if text provided is username
                RefugeeUser refugeeUser = refugeeService.findRefugeeByUserName(messageText);
                // text provided is not username
                if (refugeeUser == null) {
                    System.out.println("invalid username");
                    sendTextMessage(senderId, "Sorry the user name:" + messageText
                            + " doesn't exist in our system, would you please double check and provide the correct one?");
                } else {
                    System.out.println("User info was found");
                    // user provided user name
                    // update info and ask the first question
                    user.setUserName(refugeeUser.getName());
                    user.setUserId(refugeeUser.getId());
                    refugeeService.updateFacebook(user);
                    refugeeUser.setFacebookUserId(senderId);
                    refugeeUser.setFacebookInfo("0");
                    refugeeService.updateRefugeeUser(refugeeUser);
                    //Screening sc = refugeeService.createScreeningData(refugeeUser.getId());
                    InterviewDisplay firstQuestion = questions.get(0);
                    String message = Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), firstQuestion.getInterviewItem());

                    if (!firstQuestion.isMultiSelection())
                        sendTextMessage(senderId, message);
                    else
                        try {
                            firstQuestion.getAllowedAnswers().stream().forEach(q -> {
                                q.setTranslatedAnswer(Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), q.getAnswer()));
                            });
                            sendQuickReply(senderId, message, firstQuestion.getAllowedAnswers());
                        } catch (Exception e) {
                            handleSendException(e);
                        }
                }
            } else {
                // user already communicated and was asked ... this is an answer
                System.out.println("Welcome back known user");
                user.setScanningDate(timestamp);
                RefugeeUser refugeeUser = refugeeService.findRefugeeByUserName(user.getUserName());
                System.out.println(refugeeUser);
              /*  Screening screeningData ;
                try {
                    screeningData = refugeeService.getUserScreeningData(refugeeUser.getId());

                } catch (ScreenedBeforeException e1) {
                    sendTextMessage(senderId, Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), "Thanks for passing by, we will communicate you once done processing"));
                    return;
                }*/
                String fbInfo = refugeeUser.getFacebookInfo();
                int currentQuestionId = 0;
                try {
                    currentQuestionId = Integer.parseInt(fbInfo);
                } catch (NumberFormatException e) {
                    if ("INITIAL".equalsIgnoreCase(fbInfo)) {
                        System.out.println("Already done before, but not processed ");
                        sendTextMessage(senderId, Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), "Thanks for passing by, your submittion is still under processing"));
                    } else if ("DONE".equalsIgnoreCase(fbInfo)) {
                        System.out.println("Already done before, and processed ");
                        sendTextMessage(senderId, Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), "Thanks for passing by, your submittion processed please check our past conversations to get the status either visit our web portal"));
                    }
                    return;
                }
                System.out.println("Current under processing question: " + currentQuestionId);
                int nextQuestionId = (currentQuestionId < (questions.size() - 1)) ? (currentQuestionId + 1) : 0; //refugeeService.findNextQuestion(refugeeUser.getFacebookInfo());


                System.out.println("Next Question to go is :" + nextQuestionId);
                try {
                    if(questions.get(currentQuestionId).getType()== AnswerTypesEnum.LIST)
                    {
                       if("YES".equalsIgnoreCase(messageText))
                           messageText="1";
                       else messageText="0";
                    }
                    service.consolidateInterviewAnswer(refugeeUser.getId().toString(), questions.get(currentQuestionId), messageText);
                } catch (IOException e) {
                    sendTextMessage(senderId, Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), "Sorry it seems there is a technical issue in processing your answer, please return to help desk to solve it code 'RES_KEY_0002' "));
                    return;
                }
                // dependencies handling
                if (nextQuestionId != 0 && questionDependencies.containsKey(questions.get(nextQuestionId).objectId())) {
                    QuestionDependency dep = questionDependencies.get(questions.get(nextQuestionId).objectId());
                    try {
                        Map<String, InterviewAnswerBaseData> mp = service.getPatientAnswers(refugeeUser.getId().toString());
                        InterviewAnswerBaseData ans = mp.get(dep.getInterviewId());
                        System.out.println("Trace:\t"+questions.get(nextQuestionId).objectId());
                        System.out.println("Trace: "+dep.getValidAnswer());
                        System.out.println("Input answer:"+ans.getInterview_answer());
                        try {
                            if (ans != null)
                                if (!String.valueOf(dep.getValidAnswer().getAnswerId()).equalsIgnoreCase(ans.getInterview_answer()))
                                {
                                    nextQuestionId++;
                                    if(nextQuestionId==questions.size())
                                        nextQuestionId=0;
                                }
                        } catch (Exception e) {
                            System.out.println("Some Error occured " + e.getMessage());
                            System.out.println("Trace: "+dep.getValidAnswer());
                            System.out.println("Input answer:"+ans.getInterview_answer());
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                //end of dependency handling
                if (nextQuestionId == 0) {
                    System.out.println("just finalizing our chat");
                    //update user submittion status
                    refugeeUser.setFacebookInfo("INITIAL");
                    refugeeService.updateRefugeeUser(refugeeUser);
                    //now to add the user response to be consolidated
                    sendTextMessage(senderId, Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), "Thanks very much for your time we will contact you ASAP"));
                    return;
                }
                System.out.println("Going through questioning process");


                //refugeeService.addScreeningAnswer(currentQuestionId, screeningData.getId(), messageText);
                System.out.println("added pervious question answer");
                refugeeUser.setFacebookInfo("" + nextQuestionId);
                System.out.println("update fb info to reflect the next question id");
                user.setScanningDate(timestamp);
                refugeeService.updateFacebook(user);
                refugeeService.updateRefugeeUser(refugeeUser);
                System.out.println("Reflected updated scanning date");
                InterviewDisplay currentQuestion = questions.get(nextQuestionId);
                String message = Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), currentQuestion.getInterviewItem());
                if (!currentQuestion.isMultiSelection())
                    sendTextMessage(senderId, message);
                else
                    try {
                        currentQuestion.getAllowedAnswers().stream().forEach(q -> {
                            q.setTranslatedAnswer(Translator.translateFromEnglish(refugeeUser.getTranslateLangCode(), q.getAnswer()));
                        });
                        sendQuickReply(senderId, message, currentQuestion.getAllowedAnswers());
                    } catch (Exception e) {
                        handleSendException(e);
                    }
                System.out.println("done processing");
            }
        }

    }

    // private List<ScreeningQuestions> allQuestions;

    /*
     * private void processMessage(String senderId) throws MessengerApiException,
     * MessengerIOException { int currentStep = usersSteps.get(senderId);
     * currentStep++; String questionKey = "Q" + currentStep; if
     * (questions.containsKey(questionKey)) { if
     * (NotYesNoList.contains(currentStep)) sendTextMessage(senderId,
     * questions.getProperty(questionKey)); else sendQuickReply(senderId,
     * questions.getProperty(questionKey)); usersSteps.put(senderId, currentStep); }
     * else sendTextMessage(senderId,
     * "your data now is complete, thanks for your time"); }
     */
    // auto reply for call backs
    private QuickReplyMessageEventHandler newQuickReplyMessageEventHandler() {
        return event -> {
            logger.info("Received QuickReplyMessageEvent:" + event);

            final String senderId = event.getSender().getId();
            final String messageId = event.getMid();
            final String quickReplyPayload = event.getQuickReply().getPayload();

            logger.info(
                    "Received quick reply for message '" + messageId + "' with payload '" + quickReplyPayload + "'");
            try {
                processInputMessage(senderId, quickReplyPayload, event.getTimestamp());
            } catch (Exception e) {
                handleSendException(e);
            }
        };
    }

    public static void sendTextMessage(String recipientId, String text) {
        try {
            final Recipient recipient = Recipient.newBuilder().recipientId(recipientId).build();
            final NotificationType notificationType = NotificationType.REGULAR;
            final String metadata = "DEVELOPER_DEFINED_METADATA";
            if (sendClient == null) {
                System.out.println("SendClient is null");
                return;
            }
            sendClient.sendTextMessage(recipient, notificationType, text, metadata);
        } catch (Exception e) {
            handleSendException(e);
        }
    }

    private void sendReadReceipt(String recipientId) throws MessengerApiException, MessengerIOException {
        if (sendClient == null) {
            System.out.println("SendClient is null");
            return;
        }
        sendClient.sendSenderAction(recipientId, SenderAction.MARK_SEEN);
    }

    private static void handleSendException(Exception e) {
        e.printStackTrace();
        logger.warn("Message could not be sent. An unexpected error occurred." + e.getMessage());
    }

    private void sendQuickReply(String recipientId, String message, List<AllowedAnswer> answers) throws MessengerApiException, MessengerIOException {
        QuickReply.ListBuilder builder = QuickReply.newListBuilder();
        for (AllowedAnswer ans : answers)
            builder = builder.addTextQuickReply(ans.getTranslatedAnswer(), ans.getAnswer()).toList();
        final List<QuickReply> quickReplies = builder.build();

        ChatbotCallBack.sendClient.sendTextMessage(recipientId, message, quickReplies);
    }



	/*@Bean
	@Singleton
	public Properties getQuestions(@Value("${questions.properties.file}") final String filePath) {
		questions = new Properties();
		try {
			questions.load(new FileInputStream(filePath));
		} catch (IOException e) {
			logger.warn(e.getMessage(), e);
		}
		return questions;
	}*/

}
