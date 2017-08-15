# My Groovy Scripts

Just a collection of groovy scripts found arround the internet and customized for my needs in some jenkins administration tasks, with the will to learn a little bit of groovy.

### How to run them

You will need to setup in your workstation the right jenkins_id_rsa key to run the jenkins-cli.jar groovy scripts, 
and be able to connect to the jenkins master via ssh key pair.

In the repo the jenkins-cli.jar is already present (although is really a bad practice to add a binary into a git repo ...)

To run a groovy script against a jenkins master run:

```
java -jar jenkins-cli.jar -s <jenkins_master_fqdn>/ -i <path/to/your/jenkins_id_rsa> groovy </path/to/some_groovy_local.groovy>
```

