package net.johnewart.gearman.example;

import net.johnewart.gearman.client.NetworkGearmanWorker;
import net.johnewart.gearman.common.events.WorkEvent;
import net.johnewart.gearman.common.packets.Packet;
import net.johnewart.gearman.common.packets.request.WorkCompleteRequest;
import net.johnewart.gearman.common.packets.request.WorkDataRequest;
import net.johnewart.gearman.common.packets.request.WorkExceptionRequest;
import net.johnewart.gearman.common.packets.request.WorkRequest;
import net.johnewart.gearman.common.packets.response.WorkFailResponse;
import org.apache.commons.lang3.ArrayUtils;
import net.johnewart.gearman.common.interfaces.GearmanFunction;
import net.johnewart.gearman.common.Job;
import net.johnewart.gearman.net.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WorkerDemo {
    private static Logger LOG = LoggerFactory.getLogger(WorkerDemo.class);

    static class ReverseFunction implements GearmanFunction
    {
        @Override
        public WorkDataRequest process(WorkEvent workEvent) {
            Job job = workEvent.job;
            byte[] data = job.getData();
            String function = job.getFunctionName();
            LOG.debug("Got data for function " + function);
            ArrayUtils.reverse(data);
            return new WorkExceptionRequest(job.getJobHandle(), data);
        }
    }

    public static void main(String... args)
    {
        try {
            byte data[] = "This is a test".getBytes();
            NetworkGearmanWorker worker = new NetworkGearmanWorker.Builder()
                                        .withConnection(new Connection("54.37.16.205", 4730))
                                        .build();

            worker.registerCallback("reverse", new ReverseFunction());

            worker.doWork();
        } catch (Exception e) {
            LOG.error("oops!");
        }
    }
}
