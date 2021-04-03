package com.japplis.appletrunner.netbeans;

import java.awt.BorderLayout;
import java.util.Properties;

import com.japplis.appletrunner.IDEInterface;
import com.japplis.appletrunner.AppletRunner;

import com.japplis.appletrunner.AppletRunnerStub;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.awt.StatusDisplayer;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
import org.openide.windows.CloneableTopComponent;

/**
 * Top component which displays an Applet browser.
 * TODO:
 * * License free but not redistributable (contact info@japplis.com)
 * * Website
 * * Pro version (redistributable, start-up URL, hide toolbar, own bookmarks, customized toolbar, not cloneable,
 * special requests, restrict url's to specific domain, support, stand alone version)
 */
@ConvertAsProperties(
        dtd = "-//com.japplis.appletrunner.netbeans//AppletRunner//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "AppletRunnerTopComponent",
        iconBase = "com/japplis/appletrunner/netbeans/AppletRunner.png",
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "com.japplis.appletrunner.netbeans.AppletRunnerTopComponent")
@ActionReference(path = "Menu/Window/Tools", position = 777)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_AppletRunnerAction",
        preferredID = "AppletRunnerTopComponent"
)
@Messages({
    "CTL_AppletRunnerAction=Applet Runner",
    "CTL_AppletRunnerTopComponent=Applet Runner",
    "HINT_AppletRunnerTopComponent=Runs Applets within your IDE with Applet Runner"
})
public final class AppletRunnerTopComponent extends CloneableTopComponent implements IDEInterface {
    private AppletRunner appletRunner;

    public AppletRunnerTopComponent() {
        initComponents();
        setName(Bundle.CTL_AppletRunnerTopComponent());
        setToolTipText(Bundle.HINT_AppletRunnerTopComponent());
    }

    private void initComponents() {
        try {
            appletRunner = new AppletRunner();
            appletRunner.setStub(new AppletRunnerStub());
            appletRunner.start();

            setLayout(new BorderLayout(0, 0));
            add(appletRunner.getRootPane(), BorderLayout.CENTER);
        } catch (Throwable ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    @Override
    protected CloneableTopComponent createClonedObject() {
        try {
            AppletRunnerTopComponent newAppletRunnerComponent = new AppletRunnerTopComponent();
            return (CloneableTopComponent) newAppletRunnerComponent;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        try {
            if (appletRunner != null) appletRunner.exit();
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
    }

    void writeProperties(Properties properties) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        properties.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(Properties properties) {
        String version = properties.getProperty("version");
        // TODO read your settings according to their version
    }

    @Override
    public void log(String message) {
        StatusDisplayer.getDefault().setStatusText(message);
    }

    @Override
    public void log(String message, Throwable error) {
        StatusDisplayer.getDefault().setStatusText(message);
        Exceptions.printStackTrace(error);
    }

    @Override
    public void setName(String appletName) {
        if (appletName.equals("Applet Runner")) {
            super.setName(appletName);
        } else {
            super.setName("Applet Runner - " + appletName);
        }
    }
}
