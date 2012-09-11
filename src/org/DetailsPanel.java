package org;

import java.awt.LayoutManager;
import javax.swing.JPanel;
import javax.swing.JSplitPane;
import java.awt.*;


public class DetailsPanel extends JPanel {

	public DetailsPanel() {
		super();

		m_sections = new JPanel[2];
		m_sections[0] = new JPanel();
		m_sections[1] = new JPanel();
		
		setLayout( new BorderLayout() );
		
		m_splitter = new JSplitPane(	JSplitPane.HORIZONTAL_SPLIT,
										m_sections[0],
										m_sections[1] );
		
		add(m_splitter, BorderLayout.CENTER);
	}

	public JPanel getSectionPanel(int index) { return m_sections[index]; }
	

	private JSplitPane		m_splitter;
	private JPanel[]		m_sections;
}
