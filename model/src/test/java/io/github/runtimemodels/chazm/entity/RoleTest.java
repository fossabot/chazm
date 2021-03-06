package io.github.runtimemodels.chazm.entity;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.id.UniqueId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
public class RoleTest {

    private final Injector injector = Guice.createInjector(new EntityModule());
    private final RoleFactory roleFactory = injector.getInstance(RoleFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRole() {
        final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
        final Role r1 = roleFactory.buildRole(i1);
        final Role r2 = roleFactory.buildRole(i1);

        assertThat(r1, is(not(nullValue())));
        assertThat(r1, is(not(sameInstance(r2))));
    }

    @Test
    public void testRole1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));
        roleFactory.buildRole(null);
    }

    @Test
    public void testGetId() {
        final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
        final UniqueId<Role> i2 = idFactory.build(Role.class, 1L);
        final Role r1 = roleFactory.buildRole(i1);
        final Role r2 = roleFactory.buildRole(i2);

        assertThat(r1.getId(), is(sameInstance(i1)));
        assertThat(r1.getId(), is(not(sameInstance(r2.getId()))));
    }

    @Test
    public void testEqualsObject() {
        final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
        final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
        final Role r1 = roleFactory.buildRole(i1);
        final Role r2 = roleFactory.buildRole(i2);
        final Role r3 = roleFactory.buildRole(i1);

        assertThat(r1, is(not(equalTo(r2))));
        assertThat(r1, is(equalTo(r3)));
        assertThat(r1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
        final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
        final Role r1 = roleFactory.buildRole(i1);
        final Role r2 = roleFactory.buildRole(i2);
        final Role r3 = roleFactory.buildRole(i1);

        assertThat(r1.hashCode(), is(not(equalTo(r2.hashCode()))));
        assertThat(r1.hashCode(), is(equalTo(r3.hashCode())));
    }

    @Test
    public void testToString() {
        final UniqueId<Role> i1 = idFactory.build(Role.class, 1L);
        final UniqueId<Role> i2 = idFactory.build(Role.class, 2L);
        final Role r1 = roleFactory.buildRole(i1);
        final Role r2 = roleFactory.buildRole(i2);
        final Role r3 = roleFactory.buildRole(i1);

        assertThat(r1.toString(), is(not(equalTo(r2.toString()))));
        assertThat(r1.toString(), is(equalTo(r3.toString())));
    }

}
