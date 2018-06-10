package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JPanel;
import javax.swing.Timer;

import core.FormUI;
import core.Sprite;
import global.Constants;
import global.Settings;

@SuppressWarnings("serial")
public class Cadastro extends JPanel implements ActionListener, KeyListener, Constants {
	
	CountDownLatch CDL;
	Timer t;
	Sprite rect;
	int phase;
	private static Cadastro config;
	
	// Formulario
	
	private FormUI SGBDform;
	String[] questions = new String[] {"Escolha uma das opções abaixo:","Cadastro de Professor:","Cadastro de Aluno:"};
	String[][] options= new String[][] {{"Adicionar Professor", "Adicionar Aluno"},{"%BOX%Nome do Professor","%BOX%RA", "%BOX%CPF"},{"%BOX%Nome do Aluno","%BOX%RA", "%BOX%CPF"}};
	
	
	Sprite configSGBD, configSGBD2;
	
	public Cadastro(CountDownLatch CDL) {
		
		this.setLayout(null);
		this.setSize(Settings.widthResolution, Settings.heightResolution);
		System.out.println(this.getWidth() + " " + this.getHeight());
		
		this.CDL = CDL;
		
		this.setBackground(Color.WHITE);
		
		configSGBD = new Sprite();
		configSGBD.setString("CONFIGURAÇÃO DE BANCO DE DADOS", new Font("Khand Medium", Font.TRUETYPE_FONT, Settings.convertFont(48)), new Color[] {new Color(35,35,35)}, 1f);
		configSGBD.setFormatString(CENTER);
		configSGBD.setLocation(this.getWidth()/2, this.getHeight()/2 - Settings.convertPositionY(250));
		
		configSGBD.setInitialLocation(this.getWidth()/2, this.getHeight()/2 - Settings.convertPositionY(50));
		configSGBD.setInitialAlpha(0f);
		
		configSGBD.setAlphaToInitial();
		configSGBD.setLocationToInitial();
		
		configSGBD.setMotionAnimation(0, 0, 0, 0, 1f);
		configSGBD.setAnimation(true);
		
		configSGBD2 = new Sprite();
		configSGBD2.setString("Não foi detectado um banco de dados configurado.\nO assistente realizará uma nova configuração",
				new Font("Calibre", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143)}, 1f);
		configSGBD2.setFormatString(CENTER);
		configSGBD2.setLocation(this.getWidth()/2,this.getHeight()/2 + Settings.convertPositionY(240));
		
		configSGBD2.setInitialLocation(this.getWidth()/2, this.getHeight()/2 + Settings.convertPositionY(10));
		configSGBD2.setInitialAlpha(0f);
		
		configSGBD2.setAlphaToInitial();
		configSGBD2.setLocationToInitial();
		
		configSGBD2.setMotionAnimation(0, 0, 0, 0, 1f);
		configSGBD2.setAnimation(true);
		
	
		SGBDform = new FormUI(this,this.getWidth()/2 - Settings.convertWidth(675)/2, this.getHeight()/2 - Settings.convertPositionY(100), 675);
		SGBDform.setQuestions(questions, Constants.CENTER, new Font("Calibre", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143)}, 1f);
		SGBDform.setBackOptions(80,24, new Color[] {new Color(241,241,241), new Color(241,241,241), new Color(216,242,255)}, 1f,new int[] {Settings.convertHeight(2),Settings.convertHeight(2),Settings.convertHeight(2)}, new Color[] {new Color(185,185,185), new Color(185,185,185), new Color(84,150,209)});
		SGBDform.setOptions(options, Constants.LEFT_TO_RIGHT, new Font("Calibre", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143),new Color(143,143,143),new Color(42,42,42)}, 1f);
		//SGBDform.setValidator(0,Question1);
		SGBDform.next(2);
		
		t = new Timer((int)Settings.FPS1000,this);
		t.start();
	}
	
	public static Runnable Question1 = new Runnable() {
		public void run() {
			String answer = config.SGBDform.getAnswer(0);
			config.SGBDform.next(2);
		}
	};
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		configSGBD.draw(g);
		configSGBD2.draw(g);
		SGBDform.draw(g);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (phase == 0 && configSGBD.getAnimation() == false) {
			try {
				Thread.sleep(4000);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			configSGBD.setMotionAnimation(0, 1f, 0f, 0f, 0f);
			configSGBD.setAnimation(true);
			
			configSGBD2.setInitialAlpha(1f);
			configSGBD2.setFinalAlpha(0f);
			configSGBD2.setMotionAnimation(0, 1f, 0f, 0f, 1f);
			configSGBD2.setAnimation(true);
			phase ++;
			
		}
		
		if (phase == 1 && configSGBD.getAnimation() == false) {
			SGBDform.open();
			phase++;
		}
		
		repaint();
		
	}
}
