package models;

import com.google.code.morphia.annotations.Entity;
import com.google.code.morphia.annotations.Indexed;
import play.data.validation.Email;
import play.data.validation.Password;
import play.data.validation.Phone;
import play.data.validation.Required;
import play.modules.morphia.Model;


@play.modules.morphia.Model.NoAutoTimestamp
@Entity(noClassnameStored = true)
public class Contact extends Model {

    @Required
    public String agencyName;

    public String agencyId;

    @Required
    public String name;

    @Required
    @Email
    @Indexed(unique = true, dropDups = true)
    public String email;

    @Required
    @Phone
    @Indexed
    public String phone;

    @Required
    @Password
    public String password;

    @Required
    @Password
    public String confirmPassword;

    public String fax;

    public String verificationCode;

    public RoleType role;

    public static enum RoleType {
        AGENCY, CASE_WORKER
    }

    //TODO this the list of tasks..no metadata
    //public List<Map<String, String>> tasks = new ArrayList<Map<String, String>>();

    public Contact(String name, String email, String phone) {
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "agencyName='" + agencyName + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", fax='" + fax + '\'' +
                ", verificationCode='" + verificationCode + '\'' +
                ", role=" + role +
                '}';
    }
}

