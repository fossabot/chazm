package io.github.runtimemodels.chazm.validation;

import io.github.runtimemodels.message.E;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static io.github.runtimemodels.chazm.validation.Checks.checkNotNull;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class ChecksTest {

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testChecks() {
        assertThat(new Checks(), is(not(nullValue())));
    }

    @Test
    public void testCheckNotNull() {
        checkNotNull("", "");
    }

    @Test
    public void testCheckNotNull1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("")));

        checkNotNull(null, "");
    }

    @Test
    public void testCheckNotNull2() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("<parameter>")));

        checkNotNull(null, null);
    }

}
