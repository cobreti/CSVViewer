package Main;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


import FileSearch.FileSearchWnd;

import java.io.*;

public class DocumentController {

	private static int CountOccurencesOf(String str, char c) {
		int ret = 0;
		
		for (int index = 0; index < str.length(); ++ index) {
			if ( str.charAt(index) == c )
				++ ret;
		}
		
		return ret;
	}
	
	public DocumentController(int index, NavigationPanel NavPanel, DetailsPanel DetailsPanel) {
		
		m_index = index;
		m_NavPanel = NavPanel.getSectionPanel(m_index);
		m_DetailsPanel = DetailsPanel.getSectionPanel(m_index);
		m_DocNavPanel = new DocNavPanel(this);
		
		m_NavPanel.setLayout( new BorderLayout() );
		m_NavPanel.add( m_DocNavPanel, BorderLayout.CENTER );
		
		m_Document = new Document(null);
		
		m_FileSearchWnd = new FileSearchWnd(this);
	}
	
	public Document getDocument() { return m_Document; }

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
		
		m_Document.OnLineSelected(index, text);
		m_DocNavPanel.OnLineSelected(index, text);
		
		char			sep1 = 0xFE;
		char			sep2 = ';';
		int				count1 = CountOccurencesOf(text, sep1);
		int				count2 = CountOccurencesOf(text, sep2);
		String[]		split_line = text.split(";");
		
		if ( count1 > count2 )
			split_line = text.split(Character.toString(sep1));
		else
			split_line = text.split(Character.toString(sep2));
		
		String[]		formated_text = new String[split_line.length];
		
		for (int i = 0; i < split_line.length; ++i) {
			formated_text[i] = String.format("%5s : [%s]", Integer.toString(i), split_line[i]);
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
	
	public void OnFindNext(String text)
	{
		if ( m_Document.SearchNext(text) )
		{
			m_DocNavPanel.EnsurePosVisible(	m_Document.getFoundElementPos().getLineNo(), 
											m_Document.getFoundElementPos().getStart() );
		}
		m_DocNavPanel.updateUI();
	}
	
	public void OnFindPrevious(String text)
	{
		if ( m_Document.SearchPrevious(text) )
		{
			m_DocNavPanel.EnsurePosVisible(	m_Document.getFoundElementPos().getLineNo(), 
											m_Document.getFoundElementPos().getStart() );
		}
		m_DocNavPanel.updateUI();
	}

	public void OnChooseFile() {
		
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
			m_FileSearchWnd.OnNewDocument();
		}
	}
	
	
	public void OnFileSearch() {
		m_FileSearchWnd.setVisible(true);
	}
	
	
	private JPanel 				m_NavPanel;
	private JPanel 				m_DetailsPanel;
	private DocNavPanel 		m_DocNavPanel;
	private Document 			m_Document;
	private int					m_index;
	private FileSearchWnd		m_FileSearchWnd;
}
