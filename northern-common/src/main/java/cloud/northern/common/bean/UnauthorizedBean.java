package cloud.northern.common.bean;

import org.springframework.stereotype.Component;

/**
 * 401 Unauthorized
 *
 * @author SoulP
 *
 */
@Component
public class UnauthorizedBean extends AError {
    public UnauthorizedBean() {
        code = 401;
        name = "Unauthorized";
    }

    public UnauthorizedBean(String error) {
        this.error = error;
    }
}
