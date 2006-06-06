package net.sf.anathema.character.reporting.sheet.common.magic.generic;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;

import net.disy.commons.core.predicate.IPredicate;
import net.disy.commons.core.util.CollectionUtilities;
import net.sf.anathema.character.generic.character.IGenericCharacter;
import net.sf.anathema.character.generic.impl.magic.CharmUtilities;
import net.sf.anathema.character.generic.impl.rules.ExaltedEdition;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.traits.types.AbilityType;
import net.sf.anathema.character.reporting.sheet.pageformat.IVoidStateFormatConstants;
import net.sf.anathema.character.reporting.sheet.util.AbstractTableEncoder;
import net.sf.anathema.character.reporting.sheet.util.TableCell;
import net.sf.anathema.character.reporting.sheet.util.TableEncodingUtilities;
import net.sf.anathema.character.reporting.util.Bounds;
import net.sf.anathema.lib.resources.IResources;

import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.Image;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;

public class PdfGenericCharmTableEncoder extends AbstractTableEncoder {

  private static final float CELL_WIDTH = TableEncodingUtilities.FONT_SIZE;
  private final IResources resources;
  private final BaseFont baseFont;

  public PdfGenericCharmTableEncoder(IResources resources, BaseFont baseFont) {
    this.resources = resources;
    this.baseFont = baseFont;
  }

  @Override
  protected PdfPTable createTable(PdfContentByte directContent, IGenericCharacter character, Bounds bounds)
      throws DocumentException {
    Font font = TableEncodingUtilities.createFont(baseFont);
    PdfTemplate learnedTemplate = createCharmDotTemplate(directContent, Color.BLACK);
    PdfTemplate notLearnedTemplate = createCharmDotTemplate(directContent, Color.WHITE);
    PdfPTable table = new PdfPTable(createColumnWidths());
    table.setWidthPercentage(100);
    table.addCell(new TableCell(new Phrase(), Rectangle.NO_BORDER));
    for (AbilityType abilityType : AbilityType.getAbilityTypes(ExaltedEdition.SecondEdition)) {
      table.addCell(createHeaderCell(directContent, abilityType));
    }
    for (String genericId : CharmUtilities.SOLAR_EXCELLENCIES) {
      Phrase charmPhrase = new Phrase(resources.getString(genericId), font);
      table.addCell(new TableCell(charmPhrase, Rectangle.NO_BORDER));
      for (AbilityType abilityType : AbilityType.getAbilityTypes(ExaltedEdition.SecondEdition)) {
        table.addCell(createGenericCell(
            directContent,
            character,
            abilityType,
            genericId,
            learnedTemplate,
            notLearnedTemplate));
      }
    }
    return table;
  }

  private PdfTemplate createCharmDotTemplate(PdfContentByte directContent, Color color) {
    float lineWidth = 0.75f;
    float templateSize = IVoidStateFormatConstants.SMALL_SYMBOL_HEIGHT - 1 + 2 * lineWidth;
    PdfTemplate template = directContent.createTemplate(templateSize, templateSize);
    template.setColorFill(color);
    template.setColorStroke(Color.BLACK);
    template.setLineWidth(lineWidth);
    float radius = templateSize / 2 - lineWidth;
    template.circle(templateSize / 2, templateSize / 2, radius);
    template.fillStroke();
    return template;
  }

  private PdfPCell createGenericCell(
      PdfContentByte directContent,
      IGenericCharacter character,
      AbilityType abilityType,
      String genericId,
      PdfTemplate learnedTemplate,
      PdfTemplate notLearnedTemplate) throws DocumentException {
    final String charmId = genericId + "." + abilityType.getId();
    List<IMagic> allLearnedMagic = character.getAllLearnedMagic();
    boolean isLearned = CollectionUtilities.find(allLearnedMagic, new IPredicate<IMagic>() {
      public boolean evaluate(IMagic value) {
        return charmId.equals(value.getId());
      }
    }) != null;
    Image image = Image.getInstance(isLearned ? learnedTemplate : notLearnedTemplate);
    TableCell tableCell = new TableCell(image);
    tableCell.setPadding(0);
    return tableCell;
  }

  private PdfPCell createHeaderCell(PdfContentByte directContent, AbilityType abilityType) throws DocumentException {
    directContent.setColorStroke(Color.BLACK);
    directContent.setColorFill(Color.BLACK);
    String text = resources.getString(abilityType.getId());
    float ascentPoint = baseFont.getAscentPoint(text, TableEncodingUtilities.FONT_SIZE);
    float descentPoint = baseFont.getDescentPoint(text, TableEncodingUtilities.FONT_SIZE);
    float templateWidth = baseFont.getWidthPoint(text, TableEncodingUtilities.FONT_SIZE);
    float templateHeight = ascentPoint - descentPoint;

    PdfTemplate template = directContent.createTemplate(templateWidth, templateHeight);
    template.beginText();
    template.setFontAndSize(baseFont, TableEncodingUtilities.FONT_SIZE);
    template.showTextAligned(Element.ALIGN_LEFT, text, 0, -descentPoint, 0);
    template.endText();
    Image image = Image.getInstance(template);
    image.setRotationDegrees(90);
    TableCell tableCell = new TableCell(image);
    tableCell.setVerticalAlignment(Element.ALIGN_BOTTOM);
    tableCell.setPaddingTop(5);
    return tableCell;
  }

  private float[] createColumnWidths() {
    float[] columnWidths = new float[26];
    Arrays.fill(columnWidths, 1);
    columnWidths[0] = 10;
    return columnWidths;
  }
}