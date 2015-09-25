package io.github.runtimemodels.message;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class ETest {

	@Test
	public void test() {
		for (final E e : E.values()) {
			assertThat(e.get(), is(nullValue()));
		}
	}
}
