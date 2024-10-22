// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   SaveSlotScreen.java

package com.megacrit.cardcrawl.screens.mainMenu;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.*;
import com.megacrit.cardcrawl.helpers.controller.*;
import com.megacrit.cardcrawl.helpers.input.InputAction;
import com.megacrit.cardcrawl.helpers.input.InputActionSet;
import com.megacrit.cardcrawl.localization.LocalizedStrings;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.screens.charSelect.CharacterSelectScreen;
import com.megacrit.cardcrawl.ui.panels.DeleteSaveConfirmPopup;
import com.megacrit.cardcrawl.ui.panels.RenamePopup;
import java.util.ArrayList;
import java.util.Iterator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// Referenced classes of package com.megacrit.cardcrawl.screens.mainMenu:
//            MenuCancelButton, SaveSlot, MainMenuScreen

public class SaveSlotScreen
{
    public static final class CurrentPopup extends Enum
    {

        public static CurrentPopup[] values()
        {
            return (CurrentPopup[])$VALUES.clone();
        }

        public static CurrentPopup valueOf(String name)
        {
            return (CurrentPopup)Enum.valueOf(com/megacrit/cardcrawl/screens/mainMenu/SaveSlotScreen$CurrentPopup, name);
        }

        public static final CurrentPopup DELETE;
        public static final CurrentPopup RENAME;
        public static final CurrentPopup NONE;
        private static final CurrentPopup $VALUES[];

        static 
        {
            DELETE = new CurrentPopup("DELETE", 0);
            RENAME = new CurrentPopup("RENAME", 1);
            NONE = new CurrentPopup("NONE", 2);
            $VALUES = (new CurrentPopup[] {
                DELETE, RENAME, NONE
            });
        }

        private CurrentPopup(String s, int i)
        {
            super(s, i);
        }
    }


    public SaveSlotScreen()
    {
        screenColor = new Color(0.0F, 0.0F, 0.0F, 0.0F);
        uiColor = new Color(1.0F, 0.965F, 0.886F, 0.0F);
        shown = false;
        slots = new ArrayList();
        cancelButton = new MenuCancelButton();
        renamePopup = new RenamePopup();
        deletePopup = new DeleteSaveConfirmPopup();
        curPop = CurrentPopup.NONE;
    }

