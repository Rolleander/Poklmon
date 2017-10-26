package com.broll.pokleditor.map.objects;

import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.broll.pokleditor.gui.components.CharacterGraphicBox;
import com.broll.pokleditor.gui.components.ObjectViewDirectionBox;
import com.broll.pokleditor.gui.components.StringBox;
import com.broll.pokleditor.window.VerticalLayout;
import com.broll.pokllib.object.MapObject;

public class MapObjectStatus {

	private JPanel content = new JPanel();
	private MapObject object;
	private CharacterGraphicBox graphic = new CharacterGraphicBox();
	private StringBox name = new StringBox("Name", 20);
	private JLabel id = new JLabel("Object ID:");
	private ObjectViewDirectionBox direction = new ObjectViewDirectionBox("View Direction");

	public MapObjectStatus() {
		content.setLayout(new BorderLayout());
		id.setForeground(Color.BLUE);

		JPanel center = new JPanel();
		center.setLayout(new VerticalLayout());

		center.add(id);

		center.add(name);

		center.add(direction);

		content.add(center, BorderLayout.CENTER);
		content.add(graphic, BorderLayout.WEST);
	}

	public void setMapObject(MapObject object) {
		this.object = object;
		graphic.setImage(object.getGraphic());
		name.setText(object.getName());
		direction.setDirection(object.getDirection());
		id.setText("Object ID:" + object.getObjectID());
	}

	public void save() {
		object.setDirection(direction.getDirection());
		object.setGraphic(graphic.getImage());
		object.setName(name.getText());
	}

	public JPanel getContent() {
		return content;
	}
}
