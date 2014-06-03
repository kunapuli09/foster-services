package controllers;

import models.*;
import notifiers.Mails;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import play.data.validation.Valid;
import play.modules.paginate.ValuePaginator;
import play.mvc.Before;
import play.mvc.Controller;

import java.text.DateFormat;
import java.util.*;
import java.util.regex.Pattern;

public class Application extends Controller {
    @Before(unless = {"index", "home", "forgotPassword", "passwordReset", "sendForgotPasswordEmail", "register", "save", "login", "displayLogin", "pricing"})
    static void checkAuthentification() {
        if(session.get("user") == null) displayLogin();
    }

    public static void index() {
        session.put("count",Contact.count());
        render();
    }

    public static void home() {
        render();
    }
    public static void pricing() {
        render();
    }

    public static void forgotPassword() {
        render();
    }

    public static void passwordReset() {
        render();
    }
    public static void displayLogin() {
        render();
    }
    public static void addEmployee(String agencyId, String email) {
        if(null != email){
            Contact contact = Contact.find("byEmail", email).first();
            render(contact);
        }
        render(agencyId);
    }
    public static void addFosterHome(String id) {
        if(null != id){
            FosterHome home = FosterHome.findById(new ObjectId(id));
            render(home);
        }
        render();
    }
    public static void addParent(String fosterHomeId, String name, String email, String phone) {
        session.put("fosterHomeId", fosterHomeId);
        if(StringUtils.isNotEmpty(fosterHomeId)){
            FosterHome home = FosterHome.findById(new ObjectId(fosterHomeId));
            Set<FosterParent> parents = home.fosterParents;
            for(FosterParent fosterParent: parents) {
                if(StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(phone)){
                  if(name.equalsIgnoreCase(fosterParent.name) && email.equals(fosterParent.email) && phone.equals(fosterParent.phone)){
                    render(fosterParent);
                  }
                }
            }
        }
        render();
    }

    public static void addChildToHome(String childId) {
        String agencyId = session.get("agencyId");
        List<FosterHome> homes = FosterHome.q().filter("agencyId", agencyId).asList();
        List<Contact> caseWorkers = Contact.q().filter("agencyId", agencyId).asList();
        Child child = Child.findById(new ObjectId(childId));
        render(homes, caseWorkers, child);
    }
    public static void register(String plan) {
        session.put("plan", plan);
        Contact contact = connected();
        if (contact != null) {
            render(contact);
        }
        render();
    }

    public static void children(String objectId) {
        if(null != objectId){
          Child child = Child.findById(new ObjectId(objectId));
          render(child);
        }
        render();
    }

    /**
     * Search a Foster Home by agencyName
     */
    public static void searchFosterHome(String agencyName) {
        Contact loggedInUser = connected();
        if (loggedInUser != null) {
            List<FosterHome> fosterHomes = FosterHome.find("byAgencyId", session.get("agencyId")).fetchAll();
            ValuePaginator<FosterHome> paginator = new ValuePaginator(fosterHomes);
            paginator.setPageSize(10);
            render(paginator);

        } else {
            index();
        }
    }

    /**
     * Search a user by phone
     */
    public static void searchParents(String homeId) {
        Contact loggedInUser = connected();
        if (loggedInUser != null) {
            FosterHome home = FosterHome.findById(new ObjectId(homeId));
            if(home.agencyId.equals(loggedInUser.agencyId)){
                ValuePaginator<FosterHome> paginator = new ValuePaginator(home.fosterParents);
                paginator.setPageSize(10);
                render(paginator, home);
            }


        } else {
            index();
        }
    }
    /**
     * Search a user by phone
     * @param name
     */
    public static void searchContacts(@Valid String name) {
        List<Contact> contacts;
        Contact loggedInUser = connected();
        if (loggedInUser != null) {
            if (StringUtils.isNotEmpty(name)) {
                List<String> tokens = new ArrayList<String>();
                tokens.add(name);
                String patternString = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
                Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
                contacts = Contact.q().filter("agencyId", session.get("agencyId")).filter("name", pattern).fetchAll();

            } else {
                contacts = Contact.q().filter("name", loggedInUser.name).filter("agencyId", session.get("agencyId")).asList();
            }
            ValuePaginator<Contact> paginator = new ValuePaginator(contacts);
            paginator.setPageSize(10);
            render(paginator);

        } else {
            index();
        }
    }

