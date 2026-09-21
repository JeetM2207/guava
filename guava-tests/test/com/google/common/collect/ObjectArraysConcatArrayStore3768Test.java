package com.google.common.collect;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Test;

// https://github.com/google/guava/issues/3768: ObjectArrays.concat(T[], T) infers the
// result's component type from the array argument, so concat(new String[0], (Object) 0)
// creates a String[]-backed array and then throws ArrayStoreException trying to store an
// Integer into it, instead of returning a plain Object[] like the sibling
// concat(T, T[]) overload's Class<T>-based version does correctly.
public class ObjectArraysConcatArrayStore3768Test {
  @Test
  public void testConcatSingleElementDoesNotThrowArrayStoreException() {
    String[] array = new String[0];
    Object[] concatenated = ObjectArrays.concat(array, (Object) 0);
    assertArrayEquals(new Object[] {0}, concatenated);
  }
}
