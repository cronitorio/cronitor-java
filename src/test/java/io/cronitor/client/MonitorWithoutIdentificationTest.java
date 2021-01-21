package io.cronitor.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.verify;

import java.util.logging.Logger;


@RunWith(PowerMockRunner.class)
public class MonitorWithoutIdentificationTest {

    @InjectMocks
    private CronitorClient client = new CronitorClient();

    @Mock
    private CronitorPinger cronitorPinger;

    @Mock
    private Logger logger;


    private String monitorKey = "d3x0c1";

    @Test
    public void can_start_monitor_with_minimal_requirements() throws Exception {

        client.run(monitorKey);
        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", null, null, true);
    }

    @Test
    public void can_start_monitor_with_message() throws Exception {

        client.run(monitorKey, "customRunMessage");
        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", null, "customRunMessage", true);
    }

    @Test
    public void can_complete_monitor_with_minimal_requirements() throws Exception {

        client.complete(monitorKey);
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", null, null, true);
    }

    @Test
    public void can_complete_monitor_with_message() throws Exception {

        client.complete(monitorKey, "customCompleteMessage");
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", null, "customCompleteMessage", true);
    }

    @Test
    public void can_fail_monitor_with_minimal_requirements() throws Exception {

        client.fail(monitorKey);
        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", null, null, true);
    }

    @Test
    public void can_fail_monitor_with_message() throws Exception {

        client.fail(monitorKey, "customFailMessage");
        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", null, "customFailMessage", true);
    }

    @Test
    public void pause_logs_an_error() throws Exception {
        client.pause(monitorKey, 5);
        verify(logger).warning("Set an API key to call pause.");
    }

    @Test
    public void unpause_logs_an_error() throws Exception {
        client.unpause(monitorKey);
        verify(logger).warning("Set an API key to call unpause.");
    }
}
