// list jobs not run in the last 6 months

import groovy.time.TimeCategory
import hudson.model.AbstractProject;
import hudson.model.Job;
import com.synopsys.arc.jenkins.plugins.ownership.jobs.JobOwnerHelper;
import com.synopsys.arc.jenkins.plugins.ownership.OwnershipDescription;
import com.synopsys.arc.jenkins.plugins.ownership.util.OwnershipDescriptionHelper;

//Get jenkins master instance
jenkins_master = jenkins.model.Jenkins.instance

def getTimeTrigger(months) {
  int monthsint = months as Integer
  timestamp_trigger = new Date().minus(monthsint * 30)
  return timestamp_trigger
}

//In Scriplter set that as a parameter
months = 6
time_trigger = getTimeTrigger(months)

//Prompt the header of the script result
println '====================================================================================\n'
println 'JOBS NOT BEING RUN IN THE LAST ' + months + ' months' 
println '====================================================================================\n'

//loop over all jenkins jobs
jobs = jenkins_master.getAllItems()
jobs.each { j ->
  //skip Folders  OrganizationFolder and WorkflowMultibranchProjects
  if (j instanceof com.cloudbees.hudson.plugins.folder.Folder) { return }
  if (j instanceof jenkins.branch.OrganizationFolder) { return }
  if (j instanceof org.jenkinsci.plugins.workflow.multibranch.WorkflowMultiBranchProject) { return }

  //Tell if jobs has no builds
  numbuilds = j.builds.size()

  if (numbuilds == 0) {
    println '===================================================================================='
    println 'JOB: ' + j.fullName
    println "\thas no builds"
    return
  }
 
  //Calculate the time of the last build is bigger than time_trigger
  lastbuild = j.builds[numbuilds - 1]
  if (lastbuild.timestamp.getTime() < time_trigger) {
    OwnershipDescription descr = JobOwnerHelper.Instance.getOwnershipDescription(j)
    println '===================================================================================='
    println 'JOB: ' + j.fullName
    println '\tOwner: ' + OwnershipDescriptionHelper.getOwnerID(descr) + ' mail: ' + OwnershipDescriptionHelper.getOwnerEmail(descr) +  ' lastbuild: ' + lastbuild.displayName + ' = ' + lastbuild.result + ', time: ' + lastbuild.timestampString2
  }
}

