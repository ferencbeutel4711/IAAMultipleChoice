package de.nordakademie.multiplechoice.action;

import de.nordakademie.multiplechoice.exception.GenericErrorException;
import de.nordakademie.multiplechoice.exception.InsufficientPermissionsException;
import de.nordakademie.multiplechoice.exception.NotLoggedInException;
import de.nordakademie.multiplechoice.model.QuestionType;
import de.nordakademie.multiplechoice.model.User;
import de.nordakademie.multiplechoice.model.UserType;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ferenc on 19.10.2016.
 */
public class QuestionFormAction extends BaseAction {

    @Getter
    private List<String> questionTypes;

    public String openForm() throws NotLoggedInException, InsufficientPermissionsException, GenericErrorException {
        if(getUserType() != UserType.LECTURER) {
            throw new InsufficientPermissionsException();
        }

        questionTypes = new ArrayList<>();
        for (QuestionType questionType: QuestionType.values()) {
            questionTypes.add(questionType.toString());
        }
        return SUCCESS;
        }

    }


