// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   TheSilent.java

package com.megacrit.cardcrawl.characters;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.AnimationState;
import com.esotericsoftware.spine.AnimationStateData;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.green.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.SnakeRing;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbGreen;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.TheSilentUnlock;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.characters:
//            AbstractPlayer

public class TheSilent extends AbstractPlayer
{

    TheSilent(String playerName)
    {
        super(playerName, AbstractPlayer.PlayerClass.THE_SILENT);
        energyOrb = new EnergyOrbGreen();
        charStat = new CharStat(this);
        dialogX = drawX + 0.0F * Settings.scale;
        dialogY = drawY + 170F * Settings.scale;
        initializeClass(null, "images/characters/theSilent/shoulder2.png", "images/characters/theSilent/shoulder.png", "images/characters/theSilent/corpse.png", getLoadout(), -20F, -24F, 240F, 240F, new EnergyManager(3));
        loadAnimation("images/characters/theSilent/idle/skeleton.atlas", "images/characters/theSilent/idle/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.9F);
        if(ModHelper.enabledMods.size() > 0 && (ModHelper.isModEnabled("Diverse") || ModHelper.isModEnabled("Chimera") || ModHelper.isModEnabled("Blue Cards")))
            masterMaxOrbs = 1;
    }

    public String getPortraitImageName()
    {
        return "silentPortrait.jpg";
    }

    public ArrayList getStartingDeck()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Strike_G");
        retVal.add("Strike_G");
        retVal.add("Strike_G");
        retVal.add("Strike_G");
        retVal.add("Strike_G");
        retVal.add("Defend_G");
        retVal.add("Defend_G");
        retVal.add("Defend_G");
        retVal.add("Defend_G");
        retVal.add("Defend_G");
        retVal.add("Survivor");
        retVal.add("Neutralize");
        return retVal;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new Neutralize();
    }

    public ArrayList getStartingRelics()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Ring of the Snake");
        UnlockTracker.markRelicAsSeen("Ring of the Snake");
        return retVal;
    }

    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 70, 70, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(AbstractPlayer.PlayerClass plyrClass)
    {
        return AbstractPlayer.uiStrings.TEXT[2];
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardColor getCardColor()
    {
        return com.megacrit.cardcrawl.cards.AbstractCard.CardColor.GREEN;
    }

    public Color getCardRenderColor()
    {
        return Color.CHARTREUSE;
    }

    public String getAchievementKey()
    {
        return "EMERALD";
    }

    public ArrayList getCardPool(ArrayList tmpPool)
    {
        CardLibrary.addGreenCards(tmpPool);
        if(ModHelper.isModEnabled("Red Cards"))
            CardLibrary.addRedCards(tmpPool);
        if(ModHelper.isModEnabled("Blue Cards"))
            CardLibrary.addBlueCards(tmpPool);
        if(ModHelper.isModEnabled("Purple Cards"))
            CardLibrary.addPurpleCards(tmpPool);
        return tmpPool;
    }

    public Color getCardTrailColor()
    {
        return Color.CHARTREUSE.cpy();
    }

    public String getLeaderboardCharacterName()
    {
        return "SILENT";
    }

    public Texture getEnergyImage()
    {
        return ImageMaster.GREEN_ORB_FLASH_VFX;
    }

    public int getAscensionMaxHPLoss()
    {
        return 4;
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontGreen;
    }

    public void renderOrb(SpriteBatch sb, boolean enabled, float current_x, float current_y)
    {
        energyOrb.renderOrb(sb, enabled, current_x, current_y);
    }

    public void updateOrb(int orbCount)
    {
        energyOrb.updateOrb(orbCount);
    }

    public Prefs getPrefs()
    {
        if(prefs == null)
            logger.error("prefs need to be initialized first!");
        return prefs;
    }

    public void loadPrefs()
    {
        prefs = SaveHelper.getPrefs("STSDataTheSilent");
    }

    public CharStat getCharStat()
    {
        return charStat;
    }

    public int getUnlockedCardCount()
    {
        return UnlockTracker.unlockedGreenCardCount;
    }

    public int getSeenCardCount()
    {
        return CardLibrary.seenGreenCards;
    }

    public int getCardCount()
    {
        return CardLibrary.greenCards;
    }

    public boolean saveFileExists()
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }

    public String getWinStreakKey()
    {
        return "win_streak_silent";
    }

    public String getLeaderboardWinStreakKey()
    {
        return "SILENT_CONSECUTIVE_WINS";
    }

    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY)
    {
        if(!UnlockTracker.isCharacterLocked("The Silent"))
        {
            if(CardCrawlGame.mainMenuScreen.statsScreen.silentHb == null)
                CardCrawlGame.mainMenuScreen.statsScreen.silentHb = new Hitbox(150F * Settings.scale, 150F * Settings.scale);
            StatsScreen.renderHeader(sb, StatsScreen.NAMES[3], screenX, renderY);
            charStat.render(sb, screenX, renderY);
        }
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "ATTACK_DAGGER_2";
    }

    public Texture getCustomModeCharacterButtonImage()
    {
        return ImageMaster.FILTER_SILENT;
    }

    public CharacterStrings getCharacterString()
    {
        return CardCrawlGame.languagePack.getCharacterString("Silent");
    }

    public String getLocalizedCharacterName()
    {
        return NAMES[0];
    }

    public void refreshCharStat()
    {
        charStat = new CharStat(this);
    }

    public AbstractPlayer newInstance()
    {
        return new TheSilent(name);
    }

    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getOrb()
    {
        return AbstractCard.orb_green;
    }

    public void damage(DamageInfo info)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output - currentBlock > 0)
        {
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.9F);
        }
        super.damage(info);
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[9];
    }

    public Color getSlashAttackColor()
    {
        return Color.GREEN;
    }

    public com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return (new com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] {
            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.POISON, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        });
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[1];
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/TheSilent.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    private EnergyOrbInterface energyOrb;
    private Prefs prefs;
    private CharStat charStat;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Silent");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
    }
}
