pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'       // Assurez-vous que ce nom correspond à celui défini dans Jenkins > Global Tool Configuration
        maven 'M2_HOME'       // Idem ici pour Maven
    }

    environment {
        SONAR_TOKEN = credentials('sonar-global-token-id') // Remplacez 'sonar-global-token-id' par l'ID de votre credential Jenkins
        DOCKER_IMAGE_NAME = 'emnaaaaaaa/borgiemna-grp4-ski'
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials-id' // Remplacez par l'ID de vos credentials Docker Hub
    }

    stages {
        stage('GIT') {
            steps {
                git branch: 'BorgiEmna-4TWIN2', url: 'https://github.com/anisbm3/skiDevops.git'
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

        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true -Drevision=${BUILD_NUMBER}'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh "docker build -t ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ."
                sh "docker tag ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG} ${DOCKER_IMAGE_NAME}:latest"
            }
        }

        stage('Push Docker Image to DockerHub') {
            steps {
                script {
                    // Authentification Docker Hub
                    withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh "docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD"
                        sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                        sh "docker push ${DOCKER_IMAGE_NAME}:latest"
                        sh "docker logout"
                    }
                }
            }
        }
    }
}
