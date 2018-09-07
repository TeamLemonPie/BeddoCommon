package de.lemonpie.beddocommon.midi;

import de.tobias.midi.Mapping;

public class MidiSettings
{
	private String device;
	private Mapping mapping;

	public MidiSettings()
	{
		this.device = "";
		mapping = new Mapping();
	}

	public MidiSettings(String device, Mapping mapping)
	{
		this.device = device;
		this.mapping = mapping;
	}

	public String getDevice()
	{
		return device;
	}

	public void setDevice(String device)
	{
		this.device = device;
	}

	public Mapping getMapping()
	{
		return mapping;
	}

	public void setMapping(Mapping mapping)
	{
		this.mapping = mapping;
	}
}
