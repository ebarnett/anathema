package net.sf.anathema.character.sidereal.reporting.rendering.anima;

import net.sf.anathema.character.reporting.pdf.rendering.boxes.anima.AbstractAnimaEncoderFactory;
import net.sf.anathema.character.reporting.pdf.rendering.boxes.anima.AnimaTableEncoder;
import net.sf.anathema.character.reporting.pdf.rendering.general.table.ITableEncoder;
import net.sf.anathema.lib.resources.IResources;

public class SiderealAnimaEncoderFactory extends AbstractAnimaEncoderFactory {

  public SiderealAnimaEncoderFactory(IResources resources) {
    super(resources);
  }

  @Override
  protected ITableEncoder getAnimaTableEncoder() {
    return new AnimaTableEncoder(getResources(), getFontSize(), new SiderealAnimaTableStealthProvider(getResources()));
  }
}