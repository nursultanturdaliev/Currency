package helpers;

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
        String[][] result;
        try {
            if (string.isEmpty() || string.equals(null)) {
                return new String[][]{};
            }
            String[] rows = string.split("#");
            result = new String[rows.length][4];
            for (int i = 0; i < rows.length; i++) {
                String[] cells = rows[i].split("\\$");
                System.arraycopy(cells, 0, result[i], 0, 4);
            }
        } catch (Exception e) {
            return new String[][]{};
        }
        return result;
    }
}
