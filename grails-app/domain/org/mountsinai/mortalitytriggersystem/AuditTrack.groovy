package org.mountsinai.mortalitytriggersystem

class AuditTrack {

    long id
    String mortalityReviewFormId
    String prevStatus
    String updatedStatus
    String updatedBy
    Date dateUpdated = new Date()

    static mapping = {
        version false
    }

    static constraints = {
        mortalityReviewFormId nullable:true
    }
}
