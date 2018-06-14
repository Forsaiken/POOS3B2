package panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

import core.Sprite;
import global.Settings;
import objects.Aluno;

public class AlunoInfo extends JPanel implements ActionListener{
	
	Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
	
	private Aluno aluno;
	
	private Sprite background, nameBase, unidadeBase;
	private Sprite name, unidade;
	private ArrayList<Sprite> discSprite = new ArrayList<Sprite>();
	
	private int disciplinas;
	private int posX, posY;
	private boolean visible;
	
	public AlunoInfo(Aluno aluno) {
		
		this.aluno = aluno;
		
		this.setBackground(Color.WHITE);
		this.setSize(Settings.convertWidth(1370), Settings.convertHeight(700));
		this.setVisible(false);
		setLayout(null);
		
		Timer t = new Timer ((int) Settings.FPS1000, this);
		t.start();
		
	}
	
	public void paint(Graphics g) {
		
		super.paint(g);
		
		nameBase.draw(g);
		name.draw(g);
		unidadeBase.draw(g);
		unidade.draw(g);
		
		for (Sprite sprite:discSprite)
			sprite.draw(g);
	}
	
	public void load() {
		
		this.discSprite = new ArrayList<Sprite>();
		
		this.disciplinas = aluno.getDisciplinas().size();
		
		Font baseFont = new Font("Calibri Light", Font.TRUETYPE_FONT, Settings.convertFont(36));
		Font notaFont = new Font("Calibri Light", Font.TRUETYPE_FONT, Settings.convertFont(72));
		
		nameBase = new Sprite();
		nameBase.setString("NOME:\nRA:",baseFont, new Color[] {Color.BLACK}, 1f);
		nameBase.setLocation(this.posX, this.posY);
		nameBase.setFormatString(Sprite.LEFT_TO_RIGHT);
		nameBase.setParagraphSpacement(20);
		
		name = new Sprite();
		name.setString(aluno.getNome() + "\n" + aluno.getRA(),baseFont, new Color[] {Color.GRAY}, 1f);
		name.setLocation(nameBase.getPosX() + Settings.convertPositionX(120), this.posY);
		name.setFormatString(Sprite.LEFT_TO_RIGHT);
		name.setParagraphSpacement(20);
		
		unidadeBase = new Sprite();
		unidadeBase.setString("UNIDADE:\nCPF:",baseFont, new Color[] {Color.BLACK}, 1f);
		unidadeBase.setLocation(nameBase.getPosX() + Settings.convertPositionX(650), this.posY);
		unidadeBase.setFormatString(Sprite.LEFT_TO_RIGHT);
		unidadeBase.setParagraphSpacement(20);
		
		String CPF = "Não informado"; 
		if (aluno.getCPF() != 0)
			CPF = String.valueOf(aluno.getCPF());
		
		unidade = new Sprite();
		unidade.setString(aluno.getUnidade().getName() + "\n" + CPF,baseFont, new Color[] {Color.GRAY}, 1f);
		unidade.setLocation(unidadeBase.getPosX() + Settings.convertPositionX(150), this.posY);
		unidade.setFormatString(Sprite.LEFT_TO_RIGHT);
		unidade.setParagraphSpacement(20);
		
		System.out.println(aluno.getNome() + " " + aluno.getDisciplinas().size());
		
		for (int i = 0; i < disciplinas; i++) {
			
			Sprite disciplina = new Sprite();
			disciplina.setString(aluno.getDisciplina(i).getNome().toUpperCase(), baseFont, new Color[] {new Color(255,108,0)}, 1f);
			disciplina.setLocation(this.posX + columnXCount(i), this.posY + Settings.convertPositionY(120) + columnYCount(i));
			disciplina.setFormatString(Sprite.LEFT_TO_RIGHT);
			discSprite.add(disciplina);
			
			Sprite b1 = new Sprite();
			b1.setString("B1", baseFont, new Color[] {Color.BLACK}, 1f);
			b1.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(220), this.posY + Settings.convertPositionY(180) + columnYCount(i));
			b1.setFormatString(Sprite.CENTER);
			discSprite.add(b1);
			
			Sprite b2 = new Sprite();
			b2.setString("B2", baseFont, new Color[] {Color.BLACK}, 1f);
			b2.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(320), this.posY + Settings.convertPositionY(180) + columnYCount(i));
			b2.setFormatString(Sprite.CENTER);
			discSprite.add(b2);
			
