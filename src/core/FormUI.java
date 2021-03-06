package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import global.Constants;
import global.Settings;

public class FormUI implements KeyListener, MouseListener, MouseMotionListener, ActionListener {
	
	// FORM PROPERTIES
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	private boolean open;
	private boolean next;
	private boolean back;
	private int needed;
	
	private JPanel panel;
	ArrayList<Integer> passage = new ArrayList<Integer>();
	
	// QUESTION VARIABLES
	
	private int question;
	private int previousQuestion;
	private int oldQuestion;
	private int nextQuestion;
	
	private Font questionFont;
	private byte questionFormat;
	private Color[] questionColor;
	
	private ArrayList<Sprite> questions = new ArrayList<Sprite>();
	private ArrayList<ArrayList<Sprite>> options = new ArrayList<ArrayList<Sprite>>();
	private ArrayList<ArrayList<JTextField>> boxes = new ArrayList<ArrayList<JTextField>>();
	private ArrayList<ArrayList<String>> answers = new ArrayList<ArrayList<String>>();
	private ArrayList<Runnable> validators = new ArrayList<Runnable>();
	
	private String[][] optionStrings;
	private String[] questionStrings;
	
	// BACK ANSWER VARIABLES
	
	private int optionDistance;
	
	private int optionRectHeight;
	private Color[] optionRectColor;
	
	private int[] optionRectStroke;
	private Color[] optionRectStrokeColor;
	
	
	// ARROW VARIABLES
	
	private Sprite backArrow;
	private Sprite nextArrow;
	private boolean nextEnabled;
	private boolean backEnabled;
	
	public Timer t = new Timer(60,this);
	
	public FormUI(JPanel panel, int posX, int posY, int width) {
		
		this.panel = panel;
		
		this.posX = posX;
		this.posY = posY;
		this.width = Settings.convertWidth(width);
		Settings.window.addMouseListener(this);
		Settings.window.addMouseMotionListener(this);;
		
		backArrow = new Sprite();	
		nextArrow = new Sprite();
		
		Settings.window.addKeyListener(this);
		
	}
	
	public void draw(Graphics g) {
		
		if (open) {
			questions.get(0).setInitialLocation(Settings.WIDTH + questions.get(0).getStringWidth(g)/2, questions.get(0).getPosY());
			questions.get(0).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(0).setAnimation(true);
			open = false;
			passage.add(0);
		}
		
		if (next) {
			questions.get(nextQuestion).setFinalPosX(this.posX + this.width/2);
			questions.get(nextQuestion).setInitialLocation(Settings.WIDTH + questions.get(nextQuestion).getStringWidth(g)/2 + this.width/2, questions.get(nextQuestion).getPosY());
			questions.get(nextQuestion).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(nextQuestion).setAnimation(true);
			
			questions.get(question).reverseLocation();
			questions.get(question).setFinalPosX(0 - questions.get(question).getStringWidth(g)/2 - this.width/2);
			questions.get(question).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(question).setAnimation(true);
			
			this.passage.add(nextQuestion);
			this.oldQuestion = question;
			this.previousQuestion = question;
			this.question = nextQuestion;
			next = false;
			
			if (question != 0) {
				openBack();
				resetOptions();
				resetTextBox();
				backEnabled = true;
			} else {
				
				for (int i = 0; i < passage.size(); i++) {
					if (i > 0) {
						passage.remove(i);
					}
				}
				
				resetOptions();
				closeBack();
			}
			
			System.out.println("Quest�o " + question + " e quest�o anterior " + previousQuestion);
			
		}
		
		if (back) {
			questions.get(nextQuestion).setFinalPosX(this.posX + this.width/2);
			questions.get(nextQuestion).setInitialLocation(0 - questions.get(nextQuestion).getStringWidth(g)/2 - this.width/2, questions.get(nextQuestion).getPosY());
			questions.get(nextQuestion).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(nextQuestion).setAnimation(true);
			
			questions.get(question).reverseLocation();
			questions.get(question).setFinalPosX(Settings.WIDTH + questions.get(question).getStringWidth(g)/2 + this.width/2);
			questions.get(question).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(question).setAnimation(true);
			
			this.passage.remove(passage.size() - 1);
			this.oldQuestion = question;
			this.question = nextQuestion;
			
			back = false;
			
			if (passage.size() - 2 >= 0)
				previousQuestion = passage.get(passage.size() - 2);
			
			if (question != 0) {
				openBack();
				openNext();
				backEnabled = true;
			} else {
				openNext();
				closeBack();
			}
			
			System.out.println("Quest�o " + question + " e quest�o anterior " + previousQuestion);
			
		}
		
		backArrow.draw(g);
		nextArrow.draw(g);
		
		if(questions.get(oldQuestion).getAnimation())
			questions.get(oldQuestion).draw(g);
		
		questions.get(this.question).draw(g);
		
		if (options.get(oldQuestion).get(0).getAnimation()) {
			for (int i = 0; i < options.get(oldQuestion).size(); i++) {
				options.get(oldQuestion).get(i).draw(g);
			}
		}

		for (int i = 0; i < options.get(question).size(); i++) {
			options.get(question).get(i).draw(g);
		}
		
	}
	
