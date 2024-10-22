// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterRoomBoss.java

package com.megacrit.cardcrawl.rooms;

import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            MonsterRoom

public class MonsterRoomBoss extends MonsterRoom
{

    public MonsterRoomBoss()
    {
        mapSymbol = "B";
    }

    public void onPlayerEntry()
    {
        monsters = CardCrawlGame.dungeon.getBoss();
        logger.info((new StringBuilder()).append("BOSSES: ").append(AbstractDungeon.bossList.size()).toString());
        CardCrawlGame.metricData.path_taken.add("BOSS");
        CardCrawlGame.music.silenceBGM();
        AbstractDungeon.bossList.remove(0);
        if(monsters != null)
            monsters.init();
        waitTimer = 0.1F;
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll)
    {
        return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/rooms/MonsterRoomBoss.getName());

}
