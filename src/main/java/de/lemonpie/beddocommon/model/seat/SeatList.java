package de.lemonpie.beddocommon.model.seat;

import de.lemonpie.beddocommon.model.ObservableList;

import java.util.Optional;

public class SeatList extends ObservableList<Seat>
{
	public Optional<Seat> getSeatByPlayerId(int playerID)
	{
		for(Seat seat : getData())
		{
			if(seat.getPlayerId() == playerID)
			{
				return Optional.of(seat);
			}
		}

		return Optional.empty();
	}

	public Optional<Seat> getSeatByReader(int readerId)
	{
		for(Seat seat : getData())
		{
			if(seat.getReaderId() == readerId)
			{
				return Optional.of(seat);
			}
		}
		return Optional.empty();
	}
}
