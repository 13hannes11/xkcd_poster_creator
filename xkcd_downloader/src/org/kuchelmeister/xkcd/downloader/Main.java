package org.kuchelmeister.xkcd.downloader;

public class Main {

	public static void main(final String[] args) {
		final ImageFinder finder = new ImageFinder();
		final ImageDownloader downloader = new ImageDownloader();

		finder.findImages();
		downloader.downloadAllImages(finder.getImageURLs(), "C:/Users/Hannes/Pictures/xkcd/");
	}

}
