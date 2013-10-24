package com.prodyna.conference.util;

/**
 * Constants for navigation. Can be returned on action()-Methods to redirect to
 * given page.
 * 
 * @author Stephan Eichmann
 * 
 */
public interface NavigationConstants {

	public static final String TALK_OVERVIEW = "talkOverview.xhtml?faces-redirect=true";

	public static final String ROOM_OVERVIEW = "roomOverview.xhtml?faces-redirect=true";

	public static final String SPEAKER_OVERVIEW = "speakerOverview.xhtml?faces-redirect=true";

	public static final String INDEX = "index.xhtml?faces-redirect=true";
}
