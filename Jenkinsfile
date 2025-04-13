pipeline {
  agent any

  tools {
    jdk 'JAVA_HOME'       // Assurez-vous que ce nom correspond à celui défini dans Jenkins > Global Tool Configuration
    maven 'M2_HOME'       // Idem ici pour Maven
  }

  stages {
    stage('GIT') {
      steps {
        git branch: 'subscription', url: 'https://github.com/anisbm3/skiDevops.git'
      }
    }

    stage('Compile Stage') {
      steps {
        sh 'mvn clean compile'
      }
    }
  }
}
