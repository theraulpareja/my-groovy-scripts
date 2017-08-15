//Script that shows how many jobs/builds are being executed by an specific slave/node

//function that searches for a given slave name and returns the number of buils/jobs is running 
def polljobs(node){
    for (slave in jenkins.model.Jenkins.instance.slaves) {
        if (slave.name.equals(node)){
            return slave.getComputer().countBusy()
        }
    }
 return -1
}

name = slave_name
 
result = polljobs(name)

println(result)

//Returns the amount of jenkins jobs being executed by a slave, run it only on master
//Parameters scriptler slave_name
