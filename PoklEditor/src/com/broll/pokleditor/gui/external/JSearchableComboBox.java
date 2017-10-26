package com.broll.pokleditor.gui.external;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.plaf.basic.BasicComboBoxEditor;

/**
 * 
 * Enables efficient searching through a JComboBox.
 * 
 * Based on code by Ron (rmlchan@yahoo.com)
 * 
 *
 * 
 * @author Eric Lindauer
 * 
 * @date 2002.9.24
 */

public class JSearchableComboBox extends JComboBox {

	public JSearchableComboBox() {

		super();

		init();

	}

	public JSearchableComboBox(Object[] elements) {

		super(elements);

		init();

	}

	public void setModel(ComboBoxModel model)

	{

		super.setModel(model);

		init();

	}

	private void init() {

		setEditable(true);

		setEditor(new SearchEditor(this));

	}

	private static class SearchEditor extends BasicComboBoxEditor {

		private TernarySearchTree _data = new TernarySearchTree();

		public Object getItem() {

			return _data.get(super.getItem().toString());

		}

		public SearchEditor(final JSearchableComboBox cb) {

			// populate the search tree with the items in the list

			ComboBoxModel model = cb.getModel();

			for (int i = 0; i < model.getSize(); i++)

			{

				Object data = model.getElementAt(i);

				_data.put(data.toString(), data);

			}

			// when the user types, search the data and guess what they want

			KeyAdapter listener = new KeyAdapter() {

				public void keyReleased(KeyEvent ev) {

					if ((ev.getKeyChar() >= 'a' && ev.getKeyChar() <= 'z') ||

					(ev.getKeyChar() >= '0' && ev.getKeyChar() <= '9') ||

					(ev.getKeyChar() >= 'A' && ev.getKeyChar() <= 'Z') ||

					(ev.getKeyChar() == KeyEvent.VK_SPACE))

					{

						String startText = editor.getText();

						// cb.showPopup (); ß uncomment for smaller data sets

						String finalText = _data.matchPrefixString(startText, 1);

						if (finalText.equals(""))

							finalText = startText;

						if (!finalText.equals(startText))

						{

							editor.setText(finalText);

							editor.setSelectionStart(startText.length());

							editor.setSelectionEnd(finalText.length());

						}

						cb.setSelectedItem(_data.get(finalText));

					}

				}

			};

			editor.addKeyListener(listener);

			// register an action listener to keep the text area always
			// up-to-date

			ActionListener actionListener = new ActionListener() {

				public void actionPerformed(ActionEvent e)

				{

					if (cb.getSelectedItem() != null &&

					!editor.getText().equals(cb.getSelectedItem().toString()))

					{

						editor.setText(cb.getSelectedItem().toString());

					}

				}

			};

			cb.addActionListener(actionListener);

		}

	}

}
