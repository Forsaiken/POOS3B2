package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.concurrent.CountDownLatch;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
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
	private static JFrame info;
	private static Unidade unidade;
	private static Professor lastProfessor;
	private static Aluno lastAluno;
	private static byte lastDisciplina;
	private static AlunoInfo alunoInfo;
	int phase;
	
	// Formulario
	
	private static FormUI ADDform;
	String[] questions = new String[] {
			"%ONE%%NEED%Escolha o painel que deseja acessar:",
			"%ONE%%NEED%Escolha a opção que deseja:",
			"%ONE%%NEED%Escolha a opção que deseja:",
			"Preencha as lacunas para cadastrar o Aluno:",
			"Preencha as lacunas para Cadastrar o Professor:",
			"%ONE%Preencha uma das lacunas para remover o Aluno:",
			"%ONE%Preencha uma das lacunas para remover o Professor:",
			"%MULTI%%NEED%Vincule as matérias:",
			"%ONE%Escolha a matéria que deseja vincular nota:",
			"Preencha os campos para vincular a nota:",
			"Preencha a lacuna abaixo:"};
	
	String[][] options= new String[][] {
		{"Painel de Alunos", "Painel de Professores"},
		{"Adicionar Aluno","Remover Aluno", "Visualizar Aluno e Notas"},
		{"Adicionar Professor", "Remover Professor","Vincular Nota Bimestral"},
		{"%BOX%%NEED%Nome do Aluno","%BOX%%NEED%RA", "%BOX%CPF (Opcional)"},
		{"%BOX%%NEED%Nome do Professor","%BOX%%NEED%ID", "%BOX%CPF (Opcional)"},
		{"%BOX%%NEED%RA do Aluno", "%BOX%%NEED%CPF do Aluno"},
		{"%BOX%%NEED%ID do Professor", "%BOX%%NEED%CPF do Professor"},
		{"Economia","Engenharia de Software","Programação Orientada a Objetos","Sistemas de Computação"},		
		{"Economia","Engenharia de Software","Programação Orientada a Objetos","Sistemas de Computação"},
		{"%BOX%%NEED%ID do Professor", "%BOX%%NEED%RA do Aluno", "%BOX%%NEED%Bimestre", "%BOX%%NEED%Nota de Trabalho", "%BOX%%NEED%Nota de Prova"},
		{"%BOX%%NEED%RA ou CPF do Aluno"}};
	
	
	Sprite configSGBD, configSGBD2;
	
	public Cadastro(CountDownLatch CDL, Unidade unidade) {
		
		this.alunoInfo = new AlunoInfo(null);
		
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
		ADDform.setValidator(4,Question4);
		ADDform.setValidator(5,Question5);
		ADDform.setValidator(6,Question6);
		ADDform.setValidator(7,Question7);
		ADDform.setValidator(8,Question8);
		ADDform.setValidator(9,Question9);
		ADDform.setValidator(10,Question10);
		
		t = new Timer((int)Settings.FPS1000,this);
		t.start();
		
	}
	
	public static Runnable Question0 = new Runnable() {
		public void run() {
			
			String answer = ADDform.getAnswer(0);
			
			if (answer.equals("Painel de Alunos"))
				ADDform.next(1);
			else if (answer.equals("Painel de Professores"))
				ADDform.next(2);
		}

	};
	
	public static Runnable Question1 = new Runnable() {
		public void run() {
			String answer = ADDform.getAnswer(1);
			if (answer.equals("Adicionar Aluno"))
				ADDform.next(3);
			else if (answer.equals("Remover Aluno"))
				ADDform.next(5);
			else if (answer.equals("Visualizar Aluno e Notas"))
				ADDform.next(10);			
		}
	};
	
	public static Runnable Question2 = new Runnable() {
		public void run() {
			String answer = ADDform.getAnswer(2);
			if (answer.equals("Adicionar Professor"))
				ADDform.next(4);
			else if (answer.equals("Remover Professor"))
				ADDform.next(6);
			else if (answer.equals("Vincular Nota Bimestral"))
				ADDform.next(8);
			else if (answer.equals("Visualizar Professores"))
				ADDform.next(0);
		}
	};
	
	public static Runnable Question3 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(3, 0);
			String answer2 = ADDform.getAnswer(3, 1);
			String answer3 = ADDform.getAnswer(3, 2);
			
			boolean erro = false;
			
			if (answer1.matches(".*\\d+.*")) {
				JOptionPane.showMessageDialog(null,"Nome não pode possuir número.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 3) {
				JOptionPane.showMessageDialog(null,"Nome requer mais que 3 caracteres.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer3.matches("[0-9]+") && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer3.replaceAll("\\s+", "").length() != 11  && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				long RA = Long.parseLong(answer2);
				
				Aluno aluno = new Aluno(answer1, RA, unidade);
				if (!answer3.equals("")) {
					long CPF = Long.parseLong(answer3);
					aluno.setCPF(CPF);
				}
				
				
				unidade.setAluno(aluno);
				
				System.out.println("O aluno " + unidade.getAluno(RA).getNome() + " foi adicionado com sucesso!");
				
				lastAluno = aluno;
				ADDform.next(7);
		
			}
			
		}
	};
	
	public static Runnable Question4 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(4, 0);
			String answer2 = ADDform.getAnswer(4, 1);
			String answer3 = ADDform.getAnswer(4, 2);
			
			boolean erro = false;
			
			if (answer1.matches(".*\\d+.*")) {
				JOptionPane.showMessageDialog(null,"Nome não pode possuir número.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 3) {
				JOptionPane.showMessageDialog(null,"Nome requer mais que 3 caracteres.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer3.matches("[0-9]+") && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer3.replaceAll("\\s+", "").length() != 11  && !answer3.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				long RA = Long.parseLong(answer2);
				
				Professor professor = new Professor(answer1, RA, unidade);
				if (!answer3.equals("")) {
					long CPF = Long.parseLong(answer3);
					professor.setCPF(CPF);
				}
				
				unidade.setProfessor(professor);
				
				System.out.println("O professor " + unidade.getProfessor(RA).getNome() + " foi adicionado com sucesso!");
				
				lastProfessor = professor;
				ADDform.next(7);
		
			}
			
			
		}
	};
	
	public static Runnable Question5 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(5, 0);
			String answer2 = ADDform.getAnswer(5, 1);
			
			boolean erro = false;
			
			if (!answer1.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+") && !answer2.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() != 11  && !answer2.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				boolean exclude = false;
				
				if (!answer1.equals("")) {
					long RA = Long.parseLong(answer1);
					exclude = unidade.removerAluno(RA);
				}
				
				if (!exclude && !answer2.equals("")) {
					long CPF = Long.parseLong(answer2);
					exclude = unidade.removerAluno(CPF);
				}
				
				if (!exclude) {
					JOptionPane.showMessageDialog(null,"Aluno não encontrado!","Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				ADDform.resetTextBox();
		
			}
		}
	};
	
	public static Runnable Question6 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(6, 0);
			String answer2 = ADDform.getAnswer(6, 1);
			
			boolean erro = false;
			
			if (!answer1.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"ID somente deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"ID requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+") && !answer2.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() != 11  && !answer2.equals("")) {
				JOptionPane.showMessageDialog(null,"CPF requer 11 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (erro != true) {
				
				boolean exclude = false;
				
				if (!answer1.equals("")) {
					long ID = Long.parseLong(answer1);
					exclude = unidade.removerProfessor(ID);
				}
				
				if (!exclude && !answer2.equals("")) {
					long CPF = Long.parseLong(answer2);
					exclude = unidade.removerProfessor(CPF);
				}
				
				if (!exclude) {
					JOptionPane.showMessageDialog(null,"Professor não encontrado!","Erro", JOptionPane.ERROR_MESSAGE);
				}
				
				ADDform.resetTextBox();
		
			}
		}
	};
	
	
	public static Runnable Question7 = new Runnable() {
		public void run() {
			
			String[] answer = ADDform.getAnswers(7);
			
			for (int i = 0; i < answer.length;i++) {
				
				if (ADDform.getPreviousQuestion() == 4) {
					switch (answer[i]) {
					case "Economia":
						lastProfessor.setDisciplina(ECONOMIA); break;
					case "Engenharia de Software": 
						lastProfessor.setDisciplina(ENGENHARIA_SOFTWARE); break;
					case "Programação Orientada a Objetos":
						lastProfessor.setDisciplina(POO1); break;
					case "Sistemas de Computação":
						lastProfessor.setDisciplina(SISTEMAS_COMPUTACAO); break;
					}
				}
				
				if (ADDform.getPreviousQuestion() == 3) {
					switch (answer[i]) {
					case "Economia":
						lastAluno.setDisciplina(ECONOMIA); break;
					case "Engenharia de Software":
						lastAluno.setDisciplina(ENGENHARIA_SOFTWARE); break;
					case "Programação Orientada a Objetos":
						lastAluno.setDisciplina(POO1); break;
					case "Sistemas de Computação":
						lastAluno.setDisciplina(SISTEMAS_COMPUTACAO); break;
					}
				}
			}
			
			ADDform.next(0);
		}
	};
	
	public static Runnable Question8 = new Runnable() {
		public void run() {
			
			String answer = ADDform.getAnswer(8);
			
					switch (answer) {
					case "Economia":
						lastDisciplina = ECONOMIA; break;
					case "Engenharia de Software":
						lastDisciplina = (ENGENHARIA_SOFTWARE); break;
					case "Programação Orientada a Objetos":
						lastDisciplina = (POO1); break;
					case "Sistemas de Computação":
						lastDisciplina = (SISTEMAS_COMPUTACAO); break;
					}
			
			ADDform.next(9);
		}
	};
	
	public static Runnable Question9 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(9, 0).replaceAll("\\s+", "");
			String answer2 = ADDform.getAnswer(9, 1).replaceAll("\\s+", "");
			String answer3 = ADDform.getAnswer(9, 2).replaceAll("\\s+", "");
			String answer4 = ADDform.getAnswer(9, 3).replaceAll("\\s+", "");
			String answer5 = ADDform.getAnswer(9, 4).replaceAll("\\s+", "");
			
			boolean erro = false;
			
			if (!answer1.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"ID deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"ID requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer2.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"RA deve conter somente números","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer2.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"RA requer mais que 4 números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}
			
			if (!answer3.matches("[0-9]+") || answer3.length() != 1) {
				JOptionPane.showMessageDialog(null,"Bimestre deve conter somente o número 1 ou 2.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else {
				if (Integer.parseInt(answer3) < 0 || Integer.parseInt(answer3) > 2) {
					JOptionPane.showMessageDialog(null,"Bimestre deve conter somente o número 1 ou 2.","Erro", JOptionPane.ERROR_MESSAGE);
					erro = true;
				}
			}
			
			if (!answer4.matches("[0-9]+") || answer4.length() != 1) {
				JOptionPane.showMessageDialog(null,"Nota de Trabalho somente deve conter valor entre 0 e 3","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else {
				if (Double.parseDouble(answer4) < 0 || Double.parseDouble(answer4) > 3) {
					JOptionPane.showMessageDialog(null,"Nota de Trabalho somente deve conter valor entre 0 e 3","Erro", JOptionPane.ERROR_MESSAGE);
					erro = true;
				}
			}
			
			if (!answer5.matches("[0-9]+") || answer5.length() != 1) {
				JOptionPane.showMessageDialog(null,"Nota de Prova somente deve conter valor entre 0 e 7","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else {
				if (Double.parseDouble(answer5) < 0 || Double.parseDouble(answer5) > 7) {
					JOptionPane.showMessageDialog(null,"Nota de Prova somente deve conter valor entre 0 e 7","Erro", JOptionPane.ERROR_MESSAGE);
					erro = true;
				}
			}
			
			if (erro != true) {
				
				long ID = Long.parseLong(answer1);
				long RA = Long.parseLong(answer2);
				int bimestre = Integer.parseInt(answer3);
				double trabalho = Double.parseDouble(answer4);
				double prova = Double.parseDouble(answer5);
				
				Professor prof = unidade.getProfessor(ID);
				
				if (prof != null) {
					
					Aluno aluno = prof.getAluno(RA);
					
					if (aluno != null) {
						
						aluno.getDisciplina(lastDisciplina).setNotaTrabalho(bimestre, trabalho);
						aluno.getDisciplina(lastDisciplina).setNotaProva(bimestre, prova);
						JOptionPane.showMessageDialog(null,"As notas de " + aluno.getNome() + " foram vinculadas com sucesso!","Sucesso!", JOptionPane.INFORMATION_MESSAGE);
						
					} else
						JOptionPane.showMessageDialog(null,"Aluno não encontrado entre as disciplinas do Professor!","Erro", JOptionPane.ERROR_MESSAGE);
					
				} else
					JOptionPane.showMessageDialog(null,"Professor não encontrado!","Erro", JOptionPane.ERROR_MESSAGE);
				
		
			}
			
		}
	};
	
	public static Runnable Question10 = new Runnable() {
		public void run() {
			String answer1 = ADDform.getAnswer(10);
			
			boolean erro = false;
			
			if (!answer1.matches("[0-9]+")) {
				JOptionPane.showMessageDialog(null,"Esse campo deve conter apenas números.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			} else if (answer1.replaceAll("\\s+", "").length() < 4) {
				JOptionPane.showMessageDialog(null,"Informe um valor com 4 digitos ou mais.","Erro", JOptionPane.ERROR_MESSAGE);
				erro = true;
			}

			
			if (erro != true) {
								
				Aluno aluno;
				
				if (!answer1.equals("")) {
					long number = Long.parseLong(answer1);
					aluno = unidade.getAluno(number);
					
					if (aluno != null) {
						
						System.out.println("Aluno encontrado! Abrindo Info...");
						
						 SwingUtilities.invokeLater(new Runnable() {
					        public void run() {
					        
							alunoInfo.setAluno(aluno);
							alunoInfo.setLocationDraw(Settings.convertPositionX(50), Settings.convertPositionY(50));
							alunoInfo.load();
							alunoInfo.setVisible(true);
							
							Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
							
							if (info != null) {
								info.remove(alunoInfo);
								info.dispose();
							}
							
							JFrame info = new JFrame();
							info.setTitle(aluno.getNome());
							info.setLocationRelativeTo(null);
							info.setResizable(false);
							info.setSize(Settings.convertWidth(1370), Settings.convertHeight(700));
							info.setLocation(dim.width/2-info.getSize().width/2, dim.height/2-info.getSize().height/2);
							info.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							info.add(alunoInfo);
							info.setVisible(true);
							
					            }
						 } );
						 
					}
					
				}
		
			}
		}
	};
	
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		configSGBD.draw(g);
		configSGBD2.draw(g);
		ADDform.draw(g);
		/**
		if (alunoInfo.isVisible()) {
			alunoInfo.draw(g);
		}
		
		**/
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
