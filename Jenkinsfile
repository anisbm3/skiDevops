pipeline {
  agent any


  environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
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
        withCredentials([string(credentialsId: 'sonar', variable: 'SONAR_TOKEN')]) {
          sh "mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dmaven.test.skip=true"
        }
      }
    }
  }
}
