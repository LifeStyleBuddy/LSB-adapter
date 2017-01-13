package introsde.rest.adapters;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import javax.ejb.*;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import org.json.*;

@Stateless // will work only inside a Java EE application
@LocalBean // will work only inside a Java EE application
@Path("/adapter")
public class Adapter {

	// Getting a quote+photo from PIXABAY API
	@GET
	@Path("/getPictureQuote")
	public Response getPicture() throws ClientProtocolException, IOException {
		String[] keyres = { "frasi+citazioni", "jogging" };
		int randomNum = 0 + (int) (Math.random() * (keyres.length));

		final String TOKEN = "1933750-5a6d33432ed6f6637c1a71325";

		String ENDPOINT = "https://pixabay.com/api/?key=" + TOKEN + "&q=" + keyres[randomNum] + "&image_type=photo";

		String jsonResponse = null;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(ENDPOINT);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject o = new JSONObject(result.toString());

		if (response.getStatusLine().getStatusCode() == 200) {

			JSONArray arr = o.getJSONArray("hits");

			// generiamo numero random da 1 a 20
			Random r = new Random();
			int Low = 1;
			int High = arr.length();
			int i = r.nextInt(High - Low) + Low;

			String type = arr.getJSONObject(i).getString("type");

			if (type.equals("photo")) {
				String preview_url = "\"preview_url\":\"" + arr.getJSONObject(i).getString("previewURL") + "\"";
				String web_format_url = ", " + "\"web_format_url\":\"" + arr.getJSONObject(i).getString("webformatURL")
						+ "\"";
				jsonResponse = "{" + preview_url + web_format_url + "}";
				return Response.ok(jsonResponse).build();

			}

		}

		return Response.status(204).build();
	}

	// Getting a LUNCH RECEIPE from EDAMAM API
	@GET
	@Path("/getLunchRecipe")
	public Response getLunch() throws ClientProtocolException, IOException {

		final String APP_KEY = "385dd41c7cc9330adcf4a468f4850383";
		final String APP_ID = "a5abc70e";

		String ENDPOINT = "https://api.edamam.com/search?q=pasta&app_id=" + APP_ID + "&app_key=" + APP_KEY
				+ "&diet=balanced";

		String jsonResponse = null;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(ENDPOINT);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject o = new JSONObject(result.toString());

		if (response.getStatusLine().getStatusCode() == 200) {

			JSONArray arr = o.getJSONArray("hits");

			// generiamo numero random da 1 a arr.lenght()
			Random r = new Random();
			int Low = 1;
			int High = arr.length();
			int i = r.nextInt(High - Low) + Low;

			if (!arr.equals(null)) {

				String label = "\"label\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("label") + "\"";

				String image = ", " + "\"image\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("image")
						+ "\"";

				String url = ", " + "\"url\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("url") + "\"";

				JSONArray arr2 = arr.getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredientLines");

				String ing = "";
				for (int a = 0; a < arr2.length(); a++) {
					ing = arr2.getString(a) + "; " + ing;
				}

				String ingredients = ", " + "\"ingredients\":\"" + ing +"\"";

				jsonResponse = "{" + label + image + url + ingredients + "}";

				return Response.ok(jsonResponse).build();
			}

		}

		return Response.status(204).build();
	}

	// Getting a DINNER RECEIPE from EDAMAM API
	@GET
	@Path("/getDinnerRecipe")
	public Response getDinner() throws ClientProtocolException, IOException {

		final String APP_KEY = "385dd41c7cc9330adcf4a468f4850383";
		final String APP_ID = "a5abc70e";

		String ENDPOINT = "https://api.edamam.com/search?q=&app_id=" + APP_ID + "&app_key=" + APP_KEY
				+ "&diet=low-carb";

		String jsonResponse = null;

		DefaultHttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(ENDPOINT);
		HttpResponse response = client.execute(request);

		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null) {
			result.append(line);
		}

		JSONObject o = new JSONObject(result.toString());

		if (response.getStatusLine().getStatusCode() == 200) {

			JSONArray arr = o.getJSONArray("hits");

			// generiamo numero random da 1 a arr.lenght()
			Random r = new Random();
			int Low = 1;
			int High = arr.length();
			int i = r.nextInt(High - Low) + Low;

			if (!arr.equals(null)) {

				String label = "\"label\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("label") + "\"";

				String image = ", " + "\"image\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("image")
						+ "\"";

				String url = ", " + "\"url\":\"" + arr.getJSONObject(i).getJSONObject("recipe").getString("url") + "\"";

				JSONArray arr2 = arr.getJSONObject(i).getJSONObject("recipe").getJSONArray("ingredientLines");

				String ing = "";
				for (int a = 0; a < arr2.length(); a++) {
					ing = arr2.getString(a) + "; " + ing;
				}

				String ingredients = ", " + "\"ingredients\":\"" + ing +"\"";

				jsonResponse = "{" + label + image + url + ingredients + "}";

				return Response.ok(jsonResponse).build();
			}

		}

		return Response.status(204).build();
	}

}
