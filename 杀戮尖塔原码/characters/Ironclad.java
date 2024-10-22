// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Ironclad.java

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
import com.megacrit.cardcrawl.cards.red.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.BurningBlood;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.stats.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbRed;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.characters:
//            AbstractPlayer

public class Ironclad extends AbstractPlayer
{

    Ironclad(String playerName)
    {
        super(playerName, AbstractPlayer.PlayerClass.IRONCLAD);
        energyOrb = new EnergyOrbRed();
        charStat = new CharStat(this);
        dialogX = drawX + 0.0F * Settings.scale;
        dialogY = drawY + 220F * Settings.scale;
        initializeClass(null, "images/characters/ironclad/shoulder2.png", "images/characters/ironclad/shoulder.png", "images/characters/ironclad/corpse.png", getLoadout(), -4F, -16F, 220F, 290F, new EnergyManager(3));
        loadAnimation("images/characters/ironclad/idle/skeleton.atlas", "images/characters/ironclad/idle/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.6F);
        if(ModHelper.enabledMods.size() > 0 && (ModHelper.isModEnabled("Diverse") || ModHelper.isModEnabled("Chimera") || ModHelper.isModEnabled("Blue Cards")))
            masterMaxOrbs = 1;
    }

    public String getPortraitImageName()
    {
        return "ironcladPortrait.jpg";
    }

    public ArrayList getStartingRelics()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Burning Blood");
        UnlockTracker.markRelicAsSeen("Burning Blood");
        return retVal;
    }

    public ArrayList getStartingDeck()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Strike_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Defend_R");
        retVal.add("Bash");
        return retVal;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new Bash();
    }

    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 80, 80, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(AbstractPlayer.PlayerClass plyrClass)
    {
        return AbstractPlayer.uiStrings.TEXT[1];
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardColor getCardColor()
    {
        return com.megacrit.cardcrawl.cards.AbstractCard.CardColor.RED;
    }

    public Color getCardRenderColor()
    {
        return Color.SCARLET;
    }

    public String getAchievementKey()
    {
        return "RUBY";
    }

    public ArrayList getCardPool(ArrayList tmpPool)
    {
        CardLibrary.addRedCards(tmpPool);
        if(ModHelper.isModEnabled("Green Cards"))
            CardLibrary.addGreenCards(tmpPool);
        if(ModHelper.isModEnabled("Blue Cards"))
            CardLibrary.addBlueCards(tmpPool);
        if(ModHelper.isModEnabled("Purple Cards"))
            CardLibrary.addPurpleCards(tmpPool);
        return tmpPool;
    }

    public Color getCardTrailColor()
    {
        return new Color(1.0F, 0.4F, 0.1F, 1.0F);
    }

    public String getLeaderboardCharacterName()
    {
        return "IRONCLAD";
    }

    public Texture getEnergyImage()
    {
        return ImageMaster.RED_ORB_FLASH_VFX;
    }

    public int getAscensionMaxHPLoss()
    {
        return 5;
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontRed;
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
        prefs = SaveHelper.getPrefs("STSDataVagabond");
    }

    public CharStat getCharStat()
    {
        return charStat;
    }

    public int getUnlockedCardCount()
    {
        return UnlockTracker.unlockedRedCardCount;
    }

    public int getSeenCardCount()
    {
        return CardLibrary.seenRedCards;
    }

    public int getCardCount()
    {
        return CardLibrary.redCards;
    }

    public boolean saveFileExists()
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }

    public String getWinStreakKey()
    {
        return "win_streak_ironclad";
    }

    public String getLeaderboardWinStreakKey()
    {
        return "IRONCLAD_CONSECUTIVE_WINS";
    }

    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY)
    {
        StatsScreen.renderHeader(sb, StatsScreen.NAMES[2], screenX, renderY);
        charStat.render(sb, screenX, renderY);
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_HEAVY", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, true);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "ATTACK_HEAVY";
    }

    public Texture getCustomModeCharacterButtonImage()
    {
        return ImageMaster.FILTER_IRONCLAD;
    }

    public CharacterStrings getCharacterString()
    {
        return CardCrawlGame.languagePack.getCharacterString("Ironclad");
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
        return new Ironclad(name);
    }

    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getOrb()
    {
        return AbstractCard.orb_red;
    }

    public void damage(DamageInfo info)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output - currentBlock > 0)
        {
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTimeScale(0.6F);
        }
        super.damage(info);
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[8];
    }

    public Color getSlashAttackColor()
    {
        return Color.RED;
    }

    public com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return (new com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] {
            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY
        });
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[0];
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/Ironclad.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    private EnergyOrbInterface energyOrb;
    private Prefs prefs;
    private CharStat charStat;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Ironclad");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
    }
}
