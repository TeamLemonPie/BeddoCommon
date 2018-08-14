package de.lemonpie.beddocommon.model.lowerthird;

import de.lemonpie.beddocommon.model.ObservableList;

public class LowerThirdList extends ObservableList<LowerThird>
{
	public LowerThird add(String name, String data)
	{
		LowerThird lowerThird = new LowerThird(name, data);
		return add(lowerThird);
	}
}
