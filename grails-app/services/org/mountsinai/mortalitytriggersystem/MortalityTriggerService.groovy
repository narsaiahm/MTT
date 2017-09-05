package org.mountsinai.mortalitytriggersystem

import grails.transaction.Transactional
import com.xlson.groovycsv.CsvParser

@Transactional
class MortalityTriggerService {

    def checkMortalityReviewFiles() {
        mortalityReviewUpdate()
    }

    def mortalityReviewUpdate(){
        File file = new File("C:\\Users\\Admin\\Downloads\\BIPetrie_IPMortalityReport.csv")
        def fileString = new String(file.getBytes())
        def mortalityReviewForm
        def mortalityReviewFormMap = [:]

        def data = new CsvParser().parseCsv(fileString)

        for(fields in data) {
            println fields
            mortalityReviewForm = new MortalityReviewForm()
            mortalityReviewForm.admitUnit = fields[0]
            mortalityReviewForm.patientName = fields[1]
            mortalityReviewForm.mrn = fields[2]
            mortalityReviewForm.gender = fields[3]
            mortalityReviewForm.admittingDiagnosis = fields[4]
            mortalityReviewForm.dischargeDiagnosis = fields[5]
            mortalityReviewForm.dischargeUnit = fields[6]
            mortalityReviewForm.admitDateTime = new Date().parse("mm/dd/yyyyHH:mm",(fields[7]+fields[8]).toString())
            mortalityReviewForm.expiredDateTime=new Date().parse("mm/dd/yyyyHH:mm",(fields[9]+fields[10]).toString())
            mortalityReviewForm.hospService = fields[11]
            mortalityReviewForm.lastAttending =fields[12]
            mortalityReviewForm.dictationCode = fields[13]
            mortalityReviewForm.serviceTeam = fields[14]
            mortalityReviewForm.visitId = fields[15]
            mortalityReviewForm.hospitalSite = fields[16]

            mortalityReviewFormMap[fields[2]] = mortalityReviewForm

        }

        if(mortalityReviewFormMap.size()>0){
            //batch insert
            mortalityReviewFormMap.each {
                def mortalityReviewFormObj = it.value
                MortalityReviewForm.withTransaction{
                    mortalityReviewFormObj.save(flush:true,failOnError:true)
                }
            }

            MortalityReviewForm.withSession{ session ->
                session.clear()
            }
            println "MortalityReviewForm Information inserted at " +new Date()
        }
    }
}
