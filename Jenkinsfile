pipeline {
  agent any


  environment {
        JAVA_HOME = "/usr/lib/jvm/java-17-openjdk-amd64"
        PATH = "${env.JAVA_HOME}/bin:${env.PATH}"
    }
  stages {
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

 
 stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=squ_37442c9b66302494fe1347842cf5c9d285343b99 -Dmaven.test.skip=true';
            }
    }
      }
    }
  
}
