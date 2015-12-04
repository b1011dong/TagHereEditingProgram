package GUI.Frame.EditFloorPlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import FloorPlanObject.ObjectType;
import GUI.Component.SimpleButton;
import GUI.Component.SimpleSelectButton;
import GUI.Component.SimpleSelectImageButton;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.DimenR;

public class ToolsPanel extends JPanel {
	/** For get context */
	private EditFloorPlanFrame context;
	/** Tool's option panel */
	private ToolOptionPanel optionPanel;
	
	/** Buttons for object list */
	private ArrayList<SimpleSelectImageButton> buttons;
	
	private int yBorder = 20;
	
	public ToolsPanel(EditFloorPlanFrame context, ToolOptionPanel optionPanel, int width, int height) {
		super();
		this.setLayout(null);
		this.context = context;
		this.optionPanel = optionPanel;
		
		buttons = new ArrayList<SimpleSelectImageButton>();
		
		
		ArrayList<ImageIcon> icons = new ArrayList<ImageIcon>();
		icons.add(new ImageIcon("src/Image/line_button_base.png"));
		icons.add(new ImageIcon("src/Image/line_button_pressed.png"));
		icons.add(new ImageIcon("src/Image/icon_button_base.png"));
		icons.add(new ImageIcon("src/Image/icon_button_pressed.png"));
		icons.add(new ImageIcon("src/Image/oval_button_base.png"));
		icons.add(new ImageIcon("src/Image/oval_button_pressed.png"));
		icons.add(new ImageIcon("src/Image/rectangle_button_base.png"));
		icons.add(new ImageIcon("src/Image/rectangle_button_pressed.png"));
		icons.add(new ImageIcon("src/Image/tag_button_base.png"));
		icons.add(new ImageIcon("src/Image/tag_button_pressed.png"));
		icons.add(new ImageIcon("src/Image/select_button_base.png"));
		icons.add(new ImageIcon("src/Image/select_button_pressed.png"));
		
		int endLocation = 0;
		
		for(int i = 0; i < ObjectType.values().length; i++) {
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
			ActionListener typeChangeListener = new ActionListener() {
			    public void actionPerformed(ActionEvent e) {
			    	DataProvider.getInstance().setCurrentObjectType(
			    			ObjectType.values()[temp]);
	    			context.getEditViewPanel().repaint();
			    }
		    };
			
		    ActionListener toolOptionListener = null;
		    switch(ObjectType.values()[i]) {
		    case LINE:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawLineOption();
				    }
			    };
		    	break;

		    case ICON:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawIconOption();
				    }
			    };
		    	break;

		    case OVAL:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawOvalOption();
				    }
			    };
		    	break;

		    case RECTANGLE:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawRectOption();
				    }
			    };
		    	break;

		    case TAG:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawTagOption();
				    }
			    };
		    	break;

		    case SELECT:
		    	toolOptionListener = new ActionListener() {
				    public void actionPerformed(ActionEvent e) {
				    	optionPanel.drawSelectOption();
				    }
			    };
		    	break;
		    }
			
			buttons.get(i).addActionListener(typeChangeListener);
			buttons.get(i).addActionListener(toolOptionListener);
			
			
			
			buttons.get(i).setLocation(
					0,
					yBorder * (i + 1) + DimenR.BUTTON_HEIGHT * i
					);
			
			endLocation = buttons.get(i).getLocation().y + buttons.get(i).getHeight() + yBorder;
			
			this.add(buttons.get(i));
		}
		

		this.setBackground(ColorR.WHITE);
		this.setBorder(null);
		if(endLocation > height) {
			this.setSize(new Dimension(width, endLocation));
			this.setPreferredSize(new Dimension(width, endLocation));
		} else {
			this.setSize(new Dimension(width, height));
			this.setPreferredSize(new Dimension(width, height));
		}
	}
}
