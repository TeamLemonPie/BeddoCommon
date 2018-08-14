package de.lemonpie.beddocommon.model.lowerthird;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.lemonpie.beddocommon.model.Indexable;

@DatabaseTable(tableName = "LowerThird")
public class LowerThird implements Indexable
{
	@DatabaseField(unique = true, generatedId = true)
	private int id;
	@DatabaseField
	private String name;
	@DatabaseField
	@JsonIgnore
	private transient String data;

	public LowerThird()
	{
	}

	public LowerThird(int id, String name)
	{
		this.id = id;
		this.name = name;
	}

	public LowerThird(String name, String data)
	{
		this.name = name;
		this.data = data;
	}

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
