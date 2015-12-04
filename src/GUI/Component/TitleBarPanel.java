package GUI.Component;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import GUI.Frame.EditFloorPlan.EditFloorPlanFrame;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.DimenR;
import Resource.StringR;

import FloorPlanObject.*;

/**
 * JPanel for title bar
 * @author DongKyu
 *
 */
public class TitleBarPanel extends JPanel implements ActionListener{
	
	/** Label for show title text */
	private SimpleLabel titleLabel;
	/** Label for show user id */
	private SimpleLabel idLabel;
	/** Label for show scale */
	private SimpleLabel scaleLabel;
	/** JFrame for approach parent frame */
	private SimpleJFrame context;
	/** Button for save floor plan */
	private SimpleButton saveButton;
	
	/** String for title bar text */
	String titleText;
	
	private int xBorder = 20;
	
	public TitleBarPanel(SimpleJFrame context, String text, int width) {
		titleText = text;
		this.context = context;
		this.setLayout(null);
		
		titleLabel = new SimpleLabel(text);
		titleLabel.setSize(width, DimenR.TITLE_BAR_HEIGHT);
		titleLabel.setHorizontalAlignment(SwingConstants.LEFT);
		titleLabel.setForeground(ColorR.TITLE_COLOR);
		titleLabel.setLocation(xBorder, 0);
		
		idLabel = new SimpleLabel(DataProvider.getInstance().getId());
		idLabel.setSize(width, DimenR.TITLE_BAR_HEIGHT);
		idLabel.setHorizontalAlignment(SwingConstants.LEFT);
		idLabel.setForeground(ColorR.TITLE_COLOR);
		idLabel.setLocation(width * 5 / 6, 0);
		
		addDecoration();
		
		this.add(titleLabel);
		this.add(idLabel);
		
		this.setBackground(ColorR.TITLE_BAR_COLOR);
		this.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, ColorR.GRAY));
		this.setPreferredSize(new Dimension(width, DimenR.TITLE_BAR_HEIGHT));
		this.setSize(width, DimenR.TITLE_BAR_HEIGHT);
	}
	
	/**
	 * Function for add decoration in this panel
	 * for various parent frames
	 * */
	public void addDecoration() {
		switch(context.toString()) {
		case StringR.SELECT_BUILDING:
			break;
			
		case StringR.EDIT_FLOOR_PLAN:
			saveButton = new SimpleButton(StringR.SAVE);
			saveButton.setSize(
					DimenR.BUTTON_WIDTH,
					DimenR.BUTTON_HEIGHT
					);
			saveButton.setLocation(
					idLabel.getLocation().x - saveButton.getWidth() - xBorder,
					0
					);
			
			scaleLabel = new SimpleLabel("0");
			scaleLabel.setSize(
					DimenR.BUTTON_WIDTH * 2,
					DimenR.BUTTON_HEIGHT
					);
			scaleLabel.setLocation(
					saveButton.getLocation().x - saveButton.getWidth() * 3 - xBorder,
					0
					);
			
			saveButton.addActionListener(this);
			
			this.add(saveButton);
			this.add(scaleLabel);
			
			break;
		}
	}

	public SimpleLabel getScaleLabel() {
		return scaleLabel;
	}

	public void setScaleLabel(SimpleLabel scaleLabel) {
		this.scaleLabel = scaleLabel;
	}

	public SimpleButton getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(SimpleButton saveButton) {
		this.saveButton = saveButton;
	}
	
	public void getObjectsCode() {
		System.out.println(DataProvider.getInstance().getObjects().size());
		for(int i = 0; i < DataProvider.getInstance().getObjects().size(); i++)
		{
			System.out.print(DataProvider.getInstance().getObjects().get(i).getType().getInt()+":");
			System.out.print(DataProvider.getInstance().getObjects().get(i).getStartPosition().x+":");
			System.out.print(DataProvider.getInstance().getObjects().get(i).getStartPosition().y+":");
			System.out.print(DataProvider.getInstance().getObjects().get(i).getEndPosition().x+":");
			System.out.print(DataProvider.getInstance().getObjects().get(i).getEndPosition().y+":");
			if(DataProvider.getInstance().getObjects().get(i).getType() == ObjectType.ICON) {
				System.out.print(((Icon)DataProvider.getInstance().getObjects().get(i)).getIconType().getInt()+":");
			}
			else if(DataProvider.getInstance().getObjects().get(i).getType() == ObjectType.TAG) {
				System.out.print(((Tag)DataProvider.getInstance().getObjects().get(i)).getKey()+":");
			}
			else
				System.out.print("0"+":");
		}

	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		switch(arg0.getSource().toString()) {
		case StringR.SAVE:
			//DataProvider.getInstance().saveToServer(1, DataProvider.getInstance().getObjects());
			getObjectsCode();
			break;
		}
		
	}
}
