package com.gamedesign.mario.event;


import com.almasb.ents.Entity;
import javafx.beans.NamedArg;
import javafx.event.Event;
import javafx.event.EventType;

public class PickupEvent extends Event
{
    public static final EventType<PickupEvent> MUSHROOM
            = new EventType<>(ANY, "MUSHROOM_PICKUP");

    private Entity pickup;

    public PickupEvent(@NamedArg("eventType") EventType<? extends Event> eventType, Entity pickup)
    {
        super(eventType);
        this.pickup = pickup;
    }

    public Entity getPickup()
    {
        return pickup;
    }
}
