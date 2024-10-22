// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   Merchant.java

package com.megacrit.cardcrawl.shop;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL30;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Disposable;
import com.megacrit.cardcrawl.audio.SoundMaster;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.characters.AnimatedNpc;
import com.megacrit.cardcrawl.core.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.controller.CInputAction;
import com.megacrit.cardcrawl.helpers.controller.CInputActionSet;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.vfx.SpeechBubble;
import java.util.*;

// Referenced classes of package com.megacrit.cardcrawl.shop:
//            ShopScreen

public class Merchant
    implements Disposable
{

    public Merchant()
    {
        this(0.0F, 0.0F, 1);
    }

    public Merchant(float x, float y, int newShopScreen)
    {
        hb = new Hitbox(360F * Settings.scale, 300F * Settings.scale);
        cards1 = new ArrayList();
        cards2 = new ArrayList();
        idleMessages = new ArrayList();
        speechTimer = 1.5F;
        saidWelcome = false;
        shopScreen = 1;
        anim = new AnimatedNpc(DRAW_X + 256F * Settings.scale, AbstractDungeon.floorY + 30F * Settings.scale, "images/npcs/merchant/skeleton.atlas", "images/npcs/merchant/skeleton.json", "idle");
        AbstractCard c;
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, true).makeCopy(); c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, true).makeCopy());
        cards1.add(c);
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)cards1.get(cards1.size() - 1)).cardID) || c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.ATTACK, true).makeCopy());
        cards1.add(c);
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, true).makeCopy(); c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, true).makeCopy());
        cards1.add(c);
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, true).makeCopy(); Objects.equals(c.cardID, ((AbstractCard)cards1.get(cards1.size() - 1)).cardID) || c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.SKILL, true).makeCopy());
        cards1.add(c);
        for(c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, true).makeCopy(); c.color == com.megacrit.cardcrawl.cards.AbstractCard.CardColor.COLORLESS; c = AbstractDungeon.getCardFromPool(AbstractDungeon.rollRarity(), com.megacrit.cardcrawl.cards.AbstractCard.CardType.POWER, true).makeCopy());
        cards1.add(c);
        cards2.add(AbstractDungeon.getColorlessCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.UNCOMMON).makeCopy());
        cards2.add(AbstractDungeon.getColorlessCardFromPool(com.megacrit.cardcrawl.cards.AbstractCard.CardRarity.RARE).makeCopy());
        if(AbstractDungeon.id.equals("TheEnding"))
            Collections.addAll(idleMessages, ENDING_TEXT);
        else
            Collections.addAll(idleMessages, TEXT);
        speechTimer = 1.5F;
        modX = x;
        modY = y;
        hb.move(DRAW_X + (250F + x) * Settings.scale, DRAW_Y + (130F + y) * Settings.scale);
        shopScreen = newShopScreen;
        AbstractDungeon.shopScreen.init(cards1, cards2);
    }

    public void update()
    {
        hb.update();
        if((hb.hovered && InputHelper.justClickedLeft || CInputActionSet.select.isJustPressed()) && !AbstractDungeon.isScreenUp && !AbstractDungeon.isFadingOut && !AbstractDungeon.player.viewingRelics)
        {
            AbstractDungeon.overlayMenu.proceedButton.setLabel(NAMES[0]);
            saidWelcome = true;
            AbstractDungeon.shopScreen.open();
            hb.hovered = false;
        }
        speechTimer -= Gdx.graphics.getDeltaTime();
        if(speechTimer < 0.0F && shopScreen == 1)
        {
            String msg = (String)idleMessages.get(MathUtils.random(0, idleMessages.size() - 1));
            if(!saidWelcome)
            {
                saidWelcome = true;
                welcomeSfx();
                msg = NAMES[1];
            } else
            {
                playMiscSfx();
            }
            if(MathUtils.randomBoolean())
                AbstractDungeon.effectList.add(new SpeechBubble(hb.cX - 50F * Settings.xScale, hb.cY + 70F * Settings.yScale, 3F, msg, false));
            else
                AbstractDungeon.effectList.add(new SpeechBubble(hb.cX - 50F * Settings.xScale, hb.cY + 70F * Settings.yScale, 3F, msg, true));
            speechTimer = MathUtils.random(40F, 60F);
        }
    }

    private void welcomeSfx()
    {
        int roll = MathUtils.random(2);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
    }

    private void playMiscSfx()
    {
        int roll = MathUtils.random(5);
        if(roll == 0)
            CardCrawlGame.sound.play("VO_MERCHANT_MA");
        else
        if(roll == 1)
            CardCrawlGame.sound.play("VO_MERCHANT_MB");
        else
        if(roll == 2)
            CardCrawlGame.sound.play("VO_MERCHANT_MC");
        else
        if(roll == 3)
            CardCrawlGame.sound.play("VO_MERCHANT_3A");
        else
        if(roll == 4)
            CardCrawlGame.sound.play("VO_MERCHANT_3B");
        else
            CardCrawlGame.sound.play("VO_MERCHANT_3C");
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(Color.WHITE);
        sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X + modX, DRAW_Y + modY, 512F * Settings.scale, 512F * Settings.scale);
        if(hb.hovered)
        {
            sb.setBlendFunction(770, 1);
            sb.setColor(Settings.HALF_TRANSPARENT_WHITE_COLOR);
            sb.draw(ImageMaster.MERCHANT_RUG_IMG, DRAW_X + modX, DRAW_Y + modY, 512F * Settings.scale, 512F * Settings.scale);
            sb.setBlendFunction(770, 771);
        }
        if(anim != null)
            anim.render(sb);
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), (DRAW_X - 32F) + 150F * Settings.scale, (DRAW_Y - 32F) + 100F * Settings.scale, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
        hb.render(sb);
    }

    public void dispose()
    {
        if(anim != null)
            anim.dispose();
    }

    private static final CharacterStrings characterStrings;
    public static final String NAMES[];
    public static final String TEXT[];
    public static final String ENDING_TEXT[];
    public AnimatedNpc anim;
    public static final float DRAW_X;
    public static final float DRAW_Y;
    public Hitbox hb;
    private ArrayList cards1;
    private ArrayList cards2;
    private ArrayList idleMessages;
    private float speechTimer;
    private boolean saidWelcome;
    private static final float MIN_IDLE_MSG_TIME = 40F;
    private static final float MAX_IDLE_MSG_TIME = 60F;
    private static final float SPEECH_DURATION = 3F;
    private int shopScreen;
    protected float modX;
    protected float modY;

    static 
    {
        characterStrings = CardCrawlGame.languagePack.getCharacterString("Merchant");
        NAMES = characterStrings.NAMES;
        TEXT = characterStrings.TEXT;
        ENDING_TEXT = characterStrings.OPTIONS;
        DRAW_X = (float)Settings.WIDTH * 0.5F + 34F * Settings.xScale;
        DRAW_Y = AbstractDungeon.floorY - 109F * Settings.scale;
    }
}
