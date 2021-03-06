/*
 *  Copyright (c) 2014, Lukas Tenbrink.
 *  * http://lukas.axxim.net
 */

package ivorius.reccomplex.gui.editstructure;

import ivorius.reccomplex.gui.table.*;
import ivorius.reccomplex.worldgen.genericStructures.BiomeGenerationInfo;

import java.util.Collections;
import java.util.List;

/**
 * Created by lukas on 04.06.14.
 */
public class TableDataSourceBiomeGenList implements TableDataSource, TableElementButton.Listener, TableElementPresetAction.Listener
{
    private List<BiomeGenerationInfo> biomeGenerationInfoList;

    private TableDelegate tableDelegate;
    private TableNavigator navigator;

    public TableDataSourceBiomeGenList(List<BiomeGenerationInfo> biomeGenerationInfoList, TableDelegate tableDelegate, TableNavigator navigator)
    {
        this.biomeGenerationInfoList = biomeGenerationInfoList;
        this.tableDelegate = tableDelegate;
        this.navigator = navigator;
    }

    public List<BiomeGenerationInfo> getBiomeGenerationInfoList()
    {
        return Collections.unmodifiableList(biomeGenerationInfoList);
    }

    public void setBiomeGenerationInfoList(List<BiomeGenerationInfo> biomeGenerationInfoList)
    {
        this.biomeGenerationInfoList = biomeGenerationInfoList;
    }

    public TableDelegate getTableDelegate()
    {
        return tableDelegate;
    }

    public void setTableDelegate(TableDelegate tableDelegate)
    {
        this.tableDelegate = tableDelegate;
    }

    public TableNavigator getNavigator()
    {
        return navigator;
    }

    public void setNavigator(TableNavigator navigator)
    {
        this.navigator = navigator;
    }

    @Override
    public boolean has(GuiTable table, int index)
    {
        return index >= 0 && index < biomeGenerationInfoList.size() + 2;
    }

    @Override
    public TableElement elementForIndex(GuiTable table, int index)
    {
        if (index == 0)
        {
            TableElementPresetAction elementPresetAction = new TableElementPresetAction("biomePreset", "Presets", "Apply", new TableElementButton.Action("overworld", "Overworld"), new TableElementButton.Action("underground", "Underground"), new TableElementButton.Action("ocean", "Ocean"), new TableElementButton.Action("clear", "Clear"));
            elementPresetAction.addListener(this);
            return elementPresetAction;
        }
        else if (index == biomeGenerationInfoList.size() + 1)
        {
            TableElementButton addButton = new TableElementButton("addGen", "Add", new TableElementButton.Action("addGen", "Add Biome"));
            addButton.addListener(this);
            return addButton;
        }

        int biomeGenIndex = index - 1;
        TableElementButton.Action[] actions = {new TableElementButton.Action("earlier", "Earlier", biomeGenIndex > 0), new TableElementButton.Action("later", "Later", biomeGenIndex < biomeGenerationInfoList.size() - 1), new TableElementButton.Action("edit", "Edit"), new TableElementButton.Action("delete", "Delete")};
        BiomeGenerationInfo biomeGenerationInfo = biomeGenerationInfoList.get(biomeGenIndex);
        TableElementButton button = new TableElementButton("biomeGen" + biomeGenIndex, biomeGenerationInfo.getDisplayString() + " (" + biomeGenerationInfo.getActiveGenerationWeight() + ")", actions);
        button.addListener(this);
        return button;
    }

    @Override
    public void actionPerformed(TableElementButton tableElementButton, String actionID)
    {
        if (actionID.equals("addGen"))
        {
            BiomeGenerationInfo generationInfo = new BiomeGenerationInfo("", null);
            biomeGenerationInfoList.add(generationInfo);
            navigator.pushTable(new GuiTable(tableDelegate, new TableDataSourceBiomeGen(generationInfo, tableDelegate)));
        }
        else if (tableElementButton.getID().startsWith("biomeGen"))
        {
            int index = Integer.valueOf(tableElementButton.getID().substring(8));
            BiomeGenerationInfo generationInfo = biomeGenerationInfoList.get(index);

            switch (actionID)
            {
                case "edit":
                    navigator.pushTable(new GuiTable(tableDelegate, new TableDataSourceBiomeGen(generationInfo, tableDelegate)));
                    break;
                case "delete":
                    biomeGenerationInfoList.remove(generationInfo);
                    tableDelegate.reloadData();
                    break;
                case "earlier":
                    biomeGenerationInfoList.remove(index);
                    biomeGenerationInfoList.add(index - 1, generationInfo);
                    tableDelegate.reloadData();
                    break;
                case "later":
                    biomeGenerationInfoList.remove(index);
                    biomeGenerationInfoList.add(index + 1, generationInfo);
                    tableDelegate.reloadData();
                    break;
            }
        }
    }

    @Override
    public void actionPerformed(TableElementPresetAction tableElementButton, String actionID)
    {
        if (tableElementButton.getID().equals("biomePreset"))
        {
            switch (actionID)
            {
                case "overworld":
                    biomeGenerationInfoList.clear();
                    biomeGenerationInfoList.addAll(BiomeGenerationInfo.overworldBiomeGenerationList());
                    break;
                case "underground":
                    biomeGenerationInfoList.clear();
                    biomeGenerationInfoList.addAll(BiomeGenerationInfo.undergroundBiomeGenerationList());
                    break;
                case "ocean":
                    biomeGenerationInfoList.clear();
                    biomeGenerationInfoList.addAll(BiomeGenerationInfo.oceanBiomeGenerationList());
                    break;
                case "clear":
                    biomeGenerationInfoList.clear();
                    break;
            }

            tableDelegate.reloadData();
        }
    }
}
