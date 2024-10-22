// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Defect.java

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
import com.megacrit.cardcrawl.cards.blue.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.CrackedCore;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbBlue;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.DefectUnlock;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.characters:
//            AbstractPlayer

public class Defect extends AbstractPlayer
{

    Defect(String playerName)
    {
        super(playerName, AbstractPlayer.PlayerClass.DEFECT);
        energyOrb = new EnergyOrbBlue();
        charStat = new CharStat(this);
        drawX += 5F * Settings.scale;
        drawY += 7F * Settings.scale;
        dialogX = drawX + 0.0F * Settings.scale;
        dialogY = drawY + 170F * Settings.scale;
        initializeClass(null, "images/characters/defect/shoulder2.png", "images/characters/defect/shoulder.png", "images/characters/defect/corpse.png", getLoadout(), 0.0F, -5F, 240F, 244F, new EnergyManager(3));
        loadAnimation("images/characters/defect/idle/skeleton.atlas", "images/characters/defect/idle/skeleton.json", 1.0F);
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.9F);
    }

    public String getPortraitImageName()
    {
        return "defectPortrait.jpg";
    }

    public ArrayList getStartingRelics()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Cracked Core");
        UnlockTracker.markRelicAsSeen("Cracked Core");
        return retVal;
    }

    public ArrayList getStartingDeck()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Strike_B");
        retVal.add("Strike_B");
        retVal.add("Strike_B");
        retVal.add("Strike_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Defend_B");
        retVal.add("Zap");
        retVal.add("Dualcast");
        return retVal;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new Zap();
    }

    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 75, 75, 3, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(AbstractPlayer.PlayerClass plyrClass)
    {
        return AbstractPlayer.uiStrings.TEXT[3];
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardColor getCardColor()
    {
        return com.megacrit.cardcrawl.cards.AbstractCard.CardColor.BLUE;
    }

    public Color getCardRenderColor()
    {
        return Color.SKY;
    }

    public String getAchievementKey()
    {
        return "SAPPHIRE";
    }

    public ArrayList getCardPool(ArrayList tmpPool)
    {
        CardLibrary.addBlueCards(tmpPool);
        if(ModHelper.isModEnabled("Red Cards"))
            CardLibrary.addRedCards(tmpPool);
        if(ModHelper.isModEnabled("Green Cards"))
            CardLibrary.addGreenCards(tmpPool);
        if(ModHelper.isModEnabled("Purple Cards"))
            CardLibrary.addPurpleCards(tmpPool);
        return tmpPool;
    }

    public Color getCardTrailColor()
    {
        return Color.SKY.cpy();
    }

    public String getLeaderboardCharacterName()
    {
        return "DEFECT";
    }

    public Texture getEnergyImage()
    {
        return ImageMaster.BLUE_ORB_FLASH_VFX;
    }

    public int getAscensionMaxHPLoss()
    {
        return 4;
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontBlue;
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
        prefs = SaveHelper.getPrefs("STSDataDefect");
    }

    public CharStat getCharStat()
    {
        return charStat;
    }

    public int getUnlockedCardCount()
    {
        return UnlockTracker.unlockedBlueCardCount;
    }

    public int getSeenCardCount()
    {
        return CardLibrary.seenBlueCards;
    }

    public int getCardCount()
    {
        return CardLibrary.blueCards;
    }

    public boolean saveFileExists()
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }

    public String getWinStreakKey()
    {
        return "win_streak_defect";
    }

    public String getLeaderboardWinStreakKey()
    {
        return "DEFECT_CONSECUTIVE_WINS";
    }

    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY)
    {
        if(!UnlockTracker.isCharacterLocked("Defect"))
        {
            if(CardCrawlGame.mainMenuScreen.statsScreen.defectHb == null)
                CardCrawlGame.mainMenuScreen.statsScreen.defectHb = new Hitbox(150F * Settings.scale, 150F * Settings.scale);
            StatsScreen.renderHeader(sb, StatsScreen.NAMES[4], screenX, renderY);
            charStat.render(sb, screenX, renderY);
        }
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("ATTACK_MAGIC_BEAM_SHORT", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "ATTACK_MAGIC_BEAM_SHORT";
    }

    public Texture getCustomModeCharacterButtonImage()
    {
        return ImageMaster.FILTER_DEFECT;
    }

    public CharacterStrings getCharacterString()
    {
        return CardCrawlGame.languagePack.getCharacterString("Defect");
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
        return new Defect(name);
    }

    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getOrb()
    {
        return AbstractCard.orb_blue;
    }

    public void damage(DamageInfo info)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output - currentBlock > 0)
        {
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTime(0.9F);
        }
        super.damage(info);
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[10];
    }

    public Color getSlashAttackColor()
    {
        return Color.SKY;
    }

    public com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return (new com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] {
            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.FIRE, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.SLASH_DIAGONAL
        });
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[5];
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/Defect.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    private EnergyOrbInterface energyOrb;
    private Prefs prefs;
    private CharStat charStat;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Defect");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
    }
}
