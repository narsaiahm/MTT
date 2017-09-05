package org.mountsinai.mortalitytriggersystem

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class ReviewStatusController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond ReviewStatus.list(params), model:[reviewStatusCount: ReviewStatus.count()]
    }

    def show(ReviewStatus reviewStatus) {
        respond reviewStatus
    }

    def create() {
        respond new ReviewStatus(params)
    }

    @Transactional
    def save(ReviewStatus reviewStatus) {
        if (reviewStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (reviewStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reviewStatus.errors, view:'create'
            return
        }

        reviewStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'reviewStatus.label', default: 'ReviewStatus'), reviewStatus.id])
                redirect reviewStatus
            }
            '*' { respond reviewStatus, [status: CREATED] }
        }
    }

    def edit(ReviewStatus reviewStatus) {
        respond reviewStatus
    }

    @Transactional
    def update(ReviewStatus reviewStatus) {
        if (reviewStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (reviewStatus.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond reviewStatus.errors, view:'edit'
            return
        }

        reviewStatus.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'reviewStatus.label', default: 'ReviewStatus'), reviewStatus.id])
                redirect reviewStatus
            }
            '*'{ respond reviewStatus, [status: OK] }
        }
    }

    @Transactional
    def delete(ReviewStatus reviewStatus) {

        if (reviewStatus == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        reviewStatus.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'reviewStatus.label', default: 'ReviewStatus'), reviewStatus.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'reviewStatus.label', default: 'ReviewStatus'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
