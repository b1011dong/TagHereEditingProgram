package GUI.Frame.EditFloorPlan;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;

import Resource.ColorR;

public class PropertiesPanel extends JPanel {
	
	public PropertiesPanel(int width, int height) {
		super();
		this.setLayout(null);

		this.setBackground(ColorR.WHITE);
		this.setBorder(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}
}
