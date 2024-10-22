// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   ChannelAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import java.util.ArrayList;
import java.util.Iterator;

public class ChannelAction extends AbstractGameAction
{

    public ChannelAction(AbstractOrb newOrbType)
    {
        this(newOrbType, true);
    }

    public ChannelAction(AbstractOrb newOrbType, boolean autoEvoke)
    {
        this.autoEvoke = false;
        duration = Settings.ACTION_DUR_FAST;
        orbType = newOrbType;
        this.autoEvoke = autoEvoke;
    }

    public void update()
    {
label0:
        {
label1:
            {
                if(duration != Settings.ACTION_DUR_FAST)
                    break label0;
                if(autoEvoke)
                {
                    AbstractDungeon.player.channelOrb(orbType);
                    break label1;
                }
                Iterator iterator = AbstractDungeon.player.orbs.iterator();
                AbstractOrb o;
                do
                {
                    if(!iterator.hasNext())
                        break label1;
                    o = (AbstractOrb)iterator.next();
                } while(!(o instanceof EmptyOrbSlot));
                AbstractDungeon.player.channelOrb(orbType);
            }
            if(Settings.FAST_MODE)
            {
                isDone = true;
                return;
            }
        }
        tickDuration();
    }

    private AbstractOrb orbType;
    private boolean autoEvoke;
}
