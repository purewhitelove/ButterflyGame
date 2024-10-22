// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractPlayer.java

package com.megacrit.cardcrawl.characters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.PolygonSpriteBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Bezier;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonMeshRenderer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.defect.AnimateOrbAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.EvokeOrbAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.blue.Strike_Blue;
import com.megacrit.cardcrawl.cards.blue.Zap;
import com.megacrit.cardcrawl.cards.curses.AscendersBane;
import com.megacrit.cardcrawl.cards.curses.CurseOfTheBell;
import com.megacrit.cardcrawl.cards.curses.Necronomicurse;
import com.megacrit.cardcrawl.cards.green.Defend_Green;
import com.megacrit.cardcrawl.cards.green.Strike_Green;
import com.megacrit.cardcrawl.cards.green.Survivor;
import com.megacrit.cardcrawl.cards.purple.Defend_Watcher;
import com.megacrit.cardcrawl.cards.purple.Eruption;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Corruption;
import com.megacrit.cardcrawl.cards.red.Defend_Red;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.GameCursor;
import com.megacrit.cardcrawl.core.OverlayMenu;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Chimera;
import com.megacrit.cardcrawl.daily.mods.ControlledChaos;
import com.megacrit.cardcrawl.daily.mods.CursedRun;
import com.megacrit.cardcrawl.daily.mods.Draft;
import com.megacrit.cardcrawl.daily.mods.Insanity;
import com.megacrit.cardcrawl.daily.mods.Lethality;
import com.megacrit.cardcrawl.daily.mods.SealedDeck;
import com.megacrit.cardcrawl.daily.mods.Shiny;
import com.megacrit.cardcrawl.daily.mods.Terminal;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.MathHelper;
import com.megacrit.cardcrawl.helpers.ModHelper;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.Prefs;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.TipTracker;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.controller.CInputHelper;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.TutorialStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.map.MapRoomNode;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Dark;
import com.megacrit.cardcrawl.orbs.EmptyOrbSlot;
import com.megacrit.cardcrawl.orbs.Plasma;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.potions.PotionSlot;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.CorruptionPower;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.SurroundedPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.relics.BottledFlame;
import com.megacrit.cardcrawl.relics.BottledLightning;
import com.megacrit.cardcrawl.relics.BottledTornado;
import com.megacrit.cardcrawl.relics.Circlet;
import com.megacrit.cardcrawl.relics.CursedKey;
import com.megacrit.cardcrawl.relics.DarkstonePeriapt;
import com.megacrit.cardcrawl.relics.DuVuDoll;
import com.megacrit.cardcrawl.relics.Ectoplasm;
import com.megacrit.cardcrawl.relics.FrozenEye;
import com.megacrit.cardcrawl.relics.GoldPlatedCables;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.relics.MarkOfTheBloom;
import com.megacrit.cardcrawl.relics.Necronomicon;
import com.megacrit.cardcrawl.relics.SlaversCollar;
import com.megacrit.cardcrawl.relics.deprecated.DEPRECATED_DarkCore;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.rooms.CampfireUI;
import com.megacrit.cardcrawl.rooms.MonsterRoom;
import com.megacrit.cardcrawl.rooms.RestRoom;
import com.megacrit.cardcrawl.rooms.ShopRoom;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.DeathScreen;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.screens.stats.CharStat;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.MultiPageFtue;
import com.megacrit.cardcrawl.ui.buttons.EndTurnButton;
import com.megacrit.cardcrawl.ui.buttons.PeekButton;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.BorderFlashEffect;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.megacrit.cardcrawl.vfx.cardManip.CardDisappearEffect;
import com.megacrit.cardcrawl.vfx.combat.BlockedWordEffect;
import com.megacrit.cardcrawl.vfx.combat.HbBlockBrokenEffect;
import com.megacrit.cardcrawl.vfx.combat.StrikeEffect;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class AbstractPlayer extends AbstractCreature
{
    public static final class PlayerClass extends Enum
    {

        public static PlayerClass[] values()
        {
            return (PlayerClass[])$VALUES.clone();
        }

        public static PlayerClass valueOf(String name)
        {
            return (PlayerClass)Enum.valueOf(com/megacrit/cardcrawl/characters/AbstractPlayer$PlayerClass, name);
        }

        public static final PlayerClass IRONCLAD;
        public static final PlayerClass THE_SILENT;
        public static final PlayerClass DEFECT;
        public static final PlayerClass WATCHER;
        private static final PlayerClass $VALUES[];

        static 
        {
            IRONCLAD = new PlayerClass("IRONCLAD", 0);
            THE_SILENT = new PlayerClass("THE_SILENT", 1);
            DEFECT = new PlayerClass("DEFECT", 2);
            WATCHER = new PlayerClass("WATCHER", 3);
            $VALUES = (new PlayerClass[] {
                IRONCLAD, THE_SILENT, DEFECT, WATCHER
            });
        }

        private PlayerClass(String s, int i)
        {
            super(s, i);
        }
    }

    private static final class RHoverType extends Enum
    {

        public static RHoverType[] values()
        {
            return (RHoverType[])$VALUES.clone();
        }

        public static RHoverType valueOf(String name)
        {
            return (RHoverType)Enum.valueOf(com/megacrit/cardcrawl/characters/AbstractPlayer$RHoverType, name);
        }

        public static final RHoverType RELIC;
        public static final RHoverType BLIGHT;
        private static final RHoverType $VALUES[];

        static 
        {
            RELIC = new RHoverType("RELIC", 0);
            BLIGHT = new RHoverType("BLIGHT", 1);
            $VALUES = (new RHoverType[] {
                RELIC, BLIGHT
            });
        }

        private RHoverType(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractPlayer(String name, PlayerClass setClass)
    {
        masterDeck = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.MASTER_DECK);
        drawPile = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.DRAW_PILE);
        hand = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.HAND);
        discardPile = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.DISCARD_PILE);
        exhaustPile = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.EXHAUST_PILE);
        limbo = new CardGroup(com.megacrit.cardcrawl.cards.CardGroup.CardGroupType.UNSPECIFIED);
        relics = new ArrayList();
        blights = new ArrayList();
        potionSlots = 3;
        potions = new ArrayList();
        isEndingTurn = false;
        viewingRelics = false;
        inspectMode = false;
        inspectHb = null;
        damagedThisCombat = 0;
        orbs = new ArrayList();
        stance = new NeutralStance();
        cardsPlayedThisTurn = 0;
        isHoveringCard = false;
        isHoveringDropZone = false;
        hoverStartLine = 0.0F;
        passedHesitationLine = false;
        hoveredCard = null;
        toHover = null;
        cardInUse = null;
        isDraggingCard = false;
        isUsingClickDragControl = false;
        clickDragTimer = 0.0F;
        inSingleTargetMode = false;
        hoveredMonster = null;
        hoverEnemyWaitTimer = 0.0F;
        isInKeyboardMode = false;
        skipMouseModeOnce = false;
        keyboardCardIndex = -1;
        touchscreenInspectCount = 0;
        arrowScaleTimer = 0.0F;
        endTurnQueued = false;
        points = new Vector2[20];
        controlPoint = new Vector2();
        arrowTmp = new Vector2();
        startArrowVector = new Vector2();
        endArrowVector = new Vector2();
        renderCorpse = false;
        this.name = name;
        title = getTitle(setClass);
        drawX = (float)Settings.WIDTH * 0.25F;
        drawY = AbstractDungeon.floorY;
        chosenClass = setClass;
        isPlayer = true;
        initializeStarterRelics(setClass);
        loadPrefs();
        if(AbstractDungeon.ascensionLevel >= 11)
            potionSlots--;
        potions.clear();
        for(int i = 0; i < potionSlots; i++)
            potions.add(new PotionSlot(i));

        for(int i = 0; i < points.length; i++)
            points[i] = new Vector2();

    }

    public abstract String getPortraitImageName();

    public abstract ArrayList getStartingDeck();

    public abstract ArrayList getStartingRelics();

    public abstract CharSelectInfo getLoadout();

    public abstract String getTitle(PlayerClass playerclass);

    public abstract com.megacrit.cardcrawl.cards.AbstractCard.CardColor getCardColor();

    public abstract Color getCardRenderColor();

    public abstract String getAchievementKey();

    public abstract ArrayList getCardPool(ArrayList arraylist);

    public abstract AbstractCard getStartCardForEvent();

    public abstract Color getCardTrailColor();

    public abstract String getLeaderboardCharacterName();

    public abstract Texture getEnergyImage();

    public abstract int getAscensionMaxHPLoss();

    public abstract BitmapFont getEnergyNumFont();

    public abstract void renderOrb(SpriteBatch spritebatch, boolean flag, float f, float f1);

    public abstract void updateOrb(int i);

    public abstract Prefs getPrefs();

    public abstract void loadPrefs();

    public abstract CharStat getCharStat();

    public abstract int getUnlockedCardCount();

    public abstract int getSeenCardCount();

    public abstract int getCardCount();

    public abstract boolean saveFileExists();

    public abstract String getWinStreakKey();

    public abstract String getLeaderboardWinStreakKey();

    public abstract void renderStatScreen(SpriteBatch spritebatch, float f, float f1);

    public abstract void doCharSelectScreenSelectEffect();

    public abstract String getCustomModeCharacterButtonSoundKey();

    public abstract Texture getCustomModeCharacterButtonImage();

    public abstract CharacterStrings getCharacterString();

    public abstract String getLocalizedCharacterName();

    public abstract void refreshCharStat();

    public abstract AbstractPlayer newInstance();

    public abstract com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getOrb();

    public abstract String getSpireHeartText();

    public abstract Color getSlashAttackColor();

    public abstract com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect();

    public abstract String getVampireText();

    public String getSaveFilePath()
    {
        return SaveAndContinue.getPlayerSavePath(chosenClass);
    }

    public void dispose()
    {
        if(atlas != null)
            atlas.dispose();
        if(img != null)
            img.dispose();
        if(shoulderImg != null)
            shoulderImg.dispose();
        if(shoulder2Img != null)
            shoulder2Img.dispose();
        if(corpseImg != null)
            corpseImg.dispose();
    }

    public void adjustPotionPositions()
    {
        for(int i = 0; i < potions.size() - 1; i++)
            ((AbstractPotion)potions.get(i)).adjustPosition(i);

    }

    protected void initializeClass(String imgUrl, String shoulder2ImgUrl, String shouldImgUrl, String corpseImgUrl, CharSelectInfo info, float hb_x, float hb_y, 
            float hb_w, float hb_h, EnergyManager energy)
    {
        if(imgUrl != null)
            img = ImageMaster.loadImage(imgUrl);
        if(img != null)
            atlas = null;
        shoulderImg = ImageMaster.loadImage(shouldImgUrl);
        shoulder2Img = ImageMaster.loadImage(shoulder2ImgUrl);
        corpseImg = ImageMaster.loadImage(corpseImgUrl);
        if(Settings.isMobile)
            hb_w *= 1.17F;
        maxHealth = info.maxHp;
        startingMaxHP = maxHealth;
        currentHealth = info.currentHp;
        masterMaxOrbs = info.maxOrbs;
        this.energy = energy;
        masterHandSize = info.cardDraw;
        gameHandSize = masterHandSize;
        gold = info.gold;
        displayGold = gold;
        this.hb_h = hb_h * Settings.xScale;
        this.hb_w = hb_w * Settings.scale;
        this.hb_x = hb_x * Settings.scale;
        this.hb_y = hb_y * Settings.scale;
        hb = new Hitbox(this.hb_w, this.hb_h);
        healthHb = new Hitbox(hb.width, 72F * Settings.scale);
        refreshHitboxLocation();
    }

    public void initializeStarterDeck()
    {
        ArrayList cards = getStartingDeck();
        boolean addBaseCards = true;
        if(ModHelper.isModEnabled("Draft") || ModHelper.isModEnabled("Chimera") || ModHelper.isModEnabled("SealedDeck") || ModHelper.isModEnabled("Shiny") || ModHelper.isModEnabled("Insanity"))
            addBaseCards = false;
        if(ModHelper.isModEnabled("Chimera"))
        {
            masterDeck.addToTop(new Bash());
            masterDeck.addToTop(new Survivor());
            masterDeck.addToTop(new Zap());
            masterDeck.addToTop(new Eruption());
            masterDeck.addToTop(new Strike_Red());
            masterDeck.addToTop(new Strike_Green());
            masterDeck.addToTop(new Strike_Blue());
            masterDeck.addToTop(new Defend_Red());
            masterDeck.addToTop(new Defend_Green());
            masterDeck.addToTop(new Defend_Watcher());
        }
        if(ModHelper.isModEnabled("Insanity"))
        {
            for(int i = 0; i < 50; i++)
                masterDeck.addToTop(AbstractDungeon.returnRandomCard().makeCopy());

        }
        if(ModHelper.isModEnabled("Shiny"))
        {
            CardGroup group = AbstractDungeon.getEachRare();
            AbstractCard c;
            for(Iterator iterator2 = group.group.iterator(); iterator2.hasNext(); masterDeck.addToTop(c))
                c = (AbstractCard)iterator2.next();

        }
        if(addBaseCards)
        {
            String s;
            for(Iterator iterator = cards.iterator(); iterator.hasNext(); masterDeck.addToTop(CardLibrary.getCard(chosenClass, s).makeCopy()))
                s = (String)iterator.next();

        }
        AbstractCard c;
        for(Iterator iterator1 = masterDeck.group.iterator(); iterator1.hasNext(); UnlockTracker.markCardAsSeen(c.cardID))
            c = (AbstractCard)iterator1.next();

    }

    protected void initializeStarterRelics(PlayerClass chosenClass)
    {
        ArrayList relics = getStartingRelics();
        if(ModHelper.isModEnabled("Cursed Run"))
        {
            relics.clear();
            relics.add("Cursed Key");
            relics.add("Darkstone Periapt");
            relics.add("Du-Vu Doll");
        }
        if(ModHelper.isModEnabled("ControlledChaos"))
            relics.add("Frozen Eye");
        int index = 0;
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            String s = (String)iterator.next();
            RelicLibrary.getRelic(s).makeCopy().instantObtain(this, index, false);
            index++;
        }

        AbstractDungeon.relicsToRemoveOnStart.addAll(relics);
    }

    public void combatUpdate()
    {
        if(cardInUse != null)
            cardInUse.update();
        limbo.update();
        exhaustPile.update();
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.updateParticles())
            p = (AbstractPower)iterator.next();

        AbstractOrb o;
        for(Iterator iterator1 = orbs.iterator(); iterator1.hasNext(); o.update())
            o = (AbstractOrb)iterator1.next();

        stance.update();
    }

    public void update()
    {
        updateControllerInput();
        hb.update();
        updateHealthBar();
        updatePowers();
        healthHb.update();
        updateReticle();
        tint.update();
        if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.EVENT)
        {
            AbstractOrb o;
            for(Iterator iterator = orbs.iterator(); iterator.hasNext(); o.updateAnimation())
                o = (AbstractOrb)iterator.next();

        }
        updateEscapeAnimation();
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden)
            return;
        boolean anyHovered = false;
        boolean orbHovered = false;
        int orbIndex = 0;
        Iterator iterator = orbs.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o.hb.hovered)
            {
                orbHovered = true;
                break;
            }
            orbIndex++;
        } while(true);
        if(orbHovered && (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()))
        {
            CInputActionSet.up.unpress();
            CInputActionSet.altUp.unpress();
            inspectMode = false;
            inspectHb = null;
            orbHovered = false;
            viewingRelics = true;
            if(!blights.isEmpty())
                CInputHelper.setCursor(((AbstractBlight)blights.get(0)).hb);
            else
                CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
        } else
        if(orbHovered && (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()))
        {
            if(++orbIndex > orbs.size() - 1)
                orbIndex = 0;
            inspectHb = ((AbstractOrb)orbs.get(orbIndex)).hb;
            Gdx.input.setCursorPosition((int)((AbstractOrb)orbs.get(orbIndex)).hb.cX, Settings.HEIGHT - (int)((AbstractOrb)orbs.get(orbIndex)).hb.cY);
        } else
        if(orbHovered && (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()))
        {
            if(--orbIndex < 0)
                orbIndex = orbs.size() - 1;
            inspectHb = ((AbstractOrb)orbs.get(orbIndex)).hb;
            Gdx.input.setCursorPosition((int)((AbstractOrb)orbs.get(orbIndex)).hb.cX, Settings.HEIGHT - (int)((AbstractOrb)orbs.get(orbIndex)).hb.cY);
        } else
        if(inspectMode && (CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()))
        {
            if(orbHovered)
            {
                inspectHb = hb;
                CInputHelper.setCursor(inspectHb);
            } else
            {
                inspectMode = false;
                inspectHb = null;
                if(!hand.isEmpty() && !AbstractDungeon.actionManager.turnHasEnded)
                {
                    hoveredCard = (AbstractCard)hand.group.get(0);
                    hoverCardInHand(hoveredCard);
                    keyboardCardIndex = 0;
                }
            }
        } else
        if(!inspectMode && (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.isScreenUp)
        {
            if((float)Gdx.input.getX() < (float)Settings.WIDTH / 2.0F)
                inspectHb = hb;
            else
            if(!AbstractDungeon.getMonsters().monsters.isEmpty())
            {
                ArrayList hbs = new ArrayList();
                Iterator iterator1 = AbstractDungeon.getMonsters().monsters.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractMonster m = (AbstractMonster)iterator1.next();
                    if(!m.isDying && !m.isEscaping)
                        hbs.add(m.hb);
                } while(true);
                if(!hbs.isEmpty())
                    inspectHb = (Hitbox)hbs.get(0);
                else
                    inspectHb = hb;
            } else
            {
                inspectHb = hb;
            }
            CInputHelper.setCursor(inspectHb);
            inspectMode = true;
            releaseCard();
        } else
        if(inspectMode && (CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed()) && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            ArrayList hbs = new ArrayList();
            hbs.add(hb);
            Iterator iterator2 = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator2.next();
                if(!m.isDying && !m.isEscaping)
                    hbs.add(m.hb);
            } while(true);
            int index = 0;
            Iterator iterator4 = hbs.iterator();
            do
            {
                if(!iterator4.hasNext())
                    break;
                Hitbox h = (Hitbox)iterator4.next();
                h.update();
                if(h.hovered)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
            if(!anyHovered)
            {
                CInputHelper.setCursor((Hitbox)hbs.get(0));
                inspectHb = (Hitbox)hbs.get(0);
            } else
            {
                if(++index > hbs.size() - 1)
                    index = 0;
                CInputHelper.setCursor((Hitbox)hbs.get(index));
                inspectHb = (Hitbox)hbs.get(index);
            }
            inspectMode = true;
            releaseCard();
        } else
        if(inspectMode && (CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed()) && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            ArrayList hbs = new ArrayList();
            hbs.add(hb);
            Iterator iterator3 = AbstractDungeon.getMonsters().monsters.iterator();
            do
            {
                if(!iterator3.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator3.next();
                if(!m.isDying && !m.isEscaping)
                    hbs.add(m.hb);
            } while(true);
            int index = 0;
            Iterator iterator5 = hbs.iterator();
            do
            {
                if(!iterator5.hasNext())
                    break;
                Hitbox h = (Hitbox)iterator5.next();
                if(h.hovered)
                {
                    anyHovered = true;
                    break;
                }
                index++;
            } while(true);
            if(!anyHovered)
            {
                CInputHelper.setCursor((Hitbox)hbs.get(hbs.size() - 1));
                inspectHb = (Hitbox)hbs.get(hbs.size() - 1);
            } else
            {
                if(--index < 0)
                    index = hbs.size() - 1;
                CInputHelper.setCursor((Hitbox)hbs.get(index));
                inspectHb = (Hitbox)hbs.get(index);
            }
            inspectMode = true;
            releaseCard();
        } else
        if(inspectMode && (CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            CInputActionSet.up.unpress();
            CInputActionSet.altUp.unpress();
            if(!orbHovered && !orbs.isEmpty())
            {
                CInputHelper.setCursor(((AbstractOrb)orbs.get(0)).hb);
                inspectHb = ((AbstractOrb)orbs.get(0)).hb;
            } else
            {
                inspectMode = false;
                inspectHb = null;
                viewingRelics = true;
                if(!blights.isEmpty())
                    CInputHelper.setCursor(((AbstractBlight)blights.get(0)).hb);
                else
                    CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
            }
        }
    }

    public void updateViewRelicControls()
    {
        int index = 0;
        boolean anyHovered = false;
        RHoverType type = RHoverType.RELIC;
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = blights.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                AbstractBlight b = (AbstractBlight)iterator1.next();
                if(b.hb.hovered)
                {
                    anyHovered = true;
                    type = RHoverType.BLIGHT;
                    break;
                }
                index++;
            } while(true);
        }
        if(!anyHovered)
            CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
        else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            index--;
            if(type == RHoverType.RELIC)
            {
                if(index < AbstractRelic.relicPage * AbstractRelic.MAX_RELICS_PER_PAGE)
                {
                    AbstractRelic.relicPage--;
                    if(AbstractRelic.relicPage < 0)
                    {
                        if(relics.size() <= AbstractRelic.MAX_RELICS_PER_PAGE)
                        {
                            AbstractRelic.relicPage = 0;
                        } else
                        {
                            AbstractRelic.relicPage = relics.size() / AbstractRelic.MAX_RELICS_PER_PAGE;
                            AbstractDungeon.topPanel.adjustRelicHbs();
                        }
                        index = relics.size() - 1;
                    } else
                    {
                        index = (AbstractRelic.relicPage + 1) * AbstractRelic.MAX_RELICS_PER_PAGE - 1;
                        AbstractDungeon.topPanel.adjustRelicHbs();
                    }
                }
                CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
            } else
            {
                if(index < 0)
                    index = blights.size() - 1;
                CInputHelper.setCursor(((AbstractBlight)blights.get(index)).hb);
            }
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            index++;
            if(type == RHoverType.RELIC)
            {
                if(index > relics.size() - 1)
                    index = 0;
                else
                if(index > (AbstractRelic.relicPage + 1) * AbstractRelic.MAX_RELICS_PER_PAGE - 1)
                {
                    AbstractRelic.relicPage++;
                    AbstractDungeon.topPanel.adjustRelicHbs();
                    index = AbstractRelic.relicPage * AbstractRelic.MAX_RELICS_PER_PAGE;
                }
                CInputHelper.setCursor(((AbstractRelic)relics.get(index)).hb);
            } else
            {
                if(index > blights.size() - 1)
                    index = 0;
                CInputHelper.setCursor(((AbstractBlight)blights.get(index)).hb);
            }
        } else
        if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
        {
            CInputActionSet.up.unpress();
            if(type == RHoverType.RELIC)
            {
                viewingRelics = false;
                AbstractDungeon.topPanel.selectPotionMode = true;
                CInputHelper.setCursor(((AbstractPotion)potions.get(0)).hb);
            } else
            {
                CInputHelper.setCursor(((AbstractRelic)relics.get(0)).hb);
            }
        } else
        if(CInputActionSet.cancel.isJustPressed())
        {
            viewingRelics = false;
            Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
        } else
        if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
            if(type == RHoverType.RELIC)
            {
                if(blights.isEmpty())
                {
                    CInputActionSet.down.unpress();
                    CInputActionSet.altDown.unpress();
                    viewingRelics = false;
                    inspectMode = true;
                    if(!orbs.isEmpty())
                        inspectHb = ((AbstractOrb)orbs.get(0)).hb;
                    else
                        inspectHb = hb;
                    CInputHelper.setCursor(inspectHb);
                } else
                {
                    CInputHelper.setCursor(((AbstractBlight)blights.get(0)).hb);
                }
            } else
            {
                CInputActionSet.down.unpress();
                CInputActionSet.altDown.unpress();
                viewingRelics = false;
                inspectMode = true;
                if(!orbs.isEmpty())
                    inspectHb = ((AbstractOrb)orbs.get(0)).hb;
                else
                    inspectHb = hb;
                CInputHelper.setCursor(inspectHb);
            }
    }

    public void loseGold(int goldAmount)
    {
        if(AbstractDungeon.getCurrRoom() instanceof ShopRoom)
        {
            AbstractRelic r;
            for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.onSpendGold())
                r = (AbstractRelic)iterator.next();

        }
        if(!(AbstractDungeon.getCurrRoom() instanceof ShopRoom) && AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            CardCrawlGame.sound.play("EVENT_PURCHASE");
        if(goldAmount > 0)
        {
            gold -= goldAmount;
            if(gold < 0)
                gold = 0;
            AbstractRelic r;
            for(Iterator iterator1 = relics.iterator(); iterator1.hasNext(); r.onLoseGold())
                r = (AbstractRelic)iterator1.next();

        } else
        {
            logger.info("NEGATIVE MONEY???");
        }
    }

    public void gainGold(int amount)
    {
        if(hasRelic("Ectoplasm"))
        {
            getRelic("Ectoplasm").flash();
            return;
        }
        if(amount <= 0)
        {
            logger.info("NEGATIVE MONEY???");
        } else
        {
            CardCrawlGame.goldGained += amount;
            gold += amount;
            AbstractRelic r;
            for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.onGainGold())
                r = (AbstractRelic)iterator.next();

        }
    }

    public void playDeathAnimation()
    {
        img = corpseImg;
        renderCorpse = true;
    }

    public boolean isCursed()
    {
        boolean cursed = false;
        Iterator iterator = masterDeck.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.CURSE && c.cardID != "Necronomicurse" && c.cardID != "CurseOfTheBell" && c.cardID != "AscendersBane")
                cursed = true;
        } while(true);
        return cursed;
    }

    public void updateInput()
    {
        if(viewingRelics)
            return;
        if(hoverEnemyWaitTimer > 0.0F)
            hoverEnemyWaitTimer -= Gdx.graphics.getDeltaTime();
        if(inSingleTargetMode)
        {
            updateSingleTargetInput();
        } else
        {
            int y = InputHelper.mY;
            boolean hMonster = false;
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)iterator.next();
                m.hb.update();
                if(!m.hb.hovered || m.isDying || m.isEscaping || m.currentHealth <= 0)
                    continue;
                hMonster = true;
                break;
            } while(true);
            boolean tmp = isHoveringDropZone;
            if(!Settings.isTouchScreen)
                isHoveringDropZone = ((float)y > hoverStartLine || (float)y > 300F * Settings.scale) && (float)y < Settings.CARD_DROP_END_Y || hMonster || Settings.isControllerMode;
            else
                isHoveringDropZone = (float)y > 350F * Settings.scale && (float)y < Settings.CARD_DROP_END_Y || hMonster || Settings.isControllerMode;
            if(!tmp && isHoveringDropZone && isDraggingCard)
            {
                hoveredCard.flash(Color.SKY.cpy());
                if(hoveredCard.showEvokeValue)
                    if(hoveredCard.showEvokeOrbCount == 0)
                    {
                        AbstractOrb o;
                        for(Iterator iterator1 = orbs.iterator(); iterator1.hasNext(); o.showEvokeValue())
                            o = (AbstractOrb)iterator1.next();

                    } else
                    {
                        int tmpShowCount = hoveredCard.showEvokeOrbCount;
                        int emptyCount = 0;
                        Iterator iterator2 = orbs.iterator();
                        do
                        {
                            if(!iterator2.hasNext())
                                break;
                            AbstractOrb o = (AbstractOrb)iterator2.next();
                            if(o instanceof EmptyOrbSlot)
                                emptyCount++;
                        } while(true);
                        tmpShowCount -= emptyCount;
                        if(tmpShowCount > 0)
                        {
                            Iterator iterator3 = orbs.iterator();
                            do
                            {
                                if(!iterator3.hasNext())
                                    break;
                                AbstractOrb o = (AbstractOrb)iterator3.next();
                                o.showEvokeValue();
                            } while(--tmpShowCount > 0);
                        }
                    }
            }
            if(isDraggingCard && isHoveringDropZone && hoveredCard != null)
                passedHesitationLine = true;
            if(isDraggingCard && (float)y < 50F * Settings.scale && passedHesitationLine)
            {
                if(Settings.isTouchScreen)
                    InputHelper.moveCursorToNeutralPosition();
                releaseCard();
                CardCrawlGame.sound.play("UI_CLICK_1");
            }
            updateFullKeyboardCardSelection();
            if(isInKeyboardMode && !AbstractDungeon.topPanel.selectPotionMode && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.topPanel.potionUi.targetMode && !AbstractDungeon.isScreenUp)
            {
                if(toHover != null)
                {
                    releaseCard();
                    hoveredCard = toHover;
                    toHover = null;
                    isHoveringCard = true;
                    hoveredCard.current_y = HOVER_CARD_Y_POSITION;
                    hoveredCard.target_y = HOVER_CARD_Y_POSITION;
                    hoveredCard.setAngle(0.0F, true);
                    hand.hoverCardPush(hoveredCard);
                }
            } else
            if(hoveredCard == null && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NONE && !AbstractDungeon.topPanel.selectPotionMode)
            {
                isHoveringCard = false;
                if(toHover != null)
                {
                    hoveredCard = toHover;
                    toHover = null;
                } else
                {
                    hoveredCard = hand.getHoveredCard();
                }
                if(hoveredCard != null)
                {
                    isHoveringCard = true;
                    hoveredCard.current_y = HOVER_CARD_Y_POSITION;
                    hoveredCard.target_y = HOVER_CARD_Y_POSITION;
                    hoveredCard.setAngle(0.0F, true);
                    hand.hoverCardPush(hoveredCard);
                }
            }
            if(hoveredCard != null)
            {
                hoveredCard.drawScale = 1.0F;
                hoveredCard.targetDrawScale = 1.0F;
            }
            if(!isDraggingCard && hasRelic("Necronomicon"))
                getRelic("Necronomicon").stopPulse();
            if(!endTurnQueued)
            {
                if(!AbstractDungeon.actionManager.turnHasEnded && clickAndDragCards())
                    return;
                if(!isInKeyboardMode && isHoveringCard && hoveredCard != null && !hoveredCard.isHoveredInHand(1.0F))
                {
                    int i = 0;
                    do
                    {
                        if(i >= hand.group.size())
                            break;
                        if(hand.group.get(i) == hoveredCard && i != 0 && ((AbstractCard)hand.group.get(i - 1)).isHoveredInHand(1.0F))
                        {
                            toHover = (AbstractCard)hand.group.get(i - 1);
                            break;
                        }
                        i++;
                    } while(true);
                    releaseCard();
                }
                if(hoveredCard != null)
                    hoveredCard.updateHoverLogic();
            } else
            if(AbstractDungeon.actionManager.cardQueue.isEmpty() && !AbstractDungeon.actionManager.hasControl)
            {
                endTurnQueued = false;
                isEndingTurn = true;
            }
        }
    }

    private void updateSingleTargetInput()
    {
label0:
        {
            if(Settings.isTouchScreen && !Settings.isControllerMode && !isUsingClickDragControl && !InputHelper.isMouseDown)
                Gdx.input.setCursorPosition((int)MathUtils.lerp(Gdx.input.getX(), (float)Settings.WIDTH / 2.0F, Gdx.graphics.getDeltaTime() * 10F), (int)MathUtils.lerp(Gdx.input.getY(), (float)Settings.HEIGHT * 1.1F, Gdx.graphics.getDeltaTime() * 4F));
            if(isInKeyboardMode)
            {
                if(InputActionSet.releaseCard.isJustPressed() || CInputActionSet.cancel.isJustPressed())
                {
                    AbstractCard card = hoveredCard;
                    inSingleTargetMode = false;
                    hoveredMonster = null;
                    hoverCardInHand(card);
                } else
                {
                    updateTargetArrowWithKeyboard(false);
                }
                break label0;
            }
            hoveredMonster = null;
            Iterator iterator = AbstractDungeon.getCurrRoom().monsters.monsters.iterator();
            AbstractMonster m;
            do
            {
                if(!iterator.hasNext())
                    break label0;
                m = (AbstractMonster)iterator.next();
                m.hb.update();
            } while(!m.hb.hovered || m.isDying || m.isEscaping || m.currentHealth <= 0);
            hoveredMonster = m;
        }
        if(AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead() || InputHelper.justClickedRight || (float)InputHelper.mY < 50F * Settings.scale || (float)InputHelper.mY < hoverStartLine - 400F * Settings.scale)
        {
            if(Settings.isTouchScreen)
                InputHelper.moveCursorToNeutralPosition();
            releaseCard();
            CardCrawlGame.sound.play("UI_CLICK_2");
            isUsingClickDragControl = false;
            inSingleTargetMode = false;
            GameCursor.hidden = false;
            hoveredMonster = null;
            return;
        }
        AbstractCard cardFromHotkey = InputHelper.getCardSelectedByHotkey(hand);
        if(cardFromHotkey != null && !isCardQueued(cardFromHotkey))
        {
            boolean isSameCard = cardFromHotkey == hoveredCard;
            releaseCard();
            hoveredMonster = null;
            if(isSameCard)
            {
                GameCursor.hidden = false;
            } else
            {
                hoveredCard = cardFromHotkey;
                hoveredCard.setAngle(0.0F, false);
                isUsingClickDragControl = true;
                isDraggingCard = true;
            }
        }
        if(InputHelper.justClickedLeft || InputActionSet.confirm.isJustPressed() || CInputActionSet.select.isJustPressed())
        {
            InputHelper.justClickedLeft = false;
            if(hoveredMonster == null)
            {
                CardCrawlGame.sound.play("UI_CLICK_1");
                return;
            }
            if(hoveredCard.canUse(this, hoveredMonster))
            {
                playCard();
            } else
            {
                AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, hoveredCard.cantUseMessage, true));
                energyTip(hoveredCard);
                releaseCard();
            }
            isUsingClickDragControl = false;
            inSingleTargetMode = false;
            GameCursor.hidden = false;
            hoveredMonster = null;
            return;
        }
        if(!isUsingClickDragControl && InputHelper.justReleasedClickLeft && hoveredMonster != null)
        {
            if(hoveredCard.canUse(this, hoveredMonster))
            {
                playCard();
            } else
            {
                AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, hoveredCard.cantUseMessage, true));
                energyTip(hoveredCard);
                releaseCard();
            }
            inSingleTargetMode = false;
            GameCursor.hidden = false;
            hoveredMonster = null;
            return;
        } else
        {
            return;
        }
    }

    private boolean isCardQueued(AbstractCard card)
    {
        for(Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator(); iterator.hasNext();)
        {
            CardQueueItem item = (CardQueueItem)iterator.next();
            if(item.card == card)
                return true;
        }

        return false;
    }

    private void energyTip(AbstractCard cardToCheck)
    {
        int availableEnergy = EnergyPanel.totalCount;
        if(cardToCheck.cost > availableEnergy && !((Boolean)TipTracker.tips.get("ENERGY_USE_TIP")).booleanValue())
        {
            TipTracker.energyUseCounter++;
            if(TipTracker.energyUseCounter >= 2)
            {
                AbstractDungeon.ftue = new FtueTip(LABEL[1], MSG[1], 330F * Settings.scale, 400F * Settings.scale, com.megacrit.cardcrawl.ui.FtueTip.TipType.ENERGY);
                TipTracker.neverShowAgain("ENERGY_USE_TIP");
            }
        }
    }

    private boolean updateFullKeyboardCardSelection()
    {
        if(Settings.isControllerMode || InputActionSet.left.isJustPressed() || InputActionSet.right.isJustPressed() || InputActionSet.confirm.isJustPressed())
        {
            isInKeyboardMode = true;
            skipMouseModeOnce = true;
            GameCursor.hidden = true;
        }
        if(isInKeyboardMode && InputHelper.didMoveMouse())
            if(skipMouseModeOnce)
            {
                skipMouseModeOnce = false;
            } else
            {
                isInKeyboardMode = false;
                GameCursor.hidden = false;
            }
        if(!isInKeyboardMode || hand.isEmpty() || inspectMode)
            return false;
        if(keyboardCardIndex == -2)
        {
            if(InputActionSet.left.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                keyboardCardIndex = hand.size() - 1;
            else
            if(InputActionSet.right.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                keyboardCardIndex = 0;
            return false;
        }
        if(InputActionSet.left.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            keyboardCardIndex--;
        else
        if(InputActionSet.right.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            keyboardCardIndex++;
        keyboardCardIndex = (keyboardCardIndex + hand.size()) % hand.size();
        if(!AbstractDungeon.topPanel.selectPotionMode && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.topPanel.potionUi.targetMode)
        {
            AbstractCard card = (AbstractCard)hand.group.get(keyboardCardIndex);
            if(card != hoveredCard && Math.abs(card.current_x - card.target_x) < 400F * Settings.scale)
            {
                hoverCardInHand(card);
                return true;
            }
        }
        return false;
    }

    private void hoverCardInHand(AbstractCard card)
    {
        toHover = card;
        if(Settings.isControllerMode && AbstractDungeon.actionManager.turnHasEnded)
            toHover = null;
        if(card != null && !inspectMode)
            Gdx.input.setCursorPosition((int)card.hb.cX, (int)((float)Settings.HEIGHT - HOVER_CARD_Y_POSITION));
    }

    private void updateTargetArrowWithKeyboard(boolean autoTargetFirst)
    {
        int directionIndex = 0;
        if(autoTargetFirst)
            directionIndex++;
        if(InputActionSet.left.isJustPressed() || CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
            directionIndex--;
        if(InputActionSet.right.isJustPressed() || CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
            directionIndex++;
        if(directionIndex != 0)
        {
            ArrayList prefiltered = AbstractDungeon.getCurrRoom().monsters.monsters;
            ArrayList sortedMonsters = new ArrayList(AbstractDungeon.getCurrRoom().monsters.monsters);
            AbstractMonster newTarget = prefiltered.iterator();
            do
            {
                if(!newTarget.hasNext())
                    break;
                AbstractMonster mons = (AbstractMonster)newTarget.next();
                if(mons.isDying)
                    sortedMonsters.remove(mons);
            } while(true);
            sortedMonsters.sort(AbstractMonster.sortByHitbox);
            if(sortedMonsters.isEmpty())
                return;
            newTarget = sortedMonsters.iterator();
            do
            {
                if(!newTarget.hasNext())
                    break;
                AbstractMonster m = (AbstractMonster)newTarget.next();
                if(!m.hb.hovered)
                    continue;
                hoveredMonster = m;
                break;
            } while(true);
            newTarget = null;
            if(hoveredMonster == null)
            {
                if(directionIndex == 1)
                    newTarget = (AbstractMonster)sortedMonsters.get(0);
                else
                    newTarget = (AbstractMonster)sortedMonsters.get(sortedMonsters.size() - 1);
            } else
            {
                int currentTargetIndex = sortedMonsters.indexOf(hoveredMonster);
                int newTargetIndex = currentTargetIndex + directionIndex;
                newTargetIndex = (newTargetIndex + sortedMonsters.size()) % sortedMonsters.size();
                newTarget = (AbstractMonster)sortedMonsters.get(newTargetIndex);
            }
            if(newTarget != null)
            {
                Hitbox target = newTarget.hb;
                Gdx.input.setCursorPosition((int)target.cX, Settings.HEIGHT - (int)target.cY);
                hoveredMonster = newTarget;
                isUsingClickDragControl = true;
                isDraggingCard = true;
            }
            if(hoveredMonster.halfDead)
                hoveredMonster = null;
        }
    }

    private void renderCardHotKeyText(SpriteBatch sb)
    {
        int index = 0;
        for(Iterator iterator = hand.group.iterator(); iterator.hasNext();)
        {
            AbstractCard card = (AbstractCard)iterator.next();
            if(index < InputActionSet.selectCardActions.length)
            {
                float width = (AbstractCard.IMG_WIDTH * card.drawScale) / 2.0F;
                float height = (AbstractCard.IMG_HEIGHT * card.drawScale) / 2.0F;
                float topOfCard = card.current_y + height;
                float textSpacing = 50F * Settings.scale;
                float textY = topOfCard + textSpacing;
                float sin = (float)Math.sin((double)(card.angle / 180F) * 3.1415926535897931D);
                float xOffset = sin * width;
                FontHelper.renderFontCentered(sb, FontHelper.buttonLabelFont, InputActionSet.selectCardActions[index].getKeyString(), card.current_x - xOffset, textY, Settings.CREAM_COLOR);
            }
            index++;
        }

    }

    private boolean clickAndDragCards()
    {
        boolean simulateRightClickDrop = false;
        AbstractCard cardFromHotkey = InputHelper.getCardSelectedByHotkey(hand);
        if(cardFromHotkey != null && !isCardQueued(cardFromHotkey))
        {
            if(isDraggingCard)
            {
                simulateRightClickDrop = cardFromHotkey == hoveredCard;
                CardCrawlGame.sound.play("UI_CLICK_2");
                releaseCard();
            }
            if(!simulateRightClickDrop)
                manuallySelectCard(cardFromHotkey);
        }
        if(CInputActionSet.select.isJustPressed() && hoveredCard != null && !isCardQueued(hoveredCard) && !isDraggingCard)
        {
            manuallySelectCard(hoveredCard);
            if(hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY)
                updateTargetArrowWithKeyboard(true);
            else
                InputHelper.moveCursorToNeutralPosition();
            return true;
        }
        if(InputHelper.justClickedLeft && isHoveringCard && !isDraggingCard)
        {
            hoverStartLine = (float)InputHelper.mY + 140F * Settings.scale;
            InputHelper.justClickedLeft = false;
            if(hoveredCard != null)
            {
                CardCrawlGame.sound.play("CARD_OBTAIN");
                isDraggingCard = true;
                passedHesitationLine = false;
                hoveredCard.targetDrawScale = 0.7F;
                if(Settings.isTouchScreen && !Settings.isControllerMode && touchscreenInspectCount == 0)
                {
                    hoveredCard.current_y = AbstractCard.IMG_HEIGHT / 2.0F;
                    hoveredCard.target_y = AbstractCard.IMG_HEIGHT / 2.0F;
                    Gdx.input.setCursorPosition((int)hoveredCard.current_x, (int)((float)Settings.HEIGHT - AbstractCard.IMG_HEIGHT / 2.0F));
                    touchscreenInspectCount = 0;
                }
                return true;
            }
        }
        if(InputHelper.isMouseDown)
            clickDragTimer += Gdx.graphics.getDeltaTime();
        else
            clickDragTimer = 0.0F;
        if((InputHelper.justClickedLeft || InputActionSet.confirm.isJustPressed() || CInputActionSet.select.isJustPressed()) && isUsingClickDragControl)
        {
            if(InputHelper.justClickedRight || simulateRightClickDrop)
            {
                CardCrawlGame.sound.play("UI_CLICK_2");
                releaseCard();
                return true;
            }
            InputHelper.justClickedLeft = false;
            if(isHoveringDropZone && hoveredCard.canUse(this, null) && hoveredCard.target != com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY && hoveredCard.target != com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY)
            {
                playCard();
            } else
            {
                CardCrawlGame.sound.play("CARD_OBTAIN");
                releaseCard();
            }
            isUsingClickDragControl = false;
            return true;
        }
        if(isInKeyboardMode)
            if(InputActionSet.releaseCard.isJustPressed() || CInputActionSet.cancel.isJustPressed())
                hoverCardInHand(hoveredCard);
            else
            if((InputActionSet.confirm.isJustPressed() || CInputActionSet.select.isJustPressed()) && hoveredCard != null)
            {
                manuallySelectCard(hoveredCard);
                if(hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY)
                    updateTargetArrowWithKeyboard(true);
                else
                    Gdx.input.setCursorPosition(10, Settings.HEIGHT / 2);
            }
        if(isDraggingCard && (InputHelper.isMouseDown || isUsingClickDragControl))
        {
            if(InputHelper.justClickedRight || simulateRightClickDrop)
            {
                CardCrawlGame.sound.play("UI_CLICK_2");
                releaseCard();
                return true;
            }
            if(Settings.isTouchScreen && !Settings.isControllerMode)
            {
                hoveredCard.target_x = InputHelper.mX;
                hoveredCard.target_y = (float)InputHelper.mY + 270F * Settings.scale;
            } else
            {
                hoveredCard.target_x = InputHelper.mX;
                hoveredCard.target_y = InputHelper.mY;
            }
            if(!hoveredCard.hasEnoughEnergy() && isHoveringDropZone)
            {
                AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, hoveredCard.cantUseMessage, true));
                energyTip(hoveredCard);
                releaseCard();
                CardCrawlGame.sound.play("CARD_REJECT");
                return true;
            }
            if(isHoveringDropZone && (hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY || hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY))
            {
                inSingleTargetMode = true;
                arrowX = InputHelper.mX;
                arrowY = InputHelper.mY;
                GameCursor.hidden = true;
                hoveredCard.untip();
                hand.refreshHandLayout();
                if(Settings.isTouchScreen && !Settings.isControllerMode)
                {
                    hoveredCard.target_y = 260F * Settings.scale;
                    hoveredCard.target_x = (float)Settings.WIDTH / 2.0F;
                    hoveredCard.targetDrawScale = 1.0F;
                } else
                {
                    hoveredCard.target_y = (AbstractCard.IMG_HEIGHT * 0.75F) / 2.5F;
                    hoveredCard.target_x = (float)Settings.WIDTH / 2.0F;
                }
                isDraggingCard = false;
            }
            return true;
        }
        if(isDraggingCard && InputHelper.justReleasedClickLeft && (!Settings.isTouchScreen || Settings.isControllerMode))
        {
            if(isHoveringDropZone)
            {
                if(hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY || hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY)
                {
                    inSingleTargetMode = true;
                    arrowX = InputHelper.mX;
                    arrowY = InputHelper.mY;
                    GameCursor.hidden = true;
                    hoveredCard.untip();
                    hand.refreshHandLayout();
                    hoveredCard.target_y = (AbstractCard.IMG_HEIGHT * 0.75F) / 2.5F;
                    hoveredCard.target_x = (float)Settings.WIDTH / 2.0F;
                    isDraggingCard = false;
                    return true;
                }
                if(hoveredCard.canUse(this, null))
                {
                    playCard();
                    return true;
                } else
                {
                    AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, hoveredCard.cantUseMessage, true));
                    energyTip(hoveredCard);
                    releaseCard();
                    return true;
                }
            }
            if(clickDragTimer < 0.4F)
            {
                isUsingClickDragControl = true;
                return true;
            }
            if(AbstractDungeon.actionManager.currentAction == null)
            {
                releaseCard();
                CardCrawlGame.sound.play("CARD_OBTAIN");
                return true;
            }
        } else
        if(Settings.isTouchScreen && !Settings.isControllerMode && InputHelper.justReleasedClickLeft && hoveredCard != null)
        {
            touchscreenInspectCount++;
            if(isHoveringDropZone && hoveredCard.hasEnoughEnergy() && hoveredCard.canUse(this, null))
            {
                playCard();
                return true;
            }
            if(touchscreenInspectCount > 1)
            {
                AbstractCard newHoveredCard = null;
                Iterator iterator = hand.group.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractCard c = (AbstractCard)iterator.next();
                    c.updateHoverLogic();
                    if(!c.hb.hovered || c == hoveredCard)
                        continue;
                    newHoveredCard = c;
                    break;
                } while(true);
                releaseCard();
                if(newHoveredCard == null)
                {
                    InputHelper.moveCursorToNeutralPosition();
                } else
                {
                    newHoveredCard.current_y = AbstractCard.IMG_HEIGHT / 2.0F;
                    newHoveredCard.target_y = AbstractCard.IMG_HEIGHT / 2.0F;
                    newHoveredCard.angle = 0.0F;
                    Gdx.input.setCursorPosition((int)newHoveredCard.current_x, (int)((float)Settings.HEIGHT - AbstractCard.IMG_HEIGHT / 2.0F));
                    touchscreenInspectCount = 1;
                }
            }
        }
        return false;
    }

    private void manuallySelectCard(AbstractCard card)
    {
        hoveredCard = card;
        hoveredCard.setAngle(0.0F, false);
        isUsingClickDragControl = true;
        isDraggingCard = true;
        hoveredCard.flash(Color.SKY.cpy());
        if(hoveredCard.showEvokeValue)
            if(hoveredCard.showEvokeOrbCount == 0)
            {
                AbstractOrb o;
                for(Iterator iterator = orbs.iterator(); iterator.hasNext(); o.showEvokeValue())
                    o = (AbstractOrb)iterator.next();

            } else
            {
                int tmpShowCount = hoveredCard.showEvokeOrbCount;
                int emptyCount = 0;
                Iterator iterator1 = orbs.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractOrb o = (AbstractOrb)iterator1.next();
                    if(o instanceof EmptyOrbSlot)
                        emptyCount++;
                } while(true);
                tmpShowCount -= emptyCount;
                if(tmpShowCount > 0)
                {
                    Iterator iterator2 = orbs.iterator();
                    do
                    {
                        if(!iterator2.hasNext())
                            break;
                        AbstractOrb o = (AbstractOrb)iterator2.next();
                        o.showEvokeValue();
                    } while(--tmpShowCount > 0);
                }
            }
    }

    private void playCard()
    {
        InputHelper.justClickedLeft = false;
        hoverEnemyWaitTimer = 1.0F;
        hoveredCard.unhover();
        if(!queueContains(hoveredCard))
            if(hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY || hoveredCard.target == com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY)
            {
                if(hasPower("Surrounded"))
                    flipHorizontal = hoveredMonster.drawX < drawX;
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(hoveredCard, hoveredMonster));
            } else
            {
                AbstractDungeon.actionManager.cardQueue.add(new CardQueueItem(hoveredCard, null));
            }
        isUsingClickDragControl = false;
        hoveredCard = null;
        isDraggingCard = false;
    }

    private boolean queueContains(AbstractCard card)
    {
        for(Iterator iterator = AbstractDungeon.actionManager.cardQueue.iterator(); iterator.hasNext();)
        {
            CardQueueItem i = (CardQueueItem)iterator.next();
            if(i.card == card)
                return true;
        }

        return false;
    }

    public void releaseCard()
    {
        AbstractOrb o;
        for(Iterator iterator = orbs.iterator(); iterator.hasNext(); o.hideEvokeValues())
            o = (AbstractOrb)iterator.next();

        passedHesitationLine = false;
        InputHelper.justClickedLeft = false;
        InputHelper.justReleasedClickLeft = false;
        InputHelper.isMouseDown = false;
        inSingleTargetMode = false;
        if(!isInKeyboardMode)
            GameCursor.hidden = false;
        isUsingClickDragControl = false;
        isHoveringDropZone = false;
        isDraggingCard = false;
        isHoveringCard = false;
        if(hoveredCard != null)
        {
            if(hoveredCard.canUse(this, null))
                hoveredCard.beginGlowing();
            hoveredCard.untip();
            hoveredCard.hoverTimer = 0.25F;
            hoveredCard.unhover();
        }
        hoveredCard = null;
        hand.refreshHandLayout();
        touchscreenInspectCount = 0;
    }

    public void onCardDrawOrDiscard()
    {
        AbstractPower p;
        for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.onDrawOrDiscard())
            p = (AbstractPower)iterator.next();

        AbstractRelic r;
        for(Iterator iterator1 = relics.iterator(); iterator1.hasNext(); r.onDrawOrDiscard())
            r = (AbstractRelic)iterator1.next();

        if(hasPower("Corruption"))
        {
            Iterator iterator2 = hand.group.iterator();
            do
            {
                if(!iterator2.hasNext())
                    break;
                AbstractCard c = (AbstractCard)iterator2.next();
                if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL && c.costForTurn != 0)
                    c.modifyCostForCombat(-9);
            } while(true);
        }
        hand.applyPowers();
        hand.glowCheck();
    }

    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse)
    {
        if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
            useFastAttackAnimation();
        c.calculateCardDamage(monster);
        if(c.cost == -1 && EnergyPanel.totalCount < energyOnUse && !c.ignoreEnergyOnUse)
            c.energyOnUse = EnergyPanel.totalCount;
        if(c.cost == -1 && c.isInAutoplay)
            c.freeToPlayOnce = true;
        c.use(this, monster);
        AbstractDungeon.actionManager.addToBottom(new UseCardAction(c, monster));
        if(!c.dontTriggerOnUseCard)
            hand.triggerOnOtherCardPlayed(c);
        hand.removeCard(c);
        cardInUse = c;
        c.target_x = Settings.WIDTH / 2;
        c.target_y = Settings.HEIGHT / 2;
        if(c.costForTurn > 0 && !c.freeToPlay() && !c.isInAutoplay && (!hasPower("Corruption") || c.type != com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL))
            energy.use(c.costForTurn);
        if(!hand.canUseAnyCard() && !endTurnQueued)
            AbstractDungeon.overlayMenu.endTurnButton.isGlowing = true;
    }

    public void damage(DamageInfo info)
    {
label0:
        {
            boolean hadBlock;
label1:
            {
label2:
                {
                    int damageAmount = info.output;
                    hadBlock = true;
                    if(currentBlock == 0)
                        hadBlock = false;
                    if(damageAmount < 0)
                        damageAmount = 0;
                    if(damageAmount > 1 && hasPower("IntangiblePlayer"))
                        damageAmount = 1;
                    damageAmount = decrementBlock(info, damageAmount);
                    if(info.owner == this)
                    {
                        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
                        {
                            AbstractRelic r = (AbstractRelic)iterator.next();
                            damageAmount = r.onAttackToChangeDamage(info, damageAmount);
                        }

                    }
                    if(info.owner != null)
                    {
                        for(Iterator iterator1 = info.owner.powers.iterator(); iterator1.hasNext();)
                        {
                            AbstractPower p = (AbstractPower)iterator1.next();
                            damageAmount = p.onAttackToChangeDamage(info, damageAmount);
                        }

                    }
                    for(Iterator iterator2 = relics.iterator(); iterator2.hasNext();)
                    {
                        AbstractRelic r = (AbstractRelic)iterator2.next();
                        damageAmount = r.onAttackedToChangeDamage(info, damageAmount);
                    }

                    for(Iterator iterator3 = powers.iterator(); iterator3.hasNext();)
                    {
                        AbstractPower p = (AbstractPower)iterator3.next();
                        damageAmount = p.onAttackedToChangeDamage(info, damageAmount);
                    }

                    if(info.owner == this)
                    {
                        AbstractRelic r;
                        for(Iterator iterator4 = relics.iterator(); iterator4.hasNext(); r.onAttack(info, damageAmount, this))
                            r = (AbstractRelic)iterator4.next();

                    }
                    if(info.owner != null)
                    {
                        AbstractPower p;
                        for(Iterator iterator5 = info.owner.powers.iterator(); iterator5.hasNext(); p.onAttack(info, damageAmount, this))
                            p = (AbstractPower)iterator5.next();

                        for(Iterator iterator6 = powers.iterator(); iterator6.hasNext();)
                        {
                            AbstractPower p = (AbstractPower)iterator6.next();
                            damageAmount = p.onAttacked(info, damageAmount);
                        }

                        for(Iterator iterator7 = relics.iterator(); iterator7.hasNext();)
                        {
                            AbstractRelic r = (AbstractRelic)iterator7.next();
                            damageAmount = r.onAttacked(info, damageAmount);
                        }

                    } else
                    {
                        logger.info("NO OWNER, DON'T TRIGGER POWERS");
                    }
                    for(Iterator iterator8 = relics.iterator(); iterator8.hasNext();)
                    {
                        AbstractRelic r = (AbstractRelic)iterator8.next();
                        damageAmount = r.onLoseHpLast(damageAmount);
                    }

                    lastDamageTaken = Math.min(damageAmount, currentHealth);
                    if(damageAmount <= 0)
                        break label1;
                    for(Iterator iterator9 = powers.iterator(); iterator9.hasNext();)
                    {
                        AbstractPower p = (AbstractPower)iterator9.next();
                        damageAmount = p.onLoseHp(damageAmount);
                    }

                    AbstractRelic r;
                    for(Iterator iterator10 = relics.iterator(); iterator10.hasNext(); r.onLoseHp(damageAmount))
                        r = (AbstractRelic)iterator10.next();

                    AbstractPower p;
                    for(Iterator iterator11 = powers.iterator(); iterator11.hasNext(); p.wasHPLost(info, damageAmount))
                        p = (AbstractPower)iterator11.next();

                    AbstractRelic r;
                    for(Iterator iterator12 = relics.iterator(); iterator12.hasNext(); r.wasHPLost(damageAmount))
                        r = (AbstractRelic)iterator12.next();

                    if(info.owner != null)
                    {
                        AbstractPower p;
                        for(Iterator iterator13 = info.owner.powers.iterator(); iterator13.hasNext(); p.onInflictDamage(info, damageAmount, this))
                            p = (AbstractPower)iterator13.next();

                    }
                    if(info.owner != this)
                        useStaggerAnimation();
                    if(info.type == com.megacrit.cardcrawl.cards.DamageInfo.DamageType.HP_LOSS)
                        GameActionManager.hpLossThisCombat += damageAmount;
                    GameActionManager.damageReceivedThisTurn += damageAmount;
                    GameActionManager.damageReceivedThisCombat += damageAmount;
                    currentHealth -= damageAmount;
                    if(damageAmount > 0 && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
                    {
                        updateCardsOnDamage();
                        damagedThisCombat++;
                    }
                    AbstractDungeon.effectList.add(new StrikeEffect(this, hb.cX, hb.cY, damageAmount));
                    if(currentHealth < 0)
                        currentHealth = 0;
                    else
                    if(currentHealth < maxHealth / 4)
                        AbstractDungeon.topLevelEffects.add(new BorderFlashEffect(new Color(1.0F, 0.1F, 0.05F, 0.0F)));
                    healthBarUpdatedEvent();
                    if((float)currentHealth <= (float)maxHealth / 2.0F && !isBloodied)
                    {
                        isBloodied = true;
                        Iterator iterator14 = relics.iterator();
                        do
                        {
                            if(!iterator14.hasNext())
                                break;
                            AbstractRelic r = (AbstractRelic)iterator14.next();
                            if(r != null)
                                r.onBloodied();
                        } while(true);
                    }
                    if(currentHealth >= 1)
                        break label0;
                    if(hasRelic("Mark of the Bloom"))
                        break label2;
                    if(hasPotion("FairyPotion"))
                    {
                        Iterator iterator15 = potions.iterator();
                        AbstractPotion p;
                        do
                        {
                            if(!iterator15.hasNext())
                                break label2;
                            p = (AbstractPotion)iterator15.next();
                        } while(!p.ID.equals("FairyPotion"));
                        p.flash();
                        currentHealth = 0;
                        p.use(this);
                        AbstractDungeon.topPanel.destroyPotion(p.slot);
                        return;
                    }
                    if(hasRelic("Lizard Tail") && ((LizardTail)getRelic("Lizard Tail")).counter == -1)
                    {
                        currentHealth = 0;
                        getRelic("Lizard Tail").onTrigger();
                        return;
                    }
                }
                isDead = true;
                AbstractDungeon.deathScreen = new DeathScreen(AbstractDungeon.getMonsters());
                currentHealth = 0;
                if(currentBlock > 0)
                {
                    loseBlock();
                    AbstractDungeon.effectList.add(new HbBlockBrokenEffect((hb.cX - hb.width / 2.0F) + BLOCK_ICON_X, (hb.cY - hb.height / 2.0F) + BLOCK_ICON_Y));
                }
                break label0;
            }
            if(currentBlock > 0)
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, hb.cX, hb.cY, uiStrings.TEXT[0]));
            else
            if(hadBlock)
            {
                AbstractDungeon.effectList.add(new BlockedWordEffect(this, hb.cX, hb.cY, uiStrings.TEXT[0]));
                AbstractDungeon.effectList.add(new HbBlockBrokenEffect((hb.cX - hb.width / 2.0F) + BLOCK_ICON_X, (hb.cY - hb.height / 2.0F) + BLOCK_ICON_Y));
            } else
            {
                AbstractDungeon.effectList.add(new StrikeEffect(this, hb.cX, hb.cY, 0));
            }
        }
    }

    private void updateCardsOnDamage()
    {
        if(AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
        {
            AbstractCard c;
            for(Iterator iterator = hand.group.iterator(); iterator.hasNext(); c.tookDamage())
                c = (AbstractCard)iterator.next();

            AbstractCard c;
            for(Iterator iterator1 = discardPile.group.iterator(); iterator1.hasNext(); c.tookDamage())
                c = (AbstractCard)iterator1.next();

            AbstractCard c;
            for(Iterator iterator2 = drawPile.group.iterator(); iterator2.hasNext(); c.tookDamage())
                c = (AbstractCard)iterator2.next();

        }
    }

    public void updateCardsOnDiscard()
    {
        AbstractCard c;
        for(Iterator iterator = hand.group.iterator(); iterator.hasNext(); c.didDiscard())
            c = (AbstractCard)iterator.next();

        AbstractCard c;
        for(Iterator iterator1 = discardPile.group.iterator(); iterator1.hasNext(); c.didDiscard())
            c = (AbstractCard)iterator1.next();

        AbstractCard c;
        for(Iterator iterator2 = drawPile.group.iterator(); iterator2.hasNext(); c.didDiscard())
            c = (AbstractCard)iterator2.next();

    }

    public void heal(int healAmount)
    {
        super.heal(healAmount);
        if((float)currentHealth > (float)maxHealth / 2.0F && isBloodied)
        {
            isBloodied = false;
            AbstractRelic r;
            for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.onNotBloodied())
                r = (AbstractRelic)iterator.next();

        }
    }

    public void gainEnergy(int e)
    {
        EnergyPanel.addEnergy(e);
        hand.glowCheck();
    }

    public void loseEnergy(int e)
    {
        EnergyPanel.useEnergy(e);
    }

    public void preBattlePrep()
    {
        if(!((Boolean)TipTracker.tips.get("COMBAT_TIP")).booleanValue())
        {
            AbstractDungeon.ftue = new MultiPageFtue();
            TipTracker.neverShowAgain("COMBAT_TIP");
        }
        AbstractDungeon.actionManager.clear();
        damagedThisCombat = 0;
        cardsPlayedThisTurn = 0;
        maxOrbs = 0;
        orbs.clear();
        increaseMaxOrbSlots(masterMaxOrbs, false);
        isBloodied = currentHealth <= maxHealth / 2;
        poisonKillCount = 0;
        GameActionManager.playerHpLastTurn = currentHealth;
        endTurnQueued = false;
        gameHandSize = masterHandSize;
        isDraggingCard = false;
        isHoveringDropZone = false;
        hoveredCard = null;
        cardInUse = null;
        drawPile.initializeDeck(masterDeck);
        AbstractDungeon.overlayMenu.endTurnButton.enabled = false;
        hand.clear();
        discardPile.clear();
        exhaustPile.clear();
        if(AbstractDungeon.player.hasRelic("SlaversCollar"))
            ((SlaversCollar)AbstractDungeon.player.getRelic("SlaversCollar")).beforeEnergyPrep();
        energy.prep();
        powers.clear();
        isEndingTurn = false;
        healthBarUpdatedEvent();
        if(ModHelper.isModEnabled("Lethality"))
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
        if(ModHelper.isModEnabled("Terminal"))
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new PlatedArmorPower(this, 5), 5));
        AbstractDungeon.getCurrRoom().monsters.usePreBattleAction();
        if(Settings.isFinalActAvailable && AbstractDungeon.getCurrMapNode().hasEmeraldKey)
            AbstractDungeon.getCurrRoom().applyEmeraldEliteBuff();
        AbstractDungeon.actionManager.addToTop(new WaitAction(1.0F));
        applyPreCombatLogic();
    }

    public ArrayList getRelicNames()
    {
        ArrayList arr = new ArrayList();
        AbstractRelic relic;
        for(Iterator iterator = relics.iterator(); iterator.hasNext(); arr.add(relic.relicId))
            relic = (AbstractRelic)iterator.next();

        return arr;
    }

    public int getCircletCount()
    {
        int count = 0;
        int counterSum = 0;
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic relic = (AbstractRelic)iterator.next();
            if(relic.relicId.equals("Circlet"))
            {
                count++;
                counterSum += relic.counter;
            }
        } while(true);
        if(counterSum > 0)
            return counterSum;
        else
            return count;
    }

    public void draw(int numCards)
    {
        for(int i = 0; i < numCards; i++)
            if(!drawPile.isEmpty())
            {
                AbstractCard c = drawPile.getTopCard();
                c.current_x = CardGroup.DRAW_PILE_X;
                c.current_y = CardGroup.DRAW_PILE_Y;
                c.setAngle(0.0F, true);
                c.lighten(false);
                c.drawScale = 0.12F;
                c.targetDrawScale = 0.75F;
                c.triggerWhenDrawn();
                hand.addToHand(c);
                drawPile.removeTopCard();
                AbstractPower p;
                for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.onCardDraw(c))
                    p = (AbstractPower)iterator.next();

                AbstractRelic r;
                for(Iterator iterator1 = relics.iterator(); iterator1.hasNext(); r.onCardDraw(c))
                    r = (AbstractRelic)iterator1.next();

            } else
            {
                logger.info("ERROR: How did this happen? No cards in draw pile?? Player.java");
            }

    }

    public void draw()
    {
        if(hand.size() == 10)
        {
            createHandIsFullDialog();
            return;
        } else
        {
            CardCrawlGame.sound.playAV("CARD_DRAW_8", -0.12F, 0.25F);
            draw(1);
            onCardDrawOrDiscard();
            return;
        }
    }

    public void render(SpriteBatch sb)
    {
        stance.render(sb);
        if((AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT || (AbstractDungeon.getCurrRoom() instanceof MonsterRoom)) && !isDead)
        {
            renderHealth(sb);
            if(!orbs.isEmpty())
            {
                AbstractOrb o;
                for(Iterator iterator = orbs.iterator(); iterator.hasNext(); o.render(sb))
                    o = (AbstractOrb)iterator.next();

            }
        }
        if(!(AbstractDungeon.getCurrRoom() instanceof RestRoom))
        {
            if(atlas == null || renderCorpse)
            {
                sb.setColor(Color.WHITE);
                sb.draw(img, (drawX - ((float)img.getWidth() * Settings.scale) / 2.0F) + animX, drawY, (float)img.getWidth() * Settings.scale, (float)img.getHeight() * Settings.scale, 0, 0, img.getWidth(), img.getHeight(), flipHorizontal, flipVertical);
            } else
            {
                renderPlayerImage(sb);
            }
            hb.render(sb);
            healthHb.render(sb);
        } else
        {
            sb.setColor(Color.WHITE);
            renderShoulderImg(sb);
        }
    }

    public void renderShoulderImg(SpriteBatch sb)
    {
        if(CampfireUI.hidden)
            sb.draw(shoulder2Img, 0.0F, 0.0F, 1920F * Settings.scale, 1136F * Settings.scale);
        else
            sb.draw(shoulderImg, animX, 0.0F, 1920F * Settings.scale, 1136F * Settings.scale);
    }

    public void renderPlayerImage(SpriteBatch sb)
    {
        if(atlas != null)
        {
            state.update(Gdx.graphics.getDeltaTime());
            state.apply(skeleton);
            skeleton.updateWorldTransform();
            skeleton.setPosition(drawX + animX, drawY + animY);
            skeleton.setColor(tint.color);
            skeleton.setFlip(flipHorizontal, flipVertical);
            sb.end();
            CardCrawlGame.psb.begin();
            sr.draw(CardCrawlGame.psb, skeleton);
            CardCrawlGame.psb.end();
            sb.begin();
        } else
        {
            sb.setColor(Color.WHITE);
            sb.draw(img, (drawX - ((float)img.getWidth() * Settings.scale) / 2.0F) + animX, drawY, (float)img.getWidth() * Settings.scale, (float)img.getHeight() * Settings.scale, 0, 0, img.getWidth(), img.getHeight(), flipHorizontal, flipVertical);
        }
    }

    public void renderPlayerBattleUi(SpriteBatch sb)
    {
        if((hb.hovered || healthHb.hovered) && !AbstractDungeon.isScreenUp)
            renderPowerTips(sb);
    }

    public void renderPowerTips(SpriteBatch sb)
    {
        ArrayList tips = new ArrayList();
        if(!stance.ID.equals("Neutral"))
            tips.add(new PowerTip(stance.name, stance.description));
        for(Iterator iterator = powers.iterator(); iterator.hasNext();)
        {
            AbstractPower p = (AbstractPower)iterator.next();
            if(p.region48 != null)
                tips.add(new PowerTip(p.name, p.description, p.region48));
            else
                tips.add(new PowerTip(p.name, p.description, p.img));
        }

        if(!tips.isEmpty())
            if(hb.cX + hb.width / 2.0F < TIP_X_THRESHOLD)
                TipHelper.queuePowerTips(hb.cX + hb.width / 2.0F + TIP_OFFSET_R_X, hb.cY + TipHelper.calculateAdditionalOffset(tips, hb.cY), tips);
            else
                TipHelper.queuePowerTips((hb.cX - hb.width / 2.0F) + TIP_OFFSET_L_X, hb.cY + TipHelper.calculateAdditionalOffset(tips, hb.cY), tips);
    }

    public void renderHand(SpriteBatch sb)
    {
        if(Settings.SHOW_CARD_HOTKEYS)
            renderCardHotKeyText(sb);
        if(inspectMode && inspectHb != null)
            renderReticle(sb, inspectHb);
        if(hoveredCard != null)
        {
            int aliveMonsters = 0;
            hand.renderHand(sb, hoveredCard);
            hoveredCard.renderHoverShadow(sb);
            if((isDraggingCard || inSingleTargetMode) && isHoveringDropZone)
            {
                if(isDraggingCard && !inSingleTargetMode)
                {
                    AbstractMonster theMonster = null;
                    Iterator iterator = AbstractDungeon.getMonsters().monsters.iterator();
                    do
                    {
                        if(!iterator.hasNext())
                            break;
                        AbstractMonster m = (AbstractMonster)iterator.next();
                        if(!m.isDying && m.currentHealth > 0)
                        {
                            aliveMonsters++;
                            theMonster = m;
                        }
                    } while(true);
                    if(aliveMonsters == 1 && hoveredMonster == null)
                    {
                        hoveredCard.calculateCardDamage(theMonster);
                        hoveredCard.render(sb);
                        hoveredCard.applyPowers();
                    } else
                    {
                        hoveredCard.render(sb);
                    }
                }
                if(!AbstractDungeon.getCurrRoom().isBattleEnding())
                    renderHoverReticle(sb);
            }
            if(hoveredMonster != null)
            {
                hoveredCard.calculateCardDamage(hoveredMonster);
                hoveredCard.render(sb);
                hoveredCard.applyPowers();
            } else
            if(aliveMonsters != 1)
                hoveredCard.render(sb);
        } else
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT)
            hand.render(sb);
        else
            hand.renderHand(sb, cardInUse);
        if(cardInUse != null && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT && !PeekButton.isPeeking)
        {
            cardInUse.render(sb);
            if(AbstractDungeon.getCurrRoom().phase != com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT)
            {
                AbstractDungeon.effectList.add(new CardDisappearEffect(cardInUse.makeCopy(), cardInUse.current_x, cardInUse.current_y));
                cardInUse = null;
            }
        }
        limbo.render(sb);
        if(inSingleTargetMode && AbstractDungeon.getCurrRoom().phase == com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT && !AbstractDungeon.getCurrRoom().isBattleEnding())
            renderTargetingUi(sb);
    }

    private void renderTargetingUi(SpriteBatch sb)
    {
        arrowX = MathHelper.mouseLerpSnap(arrowX, InputHelper.mX);
        arrowY = MathHelper.mouseLerpSnap(arrowY, InputHelper.mY);
        controlPoint.x = hoveredCard.current_x - (arrowX - hoveredCard.current_x) / 4F;
        controlPoint.y = arrowY + (arrowY - hoveredCard.current_y) / 2.0F;
        if(hoveredMonster == null)
        {
            arrowScale = Settings.scale;
            arrowScaleTimer = 0.0F;
            sb.setColor(Color.WHITE);
        } else
        {
            arrowScaleTimer += Gdx.graphics.getDeltaTime();
            if(arrowScaleTimer > 1.0F)
                arrowScaleTimer = 1.0F;
            arrowScale = Interpolation.elasticOut.apply(Settings.scale, Settings.scale * 1.2F, arrowScaleTimer);
            sb.setColor(ARROW_COLOR);
        }
        arrowTmp.x = controlPoint.x - arrowX;
        arrowTmp.y = controlPoint.y - arrowY;
        arrowTmp.nor();
        startArrowVector.x = hoveredCard.current_x;
        startArrowVector.y = hoveredCard.current_y;
        endArrowVector.x = arrowX;
        endArrowVector.y = arrowY;
        drawCurvedLine(sb, startArrowVector, endArrowVector, controlPoint);
        sb.draw(ImageMaster.TARGET_UI_ARROW, arrowX - 128F, arrowY - 128F, 128F, 128F, 256F, 256F, arrowScale, arrowScale, arrowTmp.angle() + 90F, 0, 0, 256, 256, false, false);
    }

    private void drawCurvedLine(SpriteBatch sb, Vector2 start, Vector2 end, Vector2 control)
    {
        float radius = 7F * Settings.scale;
        for(int i = 0; i < points.length - 1; i++)
        {
            points[i] = (Vector2)Bezier.quadratic(points[i], (float)i / 20F, start, control, end, arrowTmp);
            radius += 0.4F * Settings.scale;
            if(i != 0)
            {
                arrowTmp.x = points[i - 1].x - points[i].x;
                arrowTmp.y = points[i - 1].y - points[i].y;
                sb.draw(ImageMaster.TARGET_UI_CIRCLE, points[i].x - 64F, points[i].y - 64F, 64F, 64F, 128F, 128F, radius / 18F, radius / 18F, arrowTmp.nor().angle() + 90F, 0, 0, 128, 128, false, false);
            } else
            {
                arrowTmp.x = controlPoint.x - points[i].x;
                arrowTmp.y = controlPoint.y - points[i].y;
                sb.draw(ImageMaster.TARGET_UI_CIRCLE, points[i].x - 64F, points[i].y - 64F, 64F, 64F, 128F, 128F, radius / 18F, radius / 18F, arrowTmp.nor().angle() + 270F, 0, 0, 128, 128, false, false);
            }
        }

    }

    public void createHandIsFullDialog()
    {
        AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, MSG[2], true));
    }

    private void renderHoverReticle(SpriteBatch sb)
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget = new int[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ENEMY.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL_ENEMY.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.SELF_AND_ENEMY.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.ALL.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$cards$AbstractCard$CardTarget[com.megacrit.cardcrawl.cards.AbstractCard.CardTarget.NONE.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.cards.AbstractCard.CardTarget[hoveredCard.target.ordinal()])
        {
        case 6: // '\006'
        default:
            break;

        case 1: // '\001'
            if(inSingleTargetMode && hoveredMonster != null)
                hoveredMonster.renderReticle(sb);
            break;

        case 2: // '\002'
            AbstractDungeon.getCurrRoom().monsters.renderReticle(sb);
            break;

        case 3: // '\003'
            renderReticle(sb);
            break;

        case 4: // '\004'
            renderReticle(sb);
            if(inSingleTargetMode && hoveredMonster != null)
                hoveredMonster.renderReticle(sb);
            break;

        case 5: // '\005'
            renderReticle(sb);
            AbstractDungeon.getCurrRoom().monsters.renderReticle(sb);
            break;
        }
    }

    public void applyPreCombatLogic()
    {
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.atPreBattle();
        } while(true);
    }

    public void applyStartOfCombatLogic()
    {
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.atBattleStart();
        } while(true);
        iterator = blights.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b != null)
                b.atBattleStart();
        } while(true);
    }

    public void applyStartOfCombatPreDrawLogic()
    {
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.atBattleStartPreDraw();
        } while(true);
    }

    public void applyStartOfTurnRelics()
    {
        stance.atStartOfTurn();
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.atTurnStart();
        } while(true);
        iterator = blights.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b != null)
                b.atTurnStart();
        } while(true);
    }

    public void applyStartOfTurnPostDrawRelics()
    {
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r != null)
                r.atTurnStartPostDraw();
        } while(true);
    }

    public void applyStartOfTurnPreDrawCards()
    {
        Iterator iterator = hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c != null)
                c.atTurnStartPreDraw();
        } while(true);
    }

    public void applyStartOfTurnCards()
    {
        Iterator iterator = drawPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c != null)
                c.atTurnStart();
        } while(true);
        iterator = hand.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c != null)
                c.atTurnStart();
        } while(true);
        iterator = discardPile.group.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c != null)
                c.atTurnStart();
        } while(true);
    }

    public void onVictory()
    {
        if(!isDying)
        {
            AbstractRelic r;
            for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.onVictory())
                r = (AbstractRelic)iterator.next();

            AbstractBlight b;
            for(Iterator iterator1 = blights.iterator(); iterator1.hasNext(); b.onVictory())
                b = (AbstractBlight)iterator1.next();

            AbstractPower p;
            for(Iterator iterator2 = powers.iterator(); iterator2.hasNext(); p.onVictory())
                p = (AbstractPower)iterator2.next();

        }
        damagedThisCombat = 0;
    }

    public boolean hasRelic(String targetID)
    {
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.relicId.equals(targetID))
                return true;
        }

        return false;
    }

    public boolean hasBlight(String targetID)
    {
        for(Iterator iterator = blights.iterator(); iterator.hasNext();)
        {
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b.blightID.equals(targetID))
                return true;
        }

        return false;
    }

    public boolean hasPotion(String id)
    {
        for(Iterator iterator = potions.iterator(); iterator.hasNext();)
        {
            AbstractPotion p = (AbstractPotion)iterator.next();
            if(p.ID.equals(id))
                return true;
        }

        return false;
    }

    public boolean hasAnyPotions()
    {
        for(Iterator iterator = potions.iterator(); iterator.hasNext();)
        {
            AbstractPotion p = (AbstractPotion)iterator.next();
            if(!(p instanceof PotionSlot))
                return true;
        }

        return false;
    }

    public void loseRandomRelics(int amount)
    {
        if(amount > relics.size())
        {
            AbstractRelic r;
            for(Iterator iterator = relics.iterator(); iterator.hasNext(); r.onUnequip())
                r = (AbstractRelic)iterator.next();

            relics.clear();
            return;
        }
        for(int i = 0; i < amount; i++)
        {
            int index = MathUtils.random(0, relics.size() - 1);
            ((AbstractRelic)relics.get(index)).onUnequip();
            relics.remove(index);
        }

        reorganizeRelics();
    }

    public boolean loseRelic(String targetID)
    {
        if(!hasRelic(targetID))
            return false;
        AbstractRelic toRemove = null;
        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.relicId.equals(targetID))
            {
                r.onUnequip();
                toRemove = r;
            }
        } while(true);
        if(toRemove == null)
        {
            logger.info((new StringBuilder()).append("WHY WAS RELIC: ").append(name).append(" NOT FOUND???").toString());
            return false;
        } else
        {
            relics.remove(toRemove);
            reorganizeRelics();
            return true;
        }
    }

    public void reorganizeRelics()
    {
        logger.info("Reorganizing relics");
        ArrayList tmpRelics = new ArrayList();
        tmpRelics.addAll(relics);
        relics.clear();
        for(int i = 0; i < tmpRelics.size(); i++)
            ((AbstractRelic)tmpRelics.get(i)).reorganizeObtain(this, i, false, tmpRelics.size());

    }

    public AbstractRelic getRelic(String targetID)
    {
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.relicId.equals(targetID))
                return r;
        }

        return null;
    }

    public AbstractBlight getBlight(String targetID)
    {
        for(Iterator iterator = blights.iterator(); iterator.hasNext();)
        {
            AbstractBlight b = (AbstractBlight)iterator.next();
            if(b.blightID.equals(targetID))
                return b;
        }

        return null;
    }

    public void obtainPotion(int slot, AbstractPotion potionToObtain)
    {
        if(slot > potionSlots)
        {
            return;
        } else
        {
            potions.set(slot, potionToObtain);
            potionToObtain.setAsObtained(slot);
            return;
        }
    }

    public boolean obtainPotion(AbstractPotion potionToObtain)
    {
        int index = 0;
        Iterator iterator = potions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPotion p = (AbstractPotion)iterator.next();
            if(p instanceof PotionSlot)
                break;
            index++;
        } while(true);
        if(index < potionSlots)
        {
            potions.set(index, potionToObtain);
            potionToObtain.setAsObtained(index);
            potionToObtain.flash();
            AbstractPotion.playPotionSound();
            return true;
        } else
        {
            logger.info("NOT ENOUGH POTION SLOTS");
            AbstractDungeon.topPanel.flashRed();
            return false;
        }
    }

    public void renderRelics(SpriteBatch sb)
    {
        for(int i = 0; i < relics.size(); i++)
            if(i / AbstractRelic.MAX_RELICS_PER_PAGE == AbstractRelic.relicPage)
                ((AbstractRelic)relics.get(i)).renderInTopPanel(sb);

        Iterator iterator = relics.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(r.hb.hovered)
                r.renderTip(sb);
        } while(true);
    }

    public void renderBlights(SpriteBatch sb)
    {
        AbstractBlight b;
        for(Iterator iterator = blights.iterator(); iterator.hasNext(); b.renderInTopPanel(sb))
            b = (AbstractBlight)iterator.next();

        Iterator iterator1 = blights.iterator();
        do
        {
            if(!iterator1.hasNext())
                break;
            AbstractBlight b = (AbstractBlight)iterator1.next();
            if(b.hb.hovered)
                b.renderTip(sb);
        } while(true);
    }

    public void bottledCardUpgradeCheck(AbstractCard c)
    {
        if(c.inBottleFlame && hasRelic("Bottled Flame"))
            ((BottledFlame)getRelic("Bottled Flame")).setDescriptionAfterLoading();
        if(c.inBottleLightning && hasRelic("Bottled Lightning"))
            ((BottledLightning)getRelic("Bottled Lightning")).setDescriptionAfterLoading();
        if(c.inBottleTornado && hasRelic("Bottled Tornado"))
            ((BottledTornado)getRelic("Bottled Tornado")).setDescriptionAfterLoading();
    }

    public void triggerEvokeAnimation(int slot)
    {
        if(maxOrbs <= 0)
        {
            return;
        } else
        {
            ((AbstractOrb)orbs.get(slot)).triggerEvokeAnimation();
            return;
        }
    }

    public void evokeOrb()
    {
        if(!orbs.isEmpty() && !(orbs.get(0) instanceof EmptyOrbSlot))
        {
            ((AbstractOrb)orbs.get(0)).onEvoke();
            AbstractOrb orbSlot = new EmptyOrbSlot();
            for(int i = 1; i < orbs.size(); i++)
                Collections.swap(orbs, i, i - 1);

            orbs.set(orbs.size() - 1, orbSlot);
            for(int i = 0; i < orbs.size(); i++)
                ((AbstractOrb)orbs.get(i)).setSlot(i, maxOrbs);

        }
    }

    public void evokeNewestOrb()
    {
        if(!orbs.isEmpty() && !(orbs.get(orbs.size() - 1) instanceof EmptyOrbSlot))
            ((AbstractOrb)orbs.get(orbs.size() - 1)).onEvoke();
    }

    public void evokeWithoutLosingOrb()
    {
        if(!orbs.isEmpty() && !(orbs.get(0) instanceof EmptyOrbSlot))
            ((AbstractOrb)orbs.get(0)).onEvoke();
    }

    public void removeNextOrb()
    {
        if(!orbs.isEmpty() && !(orbs.get(0) instanceof EmptyOrbSlot))
        {
            AbstractOrb orbSlot = new EmptyOrbSlot(((AbstractOrb)orbs.get(0)).cX, ((AbstractOrb)orbs.get(0)).cY);
            for(int i = 1; i < orbs.size(); i++)
                Collections.swap(orbs, i, i - 1);

            orbs.set(orbs.size() - 1, orbSlot);
            for(int i = 0; i < orbs.size(); i++)
                ((AbstractOrb)orbs.get(i)).setSlot(i, maxOrbs);

        }
    }

    public boolean hasEmptyOrb()
    {
        if(orbs.isEmpty())
            return false;
        for(Iterator iterator = orbs.iterator(); iterator.hasNext();)
        {
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(o instanceof EmptyOrbSlot)
                return true;
        }

        return false;
    }

    public boolean hasOrb()
    {
        if(orbs.isEmpty())
            return false;
        return !(orbs.get(0) instanceof EmptyOrbSlot);
    }

    public int filledOrbCount()
    {
        int orbCount = 0;
        Iterator iterator = orbs.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractOrb o = (AbstractOrb)iterator.next();
            if(!(o instanceof EmptyOrbSlot))
                orbCount++;
        } while(true);
        return orbCount;
    }

    public void channelOrb(AbstractOrb orbToSet)
    {
        if(maxOrbs <= 0)
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, MSG[4], true));
            return;
        }
        if(maxOrbs > 0)
        {
            if(hasRelic("Dark Core") && !(orbToSet instanceof Dark))
                orbToSet = new Dark();
            int index = -1;
            int i = 0;
            do
            {
                if(i >= orbs.size())
                    break;
                if(orbs.get(i) instanceof EmptyOrbSlot)
                {
                    index = i;
                    break;
                }
                i++;
            } while(true);
            if(index != -1)
            {
                orbToSet.cX = ((AbstractOrb)orbs.get(index)).cX;
                orbToSet.cY = ((AbstractOrb)orbs.get(index)).cY;
                orbs.set(index, orbToSet);
                ((AbstractOrb)orbs.get(index)).setSlot(index, maxOrbs);
                orbToSet.playChannelSFX();
                AbstractPower p;
                for(Iterator iterator = powers.iterator(); iterator.hasNext(); p.onChannel(orbToSet))
                    p = (AbstractPower)iterator.next();

                AbstractDungeon.actionManager.orbsChanneledThisCombat.add(orbToSet);
                AbstractDungeon.actionManager.orbsChanneledThisTurn.add(orbToSet);
                int plasmaCount = 0;
                Iterator iterator1 = AbstractDungeon.actionManager.orbsChanneledThisTurn.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractOrb o = (AbstractOrb)iterator1.next();
                    if(o instanceof Plasma)
                        plasmaCount++;
                } while(true);
                if(plasmaCount == 9)
                    UnlockTracker.unlockAchievement("NEON");
                orbToSet.applyFocus();
            } else
            {
                AbstractDungeon.actionManager.addToTop(new ChannelAction(orbToSet));
                AbstractDungeon.actionManager.addToTop(new EvokeOrbAction(1));
                AbstractDungeon.actionManager.addToTop(new AnimateOrbAction(1));
            }
        }
    }

    public void increaseMaxOrbSlots(int amount, boolean playSfx)
    {
        if(maxOrbs == 10)
        {
            AbstractDungeon.effectList.add(new ThoughtBubble(dialogX, dialogY, 3F, MSG[3], true));
            return;
        }
        if(playSfx)
            CardCrawlGame.sound.play("ORB_SLOT_GAIN", 0.1F);
        maxOrbs += amount;
        for(int i = 0; i < amount; i++)
            orbs.add(new EmptyOrbSlot());

        for(int i = 0; i < orbs.size(); i++)
            ((AbstractOrb)orbs.get(i)).setSlot(i, maxOrbs);

    }

    public void decreaseMaxOrbSlots(int amount)
    {
        if(maxOrbs <= 0)
            return;
        maxOrbs -= amount;
        if(maxOrbs < 0)
            maxOrbs = 0;
        if(!orbs.isEmpty())
            orbs.remove(orbs.size() - 1);
        for(int i = 0; i < orbs.size(); i++)
            ((AbstractOrb)orbs.get(i)).setSlot(i, maxOrbs);

    }

    public void applyStartOfTurnOrbs()
    {
        if(!orbs.isEmpty())
        {
            AbstractOrb o;
            for(Iterator iterator = orbs.iterator(); iterator.hasNext(); o.onStartOfTurn())
                o = (AbstractOrb)iterator.next();

            if(hasRelic("Cables") && !(orbs.get(0) instanceof EmptyOrbSlot))
                ((AbstractOrb)orbs.get(0)).onStartOfTurn();
        }
    }

    private void updateEscapeAnimation()
    {
        if(escapeTimer != 0.0F)
        {
            escapeTimer -= Gdx.graphics.getDeltaTime();
            if(flipHorizontal)
                drawX -= Gdx.graphics.getDeltaTime() * 400F * Settings.scale;
            else
                drawX += Gdx.graphics.getDeltaTime() * 500F * Settings.scale;
        }
        if(escapeTimer < 0.0F)
        {
            AbstractDungeon.getCurrRoom().endBattle();
            flipHorizontal = false;
            isEscaping = false;
            escapeTimer = 0.0F;
        }
    }

    public boolean relicsDoneAnimating()
    {
        for(Iterator iterator = relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            if(!r.isDone)
                return false;
        }

        return true;
    }

    public void resetControllerValues()
    {
        if(Settings.isControllerMode)
        {
            toHover = null;
            hoveredCard = null;
            inspectMode = false;
            inspectHb = null;
            keyboardCardIndex = -1;
            hand.refreshHandLayout();
        }
    }

    public AbstractPotion getRandomPotion()
    {
        ArrayList list = new ArrayList();
        Iterator iterator = potions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPotion p = (AbstractPotion)iterator.next();
            if(!(p instanceof PotionSlot))
                list.add(p);
        } while(true);
        if(list.isEmpty())
        {
            return null;
        } else
        {
            Collections.shuffle(list, new Random(AbstractDungeon.miscRng.randomLong()));
            return (AbstractPotion)list.get(0);
        }
    }

    public void removePotion(AbstractPotion potionOption)
    {
        int slot = potions.indexOf(potionOption);
        if(slot >= 0)
            potions.set(slot, new PotionSlot(slot));
    }

    public void movePosition(float x, float y)
    {
        drawX = x;
        drawY = y;
        dialogX = drawX + 0.0F * Settings.scale;
        dialogY = drawY + 170F * Settings.scale;
        animX = 0.0F;
        animY = 0.0F;
        refreshHitboxLocation();
    }

    public void switchedStance()
    {
        AbstractCard c;
        for(Iterator iterator = hand.group.iterator(); iterator.hasNext(); c.switchedStance())
            c = (AbstractCard)iterator.next();

        AbstractCard c;
        for(Iterator iterator1 = discardPile.group.iterator(); iterator1.hasNext(); c.switchedStance())
            c = (AbstractCard)iterator1.next();

        AbstractCard c;
        for(Iterator iterator2 = drawPile.group.iterator(); iterator2.hasNext(); c.switchedStance())
            c = (AbstractCard)iterator2.next();

    }

    public CharacterOption getCharacterSelectOption()
    {
        return null;
    }

    public void onStanceChange(String s)
    {
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/AbstractPlayer.getName());
    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    public PlayerClass chosenClass;
    public int gameHandSize;
    public int masterHandSize;
    public int startingMaxHP;
    public CardGroup masterDeck;
    public CardGroup drawPile;
    public CardGroup hand;
    public CardGroup discardPile;
    public CardGroup exhaustPile;
    public CardGroup limbo;
    public ArrayList relics;
    public ArrayList blights;
    public int potionSlots;
    public ArrayList potions;
    public EnergyManager energy;
    public boolean isEndingTurn;
    public boolean viewingRelics;
    public boolean inspectMode;
    public Hitbox inspectHb;
    public static int poisonKillCount = 0;
    public int damagedThisCombat;
    public String title;
    public ArrayList orbs;
    public int masterMaxOrbs;
    public int maxOrbs;
    public AbstractStance stance;
    /**
     * @deprecated Field cardsPlayedThisTurn is deprecated
     */
    public int cardsPlayedThisTurn;
    private boolean isHoveringCard;
    public boolean isHoveringDropZone;
    private float hoverStartLine;
    private boolean passedHesitationLine;
    public AbstractCard hoveredCard;
    public AbstractCard toHover;
    public AbstractCard cardInUse;
    public boolean isDraggingCard;
    private boolean isUsingClickDragControl;
    private float clickDragTimer;
    public boolean inSingleTargetMode;
    private AbstractMonster hoveredMonster;
    public float hoverEnemyWaitTimer;
    private static final float HOVER_ENEMY_WAIT_TIME = 1F;
    public boolean isInKeyboardMode;
    private boolean skipMouseModeOnce;
    private int keyboardCardIndex;
    public static ArrayList customMods = null;
    private int touchscreenInspectCount;
    public Texture img;
    public Texture shoulderImg;
    public Texture shoulder2Img;
    public Texture corpseImg;
    private static final Color ARROW_COLOR = new Color(1.0F, 0.2F, 0.3F, 1.0F);
    private float arrowScale;
    private float arrowScaleTimer;
    private float arrowX;
    private float arrowY;
    private static final float ARROW_TARGET_SCALE = 1.2F;
    private static final int TARGET_ARROW_W = 256;
    public static final float HOVER_CARD_Y_POSITION;
    public boolean endTurnQueued;
    private static final int SEGMENTS = 20;
    private Vector2 points[];
    private Vector2 controlPoint;
    private Vector2 arrowTmp;
    private Vector2 startArrowVector;
    private Vector2 endArrowVector;
    private boolean renderCorpse;
    public static final UIStrings uiStrings;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Player Tips");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        HOVER_CARD_Y_POSITION = 210F * Settings.scale;
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractPlayer");
    }
}
