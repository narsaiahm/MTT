package org.mountsinai.mortalitytriggersystem

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class MortalityUserController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond MortalityUser.list(params), model:[mortalityUserCount: MortalityUser.count()]
    }

    def show(MortalityUser mortalityUser) {
        respond mortalityUser
    }

    def create() {
        respond new MortalityUser(params)
    }

    @Transactional
    def save(MortalityUser mortalityUser) {
        if (mortalityUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mortalityUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mortalityUser.errors, view:'create'
            return
        }

        mortalityUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'mortalityUser.label', default: 'MortalityUser'), mortalityUser.id])
                redirect mortalityUser
            }
            '*' { respond mortalityUser, [status: CREATED] }
        }
    }

    def edit(MortalityUser mortalityUser) {
        respond mortalityUser
    }

    @Transactional
    def update(MortalityUser mortalityUser) {
        if (mortalityUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (mortalityUser.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond mortalityUser.errors, view:'edit'
            return
        }

        mortalityUser.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'mortalityUser.label', default: 'MortalityUser'), mortalityUser.id])
                redirect mortalityUser
            }
            '*'{ respond mortalityUser, [status: OK] }
        }
    }

    @Transactional
    def delete(MortalityUser mortalityUser) {

        if (mortalityUser == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        mortalityUser.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'mortalityUser.label', default: 'MortalityUser'), mortalityUser.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'mortalityUser.label', default: 'MortalityUser'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
