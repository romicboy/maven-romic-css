package com.meimi.maven.main;

import java.io.File;
import java.util.List;

import com.meimi.maven.handler.CssHandler;
import com.meimi.maven.main.entity.CssEntity;

public class Main {

	public static void main(String[] args) {
		CssHandler cssHandler = new CssHandler();
		List<CssEntity> cssList = cssHandler.fromCss(new File("css/css.css"));
		cssList = cssHandler.toRem(cssList);
		System.out.println(cssHandler.output(cssList));
	}

}
