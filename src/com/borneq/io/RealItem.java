package com.borneq.io;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.Icon;
import javax.swing.filechooser.FileSystemView;

import org.apache.commons.io.FilenameUtils;

public class RealItem implements Item {
	private File file;
	private static FileSystemView fileSystemView = FileSystemView
			.getFileSystemView();

	@Override
	public int compareTo(Item o) {
		return file.compareTo(((RealItem) o).file);
	}

	public RealItem(File file) {
		this.file = file;
	}

	public RealItem(String path) {
		file = new File(path);
	}

	@Override
	public String getCanonicalPath() {
		try {
			return file.getCanonicalPath();
		} catch (IOException e) {
			return null;
		}
	}

	@Override
	public String getRealDir() {
		String path = getCanonicalPath();
		if (file.isDirectory())
			return path;
		else
			return FilenameUtils.getFullPathNoEndSeparator(path);
	}

	File getFile() {
		return file;
	}

	@Override
	public Icon getIcon() {
		return fileSystemView.getSystemIcon(file);
	}

	@Override
	public List<Item> getItems() {
		return getItems(!isFileSystemRoot());
	}

	@Override
	public List<Item> getItems(boolean twoDots) {
		File[] files = fileSystemView.getFiles(getFile(), false);
		List<Item> items = new ArrayList<Item>();
		for (File file : files) {
			items.add(new RealItem(file));
		}
		Collections.sort(items);
		if (twoDots)
			items.add(0, new RealItem(".."));
		return items;
	}

	@Override
	public String getName() {
		return file.getName();
	}

	@Override
	public Item getParentDirectory() {
		return new RealItem(file.getParentFile());
	}

	@Override
	public boolean isDirectory() {
		return file.isDirectory();
	}

	@Override
	public boolean isFileSystemRoot() {
		return fileSystemView.isFileSystemRoot(file);
	}

	@Override
	public long lastModified() {
		return file.lastModified();
	}

	@Override
	public long length() {
		return file.length();
	}
}
