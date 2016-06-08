package com.romic.maven.handler;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.io.IOUtils;

import com.romic.maven.main.entity.CssEntity;

public class CssHandler {

	public List<CssEntity> toRem(List<CssEntity> list) {
		List<CssEntity> cssEntityList = new ArrayList<CssEntity>();
		for (CssEntity cssEntity : list) {
			Map<String, String> attribute = cssEntity.getAttribute();
			HashMap<String, String> newAttribute = new HashMap<String, String>();
			for (Entry<String, String> entry : attribute.entrySet()) {
				String key = entry.getKey().trim();
				String value = entry.getValue().trim();
				if (0 != value.indexOf("px")) {
					String[] split = value.split(" ");
					StringBuffer stringBuffer = new StringBuffer();
					for (String string : split) {
						string = string.trim();
						if (string.length() > 2 && string.substring(string.length() - 2).equals("px")) {
							stringBuffer.append(this.px2rem(string));
						} else {
							stringBuffer.append(string);
						}
						stringBuffer.append(" ");
					}
					value = stringBuffer.toString().trim();
				}
				newAttribute.put(key, value);
			}
			cssEntity.setAttribute(newAttribute);
			cssEntityList.add(cssEntity);
		}
		return cssEntityList;
	}

	public List<CssEntity> fromCss(String csString) {
		List<CssEntity> arrayList = new ArrayList<CssEntity>();
		csString = this.clearAnnotation(csString);
		String[] split = csString.split("}");
		for (String string2 : split) {
			string2 = string2.trim();
			CssEntity css = new CssEntity();
			String[] split1 = string2.split("\\{");
			String selector = split1[0].trim();
			css.setSelector(selector);
			String attributeString = split1[1].trim();
			String[] split2 = attributeString.split(";");
			Map<String, String> map = new HashMap<String, String>();
			for (String string3 : split2) {
				if (null != string3 && !string3.isEmpty()) {
					string3 = string3.trim();
					if (!string3.isEmpty()) {
						String[] split3 = string3.split(":", 2);
						map.put(split3[0].trim(), split3[1].trim());
					}
				}
			}
			css.setAttribute(map);
			arrayList.add(css);
		}
		return arrayList;
	}

	public List<CssEntity> fromCss(File file) {
		List<CssEntity> arrayList = null;
		try {
			String string = IOUtils.toString(new FileInputStream(file)).trim();
			arrayList = this.fromCss(string);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return arrayList;
	}

	/**
	 * 输出css字符串
	 * 
	 * @param cssList
	 * @return
	 */
	public String output(List<CssEntity> cssList) {
		StringBuffer stringBuffer = new StringBuffer();
		for (CssEntity cssEntity : cssList) {
			stringBuffer.append(cssEntity.getSelector() + " {\n");
			Map<String, String> attribute = cssEntity.getAttribute();
			for (Entry<String, String> entry : attribute.entrySet()) {
				stringBuffer.append("\t" + entry.getKey() + ":" + entry.getValue() + ";\n");
			}
			stringBuffer.append("}\n");
		}
		return stringBuffer.toString().trim();
	}

	/**
	 * 清除注释
	 * 
	 * @param csString
	 * @return
	 */
	private String clearAnnotation(String csString) {
		return csString.replaceAll("\\/*.*\\*/", "").trim();
	}

	/**
	 * px转rem
	 * 
	 * @param pxString
	 * @return
	 */
	private String px2rem(String pxString) {
		Double px = Double.valueOf(pxString.substring(0, pxString.length() - 2));
		double rem = (double) (Math.round(px / 45D * 10000) / 10000.0);
		String remString = (rem == 0) ? "0" : String.valueOf(rem);
		return remString + "rem";
	}
}
