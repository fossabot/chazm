package io.github.runtimemodels.notification;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import io.github.runtimemodels.chazm.entity.Agent;
import io.github.runtimemodels.chazm.entity.Attribute;
import io.github.runtimemodels.chazm.entity.Capability;
import io.github.runtimemodels.chazm.entity.Characteristic;
import io.github.runtimemodels.chazm.entity.InstanceGoal;
import io.github.runtimemodels.chazm.entity.Pmf;
import io.github.runtimemodels.chazm.entity.Policy;
import io.github.runtimemodels.chazm.entity.Role;
import io.github.runtimemodels.chazm.entity.SpecificationGoal;
import io.github.runtimemodels.chazm.event.AchievesEvent;
import io.github.runtimemodels.chazm.event.AgentEvent;
import io.github.runtimemodels.chazm.event.AssignmentEvent;
import io.github.runtimemodels.chazm.event.AttributeEvent;
import io.github.runtimemodels.chazm.event.CapabilityEvent;
import io.github.runtimemodels.chazm.event.CharacteristicEvent;
import io.github.runtimemodels.chazm.event.ContainsEvent;
import io.github.runtimemodels.chazm.event.EventCategory;
import io.github.runtimemodels.chazm.event.HasEvent;
import io.github.runtimemodels.chazm.event.InstanceGoalEvent;
import io.github.runtimemodels.chazm.event.ModeratesEvent;
import io.github.runtimemodels.chazm.event.NeedsEvent;
import io.github.runtimemodels.chazm.event.PmfEvent;
import io.github.runtimemodels.chazm.event.PolicyEvent;
import io.github.runtimemodels.chazm.event.PossessesEvent;
import io.github.runtimemodels.chazm.event.RequiresEvent;
import io.github.runtimemodels.chazm.event.RoleEvent;
import io.github.runtimemodels.chazm.event.SpecificationGoalEvent;
import io.github.runtimemodels.chazm.event.UsesEvent;
import io.github.runtimemodels.chazm.id.UniqueId;
import io.github.runtimemodels.message.E;
import mockit.Capturing;
import mockit.Expectations;
import mockit.FullVerifications;
import mockit.Mock;
import mockit.MockUp;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;

@SuppressWarnings("javadoc")
@RunWith(JMockit.class)
public class SubscriberTest {

    private final Injector injector = Guice.createInjector(new NotificationModule());
    private final Provider<DefaultSubscriber> provider = injector.getProvider(DefaultSubscriber.class);
    @Capturing
    private Logger logger;
    @Mocked
    private UniqueId<Agent> agentId;
    @Mocked
    private UniqueId<Attribute> attributeId;
    @Mocked
    private UniqueId<Capability> capabilityId;
    @Mocked
    private UniqueId<Characteristic> characteristicId;
    @Mocked
    private UniqueId<InstanceGoal> instGoalId;
    @Mocked
    private UniqueId<Pmf> pmfId;
    @Mocked
    private UniqueId<Policy> policyId;
    @Mocked
    private UniqueId<Role> roleId;
    @Mocked
    private UniqueId<SpecificationGoal> specGoalId;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testSubscriber() {
        final Subscriber s1 = provider.get();
        final Subscriber s2 = provider.get();

        assertThat(s1, is(not(nullValue())));
        assertThat(s1, is(not(sameInstance(s2))));
    }

