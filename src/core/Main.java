package core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;

import javax.swing.SwingUtilities;

import global.Materias;
import global.Settings;
import objects.Aluno;
import objects.Unidade;
import panels.Cadastro;

public class Main {
	
	private static Display window;
	private static boolean loadSettings = false;
	private static Cadastro cadastro;
	private static CountDownLatch stopSignal;
	private static Unidade anhanguera;

	public static void main(String[] args) {
		
		File settingsINI = new File("Settings.ini");
		
		// SETTING MONITOR DIMENSION
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Settings.widthMonitor = (int)screenSize.getSize().getWidth();
		Settings.heightMonitor = (int)screenSize.getSize().getHeight();
	
	while (!loadSettings){	
		
		if (!settingsINI.exists()){
			
			Dimension recomendedSize = Settings.getMinimumScreen(screenSize);
			String fullScreen;
			String language = Locale.getDefault().getLanguage();
			
			
			if (screenSize.getWidth() == recomendedSize.getWidth())
				fullScreen = "TRUE";
			else
				fullScreen = "FALSE";
			
			if (language != "pt")
				language = "en-us";
			else
				language = "pt-br";
			
			
			System.out.println("O arquivo Settings.ini n�o existe!");
			try {
				settingsINI.createNewFile();
				System.out.println("Arquivo Settings.ini criado com sucesso!");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				System.out.println("Não foi possivel criar o arquivo Settings.ini");
			}
			
			FileWriter settingsWrite = null;
			
			try {
				settingsWrite = new FileWriter(settingsINI);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			try {
				settingsWrite.write("WIDTH_RESOLUTION = " + (int)recomendedSize.getWidth() + "\r\n");
				settingsWrite.write("HEIGHT_RESOLUTION = " + (int)recomendedSize.getHeight() + "\r\n");
				settingsWrite.write("FULL_SCREEN = " + fullScreen + "\r\n");
				settingsWrite.write("SMOOTH_RENDER = TRUE" + "\r\n");
				settingsWrite.write("LANGUAGE = " + language);
				settingsWrite.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			Settings.widthResolution = myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 1));
			Settings.heightResolution = myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 2));
			Settings.FULL_SCREEN = myFiles.convertToBoolean(myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 3)));
			Settings.SMOOTH = myFiles.convertToBoolean(myFiles.convertToValue(myFiles.getValueLine("Settings.ini", 4)));
			Settings.LANGUAGE = myFiles.getValueLine("Settings.ini", 5);
			loadSettings = true;
		}
		
	}
	
	// INICIA JANELA
	
	SwingUtilities.invokeLater(Frame);
	
	stopSignal = new CountDownLatch(1);
	SwingUtilities.invokeLater(Cadastro);
	
	}
	
	private static Runnable Frame = new Runnable() {
		public void run() {
		window = new Display();
		Settings.window = window;
		
		Settings.WIDTH = window.getContentPane().getSize().width;
		Settings.HEIGHT = window.getContentPane().getSize().height;
		Settings.TWH = Settings.heightResolution - Settings.HEIGHT;
		
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		}
	};
	
	
	private static Runnable Cadastro = new Runnable() {
		public void run() {
			anhanguera = new Unidade("S�o Paulo (Campus Marte)");
			Aluno vitor = new Aluno("Vitor Gomes Martins Mendes",8799001598L,anhanguera);
			anhanguera.setAluno(vitor);
			vitor.setDisciplina(Materias.POO1);
			vitor.setDisciplina(Materias.SISTEMAS_COMPUTACAO);
			vitor.setDisciplina(Materias.ECONOMIA);
			vitor.setDisciplina(Materias.ENGENHARIA_SOFTWARE);
			vitor.getDisciplina(0).setNotaTrabalho(1, 2);
			vitor.getDisciplina(0).setNotaTrabalho(2, 3);
			vitor.getDisciplina(0).setNotaProva(1, 5);
			vitor.getDisciplina(0).setNotaProva(2, 6);
			
			cadastro = new Cadastro(stopSignal, anhanguera);
			cadastro.setVisible(true);
			Settings.window.getContentPane().add(cadastro);
		}
	};


}