// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   GenericEventDialog.java

package com.megacrit.cardcrawl.events;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.DialogWord;
import com.megacrit.cardcrawl.ui.buttons.LargeDialogOptionButton;
import java.util.*;

public class GenericEventDialog
{

    public GenericEventDialog()
    {
        img = null;
        panelColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        titleColor = new Color(1.0F, 0.835F, 0.39F, 0.0F);
        borderColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        imgColor = new Color(1.0F, 1.0F, 1.0F, 0.0F);
        animateTimer = 0.0F;
        title = "";
        optionList = new ArrayList();
    }

    public void loadImage(String imgUrl)
    {
        if(img != null)
        {
            img.dispose();
            img = null;
        }
        img = ImageMaster.loadImage(imgUrl);
        DIALOG_MSG_X = DIALOG_MSG_X_IMAGE;
        DIALOG_MSG_W = DIALOG_MSG_W_IMAGE;
    }

    private void clearImage()
    {
        dispose();
        DIALOG_MSG_X = DIALOG_MSG_X_TEXT;
        DIALOG_MSG_Y = DIALOG_MSG_Y_TEXT;
        DIALOG_MSG_W = DIALOG_MSG_W_TEXT;
    }

    public void update()
    {
        animateIn();
        if(show && animateTimer == 0.0F)
        {
            for(int i = 0; i < optionList.size(); i++)
            {
                ((LargeDialogOptionButton)optionList.get(i)).update(optionList.size());
                if(((LargeDialogOptionButton)optionList.get(i)).pressed && waitForInput)
                {
                    selectedOption = i;
                    ((LargeDialogOptionButton)optionList.get(i)).pressed = false;
                    waitForInput = false;
                }
            }

        }
        if(!Settings.lineBreakViaCharacter)
            bodyTextEffect();
        else
            bodyTextEffectCN();
        DialogWord w;
        for(Iterator iterator = words.iterator(); iterator.hasNext(); w.update())
            w = (DialogWord)iterator.next();

    }

    private void animateIn()
    {
        if(show)
        {
            animateTimer -= Gdx.graphics.getDeltaTime();
            if(animateTimer < 0.0F)
                animateTimer = 0.0F;
            panelColor.a = MathHelper.slowColorLerpSnap(panelColor.a, 1.0F);
            if(panelColor.a > 0.8F)
            {
                titleColor.a = MathHelper.slowColorLerpSnap(titleColor.a, 1.0F);
                borderColor.a = titleColor.a;
                if(borderColor.a > 0.8F)
                    imgColor.a = MathHelper.slowColorLerpSnap(imgColor.a, 1.0F);
            }
        }
    }

    public static int getSelectedOption()
    {
        waitForInput = true;
        return selectedOption;
    }

    public static void hide()
    {
        show = false;
    }

    public static void show()
    {
        show = true;
    }

    public void clear()
    {
        show = false;
        clearImage();
        animateTimer = 0.0F;
        panelColor.a = 0.0F;
        titleColor.a = 0.0F;
        imgColor.a = 0.0F;
        borderColor.a = 0.0F;
        optionList.clear();
        words.clear();
        waitForInput = true;
    }

    public static void cleanUp()
    {
        words.clear();
        show = false;
        waitForInput = true;
    }

    public void show(String title, String text)
    {
        this.title = title;
        updateBodyText(text);
        if(Settings.FAST_MODE)
            animateTimer = 0.125F;
        else
            animateTimer = 0.5F;
        show = true;
    }

    public void clearAllDialogs()
    {
        optionList.clear();
    }

