//List all installed plugins name, shortname and verison

//Library to run groovy scripts on a node
import hudson.util.RemotingDiagnostics;

jenkins_master = jenkins.model.Jenkins.instance;

//Get hostname 
print_hostname = 'println InetAddress.localHost.canonicalHostName';
jenkins_master_name = RemotingDiagnostics.executeGroovy(print_hostname, jenkins_master.getChannel());

println('\n########################################################');
println ('# PLUGINS INSTALLED ON ' + jenkins_master_name );
println('########################################################\n');
jenkins_master.pluginManager.plugins.each{
  plugin -> 
    println ("${plugin.getDisplayName()}\t${plugin.getShortName()}: ${plugin.getVersion()}")
}
println('*********************************')
