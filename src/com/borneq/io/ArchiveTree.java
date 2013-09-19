package com.borneq.io;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.ImageIcon;

import org.apache.commons.io.FilenameUtils;

public class ArchiveTree {
	private enum State {
		SET, GET
	}

	private State state;
	private List<ArchiveItem> itemPaths;
	ArchiveItem root;
	private int itemIndex;
	private String archiveName;

	static ImageIcon folderIcon;
	static ImageIcon regularIcon;

	public ArchiveTree(String archiveName) {
		super();
		state = State.SET;
		itemPaths = new ArrayList<ArchiveItem>();
		this.archiveName = archiveName;
		folderIcon = new ImageIcon(getClass().getResource("/folder16.png"));
		regularIcon = new ImageIcon(getClass().getResource("/regular16.png"));
	}

	String getPath() {
		return archiveName;
	}

	String getDir() {
		return FilenameUtils.getFullPathNoEndSeparator(archiveName);
	}

	public void addItem(ArchiveItem item) throws Exception {
		if (state != State.SET)
			throw new Exception("Must not be addItem after organize");
		itemPaths.add(item);
	}

	/**
	 * Main algorithm of archive walking assume that: - paths not ending with
	 * separator - if exists dir\dir2 MUST exists dir1 alone
	 */
	private void alg(int pos0, String prefix, ArchiveItem parent) {
		List<ArchiveItem> list;
		list = parent.childs;
		while (itemIndex < itemPaths.size()) {
			ArchiveItem item = itemPaths.get(itemIndex);
			item.parent = parent;
			String path = item.getCanonicalPath();
			if ((path.length() < pos0)
					|| !path.substring(0, pos0).equals(prefix))
				break;
			int pos1 = path.indexOf(File.separator, pos0);
			if (pos1 < 0) {
				pos1 = path.length();
				String sub0 = path.substring(pos0, pos1);
				for (int i = 0; i < pos0; i++)
					System.out.print(" ");
				System.out.printf("%d %s\n", itemIndex, sub0);
				list.add(item);
				itemIndex++;
			} else {
				ArchiveItem priorItem = itemPaths.get(itemIndex - 1);
				priorItem.childs = new ArrayList<ArchiveItem>();
				alg(pos1 + 1, path.substring(0, pos1 + 1), priorItem);
			}
		}
	}

	public void organize() throws Exception {
		if (state != State.SET)
			throw new Exception("Must not call organize twice");
		Collections.sort(itemPaths);
		itemIndex = 0;
		root = new ArchiveItem(this, null);
		root.childs = new ArrayList<ArchiveItem>();
		alg(0, "", root);
		state = State.GET;
	}

	public Item getRoot() {
		return root;
	}

	public List<Item> getItems(ArchiveItem dir, boolean twoDots)
			throws Exception {
		if (state != State.GET)
			throw new Exception("getFiles must be after organize");
		List<Item> items;
		if (dir.childs == null)
			items = new ArrayList<Item>();
		else
			items = new ArrayList<Item>(dir.childs);
		if (twoDots)
			items.add(0, new ArchiveItem(this, ".."));
		return items;
	}
}
