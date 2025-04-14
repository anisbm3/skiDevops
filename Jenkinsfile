pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        SONAR_TOKEN = credentials('sonar-global-token-id')
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials-id'  // Use the ID you configured in Jenkins
        DOCKER_IMAGE_NAME = 'emnaaaaaaa/borgiemna-five-as-projet-ski'
        DOCKER_IMAGE_TAG = "${BUILD_NUMBER}"
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
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Docker Login') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: "${DOCKER_CREDENTIALS_ID}", usernameVariable: 'DOCKERHUB_CREDENTIALS_USR', passwordVariable: 'DOCKERHUB_CREDENTIALS_PSW')]) {
                        sh 'echo "$DOCKERHUB_CREDENTIALS_PSW" | docker login -u "$DOCKERHUB_CREDENTIALS_USR" --password-stdin'
                    }
                }
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
                sh "docker push ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}"
                sh "docker push ${DOCKER_IMAGE_NAME}:latest"
            }
        }
stage('Deploy with Docker Compose') {
            steps {
                script {
                    sh """
                    sed -i 's|image: emnaaaaaaa/borgiemna-five-as-projet-ski:.*|image: ${DOCKER_IMAGE_NAME}:${DOCKER_IMAGE_TAG}|' docker-compose.yml
                    """

                    sh 'docker compose down'
                    sh 'docker compose up -d'
                }
            }
        }

        stage('Cleanup') {
            steps {
                sh 'docker compose down'
                sh 'docker system prune -f'
            }
        }
    }

    post {
        always {
            sh 'docker logout'
            cleanWs()
        }
        success {
            echo 'Pipeline executed successfully!'
        }
        failure {
            echo 'Pipeline execution failed!'
        }
    }
}
