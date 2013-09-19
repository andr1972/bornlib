package com.borneq.awt;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.LayoutManager2;
import java.util.ArrayList;
import java.util.List;

/**
 * A bordlist layout lays out a container, arranging and resizing its components
 * to fit in five regions: north, south, east, west, and center. Each region may
 * contains more than one component, and is identified by a corresponding
 * constant: <code>NORTH</code>, <code>SOUTH</code>, <code>EAST</code>,
 * <code>WEST</code>, and <code>CENTER</code>. When adding a component to a
 * container with a bordlist layout, use one of these five constants, for
 * example:
 * 
 * <pre>
 * Panel p = new Panel();
 * p.setLayout(new BordListLayout());
 * p.add(new Button(&quot;Okay&quot;), BordListLayout.SOUTH);
 * p.add(new TextArea(), BordListLayout.SOUTH);
 * </pre>
 * 
 * As a convenience, <code>BordListLayout</code> interprets the absence of a
 * string specification the same as the constant <code>CENTER</code>:
 * 
 * <pre>
 * Panel p2 = new Panel();
 * p2.setLayout(new BordListLayout());
 * p2.add(new TextArea()); // Same as p.add(new TextArea(), BordListLayout.CENTER);
 * </pre>
 * Here is an example of seven buttons in an applet laid out using the
 * <code>BordListLayout</code> layout manager: The code for this applet is as
 * follows:
 * <p>
 * <hr>
 * <blockquote>
 * 
 * <pre>
 * import java.applet.Applet;
 * import java.awt.Button;
 * 
 * import com.borneq.awt.BordListLayout;
 * 
 * public class BordListButtons extends Applet {
 * 	private static final long serialVersionUID = 1L;
 * 
 * 	public void init() {
 * 		setLayout(new BordListLayout());
 * 		add(new Button(&quot;North1&quot;), BordListLayout.NORTH);
 * 		add(new Button(&quot;North2&quot;), BordListLayout.NORTH);
 * 		add(new Button(&quot;South&quot;), BordListLayout.SOUTH);
 * 		add(new Button(&quot;East&quot;), BordListLayout.EAST);
 * 		add(new Button(&quot;West&quot;), BordListLayout.WEST);
 * 		add(new Button(&quot;Center1&quot;), BordListLayout.CENTER);
 * 		add(new Button(&quot;Center2&quot;), BordListLayout.CENTER);
 * 	}
 * }
 * </pre>
 * 
 * </blockquote>
 * <hr>
 * <p>
 * 
 * @author Andrzej Borucki, Arthur van Hoff
 * @see java.awt.Container#add(String, Component)
 * @see java.awt.ComponentOrientation
 */
public class BordListLayout implements LayoutManager2, java.io.Serializable {

	private static final long serialVersionUID = 1890837363792763375L;

	/**
	 * Constructs a bordlist layout with the horizontal gaps between components.
	 * The horizontal gap is specified by <code>hgap</code>.
	 * 
	 * @see #getHgap()
	 * @see #setHgap(int)
	 * 
	 * @serial
	 */
	int hgap;

	/**
	 * Constructs a bordlist layout with the vertical gaps between components.
	 * The vertical gap is specified by <code>vgap</code>.
	 * 
	 * @see #getVgap()
	 * @see #setVgap(int)
	 * @serial
	 */
	int vgap;

	/**
	 * Constant to specify components location to be the north portion of the
	 * bordlist layout.
	 * 
	 * @serial
	 * @see #getChilds(String)
	 * @see #addLayoutComponent
	 * @see #getLayoutAlignmentX
	 * @see #getLayoutAlignmentY
	 * @see #removeLayoutComponent
	 */
	List<Component> northList;
	/**
	 * Constant to specify components location to be the west portion of the
	 * bordlist layout.
	 * 
	 * @serial
	 * @see #getChilds(String)
	 * @see #addLayoutComponent
	 * @see #getLayoutAlignmentX
	 * @see #getLayoutAlignmentY
	 * @see #removeLayoutComponent
	 */
	List<Component> westList;
	/**
	 * Constant to specify components location to be the east portion of the
	 * bordlist layout.
	 * 
	 * @serial
	 * @see #getChilds(String)
	 * @see #addLayoutComponent
	 * @see #getLayoutAlignmentX
	 * @see #getLayoutAlignmentY
	 * @see #removeLayoutComponent
	 */
	List<Component> eastList;
	/**
	 * Constant to specify components location to be the south portion of the
	 * bordlist layout.
	 * 
	 * @serial
	 * @see #getChilds(String)
	 * @see #addLayoutComponent
	 * @see #getLayoutAlignmentX
	 * @see #getLayoutAlignmentY
	 * @see #removeLayoutComponent
	 */
	List<Component> southList;
	/**
	 * Constant to specify components location to be the center portion of the
	 * bordlist layout.
	 * 
	 * @serial
	 * @see #getChilds(String)
	 * @see #addLayoutComponent
	 * @see #getLayoutAlignmentX
	 * @see #getLayoutAlignmentY
	 * @see #removeLayoutComponent
	 */
	List<Component> centerList;

