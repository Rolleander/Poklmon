package com.broll.pokleditor.gui.components;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

import com.broll.pokleditor.gui.graphics.GraphicLoader;
import com.broll.pokleditor.window.VerticalLayout;

public class JScriptHelpBox extends JPanel {

	public JScriptHelpBox() {
		setLayout(new VerticalLayout());
		add(objects);
		add(classes);
		add(enums);

	}

	private List<Class> classList = new ArrayList<Class>();
	private JPanel classes = new JPanel(new VerticalLayout());
	private JPanel objects = new JPanel(new VerticalLayout());
	private JPanel enums = new JPanel(new VerticalLayout());

	public void addEnum(Class enun) {
		JPanel panel = new JPanel();
		JLabel title = new JLabel(enun.getSimpleName());
		title.setFont(title.getFont().deriveFont(16f));
		panel.add(title);
		panel.setLayout(new VerticalLayout());
		for (Object v : enun.getEnumConstants()) {
			JLabel l = new JLabel(v.toString(), GraphicLoader.loadIcon("key.png"), JLabel.RIGHT);
			panel.add(l);
		}
		enums.add(panel);
	}

	public void addObject(Class clazz, String name) {
		JLabel title = new JLabel("<html><font color='#3385ff'>" + name + "</font> : " + clazz.getSimpleName()
				+ "</html> ", GraphicLoader.loadIcon("bool.png"), JLabel.RIGHT);
		title.setFont(title.getFont().deriveFont(16f));
		addClass(clazz,name);
		objects.add(title);
	}

	public void addClass(Class clazz, String objectName) {
		if (classList.contains(clazz)) {
			return;
		}
		classList.add(clazz);
		if (clazz.isEnum()) {
			addEnum(clazz);
			return;
		}
		JPanel panel = new JPanel();
		JLabel title = new JLabel(clazz.getSimpleName());
		title.setFont(title.getFont().deriveFont(20f));
		panel.add(title);
		title = new JLabel(clazz.getName());
		title.setFont(title.getFont().deriveFont(10f));
		panel.add(title);

		panel.add(new JSeparator(SwingConstants.HORIZONTAL));
		Method[] obMethods = Object.class.getMethods();
		List<Method> list = new ArrayList<Method>(Arrays.asList(clazz.getMethods()));
		Collections.sort(list, new MethodComparator());
		for (final Method method : list) {
			if (Modifier.isPublic(method.getModifiers())) {
				boolean skip = false;
				for (Method m : obMethods) {
					if (m.equals(method)) {
						skip = true;
						break;
					}
				}
				if(method.getName().equals("invoke")||method.getName().equals("init")){
					skip=true;
				}
				
				if (!skip) {
					final JPanel info = new JPanel(new VerticalLayout());
				
					String text = "<html>";
					String returnn = method.getReturnType().getSimpleName();
					if (!returnn.equals("void")) {						
						text += "Returns: <font color='#3385ff'>" + returnn + "</font> ";
						text += "</html>";
						JButton returnButton = new JButton(text);
						returnButton.setBorder(null);
						returnButton.setContentAreaFilled(false);
						returnButton.setBorderPainted(false);
						returnButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								addClass(method.getReturnType(),null);
							}
						});
						info.add(returnButton);
					}

					int nr = 1;
					for (final Parameter param : method.getParameters()) {
						text = "<html>";
						text += "P" + nr + ":  <font color='#3385ff'>" + param.getType().getSimpleName() + "</font> : <font color='#009933'>"+param.getName()+"</font>";
						text += "</html>";
						JButton returnButton = new JButton(text);
						returnButton.setBorder(null);
						returnButton.setContentAreaFilled(false);
						returnButton.setBorderPainted(false);
						returnButton.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								addClass(param.getType(),null);
							}
						});
						info.add(returnButton);
						nr++;
					}

					info.setVisible(false);
					text = "<html>";
					text += objectName+".<font color='#3385ff'>" + method.getName() + "</font>   ";
					text += "</html>";
					JButton button = new JButton(text, GraphicLoader.loadIcon("math_functions.png"));
					button.setBorder(null);
					button.setContentAreaFilled(false);
					button.setBorderPainted(false);
					button.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							info.setVisible(!info.isVisible());
							JScriptHelpBox.this.revalidate();
							JScriptHelpBox.this.repaint();
						}
					});
					panel.add(button);
					panel.add(info);

				}
			}
		}
		panel.setLayout(new VerticalLayout());
		classes.add(panel);
	}

	private class MethodComparator implements Comparator<Method> {

		public int compare(Method m1, Method m2) {

			int val = m1.getName().compareTo(m2.getName());
			if (val == 0) {
				val = m1.getParameterTypes().length - m2.getParameterTypes().length;
				if (val == 0) {
					Class[] types1 = m1.getParameterTypes();
					Class[] types2 = m2.getParameterTypes();
					for (int i = 0; i < types1.length; i++) {
						val = types1[i].getName().compareTo(types2[i].getName());

						if (val != 0) {
							break;
						}
					}
				}
			}
			return val;
		}

	}
}
