
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DocumentController implements ActionListener {

	public DocumentController(JPanel NavPanel, JPanel DetailsPanel) {
		m_NavPanel = NavPanel;
		m_DetailsPanel = DetailsPanel;
		m_DocNavPanel = new DocNavPanel(this);
		
		m_NavPanel.setLayout( new BorderLayout() );
		m_NavPanel.add( m_DocNavPanel, BorderLayout.CENTER );
	}

	public void actionPerformed(ActionEvent ae) {
		
		if ( ae.getActionCommand().equals("BrowseFile") )
			OnChooseFile();
	}
	
	
	protected void OnChooseFile() {
		
		JFileChooser fc = new JFileChooser(m_CurrentFile);
		
		int returnVal = fc.showOpenDialog(null);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			m_CurrentFile = fc.getSelectedFile();
		
			m_DocNavPanel.getFile().setText( m_CurrentFile.getPath() );
		}
	}
	
	
	private JPanel m_NavPanel;
	private JPanel m_DetailsPanel;
	private DocNavPanel m_DocNavPanel;
	private File m_CurrentFile;
}
