package net.sf.anathema.test.character.equipment.character.stats.modification;

import net.sf.anathema.character.equipment.MagicalMaterial;
import net.sf.anathema.character.equipment.impl.character.model.stats.modification.WeaponStatsType;

import org.junit.Test;

public abstract class AbstractFirstEditionMoonsilverModificationTest extends AbstractEquipmentModificationTest {

  @Test
  public final void addsTwoToCloseCombatAccuracy() throws Exception {
    assertAccuracyModification(3, 1, WeaponStatsType.Melee);
  }

  @Test
  public final void addsOneToRangedCombatAccuracy() throws Exception {
    assertAccuracyModification(2, 1, WeaponStatsType.Bow);
    assertAccuracyModification(2, 1, WeaponStatsType.Thrown);
    assertAccuracyModification(2, 1, WeaponStatsType.Flame);
  }

  @Test
  public final void defenseUnmodified() throws Exception {
    assertDefenseUnmodified();
  }

  @Test
  public final void speedUnmodified() throws Exception {
    assertSpeedUnmodified();
  }

  @Test
  public final void bowRangeIncreasedBy100() throws Exception {
    assertRangeModification(101, 1, WeaponStatsType.Bow);
    assertRangeModification(1, 1, WeaponStatsType.Thrown);
    assertRangeModification(1, 1, WeaponStatsType.Flame);
  }

  @Test
  public final void rateUnmodified() throws Exception {
    assertRateUnmodified();
  }

  @Test
  public final void damageUnmodified() throws Exception {
    assertDamageUnmodified();
  }

  @Override
  protected final MagicalMaterial getMagicMaterial() {
    return MagicalMaterial.Moonsilver;
  }
}