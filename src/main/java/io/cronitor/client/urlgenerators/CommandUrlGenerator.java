package io.cronitor.client.urlgenerators;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class CommandUrlGenerator {

    private static final String BASE_URL = "https://cronitor.link/%s/%s";
    private static final String BASE_URL_HTTP = "http://cronitor.link/%s/%s";
    private static final String FALLBACK_BASE_URL = "https://cronitor.io/%s/%s";

    private static final String PAUSE_BASE_URL = "https://cronitor.link/%s/pause/%s";
    private static final String PAUSE_BASE_URL_HTTP = "http://cronitor.link/%s/pause/%s";
    private static final String FALLBACK_PAUSE_BASE_URL = "https://cronitor.io/%s/pause/%s";

    private Boolean usePrimaryPingDomain;
    private Boolean useHttps;

    private final static Logger logger = Logger.getLogger(CommandUrlGenerator.class.getName());

    public CommandUrlGenerator() {

        this.usePrimaryPingDomain = true;
        this.useHttps = true;
    }

    public CommandUrlGenerator(Boolean usePrimaryPingDomain, Boolean useHttps) {

        this.usePrimaryPingDomain = usePrimaryPingDomain;
        this.useHttps = useHttps;
    }


    public URL buildURL(String command, String monitorCode, String authKey, String message) throws MalformedURLException {
        String baseURL;
        if (usePrimaryPingDomain) {
            baseURL =  useHttps ? CommandUrlGenerator.BASE_URL : CommandUrlGenerator.BASE_URL_HTTP;
        } else {
            baseURL = CommandUrlGenerator.FALLBACK_BASE_URL;
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(String.format(baseURL, monitorCode, command));

            if (authKey != null) {
                uriBuilder.addParameter("auth_key", authKey);
            }
            if (message != null) {
                uriBuilder.addParameter("msg", message);
            }

            return uriBuilder.build().toURL();
        } catch (URISyntaxException e) {
            logger.warning(String.format("Failed to construct url for [%s, %s, %s, %s]", command, monitorCode, authKey, message));
            return new URL(CommandUrlGenerator.BASE_URL);
        }

    }

    public URL buildPauseURI(String monitorCode, int pauseHours, String authKey) throws MalformedURLException {
        String baseURL;
        if (usePrimaryPingDomain) {
            baseURL = useHttps ? CommandUrlGenerator.PAUSE_BASE_URL : CommandUrlGenerator.PAUSE_BASE_URL_HTTP;
        } else {
            baseURL = CommandUrlGenerator.FALLBACK_PAUSE_BASE_URL;
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(String.format(baseURL, monitorCode, pauseHours));

            if (authKey != null) {
                uriBuilder.addParameter("auth_key", authKey);
            }

            return uriBuilder.build().toURL();
        } catch (URISyntaxException e) {
            logger.warning(String.format("Failed to construct url for [%s, %d, %s, %s]", monitorCode, pauseHours, authKey));
            return new URL(CommandUrlGenerator.PAUSE_BASE_URL);
        }
    }
}
