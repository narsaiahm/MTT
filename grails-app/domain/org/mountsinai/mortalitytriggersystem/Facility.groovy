package org.mountsinai.mortalitytriggersystem

class Facility {

    long id
    String facilityCode
    String facilityName
    Date dateCreated
    String createdBy
    Date dateUpdated
    String updatedBy

    static hasMany = [depts:Department]

    static constraints = {
    }

    static mapping = {
        version false
    }

    @Override
    String toString() {
        return facilityName
    }
}
