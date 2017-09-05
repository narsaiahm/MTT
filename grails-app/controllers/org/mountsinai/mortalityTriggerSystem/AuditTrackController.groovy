package org.mountsinai.mortalitytriggersystem

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AuditTrackController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond AuditTrack.list(params), model:[auditTrackCount: AuditTrack.count()]
    }

    def show(AuditTrack auditTrack) {
        respond auditTrack
    }

    def create() {
        respond new AuditTrack(params)
    }

    @Transactional
    def save(AuditTrack auditTrack) {
        if (auditTrack == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (auditTrack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditTrack.errors, view:'create'
            return
        }

        auditTrack.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'auditTrack.label', default: 'AuditTrack'), auditTrack.id])
                redirect auditTrack
            }
            '*' { respond auditTrack, [status: CREATED] }
        }
    }

    def edit(AuditTrack auditTrack) {
        respond auditTrack
    }

    @Transactional
    def update(AuditTrack auditTrack) {
        if (auditTrack == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (auditTrack.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond auditTrack.errors, view:'edit'
            return
        }

        auditTrack.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'auditTrack.label', default: 'AuditTrack'), auditTrack.id])
                redirect auditTrack
            }
            '*'{ respond auditTrack, [status: OK] }
        }
    }

    @Transactional
    def delete(AuditTrack auditTrack) {

        if (auditTrack == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        auditTrack.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'auditTrack.label', default: 'AuditTrack'), auditTrack.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'auditTrack.label', default: 'AuditTrack'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
