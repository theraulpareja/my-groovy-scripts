//Library to run groovy scripts on slaves
import hudson.util.RemotingDiagnostics;
//To run threads
import java.util.concurrent.*

//First get the jenkins java instance to use jenkins JAVA API aand access internal data
jenkins_master = jenkins.model.Jenkins.instance;

//Function tells if a slave name matches a pattern
def slave_name_matches(slave, pattern){
  if (slave.name.contains(pattern)){
    return true;
  }
  else {
    return false;
  }
}

//Function access to system environments
def getEnviron(computer) {
   def env
   def thread = Thread.start("Getting env from ${computer.name}", { env = computer.environment })
   thread.join(2000)
   if (thread.isAlive()) thread.interrupt()
   env
}

//Function checks slave is accessible
def slave_accessible(computer) {
  if (getEnviron(computer)?.get('PATH') != null){
    return true;
  }
  else {
    return false;
  }
}

//Function checks slave is online
def slave_online(slave){
  def computer = slave.computer
  if (!computer.offline){
    return true;
  }
  else {
    return false;
  }
}

//Function that tells if a slave is running a job at the moment
def slave_busy(slave){
  if (slave.getComputer().countBusy() > 0){
    return true;
  }
  else {
    return false;
  } 
}

//Function parses uptime output
def parse_uptime(uptime, max_days){
  def day_value = uptime.split()
  return day_value[2]
}


//Commands to be executed on each slave
shell_command        = 'uptime';
max_days_up	     = 3;
print_command_output = "def proc = \"$shell_command\".execute(); proc.waitFor(); println proc.in.text"
print_ip             = 'println InetAddress.localHost.hostAddress';
print_hostname       = 'println InetAddress.localHost.canonicalHostName';
pattern 	     = 'ubuntu';

//Look for pattern matching slaves only and print some info
for (slave in jenkins_master.slaves) {

  if (slave_name_matches(slave, pattern)) {
    println "*************************************"
    println "Slave matching the pattern: " + pattern
    println slave.name;
    println 'Checking if ' + slave.name + 'is online'
    
    //Check if slave is online, available and if is currently running a job in order to be 
    //safely rebooted
    if (slave_online(slave)){
      println (slave.name + 'is online, checking availability');
      if (slave_accessible(slave.computer)){
        println (slave.name + 'is available, checking if is busy');
          if (!slave_busy(slave)){
	    try {
	      println (slave.name + 'is not running any job ATM and can be safely rebooted');
              println ('IP: ' + RemotingDiagnostics.executeGroovy(print_ip, slave.getChannel()));
              println ('Hostname: ' + RemotingDiagnostics.executeGroovy(print_hostname, slave.getChannel()));
            }
            catch(all){
	      println('ERROR: Can not get name, IP and hostname from slave');
            }
	    try {
	      slave_command_output = RemotingDiagnostics.executeGroovy(print_command_output, slave.getChannel());
              println ('Output from execution of ' + shell_command + ' :\t' + slave_command_output);
	    }
	    catch(all){
	      println ('ERROR: Can not run' + shell_command + ' on ' + slave.name );
            }
            try {
	      slave_uptime_days =  parse_uptime(slave_command_output, max_days_up);
              println(slave.name + ' is ' + slave_uptime_days + ' days up');
            }
            catch(all){
              println('ERROR: Can not get uptime days from' + slave.name );
            }	    
          }
          else {
            println ('WARNING:' + slave.name + 'is busy running jobs, will SKIP its restart');
          }
      }
      else {
	println ('ERROR:' + slave.name + 'is not available');
      }
    }
    else {
      println (slave.name + ' is offline ');
      println( slave.name + 'Offline cause?' + slave.getComputer().getOfflineCause());
      println( slave.name + 'Temporary Offline?' + slave.getComputer().isTemporarilyOffline());
    }
  }
}

println ('Script finished');
println "*************************************"
