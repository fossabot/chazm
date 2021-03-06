package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.RoleFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.RelationModule;
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
public class RoleEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final RoleEventFactory ref = injector.getInstance(RoleEventFactory.class);
    private final RoleFactory rf = injector.getInstance(RoleFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testRoleEvent() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final RoleEvent re1 = ref.build(EventCategory.ADDED, r);
        final RoleEvent re2 = ref.build(EventCategory.ADDED, r);

        assertThat(re1, is(not(nullValue())));
        assertThat(re1, is(not(sameInstance(re2))));
    }

    @Test
    public void testRoleEvent1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        ref.build(null, null);
    }

    @Test
    public void testRoleEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        ref.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final Role r = rf.buildRole(idf.build(Role.class, "r"));
        final RoleEvent re = ref.build(EventCategory.ADDED, r);

        assertThat(re.getId(), is(sameInstance(r.getId())));
    }

    @Test
    public void testEquals() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RoleEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RoleEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1, is(not(equalTo(re2))));
        assertThat(re1, is(equalTo(re3)));
        assertThat(re1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RoleEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RoleEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1.hashCode(), is(not(equalTo(re2.hashCode()))));
        assertThat(re1.hashCode(), is(equalTo(re3.hashCode())));
    }

    @Test
    public void testToString() {
        final Role r1 = rf.buildRole(idf.build(Role.class, "r1"));
        final Role r2 = rf.buildRole(idf.build(Role.class, "r2"));
        final Role r3 = rf.buildRole(idf.build(Role.class, "r1"));
        final RoleEvent re1 = ref.build(EventCategory.ADDED, r1);
        final RoleEvent re2 = ref.build(EventCategory.ADDED, r2);
        final RoleEvent re3 = ref.build(EventCategory.ADDED, r3);

        assertThat(re1.toString(), is(not(equalTo(re2.toString()))));
        assertThat(re1.toString(), is(equalTo(re3.toString())));
    }

}
