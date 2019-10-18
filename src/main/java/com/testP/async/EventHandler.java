package com.testP.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel model);

    List<EventType>getSupportEventTypes();//支持哪些种类的Event
}
