package org.kuchelmeister.xkcd.downloader;

import javax.swing.JFileChooser;

public class Main {

	public static void main(final String[] args) {
		final ImageFinder finder = new ImageFinder();
		final ImageDownloader downloader = new ImageDownloader();

		JFileChooser fileChooser;
		fileChooser = new JFileChooser();

		// FileOpenDialog
		fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		fileChooser.setDialogTitle("Select direcory to save in");
		fileChooser.setMultiSelectionEnabled(false);
		final int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Aborted!");
			return;
		}

		finder.findImages();
		downloader.downloadAllImages(finder.getImageURLs(), fileChooser.getSelectedFile().getAbsolutePath());
	}

}
