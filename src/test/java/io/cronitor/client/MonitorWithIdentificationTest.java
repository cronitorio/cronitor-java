package io.cronitor.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.powermock.modules.junit4.PowerMockRunner;

// import java.net.URL;

import static org.mockito.Mockito.verify;
import io.cronitor.client.Command;

@RunWith(PowerMockRunner.class)
public class MonitorWithIdentificationTest {

    @InjectMocks
    private CronitorClient client = new CronitorClient("customAuthKey");
    @Mock
    private CronitorPinger cronitorPinger;

    private String monitorCode = "d3x0c1";

    @Test
    public void can_start_monitor_with_minimal_requirements() throws Exception {

        client.run(monitorCode);

        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", "customAuthKey", null, true);
    }

    @Test
    public void can_start_monitor_with_message() throws Exception {

        client.run(monitorCode, "customRunMessage");
        verify(cronitorPinger).ping(Command.RUN.getValue(), "d3x0c1", "customAuthKey", "customRunMessage", true);
    }

    @Test
    public void can_complete_monitor_with_minimal_requirements() throws Exception {

        client.complete(monitorCode);
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", "customAuthKey", null, true);
    }

    @Test
    public void can_complete_monitor_with_message() throws Exception {

        client.complete(monitorCode, "customCompleteMessage");
        verify(cronitorPinger).ping(Command.COMPLETE.getValue(), "d3x0c1", "customAuthKey", "customCompleteMessage", true);
    }

    @Test
    public void can_fail_monitor_with_minimal_requirements() throws Exception {

        client.fail(monitorCode);
        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", "customAuthKey", null, true);
    }

    @Test
    public void can_fail_monitor_with_message() throws Exception {

        client.fail(monitorCode, "customFailMessage");

        verify(cronitorPinger).ping(Command.FAIL.getValue(), "d3x0c1", "customAuthKey", "customFailMessage", true);
    }

    @Test
    public void can_pause_monitor() throws Exception {

        client.pause(monitorCode, 5);
        verify(cronitorPinger).pause(monitorCode, 5, "customAuthKey", true);
    }

    @Test
    public void can_unpause_monitor() throws Exception {

        client.unpause(monitorCode);
        verify(cronitorPinger).pause(monitorCode, 0, "customAuthKey", true);
    }
}
