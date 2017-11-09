package org.kuchelmeister.xkcd.downloader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class ImageDownloader {

	List<String> downloadedImages;

	public ImageDownloader() {
		downloadedImages = new LinkedList<>();
	}

	private void downloadImage(final String url, final String folderPath, final int counter) throws IOException {
		final InputStream in = new URL(url).openStream();
		final String fileName = "Comic_" + counter;// url.substring(url.lastIndexOf("/")).replaceAll("[^A-Za-z0-9.]",
													// "");

		downloadedImages.add(folderPath + fileName);

		Files.copy(in, Paths.get(folderPath + fileName));

		System.out.println("Saved: " + folderPath + fileName);
	}

	public void downloadAllImages(final List<String> images, final String folderPath) {
		for (int i = 0; i < images.size(); i++) {
			try {
				this.downloadImage(images.get(i), folderPath, i);
			} catch (final IOException e) {
				e.printStackTrace();
				return;
			}
		}
	}
}
