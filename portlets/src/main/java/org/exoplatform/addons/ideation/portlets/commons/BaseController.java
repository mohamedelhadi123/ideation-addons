package org.exoplatform.addons.ideation.portlets.commons;

import juzu.Response;
import juzu.impl.common.JSON;
import org.exoplatform.portal.application.PortalRequestContext;
import org.exoplatform.services.log.Log;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public abstract class BaseController {
    ResourceBundle bundle;

    public Response getBundle(Locale locale) {
        try {
            if (bundle == null || (bundle.getLocale() != null && !bundle.getLocale().equals(locale))){
                bundle = getResourceBundle(locale);
            }
            JSON data = new JSON();
            Enumeration<String> enumeration = getResourceBundle().getKeys();
            while (enumeration.hasMoreElements()) {
                String key = (String) enumeration.nextElement();
                try {
                    data.set(key.replaceAll("(.*)\\.", ""), getResourceBundle().getObject(key));
                } catch (MissingResourceException e) {
                    // Nothing to do, this happens sometimes
                }
            }
            return Response.ok(data.toString()).withHeader("Cache-Control", "max-age=864000");
        } catch (Throwable e) {
            getLogger().error("error while getting categories", e);
            return Response.status(500);
        }
    }

    public abstract Log getLogger();

    protected ResourceBundle getResourceBundle(Locale locale) {
        return bundle = ResourceBundle.getBundle("locale.portlet.categoriesManagement", locale, this.getClass().getClassLoader());
    }

    protected ResourceBundle getResourceBundle() {
        if (bundle == null) {
            bundle = getResourceBundle(PortalRequestContext.getCurrentInstance().getLocale());
        }
        return bundle;
    }

}
