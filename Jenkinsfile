pipeline {
    agent any

    tools {
        jdk 'JAVA_HOME'       // Assurez-vous que ce nom correspond à celui défini dans Jenkins > Global Tool Configuration
        maven 'M2_HOME'       // Idem ici pour Maven
    }

    environment {
        SONAR_TOKEN = credentials('sonar-global-token-id') // Remplacez 'sonar-global-token-id' par l'ID de votre credential Jenkins
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
    }
}
