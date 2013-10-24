package com.prodyna.conference.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.prodyna.conference.model.Conference;
import com.prodyna.conference.model.ConferenceOverviewTreeModel;
import com.prodyna.conference.model.Talk;
import com.prodyna.conference.service.ConferenceService;
import com.prodyna.conference.util.DateUtil;
import com.prodyna.conference.util.NavigationConstants;

/**
 * Entry point of the application.
 * 
 * Shows all Conference in a tree structure.
 * 
 * @author Stephan Eichmann
 * 
 */
@Named(value = "conferenceOverviewController")
@SessionScoped
public class ConferenceOverviewController extends AbstractController {

	private static final long serialVersionUID = 8790929507576888837L;

	private TreeNode root;

	@Inject
	private ConferenceService conferenceService;

	private Locale locale;

	/**
	 * Load Conference and transform result in PrimeFaces Treemodel.
	 */
	public void loadTreeData() {
		try {
			List<Conference> conferences = conferenceService
					.getAllConferences();

			root = new DefaultTreeNode("root", null);

			for (Conference conference : conferences) {
				TreeNode conferenceNode = new DefaultTreeNode(
						new ConferenceOverviewTreeModel(conference.getName(),
								String.valueOf(conference.getTalks().size()),
								NULL_VALUE, null), root);
				Map<Date, List<Talk>> talkDayMap = new TreeMap<Date, List<Talk>>();

				for (Talk talk : conference.getTalks()) {
					Date date = DateUtil.strippedDate(talk.getStart());

					if (talkDayMap.get(date) == null) {
						List<Talk> talkList = new ArrayList<Talk>();
						talkList.add(talk);
						talkDayMap.put(date, talkList);
					} else {
						talkDayMap.get(date).add(talk);
					}
				}

				for (Entry<Date, List<Talk>> entry : talkDayMap.entrySet()) {
					TreeNode dayNode = new DefaultTreeNode(
							new ConferenceOverviewTreeModel(
									DateUtil.format(entry.getKey()),
									String.valueOf(entry.getValue().size()),
									NULL_VALUE, null), conferenceNode);
					for (Talk talk : entry.getValue()) {
						String talkDetails = DateUtil
								.formatHourMinuteSmall(talk.getStart())
								+ " - "
								+ DateUtil.formatHourMinuteSmall(talk.getEnd());
						new DefaultTreeNode(new ConferenceOverviewTreeModel(
								talk.getName(), NULL_VALUE, talkDetails,
								talk.getId()), dayNode);
					}
				}
			}
		} catch (Throwable t) {
			exceptionHandler.handle(t);
		}
	}

	public void changeLocale() {
		Map<String, String> param = facesContext.getExternalContext()
				.getRequestParameterMap();

		String locale = param.get("locale");
		try {
			if (locale != null) {
				this.locale = Locale.forLanguageTag(locale);
			}

			ExternalContext context = FacesContext.getCurrentInstance()
					.getExternalContext();
			facesContext.getExternalContext().redirect(
					context.getRequestContextPath() + "/"
							+ NavigationConstants.INDEX);
		} catch (IOException e) {
			exceptionHandler.handle(e);
		}
	}

	// **************** GETTER / SETTER *****************/
	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Locale getLocale() {
		return locale;
	}
}
