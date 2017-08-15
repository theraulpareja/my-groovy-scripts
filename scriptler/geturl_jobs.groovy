//list all jobs name started by a pattern

//Get the jenkins java instance
jenkins_master = jenkins.model.Jenkins.instance
name_pattern = name_job

//Print all the items that contain 'ess' as the start of the name
for (item in jenkins_master.getAllItems()) {
	name = item.name
  if (name.startsWith(name_pattern)){
	println('found job: ' + name + "url: " + item.getUrl())
    //    println('found job:' + name)
  }
}

//Searches jobs starting by a given pattern, run only on master
//Params scriptler name_pattern
