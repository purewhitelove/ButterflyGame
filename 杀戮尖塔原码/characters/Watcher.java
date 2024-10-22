// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Watcher.java

package com.megacrit.cardcrawl.characters;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.esotericsoftware.spine.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.purple.*;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.daily.mods.*;
import com.megacrit.cardcrawl.events.beyond.SpireHeart;
import com.megacrit.cardcrawl.events.city.Vampires;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.relics.PureWater;
import com.megacrit.cardcrawl.saveAndContinue.SaveAndContinue;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.megacrit.cardcrawl.screens.charSelect.CharacterOption;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import com.megacrit.cardcrawl.screens.stats.*;
import com.megacrit.cardcrawl.stances.*;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbInterface;
import com.megacrit.cardcrawl.ui.panels.energyorb.EnergyOrbPurple;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.unlock.misc.WatcherUnlock;
import com.megacrit.cardcrawl.vfx.TintEffect;
import java.util.ArrayList;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.characters:
//            AbstractPlayer

public class Watcher extends AbstractPlayer
{

    Watcher(String playerName)
    {
        super(playerName, AbstractPlayer.PlayerClass.WATCHER);
        energyOrb = new EnergyOrbPurple();
        eyeAtlas = null;
        charStat = new CharStat(this);
        drawY += 7F * Settings.scale;
        dialogX = drawX + 0.0F * Settings.scale;
        dialogY = drawY + 170F * Settings.scale;
        initializeClass(null, "images/characters/watcher/shoulder2.png", "images/characters/watcher/shoulder.png", "images/characters/watcher/corpse.png", getLoadout(), 0.0F, -5F, 240F, 270F, new EnergyManager(3));
        loadAnimation("images/characters/watcher/idle/skeleton.atlas", "images/characters/watcher/idle/skeleton.json", 1.0F);
        loadEyeAnimation();
        com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Idle", true);
        stateData.setMix("Hit", "Idle", 0.1F);
        e.setTimeScale(0.7F);
        eyeBone = skeleton.findBone("eye_anchor");
        if(ModHelper.enabledMods.size() > 0 && (ModHelper.isModEnabled("Diverse") || ModHelper.isModEnabled("Chimera") || ModHelper.isModEnabled("Blue Cards")))
            masterMaxOrbs = 1;
    }

    private void loadEyeAnimation()
    {
        eyeAtlas = new TextureAtlas(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.atlas"));
        SkeletonJson json = new SkeletonJson(eyeAtlas);
        json.setScale(Settings.scale / 1.0F);
        com.esotericsoftware.spine.SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal("images/characters/watcher/eye_anim/skeleton.json"));
        eyeSkeleton = new Skeleton(skeletonData);
        eyeSkeleton.setColor(Color.WHITE);
        eyeStateData = new AnimationStateData(skeletonData);
        eyeState = new AnimationState(eyeStateData);
        eyeStateData.setDefaultMix(0.2F);
        eyeState.setAnimation(0, "None", true);
    }

    public String getPortraitImageName()
    {
        return "watcherPortrait.jpg";
    }

