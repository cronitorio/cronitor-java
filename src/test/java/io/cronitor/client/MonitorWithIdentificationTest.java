package io.cronitor.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import static org.mockito.Mockito.verify;

@RunWith(PowerMockRunner.class)
public class MonitorWithIdentificationTest {

    @InjectMocks
    private CronitorClient client = new CronitorClient("anApiKey");
    @Mock
    private CronitorPinger cronitorPinger;

    private String monitorKey = "d3x0c1";

    @Test
    public void can_start_monitor_with_minimal_requirements() throws Exception {

        client.run(monitorKey);

        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", "anApiKey", null, true);
    }

    @Test
    public void can_start_monitor_with_message() throws Exception {

        client.run(monitorKey, "customRunMessage");
        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", "anApiKey", "customRunMessage", true);
    }

    @Test
    public void can_complete_monitor_with_minimal_requirements() throws Exception {

        client.complete(monitorKey);
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", "anApiKey", null, true);
    }

    @Test
    public void can_complete_monitor_with_message() throws Exception {

        client.complete(monitorKey, "customCompleteMessage");
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", "anApiKey", "customCompleteMessage", true);
    }

    @Test
    public void can_fail_monitor_with_minimal_requirements() throws Exception {

        client.fail(monitorKey);
        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", "anApiKey", null, true);
    }

    @Test
    public void can_fail_monitor_with_message() throws Exception {

        client.fail(monitorKey, "customFailMessage");

        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", "anApiKey", "customFailMessage", true);
    }

    @Test
    public void can_pause_monitor() throws Exception {

        client.pause(monitorKey, 5);
        verify(cronitorPinger).pause(monitorKey, 5, "anApiKey");
    }

    @Test
    public void can_unpause_monitor() throws Exception {

        client.unpause(monitorKey);
        verify(cronitorPinger).pause(monitorKey, 0, "anApiKey");
    }
}
