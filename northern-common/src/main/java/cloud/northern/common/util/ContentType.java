package cloud.northern.common.util;

/**
 * Content-Type
 *
 * @author SoulP
 *
 */
public enum ContentType {
    //@formatter:off
    JSON("application/json"),
    JSON_UTF8("application/json; charset=UTF-8"),
    POST("application/x-www-form-urlencoded"),
    POST_UTF8("application/x-www-form-urlencoded; charset=UTF-8");
    //@formatter:on

    private String value;

    ContentType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
