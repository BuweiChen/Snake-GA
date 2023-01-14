import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;

public class FrameForTraining {
	public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
		// TODO Auto-generated method stub
		JFrame obj = new JFrame();
		Gameplay gameplay = new Gameplay();
		obj.setBounds(0, 0, 1200, 800);
		obj.setTitle("SnakeGA");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(gameplay);
		obj.setVisible(true);
	}
}
