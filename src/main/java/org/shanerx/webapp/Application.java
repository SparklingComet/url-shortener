package org.shanerx.webapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import static spark.Spark.*;

public class Application {

	public static void main(String[] args) throws IOException, ParseException {

		final File config = new File("config.json");
		if (!config.exists()) {
			config.createNewFile();
			PrintWriter write = new PrintWriter(config);
			write.println("{\n  \"port\": 4567,\n  \"wait\": 0\n}");
			write.println();
			write.close();
		}

		final JSONObject obj = (JSONObject) new JSONParser().parse(new FileReader(config));
		port(Math.toIntExact((Long) obj.get("port")));

		final File data = new File("urls.json");
		if (!data.exists()) {
			data.createNewFile();
			PrintWriter write = new PrintWriter(data);
			write.println("{\n" +
					"  \"google\": \"http://www.google.com\",\n" +
					"  \"github\": \"https://git.io/vbeU6\"}");
			write.println();
			write.close();
		}

		final JSONObject urls = (JSONObject) new JSONParser().parse(new FileReader(data));

		final File redirect = new File("redirect.html");
		if (!redirect.exists()) {
			redirect.createNewFile();
			PrintWriter write = new PrintWriter(redirect);
			write.println("<html>\n" +
					"<head>\n" +
					"    <title>Redirecting...</title>\n" +
					"    <meta http-equiv=\"refresh\" content=\"$WAIT; URL=$DESTINATION\">\n" +
					"    <meta name=\"keywords\" content=\"automatic redirection\">\n" +
					"</head>\n" +
					"<body>\n" +
					"If your browser doesn't automatically go there within a few seconds,\n" +
					"you may want to go to\n" +
					"<a href=\"$DESTINATION\">the destination</a>\n" +
					"manually.\n" +
					"</body>\n" +
					"</html>");
			write.println();
			write.close();
		}

		final StringBuilder sb = new StringBuilder();
		String line;

		BufferedReader read = new BufferedReader(new FileReader(redirect));

		while ((line = read.readLine()) != null) {
			sb.append(line).append("\n");
		}

		final String redirectHTML = sb.toString();

		get("/:url", (req, res) -> {
			String url = req.params(":url");

			if (!urls.containsKey(url.toLowerCase())) {
				return "{\n  \"error\": 404,\n  \"message\": \"Not found\"\n}";
			}

			return redirectHTML
					.replace("$DESTINATION", (String) urls.get(url))
					.replace("$WAIT", String.valueOf((Long) obj.get("wait")));
		});

		notFound((req, res) -> {
			res.type("application/json");
			return "{\n  \"error\": 404,\n  \"message\": \"Not found\"\n}";
		});
	}
}