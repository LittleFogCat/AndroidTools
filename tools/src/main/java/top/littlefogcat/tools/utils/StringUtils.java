package top.littlefogcat.tools.utils;

/**
 * Author: LittleFogCat
 * Email: littlefogcat@foxmail.com
 * Created at: 2018/8/29
 */
public class StringUtils {

    public static String removeBetween(String s, String pre, String suf) {
        String tmp = s;
        int indexOfPre = tmp.indexOf(pre);
        int indexOfSuf = tmp.indexOf(suf);

        while (indexOfPre >= 0 && indexOfSuf >= 0) {
            tmp = removeRange(tmp, indexOfPre, indexOfSuf + 1).toString();
            indexOfPre = tmp.indexOf(pre);
            indexOfSuf = tmp.indexOf(suf);
        }

        return tmp;
    }

    public static CharSequence removeRange(CharSequence s, int startIndex, int endIndex) {
        if (endIndex < startIndex)
            throw new IndexOutOfBoundsException("End index ($endIndex) is less than start index ($startIndex).");

        if (endIndex == startIndex)
            return s.subSequence(0, s.length());

        StringBuilder sb = new StringBuilder(s.length() - (endIndex - startIndex));
        sb.append(s, 0, startIndex);
        sb.append(s, endIndex, s.length());
        return sb;
    }

    /**
     * 判断一个字符串是否是数，包括整数，小数，正负数
     */
    public static boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) {
            System.out.println("empty s");
            return false;
        }
        char[] chars = s.toCharArray();
        boolean hasDot = false;
        for (int i = 0; i < chars.length; i++) {
            char c = chars[i];
            if (c >= '0' && c <= '9') {
                continue;
            }

            if (c == '.') {
                if (i == 0 || i == chars.length - 1 || hasDot) {
                    return false;
                } else {
                    hasDot = true;
                    continue;
                }
            }

            if ((c == '+' || c == '-') && i == 0) {
                continue;
            }

            return false;
        }

        return true;
    }

}
