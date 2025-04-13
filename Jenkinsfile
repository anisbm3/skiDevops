pipeline {
  agent any

tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }

  stages {
    stage('GIT') {
      steps {
        git branch: 'FaresJerbi-4TWIN2-G4 ', url: 'https://github.com/anisbm3/skiDevops.git'
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
