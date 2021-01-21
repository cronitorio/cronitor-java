package io.cronitor.client;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import java.net.URL;
import io.cronitor.client.urlgenerators.CommandUrlGenerator;

@RunWith(PowerMockRunner.class)
public class CommandUrlGeneratorTest {

    private CommandUrlGenerator urlGenerator = new CommandUrlGenerator();
    private CommandUrlGenerator fallbackUrlGenerator = new CommandUrlGenerator(false, true);
    private CommandUrlGenerator httpUrlGenerator = new CommandUrlGenerator(true, false);

    private String monitorKey = "d3x0c1";
    private String apiKey = "anApiKey";

    @Test
    public void can_build_run_url() throws Exception {
        URL runURL = urlGenerator.buildURL(Command.RUN.getValue(), monitorKey, apiKey, null);

        Assert.assertEquals(new URL(String.format("https://cronitor.link/p/%s/%s?state=run", apiKey, monitorKey)), runURL);
    }

    @Test
    public void can_build_complete_url() throws Exception {
        URL completeUrl = urlGenerator.buildURL(Command.COMPLETE.getValue(), monitorKey, apiKey, null);

        Assert.assertEquals(new URL(String.format("https://cronitor.link/p/%s/%s?state=complete", apiKey, monitorKey)), completeUrl);
    }

    @Test
    public void can_build_fail_url() throws Exception {
        URL fail = urlGenerator.buildURL(Command.FAIL.getValue(), monitorKey, apiKey, null);

        Assert.assertEquals(new URL(String.format("https://cronitor.link/p/%s/%s?state=fail", apiKey, monitorKey)), fail);
    }

    @Test
    public void can_build_pause_url() throws Exception {
        URL pauseUrl = urlGenerator.buildPauseURI(monitorKey, 5, null);

        Assert.assertEquals(new URL("https://cronitor.io/api/monitors/d3x0c1/pause/5"), pauseUrl);
    }

    @Test
    public void can_build_url_with_msg() throws Exception {
        URL runURL = urlGenerator.buildURL(Command.RUN.getValue(), monitorKey, apiKey, "customMessage");

        Assert.assertEquals(new URL(String.format("https://cronitor.link/p/%s/%s?state=run&msg=customMessage", apiKey, monitorKey)), runURL);
    }

    @Test
    public void can_build_url_with_fallback_domain() throws Exception {
        URL runURL = fallbackUrlGenerator.buildURL(Command.RUN.getValue(), monitorKey, apiKey, null);
        Assert.assertEquals(new URL(String.format("https://cronitor.io/p/%s/%s?state=run", apiKey, monitorKey)), runURL);
    }

    @Test
    public void can_build_url_without_https() throws Exception {
        URL runURL = httpUrlGenerator.buildURL(Command.RUN.getValue(), monitorKey, apiKey, null);
        Assert.assertEquals(new URL("http://cronitor.link/d3x0c1?state=run"), runURL);
    }


}
