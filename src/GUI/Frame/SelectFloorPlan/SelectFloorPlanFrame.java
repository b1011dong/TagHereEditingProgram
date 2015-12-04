package GUI.Frame.SelectFloorPlan;

import java.awt.Point;

import javax.swing.BorderFactory;

import FloorPlan.Building;
import GUI.Component.SimpleJFrame;
import GUI.Component.SimpleScrollPane;
import GUI.Component.TitleBarPanel;
import Resource.ColorR;

/**
 * JFrame for show floor plan list and preview
 * @author DongKyu
 * @since 11.07.15
 */
public class SelectFloorPlanFrame extends SimpleJFrame{
	
	/** Title bar panel */
	private TitleBarPanel titleBar;
	/** JPanel for search floor plan */
	private SearchPanel searchPanel;
	/** For floor plan list scroll */
	private SimpleScrollPane searchScroll;
	/** PreviewPanel for show building preview */
	private PreviewPanel previewPanel;
	
	/** Building for get select building data */
	private Building building;
	
	private int xBorder = 20;
	private int yBorder = 20;
	private int scrollBarBorder = 10;

	public SelectFloorPlanFrame(String frameName, int width, int height) {
		super(frameName, width, height);
		
		titleBar = new TitleBarPanel(this, frameName, width);
		searchPanel = new SearchPanel(this, width / 3, height);
		searchScroll = new SimpleScrollPane(searchPanel);
		
		searchScroll.setLocation(0, titleBar.getHeight());
		searchScroll.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, ColorR.GRAY));
		searchScroll.setSize(
				searchPanel.getWidth() + scrollBarBorder,
				this.HEIGHT - titleBar.getHeight()
				);
		
		updatePreviewPanel();
		
		this.add(titleBar);
		this.add(searchScroll);
		this.getRootPane().setBorder(BorderFactory.createLineBorder(ColorR.GRAY, 2));
	}
	
	/** Refresh preview panel */
	public void updatePreviewPanel() {
		building = new Building(
				"AAAA",
				new Point(100, 100),
				"This is building asd fasfdasfddasfa sdfasdfasfadsfasdfasdfasdfads f asdf asdf asfd asf"
				+ "a sdfasd fasd fsdfasfd asdf asdf asf asf asdf asdf asdf asfasdf asdf asdf asdf asf as"
				+ "d fasfasdfasfa sdfasfasdfadas"
				);// TODO: DataProvider.getInstance().getBuilding(); <= Need to change
		
		if(previewPanel != null) {
			previewPanel.removeAll();
			this.remove(previewPanel);
		}
		
		previewPanel = new PreviewPanel(
				this,
				WIDTH * 2 / 3 - xBorder * 2 - scrollBarBorder,
				HEIGHT - titleBar.getHeight() - yBorder * 2,
				building
				);
		
		previewPanel.setLocation(
				searchScroll.getWidth() + xBorder,
				titleBar.getHeight() + yBorder
				);
		
		add(previewPanel);
	}
}
