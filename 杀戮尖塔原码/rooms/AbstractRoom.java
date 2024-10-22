// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   AbstractRoom.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.audio.MusicMaster;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.blights.AbstractBlight;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.dungeons.*;
import com.megacrit.cardcrawl.events.*;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.*;
import com.megacrit.cardcrawl.integrations.PublisherIntegration;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.metrics.MetricData;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.potions.BlessingOfTheForge;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.rewards.RewardItem;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.saveAndContinue.SaveFile;
import com.megacrit.cardcrawl.scenes.AbstractScene;
import com.megacrit.cardcrawl.screens.CombatRewardScreen;
import com.megacrit.cardcrawl.screens.options.SettingsScreen;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.stances.AbstractStance;
import com.megacrit.cardcrawl.stances.NeutralStance;
import com.megacrit.cardcrawl.ui.buttons.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.vfx.GameSavedEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndObtainEffect;
import com.megacrit.cardcrawl.vfx.combat.BattleStartEffect;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            MonsterRoomBoss, MonsterRoomElite, MonsterRoom, EventRoom, 
//            VictoryRoom, RestRoom

public abstract class AbstractRoom
    implements Disposable
{
    public static final class RoomType extends Enum
    {

        public static RoomType[] values()
        {
            return (RoomType[])$VALUES.clone();
        }

        public static RoomType valueOf(String name)
        {
            return (RoomType)Enum.valueOf(com/megacrit/cardcrawl/rooms/AbstractRoom$RoomType, name);
        }

        public static final RoomType SHOP;
        public static final RoomType MONSTER;
        public static final RoomType SHRINE;
        public static final RoomType TREASURE;
        public static final RoomType EVENT;
        public static final RoomType BOSS;
        private static final RoomType $VALUES[];

        static 
        {
            SHOP = new RoomType("SHOP", 0);
            MONSTER = new RoomType("MONSTER", 1);
            SHRINE = new RoomType("SHRINE", 2);
            TREASURE = new RoomType("TREASURE", 3);
            EVENT = new RoomType("EVENT", 4);
            BOSS = new RoomType("BOSS", 5);
            $VALUES = (new RoomType[] {
                SHOP, MONSTER, SHRINE, TREASURE, EVENT, BOSS
            });
        }

        private RoomType(String s, int i)
        {
            super(s, i);
        }
    }

    public static final class RoomPhase extends Enum
    {

        public static RoomPhase[] values()
        {
            return (RoomPhase[])$VALUES.clone();
        }

        public static RoomPhase valueOf(String name)
        {
            return (RoomPhase)Enum.valueOf(com/megacrit/cardcrawl/rooms/AbstractRoom$RoomPhase, name);
        }

        public static final RoomPhase COMBAT;
        public static final RoomPhase EVENT;
        public static final RoomPhase COMPLETE;
        public static final RoomPhase INCOMPLETE;
        private static final RoomPhase $VALUES[];

        static 
        {
            COMBAT = new RoomPhase("COMBAT", 0);
            EVENT = new RoomPhase("EVENT", 1);
            COMPLETE = new RoomPhase("COMPLETE", 2);
            INCOMPLETE = new RoomPhase("INCOMPLETE", 3);
            $VALUES = (new RoomPhase[] {
                COMBAT, EVENT, COMPLETE, INCOMPLETE
            });
        }

        private RoomPhase(String s, int i)
        {
            super(s, i);
        }
    }


    public AbstractRoom()
    {
        potions = new ArrayList();
        relics = new ArrayList();
        rewards = new ArrayList();
        souls = new SoulGroup();
        event = null;
        endBattleTimer = 0.0F;
        rewardPopOutTimer = 1.0F;
        isBattleOver = false;
        cannotLose = false;
        eliteTrigger = false;
        mugged = false;
        smoked = false;
        combatEvent = false;
        rewardAllowed = true;
        rewardTime = false;
        skipMonsterTurn = false;
        baseRareCardChance = 3;
        baseUncommonCardChance = 37;
        rareCardChance = baseRareCardChance;
        uncommonCardChance = baseUncommonCardChance;
    }

    public final Texture getMapImg()
    {
        return mapImg;
    }

    public final Texture getMapImgOutline()
    {
        return mapImgOutline;
    }

    public final void setMapImg(Texture img, Texture imgOutline)
    {
        mapImg = img;
        mapImgOutline = imgOutline;
    }

    public abstract void onPlayerEntry();

    public void applyEmeraldEliteBuff()
    {
    }

    public void playBGM(String key)
    {
        CardCrawlGame.music.playTempBGM(key);
    }

    public void playBgmInstantly(String key)
    {
        CardCrawlGame.music.playTempBgmInstantly(key);
    }

    public final String getMapSymbol()
    {
        return mapSymbol;
    }

    public final void setMapSymbol(String newSymbol)
    {
        mapSymbol = newSymbol;
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll)
    {
        return getCardRarity(roll, true);
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardRarity getCardRarity(int roll, boolean useAlternation)
    {
        rareCardChance = baseRareCardChance;
        uncommonCardChance = baseUncommonCardChance;
        if(useAlternation)
            alterCardRarityProbabilities();
        if(roll < rareCardChance)
        {
            if(roll >= baseRareCardChance)
            {
                Iterator iterator = AbstractDungeon.player.relics.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    AbstractRelic r = (AbstractRelic)iterator.next();
                    if(r.changeRareCardRewardChance(baseRareCardChance) > baseRareCardChance)
                        r.flash();
                } while(true);
            }
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE;
        }
        if(roll < rareCardChance + uncommonCardChance)
        {
            if(roll >= baseRareCardChance + baseUncommonCardChance)
            {
                Iterator iterator1 = AbstractDungeon.player.relics.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    AbstractRelic r = (AbstractRelic)iterator1.next();
                    if(r.changeUncommonCardRewardChance(baseUncommonCardChance) > baseUncommonCardChance)
                        r.flash();
                } while(true);
            }
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON;
        } else
        {
            return com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.COMMON;
        }
    }

    public void alterCardRarityProbabilities()
    {
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator.next();
            rareCardChance = r.changeRareCardRewardChance(rareCardChance);
        }

        for(Iterator iterator1 = AbstractDungeon.player.relics.iterator(); iterator1.hasNext();)
        {
            AbstractRelic r = (AbstractRelic)iterator1.next();
            uncommonCardChance = r.changeUncommonCardRewardChance(uncommonCardChance);
        }

    }

    public void updateObjects()
    {
        souls.update();
        Iterator i = potions.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractPotion tmpPotion = (AbstractPotion)i.next();
            tmpPotion.update();
            if(tmpPotion.isObtained)
                i.remove();
        } while(true);
        i = relics.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractRelic relic = (AbstractRelic)i.next();
            relic.update();
            if(relic.isDone)
                i.remove();
        } while(true);
    }

    public void update()
    {
        if(!AbstractDungeon.isScreenUp && InputHelper.pressedEscape && AbstractDungeon.overlayMenu.cancelButton.current_x == CancelButton.HIDE_X)
            AbstractDungeon.settingsScreen.open();
        if(Settings.isDebug)
        {
            if(InputHelper.justClickedRight)
            {
                AbstractDungeon.player.obtainPotion(new BlessingOfTheForge());
                AbstractDungeon.scene.randomizeScene();
            }
            if(Gdx.input.isKeyJustPressed(49))
                AbstractDungeon.player.increaseMaxOrbSlots(1, true);
            if(DevInputActionSet.gainGold.isJustPressed())
                AbstractDungeon.player.gainGold(100);
        }
        static class _cls2
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase = new int[RoomPhase.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase[RoomPhase.EVENT.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase[RoomPhase.COMBAT.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase[RoomPhase.COMPLETE.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$rooms$AbstractRoom$RoomPhase[RoomPhase.INCOMPLETE.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        switch(_cls2..SwitchMap.com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase[phase.ordinal()])
        {
        case 1: // '\001'
            event.updateDialog();
            break;

        case 4: // '\004'
            break;

        case 2: // '\002'
            monsters.update();
            if(waitTimer > 0.0F)
            {
                if(AbstractDungeon.actionManager.currentAction != null || !AbstractDungeon.actionManager.isEmpty())
                    AbstractDungeon.actionManager.update();
                else
                    waitTimer -= Gdx.graphics.getDeltaTime();
                if(waitTimer <= 0.0F)
                {
                    AbstractDungeon.actionManager.turnHasEnded = true;
                    if(!AbstractDungeon.isScreenUp)
                        AbstractDungeon.topLevelEffects.add(new BattleStartEffect(false));
                    AbstractDungeon.actionManager.addToBottom(new GainEnergyAndEnableControlsAction(AbstractDungeon.player.energy.energyMaster));
                    AbstractDungeon.player.applyStartOfCombatPreDrawLogic();
                    AbstractDungeon.actionManager.addToBottom(new DrawCardAction(AbstractDungeon.player, AbstractDungeon.player.gameHandSize));
                    AbstractDungeon.actionManager.addToBottom(new EnableEndTurnButtonAction());
                    AbstractDungeon.overlayMenu.showCombatPanels();
                    AbstractDungeon.player.applyStartOfCombatLogic();
                    if(ModHelper.isModEnabled("Careless"))
                        Careless.modAction();
                    if(ModHelper.isModEnabled("ControlledChaos"))
                        ControlledChaos.modAction();
                    skipMonsterTurn = false;
                    AbstractDungeon.player.applyStartOfTurnRelics();
                    AbstractDungeon.player.applyStartOfTurnPostDrawRelics();
                    AbstractDungeon.player.applyStartOfTurnCards();
                    AbstractDungeon.player.applyStartOfTurnPowers();
                    AbstractDungeon.player.applyStartOfTurnOrbs();
                    AbstractDungeon.actionManager.useNextCombatActions();
                }
            } else
            {
                if(Settings.isDebug && DevInputActionSet.drawCard.isJustPressed())
                    AbstractDungeon.actionManager.addToTop(new DrawCardAction(AbstractDungeon.player, 1));
                if(!AbstractDungeon.isScreenUp)
                {
                    AbstractDungeon.actionManager.update();
                    if(!monsters.areMonstersBasicallyDead() && AbstractDungeon.player.currentHealth > 0)
                        AbstractDungeon.player.updateInput();
                }
                if(!AbstractDungeon.screen.equals(com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.HAND_SELECT))
                    AbstractDungeon.player.combatUpdate();
                if(AbstractDungeon.player.isEndingTurn)
                    endTurn();
            }
            if(isBattleOver && AbstractDungeon.actionManager.actions.isEmpty())
            {
                skipMonsterTurn = false;
                endBattleTimer -= Gdx.graphics.getDeltaTime();
                if(endBattleTimer < 0.0F)
                {
                    phase = RoomPhase.COMPLETE;
                    if(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) || !(CardCrawlGame.dungeon instanceof TheBeyond) || Settings.isEndless)
                        CardCrawlGame.sound.play("VICTORY");
                    endBattleTimer = 0.0F;
                    if((this instanceof MonsterRoomBoss) && !AbstractDungeon.loading_post_combat)
                    {
                        if(!CardCrawlGame.loadingSave)
                            if(Settings.isDailyRun)
                            {
                                addGoldToRewards(100);
                            } else
                            {
                                int tmp = 100 + AbstractDungeon.miscRng.random(-5, 5);
                                if(AbstractDungeon.ascensionLevel >= 13)
                                    addGoldToRewards(MathUtils.round((float)tmp * 0.75F));
                                else
                                    addGoldToRewards(tmp);
                            }
                        if(ModHelper.isModEnabled("Cursed Run"))
                            AbstractDungeon.effectList.add(new ShowCardAndObtainEffect(AbstractDungeon.returnRandomCurse(), (float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    } else
                    if((this instanceof MonsterRoomElite) && !AbstractDungeon.loading_post_combat)
                    {
                        if(CardCrawlGame.dungeon instanceof Exordium)
                        {
                            CardCrawlGame.elites1Slain++;
                            logger.info((new StringBuilder()).append("ELITES SLAIN ").append(CardCrawlGame.elites1Slain).toString());
                        } else
                        if(CardCrawlGame.dungeon instanceof TheCity)
                        {
                            CardCrawlGame.elites2Slain++;
                            logger.info((new StringBuilder()).append("ELITES SLAIN ").append(CardCrawlGame.elites2Slain).toString());
                        } else
                        if(CardCrawlGame.dungeon instanceof TheBeyond)
                        {
                            CardCrawlGame.elites3Slain++;
                            logger.info((new StringBuilder()).append("ELITES SLAIN ").append(CardCrawlGame.elites3Slain).toString());
                        } else
                        {
                            CardCrawlGame.elitesModdedSlain++;
                            logger.info((new StringBuilder()).append("ELITES SLAIN ").append(CardCrawlGame.elitesModdedSlain).toString());
                        }
                        if(!CardCrawlGame.loadingSave)
                            if(Settings.isDailyRun)
                                addGoldToRewards(30);
                            else
                                addGoldToRewards(AbstractDungeon.treasureRng.random(25, 35));
                    } else
                    if((this instanceof MonsterRoom) && !AbstractDungeon.getMonsters().haveMonstersEscaped())
                    {
                        CardCrawlGame.monstersSlain++;
                        logger.info((new StringBuilder()).append("MONSTERS SLAIN ").append(CardCrawlGame.monstersSlain).toString());
                        if(Settings.isDailyRun)
                            addGoldToRewards(15);
                        else
                            addGoldToRewards(AbstractDungeon.treasureRng.random(10, 20));
                    }
                    if(!(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss) || !(CardCrawlGame.dungeon instanceof TheBeyond) && !(CardCrawlGame.dungeon instanceof TheEnding) || Settings.isEndless)
                    {
                        if(!AbstractDungeon.loading_post_combat)
                        {
                            dropReward();
                            addPotionToRewards();
                        }
                        int card_seed_before_roll = AbstractDungeon.cardRng.counter;
                        int card_randomizer_before_roll = AbstractDungeon.cardBlizzRandomizer;
                        if(rewardAllowed)
                        {
                            if(mugged)
                                AbstractDungeon.combatRewardScreen.openCombat(TEXT[0]);
                            else
                            if(smoked)
                                AbstractDungeon.combatRewardScreen.openCombat(TEXT[1], true);
                            else
                                AbstractDungeon.combatRewardScreen.open();
                            if(!CardCrawlGame.loadingSave && !AbstractDungeon.loading_post_combat)
                            {
                                SaveFile saveFile = new SaveFile(com.megacrit.cardcrawl.saveAndContinue.SaveFile.SaveType.POST_COMBAT);
                                saveFile.card_seed_count = card_seed_before_roll;
                                saveFile.card_random_seed_randomizer = card_randomizer_before_roll;
                                if(combatEvent)
                                    saveFile.event_seed_count--;
                                SaveAndContinue.save(saveFile);
                                AbstractDungeon.effectList.add(new GameSavedEffect());
                            } else
                            {
                                CardCrawlGame.loadingSave = false;
                            }
                            AbstractDungeon.loading_post_combat = false;
                        }
                    }
                }
            }
            monsters.updateAnimations();
            break;

        case 3: // '\003'
            if(AbstractDungeon.isScreenUp)
                break;
            AbstractDungeon.actionManager.update();
            if(event != null)
                event.updateDialog();
            if(!AbstractDungeon.actionManager.isEmpty() || AbstractDungeon.isFadingOut)
                break;
            if(rewardPopOutTimer > 1.0F)
                rewardPopOutTimer = 1.0F;
            rewardPopOutTimer -= Gdx.graphics.getDeltaTime();
            if(rewardPopOutTimer >= 0.0F)
                break;
            if(event == null)
            {
                AbstractDungeon.overlayMenu.proceedButton.show();
                break;
            }
            if(!(event instanceof AbstractImageEvent) && !event.hasFocus)
                AbstractDungeon.overlayMenu.proceedButton.show();
            break;

        default:
            logger.info("MISSING PHASE, bro");
            break;
        }
        AbstractDungeon.player.update();
        AbstractDungeon.player.updateAnimations();
    }

    public void endTurn()
    {
        AbstractDungeon.player.applyEndOfTurnTriggers();
        AbstractDungeon.actionManager.addToBottom(new ClearCardQueueAction());
        AbstractDungeon.actionManager.addToBottom(new DiscardAtEndOfTurnAction());
        AbstractCard c;
        for(Iterator iterator = AbstractDungeon.player.drawPile.group.iterator(); iterator.hasNext(); c.resetAttributes())
            c = (AbstractCard)iterator.next();

        AbstractCard c;
        for(Iterator iterator1 = AbstractDungeon.player.discardPile.group.iterator(); iterator1.hasNext(); c.resetAttributes())
            c = (AbstractCard)iterator1.next();

        AbstractCard c;
        for(Iterator iterator2 = AbstractDungeon.player.hand.group.iterator(); iterator2.hasNext(); c.resetAttributes())
            c = (AbstractCard)iterator2.next();

        if(AbstractDungeon.player.hoveredCard != null)
            AbstractDungeon.player.hoveredCard.resetAttributes();
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction() {

            public void update()
            {
                addToBot(new EndTurnAction());
                addToBot(new WaitAction(1.2F));
                if(!skipMonsterTurn)
                    addToBot(new MonsterStartTurnAction());
                AbstractDungeon.actionManager.monsterAttacksQueued = false;
                isDone = true;
            }

            final AbstractRoom this$0;

            
            {
                this.this$0 = AbstractRoom.this;
                super();
            }
        }
);
        AbstractDungeon.player.isEndingTurn = false;
    }

    public void endBattle()
    {
        isBattleOver = true;
        if(AbstractDungeon.player.currentHealth == 1)
            UnlockTracker.unlockAchievement("SHRUG_IT_OFF");
        if(AbstractDungeon.player.hasRelic("Meat on the Bone"))
            AbstractDungeon.player.getRelic("Meat on the Bone").onTrigger();
        AbstractDungeon.player.onVictory();
        endBattleTimer = 0.25F;
        int attackCount = 0;
        int skillCount = 0;
        Iterator iterator = AbstractDungeon.actionManager.cardsPlayedThisCombat.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCard c = (AbstractCard)iterator.next();
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK)
            {
                attackCount++;
                break;
            }
            if(c.type == com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL)
                skillCount++;
        } while(true);
        if(attackCount == 0 && !smoked)
            UnlockTracker.unlockAchievement("COME_AT_ME");
        if(skillCount != 0);
        if(!smoked && GameActionManager.damageReceivedThisCombat - GameActionManager.hpLossThisCombat <= 0 && (this instanceof MonsterRoomElite))
            CardCrawlGame.champion++;
        CardCrawlGame.metricData.addEncounterData();
        AbstractDungeon.actionManager.clear();
        AbstractDungeon.player.inSingleTargetMode = false;
        AbstractDungeon.player.releaseCard();
        AbstractDungeon.player.hand.refreshHandLayout();
        AbstractDungeon.player.resetControllerValues();
        AbstractDungeon.overlayMenu.hideCombatPanels();
        if(!AbstractDungeon.player.stance.ID.equals("Neutral") && AbstractDungeon.player.stance != null)
            AbstractDungeon.player.stance.stopIdleSfx();
    }

    public void dropReward()
    {
    }

    public void render(SpriteBatch sb)
    {
        if((this instanceof EventRoom) || (this instanceof VictoryRoom))
        {
            if(event != null && (!(event instanceof AbstractImageEvent) || event.combatTime))
            {
                event.renderRoomEventPanel(sb);
                if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.VICTORY)
                    AbstractDungeon.player.render(sb);
            }
        } else
        if(AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.BOSS_REWARD)
            AbstractDungeon.player.render(sb);
        if(!(AbstractDungeon.getCurrRoom() instanceof RestRoom))
        {
            if(monsters != null && AbstractDungeon.screen != com.megacrit.cardcrawl.dungeons.AbstractDungeon.CurrentScreen.DEATH)
                monsters.render(sb);
            if(phase == RoomPhase.COMBAT)
                AbstractDungeon.player.renderPlayerBattleUi(sb);
            Iterator iterator = potions.iterator();
            do
            {
                if(!iterator.hasNext())
                    break;
                AbstractPotion i = (AbstractPotion)iterator.next();
                if(!i.isObtained)
                    i.render(sb);
            } while(true);
        }
        AbstractRelic r;
        for(Iterator iterator1 = relics.iterator(); iterator1.hasNext(); r.render(sb))
            r = (AbstractRelic)iterator1.next();

        renderTips(sb);
    }

    public void renderAboveTopPanel(SpriteBatch sb)
    {
        Iterator iterator = potions.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractPotion i = (AbstractPotion)iterator.next();
            if(i.isObtained)
                i.render(sb);
        } while(true);
        souls.render(sb);
        if(Settings.isInfo)
        {
            String msg = (new StringBuilder()).append("[GAME MODE DATA]\n isDaily: ").append(Settings.isDailyRun).append("\n isSpecialSeed: ").append(Settings.isTrial).append("\n isAscension: ").append(AbstractDungeon.isAscensionMode).append("\n\n[CARDGROUPS]\n Deck: ").append(AbstractDungeon.player.masterDeck.size()).append("\n Draw Pile: ").append(AbstractDungeon.player.drawPile.size()).append("\n Discard Pile: ").append(AbstractDungeon.player.discardPile.size()).append("\n Exhaust Pile: ").append(AbstractDungeon.player.exhaustPile.size()).append("\n\n[ACTION MANAGER]\n Phase: ").append(AbstractDungeon.actionManager.phase.name()).append("\n turnEnded: ").append(AbstractDungeon.actionManager.turnHasEnded).append("\n numTurns: ").append(GameActionManager.turn).append("\n\n[Misc]\n Publisher Connection: ").append(CardCrawlGame.publisherIntegration.isInitialized()).append("\n CUR_SCREEN: ").append(AbstractDungeon.screen.name()).append("\n Controller Mode: ").append(Settings.isControllerMode).append("\n isFadingOut: ").append(AbstractDungeon.isFadingOut).append("\n isScreenUp: ").append(AbstractDungeon.isScreenUp).append("\n Particle Count: ").append(AbstractDungeon.effectList.size()).toString();
            FontHelper.renderFontCenteredHeight(sb, FontHelper.tipBodyFont, msg, 30F, (float)Settings.HEIGHT * 0.5F, Color.WHITE);
        }
    }

    public void renderTips(SpriteBatch spritebatch)
    {
    }

    public void spawnRelicAndObtain(float x, float y, AbstractRelic relic)
    {
        if(relic.relicId == "Circlet" && AbstractDungeon.player.hasRelic("Circlet"))
        {
            AbstractRelic circ = AbstractDungeon.player.getRelic("Circlet");
            circ.counter++;
            circ.flash();
        } else
        {
            relic.spawn(x, y);
            relics.add(relic);
            relic.obtain();
            relic.isObtained = true;
            relic.isAnimating = false;
            relic.isDone = false;
            relic.flash();
        }
    }

    public void spawnBlightAndObtain(float x, float y, AbstractBlight blight)
    {
        blight.spawn(x, y);
        blight.obtain();
        blight.isObtained = true;
        blight.isAnimating = false;
        blight.isDone = false;
        blight.flash();
    }

    public void applyEndOfTurnRelics()
    {
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.onPlayerEndTurn())
            r = (AbstractRelic)iterator.next();

        AbstractBlight b;
        for(Iterator iterator1 = AbstractDungeon.player.blights.iterator(); iterator1.hasNext(); b.onPlayerEndTurn())
            b = (AbstractBlight)iterator1.next();

    }

    public void applyEndOfTurnPreCardPowers()
    {
        AbstractPower p;
        for(Iterator iterator = AbstractDungeon.player.powers.iterator(); iterator.hasNext(); p.atEndOfTurnPreEndTurnCards(true))
            p = (AbstractPower)iterator.next();

    }

    public void addRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        rewards.add(new RewardItem(AbstractDungeon.returnRandomRelic(tier)));
    }

    public void addSapphireKey(RewardItem item)
    {
        rewards.add(new RewardItem(item, com.megacrit.cardcrawl.rewards.RewardItem.RewardType.SAPPHIRE_KEY));
    }

    public void removeOneRelicFromRewards()
    {
        Iterator i = rewards.iterator();
        do
        {
            if(!i.hasNext())
                break;
            RewardItem rewardItem = (RewardItem)i.next();
            if(rewardItem.type != com.megacrit.cardcrawl.rewards.RewardItem.RewardType.RELIC)
                continue;
            i.remove();
            if(i.hasNext() && rewardItem.relicLink == i.next())
                i.remove();
            break;
        } while(true);
    }

    public void addNoncampRelicToRewards(com.megacrit.cardcrawl.relics.AbstractRelic.RelicTier tier)
    {
        rewards.add(new RewardItem(AbstractDungeon.returnRandomNonCampfireRelic(tier)));
    }

    public void addRelicToRewards(AbstractRelic relic)
    {
        rewards.add(new RewardItem(relic));
    }

    public void addPotionToRewards(AbstractPotion potion)
    {
        rewards.add(new RewardItem(potion));
    }

    public void addCardToRewards()
    {
        RewardItem cardReward = new RewardItem();
        if(cardReward.cards.size() > 0)
            rewards.add(cardReward);
    }

    public void addPotionToRewards()
    {
        int chance = 0;
        if(this instanceof MonsterRoomElite)
        {
            chance = 40;
            chance += blizzardPotionMod;
        } else
        if(this instanceof MonsterRoom)
        {
            if(!AbstractDungeon.getMonsters().haveMonstersEscaped())
            {
                chance = 40;
                chance += blizzardPotionMod;
            }
        } else
        if(this instanceof EventRoom)
        {
            chance = 40;
            chance += blizzardPotionMod;
        }
        if(AbstractDungeon.player.hasRelic("White Beast Statue"))
            chance = 100;
        if(rewards.size() >= 4)
            chance = 0;
        logger.info((new StringBuilder()).append("POTION CHANCE: ").append(chance).toString());
        if(AbstractDungeon.potionRng.random(0, 99) < chance || Settings.isDebug)
        {
            CardCrawlGame.metricData.potions_floor_spawned.add(Integer.valueOf(AbstractDungeon.floorNum));
            rewards.add(new RewardItem(AbstractDungeon.returnRandomPotion()));
            blizzardPotionMod -= 10;
        } else
        {
            blizzardPotionMod += 10;
        }
    }

    public void addGoldToRewards(int gold)
    {
        for(Iterator iterator = rewards.iterator(); iterator.hasNext();)
        {
            RewardItem i = (RewardItem)iterator.next();
            if(i.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.GOLD)
            {
                i.incrementGold(gold);
                return;
            }
        }

        rewards.add(new RewardItem(gold));
    }

    public void addStolenGoldToRewards(int gold)
    {
        for(Iterator iterator = rewards.iterator(); iterator.hasNext();)
        {
            RewardItem i = (RewardItem)iterator.next();
            if(i.type == com.megacrit.cardcrawl.rewards.RewardItem.RewardType.STOLEN_GOLD)
            {
                i.incrementGold(gold);
                return;
            }
        }

        rewards.add(new RewardItem(gold, true));
    }

    public boolean isBattleEnding()
    {
        if(isBattleOver)
            return true;
        if(monsters != null)
            return monsters.areMonstersBasicallyDead();
        else
            return false;
    }

    public void renderEventTexts(SpriteBatch sb)
    {
        if(event != null)
            event.renderText(sb);
    }

    public void clearEvent()
    {
        if(event != null)
        {
            event.imageEventText.clear();
            event.roomEventText.clear();
        }
    }

    public void eventControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        if(AbstractDungeon.getCurrRoom().event != null && AbstractDungeon.getCurrRoom().phase != RoomPhase.COMBAT && !AbstractDungeon.topPanel.selectPotionMode && AbstractDungeon.topPanel.potionUi.isHidden && !AbstractDungeon.topPanel.potionUi.targetMode && !AbstractDungeon.player.viewingRelics)
            if(!RoomEventDialog.optionList.isEmpty())
            {
                boolean anyHovered = false;
                int index = 0;
                Iterator iterator = RoomEventDialog.optionList.iterator();
                do
                {
                    if(!iterator.hasNext())
                        break;
                    LargeDialogOptionButton o = (LargeDialogOptionButton)iterator.next();
                    if(o.hb.hovered)
                    {
                        anyHovered = true;
                        break;
                    }
                    index++;
                } while(true);
                if(!anyHovered)
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(0)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(0)).hb.cY);
                else
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if(++index > RoomEventDialog.optionList.size() - 1)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(index)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(index)).hb.cY);
                } else
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index < 0)
                        index = RoomEventDialog.optionList.size() - 1;
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(index)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)RoomEventDialog.optionList.get(index)).hb.cY);
                }
            } else
            if(!event.imageEventText.optionList.isEmpty())
            {
                boolean anyHovered = false;
                int index = 0;
                Iterator iterator1 = event.imageEventText.optionList.iterator();
                do
                {
                    if(!iterator1.hasNext())
                        break;
                    LargeDialogOptionButton o = (LargeDialogOptionButton)iterator1.next();
                    if(o.hb.hovered)
                    {
                        anyHovered = true;
                        break;
                    }
                    index++;
                } while(true);
                if(!anyHovered)
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)event.imageEventText.optionList.get(0)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)event.imageEventText.optionList.get(0)).hb.cY);
                else
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if(++index > event.imageEventText.optionList.size() - 1)
                        index = 0;
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)event.imageEventText.optionList.get(index)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)event.imageEventText.optionList.get(index)).hb.cY);
                } else
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index < 0)
                        index = event.imageEventText.optionList.size() - 1;
                    Gdx.input.setCursorPosition((int)((LargeDialogOptionButton)event.imageEventText.optionList.get(index)).hb.cX, Settings.HEIGHT - (int)((LargeDialogOptionButton)event.imageEventText.optionList.get(index)).hb.cY);
                }
            }
    }

    public void addCardReward(RewardItem rewardItem)
    {
        if(!rewardItem.cards.isEmpty())
            rewards.add(rewardItem);
    }

    public void dispose()
    {
        if(event != null)
            event.dispose();
        if(monsters != null)
        {
            AbstractMonster m;
            for(Iterator iterator = monsters.monsters.iterator(); iterator.hasNext(); m.dispose())
                m = (AbstractMonster)iterator.next();

        }
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/rooms/AbstractRoom.getName());
    public ArrayList potions;
    public ArrayList relics;
    public ArrayList rewards;
    public SoulGroup souls;
    public RoomPhase phase;
    public AbstractEvent event;
    public MonsterGroup monsters;
    private float endBattleTimer;
    public float rewardPopOutTimer;
    private static final float END_TURN_WAIT_DURATION = 1.2F;
    protected String mapSymbol;
    protected Texture mapImg;
    protected Texture mapImgOutline;
    public boolean isBattleOver;
    public boolean cannotLose;
    public boolean eliteTrigger;
    public static int blizzardPotionMod = 0;
    private static final int BLIZZARD_POTION_MOD_AMT = 10;
    public boolean mugged;
    public boolean smoked;
    public boolean combatEvent;
    public boolean rewardAllowed;
    public boolean rewardTime;
    public boolean skipMonsterTurn;
    public int baseRareCardChance;
    public int baseUncommonCardChance;
    public int rareCardChance;
    public int uncommonCardChance;
    public static float waitTimer = 0.0F;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("AbstractRoom");
        TEXT = uiStrings.TEXT;
    }
}
