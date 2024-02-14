import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

import javax.swing.JFrame;
public class FrameForReplay {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		JFrame obj = new JFrame();
		Replay replay = new Replay();
		obj.setBounds(0, 0, 1200, 800);
		obj.setTitle("SnakeGA");
		obj.setResizable(false);
		obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		obj.add(replay);
		obj.setVisible(true);
	}

}
