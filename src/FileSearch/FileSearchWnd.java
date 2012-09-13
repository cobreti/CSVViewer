package FileSearch;

import javax.swing.JFrame;
import javax.swing.JPanel;

import Main.DocumentController;

import java.io.*;

import java.awt.*;

public class FileSearchWnd extends JFrame {

	public FileSearchWnd(Main.DocumentController docController) {
		
		m_docController = docController;
		m_controller = new FileSearch.Controller(this);
		
		setSize( new Dimension(600, 200) );
		this.setTitle("File search : ");
		
		m_contentPanel = new JPanel();
		m_contentPanel.setLayout( new BorderLayout() );
		setContentPane(m_contentPanel);
	}
	
	public void OnNewDocument() {
		
		File	f = m_docController.getDocument().getFile();
		
		setTitle("File search : " + f.getPath() );
	}
	
	private Main.DocumentController			m_docController;
	private FileSearch.Controller			m_controller;
	private JPanel							m_contentPanel;
}
