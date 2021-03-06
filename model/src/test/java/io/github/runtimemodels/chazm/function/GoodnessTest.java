package io.github.runtimemodels.chazm.function;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import io.github.runtimemodels.chazm.Organization;
import io.github.runtimemodels.chazm.OrganizationModule;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Agent.ContactInfo;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Attribute.Type;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.EntityFactory;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.InstanceGoal.Parameter;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.chazm.relation.Possesses;
import io.github.runtimemodels.message.E;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.HashSet;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

@SuppressWarnings({"javadoc", "serial"})
public class GoodnessTest {

    private final Injector injector = Guice.createInjector(new OrganizationModule(), new FunctionModule());
    private final Provider<Organization> provider = injector.getProvider(Organization.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);
    private final EntityFactory entityFactory = injector.getInstance(EntityFactory.class);
    private final Goodness goodness = injector.getInstance(Goodness.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testCompute() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new Parameter() {});
        final Capability c1 = entityFactory.buildCapability(idFactory.build(Capability.class, "c1"));
        final Capability c2 = entityFactory.buildCapability(idFactory.build(Capability.class, "c2"));
        final Attribute t = entityFactory.buildAttribute(idFactory.build(Attribute.class, "t"), Type.NEGATIVE_QUALITY);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

        o.addAgent(a);
        o.addRole(r);
        o.addSpecificationGoal(sg);
        o.addInstanceGoal(ig);
        o.addCapability(c1);
        o.addAchieves(r.getId(), sg.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

        o.addRequires(r.getId(), c1.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

        o.addPossesses(a.getId(), c1.getId(), Possesses.MIN_SCORE);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));

        o.setPossessesScore(a.getId(), c1.getId(), Possesses.MAX_SCORE);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

        o.addCapability(c2);
        o.addRequires(r.getId(), c2.getId());
        o.addPossesses(a.getId(), c2.getId(), Possesses.MAX_SCORE);

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MAX_SCORE)));

        o.addAttribute(t);
        o.addNeeds(r.getId(), t.getId());

        assertThat(goodness.compute(o, a, r, ig, new HashSet<>()), is(equalTo(Goodness.MIN_SCORE)));
    }

    @Test
    public void testCompute1() {
        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "compute")));

        goodness.compute(null, null, null, null, null);
    }

    @Test
    public void testCompute2() {
        final Organization o = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg1", "compute")));

        goodness.compute(o, null, null, null, null);
    }

    @Test
    public void testCompute3() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg2", "compute")));

        goodness.compute(o, a, null, null, null);
    }

    @Test
    public void testCompute4() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg3", "compute")));

        goodness.compute(o, a, r, null, null);
    }

    @Test
    public void testCompute5() {
        final Organization o = provider.get();
        final Agent a = entityFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Role r = entityFactory.buildRole(idFactory.build(Role.class, "r"));
        final SpecificationGoal sg = entityFactory.buildSpecificationGoal(idFactory.build(SpecificationGoal.class, "sg"));
        final InstanceGoal ig = entityFactory.buildInstanceGoal(idFactory.build(InstanceGoal.class, "ig"), sg, new Parameter() {});

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.PARAMETER_CANNOT_BE_NULL.get("arg4", "compute")));

        goodness.compute(o, a, r, ig, null);
    }

}
