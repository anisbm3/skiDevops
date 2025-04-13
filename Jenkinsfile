pipeline {
  agent any

  tools {
    jdk 'JAVA_HOME'       // Assurez-vous que ce nom correspond à celui défini dans Jenkins > Global Tool Configuration
    maven 'M2_HOME'       // Idem ici pour Maven
  }

  stages {
    stage('GIT') {
      steps {
        git branch: 'subscription', url: 'https://github.com/anisbm3/skiDevops.git'
      }
    }

    stage('Compile Stage') {
      steps {
        sh 'mvn clean compile'
      }
    }
    stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=sqa_7f0b6cf17ec158c2ffdabbf5e07c6f401475aecf -Dmaven.test.skip=true';
            }
    }
     stage('MVN Nexus'){
    		steps {
    			sh 'mvn deploy -Dmaven.test.skip=true'
    		}
	    }
  }
}
