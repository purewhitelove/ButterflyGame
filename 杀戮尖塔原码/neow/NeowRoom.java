// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   NeowRoom.java

package com.megacrit.cardcrawl.neow;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;

// Referenced classes of package com.megacrit.cardcrawl.neow:
//            NeowEvent

public class NeowRoom extends AbstractRoom
{

    public NeowRoom(boolean isDone)
    {
        phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT;
        event = new NeowEvent(isDone);
        event.onEnterRoom();
    }

    public void onPlayerEntry()
    {
        AbstractDungeon.overlayMenu.proceedButton.hide();
    }

    public void update()
    {
        super.update();
        if(!AbstractDungeon.isScreenUp)
            event.update();
        if(event.waitTimer == 0.0F && !event.hasFocus && phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
            event.reopen();
        }
    }

    public void render(SpriteBatch sb)
    {
        super.render(sb);
        event.render(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        super.renderAboveTopPanel(sb);
        if(event != null)
            event.renderAboveTopPanel(sb);
    }
}