    public ArrayList getStartingRelics()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("PureWater");
        return retVal;
    }

    public ArrayList getStartingDeck()
    {
        ArrayList retVal = new ArrayList();
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Strike_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("Defend_P");
        retVal.add("Eruption");
        retVal.add("Vigilance");
        return retVal;
    }

    public AbstractCard getStartCardForEvent()
    {
        return new Eruption();
    }

    public CharSelectInfo getLoadout()
    {
        return new CharSelectInfo(NAMES[0], TEXT[0], 72, 72, 0, 99, 5, this, getStartingRelics(), getStartingDeck(), false);
    }

    public String getTitle(AbstractPlayer.PlayerClass plyrClass)
    {
        return AbstractPlayer.uiStrings.TEXT[4];
    }

    public com.megacrit.cardcrawl.cards.AbstractCard.CardColor getCardColor()
    {
        return com.megacrit.cardcrawl.cards.AbstractCard.CardColor.PURPLE;
    }

    public Color getCardRenderColor()
    {
        return Settings.PURPLE_COLOR;
    }

    public CharacterOption getCharacterSelectOption()
    {
        return new CharacterOption(CharacterSelectScreen.TEXT[14], this, ImageMaster.CHAR_SELECT_WATCHER, ImageMaster.CHAR_SELECT_BG_WATCHER);
    }

    public String getAchievementKey()
    {
        return "AMETHYST";
    }

    public ArrayList getCardPool(ArrayList tmpPool)
    {
        CardLibrary.addPurpleCards(tmpPool);
        if(ModHelper.isModEnabled("Red Cards"))
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
        return Color.PURPLE.cpy();
    }

    public String getLeaderboardCharacterName()
    {
        return "WATCHER";
    }

    public Texture getEnergyImage()
    {
        return ImageMaster.PURPLE_ORB_FLASH_VFX;
    }

    public int getAscensionMaxHPLoss()
    {
        return 4;
    }

    public BitmapFont getEnergyNumFont()
    {
        return FontHelper.energyNumFontPurple;
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
        prefs = SaveHelper.getPrefs("STSDataWatcher");
    }

    public CharStat getCharStat()
    {
        return charStat;
    }

    public int getUnlockedCardCount()
    {
        return UnlockTracker.unlockedPurpleCardCount;
    }

    public int getSeenCardCount()
    {
        return CardLibrary.seenPurpleCards;
    }

    public int getCardCount()
    {
        return CardLibrary.purpleCards;
    }

    public boolean saveFileExists()
    {
        return SaveAndContinue.saveExistsAndNotCorrupted(this);
    }

    public String getWinStreakKey()
    {
        return "win_streak_watcher";
    }

    public String getLeaderboardWinStreakKey()
    {
        return "WATCHER_CONSECUTIVE_WINS";
    }

    public void renderStatScreen(SpriteBatch sb, float screenX, float renderY)
    {
        if(!UnlockTracker.isCharacterLocked("Watcher"))
        {
            if(CardCrawlGame.mainMenuScreen.statsScreen.watcherHb == null)
                CardCrawlGame.mainMenuScreen.statsScreen.watcherHb = new Hitbox(150F * Settings.scale, 150F * Settings.scale);
            StatsScreen.renderHeader(sb, StatsScreen.NAMES[5], screenX, renderY);
            charStat.render(sb, screenX, renderY);
        }
    }

    public void doCharSelectScreenSelectEffect()
    {
        CardCrawlGame.sound.playA("SELECT_WATCHER", MathUtils.random(-0.15F, 0.15F));
        CardCrawlGame.screenShake.shake(com.megacrit.cardcrawl.helpers.ScreenShake.ShakeIntensity.MED, com.megacrit.cardcrawl.helpers.ScreenShake.ShakeDur.SHORT, false);
    }

    public String getCustomModeCharacterButtonSoundKey()
    {
        return "SELECT_WATCHER";
    }

    public Texture getCustomModeCharacterButtonImage()
    {
        return ImageMaster.FILTER_WATCHER;
    }

    public CharacterStrings getCharacterString()
    {
        return CardCrawlGame.languagePack.getCharacterString("Watcher");
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
        return new Watcher(name);
    }

    public com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion getOrb()
    {
        return AbstractCard.orb_purple;
    }

    public void damage(DamageInfo info)
    {
        if(info.owner != null && info.type != com.megacrit.cardcrawl.cards.DamageInfo.DamageType.THORNS && info.output - currentBlock > 0 && atlas != null)
        {
            com.esotericsoftware.spine.AnimationState.TrackEntry e = state.setAnimation(0, "Hit", false);
            state.addAnimation(0, "Idle", true, 0.0F);
            e.setTime(0.9F);
        }
        super.damage(info);
    }

    public String getSpireHeartText()
    {
        return SpireHeart.DESCRIPTIONS[15];
    }

    public Color getSlashAttackColor()
    {
        return Color.MAGENTA;
    }

    public com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect()
    {
        return (new com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect[] {
            com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_HEAVY, com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect.BLUNT_LIGHT
        });
    }

    public String getVampireText()
    {
        return Vampires.DESCRIPTIONS[1];
    }

    public void onStanceChange(String newStance)
    {
        if(newStance.equals("Calm"))
            eyeState.setAnimation(0, "Calm", true);
        else
        if(newStance.equals("Wrath"))
            eyeState.setAnimation(0, "Wrath", true);
        else
        if(newStance.equals("Divinity"))
            eyeState.setAnimation(0, "Divinity", true);
        else
        if(newStance.equals("Neutral"))
            eyeState.setAnimation(0, "None", true);
        else
            eyeState.setAnimation(0, "None", true);
    }

    public void renderPlayerImage(SpriteBatch sb)
    {
        state.update(Gdx.graphics.getDeltaTime());
        state.apply(skeleton);
        skeleton.updateWorldTransform();
        skeleton.setPosition(drawX + animX, drawY + animY);
        skeleton.setColor(tint.color);
        skeleton.setFlip(flipHorizontal, flipVertical);
        eyeState.update(Gdx.graphics.getDeltaTime());
        eyeState.apply(eyeSkeleton);
        eyeSkeleton.updateWorldTransform();
        eyeSkeleton.setPosition(skeleton.getX() + eyeBone.getWorldX(), skeleton.getY() + eyeBone.getWorldY());
        eyeSkeleton.setColor(tint.color);
        eyeSkeleton.setFlip(flipHorizontal, flipVertical);
        sb.end();
        CardCrawlGame.psb.begin();
        sr.draw(CardCrawlGame.psb, skeleton);
        sr.draw(CardCrawlGame.psb, eyeSkeleton);
        CardCrawlGame.psb.end();
        sb.begin();
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/characters/Watcher.getName());
    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    private EnergyOrbInterface energyOrb;
    private Prefs prefs;
    private CharStat charStat;
    private Bone eyeBone;
    protected TextureAtlas eyeAtlas;
    protected Skeleton eyeSkeleton;
    public AnimationState eyeState;
    protected AnimationStateData eyeStateData;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Watcher");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
    }
}
