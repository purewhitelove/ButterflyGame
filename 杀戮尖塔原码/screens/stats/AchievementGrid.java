// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AchievementGrid.java

package com.megacrit.cardcrawl.screens.stats;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.localization.AchievementStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.screens.stats:
//            AchievementItem

public class AchievementGrid
{

    public AchievementGrid()
    {
        items = new ArrayList();
        items.add(new AchievementItem(NAMES[0], TEXT[0], "shrugItOff", "SHRUG_IT_OFF"));
        items.add(new AchievementItem(NAMES[1], TEXT[1], "purity", "PURITY"));
        items.add(new AchievementItem(NAMES[2], TEXT[2], "comeAtMe", "COME_AT_ME"));
        items.add(new AchievementItem(NAMES[3], TEXT[3], "thePact", "THE_PACT"));
        items.add(new AchievementItem(NAMES[4], TEXT[4], "adrenaline", "ADRENALINE"));
        items.add(new AchievementItem(NAMES[5], TEXT[5], "powerful", "POWERFUL"));
        items.add(new AchievementItem(NAMES[6], TEXT[6], "jaxxed", "JAXXED"));
        items.add(new AchievementItem(NAMES[7], TEXT[7], "impervious", "IMPERVIOUS"));
        items.add(new AchievementItem(NAMES[8], TEXT[8], "barricaded", "BARRICADED"));
        items.add(new AchievementItem(NAMES[9], TEXT[9], "catalyst", "CATALYST"));
        items.add(new AchievementItem(NAMES[10], TEXT[10], "plague", "PLAGUE"));
        items.add(new AchievementItem(NAMES[11], TEXT[11], "ninja", "NINJA"));
        items.add(new AchievementItem(NAMES[12], TEXT[12], "infinity", "INFINITY"));
        items.add(new AchievementItem(NAMES[35], TEXT[35], "focused", "FOCUSED"));
        items.add(new AchievementItem(NAMES[36], TEXT[36], "neon", "NEON"));
        items.add(new AchievementItem(NAMES[13], TEXT[13], "youAreNothing", "YOU_ARE_NOTHING"));
        items.add(new AchievementItem(NAMES[31], TEXT[31], "minimalist", "MINIMALIST"));
        items.add(new AchievementItem(NAMES[32], TEXT[32], "donut", "DONUT"));
        items.add(new AchievementItem(NAMES[14], TEXT[14], "perfect", "PERFECT"));
        items.add(new AchievementItem(NAMES[27], TEXT[27], "onerelic", "ONE_RELIC"));
        items.add(new AchievementItem(NAMES[28], TEXT[28], "speed", "SPEED_CLIMBER"));
        items.add(new AchievementItem(NAMES[34], TEXT[34], "commonSense", "COMMON_SENSE"));
        items.add(new AchievementItem(NAMES[38], TEXT[38], "luckyDay", "LUCKY_DAY"));
        items.add(new AchievementItem(NAMES[29], TEXT[29], "0", "ASCEND_0"));
        items.add(new AchievementItem(NAMES[30], TEXT[30], "10", "ASCEND_10"));
        items.add(new AchievementItem(NAMES[40], TEXT[40], "20", "ASCEND_20"));
        items.add(new AchievementItem(NAMES[39], TEXT[39], "transient", "TRANSIENT"));
        items.add(new AchievementItem(NAMES[15], TEXT[15], "guardian", "GUARDIAN", true));
        items.add(new AchievementItem(NAMES[16], TEXT[16], "ghostGuardian", "GHOST_GUARDIAN", true));
        items.add(new AchievementItem(NAMES[17], TEXT[17], "slimeBoss", "SLIME_BOSS", true));
        items.add(new AchievementItem(NAMES[18], TEXT[18], "automaton", "AUTOMATON", true));
        items.add(new AchievementItem(NAMES[19], TEXT[19], "collector", "COLLECTOR", true));
        items.add(new AchievementItem(NAMES[20], TEXT[20], "champ", "CHAMP", true));
        items.add(new AchievementItem(NAMES[21], TEXT[21], "awakenedOne", "CROW", true));
        items.add(new AchievementItem(NAMES[22], TEXT[22], "shapes", "SHAPES", true));
        items.add(new AchievementItem(NAMES[23], TEXT[23], "timeEater", "TIME_EATER", true));
        items.add(new AchievementItem(NAMES[24], TEXT[24], "ironclad", "RUBY"));
        items.add(new AchievementItem(NAMES[25], TEXT[25], "silent", "EMERALD"));
        items.add(new AchievementItem(NAMES[33], TEXT[33], "sapphire", "SAPPHIRE"));
        items.add(new AchievementItem(NAMES[46], TEXT[46], "amethyst", "AMETHYST"));
        items.add(new AchievementItem(NAMES[41], TEXT[41], "rubyPlus", "RUBY_PLUS", true));
        items.add(new AchievementItem(NAMES[42], TEXT[42], "emeraldPlus", "EMERALD_PLUS", true));
        items.add(new AchievementItem(NAMES[43], TEXT[43], "sapphirePlus", "SAPPHIRE_PLUS", true));
        items.add(new AchievementItem(NAMES[47], TEXT[47], "amethyst_plus", "AMETHYST_PLUS", true));
        items.add(new AchievementItem(NAMES[44], TEXT[44], "theEnding", "THE_ENDING", true));
        items.add(new AchievementItem(NAMES[45], TEXT[45], "eternal_one", "ETERNAL_ONE", true));
        if(UnlockTracker.allAchievementsExceptPlatinumUnlocked())
            UnlockTracker.unlockAchievement("ETERNAL_ONE");
    }

