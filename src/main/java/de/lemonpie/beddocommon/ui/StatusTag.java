package de.lemonpie.beddocommon.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusTag extends VBox
{
	private String text;
	private StatusTagType type;
	private String additionalText;

	public StatusTag(String text, StatusTagType type, String additionalText)
	{
		this.text = text;
		this.type = type;
		this.additionalText = additionalText;

		setAlignment(Pos.TOP_CENTER);

		update();
	}

	private void update()
	{
		getChildren().clear();

		Label labelTag = new Label(text);
		labelTag.setStyle(getStyleForType());
		getChildren().add(labelTag);

		if(additionalText != null)
		{
			Label labelAdditionalText = new Label(additionalText);
			labelAdditionalText.setStyle("-fx-font-size: 12px; -fx-text-fill: black");
			getChildren().add(labelAdditionalText);
		}
	}

	public void setText(String text)
	{
		this.text = text;
		update();
	}

	public void setType(StatusTagType type)
	{
		this.type = type;
		update();
	}

	public void setAdditionalText(String additionalText)
	{
		this.additionalText = additionalText;
		update();
	}

	private String getStyleForType()
	{
		switch(type)
		{
			case ERROR:
				return "-fx-background-color: rgba(204, 0, 0, 0.5); -fx-padding: 5px 7px 5px 7px; -fx-background-radius: 3px";
			case WARNING:
				return "-fx-background-color: rgba(255, 165, 0, 0.5); -fx-padding: 5px 7px 5px 7px; -fx-background-radius: 3px";
			case SUCCESS:
				return "-fx-background-color: rgba(72, 219, 94, 0.5); -fx-padding: 5px 7px 5px 7px; -fx-background-radius: 3px";
		}

		return null;
	}
}
