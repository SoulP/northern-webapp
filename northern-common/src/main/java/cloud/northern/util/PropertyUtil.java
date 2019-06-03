package cloud.northern.util;

import java.util.ResourceBundle;

/**
 * Property
 *
 * @author SoulP
 *
 */
public class PropertyUtil {
    private static final ResourceBundle bundle = ResourceBundle.getBundle("application");

    private PropertyUtil() throws Exception {
    }

    /**
     * プロパティ値を取得する
     *
     * @param key
     *            キー
     * @return 値
     */
    public static String get(final String key) {
        return bundle.getString(key);
    }
}