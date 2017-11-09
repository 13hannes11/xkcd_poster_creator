package org.kuchelmeister.xkcd.poster;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.kuchelmeister.xkcd.poster.construction.ImageArranger;
import org.kuchelmeister.xkcd.poster.construction.ImageDataLoader;
import org.kuchelmeister.xkcd.poster.construction.ImageSticher;
import org.kuchelmeister.xkcd.poster.rectangle.imagepath.PathRectangle;

public class Main {

	private double aspectRatio;
	private Collection<File> inputImages;
	private File outputImagePath;

	public Main() {
		this.aspectRatio = 3D / 2D;
	}

	public Main(final double outputAspectRatio) {
		this.aspectRatio = outputAspectRatio;
	}

	public Main(final Collection<File> input, final File output) {
		this.inputImages = input;
		this.outputImagePath = output;
	}

	public Main(final Collection<File> input, final File output, final double outputAspectRatio) {
		this.aspectRatio = outputAspectRatio;
		this.inputImages = input;
		this.outputImagePath = output;
	}

	public void selectFilesDialog() {
		this.inputImages = new LinkedList<>();

		JFileChooser fileChooser;
		fileChooser = new JFileChooser();

		// FileOpenDialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
		fileChooser.setDialogTitle("Load Images File or Directory");
		fileChooser.setMultiSelectionEnabled(true);
		int returnVal = fileChooser.showOpenDialog(null);

		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Aborted!");
			return;
		}

		for (final File file : fileChooser.getSelectedFiles()) {
			inputImages.add(file);
		}

		// FileSaveDialog
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		fileChooser.setDialogTitle("Save Image");
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileFilter() {

			@Override
			public String getDescription() {
				return ".png";
			}

			@Override
			public boolean accept(final File f) {
				return true;
			}
		});

		returnVal = fileChooser.showSaveDialog(null);
		if (returnVal != JFileChooser.APPROVE_OPTION) {
			System.out.println("Aborted!");
			return;
		}
		if (!fileChooser.getSelectedFile().getName().endsWith(".png")) {
			fileChooser.setSelectedFile(new File(fileChooser.getSelectedFile().getAbsolutePath() + ".png"));
		}

		this.outputImagePath = fileChooser.getSelectedFile();
	}

	public void run() {
		if (inputImages == null || inputImages.size() == 0 || outputImagePath == null) {
			return;
		}

		System.out.println("Loading Now!");
		final ImageDataLoader loader = new ImageDataLoader();

		Collection<PathRectangle> images;
		if (inputImages.size() == 1 && ((File) inputImages.toArray()[0]).isDirectory()) {
			images = loader.load(((File) inputImages.toArray()[0]));
		} else {
			images = loader.load(inputImages);
		}

		System.out.println("Arranging Now!");
		final ImageArranger arranger = new ImageArranger();
		images = arranger.arrangeRectangles(images, aspectRatio);
		System.out.println("Count: " + images.size());

		System.out.println("Saving Now!");
		final ImageSticher sticher = new ImageSticher(images);

		try {
			// TODO: take scaling into account
			sticher.saveImage(outputImagePath);
		} catch (final IOException e) {
			// TODO: catch exception in appropriate way
			e.printStackTrace();
		}

		System.out.println("DONE!");
	}

	public static void main(final String[] args) throws IOException {
		final Main main = new Main();
		main.selectFilesDialog();
		main.run();
	}
}
