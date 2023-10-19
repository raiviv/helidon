
package io.helidon.example.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.json.Json;
import javax.json.JsonBuilderFactory;
import javax.json.JsonObject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
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
    
    @Named("greetingDataSource")
    @Inject
    javax.sql.DataSource greetDatasource;

    @Inject
    public GreetResource(@ConfigProperty(name = "app.greeting") String message) {
    	
        this.message = message;
    }
    

    /**
     * Return a worldly greeting message.
     *
     * @return {@link JsonObject}
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public JsonObject getDefaultMessage() {
        String msg = String.format("%s %s!", message, "World");
        return JSON.createObjectBuilder()
                .add("message", msg)
                .build();
    }
    @Path("/salutations")
    @GET
    public Collection<String> getAllSalutations(){
    	ArrayList salutations = new ArrayList<String>();
    	try {
    		Connection con = greetDatasource.getConnection(); //Get the connection from connection pool
    		Statement stmt = con.createStatement();
    		ResultSet rs = stmt.executeQuery("SELECT SALUTATION, RESPONSE FROM GREETING");
    		while(rs.next()) {
    			salutations.add(rs.getString("SALUTATION"));
    		}
    		rs.close();
    		stmt.close();
    		con.close(); //Return the connection back to the pool.
		} catch (Exception e) {
			e.printStackTrace();
		}    	
    	return salutations;
    }

}