    public void update()
    {
        deletePopup.update();
        renamePopup.update();
        updateColors();
        static class _cls1
        {

            static final int $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$SaveSlotScreen$CurrentPopup[];

            static 
            {
                $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$SaveSlotScreen$CurrentPopup = new int[CurrentPopup.values().length];
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$SaveSlotScreen$CurrentPopup[CurrentPopup.DELETE.ordinal()] = 1;
                }
                catch(NoSuchFieldError nosuchfielderror) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$SaveSlotScreen$CurrentPopup[CurrentPopup.NONE.ordinal()] = 2;
                }
                catch(NoSuchFieldError nosuchfielderror1) { }
                try
                {
                    $SwitchMap$com$megacrit$cardcrawl$screens$mainMenu$SaveSlotScreen$CurrentPopup[CurrentPopup.RENAME.ordinal()] = 3;
                }
                catch(NoSuchFieldError nosuchfielderror2) { }
            }
        }

        switch(_cls1..SwitchMap.com.megacrit.cardcrawl.screens.mainMenu.SaveSlotScreen.CurrentPopup[curPop.ordinal()])
        {
        case 2: // '\002'
            if(shown)
            {
                updateSaveSlots();
                updateControllerInput();
            }
            updateCancelButton();
            break;
        }
    }

    private void updateCancelButton()
    {
        cancelButton.update();
        if(cancelButton.hb.clicked || !cancelButton.isHidden && InputActionSet.cancel.isJustPressed())
        {
            cancelButton.hb.clicked = false;
            if(!((SaveSlot)slots.get(CardCrawlGame.saveSlot)).emptySlot)
            {
                confirm(CardCrawlGame.saveSlot);
            } else
            {
                for(int i = 0; i < 3; i++)
                    if(!((SaveSlot)slots.get(i)).emptySlot)
                        confirm(i);

            }
        }
    }

    private void updateControllerInput()
    {
        if(!Settings.isControllerMode || slots.isEmpty())
            return;
        boolean anyHovered = false;
        int index = 0;
        Iterator iterator = slots.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            SaveSlot slot = (SaveSlot)iterator.next();
            if(slot.slotHb.hovered)
            {
                anyHovered = true;
                break;
            }
            index++;
        } while(true);
        if(!anyHovered)
        {
            CInputHelper.setCursor(((SaveSlot)slots.get(0)).slotHb);
        } else
        {
            if(CInputActionSet.up.isJustPressed() || CInputActionSet.altUp.isJustPressed())
            {
                if(--index < 0)
                    index = 2;
            } else
            if((CInputActionSet.down.isJustPressed() || CInputActionSet.altDown.isJustPressed()) && ++index > 2)
                index = 0;
            CInputHelper.setCursor(((SaveSlot)slots.get(index)).slotHb);
        }
    }

    private void updateColors()
    {
        if(shown)
        {
            screenColor.a = MathHelper.fadeLerpSnap(screenColor.a, 0.75F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 1.0F);
        } else
        {
            screenColor.a = MathHelper.fadeLerpSnap(screenColor.a, 0.0F);
            uiColor.a = MathHelper.fadeLerpSnap(uiColor.a, 0.0F);
        }
    }

    private void updateSaveSlots()
    {
        SaveSlot slot;
        for(Iterator iterator = slots.iterator(); iterator.hasNext(); slot.update())
            slot = (SaveSlot)iterator.next();

    }

    public void open(String curName)
    {
        if(slots.isEmpty())
        {
            slots.add(new SaveSlot(0));
            slots.add(new SaveSlot(1));
            slots.add(new SaveSlot(2));
            SaveSlot.uiColor = uiColor;
        }
        shown = true;
        Iterator iterator = slots.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            SaveSlot s = (SaveSlot)iterator.next();
            if(s.emptySlot)
                continue;
            cancelButton.show(CharacterSelectScreen.TEXT[5]);
            break;
        } while(true);
    }

    public void rename(int slot, String name)
    {
        ((SaveSlot)slots.get(slot)).setName(name);
    }

    public void confirm(int slot)
    {
        shown = false;
        CardCrawlGame.saveSlot = slot;
        CardCrawlGame.playerName = ((SaveSlot)slots.get(slot)).getName();
        CardCrawlGame.mainMenuScreen.screen = MainMenuScreen.CurScreen.MAIN_MENU;
        cancelButton.hide();
        if(CardCrawlGame.saveSlotPref.getInteger("DEFAULT_SLOT", -1) != slot || slotDeleted)
        {
            logger.info((new StringBuilder()).append("Default slot updated: ").append(slot).toString());
            CardCrawlGame.saveSlotPref.putInteger("DEFAULT_SLOT", slot);
            CardCrawlGame.reloadPrefs();
            CardCrawlGame.saveSlotPref.flush();
        }
    }

    public void render(SpriteBatch sb)
    {
        sb.setColor(screenColor);
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, 0.0F, 0.0F, Settings.WIDTH, Settings.HEIGHT);
        if(shown)
        {
            renderSaveSlots(sb);
            deletePopup.render(sb);
            renamePopup.render(sb);
            if(curPop == CurrentPopup.NONE)
            {
                cancelButton.render(sb);
                renderSelectSlotMessage(sb);
            }
        }
    }

    private void renderSelectSlotMessage(SpriteBatch sb)
    {
        boolean showingTip = false;
        Iterator iterator = slots.iterator();
        do
        {
            if(!iterator.hasNext())
                break;
            SaveSlot s = (SaveSlot)iterator.next();
            if(s.renameHb.hovered)
            {
                FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, TEXT[1], (float)Settings.WIDTH / 2.0F, 80F * Settings.scale, Settings.BLUE_TEXT_COLOR);
                showingTip = true;
                break;
            }
            if(!s.deleteHb.hovered)
                continue;
            FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, TEXT[2], (float)Settings.WIDTH / 2.0F, 80F * Settings.scale, Settings.RED_TEXT_COLOR);
            showingTip = true;
            break;
        } while(true);
        if(!showingTip)
            FontHelper.renderFontCentered(sb, FontHelper.topPanelAmountFont, TEXT[0], (float)Settings.WIDTH / 2.0F, 80F * Settings.scale, Settings.CREAM_COLOR);
        if(Settings.isControllerMode)
        {
            sb.setColor(Color.WHITE);
            sb.draw(CInputActionSet.select.getKeyImg(), (float)Settings.WIDTH / 2.0F - FontHelper.getSmartWidth(FontHelper.topPanelAmountFont, TEXT[0], 99999F, 0.0F) / 2.0F - 32F - 48F * Settings.scale, 80F * Settings.scale - 32F, 32F, 32F, 64F, 64F, Settings.scale, Settings.scale, 0.0F, 0, 0, 64, 64, false, false);
        }
    }

    private void renderSaveSlots(SpriteBatch sb)
    {
        sb.setColor(uiColor);
        SaveSlot slot;
        for(Iterator iterator = slots.iterator(); iterator.hasNext(); slot.render(sb))
            slot = (SaveSlot)iterator.next();

    }

    public void openDeletePopup(int index)
    {
        deletePopup.open(index);
        curPop = CurrentPopup.DELETE;
    }

    public void deleteSlot(int index)
    {
        CardCrawlGame.saveSlotPref.putString(SaveHelper.slotName("PROFILE_NAME", index), "");
        ((SaveSlot)slots.get(index)).clear();
    }

    public void openRenamePopup(int index, boolean newSave)
    {
        renamePopup.open(index, newSave);
        curPop = CurrentPopup.RENAME;
    }

    private static final Logger logger = LogManager.getLogger(com/megacrit/cardcrawl/screens/mainMenu/SaveSlotScreen.getName());
    private static final UIStrings uiStrings;
    public static final String TEXT[];
    public static boolean slotDeleted = false;
    private Color screenColor;
    public Color uiColor;
    public boolean shown;
    public ArrayList slots;
    public MenuCancelButton cancelButton;
    private RenamePopup renamePopup;
    private DeleteSaveConfirmPopup deletePopup;
    public CurrentPopup curPop;

    static 
    {
        uiStrings = CardCrawlGame.languagePack.getUIString("SaveSlotScreen");
        TEXT = uiStrings.TEXT;
    }
}
