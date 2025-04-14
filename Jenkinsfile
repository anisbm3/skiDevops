pipeline {
    agent any

    environment {
        DOCKER_CREDENTIALS = credentials('docker-credentials-id')
    }

    stages {
        stage('Checkout SCM') {
            steps {
                checkout scm
            }
        }

        stage('Compile Stage') {
            steps {
                sh 'mvn clean compile'
            }
        }

        stage('MVN SonarQube') {
            steps {
                withCredentials([string(credentialsId: 'sonar-token', variable: 'SONAR_TOKEN')]) {
                    sh 'mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dmaven.test.skip=true'
                }
            }
        }

        stage('MVN Nexus') {
            steps {
                sh 'mvn deploy -Dmaven.test.skip=true'
            }
        }

        stage('Build Docker Image') {
            steps {
                script {
                    sh 'docker build -t emnaaaaaaa/borgiemna-five-as-projet-ski:1.0.0 .'
                }
            }
        }

        stage('Docker Image Stage') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: 'docker-credentials-id', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                        sh 'docker tag emnaaaaaaa/borgiemna-five-as-projet-ski:latest emnaaaaaaa/borgiemna-five-as-projet-ski:new-tag'
                        sh 'echo ${DOCKER_PASSWORD} | docker login -u ${DOCKER_USERNAME} --password-stdin'
                        sh 'docker push emnaaaaaaa/borgiemna-five-as-projet-ski:new-tag'
                    }
                }
            }
        }
    }

    post {
        always {
            sh 'docker logout'
        }
    }
}
