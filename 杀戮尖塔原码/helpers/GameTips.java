// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GameTips.java

package com.megacrit.cardcrawl.helpers;

import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import java.util.ArrayList;
import java.util.Collections;

public class GameTips
{

    public GameTips()
    {
        tips = new ArrayList();
        initialize();
    }

    public void initialize()
    {
        Collections.addAll(tips, tutorialStrings.TEXT);
        if(!Settings.isConsoleBuild)
            Collections.addAll(tips, CardCrawlGame.languagePack.getTutorialString("PC Tips").TEXT);
        Collections.shuffle(tips);
    }

    public String getTip()
    {
        String retVal = (String)tips.remove(MathUtils.random(tips.size() - 1));
        if(tips.isEmpty())
            initialize();
        return retVal;
    }

    public String getPotionTip()
    {
        return LABEL[0];
    }

    private static final TutorialStrings tutorialStrings;
    public static final String LABEL[];
    private ArrayList tips;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Random Tips");
        LABEL = tutorialStrings.LABEL;
    }
}
