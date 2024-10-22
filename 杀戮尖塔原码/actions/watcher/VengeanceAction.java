// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   VengeanceAction.java

package com.megacrit.cardcrawl.actions.watcher;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.stances.WrathStance;

// Referenced classes of package com.megacrit.cardcrawl.actions.watcher:
//            ChangeStanceAction

public class VengeanceAction extends AbstractGameAction
{

    public VengeanceAction()
    {
    }

    public void update()
    {
        if(GameActionManager.playerHpLastTurn > AbstractDungeon.player.currentHealth)
            addToBot(new ChangeStanceAction("Wrath"));
        isDone = true;
    }
}
