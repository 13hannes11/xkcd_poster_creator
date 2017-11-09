package org.kuchelmeister.xkcd.downloader;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

public class ImageDonwloaderTest {
	@Test
	public void noFolderExistsDownloadTest() {
		final String url = "https://imgs.xkcd.com/comics/barrel_cropped_(1).jpg";
		final String folderDIR = "testing";
		final ImageDownloader downloader = new ImageDownloader();

		final List<String> urls = new LinkedList<>();

		urls.add(url);

		final File f = new File(folderDIR);
		if (f.exists()) {
			f.delete();
		}
		downloader.downloadAllImages(urls, folderDIR);

		org.junit.Assert.assertEquals(true, f.exists());
	}

}
