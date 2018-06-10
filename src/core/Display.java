package core;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import global.Settings;

public class Display extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	public Display(){
		setTitle("Trabalho de Programação Orientada a Objetos");
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(Settings.widthResolution,Settings.heightResolution);
		setResizable(false);
		this.setLocation(dim.width/2-this.getSize().width/2, dim.height/2-this.getSize().height/2);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		setUndecorated(Settings.FULL_SCREEN);
		setVisible(true);
		setLayout(null);
	}
	
	public int getWidth(){
		return this.getContentPane().getSize().width;
	}
	
	public int getHeight(){
		return this.getContentPane().getSize().height;
	}

}
