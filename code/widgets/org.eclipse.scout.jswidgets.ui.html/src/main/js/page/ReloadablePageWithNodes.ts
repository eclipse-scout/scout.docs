/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {ChildModelOf, dates, Form, GroupBox, LabelField, numbers, Page, PageModel, PageWithNodes, scout} from '@eclipse-scout/core';
import model from './ReloadablePageWithNodesModel';

export class ReloadablePageWithNodes extends PageWithNodes {

  constructor() {
    super();
    this.reloadable = true;
    this.detailFormVisible = false; // hidden by default
  }

  protected override _jsonModel(): PageModel {
    return model();
  }

  protected override _createChildPages(): JQuery.Promise<Page[]> {
    let pages: Page[] = [];

    let dummyDetailFormModel: ChildModelOf<Form> = {
      objectType: Form,
      rootGroupBox: {
        objectType: GroupBox,
        borderDecoration: GroupBox.BorderDecoration.EMPTY,
        fields: [
          {
            objectType: LabelField,
            labelVisible: false,
            statusVisible: false,
            value: 'Nothing to see here.'
          }
        ]
      }
    };

    let page1 = scout.create(PageWithNodes, {
      parent: this.getOutline(),
      text: 'Created @ ' + dates.format(dates.newDate(), this.session.locale, 'yyyy-MM-dd HH:mm:ss'),
      leaf: true,
      detailForm: dummyDetailFormModel
    });
    pages.push(page1);

    let page2 = scout.create(PageWithNodes, {
      parent: this.getOutline(),
      text: 'Lucky number: ' + (numbers.randomInt(60) + 1), // between 1 and 60
      detailForm: dummyDetailFormModel,
      childNodes: [
        {
          objectType: PageWithNodes,
          text: 'Secret number: ' + (numbers.randomInt(9) + 1), // between 1 and 9
          detailForm: dummyDetailFormModel,
          leaf: true
        }
      ]
    });
    pages.push(page2);

    // Between 3 and 6 additional pages
    for (let i = 0; i < 3 + numbers.randomInt(4); i++) {
      let randomPage = scout.create(PageWithNodes, {
        parent: this.getOutline(),
        text: '#00' + (i + 1) + ' - ' + numbers.randomId(),
        leaf: true,
        detailForm: dummyDetailFormModel
      });
      pages.push(randomPage);
    }

    return $.resolvedPromise(pages);
  }
}