	/**
	 * The north layout constraint (top of container).
	 */
	public static final String NORTH = "North";

	/**
	 * The south layout constraint (bottom of container).
	 */
	public static final String SOUTH = "South";

	/**
	 * The east layout constraint (right side of container).
	 */
	public static final String EAST = "East";

	/**
	 * The west layout constraint (left side of container).
	 */
	public static final String WEST = "West";

	/**
	 * The center layout constraint (middle of container).
	 */
	public static final String CENTER = "Center";

	/**
	 * Constructs a new bordlist layout with no gaps between components.
	 */
	public BordListLayout() {
		this(0, 0);
	}

	/**
	 * Constructs a bordlist layout with the specified gaps between components.
	 * The horizontal gap is specified by <code>hgap</code> and the vertical gap
	 * is specified by <code>vgap</code>.
	 * 
	 * @param hgap
	 *            the horizontal gap.
	 * @param vgap
	 *            the vertical gap.
	 */
	public BordListLayout(int hgap, int vgap) {
		this.hgap = hgap;
		this.vgap = vgap;
		northList = new ArrayList<Component>();
		southList = new ArrayList<Component>();
		westList = new ArrayList<Component>();
		eastList = new ArrayList<Component>();
		centerList = new ArrayList<Component>();
	}

	/**
	 * Returns the horizontal gap between components.
	 */
	public int getHgap() {
		return hgap;
	}

	/**
	 * Sets the horizontal gap between components.
	 * 
	 * @param hgap
	 *            the horizontal gap between components
	 */
	public void setHgap(int hgap) {
		this.hgap = hgap;
	}

	/**
	 * Returns the vertical gap between components.
	 */
	public int getVgap() {
		return vgap;
	}

	/**
	 * Sets the vertical gap between components.
	 * 
	 * @param vgap
	 *            the vertical gap between components
	 */
	public void setVgap(int vgap) {
		this.vgap = vgap;
	}

	/**
	 * Adds the specified component to the layout, using the specified
	 * constraint object. For bordlist layouts, the constraint must be one of
	 * the following constants: <code>NORTH</code>, <code>SOUTH</code>,
	 * <code>EAST</code>, <code>WEST</code>, or <code>CENTER</code>.
	 * <p>
	 * Most applications do not call this method directly. This method is called
	 * when a component is added to a container using the
	 * <code>Container.add</code> method with the same argument types.
	 * 
	 * @param comp
	 *            the component to be added.
	 * @param constraints
	 *            an object that specifies how and where the component is added
	 *            to the layout.
	 * @see java.awt.Container#add(java.awt.Component, java.lang.Object)
	 * @exception IllegalArgumentException
	 *                if the constraint object is not a string, or if it not one
	 *                of the five specified constants.
	 */
	@Override
	public void addLayoutComponent(Component comp, Object constraints) {
		synchronized (comp.getTreeLock()) {
			if ((constraints == null) || (constraints instanceof String)) {
				addLayoutComponent((String) constraints, comp);
			} else {
				throw new IllegalArgumentException(
						"cannot add to layout: constraint must be a string (or null)");
			}
		}
	}

	/**
	 * @deprecated replaced by
	 *             <code>addLayoutComponent(Component, Object)</code>.
	 */
	@Override
	@Deprecated
	public void addLayoutComponent(String name, Component comp) {
		synchronized (comp.getTreeLock()) {
			/* Special case: treat null the same as "Center". */
			if (name == null) {
				name = "Center";
			}

			/*
			 * Assign the component to one of the known regions of the layout.
			 */
			if ("Center".equals(name)) {
				centerList.add(comp);
			} else if ("North".equals(name)) {
				northList.add(comp); // TODO
			} else if ("South".equals(name)) {
				southList.add(comp);
			} else if ("East".equals(name)) {
				eastList.add(comp);
			} else if ("West".equals(name)) {
				westList.add(comp);
			} else {
				throw new IllegalArgumentException(
						"cannot add to layout: unknown constraint: " + name);
			}
		}
	}

