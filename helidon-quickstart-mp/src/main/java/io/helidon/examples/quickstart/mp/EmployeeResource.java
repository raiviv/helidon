package io.helidon.examples.quickstart.mp;

import java.util.Collection;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;


@Path("/hr")
public class EmployeeResource {
EmployeeRepository database = new EmployeeRepository();

@Path("/employees")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Collection<Employee> getAllEmployees() {
	return database.getAllEmployees();
}



@Path("/employees/{paramEmpId}")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Response getSingleEmployee(@PathParam("paramEmpId") Integer empId) {
	Employee emp = database.getEmployeeById(empId);
	if(emp != null) {
		ResponseBuilder builder = Response.ok().entity(emp);
		return builder.build();
	}else {
		ResponseBuilder builder = Response.status(404).entity("Employee Not Found");
		return builder.build();
	}
}
@Path("/employees/find")
@GET
@Produces(MediaType.APPLICATION_JSON)
public Collection<Employee> findEmployeesForSalary(@QueryParam("salary") Integer salary){
	return database.getAllEmployeesWithGivenSalary(salary);
}	

@Path("/employees")
@POST
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public String createEmployee(Employee emp) {
	System.out.println("EmployeeResource.createEmployee: Employee Id - " + emp.getId());
	database.createEmployee(emp);
	return "Employee Created successfully";
}

@Path("/employees/{paramEmpId}")
@PUT
@Produces(MediaType.TEXT_PLAIN)
@Consumes(MediaType.APPLICATION_JSON)
public String updateEmployee(@PathParam("paramEmpId") Integer empId, Employee emp) {
	database.updateEmployee(empId, emp);
	return "Employee Updated successfully";
}

@Path("/employees/{paramEmpId}")
@DELETE
@Produces(MediaType.TEXT_PLAIN)
public String deleteEmployee(@PathParam("paramEmpId") Integer empId) {
	database.deleteEmployee(empId);
	return "Employee deleted successfully";
}


}
