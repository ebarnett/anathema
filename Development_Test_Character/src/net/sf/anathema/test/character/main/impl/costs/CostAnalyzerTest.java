package net.sf.anathema.test.character.main.impl.costs;

import net.sf.anathema.character.generic.IBasicCharacterData;
import net.sf.anathema.character.generic.magic.IMagic;
import net.sf.anathema.character.generic.magic.charms.MartialArtsLevel;
import net.sf.anathema.character.generic.template.magic.FavoringTraitType;
import net.sf.anathema.character.impl.model.advance.CostAnalyzer;
import net.sf.anathema.dummy.character.DummyBasicCharacterData;
import net.sf.anathema.dummy.character.magic.DummyCharm;
import net.sf.anathema.dummy.character.trait.DummyCoreTraitConfiguration;
import net.sf.anathema.lib.testing.BasicTestCase;
import net.sf.anathema.lib.util.IIdentificate;

import org.easymock.EasyMock;

public class CostAnalyzerTest extends BasicTestCase {

  private static final String CHARM_ID = "charmId"; //$NON-NLS-1$
  private DummyCoreTraitConfiguration dummyCoreTraitConfiguration;
  private DummyBasicCharacterData basicCharacterData;
  private CostAnalyzer costAnalyzer;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
    this.dummyCoreTraitConfiguration = new DummyCoreTraitConfiguration();
    this.basicCharacterData = new DummyBasicCharacterData();
    this.costAnalyzer = new CostAnalyzer(basicCharacterData, dummyCoreTraitConfiguration, FavoringTraitType.AbilityType);
  }

  public void testIsFavoredMagicDelegatesToMagic() throws Exception {
    IMagic magic = EasyMock.createStrictMock(IMagic.class);
    IBasicCharacterData trueCharacterData = new DummyBasicCharacterData();
    EasyMock.expect(magic.isFavored(trueCharacterData, dummyCoreTraitConfiguration, FavoringTraitType.AbilityType))
        .andReturn(true);
    IBasicCharacterData falseCharacterData = new DummyBasicCharacterData();
    EasyMock.expect(magic.isFavored(falseCharacterData, dummyCoreTraitConfiguration, FavoringTraitType.AbilityType))
        .andReturn(false);
    EasyMock.replay(magic);
    assertTrue(new CostAnalyzer(trueCharacterData, dummyCoreTraitConfiguration, FavoringTraitType.AbilityType).isMagicFavored(magic));
    assertFalse(new CostAnalyzer(falseCharacterData, dummyCoreTraitConfiguration, FavoringTraitType.AbilityType).isMagicFavored(magic));
    EasyMock.verify(magic);
  }

  public void testGetMartialArtsLevelFromMartialArtsCharm() throws Exception {
    assertEquals(MartialArtsLevel.Terrestrial, costAnalyzer.getMartialArtsLevel(new DummyCharm(CHARM_ID) {
      @Override
      public boolean hasAttribute(IIdentificate attribute) {
        return attribute.getId().equals("MartialArts") || attribute.getId().equals("Terrestrial"); //$NON-NLS-1$ //$NON-NLS-2$
      }
    }));
  }

  public void testGetMartialArtsLevelFromNonMartialArtsCharm() throws Exception {
    assertNull(costAnalyzer.getMartialArtsLevel(new DummyCharm(CHARM_ID)));
  }
}