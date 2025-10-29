/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
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
  MultilineSmartFieldForm, MultilineStringFieldForm, NumberFieldForm, PopupForm, ProposalFieldForm, RadioButtonGroupForm, ReloadablePageWithNodes, RestForm, SamplePageWithNodes, SamplePageWithTable, SequenceBoxForm, SimpleTabBoxForm,
  SliderFieldForm, SmartFieldForm, StringFieldForm, SwitchForm, TabBoxForm, TableForm, TableSmartFieldForm, TagFieldForm, TileAccordionForm, TileGridForm, TooltipForm, TreeBoxForm, TreeForm, TreeSmartFieldForm, UiNotificationForm,
  VirtualTileGridForm, WatchFieldForm, WidgetsOutlineOverview, WrappedFormFieldForm
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
      uuid: 'b307f074-2d79-48ef-975b-54c68c0eaa00',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Button',
      detailForm: {
        objectType: ButtonForm
      }
    },
    {
      uuid: '6ed2636c-f142-46d6-a44d-4d1ea44cb14f',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Browser Field',
      detailForm: {
        objectType: BrowserFieldForm
      }
    },
    {
      uuid: 'bba4f259-4df5-4ab6-8b3e-bb3e678217dd',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Carousel',
      detailForm: {
        objectType: CarouselForm
      }
    },
    {
      uuid: 'd108e62c-e7a6-4f3f-9d62-03dba7d0ca36',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Chart Field',
      detailForm: {
        objectType: ChartFieldForm
      }
    },
    {
      uuid: '6e4eb591-880e-48a0-9ae1-5b66ce218f1a',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Check Box Field',
      detailForm: {
        objectType: CheckBoxFieldForm
      }
    },
    {
      uuid: '3095abc5-f9d5-4ddc-a28d-8b53cb62d46b',
      objectType: PageWithNodes,
      expanded: true,
      text: 'Custom Fields',
      childNodes: [
        {
          uuid: 'ea49821c-7949-40e0-9b2e-bc3597ee8b1a',
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
      uuid: '92ba4034-4c44-4e63-9fa9-d06f2985b0d4',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Date Field',
      detailForm: {
        objectType: DateFieldForm
      }
    },
    {
      uuid: '2c40da6b-4c3a-47c6-888d-30f4803b8591',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop',
      detailForm: {
        objectType: DesktopForm
      }
    },
    {
      uuid: '2306a4c8-bf4a-4865-8198-8ee42c8df38f',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Desktop Notification',
      detailForm: {
        objectType: DesktopNotificationForm
      }
    },
    {
      uuid: 'c6f9e4bb-7638-489d-a3d5-eecd4f00c410',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Form',
      detailForm: {
        objectType: FormForm,
        detailForm: true // mark manually to hide CurrentFormPropertiesTab in constructor
      }
    },
    {
      uuid: 'df7b96fc-47d2-4443-a018-6ceecad0ed36',
      objectType: PageWithNodes,
      expanded: true,
      text: 'File Chooser Field',
      detailForm: {
        objectType: FileChooserFieldForm
      },
      childNodes: [
        {
          uuid: '54dfd5f3-f857-4690-a097-7584abe46417',
          objectType: PageWithNodes,
          leaf: true,
          text: 'File Chooser Button',
          detailForm: {
            objectType: FileChooserButtonForm
          }
        },
        {
          uuid: '1c1c1a03-789a-4182-8eba-025066ef758e',
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
      uuid: '0a4deac8-623b-4605-8b7a-428a90cd04c7',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Group Box',
      detailForm: {
        objectType: GroupBoxForm
      }
    },
    {
      uuid: 'f4d9f67f-51b4-48f2-8513-9451a58ad038',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image',
      detailForm: {
        objectType: ImageForm
      }
    },
    {
      uuid: 'fb8b2389-e878-408b-b321-d1c881504f69',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Image Field',
      detailForm: {
        objectType: ImageFieldForm
      }
    },
    {
      uuid: '7f5459e3-fe07-40e3-9c9c-b8865d25cd42',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label',
      detailForm: {
        objectType: LabelForm
      }
    },
    {
      uuid: 'ba97f51a-f41f-4370-9d29-19d099005a15',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Label Field',
      detailForm: {
        objectType: LabelFieldForm
      }
    },
    {
      uuid: 'c35dca8d-3d05-43df-b7ef-ace8a091cf01',
      objectType: PageWithNodes,
      leaf: true,
      text: 'List Box',
      detailForm: {
        objectType: ListBoxForm
      }
    },
    {
      uuid: '673e3ed7-50b8-421f-a7ce-c608cc0b6711',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Logical Grid',
      detailForm: {
        objectType: LogicalGridForm
      }
    },
    {
      uuid: '32958aad-037c-4edd-b621-ead484ad5e7f',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Menu Bar',
      detailForm: {
        objectType: MenuBarForm
      }
    },
    {
      uuid: '571a47a6-7b17-4710-a4f8-a4dbce3cd1b4',
      objectType: PageWithNodes,
      leaf: true,
      text: 'MessageBox',
      detailForm: {
        objectType: MessageBoxForm
      }
    },
    {
      uuid: '42eb9414-f4a8-47f2-a6a9-3bd7e56bf320',
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
        },
        {
          uuid: '8ab311a5-d304-4a1f-b7ea-8caa2c742219',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Slider Field',
          detailForm: {
            objectType: SliderFieldForm
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
      uuid: '3a120205-006a-4270-acfc-9cc89e257a07',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Popup',
      detailForm: {
        objectType: PopupForm
      }
    },
    {
      uuid: 'e1bf3207-52f9-41a1-b631-d358c789941e',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Radio Button Group',
      detailForm: {
        objectType: RadioButtonGroupForm
      }
    },
    {
      uuid: 'b6943183-f4a8-4de2-a100-eb64e4f6eced',
      objectType: PageWithNodes,
      text: 'Rest',
      expanded: true,
      detailForm: {
        objectType: RestForm
      },
      childNodes: [
        {
          uuid: '71718021-9c20-41c3-9199-dc977149a8dc',
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
      uuid: '4565f42d-7432-4369-a38c-0fc0b717f3e6',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Sequence Box',
      detailForm: {
        objectType: SequenceBoxForm
      }
    },
    {
      uuid: '36656553-0c2b-498a-8bb0-50c453326eec',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Simple Tab Box',
      detailForm: {
        objectType: SimpleTabBoxForm
      }
    },
    {
      uuid: '62ecb23d-5db7-4331-9449-2025e9d36441',
      objectType: PageWithNodes,
      text: 'Smart Field',
      expanded: true,
      detailForm: {
        objectType: SmartFieldForm
      },
      childNodes: [
        {
          uuid: '80b82bdc-bfca-4d8a-a5e4-37e6d7dbc60a',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Proposal Field',
          detailForm: {
            objectType: ProposalFieldForm
          }
        },
        {
          uuid: '424002a8-c702-4359-8b22-630bc5db10ee',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Multiline Smart Field',
          detailForm: {
            objectType: MultilineSmartFieldForm
          }
        },
        {
          uuid: 'a16914a7-a5a4-49eb-9bbb-4cd77965eb81',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Table Smart Field',
          detailForm: {
            objectType: TableSmartFieldForm
          }
        },
        {
          uuid: '30a8e8f7-c5c6-45aa-9cb6-c5c1d88364ec',
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
      uuid: '11ae43c3-a68d-4541-9e7f-e41ddb6cc5b6',
      objectType: PageWithNodes,
      text: 'String Field',
      expanded: true,
      detailForm: {
        objectType: StringFieldForm
      },
      childNodes: [
        {
          uuid: 'f8d68e94-beaa-4d26-99e3-87cd1b908d2d',
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
      uuid: '4e2d5bd7-75ac-47dc-beb8-260e9f77025f',
      objectType: PageWithNodes,
      text: 'Switch',
      leaf: true,
      detailForm: {
        objectType: SwitchForm
      }
    },
    {
      uuid: '14ba78f0-3000-482d-bed9-356a548b7ccb',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tab Box',
      detailForm: {
        objectType: TabBoxForm
      }
    },
    {
      uuid: '6a0dc54f-945f-46ab-84d5-8d6701f3a9df',
      objectType: PageWithNodes,
      text: 'Table',
      expanded: true,
      detailForm: {
        objectType: TableForm
      },
      childNodes: [
        {
          uuid: '6eea385b-5a31-4c11-aec2-97915c5c73fe',
          objectType: PageWithNodes,
          leaf: true,
          text: 'Editable Table',
          detailForm: {
            objectType: EditableTableForm
          }
        },
        {
          uuid: '6efe1333-5957-48fc-9464-0455e7002051',
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
      uuid: 'a32dfb36-47a1-4e9b-bdc2-af51cabef968',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tag Field',
      detailForm: {
        objectType: TagFieldForm
      }
    },
    {
      uuid: '50ab949d-2d37-4c67-ad4e-41fa362e178b',
      objectType: PageWithNodes,
      text: 'Tile Grid',
      expanded: true,
      detailForm: {
        objectType: TileGridForm
      },
      childNodes: [
        {
          uuid: '4e987015-4743-4c55-be48-cb6bf6b6ef94',
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
      uuid: '1635b64e-9426-47d5-b1e4-57b57475aca6',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tile Accordion',
      detailForm: {
        objectType: TileAccordionForm
      }
    },
    {
      uuid: 'cce2c89f-09e3-4305-9d30-7bfd0d22aee8',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tooltip',
      detailForm: {
        objectType: TooltipForm
      }
    },
    {
      uuid: '7e1303c1-724e-4105-85ff-815059078ef3',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree',
      detailForm: {
        objectType: TreeForm
      }
    },
    {
      uuid: 'eef66227-57c2-45c9-b729-5880d087e493',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Tree Box',
      detailForm: {
        objectType: TreeBoxForm
      }
    },
    {
      uuid: '810a3da1-42b7-4bad-979a-59f51915e9f6',
      objectType: PageWithNodes,
      leaf: true,
      text: 'Wrapped Form Field',
      detailForm: {
        objectType: WrappedFormFieldForm
      }
    }
  ]
});
