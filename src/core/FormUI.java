package core;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

public class FormUI implements MouseListener, MouseMotionListener {
	
	public interface qType {

	}
	
	public interface oType {

	}
	
	// FORM PROPERTIES
	
	private int posX;
	private int posY;
	private int width;
	private int height;
	
	private boolean open;
	private boolean next;
	
	private JPanel panel;
	
	// QUESTION VARIABLES
	
	private int question;
	private int previousQuestion;
	private int nextQuestion;
	
	private Font questionFont;
	private byte questionFormat;
	private Color[] questionColor;
	
	private ArrayList<Sprite> questions = new ArrayList<Sprite>();
	private ArrayList<ArrayList<Sprite>> options = new ArrayList<ArrayList<Sprite>>();
	private ArrayList<ArrayList<JTextField>> boxes = new ArrayList<ArrayList<JTextField>>();
	private ArrayList<String> answers = new ArrayList<String>();
	private ArrayList<Runnable> validators = new ArrayList<Runnable>();
	
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
	
	public FormUI(JPanel panel, int posX, int posY, int width) {
		
		this.panel = panel;
		
		this.posX = posX;
		this.posY = posY;
		this.width = Settings.convertWidth(width);
		Settings.window.addMouseListener(this);
		Settings.window.addMouseMotionListener(this);;
		
		backArrow = new Sprite();	
		nextArrow = new Sprite();
		
	}
	
	public void draw(Graphics g) {
		
		if (open) {
			questions.get(0).setInitialLocation(0 - questions.get(0).getStringWidth(g)/2, questions.get(0).getPosY());
			questions.get(0).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(0).setAnimation(true);
			open = false;
		}
		
		if (next) {
			questions.get(nextQuestion).setInitialLocation(0 - questions.get(nextQuestion).getStringWidth(g), questions.get(nextQuestion).getPosY());
			questions.get(nextQuestion).setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			questions.get(nextQuestion).setAnimation(true);
			
			questions.get(question).reverseLocation();
			questions.get(question).setFinalPosX(Settings.WIDTH + questions.get(question).getStringWidth(g));
			questions.get(question).setMotionAnimation(0.3f, 0, 0, 0, 0.1f);
			questions.get(question).setAnimation(true);
			
			this.previousQuestion = question;
			this.question = nextQuestion;
			next = false;
		}
		
		if(questions.get(previousQuestion).getAnimation())
			questions.get(previousQuestion).draw(g);
		
		questions.get(this.question).draw(g);
		
		if (options.get(0).get(previousQuestion).getAnimation()) {
			for (int i = 0; i < options.get(previousQuestion).size(); i++) {
				options.get(previousQuestion).get(i).draw(g);
			}
		}

		for (int i = 0; i < options.get(question).size(); i++) {
			options.get(question).get(i).draw(g);
		}
		
		for (int i = 0; i < boxes.get(question).size(); i++) {
			if (boxes.get(nextQuestion).get(i) != null) {
				boxes.get(question).get(i).repaint();
			}
		}
		
		backArrow.draw(g);
		nextArrow.draw(g);

	}
	
