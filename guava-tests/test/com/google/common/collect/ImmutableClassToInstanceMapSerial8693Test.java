package com.google.common.collect;

import static org.junit.Assert.assertEquals;

import java.io.ObjectStreamClass;
import org.junit.Test;

// https://github.com/google/guava/issues/8693:
// ImmutableClassToInstanceMap is missing an explicit serialVersionUID = 0,
// causing serialization incompatibility across Guava releases.
public class ImmutableClassToInstanceMapSerial8693Test {
  @Test
  public void testSerialVersionUIDMatchesMutableVersion() {
    long actualUid = ObjectStreamClass.lookup(ImmutableClassToInstanceMap.class).getSerialVersionUID();
    assertEquals("ImmutableClassToInstanceMap must declare serialVersionUID = 0 to ensure stable serialization", 0L, actualUid);
  }
}
