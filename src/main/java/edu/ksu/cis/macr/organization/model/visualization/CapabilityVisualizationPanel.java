/*
 * TreeModel.java
 *
 * Created on Sep 25, 2007
 *
 * See License.txt file the license agreement.
 */
package edu.ksu.cis.macr.organization.model.visualization;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JList;
import javax.swing.JSplitPane;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;

import edu.ksu.cis.macr.organization.model.Capability;
import edu.ksu.cis.macr.organization.model.Organization;

/**
 * The <code>CapabilityVisualizationPanel</code> class is a Swing component to
 * visualize the <code>Capability</code> currently in the
 * <code>Organization</code>.
 * 
 * @author Christopher Zhong
 * @version $Revision: 1.2.4.4 $, $Date: 2011/09/19 14:26:34 $
 * @since 3.4
 */
public class CapabilityVisualizationPanel extends
		AbstractListVisualizationPanel<Capability> {

	/**
	 * Default serial version ID.
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructs a new instance of <code>CapabilityVisualizationPanel</code>.
	 * 
	 * @param organization
	 *            the <code>Organization</code> that is used to visualize the
	 *            <code>Capability</code>.
	 */
	public CapabilityVisualizationPanel(final Organization organization) {
		super(organization, "Capabilities",
				ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,
				ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED,
				JSplitPane.VERTICAL_SPLIT, true,
				ListSelectionModel.SINGLE_SELECTION, JList.VERTICAL, 15);
	}

	@Override
	public void valueChanged(final ListSelectionEvent e) {
		final Capability capability = getList().getSelectedValue();
		if (capability != null) {
			getDetailedInformationPanel().showDetailedInformation(capability);
		}
	}

	@Override
	List<Capability> getCollection() {
		final List<Capability> capabilities = new ArrayList<>(getOrganization()
				.getCapabilities());
		final Comparator<Capability> comparator = new Comparator<Capability>() {

			@Override
			public int compare(final Capability capability1,
					final Capability capability2) {
				return capability1.toString().compareTo(capability2.toString());
			}

		};
		Collections.sort(capabilities, comparator);
		return capabilities;
	}

}
