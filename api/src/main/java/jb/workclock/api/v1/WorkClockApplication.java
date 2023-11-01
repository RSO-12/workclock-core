package jb.workclock.api.v1;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;


import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;
import org.eclipse.microprofile.openapi.annotations.servers.Server;

@OpenAPIDefinition(info = @Info(title = "WorkClock", version = "v1",
        contact = @Contact(email = "janez.sedeljsak@gmail.com"),
        license = @License(name = "dev"), description = "API for managing work time."),
        servers = @Server(url = "http://localhost:3000/"))
@ApplicationPath("/v1")
public class WorkClockApplication extends Application {

}
