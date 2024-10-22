// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractRelic.java

package com.megacrit.cardcrawl.relics;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.rooms.*;
import com.megacrit.cardcrawl.screens.SingleRelicViewPopup;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.select.BossRelicSelectScreen;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.ui.FtueTip;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.ui.campfire.AbstractCampfireOption;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.*;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.relics:
//            Circlet, HolyWater, BlackBlood, RingOfTheSerpent, 
//            FrozenCore

public abstract class AbstractRelic
    implements Comparable
{
    public static final class RelicTier extends Enum
    {

        public static RelicTier[] values()
        {
            return (RelicTier[])$VALUES.clone();
        }

        public static RelicTier valueOf(String name)
        {
            return (RelicTier)Enum.valueOf(com/megacrit/cardcrawl/relics/AbstractRelic$RelicTier, name);
        }

        public static final RelicTier DEPRECATED;
        public static final RelicTier STARTER;
        public static final RelicTier COMMON;
        public static final RelicTier UNCOMMON;
        public static final RelicTier RARE;
        public static final RelicTier SPECIAL;
        public static final RelicTier BOSS;
        public static final RelicTier SHOP;
        private static final RelicTier $VALUES[];

        static 
        {
            DEPRECATED = new RelicTier("DEPRECATED", 0);
            STARTER = new RelicTier("STARTER", 1);
            COMMON = new RelicTier("COMMON", 2);
            UNCOMMON = new RelicTier("UNCOMMON", 3);
            RARE = new RelicTier("RARE", 4);
            SPECIAL = new RelicTier("SPECIAL", 5);
            BOSS = new RelicTier("BOSS", 6);
            SHOP = new RelicTier("SHOP", 7);
            $VALUES = (new RelicTier[] {
                DEPRECATED, STARTER, COMMON, UNCOMMON, RARE, SPECIAL, BOSS, SHOP
            });
        }

        private RelicTier(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class LandingSound extends Enum
    {

        public static LandingSound[] values()
        {
            return (LandingSound[])$VALUES.clone();
        }

        public static LandingSound valueOf(String name)
        {
            return (LandingSound)Enum.valueOf(com/megacrit/cardcrawl/relics/AbstractRelic$LandingSound, name);
        }

        public static final LandingSound CLINK;
        public static final LandingSound FLAT;
        public static final LandingSound HEAVY;
        public static final LandingSound MAGICAL;
        public static final LandingSound SOLID;
        private static final LandingSound $VALUES[];

        static 
        {
            CLINK = new LandingSound("CLINK", 0);
            FLAT = new LandingSound("FLAT", 1);
            HEAVY = new LandingSound("HEAVY", 2);
            MAGICAL = new LandingSound("MAGICAL", 3);
            SOLID = new LandingSound("SOLID", 4);
            $VALUES = (new LandingSound[] {
                CLINK, FLAT, HEAVY, MAGICAL, SOLID
            });
        }

        private LandingSound(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractRelic(String setId, String imgName, RelicTier tier, LandingSound sfx)
    {
        energyBased = false;
        usedUp = false;
        grayscale = false;
        flavorText = "missing";
        counter = -1;
        tips = new ArrayList();
        imgUrl = "";
        flashColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        goldOutlineColor = new Color(1.0F, 0.9F, 0.4F, 0.0F);
        isSeen = false;
        scale = Settings.scale;
        pulse = false;
        glowTimer = 0.0F;
        flashTimer = 0.0F;
        f_effect = new FloatyEffect(10F, 0.2F);
        isDone = false;
        isAnimating = false;
        isObtained = false;
        landingSFX = LandingSound.CLINK;
        hb = new Hitbox(PAD_X, PAD_X);
        rotation = 0.0F;
        discarded = false;
        relicId = setId;
        relicStrings = CardCrawlGame.languagePack.getRelicStrings(relicId);
        DESCRIPTIONS = relicStrings.DESCRIPTIONS;
        imgUrl = imgName;
        ImageMaster.loadRelicImg(setId, imgName);
        img = ImageMaster.getRelicImg(setId);
        outlineImg = ImageMaster.getRelicOutlineImg(setId);
        name = relicStrings.NAME;
        description = getUpdatedDescription();
        flavorText = relicStrings.FLAVOR;
        this.tier = tier;
        landingSFX = sfx;
        assetURL = (new StringBuilder()).append("images/relics/").append(imgName).toString();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void usedUp()
    {
        grayscale = true;
        usedUp = true;
        description = MSG[2];
        tips.clear();
        tips.add(new PowerTip(name, description));
        initializeTips();
    }

    public void spawn(float x, float y)
    {
        if(!(AbstractDungeon.getCurrRoom() instanceof ShopRoom))
            AbstractDungeon.effectsQueue.add(new SmokePuffEffect(x, y));
        currentX = x;
        currentY = y;
        isAnimating = true;
        isObtained = false;
        if(tier == RelicTier.BOSS)
        {
            f_effect.x = 0.0F;
            f_effect.y = 0.0F;
            targetX = x;
            targetY = y;
            glowTimer = 0.0F;
        } else
        {
            f_effect.x = 0.0F;
            f_effect.y = 0.0F;
        }
    }

    public int getPrice()
    {
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[];
            static final int $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound = new int[LandingSound.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[LandingSound.CLINK.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[LandingSound.FLAT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[LandingSound.SOLID.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[LandingSound.HEAVY.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$LandingSound[LandingSound.MAGICAL.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror4) { }
                $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier = new int[RelicTier.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.STARTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror5) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.COMMON.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror6) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.UNCOMMON.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror7) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.RARE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror8) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.SHOP.ordinal()] = 5;
                }
                catch(NoSuchFieldError nosuchfielderror9) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.SPECIAL.ordinal()] = 6;
                }
                catch(NoSuchFieldError nosuchfielderror10) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.BOSS.ordinal()] = 7;
                }
                catch(NoSuchFieldError nosuchfielderror11) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$relics$AbstractRelic$RelicTier[RelicTier.DEPRECATED.ordinal()] = 8;
                }
                catch(NoSuchFieldError nosuchfielderror12) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier[tier.ordinal()])
        {
        case 1: // '\001'
            return 300;

        case 2: // '\002'
            return 150;

        case 3: // '\003'
            return 250;

        case 4: // '\004'
            return 300;

        case 5: // '\005'
            return 150;

        case 6: // '\006'
            return 400;

        case 7: // '\007'
            return 999;

        case 8: // '\b'
            return -1;
        }
        return -1;
    }

    public void reorganizeObtain(AbstractPlayer p, int slot, boolean callOnEquip, int relicAmount)
    {
        isDone = true;
        isObtained = true;
        p.relics.add(this);
        currentX = START_X + (float)slot * PAD_X;
        currentY = START_Y;
        targetX = currentX;
        targetY = currentY;
        hb.move(currentX, currentY);
        if(callOnEquip)
        {
            onEquip();
            relicTip();
        }
        UnlockTracker.markRelicAsSeen(relicId);
    }

    public void instantObtain(AbstractPlayer p, int slot, boolean callOnEquip)
    {
        if(relicId.equals("Circlet") && p != null && p.hasRelic("Circlet"))
        {
            AbstractRelic circ = p.getRelic("Circlet");
            circ.counter++;
            circ.flash();
            isDone = true;
            isObtained = true;
            discarded = true;
        } else
        {
            isDone = true;
            isObtained = true;
            if(slot >= p.relics.size())
                p.relics.add(this);
            else
                p.relics.set(slot, this);
            currentX = START_X + (float)slot * PAD_X;
            currentY = START_Y;
            targetX = currentX;
            targetY = currentY;
            hb.move(currentX, currentY);
            if(callOnEquip)
            {
                onEquip();
                relicTip();
            }
            UnlockTracker.markRelicAsSeen(relicId);
            getUpdatedDescription();
            if(AbstractDungeon.topPanel != null)
                AbstractDungeon.topPanel.adjustRelicHbs();
        }
    }

    public void instantObtain()
    {
        if(relicId == "Circlet" && AbstractDungeon.player.hasRelic("Circlet"))
        {
            AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
            circ.counter++;
            circ.flash();
        } else
        {
            playLandingSFX();
            isDone = true;
            isObtained = true;
            currentX = START_X + (float)AbstractDungeon.player.relics.size() * PAD_X;
            currentY = START_Y;
            targetX = currentX;
            targetY = currentY;
            flash();
            AbstractDungeon.player.relics.add(this);
            hb.move(currentX, currentY);
            onEquip();
            relicTip();
            UnlockTracker.markRelicAsSeen(relicId);
        }
        if(AbstractDungeon.topPanel != null)
            AbstractDungeon.topPanel.adjustRelicHbs();
    }

    public void obtain()
    {
        if(relicId == "Circlet" && AbstractDungeon.player.hasRelic("Circlet"))
        {
            AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
            circ.counter++;
            circ.flash();
            hb.hovered = false;
        } else
        {
            hb.hovered = false;
            int row = AbstractDungeon.player.relics.size();
            targetX = START_X + (float)row * PAD_X;
            targetY = START_Y;
            AbstractDungeon.player.relics.add(this);
            relicTip();
            UnlockTracker.markRelicAsSeen(relicId);
        }
    }

    public int getColumn()
    {
        return AbstractDungeon.player.relics.indexOf(this);
    }

    public void relicTip()
    {
        if(TipTracker.relicCounter < 20)
        {
            TipTracker.relicCounter++;
            if(TipTracker.relicCounter >= 1 && !((Boolean)TipTracker.tips.get("RELIC_TIP")).booleanValue())
            {
                AbstractDungeon.ftue = new FtueTip(LABEL[0], MSG[0], 360F * Settings.scale, 760F * Settings.scale, com.megacrit.cardcrawl.ui.FtueTip.TipType.RELIC);
                TipTracker.neverShowAgain("RELIC_TIP");
            }
        }
    }

    public void setCounter(int counter)
    {
        this.counter = counter;
    }

    public void update()
    {
        updateFlash();
        if(!isDone)
        {
            if(isAnimating)
            {
                glowTimer -= Gdx.graphics.getDeltaTime();
                if(glowTimer < 0.0F)
                {
                    glowTimer = 0.5F;
                    AbstractDungeon.effectList.add(new GlowRelicParticle(img, currentX + f_effect.x, currentY + f_effect.y, rotation));
                }
                f_effect.update();
                if(hb.hovered)
                    scale = Settings.scale * 1.5F;
                else
                    scale = MathHelper.scaleLerpSnap(scale, Settings.scale * 1.1F);
            } else
            if(hb.hovered)
                scale = Settings.scale * 1.25F;
            else
                scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
            if(isObtained)
            {
                if(rotation != 0.0F)
                    rotation = MathUtils.lerp(rotation, 0.0F, Gdx.graphics.getDeltaTime() * 6F * 2.0F);
                if(currentX != targetX)
                {
                    currentX = MathUtils.lerp(currentX, targetX, Gdx.graphics.getDeltaTime() * 6F);
                    if(Math.abs(currentX - targetX) < 0.5F)
                        currentX = targetX;
                }
                if(currentY != targetY)
                {
                    currentY = MathUtils.lerp(currentY, targetY, Gdx.graphics.getDeltaTime() * 6F);
                    if(Math.abs(currentY - targetY) < 0.5F)
                        currentY = targetY;
                }
                if(currentY == targetY && currentX == targetX)
                {
                    isDone = true;
                    if(AbstractDungeon.topPanel != null)
                        AbstractDungeon.topPanel.adjustRelicHbs();
                    hb.move(currentX, currentY);
                    if(tier == RelicTier.BOSS && (AbstractDungeon.getCurrRoom() instanceof TreasureRoomBoss))
                        AbstractDungeon.overlayMenu.proceedButton.show();
                    onEquip();
                }
                scale = Settings.scale;
            }
            if(hb != null)
            {
                hb.update();
                if(hb.hovered && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD) && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK)
                {
                    if(InputHelper.justClickedLeft && !isObtained)
                    {
                        InputHelper.justClickedLeft = false;
                        hb.clickStarted = true;
                    }
                    if((hb.clicked || CInputActionSet.select.isJustPressed()) && !isObtained)
                    {
                        CInputActionSet.select.unpress();
                        hb.clicked = false;
                        if(!Settings.isTouchScreen)
                        {
                            bossObtainLogic();
                        } else
                        {
                            AbstractDungeon.bossRelicScreen.confirmButton.show();
                            AbstractDungeon.bossRelicScreen.confirmButton.isDisabled = false;
                            AbstractDungeon.bossRelicScreen.touchRelic = this;
                        }
                    }
                }
            }
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
                updateAnimation();
        } else
        {
            if(AbstractDungeon.player != null && AbstractDungeon.player.relics.indexOf(this) / MAX_RELICS_PER_PAGE == relicPage)
                hb.update();
            else
                hb.hovered = false;
            if(hb.hovered && AbstractDungeon.topPanel.potionUi.isHidden)
            {
                scale = Settings.scale * 1.25F;
                CardCrawlGame.cursor.changeType(com.megacrit.cardcrawl.core.GameCursor.CursorType.INSPECT);
            } else
            {
                scale = MathHelper.scaleLerpSnap(scale, Settings.scale);
            }
            updateRelicPopupClick();
        }
    }

    public void bossObtainLogic()
    {
        if(!relicId.equals("HolyWater") && !relicId.equals("Black Blood") && !relicId.equals("Ring of the Serpent") && !relicId.equals("FrozenCore"))
            obtain();
        isObtained = true;
        f_effect.x = 0.0F;
        f_effect.y = 0.0F;
    }

    private void updateRelicPopupClick()
    {
        if(hb.hovered && InputHelper.justClickedLeft)
            hb.clickStarted = true;
        if(hb.clicked || hb.hovered && CInputActionSet.select.isJustPressed())
        {
            CardCrawlGame.relicPopup.open(this, AbstractDungeon.player.relics);
            CInputActionSet.select.unpress();
            hb.clicked = false;
            hb.clickStarted = false;
        }
    }

    public void updateDescription(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass playerclass)
    {
    }

    public String getUpdatedDescription()
    {
        return "";
    }

    public void playLandingSFX()
    {
        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.relics.AbstractRelic.LandingSound[landingSFX.ordinal()])
        {
        case 1: // '\001'
            CardCrawlGame.sound.play("RELIC_DROP_CLINK");
            break;

        case 2: // '\002'
            CardCrawlGame.sound.play("RELIC_DROP_FLAT");
            break;

        case 3: // '\003'
            CardCrawlGame.sound.play("RELIC_DROP_ROCKY");
            break;

        case 4: // '\004'
            CardCrawlGame.sound.play("RELIC_DROP_HEAVY");
            break;

        case 5: // '\005'
            CardCrawlGame.sound.play("RELIC_DROP_MAGICAL");
            break;

        default:
            CardCrawlGame.sound.play("RELIC_DROP_CLINK");
            break;
        }
    }

    protected void updateAnimation()
    {
        if(animationTimer != 0.0F)
        {
            animationTimer -= Gdx.graphics.getDeltaTime();
            if(animationTimer < 0.0F)
                animationTimer = 0.0F;
        }
    }

    private void updateFlash()
    {
        if(flashTimer != 0.0F)
        {
            flashTimer -= Gdx.graphics.getDeltaTime();
            if(flashTimer < 0.0F)
                if(pulse)
                    flashTimer = 1.0F;
                else
                    flashTimer = 0.0F;
        }
    }

    public void onEvokeOrb(AbstractOrb abstractorb)
    {
    }

    public void onPlayCard(AbstractCard abstractcard, AbstractMonster abstractmonster)
    {
    }

    public void onPreviewObtainCard(AbstractCard abstractcard)
    {
    }

    public void onObtainCard(AbstractCard abstractcard)
    {
    }

    public void onGainGold()
    {
    }

    public void onLoseGold()
    {
    }

    public void onSpendGold()
    {
    }

    public void onEquip()
    {
    }

    public void onUnequip()
    {
    }

    public void atPreBattle()
    {
    }

    public void atBattleStart()
    {
    }

    public void onSpawnMonster(AbstractMonster abstractmonster)
    {
    }

    public void atBattleStartPreDraw()
    {
    }

    public void atTurnStart()
    {
    }

    public void atTurnStartPostDraw()
    {
    }

    public void onPlayerEndTurn()
    {
    }

    public void onBloodied()
    {
    }

    public void onNotBloodied()
    {
    }

    public void onManualDiscard()
    {
    }

    public void onUseCard(AbstractCard abstractcard, UseCardAction usecardaction)
    {
    }

    public void onVictory()
    {
    }

    public void onMonsterDeath(AbstractMonster abstractmonster)
    {
    }

    public void onBlockBroken(AbstractCreature abstractcreature)
    {
    }

    public int onPlayerGainBlock(int blockAmount)
    {
        return blockAmount;
    }

    public int onPlayerGainedBlock(float blockAmount)
    {
        return MathUtils.floor(blockAmount);
    }

    public int onPlayerHeal(int healAmount)
    {
        return healAmount;
    }

    public void onMeditate()
    {
    }

    public void onEnergyRecharge()
    {
    }

    public void addCampfireOption(ArrayList arraylist)
    {
    }

    public boolean canUseCampfireOption(AbstractCampfireOption option)
    {
        return true;
    }

    public void onRest()
    {
    }

    public void onRitual()
    {
    }

    public void onEnterRestRoom()
    {
    }

    public void onRefreshHand()
    {
    }

    public void onShuffle()
    {
    }

    public void onSmith()
    {
    }

    public void onAttack(DamageInfo damageinfo, int i, AbstractCreature abstractcreature)
    {
    }

    public int onAttacked(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public int onAttackedToChangeDamage(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public int onAttackToChangeDamage(DamageInfo info, int damageAmount)
    {
        return damageAmount;
    }

    public void onExhaust(AbstractCard abstractcard)
    {
    }

    public void onTrigger()
    {
    }

    public void onTrigger(AbstractCreature abstractcreature)
    {
    }

    public boolean checkTrigger()
    {
        return false;
    }

    public void onEnterRoom(AbstractRoom abstractroom)
    {
    }

    public void justEnteredRoom(AbstractRoom abstractroom)
    {
    }

    public void onCardDraw(AbstractCard abstractcard)
    {
    }

    public void onChestOpen(boolean flag)
    {
    }

    public void onChestOpenAfter(boolean flag)
    {
    }

    public void onDrawOrDiscard()
    {
    }

    public void onMasterDeckChange()
    {
    }

    public float atDamageModify(float damage, AbstractCard c)
    {
        return damage;
    }

    public int changeNumberOfCardsInReward(int numberOfCards)
    {
        return numberOfCards;
    }

    public int changeRareCardRewardChance(int rareCardChance)
    {
        return rareCardChance;
    }

    public int changeUncommonCardRewardChance(int uncommonCardChance)
    {
        return uncommonCardChance;
    }

    public void renderInTopPanel(SpriteBatch sb)
    {
        if(Settings.hideRelics)
            return;
        renderOutline(sb, true);
        if(grayscale)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.GRAYSCALE);
        sb.setColor(Color.WHITE);
        sb.draw(img, (currentX - 64F) + offsetX, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        if(grayscale)
            ShaderHelper.setShader(sb, com.megacrit.cardcrawl.helpers.ShaderHelper.Shader.DEFAULT);
        renderCounter(sb, true);
        renderFlash(sb, true);
        hb.render(sb);
    }

    public void render(SpriteBatch sb)
    {
        if(Settings.hideRelics)
            return;
        renderOutline(sb, false);
        if(!isObtained && (!AbstractDungeon.isScreenUp || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD || AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP))
        {
            if(hb.hovered)
                renderBossTip(sb);
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
                if(hb.hovered)
                {
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    sb.draw(outlineImg, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                } else
                {
                    sb.setColor(PASSIVE_OUTLINE_COLOR);
                    sb.draw(outlineImg, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                }
        }
        if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
        {
            if(!isObtained)
            {
                sb.setColor(Color.WHITE);
                sb.draw(img, (currentX - 64F) + f_effect.x, (currentY - 64F) + f_effect.y, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            } else
            {
                sb.setColor(Color.WHITE);
                sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
                renderCounter(sb, false);
            }
        } else
        {
            sb.setColor(Color.WHITE);
            sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            renderCounter(sb, false);
        }
        if(isDone)
            renderFlash(sb, false);
        hb.render(sb);
    }

    public void renderLock(SpriteBatch sb, Color outlineColor)
    {
        sb.setColor(outlineColor);
        sb.draw(ImageMaster.RELIC_LOCK_OUTLINE, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.RELIC_LOCK, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        if(hb.hovered)
        {
            String unlockReq = (String)UnlockTracker.unlockReqs.get(relicId);
            if(unlockReq == null)
                unlockReq = "Missing unlock req.";
            unlockReq = LABEL[2];
            if((float)InputHelper.mX < 1400F * Settings.scale)
            {
                if(CardCrawlGame.mainMenuScreen.screen == com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.RELIC_VIEW && (float)InputHelper.mY < (float)Settings.HEIGHT / 5F)
                    TipHelper.renderGenericTip((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY + 100F * Settings.scale, LABEL[3], unlockReq);
                else
                    TipHelper.renderGenericTip((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, LABEL[3], unlockReq);
            } else
            {
                TipHelper.renderGenericTip((float)InputHelper.mX - 350F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, LABEL[3], unlockReq);
            }
            float tmpX = currentX;
            float tmpY = currentY;
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
            {
                tmpX += f_effect.x;
                tmpY += f_effect.y;
            }
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.RELIC_LOCK, tmpX - 64F, tmpY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
        hb.render(sb);
    }

    public void render(SpriteBatch sb, boolean renderAmount, Color outlineColor)
    {
        if(isSeen)
            renderOutline(outlineColor, sb, false);
        else
            renderOutline(Color.LIGHT_GRAY, sb, false);
        if(isSeen)
            sb.setColor(Color.WHITE);
        else
        if(hb.hovered)
            sb.setColor(Settings.HALF_TRANSPARENT_BLACK_COLOR);
        else
            sb.setColor(Color.BLACK);
        if(AbstractDungeon.screen != null && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK)
        {
            if(largeImg == null)
                sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, rotation, 0, 0, 128, 128, false, false);
            else
                sb.draw(largeImg, currentX - 128F, currentY - 128F, 128F, 128F, 256F, 256F, Settings.scale * 1.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 30F, Settings.scale * 1.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 30F, rotation, 0, 0, 256, 256, false, false);
        } else
        {
            sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            if(relicId.equals("Circlet"))
                renderCounter(sb, false);
        }
        if(hb.hovered && !CardCrawlGame.relicPopup.isOpen)
        {
            if(!isSeen)
            {
                if((float)InputHelper.mX < 1400F * Settings.scale)
                    TipHelper.renderGenericTip((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, LABEL[1], MSG[1]);
                else
                    TipHelper.renderGenericTip((float)InputHelper.mX - 350F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, LABEL[1], MSG[1]);
                return;
            }
            renderTip(sb);
        }
        hb.render(sb);
    }

    public void renderWithoutAmount(SpriteBatch sb, Color c)
    {
        renderOutline(c, sb, false);
        sb.setColor(Color.WHITE);
        sb.draw(img, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        if(hb.hovered)
        {
            renderTip(sb);
            float tmpX = currentX;
            float tmpY = currentY;
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
            {
                tmpX += f_effect.x;
                tmpY += f_effect.y;
            }
            sb.setColor(Color.WHITE);
            sb.draw(img, tmpX - 64F, tmpY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
        hb.render(sb);
    }

    public void renderCounter(SpriteBatch sb, boolean inTopPanel)
    {
        if(counter > -1)
            if(inTopPanel)
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(counter), offsetX + currentX + 30F * Settings.scale, currentY - 7F * Settings.scale, Color.WHITE);
            else
                FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(counter), currentX + 30F * Settings.scale, currentY - 7F * Settings.scale, Color.WHITE);
    }

    public void renderOutline(Color c, SpriteBatch sb, boolean inTopPanel)
    {
        sb.setColor(c);
        if(AbstractDungeon.screen != null && AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.NEOW_UNLOCK)
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, Settings.scale * 2.0F + MathUtils.cosDeg((System.currentTimeMillis() / 5L) % 360L) / 15F, rotation, 0, 0, 128, 128, false, false);
        else
        if(hb.hovered && Settings.isControllerMode)
        {
            sb.setBlendFunction(770, 1);
            goldOutlineColor.a = 0.6F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
            sb.setColor(goldOutlineColor);
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        } else
        {
            sb.draw(outlineImg, currentX - 64F, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
    }

    public void renderOutline(SpriteBatch sb, boolean inTopPanel)
    {
        float tmpX = currentX - 64F;
        if(inTopPanel)
            tmpX += offsetX;
        if(hb.hovered && Settings.isControllerMode)
        {
            sb.setBlendFunction(770, 1);
            goldOutlineColor.a = 0.6F + MathUtils.cosDeg((System.currentTimeMillis() / 2L) % 360L) / 5F;
            sb.setColor(goldOutlineColor);
            sb.draw(outlineImg, tmpX, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
            sb.setBlendFunction(770, 771);
        } else
        {
            sb.setColor(PASSIVE_OUTLINE_COLOR);
            sb.draw(outlineImg, tmpX, currentY - 64F, 64F, 64F, 128F, 128F, scale, scale, rotation, 0, 0, 128, 128, false, false);
        }
    }

    public void renderFlash(SpriteBatch sb, boolean inTopPanel)
    {
        float tmp = Interpolation.exp10In.apply(0.0F, 4F, flashTimer / 2.0F);
        sb.setBlendFunction(770, 1);
        flashColor.a = flashTimer * 0.2F;
        sb.setColor(flashColor);
        float tmpX = currentX - 64F;
        if(inTopPanel)
            tmpX += offsetX;
        sb.draw(img, tmpX, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp, scale + tmp, rotation, 0, 0, 128, 128, false, false);
        sb.draw(img, tmpX, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp * 0.66F, scale + tmp * 0.66F, rotation, 0, 0, 128, 128, false, false);
        sb.draw(img, tmpX, currentY - 64F, 64F, 64F, 128F, 128F, scale + tmp / 3F, scale + tmp / 3F, rotation, 0, 0, 128, 128, false, false);
        sb.setBlendFunction(770, 771);
    }

    public void beginPulse()
    {
        flashTimer = 1.0F;
    }

    public void beginLongPulse()
    {
        flashTimer = 1.0F;
        pulse = true;
    }

    public void stopPulse()
    {
        pulse = false;
    }

    public void flash()
    {
        flashTimer = 2.0F;
    }

    public void renderBossTip(SpriteBatch sb)
    {
        TipHelper.queuePowerTips((float)Settings.WIDTH * 0.63F, (float)Settings.HEIGHT * 0.63F, tips);
    }

    public void renderTip(SpriteBatch sb)
    {
        if((float)InputHelper.mX < 1400F * Settings.scale)
        {
            if(CardCrawlGame.mainMenuScreen.screen == com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.RELIC_VIEW)
                TipHelper.queuePowerTips(180F * Settings.scale, (float)Settings.HEIGHT * 0.7F, tips);
            else
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.SHOP && tips.size() > 2 && !AbstractDungeon.player.hasRelic(relicId))
                TipHelper.queuePowerTips((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY + 180F * Settings.scale, tips);
            else
            if(AbstractDungeon.player != null && AbstractDungeon.player.hasRelic(relicId))
                TipHelper.queuePowerTips((float)InputHelper.mX + 60F * Settings.scale, (float)InputHelper.mY - 30F * Settings.scale, tips);
            else
            if(AbstractDungeon.screen == com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.COMBAT_REWARD)
                TipHelper.queuePowerTips(360F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, tips);
            else
                TipHelper.queuePowerTips((float)InputHelper.mX + 50F * Settings.scale, (float)InputHelper.mY + 50F * Settings.scale, tips);
        } else
        {
            TipHelper.queuePowerTips((float)InputHelper.mX - 350F * Settings.scale, (float)InputHelper.mY - 50F * Settings.scale, tips);
        }
    }

    public boolean canPlay(AbstractCard card)
    {
        return true;
    }

    public static String gameDataUploadHeader()
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData("name");
        builder.addFieldData("relicID");
        builder.addFieldData("color");
        builder.addFieldData("description");
        builder.addFieldData("flavorText");
        builder.addFieldData("cost");
        builder.addFieldData("tier");
        builder.addFieldData("assetURL");
        return builder.toString();
    }

    protected void initializeTips()
    {
        Scanner desc = new Scanner(description);
        do
        {
            if(!desc.hasNext())
                break;
            String s = desc.next();
            if(s.charAt(0) == '#')
                s = s.substring(2);
            s = s.replace(',', ' ');
            s = s.replace('.', ' ');
            s = s.trim();
            s = s.toLowerCase();
            boolean alreadyExists = false;
            if(!GameDictionary.keywords.containsKey(s))
                continue;
            s = (String)GameDictionary.parentWord.get(s);
            Iterator iterator = tips.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                PowerTip t = (PowerTip)iterator.next();
                if(!t.header.toLowerCase().equals(s))
                    continue;
                alreadyExists = true;
                break;
            } while(true);
            if(!alreadyExists)
                tips.add(new PowerTip(TipHelper.capitalize(s), (String)GameDictionary.keywords.get(s)));
        } while(true);
        desc.close();
    }

    public String gameDataUploadData(String color)
    {
        GameDataStringBuilder builder = new GameDataStringBuilder();
        builder.addFieldData(name);
        builder.addFieldData(relicId);
        builder.addFieldData(color);
        builder.addFieldData(description);
        builder.addFieldData(flavorText);
        builder.addFieldData(cost);
        builder.addFieldData(tier.name());
        builder.addFieldData(assetURL);
        return builder.toString();
    }

    public abstract AbstractRelic makeCopy();

    public String toString()
    {
        return name;
    }

    public int compareTo(AbstractRelic arg0)
    {
        return name.compareTo(arg0.name);
    }

    public String getAssetURL()
    {
        return assetURL;
    }

    public HashMap getLocStrings()
    {
        HashMap relicData = new HashMap();
        relicData.put("name", name);
        relicData.put("description", description);
        return relicData;
    }

    public boolean canSpawn()
    {
        return true;
    }

    public void onUsePotion()
    {
    }

    public void onChangeStance(AbstractStance abstractstance, AbstractStance abstractstance1)
    {
    }

    public void onLoseHp(int i)
    {
    }

    public static void updateOffsetX()
    {
        float target = (float)(-relicPage * Settings.WIDTH) + (float)relicPage * (PAD_X + 36F * Settings.xScale);
        if(AbstractDungeon.player.relics.size() >= MAX_RELICS_PER_PAGE + 1)
            target += 36F * Settings.scale;
        if(offsetX != target)
            offsetX = MathHelper.uiLerpSnap(offsetX, target);
    }

    public void loadLargeImg()
    {
        if(largeImg == null)
            largeImg = ImageMaster.loadImage((new StringBuilder()).append("images/largeRelics/").append(imgUrl).toString());
    }

    protected void addToBot(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToBottom(action);
    }

    protected void addToTop(AbstractGameAction action)
    {
        AbstractDungeon.actionManager.addToTop(action);
    }

    public int onLoseHpLast(int damageAmount)
    {
        return damageAmount;
    }

    public void wasHPLost(int i)
    {
    }

    public volatile int compareTo(Object obj)
    {
        return compareTo((AbstractRelic)obj);
    }

    private static final TutorialStrings tutorialStrings;
    public static final String MSG[];
    public static final String LABEL[];
    public static final String USED_UP_MSG;
    public final String name;
    public final String relicId;
    private final RelicStrings relicStrings;
    public final String DESCRIPTIONS[];
    public boolean energyBased;
    public boolean usedUp;
    public boolean grayscale;
    public String description;
    public String flavorText;
    public int cost;
    public int counter;
    public RelicTier tier;
    public ArrayList tips;
    public Texture img;
    public Texture largeImg;
    public Texture outlineImg;
    public static final String IMG_DIR = "images/relics/";
    public static final String OUTLINE_DIR = "images/relics/outline/";
    private static final String L_IMG_DIR = "images/largeRelics/";
    public String imgUrl;
    public static final int RAW_W = 128;
    public static int relicPage = 0;
    private static float offsetX = 0.0F;
    public static final int MAX_RELICS_PER_PAGE;
    public float currentX;
    public float currentY;
    public float targetX;
    public float targetY;
    private static final float START_X;
    private static final float START_Y;
    public static final float PAD_X;
    public static final Color PASSIVE_OUTLINE_COLOR = new Color(0.0F, 0.0F, 0.0F, 0.33F);
    private Color flashColor;
    private Color goldOutlineColor;
    public boolean isSeen;
    public float scale;
    protected boolean pulse;
    private float animationTimer;
    private float glowTimer;
    public float flashTimer;
    private static final float FLASH_ANIM_TIME = 2F;
    private static final float DEFAULT_ANIM_SCALE = 4F;
    private FloatyEffect f_effect;
    public boolean isDone;
    public boolean isAnimating;
    public boolean isObtained;
    private LandingSound landingSFX;
    public Hitbox hb;
    private static final float OBTAIN_SPEED = 6F;
    private static final float OBTAIN_THRESHOLD = 0.5F;
    private float rotation;
    public boolean discarded;
    private String assetURL;

    static 
    {
        tutorialStrings = CardCrawlGame.languagePack.getTutorialString("Relic Tip");
        MSG = tutorialStrings.TEXT;
        LABEL = tutorialStrings.LABEL;
        USED_UP_MSG = MSG[2];
        MAX_RELICS_PER_PAGE = (int)((float)Settings.WIDTH / (75F * Settings.scale));
        START_X = 64F * Settings.scale;
        START_Y = Settings.isMobile ? (float)Settings.HEIGHT - 132F * Settings.scale : (float)Settings.HEIGHT - 102F * Settings.scale;
        PAD_X = 72F * Settings.scale;
    }
}
