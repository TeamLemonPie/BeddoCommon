package de.lemonpie.beddocommon.model.lowerthird;

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
