package net.sf.anathema.charmentry.properties;

import javax.swing.Icon;
import javax.swing.ListCellRenderer;

import net.disy.commons.core.message.IBasicMessage;

public interface IKeywordEntryPageProperties {

  public IBasicMessage getDefaultMessage();

  public String getPageTitle();

  public Icon getAddIcon();

  public String getKeywordLabel();

  public Icon getRemoveIcon();

  public ListCellRenderer getKeywordRenderer();
}