package cloud.northern.bean;

public abstract class AError implements IError {
    protected int    code;
    protected String name;
    protected String error;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getError() {
        return error;
    }

    @Override
    public void setError(String error) {
        this.error = error;
    }
}
