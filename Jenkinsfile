pipeline {
  agent any

   environment {
    JAVA_HOME = '/usr/lib/jvm/java-17-openjdk-amd64'
    PATH = "${JAVA_HOME}/bin:${env.PATH}"
  }

  stages {
    stage('Check Java') {
      steps {
        sh 'echo JAVA_HOME=$JAVA_HOME'
        sh '$JAVA_HOME/bin/java -version'
        sh '$JAVA_HOME/bin/javac -version'
      }
    }

    stage('Compile') {
      steps {
        sh 'mvn clean compile'
      }
    }
            stage('Run Unit Tests') {
            steps {
                script {
                    try {
                        sh '''
                            mvn test
                        '''
                    } catch (Exception e) {
                        echo "Tests failed: ${e.message}"
                    }
                }
            }
        }

    stage('SonarQube Analysis') {
            steps {
                sh 'mvn sonar:sonar -Dsonar.token=sqa_7f0b6cf17ec158c2ffdabbf5e07c6f401475aecf -Dmaven.test.skip=true';
            }
    }
  /*   stage('MVN Nexus'){
    		steps {
    			sh 'mvn deploy -Dmaven.test.skip=true'
    		}
	    }*/
	  
stage('Docker Image Stage') {
    steps {
        sh """
            # Login to Docker
            docker login -u anisbm3 -p 25/01/2003
            
            # Tag the already built image with a new tag
            docker tag anisbm3//anisbenmehrez-4twin2-g4-stationski:1.0.0 anisbm3/anisbenmehrez-4twin2-g4-stationski:new-tag
            
            # Push the image with the new tag
            docker push anisbm3/anisbenmehrez-4twin2-g4-stationski:new-tag
        """
    }
}
  }
}
