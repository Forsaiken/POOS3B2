package core;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.font.TextAttribute;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.awt.image.ImageProducer;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.GrayFilter;
import javax.swing.JPanel;
import javax.swing.JTextField;

import global.Constants;
import global.Settings;

public class Sprite implements Constants, Cloneable, MouseListener, MouseMotionListener {
	
	private static final long serialVersionUID = 1L;
	
	
	// VARIABLES - BASIC PROPERTIES
	
	private int mode = 0;
	private int tabIndex;
	private int[] width  =  {0,0,0};
	private int[] height =  {0,0,0};
	private int[] posX 	 =  {0,0,0};
	private int[] posY 	 =  {0,0,0};
	private float[] alpha = {0f,1f,1f};
	
	// VARIABLES - SPEED ANIMATION
	
	private boolean animation = false;
	private float spdX, spdY, spdWidth, spdHeight, spdAlpha;
	private int[] cX = {0,0}, cY = {0,0}, cW = {0,0}, cH = {0,0}, cA = {0,0};
	
	// VARIABLES - SHAPES
	
	private Polygon poly;
	private int[] polyX;
	private int[] polyY;
	private int polyPoints;
	
	// VARIABLES - STRING
	
	private String[] string;
	private Font font;
	private Color[] fontColor;
	private int indentation = Settings.convertPositionX(20);
	private byte textFormat;
	private boolean spacedFont;
	private double fontSpacement;
	private int stringSpacement = Settings.convertPositionY(8);
	private float fontAlpha;
	
	// VARIABLES - IMAGE
	
	private BufferedImage image;
	private Image scaledImage;
	private byte shape;
	private FontMetrics metrics;
	private Graphics2D g2d;
	
	// VARIABLES - TEXT FIELD
	
	private JPanel panel;
	private JTextField textBox;
	
	private String sample;
	private Color sampleColor;
	
	// VARIABLES - COLORS
	
	private BlendComposite blendMode;	
	private Color[] color;
	
	private RadialGradientPaint radialGradient;	
	private int gradientRadius;
	private float[] gradientDistance;
	private Color[] gradientColors;
	
	// VARIABLES - STROKE
	
	private Color[] strokeColor;
	private int[] strokeSize;
	
	public Sprite clone() {
		try {
			return (Sprite) super.clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return this;
		}
	}
	
