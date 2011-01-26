package src;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;


@SuppressWarnings("serial")
public class Main extends JFrame {

	private String nameFile;
	private String nameFileSave;
	private JLabel labelTime;

	/**
	 * @param args
	 */
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		Main fram = new Main("TwoPassConnecterComponents");
		
	}

	public Main(String title) {

		this.nameFile = null;
		this.nameFileSave = null;

		this.build();
		this.showJFrame();
	}

	private void build() {
		JButton button = new JButton("Executar");
		JButton buttonOpen = new JButton("Abrir");
		JButton buttonSave = new JButton("Gravar");
		this.labelTime = new JLabel();
		this.setLayout(new FlowLayout());

		button.addActionListener(new ButtonActionModificar());
		buttonOpen.addActionListener(new ChooseFile());
		buttonSave.addActionListener(new ChooseFileSave());

		this.add(buttonOpen);
		this.add(buttonSave);
		this.add(button);
		this.add(this.labelTime);
	}

	public void showJFrame() {
		Dimension dimension = new Dimension(500, 70);
		this.setPreferredSize(dimension);
		this.setVisible(true);
		this.pack();
		this.toFront();
	}

	private String chooseFile(boolean open) {
		String string = null;
		JFileChooser chooser = new JFileChooser(string);
		int returnVal = open ? chooser.showOpenDialog(this) : chooser
				.showSaveDialog(this);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			string = chooser.getSelectedFile().getAbsolutePath();
			return string;
		} else {
			return null;
		}
	}

	class ButtonActionModificar implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			if (Main.this.nameFile != null && Main.this.nameFileSave != null) {
				ImageOperations op = new ImageOperations();
				op.open(Main.this.nameFile);

				op.binarization(220);
				TwoPassesConnectedComponents c = new TwoPassesConnectedComponents(
						op.wRast);
				long time = System.nanoTime();

				c.run();
				long timeFinal = System.nanoTime() - time;

				Main.this.labelTime.setText("Tempo de execução:" + timeFinal
						* 0.000000001 + "\n ");

				op.save(Main.this.nameFileSave + ".bmp");

				JOptionPane.showMessageDialog(null, "Done", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(null,
						"Escolha Primeiro a Imagem!", "Info",
						JOptionPane.INFORMATION_MESSAGE);
			}

		}
	}

	class ChooseFile implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			String file = Main.this.chooseFile(true);
			BufferedWriter outFile = null;

			if (file != null)
				try {
					Main.this.nameFile = file;
				} catch (Exception e) {
					abort("Error reading file: " + e);
				} finally {
					if (outFile != null) {
						try {
							outFile.close();
						} catch (IOException e) {
							abort("Output file close failed");
						}
					}
				}
		}

		private void abort(String msg) {
			JOptionPane.showMessageDialog(null, msg, "Error",
					JOptionPane.ERROR_MESSAGE);
		}

	}

	class ChooseFileSave implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			String file = Main.this.chooseFile(false);
			BufferedWriter outFile = null;

			if (file != null)
				try {
					Main.this.nameFileSave = file;
				} catch (Exception e) {
					abort("Error reading file: " + e);
				} finally {
					if (outFile != null) {
						try {
							outFile.close();
						} catch (IOException e) {
							abort("Output file close failed");
						}
					}
				}
		}

		private void abort(String msg) {
			JOptionPane.showMessageDialog(null, msg, "Error",
					JOptionPane.ERROR_MESSAGE);
		}
	}
}
