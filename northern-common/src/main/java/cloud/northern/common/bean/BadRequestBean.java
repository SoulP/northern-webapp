package cloud.northern.common.bean;

import org.springframework.stereotype.Component;

/**
 * 400 Bad Request
 *
 * @author SoulP
 *
 */
@Component
public class BadRequestBean extends AError {
    public BadRequestBean() {
        code = 400;
        name = "Bad Request";
    }

    public BadRequestBean(String error) {
        this.error = error;
    }
}
