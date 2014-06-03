package models;

import com.google.code.morphia.annotations.Embedded;
import play.data.validation.Required;

import java.util.Date;

/**
 * Foster FosterParent
 */
@Embedded
public class FosterParent {
    @Required
    public String name;
    @Required
    public String email;
    @Required
    public String phone;
    @Required
    public boolean isPrimary = false;
    public String sixMonthDueDate;
    public String medicalEvaluationCompletedDate;
    public String medicalEvaluationExpiryDate;
    public String cprCompletedDate;
    public String cprExpiryDate;
    public String firstAideCompletedDate;
    public String firstAideExpiryDate;
    public String medCertCompletedDate;
    public String medCertExpiryDate;
    public String driversLicenceExpiryDate;
    public String autoInsuranceExpiryDate;
    public String autoRegistrationExpiryDate;
    public String coreTrainingCompletedDate;
    public String fiveYearCoreTrainingDueDate;
    public String semiAnnualEmergencySafetyDate;

    public FosterParent(boolean isPrimary, String sixMonthDueDate, String medicalEvaluationCompletedDate, String medicalEvaluationExpiryDate,
                        String cprCompletedDate, String cprExpiryDate, String firstAideCompletedDate, String firstAideExpiryDate,
                        String medCertCompletedDate, String medCertExpiryDate, String driversLicenceExpiryDate, String autoInsuranceExpiryDate,
                        String autoRegistrationExpiryDate,
                        String coreTrainingCompletedDate, String fiveYearCoreTrainingDueDate, String semiAnnualEmergencySafetyDate) {
        this.isPrimary = isPrimary;
        this.sixMonthDueDate = sixMonthDueDate;
        this.medicalEvaluationCompletedDate = medicalEvaluationCompletedDate;
        this.medicalEvaluationExpiryDate = medicalEvaluationExpiryDate;
        this.cprCompletedDate = cprCompletedDate;
        this.cprExpiryDate = cprExpiryDate;
        this.firstAideCompletedDate = firstAideCompletedDate;
        this.firstAideExpiryDate = firstAideExpiryDate;
        this.medCertCompletedDate = medCertCompletedDate;
        this.medCertExpiryDate = medCertExpiryDate;
        this.driversLicenceExpiryDate = driversLicenceExpiryDate;
        this.autoInsuranceExpiryDate = autoInsuranceExpiryDate;
        this.autoRegistrationExpiryDate = autoRegistrationExpiryDate;
        this.coreTrainingCompletedDate = coreTrainingCompletedDate;
        this.fiveYearCoreTrainingDueDate = fiveYearCoreTrainingDueDate;
        this.semiAnnualEmergencySafetyDate = semiAnnualEmergencySafetyDate;
    }

    @Override
    public String toString() {
        return "FosterParent{" +
                "isPrimary=" + isPrimary +
                ", sixMonthDueDate=" + sixMonthDueDate +
                ", medicalEvaluationCompletedDate=" + medicalEvaluationCompletedDate +
                ", medicalEvaluationExpiryDate=" + medicalEvaluationExpiryDate +
                ", cprCompletedDate=" + cprCompletedDate +
                ", cprExpiryDate=" + cprExpiryDate +
                ", firstAideCompletedDate=" + firstAideCompletedDate +
                ", firstAideExpiryDate=" + firstAideExpiryDate +
                ", medCertCompletedDate=" + medCertCompletedDate +
                ", medCertExpiryDate=" + medCertExpiryDate +
                ", driversLicenceExpiryDate=" + driversLicenceExpiryDate +
                ", autoInsuranceExpiryDate=" + autoInsuranceExpiryDate +
                ", autoRegistrationExpiryDate=" + autoRegistrationExpiryDate +
                ", coreTrainingCompletedDate=" + coreTrainingCompletedDate +
                ", fiveYearCoreTrainingDueDate=" + fiveYearCoreTrainingDueDate +
                ", semiAnnualEmergencySafetyDate=" + semiAnnualEmergencySafetyDate +
                '}';
    }
}
