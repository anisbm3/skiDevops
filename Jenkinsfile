pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'
        maven 'M2_HOME'
    }

    environment {
        SONAR_TOKEN = credentials('sonar-global-token-id')
        DOCKER_CREDENTIALS_ID = 'docker-hub-credentials-id'
        DOCKER_IMAGE = 'emnaaaaaaa/borgiemna-five-as-projet-ski'
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

        stage('Docker Image Stage') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: env.DOCKER_CREDENTIALS_ID, usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh """
                            docker login -u $DOCKER_USERNAME -p $DOCKER_PASSWORD
                            docker tag ${DOCKER_IMAGE}:1.0.0 ${DOCKER_IMAGE}:new-tag
                            docker push ${DOCKER_IMAGE}:new-tag
                            docker logout
                        """
                    }
                }
            }
        }
    }
}