    @Test
    public void testEvent1(@Mocked final AchievesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getGoalId();
                result = specGoalId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((AchievesEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventCategory.ADDED, roleId, specGoalId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventCategory.CHANGED, roleId, specGoalId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, goal = {}", AchievesEvent.class.getSimpleName(), EventCategory.REMOVED, roleId, specGoalId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent2(@Mocked final AgentEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = agentId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((AgentEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventCategory.ADDED, agentId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventCategory.CHANGED, agentId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", AgentEvent.class.getSimpleName(), EventCategory.REMOVED, agentId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent3(@Mocked final AssignmentEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getRoleId();
                result = roleId;
                event.getGoalId();
                result = instGoalId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((AssignmentEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventCategory.ADDED, agentId,
                        roleId, instGoalId);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventCategory.CHANGED, agentId,
                        roleId, instGoalId);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, role = {}, goal = {}", AssignmentEvent.class.getSimpleName(), EventCategory.REMOVED, agentId,
                        roleId, instGoalId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent4(@Mocked final AttributeEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((AttributeEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventCategory.ADDED, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventCategory.CHANGED, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", AttributeEvent.class.getSimpleName(), EventCategory.REMOVED, attributeId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent5(@Mocked final CapabilityEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = capabilityId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((CapabilityEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventCategory.ADDED, capabilityId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventCategory.CHANGED, capabilityId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", CapabilityEvent.class.getSimpleName(), EventCategory.REMOVED, capabilityId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent6(@Mocked final CharacteristicEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = characteristicId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((CharacteristicEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventCategory.ADDED, characteristicId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventCategory.CHANGED, characteristicId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", CharacteristicEvent.class.getSimpleName(), EventCategory.REMOVED, characteristicId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent7(@Mocked final ContainsEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getCharacteristicId();
                result = characteristicId;
                event.getValue();
                result = 0.25;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((ContainsEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventCategory.ADDED, roleId,
                        characteristicId, 0.25);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventCategory.CHANGED,
                        roleId, characteristicId, 0.25);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, characteristic = {}, value = {}", ContainsEvent.class.getSimpleName(), EventCategory.REMOVED,
                        roleId, characteristicId, 0.25);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent8(@Mocked final HasEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getAttributeId();
                result = attributeId;
                event.getValue();
                result = 0.7;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((HasEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventCategory.ADDED, agentId,
                        attributeId, 0.7);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventCategory.CHANGED, agentId,
                        attributeId, 0.7);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, attribute = {}, value = {}", HasEvent.class.getSimpleName(), EventCategory.REMOVED, agentId,
                        attributeId, 0.7);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent9(@Mocked final InstanceGoalEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = instGoalId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((InstanceGoalEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventCategory.ADDED, instGoalId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventCategory.CHANGED, instGoalId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", InstanceGoalEvent.class.getSimpleName(), EventCategory.REMOVED, instGoalId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent10(@Mocked final ModeratesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getPmfId();
                result = pmfId;
                event.getAttributeId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((ModeratesEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventCategory.ADDED, pmfId, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventCategory.CHANGED, pmfId, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): pmf = {}, attribute = {}", ModeratesEvent.class.getSimpleName(), EventCategory.REMOVED, pmfId, attributeId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent11(@Mocked final NeedsEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getAttributeId();
                result = attributeId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((NeedsEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventCategory.ADDED, roleId, attributeId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventCategory.CHANGED, roleId, attributeId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, attribute = {}", NeedsEvent.class.getSimpleName(), EventCategory.REMOVED, roleId, attributeId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent12(@Mocked final PmfEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = pmfId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((PmfEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventCategory.ADDED, pmfId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventCategory.CHANGED, pmfId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", PmfEvent.class.getSimpleName(), EventCategory.REMOVED, pmfId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent13(@Mocked final PolicyEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = policyId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((PolicyEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventCategory.ADDED, policyId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventCategory.CHANGED, policyId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", PolicyEvent.class.getSimpleName(), EventCategory.REMOVED, policyId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent14(@Mocked final PossessesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getAgentId();
                result = agentId;
                event.getCapabilityId();
                result = capabilityId;
                event.getScore();
                result = 0.8;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((PossessesEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventCategory.ADDED, agentId,
                        capabilityId, 0.8);
                times = 2;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventCategory.CHANGED,
                        agentId, capabilityId, 0.8);
                times = 1;
                logger.debug("Received ({}, {}): agent = {}, capability = {}, score = {}", PossessesEvent.class.getSimpleName(), EventCategory.REMOVED,
                        agentId, capabilityId, 0.8);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent15(@Mocked final RequiresEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getCapabilityId();
                result = capabilityId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((RequiresEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventCategory.ADDED, roleId, capabilityId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventCategory.CHANGED, roleId, capabilityId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, capability = {}", RequiresEvent.class.getSimpleName(), EventCategory.REMOVED, roleId, capabilityId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent16(@Mocked final RoleEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = roleId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((RoleEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventCategory.ADDED, roleId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventCategory.CHANGED, roleId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", RoleEvent.class.getSimpleName(), EventCategory.REMOVED, roleId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent17(@Mocked final SpecificationGoalEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getId();
                result = specGoalId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((SpecificationGoalEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventCategory.ADDED, specGoalId);
                times = 2;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventCategory.CHANGED, specGoalId);
                times = 1;
                logger.debug("Received ({}, {}): id = {}", SpecificationGoalEvent.class.getSimpleName(), EventCategory.REMOVED, specGoalId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent18(@Mocked final UsesEvent event) {
        final DefaultSubscriber s1 = provider.get();
        new Expectations() {
            {
                event.getCategory();
                returns(EventCategory.ADDED, EventCategory.ADDED, EventCategory.CHANGED, EventCategory.REMOVED);
                event.getRoleId();
                result = roleId;
                event.getPmfId();
                result = pmfId;
            }
        };
        final Mediator mockMediator = new MockUp<DefaultMediator>() {

            @Mock
            <T> void post(final T event) {
                s1.event((UsesEvent) event);
            }

        }.getMockInstance();

        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);
        mockMediator.post(event);

        new FullVerifications(Logger.class) {
            {
                logger.info(anyString, any, any);
                times = 4;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventCategory.ADDED, roleId, pmfId);
                times = 2;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventCategory.CHANGED, roleId, pmfId);
                times = 1;
                logger.debug("Received ({}, {}): role = {}, pmf = {}", UsesEvent.class.getSimpleName(), EventCategory.REMOVED, roleId, pmfId);
                times = 1;
            }
        };
    }

    @Test
    public void testEvent19() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AchievesEvent) null);
    }

    @Test
    public void testEvent20() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AgentEvent) null);
    }

    @Test
    public void testEvent21() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AssignmentEvent) null);
    }

    @Test
    public void testEvent22() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((AttributeEvent) null);
    }

    @Test
    public void testEvent23() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((CapabilityEvent) null);
    }

    @Test
    public void testEvent24() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((CharacteristicEvent) null);
    }

    @Test
    public void testEvent25() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((ContainsEvent) null);
    }

    @Test
    public void testEvent26() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((HasEvent) null);
    }

    @Test
    public void testEvent27() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((InstanceGoalEvent) null);
    }

    @Test
    public void testEvent28() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((ModeratesEvent) null);
    }

    @Test
    public void testEvent29() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((NeedsEvent) null);
    }

    @Test
    public void testEvent30() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PmfEvent) null);
    }

    @Test
    public void testEvent31() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PolicyEvent) null);
    }

    @Test
    public void testEvent32() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((PossessesEvent) null);
    }

    @Test
    public void testEvent33() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((RequiresEvent) null);
    }

    @Test
    public void testEvent34() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((RoleEvent) null);
    }

    @Test
    public void testEvent35() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((SpecificationGoalEvent) null);
    }

    @Test
    public void testEvent36() {
        final DefaultSubscriber s1 = provider.get();

        exception.expect(IllegalArgumentException.class);
        exception.expectMessage(E.PARAMETER_CANNOT_BE_NULL.get("arg0", "event"));

        s1.event((UsesEvent) null);
    }

}
