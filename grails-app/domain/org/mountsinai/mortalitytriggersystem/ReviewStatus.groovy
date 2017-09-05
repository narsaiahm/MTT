package org.mountsinai.mortalitytriggersystem

class ReviewStatus {
    
    long id
    String status
    Date dateCreated
    String createdBy
    Date dateUpdated
    String updatedBy

    static constraints = {
        status unique:true
    }

    @Override
    String toString() {
        return status
    }
}
