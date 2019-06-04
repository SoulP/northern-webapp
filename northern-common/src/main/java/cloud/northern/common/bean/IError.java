package cloud.northern.common.bean;

/**
 * Error
 *
 * @author SoulP
 *
 */
public interface IError {
    public int getCode();

    public String getName();

    public String getError();

    public void setError(String error);
}
