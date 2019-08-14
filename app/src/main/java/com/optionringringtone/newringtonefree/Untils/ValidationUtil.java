package com.optionringringtone.newringtonefree.Untils;

        import android.text.TextUtils;
        import android.util.Patterns;

public class ValidationUtil {
    public static boolean isValidEmail(CharSequence target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }

    public static boolean isEmptyValue(String value){
        if(value.equalsIgnoreCase("") || value == null)
            return true;
        return false;
    }
}
