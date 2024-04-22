package Tasknest.models;

public class Skill {
    private int id;
    private String name;
    private int skill_value;
    private int cv_id;

    public Skill(int id, String name, int skill_value, int cv_id) {
        this.id = id;
        setName(name);
        setSkill_value(skill_value);
        setCv_id(cv_id);
    }

    public Skill(String name, int skill_value, int cv_id) {
        setName(name); // Validate and set name
        setSkill_value(skill_value); // Validate and set skill_value
        setCv_id(cv_id); // Validate CV ID
    }

    public Skill(int id) {
        this.id = id;
    }
    private boolean isValidSkillValue(int value) {
        return value >= 0 && value <= 100;
    }
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z0-9]+") && name.length() <= 10;
    }
    private boolean isValidCV_id(int cv_id) {
        // Validate that the user_id is an integer
        try {
            Integer.parseInt(String.valueOf(cv_id));
            return true; // Parsing successful, user_id is an integer
        } catch (NumberFormatException e) {
            return false; // Parsing failed, user_id is not an integer
        }
    }
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (isValidName(name)) {
            this.name = name;
        } else {
            throw new IllegalArgumentException("Your Skill name must only contain letters, numbers and spaces : " + name);
        }
    }

    public int getSkill_value() {
        return skill_value;
    }

    public void setSkill_value(int skill_value) {
        if (isValidSkillValue(skill_value)) {
            this.skill_value = skill_value;
        } else {
            throw new IllegalArgumentException("Skill value must be between 0 and 100 : " + skill_value);
        }
    }

    public int getCv_id() {
        return cv_id;
    }

    public void setCv_id(int cv_id) {
        if (isValidCV_id(cv_id)) {
            this.cv_id = cv_id;
        } else {
            throw new IllegalArgumentException("CV ID must be a number : " + cv_id);
        }
    }

    @Override
    public String toString() {
        return "Skill{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", skill_value='" + skill_value + '\'' +
                ", cv_id=" + cv_id +
                '}'+
                "\n";
    }
}
