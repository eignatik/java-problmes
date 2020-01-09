package space.eignatik.javaProblems.algorithms;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public final class Partitions {

  public static Set createSet(List<Integer> set) {
    // Returns empty set if there are conditions not fulfilled.
    if (set == null || set.isEmpty() || set.size() < 3) {
      return Set.newBuilder().build();
    }

    int min = findMin(set);

    for (Integer value : set) {

    }

    return Set.newBuilder()
        .addAllSubsets(List.of(Subset.newBuilder().build(), Subset.newBuilder().build())).build();
  }

  private static int findMin(List<Integer> set) {
    int min = set.get(0);
    for (Integer value : set) {
      if (value < min) {
        min = value;
      }
    }
    return min;
  }

  public static class Set {

    private List<Subset> subsets;

    public static Builder newBuilder() {
      return new Builder();
    }

    public List<Subset> getSubsets() {
      return subsets;
    }

    public static class Builder {

      private Set set;

      public Builder addSubset(Subset value) {
        set.subsets.add(value);
        return this;
      }

      public Builder addAllSubsets(List<Subset> values) {
        set.subsets.addAll(values);
        return this;
      }

      public Set build() {
        return set;
      }

      private Builder() {
        this.set = new Set();
      }
    }

    private Set() {
      this.subsets = new ArrayList<>();
    }
  }

  public static class Subset {

    private List<Integer> set;

    public static Builder newBuilder() {
      return new Builder();
    }

    public List<Integer> getSet() {
      return this.set;
    }

    public static class Builder {

      private Subset subset;

      public Builder addToSubset(Integer value) {
        subset.set.add(value);
        return this;
      }

      public Builder addAllToSubset(List<Integer> values) {
        subset.set.addAll(values);
        return this;
      }

      public Subset build() {
        return subset;
      }

      private Builder() {
        this.subset = new Subset();
      }
    }

    private Subset() {
      this.set = new ArrayList<>();
    }
  }

}
