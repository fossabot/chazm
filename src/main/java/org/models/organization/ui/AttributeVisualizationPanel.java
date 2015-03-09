/*
 * AttributeVisualizationPanel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package org.models.organization.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

import org.models.organization.Organization;
import org.models.organization.entity.Attribute;

/**
 * The <code>AttributeVisualizationPanel</code> class is a Swing component to visualize the <code>Attribute</code> currently in the <code>Organization</code>.
 *
 * @author Christopher Zhong
 * @since 3.4
 */
public class AttributeVisualizationPanel extends AbstractListVisualizationPanel<Attribute> {

	/**
	 * Default serial version ID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>AttributeVisualizationPanel</code>.
	 *
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the <code>Attribute</code>.
	 */
	public AttributeVisualizationPanel(final Organization organization) {
		super(organization, "Attributes", ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true, ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 10);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Attribute attribute = getList().getSelectedValue();
		if (attribute != null) {
			getDetailedInformationPanel().showDetailedInformation(attribute);
		}
	}

	@Override
	List<Attribute> getCollection() {
		final List<Attribute> attributes = new ArrayList<>(getOrganization().getAttributes());
		final Comparator<Attribute> comparator = (attribute1, attribute2) -> attribute1.toString().compareTo(attribute2.toString());
		Collections.sort(attributes, comparator);
		return attributes;
	}

}