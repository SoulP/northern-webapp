package cloud.northern.common.util;

public enum Header {
    CONTENT_TYPE("Content-Type"), AUTHORIZATION("Authorization"), ACCEPT("Accept");

    String value;

    Header(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
