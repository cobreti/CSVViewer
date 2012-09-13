package FileSearch;

import javax.swing.JPanel;

public class ControlsPanel extends JPanel {

	public ControlsPanel( FileSearch.Controller controller ) {
		m_controller = controller;
	}
	
	private FileSearch.Controller m_controller;
}
