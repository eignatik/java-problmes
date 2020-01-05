package space.eignatik.javaProblems.algorithms;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableListMultimap.Builder;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class that finds all the permutations in the given string by the given template.
 *
 * <p>Permutations are the combinations of all the characters in a string. E.g. for a string "abc"
 * the permutations are: "abc", "acb", "cba", "bac", "bca", "cab".
 *
 * <p>This solution has a smaller algorithm complexity and memory consumption as it works with char
 * arrays  rather than with strings directly.
 *
 * <p>The approximate algorithm complexity is O(m*n) where m is a size of source string and n is a
 * size of the target string.
 */
public final class FindPermutations {

  /**
   * Returns a list of {@link Permutation} of {@code target} in {@code source}.
   *
   * <p>Boundary cases:
   * <ul>
   *   <li> Returns empty list when either or both source and target are null or empty or contain
   *   only special characters and spaces {@link String#isBlank}.
   *   <li>If inputs are equal, the only one permutation is returned.
   * </ul>
   */
  public static List<Permutation> findPermutations(String source, String target) {
    if (source == null || target == null) {
      return List.of();
    }
    if (source.isBlank() || source.isEmpty() || target.isBlank() || target.isEmpty()) {
      return List.of();
    }
    if (source.equals(target)) {
      return List.of(Permutation.newBuilder()
          .setPermutation(source)
          .setIndexes(ImmutableList.of(0))
          .build());
    }
    return mapPermutations(collectPermutations(source, target));
  }

  private static ImmutableListMultimap<String, Integer> collectPermutations(String source,
      String target) {
    Builder<String, Integer> permutationsBuilder = ImmutableListMultimap
        .builder();
    // Gets char array from target to operate with array instead of String.
    // Then it sorts target chars alphabetically. It is used for comparisons of selected particles
    // of the source array.
    char[] targetChars = target.toCharArray();
    Arrays.sort(targetChars);
    int targetSize = targetChars.length;
    for (int i = 0; i <= source.length() - targetSize; i++) {
      // Takes a char array from a part of a source array with size equal to the target array size.
      // Then it sorts the particle of the source array in order to compare it with the target one.
      char[] selectionChars = new char[targetSize];
      source.getChars(i, i + targetSize, selectionChars, 0);
      Arrays.sort(selectionChars);
      // Sorted arrays should contain the same characters in same order. If so, it uses the taken
      // char array and its start index to collect a permutation.
      // Comparing of two arrays work pretty efficient as code inside is vectorized for arrays with
      // size > 3. Also the method is a marked with HotSpotIntrinsicCandidate annotation that may
      // optimize this code even further.
      if (Arrays.equals(selectionChars, targetChars)) {
        permutationsBuilder.put(source.substring(i, i + targetSize), i);
      }
    }
    return permutationsBuilder.build();
  }

  private static List<Permutation> mapPermutations(
      ImmutableListMultimap<String, Integer> permutations) {
    return permutations
        .keySet()
        .stream()
        .map(
            permutation -> Permutation.newBuilder()
                .setPermutation(permutation)
                .setIndexes(permutations.get(permutation))
                .build())
        .collect(Collectors.toList());
  }

  /**
   * Contains a permutation {@link String} with particular permutation and a list of indexes {@link
   * List<Integer>} with all occurrences of this permutation.
   */
  public static class Permutation {

    private String permutation;
    private ImmutableList<Integer> indexes;

    public String getPermutation() {
      return permutation;
    }

    public ImmutableList<Integer> getIndexes() {
      return indexes;
    }

    private static Builder newBuilder() {
      return new Builder();
    }

    /**
     * Builder for {@link Permutation}.
     */
    private static class Builder {

      private String permutation;
      private ImmutableList.Builder<Integer> indexesBuilder;

      private Builder setPermutation(String permutation) {
        this.permutation = permutation;
        return this;
      }

      private Builder setIndexes(ImmutableList<Integer> indexes) {
        this.indexesBuilder = ImmutableList.<Integer>builder().addAll(indexes);
        return this;
      }

      private Builder addIndex(Integer index) {
        this.indexesBuilder.add(index);
        return this;
      }

      private Builder addAllIndexes(Integer... indexes) {
        this.indexesBuilder.addAll(List.of(indexes));
        return this;
      }

      private Permutation build() {
        return new Permutation(permutation, indexesBuilder.build());
      }

      private Builder() {
        indexesBuilder = ImmutableList.builder();
      }
    }

    private Permutation(String permutation, ImmutableList<Integer> indexes) {
      this.permutation = permutation;
      this.indexes = indexes;
    }
  }

  private FindPermutations() {
  }
}
