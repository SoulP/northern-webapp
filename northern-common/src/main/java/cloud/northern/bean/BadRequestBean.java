package cloud.northern.bean;

import org.springframework.stereotype.Component;

/**
 * 400 Bad Request
 *
 * @author SoulP
 *
 */
@Component
public class BadRequestBean {
    private final int    code = 400;
    private final String name = "Bad Request";
    private String       error;

    public BadRequestBean() {
    }

    public BadRequestBean(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
