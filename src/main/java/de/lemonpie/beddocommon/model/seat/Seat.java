package de.lemonpie.beddocommon.model.seat;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;
import de.lemonpie.beddocommon.model.Indexable;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

@DatabaseTable(tableName = "Seat")
public class Seat implements Indexable
{
	public static final int READER_NULL_ID = -3;

	private List<SeatListener> listeners;

	@DatabaseField(unique = true)
	private int id;

	@DatabaseField
	private int readerId;

	@DatabaseField
	private int playerId;

	public Seat()
	{
		this(0);
	}

	public Seat(int id)
	{
		this(id, READER_NULL_ID, -1);
	}

	public Seat(int id, int readerId, int playerId)
	{
		this.listeners = new LinkedList<>();

		this.id = id;
		this.readerId = readerId;
		this.playerId = playerId;
	}

	@Override
	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public int getReaderId()
	{
		return readerId;
	}

	public void setReaderId(int readerId)
	{
		this.readerId = readerId;
		fireListener(listener -> listener.readerIdDidChange(this, readerId));
	}

	public int getPlayerId()
	{
		return playerId;
	}

	public void setPlayerId(int playerId)
	{
		this.playerId = playerId;
		fireListener(listener -> listener.playerIdDidChange(this, playerId));
	}

	public void addListener(SeatListener playerListener)
	{
		this.listeners.add(playerListener);
	}

	public void removeListener(SeatListener playerListener)
	{
		this.listeners.remove(playerListener);
	}

	private void fireListener(Consumer<SeatListener> consumer)
	{
		for(SeatListener playerListener : listeners)
		{
			consumer.accept(playerListener);
		}
	}

	@Override
	public String toString()
	{
		return "Seat{" +
				"seatId=" + id +
				", readerId=" + readerId +
				", playerId=" + playerId +
				'}';
	}
}
