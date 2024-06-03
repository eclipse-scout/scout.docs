/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {OutlineModel, PageWithNodes} from '@eclipse-scout/core';
import {
  AccordionForm, BreadcrumbBarFieldForm, BrowserFieldForm, ButtonForm, CarouselForm, ChartFieldForm, CheckBoxFieldForm, DateFieldForm, DesktopForm, DesktopNotificationForm, DynamicPageWithNodes, EditableTableForm, FileChooserButtonForm,
  FileChooserFieldForm, FileChooserForm, FormForm, GroupBoxForm, HierarchicalTableForm, ImageFieldForm, ImageForm, IntegerFieldForm, LabelFieldForm, LabelForm, ListBoxForm, LogicalGridForm, MenuBarForm, MessageBoxForm, ModeSelectorForm,
  MultilineSmartFieldForm, MultilineStringFieldForm, NumberFieldForm, PopupForm, ProposalFieldForm, RadioButtonGroupForm, ReloadablePageWithNodes, RestForm, SamplePageWithNodes, SamplePageWithTable, SequenceBoxForm, SmartFieldForm,
  StringFieldForm, SwitchForm, TabBoxForm, TableForm, TableSmartFieldForm, TagFieldForm, TileAccordionForm, TileGridForm, TooltipForm, TreeBoxForm, TreeForm, TreeSmartFieldForm, UiNotificationForm, VirtualTileGridForm, WatchFieldForm,
  WidgetsOutlineOverview, WrappedFormFieldForm
} from '../index';

export default (): OutlineModel => ({
  id: 'jswidgets.WidgetsOutline',
  uuid: 'ff0876f4-e8c9-40b7-9374-39469ad34244',
  title: '${textKey:Widgets}',
  outlineOverview: {
    objectType: WidgetsOutlineOverview
  },
  nodes: [
    {
      uuid: 'e06c3603-3991-40d8-9816-ecdd67957606',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Accordion',
      detailForm: {
        objectType: AccordionForm
      }
    },
    {
      uuid: '5623270d-457b-45a4-b1ea-3466a11f8abe',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Breadcrumb Bar Field',
      detailForm: {
        objectType: BreadcrumbBarFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Button',
      detailForm: {
        objectType: ButtonForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Browser Field',
      detailForm: {
        objectType: BrowserFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Carousel',
      detailForm: {
        objectType: CarouselForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Chart Field',
      detailForm: {
        objectType: ChartFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Check Box Field',
      detailForm: {
        objectType: CheckBoxFieldForm
      }
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
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Date Field',
      detailForm: {
        objectType: DateFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop',
      detailForm: {
        objectType: DesktopForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop Notification',
      detailForm: {
        objectType: DesktopNotificationForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Form',
      detailForm: {
        objectType: FormForm,
        detailForm: true // mark manually to hide CurrentFormPropertiesTab in constructor
      }
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
          }
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'File Chooser',
          detailForm: {
            objectType: FileChooserForm
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Group Box',
      detailForm: {
        objectType: GroupBoxForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image',
      detailForm: {
        objectType: ImageForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image Field',
      detailForm: {
        objectType: ImageFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label',
      detailForm: {
        objectType: LabelForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label Field',
      detailForm: {
        objectType: LabelFieldForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'List Box',
      detailForm: {
        objectType: ListBoxForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Logical Grid',
      detailForm: {
        objectType: LogicalGridForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Menu Bar',
      detailForm: {
        objectType: MenuBarForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'MessageBox',
      detailForm: {
        objectType: MessageBoxForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Mode Selector',
      detailForm: {
        objectType: ModeSelectorForm
      }
    },
    {
      uuid: '19a1aeeb-ee96-4a63-b119-5cab5debccfd',
      objectType: PageWithNodes,
      text: 'Number Field',
      detailForm: {
        objectType: NumberFieldForm
      },
      expanded: true,
      childNodes: [
        {
          uuid: '124e9564-7aee-4509-a7a7-2fa0fd28f319',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Integer Field',
          detailForm: {
            objectType: IntegerFieldForm
          }
        }
      ]
    },
    {
      uuid: '9900cc65-13ae-408d-bd48-89dd900d844f',
      objectType: PageWithNodes,
      text: 'Pages',
      expanded: true,
      routeRef: 'pages',
      childNodes: [
        {
          objectType: SamplePageWithTable,
          routeRef: 'sample-page-with-table'
        },
        {
          objectType: SamplePageWithNodes,
          routeRef: 'sample-page-with-nodes'
        },
        {
          objectType: DynamicPageWithNodes,
          routeRef: 'dynamic-page',
          text: 'Dynamic Page'
        },
        {
          objectType: ReloadablePageWithNodes,
          routeRef: 'reloadable-page'
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Popup',
      detailForm: {
        objectType: PopupForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Radio Button Group',
      detailForm: {
        objectType: RadioButtonGroupForm
      }
    },
    {
      objectType: PageWithNodes,
      text: 'Rest',
      expanded: true,
      detailForm: {
        objectType: RestForm
      },
      childNodes: [
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Ui Notification',
          detailForm: {
            objectType: UiNotificationForm
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Sequence Box',
      detailForm: {
        objectType: SequenceBoxForm
      }
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
          }
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Multiline Smart Field',
          detailForm: {
            objectType: MultilineSmartFieldForm
          }
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Table Smart Field',
          detailForm: {
            objectType: TableSmartFieldForm
          }
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Tree Smart Field',
          detailForm: {
            objectType: TreeSmartFieldForm
          }
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
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      text: 'Switch',
      leaf: true,
      detailForm: {
        objectType: SwitchForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tab Box',
      detailForm: {
        objectType: TabBoxForm
      }
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
          }
        },
        {
          objectType: PageWithNodes,
          leaf: true,
          text: 'Hierarchical Table',
          detailForm: {
            objectType: HierarchicalTableForm
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tag Field',
      detailForm: {
        objectType: TagFieldForm
      }
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
          }
        }
      ]
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tile Accordion',
      detailForm: {
        objectType: TileAccordionForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tooltip',
      detailForm: {
        objectType: TooltipForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree',
      detailForm: {
        objectType: TreeForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree Box',
      detailForm: {
        objectType: TreeBoxForm
      }
    },
    {
      objectType: PageWithNodes,
      leaf: true,
      text: 'Wrapped Form Field',
      detailForm: {
        objectType: WrappedFormFieldForm
      }
    }
  ]
});
