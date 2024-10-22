// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterRoom.java

package com.megacrit.cardcrawl.rooms;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.daily.mods.Vintage;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.DiscardPileViewScreen;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom, MonsterRoomElite, MonsterRoomBoss

public class MonsterRoom extends AbstractRoom
{

    public MonsterRoom()
    {
        phase = AbstractRoom.RoomPhase.COMBAT;
        mapSymbol = "M";
        mapImg = ImageMaster.MAP_NODE_ENEMY;
        mapImgOutline = ImageMaster.MAP_NODE_ENEMY_OUTLINE;
        discardPileViewScreen = new DiscardPileViewScreen();
    }

    public void dropReward()
    {
        if(ModHelper.isModEnabled("Vintage") && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomElite) && !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss))
        {
            com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier = returnRandomRelicTier();
            addRelicToRewards(tier);
        }
    }

    private com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier returnRandomRelicTier()
    {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if(roll < 50)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON;
        if(roll > 85)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE;
        else
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON;
    }

    public void onPlayerEntry()
    {
        playBGM(null);
        if(monsters == null)
        {
            monsters = CardCrawlGame.dungeon.getMonsterForRoomCreation();
            monsters.init();
        }
        waitTimer = 0.1F;
    }

    public void setMonster(MonsterGroup m)
    {
        monsters = m;
    }

    public DiscardPileViewScreen discardPileViewScreen;
    public static final float COMBAT_WAIT_TIME = 0.1F;
}
