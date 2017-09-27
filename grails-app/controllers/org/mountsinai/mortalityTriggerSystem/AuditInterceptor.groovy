package org.mountsinai.mortalitytriggersystem


class AuditInterceptor {

    AuditInterceptor() {
        //match status update actions
        match(controller: "mortality", action: ~/(acceptReviewForm|assignLeadReviewer|saveMortalityReviewForm)/)
    }

    boolean before() {
        auditService.auditRequest(params, session?.user?.name, actionName)
    }
}
