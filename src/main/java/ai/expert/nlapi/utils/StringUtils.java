package ai.expert.nlapi.utils;

public class StringUtils {

    /**
     * The empty String
     */
    private static final String EMPTY = "";

    /**
     * should not be used
     */
    public StringUtils() {
    }

    /**
     * Checks if a String is empty ("") or null.
     *
     * @param s the String to check, may be null
     * @return true if the String is empty or null
     */
    public static boolean isEmpty(final String s) {
        return s == null || s.length() == 0;
    }

    /**
     * Checks if a String is empty (""), null or whitespace only.
     *
     * @param s the String to check, may be null
     * @return true if the String is null, empty or whitespace only
     */
    public static boolean isBlank(final String s) {
        if(s == null || s.length() == 0) {
            return true;
        }
        for(int i = 0; i < s.length(); i++) {
            if(!Character.isWhitespace(s.charAt(i))) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if a String is not empty (""), not null and not whitespace only.
     *
     * @param s the String to check, may be null
     * @return true if the CharSequence is not empty and not null and not whitespace only
     */
    public static boolean isNotBlank(final String s) {
        return !isBlank(s);
    }

    /**
     * Trim String, if null returns null
     *
     * @param str the String to be trimmed
     * @return the trimmed string
     */
    public static String trim(final String str) {
        return str == null ? null : str.trim();
    }

    /**
     * Trim String, if null or empty returns null
     *
     * @param str the String to be trimmed
     * @return the trimmed String
     */
    public static String trimToNull(final String str) {
        String ts = trim(str);
        return isEmpty(ts) ? null : ts;
    }

    /**
     * Trim String, if null or empty returns empty
     *
     * @param str the String to be trimmed
     * @return the trimmed String
     */
    public static String trimToEmpty(final String str) {
        return str == null ? EMPTY : str.trim();
    }


    /**
     * Compares two Strings
     *
     * @param str1 the first String
     * @param str2 the second String
     * @return {@code true} if the Strings are equal, case-sensitive
     */
    public static boolean equals(final String str1, final String str2) {
        if(str1 == null || str2 == null) {
            return false;
        }
        if(str1.length() != str2.length()) {
            return false;
        }
        return str1.equals(str2);
    }
}
