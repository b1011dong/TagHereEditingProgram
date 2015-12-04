package GUI.Frame.SelectFloorPlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import FloorPlan.Building;
import GUI.Component.SimpleButton;
import GUI.Component.SimpleLabel;
import GUI.Component.SimpleTextArea;
import GUI.Frame.EditFloorPlan.EditFloorPlanFrame;
import Resource.ColorR;
import Resource.DimenR;
import Resource.StringR;

/**
 * JPanel for preview floor plan
 * @author DongKyu
 *
 */

public class PreviewPanel extends JPanel implements ActionListener{
	
	/** For approach parent frame */
	SelectFloorPlanFrame context;
	
	/** For get building data */
	private Building building;
	/** For show building name */
	private SimpleLabel nameLabel;
	/** For show building information */
	private SimpleTextArea infoLabel;
//	/** For show building position */
//	private SimpleLabel positionLabel;
	/** Button for edit building */
	private SimpleButton editBuildingButton;
	/** Button for delete building */
	private SimpleButton deleteBuildingButton;
	
	private int xBorder = 20;
	private int buttonXBorder = 5;
	private int yBorder = 10;
	
	public PreviewPanel(SelectFloorPlanFrame context, int width, int height, Building building) {
		super();
		this.context = context;
		this.setLayout(null);
		this.building = building;
		
		nameLabel = new SimpleLabel("");
		infoLabel = new SimpleTextArea("");
		
		if(building != null) {
			nameLabel.setText(building.getName());
			nameLabel.setSize(
					width - xBorder * 2,
					DimenR.TITLE_BAR_HEIGHT
					);
			nameLabel.setHorizontalAlignment(SwingConstants.LEFT);
			nameLabel.setBigFont();
			nameLabel.setForeground(ColorR.DARK_GRAY);
			nameLabel.setLocation(
					xBorder,
					0
					);
			
			infoLabel.setText(building.getInformation());
			infoLabel.setSize(
					width - xBorder * 2,
					DimenR.BUILDING_INFO_HEIGHT
					);
			infoLabel.setSmallFont();
			infoLabel.setForeground(ColorR.DARK_GRAY);
			infoLabel.setLocation(
					xBorder,
					DimenR.TITLE_BAR_HEIGHT
					);
			infoLabel.setBorder(BorderFactory.
					createMatteBorder(1, 0, 1, 0, ColorR.DARK_GRAY));
		}
//		positionLabel = new SimpleLabel(building.getPosition());
		
		editBuildingButton = new SimpleButton(
				StringR.EDIT,
				DimenR.BUTTON_WIDTH,
				DimenR.BUTTON_HEIGHT
				);
		editBuildingButton.setLocation(
				width - DimenR.BUTTON_WIDTH - buttonXBorder,
				0
				);
		editBuildingButton.addActionListener(this);
		
		
		deleteBuildingButton = new SimpleButton(
				StringR.DELETE,
				DimenR.BUTTON_WIDTH,
				DimenR.BUTTON_HEIGHT
				);
		deleteBuildingButton.setLocation(
				width - DimenR.BUTTON_WIDTH * 2 - buttonXBorder * 2,
				0
				);
		deleteBuildingButton.addActionListener(this);
		
		
		
		this.add(editBuildingButton);
		this.add(deleteBuildingButton);
		this.add(nameLabel);
		this.add(infoLabel);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(ColorR.GRAY, 1));
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		switch(arg0.getSource().toString()) {
		case StringR.EDIT:
			EditFloorPlanFrame temp = new EditFloorPlanFrame(
					StringR.EDIT_FLOOR_PLAN,
					DimenR.FLOOR_PLAN_EDIT_FRAME_WIDTH,
					DimenR.FLOOR_PLAN_EDIT_FRAME_HEIGHT
					);
			temp.setEditViewFocus();
			context.dispose();
			break;
			
		case StringR.DELETE:
			break;
		}
		
	}

}
