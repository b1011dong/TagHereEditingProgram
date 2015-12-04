package GUI.Frame.EditFloorPlan;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

import FloorPlanObject.IconType;
import FloorPlanObject.ObjectType;
import GUI.Component.SimpleSelectImageButton;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.DimenR;

public class ToolOptionPanel extends JPanel{
	private int endLocation;
	
	public ToolOptionPanel(int width, int height) {
		super();
		this.setLayout(null);
		endLocation = 0;

		this.setBackground(ColorR.WHITE);
		this.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, ColorR.LIGHT_GRAY));
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}
	
	public void drawLineOption() {
		this.removeAll();
		DataProvider.getInstance().setCurrentIconType(null);
		endLocation = 0;

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
	
	public void drawIconOption() {
		this.removeAll();
		
		ArrayList<SimpleSelectImageButton> buttons = new ArrayList<SimpleSelectImageButton>();

		ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
		icons.add(new ImageIcon("src/Image/door_icon_base.png"));
		icons.add(new ImageIcon("src/Image/door_icon.png"));
		icons.add(new ImageIcon("src/Image/elevator_icon_base.png"));
		icons.add(new ImageIcon("src/Image/elevator_icon.png"));
		icons.add(new ImageIcon("src/Image/stairs_icon_base.png"));
		icons.add(new ImageIcon("src/Image/stairs_icon.png"));
		icons.add(new ImageIcon("src/Image/toilet_icon_base.png"));
		icons.add(new ImageIcon("src/Image/toilet_icon.png"));
		icons.add(new ImageIcon("src/Image/escalator_up_icon_base.png"));
		icons.add(new ImageIcon("src/Image/escalator_up_icon.png"));
		icons.add(new ImageIcon("src/Image/escalator_down_icon_base.png"));
		icons.add(new ImageIcon("src/Image/escalator_down_icon.png"));
		icons.add(new ImageIcon("src/Image/escalator_up_down_icon_base.png"));
		icons.add(new ImageIcon("src/Image/escalator_up_down_icon.png"));

		int yBorder = 20;
		endLocation = 0;
		
		for(int i = 0; i < IconType.values().length; i++) {
			buttons.add(
					new SimpleSelectImageButton(
						buttons,
						icons.get(i * 2),
						icons.get(i * 2 + 1),
						DimenR.BUTTON_HEIGHT,
						DimenR.BUTTON_HEIGHT,
						DimenR.BUTTON_WIDTH,
						DimenR.BUTTON_HEIGHT
					)
			);
			
			final int temp = i;
			buttons.get(i).addActionListener(new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	DataProvider.getInstance().setCurrentIconType(
				    			IconType.values()[temp]);
				    }
			    });
			
			buttons.get(i).setLocation(
					0,
					yBorder * (i + 1) + DimenR.BUTTON_HEIGHT * i
					);
			
			endLocation = buttons.get(i).getLocation().y + buttons.get(i).getHeight() + yBorder;
			
			this.add(buttons.get(i));
		}
		

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
	
	public void drawOvalOption() {
		this.removeAll();
		DataProvider.getInstance().setCurrentIconType(null);
		endLocation = 0;

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
	
	public void drawRectOption() {
		this.removeAll();
		DataProvider.getInstance().setCurrentIconType(null);
		endLocation = 0;

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
	
	public void drawTagOption() {
		this.removeAll();
		DataProvider.getInstance().setCurrentIconType(null);
		endLocation = 0;

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
	
	public void drawSelectOption() {
		this.removeAll();
		DataProvider.getInstance().setCurrentIconType(null);
		endLocation = 0;

		this.setPreferredSize(new Dimension(getWidth(), endLocation));
		this.setSize(new Dimension(getWidth(), endLocation));
		repaint();
	}
}
