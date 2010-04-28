package jatran.stub

import cms.common.Constants
import cms.common.util.DateUtil
import cms.service.core.StatisticsManager
import cms.webapp.action.core.BaseAction
import cms.webapp.form.core.TotalsHourlyForm
import org.apache.commons.validator.GenericValidator
import org.apache.struts.Globals
import org.apache.struts.action.ActionForm
import org.apache.struts.action.ActionForward
import org.apache.struts.action.ActionMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpSession
import java.text.ParseException
import java.util._


class AStrutsAction extends BaseAction {
  @throws(classOf[Exception])
  def execute(mapping:ActionMapping, form:ActionForm, request:HttpServletRequest, response:HttpServletResponse):ActionForward = {
    var session:HttpSession = request.getSession()
    var totalsHourlyForm:TotalsHourlyForm = form.asInstanceOf[TotalsHourlyForm] 
    var locale:Locale = session.getAttribute(Globals.LOCALE_KEY).asInstanceOf[Locale] 
    if (locale == null) {
      locale = new Locale("en")
    }
    var calendar:Calendar = Calendar.getInstance()
    var dateIsNow:Boolean = true
    if (!GenericValidator.isBlankOrNull(totalsHourlyForm.getDate())) {
      try {
        var date:Date = DateUtil.parseDate(totalsHourlyForm.getDate(), locale)
        dateIsNow = false
        calendar.setTime(date)
      } catch { 
        case e:ParseException => 
      }
    }
    if (dateIsNow) {
      totalsHourlyForm.setDate(DateUtil.formatDate(calendar.getTime(), locale))
    }
    var statisticsManager:StatisticsManager = getBean(Constants.STATISTICS_MANAGER_BEAN).asInstanceOf[StatisticsManager] 
    var day:Integer = new Integer(calendar.get(Calendar.DAY_OF_MONTH))
    var month:Integer = new Integer(calendar.get(Calendar.MONTH) + 1)
    var year:Integer = new Integer(calendar.get(Calendar.YEAR))
    var hours:List = statisticsManager.getTotalsHourly(year, month, day)
    var maxTotal:Int = 1
    var maxUnique:Int = 1
    
    {
      var i:Iterator = hours.iterator()
      while (i.hasNext()) {
        var hourDescr:Array[Object] = i.next().asInstanceOf[Array[Object]] 
        var total:Integer = hourDescr(1).asInstanceOf[Integer] 
        var unique:Integer = hourDescr(2).asInstanceOf[Integer] 
        if (total.intValue() > maxTotal) {
          maxTotal = total.intValue()
        }
        if (unique.intValue() > maxUnique) {
          maxUnique = unique.intValue()
        }
        
      }
    }

    request.setAttribute("hours", hours)
    request.setAttribute("maxTotal", new Integer(maxTotal))
    request.setAttribute("maxUnique", new Integer(maxUnique))
    var date:Date = calendar.getTime()
    request.setAttribute("date", date)
    return mapping.findForward("showTotalsHourly")
  }

}
