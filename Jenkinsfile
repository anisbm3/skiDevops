pipeline {
  agent any

  tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }
environment {
  JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
  PATH = "${JAVA_HOME}/bin:${env.PATH}"
}
  stages {
    stage('GIT') {
      steps {
        git branch: 'FaresJerbi-4TWIN2-G4 ', url: 'https://github.com/anisbm3/skiDevops.git'
      }
    }
 stages {
    stage('Env Check') {
      steps {
        sh 'echo JAVA_HOME=$JAVA_HOME'
        sh '$JAVA_HOME/bin/java -version || echo "❌ JAVA_HOME NOT WORKING"'
        sh '$JAVA_HOME/bin/javac -version || echo "❌ javac NOT FOUND"'
      }
    }
    stage('Compile Stage') {
      steps {
        sh 'mvn clean compile'
      }
    }
    stage('MVN SONARQUAR'){
    		steps {
    			sh 'mvn sonar:sonar -Dsonar.login=squ_f126e313c41423c79b91ae2c853288724b9e8f49 -Dmaven.test.skip=true'
    		}
	    }
  }
}
