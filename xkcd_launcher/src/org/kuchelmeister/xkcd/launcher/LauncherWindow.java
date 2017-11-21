package org.kuchelmeister.xkcd.launcher;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;

public class LauncherWindow {

	private JFrame frmXkcdpostercreator;

	/**
	 * Launch the application.
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					final LauncherWindow window = new LauncherWindow();
					window.frmXkcdpostercreator.setVisible(true);
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public LauncherWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmXkcdpostercreator = new JFrame();
		frmXkcdpostercreator.setTitle("XKCD_Poster_Creator");
		frmXkcdpostercreator.setBounds(100, 100, 251, 251);
		frmXkcdpostercreator.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmXkcdpostercreator.getContentPane().setLayout(null);

		final JButton stichImageButton = new JButton("Stich Images");
		stichImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				try {
					org.kuchelmeister.xkcd.poster.Main.main(null);
				} catch (final IOException e1) {
					e1.printStackTrace();
				}
			}
		});
		stichImageButton.setBounds(10, 178, 215, 23);
		frmXkcdpostercreator.getContentPane().add(stichImageButton);

		final JButton downloadImagesButton = new JButton("Download Images");
		downloadImagesButton.addActionListener(new ActionListener() {
			public void actionPerformed(final ActionEvent e) {
				org.kuchelmeister.xkcd.downloader.Main.main(null);
			}
		});
		downloadImagesButton.setBounds(10, 144, 215, 23);
		frmXkcdpostercreator.getContentPane().add(downloadImagesButton);
	}
}
