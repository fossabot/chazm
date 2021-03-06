package io.github.runtimemodels.chazm.event;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.entity.SpecificationGoalFactory;
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
public class SpecificationGoalEventTest {

    private final Injector injector = Guice.createInjector(new RelationModule(), new EventModule());
    private final SpecificationGoalEventFactory gef = injector.getInstance(SpecificationGoalEventFactory.class);
    private final SpecificationGoalFactory gf = injector.getInstance(SpecificationGoalFactory.class);
    private final IdFactory idf = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSpecificationGoalEvent() {
        final SpecificationGoal g = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final SpecificationGoalEvent ge1 = gef.build(EventCategory.ADDED, g);
        final SpecificationGoalEvent ge2 = gef.build(EventCategory.ADDED, g);

        assertThat(ge1, is(not(nullValue())));
        assertThat(ge1, is(not(sameInstance(ge2))));
    }

    @Test
    public void testSpecificationGoalEvent1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        gef.build(null, null);
    }

    @Test
    public void testSpecificationGoalEvent2() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        gef.build(EventCategory.ADDED, null);
    }

    @Test
    public void testGetId() {
        final SpecificationGoal g = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g"));
        final SpecificationGoalEvent ge = gef.build(EventCategory.ADDED, g);

        assertThat(ge.getId(), is(sameInstance(g.getId())));
    }

    @Test
    public void testEquals() {
        final SpecificationGoal g1 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoal g2 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g2"));
        final SpecificationGoal g3 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoalEvent ge1 = gef.build(EventCategory.ADDED, g1);
        final SpecificationGoalEvent ge2 = gef.build(EventCategory.ADDED, g2);
        final SpecificationGoalEvent ge3 = gef.build(EventCategory.ADDED, g3);

        assertThat(ge1, is(not(equalTo(ge2))));
        assertThat(ge1, is(equalTo(ge3)));
        assertThat(ge1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final SpecificationGoal g1 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoal g2 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g2"));
        final SpecificationGoal g3 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoalEvent ge1 = gef.build(EventCategory.ADDED, g1);
        final SpecificationGoalEvent ge2 = gef.build(EventCategory.ADDED, g2);
        final SpecificationGoalEvent ge3 = gef.build(EventCategory.ADDED, g3);

        assertThat(ge1.hashCode(), is(not(equalTo(ge2.hashCode()))));
        assertThat(ge1.hashCode(), is(equalTo(ge3.hashCode())));
    }

    @Test
    public void testToString() {
        final SpecificationGoal g1 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoal g2 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g2"));
        final SpecificationGoal g3 = gf.buildSpecificationGoal(idf.build(SpecificationGoal.class, "g1"));
        final SpecificationGoalEvent ge1 = gef.build(EventCategory.ADDED, g1);
        final SpecificationGoalEvent ge2 = gef.build(EventCategory.ADDED, g2);
        final SpecificationGoalEvent ge3 = gef.build(EventCategory.ADDED, g3);

        assertThat(ge1.toString(), is(not(equalTo(ge2.toString()))));
        assertThat(ge1.toString(), is(equalTo(ge3.toString())));
    }

}
