package GUI.Frame.EditFloorPlan;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

import FloorPlan.FloorPlan;
import FloorPlanObject.FPObject;
import FloorPlanObject.Icon;
import FloorPlanObject.IconType;
import FloorPlanObject.Line;
import FloorPlanObject.ObjectType;
import FloorPlanObject.Oval;
import FloorPlanObject.Rectangle;
import FloorPlanObject.Tag;
import FloorPlanObject.VertexType;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.DimenR;

public class EditViewPanel extends JPanel implements MouseListener, MouseMotionListener, MouseWheelListener, KeyListener {
	
	/** For approach parent frame */
	EditFloorPlanFrame context;
	
	/** For approach current floor plan */
	private FloorPlan floorPlan;
	
	/** New object to insert floor plan */
	private FPObject tempObject;
	
	/** Decide drawing or not */
	private boolean drawing = false;
	/** Decide dragging panel or not */
	private boolean dragging = false;
	/** Decide drag or not */
	private boolean choosePanelOn = false;
	/** Draw object in orthogonal */
	private boolean orthoMode = false;
	
	/** Mouse point adjust by offset */
	Point driftPoint;
	
	/** X position of scroll offset */
	private int scrollOffsetX;
	/** Y position of scroll offset */
	private int scrollOffsetY;
	/** X position of current scroll offset */
	private int currentScrollOffsetX;
	/** Y position of current scroll offset */
	private int currentScrollOffsetY;
	/** Start X position of scroll */
	private int scrollStartX;
	/** Start Y position of scroll */
	private int scrollStartY;

	/** Start X position of drag */
	private int dragStartX;
	/** Start Y position of drag */
	private int dragStartY;

	/** Scale offset */
	private double scaleOffset;

	/** Current list of objects */
	private ArrayList<FPObject> objects;
	/** Current intersect objects */
	private ArrayList<FPObject> intersectObjects;
	/** Current intersect object's vertex types */
	private ArrayList<VertexType> intersectObjectTypes;
	/** Intersect Object Choose panel */
	private IntersectObjectChoosePanel intersectPanel;
	
