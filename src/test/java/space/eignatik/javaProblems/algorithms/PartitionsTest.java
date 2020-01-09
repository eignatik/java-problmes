package space.eignatik.javaProblems.algorithms;

import static org.testng.Assert.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.testng.annotations.Test;
import space.eignatik.javaProblems.algorithms.Partitions.Set;
import space.eignatik.javaProblems.algorithms.Partitions.Subset;

public class PartitionsTest {

  @Test
  public void createSet_forEmptyList_returnsEmptySet() {
    Set set = Partitions.createSet(List.of());

    assertTrue(set.getSubsets().isEmpty());
  }

  @Test
  public void createSet_forListWithTwoElements_returnsEmptySet() {
    Set set = Partitions.createSet(List.of(1, 2));

    assertTrue(set.getSubsets().isEmpty());
  }

  @Test
  public void createSet_forSimpleList_returnsTwoSubsets() {
    Set set = Partitions.createSet(List.of(1, 2, 3));

    assertEquals(set.getSubsets().size(), 2, "It should consist of two subsets.");
  }

  @Test
  public void createSet_forSimpleList_returnsSubsetsWithSumDifferenceEqualToMinimum() {
    final int expected = 1;

    Set set = Partitions.createSet(List.of(expected, 3, 5));

    int diff = calculateDifference(set.getSubsets().get(0), set.getSubsets().get(1));
    assertEquals(diff, expected);

  }

  private static int calculateDifference(Subset s1, Subset s2) {
    int diff = Math.abs(calculateSum(s1)) - Math.abs(calculateSum(s2));
    return Math.abs(diff);
  }

  private static int calculateSum(Subset s) {
    return s.getSet().stream().reduce(Integer::sum).get();
  }

}