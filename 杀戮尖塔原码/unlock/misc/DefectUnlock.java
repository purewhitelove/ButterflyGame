// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DefectUnlock.java

package com.megacrit.cardcrawl.unlock.misc;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.unlock.AbstractUnlock;

public class DefectUnlock extends AbstractUnlock
{

    public DefectUnlock()
    {
        type = com.megacrit.cardcrawl.unlock.AbstractUnlock.UnlockType.CHARACTER;
        key = "Defect";
        title = "Defect";
    }

    public void onUnlockScreenOpen()
    {
        player = CardCrawlGame.characterManager.getCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT);
        player.drawX = (float)Settings.WIDTH / 2.0F - 20F * Settings.scale;
        player.drawY = (float)Settings.HEIGHT / 2.0F - 118F * Settings.scale;
    }

    public static final String KEY = "Defect";
}
