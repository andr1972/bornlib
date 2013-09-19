package com.borneq.io;

import java.util.List;

import javax.swing.Icon;

public interface Item extends Comparable<Item> {
	String getCanonicalPath();
	String getRealDir();
	Icon getIcon();
	List<Item> getItems();
	List<Item> getItems(boolean twoDots);
	String getName();
	Item getParentDirectory();
	boolean isDirectory();
	boolean isFileSystemRoot();
	long lastModified();
	long length();
}
