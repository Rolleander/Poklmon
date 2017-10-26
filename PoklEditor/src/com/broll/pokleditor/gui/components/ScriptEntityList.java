package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class ScriptEntityList extends JPanel {

	private JList list;

	public ScriptEntityList(List<String> data, int selected) {

		setPreferredSize(new Dimension(300, 500));
		setLayout(new BorderLayout());

		DefaultListModel source = new DefaultListModel(); // use a model of your
		// choice here;
		for (String e : data) {
			source.addElement(e);
		}
		final FilteredListModel filteredListModel = new FilteredListModel(source);

		list = new JList(filteredListModel);
		if (selected != -1) {
			list.setSelectedIndex(selected);
		}
		JScrollPane listScroller = new JScrollPane(list);
		add(listScroller, BorderLayout.CENTER);

		final JTextField filter = new JTextField();
		add(filter, BorderLayout.NORTH);
		filter.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				update();
			}

			public void removeUpdate(DocumentEvent e) {
				update();
			}

			public void insertUpdate(DocumentEvent e) {
				update();
			}

			public void update() {
				String text = filter.getText();
				if (text != null && text.length() > 0) {
					filteredListModel.setFilter(new Filter() {
						public boolean accept(Object element) {
							String s = (String) element;
							return s.toLowerCase().contains(text.toLowerCase());
						}
					});
				} else {
					filteredListModel.setFilter(new Filter() {
						public boolean accept(Object element) {
							return true;
						}
					});
				}
			}
		});

	}

	public int getSelected() {
		String v= (String) list.getSelectedValue();
		return Integer.parseInt(v.split(":")[0]);
	}

	public static int showList(List<String> entries) {
		return showList(entries, -1);
	}

	public static int showList(List<String> entries, int selected) {
		ScriptEntityList list = new ScriptEntityList(entries, selected);
		JOptionPane.showMessageDialog(null, list, "ID Info", JOptionPane.INFORMATION_MESSAGE, null);
		return list.getSelected();
	}

	private static interface Filter {
		boolean accept(Object element);
	}

	private class FilteredListModel extends AbstractListModel {

		private final ListModel _source;
		private Filter _filter;
		private final ArrayList<Integer> _indices = new ArrayList<Integer>();

		public FilteredListModel(ListModel source) {
			if (source == null)
				throw new IllegalArgumentException("Source is null");
			_source = source;
			_source.addListDataListener(new ListDataListener() {
				public void intervalRemoved(ListDataEvent e) {
					doFilter();
				}

				public void intervalAdded(ListDataEvent e) {
					doFilter();
				}

				public void contentsChanged(ListDataEvent e) {
					doFilter();
				}
			});
		}

		public void setFilter(Filter f) {
			_filter = f;
			doFilter();
		}

		private void doFilter() {
			_indices.clear();

			Filter f = _filter;
			if (f != null) {
				int count = _source.getSize();
				for (int i = 0; i < count; i++) {
					Object element = _source.getElementAt(i);
					if (f.accept(element)) {
						_indices.add(i);
					}
				}
				fireContentsChanged(this, 0, getSize() - 1);
			}
		}

		public int getSize() {
			return (_filter != null) ? _indices.size() : _source.getSize();
		}

		public Object getElementAt(int index) {
			return (_filter != null) ? _source.getElementAt(_indices.get(index)) : _source.getElementAt(index);
		}
	}
}
