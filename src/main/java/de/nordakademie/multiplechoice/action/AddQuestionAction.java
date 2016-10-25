package de.nordakademie.multiplechoice.action;

import de.nordakademie.multiplechoice.model.*;
import de.nordakademie.multiplechoice.service.*;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Ferenc on 19.10.2016.
 */
public class AddQuestionAction extends BaseAction {
    @Setter
    private List<String> answerTexts;
    @Setter
    private List<String> answerValidity;
    @Setter
    private String questionTypeString;
    @Autowired
    QuestionService questionService;
    @Autowired
    AnswerService answerService;
    @Getter
    @Setter
    private Question question;
    @Getter
    private List<String> questionTypes;


    public String addQuestion(){
        Long questionId = question.getQuestionId();
        Set<Answer> answers = new HashSet<>();
        for (int i = 0; i < answerTexts.size(); i++) {
            Answer answer= new Answer();
            answer.setPosition(i);
            answer.setText(answerTexts.get(i));
            answer.setCorrect(true);
            answers.add(answer);
            //answerService.saveAnswer(answer);
        }
        System.out.println(answerTexts);
        System.out.println(answerValidity);
        question.setType(QuestionType.valueOf(questionTypeString));
        question.setAnswers(answers);
        //questionService.saveQuestion(question);
        return SUCCESS;
    }

    public String prepare(){
        questionTypes = new ArrayList<String>();
        for (QuestionType questionType: QuestionType.values()) {
            questionTypes.add(questionType.name());
        }
        return SUCCESS;
    }


}