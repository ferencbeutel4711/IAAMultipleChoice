package de.nordakademie.multiplechoice.action;

import de.nordakademie.multiplechoice.exception.GenericErrorException;
import de.nordakademie.multiplechoice.exception.InsufficientPermissionsException;
import de.nordakademie.multiplechoice.exception.NotLoggedInException;
import de.nordakademie.multiplechoice.model.*;
import de.nordakademie.multiplechoice.service.*;
import de.nordakademie.multiplechoice.util.DateTimeValidationUtils;
import lombok.Getter;
import lombok.Setter;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Ferenc on 19.10.2016.
 */
public class PersistSeminarAction extends BaseAction {
    @Autowired
    private SeminarService seminarService;

    @Autowired
    private LecturerService lecturerService;

    @Getter
    @Setter
    private Seminar seminar;

    @Setter
    private String beginDateString;

    @Setter
    private String endDateString;

    @Setter
    @Getter
    private long seminarId;

    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public String persistSeminar() throws NotLoggedInException, InsufficientPermissionsException, GenericErrorException {
        if(getUserType() != UserType.LECTURER) {
            throw new InsufficientPermissionsException();
        }
        Lecturer lecturer = getLecturerFromSession();

        seminar.setSeminarId(seminarId);
        seminar.setBeginDate(LocalDate.parse(beginDateString, dateFormatter));
        seminar.setEndDate(LocalDate.parse(endDateString, dateFormatter));
        seminar.setLecturer(lecturer);
        seminarService.createOrUpdate(seminar);
        return SUCCESS;
    }

    public void validate() {



        boolean startDateParseable = false;
        if (!DateTimeValidationUtils.isDateParseable(beginDateString, dateFormatter)) {
            addFieldError("startDate", getI18NValue("persistSeminarFieldError.validStart"));
        } else {
            startDateParseable = true;
        }

        boolean endDateParseable = false;
        if (!DateTimeValidationUtils.isDateParseable(endDateString, dateFormatter)) {
            addFieldError("endDateString", getI18NValue("persistSeminarFieldError.validEnd"));
        } else {
            endDateParseable = true;
        }

        if (endDateParseable && startDateParseable) {
            LocalDate startDate = LocalDate.parse(beginDateString, dateFormatter);
            LocalDate endDate = LocalDate.parse(endDateString, dateFormatter);
            LocalDate now = LocalDate.now();
            if (startDate.isBefore(now)) {
                addFieldError("startDate", getI18NValue("persistSeminarFieldError.startInPast"));
            }
            if (endDate.isBefore(startDate)) {
                addFieldError("endDateString", getI18NValue("persistSeminarFieldError.startBeforeEnd"));
            }
        }

        if (seminar.getDescription() == null || seminar.getDescription().length() < 2) {
            addFieldError("description", getI18NValue("persistSeminarFieldError.description"));
        }
        if (seminar.getMaxParticipants() <= 0) {
            addFieldError("maxParticipants", getI18NValue("persistSeminarFieldError.maxParticipants"));
        }
        if (seminar.getName() == null || seminar.getName().length() < 2) {
            addFieldError("name", getI18NValue("persistSeminarFieldError.name"));
        }
    }


}
