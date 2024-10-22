// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EventRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class EventRoom extends AbstractRoom
{

    public EventRoom()
    {
        phase = AbstractRoom.RoomPhase.EVENT;
        mapSymbol = "?";
        mapImg = ImageMaster.MAP_NODE_EVENT;
        mapImgOutline = ImageMaster.MAP_NODE_EVENT_OUTLINE;
    }

    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
        Random eventRngDuplicate = new Random(Settings.seed, AbstractDungeon.eventRng.counter);
        event = AbstractDungeon.generateEvent(eventRngDuplicate);
        event.onEnterRoom();
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp)
            event.update();
        if(event.waitTimer == 0.0F && !event.hasFocus && phase != AbstractRoom.RoomPhase.COMBAT)
        {
            phase = AbstractRoom.RoomPhase.COMPLETE;
            event.reopen();
        }
    }

    public void render(SpriteBatch sb)
    {
        if(event != null)
            event.render(sb);
        super.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
        if(event != null)
            event.renderAboveTopPanel(sb);
    }
}
