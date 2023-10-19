
package io.helidon.example.jpa;

import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

/**
 * A simple JAX-RS resource to greet you. Examples:
 *
 * Get default greeting message:
 * curl -X GET http://localhost:8080/greet
 *
 * The message is returned as a JSON object.
 */
@Path("/greet")
public class GreetResource {
    private static final JsonBuilderFactory JSON = Json.createBuilderFactory(Collections.emptyMap());

    private final String message;

    @Inject
    public GreetResource(@ConfigProperty(name = "app.greeting") String message) {
        this.message = message;
    }

    /**
     * Return a worldly greeting message.
     *
     * @return {@link JsonObject}
     */
    @PersistenceContext
    private EntityManager em;
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getDefaultMessage() {
        String msg = String.format("%s %s!", message, "World");
        return JSON.createObjectBuilder()
                .add("message", msg)
                .build();
    }
    
    
    @GET
    @Path("response/{salutation}")
    @Produces("text/plain")
    @Transactional
    public String getResponse(@PathParam("salutation") String salutation) {
        final Greeting greeting = this.em.find(Greeting.class, salutation);
        final String returnValue;
        if (greeting == null) {
            returnValue = null;
        } else {
            returnValue = greeting.getResponse();
        }
        return returnValue;
    }

}
