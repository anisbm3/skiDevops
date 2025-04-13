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
    stage('GIT') {
  steps {
    sh 'git fetch --all --force'
    sh 'git reset --hard origin/FaresJerbi-4TWIN2-G4' // Si tu souhaites récupérer ta branche spécifique
  }
}


    stage('Checkout') {
      steps {
        // Forcer un clean checkout au cas où Jenkins garde des références cassées
        checkout([$class: 'GitSCM',
          branches: [[name: '*/FaresJerbi-4TWIN2-G4']],
          userRemoteConfigs: [[url: 'https://github.com/anisbm3/skiDevops.git']],
          extensions: [
            [$class: 'WipeWorkspace'],           // nettoyage du workspace
            [$class: 'CleanBeforeCheckout']      // nettoyage Git
          ]
        ])
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
