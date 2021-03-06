package net.sf.anathema.character.lunar.reporting.content.stats.heartsblood;

import net.sf.anathema.character.reporting.pdf.content.stats.AbstractTextStatsGroup;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPTable;

public class HeartsBloodDexterityStatsGroup extends AbstractTextStatsGroup<IHeartsBloodStats> {

  private final IResources resources;

  public HeartsBloodDexterityStatsGroup(IResources resources) {
    this.resources = resources;
  }

  public void addContent(PdfPTable table, Font font, IHeartsBloodStats stats) {
    String text = stats == null ? null : stats.getDexterityString();
    table.addCell(createTextCell(font, text));
  }

  public Float[] getColumnWeights() {
    return new Float[] { 1.5f };
  }

  public String getTitle() {
    return resources.getString("Sheet.Lunar.HeartsBlood.Dexterity"); //$NON-NLS-1$
  }
}
