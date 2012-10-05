package Main;

import java.awt.*;
import javax.swing.*;

public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		Init();
	}

	protected void Init() {
		
		m_NavigationPanel = new NavigationPanel();
		m_DetailsPanel = new DetailsPanel();
		
		m_SectionsSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
											m_NavigationPanel,
											m_DetailsPanel );
		this.setContentPane(m_SectionsSplitter);
		
		m_Controllers = new DocumentController[2];
		m_Controllers[0] = new DocumentController(0, m_NavigationPanel, m_DetailsPanel);
		m_Controllers[1] = new DocumentController(1, m_NavigationPanel, m_DetailsPanel);
		
		getContentPane().setSize( new Dimension(1280, 800) );
		setSize( new Dimension(1280, 800) );
		
		m_NavigationPanel.setSize( new Dimension(880, 800) );
		m_SectionsSplitter.setDividerLocation(880);
	}
	
	
	private JSplitPane 				m_SectionsSplitter;
	private NavigationPanel 		m_NavigationPanel;
	private DetailsPanel 			m_DetailsPanel;
	private DocumentController[] 	m_Controllers;
}
