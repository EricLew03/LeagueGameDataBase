package util;

// removes malicious injections of sql from user inputs, only for strings
public class InputSanitizer {
    private static final String[] SQL_KEYWORDS = {
            "drop", "delete", "insert", "update", "select", "union",
            "minus", "exists", "group", "having",
    };

    // returns sanitized input that does not contain sql keywords that might screw something up
    public static String sanitizeInput(String input) {
        String[] words = input.split("\\s+");
        StringBuilder sanitizedInputBuilder = new StringBuilder();

        for (String word : words) {
            // MAKE IT LOWERCASE JUST IN CASE
            if (!isSQLKeyword(word.toLowerCase())) {
                sanitizedInputBuilder.append(word).append(" "); // re-add the white space from word separating
            }
        }

        // flatten and remove extranous white space artifcating
        String sanitizedInput = sanitizedInputBuilder.toString().trim();

        return sanitizedInput;
    }

    public static boolean checkNumbersOnly(String input) {
        if (!input.matches("\\d+")) {
            return false;
        }
        return true;
    }

    // check if violates malicious injection keyword
    private static boolean isSQLKeyword(String word) {
        for (String keyword : SQL_KEYWORDS) {
            if (word.equals(keyword)) {
                return true;
            }
        }
        return false;
    }

}
