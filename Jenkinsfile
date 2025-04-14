pipeline {
  agent any

  environment {
    SONAR_TOKEN = credentials('sonar-token')
    JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
  }

stages {
  stage('Clean Workspace') {
    steps {
      cleanWs()
    }
  }

  stage('Checkout') {
    steps {
      checkout scm
    }
  }

  stage('Git Clean and Fetch') {
    steps {
      sh 'git clean -fdx'
      sh 'git fetch --all --prune'
    }
  }

    stage('Build') {
      steps {
        catchError(buildResult: 'FAILURE', stageResult: 'FAILURE') {
          sh 'mvn clean install'
        }
      }
    }
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
                sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dmaven.test.skip=true'
            }
        }
  }
}
