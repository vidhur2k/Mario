package com.gamedesign.mario.event;

import com.almasb.ents.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

public class PlatformEvent extends Event
{
    public static final EventType<PlatformEvent> QUESTION_BLOCK
            = new EventType<>(ANY, "QUESTION_BLOCK");

    private Entity platform;

    public PlatformEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Entity platform)
    {
        super(eventType);
        this.platform = platform;
    }

    public Entity getPlatform()
    {
        return platform;
    }
}
