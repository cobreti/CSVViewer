package org;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class DocNavStatusPanel extends JPanel {

	public DocNavStatusPanel(DocumentController controller) {
		m_controller = controller;
		
		setLayout( new BoxLayout(this, BoxLayout.X_AXIS));
		
		m_searchPanel = new ViewSearchPanel(m_controller);
		add(m_searchPanel);
		
	}
	
	private DocumentController 	m_controller;
	private ViewSearchPanel		m_searchPanel;
}
