package de.lemonpie.beddocommon.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "LowerThird")
public class LowerThird
{
	@DatabaseField(unique = true, generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	private String data;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getData()
	{
		return data;
	}

	public void setData(String data)
	{
		this.data = data;
	}
}
