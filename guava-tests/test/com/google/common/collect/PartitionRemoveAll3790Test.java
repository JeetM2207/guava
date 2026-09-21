package com.google.common.collect;

import static com.google.common.truth.Truth.assertThat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

// https://github.com/google/guava/issues/3790: iterating Lists.partition(list, n)'s
// sublists and calling removeAll() on each throws NoSuchElementException, because
// emptying a partition sublist removes it from the parent partitioned view, which
// invalidates the outer iteration Lists.partition() is currently in the middle of.
public class PartitionRemoveAll3790Test {
  @Test
  public void testRemoveAllOnPartitionedSublistsDoesNotThrow() {
    List<Integer> source = new ArrayList<>(Arrays.asList(1, 2, 3, 4));
    List<List<Integer>> partitions = Lists.partition(source, 2);
    for (List<Integer> partition : partitions) {
      partition.removeAll(Arrays.asList(1, 2, 3, 4));
    }
    assertThat(source).isEmpty();
  }
}
