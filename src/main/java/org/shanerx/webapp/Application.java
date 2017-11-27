package org.shanerx.webapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static spark.Spark.*;

public class Application {

	public static void main(String[] args) throws IOException, ParseException {

		long millis = System.currentTimeMillis();

		ConfigurationManager man = new ConfigurationManager();
		final JSONObject config = man.getConfig();
		final JSONObject urls = man.getUrls();
		final String redirect = man.getRedirectTemplate();
		final String notFound = man.get404Template();
		
		Logger log = Logger.getLogger("LOG");
		log.log(Level.INFO, "Successfully generated and parsed files.");

		int port = Math.toIntExact((Long) config.get("port"));
		port(port);
		log.log(Level.INFO, "Webserver started on port " + port);

		get("/:url", (req, res) -> {
			String url = req.params(":url");

			if (!urls.containsKey(url.toLowerCase())) {
				return notFound;
			}

			return redirect
					.replace("$DESTINATION", (String) urls.get(url))
					.replace("$WAIT", String.valueOf((Long) config.get("wait")));
		});

		notFound((req, res) -> {
			res.type("application/json");
			return notFound;
		});

		log.log(Level.INFO, "Done! (" + (System.currentTimeMillis() - millis)+ "ms)");
	}
}
