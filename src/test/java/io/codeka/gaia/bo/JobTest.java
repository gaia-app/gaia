package io.codeka.gaia.bo;

import org.junit.jupiter.api.Test;

import java.io.PrintWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JobTest {

    @Test
    void getLogs_shouldReturnOutputStreamResult(){
        var job = new Job();

        PrintWriter printWriter = new PrintWriter(job.getLogsWriter());
        printWriter.println("Test Line 1");
        printWriter.println("Test Line 2");

        var logs = job.getLogs();

        assertEquals("Test Line 1\nTest Line 2\n", logs);
    }

}