	public void open() {
		for (Sprite i: options.get(0)) {
			i.setInitialLocation(0 - i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		open = true;
		
	}
	
	public void next(int nextQuestion) {
		
		this.nextQuestion = nextQuestion;
		for (Sprite i: options.get(nextQuestion)) {
			i.setInitialLocation(0 - i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		for (int i = 0; i < boxes.get(nextQuestion).size(); i++) {
			if (boxes.get(nextQuestion).get(i) != null) {
				panel.add(boxes.get(nextQuestion).get(i));
				boxes.get(nextQuestion).get(i).setVisible(true);
				System.out.println("teste");
			}
		}
		
		for (Sprite i: options.get(question)) {
			i.setInitialLocation(i.getPosX(), i.getPosY());
			i.setFinalLocation(Settings.window.getWidth() + i.getWidth(), i.getFinalY());
			i.setMotionAnimation(0.5f, 0, 0, 0, 0.1f);
			i.setAnimation(true);
		}
		
		next = true;
		
	}
	
	public void setQuestions(String[] questions, byte textFormat, Font font, Color[] color, float alpha) {
		for (int i = 0; i < questions.length; i++) {
			Sprite question = new Sprite();
			question.setString(questions[i], font, color, 1f);
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
		for (int x = 0; x < options.length; x++) {
			
			this.options.add(new ArrayList<Sprite>());
			this.boxes.add(new ArrayList<JTextField>());
			this.validators.add(null);
			answers.add("null");
			
			for (int y = 0; y < options[x].length; y++) {
				
				if(options[x][y].contains("%BOX%")) {
					JTextField tf = new JTextField(this.removeTags(options[x][y]));
					tf.setFont(font);
					tf.setBackground(Color.WHITE);
					tf.setForeground(Color.BLACK);
					tf.setBounds(this.posX, this.posY + (this.optionDistance * 2) + y * (this.optionDistance + this.optionRectHeight), this.width, this.optionRectHeight );
					tf.setHorizontalAlignment(JTextField.CENTER);
					tf.setVisible(false);
					
					this.boxes.get(x).add(tf);
					
				} else {
				
					Sprite option = new Sprite();
					option.setFillRect(this.width, this.optionRectHeight, this.optionRectColor);
					option.setStroke(this.optionRectStroke, this.optionRectStrokeColor);
					option.setLocation(this.posX, this.posY + (this.optionDistance * 2) + y * (this.optionDistance + this.optionRectHeight));
					option.setString(this.removeTags(options[x][y]), font, color, 1f);
					option.setFormatString(textFormat);
					option.setInitialAlpha(0f);
					option.setAlphaToInitial();
				
					this.options.get(x).add(option);
				
				}
			}
		}
		
		backArrow.setFillPolygon(new int[] {60,0,60,45}, new int[] {30,0,-30,0}, optionRectColor, 1f);
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
		}
		answers.set(question, "null");
	}
	
	public void setVisible() {
	}
	
	public void openNext() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
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
		
		nextArrow.setLocation(this.posX + this.width + Settings.convertPositionX(200), firstOpt + (lastOpt - firstOpt) / 2 - nextArrow.getWidth()/2);
		nextArrow.setFinalAlpha(0f);
		nextArrow.setInitialAlpha(1f);
		nextArrow.setAlphaToInitial();
		nextArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		nextArrow.setAnimation(true);
		
		this.nextEnabled = false;
		
	}
	
	public void openBack() {
		
		int firstOpt = this.options.get(question).get(0).getPosY();
		int lastOpt = this.options.get(question).get(options.get(question).size() - 1).getPosY() + this.options.get(question).get(options.get(question).size() - 1).getHeight();
		
		backArrow.setLocation(this.posX - Settings.convertPositionX(200), firstOpt + (lastOpt - firstOpt) / 2 - backArrow.getWidth()/2);
		backArrow.setFinalAlpha(1f);
		backArrow.setInitialAlpha(0f);
		backArrow.setAlphaToInitial();
		backArrow.setMotionAnimation(0, 0, 0, 0, 0.5f);
		backArrow.setAnimation(true);
		
		this.backEnabled = true;
			
	}
	
	public String getAnswer(int question) {
		return answers.get(question);
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
	
	@Override
	public void mousePressed(MouseEvent e) {
		for (int i = 0; i < options.get(question).size(); i++) {
			
			int posX = options.get(question).get(i).getPosX();
			int posY = options.get(question).get(i).getPosY() + Settings.TWH;
			int eWidth = options.get(question).get(i).getWidth();
			int eHeight = options.get(question).get(i).getHeight();
			int mode = options.get(question).get(i).getMode();
			
			if (e.getX() > posX && e.getY() > posY && e.getX() < eWidth + posX && e.getY() < eHeight + posY) {
				
				if (mode != 2) {
					
					resetOptions();
					
					options.get(question).get(i).setMode(2);
					answers.set(question, options.get(question).get(i).getString()[0]);
					
					if (nextEnabled != true)
						this.openNext();
						
					
				} else if (mode == 2) {
					options.get(question).get(i).setMode(0);
					answers.set(question, "null");
					if (nextEnabled)
						this.closeNext();
					
				}
			
			}
			
			if (nextEnabled) {
				if (e.getX() > nextArrow.getPosX() && e.getY() > nextArrow.getPosY() + Settings.TWH && e.getX() < nextArrow.getWidth() + nextArrow.getPosX() && e.getY() < nextArrow.getHeight() + nextArrow.getPosY() + Settings.TWH) {
					closeNext();
					if (!validators.get(question).equals(null)) {
						validators.get(question).run();
					}
				}
			}

		}
		
	}
	
	public String removeTags(String string) {
		
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
		
		return string;
		
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	@Override
	public void mouseDragged(MouseEvent arg0) {
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
			
		
	}

	
	
}
