pipeline {
  agent any

  tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }

  stages {
    stage('GIT') {
      steps {
        git branch: 'FaresJerbi-4TWIN2-G4', url: 'https://github.com/anisbm3/skiDevops.git'
      }
    }

    stage('Compile Stage') {
      steps {
        sh 'mvn clean compile'
      }
    }

    stage('MVN SONARQUBE') {
      steps {
        withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
          sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dmaven.test.skip=true"
        }
      }
    }
  }
}
