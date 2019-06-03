package cloud.northern.util;

public enum Header {
    CONTENT_TYPE("Content-Type"), AUTHORIZATION("Authorization");

    String value;

    Header(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
