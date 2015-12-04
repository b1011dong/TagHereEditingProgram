package GUI.Frame.Login;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import GUI.Component.*;
import GUI.Frame.SelectFloorPlan.SelectFloorPlanFrame;
import Resource.ColorR;
import Resource.DataProvider;
import Resource.DimenR;
import Resource.StringR;

/**
 * JFrame for Login
 * @author DongKyu
 * @since 11.07.15
 */

public class LoginFrame extends SimpleJFrame{
	
	/** Text Field for input ID */
	private SimpleTextField idField;
	/** Text Field for input Password */
	private SimplePasswordField passwordField;

	/** Button for login */
	private SimpleButton loginButton;
	/** Button for exit */
	private SimpleButton exitButton;
	
	private int xBorder = 10;
	private int yBorder = 10;
	

	public LoginFrame(String frameName, int width, int height) {
		super(frameName, width, height);

		idField = new SimpleTextField(StringR.ID);
		idField.setBackground(ColorR.LIGHT_GRAY);
		idField.setSize(WIDTH - xBorder * 2, HEIGHT / 4);
		idField.setLocation(xBorder, yBorder * 2);

		passwordField = new SimplePasswordField(StringR.PASSWORD);
		passwordField.setBackground(ColorR.LIGHT_GRAY);
		passwordField.setSize(WIDTH - xBorder * 2, HEIGHT / 4);
		passwordField.setLocation(xBorder, yBorder * 3 + HEIGHT / 4);
		
		loginButton = new SimpleButton(StringR.LOGIN);
		loginButton.setSize((WIDTH - xBorder * 3) / 2, HEIGHT / 4);
		loginButton.setLocation(xBorder, yBorder * 4 + HEIGHT / 4 * 2);
		loginButton.addActionListener(new ButtonListener());
		
		exitButton = new SimpleButton(StringR.EXIT);
		exitButton.setSize((WIDTH - xBorder * 3) / 2, HEIGHT / 4);
		exitButton.setLocation(xBorder * 2 + loginButton.getWidth(), yBorder * 4 + HEIGHT / 4 * 2);
		exitButton.addActionListener(new ButtonListener());
		
		this.add(idField);
		this.add(passwordField);
		this.add(loginButton);
		this.add(exitButton);
		
		getContentPane().setBackground(ColorR.WHITE);
		this.getRootPane().setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent ev){
			switch(ev.getSource().toString()) {
			case StringR.LOGIN:
				DataProvider.getInstance();
				DataProvider.getInstance().setId(idField.getText());
				new SelectFloorPlanFrame(
						StringR.SELECT_BUILDING,
						DimenR.BUILDING_LIST_FRAME_WIDTH,
						DimenR.BUILDING_LIST_FRAME_HEIGHT
						);
                dispose();
				break;
				
			case StringR.EXIT:
                dispose();
                System.exit(0);
				break;
			}
		}
	}
	
	public static void main (String args[]) {
		LoginFrame frame = new LoginFrame(
				StringR.LOGIN,
				DimenR.LOGIN_FRAME_WIDTH,
				DimenR.LOGIN_FRAME_HEIGHT
				);
	}
}
