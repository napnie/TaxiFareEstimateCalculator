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
	private List<Step> route;
	private List<Object> stepList;
	
	private int distance;
	private int duration;
	private int waitTime;
	private String origin;
	private String destination;
	
	/**
	 * Initialize Route
	 * @param stepList - list of step from JSON
	 */
	public Route(List<Object> stepList) {
		this.stepList = stepList;
		initSteps();
	}
	
	/**
	 * Add every step from stepList into route list.
	 */
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
	
	/** Make Route Iterable. */
	@Override
	public Iterator<Step> iterator() {
		return route.iterator();
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getOrigin() {
		return origin;
	}

	public void setOrigin(String origin) {
		this.origin = origin;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public int getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
}
