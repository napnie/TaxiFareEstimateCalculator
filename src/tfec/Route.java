package tfec;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Collection of steps in specific route.
 * @author Nitith Chayakul
 * @since 10/05/2017
 *
 */
public class Route implements Iterable<Step> {
	List<Step> route;
	List<Object> stepList;
	
	
	public Route(List<Object> stepList) {
		this.stepList = stepList;
		initSteps();
	}
	
	private void initSteps() {
		route = new ArrayList<Step>();
		for( int i=0 ; i<stepList.size() ; i++ ) {
			Object step = stepList.get(i);
			@SuppressWarnings("unchecked")
			Map<String,Object> stepMap = (Map<String,Object>) step;
			Step thisStep = new Step(stepMap);
			route.add(thisStep);
		}
	}

	@Override
	public Iterator<Step> iterator() {
		return route.iterator();
	}
	
}
