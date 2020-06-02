package io.cronitor.client;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;

import java.util.logging.Logger;

public class CronitorClient {

    private final static Logger logger = Logger.getLogger(CronitorClient.class.getName());

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

    public void run(String monitorCode) throws IOException {
        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.RUN.getValue(), monitorCode, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /run on cronitor because the monitor code is null or empty");
        }
    }

    public void run(String monitorCode, String message) throws IOException {
        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.RUN.getValue(), monitorCode, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /run on cronitor because the monitor code is null or empty");
        }
    }

    public void complete(String monitorCode) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.COMPLETE.getValue(), monitorCode, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /complete on cronitor because the monitor code is null or empty");
        }
    }

    public void complete(String monitorCode, String message) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.COMPLETE.getValue(), monitorCode, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /complete on cronitor because the monitor code is null or empty");
        }
    }

    public void fail(String monitorCode) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorCode, apiKey, null, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void fail(String monitorCode, String message) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.ping(Command.FAIL.getValue(), monitorCode, apiKey, message, useHttps);
        } else {
            logger.warning("We can't ping /fail on cronitor because the monitor code is null or empty");
        }
    }

    public void pause(String monitorCode, int timeoutInHours) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.pause(monitorCode, timeoutInHours, apiKey, useHttps);
        } else {
            logger.warning("we can't pause monitor because the monitor code is null or empty");
        }
    }

    public void unpause(String monitorCode) throws IOException {

        if(StringUtils.isNotBlank(monitorCode)) {
            cronitorPinger.pause(monitorCode, 0, apiKey, useHttps);
        } else {
            logger.warning("we can't pause monitor because the monitor code is null or empty");
        }
    }
}
