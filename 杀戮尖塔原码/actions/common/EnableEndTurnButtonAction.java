// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   EnableEndTurnButtonAction.java

package com.megacrit.cardcrawl.actions.common;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;

public class EnableEndTurnButtonAction extends AbstractGameAction
{

    public EnableEndTurnButtonAction()
    {
    }

    public void update()
    {
        AbstractDungeon.overlayMenu.endTurnButton.enable();
        isDone = true;
    }
}
