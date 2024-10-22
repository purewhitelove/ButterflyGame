// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VictoryRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class VictoryRoom extends AbstractRoom
{
    public static final class EventType extends Enum
    {

        public static EventType[] values()
        {
            return (EventType[])$VALUES.clone();
        }

        public static EventType valueOf(String name)
        {
            return (EventType)Enum.valueOf(com/megacrit/cardcrawl/rooms/VictoryRoom$EventType, name);
        }

        public static final EventType HEART;
        public static final EventType NONE;
        private static final EventType $VALUES[];

        static 
        {
            HEART = new EventType("HEART", 0);
            NONE = new EventType("NONE", 1);
            $VALUES = (new EventType[] {
                HEART, NONE
            });
        }

        private EventType(String s, int i)
        {
            super(s, i);
        }
    }


    public VictoryRoom(EventType type)
    {
        phase = AbstractRoom.RoomPhase.EVENT;
        eType = type;
    }

    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rooms$VictoryRoom$EventType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$rooms$VictoryRoom$EventType = new int[EventType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$VictoryRoom$EventType[EventType.HEART.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$VictoryRoom$EventType[EventType.NONE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.rooms.VictoryRoom.EventType[eType.ordinal()])
        {
        case 1: // '\001'
            event = new SpireHeart();
            event.onEnterRoom();
            break;
        }
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp)
            event.update();
    }

    public void render(SpriteBatch sb)
    {
        if(event != null)
        {
            event.renderRoomEventPanel(sb);
            event.render(sb);
        }
        super.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
        if(event != null)
            event.renderAboveTopPanel(sb);
    }

    public EventType eType;
}
