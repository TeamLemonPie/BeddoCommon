package de.lemonpie.beddocommon.ui;

import javafx.geometry.Pos;
import javafx.scene.layout.HBox;

import java.util.LinkedHashMap;
import java.util.Map;

public class StatusTagBar extends HBox
{
	private Map<String, StatusTag> tags;

	public StatusTagBar()
	{
		this.tags = new LinkedHashMap<>();

		setAlignment(Pos.TOP_LEFT);
		setSpacing(20.0);
	}

	public void addTag(String key, StatusTag tag)
	{
		tags.put(key, tag);
		update();
	}

	public void removeTag(String key)
	{
		tags.remove(key);
		update();
	}

	public StatusTag getTag(String key)
	{
		return tags.get(key);
	}

	private void update()
	{
		getChildren().clear();
		for(StatusTag tag : tags.values())
		{
			getChildren().add(tag);
		}
	}
}
