package GUI.Frame.SelectFloorPlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import FloorPlan.Building;
import GUI.Component.SimpleButton;
import GUI.Component.SimpleSelectButton;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.StringR;

/**
 * JPanel for search floor plan
 * @author DongKyu
 *
 */
public class SearchPanel extends JPanel implements ActionListener {
	
	/** For approach parent frame */
	private SelectFloorPlanFrame context;
	
	/** Buttons for show list of floor plan */
	private ArrayList<SimpleSelectButton> floorPlanButtons;
	
	/** Border between buttons */
	private int xBorder = 5;
	/** Border between buttons */
	private int yBorder = 5;
	/** Button height */
	private int listItemHeight = 70;
	
	public SearchPanel(SelectFloorPlanFrame context, int width, int height) {
		super();
		this.setLayout(null);
		this.context = context;
		
		floorPlanButtons = new ArrayList<SimpleSelectButton>();
		
		ArrayList<String> buildings = DataProvider.getInstance().getBuildingsData();
		
		int i;
		for(i = 0; i < buildings.size(); i++) {
			floorPlanButtons.add(
					new SimpleSelectButton(
							floorPlanButtons,
							BorderFactory.createMatteBorder(1, 1, 1, 1, ColorR.GRAY),
							buildings.get(i),
							width - xBorder * 2,
							listItemHeight)
					);
			floorPlanButtons.get(i).setLocation(xBorder,
					yBorder * (i + 1) + listItemHeight * i);
			floorPlanButtons.get(i).setBackground(ColorR.WHITE);
			floorPlanButtons.get(i).setForeground(ColorR.DARK_GRAY);
			floorPlanButtons.get(i).addActionListener(this);
			
			this.add(floorPlanButtons.get(i));
		}
		
		if(height < yBorder * (i + 1) + listItemHeight * i)
			height = yBorder * (i + 1) + listItemHeight * i;
		
		this.setBackground(ColorR.WHITE);
		this.setBorder(null);
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		
	}
}
