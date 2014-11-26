package davidgbe_CSCI201_Assignment5;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.omg.CORBA.portable.InputStream;

public class Worker extends Thread {
	private Task currentTask;
	private Instruction currentIns;
	private Image img;
	private String target = "Task Board";
	private Factory factory;
	private int x = 70;
	private int y = 90;
	public static final int xOffset = 0;
	public static final int yOffset = 0;
	private int quantityToTake;
	private int time;
	public final int speed = 2;
	public final int margin = 2;
	private boolean canProceed = true;
	
	private TreeMap<String, Integer> tools = new TreeMap<String, Integer>();
	
	public Worker(String imagePath, Factory f) {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Image image = tk.createImage(imagePath);
		this.img = image;
		this.factory = f;
		System.out.println(image);
	}
	
	public void addTool(String toolName, int quantity) {
		if(this.tools.containsKey(toolName)) {
			this.tools.put(toolName, this.tools.get(toolName).intValue() + quantity);
		} else {
			this.tools.put(toolName, new Integer(quantity));
		}
	}
	
	public void returnTool(String toolName, int quantity) {
		if(this.tools.containsKey(toolName)) {
			this.tools.put(toolName, this.tools.get(toolName).intValue() - quantity);
		} else {
			//throw
			System.out.println("Didn't have tool!");
		}
	}
	
	public void addTask(Task newTask) {
		this.currentTask = newTask;
	}
	
	public Task getCurrentTask() {
		return this.currentTask;
	}
	
	public Image getImage() {
		return img;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setTarget(String t) {
		this.target = t;
	}
	
	public void interact() {
		if(target.equals("Task Board")) {
			if(currentTask == null || currentTask.complete()) {
				if(currentTask != null && currentTask.complete()) {
					factory.setAsComplete(currentTask.getId(), currentTask.getTaskName());
				}
				currentTask = this.factory.getFreshTask();
				if(currentTask != null) {
					factory.setAsInProgress(currentTask.getId(), currentTask.getTaskName());
				}
			}
		} else if(factory.getAllItems().get(target) instanceof Resource){
			canProceed = ((Resource)factory.getAllItems().get(target)).takeResource(quantityToTake);
			System.out.println("QUANTITY:" + quantityToTake);
		} else if(factory.getAllItems().get(target) instanceof Tool){
			if(!currentIns.needToReturnTools()) {
				for(int i = 0; i < quantityToTake; i++) {
					((Tool)factory.getAllItems().get(target)).takeTool();
					this.addTool(target, 1);
				}
			} else {
				for(int i = 0; i < quantityToTake; i++) {
					((Tool)factory.getAllItems().get(target)).returnTool();
					this.returnTool(target, 1);
				}
			}
		} else if(factory.getAllItems().get(target) instanceof WorkArea){
			WorkArea wa = ((WorkArea)factory.getAllItems().get(target));
			wa.startCountDown(time);
		}
	}
	
	public void getNextTarget() {
		if(currentTask.hasNextMaterial()) {
			target = currentTask.getNextMaterial();
			System.out.println("MATERIAL: " + target);
			for(int i = 0; i < currentTask.getMaterials().size(); i++) {
				if(currentTask.getMaterials().get(i).name.equals(target)) {
					quantityToTake = currentTask.getMaterials().get(i).quantity;
				}
			}
		} else if(currentTask.hasNextInstruction() || (currentIns != null && !currentIns.complete) ) {
			if(currentIns == null || currentIns.complete) {
				this.currentIns = currentTask.getNextInstruction();
				time = this.currentIns.getTime();
			}
			if(this.currentIns.needsTool()) {
				target = this.currentIns.nextTool();
				quantityToTake = this.currentIns.quantityForTool(target);
				if(target.equals("Paintbrush")) {
					target += "es";
				} else if(target.equals("Pliers") || target.equals("Screwdrivers") || target.equals("Scissors")) {
					
				} else {
					target += "s";
				}
				System.out.println("TOOL: " + target);
			} else if(!currentIns.needToReturnTools()){
				target = this.currentIns.location();
				if(target.equals("Paintingstation")) {
					target = "Painting Station";
				}
				target += factory.getWorkAreaNum(target);
				System.out.println("LOCATION: " + target);
			} else {
				target = "Home";
			}
		} else {
			target = "Task Board";
		}
		System.out.println("TARGET:" + target);
	}
	
	public void moveToward(String t) {
		int targetX = 0;
		int targetY = 0;
		if(t.equals("Task Board")) {
			targetX = 570;
			targetY = 85;
		} else if(t.equals("Home")) {
			targetX = 70;
			targetY = 90;
		} else {
			Item tt = this.factory.getAllItems().get(t);
			targetX = tt.getX();
			targetY = tt.getY();
			if(tt instanceof Resource) {
				targetX += 20;
				targetY += 70;
			} else if(tt instanceof Tool) {
				targetX += 75;
				targetY += 110;
			} else if(tt instanceof WorkArea) {
				targetX += 125;
				targetY += 70;
			}
		}
		
		if(targetY - margin <= getY() - yOffset && targetY + margin >= getY() - yOffset && targetX - margin <= getX() + xOffset && targetX + margin >= getX() + xOffset) {
			if(canProceed) {
				interact();
			}
			if(currentTask != null && canProceed) {
				getNextTarget();
			}
		} else if(getX() < 100) {
			if(targetY - margin <= getY() - yOffset && targetY + margin >= getY() - yOffset) {
				if(targetX > getX() + xOffset) {
					setX(getX() + this.speed);
				} else {
					setX(getX() - this.speed);
				}
			}
			else if(targetY > getY() - yOffset) {
				setY(getY() + this.speed);
			} else {
				setY(getY() - this.speed);
			} 
		} else {
			if(targetX - margin <= getX() + xOffset && targetX + margin >= getX() + xOffset) {
				if(targetY > getY() - yOffset) {
					setY(getY() + this.speed);
				} else {
					setY(getY() - this.speed);
				}
			}
			else if(targetX > getX() + xOffset) {
				setX(getX() + this.speed);
			} else {
				setX(getX() - this.speed);
			}
		}
	}
	
	public void run() {
		while(true) {
			if(!this.factory.getAllItems().containsKey(target) && !target.equals("Task Board") && !target.equals("Home")) {
				System.out.println("FAULTY TARGET: " + target);
			}
			if(target != null && (target.equals("Task Board") || target.equals("Home") || this.factory.getAllItems().containsKey(target))) {
				moveToward(target);
				try {
					Thread.sleep(16);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
