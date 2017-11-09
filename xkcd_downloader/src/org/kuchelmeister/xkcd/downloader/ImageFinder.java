package org.kuchelmeister.xkcd.downloader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class ImageFinder {
	public static final String NUM_REPLACE = "<number>";
	public static final String XKCD_URL = "https://xkcd.com/" + NUM_REPLACE + "/info.0.json";

	/**
	 * Number of tries until fetching is aborted
	 */
	public static final int ABORT_THRESHOLD = 10;

	private final List<String> imageURLs;

	public ImageFinder() {
		this.imageURLs = new LinkedList<>();
	}

	public void findImages() {
		int err_counter = 0;
		int counter = 1;
		while (err_counter < ABORT_THRESHOLD) {
			final String url = XKCD_URL.replace(NUM_REPLACE, Integer.toString(counter++));

			try {
				final JSONObject jsonObj = readJsonFromUrl(url);

				this.getImageURLs().add(jsonObj.get("img").toString());
				System.out.println(jsonObj.get("img").toString());

				err_counter = 0;
			} catch (JSONException | IOException e) {
				err_counter++;
			}
		}
	}

	public static JSONObject readJsonFromUrl(final String url) throws IOException, JSONException {
		final InputStream is = new URL(url).openStream();
		try {
			final BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			final String jsonText = readAll(rd);
			final JSONObject json = new JSONObject(jsonText);
			return json;
		} finally {
			is.close();
		}
	}

	private static String readAll(final Reader rd) throws IOException {
		final StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	/**
	 * @return the imageURLs
	 */
	public List<String> getImageURLs() {
		return imageURLs;
	}

}
