package net.idrok.model;

public enum Section {
    // bosh oyna
    HOME,


    // Registratsiya
    REGISTER_FIRST_NAME,
    REGISTER_LAST_NAME,
    REGISTER_PHONE_NUMBER,


    SELECT_IMAGE_8_MARCH,
    SELECT_IMAGE_HAYIT,
    SELECT_IMAGE_YANGI_YIL;

















    public static Section getByOrdinal(int ordinal){
        for(Section s: values()){
            if(s.ordinal() == ordinal) return s;
        }
        return null;
    }
}
