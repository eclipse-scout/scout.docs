/*
 * Copyright (c) 2022 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 */
import {OutlineModel, PageWithNodes} from '@eclipse-scout/core';
import {
  AccordionForm, BreadcrumbBarFieldForm, BrowserFieldForm, ButtonForm, CarouselForm, ChartFieldForm, CheckBoxFieldForm, DateFieldForm, DesktopForm, DesktopNotificationForm, EditableTableForm, FileChooserButtonForm, FileChooserFieldForm,
  FileChooserForm, FormForm, GroupBoxForm, HierarchicalTableForm, ImageFieldForm, ImageForm, IntegerFieldForm, LabelFieldForm, LabelForm, ListBoxForm, LogicalGridForm, MenuBarForm, MessageBoxForm, ModeSelectorForm, MultilineSmartFieldForm,
  MultilineStringFieldForm, NumberFieldForm, PopupForm, ProposalFieldForm, RadioButtonGroupForm, RestForm, SamplePageWithNodes, SamplePageWithTable, SequenceBoxForm, SmartFieldForm, StringFieldForm, TabBoxForm, TableForm,
  TableSmartFieldForm, TagFieldForm, TileAccordionForm, TileGridForm, TooltipForm, TreeBoxForm, TreeForm, TreeSmartFieldForm, VirtualTileGridForm, WatchFieldForm, WidgetsOutlineOverview, WrappedFormFieldForm
} from '../index';

export default (): OutlineModel => ({
  id: 'jswidgets.WidgetsOutline',
  title: '${textKey:Widgets}',
  outlineOverview: {
    objectType: WidgetsOutlineOverview
  },
  nodes: [
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Accordion',
      detailForm: {
        objectType: AccordionForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Breadcrumb Bar Field',
      detailForm: {
        objectType: BreadcrumbBarFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Button',
      detailForm: {
        objectType: ButtonForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Browser Field',
      detailForm: {
        objectType: BrowserFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Carousel',
      detailForm: {
        objectType: CarouselForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Chart Field',
      detailForm: {
        objectType: ChartFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Check Box Field',
      detailForm: {
        objectType: CheckBoxFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      expanded: true,
      text: 'Custom Fields',
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Watch Field',
          detailForm: {
            objectType: WatchFieldForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Date Field',
      detailForm: {
        objectType: DateFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop',
      detailForm: {
        objectType: DesktopForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop Notification',
      detailForm: {
        objectType: DesktopNotificationForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Form',
      detailForm: {
        objectType: FormForm,
        detailForm: true
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      expanded: true,
      text: 'File Chooser Field',
      detailForm: {
        objectType: FileChooserFieldForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'File Chooser Button',
          detailForm: {
            objectType: FileChooserButtonForm
          },
          detailTableVisible: false
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'File Chooser',
          detailForm: {
            objectType: FileChooserForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Group Box',
      detailForm: {
        objectType: GroupBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image',
      detailForm: {
        objectType: ImageForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image Field',
      detailForm: {
        objectType: ImageFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label',
      detailForm: {
        objectType: LabelForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label Field',
      detailForm: {
        objectType: LabelFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'List Box',
      detailForm: {
        objectType: ListBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Logical Grid',
      detailForm: {
        objectType: LogicalGridForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Menu Bar',
      detailForm: {
        objectType: MenuBarForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'MessageBox',
      detailForm: {
        objectType: MessageBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Mode Selector',
      detailForm: {
        objectType: ModeSelectorForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      text: 'Number Field',
      detailForm: {
        objectType: NumberFieldForm
      },
      expanded: true,
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Integer Field',
          detailForm: {
            objectType: IntegerFieldForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      text: 'Pages',
      expanded: true,
      childNodes: [
        {
          objectType: SamplePageWithTable
        },
        {
          objectType: SamplePageWithNodes
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Popup',
      detailForm: {
        objectType: PopupForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Radio Button Group',
      detailForm: {
        objectType: RadioButtonGroupForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Rest',
      detailForm: {
        objectType: RestForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Sequence Box',
      detailForm: {
        objectType: SequenceBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      text: 'Smart Field',
      expanded: true,
      detailForm: {
        objectType: SmartFieldForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Proposal Field',
          detailForm: {
            objectType: ProposalFieldForm
          },
          detailTableVisible: false
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Multiline Smart Field',
          detailForm: {
            objectType: MultilineSmartFieldForm
          },
          detailTableVisible: false
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Table Smart Field',
          detailForm: {
            objectType: TableSmartFieldForm
          },
          detailTableVisible: false
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Tree Smart Field',
          detailForm: {
            objectType: TreeSmartFieldForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      text: 'String Field',
      expanded: true,
      detailForm: {
        objectType: StringFieldForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Multiline String Field',
          detailForm: {
            objectType: MultilineStringFieldForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tab Box',
      detailForm: {
        objectType: TabBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      text: 'Table',
      expanded: true,
      detailForm: {
        objectType: TableForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Editable Table',
          detailForm: {
            objectType: EditableTableForm
          },
          detailTableVisible: false
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Hierarchical Table',
          detailForm: {
            objectType: HierarchicalTableForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tag Field',
      detailForm: {
        objectType: TagFieldForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      text: 'Tile Grid',
      expanded: true,
      detailForm: {
        objectType: TileGridForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Virtual Tile Grid',
          detailForm: {
            objectType: VirtualTileGridForm
          },
          detailTableVisible: false
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tile Accordion',
      detailForm: {
        objectType: TileAccordionForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tooltip',
      detailForm: {
        objectType: TooltipForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree',
      detailForm: {
        objectType: TreeForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree Box',
      detailForm: {
        objectType: TreeBoxForm
      },
      detailTableVisible: false
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Wrapped Form Field',
      detailForm: {
        objectType: WrappedFormFieldForm
      },
      detailTableVisible: false
    }
  ]
});