    /**
     * Authenticate user email and password
     * @param email
     * @param password
     */
    public static void login(@Valid String email, @Valid String password) {
        if (null == email || null == password) {
            displayLogin();
        }
        Contact user = Contact.find("byEmailAndPassword", email, password).first();
        if (user != null) {
            session.put("user", user.email);
            session.put("agencyName", user.agencyName);
            session.put("agencyId", user.agencyId);
            session.put("role", user.role);
            flash.success("Welcome, " + user.name);
            searchContacts(null);
        }
        flash.put("email", email);
        flash.error("Login failed");
        displayLogin();
    }

    /**
     * Logout user from the session
     */
    public static void logout() {
        session.clear();
        index();
    }

    /**
     * Register or Save or Update a Contact
     * @param contact
     */
    public static void save(@Valid Contact contact) {
        System.out.println(contact.toString());
        if (validation.hasErrors()) {
            if (request.isAjax()) error("Invalid value");
            render("@register", contact);
        }
        Contact user = Contact.find("byEmail", contact.email).first();
        //save new user
        if (user == null) {
            contact.role = Contact.RoleType.AGENCY;
            contact.agencyId = shortUUID();
            contact.save();
            Mails.welcome(contact);
            //flash.success(contact.name + ",  An email has been sent. Please check your email.");
            Pay.checkout(null, session.get("user"), contact.agencyId, session.get("plan"));
        } else {
            //existing user
            if (connected() == null) {
                System.out.println("A user with this email address already exists");
                flash.error("A user with email already exists");
                render("@register", contact);

            } else {
                //update
                contact.save();
                flash.success(contact.name + ",  Updated your profile");
                index();
            }
        }

    }

    /**
     * Send an email with password reset instructions
     * @param email
     */
    public static void sendForgotPasswordEmail(String email) {
        Contact user = Contact.find("byEmail", email).first();
        if (user != null) {
            user.verificationCode = shortUUID();
            Mails.forgotPassword(user);
            user.save();
            flash.success(user.name + ",  An email with password reset instructions is sent to your email address " + user.email + " Please check your email.");
        }
        displayLogin();
    }

    /**
     * Update contact with new password
     *
     * @param password
     * @param confirmPassword
     */
    public static void passwordResetFinish(String verificationCode, @Valid String password, @Valid String confirmPassword) {
        System.out.println("code=" + verificationCode + " password=" + password + " cnfPwd=" + confirmPassword);
        Contact contact = Contact.find("byVerificationCode", verificationCode).first();
        contact.password = password;
        contact.confirmPassword = confirmPassword;
        contact.save();
        flash.success(contact.name + ",  Your password has been reset. Please login with your new password.");
        displayLogin();
    }

    /**
     * Verify if the user is logged in or connected
     * @return
     */
    static Contact connected() {
        if (renderArgs.get("user") != null) {
            return renderArgs.get("user", Contact.class);
        }
        String email = session.get("user");
        if (email != null) {
            return Contact.find("byEmail", email).first();
        }
        return null;
    }

    /**
     * Generate a shortUUID
     * @return
     */
    public static String shortUUID() {
        UUID uuid = UUID.randomUUID();
        return Base64.encodeBase64URLSafeString(Application.asByteArray(uuid));
    }

    /**
     * Convert UUID to a byte array
     * @param uuid
     * @return
     */
    private static byte[] asByteArray(UUID uuid) {
        long msb = uuid.getMostSignificantBits();
        long lsb = uuid.getLeastSignificantBits();
        byte[] buffer = new byte[16];

        for (int i = 0; i < 8; i++) {
            buffer[i] = (byte) (msb >>> 8 * (7 - i));
        }
        for (int i = 8; i < 16; i++) {
            buffer[i] = (byte) (lsb >>> 8 * (7 - i));
        }
        return buffer;
    }

