package Tasknest.models;
import java.lang.*;
import java.util.Arrays;
import java.util.regex.*;
public class CV {

    private int id;
    private String bio;
    private String description;
    private String[] language;
    private String location;
    private String certification;
    private String contact;
    private int user_id;


    public CV(int id, String bio, String description, String[] language, String location, String certification, String contact, int user_id) {
        this.id = id;
        setBio(bio);
        setDescription(description);
        setLanguage(language);
        setLocation(location);
        setCertification(certification);
        setContact(contact);
        setUser_id(user_id);
    }

    public CV(String bio, String description, String[] language, String location, String certification, String contact, int user_id) {
        setBio(bio);
        setDescription(description);
        setLanguage(language);
        setLocation(location);
        setCertification(certification);
        setContact(contact);
        setUser_id(user_id);
    }

    private boolean isValidBio(String bio) {
        return bio.matches("[a-zA-Z\\-.,'\\s]+");
    }
    private boolean isValidDescription(String description) {
        int maxDescriptionLength = 1000;
        return description != null && description.length() <= maxDescriptionLength;
    }
    private boolean isValidLanguage(String[] language) {
        return language.length > 0;
    }
    private boolean isValidLocation(String location) {
        return location != null && location.matches("[a-zA-Z\\s]+");
    }
    private boolean isValidCertification(String certification) {
        // Define the regular expression pattern for certification
        String certificationPattern = "^[a-zA-Z\\s\\-\\.,]*$";

        // Compile the regular expression pattern
        Pattern pattern = Pattern.compile(certificationPattern);

        // Create a Matcher object to perform matching
        Matcher matcher = pattern.matcher(certification);

        // Return true if the certification matches the pattern, false otherwise
        return matcher.matches();
    }
    private boolean isValidContact(String contact) {
            String facebookPattern = "^https?://(www\\.)?facebook\\.com/[a-zA-Z0-9_.-]+/?(\\?locale=[a-zA-Z_]+)?$";
            Pattern pattern = Pattern.compile(facebookPattern);
            Matcher matcher = pattern.matcher(contact);
            return matcher.matches();
        }
    private boolean isValidUserId(int user_id) {
        try {
            Integer.parseInt(String.valueOf(user_id));
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    public String[] getLanguage() {
        return language;
    }

    public void setLanguage(String[] language) {
        if (isValidLanguage(language)) {
            this.language = language;
        } else {
            throw new IllegalArgumentException("Invalid language: the list of languages is empty ");
        }
    }

    public CV(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        if (isValidBio(bio)) {
            this.bio = bio;
        } else {
            throw new IllegalArgumentException("Your bio must only contain letters, hyphens (-), periods (.), comma (,) and spaces : " + bio);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        if (isValidDescription(description)) {
            this.description = description;
        } else {
            throw new IllegalArgumentException("Your Description cannot contain more than 1000 characters : " + description);
        }
    }



    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        if (isValidLocation(location)) {
            this.location = location;
        } else {
            throw new IllegalArgumentException("Your Location cannot be null and must only contain letters and spaces : " + location);
        }
    }

    public String getCertification() {
        return certification;
    }

    public void setCertification(String certification) {
        if (isValidCertification(certification)) {
            this.certification = certification;
        } else {
            throw new IllegalArgumentException("Your Certification must only contain letters , hyphens (-), periods (.), comma (,) and spaces : " + certification);
        }
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        if (isValidContact(contact)) {
            this.contact = contact;
        } else {
            throw new IllegalArgumentException("Your Contact information must be in the format of a Facebook profile URL : " + contact);
        }
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        if (isValidUserId(user_id)) {
            this.user_id = user_id;
        } else {
            throw new IllegalArgumentException("User ID must be a number : " + user_id);
        }
    }

    @Override
    public String toString() {
        return "CV{" +
                "id=" + id +
                ", bio='" + bio + '\'' +
                ", description='" + description + '\'' +
                ", language=" + Arrays.toString(language) +
                ", location='" + location + '\'' +
                ", certification='" + certification + '\'' +
                ", contact='" + contact + '\'' +
                ", user_id=" + user_id +
                '}'+
                "\n";
    }
}
