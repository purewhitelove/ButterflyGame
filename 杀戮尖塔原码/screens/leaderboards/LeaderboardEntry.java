// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   LeaderboardEntry.java

package com.megacrit.cardcrawl.screens.leaderboards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.screens.stats.CharStat;

// Referenced classes of package com.megacrit.cardcrawl.screens.leaderboards:
//            LeaderboardScreen

public class LeaderboardEntry
{

    public LeaderboardEntry(int rank, String name, int score, boolean isTime, boolean isYou)
    {
        this.isTime = false;
        color = Settings.CREAM_COLOR.cpy();
        this.rank = rank;
        if(name.length() > 18)
            this.name = (new StringBuilder()).append(name.substring(0, 18)).append("...").toString();
        else
            this.name = name;
        this.score = score;
        this.isTime = isTime;
        if(isYou)
            color = Settings.GREEN_TEXT_COLOR.cpy();
    }

    public void render(SpriteBatch sb, int index)
    {
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, Integer.toString(rank), LeaderboardScreen.RANK_X, (float)index * LINE_SPACING + START_Y, color);
        FontHelper.renderFontLeftTopAligned(sb, FontHelper.leaderboardFont, name, LeaderboardScreen.NAME_X, (float)index * LINE_SPACING + START_Y, color);
        if(isTime)
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, CharStat.formatHMSM(score), LeaderboardScreen.SCORE_X, (float)index * LINE_SPACING + START_Y, color);
        else
            FontHelper.renderFontLeftTopAligned(sb, FontHelper.panelNameFont, Integer.toString(score), LeaderboardScreen.SCORE_X, (float)index * LINE_SPACING + START_Y, color);
    }

    public int rank;
    public int score;
    public String name;
    public boolean isTime;
    private Color color;
    private static final float START_Y;
    private static final float LINE_SPACING;

    static 
    {
        START_Y = 800F * Settings.scale;
        LINE_SPACING = -32F * Settings.scale;
    }
}