    public static void saveEmployee(@Valid Contact contact){
        if (validation.hasErrors()) {
            if (request.isAjax()) error("Invalid value");
            render("@addEmployee", contact);
        }
        Contact user = Contact.find("byEmail", session.get("user")).first();
        Contact employee = Contact.find("byEmail", contact.email).first();
        //save new user
        if (user != null) {
            if(employee == null) {
                //set the primary user's agency name for all employees
                contact.role = Contact.RoleType.CASE_WORKER;
                System.out.println(contact.toString());
                contact.save();
                Mails.welcome(contact);
                flash.success(contact.name + ",  An email has been sent. Please check your email and sign in");
                index();
            }else{
                //update
                contact.save();
                flash.success(contact.name + ",  Updated your profile");
                index();
            }

        }
    }

    public static void saveChild(@Valid Child child){
        System.out.println(child.toString());
        if (validation.hasErrors()) {
            if (request.isAjax()) error("Invalid value");
            render("@children", child);
        }
        System.out.println(child.toString());
        Contact caseWorker = Contact.find("byEmail", session.get("user")).first();
        child.caseWorkerId = caseWorker.getId();
        child.save();
        render("@searchChildren", child);
    }

    public static void saveFosterHome(@Valid FosterHome home){
        System.out.println(home.toString());
        if (validation.hasErrors()) {
            if (request.isAjax()) error("Invalid value");
            render("@addFosterHome", home);
        }
        System.out.println(home.toString());
        home.save();
        ValuePaginator<FosterHome> paginator = new ValuePaginator<FosterHome>(new ArrayList(FosterHome.filter("agencyId",session.get("agencyId")).fetchAll()));
        render("@searchFosterHome", paginator);
    }

    public static void saveParent(@Valid FosterParent fosterParent){
        System.out.println(fosterParent.toString());
        if (validation.hasErrors()) {
            if (request.isAjax()) error("Invalid value");
            render("@addParent", fosterParent);
        }
        FosterHome fosterHome = FosterHome.findById(new ObjectId(session.get("fosterHomeId")));
        Iterator<FosterParent> parentIterator = fosterHome.fosterParents.iterator();
        while(parentIterator.hasNext()){
            FosterParent fosterParent1 = parentIterator.next();
            if(fosterParent.name.equalsIgnoreCase(fosterParent1.name) && fosterParent.email.equals(fosterParent1.email) && fosterParent.phone.equals(fosterParent1.phone)){
                parentIterator.remove();
            }
        }
        fosterHome.fosterParents.add(fosterParent);
        fosterHome.save();
        ValuePaginator<FosterHome> paginator = new ValuePaginator<FosterHome>(new ArrayList(FosterHome.filter("agencyId",session.get("agencyId")).fetchAll()));
        render("@searchFosterHome", paginator);
    }

    public static void placeChild(String childId, String fosterHomeId, String caseWorkerId, String childRate, String adminRate, String placementDate){
        if(null != childId && null != fosterHomeId && null != caseWorkerId){
            Child child = Child.findById(new ObjectId(childId));
            child.caseWorkerId = new ObjectId(caseWorkerId);
            child.fosterHomeId = new ObjectId(fosterHomeId);
            ChildCareRate childCareRate = new ChildCareRate();
            childCareRate.adminRate = Float.valueOf(adminRate);
            childCareRate.childRate = Float.valueOf(childRate);
            child.childCareRate = childCareRate;
            child.placementDate = placementDate;
            child.save();
            FosterHome fosterHome = FosterHome.findById(new ObjectId(fosterHomeId));
            if(null != fosterHome){
                //TODO fix duplicate child ids by moving the collections to Set
                fosterHome.fosterChildren.add(new ObjectId(childId));
            }
            fosterHome.save();
            ValuePaginator<FosterHome> paginator = new ValuePaginator<FosterHome>(new ArrayList(FosterHome.filter("agencyId",session.get("agencyId")).fetchAll()));
            render("@searchFosterHome", paginator);
        }
        addChildToHome(childId);

    }
    public static void searchChildren(String lastName){
        List<String> tokens = new ArrayList<String>();
        tokens.add(lastName);
        String patternString = "\\b(" + StringUtils.join(tokens, "|") + ")\\b";
        Pattern pattern = Pattern.compile(patternString, Pattern.CASE_INSENSITIVE);
        List<Child> children = Child.q().filter("agencyId", session.get("agencyId")).filter("lastName", pattern).fetchAll();
        ValuePaginator<Child> paginator = new ValuePaginator<Child>(children);
        paginator.setPageSize(10);
        render("@searchChildren", paginator);
    }


}