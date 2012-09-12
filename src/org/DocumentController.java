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
	
	public void OnMaxLineWidthUpdate(int maxWidth) {
		m_DocNavPanel.OnMaxLineWidthUpdate(maxWidth);
	}
	
	public void OnContentWindowIndexChanged(int index) {
		m_DocNavPanel.OnDocumentClosing();
		m_Document.ReadContent(index);
		m_DocNavPanel.OnDocumentContentChanged();
	}
	
	public void OnLineSelected(int index, String text) {
		
		m_DocNavPanel.OnLineSelected(index, text);
		
		String[]		split_line = text.split(";");
		String[]		formated_text = new String[split_line.length];
		
		for (int i = 0; i < split_line.length; ++i) {
			formated_text[i] = String.format("%5s : %s", Integer.toString(i), split_line[i]);
		}
		
		m_DetailsPanel.removeAll();
		
		JList	list = new JList(formated_text);
		list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		
		JScrollPane		scroller = new JScrollPane(list);
		m_DetailsPanel.add(scroller, BorderLayout.CENTER);
		m_DetailsPanel.updateUI();
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
			m_Document.ReadContent(0);
			m_DocNavPanel.OnNewDocument();
		}
	}
	
	
	private JPanel 			m_NavPanel;
	private JPanel 			m_DetailsPanel;
	private DocNavPanel 	m_DocNavPanel;
	private Document 		m_Document;
	private int				m_index;
}
