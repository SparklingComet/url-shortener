package org.shanerx.webapp;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ConfigurationManager {

	private final File dir = new File("url_shortener");
	private final File config = new File(dir, "config.json");
	private final File urls = new File(dir, "urls.json");
	private final File redirect = new File(dir, "redirect.html");

	protected ConfigurationManager() throws IOException {
		if (!dir.isDirectory()) {
			dir.mkdir();
		}

		if (!config.exists()) {
			generateConfig();
		}

		if (!urls.exists()) {
			generateUrls();
		}

		if (!redirect.exists()) {
			generateRedirectTemplate();
		}
	}

	void generateConfig() throws IOException {
		config.createNewFile();
		PrintWriter write = new PrintWriter(config);
		write.println("{\n  \"port\": 4567,\n  \"wait\": 0\n}");
		write.println();
		write.close();
	}

	public File getConfigFile() {
		return config;
	}

	public JSONObject getConfig() throws IOException, ParseException {
		return (JSONObject) new JSONParser().parse(new FileReader(config));
	}

	void generateUrls() throws IOException {
		urls.createNewFile();
		PrintWriter write = new PrintWriter(urls);
		write.println("{\n" +
				"  \"google\": \"http://www.google.com\",\n" +
				"  \"github\": \"https://git.io/vbeU6\"}");
		write.println();
		write.close();
	}

	public File getUrlsFile() {
		return urls;
	}

	public JSONObject getUrls() throws IOException, ParseException {
		return (JSONObject) new JSONParser().parse(new FileReader(urls));
	}

	void generateRedirectTemplate() throws IOException {
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

	public File getRedirectTemplateFile() {
		return redirect;
	}

	public String getRedirectTemplate() throws IOException {
		final StringBuilder sb = new StringBuilder();
		String line;
		BufferedReader read = new BufferedReader(new FileReader(redirect));

		while ((line = read.readLine()) != null) {
			sb.append(line).append("\n");
		}

		return sb.toString();	}
}