    public void removeDialogOption(int slot)
    {
        if(slot < optionList.size())
            optionList.remove(slot);
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void clearRemainingOptions()
    {
        for(int i = optionList.size() - 1; i > 0; i--)
            optionList.remove(i);

        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, AbstractCard previewCard)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewCard));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewRelic));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, previewCard, previewRelic));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, boolean isDisabled)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, boolean isDisabled, AbstractCard previewCard)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewCard));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, boolean isDisabled, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewRelic));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void setDialogOption(String text, boolean isDisabled, AbstractCard previewCard, AbstractRelic previewRelic)
    {
        optionList.add(new LargeDialogOptionButton(optionList.size(), text, isDisabled, previewCard, previewRelic));
        LargeDialogOptionButton b;
        for(Iterator iterator = optionList.iterator(); iterator.hasNext(); b.calculateY(optionList.size()))
            b = (LargeDialogOptionButton)iterator.next();

    }

    public void updateDialogOption(int slot, String text)
    {
        if(!optionList.isEmpty())
        {
            if(optionList.size() > slot)
                optionList.set(slot, new LargeDialogOptionButton(slot, text));
            else
                optionList.add(new LargeDialogOptionButton(slot, text));
        } else
        {
            optionList.add(new LargeDialogOptionButton(slot, text));
        }
    }

    public void updateDialogOption(int slot, String text, boolean isDisabled)
    {
        if(!optionList.isEmpty())
        {
            if(optionList.size() > slot)
                optionList.set(slot, new LargeDialogOptionButton(slot, text, isDisabled));
            else
                optionList.add(new LargeDialogOptionButton(slot, text, isDisabled));
        } else
        {
            optionList.add(new LargeDialogOptionButton(slot, text, isDisabled));
        }
    }

    public void updateDialogOption(int slot, String text, AbstractCard previewCard)
    {
        if(!optionList.isEmpty())
        {
            if(optionList.size() > slot)
                optionList.set(slot, new LargeDialogOptionButton(slot, text, previewCard));
            else
                optionList.add(new LargeDialogOptionButton(slot, text, previewCard));
        } else
        {
            optionList.add(new LargeDialogOptionButton(slot, text, previewCard));
        }
    }

    public void updateBodyText(String text)
    {
        updateBodyText(text, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect.BUMP_IN);
    }

    public void updateBodyText(String text, com.megacrit.cardcrawl.ui.DialogWord.AppearEffect ae)
    {
        s = new Scanner(text);
        words.clear();
        textDone = false;
        a_effect = ae;
        curLineWidth = 0.0F;
        curLine = 0;
    }

    private void bodyTextEffectCN()
    {
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
        {
            if(Settings.FAST_MODE)
                wordTimer = 0.005F;
            else
                wordTimer = 0.02F;
            if(s.hasNext())
            {
                String word = s.next();
                if(word.equals("NL"))
                {
                    curLine++;
                    curLineWidth = 0.0F;
                    return;
                }
                com.megacrit.cardcrawl.ui.DialogWord.WordColor color = DialogWord.identifyWordColor(word);
                if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                    word = word.substring(2, word.length());
                com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
                if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                    word = word.substring(1, word.length() - 1);
                for(int i = 0; i < word.length(); i++)
                {
                    String tmp = Character.toString(word.charAt(i));
                    gl.setText(FontHelper.charDescFont, tmp);
                    if(curLineWidth + gl.width > DIALOG_MSG_W)
                    {
                        curLine++;
                        curLineWidth = gl.width;
                    } else
                    {
                        curLineWidth += gl.width;
                    }
                    words.add(new DialogWord(FontHelper.charDescFont, tmp, a_effect, effect, color, (DIALOG_MSG_X + curLineWidth) - gl.width, DIALOG_MSG_Y - LINE_SPACING * (float)curLine, curLine));
                    if(!show)
                        ((DialogWord)words.get(words.size() - 1)).dialogFadeOut();
                }

            } else
            {
                textDone = true;
                s.close();
            }
        }
    }

    private void bodyTextEffect()
    {
        wordTimer -= Gdx.graphics.getDeltaTime();
        if(wordTimer < 0.0F && !textDone)
        {
            if(Settings.FAST_MODE)
                wordTimer = 0.005F;
            else
                wordTimer = 0.02F;
            if(s.hasNext())
            {
                String word = s.next();
                if(word.equals("NL"))
                {
                    curLine++;
                    curLineWidth = 0.0F;
                    return;
                }
                com.megacrit.cardcrawl.ui.DialogWord.WordColor color = DialogWord.identifyWordColor(word);
                if(color != com.megacrit.cardcrawl.ui.DialogWord.WordColor.DEFAULT)
                    word = word.substring(2, word.length());
                com.megacrit.cardcrawl.ui.DialogWord.WordEffect effect = DialogWord.identifyWordEffect(word);
                if(effect != com.megacrit.cardcrawl.ui.DialogWord.WordEffect.NONE)
                    word = word.substring(1, word.length() - 1);
                gl.setText(FontHelper.charDescFont, word);
                if(curLineWidth + gl.width > DIALOG_MSG_W)
                {
                    curLine++;
                    curLineWidth = gl.width + CHAR_SPACING;
                } else
                {
                    curLineWidth += gl.width + CHAR_SPACING;
                }
                words.add(new DialogWord(FontHelper.charDescFont, word, a_effect, effect, color, (DIALOG_MSG_X + curLineWidth) - gl.width, DIALOG_MSG_Y - LINE_SPACING * (float)curLine, curLine));
                if(!show)
                    ((DialogWord)words.get(words.size() - 1)).dialogFadeOut();
            } else
            {
                textDone = true;
                s.close();
            }
        }
    }

    public void render(SpriteBatch sb)
    {
        if(show && !AbstractDungeon.player.isDead)
        {
            sb.setColor(panelColor);
            sb.draw(AbstractDungeon.eventBackgroundImg, (float)Settings.WIDTH / 2.0F - 881.5F - 12F * Settings.xScale, (Settings.EVENT_Y - 403F) + 64F * Settings.scale, 881.5F, 403F, 1763F, 806F, Settings.xScale, Settings.scale, 0.0F, 0, 0, 1763, 806, false, false);
            if(img != null)
            {
                sb.setColor(imgColor);
                sb.draw(img, 460F * Settings.xScale - 300F, (Settings.EVENT_Y - 300F) + 16F * Settings.scale, 300F, 300F, 600F, 600F, Settings.scale, Settings.scale, 0.0F, 0, 0, 600, 600, false, false);
                sb.setColor(borderColor);
                sb.draw(ImageMaster.EVENT_IMG_FRAME, 460F * Settings.xScale - 305F, (Settings.EVENT_Y - 305F) + 16F * Settings.scale, 305F, 305F, 610F, 610F, Settings.scale, Settings.scale, 0.0F, 0, 0, 610, 610, false, false);
            }
            FontHelper.renderFontCentered(sb, FontHelper.losePowerFont, title, TITLE_X, TITLE_Y, titleColor, 0.88F);
            DialogWord w;
            for(Iterator iterator = words.iterator(); iterator.hasNext(); w.render(sb))
                w = (DialogWord)iterator.next();

            LargeDialogOptionButton b;
            for(Iterator iterator1 = optionList.iterator(); iterator1.hasNext(); b.render(sb))
                b = (LargeDialogOptionButton)iterator1.next();

            LargeDialogOptionButton b;
            for(Iterator iterator2 = optionList.iterator(); iterator2.hasNext(); b.renderCardPreview(sb))
                b = (LargeDialogOptionButton)iterator2.next();

            LargeDialogOptionButton b;
            for(Iterator iterator3 = optionList.iterator(); iterator3.hasNext(); b.renderRelicPreview(sb))
                b = (LargeDialogOptionButton)iterator3.next();

        }
    }

    public void dispose()
    {
        if(img != null)
        {
            img.dispose();
            img = null;
            show = false;
        }
    }

    private Texture img;
    private static final float DIALOG_MSG_X_IMAGE;
    private static final float DIALOG_MSG_W_IMAGE;
    private Color panelColor;
    private Color titleColor;
    private Color borderColor;
    private Color imgColor;
    private float animateTimer;
    private static final float ANIM_SPEED = 0.5F;
    private static boolean show = false;
    private String title;
    private static final float TITLE_X;
    private static final float TITLE_Y;
    private static float curLineWidth = 0.0F;
    private static int curLine = 0;
    private static com.megacrit.cardcrawl.ui.DialogWord.AppearEffect a_effect;
    private static Scanner s;
    private static GlyphLayout gl = new GlyphLayout();
    private static ArrayList words = new ArrayList();
    private static boolean textDone = true;
    private static float wordTimer = 0.0F;
    private static final float WORD_TIME = 0.02F;
    private static final float CHAR_SPACING;
    private static final float LINE_SPACING;
    private static final float DIALOG_MSG_X_TEXT;
    private static final float DIALOG_MSG_Y_TEXT;
    private static final float DIALOG_MSG_W_TEXT;
    private static float DIALOG_MSG_X;
    private static float DIALOG_MSG_Y;
    private static float DIALOG_MSG_W;
    public ArrayList optionList;
    public static int selectedOption = -1;
    public static boolean waitForInput = true;

    static 
    {
        DIALOG_MSG_X_IMAGE = 816F * Settings.xScale;
        DIALOG_MSG_W_IMAGE = 900F * Settings.scale;
        TITLE_X = 570F * Settings.xScale;
        TITLE_Y = Settings.EVENT_Y + 408F * Settings.scale;
        CHAR_SPACING = 8F * Settings.scale;
        LINE_SPACING = Settings.BIG_TEXT_MODE ? 40F * Settings.scale : 38F * Settings.scale;
        DIALOG_MSG_X_TEXT = 455F * Settings.xScale;
        DIALOG_MSG_Y_TEXT = Settings.isMobile ? Settings.EVENT_Y + 330F * Settings.scale : Settings.EVENT_Y + 300F * Settings.scale;
        DIALOG_MSG_W_TEXT = 1000F * Settings.scale;
        DIALOG_MSG_X = DIALOG_MSG_X_TEXT;
        DIALOG_MSG_Y = DIALOG_MSG_Y_TEXT;
        DIALOG_MSG_W = DIALOG_MSG_W_TEXT;
    }
}
