package com.taogen.commons;

import com.taogen.commons.io.FileUtils;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.*;

public class I18nUtils
{
	private static final String PACKAGE_PATH = "i18n";
	// web
	private static final String DIRECTORY_PATH = "i18n";
	//local
//	private static final String DIRECTORY_PATH = "src/i18n";


	private static List<ResourceBundle> load(Locale locale) throws URISyntaxException {
		List<ResourceBundle> bundleList = new ArrayList<>();
		// web
		URL url = FileUtils.class.getClassLoader().getResource(DIRECTORY_PATH);
		File dir = Paths.get(url.toURI()).toFile();

		// local
//		File dir = new File(DIRECTORY_PATH);

		if (dir.exists())
		{
			Set<String> baseNameSet = new HashSet<>();
			File[] files = dir.listFiles();
			for (File f : files)
			{
				String fileName = f.getName();
				int index = fileName.lastIndexOf("_");
				if (index != -1)
				{
					continue;
				}
				index = fileName.indexOf(".");
				String baseName = PACKAGE_PATH + "." + fileName.substring(0, index);
				baseNameSet.add(baseName);
			}

			for (String baseName : baseNameSet)
			{
				ResourceBundle bundle;
				try
				{
					bundle = ResourceBundle.getBundle(baseName, locale);
					bundleList.add(bundle);
				}
				catch (MissingResourceException e)
				{
					e.printStackTrace();
				}
			}
		}
		return bundleList;
	}

	public static String getMessage(String code, Locale locale) throws URISyntaxException {
		if (code == null)
		{
			return null;
		}

		if (locale == null)
		{
			locale = Locale.getDefault();
		}

		List<ResourceBundle> bundleList = load(locale);

		for (ResourceBundle b : bundleList)
		{
			if (b.containsKey(code))
			{
				String result = b.getObject(code).toString();
				return result;
			}
		}
		return null;
	}

	@SuppressWarnings("static-access")
	public static String getMessageFormat(String code, Locale locale, Object... objs) throws URISyntaxException {
		if (code == null)
		{
			return null;
		}

		if (locale == null)
		{
			locale = Locale.getDefault();
		}

		List<ResourceBundle> bundleList = load(locale);

		for (ResourceBundle b : bundleList)
		{
			if (b.containsKey(code))
			{
				String result = b.getObject(code).toString();
				result = result.format(result, objs);
				return result;
			}
		}
		return null;
	}

	public static String getMessage(String code) throws URISyntaxException {
		if (code == null)
		{
			return null;
		}

		Locale locale = Locale.getDefault();

		List<ResourceBundle> bundleList = load(locale);

		for (ResourceBundle b : bundleList)
		{
			if (b.containsKey(code))
			{
				String result = b.getObject(code).toString();
				return result;
			}
		}
		return null;
	}
}
