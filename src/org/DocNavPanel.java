package org;

import java.awt.BorderLayout;
import java.awt.LayoutManager;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DocNavPanel extends JPanel
							implements AdjustmentListener, ComponentListener {

	public DocNavPanel(DocumentController controller) {
		m_Controller = controller;
		addComponentListener(this);
		Init();
	}
	
	public JTextField getFile() {
		return m_File;
	}
	
	public void OnDocumentContentChanged() {
		
		Document doc = m_Controller.getDocument();
		int linesCount = doc.getLinesCount();
		
		
		
		m_ContentVertScrollBar.setValue(0);
		m_ContentVertScrollBar.setMaximum(linesCount);
		
		m_View.OnDocumentContentChanged();
	}
	
	public void OnNewDocument()
	{
		m_windowSelectionPanel.OnNewDocument(m_Controller.getDocument());
		
		OnDocumentContentChanged();
	}
	
	public void OnDocumentClosing() {
		m_ContentVertScrollBar.setValue(0);
		m_ContentVertScrollBar.setMaximum(0);
		
		m_View.OnDocumentClosing();
	}
	
	public void OnVisibleLinesCountChanged(int visibleLinesCount) {
		
		Document doc = m_Controller.getDocument();
		int linesCount = doc.getLinesCount();
		m_visibleLinesCount = visibleLinesCount;
		
		int maxValue = linesCount - visibleLinesCount + 1;
		if ( maxValue < 0 )
			maxValue = 0;
		
		m_ContentVertScrollBar.setMaximum(maxValue);
	}
	
	public void OnLineSelected(int index, String text) {
		
		int topPos = m_ContentVertScrollBar.getValue();
		
		if ( topPos > index )
		{
			m_ContentVertScrollBar.setValue(index);
		}
		else if ( topPos + m_visibleLinesCount < index )
		{
			m_ContentVertScrollBar.setValue(index - m_visibleLinesCount);
		}
	}
	
	public void OnMaxLineWidthUpdate(int maxWidth)
	{
		if ( maxWidth > m_ContentHorzScrollBar.getMaximum() )
			m_ContentHorzScrollBar.setMaximum(maxWidth);
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e) {
		System.out.println( "ID : " + e.getID() + " - value : " + e.getValue() );
	}
	
	public void componentHidden(ComponentEvent e) {
		
	}
	
	public void componentMoved(ComponentEvent e) {
		
	}
	
	public void componentResized(ComponentEvent e) {
	}
	
	public void componentShown(ComponentEvent e) {
		
	}

	protected void Init() {
		
		setLayout( new BorderLayout() );
		
		m_File = new JTextField();
		m_Content = new JPanel();
		m_ContentVertScrollBar = new JScrollBar(JScrollBar.VERTICAL, 0, 0, 0, 0);
		m_ContentHorzScrollBar = new JScrollBar(JScrollBar.HORIZONTAL, 0, 0, 0, 0);
		
		m_View = new DocViewPanel(m_Controller);
		m_ContentVertScrollBar.addAdjustmentListener( m_View.new VertScrollbarListener(m_View) );
		m_ContentHorzScrollBar.addAdjustmentListener( m_View.new HorzScrollbarListener(m_View) );
		
		m_Content.setLayout( new BorderLayout() );
		m_Content.add( m_ContentVertScrollBar, BorderLayout.EAST );
		m_Content.add( m_ContentHorzScrollBar, BorderLayout.SOUTH );
		m_Content.add( m_View, BorderLayout.CENTER );
		m_btnBrowseFile = new JButton("...");
		m_btnBrowseFile.setActionCommand("BrowseFile");
		m_btnBrowseFile.addActionListener(m_Controller);

		m_PathPanel = new JPanel();
		m_PathPanel.setLayout( new BorderLayout() );
		m_PathPanel.add(m_File, BorderLayout.CENTER);
		m_PathPanel.add(m_btnBrowseFile, BorderLayout.LINE_END);

		m_windowSelectionPanel = new ContentWindowSelectionPanel(m_Controller);
		
		add(m_PathPanel, BorderLayout.PAGE_START);
		add(m_Content, BorderLayout.CENTER);
		add(m_windowSelectionPanel, BorderLayout.WEST);
	}
	
	private JTextField						m_File;
	private JPanel							m_Content;
	private DocViewPanel					m_View;
	private ContentWindowSelectionPanel		m_windowSelectionPanel;
	private JScrollBar						m_ContentVertScrollBar;
	private JScrollBar						m_ContentHorzScrollBar;
	private JButton							m_btnBrowseFile;
	private JPanel							m_PathPanel;
	private DocumentController 				m_Controller;
	private int								m_visibleLinesCount;
}
