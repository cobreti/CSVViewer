import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.*;

public class DocNavPanel extends JPanel {

	public DocNavPanel(DocumentController controller) {
		m_Controller = controller;
		Init();
	}

	public DocNavPanel(LayoutManager arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DocNavPanel(boolean arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public DocNavPanel(LayoutManager arg0, boolean arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}
	
	public JTextField getFile() {
		return m_File;
	}
	
	public void updateContent() {
		
		Document doc = m_Controller.getDocument();
		int linesCount = doc.getLinesCount();
		
		m_ContentVertScrollBar.setMaximum(linesCount);
		
		m_View.updateUI();
	}

	protected void Init() {
		
		setLayout( new BorderLayout() );
		
		m_File = new JTextField();
		m_Content = new JPanel();
		m_ContentVertScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
		m_View = new DocViewPanel(m_Controller);
		m_Content.setLayout( new BorderLayout() );
		m_Content.add( m_ContentVertScrollBar, BorderLayout.EAST );
		m_Content.add( m_View, BorderLayout.CENTER );
		m_btnBrowseFile = new JButton("...");
		m_btnBrowseFile.setActionCommand("BrowseFile");
		m_btnBrowseFile.addActionListener(m_Controller);

		m_PathPanel = new JPanel();
		m_PathPanel.setLayout( new BorderLayout() );
		m_PathPanel.add(m_File, BorderLayout.CENTER);
		m_PathPanel.add(m_btnBrowseFile, BorderLayout.LINE_END);
		
		add(m_PathPanel, BorderLayout.PAGE_START);
		add(m_Content, BorderLayout.CENTER);
	}
	
	private JTextField		m_File;
	private JPanel			m_Content;
	private DocViewPanel	m_View;
	private JScrollBar		m_ContentVertScrollBar;
	private JButton			m_btnBrowseFile;
	private JPanel			m_PathPanel;
	private DocumentController m_Controller;
}
