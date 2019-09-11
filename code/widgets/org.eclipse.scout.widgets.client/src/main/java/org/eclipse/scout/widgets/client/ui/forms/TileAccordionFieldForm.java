/*
 * Copyright (c) 2014-2019 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
package org.eclipse.scout.widgets.client.ui.forms;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.ui.IWidget;
import org.eclipse.scout.rt.client.ui.action.keystroke.IKeyStroke;
import org.eclipse.scout.rt.client.ui.action.menu.AbstractMenu;
import org.eclipse.scout.rt.client.ui.action.menu.IMenuType;
import org.eclipse.scout.rt.client.ui.action.menu.TileGridMenuType;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.fields.accordionfield.AbstractAccordionField;
import org.eclipse.scout.rt.client.ui.form.fields.booleanfield.AbstractBooleanField;
import org.eclipse.scout.rt.client.ui.form.fields.button.AbstractCloseButton;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.integerfield.AbstractIntegerField;
import org.eclipse.scout.rt.client.ui.form.fields.labelfield.AbstractLabelField;
import org.eclipse.scout.rt.client.ui.form.fields.smartfield.AbstractSmartField;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.group.AbstractGroup;
import org.eclipse.scout.rt.client.ui.group.IGroup;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileAccordion;
import org.eclipse.scout.rt.client.ui.tile.AbstractTileGrid;
import org.eclipse.scout.rt.client.ui.tile.DefaultGroupManager;
import org.eclipse.scout.rt.client.ui.tile.ITileAccordionGroupManager;
import org.eclipse.scout.rt.client.ui.tile.ITileGrid;
import org.eclipse.scout.rt.client.ui.tile.TileGridLayoutConfig;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.classid.ClassId;
import org.eclipse.scout.rt.platform.exception.VetoException;
import org.eclipse.scout.rt.platform.text.TEXTS;
import org.eclipse.scout.rt.platform.util.CollectionUtility;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.data.tile.TileColorScheme;
import org.eclipse.scout.rt.shared.services.lookup.ILookupCall;
import org.eclipse.scout.widgets.client.services.lookup.CollapseStyleLookupCall;
import org.eclipse.scout.widgets.client.services.lookup.IconIdLookupCall;
import org.eclipse.scout.widgets.client.ui.desktop.outlines.IAdvancedExampleForm;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.CloseButton;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.AccordionField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.AccordionField.Accordion;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.FilterField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.DetailBox.StatusField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.CustomerHeaderWidgetField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.ExclusiveExpandField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.GroupIconIdField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.ScrollableField;
import org.eclipse.scout.widgets.client.ui.forms.TileAccordionFieldForm.MainBox.PropertiesBox.VirtualField;
import org.eclipse.scout.widgets.client.ui.forms.TileFieldForm.SimpleTile;
import org.eclipse.scout.widgets.client.ui.template.formfield.AbstractFormFieldPropertiesBox;
import org.eclipse.scout.widgets.client.ui.tile.CustomTileFilter;
import org.eclipse.scout.widgets.client.ui.tile.CustomTileGroupManager;
import org.eclipse.scout.widgets.client.ui.tile.ICustomTile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ClassId("a3b0a11c-8be7-424b-9a97-5902af85809a")
public class TileAccordionFieldForm extends AbstractForm implements IAdvancedExampleForm {
  private static final Logger LOG = LoggerFactory.getLogger(TileAccordionFieldForm.class);

  private int m_tilesAddedCount = 0;
  private CustomTileFilter m_tileFilter;

  @Override
  public void startPageForm() {
    start();
  }

  public GroupIconIdField getGroupIconIdField() {
    return getFieldByClass(GroupIconIdField.class);
  }

  public ScrollableField getScrollableField() {
    return getFieldByClass(ScrollableField.class);
  }

  public AccordionField getAccordionField() {
    return getFieldByClass(AccordionField.class);
  }

  public DetailBox getDetailBox() {
    return getFieldByClass(DetailBox.class);
  }

  public ExclusiveExpandField getExclusiveExpandField() {
    return getFieldByClass(ExclusiveExpandField.class);
  }

  public FilterField getFilterField() {
    return getFieldByClass(FilterField.class);
  }

  public StatusField getStatusField() {
    return getFieldByClass(StatusField.class);
  }

  public VirtualField getVirtualField() {
    return getFieldByClass(VirtualField.class);
  }

  public CustomerHeaderWidgetField getCustomerHeaderWidgetField() {
    return getFieldByClass(CustomerHeaderWidgetField.class);
  }

  public PropertiesBox getPropertiesBox() {
    return getFieldByClass(PropertiesBox.class);
  }

  @Override
  public AbstractCloseButton getCloseButton() {
    return getFieldByClass(CloseButton.class);
  }

  @Override
  protected String getConfiguredTitle() {
    return "AccordionField";
  }

  @ClassId("eb0180d2-0c32-4198-9771-3dfef2a3662b")
  public class MainBox extends AbstractGroupBox {

    @Order(100)
    @ClassId("eab5ced8-eceb-47d3-92f0-affda2c1547d")
    public class DetailBox extends AbstractGroupBox {

      @Order(1000)
      @ClassId("65daec6c-698e-4f7e-aa92-a80d82ed05f8")
      public class AddMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add tile to group A";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.INSERT;
        }

        @Override
        protected void execAction() {
          SimpleTile tile = new SimpleTile();
          tile.setLabel("New tile " + m_tilesAddedCount++);
          tile.setGroup("Group A");
          tile.setColorScheme(TileColorScheme.DEFAULT);
          getAccordionField().getAccordion().addTile(tile);
        }
      }

      @Order(1010)
      @ClassId("1fd54668-d9e7-4afd-accd-0eaac0c3b191")
      public class AddToBMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add tile to group B";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          SimpleTile tile = new SimpleTile();
          tile.setLabel("New tile " + m_tilesAddedCount++);
          tile.setGroup("Group B");
          tile.setColorScheme(TileColorScheme.ALTERNATIVE);
          getAccordionField().getAccordion().addTile(tile);
        }
      }

      @Order(1050)
      @ClassId("52bae9c0-c38c-4033-b8d8-c7f6a9a154a4")
      public class AddManyMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Add many tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          List<ICustomTile> tiles = new ArrayList<>();
          for (int i = 0; i < 25; i++) {
            SimpleTile tile = new SimpleTile();
            tile.setLabel("New tile " + m_tilesAddedCount++);
            tile.setGroup("Group A");
            tile.setColorScheme(TileColorScheme.DEFAULT);
            tiles.add(tile);
          }
          for (int i = 0; i < 25; i++) {
            SimpleTile tile = new SimpleTile();
            tile.setLabel("New tile " + m_tilesAddedCount++);
            tile.setGroup("Group B");
            tile.setColorScheme(TileColorScheme.ALTERNATIVE);
            tiles.add(tile);
          }
          getAccordionField().getAccordion().addTiles(tiles);
        }
      }

      @Order(1100)
      @ClassId("7cbd6273-77f6-4560-ab2e-7110a43298dc")
      public class SelectNextMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Select next tile";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          Accordion accordion = getAccordionField().getAccordion();
          if (accordion.getTiles().size() == 0) {
            return;
          }
          ICustomTile selectedTile = accordion.getSelectedTile();
          ICustomTile tileToSelect = null;
          if (selectedTile != null) {
            int selectedTileIndex = accordion.getTiles().indexOf(selectedTile);
            if (selectedTileIndex < accordion.getTiles().size() - 1) {
              tileToSelect = accordion.getTiles().get(selectedTileIndex + 1);
            }
          }
          if (tileToSelect == null) {
            tileToSelect = accordion.getTiles().get(0);
          }
          accordion.selectTile(tileToSelect);
        }
      }

      @Order(1200)
      @ClassId("5f6a9267-bdc2-427c-95fb-779c42cca9f2")
      public class SelectAllMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Select all tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.EmptySpace);
        }

        @Override
        protected void execAction() {
          getAccordionField().getAccordion().selectAllTiles();
        }
      }

      @Order(1300)
      @ClassId("6fd723b6-e00b-4c09-9fcb-8f9e8d4ded12")
      public class DeleteSelectedMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Delete selected tiles";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.<IMenuType> hashSet(TileGridMenuType.SingleSelection, TileGridMenuType.MultiSelection);
        }

        @Override
        protected String getConfiguredKeyStroke() {
          return IKeyStroke.DELETE;
        }

        @Override
        protected void execAction() {
          getAccordionField().getAccordion().deleteTiles(getAccordionField().getAccordion().getSelectedTiles());
          if (getAccordionField().getAccordion().getTileCount() == 0) {
            m_tilesAddedCount = 0;
          }
        }

        @Override
        protected void execOwnerValueChanged(Object newOwnerValue) {
          LOG.debug("ownerValueChanged" + newOwnerValue);
        }
      }

      @Order(1400)
      @ClassId("e77d6698-8e91-4a22-a17c-19e2a6da9017")
      public class SortMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Sort";
        }

        @Override
        protected Set<? extends IMenuType> getConfiguredMenuTypes() {
          return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
        }

        @Order(1000)
        @ClassId("5846d3dd-ad65-4ff2-b217-136e2ea20fdc")
        public class AscMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Ascending";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
          }

          @Override
          protected void execAction() {
            sortTiles(true);
          }
        }

        @Order(2000)
        @ClassId("dc737ecb-4d31-4a3b-8533-d133456b9282")
        public class DescMenu extends AbstractMenu {
          @Override
          protected String getConfiguredText() {
            return "Descending";
          }

          @Override
          protected Set<? extends IMenuType> getConfiguredMenuTypes() {
            return CollectionUtility.hashSet(TileGridMenuType.EmptySpace);
          }

          @Override
          protected void execAction() {
            sortTiles(false);
          }
        }

      }

      @Order(1500)
      @ClassId("9c3a2421-82a4-42a4-b455-87549a15173b")
      public class GroupMenu extends AbstractMenu {
        @Override
        protected String getConfiguredText() {
          return "Ungroup";
        }

        @Override
        protected void execAction() {
          if (getText().equals("Group")) {
            group();
            setText("Ungroup");
          }
          else {
            ungroup();
            setText("Group");
          }
        }

        @Override
        protected void execInitAction() {
          getAccordionField().getAccordion().addGroupManager(new CustomTileGroupManager());
          group();
        }
      }

      @Order(1000)
      @ClassId("37999d63-7407-44f5-b776-bb1f97344bcd")
      public class AccordionField extends AbstractAccordionField<AccordionField.Accordion> {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredGridW() {
          return FULL_WIDTH;
        }

        @Override
        protected void execInitField() {
          super.execInitField();
          updateStatus();
        }

        @ClassId("f59eaed0-afeb-48f8-a99d-cc4a15aa4253")
        public class Accordion extends AbstractTileAccordion<ICustomTile> {
          private P_PropertyChangeListener m_propertyChangeListener = new P_PropertyChangeListener();

          @Override
          protected String getConfiguredCssClass() {
            return "has-custom-tiles";
          }

          @Override
          protected void addGroupInternal(IGroup group) {
            super.addGroupInternal(group);
            if (isInitConfigDone()) {
              updateStatus();
            }
            group.getBody().addPropertyChangeListener(m_propertyChangeListener);
          }

          @Override
          protected void deleteGroupInternal(IGroup group) {
            super.deleteGroupInternal(group);
            if (isInitConfigDone()) {
              updateStatus();
            }
            group.getBody().removePropertyChangeListener(m_propertyChangeListener);
          }

          private class P_PropertyChangeListener implements PropertyChangeListener {

            @Override
            public void propertyChange(PropertyChangeEvent event) {
              if (event.getPropertyName().equals(ITileGrid.PROP_TILES) || event.getPropertyName().equals(ITileGrid.PROP_FILTERED_TILES) || event.getPropertyName().equals(ITileGrid.PROP_SELECTED_TILES)) {
                updateStatus();
              }
            }
          }

          @ClassId("0663a479-ff8f-4a72-b337-ff9759643955")
          public class TileGroup extends AbstractGroup {

            @Override
            public TileGrid getBody() {
              return (TileGrid) super.getBody();
            }

            @ClassId("3d7e78f9-75b1-48f5-aefe-2fb2118b7577")
            public class TileGrid extends AbstractTileGrid<ICustomTile> {

              @Override
              protected int getConfiguredGridColumnCount() {
                return 6;
              }

              @Override
              protected TileGridLayoutConfig getConfiguredLayoutConfig() {
                return super.getConfiguredLayoutConfig()
                    .withColumnWidth(100)
                    .withRowHeight(100);
              }

              @Override
              protected boolean getConfiguredScrollable() {
                return false;
              }
            }
          }
        }
      }

      @Order(2000)
      @ClassId("c29fa623-5430-4328-ba27-4d22d352d5fa")
      public class FilterField extends AbstractStringField {
        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("FilterBy");
        }

        @Override
        protected boolean getConfiguredUpdateDisplayTextOnModify() {
          return true;
        }

        @Override
        protected byte getConfiguredLabelPosition() {
          return LABEL_POSITION_ON_FIELD;
        }

        @Override
        protected String getConfiguredClearable() {
          return CLEARABLE_ALWAYS;
        }

        @Override
        protected int getConfiguredWidthInPixel() {
          return 300;
        }

        @Override
        protected boolean getConfiguredFillHorizontal() {
          return false;
        }

        @Override
        protected void execChangedDisplayText() {
          filterTilesByText(getDisplayText());
        }
      }

      @Order(3000)
      @ClassId("8fc514d5-342b-44fd-a7d4-1d7ce828ab58")
      @FormData(sdkCommand = FormData.SdkCommand.IGNORE)
      public class StatusField extends AbstractLabelField {

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return 1;
        }
      }

    }

    @Order(200)
    @ClassId("68892606-268e-4a13-a58c-f00ae6af47c4")
    public class PropertiesBox extends AbstractGroupBox {

      @Override
      protected String getConfiguredLabel() {
        return "Properties";
      }

      @Override
      protected boolean getConfiguredExpandable() {
        return true;
      }

      @Order(20)
      @ClassId("0c0f6c44-c7b5-483c-ad96-7026d3e30c24")
      public class ExclusiveExpandField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Exclusive Expand";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setExclusiveExpand(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isExclusiveExpand());
        }
      }

      @Order(25)
      @ClassId("e63c284c-1bc6-4e73-bd69-4c3bc8f321d5")
      public class SelectableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Selectable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setSelectable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isSelectable());
        }
      }

      @Order(27)
      @ClassId("b4148e8c-a081-4807-beb7-bd0fc4264809")
      public class MultiSelectField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Multi Select";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setMultiSelect(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isMultiSelect());
        }
      }

      @Order(38)
      @ClassId("78fc1ed3-b3b4-4834-96fc-5a0241e32ca3")
      public class ScrollableField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Scrollable";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setScrollable(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isScrollable());
        }
      }

      @Order(39)
      @ClassId("7fcfd7fe-de4a-4ea6-bd99-c7aa7ba85712")
      public class VirtualField extends AbstractBooleanField {
        @Override
        protected String getConfiguredLabel() {
          return "Virtual";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected String getConfiguredTooltipText() {
          return TEXTS.get("TileGridVirtualTooltip");
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setVirtual(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().isVirtual());
        }
      }

      @Order(39.5)
      @ClassId("454f34f1-fd29-4191-a3f2-21b15801ca69")
      public class GroupsCollapsibleField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return "Groups collapsible";
        }

        @Override
        protected boolean getConfiguredLabelVisible() {
          return false;
        }

        @Override
        protected void execChangedValue() {
          boolean collapsible = isChecked();
          getAccordionField().getAccordion().getGroups().forEach(group -> group.setCollapsible(collapsible));
        }

        @Override
        protected void execInitField() {
          if (!getAccordionField().getAccordion().getGroups().isEmpty()) {
            setValue(getAccordionField().getAccordion().getGroups().get(0).isCollapsible());
          }
        }
      }

      @Order(40)
      @ClassId("eb20300a-a46d-4449-8613-75b6991b648f")
      public class GridColumnCountField extends AbstractIntegerField {

        @Override
        protected String getConfiguredLabel() {
          return "Grid Column Count";
        }

        @Override
        protected int getConfiguredHorizontalAlignment() {
          return -1;
        }

        @Override
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null) {
            throw new VetoException("Value must not be null");
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().setGridColumnCount(getValue());
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().getGridColumnCount());
        }
      }

      @Order(50)
      @ClassId("9dd5d66b-8626-42c2-8c45-e75f1ae8e81f")
      public class MaxContentWidthField extends AbstractIntegerField {
        @Override
        protected String getConfiguredLabel() {
          return "Max Content Width";
        }

        @Override
        protected Integer execValidateValue(Integer rawValue) {
          if (rawValue == null) {
            throw new VetoException("Value must not be null");
          }
          return super.execValidateValue(rawValue);
        }

        @Override
        protected void execChangedValue() {
          TileGridLayoutConfig layoutConfig = getAccordionField().getAccordion().getTileGridLayoutConfig().copy()
              .withMaxWidth(getValue());
          getAccordionField().getAccordion().setTileGridLayoutConfig(layoutConfig);
        }

        @Override
        protected void execInitField() {
          setValue(getAccordionField().getAccordion().getTileGridLayoutConfig().getMaxWidth());
        }
      }

      @Order(60)
      @ClassId("d3db8fd8-76c5-4e1c-a308-1db55c4f98fe")
      public class GroupIconIdField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("GroupIcon");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return (Class<? extends ILookupCall<String>>) IconIdLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          ITileAccordionGroupManager<ICustomTile> groupManager = getAccordionField().getAccordion().getGroupManager();
          if (groupManager instanceof CustomTileGroupManager) {
            ((CustomTileGroupManager) groupManager).setIconId(getValue());
          }

          getAccordionField().getAccordion().getGroups().forEach(group -> group.setIconId(getValue()));
        }
      }

      @Order(70)
      @ClassId("168fe646-590d-44cf-b53a-e4d0dcd0dac5")
      public class CollapseStyleField extends AbstractSmartField<String> {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CollapseStyle");
        }

        @Override
        protected Class<? extends ILookupCall<String>> getConfiguredLookupCall() {
          return CollapseStyleLookupCall.class;
        }

        @Override
        protected void execChangedValue() {
          getAccordionField().getAccordion().getGroups().forEach(group -> group.setCollapseStyle(getValue()));
        }
      }

      @Order(2000)
      @ClassId("a70f2b6f-3f33-4f26-b0ae-2cfb2599beb3")
      public class CustomerHeaderWidgetField extends AbstractBooleanField {

        @Override
        protected String getConfiguredLabel() {
          return TEXTS.get("CustomHeaderWidget");
        }

        @Override
        public String getTooltipText() {
          return TEXTS.get("CustomHeaderWidgetTooltip");
        }

        @Override
        protected void execChangedValue() {
          IWidget header = getValue() ? prepareHeaderWidget() : null;
          getAccordionField().getAccordion().getGroups().forEach(group -> replaceHeaderWidget(group, header));
        }

        protected IWidget prepareHeaderWidget() {
          return new AbstractStringField() {
            @Override
            protected String getConfiguredLabel() {
              return "For example a string field";
            }

            @Override
            protected String getConfiguredLabelFont() {
              return "BOLD";
            }
          };
        }

        protected void replaceHeaderWidget(IGroup group, IWidget header) {
          if (group.getHeader() != null) {
            group.getHeader().dispose();
          }
          group.setHeader(header);
          group.setHeaderFocusable(header != null);
        }
      }
    }

    @Order(300)
    @ClassId("a2286e1b-0a57-48ec-8720-558f5a8b9583")
    public class FormFieldPropertiesBox extends AbstractFormFieldPropertiesBox {

      @Override
      protected void execInitField() {
        setFormField(getAccordionField());
      }

      @Override
      protected boolean getConfiguredExpanded() {
        return false;
      }
    }

    @Order(500)
    @ClassId("1c698f40-6c87-463d-be96-e74d5e0dfccc")
    public class CloseButton extends AbstractCloseButton {
    }
  }

  @Override
  protected void execInitForm() {
    int tileCount = 0;
    List<ICustomTile> tiles = new ArrayList<>();
    for (int i = 0; i < 10; i++) {
      SimpleTile tile = new SimpleTile();
      tile.setLabel("Tile " + tileCount++);
      tile.setGroup("Group A");
      tile.setColorScheme(TileColorScheme.DEFAULT);
      tiles.add(tile);
    }
    for (int i = 0; i < 10; i++) {
      SimpleTile tile = new SimpleTile();
      tile.setLabel("Tile " + tileCount++);
      tile.setGroup("Group B");
      tile.setColorScheme(TileColorScheme.ALTERNATIVE);
      tiles.add(tile);
    }
    getAccordionField().getAccordion().addTiles(tiles);
  }

  protected void group() {
    getAccordionField().getAccordion().activateGroupManager(CustomTileGroupManager.ID);
  }

  protected void ungroup() {
    getAccordionField().getAccordion().activateGroupManager(DefaultGroupManager.ID);
  }

  protected void sortTiles(boolean asc) {
    Accordion accordion = getAccordionField().getAccordion();
    accordion.setTileComparator(new Comparator<ICustomTile>() {
      @Override
      public int compare(ICustomTile tile1, ICustomTile tile2) {
        int result = StringUtility.ALPHANUMERIC_COMPARATOR.compare((tile1).getLabel(), (tile2).getLabel());
        if (!asc) {
          result = -result;
        }
        return result;
      }
    });
  }

  protected void filterTilesByText(String text) {
    if (!StringUtility.isNullOrEmpty(text)) {
      if (m_tileFilter == null) {
        m_tileFilter = new CustomTileFilter();
        getAccordionField().getAccordion().addTileFilter(m_tileFilter);
      }
      m_tileFilter.setText(text);
    }
    else {
      getAccordionField().getAccordion().removeTileFilter(m_tileFilter);
      m_tileFilter = null;
    }
    getAccordionField().getAccordion().filterTiles();
  }

  protected void updateStatus() {
    Accordion tileAccordion = getAccordionField().getAccordion();
    getStatusField().setValue(TEXTS.get("TilesStatus", tileAccordion.getTileCount() + "", tileAccordion.getFilteredTileCount() + "", tileAccordion.getSelectedTileCount() + ""));
  }

}
