# my Groovy Scripts
Just a collection of groovy scripts for jenkins administration tasks

### How to run them
You will need to setup in your workstation the right jenkins_id_rsa key to run the jenkins-cli.jar groovy scripts, 
and be able to connect to the jenkins master via ssh key pair.

In the repo the jenkins-cli.jar is already present (athought bad practice to add a binary ina git repo I know...))

To run a groovy script against a jenkins master run:

```
java -jar jenkins-cli.jar -s <jenkins_master_fqdn>/ -i <path/to/your/jenkins_id_rsa> groovy </path/to/some_groovy_local.groovy>
```

