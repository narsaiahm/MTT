package org.mountsinai.mortalitytriggersystem

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MortalityReviewFormController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MortalityReviewForm.list(params), model:[mortalityReviewFormCount: MortalityReviewForm.count()]
    }

    def show(MortalityReviewForm mortalityReviewForm) {
        respond mortalityReviewForm
    }

    def create() {
        respond new MortalityReviewForm(params)
    }

    @Transactional
    def save(MortalityReviewForm mortalityReviewForm) {
        if (mortalityReviewForm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mortalityReviewForm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mortalityReviewForm.errors, view:'create'
            return
        }

        mortalityReviewForm.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mortalityReviewForm.label', default: 'MortalityReviewForm'), mortalityReviewForm.id])
                redirect mortalityReviewForm
            }
            '*' { respond mortalityReviewForm, [status: CREATED] }
        }
    }

    def edit(MortalityReviewForm mortalityReviewForm) {
        respond mortalityReviewForm
    }

    @Transactional
    def update(MortalityReviewForm mortalityReviewForm) {
        if (mortalityReviewForm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mortalityReviewForm.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mortalityReviewForm.errors, view:'edit'
            return
        }

        mortalityReviewForm.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'mortalityReviewForm.label', default: 'MortalityReviewForm'), mortalityReviewForm.id])
                redirect mortalityReviewForm
            }
            '*'{ respond mortalityReviewForm, [status: OK] }
        }
    }

    @Transactional
    def delete(MortalityReviewForm mortalityReviewForm) {

        if (mortalityReviewForm == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        mortalityReviewForm.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'mortalityReviewForm.label', default: 'MortalityReviewForm'), mortalityReviewForm.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mortalityReviewForm.label', default: 'MortalityReviewForm'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
