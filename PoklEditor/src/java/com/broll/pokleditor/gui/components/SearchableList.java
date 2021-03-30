package com.broll.pokleditor.gui.components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.swing.AbstractListModel;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

public class SearchableList extends JPanel {

    private JList<SearchEntry> list;

    public SearchableList(List<SearchEntry> data, int selected) {

        setPreferredSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        DefaultListModel source = new DefaultListModel(); // use a model of your
        // choice here;
        for (Object e : data) {
            source.addElement(e);
        }
        final FilteredListModel filteredListModel = new FilteredListModel(source);

        list = new JList<>(filteredListModel);
        data.stream().filter(it -> it.getKey() == selected).findFirst().ifPresent(it -> list.setSelectedValue(it, true));
        list.setCellRenderer(new CellRender());
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
                            return ((SearchEntry) element).filtered(text);
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

    private class CellRender implements ListCellRenderer<SearchEntry> {
        @Override
        public Component getListCellRendererComponent(JList<? extends SearchEntry> list, SearchEntry value, int index, boolean isSelected, boolean cellHasFocus) {
            value.updateFocus(isSelected, cellHasFocus);
            return value;
        }
    }

    public int getSelected() {
        if (list.getSelectedIndex() == -1) {
            return -1;
        }
        return list.getSelectedValue().getKey();
    }

    public static Optional<Integer> showList(String title, List<SearchEntry> entries) {
        return showList(title, entries, -1);
    }

    public static Optional<Integer> showList(String title, List<SearchEntry> entries, int selected) {
        SearchableList list = new SearchableList(entries, selected);
        // JOptionPane.showMessageDialog(null, list, "ID Info", JOptionPane.INFORMATION_MESSAGE, null);
        int option = JOptionPane.showConfirmDialog(null, list, title, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE, null);
        if (option == JOptionPane.OK_OPTION) {
            return Optional.of(list.getSelected());
        }
        return Optional.empty();
    }

    private interface Filter {
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