	public void draw(Graphics g) {
		
		g2d = (Graphics2D) g;
		
		if (this.animation == true) {
			
			int ct = 0;
			
			if (this.cX[0] < this.cX[1]) {
				this.posX[1] = (int) (this.posX[0] + (cX[0]+1) * this.spdX);
				cX[0]++;
			} else
				ct += 1;
			
			if (this.cY[0] < this.cY[1]) {
				this.posY[1] = (int) (this.posY[0] + (cY[0]+1) * this.spdY);
				cY[0]++;
			} else
				ct += 1;
				
			if (this.cW[0] < this.cW[1]) {
				this.width[1] = (int) (this.width[0] + (cW[0]+1) * this.spdWidth);
				cW[0]++;
			} else
				ct += 1;
			
			if (this.cH[0] < this.cH[1]) {
				this.height[1] = (int) (this.height[0] + (cH[0]+1) * this.spdHeight);
				cH[0]++;
			} else
				ct += 1;
			
			if (this.cA[0] < this.cA[1]) {
				if (this.alpha[1] + spdAlpha > 0f && this.alpha[1] + spdAlpha < 1f)
					this.alpha[1] += this.spdAlpha;
				else
					this.alpha[1] = this.alpha[2];
				cA[0]++;
			} else
				ct += 1;
			
			if (ct == 5) {
				this.animation = false;
				this.resetCountAnimation();
			}
			
			if (this.radialGradient != null) {
				this.radialGradient = new RadialGradientPaint(this.posX[1] + this.width[1]/2,this.posY[1] + this.height[1]/2, gradientRadius, gradientDistance, gradientColors, CycleMethod.NO_CYCLE);
			}
			
			if (textBox != null) {
				textBox.setBounds(this.posX[1] + Settings.convertPositionX(20), this.posY[1], this.width[1] - Settings.convertWidth(40), this.height[1]);
			}
		}
		
		
		if (blendMode != null)
				g2d.setComposite(blendMode.derive(alpha[1]));
			else
				g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha[1]));
		
		if (image != null){
			 
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.drawImage(scaledImage, posX[1], posY[1], width[1], height[1], null);
			
		}
		
				
		if (shape == Constants.RECT) {
				
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
				
			if (this.radialGradient != null) {
				g2d.setPaint(this.radialGradient);
			} else {
				g2d.setColor(color[mode]);
			}
				
			g2d.fillRect(posX[1], posY[1], width[1], height[1]);
				
			if (strokeColor != null) {
				g2d.setStroke(new BasicStroke(strokeSize[mode]));
				g2d.setColor(strokeColor[mode]);
				g2d.drawRect(posX[1], posY[1], width[1], height[1]);
			}
			
		} else if (shape == Constants.POLYGON) {
			
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (this.radialGradient != null) {
				g2d.setPaint(this.radialGradient);
			} else {
				g2d.setColor(color[mode]);
			}
				
			g2d.fillPolygon(this.updatePolygon(posX[1],polyX), this.updatePolygon(posY[1],polyY), polyPoints);
				
			if (strokeColor != null) {
				g2d.setStroke(new BasicStroke(strokeSize[mode]));
				g2d.setColor(strokeColor[mode]);
				g2d.drawPolygon(this.updatePolygon(posX[1],polyX), this.updatePolygon(posY[1],polyY), polyPoints);
			}
			
		}
				
		if (string != null){
					
			g2d.setRenderingHint( RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (spacedFont == true) {
				Map<TextAttribute, Object> attributes = new HashMap<TextAttribute, Object>();
				attributes.put(TextAttribute.TRACKING, this.fontSpacement );
				this.font = font.deriveFont(attributes);
			}
			
			g2d.setFont(font);
			g2d.setColor(fontColor[mode]);
			
			for(int i = 0; i < string.length ; i++) {
				
				if (shape == Constants.RECT) {
					if (textFormat == Constants.CENTER)
						g2d.drawString(string[i], posX[1] + width[1]/2 - this.getStringWidth(g,i)/2, posY[1] + height[1]/2 + this.getStringHeight(g,i)/2 + i * (this.stringSpacement + this.getStringHeight(g,i)));
					else if (textFormat == Constants.LEFT_TO_RIGHT)
						g2d.drawString(string[i], posX[1] + indentation, posY[1] + height[1]/2 + this.getStringHeight(g,i)/2 + i * (this.stringSpacement + this.getStringHeight(g,i)));				
				} else {
					if (textFormat == Constants.CENTER)
						g2d.drawString(string[i], posX[1] - this.getStringWidth(g,i)/2, posY[1] + this.getStringHeight(g,i)/2 + i * (this.stringSpacement + this.getStringHeight(g,i)));
					else 
						g2d.drawString(string[i], posX[1], posY[1] + this.getStringHeight(g,i)/2 + i * (this.stringSpacement + this.getStringHeight(g,i)));
				}
			}
		}
		
		if (textBox != null) {
			textBox.repaint();
		}
	}
	
	// SETS - PROPERTIES
	
	public void setMode(int mode) {
		this.mode = mode;
	}
	
	public void setTab(int index) {
		tabIndex = index;
	}
	// SETS - GEOMETRIC SHAPES
	
	public void setFillRect(int width, int height, Color[] color){
		
		this.width[2] = width;
		this.height[2] = height;
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
		this.shape = Constants.RECT;
		this.color = color;
		
	}
	
	public void setFillPolygon (int[] constX, int[] constY, Color[] color, float alpha) {
		polyX = constX;
		polyY = constY;
		polyPoints = constX.length;
		this.color = color;
		this.alpha[1] = alpha;
		this.alpha[2] = alpha;
		this.shape = Constants.POLYGON;
		
		int lowX = 0, highX = 0, lowY = 0, highY = 0;
		
		
		for (int i = 0; i < constX.length; i++) {
			if (constX[i] < lowX)
				lowX = constX[i];
			else if (constX[i] > highX)
				highX = constX[i];
			
			if (constY[i] < lowY)
				lowY = constY[i];
			else if (constY[i] > highY)
				highY = constY[i];	
		}
		
		this.width[2] = highX - lowX;
		this.height[2] = highY - lowY;
		
		this.width[1] = highX - lowX;
		this.height[1] = highY - lowY;
		
	}
	
	public void setStroke(int[] size, Color[] color) {
			this.strokeColor = color;
			this.strokeSize = size;
	}
	
	public int[] updatePolygon(int position, int[] constants) {
		
		int[] pos = new int[polyPoints];
		
		for(int i = 0 ; i < pos.length; i++) {
			pos[i] = position + constants[i];
		}
		
		return pos;
	}
	
	// SETS - IMAGE
	
	public void setImage(String path){
		try {
			image = ImageIO.read(new File(path));
			scaledImage = image.getScaledInstance(Settings.convertWidth(image.getWidth()), Settings.convertHeight(image.getHeight()), Image.SCALE_SMOOTH);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Arquivo (" + path + ") não encontrado!");
		}
		
		this.width[2] = Settings.convertWidth(image.getWidth());
		this.height[2] = Settings.convertHeight(image.getHeight());
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}
	
	public void redimensionImageByWidth(int width) {
		float scaledHeight = image.getHeight() * ((float)width / (float)image.getWidth());
		scaledImage = image.getScaledInstance(width, (int) scaledHeight, Image.SCALE_SMOOTH);
		this.width[2] = width;
		this.height[2] = (int) scaledHeight;
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}
	
	public void redimensionImageByHeight(int height) {
		float scaledWidth = image.getWidth() * ((float)height / (float)image.getHeight());
		scaledImage = image.getScaledInstance((int) scaledWidth, height, Image.SCALE_SMOOTH);
		this.width[2] = (int) scaledWidth;
		this.height[2] = height;
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}

	// SETS - STRING
	
	public void setString(String string, Font font, Color[] color, float alpha){
		
		this.string = string.split("\n");
		this.font = font;
		this.fontColor = color;
		this.alpha[2] = alpha;
		this.alpha[1] = alpha;
	}
	
	public void setStringName(String string){
		this.string = string.split("\n");
	}

	public void setSpacementString(double spacement){
		this.spacedFont = true;
		this.fontSpacement = spacement;
	}
	
	public void setParagraphSpacement(int spacement) {
		this.stringSpacement = spacement;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setFormatString(byte formatText) {
		this.textFormat = formatText;
	}
	
	// SETS - TEXT FIELD
	
	public void setTextBox(String sample, Font font, Color sampleColor, Color typeColor) {
		
		this.font = font;
		this.sample = sample;
		this.fontColor = new Color[] {sampleColor};
		
		textBox = new JTextField("");
		textBox.setOpaque(false);
		textBox.setBorder(null);
		textBox.setBounds(this.posX[1] + Settings.convertPositionX(20), this.posY[1], this.width[1] - Settings.convertWidth(40), this.height[1]);
		textBox.setForeground(typeColor);
		textBox.setFont(font);
	}
	
	public void setTextBox(Color typeColor) {
		textBox = new JTextField("");
		this.sample = string[0];
		textBox.setOpaque(false);
		textBox.setBorder(null);
		textBox.setBounds(this.posX[1] + Settings.convertPositionX(20), this.posY[1], this.width[1] - Settings.convertWidth(40), this.height[1]);
		textBox.setForeground(typeColor);
		textBox.setFont(font);
		
		if (this.textFormat == Constants.CENTER)
			textBox.setHorizontalAlignment(JTextField.CENTER);
		else if (this.textFormat == Constants.LEFT_TO_RIGHT)
			textBox.setHorizontalAlignment(JTextField.LEFT);
	}
	
	public void add(JPanel panel) {
		panel.add(textBox);
	}
	
	public void remove(JPanel panel) {
		panel.remove(textBox);
	}

	public void setSample() {
		this.string[0] = sample;
	}
	
	
	// SETS - LOCATION

	public void setInitialLocation(int x, int y) {
		this.posX[0] = x;
		this.posY[0] = y;
	}	
	
	public void setLocation(int x, int y) {
		this.posX[1] = x;
		this.posY[1] = y;
		this.posX[2] = x;
		this.posY[2] = y;
	}
	
	public void setFinalLocation(int x, int y) {
		this.posX[2] = x;
		this.posY[2] = y;
	}
	
	public void setLocationToInitial() {
		this.posX[1] = this.posX[0];
		this.posY[1] = this.posY[0];
	}
	
	public void setLocationToFinal() {
		this.posX[1] = this.posX[2];
		this.posY[1] = this.posY[2];
	}
	
	public void reverseLocation() {

		int auxX = this.posX[0];
		int auxY = this.posY[0];
		
		this.posX[0] = this.posX[2];
		this.posY[0] = this.posY[2];
		
		this.posX[2] = auxX;
		this.posY[2] = auxY;
	}
	
	// SETS - POSITION X
	
	public void setInitialPosX(int x) {
		this.posX[0] = x;
	}
	
	public void setPosX(int x) {
		this.posX[1] = x;
	}
	
	public void setFinalPosX(int x) {
		this.posX[2] = x;
	}
	
	public void setPosXToInitial() {
		this.posX[1] = this.posX[0];
	}
	
	public void setPosXToFinal() {
		this.posX[1] = this.posX[2];
	}
	
	// SETS - POSITION Y
	
	public void setInitialPosY(int y) {
		this.posY[0] = y;
	}
	
	public void setPosY(int y) {
		this.posY[1] = y;
	}
	
	public void setFinalPosY(int y) {
		this.posY[2] = y;
	}
	
	public void setPosYToInitial() {
		this.posY[1] = this.posY[0];
	}
	
	public void setPosYToFinal() {
		this.posY[1] = this.posY[2];
	}
	
	// SETS - DIMENSION
		
	public void setInitialDimension(int width, int height){
		this.width[0] = width;
		this.height[0] = height;
	}
	
	public void setDimension(int width, int height){
		this.width[1] = width;
		this.height[1] = height;
	}
	
	public void setFinalDimension(int width, int height){
		this.width[2] = width;
		this.height[2] = height;
	}
	
	public void setDimensionToInitial() {
		this.width[1] = this.width[0];
		this.height[1] = this.height[0];
	}
	
	public void setDimensionToFinal() {
		this.width[1] = this.width[2];
		this.height[1] = this.height[2];
	}
	
	// SETS - WIDTH
	
	public void setInitialWidth(int width){
		this.width[0] = width;
	}
	
	public void setWidth(int width){
		this.width[1] = width;
	}
	
	public void setFinalWidth(int width){
		this.width[2] = width;
	}
	
	public void setWidthToInitial() {
		this.width[1] = this.width[0];
	}
	
	public void setWidthToFinal() {
		this.width[1] = this.width[2];
	}
	
	// SETS - HEIGHT
	
	public void setInitialHeight(int height){
		this.height[0] = height;
	}
	
	public void setHeight(int height){
		this.height[1] = height;
	}
	
	public void setFinalHeight(int height){
		this.height[2] = height;
	}
	
	public void setHeightToInitial() {
		this.height[1] = this.height[0];
	}
	
	public void setHeightToFinal() {
		this.height[1] = this.height[2];
	}
	
	// SETS - ALPHA
	
	public void setVisible (boolean visibility) {
		if (visibility == true){
			this.alpha[1] = 1f;
		} else {
			this.alpha[1] = 0f;
		}
	}

	public void setInitialAlpha(float alpha){
		this.alpha[0] = alpha;
	}
	
	public void setAlpha(float alpha){
		this.alpha[1] = alpha;
	}
	
	public void setFinalAlpha(float alpha) {
		this.alpha[2] = alpha;
	}
	
	public void setAlphaToInitial() {
		this.alpha[1] = this.alpha[0];
	}
	
	public void setAlphaToFinal() {
		this.alpha[1] = this.alpha[2];
	}
	
	public void reverseAlpha() {
		float aux = this.alpha[0];
		this.alpha[0] = this.alpha[2];
		this.alpha[2] = aux;
	}
	
	// SETS - ANIMATION
	
	public void setBlendMode(BlendComposite blend) {
		this.blendMode = blend;
	}
	
	public void setAnimation (boolean animation){
		this.animation = animation;
	}
	
	public void setMotionAnimation(float posX, float posY, float width, float height, float alpha){
		if (posX != 0) {
			this.spdX = (this.posX[2] - this.posX[0]) / (posX * Settings.FPS);
			this.cX[1] = (int) (Settings.FPS * posX);
		} else { this.cX[1] = 0;
		} if (posY != 0) {
			this.spdY = (this.posY[2] - this.posY[0]) / (posY * Settings.FPS);
			this.cY[1] = (int) (Settings.FPS * posY);
		} else { this.cY[1] = 0;
		} if (width != 0) {
			this.spdWidth = (this.width[2] - this.width[0]) / (width * Settings.FPS);
			this.cW[1] = (int) (Settings.FPS * width);
		} else { this.cW[1] = 0;
		} if (height != 0) {
			this.spdHeight = (this.height[2] - this.height[0]) / (height * Settings.FPS);
			this.cH[1] = (int) (Settings.FPS * height);
		} else { this.cH[1] = 0;
		} if (alpha != 0) {
			this.spdAlpha = (this.alpha[2] - this.alpha[0]) / (alpha * Settings.FPS);
			this.cA[1] = (int) (Settings.FPS * alpha);
		} else { this.cA[1] = 0;
		}
	}
	
	public void resetCountAnimation () {
		this.cX[0] = 0;
		this.cY[0] = 0;
		this.cW[0] = 0;
		this.cH[0] = 0;
		this.cA[0] = 0;
	}
	
	// SETS - COLORS
	
	public void setColor (Color[] color){
		this.color = color;
	}
	
	public void setFontColor (Color[] color) {
		this.fontColor = color;
	}
	
	public void setStrokeColor (Color[] color) {
		this.strokeColor = color;
	}
	
	public void setRadialGradient (int radius, float[] distance, Color[] colors) {
		this.gradientRadius = radius;
		this.gradientDistance = distance;
		this.gradientColors = colors;
		this.radialGradient = new RadialGradientPaint(this.posX[1] + this.width[1]/2,this.posY[1] + this.height[1]/2, radius, distance, colors, CycleMethod.NO_CYCLE);
	}

	public void grayScale() {
		ImageFilter filter = new GrayFilter(true, 0);  
		ImageProducer producer = new FilteredImageSource(scaledImage.getSource(), filter);  
		scaledImage = Toolkit.getDefaultToolkit().createImage(producer);
	}
	
	// GETS - PROPERTIES
	
	public int getMode() {
		return mode;
	}
	
	public int getTab() {
		return tabIndex;
	}
	// GETS - STRING
	
	public String[] getString() {
		return this.string;
	}
	
	public Font getFont() {
		return this.font;
	}
	
	public int getStringWidth(Graphics g){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		return metrics.stringWidth(string[0]);
	}
	
	public int getStringWidth(Graphics g, int index){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		return metrics.stringWidth(string[index]);
	}
	
	public int getStringHeight(Graphics g){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		int height = (int)font.createGlyphVector(metrics.getFontRenderContext(), string[0]).getVisualBounds().getHeight();	
		return height;
	}
	
	public int getStringHeight(Graphics g, int index){
		g2d = (Graphics2D) g;
		metrics = g2d.getFontMetrics(font);
		int height = (int)font.createGlyphVector(metrics.getFontRenderContext(), string[index]).getVisualBounds().getHeight();	
		return height;
	}
	
	// GETS - LOCATION
	
	public int getInitX() {
		return this.posX[0];
	}
	
	public int getPosX() {
		return this.posX[1];
	}
	
	public int getFinalX() {
		return this.posX[2];
	}
	
	public int getInitY() {
		return this.posY[0];
	}
	
	public int getPosY() {
		return this.posY[1];
	}
	
	public int getFinalY() {
		return this.posY[2];
	}
		
	// GETS - DIMENSION
	
	public int getInitWidth() {
		return width[0];
	}
	
	public int getWidth() {
		return width[1];
	}
	
	public int getFinalWidth() {
		return width[2];
	}
	
	public int getInitHeight() {
		return height[0];
	}
	
	public int getHeight() {
		return height[1];
	}
	
	public int getFinalHeight() {
		return height[2];
	}
	
	// GETS - ALPHA
	
	public float getInitAlpha() {
		return alpha[0];
	}
	
	public float getAlpha() {
		return alpha[1];
	}
	
	public float getFinalAlpha() {
		return alpha[2];
	}
	
	// GETS - ANIMATION
	
	public boolean getAnimation() {
		return this.animation;
	}
	
	// GETS - IMAGE
	
	public Image getImage(){
		return scaledImage;
	}

	// GETS - TEXTBOX
	
	public JTextField getTextBox() {
		return this.textBox;
	}
	
	// MOUSE - ACTIONS
		public void setMouseListener(Display window) {
		window.addMouseListener(this);
		window.addMouseMotionListener(this);
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}
	
	@Override
	public void mouseDragged(MouseEvent e) {		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if (e.getX() > this.posX[1] && e.getY() > this.posY[1] + Settings.TWH && e.getX() < this.width[1] + this.posX[1] && e.getY() < this.height[1] + this.posY[1] + Settings.TWH) {
			if (mode != 2) {
				mode = 1;
			}
		} else {
			if (mode == 1) {
				mode = 0;
			}
		}
	}
	
}
