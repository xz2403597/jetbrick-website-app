package jetbrick.website.app;

import java.util.*;
import jetbrick.template.JetConfig;
import jetbrick.template.JetEngine;
import jetbrick.template.resource.loader.ClasspathResourceLoader;

public final class TemplateUtils {
    private static final JetEngine engine = createJetEngine();

    private static JetEngine createJetEngine() {
        Properties config = new Properties();
        config.put(JetConfig.IMPORT_FUNCTIONS, AppFunctions.class.getName());
        config.put(JetConfig.COMPILE_DEBUG, "true");
        config.put(JetConfig.TEMPLATE_LOADER, ClasspathResourceLoader.class.getName());
        config.put(JetConfig.TEMPLATE_PATH, "/");
        return JetEngine.create(config);
    }

    private static Map<String, Object> getContext() {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("WEBROOT", "http://subchen.github.io");
        map.put("VERSION", "2.0.0");
        map.put("title", "jetbrick-template XXX");
        return map;
    }

    public static String render() {
        return null;
    }

}
