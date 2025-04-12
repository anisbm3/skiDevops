pipeline {
  agent any
  tools {
    jdk 'JAVA_HOME'
    maven 'M2_HOME'
  }
  
  stages {
    stage('GIT') {
      steps {
        git branch: 'anis', url: 'https://github.com/anisbm3/skiDevops.git'
      }
    }
    stage('Compile Stage') {
      steps {
        sh 'mvn clean compile'
      }
    }