	public void open() {
		for (Sprite i: options.get(0)) {
			i.setInitialLocation(Settings.WIDTH + i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		open = true;
		
	}
	
	public void next(int nextQuestion) {
		
		needed = 0;
		t.stop();
		
		this.nextQuestion = nextQuestion;
		for (Sprite i: options.get(nextQuestion)) {
			
			if (i.getTextBox() != null) {
				i.add(panel);
				i.getTextBox().setVisible(true);
			}
			
			i.setInitialLocation(Settings.WIDTH + i.getWidth(), i.getFinalY());
			i.setFinalPosX(this.posX);
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
			
		}
		
		for (String string : this.optionStrings[nextQuestion]) {
			if (string.contains("%NEED%")) {
				if (questionStrings[nextQuestion].contains("%ONE%"))
					needed = 1;
				else
					needed++;
				t.start();
			}
		}
		
		for (Sprite i: options.get(question)) {
			
			if (i.getTextBox() != null) {
				i.getTextBox().setVisible(false);
			}
			
			i.setInitialLocation(i.getPosX(), i.getPosY());
			i.setFinalLocation(0 - i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		next = true;
		
	}
	
	public void back(int previousQuestion) {
		
		needed = 0;
		t.stop();
		
		this.nextQuestion = previousQuestion;
		for (Sprite i: options.get(previousQuestion)) {
			if (i.getTextBox() != null) {
				i.getTextBox().setVisible(true);
			}
			
			i.setInitialLocation(0 - i.getWidth(), i.getFinalY());
			i.setFinalPosX(this.posX);
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		for (String string : this.optionStrings[previousQuestion]) {
			if (string.contains("%NEED%")) {
				if (questionStrings[previousQuestion].contains("%ONE%"))
					needed = 1;
				else
					needed++;
				t.start();
			}
		}
		
		for (Sprite i: options.get(question)) {
			
			if (i.getTextBox() != null) {
				i.getTextBox().setVisible(false);
			}
			
			i.setInitialLocation(i.getPosX(), i.getPosY());
			i.setFinalLocation(Settings.WIDTH + i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		back = true;		
		
	}
	
	public void setQuestions(String[] questions, byte textFormat, Font font, Color[] color, float alpha) {
		
		this.questionStrings = questions;
		
		for (int i = 0; i < questions.length; i++) {
			Sprite question = new Sprite();
			question.setString(removeTags(questions[i]), font, color, 1f);
			question.setFormatString(textFormat);
			question.setInitialAlpha(0f);
			question.setAlphaToInitial();
			
			if (textFormat == Constants.LEFT_TO_RIGHT) {
				question.setLocation(this.posX, this.posY);
			} else if (textFormat == Constants.CENTER) {
				question.setLocation(this.posX + this.width/2, this.posY);
			}
			
			this.questions.add(question);
		}
		
		this.questionFont = font;
		this.questionColor = color;
		this.questionFormat = textFormat;
		
	}
	
	public void setOptions(String[][] options, byte textFormat, Font font, Color[] color, float alpha) {
		
		this.optionStrings = options;
		
		for (int x = 0; x < options.length; x++) {
			
			this.options.add(new ArrayList<Sprite>());
			this.boxes.add(new ArrayList<JTextField>());
			this.validators.add(null);
			answers.add(new ArrayList<String>());
			
			if (questionStrings[x].contains("%ONE%")) {
				answers.get(x).add("null");
			}
			
			for (int y = 0; y < options[x].length; y++) {
				
					Sprite option = new Sprite();
					option.setFillRect(this.width, this.optionRectHeight, this.optionRectColor);
					option.setStroke(this.optionRectStroke, this.optionRectStrokeColor);
					option.setLocation(this.posX, this.posY + (this.optionDistance * 2) + y * (this.optionDistance + this.optionRectHeight));
					option.setString(this.removeTags(options[x][y]), font, color, 1f);
					option.setFormatString(textFormat);
					option.setInitialAlpha(0f);
					option.setAlphaToInitial();
					option.setTab(y);
					
				if(options[x][y].contains("%BOX%")) {
					
					option.setFillRect(this.width, this.optionRectHeight, new Color[] {Color.WHITE});
					option.setTextBox(Color.BLACK);
					option.getTextBox().addKeyListener(new KeyAdapter(){
			 			
			 			public void keyReleased(KeyEvent e) {
			 		        if (option.getTextBox().getText().equals("")) {
			 		        	option.setSample();
			 		        } else {
			 		        	option.setStringName("");
			 		        }
			 				
			 			}
			 			
			 			public void keyPressed(KeyEvent e) {
			 				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			 					if (FormUI.this.options.get(question).size() > option.getTab()+1) {
			 						if (FormUI.this.options.get(question).get(option.getTab()+1).getTextBox() != null)
			 							FormUI.this.options.get(question).get(option.getTab()+1).getTextBox().grabFocus();
			 					}
			 				}
			 			}
			 		});
				}
					
				if (!questionStrings[x].contains("%ONE%")) {
					this.answers.get(x).add("null");
				}
					this.options.get(x).add(option);
				
			}
		}
		
		backArrow.setFillPolygon(new int[] {60,0,60,45}, new int[] {0,30,60,30}, optionRectColor, 1f);
		backArrow.setStroke(new int[] {Settings.convertWidth(2),Settings.convertWidth(2),Settings.convertWidth(2)}, optionRectStrokeColor);
		backArrow.setInitialAlpha(0f);
		backArrow.setAlphaToInitial();
		
		
		nextArrow.setFillPolygon(new int[] {0,60,0,15}, new int[] {0,30,60,30}, optionRectColor, 1f);
		nextArrow.setStroke(new int[] {1,1,1}, optionRectStrokeColor);
		nextArrow.setInitialAlpha(0f);
		nextArrow.setAlphaToInitial();
	}
	
	public void setBackOptions(int height, int distance, Color[] color, float rectAlpha, int[] stroke, Color[] strokeColor) {
		
		this.optionDistance = Settings.convertPositionY(distance);
		this.optionRectHeight =  Settings.convertHeight(height);
		this.optionRectColor = color;
		this.optionRectStroke = stroke;
		this.optionRectStrokeColor = strokeColor;
	}
	
	public void resetOptions() {
		for (int i = 0; i < options.get(question).size();i++) {
			options.get(question).get(i).setMode(0);
			if (questionStrings[question].contains("%MULTI%"))	
					answers.get(question).set(i, "null");
		}
		
		if (questionStrings[question].contains("%ONE%"))
			answers.get(question).set(0, "null");
	}
	
	public void resetTextBox() {
		for (int i = 0; i < options.get(question).size();i++) {
			if (options.get(question).get(i).getTextBox() != null) {
				options.get(question).get(i).getTextBox().setText("");
				options.get(question).get(i).setSample();
			}
		}
	}
	
	public void openNext() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
		if(nextArrow.getAnimation())
			nextArrow.resetCountAnimation();
		
		nextArrow.setLocation(this.posX + this.width + Settings.convertPositionX(200), firstOpt + (lastOpt - firstOpt) / 2 - nextArrow.getWidth()/2);
		nextArrow.setFinalAlpha(1f);
		nextArrow.setInitialAlpha(0f);
		nextArrow.setAlphaToInitial();
		nextArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		nextArrow.setAnimation(true);
		
		this.nextEnabled = true;
		
	}
	
	public void closeNext() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
		if(nextArrow.getAnimation())
			nextArrow.resetCountAnimation();
		
		nextArrow.setLocation(this.posX + this.width + Settings.convertPositionX(200), firstOpt + (lastOpt - firstOpt) / 2 - nextArrow.getWidth()/2);
		nextArrow.setFinalAlpha(0f);
		nextArrow.setInitialAlpha(1f);
		nextArrow.setAlphaToInitial();
		nextArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		nextArrow.setAnimation(true);
		nextArrow.setMode(0);
		
		this.nextEnabled = false;
		
	}
	
	public void openBack() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
		if(backArrow.getAnimation())
			backArrow.resetCountAnimation();
		
		backArrow.setLocation(this.posX - Settings.convertPositionX(200) - backArrow.getWidth(), firstOpt + (lastOpt - firstOpt) / 2 - backArrow.getWidth()/2);
		backArrow.setFinalAlpha(1f);
		backArrow.setInitialAlpha(0f);
		backArrow.setAlphaToInitial();
		backArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		backArrow.setAnimation(true);
		
		this.backEnabled = true;
			
	}

	public void closeBack() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
		if(backArrow.getAnimation())
			backArrow.resetCountAnimation();
		
		backArrow.setLocation(this.posX - Settings.convertPositionX(200) - backArrow.getWidth(), firstOpt + (lastOpt - firstOpt) / 2 - backArrow.getWidth()/2);
		backArrow.setFinalAlpha(0f);
		backArrow.setInitialAlpha(1f);
		backArrow.setAlphaToInitial();
		backArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		backArrow.setAnimation(true);
		backArrow.setMode(0);
		
		this.backEnabled = false;
			
	}
	
	public String getAnswer(int question, int option) {
		if (options.get(question).get(option).getTextBox() != null)
			return options.get(question).get(option).getTextBox().getText().trim();
		else
			return answers.get(question).get(option);
	}
	
	public String getAnswer(int question) {
		if (options.get(question).get(0).getTextBox() != null)
			return options.get(question).get(0).getTextBox().getText().trim();
		else
			return answers.get(question).get(0);
	}
	
	public String[] getAnswers(int question) {
		return this.answers.get(question).toArray(new String[answers.get(question).size()]);
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void setValidator(int question, Runnable validator) {
		validators.set(question, validator);
	}
	
	public String removeTags(String string) {
		
		string = string.replaceAll("%BOX%", "");
		string = string.replaceAll("%NEED%", "");
		string = string.replaceAll("%ONE%", "");
		string = string.replaceAll("%MULTI%", "");
		
		/**
		List<Character> characters = string.chars().mapToObj((i) -> Character.valueOf((char)i)).collect(Collectors.toList());
		
		for (int i = 0; i < characters.size(); i++) {
			if (characters.get(i).equals('%')) {
				for (int x = i+1; x < characters.size(); x++) {
					if (characters.get(x).equals('%')) {
						for (int y=i; y <= x-i; y++) {
							characters.remove(i);
						}
						
						x = characters.size();
					}
				}
			}
		}
		
		string = characters.stream().map(e->e.toString()).collect(Collectors.joining());
		
		**/
		
		return string;
		
	}
	
	public int getPreviousQuestion() {
		return this.previousQuestion;
	}

	@Override
	public void mousePressed(MouseEvent e) {
		
		int count = 0;
		boolean isMulti = questionStrings[question].contains("%MULTI%");
		boolean isOne = questionStrings[question].contains("%ONE%");
				
		for (int i = 0; i < options.get(question).size(); i++) {
			
			int posX = options.get(question).get(i).getPosX();
			int posY = options.get(question).get(i).getPosY() + Settings.TWH;
			int eWidth = options.get(question).get(i).getWidth();
			int eHeight = options.get(question).get(i).getHeight();
			int mode = options.get(question).get(i).getMode();
			
			if (e.getX() > posX && e.getY() > posY && e.getX() < eWidth + posX && e.getY() < eHeight + posY) {
				
				if (mode != 2) {
					
					if (isOne)
						resetOptions();
					
					options.get(question).get(i).setMode(2);
					if (isMulti)
						answers.get(question).set(i, options.get(question).get(i).getString()[0]);
					else if (isOne)
						answers.get(question).set(0, options.get(question).get(i).getString()[0]);
						
					
					if (nextEnabled != true)
						this.openNext();
						
					
				} else if (mode == 2) {
					
					options.get(question).get(i).setMode(0);
					
					if (isMulti)
						answers.get(question).set(i, "null");
					else if (isOne)
						answers.get(question).set(0, "null");
					
					if (nextEnabled && isOne)
						this.closeNext();
					
				}
				
			}
			
			if (isMulti) {
				if (options.get(question).get(i).getMode() == 0) {
					count++;
				}
			}
		}
		
		if (isMulti) {
			System.out.println(count + " " + options.get(question).size());
			if (count == options.get(question).size())
				this.closeNext();
		}
		
		if (nextEnabled) {
			if (e.getX() > nextArrow.getPosX() && e.getY() > nextArrow.getPosY() + Settings.TWH && e.getX() < nextArrow.getWidth() + nextArrow.getPosX() && e.getY() < nextArrow.getHeight() + nextArrow.getPosY() + Settings.TWH) {
				closeNext();
				if (validators.get(question) != null) {
					validators.get(question).run();
				}
			}
		}
		
		if (backEnabled) {
			if (e.getX() > backArrow.getPosX() && e.getY() > backArrow.getPosY() + Settings.TWH && e.getX() < backArrow.getWidth() + backArrow.getPosX() && e.getY() < backArrow.getHeight() + backArrow.getPosY() + Settings.TWH) {
				closeBack();
				this.back(previousQuestion);
			}
		}
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		
		if (nextEnabled) {
			if (e.getX() > nextArrow.getPosX() && e.getY() > nextArrow.getPosY() + Settings.TWH && e.getX() < nextArrow.getWidth() + nextArrow.getPosX() && e.getY() < nextArrow.getHeight() + nextArrow.getPosY() + Settings.TWH) {
				nextArrow.setMode(2);
			} else {
				nextArrow.setMode(0);
			}
		}
		
		if (backEnabled) {
			if (e.getX() > backArrow.getPosX() && e.getY() > backArrow.getPosY() + Settings.TWH && e.getX() < backArrow.getWidth() + backArrow.getPosX() && e.getY() < backArrow.getHeight() + backArrow.getPosY() + Settings.TWH) {
				backArrow.setMode(2);
			} else {
				backArrow.setMode(0);
			}
		}
			
		
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
		
		if (needed > 0) {
		
			int count = 0;
			for (int i = 0; i < options.get(question).size(); i++) {
				Sprite option = options.get(question).get(i);
				if (option.getTextBox() != null && this.optionStrings[question][i].contains("%NEED%")) {
					if (!option.getTextBox().getText().equals("")) {
						count++;
					}
				}
			}
		
			if (count >= needed) {
				if (!nextEnabled)
					this.openNext();
			} else {
				if (nextEnabled)
					this.closeNext();
			}
		}
	}

	
	
}
