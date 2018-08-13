package de.lemonpie.beddocommon.model;

public interface ObservableListListener<T>
{
	void addObjectToList(T obj);

	void removeObjectFromList(T obj);
}
