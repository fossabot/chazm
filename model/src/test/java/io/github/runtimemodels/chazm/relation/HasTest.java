package io.github.runtimemodels.chazm.relation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.ProvisionException;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Agent.ContactInfo;
import io.github.runtimemodels.chazm.entity.AgentFactory;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Attribute.Type;
import io.github.runtimemodels.chazm.entity.AttributeFactory;
import io.github.runtimemodels.chazm.id.IdFactory;
import io.github.runtimemodels.message.E;
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
public class HasTest {

    private final Injector injector = Guice.createInjector(new RelationModule());
    private final HasFactory hasFactory = injector.getInstance(HasFactory.class);
    private final AgentFactory agentFactory = injector.getInstance(AgentFactory.class);
    private final AttributeFactory attributeFactory = injector.getInstance(AttributeFactory.class);
    private final IdFactory idFactory = injector.getInstance(IdFactory.class);

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testHas() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs1 = hasFactory.buildHas(a, c, 1d);
        final Has hs2 = hasFactory.buildHas(a, c, 1d);

        assertThat(hs1, is(not(nullValue())));
        assertThat(hs1, is(not(sameInstance(hs2))));
    }

    @Test
    public void testHas1() {
        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        hasFactory.buildHas(null, null, 1d);
    }

    @Test
    public void testHas2() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});

        exception.expect(instanceOf(ProvisionException.class));
        exception.expectMessage(allOf(containsString("parameter"), containsString(".<init>()"), containsString("is not @Nullable")));

        hasFactory.buildHas(a, null, 1d);
    }

    @Test
    public void testHas3() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.NEGATIVE_QUALITY, -0.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hasFactory.buildHas(a, c, -0.01);
    }

    @Test
    public void testHas4() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.NEGATIVE_QUALITY, 1.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hasFactory.buildHas(a, c, 1.01);
    }

    @Test
    public void testHas5() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUANTITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_AT_LEAST.get(Type.NEGATIVE_QUANTITY, -0.01, Has.QUALITY_MIN_AMOUNT)));

        hasFactory.buildHas(a, c, -0.01);
    }

    @Test
    public void testHas6() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, Double.NEGATIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    public void testHas7() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, Double.POSITIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void testHas8() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUALITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.POSITIVE_QUALITY, -0.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hasFactory.buildHas(a, c, -0.01);
    }

    @Test
    public void testHas9() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUALITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.POSITIVE_QUALITY, 1.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hasFactory.buildHas(a, c, 1.01);
    }

    @Test
    public void testHas10() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUANTITY);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_AT_LEAST.get(Type.POSITIVE_QUANTITY, -0.01, Has.QUALITY_MIN_AMOUNT)));

        hasFactory.buildHas(a, c, -0.01);
    }

    @Test
    public void testHas11() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, Double.NEGATIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testHas12() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, Double.POSITIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void testGetAgent() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        assertThat(hs.getAgent(), is(sameInstance(a)));
    }

    @Test
    public void testGetAttribute() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        assertThat(hs.getAttribute(), is(sameInstance(c)));
    }

    @Test
    public void testGetValue() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        assertThat(hs.getValue(), is(equalTo(1d)));
    }

    @Test
    public void testSetValue() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);
        hs.setValue(1d);

        assertThat(hs.getValue(), is(equalTo(1d)));
    }

    @Test
    public void testSetValue1() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.NEGATIVE_QUALITY, -0.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hs.setValue(-0.01);
    }

    @Test
    public void testSetValue2() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.NEGATIVE_QUALITY, 1.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hs.setValue(1.01);
    }

    @Test
    public void testSetValue3() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUANTITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_AT_LEAST.get(Type.NEGATIVE_QUANTITY, -0.01, Has.QUALITY_MIN_AMOUNT)));

        hs.setValue(-0.01);
    }

    @Test
    public void testSetValue4() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, 1d);
        hs.setValue(Double.NEGATIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testSetValue5() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, 1d);
        hs.setValue(Double.POSITIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void testSetValue6() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.POSITIVE_QUALITY, -0.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hs.setValue(-0.01);
    }

    @Test
    public void testSetValue7() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUALITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_BETWEEN.get(Type.POSITIVE_QUALITY, 1.01, Has.QUALITY_MIN_AMOUNT, Has.QUALITY_MAX_AMOUNT)));

        hs.setValue(1.01);
    }

    @Test
    public void testSetValue8() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_QUANTITY);
        final Has hs = hasFactory.buildHas(a, c, 1d);

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(equalTo(E.VALUE_AT_LEAST.get(Type.POSITIVE_QUANTITY, -0.01, Has.QUALITY_MIN_AMOUNT)));

        hs.setValue(-0.01);
    }

    @Test
    public void testSetValue9() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, 1d);
        hs.setValue(Double.NEGATIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.NEGATIVE_INFINITY)));
    }

    @Test
    public void testSetValue10() {
        final Agent a = agentFactory.buildAgent(idFactory.build(Agent.class, "a"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.POSITIVE_UNBOUNDED);
        final Has hs = hasFactory.buildHas(a, c, 1d);
        hs.setValue(Double.POSITIVE_INFINITY);

        assertThat(hs.getValue(), is(equalTo(Double.POSITIVE_INFINITY)));
    }

    @Test
    public void testEquals() {
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs1 = hasFactory.buildHas(a1, c, 1d);
        final Has hs2 = hasFactory.buildHas(a2, c, 1d);
        final Has hs3 = hasFactory.buildHas(a1, c, 1d);

        assertThat(hs1, is(not(equalTo(hs2))));
        assertThat(hs1, is(equalTo(hs3)));
        assertThat(hs1, is(not(equalTo(""))));
    }

    @Test
    public void testHashCode() {
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs1 = hasFactory.buildHas(a1, c, 1d);
        final Has hs2 = hasFactory.buildHas(a2, c, 1d);
        final Has hs3 = hasFactory.buildHas(a1, c, 1d);

        assertThat(hs1.hashCode(), is(not(equalTo(hs2.hashCode()))));
        assertThat(hs1.hashCode(), is(equalTo(hs3.hashCode())));
    }

    @Test
    public void testToString() {
        final Agent a1 = agentFactory.buildAgent(idFactory.build(Agent.class, "a1"), new ContactInfo() {});
        final Agent a2 = agentFactory.buildAgent(idFactory.build(Agent.class, "a2"), new ContactInfo() {});
        final Attribute c = attributeFactory.buildAttribute(idFactory.build(Attribute.class, "c"), Type.NEGATIVE_QUALITY);
        final Has hs1 = hasFactory.buildHas(a1, c, 1d);
        final Has hs2 = hasFactory.buildHas(a2, c, 1d);
        final Has hs3 = hasFactory.buildHas(a1, c, 1d);

        assertThat(hs1.toString(), is(not(equalTo(hs2.toString()))));
        assertThat(hs1.toString(), is(equalTo(hs3.toString())));
    }

}
