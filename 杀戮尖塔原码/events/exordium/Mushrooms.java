// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Mushrooms.java

package com.megacrit.cardcrawl.events.exordium;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.curses.Parasite;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.AbstractEvent;
import com.megacrit.cardcrawl.events.RoomEventDialog;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.EventStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.OddMushroom;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import java.io.PrintStream;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Mushrooms extends AbstractEvent
{

    public Mushrooms()
    {
        fgImg = ImageMaster.loadImage("images/events/fgShrooms.png");
        bgImg = ImageMaster.loadImage("images/events/bgShrooms.png");
        screenNum = 0;
        body = DESCRIPTIONS[2];
        roomEventText.addDialogOption(OPTIONS[0]);
        int temp = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
        roomEventText.addDialogOption((new StringBuilder()).append(OPTIONS[1]).append(temp).append(OPTIONS[2]).toString(), CardLibrary.getCopy("Parasite"));
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT;
        hasDialog = true;
        hasFocus = true;
    }

    public void update()
    {
        super.update();
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
    }

    protected void buttonEffect(int buttonPressed)
    {
        switch(buttonPressed)
        {
        case 0: // '\0'
            if(screenNum == 0)
            {
                AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("The Mushroom Lair");
                roomEventText.updateBodyText(FIGHT_MSG);
                roomEventText.updateDialogOption(0, OPTIONS[3]);
                roomEventText.removeDialogOption(1);
                AbstractEvent.logMetric("Mushrooms", "Fought Mushrooms");
                screenNum += 2;
            } else
            if(screenNum == 1)
                openMap();
            else
            if(screenNum == 2)
            {
                if(Settings.isDailyRun)
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(25));
                else
                    AbstractDungeon.getCurrRoom().addGoldToRewards(AbstractDungeon.miscRng.random(20, 30));
                if(AbstractDungeon.player.hasRelic("Odd Mushroom"))
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new Circlet());
                else
                    AbstractDungeon.getCurrRoom().addRelicToRewards(new OddMushroom());
                enterCombat();
                AbstractDungeon.lastCombatMetricKey = "The Mushroom Lair";
            }
            return;

        case 1: // '\001'
            com.megacrit.cardcrawl.cards.AbstractCard curse = new Parasite();
            int healAmt = (int)((float)AbstractDungeon.player.maxHealth * 0.25F);
            AbstractEvent.logMetricObtainCardAndHeal("Mushrooms", "Healed and dodged fight", curse, healAmt);
            AbstractDungeon.player.heal(healAmt);
            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(curse, (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            roomEventText.updateBodyText(HEAL_MSG);
            roomEventText.updateDialogOption(0, OPTIONS[4]);
            roomEventText.removeDialogOption(1);
            screenNum = 1;
            return;
        }
        logger.info((new StringBuilder()).append("ERROR: case ").append(buttonPressed).append(" should never be called").toString());
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(bgImg, 0.0F, -10F, Settings.WIDTH, 1080F * Settings.scale);
        sb.draw(fgImg, 0.0F, -20F * Settings.scale, Settings.WIDTH, 1080F * Settings.scale);
    }

    public void dispose()
    {
        super.dispose();
        System.out.println("DISPOSING MUSHROOM ASSETS>");
        if(bgImg != null)
        {
            bgImg.dispose();
            bgImg = null;
        }
        if(fgImg != null)
        {
            fgImg.dispose();
            fgImg = null;
        }
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/exordium/Mushrooms.getName());
    public static final String ID = "Mushrooms";
    private static final EventStrings eventStrings;
    public static final String NAME;
    public static final String DESCRIPTIONS[];
    public static final String OPTIONS[];
    public static final String ENC_NAME = "The Mushroom Lair";
    private Texture fgImg;
    private Texture bgImg;
    private static final float HEAL_AMT = 0.25F;
    private static final String HEAL_MSG;
    private static final String FIGHT_MSG;
    private int screenNum;

    static 
    {
        eventStrings = CardCrawlGame.languagePack.getEventString("Mushrooms");
        NAME = eventStrings.NAME;
        DESCRIPTIONS = eventStrings.DESCRIPTIONS;
        OPTIONS = eventStrings.OPTIONS;
        HEAL_MSG = DESCRIPTIONS[0];
        FIGHT_MSG = DESCRIPTIONS[1];
    }
}
