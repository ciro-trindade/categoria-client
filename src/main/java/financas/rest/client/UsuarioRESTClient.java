package financas.rest.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import financas.dto.CredenciaisDTO;
import financas.model.Cliente;
import financas.util.SessionContext;

public class UsuarioRESTClient  {
	
    public static final String REST_WEBSERVICE_URL = 
 	       "https://afternoon-crag-29106.herokuapp.com";
 	/*
	public static final String REST_WEBSERVICE_URL = 
	 	       "http://localhost:8090";
	*/
    private static final String REST_LOGIN_URL = "/login";
	private Response response;
	
	public boolean authenticate(CredenciaisDTO usuario) {		
		this.response = ClientBuilder.newClient().
				target(REST_WEBSERVICE_URL + REST_LOGIN_URL).
	    		queryParam("usuario", usuario).
	    		request(MediaType.APPLICATION_JSON).
	    		post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			Cliente cliente = this.response.readEntity(Cliente.class);
			System.out.println(cliente);
			SessionContext.getInstance().setAttribute("usuario", cliente);
			String token = response.getHeaderString("Authentication");
			SessionContext.getInstance().setAttribute("token", token);
			return true;
		}	    
		return false;
	}
	
}
