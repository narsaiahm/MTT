package org.mountsinai.mortalitytriggersystem

class MortalityUser {

    long id
    String name
    String email
    String networkId
    Date dateCreated
    String createdBy
    Date dateUpdated
    String updatedBy

    static hasMany = [depts:Department,specialities:Speciality]

    static belongsTo = [Department,Speciality]

    static constraints = {
        networkId nullable:true
        email nullable:true
        depts nullable:true
        specialities nullable:true
        }

    static mapping = {
        version false
        }

    @Override
    String toString(){
        return this.name
    }
}

