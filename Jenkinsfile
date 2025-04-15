pipeline {
  agent any

  environment {
    SONAR_TOKEN = credentials('sonar-token')
    JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
            DOCKERHUB_CREDENTIALS = credentials('dockerhub-credentials')

     DOCKER_IMAGE_NAME = 'faresjerbi/faresjerbi4twin2skidevops'
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
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
  stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }
     stage('MVN SONARQUBE') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.login=$SONAR_TOKEN -Dmaven.test.skip=true'
            }
        }
    stage('Docker Login') {
            steps {
                sh 'echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_IMAGE_NAME}:latest"
            }
        }
  }
}
