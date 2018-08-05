package net.johnewart.gearman.common.packets.request;

import net.johnewart.gearman.common.packets.response.ResponsePacket;
import net.johnewart.gearman.common.packets.response.WorkResponse;
import net.johnewart.gearman.constants.PacketType;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;

public class WorkExceptionRequest extends WorkDataRequest
{

    public WorkExceptionRequest(String jobhandle, byte[] exception)
    {
        super(jobhandle, exception);
        this.type = PacketType.WORK_EXCEPTION;
    }

    public WorkExceptionRequest(byte[] pktdata)
    {
        super(pktdata);
        this.jobHandle = new AtomicReference<String>();
        int pOff = parseString(0, jobHandle);
        this.data = Arrays.copyOfRange(rawdata, pOff, rawdata.length);
    }
}
