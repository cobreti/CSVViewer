package org;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;

public class DocumentController implements ActionListener {

	public DocumentController(int index, NavigationPanel NavPanel, DetailsPanel DetailsPanel) {
		
		m_index = index;
		m_NavPanel = NavPanel.getSectionPanel(m_index);
		m_DetailsPanel = DetailsPanel.getSectionPanel(m_index);
		m_DocNavPanel = new DocNavPanel(this);
		
		m_NavPanel.setLayout( new BorderLayout() );
		m_NavPanel.add( m_DocNavPanel, BorderLayout.CENTER );
		
		m_Document = new Document(null);
	}
	
	public Document getDocument() { return m_Document; }

	public void actionPerformed(ActionEvent ae) {
		
		if ( ae.getActionCommand().equals("BrowseFile") )
			OnChooseFile();
	}
	
	public void OnVisibleLinesCountChanged(int visibleLinesCount)
	{
		m_DocNavPanel.OnVisibleLinesCountChanged(visibleLinesCount);
	}
	
	
	protected void OnChooseFile() {
		
		File	f = null;
		
		if ( m_Document != null )
			f = m_Document.getFile();
		
		JFileChooser fc = new JFileChooser(f);
		
		int returnVal = fc.showOpenDialog(null);
		
		if ( returnVal == JFileChooser.APPROVE_OPTION ) {
			File file = fc.getSelectedFile();
		
			m_DocNavPanel.OnDocumentClosing();
			m_Document.close();

			m_DocNavPanel.getFile().setText( file.getPath() );
			m_Document = new Document(fc.getSelectedFile());
			m_DocNavPanel.OnDocumentContentChanged();
		}
	}
	
	
	private JPanel 			m_NavPanel;
	private JPanel 			m_DetailsPanel;
	private DocNavPanel 	m_DocNavPanel;
	private Document 		m_Document;
	private int				m_index;
}
