package models;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import org.bson.types.ObjectId;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.modules.morphia.Model;

import java.util.Date;

@play.modules.morphia.Model.NoAutoTimestamp
@Entity(noClassnameStored = true)
public class Child extends Model {
    //userId of Contact who added this child into the system
    public ObjectId caseWorkerId;

    //FosterHome that this child belongs to
    public ObjectId fosterHomeId;

    public String agencyId;

    @Embedded
    public ChildCareRate childCareRate;

    @Required
    public String firstName;

    @Required
    public String lastName;

    @Required
    public String dateOfBirth;

    @Required
    public Gender gender;

    @Required
    @Indexed(unique = true, dropDups = true)
    public String medicaidNumber;

    @Phone
    public String fosterParentPhone;

    public String nbcDueDate;

    @Required
    public String placementDate;

    @Required
    public String county;

    public String dischargeDate;

    public IntakePacket intakePacket;

    public String medicalExamDate;

    public String annualMedExamDueDate;

    public String dentalExamDate;

    public String dentalExamDueDate;

    public String eyeExamDate;

    public String eyeExamDueDate;

    public String treatmentPlanDate;

    public String healthPassport;

    public String commentsDaySeven;

    public String commentsDayFourteen;


    public enum Gender {
        M,
        F
    };
    public enum IntakePacket {
        PROVIDER,
        CASE_WORKER
    };

    public Child(ObjectId caseWorkerId, ChildCareRate childCareRate, String firstName,
                 String lastName, String dateOfBirth, Gender gender, String medicaidNumber,
                 String fosterParentPhone, String nbcDueDate, String placementDate, String dischargeDate,
                 IntakePacket intakePacket, String medicalExamDate, String annualMedExamDueDate,
                 String dentalExamDate, String dentalExamDueDate, String eyeExamDate, String eyeExamDueDate, String treatmentPlanDate,
                 String healthPassport, String commentsDaySeven, String commentsDayFourteen) {
        this.caseWorkerId = caseWorkerId;
        this.childCareRate = childCareRate;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.medicaidNumber = medicaidNumber;
        this.fosterParentPhone = fosterParentPhone;
        this.nbcDueDate = nbcDueDate;
        this.placementDate = placementDate;
        this.dischargeDate = dischargeDate;
        this.intakePacket = intakePacket;
        this.medicalExamDate = medicalExamDate;
        this.annualMedExamDueDate = annualMedExamDueDate;
        this.dentalExamDate = dentalExamDate;
        this.dentalExamDueDate = dentalExamDueDate;
        this.eyeExamDate = eyeExamDate;
        this.eyeExamDueDate = eyeExamDueDate;
        this.treatmentPlanDate = treatmentPlanDate;
        this.healthPassport = healthPassport;
        this.commentsDaySeven = commentsDaySeven;
        this.commentsDayFourteen = commentsDayFourteen;
    }

    public Contact getCaseWorker() {
        if (null == caseWorkerId) {
            return null;
        } else {
            return Contact.findById(caseWorkerId);
        }
    }

    @Override
    public String toString() {
        return "Child{" +
                "caseWorkerId=" + caseWorkerId +
                ", childCareRate=" + childCareRate +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", medicaidNumber='" + medicaidNumber + '\'' +
                ", fosterParentPhone='" + fosterParentPhone + '\'' +
                ", nbcDueDate=" + nbcDueDate +
                ", placementDate=" + placementDate +
                ", dischargeDate=" + dischargeDate +
                ", intakePacket=" + intakePacket +
                ", medicalExamDate=" + medicalExamDate +
                ", annualMedExamDueDate=" + annualMedExamDueDate +
                ", dentalExamDate=" + dentalExamDate +
                ", dentalExamDueDate=" + dentalExamDueDate +
                ", eyeExamDate=" + eyeExamDate +
                ", eyeExamDueDate=" + eyeExamDueDate +
                ", treatmentPlanDate=" + treatmentPlanDate +
                ", healthPassport='" + healthPassport + '\'' +
                ", commentsDaySeven='" + commentsDaySeven + '\'' +
                ", commentsDayFourteen='" + commentsDayFourteen + '\'' +
                '}';
    }
}
