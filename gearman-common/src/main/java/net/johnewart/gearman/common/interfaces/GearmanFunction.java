package net.johnewart.gearman.common.interfaces;

import net.johnewart.gearman.common.events.WorkEvent;
import net.johnewart.gearman.common.packets.request.WorkDataRequest;

public interface GearmanFunction {
    public WorkDataRequest process(WorkEvent workEvent);
}
