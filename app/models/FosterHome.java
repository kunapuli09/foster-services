package models;

import com.google.code.morphia.annotations.Embedded;
import com.google.code.morphia.annotations.Entity;
import org.bson.types.ObjectId;
import play.data.validation.Required;
import play.modules.morphia.Model;

import java.util.*;

@play.modules.morphia.Model.NoAutoTimestamp
@Entity(noClassnameStored = true)
public class FosterHome extends Model {

    @Required
    public String name;
    @Required
    public String county;
    //userId of person adding this home is agencyId
    public String agencyId;
    public String petVaccinationDate;
    public String homeInspectionDate;
    public String zoningHealthFireEveryTwoYears;
    public String zoningHealthFireDueDate;
    public String issuanceDate;


    @Embedded
    public Set<FosterParent> fosterParents = new HashSet<FosterParent>();

    public Set<ObjectId> fosterChildren = new HashSet<ObjectId>();

    public Set<ObjectId> biologicalChildren = new HashSet<ObjectId>();


    @Override
    public String toString() {
        return "FosterHome{" +
                "name='" + name + '\'' +
                ", county='" + county + '\'' +
                ", agencyId=" + agencyId +
                ", petVaccinationDate=" + petVaccinationDate +
                ", homeInspectionDate=" + homeInspectionDate +
                ", zoningHealthFireEveryTwoYears=" + zoningHealthFireEveryTwoYears +
                ", zoningHealthFireDueDate=" + zoningHealthFireDueDate +
                ", issuanceDate=" + issuanceDate +
                '}';
    }
}
