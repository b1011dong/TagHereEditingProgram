package GUI.Frame.EditFloorPlan;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import FloorPlanObject.FPObject;
import FloorPlanObject.Icon;
import FloorPlanObject.Line;
import FloorPlanObject.Oval;
import FloorPlanObject.Rectangle;
import FloorPlanObject.VertexType;
import GUI.Component.SimpleButton;
import GUI.Component.SimpleImageButton;
import GUI.Component.SimpleToggleButton;
import GUI.Component.SimpleToggleImageButton;
import Resource.ColorR;
import Resource.DimenR;
import Resource.StringR;

public class IntersectObjectChoosePanel extends JPanel{
	private EditViewPanel context;
	
	/** Button for delete selected object */
	private SimpleImageButton deleteButton;
	/** Button for copy selected object */
	private SimpleImageButton copyButton;
	
	/** Buttons for indicate objects */
	private ArrayList<SimpleToggleImageButton> intersectButtons;
	/** Object list for get intersect objects */
	private ArrayList<FPObject> intersectObjects;
	/** Object list for get intersect object's vertex type */
	private ArrayList<VertexType> intersectObjectTypes;
	
	public IntersectObjectChoosePanel(ArrayList<FPObject> objects, ArrayList<FPObject> intersectObjects, ArrayList<VertexType> intersectObjectTypes, EditViewPanel Context) {
		setLayout(null);
		intersectButtons = new ArrayList<SimpleToggleImageButton>();
		this.intersectObjects = intersectObjects;
		this.intersectObjectTypes = intersectObjectTypes;
		context = Context;
		
		deleteButton = new SimpleImageButton(
				new ImageIcon("src/Image/delete_button_base.png"),
				new ImageIcon("src/Image/delete_button_pressed.png"),
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT
				);
		
		deleteButton.setLocation(
				DimenR.BUTTON_BORDER,
				DimenR.BUTTON_BORDER
				);
		
		deleteButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < intersectButtons.size(); i++)
					if(intersectButtons.get(i).isSelected()) {
						objects.remove(intersectObjects.get(i));
					}
				setSelectedObjects();
				destroy();
			}
		});
		
		add(deleteButton);
		
		copyButton = new SimpleImageButton(
				new ImageIcon("src/Image/copy_icon.png"),
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT,
				DimenR.BUTTON_HEIGHT
				);
		
		copyButton.setLocation(
				DimenR.BUTTON_BORDER,
				DimenR.BUTTON_BORDER * 2 + DimenR.BUTTON_HEIGHT
				);
		
		copyButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				for(int i = 0; i < intersectButtons.size(); i++)
					if(intersectButtons.get(i).isSelected()) {
						FPObject tempObject = null;
						switch(intersectObjects.get(i).getType()) {
						case LINE:
							tempObject = new Line(
									intersectObjects.get(i).getStartPosition().x + DimenR.DUPLICATE_GAP,
									intersectObjects.get(i).getStartPosition().y - DimenR.DUPLICATE_GAP
									);
							tempObject.setEndPosition(
									new Point(
										intersectObjects.get(i).getEndPosition().x + DimenR.DUPLICATE_GAP,
										intersectObjects.get(i).getEndPosition().y - DimenR.DUPLICATE_GAP
									));
							tempObject.setColor(intersectObjects.get(i).getColor());
							((Line)tempObject).setThickness(((Line)intersectObjects.get(i)).getThickness());
							((Line)tempObject).setLineType(((Line)intersectObjects.get(i)).getLineType());
							break;
						
						case ICON:
							tempObject = new Icon(
									intersectObjects.get(i).getStartPosition().x + DimenR.DUPLICATE_GAP,
									intersectObjects.get(i).getStartPosition().y - DimenR.DUPLICATE_GAP
									);
							tempObject.setColor(intersectObjects.get(i).getColor());
							((Icon)tempObject).setIconType(((Icon)intersectObjects.get(i)).getIconType());
							((Icon)tempObject).setColor(((Icon)intersectObjects.get(i)).getColor());
							break;

						case OVAL:
							tempObject = new Oval(
									intersectObjects.get(i).getStartPosition().x + DimenR.DUPLICATE_GAP,
									intersectObjects.get(i).getStartPosition().y - DimenR.DUPLICATE_GAP
									);
							tempObject.setEndPosition(
									new Point(
											intersectObjects.get(i).getEndPosition().x + DimenR.DUPLICATE_GAP,
											intersectObjects.get(i).getEndPosition().y - DimenR.DUPLICATE_GAP
										));
							tempObject.setColor(intersectObjects.get(i).getColor());
							((Oval)tempObject).setThickness(((Oval)intersectObjects.get(i)).getThickness());
							((Oval)tempObject).setType(((Oval)intersectObjects.get(i)).getLineType());
							break;
						
						case RECTANGLE:
							tempObject = new Rectangle(
									intersectObjects.get(i).getStartPosition().x + DimenR.DUPLICATE_GAP,
									intersectObjects.get(i).getStartPosition().y - DimenR.DUPLICATE_GAP
									);
							tempObject.setEndPosition(
									new Point(
											intersectObjects.get(i).getEndPosition().x + DimenR.DUPLICATE_GAP,
											intersectObjects.get(i).getEndPosition().y - DimenR.DUPLICATE_GAP
										));
							tempObject.setColor(intersectObjects.get(i).getColor());
							((Rectangle)tempObject).setThickness(((Rectangle)intersectObjects.get(i)).getThickness());
							((Rectangle)tempObject).setType(((Rectangle)intersectObjects.get(i)).getLineType());
							break;
						
						case TAG:
							break;
						}
						
						objects.add(tempObject);
					}
				intersectObjects.clear();
				intersectObjectTypes.clear();
				context.setChoosePanelOn(false);
				destroy();
			}
		});
		
		add(copyButton);
		
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
		
		for(int i = 0; i < intersectObjects.size(); i++) {
			ImageIcon baseIcon = icons.get(intersectObjects.get(i).getType().getInt() * 2);
			ImageIcon pressedIcon = icons.get(intersectObjects.get(i).getType().getInt() * 2 + 1);
			
			SimpleToggleImageButton temp = new SimpleToggleImageButton(
					baseIcon,
					pressedIcon,
					DimenR.BUTTON_HEIGHT,
					DimenR.BUTTON_HEIGHT,
					DimenR.BUTTON_HEIGHT,
					DimenR.BUTTON_HEIGHT
					);
			
			temp.setSelected();
			
			if(i == 0)
				temp.setLocation(
						DimenR.BUTTON_BORDER,
						DimenR.BUTTON_BORDER * 3 + DimenR.BUTTON_HEIGHT * 2
						);
			else
				temp.setLocation(
						DimenR.BUTTON_BORDER,
						intersectButtons.get(i - 1).getLocation().y + DimenR.BUTTON_HEIGHT + DimenR.BUTTON_BORDER
						);
			
			add(temp);
			intersectButtons.add(temp);
			temp.addMouseListener(new ButtonMouseAdapter(intersectButtons.indexOf(temp), intersectObjects.get(i).getColor()));
		}

		this.setBackground(ColorR.WHITE);
		this.setBorder(BorderFactory.createLineBorder(ColorR.DARK_GRAY, 1));
		if(intersectObjects.size() != 0) {
			this.setPreferredSize(
					new Dimension(DimenR.BUTTON_HEIGHT + DimenR.BUTTON_BORDER * 2,
					intersectButtons.get(intersectObjects.size() - 1).getLocation().y + DimenR.BUTTON_BORDER + DimenR.BUTTON_HEIGHT)
					);
			this.setSize(
					new Dimension(DimenR.BUTTON_HEIGHT + DimenR.BUTTON_BORDER * 2,
					intersectButtons.get(intersectObjects.size() - 1).getLocation().y + DimenR.BUTTON_BORDER + DimenR.BUTTON_HEIGHT)
					);
		} else {
			this.setPreferredSize(new Dimension(0, 0));
			this.setSize(0, 0);
		}
	}
	
	public void setSelectedObjects() {
		for(int i = 0; i < intersectObjects.size(); i++)
			if(!intersectButtons.get(i).isSelected()) {
				intersectObjects.remove(i);
				intersectObjectTypes.remove(i);
				intersectButtons.remove(i);
				i--;
			}
	}
	
	public void destroy() {
		context.repaint();
		context.remove(this);
	}
	
	private class ButtonMouseAdapter extends MouseAdapter {
		private int index;
		private Color originalColor;
		
		public ButtonMouseAdapter(int index, Color color) {
			this.index = index;
			originalColor = color;
		}
		
		public void mouseClicked(MouseEvent event) {
	        context.repaint();
		}
		
		public void mouseEntered(MouseEvent event) {
			intersectObjects.get(index).setColor(ColorR.ORANGE);
	        context.repaint();
	    }
		
		public void mouseExited(MouseEvent event) {
			intersectObjects.get(index).setColor(originalColor);
	        context.repaint();
	    }
	}
}
