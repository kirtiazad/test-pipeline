pipeline {
	  def app  
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
            stage('Sanity check') {
               agent none
            steps {
                input "Does the staging environment look ok?"
            }
        }
    stage('Building image') {
      steps{
	      container('docker') {
          app = docker.build("kirtiazad1111/test")   
        
      }
    }
    }
    stage('Deploy Image') {
      steps{
	container('docker') {
	docker.withRegistry('https://registry.hub.docker.com', 'git') {            
       		app.push("${env.BUILD_NUMBER}") 
             dockerImage.push('latest')

          }
	
        }
      }
    }
  }
      post {
        always {
            junit '**/target/surefire-reports/*.xml'
        }
    }
}
