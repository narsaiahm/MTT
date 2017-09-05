package org.mountsinai.mortalitytriggersystem

class Speciality {

    long id
    String specialityName
    Date dateCreated
    String createdBy
    Date dateUpdated
    String updatedBy

    static belongsTo = [dept:Department]
    static hasMany = [leads:MortalityUser]


    static constraints = {
    }

    static mapping = {
        version false
        leads  nullable:true

    }

    @Override
    String toString() {
        return dept?.departmentName?.toString() +" : " +specialityName
    }
}
