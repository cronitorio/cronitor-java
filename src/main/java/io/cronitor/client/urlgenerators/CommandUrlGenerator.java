package io.cronitor.client.urlgenerators;

import org.apache.http.client.utils.URIBuilder;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

public class CommandUrlGenerator {

    private static final String BASE_URL = "https://cronitor.link/p/%s/%s";
    private static final String BASE_URL_HTTP = "http://cronitor.link/%s";
    private static final String FALLBACK_BASE_URL = "https://cronitor.io/p/%s/%s";

    private static final String PAUSE_BASE_URL = "https://cronitor.io/api/monitors/%s/pause/%s";
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


    public URL buildURL(String command, String monitorKey, String apiKey, String message) throws MalformedURLException {
        String baseURL;
        String url;

        if (usePrimaryPingDomain) {
            if (useHttps) {
                url = String.format(CommandUrlGenerator.BASE_URL, apiKey, monitorKey);
            } else {
                url = String.format(CommandUrlGenerator.BASE_URL_HTTP, monitorKey);
            }
        } else {
            url = String.format(CommandUrlGenerator.FALLBACK_BASE_URL, apiKey, monitorKey);
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(url);

            uriBuilder.addParameter("state", command);

            if (message != null) {
                uriBuilder.addParameter("msg", message);
            }

            return uriBuilder.build().toURL();
        } catch (URISyntaxException e) {
            logger.warning(String.format("Failed to construct url for [%s, %s, %s, %s]", command, monitorKey, apiKey, message));
            return new URL(CommandUrlGenerator.BASE_URL);
        }

    }

    public URL buildPauseURI(String monitorKey, int pauseHours, String apiKey) throws MalformedURLException {
        String baseURL;
        if (usePrimaryPingDomain) {
            baseURL = CommandUrlGenerator.PAUSE_BASE_URL;
        } else {
            baseURL = CommandUrlGenerator.FALLBACK_PAUSE_BASE_URL;
        }

        try {
            URIBuilder uriBuilder = new URIBuilder(String.format(baseURL, monitorKey, pauseHours));

            return uriBuilder.build().toURL();
        } catch (URISyntaxException e) {
            logger.warning(String.format("Failed to construct url for [%s, %d, %s, %s]", monitorKey, pauseHours, apiKey));
            return new URL(CommandUrlGenerator.PAUSE_BASE_URL);
        }
    }
}
