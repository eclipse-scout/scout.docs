/*******************************************************************************
 * Copyright (c) 2015 BSI Business Systems Integration AG.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Distribution License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/org/documents/edl-v10.html
 *
 * Contributors:
 *     BSI Business Systems Integration AG - initial API and implementation
 ******************************************************************************/
package org.eclipsescout.demo.bahbah.client.ui.forms;

import java.util.Date;

import org.eclipse.scout.rt.client.dto.FormData;
import org.eclipse.scout.rt.client.dto.FormData.SdkCommand;
import org.eclipse.scout.rt.client.ui.action.keystroke.AbstractKeyStroke;
import org.eclipse.scout.rt.client.ui.basic.cell.Cell;
import org.eclipse.scout.rt.client.ui.basic.table.AbstractTable;
import org.eclipse.scout.rt.client.ui.basic.table.ITableRow;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractIntegerColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractStringColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.AbstractTimeColumn;
import org.eclipse.scout.rt.client.ui.basic.table.columns.IColumn;
import org.eclipse.scout.rt.client.ui.form.AbstractForm;
import org.eclipse.scout.rt.client.ui.form.AbstractFormHandler;
import org.eclipse.scout.rt.client.ui.form.fields.groupbox.AbstractGroupBox;
import org.eclipse.scout.rt.client.ui.form.fields.stringfield.AbstractStringField;
import org.eclipse.scout.rt.client.ui.form.fields.tablefield.AbstractTableField;
import org.eclipse.scout.rt.platform.BEANS;
import org.eclipse.scout.rt.platform.Order;
import org.eclipse.scout.rt.platform.util.StringUtility;
import org.eclipse.scout.rt.shared.TEXTS;
import org.eclipse.scout.rt.shared.data.basic.FontSpec;
import org.eclipsescout.demo.bahbah.client.services.BuddyAvatarIconProviderService;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm.MainBox.HistoryField;
import org.eclipsescout.demo.bahbah.client.ui.forms.ChatForm.MainBox.MessageField;
import org.eclipsescout.demo.bahbah.shared.services.process.INotificationProcessService;

@FormData(sdkCommand = SdkCommand.IGNORE)
public class ChatForm extends AbstractForm {

  private String m_buddyName;
  private String m_userName;

  public ChatForm() {
    super();
  }

  @Override
  protected boolean getConfiguredAskIfNeedSave() {
    return false;
  }

  @Override
  protected int getConfiguredDisplayHint() {
    return DISPLAY_HINT_VIEW;
  }

  @FormData
  public String getBuddyName() {
    return m_buddyName;
  }

  @FormData
  public void setBuddyName(String buddyName) {
    m_buddyName = buddyName;
  }

  @FormData
  public String getUserName() {
    return m_userName;
  }

  @FormData
  public void setUserName(String userName) {
    m_userName = userName;
  }

  public void startNew() {
    startInternal(new NewHandler());
  }

  public HistoryField getHistoryField() {
    return getFieldByClass(HistoryField.class);
  }

  public MainBox getMainBox() {
    return getFieldByClass(MainBox.class);
  }

  public MessageField getMessageField() {
    return getFieldByClass(MessageField.class);
  }

  @Order(10)
  public class MainBox extends AbstractGroupBox {

    @Override
    protected int getConfiguredGridColumnCount() {
      return 1;
    }

    @Override
    protected boolean getConfiguredLabelVisible() {
      return false;
    }

    @Order(10)
    public class MessageField extends AbstractStringField {

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }
    }

    @Order(20)
    public class HistoryField extends AbstractTableField<HistoryField.Table> {

      private final Integer MESSAGE_TYPE_LOCAL = 1;
      private final Integer MESSAGE_TYPE_REMOTE = 2;

      public void addMessage(boolean local, String sender, String receiver, Date date, String message) {
        getTable().addRowByArray(new Object[]{(local ? MESSAGE_TYPE_LOCAL : MESSAGE_TYPE_REMOTE), sender, receiver, message, date});
      }

      @Override
      protected boolean getConfiguredLabelVisible() {
        return false;
      }

      public class Table extends AbstractTable {

        @Override
        protected boolean getConfiguredMultilineText() {
          return true;
        }

        @Override
        protected void execDecorateCell(Cell view, ITableRow row, IColumn<?> col) {
          // text color
          row.setForegroundColor("000000");

          // set icon id of the sender of the message (user icons have a specific prefix)
          row.setIconId(BuddyAvatarIconProviderService.BUDDY_ICON_PREFIX + getSenderColumn().getValue(row));

          // set font style and background color depending whether the message is from myself or not
          boolean isMessageFromMe = MESSAGE_TYPE_LOCAL.equals(getTypeColumn().getValue(row));
          if (!isMessageFromMe) {
            view.setFont(FontSpec.parse("BOLD"));
            row.setBackgroundColor("ddebf4");
          }
        }

        public TimeColumn getTimeColumn() {
          return getColumnSet().getColumnByClass(TimeColumn.class);
        }

        public MessageColumn getMessageColumn() {
          return getColumnSet().getColumnByClass(MessageColumn.class);
        }

        public ReceiverColumn getReceiverColumn() {
          return getColumnSet().getColumnByClass(ReceiverColumn.class);
        }

        public SenderColumn getSenderColumn() {
          return getColumnSet().getColumnByClass(SenderColumn.class);
        }

        public TypeColumn getTypeColumn() {
          return getColumnSet().getColumnByClass(TypeColumn.class);
        }

        @Order(10)
        public class TypeColumn extends AbstractIntegerColumn {

          @Override
          protected boolean getConfiguredDisplayable() {
            return false;
          }

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Type");
          }

          @Override
          protected boolean getConfiguredVisible() {
            return false;
          }
        }

        @Order(20)
        public class SenderColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Sender");
          }

          @Override
          protected int getConfiguredWidth() {
            return 110;
          }
        }

        @Order(30)
        public class ReceiverColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Receiver");
          }
        }

        @Order(40)
        public class MessageColumn extends AbstractStringColumn {

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Message");
          }

          @Override
          protected int getConfiguredWidth() {
            return 500;
          }

          @Override
          protected boolean getConfiguredTextWrap() {
            return true;
          }
        }

        @Order(50)
        public class TimeColumn extends AbstractTimeColumn {

          @Override
          protected String getConfiguredFormat() {
            return "HH:mm:ss";
          }

          @Override
          protected String getConfiguredHeaderText() {
            return TEXTS.get("Time");
          }

          @Override
          protected boolean getConfiguredSortAscending() {
            return false;
          }

          @Override
          protected int getConfiguredSortIndex() {
            return 0;
          }
        }
      }
    }

    @Order(10)
    public class SendMessageKeyStroke extends AbstractKeyStroke {

      @Override
      protected String getConfiguredKeyStroke() {
        return "enter";
      }

      @Override
      protected void execAction() {
        String message = getMessageField().getValue();

        if (!StringUtility.isNullOrEmpty(message)) {
          // send message to server
          BEANS.get(INotificationProcessService.class).sendMessage(getBuddyName(), message);
          // update local chat history

          getHistoryField().addMessage(true, getUserName(), getBuddyName(), new Date(), message);
        }
        getMessageField().setValue(null);
      }
    }
  }

  public class NewHandler extends AbstractFormHandler {
  }
}
