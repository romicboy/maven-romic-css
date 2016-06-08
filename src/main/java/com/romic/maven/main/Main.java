package com.romic.maven.main;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.romic.maven.handler.CssHandler;
import com.romic.maven.main.entity.CssEntity;

public class Main {

	static File css = new File("style.css");
	
	public static void main(String[] args) {
		CssHandler cssHandler = new CssHandler();
		List<CssEntity> cssList = cssHandler.fromCss(css);
		cssList = cssHandler.toRem(cssList);
		try {
			IOUtils.write(cssHandler.output(cssList), new FileOutputStream(css));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
