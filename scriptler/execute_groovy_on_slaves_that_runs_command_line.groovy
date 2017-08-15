//Library to run groovy scripts on slaves
import hudson.util.RemotingDiagnostics;

//First get the jenkins java instance to use its jenkins JAVA API and access internal data
jenkins_master = jenkins.model.Jenkins.instance;

print_ip       = 'println InetAddress.localHost.hostAddress';
print_hostname = 'println InetAddress.localHost.canonicalHostName';

// Execute command line in system via groovy
shell_command        = 'whoami';
print_command_output = "def proc = \"$shell_command\".execute(); proc.waitFor(); println proc.in.text"


for (slave in jenkins_master.slaves) {
//RemotingDiagnostics.executeGroovy method accepts only string as a first parameter and slave.getChannel
    println slave.name;
    println RemotingDiagnostics.executeGroovy(print_ip, slave.getChannel());
    println RemotingDiagnostics.executeGroovy(print_hostname, slave.getChannel());
    println RemotingDiagnostics.executeGroovy(print_command_output, slave.getChannel());
}

//Searches jobs starting by a given pattern, run only on master
//Params scriptler shell_command
