/**
 *
 */
package org.eclipsescout.demo.minifigcreator.shared.minifig.part;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author jbr
 */
public class PartUtilityTest {
  private static final String NAME1 = "Alice";
  private static final String NAME2 = "Bob";
  private static final String PART_NAME1 = "Part_Name1";
  private static final String PART_NAME2 = "Part_Name2";
  private static final String PART_NAME3 = "Part_Name3";
  private static final int VALUE1 = 23;
  private static final int VALUE2 = 42;
  private static final int VALUE3 = 65;
  private static final int VALUE_SUM = VALUE1 + VALUE2 + VALUE3;

  private static final Part PART1 = new Part(PartType.HEAD, 1, PART_NAME1, VALUE1);
  private static final Part PART2 = new Part(PartType.TORSO, 2, PART_NAME2, VALUE2);
  private static final Part PART3 = new Part(PartType.LEGS, 3, PART_NAME3, VALUE3);
  private static final Part PART4 = new Part(PartType.TORSO, 89, "Part_Name10", VALUE1);

  private static final String EXPECTED_PARTS_AND_VALUE_ALL = " [" + PART_NAME1 + ", " + PART_NAME2 + ", " + PART_NAME3 + "] - value: " + VALUE_SUM;
  private static final String EXPECTED_PARTS_AND_VALUE_1_AND_3 = " [" + PART_NAME1 + ", " + PART_NAME3 + "] - value: " + (VALUE1 + VALUE3);
  private static final String EXPECTED_PARTS_AND_VALUE_1_AND_2 = " [" + PART_NAME1 + ", " + PART_NAME2 + "] - value: " + (VALUE1 + VALUE2);
  private static final String EXPECTED_PARTS_AND_VALUE_2_AND_3 = " [" + PART_NAME2 + ", " + PART_NAME3 + "] - value: " + (VALUE2 + VALUE3);

  /**
   * Test method for {@link PartUtility#calculateSummary(Part, Part, Part)} .
   */
  @Test
  public void testCalculateSummary() {
    Assert.assertEquals("Summary", NAME1 + EXPECTED_PARTS_AND_VALUE_ALL, PartUtility.calculateSummary(NAME1, PART1, PART2, PART3));
    Assert.assertEquals("Summary", NAME2 + EXPECTED_PARTS_AND_VALUE_ALL, PartUtility.calculateSummary(NAME2, PART1, PART2, PART3));
    Assert.assertEquals("Summary", "..." + EXPECTED_PARTS_AND_VALUE_ALL, PartUtility.calculateSummary(null, PART1, PART2, PART3));

    Assert.assertEquals("Summary", NAME1 + EXPECTED_PARTS_AND_VALUE_2_AND_3, PartUtility.calculateSummary(NAME1, null, PART2, PART3));
    Assert.assertEquals("Summary", NAME1 + EXPECTED_PARTS_AND_VALUE_1_AND_3, PartUtility.calculateSummary(NAME1, PART1, null, PART3));
    Assert.assertEquals("Summary", NAME1 + EXPECTED_PARTS_AND_VALUE_1_AND_2, PartUtility.calculateSummary(NAME1, PART1, PART2, null));
    Assert.assertEquals("Summary", NAME2 + EXPECTED_PARTS_AND_VALUE_2_AND_3, PartUtility.calculateSummary(NAME2, null, PART2, PART3));
    Assert.assertEquals("Summary", "..." + EXPECTED_PARTS_AND_VALUE_1_AND_2, PartUtility.calculateSummary(null, PART1, PART2, null));

    Assert.assertEquals("Summary", NAME1 + " - value: 0", PartUtility.calculateSummary(NAME1, null, null, null));
    Assert.assertEquals("Summary", NAME2 + " - value: 0", PartUtility.calculateSummary(NAME2, null, null, null));
    Assert.assertEquals("Summary", "... - value: 0", PartUtility.calculateSummary(null, null, null, null));
  }

  /**
   * Test method for {@link PartUtility#calculateValue(Part, Part, Part)} .
   */
  @Test
  public void testCalculateValue() {
    Assert.assertEquals("Value", VALUE_SUM, PartUtility.calculateValue(PART1, PART2, PART3));
    Assert.assertEquals("Value", VALUE2 + VALUE3, PartUtility.calculateValue(null, PART2, PART3));
    Assert.assertEquals("Value", VALUE1 + VALUE3, PartUtility.calculateValue(PART1, null, PART3));
    Assert.assertEquals("Value", VALUE1 + VALUE2, PartUtility.calculateValue(PART1, PART2, null));
    Assert.assertEquals("Value", 0, PartUtility.calculateValue(null, null, null));
  }

  /**
   * Test method for {@link PartUtility#calculateImageId(Part, Part, Part)} .
   */
  @Test
  public void testCalculateImageId() {
    Assert.assertEquals("ImageId", "Minifig_H01_T02_L03", PartUtility.calculateImageId(PART1, PART2, PART3));
    Assert.assertEquals("ImageId", "Minifig_H01_T89_L03", PartUtility.calculateImageId(PART1, PART4, PART3));
    Assert.assertEquals("ImageId", "Minifig_H00_T02_L03", PartUtility.calculateImageId(null, PART2, PART3));
    Assert.assertEquals("ImageId", "Minifig_H01_T00_L03", PartUtility.calculateImageId(PART1, null, PART3));
    Assert.assertEquals("ImageId", "Minifig_H01_T02_L00", PartUtility.calculateImageId(PART1, PART2, null));
    Assert.assertEquals("ImageId", "Minifig_H00_T00_L00", PartUtility.calculateImageId(null, null, null));
  }

  /**
   * Test method for
   * {@link org.eclipsescout.demo.minifigcreator.shared.minifig.part.PartUtility#calculateSmallIconId(org.eclipsescout.demo.minifigcreator.shared.minifig.part.Part)}
   * .
   */
  @Test
  public void testCalculateSmallIconId() {
    Assert.assertEquals("ImageId", "Part_H01", PartUtility.calculateSmallIconId(PART1));
    Assert.assertEquals("ImageId", "Part_T02", PartUtility.calculateSmallIconId(PART2));
    Assert.assertEquals("ImageId", "Part_L03", PartUtility.calculateSmallIconId(PART3));
    Assert.assertEquals("ImageId", "Part_X00", PartUtility.calculateSmallIconId(null));
  }
}
