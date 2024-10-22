// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EvokeAllOrbsAction.java

package com.megacrit.cardcrawl.actions.defect;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;

// Referenced classes of package com.megacrit.cardcrawl.actions.defect:
//            EvokeOrbAction

public class EvokeAllOrbsAction extends AbstractGameAction
{

    public EvokeAllOrbsAction()
    {
        actionType = com.megacrit.cardcrawl.actions.AbstractGameAction.ActionType.DAMAGE;
    }

    public void update()
    {
        for(int i = 0; i < AbstractDungeon.player.orbs.size(); i++)
            addToTop(new EvokeOrbAction(1));

        isDone = true;
    }
}
