package tfec;

import java.util.Map;

/**
 * Contain information of each Step in Route.
 * @author Nitith Chayakul
 * @since 10/05/2017
 *
 */
public class Step {
	/** Distance of this step. */
	private final int distance;
	/** Duration of this step. */
	private final int duration;
	/** Instruction for this step. */
	private final String instruction;
	/** Travel mode of this step. */
	private final String travelMode;
	
	/**
	 * Initialize Step from Map of step
	 * @param stepMap - Map of step from JSON
	 */
	public Step(Map<String,Object> stepMap) {
		instruction = removeHTMLTag( (String) stepMap.get("html_instructions") );
		travelMode = (String) stepMap.get("travel_mode");
		@SuppressWarnings("unchecked")
		Map<String,Object> distanceMap = (Map<String,Object>) stepMap.get("distance");
		@SuppressWarnings("unchecked")
		Map<String,Object> durationMap = (Map<String,Object>) stepMap.get("duration");
		distance = Integer.parseInt( String.valueOf( distanceMap.get("value") ) );
		duration = Integer.parseInt( String.valueOf( durationMap.get("value") ) );
	}
	
	/**
	 * Remove HTML tags from string.
	 * because stringtree library seem to mess it up.
	 * @param remove - String that want to remove HTML tags
	 * @return HTML tags removed String 
	 */
	private String removeHTMLTag(String remove) {
		remove = remove.replace('/',' ');
		remove = remove.replace('/',' ');
		return remove.replaceAll("\\(.*?\\*", "");
//		return remove;
	}
	
	/**
	 * Get travel mode
	 * @return travel mode
	 */
	public String getTravelMode() {
		return travelMode;
	}
	
	/**
	 * Get detail instruction of this step.
	 * @return instruction of this step
	 */
	public String getInstruction() {
		return instruction;
	}
	
	/**
	 * Get duration of this step.
	 * @return duration of this step in second.
	 */
	public int getDuration() {
		return duration;
	}
	
	/**
	 * Get distance of this step.
	 * @return distance of this step in meter.
	 */
	public int getDistance() {
		return distance;
	}
}
