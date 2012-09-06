import java.awt.*;
import javax.swing.*;

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
		
		getContentPane().setSize( new Dimension(1280, 800) );
		setSize( new Dimension(1280, 800) );
		
		m_NavigationPanel.setSize( new Dimension(880, 800) );
		m_SectionsSplitter.setDividerLocation(880);
	}
	
	
	private JSplitPane m_SectionsSplitter;
	private NavigationPanel m_NavigationPanel;
	private DetailsPanel m_DetailsPanel;
	private DocumentController[] m_Controllers;
}
