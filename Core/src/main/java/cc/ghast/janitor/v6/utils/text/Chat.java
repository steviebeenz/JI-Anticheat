package cc.ghast.janitor.v6.utils.text;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.util.List;

/**
 * @author Ghast
 * @since 14-Mar-20
 */
public class Chat {
    public static String translate(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public static String[] translate(String[] s) {
        for (int i = 0; i < s.length; i++) {
            s[i] = translate(s[i]);
        }
        return s;
    }

    public static List<String> translate(List<String> s) {
        for (int i = 0; i < s.size(); i++) {
            s.set(i, translate(s.get(i)));
        }
        return s;
    }

    public static String[] translateAndConvert(List<String> s) {
        String[] var = new String[s.size()];
        for (int i = 0; i < s.size(); i++) {
            var[i] = translate(s.get(i));
        }
        return var;
    }

    public static String firstCap(String str) {
        return str != null && str.length() != 0 ? Character.toTitleCase(str.charAt(0)) + str.substring(1) : str;
    }

    public static void sendConsoleMessage(String s) {
        Bukkit.getConsoleSender().sendMessage(translate(s));
    }


    public static String spacer() {
        return translate("&8&m----------------------------");
    }


}
