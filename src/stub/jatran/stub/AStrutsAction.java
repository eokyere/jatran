/*
 *  Copyright 2005 Blandware (http://www.blandware.com)
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package jatran.stub;

import cms.common.Constants;
import cms.common.util.DateUtil;
import cms.service.core.StatisticsManager;
import cms.webapp.action.core.BaseAction;
import cms.webapp.form.core.TotalsHourlyForm;
import org.apache.commons.validator.GenericValidator;
import org.apache.struts.Globals;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.util.*;

/**
 * <p>Shows total statistics info daily.</p>
 * <p><a href="ShowStatisticsTotalsHourlyAction.java.html"><i>View source</i></a></p>
 *
 * @author Roman Puchkovskiy <a href="mailto:roman.puchkovskiy@blandware.com">
 *         &lt;roman.puchkovskiy@blandware.com&gt;</a>
 * @version $Revision: 1.2 $ $Date: 2006/05/29 07:04:38 $
 * @struts.action path="/core/statistics/total/hourly"
 * name="totalsHourlyForm"
 * scope="request"
 * roles="core-statistics-view"
 * @struts.action-forward name="showTotalsHourly"
 * path=".core.statistics.total.hourly"
 */
public final class AStrutsAction extends BaseAction {
	/**
	 * @param mapping  The ActionMapping used to select this instance
	 * @param form     The optional ActionForm bean for this request (if any)
	 * @param request  The HTTP request we are proceeding
	 * @param response The HTTP response we are creating
	 * @return an ActionForward instance describing where and how
	 *         control should be forwarded, or null if response
	 *         has already been completed
	 */
	public ActionForward execute(ActionMapping mapping, ActionForm form,
	                             HttpServletRequest request,
                                 HttpServletResponse response) throws Exception {

        HttpSession session = request.getSession();
        TotalsHourlyForm totalsHourlyForm = (TotalsHourlyForm) form;
        Locale locale = (Locale) session.getAttribute(Globals.LOCALE_KEY);
        if (locale == null) {
            locale = new Locale("en");
        }

        Calendar calendar = Calendar.getInstance();
        boolean dateIsNow = true;
        if (!GenericValidator.isBlankOrNull(totalsHourlyForm.getDate())) {
            try {
                Date date = DateUtil.parseDate(totalsHourlyForm.getDate(), locale);
                dateIsNow = false;
                calendar.setTime(date);
            } catch (ParseException e) {
            }
        }

        if (dateIsNow) {
            totalsHourlyForm.setDate(DateUtil.formatDate(calendar.getTime(), locale));
        }

        StatisticsManager statisticsManager = (StatisticsManager) getBean(Constants.STATISTICS_MANAGER_BEAN);

        Integer day = new Integer(calendar.get(Calendar.DAY_OF_MONTH));
        Integer month = new Integer(calendar.get(Calendar.MONTH) + 1);
        Integer year = new Integer(calendar.get(Calendar.YEAR));
        List hours = statisticsManager.getTotalsHourly(year, month, day);

        int maxTotal = 1;
        int maxUnique = 1;
        for (Iterator i = hours.iterator(); i.hasNext(); ) {
            Object[] hourDescr = (Object[]) i.next();
            Integer total = (Integer) hourDescr[1];
            Integer unique = (Integer) hourDescr[2];
            if (total.intValue() > maxTotal) {
                maxTotal = total.intValue();
            }
            if (unique.intValue() > maxUnique) {
                maxUnique = unique.intValue();
            }
        }

        request.setAttribute("hours", hours);
        request.setAttribute("maxTotal", new Integer(maxTotal));
        request.setAttribute("maxUnique", new Integer(maxUnique));

        Date date = calendar.getTime();
        request.setAttribute("date", date);

		return mapping.findForward("showTotalsHourly");
	}
}