    public void update()
    {
        AchievementItem i;
        for(Iterator iterator = items.iterator(); iterator.hasNext(); i.update())
            i = (AchievementItem)iterator.next();

    }

    public void render(SpriteBatch sb, float renderY)
    {
        for(int i = 0; i < items.size(); i++)
            ((AchievementItem)items.get(i)).render(sb, 560F * Settings.scale + (float)(i % 5) * SPACING, (renderY - (float)(i / 5) * SPACING) + 680F * Settings.yScale);

    }

    public void refreshImg()
    {
        AchievementItem i;
        for(Iterator iterator = items.iterator(); iterator.hasNext(); i.reloadImg())
            i = (AchievementItem)iterator.next();

    }

    private static final AchievementStrings achievementStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public ArrayList items;
    private static final float SPACING;
    private static final int ITEMS_PER_ROW = 5;
    public static final String SHRUG_KEY = "SHRUG_IT_OFF";
    public static final String PURITY_KEY = "PURITY";
    public static final String COME_AT_ME_KEY = "COME_AT_ME";
    public static final String THE_PACT_KEY = "THE_PACT";
    public static final String ADRENALINE_KEY = "ADRENALINE";
    public static final String POWERFUL_KEY = "POWERFUL";
    public static final String JAXXED_KEY = "JAXXED";
    public static final String IMPERVIOUS_KEY = "IMPERVIOUS";
    public static final String BARRICADED_KEY = "BARRICADED";
    public static final String CATALYST_KEY = "CATALYST";
    public static final String PLAGUE_KEY = "PLAGUE";
    public static final String NINJA_KEY = "NINJA";
    public static final String INFINITY_KEY = "INFINITY";
    public static final String YOU_ARE_NOTHING_KEY = "YOU_ARE_NOTHING";
    public static final String PERFECT_KEY = "PERFECT";
    public static final String ONE_RELIC_KEY = "ONE_RELIC";
    public static final String SPEED_CLIMBER_KEY = "SPEED_CLIMBER";
    public static final String ASCEND_0_KEY = "ASCEND_0";
    public static final String ASCEND_10_KEY = "ASCEND_10";
    public static final String ASCEND_20_KEY = "ASCEND_20";
    public static final String MINMALIST_KEY = "MINIMALIST";
    public static final String DONUT_KEY = "DONUT";
    public static final String COMMON_SENSE_KEY = "COMMON_SENSE";
    public static final String FOCUSED_KEY = "FOCUSED";
    public static final String LUCKY_DAY_KEY = "LUCKY_DAY";
    public static final String NEON_KEY = "NEON";
    public static final String TRANSIENT_KEY = "TRANSIENT";
    public static final String GUARDIAN_KEY = "GUARDIAN";
    public static final String GHOST_GUARDIAN_KEY = "GHOST_GUARDIAN";
    public static final String SLIME_BOSS_KEY = "SLIME_BOSS";
    public static final String AUTOMATON_KEY = "AUTOMATON";
    public static final String COLLECTOR_KEY = "COLLECTOR";
    public static final String CHAMP_KEY = "CHAMP";
    public static final String CROW_KEY = "CROW";
    public static final String SHAPES_KEY = "SHAPES";
    public static final String TIME_EATER_KEY = "TIME_EATER";
    public static final String RUBY_KEY = "RUBY";
    public static final String EMERALD_KEY = "EMERALD";
    public static final String SAPPHIRE_KEY = "SAPPHIRE";
    public static final String AMETHYST_KEY = "AMETHYST";
    public static final String RUBY_PLUS_KEY = "RUBY_PLUS";
    public static final String EMERALD_PLUS_KEY = "EMERALD_PLUS";
    public static final String SAPPHIRE_PLUS_KEY = "SAPPHIRE_PLUS";
    public static final String AMETHYST_PLUS_KEY = "AMETHYST_PLUS";
    public static final String THE_ENDING_KEY = "THE_ENDING";
    public static final String ETERNAL_ONE_KEY = "ETERNAL_ONE";

    static 
    {
        achievementStrings = CardCrawlGame.languagePack.getAchievementString("AchievementGrid");
        NAMES = achievementStrings.NAMES;
        TEXT = achievementStrings.TEXT;
        SPACING = 200F * Settings.scale;
    }
}
