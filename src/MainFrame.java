import java.awt.GraphicsConfiguration;
import javax.swing.JSplitPane;
import java.awt.HeadlessException;

import javax.swing.JFrame;


public class MainFrame extends JFrame {

	public MainFrame() throws HeadlessException {
		Init();
	}

	public MainFrame(GraphicsConfiguration arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MainFrame(String arg0) throws HeadlessException {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public MainFrame(String arg0, GraphicsConfiguration arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	protected void Init() {
		
		m_NavigationPanel = new NavigationPanel();
		m_DetailsPanel = new DetailsPanel();
		
		m_SectionsSplitter = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,
											m_NavigationPanel,
											m_DetailsPanel );
		this.setContentPane(m_SectionsSplitter);
		
		m_Controllers = new DocumentController[2];
		m_Controllers[0] = new DocumentController(m_NavigationPanel, m_DetailsPanel);
		
		pack();
		
	}
	
	
	private JSplitPane m_SectionsSplitter;
	private NavigationPanel m_NavigationPanel;
	private DetailsPanel m_DetailsPanel;
	private DocumentController[] m_Controllers;
}
