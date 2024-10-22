// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CustomModeScreen.java

package com.megacrit.cardcrawl.screens.custom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.blue.EchoForm;
import com.megacrit.cardcrawl.cards.green.WraithForm;
import com.megacrit.cardcrawl.cards.purple.DevaForm;
import com.megacrit.cardcrawl.cards.red.DemonForm;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.CharacterManager;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.*;
import com.megacrit.cardcrawl.screens.stats.AchievementGrid;
import com.megacrit.cardcrawl.trials.AbstractTrial;
import com.megacrit.cardcrawl.trials.CustomTrial;
import com.megacrit.cardcrawl.ui.buttons.GridSelectConfirmButton;
import com.megacrit.cardcrawl.ui.panels.SeedPanel;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.*;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.screens.custom:
//            CustomMod, CustomModeCharacterButton

public class CustomModeScreen
    implements ScrollBarListener
{
    private static final class CSelectionType extends Enum
    {

        public static CSelectionType[] values()
        {
            return (CSelectionType[])$VALUES.clone();
        }

        public static CSelectionType valueOf(String name)
        {
            return (CSelectionType)Enum.valueOf(com/megacrit/cardcrawl/screens/custom/CustomModeScreen$CSelectionType, name);
        }

        public static final CSelectionType CHARACTER;
        public static final CSelectionType ASCENSION;
        public static final CSelectionType SEED;
        public static final CSelectionType MODIFIERS;
        private static final CSelectionType $VALUES[];

        static 
        {
            CHARACTER = new CSelectionType("CHARACTER", 0);
            ASCENSION = new CSelectionType("ASCENSION", 1);
            SEED = new CSelectionType("SEED", 2);
            MODIFIERS = new CSelectionType("MODIFIERS", 3);
            $VALUES = (new CSelectionType[] {
                CHARACTER, ASCENSION, SEED, MODIFIERS
            });
        }

        private CSelectionType(String s, int i)
        {
            super(s, i);
        }
    }


    public CustomModeScreen()
    {
        cancelButton = new MenuCancelButton();
        confirmButton = new GridSelectConfirmButton(CharacterSelectScreen.TEXT[1]);
        options = new ArrayList();
        isAscensionMode = false;
        ascensionLevel = 0;
        seedHb = new Hitbox(400F * Settings.scale, 90F * Settings.scale);
        screenUp = false;
        ASCENSION_TEXT_Y = 480F;
        grabbedScreen = false;
        grabStartY = 0.0F;
        targetY = 0.0F;
        scrollY = 0.0F;
        screenX = Settings.isMobile ? 240F * Settings.xScale : 300F * Settings.xScale;
        imageScale = Settings.isMobile ? Settings.scale * 1.2F : Settings.scale;
        initializeMods();
        initializeCharacters();
        calculateScrollBounds();
        if(Settings.isMobile)
            scrollBar = new ScrollBar(this, (float)Settings.WIDTH - 280F * Settings.xScale - ScrollBar.TRACK_W / 2.0F, (float)Settings.HEIGHT / 2.0F, (float)Settings.HEIGHT - 256F * Settings.scale, true);
        else
            scrollBar = new ScrollBar(this, (float)Settings.WIDTH - 280F * Settings.xScale - ScrollBar.TRACK_W / 2.0F, (float)Settings.HEIGHT / 2.0F, (float)Settings.HEIGHT - 256F * Settings.scale);
        seedPanel = new SeedPanel();
    }

    private void initializeMods()
    {
        modList = new ArrayList();
        addMod("Daily Mods", "b", false);
        CustomMod draftMod = addDailyMod("Draft", "b");
        CustomMod sealedMod = addDailyMod("SealedDeck", "b");
        CustomMod endingMod = null;
        if(UnlockTracker.isAchievementUnlocked("THE_ENDING"))
            endingMod = addDailyMod("The Ending", "b");
        CustomMod endlessMod = addDailyMod("Endless", "b");
        addMod("Blight Chests", "b", false);
        addDailyMod("Hoarder", "b");
        CustomMod insanityMod = addDailyMod("Insanity", "b");
        addDailyMod("Chimera", "b");
        addMod("Praise Snecko", "b", false);
        CustomMod shinyMod = addDailyMod("Shiny", "b");
        addDailyMod("Specialized", "b");
        addDailyMod("Vintage", "b");
        addDailyMod("ControlledChaos", "b");
        addMod("Inception", "b", false);
        addDailyMod("Allstar", "g");
        CustomMod diverseMod = addDailyMod("Diverse", "g");
        CustomMod redMod = addDailyMod("Red Cards", "g");
        CustomMod greenMod = addDailyMod("Green Cards", "g");
        CustomMod blueMod = addDailyMod("Blue Cards", "g");
        CustomMod purpleMod = null;
        if(!UnlockTracker.isCharacterLocked("Watcher"))
            purpleMod = addDailyMod("Purple Cards", "g");
        addDailyMod("Colorless Cards", "g");
        addDailyMod("Heirloom", "g");
        addDailyMod("Time Dilation", "g");
        addDailyMod("Flight", "g");
        addMod("My True Form", "g", false);
        addDailyMod("DeadlyEvents", "r");
        addDailyMod("Binary", "r");
        addMod("One Hit Wonder", "r", false);
        addDailyMod("Cursed Run", "r");
        addDailyMod("Elite Swarm", "r");
        addDailyMod("Lethality", "r");
        addDailyMod("Midas", "r");
        addDailyMod("Night Terrors", "r");
        addDailyMod("Terminal", "r");
        addDailyMod("Uncertain Future", "r");
        addMod("Starter Deck", "r", false);
        if(endingMod != null)
            endingMod.setMutualExclusionPair(endlessMod);
        insanityMod.setMutualExclusionPair(shinyMod);
        sealedMod.setMutualExclusionPair(draftMod);
        diverseMod.setMutualExclusionPair(redMod);
        diverseMod.setMutualExclusionPair(greenMod);
        diverseMod.setMutualExclusionPair(blueMod);
        if(purpleMod != null)
            diverseMod.setMutualExclusionPair(purpleMod);
    }

    private CustomMod addMod(String id, String color, boolean isDailyMod)
    {
        RunModStrings modString = CardCrawlGame.languagePack.getRunModString(id);
        if(modString != null)
        {
            CustomMod mod = new CustomMod(id, color, isDailyMod);
            modList.add(mod);
            return mod;
        } else
        {
            return null;
        }
    }

    private CustomMod addDailyMod(String id, String color)
    {
        return addMod(id, color, true);
    }

    public void open()
    {
        confirmButton.show();
        controllerHb = null;
        targetY = 0.0F;
        screenUp = true;
        Settings.seed = null;
        Settings.specialSeed = null;
        CardCrawlGame.mainMenuScreen.screen = com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CUSTOM;
        CardCrawlGame.mainMenuScreen.darken();
        cancelButton.show(CharacterSelectScreen.TEXT[5]);
        confirmButton.isDisabled = false;
        ASC_RIGHT_W = FontHelper.getSmartWidth(FontHelper.charDescFont, (new StringBuilder()).append(TEXT[4]).append("22").toString(), 9999F, 0.0F) * Settings.xScale;
        ascensionModeHb = new Hitbox(80F * Settings.scale, 80F * Settings.scale);
        ascensionModeHb.move(screenX + 130F * Settings.xScale, scrollY + ASCENSION_TEXT_Y * Settings.scale);
        ascLeftHb = new Hitbox(95F * Settings.scale, 95F * Settings.scale);
        ascRightHb = new Hitbox(95F * Settings.scale, 95F * Settings.scale);
        ascLeftHb.move(screenX + ASC_RIGHT_W * 1.1F + 250F * Settings.xScale, scrollY + ASCENSION_TEXT_Y * Settings.scale);
        ascRightHb.move(screenX + ASC_RIGHT_W * 1.1F + 350F * Settings.xScale, scrollY + ASCENSION_TEXT_Y * Settings.scale);
    }

    public void initializeCharacters()
    {
        options.clear();
        options.add(new CustomModeCharacterButton(CardCrawlGame.characterManager.setChosenCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.IRONCLAD), false));
        options.add(new CustomModeCharacterButton(CardCrawlGame.characterManager.setChosenCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.THE_SILENT), UnlockTracker.isCharacterLocked("The Silent")));
        options.add(new CustomModeCharacterButton(CardCrawlGame.characterManager.setChosenCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.DEFECT), UnlockTracker.isCharacterLocked("Defect")));
        options.add(new CustomModeCharacterButton(CardCrawlGame.characterManager.setChosenCharacter(com.megacrit.cardcrawl.characters.AbstractPlayer.PlayerClass.WATCHER), UnlockTracker.isCharacterLocked("Watcher")));
        int count = options.size();
        for(int i = 0; i < count; i++)
            ((CustomModeCharacterButton)options.get(i)).move((screenX + (float)i * 100F * Settings.scale) - 200F * Settings.xScale, scrollY - 80F * Settings.scale);

        ((CustomModeCharacterButton)options.get(0)).hb.clicked = true;
    }

    public void update()
    {
        updateControllerInput();
        if(Settings.isControllerMode && controllerHb != null)
            if((float)Gdx.input.getY() > (float)Settings.HEIGHT * 0.75F)
                targetY += Settings.SCROLL_SPEED;
            else
            if((float)Gdx.input.getY() < (float)Settings.HEIGHT * 0.25F)
                targetY -= Settings.SCROLL_SPEED;
        seedPanel.update();
        if(!seedPanel.shown)
        {
            boolean isDraggingScrollBar = scrollBar.update();
            if(!isDraggingScrollBar)
                updateScrolling();
            updateCharacterButtons();
            updateAscension();
            updateSeed();
            updateMods();
            updateEmbarkButton();
            updateCancelButton();
        }
        currentSeed = SeedHelper.getUserFacingSeedString();
        if(Settings.isControllerMode && controllerHb != null)
            CInputHelper.setCursor(controllerHb);
    }

    private void updateCancelButton()
    {
        cancelButton.update();
        if(cancelButton.hb.clicked || InputHelper.pressedEscape)
        {
            InputHelper.pressedEscape = false;
            cancelButton.hb.clicked = false;
            cancelButton.hide();
            CardCrawlGame.mainMenuScreen.panelScreen.refresh();
        }
    }

    private void updateEmbarkButton()
    {
        confirmButton.update();
        if(confirmButton.hb.clicked || CInputActionSet.proceed.isJustPressed())
        {
            confirmButton.hb.clicked = false;
            CustomTrial trial = options.iterator();
            do
            {
                if(!trial.hasNext())
                    break;
                CustomModeCharacterButton b = (CustomModeCharacterButton)trial.next();
                if(!b.selected)
                    continue;
                CardCrawlGame.chosenCharacter = b.c.chosenClass;
                break;
            } while(true);
            CardCrawlGame.mainMenuScreen.isFadingOut = true;
            CardCrawlGame.mainMenuScreen.fadeOutMusic();
            Settings.isTrial = true;
            Settings.isDailyRun = false;
            Settings.isEndless = false;
            finalActAvailable = false;
            AbstractDungeon.isAscensionMode = isAscensionMode;
            if(!isAscensionMode)
                AbstractDungeon.ascensionLevel = 0;
            else
                AbstractDungeon.ascensionLevel = ascensionLevel;
            if(currentSeed.isEmpty())
            {
                long sourceTime = System.nanoTime();
                Random rng = new Random(Long.valueOf(sourceTime));
                Settings.seed = Long.valueOf(SeedHelper.generateUnoffensiveSeed(rng));
            }
            AbstractDungeon.generateSeeds();
            sourceTime = new CustomTrial();
            sourceTime.addDailyMods(getActiveDailyModIds());
            addNonDailyMods(sourceTime, getActiveNonDailyMods());
            Settings.isEndless = sourceTime.dailyModIDs().contains("Endless");
            finalActAvailable = sourceTime.dailyModIDs().contains("The Ending");
            CardCrawlGame.trial = sourceTime;
            AbstractPlayer.customMods = CardCrawlGame.trial.dailyModIDs();
        }
    }

    private void updateCharacterButtons()
    {
        for(int i = 0; i < options.size(); i++)
            if(Settings.isMobile)
                ((CustomModeCharacterButton)options.get(i)).update(screenX + (float)i * 130F * Settings.xScale + 130F * Settings.scale, scrollY + 640F * Settings.scale);
            else
                ((CustomModeCharacterButton)options.get(i)).update(screenX + (float)i * 100F * Settings.xScale + 130F * Settings.scale, scrollY + 640F * Settings.scale);

    }

    private void updateSeed()
    {
        seedHb.move(screenX + 280F * Settings.xScale, scrollY + 320F * Settings.scale);
        seedHb.update();
        if(seedHb.justHovered)
            playHoverSound();
        if(seedHb.hovered && InputHelper.justClickedLeft)
            seedHb.clickStarted = true;
        if(seedHb.clicked || CInputActionSet.select.isJustPressed() && seedHb.hovered)
        {
            seedHb.clicked = false;
            if(Settings.seed == null)
                Settings.seed = Long.valueOf(0L);
            seedPanel.show(com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen.CurScreen.CUSTOM);
        }
    }

    private void updateAscension()
    {
        ascLeftHb.moveY(scrollY + ASCENSION_TEXT_Y * Settings.scale);
        ascRightHb.moveY(scrollY + ASCENSION_TEXT_Y * Settings.scale);
        ascensionModeHb.moveY(scrollY + ASCENSION_TEXT_Y * Settings.scale);
        ascensionModeHb.update();
        ascLeftHb.update();
        ascRightHb.update();
        if(ascensionModeHb.justHovered || ascRightHb.justHovered || ascLeftHb.justHovered)
            playHoverSound();
        if(ascensionModeHb.hovered && InputHelper.justClickedLeft)
        {
            playClickStartSound();
            ascensionModeHb.clickStarted = true;
        } else
        if(ascLeftHb.hovered && InputHelper.justClickedLeft)
        {
            playClickStartSound();
            ascLeftHb.clickStarted = true;
        } else
        if(ascRightHb.hovered && InputHelper.justClickedLeft)
        {
            playClickStartSound();
            ascRightHb.clickStarted = true;
        }
        if(ascensionModeHb.clicked || CInputActionSet.topPanel.isJustPressed())
        {
            CInputActionSet.topPanel.unpress();
            playClickFinishSound();
            ascensionModeHb.clicked = false;
            isAscensionMode = !isAscensionMode;
            if(isAscensionMode && ascensionLevel == 0)
                ascensionLevel = 1;
        } else
        if(ascLeftHb.clicked || CInputActionSet.pageLeftViewDeck.isJustPressed())
        {
            playClickFinishSound();
            ascLeftHb.clicked = false;
            ascensionLevel--;
            if(ascensionLevel < 1)
                ascensionLevel = 20;
        } else
        if(ascRightHb.clicked || CInputActionSet.pageRightViewExhaust.isJustPressed())
        {
            playClickFinishSound();
            ascRightHb.clicked = false;
            ascensionLevel++;
            if(ascensionLevel > 20)
                ascensionLevel = 1;
            isAscensionMode = true;
        }
    }

    private void updateMods()
    {
        float offset = 0.0F;
        for(int i = 0; i < modList.size(); i++)
        {
            ((CustomMod)modList.get(i)).update(scrollY + offset);
            offset -= ((CustomMod)modList.get(i)).height;
        }

    }

    private ArrayList getActiveDailyModIds()
    {
        ArrayList active = new ArrayList();
        Iterator iterator = modList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CustomMod mod = (CustomMod)iterator.next();
            if(mod.selected && mod.isDailyMod)
                active.add(mod.ID);
        } while(true);
        return active;
    }

    private ArrayList getActiveNonDailyMods()
    {
        ArrayList active = new ArrayList();
        Iterator iterator = modList.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CustomMod mod = (CustomMod)iterator.next();
            if(mod.selected && !mod.isDailyMod)
                active.add(mod.ID);
        } while(true);
        return active;
    }

    private void addNonDailyMods(CustomTrial trial, ArrayList modIds)
    {
        Iterator iterator = modIds.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            String modId = (String)iterator.next();
            String s = modId;
            byte byte0 = -1;
            switch(s.hashCode())
            {
            case -833715304: 
                if(s.equals("Daily Mods"))
                    byte0 = 0;
                break;

            case -1548156094: 
                if(s.equals("One Hit Wonder"))
                    byte0 = 1;
                break;

            case 1511032449: 
                if(s.equals("Praise Snecko"))
                    byte0 = 2;
                break;

            case 302508925: 
                if(s.equals("Inception"))
                    byte0 = 3;
                break;

            case -35208510: 
                if(s.equals("My True Form"))
                    byte0 = 4;
                break;

            case 1104180698: 
                if(s.equals("Starter Deck"))
                    byte0 = 5;
                break;

            case 2062418238: 
                if(s.equals("Blight Chests"))
                    byte0 = 6;
                break;
            }
            switch(byte0)
            {
            case 0: // '\0'
                trial.setRandomDailyMods();
                break;

            case 1: // '\001'
                trial.setMaxHpOverride(1);
                break;

            case 2: // '\002'
                trial.addStarterRelic("Snecko Eye");
                trial.setShouldKeepStarterRelic(false);
                break;

            case 3: // '\003'
                trial.addStarterRelic("Unceasing Top");
                trial.setShouldKeepStarterRelic(false);
                break;

            case 4: // '\004'
                trial.addStarterCards(Arrays.asList(new String[] {
                    "Demon Form", "Wraith Form v2", "Echo Form", "DevaForm"
                }));
                break;

            case 5: // '\005'
                trial.addStarterRelic("Busted Crown");
                trial.addDailyMod("Binary");
                break;

            case 6: // '\006'
                trial.addDailyMod("Blight Chests");
                break;
            }
        } while(true);
    }

    private void playClickStartSound()
    {
        CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
    }

    private void playClickFinishSound()
    {
        CardCrawlGame.sound.playA("UI_CLICK_1", -0.1F);
    }

    private void playHoverSound()
    {
        CardCrawlGame.sound.playV("UI_HOVER", 0.75F);
    }

    public void render(SpriteBatch sb)
    {
        renderScreen(sb);
        scrollBar.render(sb);
        cancelButton.render(sb);
        confirmButton.render(sb);
        CustomModeCharacterButton o;
        for(Iterator iterator = options.iterator(); iterator.hasNext(); o.render(sb))
            o = (CustomModeCharacterButton)iterator.next();

        renderAscension(sb);
        renderSeed(sb);
        sb.setColor(Color.WHITE);
        CustomMod m;
        for(Iterator iterator1 = modList.iterator(); iterator1.hasNext(); m.render(sb))
            m = (CustomMod)iterator1.next();

        seedPanel.render(sb);
    }

    public void renderScreen(SpriteBatch sb)
    {
        renderTitle(sb, TEXT[0], scrollY - 50F * Settings.scale);
        renderHeader(sb, TEXT[2], scrollY - 120F * Settings.scale);
        renderHeader(sb, TEXT[3], scrollY - 290F * Settings.scale);
        renderHeader(sb, TEXT[7], scrollY - 460F * Settings.scale);
        renderHeader(sb, TEXT[6], scrollY - 630F * Settings.scale);
    }

    private void renderAscension(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        if(ascensionModeHb.hovered)
        {
            sb.draw(ImageMaster.CHECKBOX, ascensionModeHb.cX - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, imageScale * 1.2F, imageScale * 1.2F, 0.0F, 0, 0, 64, 64, false, false);
            sb.setColor(Color.GOLD);
            sb.setBlendFunction(770, 1);
            sb.draw(ImageMaster.CHECKBOX, ascensionModeHb.cX - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, imageScale * 1.2F, imageScale * 1.2F, 0.0F, 0, 0, 64, 64, false, false);
            sb.setBlendFunction(770, 771);
        } else
        {
            sb.draw(ImageMaster.CHECKBOX, ascensionModeHb.cX - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, imageScale, imageScale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(ascensionModeHb.hovered)
            FontHelper.renderFontCentered(sb, FontHelper.charDescFont, (new StringBuilder()).append(TEXT[4]).append(ascensionLevel).toString(), screenX + 240F * Settings.scale, scrollY + ASCENSION_TEXT_Y * Settings.scale, Color.CYAN);
        else
            FontHelper.renderFontCentered(sb, FontHelper.charDescFont, (new StringBuilder()).append(TEXT[4]).append(ascensionLevel).toString(), screenX + 240F * Settings.scale, scrollY + ASCENSION_TEXT_Y * Settings.scale, Settings.BLUE_TEXT_COLOR);
        if(isAscensionMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(ImageMaster.TICK, ascensionModeHb.cX - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, imageScale, imageScale, 0.0F, 0, 0, 64, 64, false, false);
        }
        if(ascensionLevel != 0)
            FontHelper.renderSmartText(sb, FontHelper.charDescFont, CardCrawlGame.mainMenuScreen.charSelectScreen.ascLevelInfoString = CharacterSelectScreen.A_TEXT[ascensionLevel - 1], screenX + ASC_RIGHT_W * 1.1F + 400F * Settings.scale, ascensionModeHb.cY + 10F * Settings.scale, 9999F, 32F * Settings.scale, Settings.CREAM_COLOR);
        if(ascLeftHb.hovered || Settings.isControllerMode)
            sb.setColor(Color.WHITE);
        else
            sb.setColor(Color.LIGHT_GRAY);
        sb.draw(ImageMaster.CF_LEFT_ARROW, ascLeftHb.cX - 24F, ascLeftHb.cY - 24F, 24F, 24F, 48F, 48F, imageScale, imageScale, 0.0F, 0, 0, 48, 48, false, false);
        if(ascRightHb.hovered || Settings.isControllerMode)
            sb.setColor(Color.WHITE);
        else
            sb.setColor(Color.LIGHT_GRAY);
        sb.draw(ImageMaster.CF_RIGHT_ARROW, ascRightHb.cX - 24F, ascRightHb.cY - 24F, 24F, 24F, 48F, 48F, imageScale, imageScale, 0.0F, 0, 0, 48, 48, false, false);
        if(Settings.isControllerMode)
        {
            sb.draw(CInputActionSet.topPanel.getKeyImg(), ascensionModeHb.cX - 64F * Settings.scale - 32F, ascensionModeHb.cY - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            sb.draw(CInputActionSet.pageLeftViewDeck.getKeyImg(), ascLeftHb.cX - 12F * Settings.scale - 32F, (ascLeftHb.cY + 40F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
            sb.draw(CInputActionSet.pageRightViewExhaust.getKeyImg(), (ascRightHb.cX + 12F * Settings.scale) - 32F, (ascRightHb.cY + 40F * Settings.scale) - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        ascensionModeHb.render(sb);
        ascLeftHb.render(sb);
        ascRightHb.render(sb);
    }

    private void renderSeed(SpriteBatch sb)
    {
        if(seedHb.hovered)
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, (new StringBuilder()).append(TEXT[8]).append(": ").append(currentSeed).toString(), screenX + 96F * Settings.scale, seedHb.cY, 9999F, 32F * Settings.scale, Settings.GREEN_TEXT_COLOR);
        else
            FontHelper.renderSmartText(sb, FontHelper.turnNumFont, (new StringBuilder()).append(TEXT[8]).append(": ").append(currentSeed).toString(), screenX + 96F * Settings.scale, seedHb.cY, 9999F, 32F * Settings.scale, Settings.BLUE_TEXT_COLOR);
        seedHb.render(sb);
    }

    private void renderHeader(SpriteBatch sb, String text, float y)
    {
        if(Settings.isMobile)
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, screenX + 50F * Settings.scale, y + 850F * Settings.scale, 9999F, 32F * Settings.scale, Settings.GOLD_COLOR, 1.2F);
        else
            FontHelper.renderSmartText(sb, FontHelper.panelNameFont, text, screenX + 50F * Settings.scale, y + 850F * Settings.scale, 9999F, 32F * Settings.scale, Settings.GOLD_COLOR);
    }

    private void renderTitle(SpriteBatch sb, String text, float y)
    {
        FontHelper.renderSmartText(sb, FontHelper.charTitleFont, text, screenX, y + 900F * Settings.scale, 9999F, 32F * Settings.scale, Settings.GOLD_COLOR);
        if(!Settings.usesTrophies)
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, TEXT[1], screenX + FontHelper.getSmartWidth(FontHelper.charTitleFont, text, 9999F, 9999F) + 18F * Settings.scale, y + 888F * Settings.scale, 9999F, 32F * Settings.scale, Settings.RED_TEXT_COLOR);
        else
            FontHelper.renderSmartText(sb, FontHelper.tipBodyFont, TEXT[9], screenX + FontHelper.getSmartWidth(FontHelper.charTitleFont, text, 9999F, 9999F) + 18F * Settings.scale, y + 888F * Settings.scale, 9999F, 32F * Settings.scale, Settings.RED_TEXT_COLOR);
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode)
            return;
        CSelectionType type = CSelectionType.CHARACTER;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = options.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CustomModeCharacterButton b = (CustomModeCharacterButton)iterator.next();
            if(b.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered && ascensionModeHb.hovered)
        {
            anyHovered = true;
            type = CSelectionType.ASCENSION;
        }
        if(!anyHovered && seedHb.hovered)
        {
            anyHovered = true;
            type = CSelectionType.SEED;
        }
        if(!anyHovered)
        {
            index = 0;
            Iterator iterator1 = modList.iterator();
            do
            {
                if(!iterator1.hasNext())
                    break;
                CustomMod m = (CustomMod)iterator1.next();
                if(m.hb.hovered)
                {
                    anyHovered = true;
                    type = CSelectionType.MODIFIERS;
                    break;
                }
                index++;
            } while(true);
        }
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType = new int[CSelectionType.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType[CSelectionType.CHARACTER.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType[CSelectionType.ASCENSION.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType[CSelectionType.SEED.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$custom$CustomModeScreen$CSelectionType[CSelectionType.MODIFIERS.ordinal()] = 4;
                }
                catch(NoSuchFieldError nosuchfielderror3) { }
            }
        }

        if(!anyHovered && controllerHb == null)
        {
            CInputHelper.setCursor(((CustomModeCharacterButton)options.get(0)).hb);
            controllerHb = ((CustomModeCharacterButton)options.get(0)).hb;
        } else
        {
            switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.custom.CustomModeScreen.CSelectionType[type.ordinal()])
            {
            default:
                break;

            case 1: // '\001'
                if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
                {
                    if(++index > options.size() - 1)
                        index = options.size() - 1;
                    CInputHelper.setCursor(((CustomModeCharacterButton)options.get(index)).hb);
                    controllerHb = ((CustomModeCharacterButton)options.get(index)).hb;
                    break;
                }
                if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
                {
                    if(--index < 0)
                        index = 0;
                    CInputHelper.setCursor(((CustomModeCharacterButton)options.get(index)).hb);
                    controllerHb = ((CustomModeCharacterButton)options.get(index)).hb;
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    CInputHelper.setCursor(ascensionModeHb);
                    controllerHb = ascensionModeHb;
                    break;
                }
                if(CInputActionSet.select.isJustPressed())
                {
                    CInputActionSet.select.unpress();
                    ((CustomModeCharacterButton)options.get(index)).hb.clicked = true;
                }
                break;

            case 2: // '\002'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    CInputHelper.setCursor(((CustomModeCharacterButton)options.get(0)).hb);
                    controllerHb = ((CustomModeCharacterButton)options.get(0)).hb;
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    CInputHelper.setCursor(seedHb);
                    controllerHb = seedHb;
                }
                break;

            case 3: // '\003'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    CInputHelper.setCursor(ascensionModeHb);
                    controllerHb = ascensionModeHb;
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    CInputHelper.setCursor(((CustomMod)modList.get(0)).hb);
                    controllerHb = ((CustomMod)modList.get(0)).hb;
                }
                break;

            case 4: // '\004'
                if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
                {
                    if(--index < 0)
                    {
                        CInputHelper.setCursor(seedHb);
                        controllerHb = seedHb;
                    } else
                    {
                        CInputHelper.setCursor(((CustomMod)modList.get(index)).hb);
                        controllerHb = ((CustomMod)modList.get(index)).hb;
                    }
                    break;
                }
                if(CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed())
                {
                    if(++index > modList.size() - 1)
                        index = modList.size() - 1;
                    CInputHelper.setCursor(((CustomMod)modList.get(index)).hb);
                    controllerHb = ((CustomMod)modList.get(index)).hb;
                    break;
                }
                if(CInputActionSet.select.isJustPressed())
                {
                    CInputActionSet.select.unpress();
                    ((CustomMod)modList.get(index)).hb.clicked = true;
                }
                break;
            }
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
        if(scrollUpperBound > 0.0F)
            if(!grabbedScreen)
            {
                if(InputHelper.scrolledDown)
                    targetY += Settings.SCROLL_SPEED;
                else
                if(InputHelper.scrolledUp)
                    targetY -= Settings.SCROLL_SPEED;
                if(InputHelper.justClickedLeft)
                {
                    grabbedScreen = true;
                    grabStartY = (float)y - targetY;
                }
            } else
            if(InputHelper.isMouseDown)
                targetY = (float)y - grabStartY;
            else
                grabbedScreen = false;
        scrollY = MathHelper.scrollSnapLerpSpeed(scrollY, targetY);
        if(targetY < scrollLowerBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollLowerBound);
        else
        if(targetY > scrollUpperBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollUpperBound);
        updateBarPosition();
    }

    private void calculateScrollBounds()
    {
        scrollUpperBound = (float)modList.size() * 90F * Settings.scale + 270F * Settings.scale;
        scrollLowerBound = 100F * Settings.scale;
    }

    public void scrolledUsingBar(float newPercent)
    {
        float newPosition = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        scrollY = newPosition;
        targetY = newPosition;
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
        scrollBar.parentScrolledToPercent(percent);
    }

    public void deselectOtherOptions(CustomModeCharacterButton characterOption)
    {
        Iterator iterator = options.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            CustomModeCharacterButton o = (CustomModeCharacterButton)iterator.next();
            if(o != characterOption)
                o.selected = false;
        } while(true);
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    private final float imageScale;
    private MenuCancelButton cancelButton;
    public GridSelectConfirmButton confirmButton;
    private Hitbox controllerHb;
    public ArrayList options;
    private static float ASC_RIGHT_W;
    public static boolean finalActAvailable = false;
    public boolean isAscensionMode;
    private Hitbox ascensionModeHb;
    private Hitbox ascLeftHb;
    private Hitbox ascRightHb;
    public int ascensionLevel;
    private Hitbox seedHb;
    private String currentSeed;
    private SeedPanel seedPanel;
    private ArrayList modList;
    private static final String DAILY_MODS = "Daily Mods";
    private static final String MOD_BLIGHT_CHESTS = "Blight Chests";
    private static final String MOD_ONE_HIT_WONDER = "One Hit Wonder";
    private static final String MOD_PRAISE_SNECKO = "Praise Snecko";
    private static final String MOD_INCEPTION = "Inception";
    private static final String MOD_MY_TRUE_FORM = "My True Form";
    private static final String MOD_STARTER_DECK = "Starter Deck";
    private static final String NEUTRAL_COLOR = "b";
    private static final String POSITIVE_COLOR = "g";
    private static final String NEGATIVE_COLOR = "r";
    public boolean screenUp;
    public static float screenX;
    private float ASCENSION_TEXT_Y;
    private boolean grabbedScreen;
    private float grabStartY;
    private float targetY;
    private float scrollY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private ScrollBar scrollBar;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CustomModeScreen");
        TEXT = uiStrings.TEXT;
    }
}
