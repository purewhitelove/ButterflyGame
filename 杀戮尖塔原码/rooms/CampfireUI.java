// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   CampfireUI.java

package com.megacrit.cardcrawl.rooms;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.daily.mods.Midas;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.dungeons.TheEnding;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBar;
import com.megacrit.cardcrawl.screens.mainMenu.ScrollBarListener;
import com.megacrit.cardcrawl.ui.buttons.ConfirmButton;
import com.megacrit.cardcrawl.ui.campfire.*;
import com.megacrit.cardcrawl.ui.panels.PotionPopUp;
import com.megacrit.cardcrawl.ui.panels.TopPanel;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.campfire.*;
import java.util.ArrayList;
import java.util.Iterator;

// Referenced classes of package com.megacrit.cardcrawl.rooms:
//            AbstractRoom

public class CampfireUI
    implements ScrollBarListener
{

    public CampfireUI()
    {
        somethingSelected = false;
        hideStuffTimer = 0.5F;
        charAnimTimer = 2.0F;
        buttons = new ArrayList();
        bubbles = new ArrayList();
        fireTimer = 0.0F;
        flameEffect = new ArrayList();
        effect = new BobEffect(2.0F);
        grabbedScreen = false;
        grabStartY = 0.0F;
        scrollY = START_Y;
        targetY = scrollY;
        scrollLowerBound = (float)Settings.HEIGHT - 300F * Settings.scale;
        scrollUpperBound = 2400F * Settings.scale;
        confirmButton = new ConfirmButton();
        touchOption = null;
        scrollBar = new ScrollBar(this);
        hidden = false;
        initializeButtons();
        if(buttons.size() > 2)
            bubbleAmt = 60;
        else
            bubbleAmt = 40;
        bubbleMsg = getCampMessage();
    }

    private void initializeButtons()
    {
        buttons.add(new RestOption(true));
        buttons.add(new SmithOption(AbstractDungeon.player.masterDeck.getUpgradableCards().size() > 0 && !ModHelper.isModEnabled("Midas")));
        AbstractRelic r;
        for(Iterator iterator = AbstractDungeon.player.relics.iterator(); iterator.hasNext(); r.addCampfireOption(buttons))
            r = (AbstractRelic)iterator.next();

        Iterator iterator1 = buttons.iterator();
label0:
        do
        {
            if(!iterator1.hasNext())
                break;
            AbstractCampfireOption co = (AbstractCampfireOption)iterator1.next();
            Iterator iterator3 = AbstractDungeon.player.relics.iterator();
            AbstractRelic r;
            do
            {
                if(!iterator3.hasNext())
                    continue label0;
                r = (AbstractRelic)iterator3.next();
            } while(r.canUseCampfireOption(co));
            co.usable = false;
        } while(true);
        if(Settings.isFinalActAvailable && !Settings.hasRubyKey)
            buttons.add(new RecallOption());
        boolean cannotProceed = true;
        Iterator iterator2 = buttons.iterator();
        do
        {
            if(!iterator2.hasNext())
                break;
            AbstractCampfireOption opt = (AbstractCampfireOption)iterator2.next();
            if(!opt.usable)
                continue;
            cannotProceed = false;
            break;
        } while(true);
        if(cannotProceed)
        {
            AbstractRoom.waitTimer = 0.0F;
            AbstractDungeon.getCurrRoom().phase = AbstractRoom.RoomPhase.COMPLETE;
        }
    }

    public void update()
    {
        updateCharacterPosition();
        updateTouchscreen();
        updateControllerInput();
        if(!scrollBar.update())
            updateScrolling();
        effect.update();
        if(!hidden)
        {
            updateBubbles();
            updateFire();
            AbstractCampfireOption o;
            for(Iterator iterator = buttons.iterator(); iterator.hasNext(); o.update())
                o = (AbstractCampfireOption)iterator.next();

        }
        if(somethingSelected)
        {
            hideStuffTimer -= Gdx.graphics.getDeltaTime();
            if(hideStuffTimer < 0.0F)
                hidden = true;
        }
    }

    private void updateTouchscreen()
    {
        if(!Settings.isTouchScreen)
            return;
        confirmButton.update();
        if(confirmButton.hb.clicked && touchOption != null)
        {
            confirmButton.hb.clicked = false;
            confirmButton.hb.clickStarted = false;
            confirmButton.isDisabled = true;
            confirmButton.hide();
            touchOption.useOption();
            somethingSelected = true;
            touchOption = null;
        } else
        if(InputHelper.justReleasedClickLeft && touchOption != null)
        {
            touchOption = null;
            confirmButton.isDisabled = true;
            confirmButton.hide();
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || AbstractDungeon.player.viewingRelics || AbstractDungeon.topPanel.selectPotionMode || !AbstractDungeon.topPanel.potionUi.isHidden || somethingSelected || buttons.isEmpty())
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = buttons.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            AbstractCampfireOption o = (AbstractCampfireOption)iterator.next();
            if(o.hb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
            CInputHelper.setCursor(((AbstractCampfireOption)buttons.get(0)).hb);
        else
        if(CInputActionSet.left.isJustPressed() || CInputActionSet.altLeft.isJustPressed())
        {
            if(--index < 0)
            {
                if(buttons.size() == 2)
                    index = 1;
                else
                    index = 0;
            } else
            if(index == 1)
                if(buttons.size() == 4)
                    index = 3;
                else
                    index = 2;
            CInputHelper.setCursor(((AbstractCampfireOption)buttons.get(index)).hb);
        } else
        if(CInputActionSet.right.isJustPressed() || CInputActionSet.altRight.isJustPressed())
        {
            if(++index > buttons.size() - 1)
                if(buttons.size() == 2)
                    index = 0;
                else
                if(index == 3)
                    index = 2;
                else
                    index = 0;
            CInputHelper.setCursor(((AbstractCampfireOption)buttons.get(index)).hb);
        } else
        if((CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed()) && buttons.size() > 2)
        {
            if(buttons.size() == 5)
            {
                if((index -= 2) < 0)
                    index = 4;
            } else
            if(buttons.size() == 3)
            {
                if(index == 0)
                    index = 2;
                else
                if(index == 2)
                    index = 0;
            } else
            if(index == 0)
                index = 2;
            else
            if(index == 2)
                index = 0;
            else
            if(index == 3)
                index = 1;
            else
                index = 3;
            CInputHelper.setCursor(((AbstractCampfireOption)buttons.get(index)).hb);
        } else
        if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && buttons.size() > 2)
        {
            if(buttons.size() == 5)
            {
                if(index == 4)
                    index = 0;
                else
                if(index > 2)
                    index = 4;
                else
                    index += 2;
            } else
            if(buttons.size() == 4)
            {
                if(index >= 2)
                    index -= 2;
                else
                    index += 2;
            } else
            if(index == 0 || index == 1)
                index = 2;
            else
                index = 0;
            CInputHelper.setCursor(((AbstractCampfireOption)buttons.get(index)).hb);
        }
    }

    private void updateScrolling()
    {
        int y = InputHelper.mY;
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
        resetScrolling();
        updateBarPosition();
    }

    private void resetScrolling()
    {
        if(targetY < scrollLowerBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollLowerBound);
        else
        if(targetY > scrollUpperBound)
            targetY = MathHelper.scrollSnapLerpSpeed(targetY, scrollUpperBound);
    }

    private void updateCharacterPosition()
    {
        charAnimTimer -= Gdx.graphics.getDeltaTime();
        if(charAnimTimer < 0.0F)
            charAnimTimer = 0.0F;
        AbstractDungeon.player.animX = Interpolation.exp10In.apply(0.0F, -300F * Settings.scale, charAnimTimer / 2.0F);
    }

    private void updateBubbles()
    {
        if(bubbles.size() < bubbleAmt)
        {
            int s = bubbleAmt - bubbles.size();
            for(int i = 0; i < s; i++)
                bubbles.add(new CampfireBubbleEffect(bubbleAmt == 60));

        }
        Iterator i = bubbles.iterator();
        do
        {
            if(!i.hasNext())
                break;
            CampfireBubbleEffect bubble = (CampfireBubbleEffect)i.next();
            bubble.update();
            if(bubble.isDone)
                i.remove();
        } while(true);
    }

    private void updateFire()
    {
        fireTimer -= Gdx.graphics.getDeltaTime();
        if(fireTimer < 0.0F)
        {
            fireTimer = 0.05F;
            if(AbstractDungeon.id.equals("TheEnding"))
            {
                flameEffect.add(new CampfireEndingBurningEffect());
                flameEffect.add(new CampfireEndingBurningEffect());
                flameEffect.add(new CampfireEndingBurningEffect());
                flameEffect.add(new CampfireEndingBurningEffect());
            } else
            {
                flameEffect.add(new CampfireBurningEffect());
                flameEffect.add(new CampfireBurningEffect());
            }
        }
        Iterator i = flameEffect.iterator();
        do
        {
            if(!i.hasNext())
                break;
            AbstractGameEffect fires = (AbstractGameEffect)i.next();
            fires.update();
            if(fires.isDone)
                i.remove();
        } while(true);
    }

    public void reopen()
    {
        hidden = false;
        hideStuffTimer = 0.5F;
        somethingSelected = false;
    }

    public void render(SpriteBatch sb)
    {
        if(!hidden)
        {
            renderFire(sb);
            AbstractDungeon.player.render(sb);
            CampfireBubbleEffect e;
            for(Iterator iterator = bubbles.iterator(); iterator.hasNext(); e.render(sb, 950F * Settings.xScale, (float)Settings.HEIGHT / 2.0F + 60F * Settings.yScale + effect.y / 4F))
                e = (CampfireBubbleEffect)iterator.next();

            FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, bubbleMsg, 950F * Settings.xScale, (float)Settings.HEIGHT / 2.0F + 310F * Settings.scale + effect.y / 3F, Settings.CREAM_COLOR, 1.2F);
            renderCampfireButtons(sb);
            if(shouldShowScrollBar())
                scrollBar.render(sb);
            if(Settings.isTouchScreen)
                confirmButton.render(sb);
        }
    }

    private String getCampMessage()
    {
        ArrayList msgs = new ArrayList();
        msgs.add(TEXT[0]);
        msgs.add(TEXT[1]);
        msgs.add(TEXT[2]);
        msgs.add(TEXT[3]);
        if(buttons.size() > 2)
            msgs.add(TEXT[4]);
        if(AbstractDungeon.player.currentHealth < AbstractDungeon.player.maxHealth / 2)
        {
            msgs.add(TEXT[5]);
            msgs.add(TEXT[6]);
        }
        return (String)msgs.get(MathUtils.random(msgs.size() - 1));
    }

    private void renderFire(SpriteBatch sb)
    {
        AbstractGameEffect e;
        for(Iterator iterator = flameEffect.iterator(); iterator.hasNext(); e.render(sb))
            e = (AbstractGameEffect)iterator.next();

    }

    private void renderCampfireButtons(SpriteBatch sb)
    {
        float buttonX = 0.0F;
        float buttonY = 0.0F;
        int maxPossibleStartingIndex = (buttons.size() + 1) - 6;
        int indexToStartAt = Math.max(Math.min((int)((float)(maxPossibleStartingIndex + 1) * MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY)), maxPossibleStartingIndex), 0);
        indexToStartAt = MathUtils.ceil(indexToStartAt / 2) * 2;
        AbstractCampfireOption co;
        for(Iterator iterator = buttons.iterator(); iterator.hasNext(); co.render(sb))
        {
            co = (AbstractCampfireOption)iterator.next();
            if(buttons.indexOf(co) >= indexToStartAt && buttons.indexOf(co) < indexToStartAt + 6)
            {
                if(buttons.indexOf(co) == buttons.size() - 1 && (buttons.size() - indexToStartAt) % 2 == 1)
                    buttonX = BUTTON_START_X + BUTTON_SPACING_X / 2.0F;
                else
                if((buttons.indexOf(co) - indexToStartAt) % 2 == 0)
                    buttonX = BUTTON_START_X;
                else
                    buttonX = BUTTON_START_X + BUTTON_SPACING_X;
                if((buttons.indexOf(co) - indexToStartAt) / 2 == 0)
                    buttonY = BUTTON_START_Y;
                else
                    buttonY = BUTTON_START_Y + BUTTON_SPACING_Y * (float)MathUtils.floor((buttons.indexOf(co) - indexToStartAt) / 2) + BUTTON_EXTRA_SPACING_Y;
            } else
            {
                buttonX = (float)Settings.WIDTH * 2.0F;
                buttonY = (float)Settings.HEIGHT * 2.0F;
            }
            co.setPosition(buttonX, buttonY);
        }

    }

    public void scrolledUsingBar(float newPercent)
    {
        scrollY = MathHelper.valueFromPercentBetween(scrollLowerBound, scrollUpperBound, newPercent);
        targetY = scrollY;
        updateBarPosition();
    }

    private void updateBarPosition()
    {
        float percent = MathHelper.percentFromValueBetween(scrollLowerBound, scrollUpperBound, scrollY);
        scrollBar.parentScrolledToPercent(percent);
    }

    private boolean shouldShowScrollBar()
    {
        return buttons.size() > 6;
    }

    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static boolean hidden = false;
    public boolean somethingSelected;
    private float hideStuffTimer;
    private float charAnimTimer;
    private ArrayList buttons;
    private ArrayList bubbles;
    private float fireTimer;
    private static final float FIRE_INTERVAL = 0.05F;
    private ArrayList flameEffect;
    private int bubbleAmt;
    private String bubbleMsg;
    private BobEffect effect;
    private static final float BUTTON_START_X;
    private static final float BUTTON_SPACING_X;
    private static final float BUTTON_START_Y;
    private static final float BUTTON_SPACING_Y;
    private static final float BUTTON_EXTRA_SPACING_Y;
    private static final int MAX_BUTTONS_BEFORE_SCROLL = 6;
    private static final float START_Y;
    private boolean grabbedScreen;
    private float grabStartY;
    private float scrollY;
    private float targetY;
    private float scrollLowerBound;
    private float scrollUpperBound;
    private ScrollBar scrollBar;
    public ConfirmButton confirmButton;
    public AbstractCampfireOption touchOption;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("CampfireUI");
        TEXT = uiStrings.TEXT;
        BUTTON_START_X = (float)Settings.WIDTH * 0.416F;
        BUTTON_SPACING_X = 300F * Settings.xScale;
        BUTTON_START_Y = (float)Settings.HEIGHT / 2.0F + 180F * Settings.scale;
        BUTTON_SPACING_Y = -200F * Settings.scale;
        BUTTON_EXTRA_SPACING_Y = -70F * Settings.scale;
        START_Y = (float)Settings.HEIGHT - 300F * Settings.scale;
    }
}
