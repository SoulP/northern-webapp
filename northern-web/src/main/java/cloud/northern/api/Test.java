package cloud.northern.api;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

@Path("test")
public class Test {

    @GET
    public String hello() {
        return "Hello World!";
    }
}
