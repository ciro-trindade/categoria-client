package financas.rest.client;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import financas.dto.CredenciaisDTO;
import financas.util.SessionContext;

public class UsuarioRESTClient  {
    public static final String REST_WEBSERVICE_URL = 
 	       "https://afternoon-crag-29106.herokuapp.com";
    private static final String REST_LOGIN_URL = "/login";
	private Response response;
	
	public boolean authenticate(CredenciaisDTO usuario) {		
		this.response = ClientBuilder.newClient().
				target(REST_WEBSERVICE_URL + REST_LOGIN_URL).
	    		queryParam("usuario", usuario).
	    		request(MediaType.APPLICATION_JSON).
	    		post(Entity.entity(usuario, MediaType.APPLICATION_JSON));
		if (response.getStatus() == Response.Status.OK.getStatusCode()) {
			SessionContext.getInstance().setAttribute("usuario", usuario);
			String token = response.getHeaderString("Authentication");
			SessionContext.getInstance().setAttribute("token", token);
			return true;
		}	    
		return false;
	}
	
}
