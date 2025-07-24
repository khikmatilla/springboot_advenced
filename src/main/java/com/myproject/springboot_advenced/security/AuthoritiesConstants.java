package com.myproject.springboot_advenced.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String AUDIT_READ = "ROLE_AUDIT_READ";

    public static final String ORGANIZATION_CREATE = "ROLE_ORGANIZATION_CREATE";
    public static final String ORGANIZATION_READ = "ROLE_ORGANIZATION_READ";
    public static final String ORGANIZATION_UPDATE = "ROLE_ORGANIZATION_UPDATE";
    public static final String ORGANIZATION_DELETE = "ROLE_ORGANIZATION_DELETE";

    public static final String ORGANIZATION_REQUISITE_CREATE = "ROLE_ORGANIZATION_REQUISITE_CREATE";
    public static final String ORGANIZATION_REQUISITE_READ = "ROLE_ORGANIZATION_REQUISITE_READ";
    public static final String ORGANIZATION_REQUISITE_UPDATE = "ROLE_ORGANIZATION_REQUISITE_UPDATE";
    public static final String ORGANIZATION_REQUISITE_DELETE = "ROLE_ORGANIZATION_REQUISITE_DELETE";

    public static final String AGENT_CREATE = "ROLE_AGENT_CREATE";
    public static final String AGENT_READ = "ROLE_AGENT_READ";
    public static final String AGENT_UPDATE = "ROLE_AGENT_UPDATE";
    public static final String AGENT_DELETE = "ROLE_AGENT_DELETE";

    public static final String ADDRESS_CREATE = "ROLE_ADDRESS_CREATE";
    public static final String ADDRESS_READ = "ROLE_ADDRESS_READ";
    public static final String ADDRESS_UPDATE = "ROLE_ADDRESS_UPDATE";
    public static final String ADDRESS_DELETE = "ROLE_ADDRESS_DELETE";

    public static final String CLIENT_CREATE = "ROLE_CLIENT_CREATE";
    public static final String CLIENT_READ = "ROLE_CLIENT_READ";
    public static final String CLIENT_UPDATE = "ROLE_CLIENT_UPDATE";
    public static final String CLIENT_DELETE = "ROLE_CLIENT_DELETE";
    public static final String CLIENT_CARD_SYNC = "ROLE_CLIENT_CARD_SYNC";


    public static final String CLIENT_EXTERNAL_ID_CREATE = "ROLE_CLIENT_EXTERNAL_ID_CREATE";
    public static final String CLIENT_EXTERNAL_ID_READ = "ROLE_CLIENT_EXTERNAL_ID_READ";
    public static final String CLIENT_EXTERNAL_ID_UPDATE = "ROLE_CLIENT_EXTERNAL_ID_UPDATE";
    public static final String CLIENT_EXTERNAL_ID_DELETE = "ROLE_CLIENT_EXTERNAL_ID_DELETE";

    public static final String COMMISSION_CREATE = "ROLE_COMMISSION_CREATE";
    public static final String COMMISSION_READ = "ROLE_COMMISSION_READ";
    public static final String COMMISSION_UPDATE = "ROLE_COMMISSION_UPDATE";
    public static final String COMMISSION_DELETE = "ROLE_COMMISSION_DELETE";

    public static final String LOAN_CREATE = "ROLE_LOAN_CREATE";
    public static final String LOAN_READ = "ROLE_LOAN_READ";
    public static final String LOAN_UPDATE = "ROLE_LOAN_UPDATE";
    public static final String LOAN_DELETE = "ROLE_LOAN_DELETE";

    public static final String SUB_AGENT_CREATE = "ROLE_SUB_AGENT_CREATE";
    public static final String SUB_AGENT_READ = "ROLE_SUB_AGENT_READ";
    public static final String SUB_AGENT_UPDATE = "ROLE_SUB_AGENT_UPDATE";
    public static final String SUB_AGENT_DELETE = "ROLE_SUB_AGENT_DELETE";

    public static final String TRANSACTION_CREATE = "ROLE_TRANSACTION_CREATE";
    public static final String TRANSACTION_READ = "ROLE_TRANSACTION_READ";
    public static final String TRANSACTION_UPDATE = "ROLE_TRANSACTION_UPDATE";
    public static final String TRANSACTION_DELETE = "ROLE_TRANSACTION_DELETE";
    public static final String TRANSACTION_CANCEL = "ROLE_TRANSACTION_CANCEL";
    public static final String TRANSACTION_STATUS_CHECK = "ROLE_TRANSACTION_STATUS_CHECK";

    public static final String CLIENT_LOAN_CREATE = "ROLE_CLIENT_LOAN_CREATE";

    private AuthoritiesConstants() {
    }
}
