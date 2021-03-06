# URL-Shortener
When I wrote this software, I was originally testing [Spark, *A micro framework for creating web applications in Kotlin and Java 8 with minimal effort*](http://sparkjava.com).

The result was a very simple link shortening software written on Java. Please notice that this was only created for fun and that I do not attempt to compete with URL-shortening websites such as http://goo.gl, http://tinyurl.com and http://tiny.cc.

This is not meant to replace any of those services, and has not been tested against security breaches. Use at your own risk.

## Download
The download is coming soon.

## Getting Started
1. Navigate to the directory where the downloaded .jar file resides
2. Run the command `java -jar <file.name.jar>` (without the brackets "<>")
3. Open the `url_shortener/` folder which was just generated
4. Edit the files and restart:
  - Open `config.json` to edit the port (default 4567) and the redirection delay
  - Open `urls.json` to edit the URLS
  - Open `redirect.html` to edit the page that is displayed when the user is about to be redirected
  - Open `not_found.html` to edit the 404 error page
    - **Tip:** if you are not familiar with JSON, make sure to copy the new config into a [JSON parser](http://json.parser.online.fr) to make sure there aren't any syntax errors.
5. Type your webserver's IP in the URL bar of your browser and append the port to it: `http://xxx.yy.xxx.y:zzzz`. **Tip:** If you are running the application on your own computer, you can use `localhost:zzzz`, where `zzzz` is the port specified in `config.json`. (default: 4567)
## Troubleshooting
If you run into any problems or have any questions, suggestions or concerns, feel free to reach out to me by [opening an issue](https://github.com/SparklingComet/url-shortener/issues).

## License
The work is licensed under the GNU General Public License v3, which may be found [here](https://github.com/SparklingComet/url-shortener/blob/master/LICENSE).

## Credits/Dependencies
Thanks to the following projects, which made this software possible:
* Spark Framework
* JSON-Simple
* SLF4J