	/**
	 * Removes the specified component from this bordlist layout. This method is
	 * called when a container calls its <code>remove</code> or
	 * <code>removeAll</code> methods. Most applications do not call this method
	 * directly.
	 * 
	 * @param comp
	 *            the component to be removed.
	 * @see java.awt.Container#remove(java.awt.Component)
	 * @see java.awt.Container#removeAll()
	 */
	@Override
	public void removeLayoutComponent(Component comp) {
		synchronized (comp.getTreeLock()) {
			if (centerList.remove(comp))
				;
			else if (northList.remove(comp))
				;
			else if (southList.remove(comp))
				;
			else if (eastList.remove(comp))
				;
			else if (westList.remove(comp))
				;
		}
	}

	/**
	 * Determines the minimum size of the <code>target</code> container using
	 * this layout manager.
	 * <p>
	 * This method is called when a container calls its
	 * <code>getMinimumSize</code> method. Most applications do not call this
	 * method directly.
	 * 
	 * @param target
	 *            the container in which to do the layout.
	 * @return the minimum dimensions needed to lay out the subcomponents of the
	 *         specified container.
	 * @see java.awt.Container
	 * @see java.awt.BorderLayout#preferredLayoutSize
	 * @see java.awt.Container#getMinimumSize()
	 */
	@Override
	public Dimension minimumLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);

			List<Component> li = null;

			if ((li = getChilds(EAST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getMinimumSize();
					dim.width += d.width + hgap;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(WEST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getMinimumSize();
					dim.width += d.width + hgap;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(CENTER)) != null)
				for (Component cc : li) {
					Dimension d = cc.getMinimumSize();
					dim.width += d.width;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(NORTH)) != null) // TODO robie
				for (Component cc : li) {
					Dimension d = cc.getMinimumSize();
					dim.width = Math.max(d.width, dim.width);
					dim.height += d.height + vgap;
				}
			if ((li = getChilds(SOUTH)) != null)
				for (Component cc : li) {
					Dimension d = cc.getMinimumSize();
					dim.width = Math.max(d.width, dim.width);
					dim.height += d.height + vgap;
				}
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right;
			dim.height += insets.top + insets.bottom;

			return dim;
		}
	}

	/**
	 * Determines the preferred size of the <code>target</code> container using
	 * this layout manager, based on the components in the container.
	 * <p>
	 * Most applications do not call this method directly. This method is called
	 * when a container calls its <code>getPreferredSize</code> method.
	 * 
	 * @param target
	 *            the container in which to do the layout.
	 * @return the preferred dimensions to lay out the subcomponents of the
	 *         specified container.
	 * @see java.awt.Container
	 * @see java.awt.BorderLayout#minimumLayoutSize
	 * @see java.awt.Container#getPreferredSize()
	 */
	@Override
	public Dimension preferredLayoutSize(Container target) {
		synchronized (target.getTreeLock()) {
			Dimension dim = new Dimension(0, 0);

			List<Component> li = null;

			if ((li = getChilds(EAST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					dim.width += d.width + hgap;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(WEST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					dim.width += d.width + hgap;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(CENTER)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					dim.width += d.width;
					dim.height = Math.max(d.height, dim.height);
				}
			if ((li = getChilds(NORTH)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					dim.width = Math.max(d.width, dim.width);
					dim.height += d.height + vgap;
				}
			if ((li = getChilds(SOUTH)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					dim.width = Math.max(d.width, dim.width);
					dim.height += d.height + vgap;
				}
			Insets insets = target.getInsets();
			dim.width += insets.left + insets.right;
			dim.height += insets.top + insets.bottom;

			return dim;
		}
	}

	/**
	 * Returns the maximum dimensions for this layout given the components in
	 * the specified target container.
	 * 
	 * @param target
	 *            the component which needs to be laid out
	 * @see Container
	 * @see #minimumLayoutSize
	 * @see #preferredLayoutSize
	 */
	@Override
	public Dimension maximumLayoutSize(Container target) {
		return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
	}

	/**
	 * Returns the alignment along the x axis. This specifies how the component
	 * would like to be aligned relative to other components. The value should
	 * be a number between 0 and 1 where 0 represents alignment along the
	 * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
	 * etc.
	 */
	@Override
	public float getLayoutAlignmentX(Container parent) {
		return 0.5f;
	}

	/**
	 * Returns the alignment along the y axis. This specifies how the component
	 * would like to be aligned relative to other components. The value should
	 * be a number between 0 and 1 where 0 represents alignment along the
	 * origin, 1 is aligned the furthest away from the origin, 0.5 is centered,
	 * etc.
	 */
	@Override
	public float getLayoutAlignmentY(Container parent) {
		return 0.5f;
	}

	/**
	 * Invalidates the layout, indicating that if the layout manager has cached
	 * information it should be discarded.
	 */
	@Override
	public void invalidateLayout(Container target) {
	}

	/**
	 * Lays out the container argument using this bordlist layout.
	 * <p>
	 * This method actually reshapes the components in the specified container
	 * in order to satisfy the constraints of this <code>BordListLayout</code>
	 * object. The <code>NORTH</code> and <code>SOUTH</code> components, if any,
	 * are placed at the top and bottom of the container, respectively. The
	 * <code>WEST</code> and <code>EAST</code> components are then placed on the
	 * left and right, respectively. Finally, the <code>CENTER</code> object is
	 * placed in any remaining space in the middle.
	 * <p>
	 * Most applications do not call this method directly. This method is called
	 * when a container calls its <code>doLayout</code> method.
	 * 
	 * @param target
	 *            the container in which to do the layout.
	 * @see java.awt.Container
	 * @see java.awt.Container#doLayout()
	 */
	@Override
	public void layoutContainer(Container target) {
		synchronized (target.getTreeLock()) {
			Insets insets = target.getInsets();
			int top = insets.top;
			int bottom = target.getHeight() - insets.bottom;
			int left = insets.left;
			int right = target.getWidth() - insets.right;

			List<Component> li = null;

			if ((li = getChilds(NORTH)) != null)
				for (Component cc : li) {// TODO
					Dimension d = cc.getPreferredSize();
					cc.setBounds(left, top, right - left, d.height);
					top += d.height + vgap;
				}
			if ((li = getChilds(SOUTH)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					cc.setBounds(left, bottom - d.height, right - left,
							d.height);
					bottom -= d.height + vgap;
				}
			if ((li = getChilds(EAST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					cc.setBounds(right - d.width, top, d.width, bottom - top);
					right -= d.width + hgap;
				}
			if ((li = getChilds(WEST)) != null)
				for (Component cc : li) {
					Dimension d = cc.getPreferredSize();
					cc.setBounds(left, top, d.width, bottom - top);
					left += d.width + hgap;
				}
			if ((li = getChilds(CENTER)) != null) {
				int cnt = li.size();
				int whole = right - left;
				float equalSizeF = (float) (whole - (cnt - 1) * hgap) / cnt;
				// equalSize is truncated, corrections is needed
				int equalSize = (int) equalSizeF;
				float leftF = left;
				for (int i = 0; i < cnt; i++) {
					Component cc = li.get(i);
					int adjustSize = equalSize;
					if (leftF - left > 0.5F)
						adjustSize++;
					cc.setBounds(left, top, adjustSize, bottom - top);
					left += adjustSize + hgap;
					leftF += equalSizeF + hgap;
				}
			}
		}
	}

	/**
	 * Get the component list that corresponds to the given constraint location
	 * 
	 * @param key
	 *            The desired absolute position, either NORTH, SOUTH, EAST,
	 *            WEST, or CENTER.
	 */
	private List<Component> getChilds(String key) {
		List<Component> result = null;

		if (key == NORTH) {
			result = northList;
		} else if (key == SOUTH) {
			result = southList;
		} else if (key == WEST) {
			result = westList;
		} else if (key == EAST) {
			result = eastList;
		} else if (key == CENTER) {
			result = centerList;
		}
		if (result != null && !containsVisible(result)) {
			result = null;
		}
		return result;
	}

	boolean containsVisible(List<Component> list) {
		for (Component c : list)
			if (c.isVisible())
				return true;
		return false;
	}

	/**
	 * Returns a string representation of the state of this bordlist layout.
	 * 
	 * @return a string representation of this bordlist layout.
	 */
	@Override
	public String toString() {
		return getClass().getName() + "[hgap=" + hgap + ",vgap=" + vgap + "]";
	}
}
