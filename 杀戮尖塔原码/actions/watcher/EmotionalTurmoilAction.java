// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EmotionalTurmoilAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.*;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class EmotionalTurmoilAction extends AbstractGameAction
{

    public EmotionalTurmoilAction()
    {
        duration = Settings.ACTION_DUR_FAST;
    }

    public void update()
    {
        if(duration == Settings.ACTION_DUR_FAST)
        {
            if(AbstractDungeon.player.stance.ID.equals("Calm"))
                addToBot(new ChangeStanceAction("Wrath"));
            else
            if(AbstractDungeon.player.stance.ID.equals("Wrath"))
                addToBot(new ChangeStanceAction("Calm"));
            if(Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        tickDuration();
    }
}
