pipeline {
  agent any

  environment {
    JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
    PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
  }

  stages {
    stage('Clean Workspace') {
      steps {
        cleanWs() // nettoyage complet du répertoire
      }
    }

stage('Git Clean and Fetch') {
  steps {
    sh 'git clean -fdx' // Supprime tous les fichiers non suivis
    sh 'git fetch --all --prune' // Force la récupération de toutes les branches
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
          // ⚠️ Sécurité : utiliser simple quotes autour de la commande pour éviter l’interpolation groovy
          sh '''mvn sonar:sonar -Dsonar.login=${SONAR_TOKEN} -Dmaven.test.skip=true'''
        }
      }
    }
  }
}
