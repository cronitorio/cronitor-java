package io.cronitor.client;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.logging.Logger;
import org.apache.commons.codec.binary.Base64;

import io.cronitor.client.urlgenerators.CommandUrlGenerator;

public class CronitorPinger {

    public CronitorPinger() {

        this.connection = null;
    }

    HttpURLConnection connection;
    URL url;
    private final static Logger logger = Logger.getLogger(CronitorPinger.class.getName());
    private final Integer cronitorPingTimeoutInSecond = 10;

    public void ping(String command, String monitorKey, String apiKey, String message, Boolean useHttps) throws IOException {
        for (int i=0; i<8; i++) {
            Boolean usePrimaryPingDomain = i < 4;
            setConnection(getURL(usePrimaryPingDomain, useHttps, command, monitorKey, apiKey, message), apiKey);
            if (_ping()) { return; }
        }
    }

    public void pause(String monitorKey, int timeoutHours, String apiKey) throws IOException {
        for (int i=0; i<8; i++) {
            Boolean usePrimaryPingDomain = i < 4;
            setConnection(getURL(usePrimaryPingDomain, true, monitorKey, timeoutHours, apiKey), apiKey);
            if (_ping()) { return; }
        }
    }

    // methods below are left open to package for ease of testing purposes.
	URL getURL(Boolean usePrimaryPingDomain, Boolean useHttps, String command, String monitorKey, String apiKey, String message) throws IOException {
		return new CommandUrlGenerator(usePrimaryPingDomain, useHttps).buildURL(command, monitorKey, apiKey, message);
    }

    URL getURL(Boolean usePrimaryPingDomain, Boolean useHttps, String monitorKey, int timeoutHours, String apiKey) throws IOException {
		return  new CommandUrlGenerator(usePrimaryPingDomain, useHttps).buildPauseURI(monitorKey, timeoutHours, apiKey);
    }

    void setConnection(URL url, String apiKey) {
        String authHeader = new String(Base64.encodeBase64((apiKey + ":" + "").getBytes()));

        try {
            this.connection = (HttpURLConnection) url.openConnection();
            this.connection.setRequestProperty("Authorization", authHeader);
            this.connection.setConnectTimeout(cronitorPingTimeoutInSecond * 1000);
        } catch (IOException e) {
            logger.warning(String.format("Unable to establish connection to %s", url.getPath()));
        }
      }

    boolean _ping() {
        try {
            this.connection.connect();
            this.connection.getInputStream();
            if (this.connection.getResponseCode() >= 500 ) {
                logger.warning(String.format("Failed to call url [%s] : an error occurred : [%d]", this.connection.getURL().toString(), this.connection.getResponseCode()));
                return false;
            }
            this.connection.disconnect();
        } catch (SocketTimeoutException ignore) {
            logger.warning(String.format("Failed to call url [%s] : a timeout occurred after %d seconds : [%s]", this.connection.getURL().toString(), cronitorPingTimeoutInSecond, ignore));
            return false;
        } catch (IOException e) {
            logger.warning(String.format("Unable to establish connection to %s", this.connection.getURL().toString()));
        }
        return true;

    }

}
