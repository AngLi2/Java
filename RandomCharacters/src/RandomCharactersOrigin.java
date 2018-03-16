import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class RandomCharactersOrigin extends JApplet implements Runnable,ActionListener{
	private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private JLabel outputs[];
	private JCheckBox checkboxes[];
	private final static int SIZE = 3;
	private Thread threads[];
	private boolean suspended[];
	public void init() {
		outputs = new JLabel[SIZE];
		checkboxes = new JCheckBox[SIZE];
		threads = new Thread[SIZE];
		suspended = new boolean[SIZE];
		Container c = getContentPane();
		c.setLayout(new GridLayout(SIZE, 2, 5, 5));
		for(int i = 0; i < SIZE; i++) {
			outputs[i] = new JLabel();
			outputs[i].setBackground(Color.green);
			outputs[i].setOpaque(true);
			c.add(outputs[i]);
			checkboxes[i] = new JCheckBox("Suspended");
			checkboxes[i].addActionListener(this);
			c.add(checkboxes[i]);
		}
	}
	public void start() {
		//create threads and start every time start is called
		for(int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(this, "Thread" +(i+1));
			threads[i].start();
		}
	}
	public void run() {
		Thread currentThread = Thread.currentThread();
		int index = getIndex(currentThread);
		char displayChar;
		while(threads[index] == currentThread) {
			//sleep from 0 to 1 second
			try {
				Thread.sleep((int)(Math.random()*1000));
				synchronized(this) {
					while(suspended[index] && threads[index] ==currentThread) wait();
				}
			}catch(InterruptedException e) {
				System.out.println("sleep interrupted");
			}
			displayChar = alphabet.charAt((int)(Math.random()*26));
			outputs[index].setText(currentThread.getName()+": "+displayChar);
		}
		System.err.println(currentThread.getName()+"terminating");
	}
	private int getIndex(Thread current) {
		for(int i = 0; i< threads.length; i++)
			if(current == threads[i]) return i;
		return -1;
	}
	public synchronized void stop() {
		//stop thread every time stop is called
		//as the user browses another Web page
		for(int i = 0; i < threads.length; i++) threads[i] = null;
		notifyAll();
	}
	public synchronized void actionPerformed(ActionEvent e) {
		for(int i = 0; i < checkboxes.length; i++) {
			if(e.getSource() == checkboxes[i]) {
				suspended[i] = !suspended[i];
				outputs[i].setBackground(!suspended[i] ? Color.green : Color.red);
				if(!suspended[i]) notify();
				return;
			}
		}
	}
}
