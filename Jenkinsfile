pipeline {
  agent {
	
    kubernetes {
      label 'promo-app'  // all your pods will be named with this prefix, followed by a unique id
      idleMinutes 5  // how long the pod will live after no jobs have run on it
      yamlFile 'build-pod.yaml'  // path to the pod definition relative to the root of our project 
      defaultContainer 'maven'  // define a default container if more than a few stages use it, will default to jnlp container
    }
  }
  stages {
	
    stage('Build') {
      steps {  // no container directive is needed as the maven container is the default
        sh "mvn clean package"   

      }
      
    }
 stage('Test') {
            steps {
                sh 'mvn test'
            }
        }

    stage('Deploy Image') {
      steps{
	container('docker') {
		script{
	docker.withRegistry(  , 'docker') {   	
	  def myImg = docker.image('kirtiazad11111/test')
		 sh "docker build  -t ${myImg.imageName()}:${env.BUILD_NUMBER} ."
  			// or docker.build, etc.
 		 sh "docker push  ${myImg.imageName()}:${env.BUILD_NUMBER}"

          }
		}
        }
      }
    }
	              stage('Sanity check') {
               agent none
            steps {
                input "Does the staging environment look ok?"
            }
        }
  }
	
      post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
