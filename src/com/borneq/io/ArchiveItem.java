package com.borneq.io;

import java.io.File;
import java.util.List;

import javax.swing.Icon;
import org.apache.commons.io.FilenameUtils;

import com.borneq.util.TimeUtil;

public class ArchiveItem implements Item {
	String path;
	private ArchiveTree owner;
	Item parent;
	List<ArchiveItem> childs;
	private boolean mIsDirectory;
	private long mLastModified;
	private long mLength;

	public ArchiveItem(ArchiveTree owner, String path) {
		super();
		this.owner = owner;
		this.path = path;
		if ((path != null) && path.equals(".."))
			mIsDirectory = true;
	}

	@Override
	public int compareTo(Item o) {
		return path.compareTo(((ArchiveItem) o).path + File.separator);
	}

	@Override
	public String getCanonicalPath() {
		if (path == null)
			return owner.getPath();
		else
			return path;
	}

	@Override
	public String getRealDir() {
		return owner.getDir();
	}

	@Override
	public Icon getIcon() {
		if (mIsDirectory)
			return ArchiveTree.folderIcon;
		else
			return ArchiveTree.regularIcon;
	}

	@Override
	public List<Item> getItems() {
		return getItems(true);
	}

	@Override
	public List<Item> getItems(boolean twoDots) {
		try {
			return owner.getItems(this, twoDots);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public String getName() {
		return FilenameUtils.getName(path);
	}

	@Override
	public Item getParentDirectory() {
		if (path == null)
			return new RealItem(owner.getDir());
		else
			return parent;
	}

	@Override
	public boolean isDirectory() {
		return mIsDirectory;
	}

	@Override
	public boolean isFileSystemRoot() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public long lastModified() {
		return mLastModified;
	}

	@Override
	public long length() {
		return mLength;
	}

	public void setLastModified(long time) {
		mLastModified = TimeUtil.dosToJavaTime(time);
	}

	public void setLength(long length) {
		// TODO Auto-generated method stub
		mLength = length;
	}

	public void setFlags(int flags) {
		mIsDirectory = (flags & 0x8000) == 0;
	}
}
