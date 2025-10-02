/*
 * Copyright (c) 2010, 2025 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {TooltipForm, util} from '../index';
import {icons, InitModelOf, Menu, Tooltip} from '@eclipse-scout/core';

export class TooltipWithMenusForm extends TooltipForm {
  protected override _createModel($anchor: JQuery): InitModelOf<Tooltip> {
    return {
      ...super._createModel($anchor),
      menus: [
        {
          id: 'RegularMenu',
          objectType: Menu,
          text: 'Menu',
          iconId: icons.WORLD
        },
        {
          id: 'ToggleMenu',
          objectType: Menu,
          text: 'Toggle Menu',
          toggleAction: true,
          iconId: icons.GEAR
        },
        {
          id: 'DisabledMenu',
          objectType: Menu,
          text: 'Disabled Menu',
          enabled: false,
          iconId: icons.GEAR
        },
        {
          id: 'DisabledSelectedMenu',
          objectType: Menu,
          text: 'Disabled Selected Menu',
          toggleAction: true,
          selected: true,
          enabled: false,
          iconId: icons.GEAR
        },
        {
          id: 'DisabledHierarchicalMenu',
          objectType: Menu,
          text: 'Disabled Hierarchical Menu',
          iconId: icons.GEAR,
          enabled: false,
          childActions: [
            {
              id: 'SubSubMenu1',
              objectType: Menu,
              text: 'Sub Sub Menu 1'
            }
          ]
        },
        {
          id: 'HierarchicalMenu',
          objectType: Menu,
          text: 'Hierarchical menu',
          childActions: [
            {
              id: 'SubMenu1',
              objectType: Menu,
              text: 'Sub Menu 1'
            },
            {
              id: 'SubMenu2',
              objectType: Menu,
              text: 'Sub Menu 2'
            }
          ]
        }
      ]
    };
  }

  protected override _onOpenTooltipButtonClick() {
    super._onOpenTooltipButtonClick();
    let menus = this.tooltip.menus;
    menus.forEach(menu => {
      menu.on('action', event => util.showMenuActionMessage(event.source));
      menu.visitChildMenus(menu => {
        menu.on('action', event => util.showMenuActionMessage(event.source));
      });
    });
  }
}