	public EditViewPanel(EditFloorPlanFrame context, int width, int height, FloorPlan floorPlan) {
		super();
		this.floorPlan = floorPlan;
		this.context = context;
		this.setLayout(null);
		
		dragStartX = 0;
		dragStartY = 0;
		
		scrollOffsetX = 0;
		scrollOffsetY = 0;
		
		scaleOffset = 1;
		
		driftPoint = new Point();
		
		//objects = new ArrayList<FPObject>();
		intersectObjects = new ArrayList<FPObject>();
		intersectObjectTypes = new ArrayList<VertexType>();
		
		DataProvider.getInstance().parsingObject(DataProvider.getInstance().loadFromServer(1));
		objects = DataProvider.getInstance().getObjects();
		
		context.getTitleBar().getScaleLabel().setText("" + scaleOffset);
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		this.addMouseWheelListener(this);
		this.addKeyListener(this);

		this.setBackground(Color.WHITE);
		this.setBorder(BorderFactory.createLineBorder(ColorR.GRAY, 1));
		this.setPreferredSize(new Dimension(width, height));
		this.setSize(new Dimension(width, height));
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		setDriftPoint(e);
		
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			if(DataProvider.getInstance().getCurrentObjectType() != null &&
				DataProvider.getInstance().getCurrentObjectType() != ObjectType.SELECT &&
				DataProvider.getInstance().getCurrentObjectType() != ObjectType.ICON &&
				DataProvider.getInstance().getCurrentObjectType() != ObjectType.TAG)
				objects.remove(objects.size() - 1);

			if(DataProvider.getInstance().getCurrentObjectType() == ObjectType.SELECT) {
				if(intersectPanel != null) {
					intersectPanel.removeAll();
					remove(intersectPanel);
				}
				
				if(intersectObjects.size() >= 1) {
					intersectPanel = new IntersectObjectChoosePanel(objects, intersectObjects, intersectObjectTypes, this);
					intersectPanel.setLocation(e.getPoint());
					choosePanelOn = true;
					add(intersectPanel);
				} else
					choosePanelOn = false;
			}
			break;
		}
		repaint();
	}

	/**
	 * When press mouse, set start position of tempObject and
	 * make new tempObject of appropriate type
	 * @param e MouseEvent
	 * @author DongKyu
	 */
	@Override
	public void mousePressed(MouseEvent e) {
		setDriftPoint(e);
		
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			Point attach = null;
			if(!choosePanelOn) {
				intersectObjects.clear();
				intersectObjectTypes.clear();
			}
			
			if(intersectPanel != null) {
				intersectPanel.setSelectedObjects();
				intersectPanel.removeAll();
				remove(intersectPanel);
			}
			
			for(FPObject object : objects) // Check is attached or not
				if(driftPoint != null && object.getIntersectVertex(driftPoint) != null) {
					attach = new Point(object.getIntersectPosition(object.getIntersectVertex(driftPoint)));
					if(!choosePanelOn) {
						intersectObjects.add(object);
						intersectObjectTypes.add(object.getIntersectVertex(driftPoint));
					}
				}
			
			if(DataProvider.getInstance().getCurrentObjectType() != null)
				switch(DataProvider.getInstance().getCurrentObjectType()) {
				case ICON:
					if(DataProvider.getInstance().getCurrentIconType() != null) {
						if(attach == null)
							tempObject = new Icon(driftPoint);
						else
							tempObject = new Icon(attach);
						((Icon)tempObject).setIconType(DataProvider.getInstance().getCurrentIconType());
						objects.add(tempObject);
						setDrawing(true);
					}
					break;
					
				case LINE:
					if(attach == null)
						tempObject = new Line(driftPoint);
					else
						tempObject = new Line(attach);
					objects.add(tempObject);
					setDrawing(true);
					break;
					
				case OVAL:
					if(attach == null)
						tempObject = new Oval(driftPoint);
					else
						tempObject = new Oval(attach);
					objects.add(tempObject);
					setDrawing(true);
					break;
					
				case RECTANGLE:
					if(attach == null)
						tempObject = new Rectangle(driftPoint);
					else
						tempObject = new Rectangle(attach);
					objects.add(tempObject);
					setDrawing(true);
					break;
					
				case TAG:
					System.out.println("TTTTAAGG");
					if(attach == null)
						tempObject = new Tag(driftPoint);
					else
						tempObject = new Tag(attach);
					System.out.println("TTTTAAGG" + objects.size());
					((Tag)tempObject).setTaged(true);
					objects.add(tempObject);
					System.out.println("TTTTAAGG" + objects.size());
					setDrawing(true);
					break;
	
				case SELECT:
					dragStartX = driftPoint.x;
					dragStartY = driftPoint.y;
					setDrawing(true);
					break;
				}
			
			if(tempObject != null)
				tempObject.setColor(ColorR.DARK_GRAY);
			break;
			
		case MouseEvent.BUTTON3:
			dragging = true;
			scrollStartX = e.getX();
			scrollStartY = e.getY();
			break;
		}
	}

	/**
	 * When release mouse, set end position of tempObject and
	 * add tempObject to object list
	 * @param e MouseEvent
	 * @author DongKyu
	 */
	@Override
	public void mouseReleased(MouseEvent e) {
		setDriftPoint(e);
		
		switch(e.getButton()) {
		case MouseEvent.BUTTON1:
			setDrawing(false);
			Point attach = null;
			
			for(FPObject object : objects) { // Check is attached or not
				VertexType tempType = object.getIntersectVertex(driftPoint);
				
				if(tempType != null) {
					attach = object.getIntersectPosition(tempType);
					break;
				}
			}
			
			if(tempObject != null && DataProvider.getInstance().getCurrentObjectType() != null)
				switch(DataProvider.getInstance().getCurrentObjectType()) {
				case ICON:
				case TAG:
					tempObject.setEndPosition(tempObject.getStartPosition());
					break;
					
				case LINE:
				case OVAL:
				case RECTANGLE:
					if(!orthoMode)
						if(attach == null)
							tempObject.setEndPosition(driftPoint);
						else
							tempObject.setEndPosition(attach);
					break;
				}
			
			if(!choosePanelOn && attach != null && DataProvider.getInstance().getCurrentObjectType() == ObjectType.SELECT)
				for(int i = 0; i < intersectObjects.size(); i++) {
					dragObject(intersectObjects.get(i), intersectObjectTypes.get(i), attach);
				}
	
			if(choosePanelOn) {
				intersectObjects.clear();
				intersectObjectTypes.clear();
				choosePanelOn = false;
			}
	
			tempObject = null;
			break;
			
		case MouseEvent.BUTTON3:
			dragging = false;
			scrollStartX = 0;
			scrollStartY = 0;
			
			scrollOffsetX += currentScrollOffsetX;
			scrollOffsetY += currentScrollOffsetY;
			
			currentScrollOffsetX = 0;
			currentScrollOffsetY = 0;
			
			break;
		}
		repaint();
	}

	/**
	 * During dragging, continuously get cursor position and repaint panel
	 * */
	@Override
	public void mouseDragged(MouseEvent e) {
		setDriftPoint(e);
		context.setEditViewFocus();
		
		if (drawing && DataProvider.getInstance().getCurrentObjectType() != null) { // When Drawing
			switch(DataProvider.getInstance().getCurrentObjectType()) {
			case LINE:
				if(orthoMode) {
					if(Math.abs(tempObject.getStartPosition().x - driftPoint.getX()) >
					Math.abs(tempObject.getStartPosition().y - driftPoint.getY()))
						tempObject.setEndPosition(
								new Point(
										(int)driftPoint.getX(),
										tempObject.getStartPosition().y)
								);
					else
						tempObject.setEndPosition(
								new Point(
										tempObject.getStartPosition().x,
										(int)driftPoint.getY())
								);
				} else
					tempObject.setEndPosition(driftPoint);
				break;
				
			case OVAL:
			case RECTANGLE:
				if(orthoMode) {
					if(Math.abs(tempObject.getStartPosition().x - driftPoint.getX()) >
					Math.abs(tempObject.getStartPosition().y - driftPoint.getY()))
						tempObject.setEndPosition(
								new Point(
										(int)driftPoint.getX(),
										(int)(tempObject.getStartPosition().y - (tempObject.getStartPosition().x - driftPoint.getX())))
								);		
					else
						tempObject.setEndPosition(
								new Point(
										(int)(tempObject.getStartPosition().x - (tempObject.getStartPosition().y - driftPoint.getY())),
										(int)driftPoint.getY())
								);
				} else
					tempObject.setEndPosition(driftPoint);
				break;
				
			case ICON:
			case TAG:
				break;
				
			case SELECT:
				if(intersectObjects.size() >= 1) {
					if(orthoMode){
						if(Math.abs(driftPoint.x - dragStartX) < Math.abs(driftPoint.y - dragStartY))
							for(int i = 0; i < intersectObjects.size(); i++)
								dragObject(
										intersectObjects.get(i),
										intersectObjectTypes.get(i),
										new Point(
												dragStartX,
												driftPoint.y
												)
										);
						else
							for(int i = 0; i < intersectObjects.size(); i++)
								dragObject(
										intersectObjects.get(i),
										intersectObjectTypes.get(i),
										new Point(
												driftPoint.x,
												dragStartY
												)
										);
					} else {
						for(int i = 0; i < intersectObjects.size(); i++)
							dragObject(intersectObjects.get(i), intersectObjectTypes.get(i), driftPoint);
					}
				}
				break;
			}
		} else if(dragging) { // When dragging
			currentScrollOffsetX = (e.getX()) - scrollStartX;
			currentScrollOffsetY = (e.getY()) - scrollStartY;
		}
			
		repaint();
	}
	
	/**
	 * Function for resize
	 * @author DongKyu
	 */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		scaleOffset *= (1 + (double)e.getWheelRotation() / 15);
		
		if(scaleOffset <= 0.3)
			scaleOffset = 0.3;
		else if(scaleOffset >= 5)
			scaleOffset = 5;
		
		context.getTitleBar().getScaleLabel().setText(scaleOffset + "");
		repaint();
	}
	
	/**
	 * Draw objects
	 * @param Graphics
	 * @author DongKyu
	 * */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		DataProvider.getInstance().setObjects(objects);
		
		context.getTitleBar().getScaleLabel().setText(driftPoint.getX() + " " + driftPoint.getY());
		
        ((Graphics2D)g).setRenderingHint(
        		RenderingHints.KEY_ANTIALIASING,
        		RenderingHints.VALUE_ANTIALIAS_ON
        		);
		
        /** Draw all objects, If object intersect to mouse position
         *  Colored to object
         *  */
		for(FPObject object : objects) {
			if(driftPoint != null && object.getIntersectVertex(driftPoint) != null) {
				g.setColor(ColorR.ORANGE);
			} else
				g.setColor(object.getColor());
			
			if(object.getEndPosition() != null) {
				System.out.println(object.getType());
				drawObject(
						object,
						g,
						object.getType(),
						object.getStartPosition(),
						object.getEndPosition()
						);
			}
		}
	}
	
	/**
	 * Draw object
	 * Line - Draw line at start position to end position.
	 * Oval - Draw oval, start position is center position and end position is radius of oval.
	 * Rectangle - Draw rectangle, start position is center position and end position is size of rectangle.
	 * @param object
	 * @param g Graphics
	 * @param type Object Type
	 * @param start Start position (Point)
	 * @param end End position (Point)
	 * @author DongKyu
	 * */
	public void drawObject(FPObject object, Graphics g, ObjectType type, Point start, Point end) {
		int offsetX = scrollOffsetX + currentScrollOffsetX;
		int offsetY = scrollOffsetY + currentScrollOffsetY;
		
		switch(type) {
		case ICON:
			g.drawImage(
					DataProvider.getInstance().getIconImage(
						((Icon)object).getIconType().getInt()
					),
					(int)((start.x - DimenR.ICON_RADIUS + offsetX) * scaleOffset),
					(int)((start.y - DimenR.ICON_RADIUS + offsetY) * scaleOffset),
					(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
					(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
					this);
			
			if (DataProvider.getInstance().getCurrentObjectType().equals(ObjectType.SELECT)) {
				g.drawOval(
						(int)((start.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((start.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
			}
			break;
			
		case LINE:
			g.drawLine(
					(int)((start.x + offsetX) * scaleOffset),
					(int)((start.y + offsetY) * scaleOffset),
					(int)((end.x + offsetX) * scaleOffset),
					(int)((end.y + offsetY) * scaleOffset)
					);
			if (DataProvider.getInstance().getCurrentObjectType().equals(ObjectType.SELECT)) {
				g.drawOval(
						(int)((start.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((start.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
				
				g.drawOval(
						(int)((end.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((end.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
			}
			break;
			
		case OVAL:
			g.drawOval(
					(int)((start.x - Math.abs(end.x - start.x) + offsetX) * scaleOffset),
					(int)((start.y - Math.abs(end.y - start.y) + offsetY) * scaleOffset),
					(int)((Math.abs(start.x - end.x) * 2) * scaleOffset),
					(int)((Math.abs(start.y - end.y) * 2) * scaleOffset)
					);

			if (DataProvider.getInstance().getCurrentObjectType().equals(ObjectType.SELECT)) {
				g.drawOval(
						(int)((start.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((start.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
				
				g.drawOval(
						(int)((end.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((end.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
			}
			break;
			
		case RECTANGLE:
			g.drawRect(
					(int)((start.x - Math.abs(end.x - start.x) + offsetX) * scaleOffset),
					(int)((start.y - Math.abs(end.y - start.y) + offsetY) * scaleOffset),
					(int)((Math.abs(start.x - end.x) * 2) * scaleOffset),
					(int)((Math.abs(start.y - end.y) * 2) * scaleOffset)
					);
			

			if (DataProvider.getInstance().getCurrentObjectType().equals(ObjectType.SELECT)) {
				g.drawOval(
						(int)((start.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((start.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
				
				g.drawOval(
						(int)((end.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((end.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
			}
			break;
			
		case TAG:
			System.out.println("Draw TAG");
			if(((Tag)object).isTaged())
				g.drawImage(
						DataProvider.getInstance().getIconImage(7),
						(int)((start.x - DimenR.ICON_RADIUS + offsetX) * scaleOffset),
						(int)((start.y - DimenR.ICON_RADIUS + offsetY) * scaleOffset),
						(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
						(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
						this);
			else
				g.drawImage(
						DataProvider.getInstance().getIconImage(8),
						(int)((start.x - DimenR.ICON_RADIUS + offsetX) * scaleOffset),
						(int)((start.y - DimenR.ICON_RADIUS + offsetY) * scaleOffset),
						(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
						(int)((DimenR.ICON_RADIUS * 2) * scaleOffset),
						this);
			
			if (DataProvider.getInstance().getCurrentObjectType().equals(ObjectType.SELECT)) {
				g.drawOval(
						(int)((start.x - Math.abs(DimenR.VERTEX_RADIUS) + offsetX) * scaleOffset),
						(int)((start.y - Math.abs(DimenR.VERTEX_RADIUS) + offsetY) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset),
						(int)((Math.abs(DimenR.VERTEX_RADIUS) * 2) * scaleOffset)
						);
			}
			break;
		}
	}
	
	/**
	 * Drag object to end point
	 * @param object
	 * @param vType
	 * @param end
	 * @author DongKyu
	 */
	public void dragObject(FPObject object, VertexType vType, Point end) {
		if(object.getType() == ObjectType.LINE) {
			switch(vType) {
			case START:
				object.setStartPosition(end);
				break;
				
			case END:
				object.setEndPosition(end);
				break;
			}
		} else {
			Point gap = new Point(
					object.getStartPosition().x - object.getEndPosition().x,
					object.getStartPosition().y - object.getEndPosition().y
					);
			
			switch(vType) {
			case START:
				object.setStartPosition(end);
				object.setEndPosition(new Point(end.x - gap.x, end.y - gap.y));
				break;
				
			case END:
				object.setEndPosition(end);
				break;
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		setDriftPoint(e);
		repaint();
	}
	
	public void setDriftPoint(MouseEvent e) {
		driftPoint = new Point(
				(int)(e.getX() / scaleOffset - scrollOffsetX),
				(int)(e.getY() / scaleOffset - scrollOffsetY)
				);
	}

	public boolean isDrawing() {
		return drawing;
	}

	public void setDrawing(boolean drawing) {
		this.drawing = drawing;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			orthoMode = true;
			break;
		
		case KeyEvent.VK_CONTROL:
			break;
		
		case KeyEvent.VK_ALT:
			break;
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		switch(arg0.getKeyCode()) {
		case KeyEvent.VK_SHIFT:
			orthoMode = false;
			break;
		
		case KeyEvent.VK_CONTROL:
			break;
		
		case KeyEvent.VK_ALT:
			break;
		}
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
	}
	
	public boolean isChoosePanelOn() {
		return choosePanelOn;
	}

	public void setChoosePanelOn(boolean choosePanelOn) {
		this.choosePanelOn = choosePanelOn;
	}
}
