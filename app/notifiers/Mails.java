package notifiers;

import models.Contact;
import org.apache.ivy.util.StringUtils;
import play.i18n.Messages;
import play.mvc.Mailer;

/**
 * Created with IntelliJ IDEA.
 * User: kunapuli09
 * Date: 9/11/12
 * Time: 11:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class Mails extends Mailer {

    public static void welcome(Contact contact) {
        String verificationCode = contact.getId().toString();
        setSubject(Messages.get("welcomeEmail.subject", contact.email));
        addRecipient(contact.email);
        setFrom(Messages.get("adminEmail"));
        send(contact, verificationCode);
    }

    public static void forgotPassword(Contact contact) {
        setFrom(Messages.get("adminEmail"));
        setSubject(Messages.get("passwordEmail.subject"));
        addRecipient(contact.email);
        send(contact);
    }
}
