package de.lemonpie.beddocommon.model;

import java.util.*;
import java.util.function.Consumer;

public class ObservableList<T extends Indexable> implements Iterable<T>
{
	private List<T> data = new ArrayList<>();
	private List<ObservableListListener<T>> listeners = new LinkedList<>();

	public T add(T obj)
	{
		final boolean add = data.add(obj);
		if(add)
		{
			fireListener(l -> l.addObjectToList(obj));
		}
		return add ? obj : null;
	}

	public boolean addAll(List<T> items)
	{
		final boolean addAll = this.data.addAll(items);
		if(addAll)
		{
			items.forEach(i -> fireListener(l -> l.addObjectToList(i)));
		}
		return addAll;
	}

	public Optional<T> getObject(int id)
	{
		return data.stream().filter(i -> i.getId() == id).findFirst();
	}

	public List<T> getData()
	{
		return data;
	}

	public void clear()
	{
		data.forEach(i -> fireListener(l -> l.removeObjectFromList(i)));
		data.clear();
	}

	public boolean remove(T o)
	{
		final boolean remove = data.remove(o);
		if(remove)
		{
			fireListener(l -> l.removeObjectFromList(o));
		}
		return remove;
	}

	public void addListener(ObservableListListener<T> playerListener)
	{
		this.listeners.add(playerListener);
	}

	public void removeListener(ObservableListListener<T> playerListener)
	{
		this.listeners.remove(playerListener);
	}

	private void fireListener(Consumer<ObservableListListener<T>> consumer)
	{
		for(ObservableListListener<T> playerListener : listeners)
		{
			consumer.accept(playerListener);
		}
	}

	@Override
	public Iterator<T> iterator()
	{
		return data.iterator();
	}

	@Override
	public void forEach(Consumer<? super T> action)
	{
		data.forEach(action);
	}

	@Override
	public Spliterator<T> spliterator()
	{
		return data.spliterator();
	}
}
