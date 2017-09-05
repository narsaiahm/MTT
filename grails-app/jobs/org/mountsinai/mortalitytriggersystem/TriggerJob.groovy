package org.mountsinai.mortalitytriggersystem

class TriggerJob {

	def mortalityTriggerService;

	static triggers = {
		//Every 15 Minute 8AM to 6PM and Mon-Sat
		cron name: 'cronMortalityTrigger', cronExpression: "0 0/1 08-09 ? * MON-SAT"
	}
//executing Job
	def execute() {
		def system = System.getProperty("os.name")
		def serverName = System.getProperty("weblogic.Name")
		if(system != null && system.indexOf("Win") > -1){
			serverName = "localhost"
		}
		if(serverName != null && !serverName.endsWith("node3")){
			mortalityTriggerService.checkMortalityReviewFiles()
		} else{
			println "Server Name (${serverName}) does not match, Aborting the Job."
		}

	}
}
