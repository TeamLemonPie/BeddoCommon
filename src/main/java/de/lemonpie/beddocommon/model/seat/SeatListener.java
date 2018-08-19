package de.lemonpie.beddocommon.model.seat;

public interface SeatListener
{
	void readerIdDidChange(Seat seat, int readerId);

	void playerIdDidChange(Seat seat, int playerId);
}