			Sprite finalBase = new Sprite();
			finalBase.setString("FINAL", baseFont, new Color[] {Color.BLACK}, 1f);
			finalBase.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(470), this.posY + Settings.convertPositionY(180) + columnYCount(i));
			finalBase.setFormatString(Sprite.CENTER);
			discSprite.add(finalBase);
			
			
			Sprite avaliativos = new Sprite();
			avaliativos.setString("PROVA\nTRABALHO\nTOTAL", baseFont, new Color[] {Color.BLACK}, 1f);
			avaliativos.setLocation(this.posX + columnXCount(i), this.posY + Settings.convertPositionY(230) + columnYCount(i));
			avaliativos.setFormatString(Sprite.LEFT_TO_RIGHT);
			discSprite.add(avaliativos);
			
			Sprite notasB1 = new Sprite();
			notasB1.setString(String.format("%.1f", aluno.getDisciplina(i).getNotaProva(1)) + "\n" + String.format("%.1f", aluno.getDisciplina(i).getNotaTrabalho(1)) + "\n" + String.format("%.1f", aluno.getDisciplina(i).getNotaBimestral(1)), baseFont, new Color[] {Color.GRAY}, 1f);
			notasB1.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(220), this.posY + Settings.convertPositionY(230) + columnYCount(i));
			notasB1.setFormatString(Sprite.CENTER);
			discSprite.add(notasB1);
			
			Sprite notasB2 = new Sprite();
			notasB2.setString(String.format("%.1f", aluno.getDisciplina(i).getNotaProva(2)) + "\n" + String.format("%.1f", aluno.getDisciplina(i).getNotaTrabalho(2)) + "\n" + String.format("%.1f", aluno.getDisciplina(i).getNotaBimestral(2)), baseFont, new Color[] {Color.GRAY}, 1f);
			notasB2.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(320), this.posY + Settings.convertPositionY(230) + columnYCount(i));
			notasB2.setFormatString(Sprite.CENTER);
			discSprite.add(notasB2);
			
			double nota = aluno.getDisciplina(i).getNotaTotal();
			String aprovado;
			Color corAprovado;
			
			if (aluno.getDisciplina(i).getNotaBimestral(2) == 0) {
				aprovado = "SEM NOTAS";
				corAprovado = Color.GRAY;
			} else if (nota < 6) {
				aprovado = "REPROVADO";
				corAprovado = new Color(255,0,0);
			} else {
				aprovado = "APROVADO";
				corAprovado = new Color(0,200,255);
			}
			
			Sprite notaFinal = new Sprite();
			notaFinal.setString(String.format("%.1f",nota), notaFont, new Color[] {Color.BLACK}, 1f);
			notaFinal.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(470), this.posY + Settings.convertPositionY(240) + columnYCount(i));
			notaFinal.setFormatString(Sprite.CENTER);
			discSprite.add(notaFinal);
			
			Sprite notaAprovado = new Sprite();
			notaAprovado.setString(aprovado, baseFont, new Color[] {corAprovado}, 1f);
			notaAprovado.setLocation(this.posX + columnXCount(i) + Settings.convertPositionX(470), this.posY + Settings.convertPositionY(290) + columnYCount(i));
			notaAprovado.setFormatString(Sprite.CENTER);
			discSprite.add(notaAprovado);
		}
		
		
	}
	
	public int columnXCount(int index) {
		if (index % 2 == 0) {
			return 0;
		} else {
			return Settings.convertPositionX(650);
		}
	}
	
	public int columnYCount(int index) {
		if (index == 0 || index == 1) {return 0;}
		else if (index == 2 || index == 3) {return Settings.convertPositionY(250);}
		else if (index == 4 || index == 5) {return Settings.convertPositionY(500);}
		
		return 0;
	}
	
	
	
	public void setAluno (Aluno aluno) {
		this.aluno = aluno;
	}
	
	public void setLocationDraw (int posX, int posY) {
		this.posX = posX;
		this.posY = posY;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		repaint();
		
	}
	
	

}
