// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractEvent.java

package com.megacrit.cardcrawl.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.screens.DungeonMapScreen;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.events:
//            RoomEventDialog, GenericEventDialog

public abstract class AbstractEvent
    implements Disposable
{
    public static final class EventType extends Enum
    {

        public static EventType[] values()
        {
            return (EventType[])$VALUES.clone();
        }

        public static EventType valueOf(String name)
        {
            return (EventType)Enum.valueOf(com/megacrit/cardcrawl/events/AbstractEvent$EventType, name);
        }

        public static final EventType TEXT;
        public static final EventType IMAGE;
        public static final EventType ROOM;
        private static final EventType $VALUES[];

        static 
        {
            TEXT = new EventType("TEXT", 0);
            IMAGE = new EventType("IMAGE", 1);
            ROOM = new EventType("ROOM", 2);
            $VALUES = (new EventType[] {
                TEXT, IMAGE, ROOM
            });
        }

        private EventType(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractEvent()
    {
        img = null;
        roomEventText = new RoomEventDialog();
        imageEventText = new GenericEventDialog();
        imgColor = Color.WHITE.cpy();
        hb = null;
        panelAlpha = 0.0F;
        hideAlpha = false;
        hasFocus = false;
        body = null;
        waitTimer = 1.5F;
        waitForInput = false;
        hasDialog = false;
        screenNum = 0;
        combatTime = false;
        noCardsInRewards = false;
        optionsSelected = new ArrayList();
        type = EventType.ROOM;
        if(Settings.FAST_MODE)
            waitTimer = 0.1F;
    }

    protected void initializeImage(String imgUrl, float x, float y)
    {
        img = ImageMaster.loadImage(imgUrl);
        drawX = x;
        drawY = y;
        imgWidth = (float)img.getWidth() * Settings.xScale;
        imgHeight = (float)img.getHeight() * Settings.scale;
    }

    public void onEnterRoom()
    {
    }

    public void enterCombat()
    {
        roomEventText.clear();
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.getCurrRoom().monsters.init();
        AbstractRoom.waitTimer = 0.1F;
        AbstractDungeon.player.preBattlePrep();
        hasFocus = false;
        roomEventText.hide();
    }

    protected abstract void buttonEffect(int i);

    public void updateDialog()
    {
        imageEventText.update();
        roomEventText.update();
    }

    public void update()
    {
        if(waitTimer > 0.0F)
        {
            waitTimer -= Gdx.graphics.getDeltaTime();
            if(waitTimer < 0.0F && hasDialog)
            {
                roomEventText.show(body);
                waitTimer = 0.0F;
            }
        } else
        if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !hideAlpha)
            panelAlpha = MathHelper.fadeLerpSnap(panelAlpha, 0.66F);
        else
            panelAlpha = MathHelper.fadeLerpSnap(panelAlpha, 0.0F);
        if(!RoomEventDialog.waitForInput)
            buttonEffect(roomEventText.getSelectedOption());
    }

    public void logInput(int buttonPressed)
    {
        optionsSelected.add(Integer.valueOf(buttonPressed));
    }

    protected void openMap()
    {
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMPLETE;
        AbstractDungeon.dungeonMapScreen.open(false);
    }

    public void render(SpriteBatch sb)
    {
        if(img != null)
        {
            sb.setColor(imgColor);
            sb.draw(img, drawX, drawY, imgWidth, imgHeight);
        }
        if(hb != null)
        {
            hb.render(sb);
            if(img != null && hb.hovered)
            {
                sb.setBlendFunction(770, 1);
                sb.setColor(new Color(1.0F, 1.0F, 1.0F, 0.3F));
                sb.draw(img, drawX, drawY, imgWidth, imgHeight);
                sb.setBlendFunction(770, 771);
            }
        }
    }

    public void renderText(SpriteBatch sb)
    {
        roomEventText.render(sb);
        imageEventText.render(sb);
    }

    public void renderRoomEventPanel(SpriteBatch sb)
    {
        sb.setColor(new Color(0.0F, 0.0F, 0.0F, panelAlpha));
        sb.draw(ImageMaster.EVENT_ROOM_PANEL, 0.0F, (float)Settings.HEIGHT - 475F * Settings.scale, Settings.WIDTH, 300F * Settings.scale);
    }

    public void showProceedScreen(String bodyText)
    {
        roomEventText.updateBodyText(bodyText);
        roomEventText.updateDialogOption(0, "[ #bProceed ]");
        roomEventText.clearRemainingOptions();
        screenNum = 99;
    }

    public void renderAboveTopPanel(SpriteBatch spritebatch)
    {
    }

    public void reopen()
    {
    }

    public void postCombatLoad()
    {
        AbstractDungeon.getCurrRoom().phase = com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
        AbstractDungeon.getCurrRoom().isBattleOver = true;
        AbstractDungeon.getCurrRoom().monsters = MonsterHelper.getEncounter("Colosseum Nobs");
        hasFocus = false;
        GenericEventDialog.hide();
        AbstractDungeon.rs = com.megacrit.cardcrawl.dungeons.AbstractDungeon.RenderScene.NORMAL;
    }

    public static void logMetric(String eventName, String playerChoice, List cardsObtained, List cardsRemoved, List cardsTransformed, List cardsUpgraded, List relicsObtained, List potionsObtained, 
            List relicsLost, int damageTaken, int damageHealed, int hpLoss, int hpGain, int goldGain, int goldLoss)
    {
        HashMap choice = new HashMap();
        choice.put("event_name", eventName);
        choice.put("player_choice", playerChoice);
        choice.put("floor", Integer.valueOf(AbstractDungeon.floorNum));
        choice.put("cards_obtained", cardsObtained);
        choice.put("cards_removed", cardsRemoved);
        choice.put("cards_transformed", cardsTransformed);
        choice.put("cards_upgraded", cardsUpgraded);
        choice.put("relics_obtained", relicsObtained);
        choice.put("potions_obtained", potionsObtained);
        choice.put("relics_lost", relicsLost);
        choice.put("damage_taken", Integer.valueOf(damageTaken));
        choice.put("damage_healed", Integer.valueOf(damageHealed));
        choice.put("max_hp_loss", Integer.valueOf(hpLoss));
        choice.put("max_hp_gain", Integer.valueOf(hpGain));
        choice.put("gold_gain", Integer.valueOf(goldGain));
        choice.put("gold_loss", Integer.valueOf(goldLoss));
        CardCrawlGame.metricData.event_choices.add(choice);
    }

    public static void logMetricTransformCardsAtCost(String eventName, String playerChoice, List cardsTransformed, List cardsObtained, int cost)
    {
        logMetric(eventName, playerChoice, cardsObtained, null, cardsTransformed, null, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricRemoveCardsAtCost(String eventName, String playerChoice, List cardsRemoved, int cost)
    {
        logMetric(eventName, playerChoice, null, cardsRemoved, null, null, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricRemoveCards(String eventName, String playerChoice, List cardsRemoved)
    {
        logMetricRemoveCardsAtCost(eventName, playerChoice, cardsRemoved, 0);
    }

    public static void logMetricObtainCardsLoseMapHP(String eventName, String playerChoice, List cardsObtained, int maxHPLoss)
    {
        logMetric(eventName, playerChoice, cardsObtained, null, null, null, null, null, null, 0, 0, maxHPLoss, 0, 0, 0);
    }

    public static void logMetricObtainCardsLoseRelic(String eventName, String playerChoice, List cardsObtained, AbstractRelic relicLost)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicLost.relicId);
        logMetric(eventName, playerChoice, cardsObtained, null, null, null, null, null, tempList2, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainCards(String eventName, String playerChoice, List cardsObtained)
    {
        logMetricObtainCardsLoseMapHP(eventName, playerChoice, cardsObtained, 0);
    }

    public static void logMetricUpgradeCardsAtCost(String eventName, String playerChoice, List cardsUpgraded, int cost)
    {
        logMetric(eventName, playerChoice, null, null, null, cardsUpgraded, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricUpgradeCards(String eventName, String playerChoice, List cardsUpgraded)
    {
        logMetricUpgradeCardsAtCost(eventName, playerChoice, cardsUpgraded, 0);
    }

    public static void logMetricTransformCards(String eventName, String playerChoice, List cardsTransformed, List cardsObtained)
    {
        logMetricTransformCardsAtCost(eventName, playerChoice, cardsTransformed, cardsObtained, 0);
    }

    public static void logMetricGainGoldAndDamage(String eventName, String playerChoice, int gold, int damage)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, damage, 0, 0, 0, gold, 0);
    }

    public static void logMetricGainGoldAndRelic(String eventName, String playerChoice, AbstractRelic relicGained, int gold)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList2, null, null, 0, 0, 0, 0, gold, 0);
    }

    public static void logMetricGainGoldAndLoseRelic(String eventName, String playerChoice, AbstractRelic relicLost, int gold)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicLost.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, null, null, tempList2, 0, 0, 0, 0, gold, 0);
    }

    public static void logMetricGainGoldAndCard(String eventName, String playerChoice, AbstractCard cardGained, int gold)
    {
        List tempList2 = new ArrayList();
        tempList2.add(cardGained.cardID);
        logMetric(eventName, playerChoice, tempList2, null, null, null, null, null, null, 0, 0, 0, 0, gold, 0);
    }

    public static void logMetricObtainRelicAndLoseMaxHP(String eventName, String playerChoice, AbstractRelic relicGained, int hpLoss)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList2, null, null, 0, 0, hpLoss, 0, 0, 0);
    }

    public static void logMetricObtainRelicAndDamage(String eventName, String playerChoice, AbstractRelic relicGained, int damage)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList2, null, null, damage, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainRelicAtCost(String eventName, String playerChoice, AbstractRelic relicGained, int cost)
    {
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList2, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricGainAndLoseGold(String eventName, String playerChoice, int goldGain, int goldLoss)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, goldGain, goldLoss);
    }

    public static void logMetricGainGold(String eventName, String playerChoice, int gold)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, gold, 0);
    }

    public static void logMetricLoseGold(String eventName, String playerChoice, int gold)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, 0, 0, gold);
    }

    public static void logMetricTakeDamage(String eventName, String playerChoice, int damage)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, damage, 0, 0, 0, 0, 0);
    }

    public static void logMetricCardRemovalAtCost(String eventName, String playerChoice, AbstractCard cardRemoved, int cost)
    {
        List tempList = new ArrayList();
        tempList.add(cardRemoved.cardID);
        logMetric(eventName, playerChoice, null, tempList, null, null, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricCardRemovalAndDamage(String eventName, String playerChoice, AbstractCard cardRemoved, int damage)
    {
        List tempList = new ArrayList();
        tempList.add(cardRemoved.cardID);
        logMetric(eventName, playerChoice, null, tempList, null, null, null, null, null, damage, 0, 0, 0, 0, 0);
    }

    public static void logMetricCardRemovalHealMaxHPUp(String eventName, String playerChoice, AbstractCard cardRemoved, int heal, int maxUp)
    {
        List tempList = new ArrayList();
        tempList.add(cardRemoved.cardID);
        logMetric(eventName, playerChoice, null, tempList, null, null, null, null, null, 0, heal, 0, maxUp, 0, 0);
    }

    public static void logMetricCardRemovalAndHeal(String eventName, String playerChoice, AbstractCard cardRemoved, int heal)
    {
        logMetricCardRemovalHealMaxHPUp(eventName, playerChoice, cardRemoved, heal, 0);
    }

    public static void logMetricCardRemoval(String eventName, String playerChoice, AbstractCard cardRemoved)
    {
        logMetricCardRemovalAtCost(eventName, playerChoice, cardRemoved, 0);
    }

    public static void logMetricCardUpgradeAndRemovalAtCost(String eventName, String playerChoice, AbstractCard cardUpgraded, AbstractCard cardRemoved, int cost)
    {
        List tempList = new ArrayList();
        tempList.add(cardUpgraded.cardID);
        List tempList2 = new ArrayList();
        tempList2.add(cardRemoved.cardID);
        logMetric(eventName, playerChoice, null, tempList2, null, tempList, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricCardUpgradeAndRemoval(String eventName, String playerChoice, AbstractCard cardUpgraded, AbstractCard cardRemoved)
    {
        logMetricCardUpgradeAndRemovalAtCost(eventName, playerChoice, cardUpgraded, cardRemoved, 0);
    }

    public static void logMetricCardUpgradeAtCost(String eventName, String playerChoice, AbstractCard cardUpgraded, int cost)
    {
        List tempList = new ArrayList();
        tempList.add(cardUpgraded.cardID);
        logMetric(eventName, playerChoice, null, null, null, tempList, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricCardUpgrade(String eventName, String playerChoice, AbstractCard cardUpgraded)
    {
        logMetricCardUpgradeAtCost(eventName, playerChoice, cardUpgraded, 0);
    }

    public static void logMetricHealAtCost(String eventName, String playerChoice, int cost, int healAmount)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, healAmount, 0, 0, 0, cost);
    }

    public static void logMetricHealAndLoseMaxHP(String eventName, String playerChoice, int healAmount, int maxHPLoss)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, healAmount, maxHPLoss, 0, 0, 0);
    }

    public static void logMetricHeal(String eventName, String playerChoice, int healAmount)
    {
        logMetricHealAtCost(eventName, playerChoice, 0, healAmount);
    }

    public static void logMetric(String eventName, String playerChoice)
    {
        logMetricHeal(eventName, playerChoice, 0);
    }

    public static void logMetricIgnored(String eventName)
    {
        logMetric(eventName, "Ignored");
    }

    public static void logMetricMaxHPGain(String eventName, String playerChoice, int maxHPAmount)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, 0, maxHPAmount, 0, 0);
    }

    public static void logMetricMaxHPLoss(String eventName, String playerChoice, int hpLoss)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, 0, 0, hpLoss, 0, 0, 0);
    }

    public static void logMetricDamageAndMaxHPGain(String eventName, String playerChoice, int damage, int maxHPAmount)
    {
        logMetric(eventName, playerChoice, null, null, null, null, null, null, null, damage, 0, 0, maxHPAmount, 0, 0);
    }

    public static void logMetricObtainCardAndHeal(String eventName, String playerChoice, AbstractCard cardGained, int heal)
    {
        List tempList = new ArrayList();
        tempList.add(cardGained.cardID);
        logMetric(eventName, playerChoice, tempList, null, null, null, null, null, null, 0, heal, 0, 0, 0, 0);
    }

    public static void logMetricObtainCardAndDamage(String eventName, String playerChoice, AbstractCard cardGained, int damage)
    {
        List tempList = new ArrayList();
        tempList.add(cardGained.cardID);
        logMetric(eventName, playerChoice, tempList, null, null, null, null, null, null, damage, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainCardAndLoseCard(String eventName, String playerChoice, AbstractCard cardGained, AbstractCard cardLost)
    {
        List tempList = new ArrayList();
        tempList.add(cardGained.cardID);
        List tempList2 = new ArrayList();
        tempList2.add(cardLost.cardID);
        logMetric(eventName, playerChoice, tempList, tempList2, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainCardAndRelic(String eventName, String playerChoice, AbstractCard cardGained, AbstractRelic relicGained)
    {
        List tempList = new ArrayList();
        tempList.add(cardGained.cardID);
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, tempList, null, null, null, tempList2, null, null, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricRemoveCardAndObtainRelic(String eventName, String playerChoice, AbstractCard cardRemoved, AbstractRelic relicGained)
    {
        List tempList = new ArrayList();
        tempList.add(cardRemoved.cardID);
        List tempList2 = new ArrayList();
        tempList2.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, tempList, null, null, tempList2, null, null, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricTransformCardAtCost(String eventName, String playerChoice, AbstractCard cardTransformed, AbstractCard cardGained, int cost)
    {
        List tempList = new ArrayList();
        tempList.add(cardTransformed.cardID);
        List tempList2 = new ArrayList();
        tempList2.add(cardGained.cardID);
        logMetric(eventName, playerChoice, tempList2, null, tempList, null, null, null, null, 0, 0, 0, 0, 0, cost);
    }

    public static void logMetricTransformCard(String eventName, String playerChoice, AbstractCard cardTransformed, AbstractCard cardGained)
    {
        logMetricTransformCardAtCost(eventName, playerChoice, cardTransformed, cardGained, 0);
    }

    public static void logMetricRelicSwap(String eventName, String playerChoice, AbstractRelic relicGained, AbstractRelic relicLost)
    {
        List tempList = new ArrayList();
        tempList.add(relicGained.relicId);
        List tempList2 = new ArrayList();
        tempList2.add(relicLost.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList, null, tempList2, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainRelic(String eventName, String playerChoice, AbstractRelic relicGained)
    {
        List tempList = new ArrayList();
        tempList.add(relicGained.relicId);
        logMetric(eventName, playerChoice, null, null, null, null, tempList, null, null, 0, 0, 0, 0, 0, 0);
    }

    public static void logMetricObtainCard(String eventName, String playerChoice, AbstractCard cardGained)
    {
        List tempList = new ArrayList();
        tempList.add(cardGained.cardID);
        logMetric(eventName, playerChoice, tempList, null, null, null, null, null, null, 0, 0, 0, 0, 0, 0);
    }

    public HashMap getLocStrings()
    {
        HashMap data = new HashMap();
        data.put("name", NAME);
        data.put("moves", DESCRIPTIONS);
        data.put("dialogs", OPTIONS);
        return data;
    }

    public void dispose()
    {
        if(img != null)
        {
            logger.info("Disposed event img asset");
            img.dispose();
            img = null;
        }
        imageEventText.clear();
        roomEventText.clear();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/events/AbstractEvent.getName());
    protected Texture img;
    public RoomEventDialog roomEventText;
    public GenericEventDialog imageEventText;
    protected float drawX;
    protected float drawY;
    protected float imgWidth;
    protected float imgHeight;
    protected Color imgColor;
    protected Hitbox hb;
    public float panelAlpha;
    public boolean hideAlpha;
    public boolean hasFocus;
    protected String body;
    public float waitTimer;
    protected boolean waitForInput;
    public boolean hasDialog;
    protected int screenNum;
    public static EventType type;
    public static String NAME;
    public static String DESCRIPTIONS[];
    public static String OPTIONS[];
    public boolean combatTime;
    public boolean noCardsInRewards;
    public ArrayList optionsSelected;

    static 
    {
        type = EventType.IMAGE;
    }
}
