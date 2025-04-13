pipeline {
  agent any

   environment {
    JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
    PATH = "${JAVA_HOME}/bin:${env.PATH}"
  }

  stages {
    stage('Check Java') {
      steps {
        sh 'echo JAVA_HOME=$JAVA_HOME'
        sh '$JAVA_HOME/bin/java -version'
        sh '$JAVA_HOME/bin/javac -version'
      }
    }

    stage('Compile') {
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
