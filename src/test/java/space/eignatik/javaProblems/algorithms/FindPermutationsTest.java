package space.eignatik.javaProblems.algorithms;

import static org.testng.Assert.*;

import com.google.common.collect.Iterables;
import java.util.List;
import java.util.stream.Collectors;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import space.eignatik.javaProblems.algorithms.FindPermutations.Permutation;

public class FindPermutationsTest {

  @DataProvider(name = "emptySourcesAndTargets")
  public Object[][] getEmptySourceAndTargets() {
    return new Object[][] {
        {"", ""},
        {"", null},
        {null, "",},
        {null, null}
    };
  }

  @Test(dataProvider = "emptySourcesAndTargets")
  public void findPermutations_whenCalledWithEmptyOrNull_returnsEmptyList(String source, String target) {
    assertEquals(FindPermutations.findPermutations(source, target).size(), 0);
  }

  @Test
  public void findPermutations_whenCalledForStringWithTheSamePattern_returnsOnlyPermutation() {
    String expected = "abc";

    Permutation permutation = Iterables
        .getOnlyElement(FindPermutations.findPermutations(expected, expected));

    assertEquals(permutation.getPermutation(), expected);
    int index = Iterables.getOnlyElement(permutation.getIndexes());
    assertEquals(index, 0);
  }

  @DataProvider(name = "targets")
  public Object[][] getTargets() {
    return new Object[][] {
        {"abc"}, {"bca"}, {"bac"}
    };
  }

  @Test(dataProvider = "targets")
  public void findPermutations_whenCalledForString_returnsAllPermutations(String target) {
    List<String> expectedPermutations = List.of("abc", "cba", "bca", "cab", "bac", "acb");
    List<Integer> expectedIndexes = List.of(0, 5, 3, 6, 11, 9, 12, 15);

    List<Permutation> result = FindPermutations
        .findPermutations("abccbabcaacbcabbac", target);

    List<String> permutations = result.stream()
        .map(Permutation::getPermutation)
        .collect(Collectors.toList());
    assertTrue(permutations.containsAll(expectedPermutations),
        "The permutation list should have contained listed permutations.");
    List<Integer> indexes = result.stream().flatMap(p -> p.getIndexes().stream())
        .collect(Collectors.toList());
    assertTrue(indexes.containsAll(expectedIndexes),
        "The indexes list should have contained listed indexes.");
  }

}