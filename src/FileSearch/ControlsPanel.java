package FileSearch;

import java.awt.BorderLayout;
import java.awt.*;

import javax.swing.JPanel;
import javax.swing.*;


public class ControlsPanel extends JPanel {

	
	private class TextToFindSection extends JPanel {
		public TextToFindSection() {
			setLayout( new BorderLayout() );
			m_textToFindLabel = new JLabel("  find : ");
			add(m_textToFindLabel, BorderLayout.LINE_START);
			
			m_TextToFind = new JTextField();
			int preferedHeight = m_TextToFind.getPreferredSize().height;
			m_TextToFind.setPreferredSize( new Dimension(150, preferedHeight) );
			add( m_TextToFind, BorderLayout.CENTER );
			
			m_spacer = new JPanel();
			m_spacer.setMinimumSize( new Dimension(200, 20) );
			add( m_spacer, BorderLayout.SOUTH );
		}
		
		private JLabel						m_textToFindLabel;
		private JTextField					m_TextToFind;
		private JPanel						m_spacer;
	}
	
	/**
	 * 
	 */
	private class DelimiterLinesSection extends JPanel {
		public DelimiterLinesSection() {
			setLayout( new BorderLayout() );
			m_sectionLabel = new JLabel("Delimiter lines");
			add( m_sectionLabel, BorderLayout.NORTH );
			
			m_centerPanel = new JPanel();
			m_centerPanel.setLayout( new BoxLayout(m_centerPanel, BoxLayout.Y_AXIS) );
			add( m_centerPanel, BorderLayout.CENTER );
			
			m_innerSpacer = new JPanel();
			m_innerSpacer.setMinimumSize( new Dimension(10, 10) );
			m_innerSpacer.setMaximumSize( new Dimension(10, 10) );
			m_centerPanel.add(m_innerSpacer);
			
			m_startLineLabel = new JLabel("Start line : ");
			m_startLineTextField = new JTextField();
			m_startLinePanel = new JPanel();
			m_startLinePanel.setLayout( new BorderLayout() );
			m_startLinePanel.add( m_startLineLabel, BorderLayout.LINE_START );
			m_startLinePanel.add( m_startLineTextField, BorderLayout.CENTER );
			Dimension max = m_startLineTextField.getPreferredSize();
			m_startLinePanel.setMaximumSize( new Dimension(200, max.height) );
			m_centerPanel.add( m_startLinePanel );

			m_endLineLabel = new JLabel("End line : ");
			m_endLineTextField = new JTextField();
			m_endLinePanel = new JPanel();
			m_endLinePanel.setLayout( new BorderLayout() );
			m_endLinePanel.add( m_endLineLabel, BorderLayout.LINE_START );
			m_endLinePanel.add( m_endLineTextField, BorderLayout.CENTER );
			max = m_endLineTextField.getPreferredSize();
			m_endLinePanel.setMaximumSize( new Dimension(200, max.height) );
			m_centerPanel.add( m_endLinePanel );
			
			m_bottomSpacer = new JPanel();
			m_bottomSpacer.setMinimumSize( new Dimension(100, 25) );
			m_bottomSpacer.setMaximumSize( new Dimension(100, 25) );
			add(m_bottomSpacer, BorderLayout.SOUTH);
}
		
		private JLabel						m_sectionLabel;
		private JPanel						m_centerPanel;
		private JPanel						m_startLinePanel;
		private JLabel						m_startLineLabel;
		private JTextField					m_startLineTextField;
		private JPanel						m_endLinePanel;
		private JLabel						m_endLineLabel;
		private JTextField					m_endLineTextField;
		private JPanel						m_innerSpacer;
		private JPanel						m_bottomSpacer;
	}
	
	
	public ControlsPanel( FileSearch.Controller controller ) {
		m_controller = controller;
		
		Init();
	}
	
	protected void Init() {
		
		setLayout( new BorderLayout() );
		
		m_section_TextToFind = new TextToFindSection();
		add( m_section_TextToFind, BorderLayout.NORTH );
		
		m_section_DelimiterLines = new DelimiterLinesSection();
		add( m_section_DelimiterLines, BorderLayout.CENTER );
		
		m_btn_search = new JButton("Search");
		add( m_btn_search, BorderLayout.SOUTH );
	}
	
	
	private TextToFindSection			m_section_TextToFind;
	private DelimiterLinesSection		m_section_DelimiterLines;
	private JButton						m_btn_search;
	
	private FileSearch.Controller 		m_controller;
}
