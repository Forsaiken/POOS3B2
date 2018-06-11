package panels;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.FormUI;
import core.Sprite;
import global.Constants;
import global.Materias;
import global.Settings;
import objects.Aluno;
import objects.Professor;
import objects.Unidade;

@SuppressWarnings("serial")
public class Cadastro extends JPanel implements ActionListener, KeyListener, Constants, Materias {
	
	CountDownLatch CDL;
	Timer t;
	Sprite rect;
	private static Unidade unidade;
	private static Professor lastProfessor;
	private static Aluno lastAluno;
	int phase;
	
	// Formulario
	
	private static FormUI ADDform;
	String[] questions = new String[] {
			"%ONE%%NEED%Escolha uma das opções abaixo:",
			"Cadastro de Professor:",
			"Cadastro de Aluno:",
			"%MULTI%%NEED%Vincule as matérias:"};
	
	String[][] options= new String[][] {
		{"Adicionar Professor", "Adicionar Aluno"},
		{"%BOX%%NEED%Nome do Professor","%BOX%%NEED%RA", "%BOX%CPF"},
		{"%BOX%%NEED%Nome do Aluno","%BOX%%NEED%RA", "%BOX%CPF"},
		{"Economia","Engenharia de Software","Programação Orientada a Objetos","Sistemas de Computação"}};
	
	
	Sprite configSGBD, configSGBD2;
	
	public Cadastro(CountDownLatch CDL, Unidade unidade) {
		
		this.setLayout(null);
		this.setSize(Settings.widthResolution, Settings.heightResolution);
		System.out.println(this.getWidth() + " " + this.getHeight());
		
		this.CDL = CDL;
		this.unidade = unidade;
		
		this.setBackground(Color.WHITE);
		
		configSGBD = new Sprite();
		configSGBD.setString("SISTEMA DE NOTAS", new Font("Calibri", Font.TRUETYPE_FONT, Settings.convertFont(48)), new Color[] {new Color(35,35,35)}, 1f);
		configSGBD.setFormatString(CENTER);
		configSGBD.setLocation(this.getWidth()/2, this.getHeight()/2 - Settings.convertPositionY(250));
		
		configSGBD.setInitialLocation(this.getWidth()/2, this.getHeight()/2 - Settings.convertPositionY(50));
		configSGBD.setInitialAlpha(0f);
		
		configSGBD.setAlphaToInitial();
		configSGBD.setLocationToInitial();
		
		configSGBD.setMotionAnimation(0, 0, 0, 0, 1f);
		configSGBD.setAnimation(true);
		
		configSGBD2 = new Sprite();
		configSGBD2.setString("Sistemas de Informação - 3° Semestre",
				new Font("Calibri Light", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143)}, 1f);
		configSGBD2.setFormatString(CENTER);
		configSGBD2.setLocation(this.getWidth()/2,this.getHeight()/2 + Settings.convertPositionY(240));
		
		configSGBD2.setInitialLocation(this.getWidth()/2, this.getHeight()/2 + Settings.convertPositionY(10));
		configSGBD2.setInitialAlpha(0f);
		
		configSGBD2.setAlphaToInitial();
		configSGBD2.setLocationToInitial();
		
