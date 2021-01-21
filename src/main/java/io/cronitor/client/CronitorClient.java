package io.cronitor.client;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import java.util.logging.Logger;

public class CronitorClient {

    private Logger logger = Logger.getLogger(CronitorClient.class.getName());

    private CronitorPinger cronitorPinger = new CronitorPinger();

    private String apiKey;
    private Boolean useHttps;

    public CronitorClient() {

        this.apiKey = null;
        this.useHttps = true;
    }

    public CronitorClient(String apiKey) {

        this.apiKey = apiKey;
        this.useHttps = true;
    }

    public CronitorClient(Boolean useHttps) {
        this.useHttps = useHttps;
        this.apiKey = null;
    }

    public static CronitorClient withoutHttps() {
        return new CronitorClient(false);
    }

    public void run(String monitorKey) throws IOException {
        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.RUN.getValue(), monitorKey, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /run on cronitor because the monitor code is null or empty");
        }
    }

    public void run(String monitorKey, String message) throws IOException {
        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.RUN.getValue(), monitorKey, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /run on cronitor because the monitor code is null or empty");
        }
    }

    public void complete(String monitorKey) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.COMPLETE.getValue(), monitorKey, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /complete on cronitor because the monitor code is null or empty");
        }
    }

    public void complete(String monitorKey, String message) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.COMPLETE.getValue(), monitorKey, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /complete on cronitor because the monitor code is null or empty");
        }
    }

    public void fail(String monitorKey) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorKey, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void fail(String monitorKey, String message) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorKey, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void tick(String monitorKey) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorKey, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void tick(String monitorKey, String message) throws IOException {

        if(StringUtils.isNotBlank(monitorKey)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorKey, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void pause(String monitorKey, int hours) throws IOException {
        if(StringUtils.isNotBlank(apiKey)) {
            if(StringUtils.isNotBlank(monitorKey)) {
                cronitorPinger.pause(monitorKey, hours, apiKey);
            } else {
                logger.warning("Monitor key cannot be blank.");
            }
        } else {
            logger.warning("Set an API key to call pause.");
        }
    }

    public void unpause(String monitorKey) throws IOException {

        if(StringUtils.isNotBlank(apiKey)) {
            if(StringUtils.isNotBlank(monitorKey)) {
                cronitorPinger.pause(monitorKey, 0, apiKey);
            } else {
                logger.warning("Monitor key cannot be blank.");
            }
        } else {
            logger.warning("Set an API key to call unpause.");
        }
    }
}
