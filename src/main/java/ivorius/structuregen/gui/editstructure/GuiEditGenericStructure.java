/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.structuregen.gui.editstructure;

import ivorius.structuregen.StructureGen;
import ivorius.structuregen.gui.table.Bounds;
import ivorius.structuregen.gui.table.GuiScreenModalTable;
import ivorius.structuregen.gui.table.GuiTable;
import ivorius.structuregen.worldgen.genericStructures.GenericStructureInfo;
import net.minecraft.client.gui.GuiButton;
import org.lwjgl.input.Keyboard;

/**
 * Created by lukas on 26.05.14.
 */
public class GuiEditGenericStructure extends GuiScreenModalTable
{
    TableDataSourceGenericStructure structureDataSource;

    public GuiEditGenericStructure(String key, GenericStructureInfo structureInfo)
    {
        GuiTable structureProperties = new GuiTable(this, structureDataSource = new TableDataSourceGenericStructure(structureInfo, key, this, this));
        setTable(structureProperties);
    }

    @Override
    public void initGui()
    {
        if (currentTable() != null)
        {
            currentTable().setPropertiesBounds(Bounds.boundsWithSize(width / 2 - 155, 310, height / 2 - 110, 205));
        }
        super.initGui();

        if (tableStack().size() == 1)
        {
            buttonList.add(new GuiButton(1, width / 2 - 155, height / 2 + 95, 154, 20, "Cancel"));
            buttonList.add(new GuiButton(0, width / 2 + 1, height / 2 + 95, 154, 20, "Save"));
        }
        else
        {
            buttonList.add(new GuiButton(2, width / 2 - 155, height / 2 + 95, 310, 20, "Back"));
        }
    }

    @Override
    protected void keyTyped(char keyChar, int keyCode)
    {
        if (keyCode != Keyboard.KEY_ESCAPE) // Prevent quitting without saving
        {
            super.keyTyped(keyChar, keyCode);
        }
    }

    @Override
    protected void actionPerformed(GuiButton button)
    {
        super.actionPerformed(button);

        if (button.id == 0)
        {
            StructureGen.chEditStructure.sendSaveEdit(this.mc.thePlayer, structureDataSource.getStructureInfo(), structureDataSource.getStructureKey());
            this.mc.thePlayer.closeScreen();
        }
        else if (button.id == 1)
        {
            this.mc.thePlayer.closeScreen();
        }
        else if (button.id == 2)
        {
            popTable();
        }
    }
}