		configSGBD2.setMotionAnimation(0, 0, 0, 0, 1f);
		configSGBD2.setAnimation(true);
		
	
		ADDform = new FormUI(this,this.getWidth()/2 - Settings.convertWidth(675)/2, this.getHeight()/2 - Settings.convertPositionY(100), 675);
		ADDform.setQuestions(questions, Constants.CENTER, new Font("Calibre", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143)}, 1f);
		ADDform.setBackOptions(80,24, new Color[] {new Color(241,241,241), new Color(241,241,241), new Color(216,242,255)}, 1f,new int[] {Settings.convertHeight(2),Settings.convertHeight(2),Settings.convertHeight(2)}, new Color[] {new Color(185,185,185), new Color(185,185,185), new Color(84,150,209)});
		ADDform.setOptions(options, Constants.LEFT_TO_RIGHT, new Font("Calibre", Font.TRUETYPE_FONT, Settings.convertFont(25)), new Color[] {new Color(143,143,143),new Color(143,143,143),new Color(42,42,42)}, 1f);
		ADDform.setValidator(0,Question0);
		ADDform.setValidator(1,Question1);
		ADDform.setValidator(2,Question2);
		ADDform.setValidator(3,Question3);
		
		t = new Timer((int)Settings.FPS1000,this);
		t.start();
	}
	
	public static Runnable Question0 = new Runnable() {
		public void run() {
			String answer = ADDform.getAnswer(0);
			if (answer.equals("Adicionar Aluno")) {
				ADDform.next(2);
			} else if (answer.equals("Adicionar Professor")) {
				ADDform.next(1);
			}
		}
	};
	
	public static Runnable Question1 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(1, 0);
			String answer2 = ADDform.getAnswer(1, 1);
			String answer3 = ADDform.getAnswer(1, 2);
			
			boolean erro = false;
			
			if (answer1.matches(".*\\d+.*")) {
				JOptionPane.showMessageDialog(null,"Nome não pode possuir número.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 3) {
				JOptionPane.showMessageDialog(null,"Nome requer mais que 3 caracteres.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer3.matches("[0-9]+") && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer3.replaceAll("\\s+", "").length() != 11  && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				long RA = Long.parseLong(answer2);
				
				Professor professor = new Professor(answer1, RA);
				if (!answer3.equals("")) {
					long CPF = Long.parseLong(answer3);
					professor.setCPF(CPF);
				}
				
				unidade.setProfessor(professor);
				
				System.out.println("O professor " + unidade.getProfessor(RA).getNome() + " foi adicionado com sucesso!");
				
				lastProfessor = professor;
				ADDform.next(3);
		
			}
			
			
		}
	};
	
	public static Runnable Question2 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(2, 0);
			String answer2 = ADDform.getAnswer(2, 1);
			String answer3 = ADDform.getAnswer(2, 2);
			
			boolean erro = false;
			
			if (answer1.matches(".*\\d+.*")) {
				JOptionPane.showMessageDialog(null,"Nome não pode possuir número.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 3) {
				JOptionPane.showMessageDialog(null,"Nome requer mais que 3 caracteres.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer3.matches("[0-9]+") && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer3.replaceAll("\\s+", "").length() != 11  && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				long RA = Long.parseLong(answer2);
				
				Aluno aluno = new Aluno(answer1, RA);
				if (!answer3.equals("")) {
					long CPF = Long.parseLong(answer3);
					aluno.setCPF(CPF);
				}
				
				
				unidade.setAluno(aluno);
				
				System.out.println("O aluno " + unidade.getAluno(RA).getNome() + " foi adicionado com sucesso!");
				
				lastAluno = aluno;
				ADDform.next(3);
		
			}
			
		}
	};
	
	public static Runnable Question3 = new Runnable() {
		public void run() {
			
			String[] answer = ADDform.getAnswers(3);
			
			for (int i = 0; i < answer.length;i++) {
				if (ADDform.getPreviousQuestion() == 1) {
					switch (answer[i]) {
					case "Economia":
						lastProfessor.setDisciplina(ECONOMIA);
					case "Engenharia de Software":
						lastProfessor.setDisciplina(ENGENHARIA_SOFTWARE);
					case "Programação Orientada a Objetos":
						lastProfessor.setDisciplina(POO1);
					case "Sistemas de Computação":
						lastProfessor.setDisciplina(SISTEMAS_COMPUTACAO);
					}
				}
				
				if (ADDform.getPreviousQuestion() == 2) {
					switch (answer[i]) {
					case "Economia":
						lastAluno.setDisciplina(ECONOMIA,"Economia");
					case "Engenharia de Software":
						lastAluno.setDisciplina(ENGENHARIA_SOFTWARE,"Engenharia de Software");
					case "Programação Orientada a Objetos":
						lastAluno.setDisciplina(POO1, "Programação Orientada a Objetos");
					case "Sistemas de Computação":
						lastAluno.setDisciplina(SISTEMAS_COMPUTACAO, "Sistemas de Computação");
					}
				}
			}
				
			System.out.println(lastProfessor.getNome() + " disciplinas vinculadas com sucesso.");
		}
	};
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		configSGBD.draw(g);
		configSGBD2.draw(g);
		ADDform.draw(g);
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
			ADDform.open();
			phase++;
		}
		
		repaint();
		
	}
}
