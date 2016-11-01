package de.nordakademie.multiplechoice.action;

import de.nordakademie.multiplechoice.exception.InsufficientPermissionsException;
import de.nordakademie.multiplechoice.exception.NotLoggedInException;
import de.nordakademie.multiplechoice.model.*;
import de.nordakademie.multiplechoice.service.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * Created by Ferenc on 19.10.2016.
 */
public class AddQuestionAction extends BaseAction {

    @Autowired
    private SeminarService seminarService;

    @Setter
    private List<String> singleChoiceAnswers;

    @Setter
    private List<Integer> singleChoiceAnswerValues;

    @Setter
    private List<String> multipleChoiceAnswers;

    @Setter
    private List<Integer> multipleChoiceAnswerValues;

    @Getter
    @Setter
    private Question question;

    @Getter
    @Setter
    private long seminarId;

    public String addQuestion() throws NotLoggedInException, InsufficientPermissionsException {
        if(getUserType() != UserType.LECTURER) {
            throw new InsufficientPermissionsException();
        }
        Seminar seminar = seminarService.byId(seminarId);
        Test test = seminar.getTest();
        question.setAnswers(new ArrayList<>());
        question.setPosition(test.getQuestions().size());
        List<String> answersToPersist = new ArrayList<>();
        List<Integer> answerValuesToPersist = new ArrayList<>();
        switch (question.getType()) {
            case Single:
                answersToPersist = singleChoiceAnswers;
                answerValuesToPersist = singleChoiceAnswerValues;
                break;
            case Multiple:
                answersToPersist = multipleChoiceAnswers;
                answerValuesToPersist = multipleChoiceAnswerValues;
        }

        int i = 0;
        for(String answerString : answersToPersist) {
            Answer answer = new Answer();
            answer.setText(answerString);
            answer.setPosition(i);
            if(answerValuesToPersist.contains(i)) {
                answer.setCorrect(true);
            } else {
                answer.setCorrect(false);
            }
            question.getAnswers().add(answer);
            i++;
        }

        test.getQuestions().add(question);
        seminarService.createOrUpdate(seminar);
        return SUCCESS;
    }
}
