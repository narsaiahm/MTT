package org.mountsinai.mortalitytriggersystem

class Department {

    long id
    String departmentName
    Date dateCreated
    String createdBy
    Date dateUpdated
    String updatedBy

    static belongsTo = [facility:Facility]
    static hasMany = [specialities:Speciality,admins:MortalityUser]

    static constraints = {
    }

    static mapping = {
        version false
        specialities nullable :true

    }

    @Override
    String toString() {
        return  facility?.facilityCode?.toString()+ " : "+departmentName
    }
}
