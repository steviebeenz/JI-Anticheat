package cc.ghast.janitor.v6.utils;

import cc.ghast.artemis.v4.check.data.ClickType;

import java.util.List;

/**
 * @author Ghast
 * @since 15-Mar-20
 */
public class StringUtil {
    public static String[] displaceByOne(String[] array) {
        String[] ne = new String[array.length - 1];
        System.arraycopy(array, 1, ne, 0, array.length - 1);
        return ne;
    }

    public static int countChar(String str, char c) {
        int count = 0;

        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == c)
                count++;
        }

        return count;
    }

    public static String[] toArray(List<String> strings) {
        String[] array = new String[strings.size()];

        for (int i = 0; i < strings.size(); i++) {
            array[i] = strings.get(i);
        }

        return array;
    }

    public static String[] toArrayEnum(List<ClickType> strings) {
        String[] array = new String[strings.size()];

        for (int i = 0; i < strings.size(); i++) {
            array[i] = strings.get(i).name();
        }

        return array;
    }
}
