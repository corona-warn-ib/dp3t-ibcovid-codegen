package es.caib.dp3t.ibcovid.codegen.controller.config;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.NONE)
public final class RouteConstants {
    public static final String BASE_PATH = "/v1";
    public static final String ADMIN_PATH = BASE_PATH + "/admin";

    public static final String LOGIN_PATH = BASE_PATH + "/auth/login";

    public static final String ACTUATOR_BASE_PATH = "/actuator";
    public static final String H2_CONSOLE_PATH = "/h2-console";

    public static final String CODE_GEN_BASE_PATH = BASE_PATH;
    public static final String CODE_GEN_ADMIN_BASE_PATH = ADMIN_PATH + "/access-code";
    public static final String DOWNLOADED_CODE_ADMIN_BASE_PATH = ADMIN_PATH + "/downloaded-access-code";

    // Web page routes
    public static final String WEB_APP_PATH = BASE_PATH + "/web";
    public static final String WEB_LOGIN_PATH = WEB_APP_PATH + "/login";

}
