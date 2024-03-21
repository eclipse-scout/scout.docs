/*
 * Copyright (c) 2010, 2024 BSI Business Systems Integration AG
 *
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 */
import {Action, Event, Form, FormModel, InitModelOf} from '@eclipse-scout/core';
import {RandomEmojiFormWidgetMap} from '../index';
import model from './RandomEmojiFormModel';

export class RandomEmojiForm extends Form {
  declare widgetMap: RandomEmojiFormWidgetMap;

  emoji: string;

  constructor() {
    super();
    this.emoji = this._randomEmoji();
  }

  protected override _jsonModel(): FormModel {
    return model();
  }

  protected override _init(model: InitModelOf<this>) {
    super._init(model);
    this._setEmoji(this.emoji);
    this.widget('UpdateMenu').on('action', this._onUpdateMenuAction.bind(this));
  }

  protected _onUpdateMenuAction(event: Event<Action>) {
    this.setEmoji(this._randomEmoji());
  }

  protected _toCodePoint(emoji: string): string {
    return [...emoji].map(e => 'U+' + e.codePointAt(0).toString(16).toUpperCase()).join(' ');
  }

  setEmoji(emoji: string) {
    this.setProperty('emoji', emoji);
  }

  protected _setEmoji(emoji: string) {
    this._setProperty('emoji', emoji);
    this.widget('HtmlField').setValue('<div class="jswidgets-random-emoji-box">' + this.emoji + '</div>' +
      '<div class="jswidgets-random-emoji-info">' + this._toCodePoint(this.emoji) + '</div>');
  }

  protected _randomEmoji(): string {
    let allEmojis = this._allEmojis();
    return allEmojis[Math.floor(Math.random() * allEmojis.length)];
  }

  protected _allEmojis(): string[] {
    return [
      '😀', '😁', '😂', '😃', '😄', '😅', '😆', '😇', '😉', '😋',
      '😍', '😎', '😏', '😐', '😒', '😕', '😖', '😘', '😛', '😜',
      '😠', '😢', '😣', '😤', '😦', '😬', '😭', '😮', '😱', '😲',
      '😴', '😵', '👻', '💩', '💀', '👽', '👾', '🌱', '🌲', '🌳',
      '🌴', '🌵', '🌷', '🌸', '🌻', '🌼', '🌿', '🍀', '🍂', '🍄',
      '🌰', '🐀', '🐁', '🐭', '🐹', '🐂', '🐃', '🐄', '🐮', '🐅',
      '🐆', '🐯', '🐇', '🐰', '🐈', '🐴', '🐏', '🐑', '🐐', '🐓',
      '🐤', '🐦', '🐧', '🐘', '🐪', '🐫', '🐖', '🐷', '🐕', '🐶',
      '🐺', '🐻', '🐨', '🐼', '🐵', '🐒', '🐊', '🐍', '🐢', '🐸',
      '🐳', '🐬', '🐙', '🐟', '🐠', '🐡', '🐌', '🐛', '🐜', '🐝',
      '🐞', '🌈', '🌍', '🌜', '🌞', '🍅', '🍆', '🌽', '🍠', '🍇',
      '🍈', '🍉', '🍊', '🍋', '🍌', '🍍', '🍎', '🍏', '🍐', '🍒',
      '🍓', '🍔', '🍕', '🍜', '🍝', '🍞', '🍟', '🍩', '🍮', '🍷',
      '🍸', '🍹', '🍺', '🎄', '🎈', '❤️', '💙'
    ];
  }
}
