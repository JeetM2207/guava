package com.google.common.net;

import static com.google.common.truth.Truth.assertThat;

import org.junit.Test;

// https://github.com/google/guava/issues/1065: InternetDomainName.from() rejected a
// subdomain label ending in a hyphen (e.g. "vilnius-"), even though that's a valid
// registrable label; the real-world domain in the issue resolves (HTTP 200) despite
// Guava refusing to parse it.
public class InternetDomainNameHyphen1065Test {
  @Test
  public void testTrailingHyphenInSubdomainIsAccepted() {
    InternetDomainName name = InternetDomainName.from("www.vilnius-.hotelreservierung.de");
    assertThat(name.parts()).containsExactly("www", "vilnius-", "hotelreservierung", "de")
        .inOrder();
  }
}
