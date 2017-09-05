package org.mountsinai.mortalitytriggersystem

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class FacilityController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Facility.list(params), model:[facilityCount: Facility.count()]
    }

    def show(Facility facility) {
        respond facility
    }

    def create() {
        respond new Facility(params)
    }

    @Transactional
    def save(Facility facility) {
        if (facility == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (facility.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond facility.errors, view:'create'
            return
        }

        facility.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'facility.label', default: 'Facility'), facility.id])
                redirect facility
            }
            '*' { respond facility, [status: CREATED] }
        }
    }

    def edit(Facility facility) {
        respond facility
    }

    @Transactional
    def update(Facility facility) {
        if (facility == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (facility.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond facility.errors, view:'edit'
            return
        }

        facility.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'facility.label', default: 'Facility'), facility.id])
                redirect facility
            }
            '*'{ respond facility, [status: OK] }
        }
    }

    @Transactional
    def delete(Facility facility) {

        if (facility == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        facility.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'facility.label', default: 'Facility'), facility.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'facility.label', default: 'Facility'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
