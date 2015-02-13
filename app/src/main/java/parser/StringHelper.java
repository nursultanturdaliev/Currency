package parser;

/**
 * Created by nursultan on 12-Feb 15.
 */
public class StringHelper {

    public static String mergeDoubleStringArray(String[][] stringsOfArray) {
        StringBuilder stringBuilder = new StringBuilder();
        String cellPrefix;
        for (String[] strings : stringsOfArray) {
            cellPrefix = "";
            for (String string : strings) {
                stringBuilder.append(cellPrefix);
                stringBuilder.append(string);
                cellPrefix = "$";
            }
            stringBuilder.append("#");
        }
        stringBuilder.deleteCharAt(stringBuilder.length() - 1);

        return stringBuilder.toString();
    }

    public static String[][] unMergeString(String string) {
        if (string.isEmpty() || string.equals(null)) {
            return new String[][]{};
        }
        String[] rows = string.split("#");
        String[][] result = new String[rows.length][4];
        for (int i = 0; i < rows.length; i++) {
            String[] cells = rows[i].split("\\$");
            for (int j = 0; j < 4; j++) {
                result[i][j] = cells[j];
            }
        }
        return result;
    }
}
