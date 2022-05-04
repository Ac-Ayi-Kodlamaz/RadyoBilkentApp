package model;

import com.example.radyobilkentandroid.R;

/*
    This is enum is written to handle genders more comfortably
    DNWTD stands for "Do Not Wish To Disclose"
 */
public enum Gender {
    // TODO find a way to link these with strings.xml
    MALE("Male"), FEMALE("Female"), NON_BINARY("Non-binary"), DNWTD("Do not wish to disclose");

    public final String gender;

    private Gender(String gender) {
        this.gender = gender;
    }

    public static Gender valueOfLabel(String gender) {
        for (Gender g : values()) {
            if (g.gender.equals(gender)) {
                return g;
            }
        }
        return null;
    }
}
