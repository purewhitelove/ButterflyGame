// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   MonsterRoomElite.java

package com.megacrit.cardcrawl.rooms;

import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.blights.MimicInfestation;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.BigGameHunter;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BlackStar;
import com.megacrit.cardcrawl.rewards.RewardItem;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            MonsterRoom

public class MonsterRoomElite extends MonsterRoom
{

    public MonsterRoomElite()
    {
        mapSymbol = "E";
        mapImg = ImageMaster.MAP_NODE_ELITE;
        mapImgOutline = ImageMaster.MAP_NODE_ELITE_OUTLINE;
        eliteTrigger = true;
        baseRareCardChance = 10;
        baseUncommonCardChance = 40;
    }

    public void applyEmeraldEliteBuff()
    {
        if(Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey)
            switch(AbstractDungeon.mapRng.random(0, 3))
            {
            default:
                break;

            case 0: // '\0'
                AbstractMonster m;
                for(Iterator iterator = monsters.monsters.iterator(); iterator.hasNext(); AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new StrengthPower(m, AbstractDungeon.actNum + 1), AbstractDungeon.actNum + 1)))
                    m = (AbstractMonster)iterator.next();

                break;

            case 1: // '\001'
                AbstractMonster m;
                for(Iterator iterator1 = monsters.monsters.iterator(); iterator1.hasNext(); AbstractDungeon.actionManager.addToBottom(new IncreaseMaxHpAction(m, 0.25F, true)))
                    m = (AbstractMonster)iterator1.next();

                break;

            case 2: // '\002'
                AbstractMonster m;
                for(Iterator iterator2 = monsters.monsters.iterator(); iterator2.hasNext(); AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new MetallicizePower(m, AbstractDungeon.actNum * 2 + 2), AbstractDungeon.actNum * 2 + 2)))
                    m = (AbstractMonster)iterator2.next();

                break;

            case 3: // '\003'
                AbstractMonster m;
                for(Iterator iterator3 = monsters.monsters.iterator(); iterator3.hasNext(); AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(m, m, new RegenerateMonsterPower(m, 1 + AbstractDungeon.actNum * 2), 1 + AbstractDungeon.actNum * 2)))
                    m = (AbstractMonster)iterator3.next();

                break;
            }
    }

    public void onPlayerEntry()
    {
        playBGM(null);
        if(monsters == null)
        {
            monsters = CardCrawlGame.dungeon.getEliteMonsterForRoomCreation();
            monsters.init();
        }
        waitTimer = 0.1F;
    }

    public void dropReward()
    {
        com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier = returnRandomRelicTier();
        if(Settings.isEndless && AbstractDungeon.player.hasBlight("MimicInfestation"))
        {
            AbstractDungeon.player.getBlight("MimicInfestation").flash();
        } else
        {
            addRelicToRewards(tier);
            if(AbstractDungeon.player.hasRelic("Black Star"))
                addNoncampRelicToRewards(returnRandomRelicTier());
            addEmeraldKey();
        }
    }

    private void addEmeraldKey()
    {
        if(Settings.isFinalActAvailable && !Settings.hasEmeraldKey && !rewards.isEmpty() && AbstractDungeon.getCurrMapNode().hasEmeraldKey)
            rewards.add(new RewardItem((RewardItem)rewards.get(rewards.size() - 1), com.megacrit.cardcrawl.rewards.RewardItem.RewardType.EMERALD_KEY));
    }

    private com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier returnRandomRelicTier()
    {
        int roll = AbstractDungeon.relicRng.random(0, 99);
        if(ModHelper.isModEnabled("Elite Swarm"))
            roll += 10;
        if(roll < 50)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.COMMON;
        if(roll > 82)
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.RARE;
        else
            return com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier.UNCOMMON;
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll)
    {
        if(ModHelper.isModEnabled("Elite Swarm"))
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
        else
            return super.getCardRarity(roll);
    }
}
